package dev.amble.ait.mixin.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.core.item.RenderableArmorItem;
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

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
        extends RenderLayer<T, M> {
    public ArmorFeatureRendererMixin(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Inject(at = @At("HEAD"), method = "renderArmorPiece(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/EquipmentSlot;ILnet/minecraft/client/model/HumanoidModel;FFFFFF)V", cancellable = true, require = 1)
    private void ait$renderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, T entity, EquipmentSlot slot,
            int light, A model, float limbSwing, float limbSwingAmount, float partialTick, float ageInTicks,
            float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.getItemBySlot(slot).getItem() instanceof RenderableArmorItem armor && armor.hasCustomRendering())
            ci.cancel();
    }
}
