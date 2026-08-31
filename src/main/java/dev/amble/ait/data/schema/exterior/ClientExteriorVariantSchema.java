package dev.amble.ait.data.schema.exterior;

import java.lang.reflect.Type;

import com.google.gson.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.door.ClientDoorSchema;
import dev.amble.ait.registry.impl.door.ClientDoorRegistry;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.api.Identifiable;

@Environment(EnvType.CLIENT)
public abstract class ClientExteriorVariantSchema implements Identifiable {

    private final ResourceLocation parent;
    private final ResourceLocation id;

    private ClientDoorSchema door;
    private ExteriorModel model;

    protected ClientExteriorVariantSchema(ResourceLocation parent, ResourceLocation id) {
        this.parent = parent;
        this.id = id;
    }

    protected ClientExteriorVariantSchema(ResourceLocation parent) {
        this.id = parent;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof ClientExteriorVariantSchema other && this.id.equals(other.id);
    }

    public ExteriorVariantSchema parent() {
        return ExteriorVariantRegistry.getInstance().get(this.parent);
    }

    public ResourceLocation id() {
        return id;
    }

    public abstract ResourceLocation texture();

    public abstract ResourceLocation emission();

    public abstract ExteriorModel model();

    public ExteriorModel getCachedModel() {
        return this.model != null ? this.model : (this.model = this.model());
    }

    public abstract Vector3f sonicItemTranslations();

    public abstract BiomeOverrides overrides();

    public float[] sonicItemRotations() {
        return new float[]{0f, 45f};
    }

    /**
     * The default exterior for this category
     */
    public ExteriorVariantSchema getDefaultVariant() {
        return ExteriorVariantRegistry.getInstance().get(this.parent().id()).category().getDefaultVariant();
    }

    public ClientDoorSchema getDoor() {
        if (this.door == null)
            this.door = ClientDoorRegistry.withParent(this.parent().door());

        return this.door;
    }

    public boolean hasTransparentDoors() {
        return false;
    }

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer
            implements
                JsonSerializer<ClientExteriorVariantSchema>,
                JsonDeserializer<ClientExteriorVariantSchema> {

        @Override
        public ClientExteriorVariantSchema deserialize(JsonElement json, Type typeOfT,
                JsonDeserializationContext context) throws JsonParseException {
            ResourceLocation id;

            try {
                id = ResourceLocation.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = AITMod.id("capsule_default");
            }

            return ClientExteriorVariantRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(ClientExteriorVariantSchema src, Type typeOfSrc,
                JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
