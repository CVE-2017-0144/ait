package dev.amble.ait.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.tardis.handler.distress.DistressCall;
import dev.amble.ait.core.util.ItemNbt;
import org.jetbrains.annotations.Nullable;

public class HypercubeItem extends Item {

    private static final String DISTRESS_CALL_KEY = "DistressCall";

    public HypercubeItem(Properties settings) {
        super(settings.durability(100));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (!AITMod.CONFIG.hypercubesEnabled) {
            user.displayClientMessage(Component.translatable("message.ait.hypercubes.disabled").withStyle(ChatFormatting.RED), true);
            return InteractionResultHolder.fail(new ItemStack(this));
        }
        if (hand != InteractionHand.MAIN_HAND) return InteractionResultHolder.fail(user.getItemInHand(hand));

        ItemStack held = user.getMainHandItem();

        if (!(world instanceof ServerLevel serverWorld)) {
            Minecraft.getInstance().gameRenderer.displayItemActivation(held);

            return InteractionResultHolder.success(user.getItemInHand(hand));
        }

        DistressCall call = getCall(held, serverWorld.getServer().getTickCount());
        if (call == null) {
            call = DistressCall.create(user, held.has(DataComponents.CUSTOM_NAME) ? held.getHoverName().getString() : "SOS", true);
            setCall(held, call);
        }

        boolean success = call.send(user.getUUID(), held);

        user.getCooldowns().addCooldown(this, 15 * 20);

        return success ? InteractionResultHolder.success(held) : InteractionResultHolder.fail(held);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!(world instanceof ServerLevel serverWorld)) return;

        DistressCall call = getCall(stack, serverWorld.getServer().getTickCount());
        if (call == null) return;
        if (call.isSourceCall()) return;

        stack.setDamageValue((int) ((1f - (((float) call.getTimeLeft() / (call.lifetime())))) * stack.getMaxDamage()));

        if (call.isValid()) return;

        stack.setCount(0);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        DistressCall call = getCall(stack, 0);
        if (call == null) return;

        if (call.isSourceCall()) {
            tooltip.add(Component.translatable("tooltip.ait.distresscall.source").withStyle(ChatFormatting.BOLD, ChatFormatting.GOLD));
        }
        tooltip.add(Component.literal(call.message()).withStyle(ChatFormatting.ITALIC, ChatFormatting.RED));
        tooltip.add(call.sender().getTooltip());
    }

    public static DistressCall getCall(ItemStack stack, int ticks) {
        CompoundTag data = ItemNbt.get(stack);
        if (!data.contains(DISTRESS_CALL_KEY)) return null;

        return DistressCall.fromNbt(data.getCompound(DISTRESS_CALL_KEY), ticks);
    }

    public static void setCall(ItemStack stack, DistressCall call) {
        ItemNbt.get(stack).put(DISTRESS_CALL_KEY, call.toNbt());

        stack.remove(DataComponents.CUSTOM_NAME);
    }

    public static ItemStack create(DistressCall call) {
        ItemStack stack = new ItemStack(AITItems.HYPERCUBE);
        setCall(stack, call);
        return stack;
    }
}
