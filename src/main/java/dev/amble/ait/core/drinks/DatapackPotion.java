package dev.amble.ait.core.drinks;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import com.google.common.collect.ComparisonChain;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.lib.api.Identifiable;

public record DatapackPotion(ResourceLocation id, int duration, int amplifier, Optional<Boolean> ambient,
                             Optional<Boolean> showParticles, Optional<Boolean> showIcon) implements Identifiable,
        Comparable<MobEffectInstance> {
    public static final Codec<DatapackPotion> CODEC = ExtraCodecs.catchDecoderException(RecordCodecBuilder.create(instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(DatapackPotion::id),
                    Codec.INT.fieldOf("duration").forGetter(DatapackPotion::duration),
                    Codec.INT.fieldOf("amplifier").forGetter(DatapackPotion::amplifier),
                    Codec.BOOL.optionalFieldOf("ambient").forGetter(DatapackPotion::ambient),
                    Codec.BOOL.optionalFieldOf("show_particles").forGetter(DatapackPotion::showParticles),
                    Codec.BOOL.optionalFieldOf("show_icon").forGetter(DatapackPotion::showIcon))
            .apply(instance, DatapackPotion::new)));

    @Override
    public ResourceLocation id() {
        return this.id;
    }

    public DatapackPotion(ResourceLocation id) {
        this(id, 0, 0, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public DatapackPotion(ResourceLocation id, int duration, int amplifier) {
        this(id, duration, amplifier, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public DatapackPotion(ResourceLocation id, int duration, int amplifier, boolean ambient) {
        this(id, duration, amplifier, Optional.of(ambient), Optional.empty(), Optional.empty());
    }

    public DatapackPotion(ResourceLocation id, int duration, int amplifier, boolean ambient, boolean showParticles) {
        this(id, duration, amplifier, Optional.of(ambient), Optional.of(showParticles), Optional.empty());
    }

    public DatapackPotion(ResourceLocation id, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        this(id, duration, amplifier, Optional.of(ambient), Optional.of(showParticles), Optional.of(showIcon));
    }

    public DatapackPotion(MobEffect statusEffect) {
        this(getEffect(statusEffect), 0, 0, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public DatapackPotion(MobEffect statusEffect, int duration, int amplifier) {
        this(getEffect(statusEffect), duration, amplifier, Optional.empty(), Optional.empty(), Optional.empty());
    }

    public DatapackPotion(MobEffect statusEffect, int duration, int amplifier, boolean ambient) {
        this(getEffect(statusEffect), duration, amplifier, Optional.of(ambient), Optional.empty(), Optional.empty());
    }

    public DatapackPotion(MobEffect statusEffect, int duration, int amplifier, boolean ambient, boolean showParticles) {
        this(getEffect(statusEffect), duration, amplifier, Optional.of(ambient), Optional.of(showParticles), Optional.empty());
    }

    public DatapackPotion(MobEffect statusEffect, int duration, int amplifier, boolean ambient, boolean showParticles, boolean showIcon) {
        this(getEffect(statusEffect), duration, amplifier, Optional.of(ambient), Optional.of(showParticles), Optional.of(showIcon));
    }

    @Override
    public String toString() {
        return "DatapackPotion{" +
                "id=" + id +
                "duration=" + duration +
                "amplifier=" + amplifier +
                "ambient=" + ambient +
                "show_particles=" + showParticles +
                "show_icon=" + showIcon +
                '}';
    }

    public static DatapackPotion fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static DatapackPotion fromJson(JsonObject json) {
        AtomicReference<DatapackPotion> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(var -> created.set(var.getFirst())).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack potion: {}", err);
        });

        return created.get();
    }

    @Override
    public int compareTo(MobEffectInstance statusEffectInstance) {
        int i = 32147;
        if (this.getDuration() > 32147 && statusEffectInstance.getDuration() > 32147 || this.isAmbient() && statusEffectInstance.isAmbient()) {
            return ComparisonChain.start().compare(this.isAmbient(),
                    statusEffectInstance.isAmbient()).compare(this.getEffectType().value().getColor(),
                    statusEffectInstance.getEffect().value().getColor()).result();
        }
        return ComparisonChain.start().compareFalseFirst(this.isAmbient(),
                statusEffectInstance.isAmbient()).compareFalseFirst(this.isInfinite(),
                statusEffectInstance.isInfiniteDuration()).compare(this.getDuration(),
                statusEffectInstance.getDuration()).compare(this.getEffectType().value().getColor(),
                statusEffectInstance.getEffect().value().getColor()).result();
    }

    public int getDuration() {
        return this.duration();
    }

    public boolean isInfinite() {
        return this.duration() >= 32147;
    }

    public int getAmplifier() {
        return this.amplifier();
    }

    public boolean isAmbient() {
        return this.ambient().orElse(false);
    }

    public Holder<MobEffect> getEffectType() {
        return BuiltInRegistries.MOB_EFFECT.getHolder(this.id()).orElse(null);
    }

    public static ResourceLocation getEffect(MobEffect statusEffect) {
        return BuiltInRegistries.MOB_EFFECT.getKey(statusEffect);
    }

    public MobEffectInstance getInstance() {
        Holder<MobEffect> effect1 = this.getEffectType();
        if (effect1 == null) return null;
        return new MobEffectInstance(effect1,
                this.duration(), this.amplifier(), this.ambient().orElse(false),
                this.showParticles().orElse(true), this.showIcon().orElse(false));
    }
}
