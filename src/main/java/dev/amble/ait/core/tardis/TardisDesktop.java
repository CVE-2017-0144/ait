package dev.amble.ait.core.tardis;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.blockentities.ConsoleGeneratorBlockEntity;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blockentities.EngineBlockEntity;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.QueuedTardisStructureTemplate;
import dev.amble.ait.data.Corners;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import dev.amble.lib.data.DirectedBlockPos;
import dev.drtheo.queue.api.ActionQueue;
import dev.drtheo.queue.api.util.block.ChunkEraser;
import dev.drtheo.queue.api.util.structure.QueuedStructureTemplate;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.TicketType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.status.ChunkType;
import net.minecraft.world.level.chunk.storage.ChunkSerializer;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

public class TardisDesktop extends TardisComponent {

    private static final StructurePlaceSettings SETTINGS = new StructurePlaceSettings().setKnownShape(false);
    public static final ResourceLocation CACHE_CONSOLE = AITMod.id("cache_console");
    private TardisDesktopSchema schema;
    private DirectedBlockPos doorPos;
    private BlockPos enginePos;
    private final Corners corners;
    private final Set<BlockPos> consolePos;
    public static final int RADIUS = 500;
    private static final TicketType<ChunkPos> CHANGING_TICKET = TicketType.create("ait_desktop_change", Comparator.comparingLong(ChunkPos::toLong));
    private static final Corners CORNERS;

    static {
        BlockPos first = new BlockPos(RADIUS, 0, RADIUS);
        CORNERS = new Corners(first.multiply(-1), first);

        AitNetworking.registerServerReceiver(TardisDesktop.CACHE_CONSOLE,
                ServerTardisManager.receiveTardis(SecurityControl.withLoyaltyCheck((tardis, server, player, handler, buf, responseSender) -> {
                    BlockPos console = buf.readBlockPos();

                    server.execute(() -> {
                        if (!player.level().isLoaded(console)) return;

                        if (!(player.level().getBlockEntity(console) instanceof ConsoleBlockEntity consoleBlockEntity)) return;

                        if (tardis == null)
                            return;

                        if (consoleBlockEntity.isLinked() && consoleBlockEntity.getSonicScrewdriver() != null && !consoleBlockEntity.getSonicScrewdriver().isEmpty()) {
                            player.level().playSound(null, player.blockPosition(), AITSounds.BWEEP,
                                    SoundSource.PLAYERS, 1f, 1f);
                            player.displayClientMessage(Component.translatable("tardis.message.console.has_sonic_in_port"), true);
                            return;
                        }

                        tardis.getDesktop().cacheConsole(console);
                    });
                })));
    }

    private boolean changingDesktop = false;
    @Exclude
    private List<ChunkPos> heldChunks;

    public TardisDesktop(TardisDesktopSchema schema) {
        super(Id.DESKTOP);
        this.schema = schema;

        this.corners = CORNERS;
        this.consolePos = new HashSet<>();
    }

    @Override
    public void postInit(InitContext ctx) {
        if (ctx.created()) {
            // must be done in postInit, because it accesses door and alarm handlers
            this.changeInterior(schema, false, false).execute();
            return;
        }

        if (!this.changingDesktop || !this.isServer() || this.tardis.interiorChangingHandler().queued().get())
            return;

        Scheduler.get().runTaskLater(() -> this.changeInterior(this.schema, true, false).execute(),
                TaskStage.END_SERVER_TICK, TimeUnit.TICKS, 1);
    }

    public TardisDesktopSchema getSchema() {
        return schema;
    }

    public void setDoorPos(DoorBlockEntity door) {
        if (door == null || door.getLevel() == null || door.getLevel().isClientSide())
            return;

        DirectedBlockPos pos = door.getDirectedPos();

        if (this.doorPos != null && this.doorPos.equals(pos))
            return;

        this.doorPos = pos;
        TardisEvents.DOOR_MOVE.invoker().onMove(tardis.asServer(), pos, this.doorPos);
    }

    public void setEnginePos(EngineBlockEntity engine) {
        if (engine == null || engine.getLevel() == null || engine.getLevel().isClientSide())
            return;

        BlockPos pos = engine.getBlockPos();

        if (pos.equals(this.enginePos))
            return;

        this.enginePos = pos;
        TardisEvents.ENGINE_MOVE.invoker().onMove(tardis.asServer(), pos, this.enginePos);
    }

