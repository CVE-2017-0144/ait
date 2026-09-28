package dev.amble.ait.data.schema.door;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.phys.Vec3;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.util.PortalOffsets;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import org.jetbrains.annotations.Nullable;

public class DatapackDoor extends DoorSchema implements AnimatedDoor {
    public static final Codec<DatapackDoor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(DoorSchema::id),
            ResourceLocation.CODEC.fieldOf("open_sound").forGetter(DatapackDoor::getOpenSoundId),
            ResourceLocation.CODEC.fieldOf("close_sound").forGetter(DatapackDoor::getCloseSoundId),
            ResourceLocation.CODEC.fieldOf("model").forGetter(DatapackDoor::getModelId),
            Codec.BOOL.fieldOf("is_double").forGetter(DoorSchema::isDouble),
            PortalOffsets.CODEC.optionalFieldOf("portal_info").forGetter(door -> Optional.ofNullable(door.getPortalOffsets())),
            BedrockAnimationReference.CODEC.optionalFieldOf("left_animation").forGetter(DatapackDoor::getLeftAnimation),
            BedrockAnimationReference.CODEC.optionalFieldOf("right_animation").forGetter(DatapackDoor::getRightAnimation),
            Vec3.CODEC.optionalFieldOf("scale", new Vec3(1, 1, 1)).forGetter(DatapackDoor::getScale),
            Vec3.CODEC.optionalFieldOf("offset", Vec3.ZERO).forGetter(DatapackDoor::getOffset),
            Codec.BOOL.optionalFieldOf("isDatapack", true).forGetter(DatapackDoor::wasDatapack)
        ).apply(instance, DatapackDoor::new)
    );

    protected final ResourceLocation openSound;
    protected final ResourceLocation closeSound;
    protected final ResourceLocation model;
    protected final boolean isDouble;
    protected final PortalOffsets portalOffsets;
    protected final BedrockAnimationReference leftAnimation;
    protected final BedrockAnimationReference rightAnimation;
    protected final Vec3 scale;
    protected final Vec3 offset;
    protected final boolean initiallyDatapack;

    public DatapackDoor(ResourceLocation id, ResourceLocation openSound, ResourceLocation closeSound, ResourceLocation model, boolean isDouble, Optional<PortalOffsets> portalOffsets, Optional<BedrockAnimationReference> leftAnimation, Optional<BedrockAnimationReference> rightAnimation, Vec3 scale, Vec3 offset, boolean initiallyDatapack) {
        super(id);

        this.openSound = openSound;
        this.closeSound = closeSound;
        this.model = model;
        this.isDouble = isDouble;
        this.portalOffsets = portalOffsets.orElse(null);
        this.leftAnimation = leftAnimation.orElse(null);
        this.rightAnimation = rightAnimation.orElse(null);
        this.scale = scale;
        this.offset = offset;
        this.initiallyDatapack = initiallyDatapack;
    }

    @Override
    public boolean isDouble() {
        return isDouble;
    }

    @Override
    public SoundEvent openSound() {
        return SoundEvent.createVariableRangeEvent(getOpenSoundId());
    }

    @Override
    public SoundEvent closeSound() {
        return SoundEvent.createVariableRangeEvent(getCloseSoundId());
    }

    @Override
    public Optional<BedrockAnimationReference> getLeftAnimation() {
        return Optional.ofNullable(leftAnimation);
    }

    @Override
    public Optional<BedrockAnimationReference> getRightAnimation() {
        return Optional.ofNullable(rightAnimation);
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return this.portalOffsets != null ? this.portalOffsets.offset() : null;
    }

    public ResourceLocation getOpenSoundId() {
        return openSound;
    }

    public ResourceLocation getCloseSoundId() {
        return closeSound;
    }

    public PortalOffsets getPortalOffsets() {
        return portalOffsets;
    }

    public boolean wasDatapack() {
        return initiallyDatapack; // Datapack doors are always considered as such
    }

    public ResourceLocation getModelId() {
        return model;
    }

    @Override
    public Vec3 getScale() {
        return scale;
    }

    @Override
    public Vec3 getOffset() {
        return offset;
    }

    public static DatapackDoor fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static DatapackDoor fromJson(JsonObject json) {
        AtomicReference<DatapackDoor> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(recipe -> {
            created.set(recipe.getFirst());
        }).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack door type: {}", err);
        });

        return created.get();
    }

}
