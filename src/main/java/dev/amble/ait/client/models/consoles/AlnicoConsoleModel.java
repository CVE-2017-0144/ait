package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.client.animation.console.alnico.AlnicoAnimations;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.FuelHandler;
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

public class AlnicoConsoleModel extends SimpleConsoleModel {
    private final ModelPart alnico;

    public AlnicoConsoleModel(ModelPart root) {
        this.alnico = root.getChild("alnico");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition alnico = modelPartData.addOrReplaceChild("alnico", CubeListBuilder.create(),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition section1 = alnico.addOrReplaceChild("section1", CubeListBuilder.create(),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition desktop = section1.addOrReplaceChild("desktop", CubeListBuilder.create().texOffs(45, 21).addBox(-9.5F, -13.25F,
                -27.65F, 19.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r1 = desktop.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(156, 29)
                        .addBox(-8.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(93, 157)
                        .addBox(7.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(106, 143)
                        .addBox(6.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)).texOffs(121, 143)
                        .addBox(-7.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.001F)).texOffs(65, 0)
                        .addBox(-6.0F, -4.0F, -8.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(148, 155)
                        .addBox(-5.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(155, 155)
                        .addBox(4.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 86)
                        .addBox(-9.0F, -4.0F, -10.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 127)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pillars = section1.addOrReplaceChild("pillars", CubeListBuilder.create().texOffs(111, 91).addBox(-6.0F, -19.0F,
                -12.0F, 12.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r2 = pillars.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(66, 87).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.9554F, -14.2676F, -27.1301F, -1.8326F, -0.3491F, 0.0F));

        PartDefinition cube_r3 = pillars.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(96, 91).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.0F, -13.75F, -30.0F, -1.309F, -0.3491F, 0.0F));

        PartDefinition cube_r4 = pillars
                .addOrReplaceChild("cube_r4",
                        CubeListBuilder.create().texOffs(81, 91).addBox(-1.0F, -10.0F, -2.5F, 2.0F, 20.0F, 5.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-6.6827F, -8.1365F, -18.3146F, -1.8326F, 0.3491F, 0.0F));

        PartDefinition cube_r5 = pillars.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -13.75F, -30.0F, -1.309F, 0.3491F, 0.0F));

        PartDefinition top = section1.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r6 = top.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(90, 21).addBox(-4.0F, -12.4F, -27.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 59).addBox(-1.5F, -19.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r7 = top.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -28.9F, 8.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r8 = top.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, -18.0F,
                -22.0F, 6.0F, 0.0F, 17.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bottom = section1.addOrReplaceChild("bottom",
                CubeListBuilder.create().texOffs(0, 91).addBox(-7.0F, -7.0F, -15.0F, 14.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r9 = bottom.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(116, 16).addBox(-2.5F, -9.0F,
                -19.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r10 = bottom.addOrReplaceChild("cube_r10",
                CubeListBuilder.create().texOffs(147, 77).addBox(8.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(147, 147).addBox(-11.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
                        .addBox(-8.0F, -3.0F, -1.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.0F, -24.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition controls = section1.addOrReplaceChild("controls", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition biglever = controls.addOrReplaceChild("biglever",
                CubeListBuilder.create().texOffs(23, 151).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(106, 11).addBox(-2.0F, -1.0F, -2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -16.25F, -11.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bigleverlights = biglever.addOrReplaceChild("bigleverlights", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition red1 = bigleverlights.addOrReplaceChild("red1", CubeListBuilder.create().texOffs(80, 117).addBox(-1.0F, -2.05F,
                -3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.5F, 0.0F, 0.0F));

        PartDefinition yellow1 = bigleverlights.addOrReplaceChild("yellow1", CubeListBuilder.create().texOffs(115, 41).addBox(-0.5F,
                -2.05F, -1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition green1 = bigleverlights.addOrReplaceChild("green1",
                CubeListBuilder.create().texOffs(90, 27).addBox(-0.5F, -2.05F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone = biglever.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(27, 142)
                        .addBox(-1.5F, -3.8F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(113, 21)
                        .addBox(1.5F, -3.8F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(155, 141)
                        .addBox(-1.5F, -3.8F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(98, 0)
                        .addBox(-0.5F, -6.8F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(141, 151)
                        .addBox(0.5F, -3.8F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, -0.2F, -1.0F));

        PartDefinition tinyswitch = controls.addOrReplaceChild("tinyswitch",
                CubeListBuilder.create().texOffs(155, 11).addBox(10.0F, -1.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-8.0F, -15.95F, -12.55F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone3 = tinyswitch.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(128, 104).addBox(-1.0F, -0.75F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(157, 73).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offset(11.0F, -1.0F, -2.0F));

        PartDefinition tinyswitch2 = controls.addOrReplaceChild("tinyswitch2",
                CubeListBuilder.create().texOffs(155, 7).addBox(10.0F, -1.0F, -3.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-14.0F, -15.95F, -12.55F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone2 = tinyswitch2.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(128, 4).addBox(-1.0F, -0.75F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(157, 56).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offset(11.0F, -1.0F, -2.0F));

        PartDefinition sideswitches = controls
                .addOrReplaceChild("sideswitches",
                        CubeListBuilder.create().texOffs(128, 100).addBox(-2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-7.5F, -14.7806F, -20.0173F, 0.2472F, 0.3594F, 0.0043F));

        PartDefinition sideswitch1 = sideswitches.addOrReplaceChild("sideswitch1",
                CubeListBuilder.create().texOffs(154, 21).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(68, 151).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, 0.0F, 2.5F));

        PartDefinition sideswitch2 = sideswitches.addOrReplaceChild("sideswitch2",
                CubeListBuilder.create().texOffs(125, 152).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 151).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, 0.0F, -1.5F));

        PartDefinition geiger1 = controls.addOrReplaceChild("geiger1",
                CubeListBuilder.create().texOffs(0, 151).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.25F, -14.0F, -18.0F));

        PartDefinition needle1 = geiger1.addOrReplaceChild("needle1", CubeListBuilder.create().texOffs(110, 21).addBox(-0.5F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-3.0F, 1.0F, -2.0F));

        PartDefinition geiger2 = controls.addOrReplaceChild("geiger2",
                CubeListBuilder.create().texOffs(87, 150).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.75F, -14.0F, -18.0F));

        PartDefinition needle2 = geiger2.addOrReplaceChild("needle2", CubeListBuilder.create().texOffs(10, 101).addBox(-0.5F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(2.0F, 1.0F, -2.0F));

        PartDefinition multiswitchpanel = controls.addOrReplaceChild("multiswitchpanel",
                CubeListBuilder.create().texOffs(27, 135).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -8.25F, -26.65F));

        PartDefinition longswitch1 = multiswitchpanel.addOrReplaceChild("longswitch1",
                CubeListBuilder.create().texOffs(90, 157).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(17, 157).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.25F, -2.5F, -0.5F));

        PartDefinition longswitch2 = multiswitchpanel.addOrReplaceChild("longswitch2",
                CubeListBuilder.create().texOffs(87, 157).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(35, 157).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.75F, -2.5F, -0.5F));

        PartDefinition longswitch3 = multiswitchpanel.addOrReplaceChild("longswitch3",
                CubeListBuilder.create().texOffs(40, 157).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(47, 157).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.75F, -2.5F, -0.5F));

        PartDefinition longswitch4 = multiswitchpanel.addOrReplaceChild("longswitch4",
                CubeListBuilder.create().texOffs(65, 156).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(52, 157).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.25F, -2.5F, -0.5F));

        PartDefinition fliplever1 = controls.addOrReplaceChild("fliplever1",
                CubeListBuilder.create().texOffs(151, 90).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.15F, -12.25F, -21.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone5 = fliplever1.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(100, 150).addBox(-0.5F, -7.0F, -1.0F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(113, 52).addBox(-0.5F, -7.0F, 1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(118, 119)
                        .addBox(-1.0F, -6.5F, 1.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(10, 101)
                        .addBox(0.0F, -10.5F, 1.5F, 0.0F, 5.0F, 3.0F, new CubeDeformation(0.001F)).texOffs(111, 100)
                        .addBox(0.5F, -7.0F, 1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(0, 120)
                        .addBox(0.0F, -9.0F, 0.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(133, 59)
                        .addBox(-0.5F, -13.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1.0F, 0.5F));

        PartDefinition bell = controls.addOrReplaceChild("bell",
                CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -7.0F, -3.0F, 0.0F, 7.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(9.5F, -14.0F, -24.0F, 0.2618F, -0.3491F, 0.0F));

        PartDefinition bone4 = bell.addOrReplaceChild("bone4", CubeListBuilder.create(),
                PartPose.offset(0.0F, -6.0F, -3.0F));

        PartDefinition cube_r11 = bone4.addOrReplaceChild("cube_r11",
                CubeListBuilder.create().texOffs(38, 99).addBox(-1.5F, -3.0F, -6.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(151, 51).addBox(-1.0F, -5.0F, -5.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, 3.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition dial5 = controls.addOrReplaceChild("dial5",
                CubeListBuilder.create().texOffs(156, 96).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(155, 126).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(83, 87)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-3.0F, -13.75F, -21.15F, -1.309F, 0.0F, 0.0F));

        PartDefinition dial6 = controls.addOrReplaceChild("dial6",
                CubeListBuilder.create().texOffs(152, 114).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(22, 152).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(70, 0)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-1.25F, -13.75F, -21.15F, -1.309F, 0.0F, 0.0F));

        PartDefinition section2 = alnico.addOrReplaceChild("section2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition desktop2 = section2.addOrReplaceChild("desktop2", CubeListBuilder.create().texOffs(22, 37).addBox(-9.5F,
                -13.25F, -27.65F, 19.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r12 = desktop2.addOrReplaceChild("cube_r12",
                CubeListBuilder.create().texOffs(146, 59).addBox(-8.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(106, 147).addBox(7.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(142, 93)
                        .addBox(6.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(143, 35)
                        .addBox(-7.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(55, 59)
                        .addBox(-6.0F, -4.0F, -8.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(141, 154)
                        .addBox(-5.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(155, 136)
                        .addBox(4.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(53, 82)
                        .addBox(-9.0F, -4.0F, -10.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(117, 126)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pillars2 = section2.addOrReplaceChild("pillars2", CubeListBuilder.create().texOffs(111, 91).addBox(-6.0F,
                -19.0F, -12.0F, 12.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r13 = pillars2.addOrReplaceChild("cube_r13",
                CubeListBuilder.create().texOffs(66, 87).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.9554F, -14.2676F, -27.1301F, -1.8326F, -0.3491F, 0.0F));

        PartDefinition cube_r14 = pillars2.addOrReplaceChild("cube_r14",
                CubeListBuilder.create().texOffs(96, 91).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.0F, -13.75F, -30.0F, -1.309F, -0.3491F, 0.0F));

        PartDefinition cube_r15 = pillars2
                .addOrReplaceChild("cube_r15",
                        CubeListBuilder.create().texOffs(81, 91).addBox(-1.0F, -10.0F, -2.5F, 2.0F, 20.0F, 5.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-6.6827F, -8.1365F, -18.3146F, -1.8326F, 0.3491F, 0.0F));

        PartDefinition cube_r16 = pillars2.addOrReplaceChild("cube_r16",
                CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -13.75F, -30.0F, -1.309F, 0.3491F, 0.0F));

        PartDefinition top2 = section2.addOrReplaceChild("top2", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r17 = top2.addOrReplaceChild("cube_r17",
                CubeListBuilder.create().texOffs(90, 21).addBox(-4.0F, -12.4F, -27.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 59).addBox(-1.5F, -19.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r18 = top2.addOrReplaceChild("cube_r18",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -28.9F, 8.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r19 = top2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, -18.0F,
                -22.0F, 6.0F, 0.0F, 17.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bottom2 = section2.addOrReplaceChild("bottom2",
                CubeListBuilder.create().texOffs(0, 91).addBox(-7.0F, -7.0F, -15.0F, 14.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r20 = bottom2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(116, 16).addBox(-2.5F, -9.0F,
                -19.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r21 = bottom2.addOrReplaceChild("cube_r21",
                CubeListBuilder.create().texOffs(147, 77).addBox(8.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(147, 147).addBox(-11.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
                        .addBox(-8.0F, -3.0F, -1.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.0F, -24.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition controls2 = section2.addOrReplaceChild("controls2", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition computer = controls2.addOrReplaceChild("computer",
                CubeListBuilder.create().texOffs(53, 70).addBox(-4.0F, -0.25F, -4.95F, 8.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                        .texOffs(56, 113).addBox(-3.0F, -4.25F, -2.95F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(62, 99)
                        .addBox(-3.0F, -4.25F, -3.95F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(53, 99)
                        .addBox(3.0F, -4.25F, -3.95F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(0, 15)
                        .addBox(-3.0F, -4.25F, -3.95F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, -16.25F, -13.95F));

        PartDefinition computernob = computer.addOrReplaceChild("computernob",
                CubeListBuilder.create().texOffs(12, 157).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(134, 156).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(38, 91)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(2.5F, 0.75F, -4.95F));

        PartDefinition pumpswitch1 = controls2.addOrReplaceChild("pumpswitch1", CubeListBuilder.create().texOffs(145, 138)
                .addBox(-1.0F, -1.0F, -2.4F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.0F, -13.0F, -24.25F));

        PartDefinition bone6 = pumpswitch1.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(156, 85).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 9).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.001F)).texOffs(36, 145)
                        .addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 0.0F, -2.4F));

        PartDefinition pumpswitch2 = controls2.addOrReplaceChild("pumpswitch2",
                CubeListBuilder.create().texOffs(144, 21).addBox(-1.0F, -1.0F, -2.4F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.0F, -13.0F, -24.25F));

        PartDefinition bone7 = pumpswitch2.addOrReplaceChild("bone7",
                CubeListBuilder.create().texOffs(80, 156).addBox(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(145, 111).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.001F)).texOffs(46, 67)
                        .addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 0.0F, -2.4F));

        PartDefinition dial1 = controls2.addOrReplaceChild("dial1",
                CubeListBuilder.create().texOffs(129, 156).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(124, 156).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(31, 91)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-3.0F, -11.25F, -25.65F));

        PartDefinition dial2 = controls2.addOrReplaceChild("dial2",
                CubeListBuilder.create().texOffs(151, 96).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(32, 151).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(69, 18)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-1.0F, -11.25F, -25.65F));

        PartDefinition dial3 = controls2.addOrReplaceChild("dial3",
                CubeListBuilder.create().texOffs(9, 151).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(76, 149).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(49, 67)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(1.0F, -11.25F, -25.65F));

        PartDefinition dial4 = controls2.addOrReplaceChild("dial4",
                CubeListBuilder.create().texOffs(45, 148).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 44).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(60, 59)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(3.0F, -11.25F, -25.65F));

        PartDefinition waypointcatridge = controls2.addOrReplaceChild("waypointcatridge",
                CubeListBuilder.create().texOffs(92, 130).addBox(-1.5F, -4.0F, -1.0F, 3.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -12.4F, -20.9F, 0.0F, 0.5236F, 0.0F));

        PartDefinition toastlever = waypointcatridge.addOrReplaceChild("toastlever",
                CubeListBuilder.create().texOffs(65, 5).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -3.0F, -1.0F));

        PartDefinition toast1 = waypointcatridge.addOrReplaceChild("toast1", CubeListBuilder.create().texOffs(69, 149).addBox(-0.5F,
                -3.75F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.75F, -1.25F, 2.0F));

        PartDefinition toast2 = waypointcatridge.addOrReplaceChild("toast2", CubeListBuilder.create().texOffs(47, 148).addBox(-0.5F,
                -3.75F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(-0.25F)), PartPose.offset(0.75F, -1.25F, 2.0F));

        PartDefinition section3 = alnico.addOrReplaceChild("section3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition desktop3 = section3.addOrReplaceChild("desktop3", CubeListBuilder.create().texOffs(45, 21).addBox(-9.5F,
                -13.25F, -27.65F, 19.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r22 = desktop3.addOrReplaceChild("cube_r22",
                CubeListBuilder.create().texOffs(144, 29).addBox(-8.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(91, 145).addBox(7.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(142, 50)
                        .addBox(6.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(142, 68)
                        .addBox(-7.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(59, 37)
                        .addBox(-6.0F, -4.0F, -8.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(140, 93)
                        .addBox(-5.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(80, 151)
                        .addBox(4.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(80, 16)
                        .addBox(-9.0F, -4.0F, -10.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(39, 124)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pillars3 = section3.addOrReplaceChild("pillars3", CubeListBuilder.create().texOffs(111, 91).addBox(-6.0F,
                -19.0F, -12.0F, 12.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r23 = pillars3.addOrReplaceChild("cube_r23",
                CubeListBuilder.create().texOffs(66, 87).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.9554F, -14.2676F, -27.1301F, -1.8326F, -0.3491F, 0.0F));

        PartDefinition cube_r24 = pillars3.addOrReplaceChild("cube_r24",
                CubeListBuilder.create().texOffs(96, 91).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.0F, -13.75F, -30.0F, -1.309F, -0.3491F, 0.0F));

        PartDefinition cube_r25 = pillars3
                .addOrReplaceChild("cube_r25",
                        CubeListBuilder.create().texOffs(81, 91).addBox(-1.0F, -10.0F, -2.5F, 2.0F, 20.0F, 5.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-6.6827F, -8.1365F, -18.3146F, -1.8326F, 0.3491F, 0.0F));

        PartDefinition cube_r26 = pillars3.addOrReplaceChild("cube_r26",
                CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -13.75F, -30.0F, -1.309F, 0.3491F, 0.0F));

        PartDefinition top3 = section3.addOrReplaceChild("top3", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r27 = top3.addOrReplaceChild("cube_r27",
                CubeListBuilder.create().texOffs(90, 21).addBox(-4.0F, -12.4F, -27.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 59).addBox(-1.5F, -19.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r28 = top3.addOrReplaceChild("cube_r28",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -28.9F, 8.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r29 = top3.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, -18.0F,
                -22.0F, 6.0F, 0.0F, 17.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bottom3 = section3.addOrReplaceChild("bottom3",
                CubeListBuilder.create().texOffs(0, 91).addBox(-7.0F, -7.0F, -15.0F, 14.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r30 = bottom3.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(116, 16).addBox(-2.5F, -9.0F,
                -19.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r31 = bottom3.addOrReplaceChild("cube_r31",
                CubeListBuilder.create().texOffs(147, 77).addBox(8.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(147, 147).addBox(-11.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
                        .addBox(-8.0F, -3.0F, -1.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.0F, -24.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition controls3 = section3.addOrReplaceChild("controls3", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition telepathiccircuit = controls3.addOrReplaceChild("telepathiccircuit", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -14.5967F, -18.0647F, 0.2618F, 0.0F, 0.0F));

        PartDefinition crystal = telepathiccircuit.addOrReplaceChild("crystal",
                CubeListBuilder.create().texOffs(0, 101).addBox(0.0F, -9.0F, -4.5F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 0.0F, 1.2F));

        PartDefinition cube_r32 = crystal.addOrReplaceChild("cube_r32",
                CubeListBuilder.create().texOffs(43, 99).addBox(0.0F, -9.0F, -4.5F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition lowerlever = controls3.addOrReplaceChild("lowerlever",
                CubeListBuilder.create().texOffs(137, 21).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(6.0F, -11.25F, -26.6F));

        PartDefinition bone8 = lowerlever.addOrReplaceChild("bone8",
                CubeListBuilder.create().texOffs(13, 79).addBox(0.0F, -1.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(91, 141).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.75F, -0.5F, -0.5F));

        PartDefinition bone9 = lowerlever.addOrReplaceChild("bone9",
                CubeListBuilder.create().texOffs(0, 79).addBox(0.0F, -1.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(137, 74).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.75F, -0.5F, -0.5F));

        PartDefinition lowerlever2 = controls3.addOrReplaceChild("lowerlever2",
                CubeListBuilder.create().texOffs(122, 47).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.0F, -11.25F, -26.6F));

        PartDefinition bone10 = lowerlever2.addOrReplaceChild("bone10",
                CubeListBuilder.create().texOffs(64, 36).addBox(0.0F, -1.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(133, 69).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.75F, -0.5F, -0.5F));

        PartDefinition bone11 = lowerlever2.addOrReplaceChild("bone11",
                CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -1.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(109, 65).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.75F, -0.5F, -0.5F));

        PartDefinition geiger = controls3.addOrReplaceChild("geiger",
                CubeListBuilder.create().texOffs(151, 67).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(43, 118).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(-0.5F, -10.75F, -26.6F));

        PartDefinition needle = geiger.addOrReplaceChild("needle", CubeListBuilder.create().texOffs(48, 99).addBox(-0.25F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.5F, 0.25F, -1.0F));

        PartDefinition siegemode = controls3.addOrReplaceChild("siegemode",
                CubeListBuilder.create().texOffs(53, 99).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(136, 143).addBox(-0.5F, -1.25F, -4.5F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -12.9F, -20.833F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r33 = siegemode.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(151, 44).addBox(-1.0F,
                0.0F, -3.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(-0.001F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 1.0036F, 0.0F, 0.0F));

        PartDefinition lever = siegemode.addOrReplaceChild("lever",
                CubeListBuilder.create().texOffs(142, 117)
                        .addBox(-0.6F, -3.5F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(30, 142)
                        .addBox(0.6F, -3.5F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(22, 45)
                        .addBox(-0.5F, -8.0F, -0.997F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.75F, -3.5F));

        PartDefinition sideswitches2 = controls3.addOrReplaceChild("sideswitches2",
                CubeListBuilder.create().texOffs(128, 4).addBox(-2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.5F, -14.7806F, -20.0173F, 0.2472F, 0.3594F, 0.0043F));

        PartDefinition sideswitch3 = sideswitches2.addOrReplaceChild("sideswitch3",
                CubeListBuilder.create().texOffs(151, 17).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(133, 50).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, 0.0F, 2.5F));

        PartDefinition sideswitch4 = sideswitches2.addOrReplaceChild("sideswitch4",
                CubeListBuilder.create().texOffs(142, 129).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(112, 130).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, 0.0F, -1.5F));

        PartDefinition sideswitches5 = controls3.addOrReplaceChild("sideswitches5",
                CubeListBuilder.create().texOffs(25, 125).addBox(-1.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.5F, -14.7806F, -20.0173F, 0.2472F, -0.3594F, -0.0043F));

        PartDefinition sideswitch9 = sideswitches5.addOrReplaceChild("sideswitch9",
                CubeListBuilder.create().texOffs(142, 102).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(25, 130).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.5F, 0.0F, 2.5F));

        PartDefinition sideswitch10 = sideswitches5.addOrReplaceChild("sideswitch10",
                CubeListBuilder.create().texOffs(142, 77).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 117).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.5F, 0.0F, -1.5F));

        PartDefinition section4 = alnico.addOrReplaceChild("section4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition desktop4 = section4.addOrReplaceChild("desktop4", CubeListBuilder.create().texOffs(45, 21).addBox(-9.5F,
                -13.25F, -27.65F, 19.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r34 = desktop4.addOrReplaceChild("cube_r34",
                CubeListBuilder.create().texOffs(142, 4).addBox(-8.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(142, 9).addBox(7.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(142, 0)
                        .addBox(6.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(27, 142)
                        .addBox(-7.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(22, 56)
                        .addBox(-6.0F, -4.0F, -8.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(88, 50)
                        .addBox(-5.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(21, 118)
                        .addBox(4.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(79, 70)
                        .addBox(-9.0F, -4.0F, -10.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(121, 119)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pillars4 = section4.addOrReplaceChild("pillars4", CubeListBuilder.create().texOffs(111, 91).addBox(-6.0F,
                -19.0F, -12.0F, 12.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r35 = pillars4.addOrReplaceChild("cube_r35",
                CubeListBuilder.create().texOffs(66, 87).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.9554F, -14.2676F, -27.1301F, -1.8326F, -0.3491F, 0.0F));

        PartDefinition cube_r36 = pillars4.addOrReplaceChild("cube_r36",
                CubeListBuilder.create().texOffs(96, 91).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.0F, -13.75F, -30.0F, -1.309F, -0.3491F, 0.0F));

        PartDefinition cube_r37 = pillars4
                .addOrReplaceChild("cube_r37",
                        CubeListBuilder.create().texOffs(81, 91).addBox(-1.0F, -10.0F, -2.5F, 2.0F, 20.0F, 5.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-6.6827F, -8.1365F, -18.3146F, -1.8326F, 0.3491F, 0.0F));

        PartDefinition cube_r38 = pillars4.addOrReplaceChild("cube_r38",
                CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -13.75F, -30.0F, -1.309F, 0.3491F, 0.0F));

        PartDefinition top4 = section4.addOrReplaceChild("top4", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r39 = top4.addOrReplaceChild("cube_r39",
                CubeListBuilder.create().texOffs(90, 21).addBox(-4.0F, -12.4F, -27.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 59).addBox(-1.5F, -19.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r40 = top4.addOrReplaceChild("cube_r40",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -28.9F, 8.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r41 = top4.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, -18.0F,
                -22.0F, 6.0F, 0.0F, 17.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bottom4 = section4.addOrReplaceChild("bottom4",
                CubeListBuilder.create().texOffs(0, 91).addBox(-7.0F, -7.0F, -15.0F, 14.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r42 = bottom4.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(116, 16).addBox(-2.5F, -9.0F,
                -19.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r43 = bottom4.addOrReplaceChild("cube_r43",
                CubeListBuilder.create().texOffs(147, 77).addBox(8.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(147, 147).addBox(-11.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
                        .addBox(-8.0F, -3.0F, -1.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.0F, -24.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition controls4 = section4.addOrReplaceChild("controls4", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition biglever2 = controls4.addOrReplaceChild("biglever2",
                CubeListBuilder.create().texOffs(149, 119)
                        .addBox(8.75F, -19.25F, 3.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(58, 87)
                        .addBox(7.75F, -18.25F, 4.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-20.75F, 5.5F, -16.5F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bone12 = biglever2.addOrReplaceChild("bone12",
                CubeListBuilder.create().texOffs(9, 25).addBox(-1.5F, -3.5F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(62, 107).addBox(1.5F, -3.5F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(99, 117)
                        .addBox(-1.5F, -3.5F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(13, 10)
                        .addBox(-0.5F, -6.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(104, 117)
                        .addBox(0.5F, -3.5F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(9.75F, -17.75F, 5.0F));

        PartDefinition bigleverlights2 = biglever2.addOrReplaceChild("bigleverlights2", CubeListBuilder.create(),
                PartPose.offset(9.75F, -17.25F, 6.0F));

        PartDefinition red2 = bigleverlights2.addOrReplaceChild("red2",
                CubeListBuilder.create().texOffs(88, 48).addBox(-1.0F, -2.05F, -3.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 0.0F, 0.0F));

        PartDefinition yellow2 = bigleverlights2.addOrReplaceChild("yellow2",
                CubeListBuilder.create().texOffs(53, 29).addBox(-0.5F, -2.05F, -1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition green2 = bigleverlights2.addOrReplaceChild("green2",
                CubeListBuilder.create().texOffs(20, 37).addBox(-0.5F, -2.05F, 0.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tinyswitch3 = controls4.addOrReplaceChild("tinyswitch3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-3.0F, -14.25F, -14.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r44 = tinyswitch3.addOrReplaceChild("cube_r44",
                CubeListBuilder.create().texOffs(147, 85).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone16 = tinyswitch3.addOrReplaceChild("bone16", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.9F, -0.5F));

        PartDefinition cube_r45 = bone16.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(96, 150).addBox(-0.75F, -3.0F,
                0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.9F, 0.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tinyswitch4 = controls4.addOrReplaceChild("tinyswitch4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -14.25F, -14.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r46 = tinyswitch4.addOrReplaceChild("cube_r46",
                CubeListBuilder.create().texOffs(138, 50).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone13 = tinyswitch4.addOrReplaceChild("bone13", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.9F, -0.5F));

        PartDefinition cube_r47 = bone13.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(149, 136).addBox(-0.75F,
                -3.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.9F, 0.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tinyswitch5 = controls4.addOrReplaceChild("tinyswitch5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(3.0F, -14.25F, -14.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r48 = tinyswitch5.addOrReplaceChild("cube_r48",
                CubeListBuilder.create().texOffs(23, 125).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone14 = tinyswitch5.addOrReplaceChild("bone14", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.9F, -0.5F));

        PartDefinition cube_r49 = bone14.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(149, 109).addBox(-0.75F,
                -3.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.9F, 0.5F, 0.2618F, 0.0F, 0.0F));

        PartDefinition tinylight = controls4.addOrReplaceChild("tinylight",
                CubeListBuilder.create().texOffs(152, 109)
                        .addBox(-1.0F, -0.925F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(36, 142)
                        .addBox(-1.0F, -0.075F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-3.0F, -14.975F, -16.2F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone87 = tinylight.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(107, 152).addBox(-7.0F, -1.0F,
                -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(6.0F, 0.075F, 2.7F));

        PartDefinition tinylight2 = controls4.addOrReplaceChild("tinylight2",
                CubeListBuilder.create().texOffs(36, 152)
                        .addBox(-1.0F, -0.925F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(105, 130)
                        .addBox(-1.0F, -0.075F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -14.975F, -16.2F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone15 = tinylight2.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(152, 35).addBox(-7.0F, -1.0F,
                -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(6.0F, 0.075F, 2.7F));

        PartDefinition tinylight3 = controls4.addOrReplaceChild("tinylight3",
                CubeListBuilder.create().texOffs(13, 152)
                        .addBox(-1.0F, -0.925F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(71, 18)
                        .addBox(-1.0F, -0.075F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(3.0F, -14.975F, -16.2F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone17 = tinylight3.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(134, 151).addBox(-7.0F, -1.0F,
                -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(6.0F, 0.075F, 2.7F));

        PartDefinition keyboard = controls4.addOrReplaceChild("keyboard", CubeListBuilder.create().texOffs(113, 52).addBox(-5.0F,
                -2.0F, -3.0F, 10.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -13.25F, -19.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition wrench1 = controls4.addOrReplaceChild("wrench1",
                CubeListBuilder.create().texOffs(94, 117).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-6.5F, -10.25F, -26.6F));

        PartDefinition bone18 = wrench1.addOrReplaceChild("bone18",
                CubeListBuilder.create().texOffs(22, 56).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 7.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, -1.5F, 0.0F));

        PartDefinition wrench2 = controls4.addOrReplaceChild("wrench2",
                CubeListBuilder.create().texOffs(92, 41).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.5F, -10.25F, -26.6F));

        PartDefinition bone19 = wrench2.addOrReplaceChild("bone19",
                CubeListBuilder.create().texOffs(0, 101).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 8.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, -1.5F, 0.0F));

        PartDefinition wrench3 = controls4.addOrReplaceChild("wrench3",
                CubeListBuilder.create().texOffs(33, 91).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.5F, -10.25F, -26.6F));

        PartDefinition bone20 = wrench3.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(58, 151).addBox(-1.5F, -1.5F,
                0.0F, 3.0F, 9.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(-0.5F, -1.5F, 0.0F));

        PartDefinition wrench4 = controls4.addOrReplaceChild("wrench4",
                CubeListBuilder.create().texOffs(5, 21).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(7.5F, -10.25F, -26.6F));

        PartDefinition bone21 = wrench4.addOrReplaceChild("bone21",
                CubeListBuilder.create().texOffs(44, 0).addBox(-1.5F, -1.5F, 0.0F, 3.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, -1.5F, 0.0F));

        PartDefinition section5 = alnico.addOrReplaceChild("section5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition desktop5 = section5.addOrReplaceChild("desktop5", CubeListBuilder.create().texOffs(45, 21).addBox(-9.5F,
                -13.25F, -27.65F, 19.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r50 = desktop5.addOrReplaceChild("cube_r50",
                CubeListBuilder.create().texOffs(137, 26).addBox(-8.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(137, 78).addBox(7.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(140, 120)
                        .addBox(6.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(91, 141)
                        .addBox(-7.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(55, 48)
                        .addBox(-6.0F, -4.0F, -8.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(65, 0)
                        .addBox(-5.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(22, 67)
                        .addBox(4.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(65, 11)
                        .addBox(-9.0F, -4.0F, -10.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 120)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pillars5 = section5.addOrReplaceChild("pillars5", CubeListBuilder.create().texOffs(111, 91).addBox(-6.0F,
                -19.0F, -12.0F, 12.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r51 = pillars5.addOrReplaceChild("cube_r51",
                CubeListBuilder.create().texOffs(66, 87).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.9554F, -14.2676F, -27.1301F, -1.8326F, -0.3491F, 0.0F));

        PartDefinition cube_r52 = pillars5.addOrReplaceChild("cube_r52",
                CubeListBuilder.create().texOffs(96, 91).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.0F, -13.75F, -30.0F, -1.309F, -0.3491F, 0.0F));

        PartDefinition cube_r53 = pillars5
                .addOrReplaceChild("cube_r53",
                        CubeListBuilder.create().texOffs(81, 91).addBox(-1.0F, -10.0F, -2.5F, 2.0F, 20.0F, 5.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-6.6827F, -8.1365F, -18.3146F, -1.8326F, 0.3491F, 0.0F));

        PartDefinition cube_r54 = pillars5.addOrReplaceChild("cube_r54",
                CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -13.75F, -30.0F, -1.309F, 0.3491F, 0.0F));

        PartDefinition top5 = section5.addOrReplaceChild("top5", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r55 = top5.addOrReplaceChild("cube_r55",
                CubeListBuilder.create().texOffs(90, 21).addBox(-4.0F, -12.4F, -27.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 59).addBox(-1.5F, -19.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r56 = top5.addOrReplaceChild("cube_r56",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -28.9F, 8.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r57 = top5.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, -18.0F,
                -22.0F, 6.0F, 0.0F, 17.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bottom5 = section5.addOrReplaceChild("bottom5",
                CubeListBuilder.create().texOffs(0, 91).addBox(-7.0F, -7.0F, -15.0F, 14.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r58 = bottom5.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(116, 16).addBox(-2.5F, -9.0F,
                -19.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r59 = bottom5.addOrReplaceChild("cube_r59",
                CubeListBuilder.create().texOffs(147, 77).addBox(8.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(147, 147).addBox(-11.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
                        .addBox(-8.0F, -3.0F, -1.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.0F, -24.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition controls5 = section5.addOrReplaceChild("controls5", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition taperecorder = controls5.addOrReplaceChild("taperecorder",
                CubeListBuilder.create().texOffs(111, 75).addBox(-5.0F, -1.0F, -3.0F, 10.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(98, 0).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -16.5F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone30 = taperecorder.addOrReplaceChild("bone30", CubeListBuilder.create(),
                PartPose.offsetAndRotation(2.3579F, -1.5F, -1.265F, 0.0F, -0.3491F, 0.0F));

        PartDefinition bone32 = bone30.addOrReplaceChild("bone32",
                CubeListBuilder.create().texOffs(88, 65).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone29 = taperecorder.addOrReplaceChild("bone29", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-2.4095F, -1.5F, -1.265F, 0.0F, 0.3491F, 0.0F));

        PartDefinition bone31 = bone29.addOrReplaceChild("bone31",
                CubeListBuilder.create().texOffs(22, 72).addBox(-1.5F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone22 = taperecorder.addOrReplaceChild("bone22",
                CubeListBuilder.create().texOffs(120, 69).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-3.5F, -2.0F, 0.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone23 = taperecorder.addOrReplaceChild("bone23",
                CubeListBuilder.create().texOffs(0, 79).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(3.5F, -2.0F, 0.5F));

        PartDefinition geiger4 = controls5.addOrReplaceChild("geiger4", CubeListBuilder.create().texOffs(149, 129).addBox(-1.0F,
                -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.25F, -14.0F, -18.0F));

        PartDefinition needle4 = geiger4.addOrReplaceChild("needle4", CubeListBuilder.create().texOffs(38, 99).addBox(-0.5F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 1.0F, -2.0F));

        PartDefinition geiger3 = controls5.addOrReplaceChild("geiger3", CubeListBuilder.create().texOffs(149, 102).addBox(-1.0F,
                -1.0F, -2.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.25F, -14.0F, -18.0F));

        PartDefinition needle3 = geiger3.addOrReplaceChild("needle3", CubeListBuilder.create().texOffs(55, 45).addBox(-0.5F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 1.0F, -2.0F));

        PartDefinition tinyswitch6 = controls5.addOrReplaceChild("tinyswitch6", CubeListBuilder.create().texOffs(9, 0).addBox(7.4378F,
                -3.4749F, -12.2364F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-11.5F, -10.7F, -12.55F));

        PartDefinition bone24 = tinyswitch6.addOrReplaceChild("bone24",
                CubeListBuilder.create().texOffs(55, 70)
                        .addBox(-1.0622F, -0.7749F, -0.0364F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(88, 79)
                        .addBox(-0.5622F, -0.7749F, -0.5364F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offset(8.5F, -3.45F, -11.2F));

        PartDefinition tinyswitch7 = controls5.addOrReplaceChild("tinyswitch7", CubeListBuilder.create().texOffs(101, 37).addBox(6.5F,
                -3.4749F, -12.2364F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-7.5F, -10.7F, -12.55F));

        PartDefinition bone25 = tinyswitch7.addOrReplaceChild("bone25",
                CubeListBuilder.create().texOffs(115, 31)
                        .addBox(-1.0F, -0.7249F, 0.0136F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(28, 101)
                        .addBox(-0.5F, -0.7249F, -0.4864F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offset(7.5F, -3.5F, -11.25F));

        PartDefinition tinyswitch9 = controls5.addOrReplaceChild("tinyswitch9", CubeListBuilder.create().texOffs(92, 37)
                .addBox(-9.4378F, -3.4749F, -12.2364F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(11.5F, -10.7F, -12.55F));

        PartDefinition bone26 = tinyswitch9.addOrReplaceChild("bone26",
                CubeListBuilder.create().texOffs(58, 92)
                        .addBox(-0.9378F, -0.7749F, -0.0364F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(14, 101)
                        .addBox(-0.4378F, -0.7749F, -0.5364F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)),
                PartPose.offset(-8.5F, -3.45F, -11.2F));

        PartDefinition tinylight4 = controls5.addOrReplaceChild("tinylight4",
                CubeListBuilder.create().texOffs(119, 11)
                        .addBox(2.0F, -2.2699F, -6.0191F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(62, 18)
                        .addBox(2.0F, -1.4199F, -6.0191F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-9.0F, -11.925F, -19.1F));

        PartDefinition bone27 = tinylight4.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(91, 91).addBox(-4.0F,
                -2.3449F, -8.7191F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(6.0F, 0.075F, 2.7F));

        PartDefinition tinylight5 = controls5.addOrReplaceChild("tinylight5",
                CubeListBuilder.create().texOffs(76, 87)
                        .addBox(-4.0F, -2.2699F, -6.0191F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(53, 18)
                        .addBox(-4.0F, -1.4199F, -6.0191F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offset(9.0F, -11.925F, -19.1F));

        PartDefinition bone28 = tinylight5.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(53, 74).addBox(2.0F, -2.3449F,
                -8.7191F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(-6.0F, 0.075F, 2.7F));

        PartDefinition cassetteplayer = controls5.addOrReplaceChild("cassetteplayer",
                CubeListBuilder.create().texOffs(96, 65).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -15.0306F, -20.0173F, 0.2618F, 0.0F, 0.0F));

        PartDefinition refueler = controls5.addOrReplaceChild("refueler",
                CubeListBuilder.create().texOffs(134, 28).addBox(-2.0F, -5.0F, -1.0F, 3.0F, 9.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(106, 91).addBox(-1.75F, -6.0F, -0.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)).texOffs(60, 48)
                        .addBox(-2.25F, -6.0F, -0.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(60, 18)
                        .addBox(-2.25F, -6.0F, 1.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(27, 45)
                        .addBox(0.25F, -6.0F, 1.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(81, 92)
                        .addBox(0.75F, -6.0F, -0.5F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)).texOffs(49, 11)
                        .addBox(0.25F, -6.0F, -0.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-11.0F, -13.4F, -20.083F, 1.309F, 0.5236F, 0.0F));

        PartDefinition gasknob = refueler.addOrReplaceChild("gasknob",
                CubeListBuilder.create().texOffs(118, 69).addBox(-0.5F, -0.8F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(106, 75).addBox(-1.0F, -1.4F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(-0.5F, -5.0F, 0.5F));

        PartDefinition multiswitchpanel2 = controls5.addOrReplaceChild("multiswitchpanel2",
                CubeListBuilder.create().texOffs(132, 14).addBox(-3.0F, -3.0F, -1.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -8.25F, -26.65F));

        PartDefinition longswitch5 = multiswitchpanel2.addOrReplaceChild("longswitch5",
                CubeListBuilder.create().texOffs(44, 156).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(145, 146).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.25F, -2.5F, -0.5F));

        PartDefinition longswitch6 = multiswitchpanel2.addOrReplaceChild("longswitch6",
                CubeListBuilder.create().texOffs(65, 151).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(36, 145).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.75F, -2.5F, -0.5F));

        PartDefinition longswitch7 = multiswitchpanel2.addOrReplaceChild("longswitch7",
                CubeListBuilder.create().texOffs(149, 9).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(143, 44).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.75F, -2.5F, -0.5F));

        PartDefinition longswitch8 = multiswitchpanel2.addOrReplaceChild("longswitch8",
                CubeListBuilder.create().texOffs(141, 82).addBox(0.0F, -2.5F, -0.5F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                        .texOffs(105, 133).addBox(-0.5F, -3.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.25F, -2.5F, -0.5F));

        PartDefinition computernob2 = controls5.addOrReplaceChild("computernob2",
                CubeListBuilder.create().texOffs(87, 21).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(86, 34).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)).texOffs(9, 31)
                        .addBox(-0.75F, -0.75F, -0.25F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -15.7F, -16.9F, 0.2618F, 0.0F, 0.0F));

        PartDefinition section6 = alnico.addOrReplaceChild("section6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition desktop6 = section6.addOrReplaceChild("desktop6", CubeListBuilder.create().texOffs(45, 21).addBox(-9.5F,
                -13.25F, -27.65F, 19.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r60 = desktop6.addOrReplaceChild("cube_r60",
                CubeListBuilder.create().texOffs(133, 117).addBox(-8.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(134, 41).addBox(7.0F, -4.0F, -8.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(134, 41)
                        .addBox(6.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(138, 84)
                        .addBox(-7.0F, -4.0F, -8.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(22, 45)
                        .addBox(-6.0F, -4.0F, -8.0F, 12.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(55, 48)
                        .addBox(-5.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(55, 59)
                        .addBox(4.0F, -4.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(57, 29)
                        .addBox(-9.0F, -4.0F, -10.0F, 18.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(119, 83)
                        .addBox(-4.0F, -4.0F, 0.0F, 8.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -12.0F, -14.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pillars6 = section6.addOrReplaceChild("pillars6", CubeListBuilder.create().texOffs(111, 91).addBox(-6.0F,
                -19.0F, -12.0F, 12.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r61 = pillars6.addOrReplaceChild("cube_r61",
                CubeListBuilder.create().texOffs(66, 87).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.9554F, -14.2676F, -27.1301F, -1.8326F, -0.3491F, 0.0F));

        PartDefinition cube_r62 = pillars6.addOrReplaceChild("cube_r62",
                CubeListBuilder.create().texOffs(96, 91).addBox(-2.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.0F, -13.75F, -30.0F, -1.309F, -0.3491F, 0.0F));

        PartDefinition cube_r63 = pillars6
                .addOrReplaceChild("cube_r63",
                        CubeListBuilder.create().texOffs(81, 91).addBox(-1.0F, -10.0F, -2.5F, 2.0F, 20.0F, 5.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-6.6827F, -8.1365F, -18.3146F, -1.8326F, 0.3491F, 0.0F));

        PartDefinition cube_r64 = pillars6.addOrReplaceChild("cube_r64",
                CubeListBuilder.create().texOffs(28, 99).addBox(0.0F, -21.0F, 0.9F, 2.0F, 20.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -13.75F, -30.0F, -1.309F, 0.3491F, 0.0F));

        PartDefinition top6 = section6.addOrReplaceChild("top6", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r65 = top6.addOrReplaceChild("cube_r65",
                CubeListBuilder.create().texOffs(90, 21).addBox(-4.0F, -12.4F, -27.0F, 8.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(148, 59).addBox(-1.5F, -19.0F, -12.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r66 = top6.addOrReplaceChild("cube_r66",
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -28.9F, 8.0F, 2.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.5236F, 0.0F));

        PartDefinition cube_r67 = top6.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(35, 0).addBox(-3.0F, -18.0F,
                -22.0F, 6.0F, 0.0F, 17.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.5236F, 0.0F));

        PartDefinition bottom6 = section6.addOrReplaceChild("bottom6",
                CubeListBuilder.create().texOffs(0, 91).addBox(-7.0F, -7.0F, -15.0F, 14.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 2.0F));

        PartDefinition cube_r68 = bottom6.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(116, 16).addBox(-2.5F, -9.0F,
                -19.0F, 5.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r69 = bottom6.addOrReplaceChild("cube_r69",
                CubeListBuilder.create().texOffs(147, 77).addBox(8.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(147, 147).addBox(-11.0F, -3.0F, -1.0F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(0, 21)
                        .addBox(-8.0F, -3.0F, -1.0F, 16.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -9.0F, -24.7F, -0.2618F, 0.0F, 0.0F));

        PartDefinition controls6 = section6.addOrReplaceChild("controls6", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition randomizer3 = controls6.addOrReplaceChild("randomizer3",
                CubeListBuilder.create().texOffs(71, 124).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.0F, -12.9F, -20.833F, 0.0F, 0.5236F, 0.0F));

        PartDefinition sideswitches3 = controls6.addOrReplaceChild("sideswitches3",
                CubeListBuilder.create().texOffs(57, 124).addBox(-2.0F, -1.0F, -3.0F, 3.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.5F, -14.7806F, -20.0173F, 0.2472F, 0.3594F, 0.0043F));

        PartDefinition sideswitch5 = sideswitches3.addOrReplaceChild("sideswitch5",
                CubeListBuilder.create().texOffs(137, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(128, 14).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, 0.0F, 2.5F));

        PartDefinition sideswitch6 = sideswitches3.addOrReplaceChild("sideswitch6",
                CubeListBuilder.create().texOffs(128, 0).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 83).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-0.5F, 0.0F, -1.5F));

        PartDefinition geiger5 = controls6.addOrReplaceChild("geiger5",
                CubeListBuilder.create().texOffs(151, 0).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(100, 141).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(-0.5F, -10.75F, -26.6F));

        PartDefinition needle5 = geiger5.addOrReplaceChild("needle5", CubeListBuilder.create().texOffs(45, 29).addBox(-0.25F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.5F, 0.25F, -1.0F));

        PartDefinition geiger6 = controls6.addOrReplaceChild("geiger6",
                CubeListBuilder.create().texOffs(147, 29).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 143).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(-4.5F, -10.75F, -26.6F));

        PartDefinition needle6 = geiger6.addOrReplaceChild("needle6", CubeListBuilder.create().texOffs(45, 21).addBox(-0.25F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.5F, 0.25F, -1.0F));

        PartDefinition geiger7 = controls6.addOrReplaceChild("geiger7",
                CubeListBuilder.create().texOffs(130, 143).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(122, 31).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(3.5F, -10.75F, -26.6F));

        PartDefinition needle7 = geiger7.addOrReplaceChild("needle7", CubeListBuilder.create().texOffs(15, 15).addBox(-0.25F, -2.0F,
                -0.02F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.5F, 0.25F, -1.0F));

        PartDefinition cashregister = controls6.addOrReplaceChild("cashregister",
                CubeListBuilder.create().texOffs(91, 27).addBox(-4.0F, -2.0F, -6.0F, 8.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(98, 119).addBox(-4.0F, -9.0F, -2.0F, 8.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(128, 110)
                        .addBox(-4.0F, -5.0F, -5.0F, 8.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -13.0F, -15.0F));

        PartDefinition registerlever = cashregister.addOrReplaceChild("registerlever",
                CubeListBuilder.create().texOffs(44, 11).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 0).addBox(0.5F, -3.0F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(108, 0)
                        .addBox(0.0F, -5.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(4.0F, -3.5F, -3.5F));

        PartDefinition registerswitches = cashregister.addOrReplaceChild("registerswitches",
                CubeListBuilder.create().texOffs(75, 113).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.001F))
                        .texOffs(124, 100).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-2.75F, -4.5F, -4.0F));

        PartDefinition registerswitches2 = cashregister.addOrReplaceChild("registerswitches2",
                CubeListBuilder.create().texOffs(46, 67).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.001F))
                        .texOffs(111, 83).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.25F, -4.5F, -4.0F));

        PartDefinition registerswitches3 = cashregister.addOrReplaceChild("registerswitches3",
                CubeListBuilder.create().texOffs(13, 4).addBox(0.0F, -2.0F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.001F))
                        .texOffs(110, 37).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(2.25F, -4.5F, -4.0F));

        PartDefinition column = alnico.addOrReplaceChild("column",
                CubeListBuilder.create().texOffs(89, 52).addBox(-4.0F, -72.0F, -6.93F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(130, 133).addBox(-4.0F, -80.0F, -7.93F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition cube_r70 = column.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(139, 59).addBox(-1.0F, -80.0F,
                -10.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r71 = column.addOrReplaceChild("cube_r71",
                CubeListBuilder.create().texOffs(130, 133)
                        .addBox(-4.0F, -31.0F, -7.93F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(89, 52)
                        .addBox(-4.0F, -23.0F, -6.93F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -49.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r72 = column.addOrReplaceChild("cube_r72",
                CubeListBuilder.create().texOffs(22, 67).addBox(-4.0F, 0.0F, -6.93F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r73 = column.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(17, 37).addBox(0.0F, -32.0F,
                -9.0F, 0.0F, 46.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -36.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r74 = column.addOrReplaceChild("cube_r74",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -37.0F, -11.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r75 = column
                .addOrReplaceChild(
                        "cube_r75", CubeListBuilder.create().texOffs(139, 59).addBox(-1.0F, -80.0F, -10.0F, 2.0F, 12.0F,
                                2.0F, new CubeDeformation(0.01F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.5236F, -3.1416F));

        PartDefinition cube_r76 = column.addOrReplaceChild("cube_r76",
                CubeListBuilder.create().texOffs(130, 133)
                        .addBox(-4.0F, -31.0F, -7.93F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(89, 52)
                        .addBox(-4.0F, -23.0F, -6.93F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -49.0F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r77 = column.addOrReplaceChild("cube_r77",
                CubeListBuilder.create().texOffs(22, 67).addBox(-4.0F, 0.0F, -6.93F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r78 = column
                .addOrReplaceChild("cube_r78",
                        CubeListBuilder.create().texOffs(17, 37).addBox(0.0F, -32.0F, -9.0F, 0.0F, 46.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -36.0F, 0.0F, 3.1416F, 0.5236F, -3.1416F));

        PartDefinition cube_r79 = column.addOrReplaceChild("cube_r79",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -37.0F, -11.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, -3.1416F, 0.5236F, 3.1416F));

        PartDefinition cube_r80 = column
                .addOrReplaceChild(
                        "cube_r80", CubeListBuilder.create().texOffs(139, 59).addBox(-1.0F, -80.0F, -10.0F, 2.0F, 12.0F,
                                2.0F, new CubeDeformation(0.01F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, -0.5236F, -3.1416F));

        PartDefinition cube_r81 = column.addOrReplaceChild("cube_r81",
                CubeListBuilder.create().texOffs(130, 133)
                        .addBox(-4.0F, -31.0F, -7.93F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(89, 52)
                        .addBox(-4.0F, -23.0F, -6.93F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -49.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r82 = column.addOrReplaceChild("cube_r82",
                CubeListBuilder.create().texOffs(22, 67).addBox(-4.0F, 0.0F, -6.93F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r83 = column
                .addOrReplaceChild("cube_r83",
                        CubeListBuilder.create().texOffs(17, 37).addBox(0.0F, -32.0F, -9.0F, 0.0F, 46.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -36.0F, 0.0F, 3.1416F, -0.5236F, -3.1416F));

        PartDefinition cube_r84 = column.addOrReplaceChild("cube_r84",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -37.0F, -11.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, -3.1416F, -0.5236F, 3.1416F));

        PartDefinition cube_r85 = column.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(139, 59).addBox(-1.0F, -80.0F,
                -10.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r86 = column.addOrReplaceChild("cube_r86",
                CubeListBuilder.create().texOffs(130, 133)
                        .addBox(-4.0F, -31.0F, -7.93F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(89, 52)
                        .addBox(-4.0F, -23.0F, -6.93F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -49.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r87 = column.addOrReplaceChild("cube_r87",
                CubeListBuilder.create().texOffs(22, 67).addBox(-4.0F, 0.0F, -6.93F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r88 = column.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(17, 37).addBox(0.0F, -32.0F,
                -9.0F, 0.0F, 46.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -36.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r89 = column.addOrReplaceChild("cube_r89",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -37.0F, -11.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r90 = column.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(139, 59).addBox(-1.0F, -80.0F,
                -10.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r91 = column.addOrReplaceChild("cube_r91",
                CubeListBuilder.create().texOffs(130, 133)
                        .addBox(-4.0F, -31.0F, -7.93F, 8.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(89, 52)
                        .addBox(-4.0F, -23.0F, -6.93F, 8.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -49.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r92 = column.addOrReplaceChild("cube_r92",
                CubeListBuilder.create().texOffs(22, 67).addBox(-4.0F, 0.0F, -6.93F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r93 = column.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(17, 37).addBox(0.0F, -32.0F,
                -9.0F, 0.0F, 46.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -36.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r94 = column.addOrReplaceChild("cube_r94",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -37.0F, -11.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r95 = column.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(17, 37).addBox(0.0F, -32.0F,
                -9.0F, 0.0F, 46.0F, 2.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, -36.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r96 = column.addOrReplaceChild("cube_r96",
                CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -37.0F, -11.0F, 2.0F, 10.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r97 = column.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(139, 59).addBox(-1.0F, -80.0F,
                -10.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.01F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r98 = column.addOrReplaceChild("cube_r98",
                CubeListBuilder.create().texOffs(22, 67).addBox(-4.0F, 0.0F, -6.93F, 8.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition glass = column.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -67.0F, -6.93F,
                8.0F, 41.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r99 = glass.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -67.0F,
                -6.93F, 8.0F, 41.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r100 = glass
                .addOrReplaceChild(
                        "cube_r100", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -67.0F, -6.93F, 8.0F, 41.0F,
                                0.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r101 = glass
                .addOrReplaceChild(
                        "cube_r101", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -67.0F, -6.93F, 8.0F, 41.0F,
                                0.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r102 = glass
                .addOrReplaceChild(
                        "cube_r102", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -67.0F, -6.93F, 8.0F, 41.0F,
                                0.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r103 = glass.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(0, 37).addBox(-4.0F, -67.0F,
                -6.93F, 8.0F, 41.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition timerotor = column.addOrReplaceChild("timerotor", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bottomgizmo = timerotor.addOrReplaceChild("bottomgizmo",
                CubeListBuilder.create().texOffs(111, 100)
                        .addBox(-2.0F, -14.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(33, 87)
                        .addBox(-4.0F, -4.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(106, 0)
                        .addBox(-3.5F, -8.0F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(115, 37)
                        .addBox(-3.0F, -12.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(0, 134)
                        .addBox(-6.0F, -16.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(19, 101)
                        .addBox(2.0F, -16.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(82, 136)
                        .addBox(-1.0F, -19.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -26.0F, 0.0F));

        PartDefinition cube_r104 = bottomgizmo.addOrReplaceChild("cube_r104",
                CubeListBuilder.create().texOffs(46, 131)
                        .addBox(2.0F, -42.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(35, 0)
                        .addBox(-6.0F, -42.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 26.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition topgizmo = timerotor.addOrReplaceChild("topgizmo",
                CubeListBuilder.create().texOffs(81, 117)
                        .addBox(-2.0F, -14.0F, -2.0F, 4.0F, 14.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(86, 79)
                        .addBox(-4.0F, -4.0F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(93, 41)
                        .addBox(-3.5F, -8.0F, -3.5F, 7.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).texOffs(114, 59)
                        .addBox(-3.0F, -12.0F, -3.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(55, 134)
                        .addBox(-6.0F, -16.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(64, 134)
                        .addBox(2.0F, -16.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)).texOffs(73, 134)
                        .addBox(-1.0F, -19.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -67.0F, 0.0F, 3.1416F, 0.0F, 0.0F));

        PartDefinition cube_r105 = topgizmo.addOrReplaceChild("cube_r105",
                CubeListBuilder.create().texOffs(9, 134).addBox(2.0F, -42.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F))
                        .texOffs(18, 135).addBox(-6.0F, -42.0F, 0.0F, 4.0F, 16.0F, 0.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 26.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition holographicmonitor = alnico.addOrReplaceChild("holographicmonitor", CubeListBuilder.create(),
                PartPose.offset(0.0F, 12.0F, 0.0F));

        PartDefinition monitor = holographicmonitor.addOrReplaceChild("monitor",
                CubeListBuilder.create().texOffs(57, 34)
                        .addBox(-6.5F, 0.0F, -13.25F, 13.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(79, 75)
                        .addBox(-5.5F, -0.5F, -15.25F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -34.0F, 0.0F));

        PartDefinition monitorhandle = monitor.addOrReplaceChild("monitorhandle",
                CubeListBuilder.create().texOffs(116, 152)
                        .addBox(-0.975F, -1.0F, -1.0335F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(43, 104)
                        .addBox(-0.975F, -0.5F, -6.0335F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 29)
                        .addBox(-0.975F, 0.0F, -4.0335F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -7.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition monitor2 = holographicmonitor.addOrReplaceChild("monitor2",
                CubeListBuilder.create().texOffs(57, 34)
                        .addBox(-6.5F, 0.0F, -13.25F, 13.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(79, 75)
                        .addBox(-5.5F, -0.5F, -15.25F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition monitorhandle2 = monitor2.addOrReplaceChild("monitorhandle2",
                CubeListBuilder.create().texOffs(116, 152)
                        .addBox(-0.975F, -1.0F, -1.0335F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(43, 104)
                        .addBox(-0.975F, -0.5F, -6.0335F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 29)
                        .addBox(-0.975F, 0.0F, -4.0335F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -7.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition monitor3 = holographicmonitor.addOrReplaceChild("monitor3",
                CubeListBuilder.create().texOffs(57, 34)
                        .addBox(-6.5F, 0.0F, -13.25F, 13.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(79, 75)
                        .addBox(-5.5F, -0.5F, -15.25F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition monitorhandle3 = monitor3.addOrReplaceChild("monitorhandle3",
                CubeListBuilder.create().texOffs(116, 152)
                        .addBox(-0.975F, -1.0F, -1.0335F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(43, 104)
                        .addBox(-0.975F, -0.5F, -6.0335F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 29)
                        .addBox(-0.975F, 0.0F, -4.0335F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -7.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition monitor4 = holographicmonitor.addOrReplaceChild("monitor4",
                CubeListBuilder.create().texOffs(57, 34)
                        .addBox(-6.5F, 0.0F, -13.25F, 13.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(79, 75)
                        .addBox(-5.5F, -0.5F, -15.25F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(111, 133)
                        .addBox(-4.5F, 0.5F, -14.25F, 9.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition monitorhandle4 = monitor4.addOrReplaceChild("monitorhandle4",
                CubeListBuilder.create().texOffs(116, 152)
                        .addBox(-0.975F, -1.0F, -1.0335F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(43, 104)
                        .addBox(-0.975F, -0.5F, -6.0335F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 29)
                        .addBox(-0.975F, 0.0F, -4.0335F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -7.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition monitor5 = holographicmonitor.addOrReplaceChild("monitor5",
                CubeListBuilder.create().texOffs(57, 34)
                        .addBox(-6.5F, 0.0F, -13.25F, 13.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(79, 75)
                        .addBox(-5.5F, -0.5F, -15.25F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition monitorhandle5 = monitor5.addOrReplaceChild("monitorhandle5",
                CubeListBuilder.create().texOffs(116, 152)
                        .addBox(-0.975F, -1.0F, -1.0335F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(43, 104)
                        .addBox(-0.975F, -0.5F, -6.0335F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 29)
                        .addBox(-0.975F, 0.0F, -4.0335F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -7.75F, 0.0F, 0.5236F, 0.0F));

        PartDefinition monitor6 = holographicmonitor.addOrReplaceChild("monitor6",
                CubeListBuilder.create().texOffs(57, 34)
                        .addBox(-6.5F, 0.0F, -13.25F, 13.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)).texOffs(79, 75)
                        .addBox(-5.5F, -0.5F, -15.25F, 11.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -34.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition monitorhandle6 = monitor6.addOrReplaceChild("monitorhandle6",
                CubeListBuilder.create().texOffs(116, 152)
                        .addBox(-0.975F, -1.0F, -1.0335F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(43, 104)
                        .addBox(-0.975F, -0.5F, -6.0335F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 29)
                        .addBox(-0.975F, 0.0F, -4.0335F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(-4.5F, 0.0F, -7.75F, 0.0F, 0.5236F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        alnico.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = this.controlDelta();
        matrices.pushPose();
        this.applyRootTransform(matrices);

        // Throttle Control
        ModelPart throttle = alnico.getChild("section1").getChild("controls").getChild("fliplever1").getChild("bone5");
        float throttleTarget = tardis.travel().maxSpeed().get() > 0 ? ((float) tardis.travel().speed() / (float) tardis.travel().maxSpeed().get()) * 1.5f: 0f;
        throttle.xRot = getAngle(console, "throttle", throttleTarget, delta);

        // Handbrake Control
        ModelPart handbrake = alnico.getChild("section1").getChild("controls").getChild("biglever").getChild("bone");
        float handbrakeTarget = !tardis.travel().handbrake() ? -0.9f : 0.9f;
        handbrake.xRot = getAngle(console, "handbrake", handbrakeTarget, delta);

        // Power Control
        ModelPart power = alnico.getChild("section4").getChild("controls4").getChild("biglever2").getChild("bone12");
        float powerTarget = !tardis.fuel().hasPower() ? -0.9f : 0.9f;
        power.xRot = getAngle(console, "power", powerTarget, delta);

        // Auto Pilot Control
        ModelPart autoPilot = alnico.getChild("section1").getChild("controls").getChild("multiswitchpanel")
                .getChild("longswitch1");
        float autopilotTarget = tardis.travel().autopilot() ? 0.5f : 0;
        autoPilot.xRot = getAngle(console, "autopilot", autopilotTarget, delta);

        // Security Control
        ModelPart security = alnico.getChild("section1").getChild("controls").getChild("multiswitchpanel")
                .getChild("longswitch4");
        float securityTarget = tardis.stats().security().get() ? 0.5f : 0;
        security.xRot = getAngle(console, "security", securityTarget, delta);

        // Siege Mode Control
        ModelPart siegeMode = alnico.getChild("section3").getChild("controls3").getChild("siegemode").getChild("lever");
        float siegeTarget = tardis.siege().isActive() ? 0.9f : 0;
        siegeMode.xRot = getAngle(console, "siege_mode", siegeTarget, delta);

        // Refueler
        ModelPart refueler = alnico.getChild("section5").getChild("controls5").getChild("refueler").getChild("gasknob");
        float refuelerTarget = !tardis.isRefueling() ? -0.7854f : 0;
        refueler.yRot = getAngle(console, "refueler", refuelerTarget, delta);

        // Fuel Gauge
        ModelPart fuelGauge = alnico.getChild("section3").getChild("controls3").getChild("geiger").getChild("needle");
        fuelGauge.zRot = (float) (((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 2) - 1);

        // Increment Control
        ModelPart increment = alnico.getChild("section5").getChild("controls5").getChild("multiswitchpanel2").getChild("longswitch5");
        int incrementVal = IncrementManager.increment(tardis);
        float targetOffset;

        if (incrementVal < 10) {
            targetOffset = 0f;
        } else if (incrementVal < 100) {
            targetOffset = 0.5f;
        } else if (incrementVal < 1000) {
            targetOffset = 1.0f;
        } else if (incrementVal < 10000) {
            targetOffset = 1.25f;
        } else {
            targetOffset = 1.5f;
        }
        increment.xRot = getAngle(console, "increment", targetOffset, delta);

        // Shield Control
        ModelPart shield = alnico.getChild("section5").getChild("controls5").getChild("multiswitchpanel2")
                .getChild("longswitch8");
        float shieldTarget = tardis.shields().shielded().get() ? 1f : 0;
        shield.xRot = getAngle(console, "shields", shieldTarget, delta);

        // Land Type
        ModelPart landtype = alnico.getChild("section1").getChild("controls").getChild("tinyswitch2").getChild("bone2");
        float landTypeTarget = tardis.travel().horizontalSearch().get() ? 1.5708f : 0;
        landtype.yRot = getAngle(console, "landtype", landTypeTarget, delta);

        // Anti Gravs
        ModelPart antigravs = alnico.getChild("section1").getChild("controls").getChild("tinyswitch").getChild("bone3");
        float antigravsTarget = tardis.travel().antigravs().get() ? -1.5708f : 0;
        antigravs.yRot = getAngle(console, "antigravs", antigravsTarget, delta);

        // Door Control
        ModelPart doorControl = alnico.getChild("section5").getChild("controls5").getChild("tinyswitch6")
                .getChild("bone24");
        float doorControlTarget = tardis.door().isOpen() ? tardis.door().isRightOpen() ? 1.5708f * 2f : 1.5708f : 0;
        doorControl.yRot = getAngle(console, "door_control", doorControlTarget, delta);

        // Door Lock
        ModelPart doorLock = alnico.getChild("section5").getChild("controls5").getChild("tinyswitch7")
                .getChild("bone25");
        float doorLockTarget = tardis.door().locked() ? 1.5708f : 0;
        doorLock.yRot = getAngle(console, "door_lock", doorLockTarget, delta);

        // Waypoints
        ModelPart toast1 = alnico.getChild("section2").getChild("controls2").getChild("waypointcatridge")
                .getChild("toast1");
        ModelPart toastlever = alnico.getChild("section2").getChild("controls2").getChild("waypointcatridge")
                .getChild("toastlever");
        ModelPart toast2 = alnico.getChild("section2").getChild("controls2").getChild("waypointcatridge")
                .getChild("toast2");
        toast1.visible = tardis.waypoint().hasCartridge();
        toastlever.y = toastlever.y + (!tardis.waypoint().hasCartridge() ? 2f : 0);
        toast2.visible = tardis.waypoint().hasCartridge();

        // Direction Control
        ModelPart direction = alnico.getChild("section5").getChild("controls5").getChild("tinyswitch9")
                .getChild("bone26");
        float directionTargetDegrees = (0.3927f * tardis.travel().destination().getRotation()) * (180f / (float) Math.PI);
        direction.yRot = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);

        matrices.popPose();
    }

    @Override
    public void renderMonitorText(Tardis tardis, ConsoleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        super.renderMonitorText(tardis, entity, matrices, vertexConsumers, light, overlay);

        Minecraft client = Minecraft.getInstance();
        Font renderer = client.font;
        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos abpd = travel.destination();
        BlockPos abpdPos = abpd.getPos();
        matrices.pushPose();
        // TODO dont forget to add variant.getConsoleTextPosition()!
        matrices.translate(1.86, 0.47, 0.30);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.004f, 0.004f, 0.004f);
        matrices.mulPose(Axis.YN.rotationDegrees(60f));
        matrices.translate(-252f, -228, 5f);
        String destinationPosText = abpdPos.getX() + ", " + abpdPos.getY() + ", " + abpdPos.getZ();
        Component destinationDimensionText = WorldUtil.worldText(abpd.getDimension(), false);
        String destinationDirectionText = DirectionControl.rotationToDirection(abpd.getRotation()).toUpperCase();
        String fuelText = Math.round((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 100) + "%";
        int y = 41;
        renderer.drawInBatch8xOutline(Component.nullToEmpty("\uD83E\uDC97").getVisualOrderText(), 0, y, 0xFF0000, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(destinationPosText).getVisualOrderText(), 8, y, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(destinationDimensionText.getVisualOrderText(), 8, y + 8, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(destinationDirectionText).getVisualOrderText(), 8, y + 16, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.translatable("ait.monitor.fuel_with_text", fuelText).getVisualOrderText(), 8, y + 24, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.41, 1.40, 0.38);
        matrices.mulPose(Axis.XP.rotationDegrees(150f));
        matrices.scale(0.015f, 0.015f, 0.015f);
        matrices.mulPose(Axis.YN.rotationDegrees(180f));
        matrices.mulPose(Axis.XP.rotationDegrees(-30.5f));
        String progressText = tardis.travel().getState() == TravelHandlerBase.State.LANDED
                ? "0"
                : tardis.travel().getDurationAsPercentage() + " ";
        matrices.translate(0, -38, -52);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(progressText).getVisualOrderText(), 0 - renderer.width(progressText) / 2, 0, 0xffffff, 0x03cffc,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return alnico;
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        return switch (state) {
            case FLIGHT, MAT, DEMAT -> AlnicoAnimations.CONSOLE_ALNICO_FLIGHT;
            case LANDED -> AlnicoAnimations.CONSOLE_ALNICO_IDLE;
            default -> NO_ANIMATION;
        };
    }
}
