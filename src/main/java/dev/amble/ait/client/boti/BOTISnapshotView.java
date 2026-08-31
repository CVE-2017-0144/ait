package dev.amble.ait.client.boti;

import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.api.distmarker.Dist;
import org.jetbrains.annotations.Nullable;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.material.FluidState;

import dev.amble.ait.core.tardis.util.network.BOTISnapshot;

@OnlyIn(Dist.CLIENT)
public record BOTISnapshotView(BOTISnapshot snapshot) implements BlockAndTintGetter {

    private static final int GRASS = 0x79C05A;
    private static final int FOLIAGE = 0x59AE30;
    private static final int WATER = 0x3F76E4;

    @Override
    public BlockState getBlockState(BlockPos pos) {
        return this.snapshot.get(pos.getX(), pos.getY(), pos.getZ());
    }

    @Override
    public FluidState getFluidState(BlockPos pos) {
        return this.getBlockState(pos).getFluidState();
    }

    @Nullable
    @Override
    public BlockEntity getBlockEntity(BlockPos pos) {
        return null;
    }

    @Override
    public int getHeight() {
        return BOTISnapshot.SIZE_Y;
    }

    @Override
    public int getMinBuildHeight() {
        return -BOTISnapshot.BELOW;
    }

    @Override
    public float getShade(Direction direction, boolean shade) {
        if (!shade)
            return 1.0f;

        return switch (direction) {
            case DOWN -> 0.5f;
            case UP -> 1.0f;
            case NORTH, SOUTH -> 0.8f;
            case WEST, EAST -> 0.6f;
        };
    }

    @Override
    public LevelLightEngine getLightEngine() {
        return null;
    }

    @Override
    public int getBlockTint(BlockPos pos, ColorResolver resolver) {
        if (resolver == BiomeColors.GRASS_COLOR_RESOLVER)
            return GRASS;

        if (resolver == BiomeColors.FOLIAGE_COLOR_RESOLVER)
            return FOLIAGE;

        if (resolver == BiomeColors.WATER_COLOR_RESOLVER)
            return WATER;

        return -1;
    }

    @Override
    public int getBrightness(LightLayer layer, BlockPos pos) {
        return 15;
    }

    @Override
    public int getRawBrightness(BlockPos pos, int ambientDarkness) {
        return 15;
    }
}
