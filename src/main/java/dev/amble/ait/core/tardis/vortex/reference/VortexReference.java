package dev.amble.ait.core.tardis.vortex.reference;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.Nameable;
import dev.amble.ait.client.renderers.VortexRender;
import dev.amble.lib.api.Identifiable;

public record VortexReference(ResourceLocation id, ResourceLocation texture, String name) implements Identifiable, Nameable {
    public static final Codec<VortexReference> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(VortexReference::id),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(VortexReference::texture),
            Codec.STRING.optionalFieldOf("name", "").forGetter(VortexReference::name)
    ).apply(instance, VortexReference::new));

    public VortexReference {
        if (name.isEmpty()) {
            name = id.getPath();
        }
    }

    public VortexReference(ResourceLocation id, ResourceLocation texture) {
        this(id, texture, id.getPath());
    }

    @Override
    public Component text() {
        return Component.translatableWithFallback(this.id().toLanguageKey("vortex"), this.name());
    }

    @Environment(EnvType.CLIENT)
    public VortexRender toRender() {
        return VortexRender.getInstance(this);
    }

    public static VortexReference fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static VortexReference fromJson(JsonObject json) {
        AtomicReference<VortexReference> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(planet -> created.set(planet.getFirst())).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack vortex: {}", err);
        });

        return created.get();
    }
}
