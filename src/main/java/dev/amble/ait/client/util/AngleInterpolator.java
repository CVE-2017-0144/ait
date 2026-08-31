package dev.amble.ait.client.util;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class AngleInterpolator {
    private double value;
    private double speed;
    private long lastUpdateTime;

    public AngleInterpolator() {
    }

    public double value() {
        return value;
    }

    public boolean shouldUpdate(long time) {
        return this.lastUpdateTime != time;
    }

    public void update(long time, double target) {
        this.lastUpdateTime = time;
        double d = target - this.value;
        d = Mth.positiveModulo(d + 0.5, 1.0) - 0.5;
        this.speed += d * 0.1;
        this.speed *= 0.8;
        this.value = Mth.positiveModulo(this.value + this.speed, 1.0);
    }
}