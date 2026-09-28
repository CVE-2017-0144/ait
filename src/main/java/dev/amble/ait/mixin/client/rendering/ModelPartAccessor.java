package dev.amble.ait.mixin.client.rendering;

import java.util.List;
import java.util.Map;
import net.minecraft.client.model.geom.ModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Reads the cuboid list a part owns.
 *
 * <p>Pure accessor, no injected behaviour. The list is needed to work out which parts of a model can
 * actually sample a lit texel of an emission texture, which is the only way to tell that submitting
 * them to the emission layer is wasted work.
 */
@Mixin(ModelPart.class)
public interface ModelPartAccessor {

    @Accessor("cubes")
    List<ModelPart.Cube> ait$cuboids();

    /**
     * The direct children, which {@code getAllParts()} cannot give: it flattens the whole subtree, and
     * telling a maximal unlit subtree from its parent needs the tree shape.
     */
    @Accessor("children")
    Map<String, ModelPart> ait$children();
}
