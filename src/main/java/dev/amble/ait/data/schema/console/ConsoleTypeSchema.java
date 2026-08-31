package dev.amble.ait.data.schema.console;

import java.lang.reflect.Type;
import net.minecraft.ResourceLocationException;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import com.google.gson.*;
import dev.amble.ait.api.Nameable;
import dev.amble.ait.core.tardis.control.ControlTypes;
import dev.amble.ait.data.schema.exterior.category.CapsuleCategory;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.lib.api.Identifiable;

public abstract class ConsoleTypeSchema implements Identifiable, Nameable {
    private final ResourceLocation id;
    private final String name;
    private final Component text;

    protected ConsoleTypeSchema(ResourceLocation id, String name) {
        this.id = id;
        this.name = name;

        ResourceLocation translationId = this.id.withPath(path -> path.substring(path.lastIndexOf('/') + 1));
        this.text = Component.translatableWithFallback(translationId.toLanguageKey("console"), this.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof ConsoleTypeSchema schema && id.equals(schema.id);
    }

    @Override
    public ResourceLocation id() {
        return this.id;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Component text() {
        return this.text;
    }

    @Override
    public String toString() {
        return this.name();
    }

    // @TODO protocol abstraction with numbered letters

    public abstract ControlTypes[] getControlTypes(); // fixme this kinda sucks idk

    /**
     * The default console for this category
     */
    public ConsoleVariantSchema getDefaultVariant() {
        return ConsoleVariantRegistry.withParent(this).get(0);
    }

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer implements JsonSerializer<ConsoleTypeSchema>, JsonDeserializer<ConsoleTypeSchema> {

        @Override
        public ConsoleTypeSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            ResourceLocation id;

            try {
                id = ResourceLocation.parse(json.getAsJsonPrimitive().getAsString());
            } catch (ResourceLocationException e) {
                id = CapsuleCategory.REFERENCE;
            }

            return ConsoleRegistry.getInstance().get(id);
        }

        @Override
        public JsonElement serialize(ConsoleTypeSchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
