package dev.amble.ait.core.item;

import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import org.jetbrains.annotations.Nullable;

// todo fix so many issues with having more than one of this item
public class SiegeTardisItem extends LinkableItem {
    static {
        TardisEvents.ENTER_TARDIS.register((tardis, entity) -> {
            if (!(entity instanceof ServerPlayer player))
                return TardisEvents.Interaction.PASS;
            boolean hasSiege = player.getInventory().hasAnyMatching(stack -> stack.is(AITItems.SIEGE_ITEM));
            if (!hasSiege) return TardisEvents.Interaction.PASS;

            player.displayClientMessage(Component.translatable("ait.tooltip.siege_item.enter").withStyle(ChatFormatting.RED), true);
            return TardisEvents.Interaction.FAIL;
        });
    }

    public static final String CURRENT_TEXTURE_KEY = "siege_current_texture";

    public SiegeTardisItem(Properties settings) {
        super(settings.stacksTo(1), "tardis-uuid", true);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (world.isClientSide())
            return;

        Tardis tardis = this.getTardis(world, stack);

        if (tardis == null) {
            stack.setCount(0);
            return;
        }

        if (!tardis.siege().isSiegeBeingHeld()) {
            tardis.setSiegeBeingHeld(null);
            stack.setCount(0);
            return;
        }

        if (entity instanceof ServerPlayer player)
            tardis.siege().setSiegeBeingHeld(player.getUUID());

        tardis.travel().forcePosition(fromEntity(entity));
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getHand() != InteractionHand.MAIN_HAND || context.getPlayer() == null)
            return InteractionResult.PASS;

        context.getPlayer().getInventory().setItem(context.getPlayer().getInventory().selected, Items.AIR.getDefaultInstance());

        context.getItemInHand().shrink(1);

        if (context.getLevel().isClientSide())
            return InteractionResult.SUCCESS;

        Tardis tardis = this.getTardis(context.getLevel(), context.getItemInHand());

        if (tardis == null)
            return InteractionResult.CONSUME;

        if (!tardis.siege().isSiegeBeingHeld()) {
            tardis.setSiegeBeingHeld(null);
            return InteractionResult.SUCCESS;
        }

        placeTardis(tardis, fromItemContext(context));
        return super.useOn(context);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        UUID id = this.getTardisId(stack);
        String text = id != null
                ? id.toString().substring(0, 8)
                : Component.translatable("tooltip.ait.remoteitem.notardis").getString();

        tooltip.add(Component.literal("→ " + text).withStyle(ChatFormatting.BLUE));
    }

    public static CachedDirectedGlobalPos fromItemContext(UseOnContext context) {
        return CachedDirectedGlobalPos.create((ServerLevel) context.getLevel(),
                context.getClickedPos().relative(context.getClickedFace()), (byte) 0);
    }

    public static CachedDirectedGlobalPos fromEntity(Entity entity) {
        return CachedDirectedGlobalPos.create((ServerLevel) entity.level(), BlockPos.containing(entity.position()),
                (byte) 0);
    }

    public static void pickupTardis(Tardis tardis, ServerPlayer player) {
        if (tardis.travel().handbrake() || player.getInventory().getFreeSlot() == -1)
            return;

        tardis.travel().deleteExterior();
        tardis.siege().setSiegeBeingHeld(player.getUUID());
        player.getInventory().add(create(tardis));
        player.getInventory().setChanged();
    }

    public static void placeTardis(Tardis tardis, CachedDirectedGlobalPos pos) {
        tardis.travel().forcePosition(pos);
        tardis.travel().placeExterior(false);
        tardis.setSiegeBeingHeld(null);
    }

    public static ItemStack create(Tardis tardis) {
        ItemStack stack = new ItemStack(AITItems.SIEGE_ITEM);
        stack.setCount(1);

        SiegeTardisItem.linkStatic(stack, tardis);
        return stack;
    }
}
