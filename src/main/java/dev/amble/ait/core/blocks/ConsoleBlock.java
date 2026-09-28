package dev.amble.ait.core.blocks;

import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.item.HammerItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.schema.console.type.CopperType;
import dev.amble.ait.data.schema.console.type.CrystallineType;
import dev.amble.lib.api.ICantBreak;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ConsoleBlock extends HorizontalDirectionalBlock implements EntityBlock, ICantBreak {

    private static final VoxelShape SHAPE;

    static {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0, 0, 1, 0.875, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 0.875, -0.25, 1, 1, 1.25), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(0, 1, 0, 1, 1.125, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(-0.25, 0.875, 0, 1.25, 1, 1), BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(-0.1875, 0.875, -0.125, 1.1875, 1, 0),
                BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(-0.1875, 0.875, 1, 1.1875, 1, 1.125),
                BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(1, 0.875, -0.1875, 1.125, 1, 1.1875),
                BooleanOp.OR);
        shape = Shapes.joinUnoptimized(shape, Shapes.box(-0.125, 0.875, -0.1875, 0, 1, 1.1875),
                BooleanOp.OR);

        SHAPE = shape;
    }

    public ConsoleBlock(Properties settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ConsoleBlockEntity(pos, state);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player,
                              BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ConsoleBlockEntity consoleBlockEntity) {
            if (world.dimension().equals(Level.OVERWORLD)) return InteractionResult.FAIL;
            consoleBlockEntity.useOn(world, player.isShiftKeyDown(), player);
            ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
            if (itemStack.getItem() instanceof HammerItem) {
                itemStack.getItem().useOn(new UseOnContext(world, player, InteractionHand.MAIN_HAND, itemStack, hit));
            }
        }

        if (world.isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        if (blockEntity instanceof ConsoleBlockEntity consoleBlockEntity) {
            if (!consoleBlockEntity.isEmpty()) { // This is to ensure that the console doesnt get used as an extra chest and accidental misclicks
                player.openMenu(consoleBlockEntity);
                world.playSound(null, pos, AITSounds.DOOM_DOOR_OPEN, SoundSource.BLOCKS, 1.0f, 0.7f);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level world, @NotNull BlockState state,
                                                                  @NotNull BlockEntityType<T> type) {
        return (world1, blockPos, blockState, ticker) -> {
            if (ticker instanceof ConsoleBlockEntity console) {
                console.tick(world, blockPos, blockState, console);
            }
        };
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
                         ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (world.getBlockEntity(pos) instanceof ConsoleBlockEntity consoleBlockEntity) {
            if (world.dimension().equals(Level.OVERWORLD)) {
                return;
            }
            consoleBlockEntity.markNeedsControl();
        }
    }

    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ConsoleBlockEntity console && console.isLinked()) {
            Tardis tardis = console.tardis().get();
            if (tardis.fuel().hasPower()) {
                return 15;
            }
        }
        return 0;
    }

    @Override
    public int getDirectSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
        return getSignal(state, world, pos, direction);
    }


    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (!TardisServerWorld.isTardisDimension(world)) return;
        if (entity instanceof Player player) {
            RandomSource random = world.random;
            int x_random = random.nextIntBetweenInclusive(1, 10);
            int y_random = random.nextIntBetweenInclusive(1, 10);
            int z_random = random.nextIntBetweenInclusive(1, 10);

            boolean is_x_negative = false;
            boolean is_z_negative = false;
            if (random.nextIntBetweenInclusive(1, 3) == 1) {
                is_x_negative = true;
            }
            if (random.nextIntBetweenInclusive(1, 3) == 1) {
                is_z_negative = true;
            }

            world.playSound(null, pos, AITSounds.CLOISTER, SoundSource.BLOCKS, 4f, 1f);

            player.push(0.15f * x_random * (is_x_negative ? -1 : 1), 0.1f * y_random,
                    0.15f * z_random * (is_z_negative ? -1 : 1));

            if (player instanceof ServerPlayer) {
                for (int i = 0; i < 100; i++) {
                    ((ServerLevel) world).sendParticles(ParticleTypes.ANGRY_VILLAGER,
                            pos.getX() + Mth.nextFloat(random, -2.0F, 3.0F), pos.getY() + Mth.nextFloat(random, 0.0F, 2.0F),
                            pos.getZ() + Mth.nextFloat(random, -2.0F, 3.0F), 1, Mth.nextFloat(random, -5.0F, 5.0F), Mth.nextFloat(random, -5.0F, 5.0F),
                            Mth.nextFloat(random, -5.0F, 5.0F), 1f);
                }
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    // This will literally never happen
    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        super.destroy(world, pos, state);

        if (world.getBlockEntity(pos) instanceof ConsoleBlockEntity console) {
            console.onBroken();
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, net.minecraft.util.RandomSource random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof ConsoleBlockEntity consoleBlockEntity) {

            if (!consoleBlockEntity.isLinked()) return;

            if (!consoleBlockEntity.tardis().get().fuel().hasPower()) return;

            double d = pos.getX();
            double e = pos.getY();
            double f = pos.getZ();

            if ((consoleBlockEntity.getTypeSchema() instanceof CrystallineType)) {

                for (int i = 0; i < random.nextInt(15) + 1; ++i) {
                    boolean bl = random.nextBoolean();
                    float particleSpeed = random.nextFloat() / 15.0f;

                    world.addParticle(ParticleTypes.SMOKE,
                            (double) pos.getX() + 0.5,
                            (double) pos.getY() + 2,
                            (double) pos.getZ() + 0.5,
                            bl ? particleSpeed : -particleSpeed,
                            2.2E-3,
                            bl ? particleSpeed : -particleSpeed);

                    world.addParticle(ParticleTypes.CLOUD,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            0.0,
                            0.1,
                            0.0);
                }
                return;
            }

            if (consoleBlockEntity.tardis() != null &&
                    !consoleBlockEntity.tardis().get().extra().getInsertedDisc().isEmpty() &&
                    consoleBlockEntity.getTypeSchema() instanceof CopperType) {
                for (int i = 0; i < random.nextInt(10) + 1; ++i) {
                    boolean bl = random.nextBoolean();
                    float b = (float)world.getRandom().nextInt(4) / 24.0f;

                    world.addParticle(ParticleTypes.NOTE,
                            d + 1.4,
                            e + 2.6,
                            f - 0.15f,
                            b + 1.5,
                            b,
                            b + 0.5);
                }
            }
        }
    }





    @Override
    public void onTryBreak(Level world, BlockPos pos, BlockState state) {
        if (TardisServerWorld.isTardisDimension(world)) return;

        world.destroyBlock(pos, true);
    }
}
