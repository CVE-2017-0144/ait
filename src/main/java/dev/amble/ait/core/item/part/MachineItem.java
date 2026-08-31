package dev.amble.ait.core.item.part;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.sonic.SonicMode;
import dev.amble.ait.data.schema.MachineRecipeSchema;

public class MachineItem extends Item {

    public static final ResourceLocation MACHINE_DISASSEMBLE = AITMod.id("machine_disassemble");

    public MachineItem(Properties settings) {
        super(settings);
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickType, Player player) {
        if (clickType != ClickAction.SECONDARY)
            return false;

        ItemStack machine = slot.getItem();

        // Should this be in SonicItem.Mode.INTERACTION?
        if (!stack.getItemHolder().is(AITTags.Items.SONIC_ITEM))
            return false;

        if (SonicItem.mode(stack) != SonicMode.Modes.INTERACTION)
            return false;

        MachineItem.disassemble(machine);
        return true;
    }

    @Environment(value = EnvType.CLIENT)
    public static void disassemble(ItemStack machine) {
        FriendlyByteBuf data = PacketByteBufs.create();
        data.writeItem(machine.copyWithCount(1));

        ClientPlayNetworking.send(MACHINE_DISASSEMBLE, data);
        machine.shrink(1);
    }

    @Environment(value = EnvType.SERVER)
    public static void disassemble(ServerPlayer player, ItemStack machine, MachineRecipeSchema recipe) {
        machine.shrink(1);

        for (ItemStack input : recipe.input()) {
            player.drop(input, true);
        }
    }
}
