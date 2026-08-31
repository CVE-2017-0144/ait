package dev.amble.ait.module.planet.core.space.planet;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.module.planet.core.item.SpacesuitItem;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.util.ServerLifecycleHooks;

public record Planet(ResourceLocation dimension, float gravity, boolean hasOxygen, boolean hasLandableSurface, int temperature,
                     PlanetRenderInfo render, PlanetTransition transition) implements Identifiable {
    public static final Codec<Planet> CODEC = ExtraCodecs.catchDecoderException(RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("dimension").forGetter(Planet::dimension),
            Codec.FLOAT.optionalFieldOf("gravity", -1f).forGetter(Planet::gravity),
            Codec.BOOL.fieldOf("has_oxygen").forGetter(Planet::hasOxygen),
            Codec.BOOL.fieldOf("has_landable_surface").forGetter(Planet::hasLandableSurface),
            Codec.INT.optionalFieldOf("temperature", 288).forGetter(Planet::temperature),
            PlanetRenderInfo.CODEC.optionalFieldOf("render", PlanetRenderInfo.EMPTY).forGetter(Planet::render),
            PlanetTransition.CODEC.optionalFieldOf("transition", PlanetTransition.EMPTY).forGetter(Planet::transition)
    ).apply(instance, Planet::new)));

    public static boolean hasFullSuit(LivingEntity entity) {
        return entity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpacesuitItem
                && entity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpacesuitItem
                && entity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpacesuitItem
                && entity.getItemBySlot(EquipmentSlot.FEET).getItem() instanceof SpacesuitItem;
    }

    public static double getOxygenInTank(LivingEntity entity) {
        ItemStack chestplate = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (chestplate.getItem() instanceof SpacesuitItem) {
            return ItemNbt.get(chestplate).getDouble(SpacesuitItem.OXYGEN_KEY);
        }
        return 0.0D;
    }

    public static boolean hasOxygenInTank(LivingEntity entity) {
        return Planet.getOxygenInTank(entity) > 0.0D;
    }

    public static Planet fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static Planet fromJson(JsonObject json) {
        AtomicReference<Planet> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(planet -> created.set(planet.getFirst())).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack planet: {}", err);
        });

        return created.get();
    }

    @Override
    public ResourceLocation id() {
        return this.dimension();
    }

    // Use Celsius since it's more accurate in terms of water temperature
    public float celsius() {
        return this.temperature() - 273.15f;
    }

    // Temperature is in Kelvin because SCIENCE BITCH
    public float kelvin() {
        return this.temperature();
    }

    // Celsius -> Fahrenheit conversion isn't always the most accurate but oh well cope harder I guess
    public float fahrenheit() {
        return celsius() * 1.8f + 32f;
    }

    public boolean isFreezing() {
        return this.celsius() <= 0;
    }

    public boolean zeroGravity() {
        return this.gravity() == 0.8f; // exploding head emoji
    }

    public boolean hasGravityModifier() {
        return this.gravity() >= 0;
    }

    public boolean hasNoFallDamage() {
        return this.hasGravityModifier() && this.gravity() < 1 && this.gravity() != 0;
    }

    public ServerLevel toWorld() {
        return ServerLifecycleHooks.get().getLevel(ResourceKey.create(Registries.DIMENSION, this.dimension));
    }

    public Planet with(ResourceLocation dimension) {
        return new Planet(dimension, this.gravity, this.hasOxygen, this.hasLandableSurface, this.temperature, this.render, this.transition);
    }
}
