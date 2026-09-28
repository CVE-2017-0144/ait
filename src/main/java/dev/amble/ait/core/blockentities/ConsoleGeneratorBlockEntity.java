package dev.amble.ait.core.blockentities;

import static dev.amble.ait.core.blockentities.ConsoleBlockEntity.nextConsole;
import static dev.amble.ait.core.blockentities.ConsoleBlockEntity.nextVariant;
import static dev.amble.ait.core.blockentities.ConsoleBlockEntity.previousConsole;
import static dev.amble.ait.core.blockentities.ConsoleBlockEntity.previousVariant;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ConsoleGeneratorBlockEntity extends FluidLinkBlockEntity {
    public static final ResourceLocation SYNC_TYPE = AITMod.id("sync_gen_type");
    public static final ResourceLocation SYNC_VARIANT = AITMod.id("sync_gen_variant");
    private ResourceLocation type;
    private ResourceLocation variant;

    public ConsoleGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.CONSOLE_GENERATOR_ENTITY_TYPE, pos, state);

        this.type = ConsoleRegistry.HARTNELL.id();
    }

    public ConsoleGeneratorBlockEntity(BlockPos pos, BlockState state, ResourceLocation type, ResourceLocation variant) {
        super(AITBlockEntityTypes.CONSOLE_GENERATOR_ENTITY_TYPE, pos, state);

        this.type = type;
        this.variant = variant;
    }

    public void useOn(Level world, boolean sneaking, boolean punching, Player player) {
        if (!TardisServerWorld.isTardisDimension(world))
            return;

        if (!this.isLinked())
            return;

        ItemStack stack = player.getMainHandItem();

        boolean validItem = stack.is(AITItems.SONIC_SCREWDRIVER) || stack.is(Items.BLAZE_POWDER);
        boolean decrement = stack.is(Items.BLAZE_POWDER);

        if (validItem && tardis().get().isUnlocked(this.getConsoleVariant())) {
            if (decrement) {
                stack.shrink(1);
            }

            this.createConsole(player);
            return;
        }

        world.playSound(null, this.worldPosition, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 0.5f, 1.0f);

        if (sneaking) {
            this.changeConsole(punching
                    ? previousVariant(this.getConsoleVariant())
                    : nextVariant(this.getConsoleVariant()));
        } else {
            this.changeConsole(punching
                    ? previousConsole(this.getConsoleSchema())
                    : nextConsole(this.getConsoleSchema()));
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        if (this.type != null)
            nbt.putString("console", this.type.toString());

        if (this.variant != null)
            nbt.putString("variant", this.variant.toString());
    }

    private void createConsole(Player player) {
        if (this.getLevel() != null && this.getLevel().isClientSide()) return;

        ConsoleBlockEntity be = new ConsoleBlockEntity(worldPosition, AITBlocks.CONSOLE.defaultBlockState());

        be.setType(this.getConsoleSchema());
        be.setVariant(this.getConsoleVariant());

        if (level == null)
            return;

        if (this.tardis().isPresent() && !this.tardis().get().isUnlocked(this.getConsoleVariant())) {
            player.displayClientMessage(Component.translatable("message.ait.console_generator.not_unlocked")
                    .withStyle(ChatFormatting.ITALIC), true);
            level.playSound(null, this.worldPosition, SoundEvents.GLOW_ITEM_FRAME_BREAK, SoundSource.BLOCKS, 0.5f, 1.0f);
            return;
        }

        // ConsoleBlockEntity marks for controls when it gets linked
        level.setBlockAndUpdate(this.worldPosition, AITBlocks.CONSOLE.defaultBlockState());
        level.setBlockEntity(be);

        level.playSound(null, this.worldPosition, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 0.5f, 1.0f);
    }

    public ConsoleTypeSchema getConsoleSchema() {
        if (type == null) {
            this.setConsoleSchema(ConsoleRegistry.HARTNELL.id());
        }

        return ConsoleRegistry.getInstance().get(type);
    }

    public void setConsoleSchema(ResourceLocation type) {
        this.type = type;

        this.setChanged();
        this.syncType();

        if (this.getLevel() instanceof ServerLevel serverWorld)
            serverWorld.getChunkSource().blockChanged(this.worldPosition);
    }

    public ConsoleVariantSchema getConsoleVariant() {
        if (this.variant == null)
            this.variant = this.getConsoleSchema().getDefaultVariant().id();

        return ConsoleVariantRegistry.getInstance().get(this.variant);
    }

    public void setVariant(ResourceLocation variant) {
        this.variant = variant;

        this.setChanged();
        this.syncVariant();

        if (this.getLevel() instanceof ServerLevel serverWorld)
            serverWorld.getChunkSource().blockChanged(this.worldPosition);
    }

    public void changeConsole(ConsoleTypeSchema schema) {
        this.setConsoleSchema(schema.id());
        this.setVariant(schema.getDefaultVariant().id());
    }

    public void changeConsole(ConsoleVariantSchema schema) {
        this.setConsoleSchema(schema.parent().id());
        this.setVariant(schema.id());
    }

    private void syncType() {
        if (!hasLevel() || level.isClientSide())
            return;

        RegistryFriendlyByteBuf buf = AitNetworking.buf();

        buf.writeUtf(getConsoleSchema().id().toString());
        buf.writeBlockPos(getBlockPos());

        for (Player player : level.players()) {
            AitNetworking.send((ServerPlayer) player, SYNC_TYPE, buf);
        }
    }

    private void syncVariant() {
        if (!hasLevel() || level.isClientSide())
            return;

        RegistryFriendlyByteBuf buf = AitNetworking.buf();

        buf.writeUtf(getConsoleVariant().id().toString());
        buf.writeBlockPos(getBlockPos());

        for (Player player : level.players()) {
            AitNetworking.send((ServerPlayer) player, SYNC_VARIANT, buf);
        }
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        if (nbt.contains("console")) {
            ResourceLocation console = ResourceLocation.parse(nbt.getString("console"));
            this.setConsoleSchema(console);
        }

        if (nbt.contains("variant")) {
            ResourceLocation variant = ResourceLocation.parse(nbt.getString("variant"));
            this.setVariant(variant);
        }

        super.loadAdditional(nbt, registries);
    }
}
