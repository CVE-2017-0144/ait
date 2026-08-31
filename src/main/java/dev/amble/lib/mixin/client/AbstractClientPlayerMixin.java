package dev.amble.lib.mixin.client;

import dev.amble.lib.skin.SkinData;
import dev.amble.lib.skin.SkinTracker;
import dev.amble.lib.skin.client.SkinGrabber;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

	@Unique @Nullable private SkinData lastSkin = null;

	@Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
	private void amblekit$getSkin(CallbackInfoReturnable<PlayerSkin> cir) {
		AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

		SkinTracker tracker = SkinTracker.getInstance();

		SkinData data = tracker.get(player.getUUID());
		if (data == null) return;

		ResourceLocation id = data.get();
		if (id == null) return;

		if (SkinGrabber.isMissingTexture(id) && lastSkin != null) {
			id = lastSkin.get();
		} else {
			lastSkin = data;
		}

		PlayerSkin base = cir.getReturnValue();
		if (base == null) return;

		cir.setReturnValue(new PlayerSkin(id, base.textureUrl(), base.capeTexture(), base.elytraTexture(),
				data.slim() ? PlayerSkin.Model.SLIM : PlayerSkin.Model.WIDE, base.secure()));
	}
}
