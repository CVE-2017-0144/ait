package dev.amble.ait.core.blocks;

import com.mojang.serialization.MapCodec;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.core.blockentities.MonitorBlockEntity;
import dev.amble.ait.core.util.MonitorStateUtil;

public class MonitorBlock extends BaseEntityBlock implements EntityBlock {
    public static final int MAX_ROTATION_INDEX = RotationSegment.getMaxSegmentIndex();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final EnumProperty<MonitorStateUtil> TEXTURE;
    protected static final VoxelShape SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 8.0, 12.0);

    @Override
    public MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(MonitorBlock::new);
    }

    public MonitorBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0).setValue(TEXTURE, MonitorStateUtil.DEFAULT));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MonitorBlockEntity(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
                              BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof MonitorBlockEntity monitorBlockEntity)
            monitorBlockEntity.useOn(world, player.isShiftKeyDown(), player);

        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(ctx.getRotation()));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), MAX_ROTATIONS));
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION).add(TEXTURE);
    }

    static {
        TEXTURE = EnumProperty.create("skin", MonitorStateUtil.class);;
    }

}