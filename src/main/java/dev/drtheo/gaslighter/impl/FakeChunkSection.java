package dev.drtheo.gaslighter.impl;

import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.LevelChunkSection;

public class FakeChunkSection extends LevelChunkSection {

    public FakeChunkSection(ServerLevel world) {
        super(world.registryAccess().registryOrThrow(Registries.BIOME));
    }
}
