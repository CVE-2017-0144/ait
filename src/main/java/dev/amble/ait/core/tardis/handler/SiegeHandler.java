package dev.amble.ait.core.tardis.handler;

import java.util.Objects;
import java.util.UUID;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.item.SiegeTardisItem;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.data.properties.Property;
import dev.amble.ait.data.properties.Value;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.lib.platform.lifecycle.ServerConnectionEvents;

public class SiegeHandler extends KeyedTardisComponent implements TardisTickable {

    public static final ResourceLocation DEFAULT_TEXTURRE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/exteriors/siege_mode/siege_mode.png");
    public static final ResourceLocation BRICK_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/exteriors/siege_mode/siege_mode_brick.png");
    public static final ResourceLocation COMPANION_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/exteriors/siege_mode/companion_cube.png");
    public static final ResourceLocation APERTURE_TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/exteriors/siege_mode/weighted_cube.png");

    private static final Property<UUID> HELD_KEY = new Property<>(Property.UUID, "siege_held_uuid");
    private static final Property<ResourceLocation> TEXTURE = new Property<>(Property.IDENTIFIER, "texture", DEFAULT_TEXTURRE);

    private static final BoolProperty ACTIVE = new BoolProperty("siege_mode", false);

    private final Value<UUID> heldKey = HELD_KEY.create(this);
    private final BoolValue active = ACTIVE.create(this);
    private final Value<ResourceLocation> texture = TEXTURE.create(this);

    private int siegeTime;

    public SiegeHandler() {
        super(Id.SIEGE);
    }

    static {
        TardisEvents.DEMAT.register(tardis -> tardis.siege().isActive() ? TardisEvents.Interaction.FAIL : TardisEvents.Interaction.PASS);

        ServerConnectionEvents.DISCONNECT.register((player, server) -> {

            ServerTardisManager.getInstance().forEach(tardis -> {
                if (!tardis.siege().isActive())
                    return;

                if (!Objects.equals(tardis.siege().getHeldPlayerUUID(), player.getUUID()))
                    return;

                for (ItemStack itemStack : player.getInventory().items) {
                    if (itemStack.is(AITItems.SIEGE_ITEM)) {
                        if (tardis.getUuid().equals(SiegeTardisItem.getTardisIdStatic(itemStack))) {
                            player.getInventory().setItem(player.getInventory().findSlotMatchingItem(itemStack), Items.AIR.getDefaultInstance());
                        }
                    }
                }
                SiegeTardisItem.placeTardis(tardis, SiegeTardisItem.fromEntity(player));
            });
        });
    }

    @Override
    public void onLoaded() {
        active.of(this, ACTIVE);
        heldKey.of(this, HELD_KEY);
        texture.of(this, TEXTURE);

        // fix old data using new UUID(0, 0) instead of null.
        UUID held = this.getHeldPlayerUUID();

        if (held != null && held.getMostSignificantBits() == 0 && held.getLeastSignificantBits() == 0)
            this.setSiegeBeingHeld(null);
    }

    public boolean isActive() {
        return active.get();
    }

    public boolean isSiegeBeingHeld() {
        return this.isActive() && heldKey.get() != null;
    }

    public UUID getHeldPlayerUUID() {
        return heldKey.get();
    }

    public void setSiegeBeingHeld(UUID playerId) {
        if (playerId != null) {
            this.tardis.door().closeDoors();
            this.tardis.door().setLocked(true);
            this.tardis.alarm().enable();
        }

        this.heldKey.set(playerId);
    }

    public void setActive(boolean siege) {
        if (this.tardis.getFuel() <= (0.01 * FuelHandler.TARDIS_MAX_FUEL))
            return; // The required amount of fuel to enable/disable siege mode

        SoundEvent sound;

        if (siege) {
            sound = AITSounds.SIEGE_ENABLE;
            this.tardis.door().closeDoors();
            this.tardis.door().setLocked(true);
            this.tardis.door().setDeadlocked(true);

            this.tardis.fuel().disablePower();

            TardisUtil.giveEffectToInteriorPlayers(this.tardis.asServer(),
                    new MobEffectInstance(MobEffects.CONFUSION, 100, 0, false, false));
        } else {
            sound = AITSounds.SIEGE_DISABLE;
            this.tardis.door().setDeadlocked(false);
            this.tardis.door().setLocked(false);

            this.tardis.alarm().disable();

            if (this.tardis.getExterior().findExteriorBlock().isEmpty()) {
                this.tardis.travel().placeExterior(false);
            }

            this.siegeTime = 0;
        }

        tardis.getDesktop().playSoundAtEveryConsole(sound, SoundSource.BLOCKS, 3f, 1f);

        this.tardis.removeFuel(0.01 * FuelHandler.TARDIS_MAX_FUEL * this.tardis.travel().instability());
        this.active.set(siege);

        TardisEvents.TOGGLE_SIEGE.invoker().onSiege(this.tardis, siege);
    }

    @Override
    public void tick(MinecraftServer server) {
        if (!this.active.get())
            return;

        this.siegeTime += 1;

        if (server.getTickCount() % 10 == 0)
            return;

        boolean freeze = this.siegeTime > 60 * 20 && !this.isSiegeBeingHeld()
                && !this.tardis.subsystems().lifeSupport().isEnabled();

        this.tardis.asServer().world().players().forEach(player -> {
            if (!player.isAlive() || !player.canFreeze())
                return;

            if (freeze) {
                this.freeze(player);
            } else {
                this.unfreeze(player);
            }
        });
    }

    private void freeze(ServerPlayer player) {
        int m = player.getTicksFrozen();
        if (m < 0) player.setTicksFrozen(5);
        player.setTicksFrozen(Math.min(player.getTicksRequiredToFreeze(), m + 5));
    }

    private void unfreeze(ServerPlayer player) {
        if (player.getTicksFrozen() > player.getTicksRequiredToFreeze())
            player.setTicksFrozen(0);
    }

    public Value<ResourceLocation> texture() {
        return texture;
    }
}
