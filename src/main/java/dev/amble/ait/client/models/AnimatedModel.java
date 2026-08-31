package dev.amble.ait.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.client.tardis.ClientTardis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface AnimatedModel<T extends BlockEntity> {
    void renderWithAnimations(ClientTardis tardis, T linkableBlockEntity, ModelPart root, PoseStack matrices,
                              VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta);

    default void render(PoseStack stack, VertexConsumer buffer, int maxLightCoordinate, int defaultUv, float base, float base1, float base2, float v) {
        ModelPart root = root();
        root.render(stack, buffer, maxLightCoordinate, defaultUv, base, base1, base2, v);
    }

    /**
     * @return the root model part
     */
    ModelPart root();
}
