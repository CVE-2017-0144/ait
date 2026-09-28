package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.client.animation.console.copper.CopperAnimations;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
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

public class CopperConsoleModel extends SimpleConsoleModel {
    private final ModelPart copper;
    public CopperConsoleModel(ModelPart root) {
        this.copper = root.getChild("copper");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition copper = modelPartData.addOrReplaceChild("copper", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition desktop = copper.addOrReplaceChild("desktop", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rim = desktop.addOrReplaceChild("rim", CubeListBuilder.create().texOffs(51, 50).addBox(18.0F, -5.0F, -8.0F, 2.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition panels = desktop.addOrReplaceChild("panels", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot = panels.addOrReplaceChild("rot", CubeListBuilder.create().texOffs(28, 30).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-10.0F, -0.2F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(-9.5F, -0.6F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition desktop2 = desktop.addOrReplaceChild("desktop2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rim2 = desktop2.addOrReplaceChild("rim2", CubeListBuilder.create().texOffs(51, 50).addBox(18.0F, -5.0F, -8.0F, 2.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition panels8 = desktop2.addOrReplaceChild("panels8", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot2 = panels8.addOrReplaceChild("rot2", CubeListBuilder.create().texOffs(28, 30).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-10.0F, -0.2F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(-9.5F, -0.6F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition desktop3 = desktop2.addOrReplaceChild("desktop3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rim3 = desktop3.addOrReplaceChild("rim3", CubeListBuilder.create().texOffs(51, 50).addBox(18.0F, -5.0F, -8.0F, 2.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition panels9 = desktop3.addOrReplaceChild("panels9", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot3 = panels9.addOrReplaceChild("rot3", CubeListBuilder.create().texOffs(28, 30).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-10.0F, -0.2F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(-9.5F, -0.6F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition cables4 = rot3.addOrReplaceChild("cables4", CubeListBuilder.create().texOffs(0, 120).addBox(-0.25F, 0.5F, -6.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(77, 12).addBox(-1.5F, 1.0F, 5.25F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5351F, 7.9126F, 0.0F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r1 = cables4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(100, 96).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r2 = cables4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(101, 33).addBox(-1.0F, 0.0F, -6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition desktop4 = desktop3.addOrReplaceChild("desktop4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rim4 = desktop4.addOrReplaceChild("rim4", CubeListBuilder.create().texOffs(51, 50).addBox(18.0F, -5.0F, -8.0F, 2.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition panels10 = desktop4.addOrReplaceChild("panels10", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot4 = panels10.addOrReplaceChild("rot4", CubeListBuilder.create().texOffs(28, 30).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-10.0F, -0.2F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(-9.5F, -0.6F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition bone123 = rot4.addOrReplaceChild("bone123", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 0.0F, 6.4F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r3 = bone123.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(156, 116).addBox(-3.8F, -1.9F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.4001F)), PartPose.offsetAndRotation(-4.583F, -0.5072F, -0.6219F, 0.0F, 0.48F, 0.0436F));

        PartDefinition cube_r4 = bone123.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(59, 232).addBox(-4.8F, -1.9F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
                .texOffs(218, 143).addBox(-1.5F, -1.9F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.5274F, -0.3737F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition cube_r5 = bone123.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(191, 140).addBox(-1.5F, -1.9F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition bone125 = bone123.addOrReplaceChild("bone125", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.583F, -0.5072F, -0.6219F, 0.0F, 0.48F, 0.0436F));

        PartDefinition cube_r6 = bone125.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(165, 4).addBox(-13.8F, -1.9F, -1.0F, 13.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-29.4929F, -38.3724F, -1.3642F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r7 = bone125.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(165, 4).addBox(-33.8F, -1.9F, -1.0F, 33.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-13.5515F, -10.5405F, -1.3642F, 0.0F, 0.0F, 1.0472F));

        PartDefinition cube_r8 = bone125.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(213, 69).addBox(-5.8F, -1.9F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.4001F)), PartPose.offsetAndRotation(-12.4151F, -6.7424F, -1.3642F, 0.0F, 0.0F, 1.2217F));

        PartDefinition cube_r9 = bone125.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(222, 33).addBox(-5.8F, -1.9F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-8.5998F, -4.2717F, -1.3642F, 0.0F, 0.0F, 0.6981F));

        PartDefinition cube_r10 = bone125.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(209, 12).addBox(-5.8F, -1.9F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3998F)), PartPose.offsetAndRotation(-5.7891F, -1.9133F, 0.4465F, 0.0F, -0.3491F, 0.6981F));

        PartDefinition cube_r11 = bone125.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(144, 216).addBox(-5.8F, -1.9F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3999F)), PartPose.offsetAndRotation(-2.6735F, 0.7011F, -0.0001F, 0.0F, 0.0F, 0.6981F));

        PartDefinition bone124 = bone123.addOrReplaceChild("bone124", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 0.0F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition desktop5 = desktop4.addOrReplaceChild("desktop5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rim5 = desktop5.addOrReplaceChild("rim5", CubeListBuilder.create().texOffs(51, 50).addBox(18.0F, -5.0F, -8.0F, 2.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition panels11 = desktop5.addOrReplaceChild("panels11", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot5 = panels11.addOrReplaceChild("rot5", CubeListBuilder.create().texOffs(28, 30).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-10.0F, -0.2F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(-9.5F, -0.6F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(129, 172).addBox(-11.6F, -0.7F, -4.2F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition cube_r12 = rot5.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(117, 189).addBox(-15.8F, -0.65F, 0.725F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(122, 196).addBox(-15.3F, -0.95F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(150, 180).addBox(-14.7F, -0.6F, -0.275F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(137, 167).addBox(-11.8F, -0.55F, -4.95F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.4842F, 5.6504F, 0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition cube_r13 = rot5.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(122, 137).addBox(-8.8F, -0.95F, -2.25F, 5.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(124, 149).addBox(-7.8F, -0.35F, -4.85F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(124, 149).addBox(-7.8F, -0.35F, -3.55F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(118, 146).addBox(-6.5F, -0.35F, -3.55F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(118, 146).addBox(-6.5F, -0.35F, -4.85F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(124, 143).addBox(-7.8F, -0.25F, -3.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(118, 140).addBox(-6.5F, -0.25F, -3.55F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(118, 140).addBox(-6.5F, -0.25F, -4.85F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(124, 143).addBox(-7.8F, -0.25F, -4.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(122, 131).addBox(-8.8F, -0.85F, -2.25F, 5.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.4F, 1.25F, 0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone120 = rot5.addOrReplaceChild("bone120", CubeListBuilder.create(), PartPose.offset(-5.4842F, 5.6504F, 0.75F));

        PartDefinition Cassette = rot5.addOrReplaceChild("Cassette", CubeListBuilder.create().texOffs(92, 138).addBox(-0.5F, 6.0F, -5.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(91, 131).addBox(0.0F, 6.5F, -4.5F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(105, 133).addBox(0.0F, 6.4F, -4.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(105, 133).addBox(0.0F, 6.4F, -2.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.3F, -8.6F, 2.5F));

        PartDefinition cube_r14 = Cassette.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(98, 147).addBox(-0.5F, 0.0F, -5.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r15 = Cassette.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 6.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r16 = Cassette.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(98, 147).addBox(-0.5F, 0.0F, -5.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, 0.0F, 0.0F, 2.2689F));

        PartDefinition cube_r17 = Cassette.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(98, 147).addBox(-0.5F, 0.0F, -5.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, 0.0F, 0.0F, 2.2689F));

        PartDefinition cube_r18 = Cassette.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(98, 147).addBox(-0.5F, 0.0F, -5.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r19 = Cassette.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(98, 147).addBox(-0.5F, 0.0F, -5.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.0F, 0.0F, 2.2689F));

        PartDefinition cube_r20 = Cassette.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(98, 147).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.0F, -5.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition bone118 = Cassette.addOrReplaceChild("bone118", CubeListBuilder.create(), PartPose.offset(7.3F, 7.85F, -1.75F));

        PartDefinition cube_r21 = bone118.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(135, 152).addBox(-10.8F, -0.95F, -5.25F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(135, 148).addBox(-10.8F, -0.85F, -5.25F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(135, 158).addBox(-10.8F, -0.45F, -2.25F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(136, 161).addBox(-10.8F, -0.35F, -2.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition bone119 = bone118.addOrReplaceChild("bone119", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r22 = bone119.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(129, 158).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0307F, -0.1284F, -5.2912F, -0.2618F, 0.0F, 1.5708F));

        PartDefinition cube_r23 = bone119.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(129, 160).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.4679F, -0.8388F, -5.4083F, -0.1128F, -0.2368F, 0.4498F));

        PartDefinition cube_r24 = bone119.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(129, 156).addBox(-0.5F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.3881F, -1.05F, -5.6549F, 0.0F, -0.2618F, 0.0F));

        PartDefinition bone117 = Cassette.addOrReplaceChild("bone117", CubeListBuilder.create().texOffs(110, 160).addBox(-3.6F, -0.05F, -4.55F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(123, 169).addBox(-4.1F, -0.35F, -4.05F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(127, 166).addBox(-3.1F, -1.675F, -4.05F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(131, 170).addBox(-2.6F, -2.175F, -3.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(124, 160).addBox(-2.6F, -1.575F, -3.55F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(118, 165).addBox(-3.85F, -0.65F, -4.3F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(7.3F, 7.85F, -2.15F));

        PartDefinition typewriter = rot5.addOrReplaceChild("typewriter", CubeListBuilder.create().texOffs(63, 146).addBox(-4.5F, -2.6F, -1.0F, 5.0F, 3.0F, 6.0F, new CubeDeformation(0.001F))
                .texOffs(47, 146).addBox(-4.5F, -3.6F, -1.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(55, 164).addBox(-3.5F, -3.6F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(61, 134).addBox(0.5F, -1.6F, -1.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 158).addBox(0.5F, -0.7F, -1.0F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 158).addBox(0.5F, -1.2F, -1.0F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 158).addBox(0.5F, -1.1F, -0.6F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(47, 158).addBox(-4.5F, -1.7F, -1.0F, 7.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.75F, 0.75F));

        PartDefinition bone116 = rot5.addOrReplaceChild("bone116", CubeListBuilder.create().texOffs(72, 125).addBox(-8.15F, -1.5F, 4.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-8.75F, -1.5F, 4.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-9.35F, -1.5F, 4.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-9.35F, -1.5F, 5.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-8.75F, -1.5F, 5.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-8.15F, -1.5F, 5.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(63, 27).addBox(-9.25F, -0.8F, 4.7F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(63, 27).addBox(-9.25F, -0.8F, 5.8F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 156).addBox(-0.85F, -0.9F, 5.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -0.75F, 3.4F, -0.0227F, -0.4795F, -0.0492F));

        PartDefinition cube_r25 = bone116.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(175, 85).addBox(0.5F, -1.85F, -0.8F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 0.925F, 6.35F, 0.0087F, 0.0F, 0.0F));

        PartDefinition cube_r26 = bone116.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(49, 125).addBox(1.0F, -1.65F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-7.75F, 0.45F, 5.1F, 0.0087F, 0.0F, 0.0F));

        PartDefinition cube_r27 = bone116.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(40, 130).addBox(1.0F, -1.15F, -0.3F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.75F, 0.45F, 6.1F, 0.0087F, 0.0F, 0.0F));

        PartDefinition cube_r28 = bone116.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(175, 63).addBox(0.0F, -1.65F, -1.3F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.25F, 0.75F, 6.1F, 0.0087F, 0.0F, 0.0F));

        PartDefinition desktop6 = desktop5.addOrReplaceChild("desktop6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rim6 = desktop6.addOrReplaceChild("rim6", CubeListBuilder.create().texOffs(51, 50).addBox(18.0F, -5.0F, -8.0F, 2.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition panels12 = desktop6.addOrReplaceChild("panels12", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot6 = panels12.addOrReplaceChild("rot6", CubeListBuilder.create().texOffs(28, 30).addBox(-10.0F, 0.0F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(-10.0F, -0.2F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(-9.5F, -0.6F, -7.0F, 10.0F, 0.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(219, 150).addBox(-4.2F, -0.725F, -6.1F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(219, 253).addBox(-3.3F, -0.8F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.6109F));

        PartDefinition cube_r29 = rot6.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(203, 201).addBox(-1.0F, -1.1F, 0.2F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(203, 195).addBox(-1.7F, -1.2F, -0.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(203, 201).addBox(-1.8F, -1.1F, -1.3F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(199, 215).addBox(-5.3F, -3.6F, -0.3F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(201, 207).addBox(-5.8F, -1.1F, -1.3F, 3.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 8.7F, 0.0F, -0.48F, -0.0873F));

        PartDefinition cube_r30 = rot6.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(211, 200).addBox(-1.5791F, -1.6171F, -0.5691F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.6026F, 0.6591F, 7.5787F, -0.3069F, -0.3751F, 0.6259F));

        PartDefinition cube_r31 = rot6.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(221, 250).addBox(-0.925F, -1.5F, 0.175F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.05F, -1.3F, 1.5F, -3.1416F, 0.7854F, 3.1416F));

        PartDefinition cube_r32 = rot6.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(221, 250).addBox(-0.575F, -1.5F, 0.175F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.05F, -1.3F, 1.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r33 = rot6.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(204, 251).addBox(-1.225F, -1.425F, 1.875F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, -0.3F, 1.6F, 0.0F, -0.4363F, 0.0F));

        PartDefinition cube_r34 = rot6.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(245, 176).addBox(6.9F, -4.225F, -4.35F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(226, 160).addBox(2.0F, -3.725F, -7.1F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.3993F, -0.2272F, 1.0F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r35 = rot6.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(211, 244).addBox(-2.0F, -0.7F, -0.1F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2F, 0.0F, 1.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition dial4 = rot6.addOrReplaceChild("dial4", CubeListBuilder.create().texOffs(67, 96).addBox(0.05F, -1.25F, -1.25F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(123, 114).addBox(-0.45F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0868F, 0.8499F, 9.0F, 0.0F, 0.0F, -0.6109F));

        PartDefinition lever9 = rot6.addOrReplaceChild("lever9", CubeListBuilder.create().texOffs(224, 125).addBox(-1.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.29F))
                .texOffs(226, 118).addBox(-0.6F, -1.75F, -1.1F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(233, 182).addBox(0.0F, -2.0F, -1.2F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(224, 136).addBox(-1.0F, -2.05F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(224, 136).addBox(-1.0F, -2.05F, -0.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(225, 136).addBox(0.0F, -2.325F, -1.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(225, 136).addBox(0.0F, -2.325F, -1.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-3.6555F, -0.2059F, -6.4786F, -0.0227F, 0.4795F, -0.0492F));

        PartDefinition blackredlever2 = lever9.addOrReplaceChild("blackredlever2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.9055F, -1.6441F, 0.5786F, 0.0F, 0.0F, -0.6981F));

        PartDefinition redleverright2 = blackredlever2.addOrReplaceChild("redleverright2", CubeListBuilder.create().texOffs(188, 234).addBox(-0.5F, -2.4941F, -0.475F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(161, 127).addBox(-0.5F, -2.7F, -0.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(161, 129).addBox(-0.5F, -2.7F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.1399F, 0.0549F, -1.025F, 0.0F, 0.0F, 0.0611F));

        PartDefinition cube_r36 = redleverright2.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.025F, 0.0F, 1.5708F, -1.5708F));

        PartDefinition cube_r37 = redleverright2.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -2.2F, -0.55F, 0.0F, 3.1416F, 0.0F));

        PartDefinition redleverleft2 = blackredlever2.addOrReplaceChild("redleverleft2", CubeListBuilder.create().texOffs(188, 234).addBox(-0.4877F, -2.4051F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(161, 129).addBox(-0.4877F, -2.6108F, -0.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.1756F, -0.0242F, -1.5F, 0.0F, 0.0F, 0.0436F));

        PartDefinition cube_r38 = redleverleft2.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(161, 127).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0123F, -2.1108F, -0.15F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r39 = redleverleft2.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0123F, -1.9108F, 0.0F, 0.0F, -1.5708F, 1.5708F));

        PartDefinition cube_r40 = redleverleft2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0123F, -2.1108F, -0.35F, 0.0F, 3.1416F, 0.0F));

        PartDefinition blacklever3 = blackredlever2.addOrReplaceChild("blacklever3", CubeListBuilder.create().texOffs(159, 110).addBox(-0.5F, -2.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(193, 234).addBox(-0.5F, -1.9F, -0.225F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.2F, 0.0F));

        PartDefinition cube_r41 = blacklever3.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(159, 110).addBox(-0.725F, -2.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition blacklever4 = blackredlever2.addOrReplaceChild("blacklever4", CubeListBuilder.create().texOffs(193, 234).addBox(-0.5F, -1.9F, -0.8F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(159, 110).addBox(-0.5F, -2.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.2F, 0.0F));

        PartDefinition cube_r42 = blacklever4.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(159, 110).addBox(-0.25F, -2.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone132 = rot6.addOrReplaceChild("bone132", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.3F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r43 = bone132.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(151, 44).addBox(-0.5F, -1.2F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(150, 47).addBox(-1.0F, -1.1F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(142, 132).addBox(-1.5F, -0.5F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-4.9F, -1.0F, -7.5F, 0.0F, 0.0F, -0.0436F));

        PartDefinition bone136 = rot6.addOrReplaceChild("bone136", CubeListBuilder.create(), PartPose.offset(-2.5F, -4.0F, 0.5F));

        PartDefinition bone138 = rot6.addOrReplaceChild("bone138", CubeListBuilder.create(), PartPose.offset(-2.4F, -4.0F, 0.3F));

        PartDefinition bone157 = bone138.addOrReplaceChild("bone157", CubeListBuilder.create(), PartPose.offset(1.8F, 2.0F, -2.9F));

        PartDefinition cube_r44 = bone157.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(116, 228).addBox(-0.8F, -2.0F, -0.9F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.825F, 1.225F, 0.025F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone142 = rot6.addOrReplaceChild("bone142", CubeListBuilder.create().texOffs(245, 18).addBox(-1.1125F, -0.4375F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(244, 7).addBox(-0.8625F, -0.3375F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(248, 12).addBox(-0.8625F, -0.3875F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(-5.3375F, -0.1625F, -1.45F));

        PartDefinition cube_r45 = bone142.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(155, 0).addBox(-0.75F, 0.0F, -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0875F, -0.3375F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone140 = rot6.addOrReplaceChild("bone140", CubeListBuilder.create().texOffs(156, 61).addBox(-1.5F, -0.35F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-5.7F, -0.15F, 1.4F));

        PartDefinition bone141 = rot6.addOrReplaceChild("bone141", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r46 = bone141.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(151, 73).addBox(-5.6F, -1.075F, 6.475F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(99, 245).addBox(-5.6F, -2.9F, 6.475F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(169, 151).addBox(-6.375F, -2.725F, 6.25F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(151, 73).addBox(-5.6F, -1.875F, 6.475F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, -0.0008F, -0.0611F));

        PartDefinition cables3 = rot6.addOrReplaceChild("cables3", CubeListBuilder.create().texOffs(0, 120).addBox(-0.25F, 0.5F, -6.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(77, 12).addBox(-1.5F, 1.0F, 5.25F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 8.25F, 0.0F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r47 = cables3.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(100, 96).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r48 = cables3.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(101, 33).addBox(-1.0F, 0.0F, -6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition bone143 = rot6.addOrReplaceChild("bone143", CubeListBuilder.create().texOffs(202, 188).addBox(-1.0F, -2.0F, -1.475F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4611F, 0.9F, 9.0941F, 0.0F, -1.4399F, 0.0F));

        PartDefinition cube_r49 = bone143.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(202, 188).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.9367F, -1.1655F, 1.3586F, 2.9062F, 0.9173F, 1.3049F));

        PartDefinition cube_r50 = bone143.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(202, 188).addBox(1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(202, 188).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5333F, -2.371F, 4.8574F, 2.6122F, 1.2861F, 0.9815F));

        PartDefinition cube_r51 = bone143.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(202, 188).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-7.7597F, -2.73F, 6.1633F, -1.8321F, 0.3535F, 3.0074F));

        PartDefinition cube_r52 = bone143.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(202, 188).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.3154F, -2.454F, 6.811F, -1.5708F, 0.4363F, -2.618F));

        PartDefinition cube_r53 = bone143.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(202, 188).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.6945F, -1.5182F, 6.9747F, -1.5708F, -0.2618F, -2.618F));

        PartDefinition cube_r54 = bone143.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(202, 188).addBox(2.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(202, 188).addBox(0.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(202, 188).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9852F, 0.6234F, 3.676F, -1.5708F, -0.7418F, -2.618F));

        PartDefinition cube_r55 = bone143.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(202, 188).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.2216F, 1.3201F, 2.2537F, -1.5708F, -0.9599F, -2.618F));

        PartDefinition cube_r56 = bone143.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(202, 188).addBox(-1.5F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9667F, 1.7503F, 0.525F, 0.0F, -1.5708F, 2.0944F));

        PartDefinition cube_r57 = bone143.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(202, 188).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7167F, 0.4512F, -0.975F, 0.0F, 0.0F, 2.0944F));

        PartDefinition cube_r58 = bone143.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(202, 186).addBox(-1.0F, -2.0F, -1.475F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0213F, 0.0105F, 0.0F, 0.0F, 0.0F, 0.9163F));

        PartDefinition bone133 = rot6.addOrReplaceChild("bone133", CubeListBuilder.create().texOffs(191, 222).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, -2.0F, -2.4F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r59 = bone133.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(189, 224).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.25F, 0.0F, 1.5708F, 1.5708F));

        PartDefinition cube_r60 = bone133.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(189, 224).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 1.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition bone134 = rot6.addOrReplaceChild("bone134", CubeListBuilder.create().texOffs(191, 222).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -2.0F, -3.4F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r61 = bone134.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(189, 224).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.25F, 0.0F, 1.5708F, 1.5708F));

        PartDefinition cube_r62 = bone134.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(189, 224).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 1.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition bone135 = rot6.addOrReplaceChild("bone135", CubeListBuilder.create().texOffs(191, 222).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.9F, -2.0F, -1.4F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r63 = bone135.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(189, 224).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.25F, 0.0F, 1.5708F, 1.5708F));

        PartDefinition cube_r64 = bone135.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(189, 224).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 1.5F, 0.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition bone150 = rot6.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(15, 218).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(11, 206).addBox(-0.5F, -3.3F, 0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-13.8792F, -2.6779F, -1.5F, 0.0F, 0.0F, -0.6109F));

        PartDefinition bone151 = bone150.addOrReplaceChild("bone151", CubeListBuilder.create().texOffs(14, 224).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -1.45F, 0.3F, -0.3054F, 0.0F, 0.0F));

        PartDefinition pillars = copper.addOrReplaceChild("pillars", CubeListBuilder.create().texOffs(0, 63).addBox(18.5F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 30).addBox(18.5F, -13.5F, 7.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r65 = pillars.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(63, 0).addBox(-8.0F, -2.0F, 1.0F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r66 = pillars.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(31, 66).addBox(-1.0F, -2.0F, 1.0F, 5.0F, 7.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r67 = pillars.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(28, 0).addBox(7.5948F, -10.45F, -3.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r68 = pillars.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(28, 15).addBox(7.5948F, -10.45F, 0.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r69 = pillars.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(0, 100).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, 8.366F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r70 = pillars.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, 8.387F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r71 = pillars.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(100, 103).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 7.5F, 0.4819F, 0.0851F, -1.7445F));

        PartDefinition cube_r72 = pillars.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(101, 71).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r73 = pillars.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(51, 96).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.4835F, 0.116F, -1.7282F));

        PartDefinition cube_r74 = pillars.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, 1.8362F, 0.4784F, -0.0403F, -1.2752F));

        PartDefinition cube_r75 = pillars.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(63, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, -1.8362F, -0.4784F, 0.0403F, -1.2752F));

        PartDefinition cube_r76 = pillars.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(82, 101).addBox(0.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.48F, 0.0F, -1.789F));

        PartDefinition cube_r77 = pillars.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(106, 40).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -7.5F, -0.4812F, -0.0697F, -1.7526F));

        PartDefinition cube_r78 = pillars.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, -8.387F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r79 = pillars.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(21, 93).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, -8.366F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r80 = pillars.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(108, 11).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.4835F, -0.116F, -1.7282F));

        PartDefinition cube_r81 = pillars.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(13, 105).addBox(0.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.48F, 0.0F, -1.789F));

        PartDefinition cube_r82 = pillars.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(91, 101).addBox(-1.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r83 = pillars.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(36, 96).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r84 = pillars.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(22, 108).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r85 = pillars.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(72, 48).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r86 = pillars.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(91, 0).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition pillars2 = pillars.addOrReplaceChild("pillars2", CubeListBuilder.create().texOffs(0, 63).addBox(18.5F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(27, 13).addBox(18.5866F, -10.5F, -9.55F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(30, 28).addBox(20.4866F, -10.5F, -9.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 30).addBox(18.5F, -13.5F, 7.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r87 = pillars2.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(63, 15).addBox(-6.0F, -2.0F, 1.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r88 = pillars2.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(31, 66).addBox(-1.0F, -2.0F, 1.0F, 5.0F, 7.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r89 = pillars2.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(72, 11).addBox(9.3948F, -5.45F, -2.7638F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
                .texOffs(71, 32).addBox(10.6948F, -6.45F, -2.7638F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(7.5948F, -10.45F, -3.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r90 = pillars2.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(65, 71).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(10.7443F, -3.0F, -3.6047F, -0.7854F, 0.48F, 0.0F));

        PartDefinition cube_r91 = pillars2.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(69, 26).addBox(10.6948F, -6.45F, 0.7638F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 15).addBox(7.5948F, -10.45F, 0.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r92 = pillars2.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(0, 100).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, 8.366F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r93 = pillars2.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, 8.387F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r94 = pillars2.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(100, 103).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 7.5F, 0.4819F, 0.0851F, -1.7445F));

        PartDefinition cube_r95 = pillars2.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(101, 71).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r96 = pillars2.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(51, 96).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.4835F, 0.116F, -1.7282F));

        PartDefinition cube_r97 = pillars2.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, 1.8362F, 0.4784F, -0.0403F, -1.2752F));

        PartDefinition cube_r98 = pillars2.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(63, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, -1.8362F, -0.4784F, 0.0403F, -1.2752F));

        PartDefinition cube_r99 = pillars2.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(82, 101).addBox(0.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.48F, 0.0F, -1.789F));

        PartDefinition cube_r100 = pillars2.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(106, 40).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -7.5F, -0.4812F, -0.0697F, -1.7526F));

        PartDefinition cube_r101 = pillars2.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, -8.387F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r102 = pillars2.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(21, 93).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, -8.366F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r103 = pillars2.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(108, 11).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.4835F, -0.116F, -1.7282F));

        PartDefinition cube_r104 = pillars2.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(13, 105).addBox(0.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.48F, 0.0F, -1.789F));

        PartDefinition cube_r105 = pillars2.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(91, 101).addBox(-1.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r106 = pillars2.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(36, 96).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r107 = pillars2.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(22, 108).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r108 = pillars2.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(72, 48).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r109 = pillars2.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(91, 0).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r110 = pillars2.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(233, 18).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.31F)), PartPose.offsetAndRotation(18.5422F, -2.7757F, -7.861F, 0.4099F, -0.3944F, 2.3174F));

        PartDefinition cube_r111 = pillars2.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(233, 18).addBox(2.3F, -0.5F, -0.2F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.31F)), PartPose.offsetAndRotation(13.3056F, -0.4992F, -6.4681F, 0.479F, -0.7216F, -2.5504F));

        PartDefinition cube_r112 = pillars2.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(233, 18).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.32F)), PartPose.offsetAndRotation(12.7F, -1.3063F, -5.1195F, 0.475F, -0.5409F, -2.9837F));

        PartDefinition cube_r113 = pillars2.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(233, 18).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.31F)), PartPose.offsetAndRotation(14.7249F, -1.3035F, -6.1852F, 0.3211F, -0.4004F, 3.0565F));

        PartDefinition cube_r114 = pillars2.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(233, 18).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.32F)), PartPose.offsetAndRotation(16.8255F, -1.7326F, -7.067F, 0.1247F, -0.347F, 2.878F));

        PartDefinition cube_r115 = pillars2.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(233, 18).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.32F)), PartPose.offsetAndRotation(19.7269F, -4.5673F, -8.5853F, 0.3124F, -0.2079F, 1.9842F));

        PartDefinition cube_r116 = pillars2.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(233, 18).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.31F)), PartPose.offsetAndRotation(20.6425F, -6.7213F, -8.9233F, 0.2533F, -0.067F, 1.955F));

