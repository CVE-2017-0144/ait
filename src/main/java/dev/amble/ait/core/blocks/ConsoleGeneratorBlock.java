package dev.amble.ait.core.blocks;

import static dev.amble.ait.core.blockentities.ConsoleBlockEntity.previousConsole;
import static dev.amble.ait.core.blockentities.ConsoleBlockEntity.previousVariant;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.blockentities.ConsoleGeneratorBlockEntity;
import dev.amble.ait.core.engine.link.block.FluidLinkBlock;
import dev.amble.ait.core.engine.link.block.FluidLinkBlockEntity;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.api.ICantBreak;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ConsoleGeneratorBlock extends FluidLinkBlock implements EntityBlock, ICantBreak {

    public ConsoleGeneratorBlock(Properties settings) {
        super(settings);
    }

    @Nullable @Override
    public FluidLinkBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConsoleGeneratorBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
            BlockHitResult hit) {

        if (world.getBlockEntity(pos) instanceof ConsoleGeneratorBlockEntity be)
            be.useOn(world, player.isShiftKeyDown(), false, player);

        return InteractionResult.SUCCESS;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        double centerX = pos.getX() + 0.5;
        double centerY = pos.getY() + 0.5;
        double centerZ = pos.getZ() + 0.5;
        double radius = 1;
        double angle = random.nextDouble() * 2 * Math.PI;
        int side = random.nextInt(4);

        double x = centerX;
        double y = centerY + (random.nextDouble() - 0.5) * 0.2;
        double z = centerZ;

        switch (side) {
            case 0: // +X
                x += radius * Math.cos(angle);
                z += radius * Math.sin(angle);
                break;
            case 1: // -X
                x -= radius * Math.cos(angle);
                z += radius * Math.sin(angle);
                break;
            case 2: // +Z
                z += radius * Math.cos(angle);
                x += radius * Math.sin(angle);
                break;
            case 3: // -Z
                z -= radius * Math.cos(angle);
                x += radius * Math.sin(angle);
                break;
        }

        if (random.nextInt(5) == 0) {
            for (int i = 0; i < 3; i++) {
                world.addParticle(AITMod.CORAL_PARTICLE, x, y, z,
                        random.nextGaussian() * 0.01, random.nextGaussian() * 0.01, random.nextGaussian() * 0.01);
            }
        }
    }

    // Triggers instead of onTryBreak when punching in survival mode.
    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        if (world.getBlockEntity(pos) instanceof ConsoleGeneratorBlockEntity be)
            be.useOn(world, player.isShiftKeyDown(), true, player);
    }

    // Triggers instead of onBlockBreakStart when punching in creative mode.
    @Override
    public void onTryBreak(Level world, BlockPos pos, BlockState state, Player player) {
        if (player == null)
            return;

        if (!TardisServerWorld.isTardisDimension(world) || !player.getMainHandItem().isEmpty()) {
            world.destroyBlock(pos, true);
            return;
        }

        if (world.getBlockEntity(pos) instanceof ConsoleGeneratorBlockEntity be) {
            world.playSound(null, pos, SoundEvents.SCULK_BLOCK_CHARGE, SoundSource.BLOCKS, 0.5f, 1.0f);

            if (player.isShiftKeyDown())
                be.changeConsole(previousVariant(be.getConsoleVariant()));
            else
                be.changeConsole(previousConsole(be.getConsoleSchema()));
        }
    }
}
