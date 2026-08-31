package dev.amble.ait.core.item.component;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.link.AbstractLinkItem;
import dev.amble.ait.core.item.sonic.SonicMode;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.core.util.StackUtil;
import dev.amble.ait.data.schema.MachineRecipeSchema;

public class AbstractTardisPart extends Item {

    public static final ResourceLocation DISASSEMBLE = AITMod.id("part_disassemble");
    public static final ResourceLocation ATTACH = AITMod.id("link_attach");
    public static final ResourceLocation UNATTACH = AITMod.id("link_unattach");

    private final AbstractLinkItem.Type[] slots;

    public AbstractTardisPart(Properties settings, AbstractLinkItem.Type... slots) {
        super(settings.stacksTo(1));
        this.slots = slots;
    }

    private static void set(ItemStack stack, AbstractLinkItem item, AbstractLinkItem.Type type) {
        CompoundTag nbt = ItemNbt.get(stack);
        StackUtil.write(nbt, type.toString(), item);
    }

    public static void remove(ItemStack stack, AbstractLinkItem.Type type) {
        AbstractTardisPart.set(stack, null, type);
    }

    public static void remove(ItemStack stack, AbstractLinkItem item) {
        AbstractTardisPart.remove(stack, item.getType());
    }

    public static void set(ItemStack stack, AbstractLinkItem item) {
        AbstractTardisPart.set(stack, item, item.getType());
    }

    public static AbstractLinkItem get(ItemStack stack, AbstractLinkItem.Type type) {
        CompoundTag nbt = ItemNbt.get(stack);
        Item result = StackUtil.readItem(nbt, type.toString());

        if (result != null)
            return (AbstractLinkItem) result;

        return null;
    }

    public static AbstractLinkItem getAny(ItemStack stack) {
        // i love guessing
        for (AbstractLinkItem.Type type : AbstractLinkItem.Type.values()) {
            AbstractLinkItem item = get(stack, type);

            if (item != null)
                return item;
        }

        return null;
    }

    public static AbstractLinkItem removeAny(ItemStack stack) {
        AbstractLinkItem item = AbstractTardisPart.getAny(stack);

        if (item == null)
            return null;

        AbstractTardisPart.remove(stack, item);
        return item;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) { // slot is the
                                                                                                            // machine,
                                                                                                            // stack is
                                                                                                            // the
                                                                                                            // cursor
        ItemStack machine = slot.getItem();

        if (clickType != ClickAction.SECONDARY)
            return false;

        // Should this be in SonicItem.Mode.INTERACTION?
        if (!stack.getItemHolder().is(AITTags.Items.SONIC_ITEM))
            return false;

        if (SonicItem.mode(stack) != SonicMode.Modes.INTERACTION)
            return false;

        AbstractTardisPart.disassemble(machine);
        return true;
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player,
            SlotAccess cursor) {
        if (player.level().isClientSide())
            return false;

        if (!(stack.getItem() instanceof AbstractLinkItem))
            return false;

        if (clickType == ClickAction.SECONDARY)
            AbstractTardisPart.set(stack, (AbstractLinkItem) StackUtil.take(otherStack).getItem());
        else {
            cursor.set(new ItemStack(StackUtil.orAir(AbstractTardisPart.removeAny(stack))));
        }

        return true;
    }

    public AbstractLinkItem.Type[] getSlots() {
        return slots;
    }

    @Environment(value = EnvType.CLIENT)
    public static void disassemble(ItemStack machine) {
        FriendlyByteBuf data = AitNetworking.buf();
        data.writeItem(StackUtil.take(machine));

        AitNetworking.send(DISASSEMBLE, data);
    }

    @Environment(value = EnvType.CLIENT)
    public static void unattach(ItemStack machine, AbstractLinkItem.Type link) {
        FriendlyByteBuf data = AitNetworking.buf();
        data.writeItem(machine);
        data.writeEnum(link);

        AitNetworking.send(UNATTACH, data);
    }

    @Environment(value = EnvType.CLIENT)
    public static void attach(ItemStack machine, AbstractLinkItem link) {
        FriendlyByteBuf data = AitNetworking.buf();
        data.writeItem(machine);
        StackUtil.writeItem(data, link);

        AitNetworking.send(ATTACH, data);
    }

    @Environment(value = EnvType.SERVER)
    public static void disassemble(ServerPlayer player, ItemStack machine, MachineRecipeSchema recipe) {
        machine.shrink(1);

        for (ItemStack input : recipe.input()) {
            player.drop(input, true);
        }
    }

    @Environment(value = EnvType.SERVER)
    public static void unattach(ServerPlayer player, ItemStack machine, AbstractLinkItem.Type type) {
        AbstractTardisPart.remove(machine, type);
    }
}
