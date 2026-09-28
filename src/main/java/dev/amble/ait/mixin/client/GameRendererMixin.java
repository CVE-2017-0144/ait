package dev.amble.ait.mixin.client;

import dev.amble.ait.core.blocks.DoorBlock;
import dev.amble.ait.core.blocks.ExteriorBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final Minecraft minecraft;

    @ModifyVariable(method = "pick(Lnet/minecraft/world/entity/Entity;DDF)Lnet/minecraft/world/phys/HitResult;",
            at = @At("STORE"), ordinal = 0)
    private HitResult ait$targetUpperHalf(HitResult target, Entity camera, double blockRange, double entityRange, float tickDelta) {
        Level world = this.minecraft.level;

        Vec3 start = camera.getEyePosition(tickDelta);
        Vec3 end = start.add(camera.getViewVector(tickDelta).scale(blockRange));
        BlockPos.MutableBlockPos below = new BlockPos.MutableBlockPos();

        BlockHitResult hit = BlockGetter.traverseBlocks(start, end, null, (ctx, pos) -> {
            below.setWithOffset(pos, Direction.DOWN);
            BlockState state = world.getBlockState(below);

            if (!(state.getBlock() instanceof DoorBlock) && !(state.getBlock() instanceof ExteriorBlock))
                return null;

            return state.getShape(world, below, CollisionContext.of(camera)).clip(start, end, below);
        }, ctx -> null);

        if (hit == null || target.getLocation().distanceToSqr(start) < hit.getLocation().distanceToSqr(start))
            return target;

        BlockPos pos = hit.getBlockPos().immutable();
        Vec3 at = hit.getLocation();

        return new BlockHitResult(new Vec3(at.x, Math.min(at.y, pos.getY() + 1), at.z),
                hit.getDirection(), pos, hit.isInside());
    }
}
