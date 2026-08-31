package dev.amble.ait.module.planet.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.item.RenderableArmorItem;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;


public class SpacesuitItem extends RenderableArmorItem {
    public static final String OXYGEN_KEY = "oxygen";
    public static final double MAX_OXYGEN = 5.2D;

    public SpacesuitItem(Holder<ArmorMaterial> material, Type type, Properties settings, boolean hasCustomRendering) {
        super(material, type, settings, hasCustomRendering);
    }

    @Override
    public ItemStack getDefaultInstance() {
        if (this.type != Type.CHESTPLATE) {
            return super.getDefaultInstance();
        }
        ItemStack stack = new ItemStack(this);
        CompoundTag compound = ItemNbt.get(stack);
        compound.putDouble(OXYGEN_KEY, MAX_OXYGEN);
        ItemNbt.set(stack, compound);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (this.type != Type.CHESTPLATE) return;
        CompoundTag compound = ItemNbt.get(stack);

        if (world == null || world.getServer() == null) return;

        if (world.getServer().getTickCount() % 20 != 0) {
            return;
        }

        Planet planet = PlanetRegistry.getInstance().get(world);

        if ((TardisServerWorld.isTardisDimension(world) || (planet != null && planet.hasOxygen()))) {
            compound.putDouble(OXYGEN_KEY, Math.min(MAX_OXYGEN, compound.getDouble(OXYGEN_KEY) + 0.2D));
            ItemNbt.set(stack, compound);
        } else if (compound.getDouble(OXYGEN_KEY) > 0.0D) {
            compound.putDouble(OXYGEN_KEY, Math.max(0.0D, compound.getDouble(OXYGEN_KEY) - 0.0035D));
            ItemNbt.set(stack, compound);
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);
        if (this.type != Type.CHESTPLATE) return;

        double oxygenLevel = ItemNbt.get(stack).getDouble(OXYGEN_KEY);
        String oxygenFormatted = String.format("%.1f", oxygenLevel) + "L / " + MAX_OXYGEN + "L";

        tooltip.add(Component.translatable("message.ait.oxygen", oxygenFormatted).withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE));
    }
}