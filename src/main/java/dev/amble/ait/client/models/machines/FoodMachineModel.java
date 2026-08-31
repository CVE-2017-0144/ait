package dev.amble.ait.client.models.machines;

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

public class FoodMachineModel extends HierarchicalModel {
    private final ModelPart root;
    private final ModelPart pannel;
    private final ModelPart bone;
    private final ModelPart dial2;
    private final ModelPart dial;
    private final ModelPart water;
    private final ModelPart milk;
    private final ModelPart select_dial;
    private final ModelPart spiny_thing2;
    private final ModelPart water2;
    private final ModelPart spiny_thing;
    private final ModelPart door_slider;
    private final ModelPart bb_main;
    public FoodMachineModel(ModelPart root) {
        this.root = root;
        this.pannel = root.getChild("pannel");
        this.bone = this.pannel.getChild("bone");
        this.dial2 = this.bone.getChild("dial2");
        this.dial = this.bone.getChild("dial");
        this.water = this.bone.getChild("water");
        this.milk = this.bone.getChild("milk");
        this.select_dial = this.bone.getChild("select_dial");
        this.spiny_thing2 = this.bone.getChild("spiny_thing2");
        this.water2 = this.bone.getChild("water2");
        this.spiny_thing = this.bone.getChild("spiny_thing");
        this.door_slider = this.pannel.getChild("door_slider");
        this.bb_main = root.getChild("bb_main");
    }

    public FoodMachineModel(ModelPart root, ModelPart pannel, ModelPart bone, ModelPart dial2, ModelPart dial, ModelPart water, ModelPart milk, ModelPart selectDial, ModelPart spinyThing2, ModelPart water2, ModelPart spinyThing, ModelPart doorSlider, ModelPart bbMain) {
        this.root = root;
        this.pannel = pannel;
        this.bone = bone;
        this.dial2 = dial2;
        this.dial = dial;
        this.water = water;
        this.milk = milk;
        select_dial = selectDial;
        spiny_thing2 = spinyThing2;
        this.water2 = water2;
        spiny_thing = spinyThing;
        door_slider = doorSlider;
        bb_main = bbMain;
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition pannel = modelPartData.addOrReplaceChild("pannel", CubeListBuilder.create().texOffs(62, 2).addBox(-0.5F, 3.5F, -43.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-6.0F, -2.7F, -42.9F, 12.0F, 13.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).addBox(-6.0F, -14.7F, -41.9F, 0.0F, 12.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(0, 26).mirror().addBox(6.0F, -14.7F, -41.9F, 0.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 77).addBox(-5.997F, -4.7F, -41.9F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(78, 49).addBox(-4.0F, -1.7F, -42.9F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(36, 49).addBox(-6.0F, -14.7F, -30.9F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(1, 50).addBox(-6.0F, -14.7F, -35.9F, 12.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 77).addBox(5.998F, -4.7F, -41.9F, 0.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 13.7F, 36.9F));

        PartDefinition cube_r1 = pannel.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 55).addBox(-6.0F, 0.0F, 0.0F, 12.0F, 14.0F, 3.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(0.0F, -14.701F, -35.9F, -0.5236F, 0.0F, 0.0F));

        PartDefinition bone = pannel.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, -14.7067F, -35.8873F));

        PartDefinition cube_r2 = bone.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, 26).addBox(-5.0F, -14.0F, 0.0F, 10.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0567F, -7.0127F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r3 = bone.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(16, 25).addBox(-4.0F, -11.2F, -0.6F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 25).addBox(-4.0F, -11.2F, -0.4F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(64, 17).addBox(-4.0F, -11.2F, -0.3F, 8.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0567F, -6.7627F, -0.5236F, 0.0F, 0.0F));

        PartDefinition dial2 = bone.addOrReplaceChild("dial2", CubeListBuilder.create(), PartPose.offset(2.5F, 11.7567F, -4.0127F));

        PartDefinition cube_r4 = dial2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(2.0F, -7.0F, -0.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(48, 44).mirror().addBox(2.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5F, 2.7F, -4.1F, -0.5236F, 0.0F, 0.0F));

        PartDefinition dial = bone.addOrReplaceChild("dial", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4567F, -8.1127F));

        PartDefinition cube_r5 = dial.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(48, 44).addBox(-3.0F, -6.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r6 = dial.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, -7.4461F, 3.567F, -0.5236F, 0.0F, 0.0F));

        PartDefinition water = bone.addOrReplaceChild("water", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4567F, -8.1127F));

        PartDefinition cube_r7 = water.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(54, 44).addBox(-0.5F, -7.5F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition milk = bone.addOrReplaceChild("milk", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4567F, -8.1127F));

        PartDefinition cube_r8 = milk.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 3).addBox(-0.5F, -4.3F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition select_dial = bone.addOrReplaceChild("select_dial", CubeListBuilder.create(), PartPose.offset(0.0F, 8.9321F, -4.3457F));

        PartDefinition cube_r9 = select_dial.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(3, 0).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
                .texOffs(48, 44).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition spiny_thing2 = bone.addOrReplaceChild("spiny_thing2", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4567F, -8.1127F));

        PartDefinition cube_r10 = spiny_thing2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(48, 40).addBox(-4.5F, -7.5F, -0.4F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition water2 = bone.addOrReplaceChild("water2", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4567F, -8.1127F));

        PartDefinition cube_r11 = water2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 1).addBox(-4.2F, -8.8F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition spiny_thing = bone.addOrReplaceChild("spiny_thing", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4567F, -8.1127F));

        PartDefinition cube_r12 = spiny_thing.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(48, 34).mirror().addBox(0.5F, -7.5F, -0.4F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition door_slider = pannel.addOrReplaceChild("door_slider", CubeListBuilder.create().texOffs(63, 2).addBox(-4.7F, -11.1F, -50.1F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.3F, 7.0F));

        PartDefinition bb_main = modelPartData.addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(71, 0).addBox(-6.0F, -13.1F, -6.1F, 12.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        return LayerDefinition.create(modelData, 128, 128);
    }
    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        pannel.render(matrices, vertexConsumer, light, overlay, color);
        bb_main.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}