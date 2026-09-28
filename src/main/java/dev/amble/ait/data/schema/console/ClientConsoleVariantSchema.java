package dev.amble.ait.data.schema.console;

import java.lang.reflect.Type;

import com.google.gson.*;
import dev.amble.ait.client.models.consoles.ConsoleModel;
import dev.amble.ait.data.schema.console.variant.hartnell.HartnellVariant;
import dev.amble.ait.registry.impl.console.variant.ClientConsoleVariantRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.lib.api.Identifiable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ResourceLocationException;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public abstract class ClientConsoleVariantSchema implements Identifiable {

    private final ResourceLocation parent;
    private final ResourceLocation id;

    private ConsoleModel model;

    protected ClientConsoleVariantSchema(ResourceLocation parent, ResourceLocation id) {
        this.parent = parent;
        this.id = id;
    }

    protected ClientConsoleVariantSchema(ResourceLocation parent) {
        this.id = parent;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof ClientConsoleVariantSchema other && this.id.equals(other.id);
    }

    public ConsoleVariantSchema parent() {
        return ConsoleVariantRegistry.getInstance().get(this.parent);
    }

    public ResourceLocation id() {
        return id;
    }

    public abstract ResourceLocation texture();

    public abstract ResourceLocation emission();

    /**
     * The layer a console generator draws this variant's hologram on.
     *
     * <p>Overridable so a variant can pick its own rather than have the renderer special case it.
     * The hologram is drawn at partial alpha, so this is a translucent layer by default.
     */
    public RenderType hologramLayer(ResourceLocation texture) {
        return RenderType.entityTranslucentCull(texture);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract ConsoleModel model();

    public ConsoleModel getCachedModel() {
        return this.model != null ? this.model : (this.model = this.model());
    }

    public static Object serializer() {
        return new Serializer();
    }

    public Vector3f sonicItemTranslations() {
        return ConsoleVariantSchema.DEFAULT_SONIC_POS;
    }

    public float[] sonicItemRotations() {
        return ConsoleVariantSchema.DEFAULT_SONIC_ROTATION;
    }

    public Vector3f handlesTranslations() {
        return ConsoleVariantSchema.DEFAULT_HANDLES_POS;
    }

    public float[] handlesRotations() {
        return ConsoleVariantSchema.DEFAULT_HANDLES_ROTATION;
    }

    private static class Serializer
            implements
                JsonSerializer<ClientConsoleVariantSchema>,
                JsonDeserializer<ClientConsoleVariantSchema> {

        @Override
        public ClientConsoleVariantSchema deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation id;

            try {
                id = ResourceLocation.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = HartnellVariant.REFERENCE;
            }

            return ClientConsoleVariantRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(ClientConsoleVariantSchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
