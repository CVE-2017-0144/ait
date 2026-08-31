package dev.amble.ait.data.schema.door;

import java.lang.reflect.Type;

import com.google.gson.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.data.schema.door.impl.CapsuleDoorVariant;
import dev.amble.ait.registry.impl.door.ClientDoorRegistry;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import dev.amble.lib.api.Identifiable;

@Environment(EnvType.CLIENT)
public abstract class ClientDoorSchema implements Identifiable {
    private final ResourceLocation parent;
    private final ResourceLocation id;

    protected ClientDoorSchema(ResourceLocation parent, ResourceLocation id) {
        this.parent = parent;
        this.id = id;
    }

    protected ClientDoorSchema(ResourceLocation parent) {
        this.id = parent;
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() == null)
            return false;

        ClientDoorSchema that = (ClientDoorSchema) o;

        return id.equals(that.id);
    }

    public DoorSchema parent() {
        return DoorRegistry.getInstance().get(this.parent);
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    // public abstract Identifier texture();
    // public abstract Identifier emission();
    public abstract AnimatedModel model();

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer implements JsonSerializer<ClientDoorSchema>, JsonDeserializer<ClientDoorSchema> {

        @Override
        public ClientDoorSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            ResourceLocation id;

            try {
                id = new ResourceLocation(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = CapsuleDoorVariant.REFERENCE;
            }

            return ClientDoorRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(ClientDoorSchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
