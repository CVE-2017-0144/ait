package dev.amble.lib.animation;

import dev.amble.ait.core.net.AitNetworking;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import dev.amble.lib.util.ServerLifecycleHooks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class AnimationTracker {
	private static final AnimationTracker INSTANCE = new AnimationTracker();
	public static final ResourceLocation SYNC_KEY = AmbleKit.id("animation_sync");

	public static AnimationTracker getInstance() {
		return INSTANCE;
	}

	public static void init() {
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			getInstance().sync(handler.getPlayer());
		});

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			initClient();
		}
	}

	@Environment(EnvType.CLIENT)
	private static void initClient() {
		ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
			getInstance().clear();
		});

		AitNetworking.registerClientReceiver(SYNC_KEY, ((client, handler, buf, responseSender) -> {
			getInstance().receive(buf);
		}));

	}

	private final HashMap<UUID, BedrockAnimationReference> animations = new HashMap<>();
	private final Set<UUID> updated = new HashSet<>(); // entities which have been updated recently

	@Nullable
	public BedrockAnimationReference get(AnimatedInstance entity) {
		return this.animations.get(entity.getUuid());
	}

	public void add(UUID id, BedrockAnimationReference animation) {
		this.animations.put(id, animation);

		sync(toBuf(id, animation));
	}

	public void add(AnimatedInstance entity, BedrockAnimationReference animation) {
		this.add(entity.getUuid(), animation);
	}

	public void removeLocal(UUID id) {
		this.animations.remove(id);
	}

	public void removeLocal(AnimatedInstance entity) {
		this.removeLocal(entity.getUuid());
	}

	public void remove(UUID id) {
		this.removeLocal(id);

		sync(toRemovalBuf(id));
	}

	public void remove(AnimatedInstance entity) {
		this.remove(entity.getUuid());
	}

	public boolean isDirty(AnimatedInstance entity) {
		return this.updated.remove(entity.getUuid());
	}

	private void clear() {
		this.animations.clear();
	}

	private FriendlyByteBuf toBuf(Map<UUID, BedrockAnimationReference> map) {
		FriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(map.size());
		for (Map.Entry<UUID, BedrockAnimationReference> entry : map.entrySet()) {
			buf.writeUUID(entry.getKey());
			buf.writeResourceLocation(entry.getValue().id());
		}

		return buf;
	}

	private FriendlyByteBuf toBuf() {
		return toBuf(this.animations);
	}

	private FriendlyByteBuf toBuf(UUID id, BedrockAnimationReference animation) {
		FriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(1);
		buf.writeUUID(id);
		buf.writeResourceLocation(animation.id());

		return buf;
	}

	private FriendlyByteBuf toRemovalBuf(UUID id) {
		FriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(-1);
		buf.writeUUID(id);

		return buf;
	}

	private void receive(FriendlyByteBuf buf) {
		int count = buf.readInt();

		if (count == -1) {
			UUID id = buf.readUUID();
			this.animations.remove(id);
			return;
		}

		for (int i = 0; i < count; i++) {
			UUID id = buf.readUUID();
			BedrockAnimationReference reference = BedrockAnimationReference.parse(buf.readResourceLocation());

			this.animations.put(id, reference);
			this.updated.add(id);
		}
	}

	public void sync() {
		sync(toBuf());
	}

	public void sync(ServerPlayer target) {
		sync(toBuf(), target);
	}

	private void sync(FriendlyByteBuf buf) {
		ServerLifecycleHooks.get().getPlayerList().getPlayers().forEach((p) -> this.sync(buf, p));
	}

	private void sync(FriendlyByteBuf buf, ServerPlayer player) {
		AitNetworking.send(player, SYNC_KEY, buf);
	}
}
