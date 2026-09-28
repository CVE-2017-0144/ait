package dev.amble.ait.client.util;

import static dev.amble.ait.core.tardis.util.TardisUtil.*;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import dev.amble.ait.api.ClientWorldEvents;
import dev.amble.ait.api.tardis.TardisClientEvents;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.tardis.manager.ClientTardisManager;
import dev.amble.ait.client.util.DyeColorUtil;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisExterior;
import dev.amble.ait.core.tardis.handler.SonicHandler;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.Level;

@OnlyIn(Dist.CLIENT)
public class ClientTardisUtil {

    public static final int MAX_POWER_DELTA_TICKS = 3 * 20;
    public static final int MAX_ALARM_DELTA_TICKS = 60;

    private static int alarmDeltaTick;
    private static boolean alarmDeltaDirection; // true for increasing false for decreasing
    private static int powerDeltaTick;

    private static TardisRef currentTardis;


    public static void init() {
        ClientWorldEvents.CHANGE_WORLD.register((client, world) -> {
            UUID id = TardisServerWorld.getTardisId(world);
            currentTardis = new TardisRef(id, uuid -> ClientTardisManager.getInstance().demandTardis(uuid));
            if (id == null) {
                TardisClientEvents.ENTER_CLIENT_TARDIS.invoker().enterClientTardis(null);
                return;
            }
            if (currentTardis.isEmpty()) {
                ClientTardisManager.getInstance().subscribers.put(id, clientTardis -> {
                    TardisClientEvents.ENTER_CLIENT_TARDIS.invoker().enterClientTardis(clientTardis);
                });
            } else {
                TardisClientEvents.ENTER_CLIENT_TARDIS.invoker().enterClientTardis(currentTardis.get().asClient());
            }
        });
        ClientEvents.DISCONNECT.register((client) -> {
            currentTardis = null;
        });
    }

