package dev.amble.ait.core.util;

import java.util.*;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.LevelResource;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.mixin.server.EnderDragonFightAccessor;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.util.ServerLifecycleHooks;

public class WorldUtil {

    private static final List<ServerLevel> PROJECTOR_WORLDS = new ArrayList<>();
    private static final List<ServerLevel> TRAVEL_WORLDS = new ArrayList<>();

    private static ServerLevel OVERWORLD;
    private static ServerLevel TIME_VORTEX;

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTED.register(WorldUtil::generateWorldCache);
        ServerLifecycleEvents.SERVER_STOPPING.register(WorldUtil::clearWorldCache);

        ServerWorldEvents.UNLOAD.register((server, world) -> {
            if (world.dimension() == Level.OVERWORLD)
                OVERWORLD = null;

            if (world.dimension() == AITDimensions.TIME_VORTEX_WORLD)
                TIME_VORTEX = null;
        });

        ServerWorldEvents.LOAD.register((server, world) -> {
            if (world.dimension() == Level.OVERWORLD)
                OVERWORLD = world;

            if (world.dimension() == AITDimensions.TIME_VORTEX_WORLD)
                TIME_VORTEX = world;
        });

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            OVERWORLD = server.overworld();
            TIME_VORTEX = server.getLevel(AITDimensions.TIME_VORTEX_WORLD);
        });
    }

    public static ServerLevel getOverworld() {
        return OVERWORLD;
    }

    public static ServerLevel getTimeVortex() {
        return TIME_VORTEX;
    }

    private static void generateWorldCache(MinecraftServer server) {
        generateWorldCache(server, "environment projector", AITMod.CONFIG.projectorBlacklist, AITMod.CONFIG.projectorWhitelist, PROJECTOR_WORLDS, false);
        generateWorldCache(server, "travel", AITMod.CONFIG.travelBlacklist, AITMod.CONFIG.travelWhitelist, TRAVEL_WORLDS, true);
    }

    private static void generateWorldCache(MinecraftServer server, String cacheName, List<String> blacklist, List<String> whitelist,
                                           Collection<ServerLevel> worlds, boolean allowTardisWorlds) {
        worlds.clear();

        Set<ResourceLocation> whitelistIds = new HashSet<>();
        boolean whitelistHasTardisFlag = collectWorldIds(whitelist, whitelistIds);
        boolean useWhitelist = whitelistHasTardisFlag || !whitelistIds.isEmpty();

        Set<ResourceLocation> blacklistIds = new HashSet<>();
        boolean blacklistHasTardisFlag = collectWorldIds(blacklist, blacklistIds);

        if (useWhitelist && (blacklistHasTardisFlag || !blacklistIds.isEmpty()))
            AITMod.LOGGER.warn("Both {} blacklist and whitelist are populated - whitelist takes priority. Clear one to suppress this warning.", cacheName);

        Set<ResourceLocation> activeIds = useWhitelist ? whitelistIds : blacklistIds;
        boolean hasTardisFlag = useWhitelist ? whitelistHasTardisFlag : blacklistHasTardisFlag;

        for (ServerLevel world : server.getAllLevels()) {
            boolean isTardis = TardisServerWorld.isTardisDimension(world);

            if (!allowTardisWorlds && isTardis)
                continue;

            ResourceLocation worldId = world.dimension().location();
            boolean matches = idsMatch(activeIds, hasTardisFlag, worldId, isTardis);

            if (useWhitelist) {
                if (matches)
                    worlds.add(world);
            } else {
                if (!matches)
                    worlds.add(world);
            }
        }

        if (useWhitelist && worlds.isEmpty())
            AITMod.LOGGER.warn("The {} whitelist is configured but does not resolve to any available worlds.", cacheName);
    }

    private static boolean collectWorldIds(List<String> rawIds, Set<ResourceLocation> ids) {
        boolean hasTardisFlag = false;

        for (String rawId : rawIds) {
            if (rawId == null)
                continue;

            String cleaned = rawId.trim();

            if (cleaned.isEmpty())
                continue;

            if (cleaned.equals("ait-tardis")) {
                hasTardisFlag = true;
                continue;
            }

            ResourceLocation id = ResourceLocation.tryParse(cleaned);

            if (id == null)
                continue;

            ids.add(id);
        }

        return hasTardisFlag;
    }

    private static boolean idsMatch(Set<ResourceLocation> ids, boolean hasTardisFlag, ResourceLocation worldId, boolean isTardis) {
        return (hasTardisFlag && isTardis) || ids.contains(worldId);
    }

    private static void clearWorldCache(MinecraftServer server) {
        PROJECTOR_WORLDS.clear();
        TRAVEL_WORLDS.clear();
    }

    /**
     * @implNote This method uses a reference check (by `==`), instead of
     * {@link Object#equals(Object)}, as its {@link List} counterpart does.
     */
    public static int travelWorldIndex(ServerLevel world) {
        for (int i = 0; i < TRAVEL_WORLDS.size(); i++) {
            if (world == TRAVEL_WORLDS.get(i))
                return i;
        }

        return -1;
    }

    public static List<ServerLevel> getProjectorWorlds() {
        return PROJECTOR_WORLDS;
    }

    public static List<ServerLevel> getTravelWorlds() {
        return TRAVEL_WORLDS;
    }

    @Environment(EnvType.CLIENT)
    @SuppressWarnings("DataFlowIssue")
    public static String getName(Minecraft client) {
        if (client.isLocalServer())
            return client.getSingleplayerServer().getWorldPath(LevelResource.ROOT).getParent().getFileName().toString();

        return client.getCurrentServer().ip;
    }

    public static Component worldText(ResourceKey<Level> key) {
        Component translated = Component.translatableWithFallback(key.location().toLanguageKey("dimension"), fakeTranslate(key));

        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            return hackWorldText(translated);

        return translated;
    }

    @Environment(EnvType.CLIENT)
    private static Component hackWorldText(Component existing) {
        if (ClientTardisUtil.getCurrentTardis() != null &&
                !ClientTardisUtil.getCurrentTardis().flight().isFlying() && ClientTardisUtil.getCurrentTardis().travel().inFlight()) {
            ResourceKey<Level> timeVortex = AITDimensions.TIME_VORTEX_WORLD;
            return
                    Component.translatableWithFallback(
                            timeVortex.location().toLanguageKey("dimension"),
                            fakeTranslate(timeVortex)).append(" [").append(existing).append( "]");
        }

        return existing;
    }

    public static Component worldText(ResourceKey<Level> key, boolean justToSeperate) {
        return Component.translatableWithFallback(key.location().toLanguageKey("dimension"), fakeTranslate(key));
    }

    private static String fakeTranslate(ResourceKey<Level> id) {
        return fakeTranslate(id.location());
    }

    private static String fakeTranslate(ResourceLocation id) {
        return fakeTranslate(id.getPath());
    }

    public static String fakeTranslate(String path) {
        // Split the string into words
        String[] words = path.split("_");

        // Capitalize the first letter of each word
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].substring(0, 1).toUpperCase() + words[i].substring(1).toLowerCase();
        }

        // Join the words back together with spaces
        return String.join(" ", words);
    }

    public static Component rot2Text(int rotation) {
        String key = switch (rotation) {
            case 0 -> "direction.north";
            case 1, 2, 3 -> "direction.north_east";
            case 4 -> "direction.east";
            case 5, 6, 7 -> "direction.south_east";
            case 8 -> "direction.south";
            case 9, 10, 11 -> "direction.south_west";
            case 12 -> "direction.west";
            case 13, 14, 15 -> "direction.north_west";
            default -> null;
        };

        return Component.translatable(key);
    }

    public static byte tardis2Rot(ServerTardis tardis) {
        if (tardis == null)
            return 0;
        CachedDirectedGlobalPos position = tardis.travel().position();
        return position == null ? 0 : position.getRotation();
    }

    public static byte stringName2Rot(String name) {
        String s = name == null ? "" : name.toLowerCase();
        return switch (s) {
            case "north_east" -> 2;     // group 1..3
            case "east" -> 4;           // group 4
            case "south_east" -> 6;     // group 5..7
            case "south" -> 8;          // group 8
            case "south_west" -> 10;    // group 9..11
            case "west" -> 12;          // group 12
            case "north_west" -> 14;    // group 13..15
            default -> 0;               // group 0 (north)
        };
    }

    public static String rot2StringName(int rotation) {
        return switch (rotation) {
            case 1, 2, 3 -> "north_east";
            case 4 -> "east";
            case 5, 6, 7 -> "south_east";
            case 8 -> "south";
            case 9, 10, 11 -> "south_west";
            case 12 -> "west";
            case 13, 14, 15 -> "north_west";
            default -> "north";
        };
    }

    public static void onBreakHalfInCreative(Level world, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF);

        if (doubleBlockHalf != DoubleBlockHalf.UPPER)
            return;

        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);

        if (blockState.is(state.getBlock())
                && blockState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER) {
            BlockState withFluid = blockState.getFluidState().is(Fluids.WATER)
                    ? Blocks.WATER.defaultBlockState()
                    : Blocks.AIR.defaultBlockState();

            world.setBlock(blockPos, withFluid, 35);
            world.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));
        }
    }

    public static boolean isEndDragonDead() {
        ServerLevel end = ServerLifecycleHooks.get().getLevel(Level.END);
        if (end == null) return true;
        return ((EnderDragonFightAccessor) end.getDragonFight()).getDragonKilled();
    }

    public static void teleportToWorld(ServerPlayer player, ServerLevel target, Vec3 pos, float yaw, float pitch) {
        player.teleportTo(target, pos.x, pos.y, pos.z, yaw, pitch);
        player.giveExperiencePoints(0);

        player.getActiveEffects().forEach(effect -> player.connection.send(
                new ClientboundUpdateMobEffectPacket(player.getId(), effect)));
    }
}
