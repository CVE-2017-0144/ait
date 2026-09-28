package dev.amble.ait.core.blockentities;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.ArtronHolder;
import dev.amble.ait.api.ArtronHolderItem;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.item.ArtronCollectorItem;
import dev.amble.ait.core.item.ChargedZeitonCrystalItem;
import dev.amble.ait.core.world.RiftChunkManager;
import dev.amble.ait.module.gun.core.item.StaserBoltMagazine;
import dev.amble.lib.animation.BedrockModelProvider;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ArtronCollectorBlockEntity extends FluidLinkBlockEntity implements BedrockModelProvider, BlockEntityTicker<ArtronCollectorBlockEntity>, ArtronHolder {

    public static final int FLOW_AMOUNT = 3;

    public double artronAmount = 0;

    public ArtronCollectorBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.ARTRON_COLLECTOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putDouble("artronAmount", this.artronAmount);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        if (nbt.contains("artronAmount"))
            this.setCurrentFuel(nbt.getDouble("artronAmount"));
        super.loadAdditional(nbt, registries);
    }

    public void useOn(Level world, boolean sneaking, Player player) {
        if (!world.isClientSide()) {
            player.sendSystemMessage(Component.literal(this.getCurrentFuel() + "/" + ArtronCollectorItem.COLLECTOR_MAX_FUEL)
                    .withStyle(ChatFormatting.GOLD));
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof ArtronCollectorItem) {
                double residual = ArtronCollectorItem.addFuel(stack, this.getCurrentFuel());
                this.setCurrentFuel(residual);
            } else if (stack.getItem() instanceof ArtronHolderItem artronHolderItem) {
                double residual = artronHolderItem.addFuel(this.getCurrentFuel(), stack);
                this.setCurrentFuel(residual);
            } else if (stack.getItem() instanceof ChargedZeitonCrystalItem crystal) {
                double residual = crystal.addFuel(this.getCurrentFuel(), stack);
                this.setCurrentFuel(residual);
            } else if (stack.getItem() instanceof StaserBoltMagazine magazine) {
                double residual = magazine.addFuel(this.getCurrentFuel(), stack);
                this.setCurrentFuel(residual);
            }
            if (stack.is(AITBlocks.ZEITON_CLUSTER.asItem())) {
                if (sneaking) {
                    player.getInventory().setItem(player.getInventory().selected,
                            new ItemStack(AITItems.CHARGED_ZEITON_CRYSTAL));
                    return;
                }

                this.addFuel(15);
                stack.shrink(1);
            }
        }
    }

    @Override
    public void setCurrentFuel(double artronAmount) {
        this.artronAmount = artronAmount;
        this.updateListeners(this.getBlockState());
    }

    @Override
    public double getMaxFuel() {
        return ArtronCollectorItem.COLLECTOR_MAX_FUEL;
    }

    @Override
    public double getCurrentFuel() {
        return this.artronAmount;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag nbtCompound = super.getUpdateTag(registries);
        nbtCompound.putDouble("artronAmount", this.artronAmount);
        return nbtCompound;
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state, ArtronCollectorBlockEntity blockEntity) {
        if (!(world instanceof ServerLevel serverWorld))
            return;

        if (serverWorld.getServer().getTickCount() % 3 == 0)
            return;

        ChunkPos chunk = new ChunkPos(pos);
        RiftChunkManager manager = RiftChunkManager.getInstance(serverWorld);

        if (shouldDrainChunk(manager, chunk)) {
            manager.removeFuel(chunk, FLOW_AMOUNT);
            this.addFuel(FLOW_AMOUNT);

            this.updateListeners(state);
        }

        if (shouldDrawFluid()) {
            this.removeFuel(FLOW_AMOUNT);
            blockEntity.source().addLevel(FLOW_AMOUNT);
            this.updateListeners(state);
        }
    }

    private boolean shouldDrainChunk(RiftChunkManager manager, ChunkPos pos) {
        return this.getCurrentFuel() < ArtronCollectorItem.COLLECTOR_MAX_FUEL
                && manager.getArtron(pos) >= FLOW_AMOUNT;
    }

    private boolean shouldDrawFluid() {
        return this.getCurrentFuel() >= FLOW_AMOUNT && source() != null && !source().isLevelFull();
    }

    private void updateListeners(BlockState state) {
        this.setChanged();

        if (!this.hasLevel())
            return;

        this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), state, Block.UPDATE_ALL);
    }

    @Override
    public String getTexturePrefix() {
        return "blockentities/machines";
    }

    @Override
    public @Nullable BedrockModelReference getModel() {
        return new BedrockModelReference(AITMod.MOD_ID, "artron_collector");
    }
}
