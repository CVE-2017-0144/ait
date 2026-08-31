package dev.amble.ait.core.blockentities;


import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.item.blueprint.Blueprint;
import dev.amble.ait.core.item.blueprint.BlueprintItem;
import dev.amble.ait.core.item.blueprint.BlueprintSchema;
import dev.amble.ait.core.util.StackUtil;

public class FabricatorBlockEntity extends InteriorLinkableBlockEntity {
    private Blueprint blueprint;

    public FabricatorBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.FABRICATOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    public void useOn(BlockState state, Level world, boolean sneaking, Player player) {
        if (world.isClientSide())
            return;

        if (!this.isValid())
            return;

        ItemStack hand = player.getMainHandItem();

        // accept new blueprint
        if (!this.hasBlueprint() && hand.getItem() instanceof BlueprintItem) {
            BlueprintSchema schema = BlueprintItem.getSchema(hand);

            if (schema == null)
                return;

            this.setBlueprint(schema.create());
            world.playSound(null, this.getBlockPos(), AITSounds.FABRICATOR_START, SoundSource.BLOCKS, 1, 1);
            return;
        }

        // try to insert items into the fabricator
        if (this.hasBlueprint()) {
            Blueprint blueprint = this.getBlueprint().get();

            if (hand.isEmpty() && sneaking) {
                List<ItemStack> inputs = blueprint.getInsertedItems();
                for (ItemStack stack : inputs) {
                    player.getInventory().placeItemBackInInventory(stack);
                }

                this.setBlueprint(null, true);
                this.sync();
                this.setChanged();

                return;
            }

            if (blueprint.tryAdd(hand)) {
                this.syncChanges();

                if (blueprint.isComplete())
                    world.playSound(null, this.getBlockPos(), AITSounds.FABRICATOR_END, SoundSource.BLOCKS, 1, 1);

                return;
            }

            // try to craft the blueprint
            Optional<ItemStack> output = blueprint.tryCraft();
            if (output.isPresent()) {
                ItemStack stack = output.get();
                player.getInventory().placeItemBackInInventory(stack);

                this.setBlueprint(null, true);
                this.sync();
                this.setChanged();
            }
        }
    }

    public boolean isValid() {
        if (!this.hasLevel())
            return false;

        return this.getLevel().getBlockState(this.getBlockPos().below()).is(Blocks.SMITHING_TABLE);
    }

    public Optional<Blueprint> getBlueprint() {
        return Optional.ofNullable(this.blueprint);
    }

    public boolean hasBlueprint() {
        return this.getBlueprint().isPresent();
    }

    /**
     * attempts to set the blueprint of this fabricator
     * @return false if this fabricator already has a blueprint
     */
    public boolean setBlueprint(Blueprint blueprint, boolean force) {
        if (!force && this.hasBlueprint()) return false;
        this.blueprint = blueprint;
        this.syncChanges();
        return true;
    }
    /**
     * attempts to set the blueprint of this fabricator
     * @return false if this fabricator already has a blueprint
     */
    public boolean setBlueprint(Blueprint blueprint) {
        return this.setBlueprint(blueprint, false);
    }

    /**
     * @return the itemstack that should be displayed in the fabricator's renderer
     */
    public ItemStack getShowcaseStack() {
        if (this.hasBlueprint()) {
            if (this.blueprint.isComplete()) return this.blueprint.getOutput();

            // cycle through the requirements based off world ticks
            int index = (int) (level.getGameTime() / 20 % this.blueprint.getRequirements().size());
            return this.blueprint.getRequirements().get(index);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        this.blueprint = null;

        if (nbt.contains("Blueprint"))
            this.blueprint = new Blueprint(nbt.getCompound("Blueprint"));
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        if (blueprint != null)
            nbt.put("Blueprint", blueprint.toNbt(registries));

        nbt.putBoolean("HasBlueprint", this.hasBlueprint());
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    protected void syncChanges() {
        if (this.getLevel().isClientSide())
            return;

        ServerLevel world = (ServerLevel) this.getLevel();
        world.getChunkSource().blockChanged(this.getBlockPos());
        this.setChanged();
    }

    public void onBroken() {
        if (this.hasBlueprint()) {
            this.getBlueprint().ifPresent(blueprint -> {
                ItemStack stack = AITItems.BLUEPRINT.getDefaultInstance();
                BlueprintItem.setSchema(stack, blueprint.getSource());

                StackUtil.spawn(this.getLevel(), this.getBlockPos(), stack);

                List<ItemStack> inputs = blueprint.getInsertedItems();
                StackUtil.scatter(this.getLevel(), this.getBlockPos(), inputs);
            });
        }
    }
}
