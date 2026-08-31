package dev.amble.ait.core.item;




import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;


public class WaypointItem extends AbstractCoordinateModifierItem implements DyeableLeatherItem {

    public static final int DEFAULT_LEATHER_COLOR = 16777215;

    public WaypointItem(Properties settings) {
        super(settings);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag nbt = stack.getTagElement(TAG_DISPLAY);

        if (nbt != null && nbt.contains(TAG_COLOR, Tag.TAG_ANY_NUMERIC))
            return nbt.getInt(TAG_COLOR);

        return DEFAULT_LEATHER_COLOR; // white
    }
}
