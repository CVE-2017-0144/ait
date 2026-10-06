package dev.amble.ait.compat.portal;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.BooleanSupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public class PortalsAPI {

    public static Optional<VisualizerImpl> VISUALIZER = Optional.empty();
    public static BooleanSupplier RENDERING_PORTAL = () -> false;
    public static BiPredicate<Level, double[]> CLIP_PLANE = (level, plane) -> false;

    @FunctionalInterface
    public interface VisualizerImpl {
        void open(ServerPlayer player, ServerLevel world, BlockPos pos);
    }
}
