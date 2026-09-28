package dev.amble.ait.client.util;

import net.minecraft.util.FastColor;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;

public class DyeColorUtil {

    public static float[] rgb(DyeColor color) {
        int packed = Sheep.getColor(color);
        return new float[] {FastColor.ARGB32.red(packed) / 255.0f, FastColor.ARGB32.green(packed) / 255.0f,
                FastColor.ARGB32.blue(packed) / 255.0f};
    }
}
