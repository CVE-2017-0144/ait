package dev.amble.ait.core;


import dev.amble.ait.AITMod;
import dev.amble.lib.container.impl.PaintingContainer;
import net.minecraft.world.entity.decoration.PaintingVariant;

public class AITPaintings implements PaintingContainer {
    public static final PaintingVariant CRAB_THROWER = new PaintingVariant(48, 32, AITMod.id("crab_thrower"));
    public static final PaintingVariant PEANUT = new PaintingVariant(16, 16, AITMod.id("peanut"));
}
