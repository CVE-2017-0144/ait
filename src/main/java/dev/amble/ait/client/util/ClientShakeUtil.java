package dev.amble.ait.client.util;


import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class ClientShakeUtil {
    @Deprecated
    private static final float SHAKE_CLAMP = 45.0f;
    @Deprecated
    private static final float SHAKE_INTENSITY = 0.5f;
    @Deprecated
    private static final int MAX_DISTANCE = 16;

    public static float getShakeAmount(Tardis tardis) {
        TravelHandler travel = tardis.travel();
        float low = 0.15f;
        float medium = 0.225f;
        float high = 0.3f;

        float speed = (float) Mth.clamp(0.1f * travel.speed(), 0.1, 0.6f);
        low += speed;
        medium += speed;
        high += speed;

        if (ClientTardisUtil.getCurrentTardis() != tardis)
            return 0;

        if (travel.getState() == TravelHandlerBase.State.MAT)
            return medium;

        if (travel.getState() == TravelHandlerBase.State.DEMAT)
            return medium;

        if (!tardis.crash().isNormal() && !travel.isLanded())
            return medium;

        if (!travel.inFlight())
            return 0;

        if (tardis.sequence().hasClientActiveSequence())
            return high;

        if (tardis.flight().falling().get())
            return high;

        if (!travel.autopilot())
            return low;

        return 0;
    }

    /**
     * Shakes based off the distance of the player from the console
     */
    @Deprecated
    public static void shakeFromConsole() {
        shake(1f - (float) (ClientTardisUtil.distanceFromConsole() / MAX_DISTANCE));
    }

    public static void shakeFromEverywhere() {
        shake(0.25f);
    }

    public static void shake(float scale) {
        if (scale == 0) return;
        Minecraft client = Minecraft.getInstance();
        if (client.player == null)
            return;

        float targetPitch = getShakeX(client.player.getXRot(), scale);
        float targetYaw = getShakeY(client.player.getYRot(), scale);

        client.player.setXRot(Mth.lerp(SHAKE_INTENSITY, client.player.getXRot(), targetPitch));
        client.player.setYRot(Mth.lerp(SHAKE_INTENSITY, client.player.getYRot(), targetYaw));
    }

    private static float getShakeY(float baseYaw, float scale) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null)
            return baseYaw;

        float temp = (client.player.getRandom().nextFloat() * scale);
        float shakeYaw = baseYaw + (client.player.getRandom().nextBoolean() ? temp : -temp);

        return Mth.clamp(shakeYaw, baseYaw - SHAKE_CLAMP, baseYaw + SHAKE_CLAMP);
    }

    private static float getShakeX(float basePitch, float scale) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null)
            return basePitch;

        float temp = (client.player.getRandom().nextFloat() * (scale / 2));
        float shakePitch = basePitch + (client.player.getRandom().nextBoolean() ? temp : -temp);

        return Mth.clamp(shakePitch, basePitch - SHAKE_CLAMP, basePitch + SHAKE_CLAMP);
    }
}
