package dev.amble.lib.mixin.client;

import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.animation.client.AnimatedEntityModel;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerModel.class)
public abstract class PlayerEntityModelMixin<T extends LivingEntity>
        extends HumanoidModel<T> implements AnimatedEntityModel {

    @Unique
    ModelPart root;

    public PlayerEntityModelMixin(ModelPart root) {
        super(root);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void animation$init(ModelPart root, boolean thinArms, CallbackInfo ci) {
        this.root = root;
    }

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void animation$setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (!(livingEntity instanceof AnimatedEntity player)) return;

		PlayerModel model = (PlayerModel)(Object)this;
	    model.hat.copyFrom(model.head);
	    model.leftPants.copyFrom(model.leftLeg);
	    model.rightPants.copyFrom(model.rightLeg);
	    model.leftSleeve.copyFrom(model.leftArm);
	    model.rightSleeve.copyFrom(model.rightArm);
	    model.jacket.copyFrom(model.body);

	    if (!BedrockAnimation.IS_RENDERING_PLAYER || BedrockAnimation.IS_RENDERING_HEAD == null || livingEntity != Minecraft.getInstance().cameraEntity) return;

	    hat.visible = BedrockAnimation.IS_RENDERING_HEAD;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
