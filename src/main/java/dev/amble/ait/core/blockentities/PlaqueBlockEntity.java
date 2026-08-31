package dev.amble.ait.core.blockentities;

import com.google.gson.JsonParseException;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.AITBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.state.BlockState;

public class PlaqueBlockEntity extends InteriorLinkableBlockEntity {

    private Component customPlaqueText = Component.translatable("block.ait.plaque.default_text");

    public PlaqueBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.PLAQUE_BLOCK_ENTITY_TYPE, pos, state);
    }

    public Component getPlaqueText() {
        return this.customPlaqueText;
    }

    public void setPlaqueText(Component name) {
        this.customPlaqueText = name.copy();
        setChanged();
        if (this.getLevel() != null && !this.getLevel().isClientSide) {
            this.getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    }

    public boolean onUse(ServerPlayer player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == Items.NAME_TAG && stack.has(DataComponents.CUSTOM_NAME)) {
            this.setPlaqueText(stack.getHoverName());
            if (!player.isCreative()) {
                stack.shrink(1);
            }
            return true;
        }
        return false;
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        nbt.putString("CustomPlaqueText", Component.Serializer.toJson(this.customPlaqueText, registries));
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        if (nbt.contains("CustomPlaqueText", Tag.TAG_STRING)) {
            this.customPlaqueText = readPlaqueText(nbt.getString("CustomPlaqueText"), registries);
        }
        if (this.customPlaqueText == null || this.customPlaqueText.getString().isEmpty()) {
            this.customPlaqueText = Component.translatable("block.ait.plaque.default_text");
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private static Component readPlaqueText(String plaqueText, HolderLookup.Provider registries) {
        try {
            Component text = Component.Serializer.fromJson(plaqueText, registries);
            if (text != null)
                return text;
        } catch (JsonParseException ignored) {
        }

        return Component.literal(plaqueText);
    }
}
