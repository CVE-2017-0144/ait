package dev.amble.ait.core.tardis.control.sound;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.registry.impl.ControlRegistry;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.amble.lib.api.Identifiable;

/**
 * Represents a sound that is played when a control is used
 * @param controlId The identifier of the control that this sound is for
 * @param consoleId The identifier of the console that this sound is for
 * @param successId The identifier of the sound that is played when the control is successful
 * @param altId The identifier of the sound that is played when the control fails OR a value is switched
 * @see Control
 * @see ConsoleTypeSchema
 * @author duzo
 */
public record ControlSound(ResourceLocation controlId, ResourceLocation consoleId, ResourceLocation successId, ResourceLocation altId) implements Identifiable {
    public static final Codec<ControlSound> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("control").forGetter(ControlSound::controlId),
            ResourceLocation.CODEC.fieldOf("console").forGetter(ControlSound::consoleId),
            ResourceLocation.CODEC.fieldOf("success_sound").forGetter(ControlSound::successId),
            ResourceLocation.CODEC.optionalFieldOf("alt_sound", SoundEvents.EMPTY.getLocation()).forGetter(ControlSound::altId)
    ).apply(instance, ControlSound::new));

    @Override
    public ResourceLocation id() {
        return mergeIdentifiers(controlId, consoleId);
    }

    public ConsoleTypeSchema console() {
        return ConsoleRegistry.getInstance().get(this.consoleId());
    }
    public Control control() {
        return ControlRegistry.REGISTRY.get(this.controlId());
    }
    public SoundEvent sound(Control.Result result) {
        return !result.isAltSound() ? this.successSound() : this.altSound();
    }

    public SoundEvent successSound() {
        SoundEvent sfx = BuiltInRegistries.SOUND_EVENT.get(this.successId());

        if (sfx == null) {
            AITMod.LOGGER.error("Unknown success sound event: {} in control sfx {}", this.successId(), this.id());
            sfx = AITSounds.ERROR;
        }

        return sfx;
    }
    public SoundEvent altSound() {
        SoundEvent sfx = BuiltInRegistries.SOUND_EVENT.get(this.altId());

        if (sfx == null || this.altId() == SoundEvents.EMPTY.getLocation()) {
            AITMod.LOGGER.error("Unknown alt sound event: {} in control sfx {}", this.altId(), this.id());
            sfx = successSound();
        }

        return sfx;
    }

    public static ControlSound forFallback(ResourceLocation controlId, SoundEvent success, SoundEvent alt) {
        return new ControlSound(controlId, AITMod.id("fallback"), success.getLocation(), alt.getLocation());
    }

    /**
     * Merges the two identifiers into one
     * Example
     * controlId - ait:monitor
     * consoleId - ait:hartnell
     * return - ait:hartnell/ait/monitor
     * @param controlId id of the control
     * @param consoleId id of the console
     * @return Merged identifier
     */
    public static ResourceLocation mergeIdentifiers(ResourceLocation controlId, ResourceLocation consoleId) {
        return ResourceLocation.tryBuild(consoleId.getNamespace(), consoleId.getPath() + "/" + controlId.getNamespace() + "/" + controlId.getPath());
    }

    public static ControlSound fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static ControlSound fromJson(JsonObject json) {
        AtomicReference<ControlSound> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(var -> created.set(var.getFirst())).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack console variant: {}", err);
        });

        return created.get();
    }
}
