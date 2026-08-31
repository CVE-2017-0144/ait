package dev.amble.lib.client.bedrock;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.platform.resource.ReloadListeners;
import dev.amble.lib.platform.resource.SimpleReloadListener;
import dev.amble.lib.register.datapack.DatapackRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.commons.lang3.NotImplementedException;

import java.io.InputStream;
import java.io.InputStreamReader;

@OnlyIn(Dist.CLIENT)
public class BedrockModelRegistry extends DatapackRegistry<BedrockModel> implements SimpleReloadListener {
	private static final BedrockModelRegistry INSTANCE = new BedrockModelRegistry();

	private BedrockModelRegistry() {
		ReloadListeners.register(PackType.CLIENT_RESOURCES, this);
	}

	@Override
	public BedrockModel fallback() {
		throw new NotImplementedException();
	}

	@Override
	public ResourceLocation getReloadId() {
		return AmbleKit.id("bedrock_model");
	}

	@Override
	public void onResourceManagerReload(ResourceManager manager) {
		clearCache();

		for (ResourceLocation rawId : manager.listResources("bedrock", filename -> filename.getPath().endsWith("geo.json")).keySet()) {
			try (InputStream stream = manager.getResource(rawId).get().open()) {
				String path = rawId.getPath();
				// remove "bedrock/" prefix and ".geo.json" suffix
				String idPath = path.substring("bedrock/".length(), path.length() - ".geo.json".length());
				ResourceLocation id = ResourceLocation.tryBuild(rawId.getNamespace(), idPath);

				JsonObject json = JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject();
				BedrockModel model = BedrockModel.from(json, id);

				register(model);

				AmbleKit.LOGGER.debug("Loaded bedrock model {} {}", id, model);
			} catch (Exception e) {
				AmbleKit.LOGGER.error("Error occurred while loading resource json {}", rawId.toString(), e);
			}
		}
	}

	@Override
	public void syncToClient(ServerPlayer player) {
		throw new UnsupportedOperationException("Client-side only registry");
	}

	@Override
	public void readFromServer(RegistryFriendlyByteBuf buf) {
		throw new UnsupportedOperationException("Client-side only registry");
	}

	public static BedrockModelRegistry getInstance() {
		return INSTANCE;
	}
}