        PartDefinition cube_r117 = pillars2.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(233, 18).addBox(-1.7F, 0.2F, -0.6F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.32F)), PartPose.offsetAndRotation(21.4935F, -8.8201F, -8.9412F, 0.0F, 0.0F, 1.3526F));

        PartDefinition pillars3 = pillars2.addOrReplaceChild("pillars3", CubeListBuilder.create().texOffs(0, 63).addBox(18.5F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(136, 225).addBox(18.7F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(201, 87).addBox(19.85F, -8.9615F, -10.1787F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(28, 30).addBox(18.5F, -13.5F, 7.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(216, 152).addBox(19.8F, -11.5F, 8.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(161, 127).addBox(20.2F, -11.0F, 8.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r118 = pillars3.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(31, 66).addBox(-1.0F, -2.0F, 1.0F, 5.0F, 7.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r119 = pillars3.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(63, 0).addBox(-8.0F, -2.0F, 1.0F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r120 = pillars3.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(128, 19).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.8683F, -3.0F, 4.2329F, -0.48F, 0.0F, 1.5708F));

        PartDefinition cube_r121 = pillars3.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(128, 19).addBox(-1.5F, -0.5F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(128, 19).addBox(-0.5F, -0.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0991F, -2.0F, 3.7894F, -0.48F, 0.0F, 1.5708F));

        PartDefinition cube_r122 = pillars3.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(125, 18).addBox(8.5948F, -6.45F, -2.7638F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(7.5948F, -10.45F, -3.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r123 = pillars3.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(28, 15).addBox(7.5948F, -10.45F, 0.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r124 = pillars3.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(216, 152).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(20.6F, -10.5F, 9.0F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r125 = pillars3.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(0, 100).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, 8.366F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r126 = pillars3.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, 8.387F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r127 = pillars3.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(100, 103).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 7.5F, 0.4819F, 0.0851F, -1.7445F));

        PartDefinition cube_r128 = pillars3.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(101, 71).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r129 = pillars3.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(51, 96).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.4835F, 0.116F, -1.7282F));

        PartDefinition cube_r130 = pillars3.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, 1.8362F, 0.4784F, -0.0403F, -1.2752F));

        PartDefinition cube_r131 = pillars3.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(63, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, -1.8362F, -0.4784F, 0.0403F, -1.2752F));

        PartDefinition cube_r132 = pillars3.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(82, 101).addBox(0.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.48F, 0.0F, -1.789F));

        PartDefinition cube_r133 = pillars3.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(106, 40).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -7.5F, -0.4812F, -0.0697F, -1.7526F));

        PartDefinition cube_r134 = pillars3.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, -8.387F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r135 = pillars3.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(21, 93).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, -8.366F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r136 = pillars3.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(108, 11).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.4835F, -0.116F, -1.7282F));

        PartDefinition cube_r137 = pillars3.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(34, 28).addBox(-0.5F, -4.7F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 105).addBox(0.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.48F, 0.0F, -1.789F));

        PartDefinition cube_r138 = pillars3.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(91, 101).addBox(-1.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r139 = pillars3.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(36, 96).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r140 = pillars3.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(22, 108).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r141 = pillars3.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(72, 48).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r142 = pillars3.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(91, 0).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r143 = pillars3.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(185, 97).addBox(0.0F, 0.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.9F, -10.9373F, -9.2343F, 0.0F, -0.1745F, 0.0F));

        PartDefinition cube_r144 = pillars3.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(211, 92).addBox(0.5F, 0.2F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.9F, -5.4373F, -9.2343F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r145 = pillars3.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(211, 92).addBox(0.05F, -1.4F, -0.1F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(21.45F, -6.5187F, -9.5179F, -2.2689F, 0.0F, 0.0F));

        PartDefinition cube_r146 = pillars3.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(213, 97).addBox(1.6F, -1.2F, -1.6F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(211, 92).addBox(1.7F, -1.2F, -1.6F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(19.9F, -10.9373F, -9.2343F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r147 = pillars3.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(211, 92).addBox(0.5F, 0.2F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.9F, -10.9373F, -9.2343F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r148 = pillars3.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(185, 97).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.9F, -10.9373F, -9.2343F, -1.5708F, -0.1745F, 0.0F));

        PartDefinition cube_r149 = pillars3.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(211, 87).addBox(-0.2F, -0.3F, 0.2F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.0F, -8.2373F, -8.4343F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r150 = pillars3.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(202, 82).addBox(0.0F, -1.3F, 0.2F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.65F, -11.2611F, -8.7474F, 0.7854F, 0.0F, 0.0F));

        PartDefinition handbrake = pillars3.addOrReplaceChild("handbrake", CubeListBuilder.create(), PartPose.offset(23.5F, -10.5F, -9.0F));

        PartDefinition cube_r151 = handbrake.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(201, 87).addBox(-0.65F, -1.7F, -0.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 1.8385F, 2.1213F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r152 = handbrake.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(202, 95).addBox(-4.2F, -2.1F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, -1.5708F));

        PartDefinition cube_r153 = handbrake.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(202, 99).addBox(-3.5F, -1.8F, 0.5F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(194, 96).addBox(-3.0F, -1.8F, -0.5F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r154 = handbrake.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(212, 92).addBox(2.8F, -0.8F, 0.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(211, 92).addBox(2.5F, 0.2F, -1.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-5.5F, 0.2121F, 0.7778F, -2.3562F, 0.0F, 0.0F));

        PartDefinition lampthingy = pillars3.addOrReplaceChild("lampthingy", CubeListBuilder.create(), PartPose.offset(18.082F, -5.4667F, -6.844F));

        PartDefinition cube_r155 = lampthingy.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(35, 13).addBox(-0.51F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.7717F, 0.3667F, -0.4017F, -0.7854F, 0.48F, 0.0F));

        PartDefinition cube_r156 = lampthingy.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(59, 49).addBox(13.5948F, -6.45F, 1.2638F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(87, 92).addBox(12.4948F, -7.55F, 0.7638F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-13.432F, 5.8167F, 5.0038F, 0.0F, 0.48F, 0.0F));

        PartDefinition pillars4 = pillars3.addOrReplaceChild("pillars4", CubeListBuilder.create().texOffs(0, 63).addBox(18.5F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 30).addBox(18.5F, -13.5F, 7.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r157 = pillars4.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(186, 41).addBox(-2.0F, -3.6F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F))
                .texOffs(183, 23).addBox(-2.5F, -2.0F, -2.5F, 3.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0107F, -20.7533F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition cube_r158 = pillars4.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(184, 14).addBox(2.7F, -1.0F, -1.1F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(187, 32).addBox(1.75F, -0.5F, -0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F))
                .texOffs(187, 29).addBox(-0.2F, -0.5F, -0.6F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.8796F, -24.6981F, -0.35F, 0.0F, 1.5708F, 1.789F));

        PartDefinition cube_r159 = pillars4.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(187, 27).addBox(0.4F, -0.5F, -0.8F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.8796F, -24.6981F, -0.35F, 0.0F, 0.0F, 1.789F));

        PartDefinition cube_r160 = pillars4.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(188, 27).addBox(-0.475F, -1.1F, -0.575F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(10.9183F, -23.2067F, -1.7822F, 0.0F, 0.0F, 1.3963F));

        PartDefinition cube_r161 = pillars4.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(189, 27).addBox(-0.3F, -0.5F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.5069F, -23.3714F, -2.0572F, 0.0F, -1.5708F, 0.3927F));

        PartDefinition cube_r162 = pillars4.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(189, 27).addBox(-0.3F, -0.5F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(11.5339F, -23.3602F, -2.1279F, -3.1416F, -0.7854F, -2.7489F));

        PartDefinition cube_r163 = pillars4.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(188, 27).addBox(-1.0F, -0.5F, -0.49F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(10.6912F, -23.7093F, -2.15F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r164 = pillars4.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(188, 27).addBox(-1.0F, -0.6F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(9.4662F, -23.7731F, -2.15F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r165 = pillars4.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(63, 15).addBox(-6.0F, -2.0F, 1.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r166 = pillars4.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(31, 66).addBox(-1.0F, -2.0F, 1.0F, 5.0F, 7.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r167 = pillars4.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(85, 55).addBox(-0.5F, -0.3F, -1.7F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(10.5244F, -2.3F, 3.3775F, 0.9599F, -0.48F, 0.0F));

        PartDefinition cube_r168 = pillars4.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(73, 26).addBox(10.6948F, -5.55F, -2.7638F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(28, 0).addBox(7.5948F, -10.45F, -3.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r169 = pillars4.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(28, 15).addBox(7.5948F, -10.45F, 0.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r170 = pillars4.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(0, 100).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, 8.366F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r171 = pillars4.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, 8.387F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r172 = pillars4.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(100, 103).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 7.5F, 0.4819F, 0.0851F, -1.7445F));

        PartDefinition cube_r173 = pillars4.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(101, 71).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r174 = pillars4.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(51, 96).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.4835F, 0.116F, -1.7282F));

        PartDefinition cube_r175 = pillars4.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, 1.8362F, 0.4784F, -0.0403F, -1.2752F));

        PartDefinition cube_r176 = pillars4.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(63, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, -1.8362F, -0.4784F, 0.0403F, -1.2752F));

        PartDefinition cube_r177 = pillars4.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(82, 101).addBox(0.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.48F, 0.0F, -1.789F));

        PartDefinition cube_r178 = pillars4.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(106, 40).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -7.5F, -0.4812F, -0.0697F, -1.7526F));

        PartDefinition cube_r179 = pillars4.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, -8.387F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r180 = pillars4.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(21, 93).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, -8.366F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r181 = pillars4.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(108, 11).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.4835F, -0.116F, -1.7282F));

        PartDefinition cube_r182 = pillars4.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(13, 105).addBox(0.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.48F, 0.0F, -1.789F));

        PartDefinition cube_r183 = pillars4.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(91, 101).addBox(-1.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r184 = pillars4.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(36, 96).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r185 = pillars4.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(22, 108).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r186 = pillars4.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(72, 48).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r187 = pillars4.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(91, 0).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition bone127 = pillars4.addOrReplaceChild("bone127", CubeListBuilder.create(), PartPose.offset(8.8796F, -24.6981F, -0.35F));

        PartDefinition bone122 = pillars4.addOrReplaceChild("bone122", CubeListBuilder.create().texOffs(158, 141).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offset(7.835F, -24.212F, 0.15F));

        PartDefinition bone121 = pillars4.addOrReplaceChild("bone121", CubeListBuilder.create().texOffs(245, 18).addBox(-1.1125F, -0.4375F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(244, 7).addBox(-0.8625F, -0.3375F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(248, 12).addBox(-0.8625F, -0.3875F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(8.0125F, -26.2625F, 0.15F, 0.0F, 0.0F, 1.5708F));

        PartDefinition fuel_gauge = bone121.addOrReplaceChild("fuel_gauge", CubeListBuilder.create(), PartPose.offset(0.0875F, 1.2625F, 0.0F));

        PartDefinition cube_r188 = fuel_gauge.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(155, 0).addBox(-0.75F, -1.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -0.675F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition pillars5 = pillars4.addOrReplaceChild("pillars5", CubeListBuilder.create().texOffs(0, 63).addBox(18.5F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 30).addBox(18.5F, -13.5F, 7.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(238, 78).addBox(18.6F, -13.5F, 7.3F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 154).addBox(20.0F, -12.5F, 7.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(49, 128).addBox(20.0F, -11.3F, 9.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(30, 169).addBox(19.7F, -11.1F, -8.6F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(51, 132).addBox(20.6F, -11.1F, -10.2F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r189 = pillars5.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(157, 222).addBox(-5.0F, -2.05F, 6.0F, 6.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(63, 0).addBox(-8.0F, -2.0F, 1.0F, 9.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r190 = pillars5.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(31, 66).addBox(-1.0F, -2.0F, 1.0F, 5.0F, 7.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r191 = pillars5.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(28, 0).addBox(7.5948F, -10.45F, -3.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r192 = pillars5.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(28, 15).addBox(7.5948F, -10.45F, 0.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r193 = pillars5.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(30, 163).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.2F, -8.2F, 8.8F, 1.5708F, 0.0F, 0.7854F));

        PartDefinition cube_r194 = pillars5.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(30, 163).addBox(-1.05F, 15.1F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.75F, -10.0F, -7.3F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r195 = pillars5.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(30, 160).addBox(-0.75F, 0.9F, 0.65F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.75F, -9.8F, -7.3F, -1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r196 = pillars5.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(0, 100).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, 8.366F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r197 = pillars5.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, 8.387F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r198 = pillars5.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(100, 103).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 7.5F, 0.4819F, 0.0851F, -1.7445F));

        PartDefinition cube_r199 = pillars5.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(101, 71).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r200 = pillars5.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(51, 96).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.4835F, 0.116F, -1.7282F));

        PartDefinition cube_r201 = pillars5.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, 1.8362F, 0.4784F, -0.0403F, -1.2752F));

        PartDefinition cube_r202 = pillars5.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(63, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, -1.8362F, -0.4784F, 0.0403F, -1.2752F));

        PartDefinition cube_r203 = pillars5.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(82, 101).addBox(0.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.48F, 0.0F, -1.789F));

        PartDefinition cube_r204 = pillars5.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(106, 40).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -7.5F, -0.4812F, -0.0697F, -1.7526F));

        PartDefinition cube_r205 = pillars5.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, -8.387F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r206 = pillars5.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(21, 93).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, -8.366F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r207 = pillars5.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(108, 11).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.4835F, -0.116F, -1.7282F));

        PartDefinition cube_r208 = pillars5.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(13, 105).addBox(0.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.48F, 0.0F, -1.789F));

        PartDefinition cube_r209 = pillars5.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(168, 205).addBox(0.05F, -15.0F, -2.95F, 0.0F, 15.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(91, 101).addBox(-1.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r210 = pillars5.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(36, 96).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r211 = pillars5.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(22, 108).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r212 = pillars5.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(72, 48).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r213 = pillars5.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(91, 0).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition bone115 = pillars5.addOrReplaceChild("bone115", CubeListBuilder.create(), PartPose.offset(20.5F, -13.5F, -10.5F));

        PartDefinition cube_r214 = bone115.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(134, 64).addBox(-0.6F, -4.0F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(135, 68).addBox(-0.3F, -3.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(134, 64).addBox(-0.6F, -7.0F, 0.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(135, 68).addBox(-0.3F, -6.5F, 1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.48F, 0.0F, -1.0472F));

        PartDefinition bone126 = pillars5.addOrReplaceChild("bone126", CubeListBuilder.create().texOffs(39, 141).addBox(-0.5F, -2.8468F, -0.8786F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(45, 140).addBox(0.5F, -2.8468F, -0.8786F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.6F, -10.025F, -9.45F, 0.6981F, 0.0F, 0.0F));

        PartDefinition pillars6 = pillars5.addOrReplaceChild("pillars6", CubeListBuilder.create().texOffs(0, 63).addBox(18.5F, -13.5F, -10.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 30).addBox(18.5F, -13.5F, 7.5F, 2.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r215 = pillars6.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(22, 108).addBox(-1.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r216 = pillars6.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(136, 204).addBox(1.2F, -0.1F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(130, 203).addBox(1.2F, -1.1F, -1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(126, 212).addBox(-0.9F, -1.1F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -23.0F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition cube_r217 = pillars6.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(63, 15).addBox(-6.0F, -2.0F, 1.0F, 7.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r218 = pillars6.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(31, 66).addBox(-1.0F, -2.0F, 1.0F, 5.0F, 7.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.0941F, -18.3532F, -5.5F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r219 = pillars6.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(28, 0).addBox(7.5948F, -10.45F, -3.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r220 = pillars6.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(28, 15).addBox(7.5948F, -10.45F, 0.2638F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.45F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r221 = pillars6.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(0, 100).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, 8.366F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r222 = pillars6.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(63, 0).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, 8.387F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r223 = pillars6.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(100, 103).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 7.5F, 0.4819F, 0.0851F, -1.7445F));

        PartDefinition cube_r224 = pillars6.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(101, 71).addBox(-5.0F, -13.0F, -1.0F, 5.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.4899F, -0.1932F, -1.1492F));

        PartDefinition cube_r225 = pillars6.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(51, 96).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.4835F, 0.116F, -1.7282F));

        PartDefinition cube_r226 = pillars6.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, 1.8362F, 0.4784F, -0.0403F, -1.2752F));

        PartDefinition cube_r227 = pillars6.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(63, 30).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.5948F, 0.6969F, -1.8362F, -0.4784F, 0.0403F, -1.2752F));

        PartDefinition cube_r228 = pillars6.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(82, 101).addBox(0.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.48F, 0.0F, -1.789F));

        PartDefinition cube_r229 = pillars6.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(106, 40).addBox(0.0F, -13.0F, -1.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -7.5F, -0.4812F, -0.0697F, -1.7526F));

        PartDefinition cube_r230 = pillars6.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(63, 15).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0383F, -7.5F, -8.387F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r231 = pillars6.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(21, 93).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.0014F, -13.538F, -8.366F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r232 = pillars6.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(108, 11).addBox(0.0F, -13.0F, 0.0F, 4.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.4835F, -0.116F, -1.7282F));

        PartDefinition cube_r233 = pillars6.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(13, 105).addBox(0.0F, -13.0F, 0.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, -0.48F, 0.0F, -1.789F));

        PartDefinition cube_r234 = pillars6.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(224, 191).addBox(-0.975F, -4.3F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(91, 101).addBox(-1.0F, -13.0F, -3.0F, 1.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, 10.5F, 0.48F, 0.0F, -1.0472F));

        PartDefinition cube_r235 = pillars6.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(36, 96).addBox(-6.0F, -13.0F, 0.0F, 6.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -13.5F, -10.5F, -0.4899F, 0.1932F, -1.1492F));

        PartDefinition cube_r236 = pillars6.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(72, 48).addBox(-3.0F, -6.0F, -1.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, 10.5F, 0.0F, -0.48F, 0.0F));

        PartDefinition cube_r237 = pillars6.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(91, 0).addBox(-3.0F, -6.0F, 0.0F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(20.5F, -7.5F, -10.5F, 0.0F, 0.48F, 0.0F));

        PartDefinition bone152 = pillars6.addOrReplaceChild("bone152", CubeListBuilder.create(), PartPose.offset(11.0761F, -22.7756F, -3.2F));

        PartDefinition cube_r238 = bone152.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(216, 39).addBox(-4.0F, -3.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(220, 43).addBox(-4.5F, -3.0F, 1.3F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.982F, 4.4225F, -2.3F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r239 = bone152.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(212, 25).addBox(2.5F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(212, 25).addBox(-2.5F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(216, 27).addBox(-2.5F, -0.5F, -1.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(216, 27).addBox(-2.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(212, 25).addBox(-2.5F, 0.5F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(213, 20).addBox(-2.5F, 0.0F, -0.75F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone153 = pillars6.addOrReplaceChild("bone153", CubeListBuilder.create(), PartPose.offset(11.0761F, -22.7756F, 3.2F));

        PartDefinition cube_r240 = bone153.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(216, 39).addBox(-4.0F, -3.5F, 1.8F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(220, 43).addBox(-4.5F, -3.0F, 1.3F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.982F, 4.4225F, -2.3F, 0.0F, 0.0F, 1.309F));

        PartDefinition cube_r241 = bone153.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(216, 27).addBox(-2.5F, -0.5F, 1.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(216, 27).addBox(-2.5F, -0.5F, -1.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(212, 25).addBox(2.5F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(212, 25).addBox(-2.5F, -0.5F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(212, 25).addBox(-2.5F, 0.5F, -1.0F, 5.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(213, 20).addBox(-2.5F, 0.0F, -0.75F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone147 = pillars6.addOrReplaceChild("bone147", CubeListBuilder.create(), PartPose.offset(9.8F, -20.9F, 0.0F));

        PartDefinition bone146 = bone147.addOrReplaceChild("bone146", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone145 = bone147.addOrReplaceChild("bone145", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition hook = pillars6.addOrReplaceChild("hook", CubeListBuilder.create().texOffs(215, 189).addBox(0.748F, -2.9408F, -9.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(212, 193).addBox(0.748F, -2.6908F, -9.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(19.852F, -8.3092F, 0.0F));

        PartDefinition cube_r242 = hook.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(215, 185).addBox(2.6F, -1.25F, -9.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition hammer = hook.addOrReplaceChild("hammer", CubeListBuilder.create().texOffs(166, 245).addBox(-0.5F, -1.25F, -0.125F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.4796F, -2.3657F, -9.0F, 1.5708F, 0.0F, 1.5708F));

        PartDefinition bone5 = hammer.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(169, 234).addBox(-0.5F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(166, 227).addBox(5.25F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(2.2F, 0.0F, -0.125F));

        PartDefinition bottom = copper.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rotor = bottom.addOrReplaceChild("rotor", CubeListBuilder.create().texOffs(67, 98).addBox(4.2F, -9.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition base = bottom.addOrReplaceChild("base", CubeListBuilder.create().texOffs(28, 45).addBox(-0.35F, -8.0F, -5.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panels7 = bottom.addOrReplaceChild("panels7", CubeListBuilder.create().texOffs(70, 72).addBox(8.0F, -11.0F, -2.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r243 = panels7.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.4545F, -8.0F, 9.5F, -0.5087F, -0.1298F, 1.3428F));

        PartDefinition cube_r244 = panels7.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(76, 27).addBox(-11.0F, -0.999F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -9.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r245 = panels7.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(76, 33).addBox(-11.0F, -2.0F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3156F, -9.1197F, 2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom2 = bottom.addOrReplaceChild("bottom2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor2 = bottom2.addOrReplaceChild("rotor2", CubeListBuilder.create().texOffs(67, 98).addBox(4.2F, -9.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition base2 = bottom2.addOrReplaceChild("base2", CubeListBuilder.create().texOffs(28, 45).addBox(-0.35F, -8.0F, -5.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(125, 94).addBox(8.25F, -6.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(122, 116).addBox(8.65F, -4.0F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panels2 = bottom2.addOrReplaceChild("panels2", CubeListBuilder.create().texOffs(70, 72).addBox(8.0F, -11.0F, -2.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r246 = panels2.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.4545F, -8.0F, 9.5F, -0.5087F, -0.1298F, 1.3428F));

        PartDefinition cube_r247 = panels2.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(76, 27).addBox(-11.0F, -0.999F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -9.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r248 = panels2.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(76, 33).addBox(-11.0F, -2.0F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3156F, -9.1197F, 2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom3 = bottom2.addOrReplaceChild("bottom3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor3 = bottom3.addOrReplaceChild("rotor3", CubeListBuilder.create().texOffs(67, 98).addBox(4.2F, -9.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition base3 = bottom3.addOrReplaceChild("base3", CubeListBuilder.create().texOffs(28, 45).addBox(-0.35F, -8.0F, -5.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(117, 60).addBox(9.65F, 2.0F, -1.0F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r249 = base3.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(62, 43).addBox(-3.0357F, -0.3246F, -1.0F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.6854F, 1.6753F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition panels3 = bottom3.addOrReplaceChild("panels3", CubeListBuilder.create().texOffs(70, 72).addBox(8.0F, -11.0F, -2.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r250 = panels3.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.4545F, -8.0F, 9.5F, -0.5087F, -0.1298F, 1.3428F));

        PartDefinition cube_r251 = panels3.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(76, 27).addBox(-11.0F, -0.999F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -9.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r252 = panels3.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(76, 33).addBox(-11.0F, -2.0F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3156F, -9.1197F, 2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom4 = bottom3.addOrReplaceChild("bottom4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor4 = bottom4.addOrReplaceChild("rotor4", CubeListBuilder.create().texOffs(67, 98).addBox(4.2F, -9.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition base4 = bottom4.addOrReplaceChild("base4", CubeListBuilder.create().texOffs(28, 45).addBox(-0.35F, -8.0F, -5.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panels4 = bottom4.addOrReplaceChild("panels4", CubeListBuilder.create().texOffs(70, 72).addBox(8.0F, -11.0F, -2.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r253 = panels4.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.4545F, -8.0F, 9.5F, -0.5087F, -0.1298F, 1.3428F));

        PartDefinition cube_r254 = panels4.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(76, 27).addBox(-11.0F, -0.999F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -9.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r255 = panels4.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(76, 33).addBox(-11.0F, -2.0F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3156F, -9.1197F, 2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom5 = bottom4.addOrReplaceChild("bottom5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor5 = bottom5.addOrReplaceChild("rotor5", CubeListBuilder.create().texOffs(67, 98).addBox(4.2F, -9.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition base5 = bottom5.addOrReplaceChild("base5", CubeListBuilder.create().texOffs(28, 45).addBox(-0.35F, -8.0F, -5.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(68, 78).addBox(10.15F, 1.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r256 = base5.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(16, 74).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.35F)), PartPose.offsetAndRotation(11.9988F, 0.8482F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition panels5 = bottom5.addOrReplaceChild("panels5", CubeListBuilder.create().texOffs(70, 72).addBox(8.0F, -11.0F, -2.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r257 = panels5.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.4545F, -8.0F, 9.5F, -0.5087F, -0.1298F, 1.3428F));

        PartDefinition cube_r258 = panels5.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(76, 27).addBox(-11.0F, -0.999F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -9.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r259 = panels5.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(76, 33).addBox(-11.0F, -2.0F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3156F, -9.1197F, 2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bottom6 = bottom5.addOrReplaceChild("bottom6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor6 = bottom6.addOrReplaceChild("rotor6", CubeListBuilder.create().texOffs(67, 98).addBox(4.2F, -9.0F, -3.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition base6 = bottom6.addOrReplaceChild("base6", CubeListBuilder.create().texOffs(28, 45).addBox(-0.35F, -8.0F, -5.0F, 9.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pump3 = base6.addOrReplaceChild("pump3", CubeListBuilder.create().texOffs(117, 60).addBox(9.65F, 2.0F, -1.0F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r260 = pump3.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(56, 43).mirror().addBox(-3.0F, 0.0F, -1.0F, 6.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.7209F, 1.3507F, 0.0F, 3.1416F, 0.0F, 0.2182F));

        PartDefinition panels6 = bottom6.addOrReplaceChild("panels6", CubeListBuilder.create().texOffs(70, 72).addBox(8.0F, -11.0F, -2.0F, 11.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r261 = panels6.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(0, 63).addBox(-1.0F, 0.0F, -7.0F, 1.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.4545F, -8.0F, 9.5F, -0.5087F, -0.1298F, 1.3428F));

        PartDefinition cube_r262 = panels6.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(76, 27).addBox(-11.0F, -0.999F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(19.0F, -9.0F, 2.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r263 = panels6.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(76, 33).addBox(-11.0F, -2.0F, -4.0F, 11.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.3156F, -9.1197F, 2.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition bone144 = bottom5.addOrReplaceChild("bone144", CubeListBuilder.create().texOffs(192, 120).addBox(21.2F, -11.2F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r264 = bone144.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(184, 113).addBox(-0.2172F, -0.2172F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(21.7F, -10.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition bone155 = bottom5.addOrReplaceChild("bone155", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition copperbikepump = bone155.addOrReplaceChild("copperbikepump", CubeListBuilder.create().texOffs(219, 174).addBox(1.0F, -2.2F, -1.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(14.0F, -16.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition copperpump = copperbikepump.addOrReplaceChild("copperpump", CubeListBuilder.create().texOffs(219, 174).addBox(1.0F, -4.3F, -1.1F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(219, 174).addBox(1.0F, -2.9F, -1.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition copperbikepump2 = bone155.addOrReplaceChild("copperbikepump2", CubeListBuilder.create().texOffs(219, 174).addBox(1.0F, -2.2F, -1.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(14.0F, -16.0F, 1.2F, 0.0F, 0.0F, 0.48F));

        PartDefinition copperpump2 = copperbikepump2.addOrReplaceChild("copperpump2", CubeListBuilder.create().texOffs(219, 174).addBox(1.0F, -4.3F, -1.1F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(219, 174).addBox(1.0F, -2.9F, -1.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone160 = bone155.addOrReplaceChild("bone160", CubeListBuilder.create().texOffs(223, 169).addBox(4.9F, -2.7F, -1.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(223, 169).addBox(4.9F, -3.3F, -1.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(217, 166).addBox(4.9F, -1.425F, -1.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.025F))
                .texOffs(215, 158).addBox(3.9F, -1.425F, -2.175F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.025F)), PartPose.offsetAndRotation(14.0F, -16.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition copperpump3 = bone160.addOrReplaceChild("copperpump3", CubeListBuilder.create().texOffs(232, 186).addBox(4.15F, -4.675F, -0.675F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(235, 178).addBox(4.15F, -5.175F, -0.675F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(226, 180).addBox(4.15F, -4.675F, -0.175F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(235, 192).addBox(4.9F, -4.675F, 0.075F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.25F, 0.0F, -0.75F));

        PartDefinition bone162 = bone160.addOrReplaceChild("bone162", CubeListBuilder.create().texOffs(232, 186).addBox(-1.0F, -0.3333F, -0.6667F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(235, 178).addBox(-1.0F, -0.8333F, -0.6667F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(226, 180).addBox(-1.0F, -0.3333F, -0.1667F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.9126F, -1.9417F, -0.3164F, 0.0F, -0.7854F, 0.0F));

        PartDefinition controls = copper.addOrReplaceChild("controls", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition panel_1 = controls.addOrReplaceChild("panel_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rot7 = panel_1.addOrReplaceChild("rot7", CubeListBuilder.create(), PartPose.offsetAndRotation(20.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition cube_r265 = rot7.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(85, 124).addBox(-0.75F, -0.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(85, 124).addBox(0.25F, -0.3F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(85, 124).addBox(0.25F, -0.3F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.5183F, -3.4837F, 2.5F, 0.0F, 0.0F, 0.733F));

        PartDefinition atomic_acc = rot7.addOrReplaceChild("atomic_acc", CubeListBuilder.create().texOffs(55, 117).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(67, 125).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0482F, 0.2624F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition bone2 = atomic_acc.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(125, 24).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(119, 25).addBox(-0.5F, -1.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r266 = bone2.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(119, 17).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition cube_r267 = bone2.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(119, 21).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 1.5708F, 0.0F, 3.1416F));

        PartDefinition cube_r268 = bone2.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(119, 25).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r269 = bone2.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(119, 21).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r270 = bone2.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(119, 21).addBox(-0.5F, -0.5F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition button = rot7.addOrReplaceChild("button", CubeListBuilder.create().texOffs(22, 125).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.75F, 0.6F, 0.0F));

        PartDefinition bone3 = button.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(125, 16).addBox(-0.5F, -1.55F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition button2 = rot7.addOrReplaceChild("button2", CubeListBuilder.create().texOffs(9, 125).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(115, 124).addBox(-0.5F, -1.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-2.25F, 0.5F, -2.0F));

        PartDefinition bone4 = button2.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(125, 108).addBox(-0.5F, -1.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition keyboard = rot7.addOrReplaceChild("keyboard", CubeListBuilder.create().texOffs(23, 83).addBox(-0.697F, -3.1804F, -4.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(-0.3F))
                .texOffs(41, 0).addBox(1.303F, -2.7804F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 89).addBox(-0.197F, -3.4304F, -3.5F, 4.0F, 1.0F, 7.0F, new CubeDeformation(-0.2F))
                .texOffs(88, 62).addBox(-0.197F, -3.7304F, -3.5F, 4.0F, 1.0F, 7.0F, new CubeDeformation(-0.4F))
                .texOffs(17, 66).addBox(-0.197F, -3.8304F, -3.5F, 4.0F, 1.0F, 7.0F, new CubeDeformation(-0.4F)), PartPose.offset(2.05F, 4.675F, 0.0F));

        PartDefinition cube_r271 = keyboard.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(41, 0).addBox(0.0F, -0.7F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-1.0271F, -0.1631F, 0.0F, 0.0F, 0.0F, -1.0036F));

        PartDefinition cube_r272 = keyboard.addOrReplaceChild("cube_r272", CubeListBuilder.create().texOffs(41, 2).addBox(-1.8F, 2.4F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.303F, -2.1804F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition cash_reg = rot7.addOrReplaceChild("cash_reg", CubeListBuilder.create().texOffs(53, 83).addBox(-0.9F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.3F))
                .texOffs(105, 124).addBox(-0.2F, -0.7F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(2.0F, 2.0F, -9.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition cube_r273 = cash_reg.addOrReplaceChild("cube_r273", CubeListBuilder.create().texOffs(119, 13).addBox(-0.15F, -1.25F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.5F, -0.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition pump = rot7.addOrReplaceChild("pump", CubeListBuilder.create().texOffs(117, 44).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9661F, 2.03F, -5.0F, 0.0F, -1.0036F, -0.5672F));

        PartDefinition pinballpump2 = pump.addOrReplaceChild("pinballpump2", CubeListBuilder.create().texOffs(117, 39).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F))
                .texOffs(13, 100).addBox(-0.5F, -0.5F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pump2 = rot7.addOrReplaceChild("pump2", CubeListBuilder.create().texOffs(117, 44).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.9661F, 2.03F, 5.0F, 0.0F, -0.5672F, -0.5672F));

        PartDefinition pinballpump = pump2.addOrReplaceChild("pinballpump", CubeListBuilder.create().texOffs(117, 39).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F))
                .texOffs(13, 100).addBox(-0.5F, -0.5F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition lever = rot7.addOrReplaceChild("lever", CubeListBuilder.create().texOffs(118, 78).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(54, 125).addBox(0.85F, -1.9F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(54, 125).addBox(0.85F, -1.9F, 0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(59, 125).addBox(-1.9F, -1.7F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(59, 125).addBox(-1.9F, -1.7F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.5F, 0.5F, -6.9F, 0.0F, 0.48F, 0.0F));

        PartDefinition bone7 = lever.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(0, 63).addBox(-0.75F, -0.75F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(100, 124).addBox(-0.5F, -0.5F, -0.65F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(95, 124).addBox(-0.5F, -3.0F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.5F, -1.5F, 1.15F, 0.0F, 0.0F, -0.829F));

        PartDefinition cube_r274 = bone7.addOrReplaceChild("cube_r274", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -2.0F, -0.001F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.25F, -0.75F, 0.001F, 0.2618F, 0.0F, 0.0F));

        PartDefinition bone8 = lever.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(0, 63).addBox(-0.75F, -0.75F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(95, 124).addBox(-0.5F, -3.0F, -0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(100, 124).addBox(-0.5F, -0.5F, -0.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.5F, -1.5F, -1.15F, 0.0F, 0.0F, -0.829F));

        PartDefinition cube_r275 = bone8.addOrReplaceChild("cube_r275", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -2.0F, -0.001F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.25F, -0.75F, 0.001F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bell = rot7.addOrReplaceChild("bell", CubeListBuilder.create().texOffs(28, 40).addBox(-2.0F, -2.0F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(104, 63).addBox(-7.75F, -1.5F, -1.8F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(103, 26).addBox(-7.75F, -1.8F, -1.8F, 6.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(63, 27).addBox(-5.0F, -1.55F, -1.4F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(72, 125).addBox(-5.1F, -2.25F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-4.5F, -2.25F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-3.9F, -2.25F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-4.5F, -2.25F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-5.1F, -2.25F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 125).addBox(-3.9F, -2.25F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(63, 27).addBox(-5.0F, -1.55F, -0.3F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(49, 125).addBox(-6.05F, -2.15F, -0.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(88, 101).addBox(2.45F, -1.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 63).addBox(2.45F, -2.05F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(36, 30).addBox(2.7F, -2.25F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(67, 101).addBox(1.7F, -1.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(118, 57).addBox(-2.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(118, 57).addBox(0.0F, -2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-5.0F, 0.75F, 6.1F, 0.0227F, -0.4795F, -0.0492F));

        PartDefinition bone9 = bell.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(79, 84).addBox(-1.65F, -0.1F, -0.65F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.0F, -2.5F, 0.0F, 0.0F, -0.1745F, 0.0F));

        PartDefinition bone10 = bell.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(79, 84).addBox(-1.65F, -0.1F, -0.65F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(1.0F, -2.5F, 0.0F, 0.0F, 1.2217F, 0.0F));

        PartDefinition bone11 = rot7.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(98, 89).addBox(-0.75F, -1.1F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(42, 88).addBox(-3.05F, -1.55F, 0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(42, 88).addBox(-2.25F, -1.55F, 1.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(47, 120).addBox(0.25F, -0.9F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 89).addBox(-0.75F, -1.35F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(124, 86).addBox(-2.15F, -1.15F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(103, 11).addBox(-0.75F, -2.55F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
                .texOffs(103, 11).addBox(-0.75F, -2.55F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.35F))
                .texOffs(90, 124).addBox(-0.75F, -2.55F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(90, 124).addBox(-0.75F, -2.55F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-7.0F, 0.5F, -5.1F, 0.0F, 0.0F, -0.0436F));

        PartDefinition bow2 = rot7.addOrReplaceChild("bow2", CubeListBuilder.create().texOffs(79, 79).addBox(-1.8107F, 0.2016F, -4.0F, 5.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(86, 48).addBox(-1.8107F, 0.4016F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(85, 124).addBox(-1.55F, -0.1F, -1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(85, 124).addBox(-1.85F, -0.6F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.2863F, -1.9443F, 0.0F, 0.0F, 0.0F, 1.0385F));

        PartDefinition bone6 = bow2.addOrReplaceChild("bone6", CubeListBuilder.create(), PartPose.offset(-0.4895F, 2.3462F, 2.9013F));

        PartDefinition cube_r276 = bone6.addOrReplaceChild("cube_r276", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -5.8027F, 0.0F, -1.5708F, 0.9163F));

        PartDefinition cube_r277 = bone6.addOrReplaceChild("cube_r277", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -5.8027F, 0.0F, -1.5708F, 1.2217F));

        PartDefinition cube_r278 = bone6.addOrReplaceChild("cube_r278", CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -5.8027F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r279 = bone6.addOrReplaceChild("cube_r279", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.7487F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -5.8027F, 0.0F, -1.5708F, 0.6109F));

        PartDefinition cube_r280 = bone6.addOrReplaceChild("cube_r280", CubeListBuilder.create().texOffs(112, 142).addBox(0.0F, -1.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -5.8027F, 0.0F, -1.5708F, 0.3054F));

        PartDefinition cube_r281 = bone6.addOrReplaceChild("cube_r281", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -6.8027F, 0.0F, -1.5708F, 0.9163F));

        PartDefinition cube_r282 = bone6.addOrReplaceChild("cube_r282", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -6.8027F, 0.0F, -1.5708F, 1.2217F));

        PartDefinition cube_r283 = bone6.addOrReplaceChild("cube_r283", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.7487F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -6.8027F, 0.0F, -1.5708F, 0.6109F));

        PartDefinition cube_r284 = bone6.addOrReplaceChild("cube_r284", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.7487F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.4027F, 0.0F, -1.5708F, 0.6109F));

        PartDefinition cube_r285 = bone6.addOrReplaceChild("cube_r285", CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.4027F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r286 = bone6.addOrReplaceChild("cube_r286", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.4027F, 0.0F, -1.5708F, 1.2217F));

        PartDefinition cube_r287 = bone6.addOrReplaceChild("cube_r287", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.4027F, 0.0F, -1.5708F, 0.9163F));

        PartDefinition cube_r288 = bone6.addOrReplaceChild("cube_r288", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.7487F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -4.9027F, 0.0F, -1.5708F, 0.6109F));

        PartDefinition cube_r289 = bone6.addOrReplaceChild("cube_r289", CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -4.9027F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r290 = bone6.addOrReplaceChild("cube_r290", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, -1.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -4.9027F, 0.0F, -1.5708F, 1.2217F));

        PartDefinition cube_r291 = bone6.addOrReplaceChild("cube_r291", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -4.9027F, 0.0F, -1.5708F, 0.9163F));

        PartDefinition cube_r292 = bone6.addOrReplaceChild("cube_r292", CubeListBuilder.create().texOffs(112, 142).addBox(0.0F, -1.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, -4.9027F, 0.0F, -1.5708F, 0.3054F));

        PartDefinition cube_r293 = bone6.addOrReplaceChild("cube_r293", CubeListBuilder.create().texOffs(112, 142).addBox(0.0F, -1.2522F, -0.8644F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -6.4027F, 0.0F, -1.5708F, 0.3054F));

        PartDefinition bone12 = bone6.addOrReplaceChild("bone12", CubeListBuilder.create(), PartPose.offset(0.5F, 0.0F, -6.3027F));

        PartDefinition cube_r294 = bone12.addOrReplaceChild("cube_r294", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 6.4027F, 0.0F, 1.5708F, 0.9163F));

        PartDefinition cube_r295 = bone12.addOrReplaceChild("cube_r295", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 6.4027F, 0.0F, 1.5708F, 1.2217F));

        PartDefinition cube_r296 = bone12.addOrReplaceChild("cube_r296", CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 6.4027F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r297 = bone12.addOrReplaceChild("cube_r297", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.2513F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 6.4027F, 0.0F, 1.5708F, 0.6109F));

        PartDefinition cube_r298 = bone12.addOrReplaceChild("cube_r298", CubeListBuilder.create().texOffs(112, 142).addBox(0.0F, -1.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 6.4027F, 0.0F, 1.5708F, 0.3054F));

        PartDefinition cube_r299 = bone12.addOrReplaceChild("cube_r299", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.3027F, 0.0F, 1.5708F, 0.9163F));

        PartDefinition cube_r300 = bone12.addOrReplaceChild("cube_r300", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.3027F, 0.0F, 1.5708F, 1.2217F));

        PartDefinition cube_r301 = bone12.addOrReplaceChild("cube_r301", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.2513F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 7.3027F, 0.0F, 1.5708F, 0.6109F));

        PartDefinition cube_r302 = bone12.addOrReplaceChild("cube_r302", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.2513F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.9027F, 0.0F, 1.5708F, 0.6109F));

        PartDefinition cube_r303 = bone12.addOrReplaceChild("cube_r303", CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.9027F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r304 = bone12.addOrReplaceChild("cube_r304", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.9027F, 0.0F, 1.5708F, 1.2217F));

        PartDefinition cube_r305 = bone12.addOrReplaceChild("cube_r305", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.9027F, 0.0F, 1.5708F, 0.9163F));

        PartDefinition cube_r306 = bone12.addOrReplaceChild("cube_r306", CubeListBuilder.create().texOffs(81, 180).addBox(0.0F, -0.5F, -0.2513F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 4.9027F, 0.0F, 1.5708F, 0.6109F));

        PartDefinition cube_r307 = bone12.addOrReplaceChild("cube_r307", CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 4.9027F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r308 = bone12.addOrReplaceChild("cube_r308", CubeListBuilder.create().texOffs(70, 207).addBox(0.0F, 0.9347F, 0.201F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 4.9027F, 0.0F, 1.5708F, 1.2217F));

        PartDefinition cube_r309 = bone12.addOrReplaceChild("cube_r309", CubeListBuilder.create().texOffs(78, 190).addBox(0.0F, 0.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 4.9027F, 0.0F, 1.5708F, 0.9163F));

        PartDefinition cube_r310 = bone12.addOrReplaceChild("cube_r310", CubeListBuilder.create().texOffs(112, 142).addBox(0.0F, -1.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 4.9027F, 0.0F, 1.5708F, 0.3054F));

        PartDefinition cube_r311 = bone12.addOrReplaceChild("cube_r311", CubeListBuilder.create().texOffs(112, 142).addBox(0.0F, -1.2522F, -0.1356F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 6.9027F, 0.0F, 1.5708F, 0.3054F));

        PartDefinition bow = bow2.addOrReplaceChild("bow", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5895F, -0.5538F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone137 = bow.addOrReplaceChild("bone137", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0107F, 0.9681F, 0.5539F, 0.2236F, 0.0188F, -0.0003F));

        PartDefinition cube_r312 = bone137.addOrReplaceChild("cube_r312", CubeListBuilder.create().texOffs(21, 66).addBox(-0.15F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, 0.0319F, 0.9461F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r313 = bone137.addOrReplaceChild("cube_r313", CubeListBuilder.create().texOffs(70, 207).addBox(-0.15F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, 0.0319F, 0.9461F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r314 = bone137.addOrReplaceChild("cube_r314", CubeListBuilder.create().texOffs(78, 190).addBox(-0.15F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, 0.0319F, 0.9461F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r315 = bone137.addOrReplaceChild("cube_r315", CubeListBuilder.create().texOffs(112, 142).addBox(-0.15F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, 0.0319F, 0.9461F, -0.48F, 0.0F, 0.0F));

        PartDefinition cube_r316 = bone137.addOrReplaceChild("cube_r316", CubeListBuilder.create().texOffs(81, 180).addBox(-0.15F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, 0.0319F, 0.9461F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r317 = bone137.addOrReplaceChild("cube_r317", CubeListBuilder.create().texOffs(100, 213).addBox(0.05F, -0.75F, -1.3F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, -0.764F, -0.5832F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r318 = bone137.addOrReplaceChild("cube_r318", CubeListBuilder.create().texOffs(100, 213).addBox(0.15F, -0.05F, -1.3F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, -0.764F, -0.5832F, -0.48F, 0.0F, 0.0F));

        PartDefinition cube_r319 = bone137.addOrReplaceChild("cube_r319", CubeListBuilder.create().texOffs(84, 216).addBox(-0.025F, -0.45F, -1.7F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, -0.764F, -0.5832F, 1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r320 = bone137.addOrReplaceChild("cube_r320", CubeListBuilder.create().texOffs(100, 213).addBox(-0.05F, 0.675F, -1.85F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, -0.764F, -0.5832F, 0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r321 = bone137.addOrReplaceChild("cube_r321", CubeListBuilder.create().texOffs(100, 213).addBox(-0.05F, 1.35F, -2.1F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, -0.764F, -0.5832F, 0.4363F, 0.0F, 0.0F));

        PartDefinition cube_r322 = bone137.addOrReplaceChild("cube_r322", CubeListBuilder.create().texOffs(72, 221).addBox(-0.5F, -2.498F, -0.0594F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0107F, -0.0281F, 1.285F, 0.2967F, 0.0011F, 0.0042F));

        PartDefinition cube_r323 = bone137.addOrReplaceChild("cube_r323", CubeListBuilder.create().texOffs(74, 207).addBox(-0.5F, -0.5251F, -0.9399F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-0.0107F, -0.0281F, 1.285F, 0.3491F, 0.0011F, 0.0042F));

        PartDefinition bone139 = bow.addOrReplaceChild("bone139", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25F, 2.0F, -0.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition bone148 = bow.addOrReplaceChild("bone148", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25F, 0.75F, 0.9F, 0.2182F, 0.0F, 0.0F));

        PartDefinition bone149 = bow.addOrReplaceChild("bone149", CubeListBuilder.create(), PartPose.offsetAndRotation(0.25F, 0.0F, -0.85F, 1.0472F, 0.0F, 0.0F));

        PartDefinition bone158 = bow.addOrReplaceChild("bone158", CubeListBuilder.create(), PartPose.offsetAndRotation(0.15F, 0.25F, -0.1F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone159 = bow.addOrReplaceChild("bone159", CubeListBuilder.create(), PartPose.offsetAndRotation(0.15F, 1.75F, 0.25F, -0.2618F, 0.0F, 0.0F));

        PartDefinition bone96 = bow2.addOrReplaceChild("bone96", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.1682F, 42.1296F, 0.2224F, 0.0F, -0.48F, -0.1745F));

        PartDefinition meter6 = bone96.addOrReplaceChild("meter6", CubeListBuilder.create().texOffs(248, 25).addBox(-0.9167F, -0.5F, -0.9167F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(244, 20).addBox(-0.9167F, -0.45F, -0.9167F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(245, 31).addBox(-1.1667F, -0.55F, -1.1667F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(3.3037F, -38.6618F, -5.0688F, -0.0678F, 0.4755F, -0.1473F));

        PartDefinition bone102 = meter6.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(155, 13).addBox(-0.75F, 0.0F, -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0333F, -0.45F, 0.0833F, 0.0F, -0.48F, 0.0F));

        PartDefinition valve = rot7.addOrReplaceChild("valve", CubeListBuilder.create().texOffs(80, 124).addBox(-0.5F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 124).addBox(0.25F, -2.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(76, 121).addBox(-0.5F, -1.25F, 0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-17.9156F, -4.912F, 0.35F, 0.0F, 0.0F, -0.5236F));

        PartDefinition bone13 = valve.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(120, 63).addBox(-1.4875F, -0.4375F, -0.4375F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
                .texOffs(124, 4).addBox(-0.4875F, -0.4375F, -0.4375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(100, 0).addBox(0.2625F, -1.1875F, -1.1875F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(125, 43).addBox(-0.2875F, -0.4375F, -0.4375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.4375F, -1.5F, 0.9116F, 0.7854F, 0.0F, 0.0F));

        PartDefinition valve2 = rot7.addOrReplaceChild("valve2", CubeListBuilder.create().texOffs(80, 124).addBox(-0.5F, -2.3007F, 0.4537F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 124).addBox(0.25F, -2.3007F, 0.4537F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(76, 121).addBox(-0.5F, -1.5507F, 0.4537F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-17.9156F, -4.762F, 2.1F, -0.3011F, 0.0522F, -0.5315F));

        PartDefinition bone14 = valve2.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(120, 63).addBox(-1.4875F, -0.4375F, -0.4375F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F))
                .texOffs(124, 4).addBox(-0.4875F, -0.4375F, -0.4375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.15F))
                .texOffs(100, 0).addBox(0.2625F, -1.1875F, -1.1875F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(125, 43).addBox(-0.2875F, -0.4375F, -0.4375F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offsetAndRotation(1.4375F, -1.7778F, 0.8683F, 1.0472F, 0.0F, 0.0F));

        PartDefinition cables2 = rot7.addOrReplaceChild("cables2", CubeListBuilder.create().texOffs(0, 120).addBox(-0.25F, 0.5F, -6.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(77, 12).addBox(-1.5F, 1.0F, 5.25F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 8.25F, 0.0F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r324 = cables2.addOrReplaceChild("cube_r324", CubeListBuilder.create().texOffs(100, 96).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r325 = cables2.addOrReplaceChild("cube_r325", CubeListBuilder.create().texOffs(101, 33).addBox(-1.0F, 0.0F, -6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition panel_2 = controls.addOrReplaceChild("panel_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rot8 = panel_2.addOrReplaceChild("rot8", CubeListBuilder.create(), PartPose.offsetAndRotation(20.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition cube_r326 = rot8.addOrReplaceChild("cube_r326", CubeListBuilder.create().texOffs(57, 48).addBox(0.1F, 1.5F, -2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition flightlever = rot8.addOrReplaceChild("flightlever", CubeListBuilder.create().texOffs(76, 98).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(60, 75).addBox(-1.0F, -2.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(117, 5).addBox(-0.75F, -2.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(74, 116).addBox(-0.5F, -2.0F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.5F, -0.5F, 5.75F, 0.0F, -0.48F, 0.0F));

        PartDefinition lights2 = flightlever.addOrReplaceChild("lights2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition light = lights2.addOrReplaceChild("light", CubeListBuilder.create().texOffs(86, 52).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.03F)), PartPose.offset(0.0F, -1.5F, 0.0F));

        PartDefinition light2 = lights2.addOrReplaceChild("light2", CubeListBuilder.create().texOffs(86, 52).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.03F)), PartPose.offset(0.0F, -1.5F, 0.0F));

        PartDefinition light3 = lights2.addOrReplaceChild("light3", CubeListBuilder.create().texOffs(86, 52).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition light4 = lights2.addOrReplaceChild("light4", CubeListBuilder.create().texOffs(86, 52).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition light5 = lights2.addOrReplaceChild("light5", CubeListBuilder.create().texOffs(86, 52).addBox(0.0F, 0.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.03F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone29 = flightlever.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(28, 30).addBox(-0.75F, -2.25F, -1.25F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(88, 65).addBox(-0.5F, -3.05F, -1.85F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, 0.0F, -1.309F));

        PartDefinition bone30 = flightlever.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(88, 65).addBox(-0.5F, -3.05F, -0.2F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(28, 30).addBox(-0.75F, -2.25F, 1.25F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.5F, 0.0F, 0.0F, 0.0F, -1.2217F));

        PartDefinition disc = rot8.addOrReplaceChild("disc", CubeListBuilder.create().texOffs(7, 120).addBox(-1.75F, 0.3F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(117, 116).addBox(-2.6F, 0.2F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(121, 123).addBox(1.85F, 0.15F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-4.0F, -0.5F, 0.0F));

        PartDefinition cube_r327 = disc.addOrReplaceChild("cube_r327", CubeListBuilder.create().texOffs(117, 116).addBox(-2.6F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r328 = disc.addOrReplaceChild("cube_r328", CubeListBuilder.create().texOffs(117, 116).addBox(-2.6F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r329 = disc.addOrReplaceChild("cube_r329", CubeListBuilder.create().texOffs(117, 116).addBox(-2.6F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition cube_r330 = disc.addOrReplaceChild("cube_r330", CubeListBuilder.create().texOffs(117, 116).addBox(-2.6F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(7, 120).addBox(-1.75F, 0.1F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r331 = disc.addOrReplaceChild("cube_r331", CubeListBuilder.create().texOffs(117, 116).addBox(-2.6F, 0.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(7, 120).addBox(-1.75F, 0.1F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.2F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r332 = disc.addOrReplaceChild("cube_r332", CubeListBuilder.create().texOffs(7, 120).addBox(-1.75F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3F, 0.0F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r333 = disc.addOrReplaceChild("cube_r333", CubeListBuilder.create().texOffs(7, 120).addBox(-1.75F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3F, 0.0F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r334 = disc.addOrReplaceChild("cube_r334", CubeListBuilder.create().texOffs(7, 120).addBox(-1.75F, 0.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.3F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        PartDefinition bone31 = disc.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(70, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.05F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone34 = disc.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(70, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.05F, -1.0F, 0.0F, -0.4363F, 0.0F));

        PartDefinition bone35 = disc.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(70, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.05F, 1.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition bone32 = disc.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(70, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.05F, -1.75F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone33 = disc.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(28, 45).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(70, 12).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.05F, 1.75F, 0.0F, 1.5708F, 0.0F));

        PartDefinition dial = rot8.addOrReplaceChild("dial", CubeListBuilder.create().texOffs(67, 96).addBox(0.0F, -1.25F, -1.25F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(123, 114).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.25F, 0.9F, 9.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition crank = rot8.addOrReplaceChild("crank", CubeListBuilder.create().texOffs(28, 15).addBox(-0.2F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2687F, 0.6717F, -9.75F, 0.0F, 0.0F, -0.5672F));

        PartDefinition bone36 = crank.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(40, 120).addBox(-0.6F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(123, 92).addBox(0.8F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(11, 66).addBox(1.1F, -0.25F, -0.25F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.0F, 0.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition crank2 = rot8.addOrReplaceChild("crank2", CubeListBuilder.create().texOffs(28, 15).addBox(-0.2F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2687F, 0.6717F, -8.25F, 0.0F, 0.0F, -0.5672F));

        PartDefinition bone37 = crank2.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(40, 120).addBox(-0.6F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(123, 92).addBox(0.8F, 0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(11, 66).addBox(1.1F, -0.25F, -0.25F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition button3 = rot8.addOrReplaceChild("button3", CubeListBuilder.create().texOffs(63, 40).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -0.25F, -4.0F));

        PartDefinition bone38 = button3.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(123, 75).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone128 = bone38.addOrReplaceChild("bone128", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cables = rot8.addOrReplaceChild("cables", CubeListBuilder.create().texOffs(0, 120).addBox(-0.25F, 0.5F, -6.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(77, 12).addBox(-1.5F, 1.0F, 5.25F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 8.25F, 0.0F, 0.0F, 0.0F, 2.3562F));

        PartDefinition cube_r335 = cables.addOrReplaceChild("cube_r335", CubeListBuilder.create().texOffs(100, 96).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition cube_r336 = cables.addOrReplaceChild("cube_r336", CubeListBuilder.create().texOffs(101, 33).addBox(-1.0F, 0.0F, -6.0F, 3.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition lever3 = rot8.addOrReplaceChild("lever3", CubeListBuilder.create().texOffs(110, 119).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(110, 119).addBox(-7.0F, -1.0F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(119, 100).addBox(-1.0F, -1.8F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(51, 72).addBox(-6.0F, -1.7F, -1.0F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(119, 100).addBox(-7.0F, -1.8F, -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(0, 115).addBox(-4.0F, -2.5F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(123, 69).addBox(-3.5F, -2.0F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 0.0F, -6.75F, -0.0182F, 0.4796F, -0.0393F));

        PartDefinition button5 = lever3.addOrReplaceChild("button5", CubeListBuilder.create().texOffs(63, 123).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-6.0F, -1.55F, -0.5F));

        PartDefinition button6 = lever3.addOrReplaceChild("button6", CubeListBuilder.create().texOffs(63, 123).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, -1.55F, -0.5F));

        PartDefinition bone39 = lever3.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(70, 38).addBox(0.0F, -0.75F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(45, 123).addBox(-0.5F, -0.5F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.0F, -1.5F, 1.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition cube_r337 = bone39.addOrReplaceChild("cube_r337", CubeListBuilder.create().texOffs(70, 38).addBox(0.0F, -0.75F, 0.25F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.25F, 0.0F, 0.0F, -1.5708F));

        PartDefinition panel = rot8.addOrReplaceChild("panel", CubeListBuilder.create().texOffs(49, 111).addBox(-1.0F, -0.7F, -3.0F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(111, 103).addBox(-0.75F, -1.0F, -2.8F, 3.0F, 1.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-13.0F, -0.5F, 2.5F, 0.0F, 0.0F, 0.48F));

        PartDefinition cube_r338 = panel.addOrReplaceChild("cube_r338", CubeListBuilder.create().texOffs(83, 39).addBox(-2.75F, -0.4F, -4.2F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(104, 57).addBox(-2.75F, -0.3F, -4.2F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition dial2 = panel.addOrReplaceChild("dial2", CubeListBuilder.create().texOffs(40, 123).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(34, 45).addBox(-0.5F, -1.75F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -0.1F, -2.25F));

        PartDefinition dial3 = panel.addOrReplaceChild("dial3", CubeListBuilder.create().texOffs(40, 123).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(34, 45).addBox(-0.5F, -1.75F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, -0.1F, 0.25F));

        PartDefinition light15 = panel.addOrReplaceChild("light15", CubeListBuilder.create().texOffs(123, 39).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-0.5F, -0.75F, -2.4F));

        PartDefinition bone40 = light15.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(123, 33).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.19F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition light16 = panel.addOrReplaceChild("light16", CubeListBuilder.create().texOffs(123, 39).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-0.5F, -0.75F, -1.5F));

        PartDefinition bone41 = light16.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(123, 33).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.19F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition light17 = panel.addOrReplaceChild("light17", CubeListBuilder.create().texOffs(123, 39).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-0.5F, -0.75F, -0.6F));

        PartDefinition bone42 = light17.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(123, 33).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.19F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition light18 = panel.addOrReplaceChild("light18", CubeListBuilder.create().texOffs(123, 39).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-0.5F, -0.75F, 0.3F));

        PartDefinition bone43 = light18.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(123, 33).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.19F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fluid_reservoir = rot8.addOrReplaceChild("fluid_reservoir", CubeListBuilder.create().texOffs(42, 83).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-15.0F, -2.0F, -2.5F, 0.0F, 0.0F, -0.829F));

        PartDefinition cube_r339 = fluid_reservoir.addOrReplaceChild("cube_r339", CubeListBuilder.create().texOffs(121, 0).addBox(-1.2549F, -2.1001F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.0999F))
                .texOffs(5, 123).addBox(-0.5049F, -2.6001F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(71, 121).addBox(-0.5049F, -2.1001F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.75F, -1.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r340 = fluid_reservoir.addOrReplaceChild("cube_r340", CubeListBuilder.create().texOffs(101, 118).addBox(-0.5546F, -0.5912F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.75F, -1.25F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition cube_r341 = fluid_reservoir.addOrReplaceChild("cube_r341", CubeListBuilder.create().texOffs(119, 10).addBox(0.35F, -1.45F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.099F))
                .texOffs(0, 123).addBox(0.75F, -0.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(0, 123).addBox(2.0F, -0.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(72, 56).addBox(0.0F, -0.45F, -0.5F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1309F));

        PartDefinition cube_r342 = fluid_reservoir.addOrReplaceChild("cube_r342", CubeListBuilder.create().texOffs(122, 120).addBox(-0.8158F, -0.9721F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(4.75F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition needle = rot8.addOrReplaceChild("needle", CubeListBuilder.create().texOffs(39, 33).addBox(0.0F, -3.0F, -0.25F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(111, 122).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-4.0F, 0.0F, 0.0F));

        PartDefinition cube_r343 = needle.addOrReplaceChild("cube_r343", CubeListBuilder.create().texOffs(39, 33).addBox(0.0F, -3.0F, -0.25F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition handbrake2 = panel_2.addOrReplaceChild("handbrake2", CubeListBuilder.create(), PartPose.offsetAndRotation(19.9345F, -11.65F, 11.7926F, 1.0504F, -0.061F, 1.4649F));

        PartDefinition cube_r344 = handbrake2.addOrReplaceChild("cube_r344", CubeListBuilder.create().texOffs(175, 253).addBox(-1.0F, -0.5F, -1.8F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(174, 252).addBox(-1.0F, -0.5F, -1.55F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.7904F, -1.0F, -0.9978F, 0.0F, -1.4835F, 0.0F));

        PartDefinition cube_r345 = handbrake2.addOrReplaceChild("cube_r345", CubeListBuilder.create().texOffs(174, 252).addBox(-0.9F, -1.5F, -1.2F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone129 = handbrake2.addOrReplaceChild("bone129", CubeListBuilder.create().texOffs(194, 253).addBox(-2.7071F, -0.5F, -0.7071F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(1.1487F, -1.0F, 0.0196F, 0.0F, 0.6981F, 0.0F));

        PartDefinition cube_r346 = bone129.addOrReplaceChild("cube_r346", CubeListBuilder.create().texOffs(183, 254).addBox(-5.0F, -0.5F, 0.4F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.7071F, 0.0F, -0.7071F, 0.0F, -0.2182F, 0.0F));

        PartDefinition panel_3 = controls.addOrReplaceChild("panel_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition rot9 = panel_3.addOrReplaceChild("rot9", CubeListBuilder.create(), PartPose.offsetAndRotation(20.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition meter = rot9.addOrReplaceChild("meter", CubeListBuilder.create().texOffs(111, 114).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(79, 79).addBox(-0.15F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-14.0F, -1.0F, -3.25F, 0.0F, 0.0F, -0.829F));

        PartDefinition bone15 = meter.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(28, 0).addBox(0.85F, -1.0F, -0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.25F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition meter2 = rot9.addOrReplaceChild("meter2", CubeListBuilder.create().texOffs(111, 114).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(79, 79).addBox(-0.15F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-14.0F, -1.0F, -1.15F, 0.0F, 0.0F, -0.829F));

        PartDefinition bone16 = meter2.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(28, 0).addBox(0.85F, -1.0F, -0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.25F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition meter3 = rot9.addOrReplaceChild("meter3", CubeListBuilder.create().texOffs(111, 114).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(79, 79).addBox(-0.15F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-14.0F, -1.0F, 1.15F, 0.0F, 0.0F, -0.829F));

        PartDefinition bone17 = meter3.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(28, 0).addBox(0.85F, -1.0F, -0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.25F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition meter4 = rot9.addOrReplaceChild("meter4", CubeListBuilder.create().texOffs(111, 114).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(79, 79).addBox(-0.15F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-14.0F, -1.0F, 3.25F, 0.0F, 0.0F, -0.829F));

        PartDefinition bone18 = meter4.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(28, 0).addBox(0.85F, -1.0F, -0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.25F, 0.0F, 0.6109F, 0.0F, 0.0F));

        PartDefinition bone19 = rot9.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(114, 33).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.2F))
                .texOffs(27, 141).addBox(0.2F, -2.2F, -3.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 149).addBox(0.2F, -2.225F, -3.25F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 139).addBox(0.2F, -2.25F, -3.25F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 142).addBox(-0.8F, -2.15F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(17, 138).addBox(-1.0F, -2.15F, 3.65F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 142).addBox(-2.0F, -2.15F, -2.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(13, 122).addBox(-0.5F, -3.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F))
                .texOffs(86, 121).addBox(-0.5F, -3.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(113, 51).addBox(-1.25F, -2.1F, -0.75F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(-17.0F, -1.75F, -1.0F, 0.0F, 0.0F, 0.7418F));

        PartDefinition cube_r347 = bone19.addOrReplaceChild("cube_r347", CubeListBuilder.create().texOffs(124, 74).addBox(-3.0F, 0.0F, -2.6F, 6.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -2.6F, 3.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition cube_r348 = bone19.addOrReplaceChild("cube_r348", CubeListBuilder.create().texOffs(19, 143).addBox(-0.5F, -0.5F, -0.2F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9F, -1.65F, -1.85F, -2.3562F, 0.0F, 0.0F));

        PartDefinition cube_r349 = bone19.addOrReplaceChild("cube_r349", CubeListBuilder.create().texOffs(19, 143).addBox(-0.5F, -0.5F, -0.2F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9F, -1.65F, -2.65F, -0.7854F, 0.0F, 0.0F));

        PartDefinition vent = rot9.addOrReplaceChild("vent", CubeListBuilder.create().texOffs(0, 73).addBox(-3.0F, -1.501F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.851F, 7.75F, 0.0227F, -0.4795F, -0.0492F));

        PartDefinition bone215 = vent.addOrReplaceChild("bone215", CubeListBuilder.create(), PartPose.offset(0.5F, -1.601F, 0.0F));

        PartDefinition cube_r350 = bone215.addOrReplaceChild("cube_r350", CubeListBuilder.create().texOffs(97, 57).addBox(-2.9F, 0.05F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.288F));

        PartDefinition levers = rot9.addOrReplaceChild("levers", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -1.0F));

        PartDefinition lever4 = levers.addOrReplaceChild("lever4", CubeListBuilder.create().texOffs(109, 89).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(-3.55F, -0.15F, 2.0F));

        PartDefinition bone20 = lever4.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(125, 56).addBox(-0.25F, -1.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.5F, 0.0F, -0.4F));

        PartDefinition lever5 = levers.addOrReplaceChild("lever5", CubeListBuilder.create().texOffs(109, 89).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-2.75F, -0.15F, 3.25F, 0.0F, 0.7854F, 0.0F));

        PartDefinition bone21 = lever5.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(125, 56).addBox(-0.25F, -1.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.5F, 0.0F, -0.4F));

        PartDefinition lever6 = levers.addOrReplaceChild("lever6", CubeListBuilder.create().texOffs(109, 89).addBox(-1.0F, -0.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.25F, -0.15F, 3.5F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone22 = lever6.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(125, 56).addBox(-0.25F, -1.3F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-0.5F, 0.0F, -0.4F));

        PartDefinition needle2 = rot9.addOrReplaceChild("needle2", CubeListBuilder.create().texOffs(62, 50).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(122, 103).addBox(-0.5F, -0.8F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-2.0F, -0.2F, -3.5F));

        PartDefinition bone216 = needle2.addOrReplaceChild("bone216", CubeListBuilder.create().texOffs(38, 15).addBox(-0.25F, -2.6F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r351 = bone216.addOrReplaceChild("cube_r351", CubeListBuilder.create().texOffs(38, 15).addBox(-0.25F, -2.6F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition spinny = rot9.addOrReplaceChild("spinny", CubeListBuilder.create().texOffs(67, 91).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, 1.75F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition bone24 = spinny.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(117, 120).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(69, 30).addBox(-0.5F, -0.7F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F))
                .texOffs(69, 30).addBox(-0.5F, -1.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offset(1.0F, -2.0F, 0.0F));

        PartDefinition bone26 = bone24.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(36, 93).addBox(-0.5F, -0.45F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F))
                .texOffs(112, 0).addBox(-1.0F, -0.95F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(86, 48).addBox(-0.5F, -0.45F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(53, 88).addBox(-0.5F, -0.45F, -1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, -1.15F, 0.0F));

        PartDefinition bone27 = bone26.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(53, 122).addBox(-0.5F, -1.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.04F))
                .texOffs(122, 49).addBox(-0.5F, -1.85F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.24F))
                .texOffs(122, 52).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.3F, 0.0F));

        PartDefinition stabilizers = rot9.addOrReplaceChild("stabilizers", CubeListBuilder.create().texOffs(122, 72).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.5F, 4.5F));

        PartDefinition bone25 = stabilizers.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(122, 66).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(58, 122).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition wibblylever2 = rot9.addOrReplaceChild("wibblylever2", CubeListBuilder.create().texOffs(118, 89).addBox(-1.0F, 0.0F, -0.75F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(96, 121).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-6.25F, -0.5F, -6.0F));

        PartDefinition wibblylever = wibblylever2.addOrReplaceChild("wibblylever", CubeListBuilder.create().texOffs(83, 118).addBox(-0.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(76, 39).addBox(2.75F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(91, 121).addBox(2.25F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.5F, -0.5F, 0.5F, 0.0F, 0.0F, -2.0944F));

        PartDefinition lever10 = rot9.addOrReplaceChild("lever10", CubeListBuilder.create().texOffs(118, 82).addBox(-1.4F, -0.5F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(125, 28).addBox(-0.9F, -1.5F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(64, 119).addBox(-1.0F, -2.5F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-8.8F, 0.0F, -4.4F));

        PartDefinition bone97 = lever10.addOrReplaceChild("bone97", CubeListBuilder.create().texOffs(39, 38).addBox(-0.25F, -2.75F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(81, 121).addBox(-0.5F, -2.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(-0.35F, -1.5F, 0.5F));

        PartDefinition pump4 = rot9.addOrReplaceChild("pump4", CubeListBuilder.create().texOffs(141, 87).addBox(1.9332F, -0.5046F, -0.6F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(151, 94).addBox(-0.5F, -0.5F, -0.4F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-8.3832F, -2.5204F, 5.6F, 0.0F, 0.0F, -0.0873F));

        PartDefinition bone163 = pump4.addOrReplaceChild("bone163", CubeListBuilder.create(), PartPose.offsetAndRotation(1.6F, 0.8F, -0.1F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r352 = bone163.addOrReplaceChild("cube_r352", CubeListBuilder.create().texOffs(138, 102).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.1217F, 0.7263F, 0.0F, 0.0F, 0.0F, 2.0508F));

        PartDefinition cube_r353 = bone163.addOrReplaceChild("cube_r353", CubeListBuilder.create().texOffs(138, 102).addBox(-2.075F, -0.4F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.7017F));

        PartDefinition button4 = rot9.addOrReplaceChild("button4", CubeListBuilder.create().texOffs(176, 135).addBox(2.0F, -2.0F, 1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(145, 120).addBox(-0.65F, -3.0F, 1.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.0999F))
                .texOffs(144, 106).addBox(0.225F, -3.5F, 1.25F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.0999F))
                .texOffs(178, 120).addBox(2.25F, -3.0F, 1.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5F, 1.5F, 3.25F));

        PartDefinition cube_r354 = button4.addOrReplaceChild("cube_r354", CubeListBuilder.create().texOffs(154, 100).addBox(-2.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-3.9677F, -2.1402F, 1.7373F, 0.2042F, -0.3378F, -0.5585F));

        PartDefinition cube_r355 = button4.addOrReplaceChild("cube_r355", CubeListBuilder.create().texOffs(181, 129).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-2.727F, -2.4999F, 2.2511F, 0.0F, -0.3927F, 0.0F));

        PartDefinition cube_r356 = button4.addOrReplaceChild("cube_r356", CubeListBuilder.create().texOffs(166, 99).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1999F)), PartPose.offsetAndRotation(0.1812F, -1.5F, 2.2307F, 0.0F, 0.0436F, 0.0F));

        PartDefinition cube_r357 = button4.addOrReplaceChild("cube_r357", CubeListBuilder.create().texOffs(178, 106).addBox(-1.65F, -3.0F, 1.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-1.1174F, 0.0F, 0.0715F, 0.0F, 0.1745F, 0.0F));

        PartDefinition cube_r358 = button4.addOrReplaceChild("cube_r358", CubeListBuilder.create().texOffs(138, 102).addBox(-0.2F, 0.0F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(1.6168F, -4.0204F, 2.25F, 0.0F, 0.0F, 1.5708F));

        PartDefinition bone217 = rot9.addOrReplaceChild("bone217", CubeListBuilder.create().texOffs(-2, 137).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 147).addBox(-0.75F, -2.5F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-9.5F, 1.5F, 3.25F));

        PartDefinition bone98 = rot9.addOrReplaceChild("bone98", CubeListBuilder.create().texOffs(0, 150).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-9.25F, 1.1F, 3.0F));

        PartDefinition lever11 = rot9.addOrReplaceChild("lever11", CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 192).addBox(-0.5F, -2.4F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(-1.25F, 1.3F, -8.0F));

        PartDefinition bone99 = lever11.addOrReplaceChild("bone99", CubeListBuilder.create().texOffs(15, 176).addBox(-0.25F, -1.8F, 0.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(14, 193).addBox(-0.5F, -2.2F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(-0.025F, -2.1835F, 0.0F, 0.0F, 0.0F, 0.0087F));

        PartDefinition panel_4 = controls.addOrReplaceChild("panel_4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition rot16 = panel_4.addOrReplaceChild("rot16", CubeListBuilder.create(), PartPose.offsetAndRotation(20.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition spinny2 = rot16.addOrReplaceChild("spinny2", CubeListBuilder.create().texOffs(67, 91).addBox(-1.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, 1.75F, 0.0F, 0.0F, 0.0F, -0.5672F));

        PartDefinition bone23 = spinny2.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(52, 203).addBox(-0.5F, -1.5F, -1.1F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(30, 215).addBox(-1.0F, -2.9F, -1.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(30, 215).addBox(-1.0F, -2.9F, 0.1F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(36, 186).addBox(-0.5F, -2.4F, -1.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(36, 186).addBox(-0.5F, -2.4F, 0.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(52, 203).addBox(-0.5F, -1.5F, 0.1F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(36, 233).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(1.0F, -2.15F, 0.0F, 0.0F, 0.3054F, 0.0F));

        PartDefinition bone100 = bone23.addOrReplaceChild("bone100", CubeListBuilder.create().texOffs(87, 238).addBox(-0.75F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -1.9F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition crank3 = rot16.addOrReplaceChild("crank3", CubeListBuilder.create().texOffs(190, 77).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -9.0F, 0.0F, 0.0F, 1.0036F));

        PartDefinition bone101 = crank3.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(211, 55).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(182, 55).addBox(-0.75F, -2.1F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(204, 40).addBox(1.5F, -3.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(167, 39).addBox(0.5F, -1.75F, -0.25F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(158, 84).addBox(2.0F, -2.75F, -0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition lever8 = rot16.addOrReplaceChild("lever8", CubeListBuilder.create().texOffs(224, 125).addBox(-1.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(-0.29F))
                .texOffs(226, 118).addBox(-0.6F, -1.75F, -1.1F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(233, 182).addBox(0.0F, -2.0F, -1.2F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(224, 136).addBox(-1.0F, -2.05F, 0.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(224, 136).addBox(-1.0F, -2.05F, -0.5F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(225, 136).addBox(0.0F, -2.325F, -1.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(225, 136).addBox(0.0F, -2.325F, -1.8F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-6.5F, 0.25F, -5.25F, -0.0227F, 0.4795F, -0.0492F));

        PartDefinition blackredlever = lever8.addOrReplaceChild("blackredlever", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -1.5F, 0.6F, 0.0F, 0.0F, -0.4014F));

        PartDefinition redleverright3 = blackredlever.addOrReplaceChild("redleverright3", CubeListBuilder.create().texOffs(188, 234).addBox(-0.5F, -2.494F, -0.475F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(161, 127).addBox(-0.5F, -2.7F, -0.35F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(161, 129).addBox(-0.5F, -2.7F, -0.55F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.0809F, -0.0273F, -1.075F, 0.0F, 0.0F, -0.0742F));

        PartDefinition cube_r359 = redleverright3.addOrReplaceChild("cube_r359", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -2.0F, 0.025F, 0.0F, 1.5708F, -1.5708F));

        PartDefinition cube_r360 = redleverright3.addOrReplaceChild("cube_r360", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -1.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -2.2F, -0.55F, 0.0F, 3.1416F, 0.0F));

        PartDefinition redleverleft3 = blackredlever.addOrReplaceChild("redleverleft3", CubeListBuilder.create().texOffs(188, 234).addBox(-0.4877F, -2.4051F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(161, 129).addBox(-0.4877F, -2.6108F, -0.85F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(-0.0467F, -0.1072F, -1.55F, 0.0F, 0.0F, -0.096F));

        PartDefinition cube_r361 = redleverleft3.addOrReplaceChild("cube_r361", CubeListBuilder.create().texOffs(161, 127).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0123F, -2.1108F, -0.15F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r362 = redleverleft3.addOrReplaceChild("cube_r362", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0123F, -1.9108F, 0.0F, 0.0F, -1.5708F, 1.5708F));

        PartDefinition cube_r363 = redleverleft3.addOrReplaceChild("cube_r363", CubeListBuilder.create().texOffs(161, 129).addBox(-0.5F, -0.5F, -0.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0123F, -2.1108F, -0.35F, 0.0F, 3.1416F, 0.0F));

        PartDefinition blacklever5 = blackredlever.addOrReplaceChild("blacklever5", CubeListBuilder.create().texOffs(159, 110).addBox(-0.4963F, -2.6461F, -1.0861F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(193, 234).addBox(-0.4963F, -2.246F, -0.8861F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-0.0037F, 0.471F, 0.0861F));

        PartDefinition cube_r364 = blacklever5.addOrReplaceChild("cube_r364", CubeListBuilder.create().texOffs(159, 110).addBox(-0.5038F, -2.1461F, -0.1638F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition blacklever2 = blackredlever.addOrReplaceChild("blacklever2", CubeListBuilder.create().texOffs(159, 110).addBox(-0.4963F, -2.6461F, -0.0861F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(193, 234).addBox(-0.4963F, -2.246F, -0.3361F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-0.0037F, 0.471F, 0.0861F));

        PartDefinition cube_r365 = blacklever2.addOrReplaceChild("cube_r365", CubeListBuilder.create().texOffs(159, 110).addBox(-0.5038F, -2.1461F, -0.6138F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, -3.1416F, 0.0F, -3.1416F));

        PartDefinition sonicport = rot16.addOrReplaceChild("sonicport", CubeListBuilder.create().texOffs(223, 92).addBox(-1.75F, 0.0F, -1.75F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(223, 105).addBox(-1.0F, 0.0F, -4.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(210, 109).addBox(-1.75F, -0.5F, -1.75F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.001F))
                .texOffs(206, 132).addBox(0.25F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(-0.5F, -0.75F, -1.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(-0.5F, -0.75F, 0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(-1.25F, -0.75F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(-1.5F, -1.0F, -0.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(-0.25F, -1.0F, -1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(0.5F, -1.0F, -0.25F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(206, 132).addBox(-0.75F, -1.0F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F)), PartPose.offset(-2.0F, -0.2F, 0.0F));

        PartDefinition meter5 = rot16.addOrReplaceChild("meter5", CubeListBuilder.create().texOffs(248, 12).addBox(-0.95F, -1.65F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F))
                .texOffs(244, 7).addBox(-0.95F, -1.6F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(245, 18).addBox(-1.2F, -1.7F, -1.25F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-9.2F, 0.0F, -3.95F, 0.0F, 0.48F, 0.0F));

        PartDefinition cube_r366 = meter5.addOrReplaceChild("cube_r366", CubeListBuilder.create().texOffs(248, 41).addBox(-1.0F, -0.75F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

        PartDefinition bone105 = meter5.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(155, 0).addBox(-0.75F, 0.0F, -0.25F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -1.6F, 0.0F, 0.0F, -0.48F, 0.0F));

        PartDefinition mustard = rot16.addOrReplaceChild("mustard", CubeListBuilder.create().texOffs(154, 254).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.09F))
                .texOffs(155, 238).addBox(-0.5F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offset(-1.95F, -1.0F, -7.75F));

        PartDefinition cube_r367 = mustard.addOrReplaceChild("cube_r367", CubeListBuilder.create().texOffs(135, 252).addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition mustardbutton = mustard.addOrReplaceChild("mustardbutton", CubeListBuilder.create().texOffs(154, 253).addBox(-0.5F, -1.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.8F, 0.0F));

        PartDefinition ketchup = rot16.addOrReplaceChild("ketchup", CubeListBuilder.create(), PartPose.offset(-1.95F, -0.75F, 7.75F));

        PartDefinition cube_r368 = ketchup.addOrReplaceChild("cube_r368", CubeListBuilder.create().texOffs(135, 252).addBox(-0.5F, -0.25F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition ketchupbutton = ketchup.addOrReplaceChild("ketchupbutton", CubeListBuilder.create().texOffs(155, 248).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -0.55F, 0.0F));

        PartDefinition t_switch = rot16.addOrReplaceChild("t_switch", CubeListBuilder.create().texOffs(94, 174).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
                .texOffs(96, 191).addBox(-1.0F, -4.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(96, 199).addBox(-1.0F, -5.0F, -0.95F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.31F))
                .texOffs(104, 202).addBox(-1.0F, -5.25F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(95, 185).addBox(-1.05F, -3.75F, -0.75F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.75F, 1.5F, 9.0F, 0.0F, 0.0F, 1.0036F));

        PartDefinition bone106 = t_switch.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(104, 202).addBox(-0.5F, -1.5F, -0.95F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.25F))
                .texOffs(96, 199).addBox(-0.5F, -1.25F, -0.95F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.31F)), PartPose.offsetAndRotation(-0.75F, -3.35F, 0.5F, 0.0F, 0.0F, -1.5708F));

        PartDefinition middle_1 = controls.addOrReplaceChild("middle_1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rot10 = middle_1.addOrReplaceChild("rot10", CubeListBuilder.create(), PartPose.offsetAndRotation(21.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition coil = rot10.addOrReplaceChild("coil", CubeListBuilder.create().texOffs(17, 66).addBox(-5.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 89).addBox(1.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 89).addBox(-3.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(107, 30).addBox(-5.0F, -1.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.5F, -0.5F, 0.0F));

        PartDefinition bone172 = coil.addOrReplaceChild("bone172", CubeListBuilder.create().texOffs(111, 109).addBox(-3.5F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone174 = bone172.addOrReplaceChild("bone174", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone173 = bone172.addOrReplaceChild("bone173", CubeListBuilder.create().texOffs(114, 73).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.08F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition coilhandel = coil.addOrReplaceChild("coilhandel", CubeListBuilder.create().texOffs(186, 254).addBox(-0.4F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(17, 66).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offset(2.5F, -1.0F, 0.0F));

        PartDefinition middle_2 = controls.addOrReplaceChild("middle_2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition rot11 = middle_2.addOrReplaceChild("rot11", CubeListBuilder.create(), PartPose.offsetAndRotation(21.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition coil2 = rot11.addOrReplaceChild("coil2", CubeListBuilder.create().texOffs(111, 109).addBox(-3.5F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(17, 66).addBox(-5.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 89).addBox(1.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 89).addBox(-3.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(107, 30).addBox(-5.0F, -1.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.5F, -0.5F, 0.0F));

        PartDefinition bone214 = coil2.addOrReplaceChild("bone214", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.08F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

        PartDefinition bone213 = coil2.addOrReplaceChild("bone213", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition coilhandel5 = coil2.addOrReplaceChild("coilhandel5", CubeListBuilder.create().texOffs(17, 66).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(186, 254).addBox(-0.4F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(2.5F, -1.0F, 0.0F));

        PartDefinition middle_3 = controls.addOrReplaceChild("middle_3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition rot12 = middle_3.addOrReplaceChild("rot12", CubeListBuilder.create(), PartPose.offsetAndRotation(21.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition coil3 = rot12.addOrReplaceChild("coil3", CubeListBuilder.create().texOffs(111, 109).addBox(-3.5F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(17, 66).addBox(-5.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 89).addBox(1.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 89).addBox(-3.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(107, 30).addBox(-5.0F, -1.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.5F, -0.5F, 0.0F));

        PartDefinition bone212 = coil3.addOrReplaceChild("bone212", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone211 = coil3.addOrReplaceChild("bone211", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.08F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

        PartDefinition coilhandel4 = coil3.addOrReplaceChild("coilhandel4", CubeListBuilder.create().texOffs(17, 66).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(186, 254).addBox(-0.4F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(2.5F, -1.0F, 0.0F));

        PartDefinition middle_4 = controls.addOrReplaceChild("middle_4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition rot13 = middle_4.addOrReplaceChild("rot13", CubeListBuilder.create(), PartPose.offsetAndRotation(21.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition coil4 = rot13.addOrReplaceChild("coil4", CubeListBuilder.create().texOffs(111, 109).addBox(-3.5F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(17, 66).addBox(-5.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 89).addBox(1.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 89).addBox(-3.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(107, 30).addBox(-5.0F, -1.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.5F, -0.5F, 0.0F));

        PartDefinition bone177 = coil4.addOrReplaceChild("bone177", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.08F)), PartPose.offset(-2.0F, -1.0F, 0.0F));

        PartDefinition bone204 = coil4.addOrReplaceChild("bone204", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition coilhandel3 = coil4.addOrReplaceChild("coilhandel3", CubeListBuilder.create().texOffs(17, 66).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(186, 254).addBox(-0.4F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(2.5F, -1.0F, 0.0F));

        PartDefinition middle_5 = controls.addOrReplaceChild("middle_5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition rot14 = middle_5.addOrReplaceChild("rot14", CubeListBuilder.create(), PartPose.offsetAndRotation(21.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition middle_6 = controls.addOrReplaceChild("middle_6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition rot15 = middle_6.addOrReplaceChild("rot15", CubeListBuilder.create(), PartPose.offsetAndRotation(21.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.48F));

        PartDefinition coil6 = rot15.addOrReplaceChild("coil6", CubeListBuilder.create().texOffs(111, 109).addBox(-3.5F, -2.0F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                .texOffs(17, 66).addBox(-5.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(0, 89).addBox(1.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(0, 89).addBox(-3.5F, -2.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(107, 30).addBox(-5.0F, -1.5F, -0.5F, 8.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-1.5F, -0.5F, 0.0F));

        PartDefinition bone176 = coil6.addOrReplaceChild("bone176", CubeListBuilder.create().texOffs(114, 73).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone175 = coil6.addOrReplaceChild("bone175", CubeListBuilder.create().texOffs(114, 73).addBox(-3.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.08F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition coilhandel2 = coil6.addOrReplaceChild("coilhandel2", CubeListBuilder.create().texOffs(17, 66).addBox(-0.5F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(186, 254).addBox(-0.4F, -1.0F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(2.5F, -1.0F, 0.0F));

        PartDefinition column = copper.addOrReplaceChild("column", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition wood = column.addOrReplaceChild("wood", CubeListBuilder.create(), PartPose.offsetAndRotation(3.25F, -24.0F, -5.25F, 0.0785F, -0.5236F, 0.0F));

        PartDefinition bone = wood.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -87.9938F, -5.8931F, 7.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0981F, 31.0F, 5.8301F));

        PartDefinition bone28 = wood.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(17, 0).addBox(-0.6359F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone44 = wood.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(17, 0).addBox(-3.3641F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, -1.0472F, 0.0F));

        PartDefinition wood2 = column.addOrReplaceChild("wood2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.25F, -24.0F, 5.25F, -3.0631F, 0.5236F, -3.1416F));

        PartDefinition bone47 = wood2.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -87.9938F, -5.8931F, 7.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0981F, 31.0F, 5.8301F));

        PartDefinition bone48 = wood2.addOrReplaceChild("bone48", CubeListBuilder.create().texOffs(17, 0).addBox(-0.6359F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone49 = wood2.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(17, 0).addBox(-3.3641F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rings = column.addOrReplaceChild("rings", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone51 = rings.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(19, 165).addBox(4.3F, -77.0F, -2.5F, 0.0F, 64.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition bone52 = bone51.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(19, 165).addBox(4.3F, -77.0F, -2.5F, 0.0F, 64.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(19, 165).addBox(4.3F, -77.0F, -2.5F, 0.0F, 64.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(19, 165).addBox(4.3F, -77.0F, -2.5F, 0.0F, 64.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone55 = bone54.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(19, 165).addBox(4.3F, -77.0F, -2.5F, 0.0F, 64.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone56 = bone55.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(19, 165).addBox(4.3F, -77.0F, -2.5F, 0.0F, 64.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone109 = rings.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, 0.0F));

        PartDefinition bone110 = bone109.addOrReplaceChild("bone110", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone111 = bone110.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone112 = bone111.addOrReplaceChild("bone112", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone113 = bone112.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone114 = bone113.addOrReplaceChild("bone114", CubeListBuilder.create().texOffs(40, 111).addBox(4.15F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone57 = rings.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(99, 86).addBox(5.0F, -27.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

        PartDefinition bone58 = bone57.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(99, 86).addBox(5.0F, -27.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(99, 86).addBox(5.0F, -27.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone60 = bone59.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(99, 86).addBox(5.0F, -27.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone61 = bone60.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(99, 86).addBox(5.0F, -27.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone62 = bone61.addOrReplaceChild("bone62", CubeListBuilder.create().texOffs(99, 86).addBox(5.0F, -27.0F, -3.5F, 1.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone63 = rings.addOrReplaceChild("bone63", CubeListBuilder.create().texOffs(87, 90).addBox(5.9F, -27.0F, -4.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 0.0F));

        PartDefinition bone64 = bone63.addOrReplaceChild("bone64", CubeListBuilder.create().texOffs(87, 90).addBox(5.9F, -27.0F, -4.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone65 = bone64.addOrReplaceChild("bone65", CubeListBuilder.create().texOffs(87, 90).addBox(5.9F, -27.0F, -4.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone66 = bone65.addOrReplaceChild("bone66", CubeListBuilder.create().texOffs(87, 90).addBox(5.9F, -27.0F, -4.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone67 = bone66.addOrReplaceChild("bone67", CubeListBuilder.create().texOffs(87, 90).addBox(5.9F, -27.0F, -4.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone68 = bone67.addOrReplaceChild("bone68", CubeListBuilder.create().texOffs(87, 90).addBox(5.9F, -27.0F, -4.0F, 1.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone205 = rings.addOrReplaceChild("bone205", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -36.0F, 0.0F));

        PartDefinition bone206 = bone205.addOrReplaceChild("bone206", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone207 = bone206.addOrReplaceChild("bone207", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone208 = bone207.addOrReplaceChild("bone208", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone209 = bone208.addOrReplaceChild("bone209", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone210 = bone209.addOrReplaceChild("bone210", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone69 = rings.addOrReplaceChild("bone69", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -53.0F, 0.0F));

        PartDefinition bone70 = bone69.addOrReplaceChild("bone70", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone71 = bone70.addOrReplaceChild("bone71", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone72 = bone71.addOrReplaceChild("bone72", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone73 = bone72.addOrReplaceChild("bone73", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone74 = bone73.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone50 = rings.addOrReplaceChild("bone50", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -53.0F, 0.0F));

        PartDefinition bone79 = bone50.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone80 = bone79.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone81 = bone80.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone82 = bone81.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone83 = bone82.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone45 = rings.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 112).addBox(6.5F, -26.5F, -3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -19.0F, 0.0F));

        PartDefinition bone46 = bone45.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone75 = bone46.addOrReplaceChild("bone75", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 112).addBox(6.5F, -26.5F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone76 = bone75.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 112).addBox(6.5F, -26.5F, -3.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone77 = bone76.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone78 = bone77.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(74, 112).addBox(6.5F, -26.5F, 2.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor7 = column.addOrReplaceChild("rotor7", CubeListBuilder.create().texOffs(0, 89).addBox(-2.5F, -31.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(114, 67).addBox(-1.0F, -32.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
                .texOffs(33, 68).addBox(-0.5F, -43.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(62, 91).addBox(-0.5F, -25.5F, -0.5F, 1.0F, 22.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(106, 121).addBox(-0.5F, -45.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(113, 93).addBox(-1.5F, -35.75F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.2F))
                .texOffs(113, 93).addBox(-1.5F, -38.9F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(97, 40).addBox(-1.0F, -42.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(97, 40).addBox(-1.0F, -44.25F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(113, 93).addBox(-1.5F, -41.65F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 3.0F, 0.0F));

        PartDefinition toprotor = column.addOrReplaceChild("toprotor", CubeListBuilder.create().texOffs(0, 236).addBox(-2.0F, 4.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.6F))
                .texOffs(0, 208).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.6F))
                .texOffs(2, 248).addBox(-1.5F, -9.15F, -1.5F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.6F))
                .texOffs(26, 246).addBox(-2.5F, 11.15F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(48, 227).addBox(-1.0F, -18.15F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -61.85F, 0.0F));

        PartDefinition horn = column.addOrReplaceChild("horn", CubeListBuilder.create().texOffs(28, 108).addBox(0.0F, -2.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.01F))
                .texOffs(17, 63).addBox(4.3F, -5.9F, -0.9F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(101, 121).addBox(7.05F, -6.65F, 0.1F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(88, 89).addBox(4.3F, -6.9F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(40, 111).addBox(4.3F, -8.65F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(5.3083F, -27.0436F, -1.6007F, 0.1599F, 0.8252F, 0.2524F));

        PartDefinition cube_r369 = horn.addOrReplaceChild("cube_r369", CubeListBuilder.create().texOffs(64, 112).addBox(-0.6F, -2.75F, -1.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(100, 0).addBox(2.4F, -3.75F, -2.0F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(60, 72).addBox(5.5F, -5.75F, -4.0F, 0.0F, 9.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(4.8F, -7.9F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r370 = horn.addOrReplaceChild("cube_r370", CubeListBuilder.create().texOffs(62, 48).addBox(1.0F, 0.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(57, 48).addBox(0.0F, -2.0F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.4492F, -3.2736F, 0.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition cube_r371 = horn.addOrReplaceChild("cube_r371", CubeListBuilder.create().texOffs(92, 118).addBox(0.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.3F, 0.1F, 0.0F, 0.0F, 0.0F, -1.0036F));

        PartDefinition rack = column.addOrReplaceChild("rack", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone86 = rack.addOrReplaceChild("bone86", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone84 = bone86.addOrReplaceChild("bone84", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r372 = bone84.addOrReplaceChild("cube_r372", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -0.5F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -46.0F, 9.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r373 = bone84.addOrReplaceChild("cube_r373", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, 0.0F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -48.0F, 9.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition bone85 = bone86.addOrReplaceChild("bone85", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r374 = bone85.addOrReplaceChild("cube_r374", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -0.5F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -46.0F, 9.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r375 = bone85.addOrReplaceChild("cube_r375", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, 0.0F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -48.0F, 9.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition bone87 = bone86.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(72, 62).addBox(-12.0F, -46.0F, 20.75F, 11.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone88 = bone87.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(57, 45).addBox(-12.0F, -46.0F, 20.75F, 24.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(139, 15).addBox(-8.0F, -46.5F, 20.25F, 16.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone89 = bone88.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(72, 62).addBox(1.0F, -46.0F, 20.75F, 11.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone90 = rack.addOrReplaceChild("bone90", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone91 = bone90.addOrReplaceChild("bone91", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r376 = bone91.addOrReplaceChild("cube_r376", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -0.5F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -46.0F, 9.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r377 = bone91.addOrReplaceChild("cube_r377", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, 0.0F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -48.0F, 9.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition bone92 = bone90.addOrReplaceChild("bone92", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r378 = bone92.addOrReplaceChild("cube_r378", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, -0.5F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -46.0F, 9.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r379 = bone92.addOrReplaceChild("cube_r379", CubeListBuilder.create().texOffs(63, 30).addBox(0.0F, 0.0F, -0.25F, 0.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -48.0F, 9.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition bone93 = bone90.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(72, 62).addBox(-12.0F, -46.0F, 20.75F, 11.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone94 = bone93.addOrReplaceChild("bone94", CubeListBuilder.create().texOffs(57, 45).addBox(-12.0F, -46.0F, 20.75F, 24.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone95 = bone94.addOrReplaceChild("bone95", CubeListBuilder.create().texOffs(72, 62).addBox(1.0F, -46.0F, 20.75F, 11.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition scanner = rack.addOrReplaceChild("scanner", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition monitor = scanner.addOrReplaceChild("monitor", CubeListBuilder.create().texOffs(163, 183).addBox(-6.0F, -5.0F, -2.0F, 12.0F, 10.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(155, 160).addBox(-6.0F, -5.0F, -2.0F, 12.0F, 10.0F, 4.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -31.0F, 20.75F, -0.3491F, 0.0F, 0.0F));

        PartDefinition redstreaks = monitor.addOrReplaceChild("redstreaks", CubeListBuilder.create().texOffs(234, 60).addBox(-4.0F, -2.5F, 1.825F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition screenplanets = monitor.addOrReplaceChild("screenplanets", CubeListBuilder.create().texOffs(233, 141).addBox(-4.0F, -2.5F, 1.9F, 8.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition planettopleft = screenplanets.addOrReplaceChild("planettopleft", CubeListBuilder.create().texOffs(244, 117).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.0F, -1.0F, 1.9F));

        PartDefinition planetlowleft = screenplanets.addOrReplaceChild("planetlowleft", CubeListBuilder.create().texOffs(244, 117).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 2.5F, 1.9F));

        PartDefinition planettopright = screenplanets.addOrReplaceChild("planettopright", CubeListBuilder.create().texOffs(244, 117).addBox(-1.0F, -0.5F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, -1.5F, 1.9F));

        PartDefinition bigplant = screenplanets.addOrReplaceChild("bigplant", CubeListBuilder.create().texOffs(241, 133).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 1.9F));

        PartDefinition rack2 = scanner.addOrReplaceChild("rack2", CubeListBuilder.create().texOffs(220, 209).addBox(-8.0F, -40.0F, 20.0F, 16.0F, 14.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.75F));

        PartDefinition cube_r380 = rack2.addOrReplaceChild("cube_r380", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.25F, 19.9F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r381 = rack2.addOrReplaceChild("cube_r381", CubeListBuilder.create().texOffs(220, 231).addBox(-1.0F, -0.5F, 0.0F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -46.25F, 19.9F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r382 = rack2.addOrReplaceChild("cube_r382", CubeListBuilder.create().texOffs(220, 231).addBox(0.0F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -39.25F, 19.9F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r383 = rack2.addOrReplaceChild("cube_r383", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 8.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -39.25F, 19.9F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r384 = rack2.addOrReplaceChild("cube_r384", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -42.75F, 19.9F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r385 = rack2.addOrReplaceChild("cube_r385", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -42.75F, 19.9F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r386 = rack2.addOrReplaceChild("cube_r386", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 15.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.25F, 19.9F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r387 = rack2.addOrReplaceChild("cube_r387", CubeListBuilder.create().texOffs(220, 231).addBox(-7.0F, -0.5F, 0.0F, 15.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -44.25F, 19.9F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r388 = rack2.addOrReplaceChild("cube_r388", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.75F, 19.9F, 0.0F, 0.0F, 0.2182F));

        PartDefinition cube_r389 = rack2.addOrReplaceChild("cube_r389", CubeListBuilder.create().texOffs(220, 231).addBox(-8.0F, -0.5F, 0.0F, 16.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -40.75F, 19.9F, 0.0F, 0.0F, -0.2182F));

        PartDefinition column_extension = copper.addOrReplaceChild("column_extension", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition wood3 = column_extension.addOrReplaceChild("wood3", CubeListBuilder.create(), PartPose.offsetAndRotation(5.643F, -84.812F, -9.3948F, 0.0785F, -0.5236F, 0.0F));

        PartDefinition bone103 = wood3.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -87.9938F, -5.8931F, 7.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0981F, 31.0F, 5.8301F));

        PartDefinition bone104 = wood3.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(17, 0).addBox(-0.6359F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone107 = wood3.addOrReplaceChild("bone107", CubeListBuilder.create().texOffs(18, 0).addBox(-3.3641F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, -1.0472F, 0.0F));

        PartDefinition wood5 = column_extension.addOrReplaceChild("wood5", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.643F, -84.812F, 9.3948F, -3.0631F, 0.5236F, -3.1416F));

        PartDefinition bone108 = wood5.addOrReplaceChild("bone108", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -87.9938F, -5.8931F, 7.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.0981F, 31.0F, 5.8301F));

        PartDefinition bone130 = wood5.addOrReplaceChild("bone130", CubeListBuilder.create().texOffs(18, 0).addBox(-0.6359F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone131 = wood5.addOrReplaceChild("bone131", CubeListBuilder.create().texOffs(18, 0).addBox(-3.3641F, -87.9938F, -5.9715F, 4.0F, 61.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.0981F, 31.0F, 5.8301F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rings4 = column_extension.addOrReplaceChild("rings4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition glass2 = rings4.addOrReplaceChild("glass2", CubeListBuilder.create().texOffs(19, 165).addBox(4.3F, -117.0F, -2.5F, 0.0F, 40.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition bone154 = glass2.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(19, 165).addBox(4.3F, -117.0F, -2.5F, 0.0F, 40.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone156 = bone154.addOrReplaceChild("bone156", CubeListBuilder.create().texOffs(19, 165).addBox(4.3F, -117.0F, -2.5F, 0.0F, 40.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone161 = bone156.addOrReplaceChild("bone161", CubeListBuilder.create().texOffs(19, 165).addBox(4.3F, -117.0F, -2.5F, 0.0F, 40.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone164 = bone161.addOrReplaceChild("bone164", CubeListBuilder.create().texOffs(19, 165).addBox(4.3F, -117.0F, -2.5F, 0.0F, 40.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone165 = bone164.addOrReplaceChild("bone165", CubeListBuilder.create().texOffs(19, 165).addBox(4.3F, -117.0F, -2.5F, 0.0F, 40.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone166 = rings4.addOrReplaceChild("bone166", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -70.0F, 0.0F));

        PartDefinition bone167 = bone166.addOrReplaceChild("bone167", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone168 = bone167.addOrReplaceChild("bone168", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone169 = bone168.addOrReplaceChild("bone169", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone170 = bone169.addOrReplaceChild("bone170", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone171 = bone170.addOrReplaceChild("bone171", CubeListBuilder.create().texOffs(41, 84).addBox(6.75F, -27.0F, -4.5F, 1.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone184 = rings4.addOrReplaceChild("bone184", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -70.0F, 0.0F));

        PartDefinition bone185 = bone184.addOrReplaceChild("bone185", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone186 = bone185.addOrReplaceChild("bone186", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone187 = bone186.addOrReplaceChild("bone187", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone188 = bone187.addOrReplaceChild("bone188", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone189 = bone188.addOrReplaceChild("bone189", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(63, 12).addBox(4.2F, -26.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone178 = rings4.addOrReplaceChild("bone178", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -94.0F, 0.0F));

        PartDefinition bone179 = bone178.addOrReplaceChild("bone179", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone180 = bone179.addOrReplaceChild("bone180", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone181 = bone180.addOrReplaceChild("bone181", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone182 = bone181.addOrReplaceChild("bone182", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone183 = bone182.addOrReplaceChild("bone183", CubeListBuilder.create().texOffs(72, 48).addBox(8.5F, -27.0F, -5.5F, 1.0F, 2.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone190 = rings4.addOrReplaceChild("bone190", CubeListBuilder.create().texOffs(388, 138).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(388, 218).addBox(4.2F, -26.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -79.0F, 0.0F));

        PartDefinition bone191 = bone190.addOrReplaceChild("bone191", CubeListBuilder.create().texOffs(388, 138).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(388, 218).addBox(4.2F, -26.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone192 = bone191.addOrReplaceChild("bone192", CubeListBuilder.create().texOffs(388, 138).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(388, 218).addBox(4.2F, -26.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone193 = bone192.addOrReplaceChild("bone193", CubeListBuilder.create().texOffs(388, 138).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(388, 218).addBox(4.2F, -26.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone194 = bone193.addOrReplaceChild("bone194", CubeListBuilder.create().texOffs(388, 138).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(388, 218).addBox(4.2F, -26.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone195 = bone194.addOrReplaceChild("bone195", CubeListBuilder.create().texOffs(388, 138).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(388, 218).addBox(4.2F, -26.5F, 0.0F, 4.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone196 = rings4.addOrReplaceChild("bone196", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(59, 12).addBox(4.2F, -26.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -94.0F, 0.0F));

        PartDefinition bone197 = bone196.addOrReplaceChild("bone197", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(59, 12).addBox(4.2F, -26.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone198 = bone197.addOrReplaceChild("bone198", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(59, 12).addBox(4.2F, -26.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone199 = bone198.addOrReplaceChild("bone199", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(59, 12).addBox(4.2F, -26.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone200 = bone199.addOrReplaceChild("bone200", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(59, 12).addBox(4.2F, -26.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone201 = bone200.addOrReplaceChild("bone201", CubeListBuilder.create().texOffs(109, 80).addBox(4.2F, -27.0F, -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(59, 12).addBox(4.2F, -26.5F, 0.0F, 5.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition desktop7 = column_extension.addOrReplaceChild("desktop7", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition panels13 = desktop7.addOrReplaceChild("panels13", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition rot17 = panels13.addOrReplaceChild("rot17", CubeListBuilder.create(), PartPose.offsetAndRotation(19.25F, -14.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition bone202 = rot17.addOrReplaceChild("bone202", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, 0.0F, 6.4F, 0.0F, -0.48F, 0.0F));

        PartDefinition bone203 = bone202.addOrReplaceChild("bone203", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.583F, -0.5072F, -0.6219F, 0.0F, 0.48F, 0.0436F));

        PartDefinition cube_r390 = bone203.addOrReplaceChild("cube_r390", CubeListBuilder.create().texOffs(128, 240).addBox(-63.0F, -1.9F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
                .texOffs(128, 240).addBox(-61.8F, -1.9F, -1.0F, 62.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(-36.8385F, -49.222F, -7.078F, 0.5323F, 0.0F, 1.0341F));

        PartDefinition top2 = column_extension.addOrReplaceChild("top2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -91.72F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r391 = top2.addOrReplaceChild("cube_r391", CubeListBuilder.create().texOffs(31, 111).addBox(-1.0F, -23.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(31, 111).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 227).addBox(-1.0F, 7.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 227).addBox(-1.0F, 0.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(48, 227).addBox(-1.0F, 17.5F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(26, 246).addBox(-2.5F, -8.1F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.9F))
                .texOffs(49, 184).addBox(-3.0F, 4.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.1F))
                .texOffs(49, 184).addBox(-3.0F, 12.925F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -4.78F, 0.0F, 0.0F, 0.5236F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        matrices.pushPose();
        matrices.mulPose(Axis.YN.rotationDegrees(150f));

        copper.render(matrices, vertexConsumer, light, overlay, color);
        matrices.popPose();
    }

    @Override
    protected void applyRootTransform(PoseStack matrices) {
        matrices.translate(0.5f, -1.52f, -0.5f);
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = this.controlDelta();
        matrices.pushPose();
        this.applyRootTransform(matrices);

        // Fuel Gauge
        float fuelTarget = (float) ((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 3f);
        this.copper.getChild("pillars").getChild("pillars2").getChild("pillars3").getChild("pillars4").getChild("bone121").getChild("fuel_gauge").yRot = getAngle(console, "fuel", fuelTarget, delta);

        // Direction
        float directionTargetDegrees = tardis.travel().destination().getRotation() * 22.5f;
        float directionAngle = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);
        this.copper.getChild("pillars").getChild("pillars2").getChild("pillars3").getChild("pillars4").getChild("pillars5").getChild("bone126").xRot = -directionAngle;

        // Handbrake
        float handbrakeTarget = tardis.travel().handbrake() ? -1.0f : 0f;
        this.copper.getChild("pillars").getChild("pillars2").getChild("pillars3").getChild("handbrake").xRot = getAngle(console, "handbrake", handbrakeTarget, delta);

        // Throttle
        ModelPart throttle1 = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("bone29");
        ModelPart throttle2 = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("bone30");

        ModelPart lights = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("lights2").getChild("light");
        ModelPart lights2 = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("lights2").getChild("light2");
        ModelPart lights3 = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("lights2").getChild("light3");
        ModelPart lights4 = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("lights2").getChild("light4");
        ModelPart lights5 = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("flightlever").getChild("lights2").getChild("light5");

        float speedPercent = tardis.travel().speed() / (float) tardis.travel().maxSpeed().get();
        float throttleTarget = speedPercent * 2f;
        float clampedSpeedAmount = Math.max(0f, Math.min(speedPercent, 5f)) * 5f;

        float throttleAngle = getAngle(console, "throttle", throttleTarget, delta);
        throttle1.zRot = throttleAngle - 1f;
        throttle2.zRot = throttleAngle - 1f;

        lights.visible = clampedSpeedAmount >= 5f;
        lights2.visible = clampedSpeedAmount >= 4f;
        lights3.visible = clampedSpeedAmount >= 3f;
        lights4.visible = clampedSpeedAmount >= 2f;
        lights5.visible = clampedSpeedAmount >= 1f;

        // Cloak
        ModelPart cloak = this.copper.getChild("desktop").getChild("desktop2").getChild("desktop3").getChild("desktop4").getChild("desktop5").getChild("desktop6").getChild("panels12").getChild("rot6").getChild("bone150");
        float cloakTarget = tardis.cloak().cloaked().get() ? 2f : 0f;
        cloak.zRot = getAngle(console, "cloak", cloakTarget, delta);

        // Power
        ModelPart power = this.copper.getChild("desktop").getChild("desktop2").getChild("desktop3").getChild("desktop4").getChild("desktop5").getChild("desktop6").getChild("panels12").getChild("rot6").getChild("lever9").getChild("blackredlever2").getChild("redleverleft2");
        ModelPart power1 = this.copper.getChild("desktop").getChild("desktop2").getChild("desktop3").getChild("desktop4").getChild("desktop5").getChild("desktop6").getChild("panels12").getChild("rot6").getChild("lever9").getChild("blackredlever2").getChild("redleverright2");
        float powerTarget = tardis.fuel().hasPower() ? 0f : -1.0f;
        float powerAngle = getAngle(console, "power", powerTarget, delta) + 1f;
        power.zRot = powerAngle;
        power1.zRot = powerAngle;

        // Wibbly Lever (Siege)
        ModelPart wibblyLever = this.copper.getChild("controls").getChild("panel_3").getChild("rot9").getChild("wibblylever2").getChild("wibblylever");
        float siegeTarget = tardis.siege().isActive() ? -2.0f : 0f;
        wibblyLever.zRot = getAngle(console, "siege", siegeTarget, delta);

        // Security Control
        ModelPart securityControl = this.copper.getChild("controls").getChild("panel_3").getChild("rot9").getChild("lever10").getChild("bone97");
        float securityTarget = tardis.stats().security().get() ? 0.75f : 0f;
        securityControl.zRot = getAngle(console, "security", securityTarget, delta);

        // Stabilisers (Autopilot)
        ModelPart stabilisers = this.copper.getChild("controls").getChild("panel_3").getChild("rot9").getChild("stabilizers").getChild("bone25");
        float stabiliserTarget = tardis.travel().autopilot() ? -0.8f : 0f;
        stabilisers.y = getAngle(console, "stabilisers", stabiliserTarget, delta);

        // Door Control
        ModelPart doorControl = this.copper.getChild("controls").getChild("panel_2").getChild("rot8").getChild("crank").getChild("bone36");
        float doorControlTarget = 0f;
        if (tardis.door().isRightOpen()) {
            doorControlTarget = -1.0f;
        } else if (tardis.door().isLeftOpen()) {
            doorControlTarget = -0.5f;
        }
        doorControl.xRot = getAngle(console, "door_control", doorControlTarget, delta);

        // Door Lock
        ModelPart doorLock = this.copper.getChild("controls").getChild("panel_4").getChild("rot16").getChild("crank3").getChild("bone101");
        float doorLockTarget = tardis.door().locked() ? 1.575f : 0f;
        doorLock.yRot = getAngle(console, "door_lock", doorLockTarget, delta);

        // Shields
        ModelPart shields = this.copper.getChild("controls").getChild("panel_4").getChild("rot16").getChild("t_switch").getChild("bone106");
        float shieldYTarget = tardis.shields().shielded().get() ? -2.5f : shields.storePose().y;
        float shieldZTarget = tardis.shields().visuallyShielded().get() ? 0 : shields.storePose().z;

        shields.y = getAngle(console, "shields_y", shieldYTarget, delta);
        shields.z = getAngle(console, "shields_z", shieldZTarget, delta);

        // Antigravs
        ModelPart antigravs = this.copper.getChild("controls").getChild("panel_4").getChild("rot16").getChild("lever8").getChild("blackredlever").getChild("redleverleft3");
        ModelPart antigravs1 = this.copper.getChild("controls").getChild("panel_4").getChild("rot16").getChild("lever8").getChild("blackredlever").getChild("redleverright3");
        float antigravTarget = tardis.travel().antigravs().get() ? 0f : -1.0f;
        float antigravsAngle = getAngle(console, "antigravs", antigravTarget, delta) + 1f;
        antigravs.zRot = antigravsAngle;
        antigravs1.zRot = antigravsAngle;

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }


    @Override
    public void renderMonitorText(Tardis tardis, ConsoleBlockEntity entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        super.renderMonitorText(tardis, entity, matrices, vertexConsumers, light, overlay);

        Minecraft client = Minecraft.getInstance();
        Font renderer = client.font;
        TravelHandler travel = tardis.travel();
        CachedDirectedGlobalPos abpp = travel.isLanded() || travel.getState() == TravelHandlerBase.State.MAT
                ? travel.position()
                : travel.getProgress();

        BlockPos abppPos = abpp.getPos();
        matrices.pushPose();
        // TODO dont forget to add variant.getConsoleTextPosition()!
        matrices.translate(-0.58, 1.28, 1.5f);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.005f, 0.005f, 0.005f);
        matrices.mulPose(Axis.ZN.rotationDegrees(22.5f));
        matrices.mulPose(Axis.YN.rotationDegrees(-90f));
        matrices.translate(-245f, -222, -2.8f);
        String positionPosText = abppPos.getX() + ", " + abppPos.getY() + ", " + abppPos.getZ();
        Component positionDimensionText = WorldUtil.worldText(abpp.getDimension());
        String positionDirectionText = DirectionControl.rotationToDirection(abpp.getRotation()).toUpperCase();
        int y = 40;
        renderer.drawInBatch(Component.nullToEmpty("\uD83D\uDCCD").getVisualOrderText(), 0, y, 0x00EEFF, true,
                matrices.last().pose(), vertexConsumers,
                Font.DisplayMode.POLYGON_OFFSET, 0, 0xF000F0);
        renderer.drawInBatch(Component.nullToEmpty(positionPosText).getVisualOrderText(), 8, y, 0xFFFFFF, true,
                matrices.last().pose(), vertexConsumers,
                Font.DisplayMode.POLYGON_OFFSET, 0, 0xF000F0);
        renderer.drawInBatch(positionDimensionText.getVisualOrderText(), 8, y + 8, 0xFFFFFF, true,
                matrices.last().pose(), vertexConsumers,
                Font.DisplayMode.POLYGON_OFFSET, 0, 0xF000F0);
        renderer.drawInBatch(Component.nullToEmpty(positionDirectionText).getVisualOrderText(), 8, y + 16, 0xFFFFFF, true,
                matrices.last().pose(), vertexConsumers,
                Font.DisplayMode.POLYGON_OFFSET, 0, 0xF000F0);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-0.45, 1.8, 0.66f);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.007f, 0.007f, 0.007f);
        matrices.mulPose(Axis.ZN.rotationDegrees(22.5f));
        matrices.mulPose(Axis.YN.rotationDegrees(-90f));
        String progressText = tardis.travel().getState() == TravelHandlerBase.State.LANDED
                ? "⏳: 0%"
                : "⏳: " + tardis.travel().getDurationAsPercentage() + "%";
        matrices.translate(-10, -47, -48.5f);
        renderer.drawInBatch(Component.nullToEmpty(progressText).getVisualOrderText(),
                -renderer.width(progressText) / 2, 0, 0xffffff, true,
                matrices.last().pose(), vertexConsumers,
                Font.DisplayMode.POLYGON_OFFSET, 0, 0xF000F0);
        matrices.popPose();

    }

    @Override
    public ModelPart root() {
        return copper;
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        if (state.equals(TravelHandlerBase.State.LANDED)) {
            return CopperAnimations.COPPER_IDLE;
        }

        return CopperAnimations.COPPER_FLIGHT;
    }
}