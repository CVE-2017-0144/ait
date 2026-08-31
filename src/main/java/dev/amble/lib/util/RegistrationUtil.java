package dev.amble.lib.util;

import dev.amble.lib.platform.render.ClientRegistries;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.entity.EntityType;

public class RegistrationUtil {
	// if i put this in the interface it crashes cus it cant load that stuff
	@OnlyIn(Dist.CLIENT)
	public static void registerBedrockRenderer(EntityType<?> type) {
		ClientRegistries.entityRenderer(type, ctx -> new dev.amble.lib.animation.client.BedrockEntityRenderer(ctx));
	}
}
