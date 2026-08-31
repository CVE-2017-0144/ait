package dev.amble.ait.mixin.client.rendering;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.core.item.RenderableArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorFeatureRendererMixin<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>>
        extends RenderLayer<T, M> {
    public ArmorFeatureRendererMixin(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Inject(at = @At("HEAD"), method = "renderModel", cancellable = true)
    private void ait$renderArmor(PoseStack matrices, MultiBufferSource vertexConsumers, int light, ArmorItem item, A model, boolean secondTextureLayer, float red, float green, float blue, @Nullable String overlay, CallbackInfo ci) {
        if (item instanceof RenderableArmorItem armor && armor.hasCustomRendering()) ci.cancel();
    }
}
