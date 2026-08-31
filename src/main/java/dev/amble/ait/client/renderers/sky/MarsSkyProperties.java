package dev.amble.ait.client.renderers.sky;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class MarsSkyProperties extends DimensionSpecialEffects {
    public static final float[] SUNSET_COLORS = {0,0  , 1, 1};

    public MarsSkyProperties() {
        super(OverworldEffects.CLOUD_LEVEL, true, SkyType.NORMAL, false, false);
//            this.
    }

    public MarsSkyProperties(float cloudLevel, boolean hasGround, SkyType skyType, boolean forceBrightLightmap,
                             boolean constantAmbientLight) {
        super(cloudLevel, hasGround, skyType, forceBrightLightmap, constantAmbientLight);
    }

    //adjustSkyColor
    @Override
    public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
        return color.multiply(sunHeight * 0.91f + 0.09f, sunHeight * 0.94f + 0.06f, sunHeight * 0.94f + 0.06f);
    }

    //isFoggyAt
    @Override
    public boolean isFoggyAt(int camX, int camY) {
        return false;
    }

    @Override
    public float @Nullable [] getSunriseColor(float p_230492_1, float p230492_2) {
        float g = Mth.cos(p_230492_1 * ((float)Math.PI * 2)) - 0.0f;
        if (g >= -0.5f && g <= 0.5f) {
            float i = (g - -0.0f) / 0.7f * 0.5f + 0.5f;
            float j = 1.0f - (1.0f - Mth.sin(i * (float)Math.PI)) * 0.99f;
            j *= j;
            SUNSET_COLORS[2] = i * 0.1f + 0.7f;
            SUNSET_COLORS[1] = i * i * 0.7f + 0.2f;
            SUNSET_COLORS[0] = i * i * 0.3f + 0.2f;
            SUNSET_COLORS[3] = j;
            return SUNSET_COLORS;
        }
        return null;
    }
}