    public static void changeExteriorWithScreen(UUID uuid, ResourceLocation variant, boolean variantchange) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(uuid);
        buf.writeBoolean(variantchange);
        buf.writeResourceLocation(variant);
        AitNetworking.send(TardisExterior.CHANGE_EXTERIOR, buf);
    }

    public static void changeExteriorWithScreen(ClientTardis tardis, ResourceLocation variant, boolean variantchange) {
        changeExteriorWithScreen(tardis.getUuid(), variant, variantchange);
    }

    public static void changeSonicWithScreen(UUID uuid, SonicSchema schema, BlockPos consolePos) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(uuid);
        buf.writeResourceLocation(schema.id());
        buf.writeBlockPos(consolePos);
        AitNetworking.send(SonicHandler.CHANGE_SONIC, buf);
    }

    public static void snapToOpenDoors(Tardis tardis) {
        snapToOpenDoors(tardis.getUuid());
    }

    public static void snapToOpenDoors(UUID uuid) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(uuid);

        AitNetworking.send(SNAP, buf);
    }

    public static void flyingSpeedPacket(Tardis tardis, String direction) {
        flyingSpeedPacket(tardis.getUuid(), direction);
    }

    public static void flyingSpeedPacket(UUID uuid, String direction) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(uuid);
        buf.writeUtf(direction);

        AitNetworking.send(FLYING_SPEED, buf);
    }

    public static void toggleAntigravs(Tardis tardis) {
        toggleAntigravs(tardis.getUuid());
    }

    public static void toggleAntigravs(UUID uuid) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(uuid);

        AitNetworking.send(TOGGLE_ANTIGRAVS, buf);
    }

    public static boolean isPlayerInATardis() {
        return currentTardis != null;
    }

    /**
     * Gets the tardis the player is currently inside
     */
    public static ClientTardis getCurrentTardis() {
        if (currentTardis == null)
            return null;

        return (ClientTardis) currentTardis.get();
    }

    public static Optional<ClientTardis> getNearestTardis(double radius) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null)
            return Optional.empty();

        BlockPos pos = player.blockPosition();
        ResourceKey<Level> dimension = player.level().dimension();

        // doesnt find nearest, only finds if within radius.
        // could be more performant though
        //
        // ya dont say
        //  - Theo
        /*
        return ClientTardisManager.getInstance().find(tardis -> {
            if (!tardis.travel().position().getDimension().equals(dimension))
                return false;

            BlockPos tPos = tardis.travel().position().getPos();
            double distance = Math.sqrt(pos.getSquaredDistance(tPos));

            return distance < radius;
        });
        */

        double radiusSquared = Math.pow(radius, 2);

        final ClientTardis[] nearestTardis = new ClientTardis[1];
        final double[] nearestDistanceSquared = {Double.MAX_VALUE};

        ClientTardisManager.getInstance().forEach(tardis -> {
            if (!tardis.travel().position().getDimension().equals(dimension))
                return;

            BlockPos tPos = tardis.travel().position().getPos();
            double distanceSquared = pos.distSqr(tPos);

            if (radiusSquared > distanceSquared && distanceSquared < nearestDistanceSquared[0]) {
                nearestDistanceSquared[0] = distanceSquared;
                nearestTardis[0] = tardis;
            }
        });

        return Optional.ofNullable(nearestTardis[0]);
    }

    public static double distanceFromConsole() {
        if (!isPlayerInATardis())
            return 0;

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null)
            return 0;

        Tardis tardis = getCurrentTardis();

        if (tardis == null)
            return 0;

        Collection<BlockPos> consoles = tardis.getDesktop().getConsolePos();

        if (consoles.isEmpty())
            return 0;

        BlockPos pos = player.blockPosition();
        double lowest = Double.MAX_VALUE;

        for (BlockPos console : consoles) {
            double distance = Math.sqrt(pos.distSqr(console));

            if (distance < lowest)
                lowest = distance;
        }

        return lowest;
    }

    public static BlockPos getNearestConsole() {
        if (!isPlayerInATardis())
            return BlockPos.ZERO;

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null)
            return BlockPos.ZERO;

        Tardis tardis = getCurrentTardis();

        if (tardis == null)
            return BlockPos.ZERO;

        BlockPos pos = player.blockPosition();
        double lowest = Double.MAX_VALUE;
        BlockPos nearest = BlockPos.ZERO;

        for (BlockPos console : tardis.getDesktop().getConsolePos()) {
            double distance = Math.sqrt(pos.distSqr(console));

            if (distance < lowest) {
                lowest = distance;
                nearest = console;
            }
        }

        return nearest;
    }

    public static BlockPos getNearestEngine() {
        if (!isPlayerInATardis())
            return null;

        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null)
            return null;

        Tardis tardis = getCurrentTardis();

        if (tardis == null)
            return null;

        BlockPos pos = player.blockPosition();
        double lowest = Double.MAX_VALUE;
        BlockPos nearest = BlockPos.ZERO;

        BlockPos engine = tardis.getDesktop().getEnginePos();
        if (engine != null) {
            double distance = Math.sqrt(pos.distSqr(engine));

            if (distance < lowest) {
                lowest = distance;
                nearest = engine;
            }
        }

        return nearest;
    }

    public static void tickPowerDelta() {
        Tardis tardis = getCurrentTardis();

        if (tardis == null) {
            powerDeltaTick = MAX_POWER_DELTA_TICKS;
            return;
        }

        if (tardis.fuel().hasPower() && getPowerDelta() < MAX_POWER_DELTA_TICKS) {
            powerDeltaTick++;
        } else if (!tardis.fuel().hasPower() && getPowerDelta() > 0) {
            powerDeltaTick--;
        }
    }

    public static int getPowerDelta() {
        return currentTardis != null ? powerDeltaTick : 0;
    }

    public static float getPowerDeltaForLerp() {
        return (float) getPowerDelta() / MAX_POWER_DELTA_TICKS;
    }

    public static void tickAlarmDelta() {
        Tardis tardis = getCurrentTardis();

        if (tardis == null || !tardis.alarm().isEnabled()) {
            alarmDeltaTick = MAX_ALARM_DELTA_TICKS;
            return;
        }

        if (alarmDeltaTick < MAX_ALARM_DELTA_TICKS && alarmDeltaDirection) {
            alarmDeltaTick++;
        } else if (alarmDeltaTick > 0 && !alarmDeltaDirection) {
            alarmDeltaTick--;
        }

        if (alarmDeltaTick >= MAX_ALARM_DELTA_TICKS)
            alarmDeltaDirection = false;

        if (alarmDeltaTick == 0)
            alarmDeltaDirection = true;
    }

    public static int getAlarmDelta() {
        if (!isPlayerInATardis())
            return 0;

        return alarmDeltaTick;
    }

    public static float getAlarmDeltaForLerp() {
        return (float) getAlarmDelta() / MAX_ALARM_DELTA_TICKS;
    }

    public static float[] getPartyColors() {
        final int m = 25;
        final Player player = Minecraft.getInstance().player;

        int n = player.tickCount / m + player.getId();
        int o = DyeColor.values().length;
        int p = n % o;
        int q = (n + 1) % o;
        float r = ((float)(player.tickCount % m)) / m;
        float[] fs = DyeColorUtil.rgb(DyeColor.byId(p));
        float[] gs = DyeColorUtil.rgb(DyeColor.byId(q));

        float s = fs[0] * (1f - r) + gs[0] * r;
        float t = fs[1] * (1f - r) + gs[1] * r;
        float u = fs[2] * (1f - r) + gs[2] * r;

        return new float[] { s, t, u };
    }
}
