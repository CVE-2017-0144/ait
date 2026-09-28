package dev.amble.ait.core.item;

import java.util.List;

import dev.amble.ait.api.AITUseActions;
import dev.amble.ait.api.ArtronHolderItem;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.client.sounds.ClientSoundManager;
import dev.amble.ait.core.AITDataComponents;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ArtronCollectorBlockEntity;
import dev.amble.ait.core.item.sonic.SonicMode;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import dev.amble.ait.registry.impl.SonicRegistry;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;


public class SonicItem extends LinkableItem implements ArtronHolderItem {

    public static final double MAX_FUEL = 1000;
    public static final String MODE_KEY = "mode";
    public static final String SONIC_TYPE = "sonic_type";

    public SonicItem(Properties settings) {
        super(settings, true);
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = ItemNbt.get(stack);

        stack.set(AITDataComponents.MODE, -1);
        nbt.putDouble(FUEL_KEY, getMaxFuel(stack));
        ItemNbt.set(stack, nbt);

        if (SonicRegistry.DEFAULT != null)
            stack.set(AITDataComponents.SONIC_TYPE, SonicRegistry.DEFAULT.id().toString());

        return stack;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return AITUseActions.SONIC;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        // Charge the sonic with the artron collector block
        if (context.getLevel().getBlockEntity(context.getClickedPos()) instanceof ArtronCollectorBlockEntity artronCollectorBlockEntity) {
            double remainder = this.addFuel(artronCollectorBlockEntity.getCurrentFuel(), context.getItemInHand());
            artronCollectorBlockEntity.setCurrentFuel(remainder);
        }
        return super.useOn(context);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        SonicMode mode = mode(stack);

        if (mode == null)
            return InteractionResultHolder.fail(stack);

        if (!this.checkFuel(stack))
            return InteractionResultHolder.fail(stack);

        if (user.isShiftKeyDown()) {
            mode = mode.next();
            setMode(stack, mode);

            world.playSound(user, user.blockPosition(), AITSounds.SONIC_SWITCH, SoundSource.PLAYERS, 1F, 1F);
            user.displayClientMessage(mode.text(), true);

            return InteractionResultHolder.consume(stack);
        }

        if (mode.startUsing(stack, world, user, hand)) {
            user.startUsingItem(hand);
            return InteractionResultHolder.consume(stack);
        }

        return InteractionResultHolder.fail(stack);
    }

    @Override
    public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
        SonicMode mode = mode(stack);

        if (mode == SonicMode.Modes.INACTIVE)
            return;

        if (world.isClientSide())
            ClientSoundManager.getSonicSound().onUse((AbstractClientPlayer) user);

        int ticks = mode.maxTime() - remainingUseTicks;

        if (ticks % 10 == 0) {
            removeFuel(mode.fuelCost(), stack);

            if (!this.checkFuel(stack))
                return;
        }

        mode.tick(stack, world, user, ticks, remainingUseTicks);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        SonicMode mode = mode(stack);

        if (mode == SonicMode.Modes.INACTIVE)
            return;

        if (world.isClientSide())
            ClientSoundManager.getSonicSound().onFinishUse((AbstractClientPlayer) user);

        mode.stopUsing(stack, world, user, mode.maxTime() - remainingUseTicks, remainingUseTicks);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        SonicMode mode = mode(stack);

        if (mode == SonicMode.Modes.INACTIVE)
            return stack;

        if (world.isClientSide())
            ClientSoundManager.getSonicSound().onFinishUse((AbstractClientPlayer) user);

        mode.finishUsing(stack, world, user);
        return stack;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return mode(stack).maxTime();
    }

    private boolean checkFuel(ItemStack stack) {
        SonicMode mode = mode(stack);

        if (this.isOutOfFuel(stack) && mode != SonicMode.Modes.INACTIVE) {
            mode = SonicMode.Modes.INACTIVE;

            setMode(stack, mode);
            return false;
        }

        return true;
    }

    private static final Component TEXT_MODE = Component.translatable("message.ait.sonic.mode");
    private static final Component TEXT_ARTRON = Component.translatable("message.ait.tooltips.artron_units").withStyle(ChatFormatting.BLUE);
    private static final Component TEXT_CASING = Component.translatable("message.ait.sonic.currenttype").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(TEXT_MODE.copy().append(mode(stack).text()));

        int fuel = (int) Math.round(this.getCurrentFuel(stack));
        boolean acceptableFuel = this.getCurrentFuel(stack) > this.getMaxFuel(stack) / 4;

        tooltip.add(TEXT_ARTRON.copy().append(Component.literal(String.valueOf(fuel))
                .withStyle(acceptableFuel ? ChatFormatting.GREEN : ChatFormatting.RED)));

        tooltip.add(TEXT_CASING.copy().append(schema(stack).name()));
        super.appendHoverText(stack, tooltipContext, tooltip, context);
    }

    public static SonicMode mode(ItemStack stack) {
        return SonicMode.Modes.getAndWrap(stack.getOrDefault(AITDataComponents.MODE, 0));
    }

    public static void setMode(ItemStack stack, SonicMode mode) {
        stack.set(AITDataComponents.MODE, mode.index());
    }

    public static SonicSchema schema(ItemStack stack) {
        String rawId = stack.getOrDefault(AITDataComponents.SONIC_TYPE, "");

        if (rawId == null)
            return SonicRegistry.DEFAULT;

        ResourceLocation id = ResourceLocation.tryParse(rawId);
        SonicSchema schema = SonicRegistry.getInstance().get(id);

        return schema == null ? SonicRegistry.DEFAULT : schema;
    }

    public static void setSchema(ItemStack stack, ResourceLocation id) {
        stack.set(AITDataComponents.SONIC_TYPE, id.toString());
    }

    public static void setSchema(ItemStack stack, SonicSchema schema) {
        SonicItem.setSchema(stack, schema.id());
    }

    @Override
    public double getMaxFuel(ItemStack stack) {
        return MAX_FUEL;
    }

    public static boolean isBeingUsed(Player player, ItemStack stack) {
        return player.isUsingItem() && player.getUseItem() == stack;
    }
}
