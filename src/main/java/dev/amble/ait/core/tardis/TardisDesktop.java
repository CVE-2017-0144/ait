package dev.amble.ait.core.tardis;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
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
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.QueuedTardisStructureTemplate;
import dev.amble.ait.data.Corners;
import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import dev.amble.lib.data.DirectedBlockPos;

public class TardisDesktop extends TardisComponent {

    private static final StructurePlaceSettings SETTINGS = new StructurePlaceSettings().setKnownShape(false);
    public static final ResourceLocation CACHE_CONSOLE = AITMod.id("cache_console");
    private TardisDesktopSchema schema;
    private DirectedBlockPos doorPos;
    private BlockPos enginePos;
    private final Corners corners;
    private final Set<BlockPos> consolePos;
    public static final int RADIUS = 500;
    private static final Corners CORNERS;

    static {
        BlockPos first = new BlockPos(RADIUS, 0, RADIUS);
        CORNERS = new Corners(first.multiply(-1), first);

        AitNetworking.registerServerReceiver(TardisDesktop.CACHE_CONSOLE,
                ServerTardisManager.receiveTardis((tardis, server, player, handler, buf, responseSender) -> {
                    BlockPos console = buf.readBlockPos();

                    server.execute(() -> {
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
                }));
    }

    private boolean changingDesktop = false;

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

        if (!this.changingDesktop || !this.isServer())
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

        return new ChunkEraser.Builder().withFlags(Block.UPDATE_KNOWN_SHAPE).build(
                world, -chunkRadius, -chunkRadius, chunkRadius, chunkRadius
        ).thenRun(() -> {
            this.consolePos.clear();
            this.doorPos = null;
        });
    }

    public void startQueue(boolean interact) {
        if (interact) // we use this for the SFX
            this.tardis.door().interactLock(true, null, false);

        this.tardis.door().setDeadlocked(true);
        this.tardis.alarm().enable();
    }

    private void completeQueue() {
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
