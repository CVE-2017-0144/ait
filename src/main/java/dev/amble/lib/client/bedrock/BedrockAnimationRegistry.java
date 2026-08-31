/*
 * Copyright (C) 2025 AmbleLabs
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * This code is MPL, due to it referencing this code: https://gitlab.com/cable-mc/cobblemon/-/blob/main/common/src/main/kotlin/com/cobblemon/mod/common/client/render/models/blockbench/bedrock/animation/BedrockAnimationRepository.kt
 */


package dev.amble.lib.client.bedrock;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.platform.resource.ReloadListeners;
import dev.amble.lib.platform.resource.SimpleReloadListener;
import dev.amble.lib.register.Registry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class BedrockAnimationRegistry implements SimpleReloadListener, Registry {
	private static final BedrockAnimationRegistry INSTANCE = new BedrockAnimationRegistry();

	private final Map<String, BedrockAnimation.Group> groups = new HashMap<>();

	public BedrockAnimationRegistry() {
		ReloadListeners.register(PackType.CLIENT_RESOURCES, this);
	}

	public BedrockAnimation get(String fileName, String animationName) {
		BedrockAnimation.Group group = groups.get(fileName);
		if (group == null) {
			return null;
		}
		return group.animations.get(animationName);
	}

	public BedrockAnimation get(BedrockAnimationReference data) {
		return get(data.fileName(), data.animationName());
	}

	@Override
	public ResourceLocation getReloadId() {
		return AmbleKit.id("bedrock_animation");
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		int animationCount = 0;
		groups.clear();

		for (ResourceLocation rawId : manager.listResources("bedrock", filename -> filename.getPath().endsWith(".animation.json")).keySet()) {
			try (InputStream stream = manager.getResource(rawId).get().open()) {
				JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();

				@Nullable JsonObject metadata = null;
				ResourceLocation metadataId = ResourceLocation.tryBuild(rawId.getNamespace(), rawId.getPath().replaceFirst("\\.animation\\.json$", ".metadata.json"));
				if (manager.getResource(metadataId).isPresent()) {
					try (InputStream metaStream = manager.getResource(metadataId).get().open()) {
						metadata = JsonParser.parseReader(new InputStreamReader(metaStream)).getAsJsonObject();
					} catch (Exception e) {
						AmbleKit.LOGGER.error("Error occurred while loading metadata for bedrock model {}", rawId.toString(), e);
					}
				}

				if (metadata != null) {
					// each key will be anim id
					for (String key : metadata.keySet()) {
						if (json.has("animations") && json.getAsJsonObject("animations").has(key)) {
							json.getAsJsonObject("animations").getAsJsonObject(key).add("metadata", metadata.getAsJsonObject(key));
						}
					}
				}

				BedrockAnimation.Group group = BedrockAnimation.GSON.fromJson(json, BedrockAnimation.Group.class);

				group.animations.forEach((name, animation) -> animation.name = name);

				String groupName = rawId.getPath().substring(rawId.getPath().lastIndexOf("/") + 1).replace(".animation.json", "");
				groups.put(groupName, group);
				animationCount += group.animations.size();
			} catch (Exception e) {
				AmbleKit.LOGGER.error("Error occurred while loading resource json {}", rawId.toString(), e);
			}
		}

		AmbleKit.LOGGER.info("Loaded {} animations from {} groups", animationCount, groups.size());
	}

	public static BedrockAnimationRegistry getInstance() {
		return INSTANCE;
	}
}
