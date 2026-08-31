package dev.amble.ait.core.blocks;

import static dev.amble.ait.client.util.TooltipUtil.addMultilineTooltip;
import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.blockentities.MatrixEnergizerBlockEntity;
import dev.amble.ait.core.item.TardisMatrixItem;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.SpawnUtil;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.SculkSensorPhase;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.vibrations.VibrationSystem;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MatrixEnergizerBlock extends Block implements EntityBlock {
    private final VoxelShape DEFAULT = Block.box(0.0, 0.0, 0.0, 16.0, 11.0, 16.0);
    public static final EnumProperty<SculkSensorPhase> SENSOR_PHASE = BlockStateProperties.SCULK_SENSOR_PHASE;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty HAS_POWER = BooleanProperty.create("has_power");
    public static final BooleanProperty SILENT = BooleanProperty.create("silent");
    public MatrixEnergizerBlock(Properties settings) {
        super(settings);

        this.registerDefaultState(
                this.defaultBlockState().setValue(AGE, 0).setValue(HAS_POWER, false)
                        .setValue(SENSOR_PHASE, SculkSensorPhase.INACTIVE).setValue(SILENT, true)
        );
    }

    public static boolean isInactive(BlockState blockState) {
        return blockState.getValue(SENSOR_PHASE) == SculkSensorPhase.INACTIVE;
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 3;
    }

    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public final boolean isMature(BlockState blockState) {
        return this.getAge(blockState) >= this.getMaxAge();
    }
    public static boolean hasPower(BlockState state) {
        return state.getValue(HAS_POWER);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getItemInHand(hand);
        if (world.isClientSide()) return InteractionResult.SUCCESS;

        if (hasPower(state)) return InteractionResult.FAIL;

        if (stack.is(Items.NETHER_STAR)) {
            world.setBlockAndUpdate(pos, state.setValue(HAS_POWER, true));
            stack.shrink(1);

            world.playSound(null, pos, SoundEvents.BEACON_POWER_SELECT, SoundSource.BLOCKS, 1.0F, 0.6F);

            return InteractionResult.SUCCESS;
        }

        player.displayClientMessage(Component.translatable("block.ait.matrix_energizer.needs_nether_star"), true);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (MatrixEnergizerBlock.getPhase(state) != SculkSensorPhase.ACTIVE) {
            if (MatrixEnergizerBlock.getPhase(state) == SculkSensorPhase.COOLDOWN) {
                world.setBlock(pos, state.setValue(SENSOR_PHASE, SculkSensorPhase.INACTIVE), Block.UPDATE_ALL);
            }
            return;
        }
        MatrixEnergizerBlock.setCooldown(world, pos, state);
    }

    public static SculkSensorPhase getPhase(BlockState state) {
        return state.getValue(SENSOR_PHASE);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        Vec3 centre = pos.above().getCenter();
        if (hasPower(state)) {
            for (int i = 0; i < getAge(state); i++) {
                double offsetX = AITMod.RANDOM.nextGaussian() * getAge(state) * 0.01f;
                double offsetY = AITMod.RANDOM.nextGaussian() * getAge(state) * 0.01f;
                double offsetZ = AITMod.RANDOM.nextGaussian() * getAge(state) * 0.01f;
                world.addParticle(AITMod.CORAL_PARTICLE, centre.x(), centre.y() - 0.65f, centre.z(), offsetX, offsetY, offsetZ);
                world.addParticle(ParticleTypes.SCULK_SOUL, centre.x(), centre.y() - 0.65f, centre.z(), offsetX, offsetY, offsetZ);
                world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, centre.x(), centre.y() - 0.65f, centre.z(), offsetX, offsetY, offsetZ);
            }
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        tryCreate(world, pos, state);
        shriekerShrieks(state, world, pos);
    }

    public void shriekerShrieks(BlockState state, Level world, BlockPos pos) {
        if (!(world instanceof ServerLevel serverWorld)) return;
        if (!hasPower(state)) return;

        BlockState shriekerState = world.getBlockState(pos.below());

        if (!(shriekerState.getBlock() instanceof SculkShriekerBlock))
            return;

        if (!shriekerState.getValue(SculkShriekerBlock.CAN_SUMMON) || !shriekerState.getValue(SculkShriekerBlock.SHRIEKING))
            return;

        if (world.getBlockEntity(pos) instanceof MatrixEnergizerBlockEntity mbe) {
            if (mbe.getVibrationUser().canReceiveVibration(serverWorld, pos, GameEvent.SHRIEK, GameEvent.Context.of(state))) {
                mbe.getListener().forceScheduleVibration(serverWorld, GameEvent.SHRIEK, GameEvent.Context.of(state), pos.below().getCenter());
                int i = this.getAge(state);

                if (i < this.getMaxAge()) {
                    world.setBlock(pos, state.setValue(AGE, i + 1), 2);
                } else {
                    tryCreate(world, pos, state);
                }
            }
        }
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        if (!hasPower(state)) return;

        // Drop if the block has power, gets broken, and isn't at finished producing the matrix. - Loqor
        ItemStack netherStar = new ItemStack(Items.NETHER_STAR);
        popResource((Level) world, pos, netherStar);

        if (this.getAge(state) == this.getMaxAge()) {
            ItemStack pmStack = TardisMatrixItem.randomize();
            popResource((Level) world, pos, pmStack);
        }
        super.destroy(world, pos, state);
    }

    private boolean tryCreate(Level world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) return false;
        if (this.isMature(state) && hasPower(state)) {
            world.playSound(null, pos, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 1.0F, 1.0F);
            ItemStack pmStack = TardisMatrixItem.randomize();
            ItemEntity matrix = new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), pmStack);
            world.addFreshEntity(matrix);
            if (world.getBlockEntity(pos.below()) instanceof SculkShriekerBlockEntity) {
                SpawnUtil.trySpawnMob(EntityType.WARDEN, MobSpawnType.TRIGGERED, (ServerLevel) world,
                        pos.below(), 20, 5, 6,
                        SpawnUtil.Strategy.ON_TOP_OF_COLLIDER).isPresent();
            }
            world.destroyBlock(pos, false);

            return true;
        }

        return false;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
            ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (!(placer instanceof ServerPlayer player)) {
            return;
        }

        state.setValue(SILENT, true);

        if (!(world.getBlockState(pos.below()).getBlock() instanceof SculkShriekerBlock)) {
            world.destroyBlock(pos, false);
            if (!player.isCreative()) popResource(world, pos, AITBlocks.MATRIX_ENERGIZER.asItem().getDefaultInstance());
            return;
        }

        if (world.getBlockEntity(pos) instanceof MatrixEnergizerBlockEntity) {
            TardisCriterions.PLACE_ENERGIZER.trigger(player);
        }
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return DEFAULT;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return DEFAULT;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return AITBlocks.MATRIX_ENERGIZER.asItem().getDefaultInstance();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE).add(HAS_POWER).add(SENSOR_PHASE).add(SILENT);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, tooltipContext, tooltip, options);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            addMultilineTooltip(tooltips, Component.translatable("tooltip.ait.matrix_energizer")
                    .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }

    @Override
    @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClientSide) {
            return MatrixEnergizerBlock.checkType(type, AITBlockEntityTypes.MATRIX_ENERGIZER_BLOCK_ENTITY_TYPE,
                    (worldx, pos, statex, blockEntity) -> {
                        VibrationSystem.Ticker.tick(worldx,
                                blockEntity.getVibrationData(), blockEntity.getVibrationUser());
                                shriekerShrieks(statex, worldx, pos);
                    });
        }
        return null;
    }

    @Nullable protected static <E extends BlockEntity, A extends BlockEntity> BlockEntityTicker<A> checkType(BlockEntityType<A> givenType, BlockEntityType<E> expectedType, BlockEntityTicker<? super E> ticker) {
        return expectedType == givenType ? (BlockEntityTicker<A>) ticker : null;
    }

    public static void setCooldown(Level world, BlockPos pos, BlockState state) {
        world.setBlock(pos, state.setValue(SENSOR_PHASE, SculkSensorPhase.COOLDOWN), Block.UPDATE_ALL);
        world.scheduleTick(pos, state.getBlock(), 10);
        MatrixEnergizerBlock.updateNeighbors(world, pos, state);
    }

    private static void updateNeighbors(Level world, BlockPos pos, BlockState state) {
        Block block = state.getBlock();
        world.updateNeighborsAt(pos, block);
        world.updateNeighborsAt(pos.below(), block);
        state.setValue(SILENT, true);
    }

    public int getCooldownTime() {
        return 15;
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new MatrixEnergizerBlockEntity(pos, state);
    }

    public void setActive(Level world, BlockPos pos, BlockState state, int frequency) {
        world.setBlock(pos,
                state.setValue(SENSOR_PHASE, SculkSensorPhase.ACTIVE), Block.UPDATE_ALL);
        world.scheduleTick(pos, state.getBlock(), this.getCooldownTime());
        MatrixEnergizerBlock.updateNeighbors(world, pos, state);
    }
}
