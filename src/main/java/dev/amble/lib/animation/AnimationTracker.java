package dev.amble.lib.animation;

import dev.amble.ait.core.net.AitNetworking;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.amble.lib.platform.lifecycle.ServerConnectionEvents;
import dev.amble.lib.util.ServerLifecycleHooks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.network.RegistryFriendlyByteBuf;
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
		ServerConnectionEvents.JOIN.register((player, server) -> {
			getInstance().sync(player);
		});

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

	private RegistryFriendlyByteBuf toBuf(Map<UUID, BedrockAnimationReference> map) {
		RegistryFriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(map.size());
		for (Map.Entry<UUID, BedrockAnimationReference> entry : map.entrySet()) {
			buf.writeUUID(entry.getKey());
			buf.writeResourceLocation(entry.getValue().id());
		}

		return buf;
	}

	private RegistryFriendlyByteBuf toBuf() {
		return toBuf(this.animations);
	}

	private RegistryFriendlyByteBuf toBuf(UUID id, BedrockAnimationReference animation) {
		RegistryFriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(1);
		buf.writeUUID(id);
		buf.writeResourceLocation(animation.id());

		return buf;
	}

	private RegistryFriendlyByteBuf toRemovalBuf(UUID id) {
		RegistryFriendlyByteBuf buf = AitNetworking.buf();

		buf.writeInt(-1);
		buf.writeUUID(id);

		return buf;
	}

	private void receive(RegistryFriendlyByteBuf buf) {
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

	private void sync(RegistryFriendlyByteBuf buf) {
		ServerLifecycleHooks.get().getPlayerList().getPlayers().forEach((p) -> this.sync(buf, p));
	}

	private void sync(RegistryFriendlyByteBuf buf, ServerPlayer player) {
		AitNetworking.send(player, SYNC_KEY, buf);
	}
}
