package dev.amble.ait.core.item;




import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;


public class WaypointItem extends AbstractCoordinateModifierItem {

    public static final int DEFAULT_LEATHER_COLOR = 16777215;

    public WaypointItem(Properties settings) {
        super(settings);
    }

    public static int getColor(ItemStack stack) {
        return DyedItemColor.getOrDefault(stack, DEFAULT_LEATHER_COLOR);
    }

    public static void setColor(ItemStack stack, int color) {
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(color & 0xFFFFFF, true));
    }
}
