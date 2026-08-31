package dev.amble.ait.core.engine.block;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.engine.block.generic.GenericStructureSystemBlockEntity;
import dev.amble.ait.core.engine.link.block.FluidLinkBlock;
import dev.amble.ait.core.item.RepairToolItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public abstract class SubSystemBlock extends FluidLinkBlock {
    private final SubSystem.IdLike id;

    protected SubSystemBlock(Properties settings, SubSystem.IdLike system) {
        super(settings);

        this.id = system;
    }

    public SubSystem.IdLike getSystemId() {
        return this.id;
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock() && !(world.isClientSide())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof SubSystemBlockEntity be)
                world.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return (world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof SubSystemBlockEntity be) {
                be.tick(world1, pos, state1);
            }
        };
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
                              BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() instanceof RepairToolItem)
            return InteractionResult.PASS;

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof GenericStructureSystemBlockEntity be)
            return be.useOn(state, world, player.isShiftKeyDown(), player, stack);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        if (!(world.getBlockEntity(pos) instanceof SubSystemBlockEntity be))
            return;
        if (!be.isLinked()) return;
        if (!(be.system() instanceof DurableSubSystem system)) return;

        float durability = system.durability();

        float percentageOf = (durability / DurableSubSystem.MAX_DURABILITY) * 100;

        if (percentageOf > 50) return;

        for(int i=0;i<3;i++){
            world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, 0.1,
                    0, 0.05f);
            world.addParticle(ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, 0.1,
                    0, 0.05f);
            world.addParticle(ParticleTypes.LAVA, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, -0.1,
                    0, -0.05f);
            world.addParticle(ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, -0.1,
                    0, -0.05f);
        }

        if (percentageOf > 25) return;

        for(int i=0;i<3;i++){
            world.addParticle(ParticleTypes.SMOKE, true, pos.getX() + 0.5f, pos.getY() + 1,
                    pos.getZ() + 0.5f, 0.15, 0, 0);
            world.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5f, pos.getY() + 1, pos.getZ() + 0.5f, 0.1,
                    0, 0.05f);
            world.addParticle(ParticleTypes.SMOKE, true, pos.getX() + 0.5f, pos.getY() + 1,
                    pos.getZ() + 0.5f, -0.15, 0, 0);
            world.addParticle(ParticleTypes.CLOUD, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, -0.1,
                    0, -0.05f);
        }

    }
}
