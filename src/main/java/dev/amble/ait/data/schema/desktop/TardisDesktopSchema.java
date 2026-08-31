package dev.amble.ait.data.schema.desktop;

import java.lang.reflect.Type;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import com.google.gson.*;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.BasicSchema;
import dev.amble.ait.data.schema.desktop.textures.DesktopPreviewTexture;
import dev.amble.ait.registry.impl.DesktopRegistry;
import dev.amble.lib.register.unlockable.Unlockable;

public abstract class TardisDesktopSchema extends BasicSchema implements Unlockable {

    private final ResourceLocation id;

    private final DesktopPreviewTexture preview;
    private final Loyalty loyalty;

    protected TardisDesktopSchema(ResourceLocation id, DesktopPreviewTexture texture, Optional<Loyalty> loyalty) {
        super("desktop");
        this.id = id;

        this.preview = texture;
        this.loyalty = loyalty.orElse(null);
    }

    protected TardisDesktopSchema(ResourceLocation id, DesktopPreviewTexture texture, Loyalty loyalty) {
        this(id, texture, Optional.of(loyalty));
    }

    protected TardisDesktopSchema(ResourceLocation id, DesktopPreviewTexture texture) {
        this(id, texture, Optional.empty());
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public Optional<Loyalty> requirement() {
        return Optional.ofNullable(loyalty);
    }

    @Override
    public UnlockType unlockType() {
        return UnlockType.DESKTOP;
    }

    public DesktopPreviewTexture previewTexture() {
        return this.preview;
    }

    public Optional<StructureTemplate> findTemplate() {
        return WorldUtil.getOverworld().getStructureManager()
                .get(this.getStructureLocation());
    }

    private ResourceLocation getStructureLocation() {
        ResourceLocation id = this.id();

        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "interiors/" + id.getPath());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o instanceof TardisDesktopSchema that)
            return id.equals(that.id);

        return false;
    }

    public static Object serializer() {
        return new Serializer();
    }

    private static class Serializer
            implements
                JsonSerializer<TardisDesktopSchema>,
                JsonDeserializer<TardisDesktopSchema> {

        @Override
        public TardisDesktopSchema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return DesktopRegistry.getInstance().get(ResourceLocation.parse(json.getAsJsonPrimitive().getAsString()));
        }

        @Override
        public JsonElement serialize(TardisDesktopSchema src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.id().toString());
        }
    }
}
