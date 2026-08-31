package dev.amble.lib.skin;

import dev.amble.lib.skin.client.SkinGrabber;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.entity.EntityAccess;

public interface PlayerSkinTexturable extends EntityAccess {
	default SkinData getSkin() {
		return SkinTracker.getInstance().get(this.getUUID());
	}

	default void setSkin(SkinData skin) {
		skin.upload(this);
	}

	@OnlyIn(Dist.CLIENT)
	default ResourceLocation getSkinTexture() {
		SkinData skin = this.getSkin();
		if (skin == null) return SkinGrabber.missing();
		return skin.get();
	}
}
