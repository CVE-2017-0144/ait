package dev.amble.ait.core.tardis.util.network;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class BOTISnapshot {

    public static final int RADIUS_XZ = 12;
    public static final int BELOW = 3;
    public static final int ABOVE = 12;

    public static final int SIZE_XZ = RADIUS_XZ * 2 + 1;
    public static final int SIZE_Y = BELOW + ABOVE + 1;
    public static final int VOLUME = SIZE_XZ * SIZE_XZ * SIZE_Y;

    private final int[] states;

    private BOTISnapshot(int[] states) {
        this.states = states;
    }

    private static int index(int x, int y, int z) {
        return ((y + BELOW) * SIZE_XZ + (z + RADIUS_XZ)) * SIZE_XZ + (x + RADIUS_XZ);
    }

    public BlockState get(int x, int y, int z) {
        if (x < -RADIUS_XZ || x > RADIUS_XZ || z < -RADIUS_XZ || z > RADIUS_XZ || y < -BELOW || y > ABOVE)
            return Blocks.AIR.defaultBlockState();

        return Block.stateById(this.states[index(x, y, z)]);
    }

    public static BOTISnapshot capture(BlockGetter level, BlockPos origin) {
        int[] states = new int[VOLUME];
        BlockPos.MutableBlockPos cursor = new BlockPos.MutableBlockPos();

        for (int y = -BELOW; y <= ABOVE; y++) {
            for (int z = -RADIUS_XZ; z <= RADIUS_XZ; z++) {
                for (int x = -RADIUS_XZ; x <= RADIUS_XZ; x++) {
                    cursor.set(origin.getX() + x, origin.getY() + y, origin.getZ() + z);
                    states[index(x, y, z)] = Block.getId(level.getBlockState(cursor));
                }
            }
        }

        return new BOTISnapshot(states);
    }

    public void write(FriendlyByteBuf buf) {
        int run = 1;

        for (int i = 1; i <= this.states.length; i++) {
            if (i < this.states.length && this.states[i] == this.states[i - 1]) {
                run++;
                continue;
            }

            buf.writeVarInt(this.states[i - 1]);
            buf.writeVarInt(run);
            run = 1;
        }

        buf.writeVarInt(-1);
    }

    public static BOTISnapshot read(FriendlyByteBuf buf) {
        int[] states = new int[VOLUME];
        int at = 0;

        while (true) {
            int state = buf.readVarInt();

            if (state < 0)
                break;

            int run = buf.readVarInt();

            for (int i = 0; i < run && at < states.length; i++) {
                states[at++] = state;
            }
        }

        return new BOTISnapshot(states);
    }
}
