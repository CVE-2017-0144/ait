package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
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

public class ConsoleGeneratorModel extends HierarchicalModel {
    private final ModelPart bone7;

    public ConsoleGeneratorModel(ModelPart root) {
        this.bone7 = root.getChild("bone7");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone7 = modelPartData.addOrReplaceChild("bone7", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition bone = bone7.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(20, 16).addBox(9.25F, -3.0F, -6.5F, 2.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone11 = bone.addOrReplaceChild("bone11",
                CubeListBuilder.create().texOffs(20, 16).addBox(9.25F, -3.0F, -6.5F, 2.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone12 = bone11.addOrReplaceChild("bone12",
                CubeListBuilder.create().texOffs(20, 16).addBox(9.25F, -3.0F, -6.5F, 2.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone13 = bone12.addOrReplaceChild("bone13",
                CubeListBuilder.create().texOffs(20, 16).addBox(9.25F, -3.0F, -6.5F, 2.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone15 = bone13.addOrReplaceChild("bone15",
                CubeListBuilder.create().texOffs(20, 16).addBox(9.25F, -3.0F, -6.5F, 2.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16",
                CubeListBuilder.create().texOffs(20, 16).addBox(9.25F, -3.0F, -6.5F, 2.0F, 3.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone8 = bone7.addOrReplaceChild("bone8",
                CubeListBuilder.create().texOffs(0, 0).addBox(-0.45F, -1.0F, -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.1F, 0.0F));

        PartDefinition bone18 = bone8.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(0, 0).addBox(-0.45F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone19 = bone18.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(0, 0).addBox(-0.45F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone23 = bone19.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(0, 0).addBox(-0.45F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.003F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone24 = bone23.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(0, 0).addBox(-0.45F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.004F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone25 = bone24.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(0, 0).addBox(-0.45F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.005F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone14 = bone7.addOrReplaceChild("bone14",
                CubeListBuilder.create().texOffs(0, 13).addBox(11.95F, 0.0F, -7.5F, 1.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.0F, -0.1F, 0.0F));

        PartDefinition bone3 = bone14.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(0, 13).addBox(11.95F, 0.0F, -7.5F, 1.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4",
                CubeListBuilder.create().texOffs(0, 13).addBox(11.95F, 0.0F, -7.5F, 1.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(0, 13).addBox(11.95F, 0.0F, -7.5F, 1.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(0, 13).addBox(11.95F, 0.0F, -7.5F, 1.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone9 = bone6.addOrReplaceChild("bone9",
                CubeListBuilder.create().texOffs(0, 13).addBox(11.95F, 0.0F, -7.5F, 1.0F, 0.0F, 15.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone84 = bone7.addOrReplaceChild("bone84", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone20 = bone84.addOrReplaceChild("bone20", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone21 = bone20.addOrReplaceChild("bone21",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(8.4F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bone22 = bone21.addOrReplaceChild("bone22",
                CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition bone69 = bone84.addOrReplaceChild("bone69", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone70 = bone69.addOrReplaceChild("bone70",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(8.4F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bone71 = bone70.addOrReplaceChild("bone71",
                CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition bone72 = bone84.addOrReplaceChild("bone72", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone73 = bone72.addOrReplaceChild("bone73",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(8.4F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bone74 = bone73.addOrReplaceChild("bone74",
                CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition bone75 = bone84.addOrReplaceChild("bone75", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition bone76 = bone75.addOrReplaceChild("bone76",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(8.4F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bone77 = bone76.addOrReplaceChild("bone77",
                CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition bone78 = bone84.addOrReplaceChild("bone78", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition bone79 = bone78.addOrReplaceChild("bone79",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(8.4F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bone80 = bone79.addOrReplaceChild("bone80",
                CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition bone81 = bone84.addOrReplaceChild("bone81", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone82 = bone81.addOrReplaceChild("bone82",
                CubeListBuilder.create().texOffs(32, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(8.4F, -1.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition bone83 = bone82.addOrReplaceChild("bone83",
                CubeListBuilder.create().texOffs(32, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 33).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, -7.0F, 0.0F, 0.0F, 0.0F, -1.1345F));

        PartDefinition bone2 = bone7.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(0, 29).addBox(5.8F, -2.0F, -4.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone27 = bone2.addOrReplaceChild("bone27",
                CubeListBuilder.create().texOffs(0, 29).addBox(5.8F, -2.0F, -4.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone28 = bone27.addOrReplaceChild("bone28",
                CubeListBuilder.create().texOffs(0, 29).addBox(5.8F, -2.0F, -4.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone29 = bone28.addOrReplaceChild("bone29",
                CubeListBuilder.create().texOffs(0, 29).addBox(5.8F, -2.0F, -4.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone30 = bone29.addOrReplaceChild("bone30",
                CubeListBuilder.create().texOffs(0, 29).addBox(5.8F, -2.0F, -4.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone31 = bone30.addOrReplaceChild("bone31",
                CubeListBuilder.create().texOffs(0, 29).addBox(5.8F, -2.0F, -4.5F, 2.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone10 = bone7.addOrReplaceChild("bone10",
                CubeListBuilder.create().texOffs(0, 13).addBox(6.05F, -3.0F, -3.5F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone33 = bone10.addOrReplaceChild("bone33",
                CubeListBuilder.create().texOffs(0, 13).addBox(6.05F, -3.0F, -3.5F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone34 = bone33.addOrReplaceChild("bone34",
                CubeListBuilder.create().texOffs(0, 13).addBox(6.05F, -3.0F, -3.5F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone35 = bone34.addOrReplaceChild("bone35",
                CubeListBuilder.create().texOffs(0, 13).addBox(6.05F, -3.0F, -3.5F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone36 = bone35.addOrReplaceChild("bone36",
                CubeListBuilder.create().texOffs(0, 13).addBox(6.05F, -3.0F, -3.5F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone37 = bone36.addOrReplaceChild("bone37",
                CubeListBuilder.create().texOffs(0, 13).addBox(6.05F, -3.0F, -3.5F, 0.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone50 = bone7.addOrReplaceChild("bone50", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone17 = bone50.addOrReplaceChild("bone17",
                CubeListBuilder.create().texOffs(18, 15).addBox(5.2F, -3.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone39 = bone17.addOrReplaceChild("bone39",
                CubeListBuilder.create().texOffs(18, 15).addBox(5.2F, -3.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone40 = bone39.addOrReplaceChild("bone40",
                CubeListBuilder.create().texOffs(18, 15).addBox(5.2F, -3.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone41 = bone40.addOrReplaceChild("bone41",
                CubeListBuilder.create().texOffs(18, 15).addBox(5.2F, -3.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone42 = bone41.addOrReplaceChild("bone42",
                CubeListBuilder.create().texOffs(18, 15).addBox(5.2F, -3.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone43 = bone42.addOrReplaceChild("bone43",
                CubeListBuilder.create().texOffs(18, 15).addBox(5.2F, -3.0F, -3.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone26 = bone50.addOrReplaceChild("bone26",
                CubeListBuilder.create().texOffs(38, 15).addBox(4.3F, -3.0F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone45 = bone26.addOrReplaceChild("bone45",
                CubeListBuilder.create().texOffs(38, 15).addBox(4.3F, -3.0F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone46 = bone45.addOrReplaceChild("bone46",
                CubeListBuilder.create().texOffs(38, 15).addBox(4.3F, -3.0F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone47 = bone46.addOrReplaceChild("bone47",
                CubeListBuilder.create().texOffs(38, 15).addBox(4.3F, -3.0F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone48 = bone47.addOrReplaceChild("bone48",
                CubeListBuilder.create().texOffs(38, 15).addBox(4.3F, -3.0F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone49 = bone48.addOrReplaceChild("bone49",
                CubeListBuilder.create().texOffs(38, 15).addBox(4.3F, -3.0F, -2.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone38 = bone50.addOrReplaceChild("bone38",
                CubeListBuilder.create().texOffs(0, 22).addBox(3.45F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone52 = bone38.addOrReplaceChild("bone52",
                CubeListBuilder.create().texOffs(0, 22).addBox(3.45F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone53 = bone52.addOrReplaceChild("bone53",
                CubeListBuilder.create().texOffs(0, 22).addBox(3.45F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone54 = bone53.addOrReplaceChild("bone54",
                CubeListBuilder.create().texOffs(0, 22).addBox(3.45F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone55 = bone54.addOrReplaceChild("bone55",
                CubeListBuilder.create().texOffs(0, 22).addBox(3.45F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone56 = bone55.addOrReplaceChild("bone56",
                CubeListBuilder.create().texOffs(0, 22).addBox(3.45F, -3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone44 = bone50.addOrReplaceChild("bone44",
                CubeListBuilder.create().texOffs(0, 13).addBox(2.6F, -3.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone57 = bone44.addOrReplaceChild("bone57",
                CubeListBuilder.create().texOffs(0, 13).addBox(2.6F, -3.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone58 = bone57.addOrReplaceChild("bone58",
                CubeListBuilder.create().texOffs(0, 13).addBox(2.6F, -3.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone59 = bone58.addOrReplaceChild("bone59",
                CubeListBuilder.create().texOffs(0, 13).addBox(2.6F, -3.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone60 = bone59.addOrReplaceChild("bone60",
                CubeListBuilder.create().texOffs(0, 13).addBox(2.6F, -3.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone61 = bone60.addOrReplaceChild("bone61",
                CubeListBuilder.create().texOffs(0, 13).addBox(2.6F, -3.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone32 = bone50.addOrReplaceChild("bone32",
                CubeListBuilder.create().texOffs(5, 22).addBox(-0.3F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone62 = bone32.addOrReplaceChild("bone62",
                CubeListBuilder.create().texOffs(5, 22).addBox(-0.3F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone63 = bone62.addOrReplaceChild("bone63",
                CubeListBuilder.create().texOffs(5, 22).addBox(-0.3F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone64 = bone63.addOrReplaceChild("bone64",
                CubeListBuilder.create().texOffs(5, 22).addBox(-0.3F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone65 = bone64.addOrReplaceChild("bone65",
                CubeListBuilder.create().texOffs(5, 22).addBox(-0.3F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone66 = bone65.addOrReplaceChild("bone66",
                CubeListBuilder.create().texOffs(5, 22).addBox(-0.3F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone51 = bone50.addOrReplaceChild("bone51",
                CubeListBuilder.create().texOffs(18, 13).addBox(-7.0F, -3.0F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone67 = bone51.addOrReplaceChild("bone67",
                CubeListBuilder.create().texOffs(18, 13).addBox(-7.0F, -3.0F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone68 = bone67.addOrReplaceChild("bone68",
                CubeListBuilder.create().texOffs(18, 13).addBox(-7.0F, -3.0F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        matrices.pushPose();

        bone7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return bone7;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
