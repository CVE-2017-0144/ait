package dev.amble.ait.api.tardis.link;

import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.client.tardis.manager.ClientTardisManager;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisManager;
import dev.amble.ait.core.util.ItemNbt;
import org.jetbrains.annotations.Nullable;

public abstract class LinkableItem extends Item {

    private final boolean showTooltip;
    private final String path;

    public LinkableItem(Properties settings, boolean showTooltip) {
        this(settings, "tardis", showTooltip);
    }

    public LinkableItem(Properties settings, String path, boolean showTooltip) {
        super(settings);

        this.path = path;
        this.showTooltip = showTooltip;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        this.handleTooltip(stack, tooltip);
        super.appendHoverText(stack, tooltipContext, tooltip, context);
    }

    private void handleTooltip(ItemStack stack, List<Component> tooltip) {
        if (!showTooltip)
            return;

        UUID id = this.getTardisId(stack);

        if (id == null)
            return;

        if (!Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip.ait.remoteitem.holdformoreinfo").withStyle(ChatFormatting.GRAY)
                    .withStyle(ChatFormatting.ITALIC));
            return;
        }

        ClientTardisManager.getInstance().getTardis(id, tardis -> {
            if (tardis != null) {
                tooltip.add(Component.translatable("tooltip.ait.linked_tardis").withStyle(ChatFormatting.BLUE));
                tooltip.add(Component.literal("> " + tardis.stats().getName()));
                tooltip.add(Component.literal("> " + tardis.getUuid().toString().substring(0, 8))
                        .withStyle(ChatFormatting.DARK_GRAY));
            }
        });
    }

    public void link(ItemStack stack, Tardis tardis) {
        this.link(stack, tardis.getUuid());
    }

    public void link(ItemStack stack, UUID uuid) {
        ItemNbt.edit(stack, tag -> tag.putUUID(this.path, uuid));
    }

    public void unlink(ItemStack stack) {
        ItemNbt.edit(stack, tag -> tag.remove(this.path));
    }

    public boolean isLinked(ItemStack stack) {
        return ItemNbt.get(stack).contains(this.path);
    }

    public boolean isOf(ItemStack stack, Tardis tardis) {
        if (tardis == null)
            return false;

        return tardis.getUuid().equals(this.getTardisId(stack));
    }

    public UUID getTardisId(ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);
        Tag element = nbt.get(path);

        if (element == null)
            return null;

        try {
            // convert old string data
            if (element.getId() == Tag.TAG_STRING) {
                UUID converted = UUID.fromString(element.getAsString());

                nbt.putUUID(path, converted);
                ItemNbt.set(stack, nbt);
                return converted;
            }

            return NbtUtils.loadUUID(element);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Tardis getTardis(Level world, ItemStack stack) {
        if (world == null)
            return null;

        UUID tardisId = this.getTardisId(stack);

        if (tardisId == null)
            return null;

        return TardisManager.with(world, (o, manager) ->
                manager.demandTardis(o, tardisId));
    }

    public static <T> T apply(ItemStack stack, BiFunction<LinkableItem, ItemStack, T> f) {
        if (!(stack.getItem() instanceof LinkableItem linkable))
            throw new IllegalArgumentException("Not a linkable!");

        return f.apply(linkable, stack);
    }

    public static void accept(ItemStack stack, BiConsumer<LinkableItem, ItemStack> c) {
        if (!(stack.getItem() instanceof LinkableItem linkable))
            throw new IllegalArgumentException("Not a linkable!");

        c.accept(linkable, stack);
    }

    public static void linkStatic(ItemStack stack, Tardis tardis) {
        accept(stack, (i, s) -> i.link(s, tardis));
    }

    public static void linkStatic(ItemStack stack, UUID id) {
        accept(stack, (i, s) -> i.link(s, id));
    }

    public static boolean isLinkedStatic(ItemStack stack) {
        return apply(stack, LinkableItem::isLinked);
    }

    public static boolean isOfStatic(ItemStack stack, Tardis tardis) {
        return apply(stack, (i, s) -> i.isOf(s, tardis));
    }

    public static UUID getTardisIdStatic(ItemStack stack) {
        return apply(stack, LinkableItem::getTardisId);
    }

    public static Tardis getTardisStatic(Level world, ItemStack stack) {
        return apply(stack, (i, s) -> i.getTardis(world, s));
    }
}
