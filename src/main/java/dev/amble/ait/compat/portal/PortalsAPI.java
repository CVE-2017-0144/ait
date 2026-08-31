package dev.amble.ait.compat.portal;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class PortalsAPI {

    public static Optional<VisualizerImpl> VISUALIZER = Optional.empty();

    @FunctionalInterface
    public interface VisualizerImpl {
        void open(ServerPlayer player, ServerLevel world, BlockPos pos);
    }
}
