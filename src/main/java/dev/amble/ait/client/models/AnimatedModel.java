package dev.amble.ait.client.models;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.client.tardis.ClientTardis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface AnimatedModel<T extends BlockEntity> {
    void renderWithAnimations(ClientTardis tardis, T linkableBlockEntity, ModelPart root, PoseStack matrices,
                              VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta);

    default void render(PoseStack stack, VertexConsumer buffer, int light, int overlay, int color) {
        root().render(stack, buffer, light, overlay, color);
    }

    /**
     * @return the root model part
     */
    ModelPart root();
}
