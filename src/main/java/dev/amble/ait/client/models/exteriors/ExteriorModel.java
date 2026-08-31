package dev.amble.ait.client.models.exteriors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public interface ExteriorModel extends AnimatedModel<ExteriorBlockEntity> {
    <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                    VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha);

    void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI);
}
