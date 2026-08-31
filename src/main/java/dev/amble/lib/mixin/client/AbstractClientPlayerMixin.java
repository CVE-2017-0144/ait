package dev.amble.lib.mixin.client;

import dev.amble.lib.skin.SkinData;
import dev.amble.lib.skin.SkinTracker;
import dev.amble.lib.skin.client.SkinGrabber;
import net.minecraft.client.player.AbstractClientPlayer;
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

	@Inject(method="getSkinTextureLocation", at=@At("HEAD"), cancellable = true)
	private void amblekit$getSkinTexture(CallbackInfoReturnable<ResourceLocation> cir) {
		AbstractClientPlayer player = (AbstractClientPlayer)(Object)this;

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

		cir.setReturnValue(id);
	}

	@Inject(method="getModelName", at=@At("HEAD"), cancellable = true)
	private void amblekit$getModel(CallbackInfoReturnable<String> cir) {
		AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;

		SkinTracker tracker = SkinTracker.getInstance();

		SkinData data = tracker.get(player.getUUID());
		if (data == null) return;

		cir.setReturnValue(data.slim() ? "slim" : "default");
	}
}
