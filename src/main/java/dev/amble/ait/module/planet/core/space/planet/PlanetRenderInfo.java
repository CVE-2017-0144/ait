package dev.amble.ait.module.planet.core.space.planet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.codec.MoreCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public record PlanetRenderInfo(ResourceLocation texture, Vec3 position, Vector3f scale, Vector3f rotation, boolean clouds,
                               boolean atmosphere, Vector3f color, double radius, double suctionRadius, boolean hasRings) {
    public static final Codec<PlanetRenderInfo> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("texture").forGetter(PlanetRenderInfo::texture),
            Vec3.CODEC.fieldOf("position").forGetter(PlanetRenderInfo::position),
            MoreCodec.VECTOR3F.fieldOf("scale").forGetter(PlanetRenderInfo::scale),
            MoreCodec.VECTOR3F.fieldOf("rotation").forGetter(PlanetRenderInfo::rotation),
            Codec.BOOL.fieldOf("clouds").forGetter(PlanetRenderInfo::clouds),
            Codec.BOOL.fieldOf("atmosphere").forGetter(PlanetRenderInfo::atmosphere),
            MoreCodec.VECTOR3F.fieldOf("color").forGetter(PlanetRenderInfo::color),
            Codec.DOUBLE.fieldOf("radius").forGetter(PlanetRenderInfo::radius),
            Codec.DOUBLE.fieldOf("suction_radius").forGetter(PlanetRenderInfo::suctionRadius),
            Codec.BOOL.fieldOf("has_rings").forGetter(PlanetRenderInfo::hasRings)
    ).apply(instance, PlanetRenderInfo::new));

    @Override
    public double suctionRadius() {
        return radius + suctionRadius;
    }

    public boolean isEmpty() {
        return this == EMPTY;
    }

    public static final PlanetRenderInfo EMPTY = new PlanetRenderInfo(AITMod.id("textures/item/error.png"),
            new Vec3(0, 0, 0), new Vector3f(1, 1, 1),
            new Vector3f(0, 0, 0), false, false,
            new Vector3f(1, 1, 1), 0, 0, false);
}
