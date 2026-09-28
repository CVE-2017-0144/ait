package dev.loqor.portal.client;

import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;

public class ClientWorldAnalog extends ClientLevel {
    public ClientWorldAnalog(ClientPacketListener networkHandler, ClientLevelData properties, ResourceKey<Level> registryRef,
                             Holder<DimensionType> dimensionTypeEntry, int loadDistance, int simulationDistance, Supplier<ProfilerFiller> profiler,
                             LevelRenderer worldRenderer, boolean debugWorld, long seed) {
        super(networkHandler, properties, registryRef, dimensionTypeEntry, loadDistance, simulationDistance, profiler, worldRenderer, debugWorld, seed);
    }
}
