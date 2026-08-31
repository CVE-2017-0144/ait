
package dev.amble.lib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HumanoidArmorLayer.class, priority = 100)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
        extends RenderLayer<T, M> {

	public ArmorFeatureRendererMixin(RenderLayerParent<T, M> context) {
		super(context);
	}

	@Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void renderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        if (entity != Minecraft.getInstance().cameraEntity) {
            return;
        }

        if (armorSlot == EquipmentSlot.HEAD && BedrockAnimation.IS_RENDERING_PLAYER && BedrockAnimation.IS_RENDERING_HEAD == false) {
            ci.cancel();
        }
    }

}
