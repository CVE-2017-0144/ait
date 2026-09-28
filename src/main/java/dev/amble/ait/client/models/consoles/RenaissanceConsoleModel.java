package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.client.animation.console.renaissance.RenaissanceAnimation;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.gui.Font;
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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class RenaissanceConsoleModel extends SimpleConsoleModel {

    private final ModelPart console;

    public RenaissanceConsoleModel(ModelPart root) {

        this.console = root.getChild("bone7");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone7 = modelPartData.addOrReplaceChild("bone7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 28.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition panelf = bone7.addOrReplaceChild("panelf", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -15.9306F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 255).addBox(-14.0F, -15.4306F, -24.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(26, 100).addBox(-5.5F, -22.0F, -9.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 100).addBox(-0.5F, -21.5F, -8.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r1 = panelf.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(177, 121).addBox(-0.5F, 29.2534F, -21.9032F, 1.0F, 1.0F, 11.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.0644F, -37.5334F, -0.1136F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r2 = panelf.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(26, 181).addBox(-0.5F, 21.5366F, -29.1165F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r3 = panelf.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(32, 27).addBox(-0.5F, 19.3203F, -8.5587F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r4 = panelf.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(179, 122).addBox(-0.5F, 13.8677F, -33.3321F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.065F, -37.4993F, -0.1105F, 0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r5 = panelf.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 247).addBox(-14.0F, 24.1225F, 23.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 248).addBox(-14.0F, 24.1225F, 22.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r6 = panelf.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -23.5864F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r7 = panelf.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(66, 240).addBox(-10.0F, -25.9158F, 16.4292F, 20.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r8 = panelf.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(34, 10).addBox(-14.0F, -18.1619F, -29.2849F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r9 = panelf.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(183, 241).addBox(-8.0F, -6.2872F, 19.353F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.8326F, 0.0F, -3.1416F));

        PartDefinition cube_r10 = panelf.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(66, 244).addBox(-10.0F, -16.8951F, 21.2658F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(29, 227).addBox(-10.0F, -17.3951F, 21.5158F, 20.0F, 6.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, -3.1416F));

        PartDefinition cube_r11 = panelf.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 238).addBox(-11.0F, 19.5158F, 17.3951F, 22.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r12 = panelf.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 10).addBox(-5.1325F, -29.6577F, 14.2237F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.2707F, -0.504F, -2.9933F));

        PartDefinition cube_r13 = panelf.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(2, 10).addBox(4.1325F, -29.6577F, 14.2237F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.2707F, 0.504F, 2.9933F));

        PartDefinition cube_r14 = panelf.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(26, 17).addBox(8.2F, -25.1535F, 14.0487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(26, 17).addBox(7.2F, -25.1535F, 14.0487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(26, 17).addBox(6.2F, -25.1535F, 14.0487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(26, 17).addBox(5.2F, -25.1535F, 14.0487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(26, 17).addBox(-0.3F, -28.1535F, 13.6487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(26, 17).addBox(-2.2F, -28.1535F, 13.7487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(56, 37).addBox(-11.0F, -28.3535F, 14.2487F, 22.0F, 5.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(92, 89).addBox(-14.0F, -21.8535F, 15.4487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(90, 4).addBox(-14.0F, -21.8535F, 15.8487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 82).addBox(-14.0F, -21.8535F, 16.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(67, 97).addBox(-8.0F, -21.8535F, 14.3487F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(67, 97).addBox(6.0F, -21.8535F, 14.3487F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(0, 0).addBox(-14.0F, -29.8535F, 14.3487F, 28.0F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition cube_r15 = panelf.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 16).addBox(21.7359F, -12.694F, 13.4487F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(21.0288F, -13.4011F, 13.4487F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(23.1501F, -11.2798F, 13.4487F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(22.443F, -11.9869F, 13.4487F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition cube_r16 = panelf.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(29, 17).addBox(8.8F, -28.4183F, 9.726F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(0, 29).addBox(7.8F, -28.4183F, 9.726F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(29, 17).addBox(6.8F, -28.4183F, 9.726F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(0, 29).addBox(5.8F, -28.4183F, 9.726F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.1781F, 0.0F, -3.1416F));

        PartDefinition cube_r17 = panelf.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(4, 10).addBox(-16.0F, 22.517F, -2.0F, 11.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition telepathic_circuits = panelf.addOrReplaceChild("telepathic_circuits", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r18 = telepathic_circuits.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(56, 116).addBox(7.5F, -22.0535F, 11.6237F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 116).addBox(7.5F, -20.0535F, 11.6237F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(56, 116).addBox(10.5F, -22.0535F, 11.6237F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(53, 116).addBox(7.5F, -22.0535F, 11.6237F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(54, 117).addBox(7.5F, -22.0535F, 12.6237F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(55, 116).addBox(8.5F, -21.5535F, 12.6487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(42, 31).addBox(7.4F, -22.0535F, 12.6237F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(42, 31).addBox(5.6F, -22.0535F, 12.6237F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(67, 96).addBox(8.5F, -21.4535F, 13.5487F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition p116 = panelf.addOrReplaceChild("p116", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r19 = p116.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(56, 42).addBox(-11.3F, -28.3535F, 14.1487F, 11.0F, 5.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(56, 47).addBox(-11.3F, -28.3535F, 14.0487F, 11.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition p19 = panelf.addOrReplaceChild("p19", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r20 = p19.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(56, 42).mirror().addBox(-1.1F, -28.3535F, 14.1487F, 11.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false)
                .texOffs(56, 47).mirror().addBox(-1.1F, -28.3535F, 14.0487F, 11.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition sonic_port = panelf.addOrReplaceChild("sonic_port", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r21 = sonic_port.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(28, 27).addBox(-9.2F, -26.6535F, 14.1487F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition handbrake = panelf.addOrReplaceChild("handbrake", CubeListBuilder.create(), PartPose.offset(0.8F, -16.614F, -21.9927F));

        PartDefinition cube_r22 = handbrake.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(62, 28).addBox(-0.5F, -0.5F, -2.35F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.31F))
                .texOffs(27, 75).addBox(-0.5F, -0.5F, -2.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition bone50 = panelf.addOrReplaceChild("bone50", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition twist5 = panelf.addOrReplaceChild("twist5", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.4023F, -18.7395F, -16.5545F, 0.196F, -0.6286F, -0.3257F));

        PartDefinition cube_r23 = twist5.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -0.6F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(30, 12).addBox(-0.3F, -0.6F, -0.19F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0955F, -0.083F, 0.0987F, 1.2012F, 0.7534F, 2.8782F));

        PartDefinition cube_r24 = twist5.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.1188F, 0.0028F, -0.0234F, 1.2012F, 0.7534F, 2.8782F));

        PartDefinition rotation = panelf.addOrReplaceChild("rotation", CubeListBuilder.create(), PartPose.offsetAndRotation(6.8023F, -18.7395F, -16.5545F, 0.196F, 0.6286F, 0.3257F));

        PartDefinition cube_r25 = rotation.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(0, 32).mirror().addBox(-0.5F, -0.6F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false)
                .texOffs(30, 12).mirror().addBox(-0.7F, -0.6F, -0.19F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).mirror(false), PartPose.offsetAndRotation(-0.0955F, -0.083F, 0.0987F, 1.176F, -0.7428F, -2.8888F));

        PartDefinition cube_r26 = rotation.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(0, 30).mirror().addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.1188F, 0.0028F, -0.0234F, 1.176F, -0.7428F, -2.8888F));

        PartDefinition panelf2 = bone7.addOrReplaceChild("panelf2", CubeListBuilder.create().texOffs(29, 234).addBox(-14.0F, -15.9306F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 255).addBox(-14.0F, -15.4306F, -24.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(26, 100).addBox(-5.5F, -22.0F, -9.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(24, 100).addBox(-0.5F, -21.5F, -8.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r27 = panelf2.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(90, 4).addBox(-14.0F, -21.8535F, 15.8487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(92, 89).addBox(-14.0F, -21.8535F, 15.4487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 82).addBox(-14.0F, -21.8535F, 16.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.2939F, 0.0002F, -3.1329F));

        PartDefinition cube_r28 = panelf2.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(66, 240).addBox(-10.0F, -25.9158F, 16.4292F, 20.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r29 = panelf2.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(29, 227).addBox(-10.0F, -17.3951F, 21.5158F, 20.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 244).addBox(-10.0F, -16.8951F, 21.2658F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, 3.1416F));

        PartDefinition cube_r30 = panelf2.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(0, 238).addBox(-11.0F, 19.5158F, 17.3951F, 22.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r31 = panelf2.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(177, 121).addBox(-0.5F, 29.2534F, -21.9032F, 1.0F, 1.0F, 11.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.0644F, -37.5334F, -0.1136F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r32 = panelf2.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(26, 181).addBox(-0.5F, 21.5366F, -29.1165F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r33 = panelf2.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(32, 27).addBox(-0.5F, 19.3203F, -8.5587F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r34 = panelf2.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(179, 122).addBox(-0.5F, 13.8677F, -33.3321F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.065F, -37.4993F, -0.1105F, 0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r35 = panelf2.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(0, 247).addBox(-14.0F, 24.1225F, 23.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 248).addBox(-14.0F, 24.1225F, 22.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r36 = panelf2.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -23.5864F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r37 = panelf2.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(34, 10).addBox(-14.0F, -18.1619F, -29.2849F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r38 = panelf2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(183, 241).addBox(-8.0F, -6.2872F, 19.353F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.8326F, 0.0F, 3.1416F));

        PartDefinition cube_r39 = panelf2.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(24, 32).addBox(-29.7737F, -7.7976F, 14.3237F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, -0.9909F, -2.7202F));

        PartDefinition cube_r40 = panelf2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(24, 34).addBox(24.7737F, -7.7976F, 14.3237F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, 0.9909F, 2.7202F));

        PartDefinition cube_r41 = panelf2.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(32, 8).addBox(-6.5F, -25.8535F, 14.3237F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 34).mirror().addBox(8.1F, -28.2535F, 13.3487F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.001F)).mirror(false)
                .texOffs(38, 27).addBox(8.15F, -28.2535F, 14.2487F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(38, 27).addBox(-11.1F, -28.2535F, 14.2487F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(36, 34).addBox(-11.1F, -28.2535F, 13.3487F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(43, 110).addBox(-2.8F, -29.2035F, 14.0487F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(43, 110).addBox(-1.8F, -29.2035F, 14.0487F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(43, 110).addBox(-0.7F, -29.2035F, 14.0487F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(43, 110).addBox(0.2F, -29.2035F, 14.0487F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(26, 17).addBox(-7.25F, -29.9535F, 14.0487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(26, 17).mirror().addBox(6.25F, -29.9535F, 14.0487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
                .texOffs(0, 38).addBox(-14.0F, -29.8535F, 14.3487F, 28.0F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r42 = panelf2.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(56, 235).addBox(-8.0F, -27.9969F, 16.9828F, 16.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.3963F, 0.0F, 3.1416F));

        PartDefinition cube_r43 = panelf2.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(4, 10).addBox(-16.0F, 22.517F, -2.0F, 11.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition x = panelf2.addOrReplaceChild("x", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, -19.3267F, -14.7377F, -0.1308F, -0.0057F, -0.0433F));

        PartDefinition cube_r44 = x.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(110, 73).addBox(-0.5061F, -1.5F, -0.4939F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(116, 70).addBox(-1.0061F, -1.5F, -0.9939F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.5299F, 0.7119F, 0.3655F));

        PartDefinition cube_r45 = x.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(107, 70).addBox(-1.5F, -0.99F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r46 = x.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.6918F, -0.9275F, -0.5853F));

        PartDefinition cube_r47 = x.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.6918F, 0.9275F, 0.5853F));

        PartDefinition cube_r48 = x.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 2.7489F, 0.0F, 3.1416F));

        PartDefinition cube_r49 = x.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 2.4498F, 0.9275F, 2.5563F));

        PartDefinition cube_r50 = x.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 2.4498F, -0.9275F, -2.5563F));

        PartDefinition y = panelf2.addOrReplaceChild("y", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, -19.5759F, -13.9897F, 0.0872F, -0.0019F, 0.0436F));

        PartDefinition cube_r51 = y.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(110, 73).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(116, 73).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -0.3447F, -0.1509F, 0.5299F, 0.7119F, 0.3655F));

        PartDefinition cube_r52 = y.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(107, 70).addBox(-1.5F, -0.99F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 2.7489F, 0.0F, -3.1416F));

        PartDefinition cube_r53 = y.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 2.4498F, 0.9275F, 2.5563F));

        PartDefinition cube_r54 = y.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 2.4498F, -0.9275F, -2.5563F));

        PartDefinition cube_r55 = y.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r56 = y.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 0.6918F, -0.9275F, -0.5853F));

        PartDefinition cube_r57 = y.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 0.6918F, 0.9275F, 0.5853F));

        PartDefinition z = panelf2.addOrReplaceChild("z", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, -19.3267F, -14.7377F, -0.0436F, -0.0019F, -0.0436F));

        PartDefinition cube_r58 = z.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(110, 73).addBox(-0.5061F, -1.5F, -0.4939F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(116, 70).addBox(-1.0061F, -1.5F, -0.9939F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.5299F, 0.7119F, 0.3655F));

        PartDefinition cube_r59 = z.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(107, 70).addBox(-1.5F, -0.99F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r60 = z.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.6918F, -0.9275F, -0.5853F));

        PartDefinition cube_r61 = z.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 0.6918F, 0.9275F, 0.5853F));

        PartDefinition cube_r62 = z.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 2.7489F, 0.0F, 3.1416F));

        PartDefinition cube_r63 = z.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 2.4498F, 0.9275F, 2.5563F));

        PartDefinition cube_r64 = z.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5767F, 0.2377F, 2.4498F, -0.9275F, -2.5563F));

        PartDefinition randomiser = panelf2.addOrReplaceChild("randomiser", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, -19.5759F, -13.9897F, 0.0869F, -0.0076F, 0.0869F));

        PartDefinition cube_r65 = randomiser.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(110, 73).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(116, 76).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, -0.3447F, -0.1509F, 0.5299F, 0.7119F, 0.3655F));

        PartDefinition cube_r66 = randomiser.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(107, 70).addBox(-1.5F, -0.99F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 2.7489F, 0.0F, -3.1416F));

        PartDefinition cube_r67 = randomiser.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 2.4498F, 0.9275F, 2.5563F));

        PartDefinition cube_r68 = randomiser.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 2.4498F, -0.9275F, -2.5563F));

        PartDefinition cube_r69 = randomiser.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(114, 68).addBox(-2.0F, -1.0F, 0.75F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r70 = randomiser.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 0.6918F, -0.9275F, -0.5853F));

        PartDefinition cube_r71 = randomiser.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(110, 68).addBox(-0.5F, -1.0F, 1.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.5759F, 0.2397F, 0.6918F, 0.9275F, 0.5853F));

        PartDefinition land_type = panelf2.addOrReplaceChild("land_type", CubeListBuilder.create(), PartPose.offset(-0.25F, -37.517F, 0.0F));

        PartDefinition cube_r72 = land_type.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(45, 35).addBox(0.75F, -29.7035F, 13.7487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.28F))
                .texOffs(45, 35).addBox(1.25F, -29.7035F, 13.7487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(1.25F, -29.0535F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(1.25F, -27.7535F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(1.25F, -28.4035F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(0.75F, -28.4035F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(0.75F, -27.7535F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(0.75F, -29.0535F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(0.75F, -27.0535F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F))
                .texOffs(44, 34).addBox(1.25F, -27.0535F, 13.7487F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, 3.1416F));

        PartDefinition dimension = panelf2.addOrReplaceChild("dimension", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r73 = dimension.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(26, 17).addBox(-1.5F, -1.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(4.0F, 21.0895F, -22.1695F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r74 = dimension.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(26, 17).addBox(-1.5F, -1.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(5.0F, 21.0895F, -22.1695F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r75 = dimension.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(26, 17).addBox(-1.5F, -1.5F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.0F, 21.0895F, -22.1695F, 1.309F, 0.0F, 3.1416F));

        PartDefinition bone57 = dimension.addOrReplaceChild("bone57", CubeListBuilder.create(), PartPose.offset(4.0F, 21.1068F, -23.2001F));

        PartDefinition cube_r76 = bone57.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(1, 16).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6279F, -0.1682F, 1.2086F, -0.7519F, -2.8883F));

        PartDefinition bone58 = dimension.addOrReplaceChild("bone58", CubeListBuilder.create(), PartPose.offset(5.0F, 21.1068F, -23.2001F));

        PartDefinition cube_r77 = bone58.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(1, 16).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6279F, -0.1682F, -1.2086F, 0.7519F, 0.2533F));

        PartDefinition bone59 = dimension.addOrReplaceChild("bone59", CubeListBuilder.create(), PartPose.offset(6.0F, 21.1068F, -23.2001F));

        PartDefinition cube_r78 = bone59.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(1, 16).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.6279F, -0.1682F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition p813 = panelf2.addOrReplaceChild("p813", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r79 = p813.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(4, 12).addBox(2.5F, -28.3724F, 13.533F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(4, 12).addBox(3.5F, -28.3724F, 13.533F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(4, 12).addBox(4.5F, -28.3724F, 13.533F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, 3.1416F));

        PartDefinition monitor = panelf2.addOrReplaceChild("monitor", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -25.517F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r80 = monitor.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(76, 81).addBox(34.5F, -2.0F, -4.0F, 0.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.5F, 0.0F, -0.2F, 0.0151F, 0.0001F, -0.0087F));

        PartDefinition cube_r81 = monitor.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(24, 54).addBox(-0.5F, -2.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(13.7F, 1.7321F, -0.1641F, 0.5387F, 0.0001F, -0.0087F));

        PartDefinition cube_r82 = monitor.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(24, 54).addBox(-0.5F, -2.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(13.7F, -0.025F, -1.1891F, -0.5085F, 0.0001F, -0.0087F));

        PartDefinition cube_r83 = monitor.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(28, 36).addBox(-0.5F, -2.0F, 3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(13.7F, 1.7321F, -0.7F, 0.0151F, 0.0001F, -0.0087F));

        PartDefinition cube_r84 = monitor.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(50, 28).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(13.7F, -0.05F, 0.7641F, 0.5387F, 0.0001F, -0.0087F));

        PartDefinition cube_r85 = monitor.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(0, 34).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(13.7F, 1.7321F, 0.3F, 0.0151F, 0.0001F, -0.0087F));

        PartDefinition cube_r86 = monitor.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(50, 28).addBox(-0.5F, -2.0F, -4.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(13.7F, 1.7321F, -0.2359F, -0.5085F, 0.0001F, -0.0087F));

        PartDefinition cube_r87 = monitor.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(19, 99).addBox(-0.5F, -0.75F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(10.7626F, 3.8011F, -0.1358F, -0.0001F, 0.0151F, -1.5795F));

        PartDefinition cube_r88 = monitor.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(29, 54).addBox(-5.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.578F, -0.113F, -0.1948F, 0.0086F, 0.0124F, -0.9686F));

        PartDefinition cube_r89 = monitor.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(78, 43).addBox(-2.5F, 0.1F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(28, 78).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(76, 20).addBox(-2.5F, 0.2F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0743F, 2.8694F, -3.3271F, 0.5704F, -0.2509F, -0.0175F));

        PartDefinition cube_r90 = monitor.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(14, 78).addBox(-2.5F, 0.0F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 78).addBox(-2.5F, 0.1F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(76, 72).addBox(-2.5F, 0.2F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(17.0743F, 2.8694F, 2.9271F, -0.5392F, 0.2512F, -0.0097F));

        PartDefinition cube_r91 = monitor.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(36, 32).addBox(36.0F, 1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 0).addBox(34.0F, 2.0F, -2.0F, 5.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-19.8F, 0.0F, -0.2F, 0.0151F, 0.0001F, -0.0087F));

        PartDefinition cube_r92 = monitor.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(75, 77).addBox(34.0F, -2.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.8F, 0.25F, -0.2F, 0.0151F, 0.0001F, -0.0087F));

        PartDefinition symbol = monitor.addOrReplaceChild("symbol", CubeListBuilder.create(), PartPose.offset(14.0237F, -0.3013F, -0.2023F));

        PartDefinition cube_r93 = symbol.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(82, 93).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0151F, 0.0001F, -0.0087F));

        PartDefinition panelf3 = bone7.addOrReplaceChild("panelf3", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -15.9306F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 255).addBox(-14.0F, -15.4306F, -24.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(26, 100).addBox(-5.5F, -22.0F, -9.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 100).addBox(-0.5F, -21.5F, -8.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition cube_r94 = panelf3.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(90, 4).addBox(-14.0F, -21.8535F, 15.8487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(92, 89).addBox(-14.0F, -21.8535F, 15.4487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 82).addBox(-14.0F, -21.8535F, 16.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.2939F, 0.0003F, -3.1154F));

        PartDefinition cube_r95 = panelf3.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(66, 240).addBox(-10.0F, -25.9158F, 16.4292F, 20.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r96 = panelf3.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(29, 227).addBox(-10.0F, -17.3951F, 21.5158F, 20.0F, 6.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(66, 244).addBox(-10.0F, -16.8951F, 21.2658F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, -3.1416F));

        PartDefinition cube_r97 = panelf3.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(0, 236).addBox(-11.0F, 19.5158F, 17.3951F, 22.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r98 = panelf3.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(177, 121).addBox(-0.5F, 29.2534F, -21.9032F, 1.0F, 1.0F, 11.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.0644F, -37.5334F, -0.1136F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r99 = panelf3.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(24, 155).addBox(-0.475F, 21.5366F, -28.1165F, 0.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(26, 181).addBox(-0.5F, 21.5366F, -29.1165F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r100 = panelf3.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(32, 27).addBox(-0.5F, 19.3203F, -8.5587F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r101 = panelf3.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(179, 122).addBox(-0.5F, 13.8677F, -33.3321F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.065F, -37.4993F, -0.1105F, 0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r102 = panelf3.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(0, 247).addBox(-14.0F, 24.1225F, 23.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 248).addBox(-14.0F, 24.1225F, 22.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r103 = panelf3.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -23.5864F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r104 = panelf3.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(34, 10).addBox(-14.0F, -18.1619F, -29.2849F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r105 = panelf3.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(183, 241).addBox(-8.0F, -6.2872F, 19.353F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.8326F, 0.0F, -3.1416F));

        PartDefinition cube_r106 = panelf3.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(0, 27).addBox(-29.9615F, -5.5966F, 14.3237F, 12.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, -0.9909F, -2.7202F));

        PartDefinition cube_r107 = panelf3.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(17.9615F, -5.5966F, 14.3237F, 12.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, 0.9909F, 2.7202F));

        PartDefinition cube_r108 = panelf3.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -18.8535F, 14.3237F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(62, 163).addBox(-14.0F, -29.8535F, 14.3487F, 28.0F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition cube_r109 = panelf3.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(0, 15).mirror().addBox(1.3F, -23.495F, 16.6652F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 15).mirror().addBox(0.8F, -24.495F, 16.6652F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(23, 8).addBox(0.9F, -25.495F, 16.6652F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-1.9F, -25.495F, 16.6652F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(2, 15).addBox(-2.4F, -24.495F, 16.6652F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 15).addBox(-1.7F, -23.495F, 16.6652F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(56, 52).addBox(-4.0F, -19.695F, 16.0652F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(58, 124).addBox(-3.25F, -21.745F, 16.6652F, 6.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(72, 27).addBox(-10.0F, -26.995F, 16.7652F, 20.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition cube_r110 = panelf3.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(4, 10).addBox(-16.0F, 22.517F, -2.0F, 11.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition fast_return = panelf3.addOrReplaceChild("fast_return", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r111 = fast_return.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(30, 10).addBox(-3.5F, -19.195F, 15.3652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(30, 10).mirror().addBox(2.5F, -19.195F, 15.3652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).mirror(false)
                .texOffs(25, 8).addBox(1.2F, -18.195F, 15.2652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 8).addBox(-0.8F, -18.195F, 15.2652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 8).addBox(0.2F, -18.195F, 15.2652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 8).addBox(-1.8F, -18.195F, 15.2652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(25, 8).addBox(-2.8F, -18.195F, 15.2652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 9).addBox(1.2F, -19.195F, 15.0652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(27, 9).addBox(0.2F, -19.195F, 15.0652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 9).addBox(-2.8F, -19.195F, 15.0652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 8).addBox(-1.8F, -19.195F, 15.0652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 9).addBox(-0.8F, -19.195F, 15.0652F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition p3 = panelf3.addOrReplaceChild("p3", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r112 = p3.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(26, 17).addBox(22.6189F, -13.1537F, 16.2152F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(26, 17).addBox(22.1239F, -14.2143F, 16.2152F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3264F, 0.7703F, 2.9697F));

        PartDefinition adaptive = panelf3.addOrReplaceChild("adaptive", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r113 = adaptive.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(26, 17).mirror().addBox(-23.6189F, -13.1537F, 16.2152F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false)
                .texOffs(26, 17).mirror().addBox(-23.1239F, -14.2143F, 16.2152F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3264F, -0.7703F, -2.9697F));

        PartDefinition panelf4 = bone7.addOrReplaceChild("panelf4", CubeListBuilder.create().texOffs(118, 20).addBox(-14.0F, -15.4306F, -23.1225F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(120, 29).addBox(-13.0F, -15.4306F, -22.1225F, 26.0F, 9.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(122, 38).addBox(-12.0F, -15.4306F, -21.1225F, 24.0F, 9.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(124, 47).addBox(-11.0F, -15.4306F, -20.1225F, 22.0F, 9.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(126, 56).addBox(-10.0F, -15.4306F, -19.1225F, 20.0F, 9.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(107, 181).addBox(-14.0F, -15.9306F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(26, 100).addBox(-5.5F, -22.0F, -9.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(24, 100).addBox(-0.5F, -21.5F, -8.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r114 = panelf4.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(90, 4).addBox(-14.0F, -21.8535F, 15.8487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(92, 89).addBox(-14.0F, -21.8535F, 15.4487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 82).addBox(-14.0F, -21.8535F, 16.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, -3.1067F));

        PartDefinition cube_r115 = panelf4.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(29, 227).addBox(-10.0F, -17.3951F, 21.5158F, 20.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 244).addBox(-10.0F, -16.8951F, 21.2658F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, 3.1416F));

        PartDefinition cube_r116 = panelf4.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(0, 238).addBox(-11.0F, 19.5158F, 17.3951F, 22.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r117 = panelf4.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(177, 121).addBox(-0.5F, 29.2534F, -21.9032F, 1.0F, 1.0F, 11.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.0644F, -37.5334F, -0.1136F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r118 = panelf4.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(24, 155).addBox(0.475F, 21.5366F, -28.1165F, 0.0F, 4.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(26, 181).addBox(-0.5F, 21.5366F, -29.1165F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r119 = panelf4.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(32, 27).addBox(-0.5F, 19.3203F, -8.5587F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r120 = panelf4.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(179, 122).addBox(-0.5F, 13.8677F, -33.3321F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.0632F, -37.4993F, -0.1115F, 0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r121 = panelf4.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(34, 10).addBox(-14.0F, -25.1225F, -22.0864F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r122 = panelf4.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(24, 146).addBox(5.0F, -10.7115F, 21.5141F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F))
                .texOffs(0, 54).addBox(5.0F, -12.7115F, 21.5141F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F))
                .texOffs(24, 146).addBox(-7.0F, -10.7115F, 21.5141F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.15F))
                .texOffs(0, 54).addBox(-7.0F, -12.7115F, 21.5141F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.8326F, 0.0F, 3.1416F));

        PartDefinition cube_r123 = panelf4.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(11, 240).addBox(-4.0F, -28.3535F, 14.3237F, 8.0F, 5.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(50, 34).addBox(-7.5F, -27.3535F, 13.3487F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(50, 34).addBox(5.5F, -27.3535F, 13.3487F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(50, 34).addBox(5.5F, -27.3535F, 13.3487F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(0, 46).addBox(-14.0F, -29.8535F, 14.3487F, 28.0F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r124 = panelf4.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(4, 10).addBox(-16.0F, 22.517F, -2.0F, 11.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone61 = panelf4.addOrReplaceChild("bone61", CubeListBuilder.create(), PartPose.offset(3.0F, -17.499F, -19.8488F));

        PartDefinition cube_r125 = bone61.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(26, 17).addBox(-3.5F, -24.8535F, 13.6987F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.0F, -20.018F, 19.8488F, 1.309F, 0.0F, 3.1416F));

        PartDefinition bone60 = panelf4.addOrReplaceChild("bone60", CubeListBuilder.create(), PartPose.offset(-3.0F, -17.499F, -19.8488F));

        PartDefinition cube_r126 = bone60.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(26, 17).addBox(2.5F, -24.8535F, 13.6987F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(3.0F, -20.018F, 19.8488F, 1.309F, 0.0F, 3.1416F));

        PartDefinition antigravs = panelf4.addOrReplaceChild("antigravs", CubeListBuilder.create(), PartPose.offset(-1.25F, -17.6629F, -20.2032F));

        PartDefinition cube_r127 = antigravs.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(27, 75).addBox(0.75F, -25.1535F, 13.4487F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.25F, -19.8542F, 20.2032F, 1.309F, 0.0F, 3.1416F));

        PartDefinition p1913 = panelf4.addOrReplaceChild("p1913", CubeListBuilder.create(), PartPose.offset(4.0F, -17.9647F, -17.8813F));

        PartDefinition cube_r128 = p1913.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(54, 70).addBox(-7.0F, -14.2115F, 21.5141F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(56, 63).addBox(-7.0F, -13.7115F, 21.5141F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, -19.5523F, 17.8813F, 1.8326F, 0.0F, 3.1416F));

        PartDefinition power = panelf4.addOrReplaceChild("power", CubeListBuilder.create(), PartPose.offset(4.0F, -17.9647F, -17.8813F));

        PartDefinition cube_r129 = power.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(54, 70).addBox(5.0F, -14.2115F, 21.5141F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(56, 63).addBox(5.0F, -13.7115F, 21.5141F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.0F, -19.5523F, 17.8813F, 1.8326F, 0.0F, 3.1416F));

        PartDefinition panelf5 = bone7.addOrReplaceChild("panelf5", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -15.9306F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 255).addBox(-14.0F, -15.4306F, -24.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(26, 100).addBox(-5.5F, -22.0F, -9.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(24, 100).addBox(-0.5F, -21.5F, -8.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r130 = panelf5.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(90, 4).addBox(-14.0F, -21.8535F, 15.8487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 82).addBox(-14.0F, -21.8535F, 16.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.3241F, -0.0003F, -3.1154F));

        PartDefinition cube_r131 = panelf5.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(92, 89).addBox(-14.0F, -7.0F, 0.0F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.4906F, -18.9154F, -10.6313F, 1.3067F, -0.0067F, -3.1408F));

        PartDefinition cube_r132 = panelf5.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(66, 240).addBox(-10.0F, -25.9158F, 16.4292F, 20.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r133 = panelf5.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(29, 227).addBox(-10.0F, -17.3951F, 21.5158F, 20.0F, 6.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(66, 244).addBox(-10.0F, -16.8951F, 21.2658F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, -3.1416F));

        PartDefinition cube_r134 = panelf5.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(0, 236).addBox(-11.0F, 19.5158F, 17.3951F, 22.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition cube_r135 = panelf5.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(177, 121).addBox(-0.5F, 29.2534F, -21.9032F, 1.0F, 1.0F, 11.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.0644F, -37.5334F, -0.1136F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r136 = panelf5.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(26, 181).addBox(-0.5F, 21.5366F, -29.1165F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r137 = panelf5.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(32, 27).addBox(-0.5F, 19.3203F, -8.5587F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r138 = panelf5.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(179, 122).addBox(-0.5F, 13.8677F, -33.3321F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.065F, -37.4993F, -0.1105F, 0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r139 = panelf5.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(0, 247).addBox(-14.0F, 24.1225F, 23.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 248).addBox(-14.0F, 24.1225F, 22.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r140 = panelf5.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -23.5864F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r141 = panelf5.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(34, 10).addBox(-14.0F, -18.1619F, -29.2849F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r142 = panelf5.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(183, 241).addBox(-8.0F, -6.2872F, 19.353F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.8326F, 0.0F, -3.1416F));

        PartDefinition cube_r143 = panelf5.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(0, 27).mirror().addBox(17.9615F, -5.5966F, 14.3237F, 12.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, 0.9909F, 2.7202F));

        PartDefinition cube_r144 = panelf5.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(0, 27).addBox(-29.9615F, -5.5966F, 14.3237F, 12.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, -0.9909F, -2.7202F));

        PartDefinition cube_r145 = panelf5.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(0, 32).addBox(-5.0F, -18.8535F, 14.3237F, 10.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(-14.0F, -29.8535F, 14.3487F, 28.0F, 8.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, -3.1416F));

        PartDefinition cube_r146 = panelf5.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(8, 54).addBox(-2.0F, -22.995F, 15.7652F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 17).addBox(-3.8F, -22.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 17).addBox(-6.2F, -22.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(0, 27).addBox(2.0F, -22.495F, 15.9652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(26, 17).addBox(-2.1F, -24.995F, 16.4652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
                .texOffs(26, 17).addBox(-0.525F, -19.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 17).addBox(1.475F, -19.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 17).addBox(3.475F, -19.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 17).addBox(-2.525F, -19.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(26, 17).addBox(-4.525F, -19.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(145, 232).addBox(-7.5F, -27.245F, 16.2652F, 5.0F, 4.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(143, 242).addBox(-10.0F, -26.995F, 16.7652F, 20.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition cube_r147 = panelf5.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(26, 17).addBox(-0.35F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.1843F, -16.2392F, -20.8577F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition cube_r148 = panelf5.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(23, 8).addBox(-0.7F, 2.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 15).addBox(-0.2F, 1.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(2, 15).mirror().addBox(-0.3F, 0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false)
                .texOffs(0, 15).addBox(-0.7F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.3164F, -16.2798F, -21.8958F, 0.0F, -1.3963F, -1.5708F));

        PartDefinition cube_r149 = panelf5.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.3286F, -17.7269F, -16.5699F, 1.2889F, 0.8967F, 2.919F));

        PartDefinition cube_r150 = panelf5.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(0, 16).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.3286F, -17.7356F, -16.57F, 1.3902F, -0.2577F, -3.0951F));

        PartDefinition cube_r151 = panelf5.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(4, 10).addBox(-16.0F, 22.517F, -2.0F, 11.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition rwf = panelf5.addOrReplaceChild("rwf", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r152 = rwf.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(21, 17).addBox(3.55F, -23.245F, 16.4652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(21, 17).addBox(5.0F, -23.245F, 16.4652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition cube_r153 = rwf.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(26, 17).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-1.6F, 20.961F, -21.1769F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition bone56 = rwf.addOrReplaceChild("bone56", CubeListBuilder.create(), PartPose.offset(0.0343F, 21.2779F, -20.8577F));

        PartDefinition cube_r154 = bone56.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(1, 16).addBox(0.0F, -1.0F, -0.4F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0157F, -1.0497F, -0.1946F, -1.3264F, -0.7703F, -0.1719F));

        PartDefinition bone54 = rwf.addOrReplaceChild("bone54", CubeListBuilder.create(), PartPose.offset(-1.6F, 20.961F, -21.1769F));

        PartDefinition cube_r155 = bone54.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(1, 16).addBox(0.0F, -1.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.9356F, -0.165F, 1.3264F, -0.7703F, -2.9697F));

        PartDefinition bone55 = rwf.addOrReplaceChild("bone55", CubeListBuilder.create(), PartPose.offset(1.6F, 20.961F, -21.1769F));

        PartDefinition cube_r156 = bone55.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(1, 16).addBox(0.0F, -1.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.9356F, -0.165F, -1.3264F, 0.7703F, 0.1719F));

        PartDefinition alarms = panelf5.addOrReplaceChild("alarms", CubeListBuilder.create(), PartPose.offset(4.5F, -17.1871F, -18.7496F));

        PartDefinition cube_r157 = alarms.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(26, 17).addBox(-5.0F, -22.495F, 16.2652F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.5F, -20.3299F, 18.7496F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition increment = panelf5.addOrReplaceChild("increment", CubeListBuilder.create(), PartPose.offset(0.0F, -17.5318F, -18.8104F));

        PartDefinition cube_r158 = increment.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(53, 77).addBox(-0.5F, -0.5F, -2.75F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition door_lock = panelf5.addOrReplaceChild("door_lock", CubeListBuilder.create(), PartPose.offset(-1.5F, -17.5318F, -18.8104F));

        PartDefinition cube_r159 = door_lock.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(64, 63).addBox(-0.5F, -0.5F, -3.25F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition doors = panelf5.addOrReplaceChild("doors", CubeListBuilder.create(), PartPose.offset(1.5F, -17.2856F, -18.767F));

        PartDefinition cube_r160 = doors.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(64, 63).addBox(-0.5F, -0.5F, -3.5F, 1.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.3963F, 0.0F, -3.1416F));

        PartDefinition thingy_for_later_use = panelf5.addOrReplaceChild("thingy_for_later_use", CubeListBuilder.create(), PartPose.offset(-7.5475F, -17.9618F, -14.144F));

        PartDefinition cube_r161 = thingy_for_later_use.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(193, 127).addBox(-0.5F, -3.0F, 12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(191, 124).addBox(-0.75F, -2.0F, 11.25F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(201, 127).addBox(-0.5F, -4.0F, 12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(201, 127).addBox(-0.5F, -3.0F, 12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.05F))
                .texOffs(199, 124).addBox(0.0F, -4.5F, 12.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(193, 127).addBox(-0.5F, -4.0F, 12.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F))
                .texOffs(201, 124).addBox(-0.5F, -4.25F, 12.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(-6.9461F, 2.9643F, -10.9595F, 0.0F, 0.5236F, 0.0F));

        PartDefinition panelf6 = bone7.addOrReplaceChild("panelf6", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -15.9306F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 255).addBox(-14.0F, -15.4306F, -24.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(26, 100).addBox(-5.5F, -22.0F, -9.5F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.01F))
                .texOffs(24, 100).addBox(-0.5F, -21.5F, -8.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r162 = panelf6.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(90, 4).addBox(-14.0F, -21.8535F, 15.8487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(92, 89).addBox(-14.0F, -21.8535F, 15.4487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 82).addBox(-14.0F, -21.8535F, 16.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.3241F, -0.0002F, -3.1329F));

        PartDefinition cube_r163 = panelf6.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(66, 240).addBox(-10.0F, -25.9158F, 16.4292F, 20.0F, 4.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r164 = panelf6.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(29, 227).addBox(-10.0F, -17.3951F, 21.5158F, 20.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(66, 244).addBox(-10.0F, -16.8951F, 21.2658F, 20.0F, 5.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.5708F, 0.0F, 3.1416F));

        PartDefinition cube_r165 = panelf6.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(0, 238).addBox(-11.0F, 19.5158F, 17.3951F, 22.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r166 = panelf6.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(177, 121).addBox(-0.5F, 29.2534F, -21.9032F, 1.0F, 1.0F, 11.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-0.0644F, -37.5334F, -0.1136F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r167 = panelf6.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(26, 181).addBox(-0.5F, 21.5366F, -29.1165F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r168 = panelf6.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(32, 27).addBox(-0.5F, 19.3203F, -8.5587F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r169 = panelf6.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(179, 122).addBox(-0.5F, 13.8677F, -33.3321F, 1.0F, 1.0F, 10.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.065F, -37.4993F, -0.1105F, 0.2443F, 0.5236F, 0.0F));

        PartDefinition cube_r170 = panelf6.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(0, 247).addBox(-14.0F, 24.1225F, 23.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(0, 248).addBox(-14.0F, 24.1225F, 22.0864F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r171 = panelf6.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(107, 181).addBox(-14.0F, -23.5864F, -25.1225F, 28.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r172 = panelf6.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(34, 10).addBox(-14.0F, -18.1619F, -29.2849F, 28.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 0.0F));

        PartDefinition cube_r173 = panelf6.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(183, 241).addBox(-8.0F, -6.2872F, 19.353F, 16.0F, 3.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.8326F, 0.0F, 3.1416F));

        PartDefinition cube_r174 = panelf6.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(65, 112).addBox(-29.4795F, -4.1636F, 14.3237F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, -0.9909F, -2.7202F));

        PartDefinition cube_r175 = panelf6.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(65, 112).addBox(16.4795F, -4.1636F, 14.3237F, 13.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.0788F, 0.9909F, 2.7202F));

        PartDefinition cube_r176 = panelf6.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(0, 8).addBox(-5.5F, -16.8535F, 14.3237F, 11.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 37).addBox(-5.0F, -24.8535F, 13.3487F, 10.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 77).addBox(-2.75F, -29.8535F, 14.2487F, 6.0F, 14.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(20, 74).addBox(-8.0F, -26.3535F, 13.8487F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(0, 57).addBox(-14.0F, -29.8535F, 14.3487F, 28.0F, 14.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r177 = panelf6.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(32, 36).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, -3.25F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, -4.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, 1.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, 2.5F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, 3.25F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, 4.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(32, 36).addBox(-0.5F, 4.75F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.3195F, -21.8712F, 0.0F, 1.309F, 1.5708F));

        PartDefinition cube_r178 = panelf6.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(25, 17).mirror().addBox(-12.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).mirror(false)
                .texOffs(25, 17).addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-6.1688F, -18.0812F, -16.4526F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r179 = panelf6.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(4, 10).addBox(-16.0F, 22.517F, -2.0F, 11.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition waypoint_slot = panelf6.addOrReplaceChild("waypoint_slot", CubeListBuilder.create(), PartPose.offset(-0.1584F, -19.261F, -13.5796F));

        PartDefinition cube_r180 = waypoint_slot.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(18, 54).addBox(-1.0F, -1.0F, -0.45F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.15F, 0.0F, 0.0F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition thing = panelf6.addOrReplaceChild("thing", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r181 = thing.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(20, 71).addBox(-7.5F, -25.8535F, 12.6987F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.309F, 0.0F, 3.1416F));

        PartDefinition cube_r182 = thing.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(26, 72).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(7.0F, 19.0694F, -21.1382F, 1.1781F, 0.0F, 3.1416F));

        PartDefinition cube_r183 = thing.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(26, 74).addBox(-0.5F, -0.5F, -1.75F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(7.0F, 19.0694F, -21.1382F, 1.0472F, 0.0F, 3.1416F));

        PartDefinition save_waypoint = panelf6.addOrReplaceChild("save_waypoint", CubeListBuilder.create(), PartPose.offset(0.0F, -37.517F, 0.0F));

        PartDefinition cube_r184 = save_waypoint.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(101, 112).addBox(-1.25F, 1.25F, -1.1F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(100, 113).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(107, 117).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.8F, 19.1276F, -15.9462F, 0.9163F, 0.0F, 3.1416F));

        PartDefinition cube_r185 = save_waypoint.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(101, 107).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.3797F, 17.7701F, -10.251F, 2.9228F, -0.0435F, 3.1226F));

        PartDefinition cube_r186 = save_waypoint.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(101, 110).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.4261F, 17.1978F, -11.0697F, 2.1811F, -0.0435F, 3.1226F));

        PartDefinition cube_r187 = save_waypoint.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(101, 110).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-4.4711F, 17.1121F, -12.065F, 1.6575F, -0.0435F, 3.1226F));

        PartDefinition cube_r188 = save_waypoint.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(101, 110).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.8872F, 17.0208F, -13.014F, 1.6577F, 0.0869F, 3.134F));

        PartDefinition cube_r189 = save_waypoint.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(101, 110).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-3.8F, 17.6057F, -13.9628F, 1.4832F, 0.0869F, 3.134F));

        PartDefinition bone63 = save_waypoint.addOrReplaceChild("bone63", CubeListBuilder.create(), PartPose.offset(-3.8F, 18.7309F, -16.2506F));

        PartDefinition cube_r190 = bone63.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(26, 17).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.3967F, 0.3044F, 0.9163F, 0.0F, 3.1416F));

        PartDefinition load_waypoint = panelf6.addOrReplaceChild("load_waypoint", CubeListBuilder.create(), PartPose.offset(1.3755F, -19.6406F, -14.0295F));

        PartDefinition cube_r191 = load_waypoint.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(101, 112).addBox(-1.25F, 1.25F, -1.1F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(100, 113).addBox(-1.0F, 1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(99, 117).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.4245F, 1.2512F, -1.9167F, 0.9163F, 0.0F, 3.1416F));

        PartDefinition cube_r192 = load_waypoint.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(101, 107).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.8448F, -0.1063F, 3.7786F, 2.9228F, -0.0435F, 3.1226F));

        PartDefinition cube_r193 = load_waypoint.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(101, 110).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7983F, -0.6786F, 2.9598F, 2.1811F, -0.0435F, 3.1226F));

        PartDefinition cube_r194 = load_waypoint.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(101, 110).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(1.7534F, -0.7644F, 1.9645F, 1.6575F, -0.0435F, 3.1226F));

        PartDefinition cube_r195 = load_waypoint.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(101, 110).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.3373F, -0.8557F, 1.0155F, 1.6577F, 0.0869F, 3.134F));

        PartDefinition cube_r196 = load_waypoint.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(101, 110).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(2.4245F, -0.2707F, 0.0667F, 1.4832F, 0.0869F, 3.134F));

        PartDefinition bone62 = load_waypoint.addOrReplaceChild("bone62", CubeListBuilder.create(), PartPose.offset(2.4245F, 0.8545F, -2.2211F));

        PartDefinition cube_r197 = bone62.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(26, 17).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.3967F, 0.3044F, 0.9163F, 0.0F, 3.1416F));

        PartDefinition twist = panelf6.addOrReplaceChild("twist", CubeListBuilder.create(), PartPose.offset(-8.4583F, -17.7197F, -19.7158F));

        PartDefinition cube_r198 = twist.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -0.6F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(30, 12).addBox(-0.3F, -0.6F, -0.19F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition cube_r199 = twist.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.2143F, 0.0858F, -0.1221F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition twist3 = panelf6.addOrReplaceChild("twist3", CubeListBuilder.create(), PartPose.offset(8.5417F, -17.6197F, -19.6658F));

        PartDefinition cube_r200 = twist3.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -0.6F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(30, 12).addBox(-0.3F, -0.6F, -0.19F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition cube_r201 = twist3.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.2143F, 0.0859F, -0.1221F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition twist4 = panelf6.addOrReplaceChild("twist4", CubeListBuilder.create(), PartPose.offset(8.8917F, -17.2697F, -21.2658F));

        PartDefinition cube_r202 = twist4.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -0.6F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(30, 12).addBox(-0.3F, -0.6F, -0.19F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition cube_r203 = twist4.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.2143F, 0.0859F, -0.1221F, 1.2086F, 0.7519F, 2.8883F));

        PartDefinition twist2 = panelf6.addOrReplaceChild("twist2", CubeListBuilder.create(), PartPose.offsetAndRotation(-9.0538F, -16.3367F, -21.3145F, 0.0668F, -0.6516F, -0.1098F));

        PartDefinition cube_r204 = twist2.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(0, 32).addBox(-0.5F, -0.6F, -0.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(30, 12).addBox(-0.3F, -0.6F, -0.19F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(0.0955F, -1.083F, 0.0987F, 1.2109F, 0.7538F, 2.901F));

        PartDefinition cube_r205 = twist2.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(0, 30).addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.1188F, -0.9972F, -0.0234F, 1.2109F, 0.7538F, 2.901F));

        PartDefinition rotor = bone7.addOrReplaceChild("rotor", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rotorrings = rotor.addOrReplaceChild("rotorrings", CubeListBuilder.create().texOffs(38, 117).addBox(0.0F, -14.0F, -1.0F, 0.0F, 24.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(45, 160).addBox(-1.5F, -3.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -46.0F, 0.0F));

        PartDefinition cube_r206 = rotorrings.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(42, 117).addBox(0.0F, -18.483F, -1.0F, 0.0F, 24.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone4 = rotorrings.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r207 = bone4.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(207, 232).addBox(-2.0F, 5.517F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(207, 232).addBox(-2.0F, -18.483F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 1.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 3.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -14.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -16.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -0.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -2.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -10.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -12.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -4.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(234, 116).addBox(0.0F, -18.983F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.01F))
                .texOffs(212, 243).addBox(-2.5F, -6.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -8.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r208 = bone4.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(234, 141).addBox(0.0F, -18.983F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 3.1416F, 1.0472F, -3.1416F));

        PartDefinition cube_r209 = bone4.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(234, 141).addBox(0.0F, -18.983F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r210 = bone4.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(234, 141).addBox(0.0F, -18.983F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r211 = bone4.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(234, 116).addBox(0.0F, -18.983F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, -3.1416F, -0.5236F, -3.1416F));

        PartDefinition cube_r212 = bone4.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(234, 116).addBox(0.0F, -18.983F, -5.5F, 0.0F, 25.0F, 11.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, -3.1416F, 0.5236F, 3.1416F));

        PartDefinition bone45 = rotorrings.addOrReplaceChild("bone45", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r213 = bone45.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(207, 232).addBox(-2.0F, 5.517F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(207, 232).addBox(-2.0F, -18.483F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 1.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 3.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -14.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -16.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -0.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -2.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -10.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -12.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -4.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(212, 243).addBox(-2.5F, -6.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -8.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone46 = rotorrings.addOrReplaceChild("bone46", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r214 = bone46.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(207, 232).addBox(-2.0F, 5.517F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(207, 232).addBox(-2.0F, -18.483F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 1.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 3.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -14.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -16.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -0.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -2.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -10.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -12.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -4.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(212, 243).addBox(-2.5F, -6.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -8.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone47 = rotorrings.addOrReplaceChild("bone47", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r215 = bone47.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(207, 232).addBox(-2.0F, 5.517F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(207, 232).addBox(-2.0F, -18.483F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 1.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 3.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -14.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -16.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -0.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -2.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -10.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -12.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -4.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(212, 243).addBox(-2.5F, -6.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -8.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone48 = rotorrings.addOrReplaceChild("bone48", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r216 = bone48.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(207, 232).addBox(-2.0F, 5.517F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(207, 232).addBox(-2.0F, -18.483F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 1.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 3.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -14.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -16.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -0.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -2.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -10.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -12.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -4.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(212, 243).addBox(-2.5F, -6.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -8.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone49 = rotorrings.addOrReplaceChild("bone49", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r217 = bone49.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(207, 232).addBox(-2.0F, 5.517F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(207, 232).addBox(-2.0F, -18.483F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 1.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, 3.517F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -14.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(217, 233).addBox(-2.0F, -16.483F, -3.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -0.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -2.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -10.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(244, 247).addBox(-2.0F, -12.483F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -4.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(212, 243).addBox(-2.5F, -6.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F))
                .texOffs(233, 251).addBox(-2.5F, -8.483F, -4.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 0.483F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone51 = rotorrings.addOrReplaceChild("bone51", CubeListBuilder.create(), PartPose.offset(0.0F, -13.5F, 0.0F));

        PartDefinition cube_r218 = bone51.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 18.483F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 17.983F, 0.0F, 0.0F, 1.0472F, 3.1416F));

        PartDefinition cube_r219 = bone51.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 18.483F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 17.983F, 0.0F, -3.1416F, 1.0472F, 0.0F));

        PartDefinition cube_r220 = bone51.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 18.483F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 17.983F, 0.0F, -3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r221 = bone51.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 18.483F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 17.983F, 0.0F, -3.1416F, -1.0472F, 0.0F));

        PartDefinition cube_r222 = bone51.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 18.483F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 17.983F, 0.0F, 0.0F, -1.0472F, -3.1416F));

        PartDefinition cube_r223 = bone51.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 18.483F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 17.983F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone52 = rotorrings.addOrReplaceChild("bone52", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 13.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r224 = bone52.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 5.517F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 9.017F, 0.0F, 0.0F, 1.0472F, 3.1416F));

        PartDefinition cube_r225 = bone52.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 5.517F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 9.017F, 0.0F, -3.1416F, 1.0472F, 0.0F));

        PartDefinition cube_r226 = bone52.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 5.517F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 9.017F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r227 = bone52.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 5.517F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 9.017F, 0.0F, -3.1416F, -1.0472F, 0.0F));

        PartDefinition cube_r228 = bone52.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 5.517F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 9.017F, 0.0F, 0.0F, -1.0472F, -3.1416F));

        PartDefinition cube_r229 = bone52.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(50, 129).addBox(-1.5F, 5.517F, -2.6F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, 9.017F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition rotorlight = rotorrings.addOrReplaceChild("rotorlight", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition cube_r230 = rotorlight.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(251, 177).addBox(-0.5F, -18.483F, -0.5F, 1.0F, 24.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.983F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition rotorf = rotor.addOrReplaceChild("rotorf", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, 0.0F));

        PartDefinition cube_r231 = rotorf.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(251, 210).addBox(-0.5F, -8.517F, -0.85F, 1.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.0472F, 3.1416F));

        PartDefinition bone33 = rotorf.addOrReplaceChild("bone33", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r232 = bone33.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone34 = rotorf.addOrReplaceChild("bone34", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r233 = bone34.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone35 = rotorf.addOrReplaceChild("bone35", CubeListBuilder.create(), PartPose.offset(-6.7024F, -24.9653F, 0.0F));

        PartDefinition cube_r234 = bone35.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(68, 134).addBox(0.3165F, -9.5874F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 249).addBox(-0.803F, 22.9373F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r235 = bone35.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(87, 226).addBox(-35.2353F, 0.2301F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(62, 225).addBox(-35.4815F, 1.1367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition cube_r236 = bone35.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(116, 231).addBox(-26.7142F, -12.3461F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r237 = bone35.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(112, 231).addBox(-13.9008F, -25.5895F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r238 = bone35.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(106, 231).addBox(-30.1146F, 7.246F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(144, 104).addBox(10.0F, 4.4674F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition glass_r1 = bone35.addOrReplaceChild("glass_r1", CubeListBuilder.create().texOffs(-6, 249).addBox(-23.4274F, 5.197F, -3.0F, 33.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition lightblinkythingidk3 = bone35.addOrReplaceChild("lightblinkythingidk3", CubeListBuilder.create().texOffs(62, 216).addBox(-35.4815F, 1.0367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone = bone35.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6888F, 2.1584F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r239 = bone.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(221, 249).addBox(6.4928F, 8.8136F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 250).addBox(6.7428F, 8.3136F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 239).addBox(8.7428F, 7.8136F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.969F, -13.3557F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lamp1 = bone.addOrReplaceChild("lamp1", CubeListBuilder.create(), PartPose.offset(-0.0453F, -3.7908F, 0.0F));

        PartDefinition cube_r240 = lamp1.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(210, 223).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition bone36 = rotorf.addOrReplaceChild("bone36", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4641F, 0.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r241 = bone36.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(141, 154).addBox(20.3191F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 2.0944F, 0.0F, -1.5708F));

        PartDefinition cube_r242 = bone36.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(141, 154).addBox(7.4309F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r243 = bone36.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(20, 142).addBox(-0.7247F, -9.5559F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r244 = bone36.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(144, 135).addBox(15.062F, 4.42F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 133).addBox(10.062F, 4.42F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition cube_r245 = bone36.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(138, 147).addBox(-35.3903F, 1.3033F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create(), PartPose.offset(0.0747F, -30.0862F, 0.0F));

        PartDefinition cube_r246 = bone37.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(212, 238).addBox(3.6809F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9253F, -7.4309F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone39 = bone36.addOrReplaceChild("bone39", CubeListBuilder.create(), PartPose.offset(0.0747F, -57.8362F, 0.0F));

        PartDefinition cube_r247 = bone39.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(212, 238).mirror().addBox(-21.5691F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.9253F, 20.3191F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition rotorf2 = rotor.addOrReplaceChild("rotorf2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r248 = rotorf2.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(253, 210).addBox(-0.5F, -8.517F, -0.85F, 1.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.0472F, -3.1416F));

        PartDefinition bone2 = rotorf2.addOrReplaceChild("bone2", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r249 = bone2.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone3 = rotorf2.addOrReplaceChild("bone3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r250 = bone3.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone5 = rotorf2.addOrReplaceChild("bone5", CubeListBuilder.create(), PartPose.offset(-6.7024F, -24.9653F, 0.0F));

        PartDefinition cube_r251 = bone5.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(68, 134).addBox(0.3165F, -9.5773F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 249).addBox(-0.803F, 22.9274F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r252 = bone5.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(87, 226).addBox(-35.2353F, 0.2301F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(151, 231).addBox(-35.4815F, 1.1367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition glass_r2 = bone5.addOrReplaceChild("glass_r2", CubeListBuilder.create().texOffs(-6, 249).addBox(-23.4274F, 5.197F, -3.0F, 33.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r253 = bone5.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(144, 104).addBox(10.0F, 4.4674F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6888F, 2.1584F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r254 = bone6.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(221, 249).addBox(6.4928F, 8.8136F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 250).addBox(6.7428F, 8.3136F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 239).addBox(8.7428F, 7.8136F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.969F, -13.3557F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lamp2 = bone6.addOrReplaceChild("lamp2", CubeListBuilder.create(), PartPose.offset(-0.0453F, -3.7908F, 0.0F));

        PartDefinition cube_r255 = lamp2.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(210, 223).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition bone8 = rotorf2.addOrReplaceChild("bone8", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4641F, 0.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r256 = bone8.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(141, 154).addBox(20.3191F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, -2.0944F, 0.0F, -1.5708F));

        PartDefinition cube_r257 = bone8.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(141, 154).addBox(7.4309F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r258 = bone8.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(20, 142).addBox(-0.7247F, -9.5559F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r259 = bone8.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(144, 135).addBox(15.062F, 4.42F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 133).addBox(10.062F, 4.42F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition cube_r260 = bone8.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(138, 147).addBox(-35.3903F, 1.3033F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create(), PartPose.offset(0.0747F, -30.0862F, 0.0F));

        PartDefinition cube_r261 = bone9.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(212, 238).addBox(3.6809F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9253F, -7.4309F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone10 = bone8.addOrReplaceChild("bone10", CubeListBuilder.create(), PartPose.offset(0.0747F, -57.8362F, 0.0F));

        PartDefinition cube_r262 = bone10.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(212, 238).mirror().addBox(-21.5691F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.9253F, 20.3191F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition rotorf3 = rotor.addOrReplaceChild("rotorf3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r263 = rotorf3.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(251, 210).addBox(-0.5F, -8.517F, -0.85F, 1.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.0472F, 3.1416F));

        PartDefinition bone11 = rotorf3.addOrReplaceChild("bone11", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r264 = bone11.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone12 = rotorf3.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r265 = bone12.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone13 = rotorf3.addOrReplaceChild("bone13", CubeListBuilder.create(), PartPose.offset(-6.7024F, -24.9653F, 0.0F));

        PartDefinition cube_r266 = bone13.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(68, 134).addBox(0.3165F, -9.5874F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 249).addBox(-0.803F, 22.9373F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r267 = bone13.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(87, 226).addBox(-35.2353F, 0.2301F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(62, 225).addBox(-35.4815F, 1.1367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition cube_r268 = bone13.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(116, 231).addBox(-26.7142F, -12.3461F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r269 = bone13.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(112, 231).addBox(-13.9008F, -25.5895F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r270 = bone13.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(106, 231).addBox(-30.1146F, 7.246F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(144, 104).addBox(10.0F, 4.4674F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition glass_r3 = bone13.addOrReplaceChild("glass_r3", CubeListBuilder.create().texOffs(-6, 249).addBox(-23.4274F, 5.197F, -3.0F, 33.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone14 = bone13.addOrReplaceChild("bone14", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6888F, 2.1584F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r271 = bone14.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(221, 249).addBox(6.4928F, 8.8136F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 250).addBox(6.7428F, 8.3136F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 239).addBox(8.7428F, 7.8136F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.969F, -13.3557F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lamp3 = bone14.addOrReplaceChild("lamp3", CubeListBuilder.create(), PartPose.offset(-0.0453F, -3.7908F, 0.0F));

        PartDefinition cube_r272 = lamp3.addOrReplaceChild("cube_r272", CubeListBuilder.create().texOffs(210, 223).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lightblinkythingidk2 = bone13.addOrReplaceChild("lightblinkythingidk2", CubeListBuilder.create().texOffs(62, 216).addBox(-35.4815F, 1.0367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone15 = rotorf3.addOrReplaceChild("bone15", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4641F, 0.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r273 = bone15.addOrReplaceChild("cube_r273", CubeListBuilder.create().texOffs(141, 154).addBox(20.3191F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r274 = bone15.addOrReplaceChild("cube_r274", CubeListBuilder.create().texOffs(141, 154).addBox(7.4309F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r275 = bone15.addOrReplaceChild("cube_r275", CubeListBuilder.create().texOffs(20, 142).addBox(-0.7247F, -9.5559F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r276 = bone15.addOrReplaceChild("cube_r276", CubeListBuilder.create().texOffs(144, 135).addBox(15.062F, 4.42F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 133).addBox(10.062F, 4.42F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition cube_r277 = bone15.addOrReplaceChild("cube_r277", CubeListBuilder.create().texOffs(138, 147).addBox(-35.3903F, 1.3033F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create(), PartPose.offset(0.0747F, -30.0862F, 0.0F));

        PartDefinition cube_r278 = bone16.addOrReplaceChild("cube_r278", CubeListBuilder.create().texOffs(212, 238).addBox(3.6809F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9253F, -7.4309F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone17 = bone15.addOrReplaceChild("bone17", CubeListBuilder.create(), PartPose.offset(0.0747F, -57.8362F, 0.0F));

        PartDefinition cube_r279 = bone17.addOrReplaceChild("cube_r279", CubeListBuilder.create().texOffs(212, 238).mirror().addBox(-21.5691F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.9253F, 20.3191F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition rotorf4 = rotor.addOrReplaceChild("rotorf4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r280 = rotorf4.addOrReplaceChild("cube_r280", CubeListBuilder.create().texOffs(253, 210).addBox(-0.5F, -8.517F, -0.85F, 1.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.0472F, 3.1416F));

        PartDefinition bone18 = rotorf4.addOrReplaceChild("bone18", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r281 = bone18.addOrReplaceChild("cube_r281", CubeListBuilder.create().texOffs(56, 56).addBox(31.5679F, -2.683F, -2.91F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -1.5708F, -0.1309F, 1.5708F));

        PartDefinition cube_r282 = bone18.addOrReplaceChild("cube_r282", CubeListBuilder.create().texOffs(26, 71).addBox(-38.5597F, 1.621F, -2.9364F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.7811F, 0.0924F, -1.6636F));

        PartDefinition cube_r283 = bone18.addOrReplaceChild("cube_r283", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.3326F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 3.1416F, 0.0F, -1.5708F));

        PartDefinition cube_r284 = bone18.addOrReplaceChild("cube_r284", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.2426F, -3.09F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0001F))
                .texOffs(50, 27).addBox(-31.483F, -6.0357F, -2.383F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -1.5708F, 0.0F, -1.5708F));

        PartDefinition cube_r285 = bone18.addOrReplaceChild("cube_r285", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.3063F, -3.0636F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(50, 27).addBox(-31.483F, -6.1184F, -2.4173F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -2.3562F, 0.0F, -1.5708F));

        PartDefinition cube_r286 = bone18.addOrReplaceChild("cube_r286", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.3063F, -2.9364F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(50, 27).addBox(-31.483F, 5.953F, -2.5827F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 2.3562F, 0.0F, -1.5708F));

        PartDefinition cube_r287 = bone18.addOrReplaceChild("cube_r287", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.2426F, -2.91F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0001F))
                .texOffs(50, 27).addBox(-31.483F, -6.0357F, -2.617F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 1.5708F, 0.0F, -1.5708F));

        PartDefinition cube_r288 = bone18.addOrReplaceChild("cube_r288", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.179F, -2.9364F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(50, 27).addBox(-31.483F, -5.953F, -2.5827F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.7854F, 0.0F, -1.5708F));

        PartDefinition cube_r289 = bone18.addOrReplaceChild("cube_r289", CubeListBuilder.create().texOffs(42, 27).addBox(-30.483F, 6.179F, -3.0636F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(50, 27).addBox(-31.483F, 6.1184F, -2.4173F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -0.7854F, 0.0F, -1.5708F));

        PartDefinition cube_r290 = bone18.addOrReplaceChild("cube_r290", CubeListBuilder.create().texOffs(50, 27).addBox(-31.483F, -5.9187F, -2.5F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F))
                .texOffs(50, 27).addBox(-31.483F, 6.1527F, -2.5F, 3.0F, 0.0F, 5.0F, new CubeDeformation(0.0001F))
                .texOffs(42, 27).addBox(-30.483F, 6.1527F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0001F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r291 = bone18.addOrReplaceChild("cube_r291", CubeListBuilder.create().texOffs(26, 71).addBox(-38.5563F, 1.5948F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, -1.7017F));

        PartDefinition cube_r292 = bone18.addOrReplaceChild("cube_r292", CubeListBuilder.create().texOffs(0, 71).addBox(31.5681F, -2.6843F, -3.0903F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 1.5708F, 0.1309F, 1.5708F));

        PartDefinition cube_r293 = bone18.addOrReplaceChild("cube_r293", CubeListBuilder.create().texOffs(26, 71).addBox(-38.5598F, 1.6212F, -3.0643F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -0.7811F, -0.0924F, -1.6636F));

        PartDefinition cube_r294 = bone18.addOrReplaceChild("cube_r294", CubeListBuilder.create().texOffs(56, 56).addBox(31.5765F, -2.7481F, -2.9364F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -2.3605F, -0.0924F, 1.6636F));

        PartDefinition cube_r295 = bone18.addOrReplaceChild("cube_r295", CubeListBuilder.create().texOffs(56, 56).addBox(31.5799F, -2.7742F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 3.1416F, 0.0F, 1.7017F));

        PartDefinition cube_r296 = bone18.addOrReplaceChild("cube_r296", CubeListBuilder.create().texOffs(56, 20).addBox(20.885F, -10.5102F, -2.91F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -1.5708F, 0.1309F, 1.5708F));

        PartDefinition cube_r297 = bone18.addOrReplaceChild("cube_r297", CubeListBuilder.create().texOffs(56, 20).addBox(20.8848F, -10.5114F, -3.0903F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 1.5708F, -0.1309F, 1.5708F));

        PartDefinition cube_r298 = bone18.addOrReplaceChild("cube_r298", CubeListBuilder.create().texOffs(56, 0).addBox(-27.8931F, 9.4484F, -3.0643F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -0.7811F, 0.0924F, -1.478F));

        PartDefinition cube_r299 = bone18.addOrReplaceChild("cube_r299", CubeListBuilder.create().texOffs(56, 0).addBox(-27.8966F, 9.422F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, -1.4399F));

        PartDefinition cube_r300 = bone18.addOrReplaceChild("cube_r300", CubeListBuilder.create().texOffs(56, 0).addBox(-27.8932F, 9.4481F, -2.9364F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.7811F, -0.0924F, -1.478F));

        PartDefinition cube_r301 = bone18.addOrReplaceChild("cube_r301", CubeListBuilder.create().texOffs(56, 20).addBox(20.8764F, -10.5752F, -2.9364F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -2.3605F, 0.0924F, 1.478F));

        PartDefinition cube_r302 = bone18.addOrReplaceChild("cube_r302", CubeListBuilder.create().texOffs(56, 70).addBox(31.5764F, 1.7478F, -2.9357F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -0.7811F, 0.0924F, 1.6636F));

        PartDefinition cube_r303 = bone18.addOrReplaceChild("cube_r303", CubeListBuilder.create().texOffs(56, 20).addBox(20.8765F, -10.5749F, -3.0643F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 2.3605F, -0.0924F, 1.478F));

        PartDefinition cube_r304 = bone18.addOrReplaceChild("cube_r304", CubeListBuilder.create().texOffs(56, 20).addBox(20.873F, -10.6013F, -3.0F, 7.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, -3.1416F, 0.0F, 1.4399F));

        PartDefinition cube_r305 = bone18.addOrReplaceChild("cube_r305", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone19 = rotorf4.addOrReplaceChild("bone19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r306 = bone19.addOrReplaceChild("cube_r306", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone20 = rotorf4.addOrReplaceChild("bone20", CubeListBuilder.create(), PartPose.offset(-6.7024F, -24.9653F, 0.0F));

        PartDefinition cube_r307 = bone20.addOrReplaceChild("cube_r307", CubeListBuilder.create().texOffs(68, 134).addBox(0.3165F, -9.5773F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 249).addBox(-0.803F, 22.9274F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r308 = bone20.addOrReplaceChild("cube_r308", CubeListBuilder.create().texOffs(87, 226).addBox(-35.2353F, 0.2301F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(151, 231).addBox(-35.4815F, 1.1367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition glass_r4 = bone20.addOrReplaceChild("glass_r4", CubeListBuilder.create().texOffs(-6, 249).addBox(-23.4274F, 5.197F, -3.0F, 33.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r309 = bone20.addOrReplaceChild("cube_r309", CubeListBuilder.create().texOffs(144, 104).addBox(10.0F, 4.4674F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6888F, 2.1584F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r310 = bone21.addOrReplaceChild("cube_r310", CubeListBuilder.create().texOffs(221, 249).addBox(6.4928F, 8.8136F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 250).addBox(6.7428F, 8.3136F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 239).addBox(8.7428F, 7.8136F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.969F, -13.3557F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lamp4 = bone21.addOrReplaceChild("lamp4", CubeListBuilder.create(), PartPose.offset(-0.0453F, -3.7908F, 0.0F));

        PartDefinition cube_r311 = lamp4.addOrReplaceChild("cube_r311", CubeListBuilder.create().texOffs(210, 223).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition bone22 = rotorf4.addOrReplaceChild("bone22", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4641F, 0.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r312 = bone22.addOrReplaceChild("cube_r312", CubeListBuilder.create().texOffs(141, 154).addBox(20.3191F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 2.0944F, 0.0F, -1.5708F));

        PartDefinition cube_r313 = bone22.addOrReplaceChild("cube_r313", CubeListBuilder.create().texOffs(141, 154).addBox(7.4309F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r314 = bone22.addOrReplaceChild("cube_r314", CubeListBuilder.create().texOffs(20, 142).addBox(-0.7247F, -9.5559F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r315 = bone22.addOrReplaceChild("cube_r315", CubeListBuilder.create().texOffs(144, 135).addBox(15.062F, 4.42F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 133).addBox(10.062F, 4.42F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition cube_r316 = bone22.addOrReplaceChild("cube_r316", CubeListBuilder.create().texOffs(138, 147).addBox(-35.3903F, 1.3033F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create(), PartPose.offset(0.0747F, -30.0862F, 0.0F));

        PartDefinition cube_r317 = bone23.addOrReplaceChild("cube_r317", CubeListBuilder.create().texOffs(212, 238).addBox(3.6809F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9253F, -7.4309F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone24 = bone22.addOrReplaceChild("bone24", CubeListBuilder.create(), PartPose.offset(0.0747F, -57.8362F, 0.0F));

        PartDefinition cube_r318 = bone24.addOrReplaceChild("cube_r318", CubeListBuilder.create().texOffs(212, 238).mirror().addBox(-21.5691F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.9253F, 20.3191F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition rotorf5 = rotor.addOrReplaceChild("rotorf5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r319 = rotorf5.addOrReplaceChild("cube_r319", CubeListBuilder.create().texOffs(251, 210).addBox(-0.5F, -8.517F, -0.85F, 1.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.0472F, 3.1416F));

        PartDefinition bone25 = rotorf5.addOrReplaceChild("bone25", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r320 = bone25.addOrReplaceChild("cube_r320", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone26 = rotorf5.addOrReplaceChild("bone26", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r321 = bone26.addOrReplaceChild("cube_r321", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone27 = rotorf5.addOrReplaceChild("bone27", CubeListBuilder.create(), PartPose.offset(-6.7024F, -24.9653F, 0.0F));

        PartDefinition cube_r322 = bone27.addOrReplaceChild("cube_r322", CubeListBuilder.create().texOffs(68, 134).addBox(0.3165F, -9.5874F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 249).addBox(-0.803F, 22.9373F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r323 = bone27.addOrReplaceChild("cube_r323", CubeListBuilder.create().texOffs(87, 226).addBox(-35.2353F, 0.2301F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(62, 225).addBox(-35.4815F, 1.1367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition cube_r324 = bone27.addOrReplaceChild("cube_r324", CubeListBuilder.create().texOffs(116, 231).addBox(-26.7142F, -12.3461F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r325 = bone27.addOrReplaceChild("cube_r325", CubeListBuilder.create().texOffs(112, 231).addBox(-13.9008F, -25.5895F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition cube_r326 = bone27.addOrReplaceChild("cube_r326", CubeListBuilder.create().texOffs(106, 231).addBox(-30.1146F, 7.246F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(144, 104).addBox(10.0F, 4.4674F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition glass_r5 = bone27.addOrReplaceChild("glass_r5", CubeListBuilder.create().texOffs(-6, 249).addBox(-23.4274F, 5.197F, -3.0F, 33.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6888F, 2.1584F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r327 = bone28.addOrReplaceChild("cube_r327", CubeListBuilder.create().texOffs(221, 249).addBox(6.4928F, 8.8136F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 250).addBox(6.7428F, 8.3136F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 239).addBox(8.7428F, 7.8136F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.969F, -13.3557F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lamp5 = bone28.addOrReplaceChild("lamp5", CubeListBuilder.create(), PartPose.offset(-0.0453F, -3.7908F, 0.0F));

        PartDefinition cube_r328 = lamp5.addOrReplaceChild("cube_r328", CubeListBuilder.create().texOffs(210, 223).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lightblinkythingidk = bone27.addOrReplaceChild("lightblinkythingidk", CubeListBuilder.create().texOffs(62, 216).addBox(-35.4815F, 1.0367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone29 = rotorf5.addOrReplaceChild("bone29", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4641F, 0.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r329 = bone29.addOrReplaceChild("cube_r329", CubeListBuilder.create().texOffs(141, 154).addBox(20.3191F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, -2.0944F, 0.0F, -1.5708F));

        PartDefinition cube_r330 = bone29.addOrReplaceChild("cube_r330", CubeListBuilder.create().texOffs(141, 154).addBox(7.4309F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r331 = bone29.addOrReplaceChild("cube_r331", CubeListBuilder.create().texOffs(20, 142).addBox(-0.7247F, -9.5559F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r332 = bone29.addOrReplaceChild("cube_r332", CubeListBuilder.create().texOffs(144, 135).addBox(15.062F, 4.42F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 133).addBox(10.062F, 4.42F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition cube_r333 = bone29.addOrReplaceChild("cube_r333", CubeListBuilder.create().texOffs(138, 147).addBox(-35.3903F, 1.3033F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create(), PartPose.offset(0.0747F, -30.0862F, 0.0F));

        PartDefinition cube_r334 = bone30.addOrReplaceChild("cube_r334", CubeListBuilder.create().texOffs(212, 238).addBox(3.6809F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9253F, -7.4309F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone31 = bone29.addOrReplaceChild("bone31", CubeListBuilder.create(), PartPose.offset(0.0747F, -57.8362F, 0.0F));

        PartDefinition cube_r335 = bone31.addOrReplaceChild("cube_r335", CubeListBuilder.create().texOffs(212, 238).mirror().addBox(-21.5691F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.9253F, 20.3191F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition rotorf6 = rotor.addOrReplaceChild("rotorf6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r336 = rotorf6.addOrReplaceChild("cube_r336", CubeListBuilder.create().texOffs(253, 210).addBox(-0.5F, -8.517F, -0.85F, 1.0F, 30.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -37.517F, 0.0F, 0.0F, -1.0472F, 3.1416F));

        PartDefinition bone32 = rotorf6.addOrReplaceChild("bone32", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition cube_r337 = bone32.addOrReplaceChild("cube_r337", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone38 = rotorf6.addOrReplaceChild("bone38", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r338 = bone38.addOrReplaceChild("cube_r338", CubeListBuilder.create().texOffs(144, 110).addBox(16.983F, 6.3336F, -2.0F, 8.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone40 = rotorf6.addOrReplaceChild("bone40", CubeListBuilder.create(), PartPose.offset(-6.7024F, -24.9653F, 0.0F));

        PartDefinition cube_r339 = bone40.addOrReplaceChild("cube_r339", CubeListBuilder.create().texOffs(68, 134).addBox(0.3165F, -9.5773F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(60, 249).addBox(-0.803F, 22.9274F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r340 = bone40.addOrReplaceChild("cube_r340", CubeListBuilder.create().texOffs(87, 226).addBox(-35.2353F, 0.2301F, -4.0F, 1.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(151, 231).addBox(-35.4815F, 1.1367F, -4.5F, 12.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition glass_r6 = bone40.addOrReplaceChild("glass_r6", CubeListBuilder.create().texOffs(-6, 249).addBox(-23.4274F, 5.197F, -3.0F, 33.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r341 = bone40.addOrReplaceChild("cube_r341", CubeListBuilder.create().texOffs(144, 104).addBox(10.0F, 4.4674F, -3.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.7024F, -12.5518F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create(), PartPose.offsetAndRotation(0.6888F, 2.1584F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r342 = bone41.addOrReplaceChild("cube_r342", CubeListBuilder.create().texOffs(221, 249).addBox(6.4928F, 8.8136F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 250).addBox(6.7428F, 8.3136F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(40, 239).addBox(8.7428F, 7.8136F, -2.0F, 6.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.969F, -13.3557F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition lamp6 = bone41.addOrReplaceChild("lamp6", CubeListBuilder.create(), PartPose.offset(-0.0453F, -3.7908F, 0.0F));

        PartDefinition cube_r343 = lamp6.addOrReplaceChild("cube_r343", CubeListBuilder.create().texOffs(210, 223).addBox(-2.0F, -1.5F, -1.5F, 4.0F, 3.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.4835F));

        PartDefinition bone42 = rotorf6.addOrReplaceChild("bone42", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.4641F, 0.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r344 = bone42.addOrReplaceChild("cube_r344", CubeListBuilder.create().texOffs(141, 154).addBox(20.3191F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r345 = bone42.addOrReplaceChild("cube_r345", CubeListBuilder.create().texOffs(141, 154).addBox(7.4309F, 2.3253F, -2.5F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition cube_r346 = bone42.addOrReplaceChild("cube_r346", CubeListBuilder.create().texOffs(20, 142).addBox(-0.7247F, -9.5559F, -0.5F, 7.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 3.1416F));

        PartDefinition cube_r347 = bone42.addOrReplaceChild("cube_r347", CubeListBuilder.create().texOffs(144, 135).addBox(15.062F, 4.42F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(142, 133).addBox(10.062F, 4.42F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.6581F));

        PartDefinition cube_r348 = bone42.addOrReplaceChild("cube_r348", CubeListBuilder.create().texOffs(138, 147).addBox(-35.3903F, 1.3033F, -0.5F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -37.517F, 0.0F, 0.0F, 0.0F, 1.3963F));

        PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create(), PartPose.offset(0.0747F, -30.0862F, 0.0F));

        PartDefinition cube_r349 = bone43.addOrReplaceChild("cube_r349", CubeListBuilder.create().texOffs(212, 238).addBox(3.6809F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.9253F, -7.4309F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone44 = bone42.addOrReplaceChild("bone44", CubeListBuilder.create(), PartPose.offset(0.0747F, -57.8362F, 0.0F));

        PartDefinition cube_r350 = bone44.addOrReplaceChild("cube_r350", CubeListBuilder.create().texOffs(212, 238).mirror().addBox(-21.5691F, 3.9253F, -1.5F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(3.9253F, 20.3191F, 0.0F, 0.0F, 0.0F, 1.5708F));

        PartDefinition coffeemachinetwo = bone7.addOrReplaceChild("coffeemachinetwo", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -21.8117F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition coffeemachine = coffeemachinetwo.addOrReplaceChild("coffeemachine", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 13.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r351 = coffeemachine.addOrReplaceChild("cube_r351", CubeListBuilder.create().texOffs(198, 248).addBox(7.5535F, -21.7053F, 8.4047F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.15F))
                .texOffs(225, 248).addBox(7.4535F, -21.7053F, 8.4047F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(12.0248F, -19.7053F, -0.0248F, 0.0F, 1.0472F, -3.1416F));

        PartDefinition cube_r352 = coffeemachine.addOrReplaceChild("cube_r352", CubeListBuilder.create().texOffs(198, 248).addBox(-4.4465F, -21.7053F, 8.3799F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.16F))
                .texOffs(225, 248).addBox(-4.5465F, -21.7053F, 8.3799F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0248F, -19.7053F, -0.0248F, -3.1416F, 1.0472F, 0.0F));

        PartDefinition cube_r353 = coffeemachine.addOrReplaceChild("cube_r353", CubeListBuilder.create().texOffs(198, 248).addBox(-10.425F, -21.7053F, -2.0248F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.15F))
                .texOffs(225, 248).addBox(-10.525F, -21.7053F, -2.0248F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(12.0248F, -19.7053F, -0.0248F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r354 = coffeemachine.addOrReplaceChild("cube_r354", CubeListBuilder.create().texOffs(198, 248).addBox(-4.4035F, -21.7053F, -12.4047F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.16F))
                .texOffs(225, 248).addBox(-4.5035F, -21.7053F, -12.4047F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0248F, -19.7053F, -0.0248F, -3.1416F, -1.0472F, 0.0F));

        PartDefinition cube_r355 = coffeemachine.addOrReplaceChild("cube_r355", CubeListBuilder.create().texOffs(198, 248).addBox(7.5965F, -21.7053F, -12.3799F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.15F))
                .texOffs(225, 248).addBox(7.4965F, -21.7053F, -12.3799F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(12.0248F, -19.7053F, -0.0248F, 0.0F, -1.0472F, -3.1416F));

        PartDefinition cube_r356 = coffeemachine.addOrReplaceChild("cube_r356", CubeListBuilder.create().texOffs(198, 248).addBox(13.575F, -21.7053F, -1.9752F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.16F))
                .texOffs(225, 248).addBox(13.475F, -21.7053F, -1.9752F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0248F, -19.7053F, -0.0248F, 0.0F, 0.0F, -3.1416F));

        PartDefinition top = coffeemachine.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0248F, 0.0F, 0.0F));

        PartDefinition cube_r357 = top.addOrReplaceChild("cube_r357", CubeListBuilder.create().texOffs(107, 247).addBox(9.45F, -21.9553F, -2.4752F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -19.7053F, -0.0248F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone53 = bone7.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(213, 63).addBox(-7.5F, -34.483F, -6.5F, 15.0F, 0.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -41.517F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r358 = bone53.addOrReplaceChild("cube_r358", CubeListBuilder.create().texOffs(214, 34).addBox(-7.5F, 0.0F, -6.5F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 28.267F, -0.4F, 0.0F, 0.0F, 0.0175F));

        PartDefinition cube_r359 = bone53.addOrReplaceChild("cube_r359", CubeListBuilder.create().texOffs(237, 52).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -20.483F, 0.0F, 0.0F, 0.0F, 0.0175F));

        PartDefinition cube_r360 = bone53.addOrReplaceChild("cube_r360", CubeListBuilder.create().texOffs(237, 52).addBox(-2.5F, 0.0F, -3.5F, 5.0F, 0.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.517F, 0.0F, 0.0F, 0.0F, 0.0175F));

        PartDefinition cube_r361 = bone53.addOrReplaceChild("cube_r361", CubeListBuilder.create().texOffs(211, 48).addBox(-7.5F, 0.0F, -7.5F, 15.0F, 0.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 37.517F, 0.0F, 0.0F, 0.0F, 0.0175F));

        PartDefinition cube_r362 = bone53.addOrReplaceChild("cube_r362", CubeListBuilder.create().texOffs(230, 237).addBox(1.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.15F))
                .texOffs(230, 229).addBox(-4.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.15F)), PartPose.offsetAndRotation(0.0F, 19.017F, -14.0F, -0.3927F, 0.0F, -0.0175F));

        PartDefinition cube_r363 = bone53.addOrReplaceChild("cube_r363", CubeListBuilder.create().texOffs(250, 205).addBox(-1.0F, 21.927F, -8.1568F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(250, 202).addBox(-1.0F, 21.927F, -8.1568F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(224, 221).addBox(-4.5F, 20.927F, -7.6568F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r364 = bone53.addOrReplaceChild("cube_r364", CubeListBuilder.create().texOffs(240, 229).addBox(-5.0F, 11.2119F, -21.2118F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition throttle = bone53.addOrReplaceChild("throttle", CubeListBuilder.create(), PartPose.offset(0.0F, 19.017F, -14.0F));

        PartDefinition cube_r365 = throttle.addOrReplaceChild("cube_r365", CubeListBuilder.create().texOffs(4, 10).addBox(-5.75F, -6.1568F, -18.877F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(233, 245).addBox(-5.75F, -6.1568F, -21.077F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.15F))
                .texOffs(240, 239).addBox(-5.5F, -6.1568F, -23.427F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(240, 236).addBox(-5.25F, -6.4068F, -22.927F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -19.017F, 14.0F, 1.1781F, 0.0F, 0.0F));

        PartDefinition refueler = bone53.addOrReplaceChild("refueler", CubeListBuilder.create(), PartPose.offset(0.0F, 17.9647F, -16.5407F));

        PartDefinition cube_r366 = refueler.addOrReplaceChild("cube_r366", CubeListBuilder.create().texOffs(252, 208).addBox(15.7119F, 15.7119F, -8.4068F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -17.9647F, 16.5407F, -0.2849F, 0.274F, 0.7459F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        matrices.mulPose(Axis.YN.rotationDegrees(180f));
        console.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    protected void applyRootTransform(PoseStack matrices) {
        super.applyRootTransform(matrices);
        matrices.mulPose(Axis.YN.rotationDegrees(180f));
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = this.controlDelta();
        matrices.pushPose();
        this.applyRootTransform(matrices);

        // Throttle
        ModelPart throttle = this.console.getChild("bone53").getChild("throttle");
        float throttleTarget = (tardis.travel().speed() / (float) tardis.travel().maxSpeed().get()) * 1.5f;
        throttle.xRot = getAngle(console, "throttle", throttleTarget, delta);

        // Handbrake
        ModelPart handbrake = this.console.getChild("panelf").getChild("handbrake");
        float handbrakeTarget = !tardis.travel().handbrake() ? 0.57f : 0f;
        handbrake.xRot = getAngle(console, "handbrake", handbrakeTarget, delta);

        // Antigravs
        ModelPart antigravs = this.console.getChild("panelf4").getChild("antigravs");
        float antiGravTargetYaw = tardis.travel().antigravs().get() ? 1.0f : 0f;
        float antiGravTargetRoll = tardis.travel().antigravs().get() ? 0.2f : 0f;
        antigravs.yRot = getAngle(console, "antigravs_yaw", antiGravTargetYaw, delta);
        antigravs.zRot = getAngle(console, "antigravs_roll", antiGravTargetRoll, delta);

        // Door Lock
        ModelPart doorlock = this.console.getChild("panelf5").getChild("door_lock");
        float doorLockTarget = tardis.door().locked() ? 0.5f : 0f;
        doorlock.xRot = getAngle(console, "door_lock", doorLockTarget, delta);

        // Door Control
        ModelPart doorControl = this.console.getChild("panelf5").getChild("doors");
        float doorControlTarget = 0f;
        if (tardis.door().isLeftOpen()) {
            doorControlTarget = -0.5f;
        } else if (tardis.door().isRightOpen()) {
            doorControlTarget = -1.55f;
        }
        doorControl.xRot = getAngle(console, "door_control", doorControlTarget, delta);

        // Alarms
        ModelPart alarms = this.console.getChild("panelf5").getChild("alarms");
        float alarmTarget = tardis.alarm().isEnabled() ? 0.2f : 0f;
        alarms.yRot = getAngle(console, "alarm", alarmTarget, delta);

        // Shields
        ModelPart shields = this.console.getChild("panelf5").getChild("rwf").getChild("bone56");
        float shieldTarget = tardis.shields().shielded().get() ? 1.85f : 0f;
        shields.yRot = getAngle(console, "shields", shieldTarget, delta);

        // Refuel
        ModelPart refuel = this.console.getChild("bone53").getChild("refueler");
        float refuelTargetRoll = tardis.isRefueling() ? 0.6f : 0f;
        float refuelTargetYaw = tardis.isRefueling() ? 0.1f : 0f;
        refuel.zRot = getAngle(console, "refuel_roll", refuelTargetRoll, delta);
        refuel.yRot = getAngle(console, "refuel_yaw", refuelTargetYaw, delta);

        // Direction
        ModelPart direction = this.console.getChild("panelf").getChild("rotation");
        float directionTargetDegrees = tardis.travel().destination().getRotation() * 22.5f;
        direction.xRot = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);

        // Increment
        ModelPart increment = this.console.getChild("panelf5").getChild("increment");
        int incrementVal = IncrementManager.increment(tardis);
        float targetOffset;

        if (incrementVal < 10) {
            targetOffset =0;
        } else if (incrementVal < 100) {
            targetOffset = 0.25f;
        } else if (incrementVal < 1000) {
            targetOffset = 0.5f;
        } else if (incrementVal < 10000) {
            targetOffset = 0.75f;
        } else {
            targetOffset = 1.0f;
        }
        increment.xRot = getAngle(console, "increment", targetOffset, delta);

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }


    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        return switch (state) {
            default -> RenaissanceAnimation.FLIGHT;
            case LANDED -> RenaissanceAnimation.IDLE;
        };
    }

    @Override
    public ModelPart root() {
        return console;
    }

    @Override
    public void renderMonitorText(Tardis tardis, ConsoleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        super.renderMonitorText(tardis, entity, matrices, vertexConsumers, light, overlay);

        Minecraft client = Minecraft.getInstance();
        Font renderer = client.font;
        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos abpd = travel.destination();
        CachedDirectedGlobalPos abpp = travel.isLanded() || travel.getState() == TravelHandlerBase.State.MAT
                ? travel.position()
                : travel.getProgress();

        BlockPos abppPos = abpp.getPos();
        BlockPos abpdPos = abpd.getPos();
        matrices.pushPose();
        // TODO dont forget to add variant.getConsoleTextPosition()!
        matrices.translate(-0.365, 1.01, 1.18);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.0035f, 0.0035f, 0.0035f);
        matrices.mulPose(Axis.YN.rotationDegrees(270f));
        matrices.translate(-240f, -220, -5f);
        String positionPosText = abppPos.getX() + ", " + abppPos.getY() + ", " + abppPos.getZ();
        Component positionDimensionText = WorldUtil.worldText(abpp.getDimension());
        String positionDirectionText = DirectionControl.rotationToDirection(abpp.getRotation()).toUpperCase();
        renderer.drawInBatch8xOutline(Component.nullToEmpty("\uD83D\uDCCD").getVisualOrderText(), 0, 40, 0x00EEFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(positionPosText).getVisualOrderText(), 8, 40, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(positionDimensionText.getVisualOrderText(), 8, 48, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(positionDirectionText).getVisualOrderText(), 8, 56, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-0.17, 1.535, 0.565);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.0040f, 0.0040f, 0.0040f);
        matrices.mulPose(Axis.YN.rotationDegrees(270f));
        String progressText = tardis.travel().getState() == TravelHandlerBase.State.LANDED
                ? "⏳: 0%"
                : "⏳: " + tardis.travel().getDurationAsPercentage() + "%";
        matrices.translate(0, -38, -52);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(progressText).getVisualOrderText(), 0 - renderer.width(progressText) / 2, 0, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();
    }
}