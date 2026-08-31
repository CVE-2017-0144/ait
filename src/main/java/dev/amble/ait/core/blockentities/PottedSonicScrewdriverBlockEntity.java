package dev.amble.ait.core.blockentities;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.blocks.PottedSonicScrewdriverBlock;

public class PottedSonicScrewdriverBlockEntity extends BlockEntity {

    private final List<ItemStack> sonics = new ArrayList<>();

    public PottedSonicScrewdriverBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.POTTED_SONIC_SCREWDRIVER_BLOCK_ENTITY_TYPE, pos, state);
    }

    public List<ItemStack> getSonics() {
        return this.sonics;
    }

    public int count() {
        return this.sonics.size();
    }

    public boolean isFull() {
        return this.sonics.size() >= PottedSonicScrewdriverBlock.MAX_SONICS;
    }

    public void addSonic(ItemStack stack) {
        if (this.isFull())
            return;

        this.sonics.add(stack.copyWithCount(1));
        this.sync();
    }

    public ItemStack removeLast() {
        if (this.sonics.isEmpty())
            return ItemStack.EMPTY;

        ItemStack removed = this.sonics.remove(this.sonics.size() - 1);
        this.sync();
        return removed;
    }

    private void sync() {
        this.setChanged();
        if (this.level != null && !this.level.isClientSide)
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), net.minecraft.world.level.block.Block.UPDATE_ALL);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        ListTag list = new ListTag();
        for (ItemStack stack : this.sonics)
            list.add(stack.save(new CompoundTag()));

        nbt.put("Sonics", list);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        this.sonics.clear();
        ListTag list = nbt.getList("Sonics", Tag.TAG_COMPOUND);
        for (int i = 0; i < list.size() && this.sonics.size() < PottedSonicScrewdriverBlock.MAX_SONICS; i++) {
            ItemStack stack = ItemStack.of(list.getCompound(i));
            if (!stack.isEmpty())
                this.sonics.add(stack.copyWithCount(1));
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveWithoutMetadata();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