    public void removeDoor(DoorBlockEntity door) {
        if (this.doorPos == null)
            return;

        if (!this.doorPos.equals(door.getDirectedPos()))
            return;

        this.doorPos = null;
        TardisEvents.BREAK_DOOR.invoker().onBreak(this.tardis, doorPos);
    }

    public DirectedBlockPos getDoorPos() {
        if (this.doorPos == null) {
            // womp womp
            for (BlockPos consolePos : this.consolePos) {
                return DirectedBlockPos.create(consolePos, (byte) 0);
            }

            // oh no this this cant be
            return DirectedBlockPos.create(BlockPos.ZERO, (byte) 0);
        }

        return doorPos;
    }

    public BlockPos getEnginePos() {
        return enginePos;
    }

    // TODO this is strictly for clearing the interior now
    @Deprecated(forRemoval = true, since = "1.1.0")
    public Corners getCorners() {
        return corners;
    }

    public Optional<ActionQueue> createInteriorChangeQueue(TardisDesktopSchema schema, boolean sendEvent) {
        long start = System.currentTimeMillis();
        this.schema = schema;

        if (sendEvent)
            TardisEvents.RECONFIGURE_DESKTOP.invoker().reconfigure(this.tardis);

        ServerTardis tardis = this.tardis.asServer();
        ServerLevel world = tardis.world();

        Optional<StructureTemplate> optional = this.schema.findTemplate();

        if (optional.isEmpty()) {
            AITMod.LOGGER.error("Failed to find template for {}", this.schema.id());
            return Optional.empty();
        }

        QueuedStructureTemplate template = new QueuedTardisStructureTemplate(optional.get(), tardis);

        Optional<ActionQueue> optionalQueue = template.place(world, BlockPos.containing(corners.getBox().getCenter()),
                BlockPos.containing(corners.getBox().getCenter()), SETTINGS, world.getRandom(), Block.UPDATE_KNOWN_SHAPE);

        optionalQueue.ifPresentOrElse(queue -> queue.thenRun(
                        () -> AITMod.LOGGER.warn("Time taken to generate interior: {}ms",
                                System.currentTimeMillis() - start)),
                () -> AITMod.LOGGER.error("Failed to generate interior for {}",
                        this.tardis.getUuid())
        );

        return optionalQueue;
    }

    public ActionQueue createDesktopClearQueue() {
        ServerTardis tardis = this.tardis.asServer();
        ServerLevel world = tardis.world();
        int chunkRadius = SectionPos.blockToSectionCoord(RADIUS);

        TardisUtil.getEntitiesInBox(HangingEntity.class, world, corners.getBox(), frame -> true)
                .forEach(frame -> frame.remove(Entity.RemovalReason.DISCARDED));

        int[] bounds = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};

