package dev.amble.ait.core.tardis.control.impl.pos;

import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;

public enum PosType implements StringRepresentable {
    X() {
        @Override
        public BlockPos add(BlockPos pos, int amount) {
            return pos.offset(amount, 0, 0);
        }
    },
    Y() {
        @Override
        public BlockPos add(BlockPos pos, int amount, Level world) {
            return PosType.clamp(pos, amount, world);
        }

        @Override
        public BlockPos add(BlockPos pos, int amount) {
            return pos.offset(0, amount, 0);
            // @TODO in the nether, search below 128 and above 0.
        }
    },
    Z() {
        @Override
        public BlockPos add(BlockPos pos, int amount) {
            return pos.offset(0, 0, amount);
        }
    };

    // Clamps the Y coordinate of a position depending on the world.
    public static BlockPos clamp(BlockPos pos, int amount, Level world) {
        if (world.dimension() == Level.NETHER) {
            return pos.atY(Mth.clamp(pos.getY() + amount, world.getMinBuildHeight(), 120));
        }

        return pos.atY(Mth.clamp(pos.getY() + amount, world.getMinBuildHeight(), world.getMaxBuildHeight() - 1));
    }

    // adds an amount to a blockpos based on this type
    public abstract BlockPos add(BlockPos pos, int amount);

    public BlockPos add(BlockPos pos, int amount, Level world) {
        return add(pos, amount);
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase();
    }
}
