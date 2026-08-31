package dev.amble.lib.mixin.client;

import dev.amble.lib.animation.AnimatedEntity;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method="render", at=@At("HEAD"))
	private void amble$renderGame(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
		LocalPlayer player = Minecraft.getInstance().player;

		if (player != null) {
			BedrockAnimation anim = BedrockAnimation.getFor((AnimatedEntity) player);
			Optional<Boolean> wasHudHidden = BedrockAnimation.WAS_HUD_HIDDEN;

			if (anim != null) {
				if (wasHudHidden.isEmpty()) { // start
					wasHudHidden = Optional.of(Minecraft.getInstance().options.hideGui);

					if (anim.metadata.hideHud()) {
						Minecraft.getInstance().options.hideGui = true;
						BedrockAnimation.WAS_HUD_HIDDEN = wasHudHidden;
					}
				}
			} else {
				if (wasHudHidden.isPresent()) { // end
					Minecraft.getInstance().options.hideGui = wasHudHidden.get();
					wasHudHidden = Optional.empty();
					BedrockAnimation.WAS_HUD_HIDDEN = wasHudHidden;
				}
			}}
	}
}
