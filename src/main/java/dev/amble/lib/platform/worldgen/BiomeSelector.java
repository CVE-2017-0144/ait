package dev.amble.lib.platform.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;

@FunctionalInterface
public interface BiomeSelector {

    boolean test(Holder<Biome> biome);
}
