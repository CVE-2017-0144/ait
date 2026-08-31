package dev.amble.ait.module.gun.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.module.gun.core.item.BaseGunItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.InteractionHand;

@Mixin(value = PlayerRenderer.class, priority = 1002)
public abstract class PlayerEntityRendererMixin
        extends
        LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerEntityRendererMixin(EntityRendererProvider.Context ctx,
                                     PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(at = @At("RETURN"), method = "getArmPose", cancellable = true)
    private static void ait$getArmPose(AbstractClientPlayer player, InteractionHand hand, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (player == null) return;
        if (player.getMainHandItem().getItem() instanceof BaseGunItem) {
            if (Minecraft.getInstance().options.keyUse.isDown()) {
                cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_CHARGE);
            }
            cir.setReturnValue(HumanoidModel.ArmPose.CROSSBOW_HOLD);
        }
    }
}
