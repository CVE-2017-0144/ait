package dev.amble.ait.data.datapack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.control.ControlTypes;
import dev.amble.ait.data.codec.MoreCodec;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import org.joml.Vector3f;

// Example usage
/*
{
  "id": "ait:white_alnico",
  "parent": "ait:console/alnico",
  "texture": "ait:textures/console/alnico.png",
  "emission": "ait:textures/console/alnico_emission.png"
}
 */
public class DatapackConsole extends ConsoleVariantSchema implements TravelAnimationMap.Holder {
    public static final ResourceLocation EMPTY = AITMod.id("intentionally_empty");

    protected final ResourceLocation texture;
    protected final ResourceLocation emission;
    protected final ResourceLocation id;
    protected final List<Float> sonicRotation;
    protected final Vector3f sonicTranslation;
    protected final List<Float> handlesRotation;
    protected final Vector3f handlesTranslation;
    protected final ResourceLocation model;
    protected final Vec3 scale;
    protected final Vec3 offset;
    public static final Codec<DatapackConsole> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(ResourceLocation.CODEC.fieldOf("id").forGetter(ConsoleVariantSchema::id),
                    ResourceLocation.CODEC.optionalFieldOf("parent").forGetter(c -> Optional.ofNullable(c.parentId())),
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DatapackConsole::texture),
                    ResourceLocation.CODEC.optionalFieldOf("emission", EMPTY).forGetter(DatapackConsole::emission),
                    Codec.list(Codec.FLOAT).optionalFieldOf("sonic_rotation", List.of())
                            .forGetter(DatapackConsole::sonicRotation),
                    MoreCodec.VECTOR3F.optionalFieldOf("sonic_translation", new Vector3f()).forGetter(DatapackConsole::sonicTranslation),
                    Codec.list(Codec.FLOAT).optionalFieldOf("handles_rotation", List.of())
                            .forGetter(DatapackConsole::handlesRotation),
                    MoreCodec.VECTOR3F.optionalFieldOf("handles_translation", new Vector3f()).forGetter(DatapackConsole::handlesTranslation),
                    ResourceLocation.CODEC.optionalFieldOf("model").forGetter(DatapackConsole::model),
                    Vec3.CODEC.optionalFieldOf("scale", new Vec3(1, 1, 1)).forGetter(DatapackConsole::getScale),
                    Vec3.CODEC.optionalFieldOf("offset", new Vec3(0, 0, 0)).forGetter(DatapackConsole::getOffset),
                    TravelAnimationMap.CODEC.optionalFieldOf("animations", new TravelAnimationMap())
                            .forGetter(DatapackConsole::getAnimations),
                    SimpleType.CODEC.optionalFieldOf("type").forGetter(DatapackConsole::getCustomType),
                    Codec.BOOL.optionalFieldOf("isDatapack", true).forGetter(DatapackConsole::wasDatapack))
            .apply(instance, DatapackConsole::new));
    protected boolean initiallyDatapack;
    protected final TravelAnimationMap animations;

    public DatapackConsole(ResourceLocation id,
                           Optional<ResourceLocation> category,
                           ResourceLocation texture,
                           ResourceLocation emission,
                           List<Float> sonicRot,
                           Vector3f sonicTranslation,
                           List<Float> handlesRot,
                           Vector3f handlesTranslation,
                           Optional<ResourceLocation> model,
                           Vec3 scale,
                           Vec3 offset,
                           TravelAnimationMap animations,
                           Optional<SimpleType> type,
                           boolean isDatapack) {
        super(resolveParentId(category, type), id);
        this.id = id;
        this.texture = texture;
        this.emission = emission;
        this.initiallyDatapack = isDatapack;
        this.sonicRotation = sonicRot;
        this.sonicTranslation = sonicTranslation;
        this.handlesRotation = handlesRot;
        this.handlesTranslation = handlesTranslation;
        this.model = model.orElse(null);
        this.scale = scale;
        this.offset = offset;
        this.animations = animations != null ? animations : new TravelAnimationMap();
    }

    private static ResourceLocation resolveParentId(Optional<ResourceLocation> parent, Optional<SimpleType> type) {
        if (parent.isPresent()) {
            return parent.get();
        } else if (type.isPresent()) {
            type.get().register();
            return type.get().id();
        } else {
            throw new IllegalArgumentException("DatapackConsole must have a parent or a type defined");
        }
    }

    public boolean wasDatapack() {
        return this.initiallyDatapack;
    }

    public ResourceLocation texture() {
        return this.texture;
    }

    public ResourceLocation emission() {
        return this.emission;
    }

    public ResourceLocation id() {
        return this.id;
    }

    public List<Float> sonicRotation() {
        return this.sonicRotation;
    }
    public Vector3f sonicTranslation() {
        return this.sonicTranslation;
    }

    public List<Float> handlesRotation() {
        return this.handlesRotation;
    }
    public Vector3f handlesTranslation() {
        return this.handlesTranslation;
    }

    public Optional<ResourceLocation> model() {
        return Optional.ofNullable(model);
    }

    public Vec3 getScale() {
        return scale;
    }

    public Vec3 getOffset() {
        return offset;
    }

    @Override
    public TravelAnimationMap getAnimations() {
        return animations;
    }

    public Optional<SimpleType> getCustomType() {
        if (this.parent() instanceof SimpleType simpleType) {
            return Optional.of(simpleType);
        }
        return Optional.empty();
    }

    public static DatapackConsole fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static DatapackConsole fromJson(JsonObject json) {
        AtomicReference<DatapackConsole> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(var -> created.set(var.getFirst())).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack console variant: {}", err);
        });

        return created.get();
    }

    public static class SimpleType extends ConsoleTypeSchema {
        public static final Codec<SimpleType> CODEC = RecordCodecBuilder.create(instance -> instance
                .group(ResourceLocation.CODEC.fieldOf("id").forGetter(SimpleType::id),
                        Codec.STRING.fieldOf("name").forGetter(SimpleType::name),
                        ControlTypes.CODEC.listOf().fieldOf("controls").forGetter(c -> c.controls))
                .apply(instance, SimpleType::new));

        private final List<ControlTypes> controls;

        protected SimpleType(ResourceLocation id, String name, List<ControlTypes> controls) {
            super(id, name);

            this.controls = controls;
        }

        @Override
        public ControlTypes[] getControlTypes() {
            return controls.toArray(new ControlTypes[0]);
        }

        @Override
        public Component text() {
            return Component.literal(this.name());
        }

        public void register() {
            ConsoleRegistry.getInstance().register(this);
        }
    }
}
