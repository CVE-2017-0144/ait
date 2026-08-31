package dev.amble.plushies;

import dev.amble.lib.animation.AnimatedBlockEntity;
import dev.amble.lib.blockentity.ABlockEntity;
import dev.amble.lib.client.bedrock.*;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class MarketablePlushieBlockEntity extends ABlockEntity implements AnimatedBlockEntity {

    private static final BedrockAnimationReference ANIMATION_REFERENCE = new BedrockAnimationReference("squish", "squish");

    private final AnimationState animationState = new AnimationState();

    private int age = 0;

    public MarketablePlushieBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public MarketablePlushieBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(PlushieBlockEntities.MARKETABLE_PLUSHIE_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public AnimationState getAnimationState() {
        return animationState;
    }

    @Override
    public String getTexturePrefix() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof MarketablePlushieBlock plushieBlock) {
            return plushieBlock.getTexturePrefix();
        }
        return "block";
    }

    @Override
    public @Nullable BedrockModelReference getModel() {
        Block block = this.getBlockState().getBlock();
        if (block instanceof MarketablePlushieBlock plushieBlock) {
            return plushieBlock.getModel();
        }
        return null;
    }

    @Override
    public void tick(Level world, BlockPos pos, BlockState state) {
        age++;
    }

    @Override
    public float getRenderYaw() {
        return this.getBlockState().getValue(MarketablePlushieBlock.ROTATION) * 22.5f;
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (world.isClientSide()) return InteractionResult.SUCCESS;
        world.playSound(null, pos, PlushieSounds.BOOP, SoundSource.BLOCKS, 0.4f, world.getRandom().nextBoolean() ? 1.0f : 0.9f);
        this.playAnimation(ANIMATION_REFERENCE);
        return InteractionResult.SUCCESS;
    }
}
