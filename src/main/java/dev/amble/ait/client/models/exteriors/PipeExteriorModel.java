package dev.amble.ait.client.models.exteriors;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;

public class PipeExteriorModel extends SimpleExteriorModel {
    private final ModelPart tardis;
    public PipeExteriorModel(ModelPart root) {
        this.tardis = root.getChild("tardis");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition tardis = modelPartData.addOrReplaceChild("tardis", CubeListBuilder.create().texOffs(0, 0).addBox(-7.5F, -14.5F, -7.0F, 15.0F, 7.0F, 15.0F, new CubeDeformation(0.0F))
        .texOffs(0, 22).addBox(-6.5F, -8.0F, -6.0F, 13.0F, 8.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -22.5F, -0.5F, 0.0F, 0.0F, -3.1416F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        matrices.pushPose();
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        tardis.render(matrices, vertexConsumer, light, overlay, color);
        matrices.popPose();
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, alpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        matrices.pushPose();
        //matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180f));
        Tardis tardis = falling.tardis().get();

        if (tardis == null) return;

        this.tardis.y = !tardis.door().isOpen() ? 22F : 8f;

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return tardis;
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        this.tardis.y = !tardis.door().isOpen() ? -14F : 0;
    }
}