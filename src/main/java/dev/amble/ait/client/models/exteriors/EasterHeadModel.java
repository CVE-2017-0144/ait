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
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;

public class EasterHeadModel extends SimpleExteriorModel {
    private final ModelPart head;

    public EasterHeadModel(ModelPart root) {
        this.head = root.getChild("head");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, -1.0534F,
                -14.6026F, 24.0F, 14.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 11.0534F, 2.6026F));

        PartDefinition cube_r1 = head
                .addOrReplaceChild(
                        "cube_r1", CubeListBuilder.create().texOffs(78, 57).addBox(-9.0F, -2.0F, -11.5F, 18.0F, 19.0F,
                                15.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -11.0534F, 9.3974F, -0.4363F, 0.0F, 0.0F));

        PartDefinition door = head.addOrReplaceChild("door", CubeListBuilder.create().texOffs(0, 72)
                .addBox(-10.0F, -22.7922F, -20.2482F, 20.0F, 23.0F, 20.0F, new CubeDeformation(0.0F)).texOffs(81, 92)
                .addBox(10.0F, -30.7922F, -10.2483F, 6.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(0, 39)
                .addBox(-12.0F, -32.7922F, -22.2482F, 24.0F, 10.0F, 22.0F, new CubeDeformation(0.0F)).texOffs(81, 92).mirror()
                .addBox(-16.0F, -30.7922F, -10.2483F, 6.0F, 31.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, -1.2612F, 9.6457F));

        PartDefinition cube_r2 = door.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 39).addBox(5.0F, 12.0F, 2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 20.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-8.0F, 12.0F, 2.0F, 3.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -22.7922F, -20.2482F, -0.2618F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);
        this.head.getChild("door").xRot = (tardis.door().isOpen()) ? -45f : 0f;

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (falling.tardis().isEmpty())
            return;

        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);
        this.head.getChild("door").xRot = (falling.tardis().get().door().isOpen()) ? -45f : 0f;

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return head;
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {

    }
}
