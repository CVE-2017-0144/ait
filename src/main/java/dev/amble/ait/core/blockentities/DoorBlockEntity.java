package dev.amble.ait.core.blockentities;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.blocks.DoorBlock;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.item.KeyItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.SonicHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedBlockPos;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.gameevent.GameEvent;

public class DoorBlockEntity extends InteriorLinkableBlockEntity {

    private DirectedBlockPos directedPos;

    public DoorBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.DOOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    public static <T extends BlockEntity> void tick(Level world, BlockPos pos, BlockState blockState, T tDoor) {
        DoorBlockEntity door = (DoorBlockEntity) tDoor;

        if (!(world instanceof ServerLevel serverWorld))
            return;

        if (!door.isLinked())
            return;

        Tardis tardis = door.tardis().get();

        if (world.getServer().getTickCount() % 5 != 0)
            return;

        CachedDirectedGlobalPos globalExteriorPos = tardis.travel().position();

        if (globalExteriorPos == null)
            return;

        BlockPos exteriorPos = globalExteriorPos.getPos();
        Level exteriorWorld = globalExteriorPos.getWorld();
        boolean open = tardis.door().isOpen();

        if (exteriorWorld == null)
            return;

        BlockState blockstate1 = blockState.setValue(DoorBlock.LEVEL_4, exteriorWorld.getMaxLocalRawBrightness(exteriorPos.above()));

        // exit early for light updates
        if (world.getServer().getTickCount() % 20 != 0) {
            if (!open) {
                blockstate1 = blockstate1.setValue(DoorBlock.LEVEL_4, 0);
            }
            world.setBlock(pos, blockstate1, Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
            return;
        }

        if (!open || tardis.areShieldsActive()) {
            world.setBlock(pos, blockState.setValue(BlockStateProperties.WATERLOGGED, false),
                Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);
            return;
        }

        if (blockState.getValue(BlockStateProperties.WATERLOGGED) && world.getRandom().nextBoolean()) {
            serverWorld.players().forEach(player -> tardis.loyalty().subLevel(player, 2));
        }

        ChunkPos exteriorChunkPos = new ChunkPos(exteriorPos);
        ChunkAccess exteriorChunk = exteriorWorld.getChunk(exteriorChunkPos.x, exteriorChunkPos.z, ChunkStatus.EMPTY, false);

        if (exteriorChunk == null)
            return;

        BlockState exteriorState = exteriorChunk.getBlockState(exteriorPos);

        if (!(exteriorState.getBlock() instanceof ExteriorBlock))
            return;

        // TODO: performance sink. this should ideally be done in the exterior block code...
        boolean waterlogged = exteriorWorld.getBlockState(exteriorPos).getValue(BlockStateProperties.WATERLOGGED);

        world.setBlock(pos, blockState.setValue(BlockStateProperties.WATERLOGGED, waterlogged),
                Block.UPDATE_ALL | Block.UPDATE_IMMEDIATE);

        world.gameEvent(null, GameEvent.BLOCK_CHANGE, pos);
        world.scheduleTick(pos, blockState.getFluidState().getType(),
                blockState.getFluidState().getType().getTickDelay(world));
    }

    public void useOn(Level world, boolean sneaking, Player player) {
        if (player == null || this.tardis() == null || this.tardis().isEmpty())
            return;

        Tardis tardis = this.tardis().get();
        ItemStack keyStack = player.getMainHandItem();

        if (tardis.hasGrowthExterior())
            return;

        tardis.getDesktop().setDoorPos(this);

        if (keyStack.getItem() instanceof KeyItem key && !tardis.siege().isActive()) {
            if (keyStack.is(AITItems.SKELETON_KEY) || key.isOf(keyStack, tardis)) {
                tardis.door().interactToggleLock((ServerPlayer) player);
            } else {
                world.playSound(null, worldPosition, SoundEvents.NOTE_BLOCK_BIT.value(), SoundSource.BLOCKS, 1F, 0.2F);
                player.displayClientMessage(Component.translatable("tardis.key.identity_error"), true); // TARDIS does not identify with key
            }

            return;
        }

        if (tardis.sonic().getExteriorSonic() != null) {
            SonicHandler handler = tardis.sonic();
            if (worldPosition != null) {
                player.getInventory().placeItemBackInInventory(handler.takeExteriorSonic());
                world.playSound(null, worldPosition, SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(), SoundSource.BLOCKS, 1F,
                        0.2F);
            }

            return;
        }

        tardis.door().interact((ServerLevel) world, this.getBlockPos(), (ServerPlayer) player);
    }

    public Direction getFacing() {
        return this.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
    }

    @Nullable @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void onEntityCollision(Entity entity) {
        if (!TardisServerWorld.isTardisDimension((ServerLevel) this.getLevel()))
            return;

        if (!this.isLinked())
            return;

        Tardis tardis = this.tardis().get();

        if (tardis.door().isClosed())
            return;

        if (DependencyChecker.hasPortals() && AITMod.CONFIG.allowPortalsBoti && tardis.getExterior().getVariant().hasPortals())
            return;

        TravelHandler travel = tardis.travel();

        if (!tardis.flight().isFlying() && travel.getState() == TravelHandlerBase.State.FLIGHT && !tardis.areShieldsActive()) {
            TardisUtil.dropOutside(tardis, entity);
            return;
        }

        if (travel.getState() != TravelHandlerBase.State.LANDED)
            return;

        TardisUtil.teleportOutside(tardis, entity);
    }

    @Override
    public void onLinked() {
        this.tardis().ifPresent(tardis -> tardis.getDesktop().setDoorPos(this));
    }

    public void onBreak() {
        if (!this.isLinked())
            return;

        Tardis tardis = this.tardis().get();
        tardis.door().closeDoors();

        tardis.getDesktop().removeDoor(this);
    }

    public DirectedBlockPos getDirectedPos() {
        if (this.directedPos != null)
            return this.directedPos;

        this.directedPos = DirectedBlockPos.create(this.getBlockPos(), (byte)
                RotationSegment.convertToSegment(this.getFacing()));

        return this.directedPos;
    }
}
