package dev.amble.ait.mixin.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.core.item.RenderableArmorItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public abstract class HeadFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    public HeadFeatureRendererMixin(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true, require = 1)
    public void ait$render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i,
            T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof RenderableArmorItem armor && armor.hasCustomRendering()) {
            ci.cancel();
        }
    }
}
