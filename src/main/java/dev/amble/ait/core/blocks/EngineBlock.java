package dev.amble.ait.core.blocks;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.EngineBlockEntity;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.block.SubSystemBlock;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.world.TardisServerWorld;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EngineBlock extends SubSystemBlock implements EntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    protected static final VoxelShape Y_SHAPE = Shapes.block();

    public EngineBlock(Properties settings) {
        super(settings, SubSystem.Id.ENGINE);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        for (BlockPos offset : BlockPos.betweenClosed(pos.offset(-1, 0, -1), pos.offset(1, 0, 1))) {
            if (!offset.equals(pos) && !world.getBlockState(offset).canBeReplaced()) {
                if (ctx.getPlayer() instanceof ServerPlayer player)
                    player.displayClientMessage(Component.translatable("tardis.message.engine.no_space").withStyle(ChatFormatting.RED), true);

                return null;
            }
        }

        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Nullable @Override
    public FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EngineBlockEntity(pos, state);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world instanceof Level world1 && TardisServerWorld.isTardisDimension(world1);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (!(world.getBlockEntity(pos) instanceof EngineBlockEntity engine))
            return;

        if (!engine.isLinked()) return;

        double offsetX = (random.nextDouble() - 0.5) * 1.5;
        double offsetZ = (random.nextDouble() - 0.5) * 1.5;
        double offsetY = 1.5 + random.nextDouble();

        world.addParticle(AITMod.CORAL_PARTICLE, true, pos.getX() + 0.5 + offsetX, pos.getY() + offsetY,
                pos.getZ() + 0.5 + offsetZ, 0, 0.05, 0);
        world.addParticle(ParticleTypes.ENCHANT, true, pos.getX() + 0.5 + offsetX, pos.getY() + offsetY,
                pos.getZ() + 0.5 + offsetZ, 0, 0.05, 0);

        float durability = engine.tardis().get().subsystems().engine().durability();

        if (durability > 10) return;

        world.addParticle(ParticleTypes.LARGE_SMOKE, true, pos.getX() + 0.5, pos.getY() + 1.5,
                pos.getZ() + 0.5, 0, 0.1, 0);
        world.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1.5,
                pos.getZ() + 0.5, 0.1, 0, 0.05);

        world.addParticle(ParticleTypes.LARGE_SMOKE, true, pos.getX() + 0.5, pos.getY() + 1.5,
                pos.getZ() + 0.5, 0, 0.1, 0);
        world.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5, pos.getY() + 1.5,
                pos.getZ() + 0.5, 0.1, 0, 0.05);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
