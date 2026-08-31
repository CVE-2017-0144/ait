package dev.amble.lib.skin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.amble.lib.platform.lifecycle.ServerConnectionEvents;
import dev.amble.lib.platform.lifecycle.ServerLifecycleEvents;
import dev.amble.lib.util.ServerLifecycleHooks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.storage.LevelResource;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SkinTracker extends HashMap<UUID, SkinData> {
	public static final ResourceLocation SYNC_KEY = AmbleKit.id("skin_sync");

	private static SkinTracker INSTANCE;

	public static SkinTracker getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new SkinTracker();
		}
		return INSTANCE;
	}

	public static void init() {
		INSTANCE = new SkinTracker();

		ServerConnectionEvents.JOIN.register((player, server) -> {
			getInstance().sync(player);
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			getInstance().write(server);
		});

		ServerLifecycleEvents.SERVER_STARTED.register(SkinTracker::read);

		if (Platform.isClient()) {
			initClient();
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static void initClient() {
		ClientEvents.DISCONNECT.register((client) -> {
			getInstance().clear();
		});

		AitNetworking.registerClientReceiver(SYNC_KEY, ((client, handler, buf, responseSender) -> {
			getInstance().receive(buf);
		}));
	}

	@Nullable
	public SkinData putSynced(UUID id, SkinData data) {
		SkinData previous = this.put(id, data);
		sync(toBuf(id, data));
		return previous;
	}

	@Nullable
	public SkinData removeSynced(UUID id) {
		SkinData previous = this.remove(id);
		sync(toBuf(id, SkinData.clear()));
		return previous;
	}

	public Optional<SkinData> getOptional(UUID id) {
		return Optional.ofNullable(this.get(id));
	}

	private RegistryFriendlyByteBuf toBuf(UUID id, SkinData data) {
		RegistryFriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(1);

		buf.writeUUID(id);
		data.writeBuf(buf);

		return buf;
	}

	private RegistryFriendlyByteBuf toBuf() {
		return toBuf(this);
	}

	private RegistryFriendlyByteBuf toBuf(Map<UUID, SkinData> map) {
		RegistryFriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(map.size());

		for (Map.Entry<UUID, SkinData> entry : map.entrySet()) {
			buf.writeUUID(entry.getKey());

			entry.getValue().writeBuf(buf);
		}

		return buf;
	}

	private void sync(RegistryFriendlyByteBuf buf) {
		ServerLifecycleHooks.get().getPlayerList().getPlayers().forEach((p) -> this.sync(buf, p));
	}

	private void sync(RegistryFriendlyByteBuf buf, ServerPlayer player) {
		AitNetworking.send(player, SYNC_KEY, buf);
	}

	private void receive(RegistryFriendlyByteBuf buf) {
		int count = buf.readInt();
		for (int i = 0; i < count; i++) {
			UUID id = buf.readUUID();
			SkinData val = SkinData.readBuf(buf);
			if (val == null) continue;
			this.put(id, val);
		}
	}

	public void sync() {
		sync(toBuf());
	}

	public void sync(ServerPlayer target) {
		sync(toBuf(), target);
	}

	private static Path getSavePath(MinecraftServer server) {
		return server.getWorldPath(LevelResource.ROOT).resolve("amblekit").resolve("skins.json");
	}

	private void write(MinecraftServer server) {
		try {
			Path savePath = getSavePath(server);
			if (!Files.exists(savePath)) {
				Files.createDirectories(savePath.getParent());
			}

			Files.writeString(savePath, AmbleKit.GSON.toJson(this, SkinTracker.class));
		} catch (Exception e) {
			AmbleKit.LOGGER.error("Failed to write skins.json", e);
		}
	}

	private static void read(MinecraftServer server) {
		if (!(Files.exists(getSavePath(server)))) return;

		try {
			String raw = Files.readString(getSavePath(server));
			JsonObject object = JsonParser.parseString(raw).getAsJsonObject();
			INSTANCE = AmbleKit.GSON.fromJson(object, SkinTracker.class);
			INSTANCE.sync();
		} catch (Exception e) {
			AmbleKit.LOGGER.error("Failed to read skins.json", e);
		}
	}
}
