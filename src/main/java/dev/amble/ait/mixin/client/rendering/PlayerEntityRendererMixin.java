package dev.amble.ait.mixin.client.rendering;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.renderers.wearables.RespiratorFeatureRenderer;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.item.PsychpaperItem;
import dev.amble.ait.module.planet.client.models.wearables.SpacesuitModel;
import dev.amble.ait.module.planet.client.renderers.wearables.SpacesuitFeatureRenderer;
import dev.amble.ait.module.planet.core.item.SpacesuitItem;

@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends
            LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    @Shadow
    protected abstract void setModelProperties(AbstractClientPlayer player);

    public PlayerEntityRendererMixin(EntityRendererProvider.Context ctx,
            PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ait$PlayerEntityRenderer(EntityRendererProvider.Context ctx, boolean slim, CallbackInfo ci) {
        PlayerRenderer renderer = (PlayerRenderer) (Object) this;

        this.addLayer(new RespiratorFeatureRenderer<>(renderer, ctx.getModelSet()));
        this.addLayer(new SpacesuitFeatureRenderer<>(renderer, ctx.getModelSet()));
        //this.addFeature(new SantaHatFeatureRenderer<>(renderer, ctx.getModelLoader()));
    }

    @Inject(method = "renderHand", at = @At("HEAD"), cancellable = true)
    private void ait$renderArm(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
        if (!(player.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpacesuitItem)) return;
        ci.cancel();

        PlayerModel<AbstractClientPlayer> playerEntityModel = this.getModel();
        this.setModelProperties(player);
        playerEntityModel.attackTime = 0.0f;
        playerEntityModel.crouching = false;
        playerEntityModel.swimAmount = 0.0f;
        playerEntityModel.setupAnim(player, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f);
        arm.xRot = 0.0f;

        SpacesuitModel spacesuitModel = new SpacesuitModel(SpacesuitModel.getTexturedModelData().bakeRoot());

        boolean rightHanded = player.getMainArm() == HumanoidArm.RIGHT;

        if (rightHanded) {
            spacesuitModel.RightArm.copyFrom(arm);
            spacesuitModel.RightArm.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(SpacesuitFeatureRenderer.BLANK_SPACESUIT)), light, OverlayTexture.NO_OVERLAY);
        } else {
            spacesuitModel.LeftArm.copyFrom(arm);
            spacesuitModel.LeftArm.render(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(SpacesuitFeatureRenderer.BLANK_SPACESUIT)), light, OverlayTexture.NO_OVERLAY);
        }
    }

    @Inject(method = "render*", at = @At("HEAD"), cancellable = true)
    public void ait$render(AbstractClientPlayer abstractClientPlayerEntity, float f, float g, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, CallbackInfo ci) {
        if (abstractClientPlayerEntity.getVehicle() instanceof FlightTardisEntity) {
            ci.cancel();
        }
    }

    @Inject(method = "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
    public void ait$psychicPaperNaming(AbstractClientPlayer abstractClientPlayerEntity, Component text, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, CallbackInfo ci) {
        List<ItemStack> papers = getItemInInventory(abstractClientPlayerEntity);

        if (!papers.isEmpty() && papers.get(0).hasCustomHoverName()) {
            super.renderNameTag(abstractClientPlayerEntity, papers.get(0).getHoverName(), matrixStack, vertexConsumerProvider, i);
            ci.cancel();
        }
    }

    @Unique private static List<ItemStack> getItemInInventory(Player player) {
        List<ItemStack> items = new ArrayList<>();

        for (ItemStack stack : player.getInventory().items) {
            if (stack != null && stack.getItem() instanceof PsychpaperItem)
                items.add(stack);
        }

        return items;
    }
}
