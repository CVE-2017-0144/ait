package dev.amble.ait.mixin.client.rendering;

import java.util.EnumSet;

import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LayerDefinition.class)
public class LayerDefinitionMixin {

    // our models are full of flat cubes, their four zero-area sides still cost a quad each per draw
    @Inject(method = "create", at = @At("HEAD"))
    private static void ait$dropFlatSides(MeshDefinition mesh, int width, int height, CallbackInfoReturnable<LayerDefinition> cir) {
        Class<?> caller = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).walk(frames -> frames
                .map(StackWalker.StackFrame::getDeclaringClass)
                .filter(c -> c != LayerDefinition.class)
                .findFirst().orElse(null));

        if (caller != null && caller.getName().startsWith("dev.amble."))
            ait$trim(mesh.getRoot());
    }

    @Unique
    private static void ait$trim(PartDefinition part) {
        for (CubeDefinition cube : part.cubes) {
            boolean x = cube.dimensions.x() + 2 * cube.grow.growX == 0;
            boolean y = cube.dimensions.y() + 2 * cube.grow.growY == 0;
            boolean z = cube.dimensions.z() + 2 * cube.grow.growZ == 0;

            if (!x && !y && !z)
                continue;

            EnumSet<Direction> faces = EnumSet.noneOf(Direction.class);
            faces.addAll(cube.visibleFaces);

            if (x)
                faces.removeAll(EnumSet.of(Direction.UP, Direction.DOWN, Direction.NORTH, Direction.SOUTH));
            if (y)
                faces.removeAll(EnumSet.of(Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH));
            if (z)
                faces.removeAll(EnumSet.of(Direction.UP, Direction.DOWN, Direction.EAST, Direction.WEST));

            cube.visibleFaces = faces;
        }

        for (PartDefinition child : part.children.values())
            ait$trim(child);
    }
}
