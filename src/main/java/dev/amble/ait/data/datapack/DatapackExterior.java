package dev.amble.ait.data.datapack;

import static dev.amble.ait.data.datapack.DatapackConsole.EMPTY;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.util.PortalOffsets;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.door.AnimatedDoor;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class DatapackExterior extends ExteriorVariantSchema implements AnimatedDoor, TravelAnimationMap.Holder {

    public static final ResourceLocation DEFAULT_TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            "textures/gui/tardis/desktop/missing_preview.png");

    public static final Codec<DatapackExterior> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(ResourceLocation.CODEC.fieldOf("id").forGetter(ExteriorVariantSchema::id),
                    ResourceLocation.CODEC.fieldOf("category").forGetter(ExteriorVariantSchema::categoryId),
                    ResourceLocation.CODEC.fieldOf("parent").forGetter(DatapackExterior::getParentId),
                    ResourceLocation.CODEC.fieldOf("texture").forGetter(DatapackExterior::texture),
                    ResourceLocation.CODEC.optionalFieldOf("emission", EMPTY).forGetter(DatapackExterior::emission),
                    Loyalty.CODEC.optionalFieldOf("loyalty").forGetter(DatapackExterior::requirement),
                    BiomeOverrides.CODEC.fieldOf("overrides").orElse(BiomeOverrides.EMPTY)
                            .forGetter(DatapackExterior::overrides),
                    Vec3.CODEC.optionalFieldOf("seat_translations", new Vec3(0.5, 1, 0.5)).forGetter(DatapackExterior::seatTranslations),
                    Codec.BOOL.optionalFieldOf("has_transparent_doors", false).forGetter(DatapackExterior::hasTransparentDoors),
                    ResourceLocation.CODEC.optionalFieldOf("model").forGetter(DatapackExterior::model),
                    ResourceLocation.CODEC.optionalFieldOf("door").forGetter(DatapackExterior::getDoorId),
                    PortalOffsets.CODEC.optionalFieldOf("portal_info").forGetter(ext -> Optional.ofNullable(ext.getPortalOffsets())),
                    BedrockAnimationReference.CODEC.optionalFieldOf("left_animation").forGetter(DatapackExterior::getLeftAnimation),
                    BedrockAnimationReference.CODEC.optionalFieldOf("right_animation").forGetter(DatapackExterior::getRightAnimation),
                    Vec3.CODEC.optionalFieldOf("scale", new Vec3(1, 1, 1)).forGetter(DatapackExterior::getScale),
                    TravelAnimationMap.CODEC.optionalFieldOf("animations", new TravelAnimationMap())
                            .forGetter(DatapackExterior::getAnimations)
            ).apply(instance, DatapackExterior::new)
    );

    protected final ResourceLocation parent;
    protected final ResourceLocation texture;
    protected final ResourceLocation emission;
    protected final BiomeOverrides overrides;
    protected final Vec3 seatTranslations;
    protected final boolean initiallyDatapack;
    protected final boolean hasTransparentDoors;
    protected final ResourceLocation model;
    protected final ResourceLocation doorId;
    protected final PortalOffsets portalOffsets;
    protected final BedrockAnimationReference leftAnimation;
    protected final BedrockAnimationReference rightAnimation;
    protected final Vec3 scale;
    protected final TravelAnimationMap animations;

    public DatapackExterior(ResourceLocation id, ResourceLocation category, ResourceLocation parent, ResourceLocation texture,
                            ResourceLocation emission, Optional<Loyalty> loyalty, BiomeOverrides overrides, Vec3 seatTranslations, boolean hasTransparentDoors, Optional<ResourceLocation> model, Optional<ResourceLocation> door, Optional<PortalOffsets> offsets, Optional<BedrockAnimationReference> leftAnimation, Optional<BedrockAnimationReference> rightAnimation, Vec3 scale, TravelAnimationMap animations) {
        super(category, id, loyalty);
        this.parent = parent;
        this.texture = texture;
        this.emission = emission;
        this.seatTranslations = seatTranslations;
        this.hasTransparentDoors = hasTransparentDoors;
        this.initiallyDatapack = true;
        this.overrides = overrides;
        this.model = model.orElse(null);
        this.doorId = door.orElse(null);
        this.portalOffsets = offsets.orElse(null);
        this.leftAnimation = leftAnimation.orElse(null);
        this.rightAnimation = rightAnimation.orElse(null);
        this.scale = scale;
        this.animations = animations;
    }

    public static DatapackExterior fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static DatapackExterior fromJson(JsonObject json) {
        AtomicReference<DatapackExterior> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).get().ifLeft(var -> created.set(var.getFirst())).ifRight(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack exterior variant: {}", err);
        });

        return created.get();
    }

    public ExteriorVariantSchema getParent() {
        return ExteriorVariantRegistry.getInstance().get(this.getParentId());
    }

    public ResourceLocation getParentId() {
        return this.parent;
    }

    private Optional<ResourceLocation> getDoorId() {
        return Optional.ofNullable(this.door().id());
    }

    @Override
    public DoorSchema door() {
        if (doorId == null) {
            return this.getParent().door();
        }

        return DoorRegistry.getInstance().getOrElse(doorId, this.getParent().door());
    }

    public BiomeOverrides overrides() {
        return overrides;
    }

    @Override
    public Vec3 seatTranslations() {
        return seatTranslations;
    }

    public boolean hasTransparentDoors() {
        return hasTransparentDoors;
    }

    @Override
    public VoxelShape bounding(Direction dir) {
        return this.getParent().bounding(dir);
    }

    @Override
    public boolean hasPortals() {
        if (this.getPortalOffsets() != null) {
            return this.getPortalOffsets().enabled();
        }

        return this.getParent().hasPortals();
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        if (this.getPortalOffsets() != null) {
            return this.getPortalOffsets().offset();
        }

        return this.getParent().getPortalPosition();
    }

    @Override
    public double portalWidth() {
        if (this.getPortalOffsets() != null) {
            return this.getPortalOffsets().width();
        }

        return this.getParent().portalWidth();
    }

    @Override
    public double portalHeight() {
        if (this.getPortalOffsets() != null) {
            return this.getPortalOffsets().height();
        }

        return this.getParent().portalHeight();
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

    /**
     * A possible identifier for a bedrock model in the BedrockModelRegistry.
     */
    public Optional<ResourceLocation> model() {
        return Optional.ofNullable(this.model);
    }

    public PortalOffsets getPortalOffsets() {
        return this.portalOffsets;
    }

    @Override
    public Optional<BedrockAnimationReference> getLeftAnimation() {
        return Optional.ofNullable(this.leftAnimation);
    }

    @Override
    public Optional<BedrockAnimationReference> getRightAnimation() {
        return Optional.ofNullable(this.rightAnimation);
    }

    @Override
    public Vec3 getScale() {
        return scale;
    }

    @Override
    public TravelAnimationMap getAnimations() {
        return animations;
    }
}
