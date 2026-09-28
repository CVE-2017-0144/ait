package dev.amble.ait.core.world;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.data.landing.LandingPadRegion;
import dev.amble.lib.platform.PlayerLookup;
import dev.amble.lib.platform.lifecycle.ServerConnectionEvents;
import dev.amble.lib.platform.lifecycle.ServerPlayerEvents;
import dev.amble.lib.platform.registry.Attachments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Heightmap;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class LandingPadManager {

    private static final Attachments.Type<LandingPadRegion> PERSISTENT = Attachments.createPersistent(
            AITMod.id("landing_pads"), LandingPadRegion.CODEC
    );

    public static void init() {
        Network.init();
    }

    private final ServerLevel world;

    public LandingPadManager(ServerLevel world) {
        this.world = world;
    }

    @Nullable public LandingPadRegion getRegion(ChunkPos pos) {
        ChunkAccess chunk = this.world.getChunk(pos.x, pos.z, ChunkStatus.FULL, true);

        if (chunk == null)
            return null;

        return Attachments.get(chunk, PERSISTENT);
    }

    @Nullable public LandingPadRegion getRegion(long pos) {
        return this.getRegion(new ChunkPos(pos));
    }

    @Nullable public LandingPadRegion getRegionAt(BlockPos pos) {
        return this.getRegion(new ChunkPos(pos));
    }

    private LandingPadRegion claim(ChunkPos pos, int y) {
        LevelChunk chunk = this.world.getChunk(pos.x, pos.z);

        if (Attachments.has(chunk, PERSISTENT))
            throw new IllegalStateException("Region already occupied");

        LandingPadRegion created = new LandingPadRegion(pos, y, "");
        Attachments.set(chunk, PERSISTENT, created);

        Network.syncTracked(Network.Action.ADD, this.world, pos);
        return created;
    }

    public LandingPadRegion claim(BlockPos pos) {
        return this.claim(new ChunkPos(pos), world.getChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()))
                .getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, pos.getX() & 15, pos.getZ() & 15));
    }

    private @Nullable LandingPadRegion release(ChunkPos pos) {
        LandingPadRegion result = Attachments.remove(this.world.getChunk(pos.x, pos.z), PERSISTENT);

        Network.syncTracked(Network.Action.REMOVE, this.world, pos);
        return result;
    }

    public @Nullable LandingPadRegion releaseAt(BlockPos pos) {
        return this.release(new ChunkPos(pos));
    }

    public static LandingPadManager getInstance(ServerLevel world) {
        return new LandingPadManager(world);
    }

    public static class Network {

        public static final ResourceLocation SYNC = AITMod.id("landingpad_sync");
        public static final ResourceLocation REQUEST = AITMod.id("landingpad_request");

        public static void syncForPlayer(Action action, ServerPlayer player) {
            syncForPlayer(action, player, player.chunkPosition());
        }

        public static void syncForPlayer(Action action, ServerPlayer player, ChunkPos pos) {
            ServerLevel world = player.serverLevel();

            RegistryFriendlyByteBuf buf = AitNetworking.buf();
            buf.writeEnum(action);

            if (action != Action.CLEAR)
                buf.writeChunkPos(pos);

            if (action == Action.ADD) {
                LandingPadManager manager = LandingPadManager.getInstance(world);
                LandingPadRegion region = manager.getRegion(pos);

                if (region == null)
                    return;

                NetworkUtil.send(player, buf, SYNC, LandingPadRegion.CODEC, region);
                return;
            }

            NetworkUtil.send(player, SYNC, buf);
        }

        public static void syncTracked(Action action, ServerLevel world, ChunkPos pos) {
            RegistryFriendlyByteBuf buf = AitNetworking.buf();
            buf.writeEnum(action);

            if (action != Action.CLEAR)
                buf.writeChunkPos(pos);

            if (action == Action.ADD) {
                LandingPadManager manager = LandingPadManager.getInstance(world);
                LandingPadRegion region = manager.getRegion(pos);

                if (region == null)
                    return;

                Attachments.set(world.getChunk(pos.x, pos.z), PERSISTENT, region);

                for (ServerPlayer player : PlayerLookup.tracking(world, pos)) {
                    NetworkUtil.send(player, buf, SYNC, LandingPadRegion.CODEC, region);
                }

                return;
            }

            for (ServerPlayer player : PlayerLookup.tracking(world, pos)) {
                NetworkUtil.send(player, SYNC, buf);
            }
        }

        private static void init() {
            ServerConnectionEvents.JOIN.register((player, server) -> {
                syncForPlayer(Action.ADD, player);
            });

            ServerPlayerEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
                syncForPlayer(Action.CLEAR, player);
                syncForPlayer(Action.ADD, player);
            });

            AitNetworking.registerServerReceiver(LandingPadManager.Network.REQUEST, (server, player, handler, buf, responseSender) -> {
                ChunkPos pos = new ChunkPos(buf.readNbt().getLong("Chunk"));

                server.execute(() -> {
                    ServerLevel world = player.serverLevel();

                    if (world.hasChunk(pos.x, pos.z) && PlayerLookup.tracking(world, pos).contains(player))
                        syncForPlayer(Action.ADD, player, pos);
                });
            });
        }

        public enum Action {
            ADD,
            REMOVE,
            CLEAR
        }
    }
}
