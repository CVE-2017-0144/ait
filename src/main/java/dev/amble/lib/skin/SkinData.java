package dev.amble.lib.skin;

import dev.amble.lib.skin.client.SkinGrabber;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.Consumer;

public record SkinData(String key, @Nullable String url, @Nullable ResourceLocation localTexture, boolean slim) {
	private static final SkinData CLEAR = new SkinData("supersecretcodeword", null, null, false);

	public static SkinData username(String username, boolean slim) {
		return new SkinData(username, SkinConstants.SKIN_URL + username, null, slim);
	}

	public static void username(String username, Consumer<SkinData> consumer) {
		SkinConstants.fetchPlayerModel(username, slim -> consumer.accept(username(username, slim)));
	}

	public static void usernameUpload(String username, UUID uuid) {
		username(username, skin -> skin.upload(uuid));
	}

	public static SkinData url(String url, boolean slim) {
		return new SkinData(SkinConstants.encodeURL(url), url, null, slim);
	}

	public static SkinData texture(ResourceLocation texture, boolean slim) {
		return new SkinData(texture.toString(), null, texture, slim);
	}

	public static SkinData clear() {
		return CLEAR;
	}

	public static SkinData readBuf(RegistryFriendlyByteBuf buf) {
		String key = buf.readUtf();
		String url = buf.readBoolean() ? buf.readUtf() : null;
		ResourceLocation localTexture = buf.readBoolean() ? buf.readResourceLocation() : null;
		boolean slim = buf.readBoolean();


		if (key.equalsIgnoreCase(CLEAR.key())) return null;

		return new SkinData(key, url, localTexture, slim);
	}

	public void writeBuf(RegistryFriendlyByteBuf buf) {
		buf.writeUtf(key);
		buf.writeBoolean(url != null);
		if (url != null) buf.writeUtf(url);
		buf.writeBoolean(localTexture != null);
		if (localTexture != null) buf.writeResourceLocation(localTexture);
		buf.writeBoolean(slim);
	}

	public SkinData withSlim(boolean slim) {
		return new SkinData(key, url, localTexture, slim);
	}

	@Environment(EnvType.CLIENT)
	public ResourceLocation get() {
		if (localTexture != null) return localTexture;

		SkinGrabber grabber = SkinGrabber.INSTANCE;

		if (url == null) return grabber.getPossibleSkin(key).orElse(null);

		return grabber.getSkinOrDownload(key, url);
	}

	/**
	 * Uploads this skin data to the tracker for the given UUID.
	 * @param uuid the players UUID
	 */
	public void upload(UUID uuid) {
		SkinTracker.getInstance().putSynced(uuid, this);
	}

	/**
	 * Uploads this skin data to the tracker for the given player.
	 * @param player the player
	 */
	public void upload(ServerPlayer player) {
		upload(player.getUUID());
	}

	/**
	 * Uploads this skin data to the tracker for the given player texturable.
	 * @param player the player
	 */
	public void upload(PlayerSkinTexturable player) {
		upload(player.getUUID());
	}
}
