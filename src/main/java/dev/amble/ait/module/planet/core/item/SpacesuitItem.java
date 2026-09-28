package dev.amble.ait.module.planet.core.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import dev.amble.ait.core.AITDataComponents;
import dev.amble.ait.core.item.RenderableArmorItem;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;
import org.jetbrains.annotations.Nullable;


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
        stack.set(AITDataComponents.OXYGEN, MAX_OXYGEN);
        return stack;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        if (this.type != Type.CHESTPLATE) return;
        if (world == null || world.getServer() == null) return;

        if (world.getServer().getTickCount() % 20 != 0) {
            return;
        }

        Planet planet = PlanetRegistry.getInstance().get(world);

        if ((TardisServerWorld.isTardisDimension(world) || (planet != null && planet.hasOxygen()))) {
            stack.set(AITDataComponents.OXYGEN, Math.min(MAX_OXYGEN, stack.getOrDefault(AITDataComponents.OXYGEN, 0.0) + 0.2D));
        } else if (stack.getOrDefault(AITDataComponents.OXYGEN, 0.0) > 0.0D) {
            stack.set(AITDataComponents.OXYGEN, Math.max(0.0D, stack.getOrDefault(AITDataComponents.OXYGEN, 0.0) - 0.0035D));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);
        if (this.type != Type.CHESTPLATE) return;

        double oxygenLevel = stack.getOrDefault(AITDataComponents.OXYGEN, 0.0);
        String oxygenFormatted = String.format("%.1f", oxygenLevel) + "L / " + MAX_OXYGEN + "L";

        tooltip.add(Component.translatable("message.ait.oxygen", oxygenFormatted).withStyle(ChatFormatting.BOLD, ChatFormatting.BLUE));
    }
}