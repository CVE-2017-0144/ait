package dev.amble.lib.client;

import dev.amble.lib.client.bedrock.BedrockAnimationRegistry;
import dev.amble.lib.client.bedrock.BedrockModelRegistry;
import dev.amble.lib.register.AmbleRegistries;
import dev.amble.lib.skin.client.SkinGrabber;
import net.fabricmc.api.ClientModInitializer;

import dev.amble.lib.api.AmbleKitClientInitializer;
import dev.amble.lib.platform.Entrypoints;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;

public class AmbleKitClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Entrypoints.invoke(AmbleKitClientInitializer.class, AmbleKitClientInitializer::onInitialize);

        AmbleRegistries.getInstance().registerAll(
                BedrockModelRegistry.getInstance(),
                BedrockAnimationRegistry.getInstance()
        );

	    ClientEvents.END_CLIENT_TICK.register((client) -> {
			SkinGrabber.INSTANCE.tick();
	    });
	}
}
