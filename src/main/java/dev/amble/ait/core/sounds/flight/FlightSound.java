package dev.amble.ait.core.sounds.flight;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.Nameable;
import dev.amble.ait.core.AITSounds;
import dev.amble.lib.api.Identifiable;

public record FlightSound(ResourceLocation id, ResourceLocation soundId, int length, String name) implements Identifiable, Nameable {

    public static final Codec<FlightSound> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    ResourceLocation.CODEC.fieldOf("id").forGetter(FlightSound::id),
                    ResourceLocation.CODEC.fieldOf("sound").forGetter(FlightSound::soundId),
                    Codec.INT.fieldOf("length").forGetter(FlightSound::length),
                    Codec.STRING.optionalFieldOf("name", "").forGetter(FlightSound::name)
            ).apply(instance, FlightSound::new)
    );

    public FlightSound {
        if (name.isEmpty()) {
            name = id.getPath();
        }
    }

    @Override
    public ResourceLocation id() {
        return this.id;
    }

    @Override
    public Component text() {
        return Component.translatableWithFallback(this.id().toLanguageKey("flight"), this.name());
    }

    public SoundEvent sound() {
        SoundEvent sfx = BuiltInRegistries.SOUND_EVENT.get(this.soundId());

        if (sfx == null) {
            AITMod.LOGGER.error("Unknown sound event: {} in flight sfx {}", this.soundId(), this.id());
            sfx = AITSounds.ERROR;
        }

        return sfx;
    }

    public static FlightSound fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static FlightSound fromJson(JsonObject json) {
        AtomicReference<FlightSound> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).get().ifLeft(var -> created.set(var.getFirst())).ifRight(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack flight sfx: {}", err);
        });

        return created.get();
    }
}
