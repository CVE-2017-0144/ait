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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

// Made with Blockbench 4.9.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class TardimExteriorModel extends SimpleExteriorModel {
    private final ModelPart tardis;

    public TardimExteriorModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.tardis = root.getChild("tardis");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition tardis = modelPartData.addOrReplaceChild("tardis",
                CubeListBuilder.create().texOffs(62, 58)
                        .addBox(-11.0F, -32.0F, -8.0F, 3.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(-8.0F, -40.0F, -8.0F, 16.0F, 8.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(78, 26)
                        .addBox(-8.0F, -0.02F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(62, 9)
                        .addBox(-8.0F, 0.02F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition cube_r1 = tardis.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 25).addBox(-11.0F, -32.0F,
                -8.0F, 3.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r2 = tardis.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(39, 25).addBox(-11.0F, -32.0F,
                -8.0F, 3.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition left_door = tardis.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(23, 74).addBox(-6.5F,
                -32.0F, -1.5F, 8.0F, 32.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, 0.0F, -9.5F));

        PartDefinition right_door = tardis.addOrReplaceChild("right_door",
                CubeListBuilder.create().texOffs(0, 74).addBox(-1.5F, -32.0F, -1.5F, 8.0F, 32.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.5F, 0.0F, -9.5F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        tardis.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart root() {
        return tardis;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);

        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler handler = tardis.door();

            this.tardis.getChild("left_door").yRot = (handler.isLeftOpen() || handler.isOpen()) ? -1.575f : 0.0F;
            this.tardis.getChild("right_door").yRot = (handler.isRightOpen() || handler.areBothOpen()) ? 1.575f : 0.0F;
        } else {
            float maxRot = 90f;
            this.tardis.getChild("left_door").yRot = -(float) Math.toRadians(maxRot * tardis.door().getLeftRot());
            this.tardis.getChild("right_door").yRot = (float) Math.toRadians(maxRot * tardis.door().getRightRot());
        }

        if (isBOTI) {
            matrices.pushPose();
            matrices.translate(0, -1.5f, 0);
            this.tardis.getChild("left_door").render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
            this.tardis.getChild("right_door").render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
            matrices.popPose();
        }
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (falling.tardis().isEmpty())
            return;

        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);

        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler handler = falling.tardis().get().door();

            this.tardis.getChild("left_door").yRot = (handler.isLeftOpen() || handler.isOpen()) ? -1.575f : 0.0F;
            this.tardis.getChild("right_door").yRot = (handler.isRightOpen() || handler.areBothOpen()) ? 1.575f : 0.0F;
        } else {
            float maxRot = 90f;
            this.tardis.getChild("left_door").yRot = -(float) Math.toRadians(maxRot * falling.tardis().get().door().getLeftRot());
            this.tardis.getChild("right_door").yRot = (float) Math.toRadians(maxRot * falling.tardis().get().door().getRightRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }
}
