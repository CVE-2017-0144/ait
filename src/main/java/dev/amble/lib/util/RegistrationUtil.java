package dev.amble.lib.util;

import dev.amble.lib.platform.render.ClientRegistries;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.EntityType;

public class RegistrationUtil {
	// if i put this in the interface it crashes cus it cant load that stuff
	@Environment(EnvType.CLIENT)
	public static void registerBedrockRenderer(EntityType<?> type) {
		ClientRegistries.entityRenderer(type, ctx -> new dev.amble.lib.animation.client.BedrockEntityRenderer(ctx));
	}
}
