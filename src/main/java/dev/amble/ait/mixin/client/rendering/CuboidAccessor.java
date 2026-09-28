package dev.amble.ait.mixin.client.rendering;

import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Reads the quads a cuboid is built from, for their texture coordinates.
 *
 * <p>Pure accessor, no injected behaviour. {@code Polygon.vertices} is already public; only the array of
 * quads itself is not.
 */
@Mixin(ModelPart.Cube.class)
public interface CuboidAccessor {

    @Accessor("polygons")
    ModelPart.Polygon[] ait$sides();
}
