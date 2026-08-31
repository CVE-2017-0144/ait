package dev.amble.lib.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.lib.animation.client.AnimationMetadata;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerEntityRendererMixin {
	@Inject(method="renderHand", at=@At("HEAD"), cancellable = true)
	private void amble$renderArm(PoseStack matrices, MultiBufferSource vertexConsumers, int light, AbstractClientPlayer player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {
		AnimationMetadata metadata = AnimationMetadata.getFor((dev.amble.lib.animation.AnimatedEntity) player);
		if (metadata == null || !metadata.fpsCamera()) return;

		ci.cancel();
	}
}
