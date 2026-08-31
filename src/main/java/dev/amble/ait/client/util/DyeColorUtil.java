package dev.amble.ait.client.util;

import net.minecraft.util.FastColor;
import net.minecraft.world.item.DyeColor;

public final class DyeColorUtil {

    private DyeColorUtil() {}

    public static float[] rgb(DyeColor color) {
        int packed = color.getTextureDiffuseColor();
        return new float[] {FastColor.ARGB32.red(packed) / 255.0f, FastColor.ARGB32.green(packed) / 255.0f,
                FastColor.ARGB32.blue(packed) / 255.0f};
    }
}
