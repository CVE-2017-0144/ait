package dev.amble.ait.core.blocks;

import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.FoodMachineBlockEntity;
import dev.amble.ait.core.drinks.DrinkRegistry;
import dev.amble.ait.core.drinks.DrinkUtil;

public class FoodMachineBlock extends BaseEntityBlock implements EntityBlock {
    public static final int MAX_ROTATION_INDEX = RotationSegment.getMaxSegmentIndex();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    protected static final VoxelShape Y_SHAPE = Block.box(
            2.0,
            0.0,
            2,
            14.0,
            25.0,
            14
    );

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Y_SHAPE;
    }

    public FoodMachineBlock(Properties settings) {
        super(FabricBlockSettings.of()
                .strength(3.0F, 6.0F)
                .requiresCorrectToolForDrops());
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof FoodMachineBlockEntity machine)) return InteractionResult.SUCCESS;
        if (!machine.isPoweredOn()) return InteractionResult.PASS;
        ItemStack stack = player.getItemInHand(hand);

        // cycle through different modes
        if (player.isShiftKeyDown() && stack.isEmpty()) {
            world.playSound(null, pos, AITSounds.LOAD_WAYPOINT, SoundSource.BLOCKS, 1.0F, 1.0F);
            switch (machine.getMode()) {
                case FOOD_CUBES -> {
                    machine.setMode(FoodMachineBlockEntity.Mode.DRINKS);
                    player.displayClientMessage(Component.translatable("ait.foodmachine.mode.drinks").copy().withStyle(ChatFormatting.AQUA), true);
                }
                case DRINKS -> {
                    machine.setMode(FoodMachineBlockEntity.Mode.OVERCHARGED_FOOD_CUBES);
                    player.displayClientMessage(Component.translatable("ait.foodmachine.mode.overcharged_food_cubes").copy().withStyle(ChatFormatting.LIGHT_PURPLE), true);
                }
                case OVERCHARGED_FOOD_CUBES -> {
                    machine.setMode(FoodMachineBlockEntity.Mode.FOOD_CUBES);
                    player.displayClientMessage(Component.translatable("ait.foodmachine.mode.food_cubes").copy().withStyle(ChatFormatting.GREEN), true);
                }

            }
            return InteractionResult.SUCCESS;
        }

        // logic for the current mode selected
        switch (machine.getMode()) {
            case FOOD_CUBES -> {
                machine.eatFuel();
                world.playSound(null, pos, AITSounds.POWER_CONVERT, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.getInventory().add(AITItems.FOOD_CUBE.getDefaultInstance());
            }
            case DRINKS -> {
                machine.eatFuel();
                world.playSound(null, pos, AITSounds.COFFEE_MACHINE, SoundSource.BLOCKS, 1.0F, 1.0F);
                if (machine.getSelectedItem() == null) {
                    machine.setSelectedItem(DrinkUtil.setDrink(new ItemStack(AITItems.MUG), DrinkRegistry.getInstance().toList().get(1)));
                }
                player.getInventory().add(machine.getSelectedItem().copy());
            }
            case OVERCHARGED_FOOD_CUBES -> {
                machine.eatFuel();
                world.playSound(null, pos, AITSounds.POWER_CONVERT, SoundSource.BLOCKS, 1.0F, 1.0F);
                player.getInventory().add(AITItems.OVERCHARGED_FOOD_CUBE.getDefaultInstance());
            }

        }
        return InteractionResult.SUCCESS;
    }

    {
        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof FoodMachineBlockEntity machine)) return InteractionResult.PASS;
            if (!machine.isPoweredOn()) return InteractionResult.PASS;
            if (world.getBlockState(pos).getBlock() instanceof FoodMachineBlock) {
                if (player.isShiftKeyDown() && machine.getMode() == FoodMachineBlockEntity.Mode.DRINKS) {
                    if (!world.isClientSide) {
                        long now = world.getGameTime();
                        int cooldownTicks = 5;
                        if (now - machine.getLastDrinkTime() < cooldownTicks) {
                            return InteractionResult.FAIL;
                        }
                        machine.setLastDrinkTime(now);
                        int currentIndex = (machine.getCurrentIndex() + 1) % DrinkRegistry.getInstance().size();
                        machine.setCurrentIndex(currentIndex);
                        ItemStack selectedItem = DrinkUtil.setDrink(new ItemStack(AITItems.MUG), DrinkRegistry.getInstance().toList().get(machine.getCurrentIndex()));
                        machine.setSelectedItem(selectedItem);
                        player.displayClientMessage(Component.translatable("ait.foodmachine.mode.refreshement_set_to", selectedItem.getHoverName()), true);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AITBlockEntityTypes.FOOD_MACHINE_BLOCK_ENTITY_TYPE.create(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
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
        builder.add(ROTATION);
    }


}
