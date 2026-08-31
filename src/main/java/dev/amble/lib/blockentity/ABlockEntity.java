package dev.amble.lib.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class ABlockEntity extends BlockEntity {

    public ABlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void onPlaced(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) { }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        return InteractionResult.PASS;
    }

    public void onBreak(BlockState state, Level world, BlockPos pos, BlockState newState) { }

    protected void sync() {
        if (this.level != null && this.level.getChunkSource() instanceof ServerChunkCache chunkManager)
            chunkManager.blockChanged(this.worldPosition);
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    public static <E extends BlockEntity> void tick(Level world, BlockPos blockPos, BlockState blockState, E e) {
        ((ABlockEntity) e).tick(world, blockPos, blockState);
    }

    public void tick(Level world, BlockPos pos, BlockState state) { }
}