        return new ActionQueue().thenRun(done -> {
            ServerChunkCache chunks = world.getChunkSource();
            List<CompletableFuture<?>> reads = new ArrayList<>();

            for (int x = -chunkRadius; x <= chunkRadius; x++) {
                for (int z = -chunkRadius; z <= chunkRadius; z++) {
                    ChunkPos pos = new ChunkPos(x, z);

                    if (chunks.hasChunk(x, z)) {
                        include(bounds, pos);
                        continue;
                    }

                    reads.add(chunks.chunkMap.read(pos).thenAccept(nbt -> nbt
                            .filter(chunk -> ChunkSerializer.getChunkTypeFromTag(chunk) == ChunkType.LEVELCHUNK)
                            .ifPresent(chunk -> include(bounds, pos))));
                }
            }

            CompletableFuture.allOf(reads.toArray(CompletableFuture[]::new))
                    .whenComplete((v, e) -> world.getServer().execute(done::finish));
        }).thenRun(done -> {
            if (bounds[0] > bounds[2]) {
                done.finish();
                return;
            }

            ServerChunkCache chunks = world.getChunkSource();
            this.heldChunks = new ArrayList<>();

            for (int x = bounds[0]; x <= bounds[2]; x++) {
                for (int z = bounds[1]; z <= bounds[3]; z++) {
                    ChunkPos pos = new ChunkPos(x, z);
                    chunks.addRegionTicket(CHANGING_TICKET, pos, 0, pos);
                    this.heldChunks.add(pos);
                }
            }

            new ChunkEraser.Builder().withFlags(Block.UPDATE_KNOWN_SHAPE).build(world, bounds[0], bounds[1], bounds[2] + 1, bounds[3] + 1)
                    .thenRun(done::finish).execute();
        }).thenRun(() -> {
            this.consolePos.clear();
            this.doorPos = null;
        });
    }

    private static void include(int[] bounds, ChunkPos pos) {
        synchronized (bounds) {
            bounds[0] = Math.min(bounds[0], pos.x);
            bounds[1] = Math.min(bounds[1], pos.z);
            bounds[2] = Math.max(bounds[2], pos.x);
            bounds[3] = Math.max(bounds[3], pos.z);
        }
    }

    public void startQueue(boolean interact) {
        if (interact) // we use this for the SFX
            this.tardis.door().interactLock(true, null, false);

        this.tardis.door().setDeadlocked(true);
        this.tardis.alarm().enable();
    }

    private void completeQueue() {
        if (this.heldChunks != null) {
            ServerChunkCache chunks = this.tardis.asServer().world().getChunkSource();
            this.heldChunks.forEach(pos -> chunks.removeRegionTicket(CHANGING_TICKET, pos, 0, pos));
            this.heldChunks = null;
        }

        this.tardis.door().setLocked(false);
        this.tardis.door().setDeadlocked(false);
        this.tardis.alarm().disable();

        this.changingDesktop = false;
    }

    public ActionQueue changeInterior(TardisDesktopSchema schema, boolean clear, boolean sendEvent) {
        this.changingDesktop = true;
        ActionQueue queue = new ActionQueue()
                .thenRun(() -> this.startQueue(sendEvent));

        if (clear)
            queue.thenRun(this.createDesktopClearQueue());

        return queue.thenRun(createInteriorChangeQueue(schema, sendEvent))
                .thenRun(this::completeQueue);
    }

    public void cacheConsole(BlockPos consolePos) {
        Level dim = this.tardis.asServer().world();
        dim.playSound(null, consolePos, SoundEvents.BEACON_DEACTIVATE, SoundSource.BLOCKS, 0.5f, 1.0f);

        if (dim.getBlockEntity(consolePos) instanceof ConsoleBlockEntity entity) {
            ConsoleGeneratorBlockEntity generator = new ConsoleGeneratorBlockEntity(consolePos,
                    AITBlocks.CONSOLE_GENERATOR.defaultBlockState(), entity.getTypeSchema().id(), entity.getVariant().id());

            entity.onBroken();

            dim.removeBlock(consolePos, false);
            dim.removeBlockEntity(consolePos);

            dim.setBlock(consolePos, AITBlocks.CONSOLE_GENERATOR.defaultBlockState(), Block.UPDATE_ALL);

            dim.setBlockEntity(generator);
        }
    }

    public static void playSoundAtConsole(Level dim, BlockPos console, SoundEvent sound, SoundSource category, float volume,
                                          float pitch) {
        dim.playSound(null, console, sound, category, volume, pitch);
    }

    public void playSoundAtEveryConsole(SoundEvent sound, SoundSource category, float volume, float pitch) {
        if (!this.isServer()) return;

        ServerLevel world = this.tardis.asServer().world();

        this.getConsolePos().forEach(consolePos ->
                playSoundAtConsole(world, consolePos, sound, category, volume, pitch));
    }

    public void forcePlaySoundAtEveryConsole(ResourceLocation soundId, SoundSource category) {
        if (!this.isServer()) return;

        ResourceKey<Level> worldKey = this.tardis.asServer().world().dimension();
        this.getConsolePos().forEach(consolePos -> {
            NetworkUtil.playSound(worldKey, consolePos, soundId, category, 1);
        });
    }

    public void playSoundAtEveryConsole(SoundEvent sound, SoundSource category) {
        this.playSoundAtEveryConsole(sound, category, 1f, 1f);
    }

    public void playSoundAtEveryConsole(SoundEvent sound) {
        this.playSoundAtEveryConsole(sound, SoundSource.BLOCKS);
    }

    public Set<BlockPos> getConsolePos() {
        return consolePos;
    }

    public boolean isChanging() {
        return changingDesktop;
    }
}
