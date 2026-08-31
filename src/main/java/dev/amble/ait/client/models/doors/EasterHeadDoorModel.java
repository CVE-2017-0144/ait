package dev.amble.ait.client.models.doors;


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
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.tardis.ClientTardis;

public class EasterHeadDoorModel extends DoorModel {
    private final ModelPart bottom;

    public EasterHeadDoorModel(ModelPart root) {
        this.bottom = root.getChild("bottom");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bottom = modelPartData.addOrReplaceChild("bottom", CubeListBuilder.create(),
                PartPose.offset(0.0F, 54.0F, 0.0F));

        bottom.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-12.0F, 30.0F, -12.0F, 24.0F, 14.0F, 24.0F,
                new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));
        bottom.addOrReplaceChild("door",
                CubeListBuilder.create().texOffs(8, 1).mirror()
                        .addBox(-9.0F, -30.0F, -8.0F, 18.0F, 0.0F, 19.0F, new CubeDeformation(0.005F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        bottom.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.translate(0, -1.5f, 0);

        this.bottom.y = tardis.door().isOpen() ? 22 : 54;

        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return bottom;
    }

}
