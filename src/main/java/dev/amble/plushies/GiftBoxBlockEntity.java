package dev.amble.plushies;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.animation.AnimatedBlockEntity;
import dev.amble.lib.blockentity.ABlockEntity;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import lombok.Getter;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class GiftBoxBlockEntity extends ABlockEntity implements AnimatedBlockEntity {

    private static final BedrockModelReference REF = new BedrockModelReference(AmbleKit.MOD_ID, "gift_box");

    @Getter
    private final AnimationState animationState = new AnimationState();

    public GiftBoxBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public GiftBoxBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(PlushieBlockEntities.GIFT_BOX_BLOCK_ENTITY_TYPE, blockPos, blockState);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public int getAge() {
        Minecraft client = Minecraft.getInstance();
        return client.player != null ? client.player.tickCount : 0;
    }

    @Override
    public AnimationState getAnimationState() {
        return animationState;
    }

    @Override
    public String getTexturePrefix() {
        return "block";
    }

    @Override
    public @Nullable BedrockModelReference getModel() {
        return REF;
    }

    @Override
    public float getRenderYaw() {
        return this.getBlockState().getValue(GiftBoxBlock.ROTATION) * 22.5f;
    }
}
