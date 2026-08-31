package dev.amble.ait.core.blockentities;

import java.util.ArrayDeque;
import java.util.Deque;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.util.StackUtil;
import dev.amble.ait.registry.impl.MachineRecipeRegistry;

public class MachineCasingBlockEntity extends BlockEntity {

    private final Deque<ItemStack> parts = new ArrayDeque<>();

    public MachineCasingBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.MACHINE_CASING_ENTITY_TYPE, pos, state);
    }

    public void onUse(Level world, ItemStack stack, Player player) {
        if (stack.isEmpty() && player.isShiftKeyDown()) {
            if (this.parts.isEmpty())
                return;

            StackUtil.spawn(world, this.worldPosition, parts.pop());
            return;
        }

        if (!(stack.getItem() instanceof SonicItem)) {
            this.parts.push(stack.copyWithCount(1));
            stack.shrink(1);
        }
    }

    public void construct() {
        MachineRecipeRegistry.getInstance().findMatching(this.parts).ifPresent(schema -> {
            StackUtil.spawn(level, this.worldPosition, schema.output());

            level.removeBlock(this.worldPosition, false);
            this.setRemoved();
        });
    }

    public void onBreak(Level world) {
        this.parts.add(new ItemStack(AITBlocks.MACHINE_CASING.asItem())); // don't care about the parts now anyway
        StackUtil.scatter(world, worldPosition.above(1), this.parts);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        StackUtil.writeUnordered(nbt, this.parts);
    }

    @Override
    protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        StackUtil.readUnordered(nbt, this.parts);
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata();
    }
}
