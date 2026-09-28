package dev.amble.ait.data.schema.door;

import java.util.Optional;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.DoorHandler;
import dev.amble.ait.data.schema.AnimatedFeature;
import dev.amble.lib.client.bedrock.BedrockAnimationReference;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.phys.Vec3;

public interface AnimatedDoor extends AnimatedFeature {
    default Optional<BedrockAnimationReference> getLeftAnimation() {
        return Optional.empty();
    }

    default Optional<BedrockAnimationReference> getRightAnimation() {
        return Optional.empty();
    }

    default Vec3 getScale() {
        return new Vec3(1, 1, 1);
    }

    default Vec3 getOffset() {
        return Vec3.ZERO;
    }

    @OnlyIn(Dist.CLIENT)
    default void runAnimations(ModelPart root, PoseStack matrices, float tickDelta, ClientTardis tardis) {
        DoorHandler doors = tardis.door();

        Vec3 offset = this.getOffset().scale(-1);
        matrices.translate(offset.x, offset.y, offset.z);

        Vec3 scale = this.getScale();
        matrices.scale((float) scale.x, (float) scale.y, (float) scale.z);

        matrices.pushPose();
        float leftProgress = doors.getLeftRot();
        float rightProgress = doors.getRightRot();

        if (!AITModClient.CONFIG.animateDoors) {
            leftProgress = doors.isLeftOpen() ? 1 : 0;
            rightProgress = doors.isRightOpen() ? 1 : 0;
        }

        float leftDelta;
        if (leftProgress == 1 || leftProgress == 0) {
            leftDelta = 0;
        } else {
            leftDelta = tickDelta / 10F;
        }

        float rightDelta;
        if (rightProgress == 1 || rightProgress == 0) {
            rightDelta = 0;
        } else {
            rightDelta = tickDelta / 10F;
        }

        float finalRightProgress = rightProgress - 0.001F;
        float finalLeftProgress = leftProgress - 0.001F;
        this.getLeftAnimation().flatMap(BedrockAnimationReference::get).ifPresent(anim -> anim.apply(root, (int) (finalLeftProgress * anim.animationLength * 20), leftDelta));
        this.getRightAnimation().flatMap(BedrockAnimationReference::get).ifPresent(anim -> anim.apply(root, (int) (finalRightProgress * anim.animationLength * 20), rightDelta));
        matrices.popPose();
    }
}
