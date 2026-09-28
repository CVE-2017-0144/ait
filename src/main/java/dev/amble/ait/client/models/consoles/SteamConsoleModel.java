package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.client.animation.console.steam.SteamAnimations;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SteamConsoleModel extends SimpleConsoleModel {
    private final ModelPart steam;

    public SteamConsoleModel(ModelPart root) {
        this.steam = root.getChild("steam");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition steam = modelPartData.addOrReplaceChild("steam", CubeListBuilder.create(),
                PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition base = steam.addOrReplaceChild("base", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r1 = base.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(50, 14)
                        .addBox(-8.0F, -1.3F, -13.85F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 47)
                        .addBox(-8.0F, -1.0F, -13.85F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition base2 = base.addOrReplaceChild("base2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r2 = base2.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(50, 14)
                        .addBox(-8.0F, -1.3F, -13.85F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 47)
                        .addBox(-8.0F, -1.0F, -13.85F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition base3 = base2.addOrReplaceChild("base3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r3 = base3.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(50, 14)
                        .addBox(-8.0F, -1.3F, -13.85F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 47)
                        .addBox(-8.0F, -1.0F, -13.85F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition base4 = base3.addOrReplaceChild("base4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r4 = base4.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(50, 14)
                        .addBox(-8.0F, -1.3F, -13.85F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 47)
                        .addBox(-8.0F, -1.0F, -13.85F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition base5 = base4.addOrReplaceChild("base5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r5 = base5.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(50, 14)
                        .addBox(-8.0F, -1.3F, -13.85F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 47)
                        .addBox(-8.0F, -1.0F, -13.85F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition base6 = base5.addOrReplaceChild("base6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r6 = base6.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(50, 14)
                        .addBox(-8.0F, -1.3F, -13.85F, 16.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(0, 47)
                        .addBox(-8.0F, -1.0F, -13.85F, 16.0F, 1.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition dividers = steam.addOrReplaceChild("dividers",
                CubeListBuilder.create().texOffs(97, 90)
                        .addBox(-2.0F, -1.9742F, -15.9783F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(97, 14)
                        .addBox(-1.0F, -14.75F, -20.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(-0.5F, -15.75F, -21.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r7 = dividers.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, 0.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -11.4504F, -3.9606F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r8 = dividers.addOrReplaceChild("cube_r8",
                CubeListBuilder.create().texOffs(72, 78)
                        .addBox(-0.5F, -3.019F, -16.4358F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 82)
                        .addBox(-1.0F, -2.019F, -14.9358F, 2.0F, 4.0F, 10.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r9 = dividers
                .addOrReplaceChild("cube_r9",
                        CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, 4.25F, -10.75F, 2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.0F, -1.7004F, 0.2894F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r10 = dividers.addOrReplaceChild("cube_r10",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -3.0F, -13.0F, 4.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.333F, -0.0495F, 0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r11 = dividers.addOrReplaceChild("cube_r11",
                CubeListBuilder.create().texOffs(23, 68).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(63, 29).addBox(-4.5F, 0.0F, 0.0F, 2.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.5F, -14.75F, -17.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition dividers2 = dividers.addOrReplaceChild("dividers2",
                CubeListBuilder.create().texOffs(97, 90)
                        .addBox(-2.0F, -1.9742F, -15.9783F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(97, 14)
                        .addBox(-1.0F, -14.75F, -20.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(-0.5F, -15.75F, -21.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r12 = dividers2.addOrReplaceChild("cube_r12",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, 0.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -11.4504F, -3.9606F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r13 = dividers2.addOrReplaceChild("cube_r13",
                CubeListBuilder.create().texOffs(72, 78)
                        .addBox(-0.5F, -3.019F, -16.4358F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 82)
                        .addBox(-1.0F, -2.019F, -14.9358F, 2.0F, 4.0F, 10.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r14 = dividers2
                .addOrReplaceChild("cube_r14",
                        CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, 4.25F, -10.75F, 2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.0F, -1.7004F, 0.2894F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r15 = dividers2.addOrReplaceChild("cube_r15",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -3.0F, -13.0F, 4.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.333F, -0.0495F, 0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r16 = dividers2.addOrReplaceChild("cube_r16",
                CubeListBuilder.create().texOffs(23, 68).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(63, 29).addBox(-4.5F, 0.0F, 0.0F, 2.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.5F, -14.75F, -17.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition dividers3 = dividers2.addOrReplaceChild("dividers3",
                CubeListBuilder.create().texOffs(97, 90)
                        .addBox(-2.0F, -1.9742F, -15.9783F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(97, 14)
                        .addBox(-1.0F, -14.75F, -20.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(-0.5F, -15.75F, -21.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r17 = dividers3.addOrReplaceChild("cube_r17",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, 0.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -11.4504F, -3.9606F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r18 = dividers3.addOrReplaceChild("cube_r18",
                CubeListBuilder.create().texOffs(72, 78)
                        .addBox(-0.5F, -3.019F, -16.4358F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 82)
                        .addBox(-1.0F, -2.019F, -14.9358F, 2.0F, 4.0F, 10.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r19 = dividers3
                .addOrReplaceChild("cube_r19",
                        CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, 4.25F, -10.75F, 2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.0F, -1.7004F, 0.2894F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r20 = dividers3.addOrReplaceChild("cube_r20",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -3.0F, -13.0F, 4.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.333F, -0.0495F, 0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r21 = dividers3.addOrReplaceChild("cube_r21",
                CubeListBuilder.create().texOffs(23, 68).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(63, 29).addBox(-4.5F, 0.0F, 0.0F, 2.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.5F, -14.75F, -17.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition dividers4 = dividers3.addOrReplaceChild("dividers4",
                CubeListBuilder.create().texOffs(97, 90)
                        .addBox(-2.0F, -1.9742F, -15.9783F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(97, 14)
                        .addBox(-1.0F, -14.75F, -20.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(-0.5F, -15.75F, -21.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r22 = dividers4.addOrReplaceChild("cube_r22",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, 0.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -11.4504F, -3.9606F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r23 = dividers4.addOrReplaceChild("cube_r23",
                CubeListBuilder.create().texOffs(72, 78)
                        .addBox(-0.5F, -3.019F, -16.4358F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 82)
                        .addBox(-1.0F, -2.019F, -14.9358F, 2.0F, 4.0F, 10.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r24 = dividers4
                .addOrReplaceChild("cube_r24",
                        CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, 4.25F, -10.75F, 2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.0F, -1.7004F, 0.2894F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r25 = dividers4.addOrReplaceChild("cube_r25",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -3.0F, -13.0F, 4.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.333F, -0.0495F, 0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r26 = dividers4.addOrReplaceChild("cube_r26",
                CubeListBuilder.create().texOffs(23, 68).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(63, 29).addBox(-4.5F, 0.0F, 0.0F, 2.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.5F, -14.75F, -17.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition dividers5 = dividers4.addOrReplaceChild("dividers5",
                CubeListBuilder.create().texOffs(97, 90)
                        .addBox(-2.0F, -1.9742F, -15.9783F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(97, 14)
                        .addBox(-1.0F, -14.75F, -20.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(-0.5F, -15.75F, -21.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r27 = dividers5.addOrReplaceChild("cube_r27",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, 0.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -11.4504F, -3.9606F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r28 = dividers5.addOrReplaceChild("cube_r28",
                CubeListBuilder.create().texOffs(72, 78)
                        .addBox(-0.5F, -3.019F, -16.4358F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 82)
                        .addBox(-1.0F, -2.019F, -14.9358F, 2.0F, 4.0F, 10.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r29 = dividers5
                .addOrReplaceChild("cube_r29",
                        CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, 4.25F, -10.75F, 2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.0F, -1.7004F, 0.2894F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r30 = dividers5.addOrReplaceChild("cube_r30",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -3.0F, -13.0F, 4.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.333F, -0.0495F, 0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r31 = dividers5.addOrReplaceChild("cube_r31",
                CubeListBuilder.create().texOffs(23, 68).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(63, 29).addBox(-4.5F, 0.0F, 0.0F, 2.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.5F, -14.75F, -17.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition dividers6 = dividers5.addOrReplaceChild("dividers6",
                CubeListBuilder.create().texOffs(97, 90)
                        .addBox(-2.0F, -1.9742F, -15.9783F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)).texOffs(97, 14)
                        .addBox(-1.0F, -14.75F, -20.0F, 2.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(-0.5F, -15.75F, -21.0F, 1.0F, 5.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r32 = dividers6.addOrReplaceChild("cube_r32",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, 0.0F, -14.0F, 2.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.0F, -11.4504F, -3.9606F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r33 = dividers6.addOrReplaceChild("cube_r33",
                CubeListBuilder.create().texOffs(72, 78)
                        .addBox(-0.5F, -3.019F, -16.4358F, 1.0F, 2.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(44, 82)
                        .addBox(-1.0F, -2.019F, -14.9358F, 2.0F, 4.0F, 10.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, -5.0F, 0.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r34 = dividers6
                .addOrReplaceChild("cube_r34",
                        CubeListBuilder.create().texOffs(0, 81).addBox(0.0F, 4.25F, -10.75F, 2.0F, 2.0F, 11.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.0F, -1.7004F, 0.2894F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r35 = dividers6.addOrReplaceChild("cube_r35",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.5F, -3.0F, -13.0F, 4.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -0.333F, -0.0495F, 0.1047F, 0.0F, 0.0F));

        PartDefinition cube_r36 = dividers6.addOrReplaceChild("cube_r36",
                CubeListBuilder.create().texOffs(23, 68).addBox(-4.0F, -1.0F, 0.0F, 1.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                        .texOffs(63, 29).addBox(-4.5F, 0.0F, 0.0F, 2.0F, 3.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(3.5F, -14.75F, -17.0F, 0.3491F, 0.0F, 0.0F));

        PartDefinition panels = steam.addOrReplaceChild("panels", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -18.5F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r37 = panels.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(47, 47).addBox(-9.0F, -1.8F,
                -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panels2 = panels.addOrReplaceChild("panels2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r38 = panels2.addOrReplaceChild("cube_r38",
                CubeListBuilder.create().texOffs(43, 0)
                        .addBox(-9.0F, -2.05F, -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(47, 47)
                        .addBox(-9.0F, -1.8F, -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panels3 = panels2.addOrReplaceChild("panels3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r39 = panels3.addOrReplaceChild("cube_r39",
                CubeListBuilder.create().texOffs(0, 33)
                        .addBox(-9.0F, -2.05F, -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(47, 47)
                        .addBox(-9.0F, -1.8F, -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panels4 = panels3.addOrReplaceChild("panels4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r40 = panels4.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(47, 47).addBox(-9.0F, -1.8F,
                -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panels5 = panels4.addOrReplaceChild("panels5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r41 = panels5.addOrReplaceChild("cube_r41",
                CubeListBuilder.create().texOffs(0, 19)
                        .addBox(-9.0F, -2.05F, -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)).texOffs(47, 47)
                        .addBox(-9.0F, -1.8F, -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition panels6 = panels5.addOrReplaceChild("panels6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r42 = panels6.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(47, 47).addBox(-9.0F, -1.8F,
                -16.85F, 18.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition rim = steam.addOrReplaceChild("rim", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition cube_r43 = rim.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(43, 61).addBox(-0.75F, -1.0F,
                -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rim2 = rim.addOrReplaceChild("rim2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r44 = rim2.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(43, 61).addBox(-0.75F, -1.0F,
                -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rim3 = rim2.addOrReplaceChild("rim3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r45 = rim3.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(43, 61).addBox(-0.75F, -1.0F,
                -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rim4 = rim3.addOrReplaceChild("rim4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r46 = rim4.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(43, 61).addBox(-0.75F, -1.0F,
                -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rim5 = rim4.addOrReplaceChild("rim5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r47 = rim5.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(43, 61).addBox(-0.75F, -1.0F,
                -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rim6 = rim5.addOrReplaceChild("rim6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r48 = rim6.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(43, 61).addBox(-0.75F, -1.0F,
                -9.0F, 2.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.0F, -14.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition rotor = steam.addOrReplaceChild("rotor", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone2 = rotor.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(82, 29).addBox(3.25F, -22.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 65).addBox(3.25F, -24.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -18.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(57, 19)
                        .addBox(3.5F, -23.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r49 = bone2.addOrReplaceChild("cube_r49",
                CubeListBuilder.create().texOffs(0, 81).addBox(3.25F, -24.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone3 = bone2.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(82, 29).addBox(3.25F, -22.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 65).addBox(3.25F, -24.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -18.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(57, 19)
                        .addBox(3.5F, -23.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r50 = bone3.addOrReplaceChild("cube_r50",
                CubeListBuilder.create().texOffs(0, 81).addBox(3.25F, -24.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4",
                CubeListBuilder.create().texOffs(82, 29).addBox(3.25F, -22.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 65).addBox(3.25F, -24.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -18.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(57, 19)
                        .addBox(3.5F, -23.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r51 = bone4.addOrReplaceChild("cube_r51",
                CubeListBuilder.create().texOffs(0, 81).addBox(3.25F, -24.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(82, 29).addBox(3.25F, -22.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 65).addBox(3.25F, -24.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -18.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(57, 19)
                        .addBox(3.5F, -23.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r52 = bone5.addOrReplaceChild("cube_r52",
                CubeListBuilder.create().texOffs(0, 81).addBox(3.25F, -24.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(82, 29).addBox(3.25F, -22.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 65).addBox(3.25F, -24.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -18.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(57, 19)
                        .addBox(3.5F, -23.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r53 = bone6.addOrReplaceChild("cube_r53",
                CubeListBuilder.create().texOffs(0, 81).addBox(3.25F, -24.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone7 = bone6.addOrReplaceChild("bone7",
                CubeListBuilder.create().texOffs(82, 29).addBox(3.25F, -22.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 65).addBox(3.25F, -24.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 11)
                        .addBox(1.5F, -18.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(57, 19)
                        .addBox(3.5F, -23.0F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r54 = bone7.addOrReplaceChild("cube_r54",
                CubeListBuilder.create().texOffs(0, 81).addBox(3.25F, -24.5F, -1.5F, 1.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition hourglass = rotor.addOrReplaceChild("hourglass", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition base7 = hourglass.addOrReplaceChild("base7",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.4F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -21.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition base8 = base7.addOrReplaceChild("base8",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.4F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition base9 = base8.addOrReplaceChild("base9",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.4F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition base10 = base9.addOrReplaceChild("base10",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.4F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition base11 = base10.addOrReplaceChild("base11",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.4F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition base12 = base11.addOrReplaceChild("base12",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.4F, -1.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone15 = base7.addOrReplaceChild("bone15",
                CubeListBuilder.create().texOffs(61, 14).addBox(-2.25F, -6.3F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(61, 14).addBox(2.25F, -6.3F, -0.5F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition spin = base7.addOrReplaceChild("spin",
                CubeListBuilder.create().texOffs(103, 29).addBox(-1.5F, 0.25F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.3F))
                        .texOffs(106, 39).addBox(-1.5F, 2.65F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).texOffs(106, 0)
                        .addBox(-1.5F, -3.6F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)).texOffs(0, 103)
                        .addBox(-1.5F, -3.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(40, 74)
                        .addBox(-2.0F, -3.5F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(9, 51)
                        .addBox(1.65F, -4.0F, -0.5F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(94, 87)
                        .addBox(1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(10, 103)
                        .addBox(-3.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(9, 51)
                        .addBox(-1.65F, -4.0F, -0.5F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(40, 74)
                        .addBox(-2.0F, 3.6F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(111, 66)
                        .addBox(-0.5F, -0.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -6.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bottom = steam.addOrReplaceChild("bottom", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r55 = bottom
                .addOrReplaceChild(
                        "cube_r55", CubeListBuilder.create().texOffs(0, 0).addBox(0.479F, -0.7185F, -9.0F, 12.0F, 0.0F,
                                18.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-4.0F, -11.0F, 0.0F, 0.0F, 0.0F, -2.9671F));

        PartDefinition bottom2 = bottom.addOrReplaceChild("bottom2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r56 = bottom2
                .addOrReplaceChild(
                        "cube_r56", CubeListBuilder.create().texOffs(0, 0).addBox(0.479F, -0.7185F, -9.0F, 12.0F, 0.0F,
                                18.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-4.0F, -11.0F, 0.0F, 0.0F, 0.0F, -2.9671F));

        PartDefinition bottom3 = bottom2.addOrReplaceChild("bottom3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r57 = bottom3
                .addOrReplaceChild(
                        "cube_r57", CubeListBuilder.create().texOffs(0, 0).addBox(0.479F, -0.7185F, -9.0F, 12.0F, 0.0F,
                                18.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-4.0F, -11.0F, 0.0F, 0.0F, 0.0F, -2.9671F));

        PartDefinition bottom4 = bottom3.addOrReplaceChild("bottom4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r58 = bottom4
                .addOrReplaceChild(
                        "cube_r58", CubeListBuilder.create().texOffs(0, 0).addBox(0.479F, -0.7185F, -9.0F, 12.0F, 0.0F,
                                18.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-4.0F, -11.0F, 0.0F, 0.0F, 0.0F, -2.9671F));

        PartDefinition bottom5 = bottom4.addOrReplaceChild("bottom5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r59 = bottom5
                .addOrReplaceChild(
                        "cube_r59", CubeListBuilder.create().texOffs(0, 0).addBox(0.479F, -0.7185F, -9.0F, 12.0F, 0.0F,
                                18.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-4.0F, -11.0F, 0.0F, 0.0F, 0.0F, -2.9671F));

        PartDefinition bottom6 = bottom5.addOrReplaceChild("bottom6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r60 = bottom6
                .addOrReplaceChild(
                        "cube_r60", CubeListBuilder.create().texOffs(0, 0).addBox(0.479F, -0.7185F, -9.0F, 12.0F, 0.0F,
                                18.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-4.0F, -11.0F, 0.0F, 0.0F, 0.0F, -2.9671F));

        PartDefinition bone8 = steam.addOrReplaceChild("bone8",
                CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone57 = bone8.addOrReplaceChild("bone57",
                CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone58 = bone57.addOrReplaceChild("bone58",
                CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone59 = bone58.addOrReplaceChild("bone59",
                CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone60 = bone59.addOrReplaceChild("bone60",
                CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone61 = bone60.addOrReplaceChild("bone61",
                CubeListBuilder.create().texOffs(0, 47).addBox(5.0F, -11.0F, -2.0F, 0.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition controls = steam.addOrReplaceChild("controls", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel_1 = controls.addOrReplaceChild("panel_1", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rot = panel_1.addOrReplaceChild("rot", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.0F, -14.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition radio = rot.addOrReplaceChild("radio",
                CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(47, 47).addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.1F)).texOffs(43, 0)
                        .addBox(-1.0F, -4.0F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.2F)).texOffs(112, 69)
                        .addBox(-0.5F, -3.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(73, 61)
                        .addBox(1.05F, -0.75F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-9.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.6545F));

        PartDefinition light = rot.addOrReplaceChild("light",
                CubeListBuilder.create().texOffs(85, 71).addBox(-1.0F, -0.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                        .texOffs(6, 81).addBox(0.15F, -0.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(108, 97)
                        .addBox(-1.0F, -1.85F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(19, 112)
                        .addBox(-0.5F, -1.95F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(74, 69)
                        .addBox(-0.5F, -3.55F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.0F, 0.0F, -2.5F));

        PartDefinition bone28 = light.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(23, 68).addBox(-0.5F, -3.55F,
                -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition switch0 = light.addOrReplaceChild("switch0",
                CubeListBuilder.create().texOffs(9, 44).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.9F, -0.1F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition light2 = rot.addOrReplaceChild("light2",
                CubeListBuilder.create().texOffs(85, 71).addBox(-1.0F, -0.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                        .texOffs(6, 81).addBox(0.15F, -0.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(108, 97)
                        .addBox(-1.0F, -1.85F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(19, 112)
                        .addBox(-0.5F, -1.95F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(74, 69)
                        .addBox(-0.5F, -3.55F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.0F, 0.0F, 2.5F));

        PartDefinition bone24 = light2.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(23, 68).addBox(-0.5F, -3.55F,
                -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition switch1 = light2.addOrReplaceChild("switch1",
                CubeListBuilder.create().texOffs(9, 44).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.9F, -0.1F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition valve = rot.addOrReplaceChild("valve",
                CubeListBuilder.create().texOffs(50, 111).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(111, 44).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-6.0F, 0.25F, 0.0F));

        PartDefinition bone9 = valve.addOrReplaceChild("bone9",
                CubeListBuilder.create().texOffs(12, 11).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
                        .texOffs(63, 29).addBox(-1.0F, -1.8F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)),
                PartPose.offset(0.0F, -0.65F, 0.0F));

        PartDefinition switch5 = rot.addOrReplaceChild("switch5",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.5F, 0.5F));

        PartDefinition bone25 = switch5.addOrReplaceChild("bone25",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch2 = rot.addOrReplaceChild("switch2",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.5F, 2.0F));

        PartDefinition bone11 = switch2.addOrReplaceChild("bone11",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch3 = rot.addOrReplaceChild("switch3",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.5F, 3.5F));

        PartDefinition bone12 = switch3.addOrReplaceChild("bone12",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch4 = rot.addOrReplaceChild("switch4",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.5F, 5.0F));

        PartDefinition bone13 = switch4.addOrReplaceChild("bone13",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch17 = rot.addOrReplaceChild("switch17",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.5F, 6.5F));

        PartDefinition bone14 = switch17.addOrReplaceChild("bone14",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition lever2 = rot.addOrReplaceChild("lever2",
                CubeListBuilder.create().texOffs(47, 57).addBox(-1.0F, -0.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, 0.25F, -1.5F));

        PartDefinition bone20 = lever2.addOrReplaceChild("bone20",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.25F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 10).addBox(-0.75F, -3.25F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(97, 47)
                        .addBox(-0.25F, -3.95F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition lever3 = rot.addOrReplaceChild("lever3",
                CubeListBuilder.create().texOffs(47, 57).addBox(-1.0F, -0.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, 0.25F, 0.0F));

        PartDefinition bone19 = lever3.addOrReplaceChild("bone19",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.25F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 10).addBox(-0.75F, -3.25F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(97, 47)
                        .addBox(-0.25F, -3.95F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition lever5 = rot.addOrReplaceChild("lever5",
                CubeListBuilder.create().texOffs(47, 57).addBox(-1.0F, -0.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, 0.25F, 1.5F));

        PartDefinition bone21 = lever5.addOrReplaceChild("bone21",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.25F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 10).addBox(-0.75F, -3.25F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(97, 47)
                        .addBox(-0.25F, -3.95F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition lever = rot.addOrReplaceChild("lever",
                CubeListBuilder.create().texOffs(47, 57).addBox(-1.0F, -0.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, 0.25F, 3.0F));

        PartDefinition bone23 = lever.addOrReplaceChild("bone23",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.25F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 10).addBox(-0.75F, -3.25F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(97, 47)
                        .addBox(-0.25F, -3.95F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition lever4 = rot.addOrReplaceChild("lever4",
                CubeListBuilder.create().texOffs(47, 57).addBox(-1.0F, -0.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.0F, 0.25F, -3.0F));

        PartDefinition bone22 = lever4.addOrReplaceChild("bone22",
                CubeListBuilder.create().texOffs(0, 19).addBox(-0.5F, -2.25F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(52, 10).addBox(-0.75F, -3.25F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(97, 47)
                        .addBox(-0.25F, -3.95F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, 0.25F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition stabilizer = rot.addOrReplaceChild("stabilizer",
                CubeListBuilder.create().texOffs(43, 86).addBox(-0.5F, -0.3F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.5F, 0.0F, -4.0F));

        PartDefinition bone26 = stabilizer.addOrReplaceChild("bone26",
                CubeListBuilder.create().texOffs(108, 52).addBox(-0.5F, -0.75F, -2.0F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.1F))
                        .texOffs(30, 68).addBox(0.0F, -0.75F, -1.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.1F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel_2 = controls.addOrReplaceChild("panel_2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rot2 = panel_2.addOrReplaceChild("rot2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.0F, -14.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition typewriter = rot2.addOrReplaceChild("typewriter",
                CubeListBuilder.create().texOffs(106, 61)
                        .addBox(-6.0485F, -2.3697F, -3.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(111, 36)
                        .addBox(-1.5485F, -2.3697F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(9, 108)
                        .addBox(-2.0485F, -2.3697F, -2.0F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(106, 47)
                        .addBox(-6.0485F, -2.3697F, 2.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 95)
                        .addBox(-6.0485F, -2.3697F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(36, 100)
                        .addBox(-5.2985F, -3.1197F, -2.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(33, 90)
                        .addBox(-5.2985F, -3.1197F, -3.5F, 1.0F, 1.0F, 7.0F, new CubeDeformation(-0.2F)).texOffs(21, 89)
                        .addBox(-1.1985F, -0.3697F, -3.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(99, 71)
                        .addBox(-4.0597F, -1.2718F, -2.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.0F, 0.0F, 0.0F));

        PartDefinition cube_r61 = typewriter.addOrReplaceChild("cube_r61",
                CubeListBuilder.create().texOffs(50, 19).addBox(-2.75F, -0.55F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                        .texOffs(66, 61).addBox(-1.75F, -0.55F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(50, 19)
                        .addBox(-0.75F, -0.55F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(23, 68)
                        .addBox(-0.25F, -0.55F, 0.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(93, 0)
                        .addBox(-3.0F, -0.55F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.8015F, -0.3697F, -3.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r62 = typewriter.addOrReplaceChild("cube_r62",
                CubeListBuilder.create().texOffs(59, 82).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 1.0F, 6.0F, new CubeDeformation(-0.001F)),
                PartPose.offsetAndRotation(1.8015F, -0.3697F, -3.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition cube_r63 = typewriter.addOrReplaceChild("cube_r63",
                CubeListBuilder.create().texOffs(44, 111).addBox(-3.4F, -1.0F, 1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.1454F, -2.3697F, -0.0004F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r64 = typewriter
                .addOrReplaceChild("cube_r64",
                        CubeListBuilder.create().texOffs(89, 110).addBox(-3.4002F, -1.0F, -3.9993F, 1.0F, 1.0F, 3.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-0.1454F, -2.3697F, -0.0004F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r65 = typewriter.addOrReplaceChild("cube_r65",
                CubeListBuilder.create().texOffs(107, 14).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.1454F, -1.8697F, -0.0004F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r66 = typewriter
                .addOrReplaceChild("cube_r66",
                        CubeListBuilder.create().texOffs(36, 108).addBox(-2.0002F, -1.0F, -2.9993F, 2.0F, 1.0F, 3.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(-1.1454F, -1.8697F, -0.0004F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r67 = typewriter
                .addOrReplaceChild("cube_r67",
                        CubeListBuilder.create().texOffs(97, 47).addBox(-1.25F, -1.1F, -3.0F, 1.0F, 1.0F, 6.0F,
                                new CubeDeformation(-0.4F)),
                        PartPose.offsetAndRotation(-5.0485F, -2.3697F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r68 = typewriter.addOrReplaceChild("cube_r68",
                CubeListBuilder.create().texOffs(44, 105).addBox(-0.55F, 1.9F, -2.5F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0485F, -3.6197F, 0.0F, 0.0F, 0.0F, 0.9163F));

        PartDefinition cube_r69 = typewriter.addOrReplaceChild("cube_r69",
                CubeListBuilder.create().texOffs(66, 69).addBox(0.0F, 0.001F, -5.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.1572F, -3.0927F, 2.5F, 0.0F, 0.0F, 1.6057F));

        PartDefinition cube_r70 = typewriter.addOrReplaceChild("cube_r70",
                CubeListBuilder.create().texOffs(98, 97).addBox(0.0F, 0.001F, -5.0F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.4627F, -3.8313F, 2.5F, 0.0F, 0.0F, 0.5149F));

        PartDefinition cube_r71 = typewriter.addOrReplaceChild("cube_r71",
                CubeListBuilder.create().texOffs(101, 84).addBox(0.0F, 0.001F, -5.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.4553F, -3.9532F, 2.5F, 0.0F, 0.0F, 0.1222F));

        PartDefinition cube_r72 = typewriter.addOrReplaceChild("cube_r72",
                CubeListBuilder.create().texOffs(50, 37).addBox(-2.95F, 1.45F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(26, 99).addBox(-1.45F, 1.65F, -2.5F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0485F, -3.6197F, 0.0F, 0.0F, 0.0F, 0.7767F));

        PartDefinition cube_r73 = typewriter.addOrReplaceChild("cube_r73",
                CubeListBuilder.create().texOffs(23, 63)
                        .addBox(-0.7174F, -1.3479F, 5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 105)
                        .addBox(-0.7174F, -1.3479F, 0.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(23, 63)
                        .addBox(-0.7174F, -1.3479F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.5485F, -3.1197F, -2.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r74 = typewriter.addOrReplaceChild("cube_r74",
                CubeListBuilder.create().texOffs(64, 37).addBox(-1.0F, 0.0F, 5.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 37).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-5.2985F, -3.1197F, -2.5F, 0.0F, 0.0F, -0.4363F));

        PartDefinition bone27 = typewriter.addOrReplaceChild("bone27",
                CubeListBuilder.create().texOffs(54, 57).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(9, 30).addBox(-0.25F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.25F, -1.85F, 2.5F, -1.3526F, 0.0F, 0.0F));

        PartDefinition counter = rot2.addOrReplaceChild("counter",
                CubeListBuilder.create().texOffs(69, 102).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(72, 82).addBox(-1.0F, -3.0F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(106, 9)
                        .addBox(0.0F, -3.0F, -2.0F, 1.0F, 0.0F, 4.0F, new CubeDeformation(0.001F)).texOffs(97, 14)
                        .addBox(0.0F, -3.2F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(97, 14)
                        .addBox(0.0F, -3.2F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(97, 14)
                        .addBox(0.0F, -3.2F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(97, 14)
                        .addBox(0.0F, -3.2F, 1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(36, 63)
                        .addBox(0.0F, -3.0F, -2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(62, 29)
                        .addBox(0.0F, -3.0F, 2.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(58, 16)
                        .addBox(0.0F, -3.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-9.0F, 0.75F, 0.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition valve3 = rot2.addOrReplaceChild("valve3",
                CubeListBuilder.create().texOffs(82, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 14).addBox(-0.75F, -1.75F, -0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(5, 47)
                        .addBox(-1.0F, -1.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(8, 33)
                        .addBox(-0.75F, -1.75F, -1.25F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(0.75F, -1.75F, -0.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(57, 43)
                        .addBox(-1.25F, -1.75F, 0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.75F, 0.25F, -5.5F));

        PartDefinition bone16 = valve3.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(57, 29).addBox(-0.65F, -0.2F,
                -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.25F, 0.0F));

        PartDefinition valve2 = rot2.addOrReplaceChild("valve2",
                CubeListBuilder.create().texOffs(82, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 14).addBox(-0.75F, -1.75F, -0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(5, 47)
                        .addBox(-1.0F, -1.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(8, 33)
                        .addBox(-0.75F, -1.75F, -1.25F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(0.75F, -1.75F, -0.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(57, 43)
                        .addBox(-1.25F, -1.75F, 0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.75F, 0.25F, 5.5F));

        PartDefinition bone17 = valve2.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(57, 29).addBox(-0.65F, -0.2F,
                -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.25F, 0.0F));

        PartDefinition panel_3 = controls.addOrReplaceChild("panel_3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition rot3 = panel_3.addOrReplaceChild("rot3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.0F, -14.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition board = rot3.addOrReplaceChild("board",
                CubeListBuilder.create().texOffs(85, 61).addBox(-1.5F, -1.4F, -4.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                        .texOffs(82, 29).addBox(-1.5F, -1.4F, -4.0F, 6.0F, 1.0F, 8.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-5.0F, 1.0F, 0.0F));

        PartDefinition bone30 = board.addOrReplaceChild("bone30",
                CubeListBuilder.create().texOffs(112, 25).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(1.0F, -0.75F, 2.25F, 0.0F, -0.3927F, 0.0F));

        PartDefinition button = rot3.addOrReplaceChild("button",
                CubeListBuilder.create().texOffs(91, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(34, 86).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-0.5F, 0.5F, -5.5F));

        PartDefinition bone31 = button.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(106, 66).addBox(-0.5F, -1.25F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -0.2F, 0.0F));

        PartDefinition button2 = rot3.addOrReplaceChild("button2",
                CubeListBuilder.create().texOffs(91, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(34, 86).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-0.5F, 0.5F, 5.5F));

        PartDefinition bone10 = button2.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(106, 66).addBox(-0.5F, -1.25F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, -0.2F, 0.0F));

        PartDefinition light3 = rot3.addOrReplaceChild("light3",
                CubeListBuilder.create().texOffs(26, 105).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(78, 102).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 73)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(93, 101)
                        .addBox(-0.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(16, 81)
                        .addBox(-1.0F, -1.75F, -0.35F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(30, 86)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, -0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, 0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 101)
                        .addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-10.25F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition light4 = rot3.addOrReplaceChild("light4",
                CubeListBuilder.create().texOffs(26, 105).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(78, 102).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 73)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(93, 101)
                        .addBox(-0.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(16, 81)
                        .addBox(-1.0F, -1.75F, -0.35F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(30, 86)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, -0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, 0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 101)
                        .addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-9.75F, 0.0F, -1.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition light5 = rot3.addOrReplaceChild("light5",
                CubeListBuilder.create().texOffs(26, 105).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(78, 102).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 73)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(93, 101)
                        .addBox(-0.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(16, 81)
                        .addBox(-1.0F, -1.75F, -0.35F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(30, 86)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, -0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, 0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 101)
                        .addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-9.75F, 0.0F, 1.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition switch6 = rot3.addOrReplaceChild("switch6",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-8.25F, 0.5F, 3.0F));

        PartDefinition bone33 = switch6.addOrReplaceChild("bone33",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch7 = rot3.addOrReplaceChild("switch7",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-8.25F, 0.5F, 1.5F));

        PartDefinition bone34 = switch7.addOrReplaceChild("bone34",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition panel_4 = controls.addOrReplaceChild("panel_4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition rot4 = panel_4.addOrReplaceChild("rot4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.0F, -14.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition panel = rot4.addOrReplaceChild("panel",
                CubeListBuilder.create().texOffs(87, 78).addBox(0.0F, -1.0F, -3.5F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(87, 78).addBox(-2.5F, -1.0F, -3.5F, 2.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-5.0F, 0.75F, 0.0F));

        PartDefinition switch8 = panel.addOrReplaceChild("switch8",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, 3.0F));

        PartDefinition bone35 = switch8.addOrReplaceChild("bone35",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch9 = panel.addOrReplaceChild("switch9",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, 1.5F));

        PartDefinition bone36 = switch9.addOrReplaceChild("bone36",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch10 = panel.addOrReplaceChild("switch10",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, -2.0F));

        PartDefinition bone37 = switch10.addOrReplaceChild("bone37",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch11 = panel.addOrReplaceChild("switch11",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, -0.5F));

        PartDefinition bone38 = switch11.addOrReplaceChild("bone38",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition button3 = panel.addOrReplaceChild("button3",
                CubeListBuilder.create().texOffs(13, 95).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                        .texOffs(109, 90).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(1.0F, 0.0F, 2.75F));

        PartDefinition button4 = panel.addOrReplaceChild("button4",
                CubeListBuilder.create().texOffs(86, 93).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(1.0F, 0.0F, 1.5F));

        PartDefinition cube_r75 = button4.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(109, 86).addBox(-0.5F, -2.0F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition button5 = panel.addOrReplaceChild("button5",
                CubeListBuilder.create().texOffs(93, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(1.0F, 0.0F, -1.5F));

        PartDefinition cube_r76 = button5.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(109, 77).addBox(-0.5F, -2.0F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition button6 = panel.addOrReplaceChild("button6",
                CubeListBuilder.create().texOffs(34, 90).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                        .texOffs(75, 109).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(1.0F, 0.0F, -2.75F));

        PartDefinition valve4 = panel.addOrReplaceChild("valve4",
                CubeListBuilder.create().texOffs(56, 14).addBox(-0.75F, -2.0F, -0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(57, 43).addBox(-1.25F, -2.0F, 0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(0.75F, -2.0F, -0.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(8, 33)
                        .addBox(-0.75F, -2.0F, -1.25F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(5, 47)
                        .addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(1.1F, 0.25F, 0.0F));

        PartDefinition bone39 = valve4.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(57, 29).addBox(-0.65F, 0.3F,
                -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition globe = rot4.addOrReplaceChild("globe",
                CubeListBuilder.create().texOffs(24, 111).addBox(-1.0F, -1.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
                        .texOffs(0, 44).addBox(-1.5F, -2.75F, -0.25F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(89, 108)
                        .addBox(-1.5F, -5.75F, -0.25F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 30)
                        .addBox(-1.5F, -5.25F, -0.25F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(50, 19)
                        .addBox(-0.5F, -5.75F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(26, 97)
                        .addBox(-0.5F, -2.65F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.05F)).texOffs(64, 90)
                        .addBox(-0.5F, -5.35F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(59, 90)
                        .addBox(0.75F, -2.7F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-9.0F, 0.75F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone40 = globe.addOrReplaceChild("bone40",
                CubeListBuilder.create().texOffs(83, 101).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.5F))
                        .texOffs(99, 77).addBox(-1.5F, -2.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.45F)),
                PartPose.offsetAndRotation(0.0F, -3.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition lever6 = rot4.addOrReplaceChild("lever6",
                CubeListBuilder.create().texOffs(110, 57).addBox(-1.25F, -1.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(85, 61).addBox(-0.75F, -1.7F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(-0.75F, 0.75F, 5.0F));

        PartDefinition bone41 = lever6.addOrReplaceChild("bone41",
                CubeListBuilder.create().texOffs(78, 88).addBox(-0.5F, -1.75F, -0.9F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(78, 88).addBox(-0.5F, -1.75F, -2.1F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(0, 63)
                        .addBox(-0.5F, -1.75F, -2.1F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 63)
                        .addBox(-0.5F, -1.75F, 0.1F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 63)
                        .addBox(-0.5F, -1.75F, -1.1F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 63)
                        .addBox(-0.5F, -1.75F, -0.9F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 110)
                        .addBox(-0.5F, -2.559F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-0.25F, -1.25F, 0.0F, 0.0F, 0.0F, -0.829F));

        PartDefinition crank = rot4.addOrReplaceChild("crank",
                CubeListBuilder.create().texOffs(105, 103)
                        .addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(0, 11)
                        .addBox(-0.5F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(44, 99)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(36, 99)
                        .addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.0F, 0.0F, -4.5F));

        PartDefinition bone42 = crank.addOrReplaceChild("bone42",
                CubeListBuilder.create().texOffs(64, 37).addBox(-1.5F, -0.95F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F))
                        .texOffs(72, 29).addBox(-1.5F, -2.3F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F)),
                PartPose.offset(0.0F, -1.9F, 0.0F));

        PartDefinition vent2 = rot4.addOrReplaceChild("vent2",
                CubeListBuilder.create().texOffs(50, 43).addBox(-0.85F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 55).addBox(-1.4F, -0.08F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-1.5F, -0.2F, 0.0F));

        PartDefinition cube_r77 = vent2.addOrReplaceChild("cube_r77",
                CubeListBuilder.create().texOffs(66, 65).addBox(-0.5F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 72).addBox(-0.5F, 0.0F, -2.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(50, 29)
                        .addBox(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.85F, -0.001F, 1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition panel_5 = controls.addOrReplaceChild("panel_5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition rot5 = panel_5.addOrReplaceChild("rot5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.0F, -14.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition clock = rot5.addOrReplaceChild("clock",
                CubeListBuilder.create().texOffs(93, 7).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 63).addBox(-1.75F, -1.1F, -1.75F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(110, 5)
                        .addBox(-1.0F, -1.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.01F)),
                PartPose.offset(-9.0F, 0.0F, 0.0F));

        PartDefinition bone44 = clock.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(59, 86).addBox(-0.9F, -1.45F,
                -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, 0.4F, 0.0F));

        PartDefinition lever7 = rot5.addOrReplaceChild("lever7",
                CubeListBuilder.create().texOffs(87, 87).addBox(-1.0F, -1.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(94, 71).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(-5.25F, 0.25F, 0.0F));

        PartDefinition bone45 = lever7.addOrReplaceChild("bone45",
                CubeListBuilder.create().texOffs(57, 19).addBox(-0.25F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(73, 112).addBox(-0.5F, -2.5F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.5061F));

        PartDefinition lever8 = rot5.addOrReplaceChild("lever8",
                CubeListBuilder.create().texOffs(60, 98).addBox(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.5F, 0.5F, -3.5F));

        PartDefinition bone46 = lever8.addOrReplaceChild("bone46",
                CubeListBuilder.create().texOffs(43, 0).addBox(-0.75F, -3.0F, -1.25F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 47).addBox(-0.5F, -3.45F, -1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition bone47 = lever8.addOrReplaceChild("bone47",
                CubeListBuilder.create().texOffs(43, 0).addBox(-0.75F, -3.0F, -1.25F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 47).addBox(-0.5F, -3.45F, -1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 1.25F, 0.0F, 0.0F, 0.8727F));

        PartDefinition bone48 = lever8.addOrReplaceChild("bone48",
                CubeListBuilder.create().texOffs(43, 0).addBox(-0.75F, -3.0F, -1.25F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 47).addBox(-0.5F, -3.45F, -1.75F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 2.5F, 0.0F, 0.0F, 0.8727F));

        PartDefinition crank2 = rot5.addOrReplaceChild("crank2",
                CubeListBuilder.create().texOffs(105, 103)
                        .addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(0, 11)
                        .addBox(-0.5F, -2.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(44, 99)
                        .addBox(-0.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(36, 99)
                        .addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.75F, 0.0F, 4.0F));

        PartDefinition bone18 = crank2.addOrReplaceChild("bone18",
                CubeListBuilder.create().texOffs(64, 37).addBox(-1.5F, -0.95F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F))
                        .texOffs(72, 29).addBox(0.5F, -2.3F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F)),
                PartPose.offset(0.0F, -1.9F, 0.0F));

        PartDefinition light6 = rot5.addOrReplaceChild("light6",
                CubeListBuilder.create().texOffs(85, 71).addBox(-1.0F, -0.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                        .texOffs(6, 81).addBox(0.15F, -0.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(108, 97)
                        .addBox(-1.0F, -1.85F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(19, 112)
                        .addBox(-0.5F, -1.95F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(66, 69)
                        .addBox(-0.5F, -3.55F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.0F, 0.0F, 2.5F));

        PartDefinition bone32 = light6.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(66, 61).addBox(-0.5F, -3.55F,
                -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition switch12 = light6.addOrReplaceChild("switch12",
                CubeListBuilder.create().texOffs(9, 44).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.9F, -0.1F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition light7 = rot5.addOrReplaceChild("light7",
                CubeListBuilder.create().texOffs(85, 71).addBox(-1.0F, -0.85F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                        .texOffs(6, 81).addBox(0.15F, -0.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(108, 97)
                        .addBox(-1.0F, -1.85F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(19, 112)
                        .addBox(-0.5F, -1.95F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(66, 69)
                        .addBox(-0.5F, -3.55F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.0F, 0.0F, -2.5F));

        PartDefinition bone29 = light7.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(66, 61).addBox(-0.5F, -3.55F,
                -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition switch13 = light7.addOrReplaceChild("switch13",
                CubeListBuilder.create().texOffs(9, 44).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.9F, -0.1F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition valve5 = rot5.addOrReplaceChild("valve5",
                CubeListBuilder.create().texOffs(82, 39).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 14).addBox(-0.75F, -1.75F, -0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(5, 47)
                        .addBox(-1.0F, -1.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(8, 33)
                        .addBox(-0.75F, -1.75F, -1.25F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 33)
                        .addBox(0.75F, -1.75F, -0.75F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(57, 43)
                        .addBox(-1.25F, -1.75F, 0.75F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.5F, 0.25F, 0.5F));

        PartDefinition bone49 = valve5.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(57, 29).addBox(-0.65F, -0.2F,
                -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.25F, 0.0F));

        PartDefinition panel_6 = controls.addOrReplaceChild("panel_6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rot6 = panel_6.addOrReplaceChild("rot6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(15.0F, -14.25F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition lever9 = rot6.addOrReplaceChild("lever9",
                CubeListBuilder.create().texOffs(43, 14).addBox(-2.0F, 0.0F, -1.0F, 5.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(53, 74).addBox(-0.5F, -1.0F, 0.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(53, 74)
                        .addBox(-0.5F, -1.0F, -1.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(44, 107)
                        .addBox(1.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(44, 107)
                        .addBox(0.0F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(44, 107)
                        .addBox(-1.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(19, 112)
                        .addBox(0.0F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(19, 112)
                        .addBox(-1.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(19, 112)
                        .addBox(1.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(59, 82)
                        .addBox(1.5F, -2.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(66, 69)
                        .addBox(-1.5F, -2.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(79, 82)
                        .addBox(0.0F, -2.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-3.5F, -0.6F, 3.0F));

        PartDefinition bone63 = lever9.addOrReplaceChild("bone63",
                CubeListBuilder.create().texOffs(50, 37).addBox(1.0F, -3.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.5F, 0.5F, 0.0F));

        PartDefinition bone43 = lever9.addOrReplaceChild("bone43",
                CubeListBuilder.create().texOffs(66, 61).addBox(-2.0F, -3.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.5F, 0.5F, 0.0F));

        PartDefinition bone62 = lever9.addOrReplaceChild("bone62",
                CubeListBuilder.create().texOffs(9, 63).addBox(-0.5F, -3.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.01F)),
                PartPose.offset(0.5F, 0.5F, 0.0F));

        PartDefinition bone50 = lever9.addOrReplaceChild("bone50",
                CubeListBuilder.create().texOffs(56, 47).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(87, 82).addBox(-0.5F, -2.0F, 0.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(52, 0)
                        .addBox(-0.5F, -4.0F, 2.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(41, 86)
                        .addBox(-0.5F, -4.0F, 1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(55, 97)
                        .addBox(-0.5F, -4.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, 0.0F, 1.3F, 0.0F, 0.0F, 1.0908F));

        PartDefinition panel2 = rot6.addOrReplaceChild("panel2",
                CubeListBuilder.create().texOffs(86, 93).addBox(-2.5F, -1.0F, -3.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-6.0F, 0.75F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition switch14 = panel2.addOrReplaceChild("switch14",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, -1.0F));

        PartDefinition bone51 = switch14.addOrReplaceChild("bone51",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch15 = panel2.addOrReplaceChild("switch15",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, 0.5F));

        PartDefinition bone52 = switch15.addOrReplaceChild("bone52",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition switch16 = panel2.addOrReplaceChild("switch16",
                CubeListBuilder.create().texOffs(52, 105).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.0F, -0.25F, 2.0F));

        PartDefinition bone53 = switch16.addOrReplaceChild("bone53",
                CubeListBuilder.create().texOffs(0, 47).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.5F, -1.0F, -0.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition lever10 = rot6.addOrReplaceChild("lever10",
                CubeListBuilder.create().texOffs(97, 23).addBox(-3.0F, -1.2F, -1.0F, 4.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F))
                        .texOffs(87, 78).addBox(-1.5F, -1.75F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(-1.5F, 0.5F, -3.75F));

        PartDefinition bone54 = lever10.addOrReplaceChild("bone54",
                CubeListBuilder.create().texOffs(47, 47).addBox(-0.75F, -1.25F, -0.9F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(47, 47).addBox(-0.75F, -1.25F, 0.9F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(109, 81)
                        .addBox(-0.5F, -2.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.25F)).texOffs(34, 107)
                        .addBox(-0.5F, -2.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F)).texOffs(97, 47)
                        .addBox(-0.5F, -3.65F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offsetAndRotation(-1.0F, -1.25F, 0.5F, 0.0F, 0.0F, 1.3963F));

        PartDefinition meter = rot6.addOrReplaceChild("meter",
                CubeListBuilder.create().texOffs(109, 20).addBox(-0.5F, -1.1F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(109, 20).addBox(1.0F, -1.1F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(43, 10)
                        .addBox(-0.75F, -0.75F, -1.0F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-1.75F, 0.75F, 0.0F));

        PartDefinition bone55 = meter.addOrReplaceChild("bone55",
                CubeListBuilder.create().texOffs(25, 86).addBox(-0.5F, 0.0F, -0.75F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.15F, 0.0F));

        PartDefinition bone56 = meter.addOrReplaceChild("bone56",
                CubeListBuilder.create().texOffs(25, 86).addBox(-0.5F, 0.0F, -0.75F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.5F, -1.15F, 0.0F));

        PartDefinition light8 = rot6.addOrReplaceChild("light8",
                CubeListBuilder.create().texOffs(26, 105).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(78, 102).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 73)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(93, 101)
                        .addBox(-0.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(16, 81)
                        .addBox(-1.0F, -1.75F, -0.35F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(30, 86)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, -0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, 0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 101)
                        .addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-9.75F, 0.0F, -1.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition light9 = rot6.addOrReplaceChild("light9",
                CubeListBuilder.create().texOffs(26, 105).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(78, 102).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(9, 73)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(93, 101)
                        .addBox(-0.5F, -2.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(16, 81)
                        .addBox(-1.0F, -1.75F, -0.35F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(30, 86)
                        .addBox(-0.5F, -3.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, -0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(10, 19)
                        .addBox(-0.75F, -3.75F, 0.5F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(26, 101)
                        .addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-9.75F, 0.0F, 1.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition vent = rot6.addOrReplaceChild("vent",
                CubeListBuilder.create().texOffs(50, 43).addBox(-0.85F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(97, 55).addBox(-1.4F, -0.08F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-4.5F, -0.2F, 0.0F));

        PartDefinition cube_r78 = vent.addOrReplaceChild("cube_r78",
                CubeListBuilder.create().texOffs(66, 65).addBox(-0.5F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(23, 72).addBox(-0.5F, 0.0F, -2.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(50, 29)
                        .addBox(-0.5F, 0.0F, -2.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.85F, -0.001F, 1.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition pipes = steam.addOrReplaceChild("pipes", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone85 = pipes.addOrReplaceChild("bone85",
                CubeListBuilder.create().texOffs(0, 73).addBox(6.25F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(80, 108).addBox(6.25F, -5.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone84 = bone85.addOrReplaceChild("bone84", CubeListBuilder.create().texOffs(17, 97).addBox(9.0F, -12.0F,
                -2.75F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone86 = bone85.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(57, 105).addBox(9.0F, -11.0F,
                -2.75F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone87 = bone85.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(105, 108).addBox(9.0F, -2.0F,
                -2.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pipes2 = pipes.addOrReplaceChild("pipes2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone77 = pipes2.addOrReplaceChild("bone77", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone78 = bone77.addOrReplaceChild("bone78",
                CubeListBuilder.create().texOffs(16, 84).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(7.2F, -8.9F, -0.35F));

        PartDefinition bone79 = bone77.addOrReplaceChild("bone79",
                CubeListBuilder.create().texOffs(96, 39).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(7.2F, -6.0F, 1.15F));

        PartDefinition bone80 = bone77.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(66, 109).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(8.6F, -6.0F, 2.65F));

        PartDefinition bone81 = bone77.addOrReplaceChild("bone81",
                CubeListBuilder.create().texOffs(45, 97).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(10.0F, -6.0F, 1.15F));

        PartDefinition bone82 = bone77.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(96, 103).addBox(-1.0F, -3.5F,
                -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(10.0F, -3.5F, -1.75F));

        PartDefinition bone83 = bone77.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(105, 108).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -1.0F, -1.75F));

        PartDefinition pipes3 = pipes2.addOrReplaceChild("pipes3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone88 = pipes3.addOrReplaceChild("bone88",
                CubeListBuilder.create().texOffs(0, 73).addBox(6.25F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(80, 108).addBox(6.25F, -5.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone89 = bone88.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(17, 97).addBox(-1.0F, -6.0F,
                -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(10.0F, -6.0F, -1.75F));

        PartDefinition bone90 = bone88.addOrReplaceChild("bone90",
                CubeListBuilder.create().texOffs(57, 105).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(10.0F, -8.0F, -1.75F));

        PartDefinition bone91 = bone88.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(105, 108).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -1.0F, -1.75F));

        PartDefinition pipes4 = pipes3.addOrReplaceChild("pipes4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone = pipes4.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone64 = bone.addOrReplaceChild("bone64",
                CubeListBuilder.create().texOffs(16, 84).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(7.2F, -8.9F, -0.35F));

        PartDefinition bone65 = bone.addOrReplaceChild("bone65",
                CubeListBuilder.create().texOffs(96, 39).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(7.2F, -6.0F, 1.15F));

        PartDefinition bone67 = bone.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(66, 109).addBox(-1.0F, -1.0F, -1.0F,
                2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(8.6F, -6.0F, 2.65F));

        PartDefinition bone66 = bone.addOrReplaceChild("bone66",
                CubeListBuilder.create().texOffs(45, 97).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(10.0F, -6.0F, 1.15F));

        PartDefinition bone68 = bone.addOrReplaceChild("bone68", CubeListBuilder.create().texOffs(96, 103).addBox(-1.0F, -3.5F, -1.0F,
                2.0F, 7.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(10.0F, -3.5F, -1.75F));

        PartDefinition bone69 = bone.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(105, 108).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -1.0F, -1.75F));

        PartDefinition pipes5 = pipes4.addOrReplaceChild("pipes5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone92 = pipes5.addOrReplaceChild("bone92",
                CubeListBuilder.create().texOffs(0, 73).addBox(6.25F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(80, 108).addBox(6.25F, -5.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone93 = bone92.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(17, 97).addBox(-1.0F, -6.0F,
                -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(10.0F, -6.0F, -1.75F));

        PartDefinition bone94 = bone92.addOrReplaceChild("bone94",
                CubeListBuilder.create().texOffs(57, 105).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(10.0F, -8.0F, -1.75F));

        PartDefinition bone95 = bone92.addOrReplaceChild("bone95", CubeListBuilder.create().texOffs(105, 108).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -1.0F, -1.75F));

        PartDefinition pipes6 = pipes5.addOrReplaceChild("pipes6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone70 = pipes6.addOrReplaceChild("bone70", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone71 = bone70.addOrReplaceChild("bone71",
                CubeListBuilder.create().texOffs(16, 84).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(7.2F, -8.9F, -0.35F));

        PartDefinition bone72 = bone70.addOrReplaceChild("bone72",
                CubeListBuilder.create().texOffs(96, 39).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(7.2F, -6.0F, 1.15F));

        PartDefinition bone73 = bone70.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(66, 109).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(8.6F, -6.0F, 2.65F));

        PartDefinition bone74 = bone70.addOrReplaceChild("bone74",
                CubeListBuilder.create().texOffs(45, 97).addBox(-1.0F, -1.0F, -2.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(10.0F, -6.0F, 1.15F));

        PartDefinition bone75 = bone70.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(96, 103).addBox(-1.0F, -3.5F,
                -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(10.0F, -3.5F, -1.75F));

        PartDefinition bone76 = bone70.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(105, 108).addBox(-1.0F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.0F, -1.0F, -1.75F));

        PartDefinition gears = steam.addOrReplaceChild("gears", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r79 = gears.addOrReplaceChild("cube_r79",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -17.25F, -0.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

        PartDefinition gears2 = gears.addOrReplaceChild("gears2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r80 = gears2.addOrReplaceChild("cube_r80",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -17.25F, -0.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

        PartDefinition gears3 = gears2.addOrReplaceChild("gears3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r81 = gears3.addOrReplaceChild("cube_r81",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -17.25F, -0.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

        PartDefinition gears4 = gears3.addOrReplaceChild("gears4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r82 = gears4.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(50, 33).addBox(-2.0F, -10.5F,
                -0.5F, 11.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition wheel = gears4.addOrReplaceChild("wheel",
                CubeListBuilder.create().texOffs(69, 93).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(40, 63).addBox(-4.5F, -4.5F, -0.5F, 9.0F, 9.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(9.0F, -8.0F, 0.0F, 0.0F, 0.0F, -2.5744F));

        PartDefinition gears5 = gears4.addOrReplaceChild("gears5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r83 = gears5.addOrReplaceChild("cube_r83",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -17.25F, -0.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

        PartDefinition gears6 = gears5.addOrReplaceChild("gears6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r84 = gears6.addOrReplaceChild("cube_r84",
                CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -17.25F, -0.5F, 7.0F, 9.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));
        return LayerDefinition.create(modelData, 128, 128);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        steam.render(matrices, vertexConsumer, light, overlay, color);
    }


    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = this.controlDelta();
        matrices.pushPose();
        this.applyRootTransform(matrices);

        // Throttle Control
        ModelPart throttle = steam.getChild("controls").getChild("panel_6").getChild("rot6").getChild("lever9")
                .getChild("bone50");
        float throttleTarget = tardis.travel().maxSpeed().get() > 0 ? ((float) tardis.travel().speed() / (float) tardis.travel().maxSpeed().get()) : 0f;
        throttle.zRot = getAngle(console, "throttle", throttleTarget, delta) - 0.5f;

        // Increment Control
        ModelPart increment = steam.getChild("controls").getChild("panel_6").getChild("rot6").getChild("lever10").getChild("bone54");
        int incrementVal = IncrementManager.increment(tardis);
        float targetOffset = 0f;
        if (incrementVal >= 10000) {
            targetOffset = -(1.3963F * 2);
        } else if (incrementVal >= 1000) {
            targetOffset = -(1.047225F * 2);
        } else if (incrementVal >= 100) {
            targetOffset = -(0.69815F * 2);
        } else if (incrementVal >= 10) {
            targetOffset = -0.69815F;
        }
        increment.zRot = getAngle(console, "increment", targetOffset, delta) + 1.5f;

        // Alarm Control
        ModelPart alarms = steam.getChild("controls").getChild("panel_1").getChild("rot").getChild("lever4")
                .getChild("bone22");
        float alarmTarget = tardis.alarm().isEnabled() ? 0.4363F : -0.5672F;
        alarms.zRot = getAngle(console, "alarm", alarmTarget, delta);

        // Security Control
        ModelPart security = steam.getChild("controls").getChild("panel_1").getChild("rot").getChild("lever2")
                .getChild("bone20");
        float securityTarget = tardis.stats().security().get() ? 0.4363F : -0.5672F;
        security.zRot = getAngle(console, "security", securityTarget, delta);

        // Anti Grav Control
        ModelPart antigrav = steam.getChild("controls").getChild("panel_1").getChild("rot").getChild("lever3")
                .getChild("bone19");
        float antigravTarget = tardis.travel().antigravs().get() ? 0.4363F : -0.5672F;
        antigrav.zRot = getAngle(console, "antigravs", antigravTarget, delta);

        // Shields Control
        ModelPart shields = steam.getChild("controls").getChild("panel_1").getChild("rot").getChild("lever5")
                .getChild("bone21");
        float shieldsTarget = tardis.shields().shielded().get()
                ? (tardis.shields().visuallyShielded().get() ? 0.0F : 0.4363F)
                : -0.5672F;
        shields.zRot = getAngle(console, "shields", shieldsTarget, delta);

        // Refueling Control
        ModelPart refueling = steam.getChild("controls").getChild("panel_1").getChild("rot").getChild("lever")
                .getChild("bone23");
        float refuelerTarget = tardis.isRefueling() ? 0.4363F : -0.5672F;
        refueling.zRot = getAngle(console, "refueler", refuelerTarget, delta);

        // Handbrake Control
        ModelPart handbrake = steam.getChild("controls").getChild("panel_4").getChild("rot4").getChild("lever6")
                .getChild("bone41");
        float handbrakeTarget = tardis.travel().handbrake() ? 1.5f : 0;
        handbrake.zRot = getAngle(console, "handbrake", handbrakeTarget, delta) - 0.5f;

        // Power Control
        ModelPart power = steam.getChild("controls").getChild("panel_5").getChild("rot5").getChild("lever7")
                .getChild("bone45");
        float powerTarget = tardis.fuel().hasPower() ? 0f : 1.5f;
        power.zRot = getAngle(console, "power", powerTarget, delta) - 0.75f;

        // Ground Search Control
        ModelPart landType = steam.getChild("controls").getChild("panel_1").getChild("rot").getChild("valve")
                .getChild("bone9");
        float groundSearchTarget = tardis.travel().horizontalSearch().get() ? 0.5f : 0;
        landType.y = getAngle(console, "ground_search", groundSearchTarget, delta);

        // Direction Control
        ModelPart direction = steam.getChild("controls").getChild("panel_4").getChild("rot4").getChild("crank")
                .getChild("bone42");
        float directionTargetDegrees = (0.3927f * tardis.travel().destination().getRotation()) * (180f / (float) Math.PI);
        direction.yRot = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);

        // Door Control
        ModelPart doorControl = steam.getChild("controls").getChild("panel_5").getChild("rot5").getChild("crank2")
                .getChild("bone18");
        float doorControlTarget = tardis.door().isLeftOpen() ? 1.5708f : (tardis.door().areBothOpen() ? 1.5708f * 2f : 0);
        doorControl.yRot = getAngle(console, "door_control", doorControlTarget, delta);

        // Cloak Control
        ModelPart cloak = steam.getChild("controls").getChild("panel_5").getChild("rot5").getChild("lever8")
                .getChild("bone46");
        float cloakTarget = tardis.cloak().cloaked().get() ? 1.35f : 0;
        cloak.zRot = getAngle(console, "cloak", cloakTarget, delta) - 0.5f;

        // Door Lock Control
        ModelPart doorLock = steam.getChild("controls").getChild("panel_5").getChild("rot5").getChild("lever8")
                .getChild("bone47");
        float doorLockTarget = tardis.door().locked() ? 1.35f : 0;
        doorLock.zRot = getAngle(console, "door_lock", doorLockTarget, delta) - 0.5f;

        // Auto Pilot Control
        ModelPart autopilot = steam.getChild("controls").getChild("panel_5").getChild("rot5").getChild("lever8")
                .getChild("bone48");
        float autopilotTarget = tardis.travel().autopilot() ? 1.35f : 0;
        autopilot.zRot = getAngle(console, "autopilot", autopilotTarget, delta) - 0.5f;

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        if (state == TravelHandlerBase.State.LANDED)
            return SteamAnimations.CONSOLE_STEAM_IDLE;

        return SteamAnimations.CONSOLE_STEAM_FLIGHT;
    }

    @Override
    public ModelPart root() {
        return steam;
    }
}
