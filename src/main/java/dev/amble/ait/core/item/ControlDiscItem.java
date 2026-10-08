package dev.amble.ait.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.item.sonic.SonicMode;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.Waypoint;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedGlobalPos;
import org.jetbrains.annotations.Nullable;

public class ControlDiscItem extends AbstractCoordinateModifierItem {

    public static final String CAN_CONTAIN_PLAYERS = "can_contain_players";

    public ControlDiscItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if (TardisServerWorld.isTardisDimension(world)) {
            user.displayClientMessage(Component.translatable("ait.control_disc.unusable_in_tardis_world"), true);
            return InteractionResultHolder.fail(user.getItemInHand(hand));
        }

        ItemStack discStack = user.getItemInHand(hand);
        ItemStack otherStack = user.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        if (otherStack.getItem() instanceof SonicItem sonic && sonic.isLinked(otherStack)) {
            SonicMode mode = SonicItem.mode(otherStack);
            if (mode.equals(SonicMode.Modes.INTERACTION) && AbstractCoordinateModifierItem.getPos(discStack) == null) {
                CachedDirectedGlobalPos targetPos = CachedDirectedGlobalPos.create(world.dimension(),
                        user.blockPosition(), DirectedGlobalPos.getGeneralizedRotation(user.getMotionDirection()));
                AbstractCoordinateModifierItem.setPos(discStack, targetPos);
                ControlDiscItem.setCanContainPlayers(discStack, true);
                user.playSound(AITSounds.DING, 1f, 1f);
                user.displayClientMessage(Component.translatable("ait.control_disc.set_position")
                        .append(Component.literal(" > " + targetPos)
                                .withStyle(ChatFormatting.BLUE)), true);
            } else if (mode.equals(SonicMode.Modes.OVERLOAD) && AbstractCoordinateModifierItem.getPos(discStack) != null) {
                ControlDiscItem.setCanContainPlayers(discStack, !ControlDiscItem.canContainPlayers(discStack));
                user.playSound(AITSounds.DING, 1f, 0.1f);
                user.displayClientMessage(Component.translatable("ait.control_disc.can_contain_players.toggle", ControlDiscItem.canContainPlayers(discStack))
                                .withStyle(ChatFormatting.BLUE), true);
            }
        }

        return super.use(world, user, hand);
    }

    public static boolean canContainPlayers(ItemStack stack) {
        CompoundTag main = ItemNbt.get(stack);
        if (!main.contains(CAN_CONTAIN_PLAYERS))
            return false;
        return main.getBoolean(CAN_CONTAIN_PLAYERS);
    }

    public static void setCanContainPlayers(ItemStack stack, boolean canContainPlayers) {
        CompoundTag main = ItemNbt.get(stack);
        main.putBoolean(CAN_CONTAIN_PLAYERS, canContainPlayers);
        ItemNbt.set(stack, main);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        CompoundTag main = ItemNbt.get(stack);
        if (!main.contains(CAN_CONTAIN_PLAYERS))
            return;
        boolean canContainPlayers = main.getBoolean(CAN_CONTAIN_PLAYERS);
        tooltip.add(Component.translatable("ait.control_disc.can_contain_players.toggle", canContainPlayers)
                .withStyle(ChatFormatting.BLUE));
    }

    public static ItemStack create(Waypoint pos) {
        ItemStack stack = new ItemStack(AITItems.CONTROL_DISC);
        if (pos == null) return stack;

        setPos(stack, pos.getPos());

        if (pos.hasName())
            stack.set(DataComponents.CUSTOM_NAME, Component.literal(pos.name()));

        return stack;
    }
}
