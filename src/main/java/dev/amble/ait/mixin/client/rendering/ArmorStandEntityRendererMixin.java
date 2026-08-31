package dev.amble.ait.mixin.client.rendering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.client.renderers.wearables.RespiratorFeatureRenderer;
import dev.amble.ait.module.planet.client.renderers.wearables.SpacesuitFeatureRenderer;
import net.minecraft.client.model.ArmorStandArmorModel;
import net.minecraft.client.model.ArmorStandModel;
import net.minecraft.client.renderer.entity.ArmorStandRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.decoration.ArmorStand;


@Mixin(ArmorStandRenderer.class)
public abstract class ArmorStandEntityRendererMixin
        extends
            LivingEntityRenderer<ArmorStand, ArmorStandArmorModel> {

    public ArmorStandEntityRendererMixin(EntityRendererProvider.Context ctx, ArmorStandModel model,
                                         float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ait$armorStandEntityRenderer(EntityRendererProvider.Context ctx, CallbackInfo ci) {
        ArmorStandRenderer renderer = (ArmorStandRenderer) (Object) this;

        this.addLayer(new RespiratorFeatureRenderer<>(renderer, ctx.getModelSet()));
        this.addLayer(new SpacesuitFeatureRenderer<>(renderer, ctx.getModelSet()));
    }
}
