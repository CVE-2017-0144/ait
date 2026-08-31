package dev.amble.ait.mixin.client;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.models.items.HandlesModel;
import dev.amble.ait.client.models.items.RiftScannerModel;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.module.planet.core.PlanetItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Unique private final RiftScannerModel riftScannerModel = new RiftScannerModel(RiftScannerModel.getTexturedModelData().bakeRoot());
    @Unique private final HandlesModel handlesModel = new HandlesModel(HandlesModel.getTexturedModelData().bakeRoot());

    @Inject(method = "renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V", at = @At("HEAD"), cancellable = true)
    public void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, @Nullable Level world, int light, int overlay, int seed, CallbackInfo ci) {
        if (stack.isEmpty()) return;

        if (stack.is(AITItems.RIFT_SCANNER)) {
            this.ait$handleRiftScannerRendering(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, world, light, overlay, seed, ci);
        }

        if (stack.is(PlanetItems.HANDLES)) {
            this.ait$handleHandlesRendering(entity, stack, renderMode, leftHanded, matrices, vertexConsumers, world, light, overlay, seed, ci);
        }
    }

    @Inject(method = "render(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/client/resources/model/BakedModel;)V", at = @At("HEAD"), cancellable = true)
    private void renderItem(ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay, BakedModel model, CallbackInfo ci) {
        if (stack.isEmpty()) return;

        if (stack.is(AITItems.RIFT_SCANNER)) {
            this.ait$handleRiftScannerRendering(null, stack, renderMode, leftHanded, matrices, vertexConsumers, null, light, overlay, 0, ci);
        }

        if (stack.is(PlanetItems.HANDLES)) {
            this.ait$handleHandlesRendering(null, stack, renderMode, leftHanded, matrices, vertexConsumers, null, light, overlay, 0, ci);
        }
    }

    @Unique private void ait$handleRiftScannerRendering(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, @Nullable Level world, int light, int overlay, int seed, CallbackInfo ci) {
        if (!stack.is(AITItems.RIFT_SCANNER))
            return;

        matrices.pushPose();

        matrices.translate(-0.5f, -0.5f, -0.5f);
        matrices.scale(1.0f, -1.0f, -1.0f);

        // render model here
        riftScannerModel.setAngles(matrices, renderMode, leftHanded);

        ClientLevel clientWorld = world instanceof ClientLevel ? (ClientLevel) world : null;
        riftScannerModel.render(clientWorld, entity, stack, matrices, vertexConsumers, light, overlay, seed);

        matrices.popPose();
        ci.cancel();
    }

    @Unique private void ait$handleHandlesRendering(LivingEntity entity, ItemStack stack, ItemDisplayContext renderMode, boolean leftHanded, PoseStack matrices, MultiBufferSource vertexConsumers, @Nullable Level world, int light, int overlay, int seed, CallbackInfo ci) {
        if (!stack.is(PlanetItems.HANDLES))
            return;

        matrices.pushPose();

        matrices.translate(-0.5f, -0.5f, -0.5f);
        matrices.scale(1.0f, -1.0f, -1.0f);

        // render model here
        handlesModel.setAngles(matrices, renderMode, leftHanded);

        ClientLevel clientWorld = world instanceof ClientLevel ? (ClientLevel) world : null;
        handlesModel.render(clientWorld, entity, stack, matrices, FastColor.ARGB32.colorFromFloat(seed, vertexConsumers, light, overlay));

        matrices.popPose();
        ci.cancel();
    }
}
