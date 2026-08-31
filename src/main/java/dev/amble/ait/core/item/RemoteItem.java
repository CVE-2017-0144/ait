package dev.amble.ait.core.item;

import static dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase.State.LANDED;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.handler.travel.TravelUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class RemoteItem extends LinkableItem {

    public RemoteItem(Properties settings) {
        super(settings, true);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();

        if (player == null)
            return InteractionResult.PASS;

        if (!(world instanceof ServerLevel serverWorld))
            return InteractionResult.PASS;

        Tardis tardis = RemoteItem.getTardisStatic(world, itemStack);

        if (tardis == null)
            return InteractionResult.FAIL;

        if (player.isShiftKeyDown()) {
            if (!tardis.travel().inFlight()) {
                if (!tardis.fuel().hasPower()) {
                    tardis.fuel().enablePower();
                    player.displayClientMessage(Component.translatable("message.ait.remoteitem.powering_up"), true);
                    tardis.getExterior().playSound(AITSounds.POWERUP, SoundSource.BLOCKS);
                    world.playSound(null, pos, AITSounds.REMOTE, SoundSource.BLOCKS);
                } else {
                    tardis.fuel().disablePower();
                    player.displayClientMessage(Component.translatable("message.ait.remoteitem.powering_down"), true);
                    tardis.getExterior().playSound(AITSounds.SHUTDOWN, SoundSource.BLOCKS);
                    world.playSound(null, pos, AITSounds.REMOTE, SoundSource.BLOCKS);
                }
            } else if (tardis.travel().inFlight() || !tardis.travel().isLanded()) {
                player.displayClientMessage(Component.translatable("message.ait.remoteitem.power_switch_disabled"), true);
            }
            return InteractionResult.PASS;
        }

        if (tardis.getFuel() <= 0)
            player.sendSystemMessage(Component.translatable("message.ait.remoteitem.warning1"));

        if (tardis.isRefueling())
            player.sendSystemMessage(Component.translatable("message.ait.remoteitem.cancel.refuel"));

        //It was dematting before anyway so as a lazy fix its a feature now!!
        //player.sendMessage(Text.translatable("message.ait.remoteitem.warning2"));

        // Check if the Tardis is already present at this location before moving
        // it there

        CachedDirectedGlobalPos currentPosition = tardis.travel().position();

        if (currentPosition.getPos().equals(pos))
            return InteractionResult.FAIL;

        if (!TardisServerWorld.isTardisDimension((ServerLevel) world)) {
            world.playSound(null, pos, AITSounds.REMOTE, SoundSource.BLOCKS);

            BlockPos temp = pos.above();

            if (world.getBlockState(pos).canBeReplaced())
                temp = pos;
                if (tardis.fuel().hasPower()) {
                    tardis.travel().speed(tardis.travel().maxSpeed().get());

                    TravelUtil.travelTo(tardis, CachedDirectedGlobalPos.create(serverWorld, temp, DirectionControl
                            .getGeneralizedRotation(RotationSegment.convertToSegment(player.getVisualRotationYInDegrees()))));
                } else {
                    player.displayClientMessage(Component.translatable("message.ait.remoteitem.takeoff_failed_powered_off"), true);
                }
            } else {
            world.playSound(null, pos, SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.BLOCKS, 1F,
                    0.2F);
            player.displayClientMessage(Component.translatable("message.ait.remoteitem.warning3"), true);
        }

        return InteractionResult.PASS;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        Tardis tardis = RemoteItem.getTardisStatic(world, stack);

        if (tardis == null)
            return;

        if (tardis.travel().getState() != LANDED)
            tooltip.add(Component.literal("→ " + tardis.travel().getDurationAsPercentage() + "%")
                    .withStyle(ChatFormatting.GOLD));
    }
}
