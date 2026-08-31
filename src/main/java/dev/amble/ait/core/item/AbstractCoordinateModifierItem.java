package dev.amble.ait.core.item;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.Waypoint;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedGlobalPos;

// Abstract out the WaypointItem code so if we make anything in future needing coordinate modification we can reuse it - Loqor
public abstract class AbstractCoordinateModifierItem extends Item {

    public static final String POS_KEY = "pos";

    public AbstractCoordinateModifierItem(Properties settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultInstance() {
        return super.getDefaultInstance();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            CompoundTag main = ItemNbt.get(stack);

            if (!main.contains(POS_KEY))
                return;

            CompoundTag nbt = main.getCompound(POS_KEY);
            DirectedGlobalPos globalPos = DirectedGlobalPos.fromNbt(nbt);

            BlockPos pos = globalPos.getPos();
            String dir = DirectionControl.rotationToDirection(globalPos.getRotation());
            ResourceKey<Level> dimension = globalPos.getDimension();

            tooltips.add(Component.translatable("waypoint.position.tooltip")
                    .append(Component.literal(" > " + pos.getX() + ", " + pos.getY() + ", " + pos.getZ()))
                    .withStyle(ChatFormatting.BLUE));

            tooltips.add(Component.translatable("waypoint.direction.tooltip")
                    .append(Component.literal(" > " + dir.toUpperCase()))
                    .withStyle(ChatFormatting.BLUE));

            tooltips.add(Component.translatable("waypoint.dimension.tooltip")
                    .append(Component.literal(" > ").append(WorldUtil.worldText(dimension, false)))
                    .withStyle(ChatFormatting.BLUE));
        });
    }

    public static ItemStack create(Waypoint pos) {
        ItemStack stack = new ItemStack(AITItems.WAYPOINT_CARTRIDGE);
        if (pos == null) return stack;

        setPos(stack, pos.getPos());

        if (pos.hasName())
            stack.set(DataComponents.CUSTOM_NAME, Component.literal(pos.name()));

        return stack;
    }

    public static CachedDirectedGlobalPos getPos(ItemStack stack) {
        CompoundTag nbt = ItemNbt.get(stack);

        if (!nbt.contains(POS_KEY))
            return null;

        CachedDirectedGlobalPos cached = CachedDirectedGlobalPos.fromNbt(nbt.getCompound(POS_KEY));
        if (cached.getWorld() instanceof TardisServerWorld) {
            cached = CachedDirectedGlobalPos.create(TardisServerWorld.OVERWORLD, cached.getPos(), cached.getRotation());
        }

        return cached;
    }

    public static void setPos(ItemStack stack, DirectedGlobalPos pos) {
        CompoundTag nbt = ItemNbt.get(stack);
        if (pos == null) return;
        CachedDirectedGlobalPos cached = CachedDirectedGlobalPos.create(pos.getDimension(), pos.getPos(), pos.getRotation());
        if (cached.getWorld() instanceof TardisServerWorld) {
            cached = CachedDirectedGlobalPos.create(TardisServerWorld.OVERWORLD, cached.getPos(), cached.getRotation());
        }
        nbt.put(POS_KEY, cached.toNbt());
    }
}