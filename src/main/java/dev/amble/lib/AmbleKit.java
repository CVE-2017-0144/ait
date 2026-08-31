package dev.amble.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.amble.lib.animation.AnimationTracker;
import dev.amble.lib.command.PlayAnimationCommand;
import dev.amble.lib.command.SetSkinCommand;
import dev.amble.lib.skin.SkinTracker;
import net.fabricmc.api.ModInitializer;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.amble.lib.api.AmbleKitInitializer;
import dev.amble.lib.platform.Entrypoints;
import dev.amble.lib.platform.command.Commands;
import dev.amble.lib.register.AmbleRegistries;
import dev.amble.lib.util.ServerLifecycleHooks;

public class AmbleKit implements ModInitializer {
    public static final String MOD_ID = "amblekit";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Gson GSON = new GsonBuilder().create();

    @Override
    public void onInitialize() {
        AmbleRegistries.getInstance();
        ServerLifecycleHooks.init();
		SkinTracker.init();
		AnimationTracker.init();

		Commands.register((dispatcher, access, env) -> {
			SetSkinCommand.register(dispatcher);
			PlayAnimationCommand.register(dispatcher);
		});

        Entrypoints.invoke(AmbleKitInitializer.class, AmbleKitInitializer::onInitialize);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}