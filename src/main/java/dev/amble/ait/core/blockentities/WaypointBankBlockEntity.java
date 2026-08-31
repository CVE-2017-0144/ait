package dev.amble.ait.core.blockentities;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blocks.WaypointBankBlock;
import dev.amble.ait.core.item.WaypointItem;
import dev.amble.ait.core.util.StackUtil;
import dev.amble.ait.data.Waypoint;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class WaypointBankBlockEntity extends InteriorLinkableBlockEntity {

    private final WaypointData[] waypoints = new WaypointData[WaypointBankBlock.MAX_COUNT];
    private int selected = -1;

    public WaypointBankBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.WAYPOINT_BANK_BLOCK_ENTITY_TYPE, pos, state);
    }

    public void unselect() {
        this.selected = -1;
    }

    public void dropItems() {
        List<ItemStack> stacks = new ArrayList<>();

        for (WaypointData data : this.waypoints) {
            if (data == null)
                continue;

            stacks.add(data.toStack());
        }

        StackUtil.scatter(this.level, this.worldPosition, stacks);
    }

    public InteractionResult onUse(Level world, BlockState state, Player player, InteractionHand hand, int slot) {
        if (!this.isLinked())
            return InteractionResult.FAIL;

        ItemStack stack = player.getItemInHand(hand);

        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (stack.getItem() instanceof WaypointItem)
            return this.insert(state, stack, slot);

        if (player.isShiftKeyDown())
            return this.take(state, player, slot);

        return this.select(state, slot);
    }

    private InteractionResult take(BlockState state, Player player, int slot) {
        if (this.selected != slot)
            return InteractionResult.FAIL;

        WaypointData waypoint = this.waypoints[slot];

        if (waypoint == null)
            return InteractionResult.FAIL;

        this.waypoints[slot] = null;
        this.sync(state);

        player.addItem(waypoint.toStack());
        return InteractionResult.SUCCESS;
    }

    private InteractionResult insert(BlockState state, ItemStack stack, int slot) {
        WaypointData inserted = WaypointData.fromStack(stack);

        if (inserted == null || this.waypoints[slot] != null)
            return InteractionResult.FAIL;

        stack.shrink(1);

        this.waypoints[slot] = inserted;
        this.sync(state);

        return InteractionResult.SUCCESS;
    }

    private InteractionResult activate(int slot) {
        if (!this.isLinked())
            return InteractionResult.FAIL;

        WaypointData data = this.waypoints[slot];

        if (data == null)
            return InteractionResult.FAIL;

        this.tardis().get().travel().forceDestination(data.pos);

        this.level.playSound(null, this.getBlockPos(), AITSounds.WAYPOINT_ACTIVATE, SoundSource.BLOCKS);
        return InteractionResult.SUCCESS;
    }

    private InteractionResult select(BlockState state, int slot) {
        if (this.selected == slot && this.isLinked())
            return this.activate(slot);

        this.selected = slot;
        this.sync(state);

        return InteractionResult.SUCCESS;
    }

    protected void sync(BlockState state) {
        this.setChanged();
        level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_CLIENTS);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        ListTag waypoints = nbt.getList("waypoints", Tag.TAG_COMPOUND);

        for (int i = 0; i < this.waypoints.length; i++) {
            this.waypoints[i] = WaypointData.fromNbt(waypoints.getCompound(i));
        }

        this.selected = nbt.getShort("selected");
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        ListTag waypoints = new ListTag();

        for (int i = 0; i < this.waypoints.length; i++) {
            WaypointData data = this.waypoints[i];
            CompoundTag compound = new CompoundTag();

            if (data != null)
                data.toNbt(compound);

            waypoints.add(i, compound);
        }

        nbt.put("waypoints", waypoints);
        nbt.putShort("selected", (short) selected);
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public DoubleBlockHalf getHalf() {
        return null;
    }

    public WaypointData[] getWaypoints() {
        return waypoints;
    }

    public int getSelected() {
        return selected;
    }

    public record WaypointData(int color, String name, CachedDirectedGlobalPos pos) {

        private WaypointData(int color, Waypoint waypoint) {
            this(color, waypoint.name(), waypoint.getPos());
        }

        public static WaypointData fromStack(ItemStack stack) {
            if (!stack.getOrCreateTag().contains(WaypointItem.POS_KEY))
                return null;

            int color = ((DyeableLeatherItem) AITItems.WAYPOINT_CARTRIDGE).getColor(stack);
            Waypoint waypoint = Waypoint.fromStack(stack);

            return new WaypointData(color, waypoint);
        }

        public ItemStack toStack() {
            ItemStack result = new ItemStack(AITItems.WAYPOINT_CARTRIDGE);

            WaypointItem.setPos(result, this.pos);
            result.setHoverName(Component.literal(this.name));

            if (this.color != WaypointItem.DEFAULT_LEATHER_COLOR)
                ((DyeableLeatherItem) AITItems.WAYPOINT_CARTRIDGE).setColor(result, this.color);

            return result;
        }

        public void toNbt(CompoundTag nbt) {
            nbt.putInt("color", this.color);
            nbt.putString("name", this.name);

            if (this.pos != null)
                nbt.put("pos", this.pos.toNbt());
        }

        public static WaypointData fromNbt(CompoundTag nbt) {
            if (nbt.isEmpty())
                return null;

            int color = nbt.getInt("color");
            String name = nbt.getString("name");

            CachedDirectedGlobalPos pos = CachedDirectedGlobalPos.fromNbt(nbt.getCompound("pos"));

            return new WaypointData(color, name, pos);
        }
    }
}
