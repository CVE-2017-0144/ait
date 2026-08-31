package dev.amble.ait.client.models.doors.exclusive;

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
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.models.doors.DoorModel;
import dev.amble.ait.client.tardis.ClientTardis;

public class DoomDoorModel extends DoorModel {
    private final ModelPart doom;

    public static final ResourceLocation DOOM_DOOR = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/exteriors/exclusive/doom/doom_door.png");
    public static final ResourceLocation DOOM_DOOR_OPEN = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/exteriors/exclusive/doom/doom_door_open.png");

    public DoomDoorModel(ModelPart root) {
        this.doom = root.getChild("doom");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition doom = modelPartData.addOrReplaceChild("doom", CubeListBuilder.create().texOffs(0, 0).addBox(-25.5F, -86.0F,
                13.0F, 51.0F, 86.0F, 0.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(modelData, 102, 86);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        doom.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();
        matrices.translate(0, -0.75f, 0);
        matrices.scale(0.5f, 0.5f, 0.5f);
        super.renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return doom;
    }
}
