package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.client.animation.console.crystalline.CrystallineAnimations;
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

public class CrystallineConsoleModel extends SimpleConsoleModel {
    private final ModelPart console;
    public CrystallineConsoleModel(ModelPart root) {
        this.console = root.getChild("console");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition console = modelPartData.addOrReplaceChild("console", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition pannel7 = console.addOrReplaceChild("pannel7", CubeListBuilder.create().texOffs(0, 184).addBox(5.5F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(186, 16).addBox(7.3F, -24.5F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
                .texOffs(53, 211).addBox(6.3189F, -24.7F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(122, 185).addBox(5.1F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r1 = pannel7.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(189, 211).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, 4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r2 = pannel7.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(147, 178).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition pillars56 = pannel7.addOrReplaceChild("pillars56", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition pillars57 = pillars56.addOrReplaceChild("pillars57", CubeListBuilder.create().texOffs(130, 102).addBox(-0.999F, -24.7252F, -11.2925F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r3 = pillars57.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(174, 213).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.1842F, -9.915F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r4 = pillars57.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(35, 93).addBox(-13.3F, -15.05F, -20.45F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(4.4261F, 0.4143F, -11.6486F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r5 = pillars57.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(216, 27).addBox(-0.1F, -0.2F, -10.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(216, 27).addBox(-1.0F, -0.2F, -10.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(137, 213).addBox(-1.0F, -0.2F, -9.8F, 2.0F, 1.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.001F, -19.3098F, -5.0285F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r6 = pillars57.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(25, 114).addBox(-1.0F, -0.5F, -1.5F, 0.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2279F, -24.3252F, -9.9853F, 0.0F, 0.0873F, 0.0F));

        PartDefinition cube_r7 = pillars57.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r8 = pillars57.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(84, 215).addBox(22.7F, -3.7873F, -41.3554F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-23.7F, 8.2565F, 12.0063F, -0.5672F, 0.0F, 0.0F));

        PartDefinition spinnio = pillars57.addOrReplaceChild("spinnio", CubeListBuilder.create().texOffs(183, 213).addBox(-1.1167F, -1.2167F, -0.1667F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(215, 132).addBox(-0.3167F, -0.0167F, -1.4667F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.65F))
                .texOffs(215, 132).addBox(-0.5667F, -0.7667F, -0.3667F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.23F)), PartPose.offsetAndRotation(-0.1085F, -16.2678F, -21.9433F, -0.3486F, 0.1757F, -0.0873F));

        PartDefinition pillars58 = pillars57.addOrReplaceChild("pillars58", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars59 = pillars58.addOrReplaceChild("pillars59", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars60 = pillars59.addOrReplaceChild("pillars60", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars61 = pillars56.addOrReplaceChild("pillars61", CubeListBuilder.create().texOffs(35, 89).addBox(-0.999F, -24.7252F, -10.2925F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 76).addBox(-1.0F, -14.6347F, -22.4402F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r9 = pillars61.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition pillars62 = pillars61.addOrReplaceChild("pillars62", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars63 = pillars62.addOrReplaceChild("pillars63", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars64 = pillars63.addOrReplaceChild("pillars64", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rim13 = pannel7.addOrReplaceChild("rim13", CubeListBuilder.create().texOffs(25, 128).addBox(18.5F, -14.3F, -5.5F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(173, 111).addBox(19.5F, -13.5F, -5.5F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, -2.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r10 = rim13.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, -5.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(171, 146).addBox(20.5F, -16.0F, -5.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r11 = rim13.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, 3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r12 = rim13.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(91, 207).addBox(19.7F, -15.8F, 1.2F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1607F, 2.3F, -1.8854F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r13 = rim13.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(206, 103).addBox(18.5F, -16.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r14 = rim13.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(205, 157).addBox(18.5F, -16.0F, -0.5F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim14 = pannel7.addOrReplaceChild("rim14", CubeListBuilder.create(), PartPose.offset(-11.2F, -11.4F, 0.0F));

        PartDefinition panels7 = pannel7.addOrReplaceChild("panels7", CubeListBuilder.create().texOffs(53, 199).addBox(11.2348F, -20.4107F, 1.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r15 = panels7.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(215, 132).addBox(-2.5775F, -0.875F, 14.5775F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.23F)), PartPose.offsetAndRotation(5.9965F, -17.0097F, 22.6784F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r16 = panels7.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(188, 216).addBox(5.8F, -22.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.2F, -0.8F, -0.4F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r17 = panels7.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(17.0806F, -17.8044F, -1.9F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r18 = panels7.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3F, 0.16F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(17.3341F, -18.3482F, -1.9F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r19 = panels7.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(193, 187).addBox(-1.2F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(16.8754F, -17.3277F, -1.9F, 1.5708F, 0.0F, -1.117F));

        PartDefinition cube_r20 = panels7.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3F, 0.16F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(14.8341F, -19.7482F, -0.8F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r21 = panels7.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(193, 187).addBox(-1.2F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(14.3754F, -18.7277F, -0.8F, 1.5708F, 0.0F, -1.117F));

        PartDefinition cube_r22 = panels7.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(209, 215).addBox(-0.5F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(14.5444F, -19.0902F, 0.3F, 1.5708F, 0.0F, 0.4363F));

        PartDefinition cube_r23 = panels7.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(14.5806F, -19.2044F, -0.8F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r24 = panels7.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(188, 216).addBox(5.8F, -22.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, -2.2F, 0.7F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r25 = panels7.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(36, 195).addBox(7.8F, -23.1F, 1.05F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(177, 139).addBox(7.8F, -21.8F, 1.05F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(177, 139).addBox(3.8F, -22.2F, 1.05F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, -1.0F, 0.2F, 0.0F, 0.0F, 0.4363F));

        PartDefinition spinnio_r1 = panels7.addOrReplaceChild("spinnio_r1", CubeListBuilder.create().texOffs(117, 83).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(12.1348F, -20.9107F, 0.0F, 0.0F, 0.0F, 1.2217F));

        PartDefinition cube_r26 = panels7.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(209, 19).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.6613F, -19.9284F, 3.4F, 0.2967F, 0.0F, 0.5934F));

        PartDefinition cube_r27 = panels7.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(0, 212).addBox(-0.5F, 0.0F, -3.0F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.4603F, -20.2706F, -4.0F, 0.3142F, 0.0F, 0.4363F));

        PartDefinition cube_r28 = panels7.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(209, 14).addBox(-2.0F, 0.1F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(10.3893F, -22.8843F, 0.25F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r29 = panels7.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(13, 213).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(9.2836F, -22.1862F, 0.25F, 0.0F, 0.7854F, 0.4363F));

        PartDefinition cube_r30 = panels7.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(141, 185).addBox(-1.1F, -25.0F, -0.25F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.4F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r31 = panels7.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(0, 19).addBox(-5.2F, -22.2F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r32 = panels7.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(178, 92).addBox(-4.0F, 0.0F, -1.8F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.4953F, -16.9867F, -4.5F, 0.0523F, 0.784F, 0.4667F));

        PartDefinition cube_r33 = panels7.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(166, 185).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.6953F, -16.9867F, 4.5F, -0.0523F, -0.784F, 0.4667F));

        PartDefinition cube_r34 = panels7.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition handle = panels7.addOrReplaceChild("handle", CubeListBuilder.create(), PartPose.offset(13.1947F, -19.1215F, 1.75F));

        PartDefinition cube_r35 = handle.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(210, 43).addBox(3.8F, -22.0F, 1.05F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-13.1947F, 18.1215F, -1.55F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube1 = panels7.addOrReplaceChild("cube1", CubeListBuilder.create(), PartPose.offset(14.3331F, -18.6371F, -0.7F));

        PartDefinition cube1_r1 = cube1.addOrReplaceChild("cube1_r1", CubeListBuilder.create().texOffs(209, 215).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition cube2 = panels7.addOrReplaceChild("cube2", CubeListBuilder.create(), PartPose.offset(17.0444F, -17.6902F, -0.8F));

        PartDefinition cube2_r1 = cube2.addOrReplaceChild("cube2_r1", CubeListBuilder.create().texOffs(209, 215).addBox(-0.5F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.15F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.4363F));

        PartDefinition bone4 = panels7.addOrReplaceChild("bone4", CubeListBuilder.create(), PartPose.offset(16.8331F, -17.2371F, -2.0F));

        PartDefinition cube_r36 = bone4.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(209, 215).addBox(-0.5F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition power = panels7.addOrReplaceChild("power", CubeListBuilder.create(), PartPose.offset(15.0228F, -16.9847F, 10.9994F));

        PartDefinition cube_r37 = power.addOrReplaceChild("cube_r37", CubeListBuilder.create().texOffs(215, 132).addBox(-2.3275F, -0.125F, 13.4775F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.65F))
                .texOffs(183, 213).addBox(-3.1275F, -1.325F, 14.7775F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-9.0262F, -0.025F, 11.679F, 0.0F, 2.618F, 0.0F));

        PartDefinition under7 = pannel7.addOrReplaceChild("under7", CubeListBuilder.create().texOffs(0, 76).addBox(6.9F, -14.3F, -5.5F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r38 = under7.addOrReplaceChild("cube_r38", CubeListBuilder.create().texOffs(117, 76).addBox(9.5F, -16.0F, -5.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r39 = under7.addOrReplaceChild("cube_r39", CubeListBuilder.create().texOffs(138, 147).addBox(9.5F, -16.0F, 0.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition pannel2 = console.addOrReplaceChild("pannel2", CubeListBuilder.create().texOffs(0, 184).addBox(5.5F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(186, 16).addBox(7.3F, -24.5F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
                .texOffs(53, 211).addBox(6.3189F, -24.7F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(122, 185).addBox(5.1F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r40 = pannel2.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(189, 211).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, 4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r41 = pannel2.addOrReplaceChild("cube_r41", CubeListBuilder.create().texOffs(147, 178).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition pillars2 = pannel2.addOrReplaceChild("pillars2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition pillars3 = pillars2.addOrReplaceChild("pillars3", CubeListBuilder.create().texOffs(130, 102).addBox(-0.999F, -24.7252F, -11.2925F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(215, 187).addBox(-0.999F, -26.5252F, -9.2925F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(215, 191).addBox(-0.999F, -26.5252F, 8.2925F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r42 = pillars3.addOrReplaceChild("cube_r42", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r43 = pillars3.addOrReplaceChild("cube_r43", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition cube_r44 = pillars3.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(84, 215).addBox(21.0F, -3.5261F, -42.1375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.999F, 8.4485F, 12.8146F, -0.5672F, 0.0F, 0.0F));

        PartDefinition leveer = pillars3.addOrReplaceChild("leveer", CubeListBuilder.create(), PartPose.offset(0.001F, -26.5205F, -8.2399F));

        PartDefinition cube_r45 = leveer.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(216, 0).addBox(-1.0F, 0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(141, 121).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(52, 128).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.4547F, -1.5687F, 0.9599F, 0.0F, 0.0F));

        PartDefinition leveer2 = pillars3.addOrReplaceChild("leveer2", CubeListBuilder.create(), PartPose.offset(0.001F, -26.4752F, 8.2086F));

        PartDefinition cube_r46 = leveer2.addOrReplaceChild("cube_r46", CubeListBuilder.create().texOffs(216, 3).addBox(-1.0F, 0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(215, 127).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.5F, 1.6F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r47 = leveer2.addOrReplaceChild("cube_r47", CubeListBuilder.create().texOffs(148, 213).addBox(-1.0F, -3.25F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.5038F, 0.1665F, -0.9599F, 0.0F, 0.0F));

        PartDefinition pillars4 = pillars3.addOrReplaceChild("pillars4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars5 = pillars4.addOrReplaceChild("pillars5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars6 = pillars5.addOrReplaceChild("pillars6", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars7 = pillars2.addOrReplaceChild("pillars7", CubeListBuilder.create().texOffs(35, 89).addBox(-0.999F, -24.7252F, -10.2925F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(47, 76).addBox(-1.001F, -14.6268F, -22.4484F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 204).addBox(-1.0F, -1.8637F, -15.2598F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r48 = pillars7.addOrReplaceChild("cube_r48", CubeListBuilder.create().texOffs(66, 211).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4737F, -13.1113F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r49 = pillars7.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r50 = pillars7.addOrReplaceChild("cube_r50", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(200, 201).addBox(-1.0F, -8.6049F, -6.676F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition pillars8 = pillars7.addOrReplaceChild("pillars8", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars9 = pillars8.addOrReplaceChild("pillars9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars10 = pillars9.addOrReplaceChild("pillars10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone65 = pannel2.addOrReplaceChild("bone65", CubeListBuilder.create(), PartPose.offset(4.0F, 0.7F, 16.0F));

        PartDefinition cube_r51 = bone65.addOrReplaceChild("cube_r51", CubeListBuilder.create().texOffs(156, 199).addBox(-3.5F, -17.5F, -21.6F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r52 = bone65.addOrReplaceChild("cube_r52", CubeListBuilder.create().texOffs(113, 214).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.0577F, -17.5624F, -9.6158F, 1.5272F, 1.0472F, 0.0F));

        PartDefinition cube_r53 = bone65.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(113, 214).addBox(-0.5005F, 1.9782F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(104, 214).addBox(-0.5005F, 3.9782F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.002F)), PartPose.offsetAndRotation(9.4621F, -17.1933F, -11.1144F, 0.5232F, -0.0218F, -1.533F));

        PartDefinition cube_r54 = bone65.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(186, 26).addBox(-0.7691F, 5.5565F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(12.3888F, -22.7473F, -9.4247F, 0.2605F, -0.4595F, -0.5412F));

        PartDefinition cube_r55 = bone65.addOrReplaceChild("cube_r55", CubeListBuilder.create().texOffs(156, 199).addBox(19.6F, -17.5F, -3.5F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 0.0F, -16.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r56 = bone65.addOrReplaceChild("cube_r56", CubeListBuilder.create().texOffs(93, 215).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4333F, -9.6213F, -27.5081F, -1.5708F, 1.0472F, 0.0F));

        PartDefinition cube_r57 = bone65.addOrReplaceChild("cube_r57", CubeListBuilder.create().texOffs(75, 215).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.0456F, -9.9142F, -27.8617F, 0.7854F, 1.0472F, 0.0F));

        PartDefinition cube_r58 = bone65.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(75, 133).addBox(-23.0F, 0.5F, -8.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.6F, -9.0F, -42.5F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r59 = bone65.addOrReplaceChild("cube_r59", CubeListBuilder.create().texOffs(215, 119).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(6.0217F, -1.7808F, -15.4433F, -3.1416F, 0.1745F, -3.1416F));

        PartDefinition cube_r60 = bone65.addOrReplaceChild("cube_r60", CubeListBuilder.create().texOffs(85, 115).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.7056F, -8.1054F, -13.6504F, -2.7925F, 0.1745F, -3.1416F));

        PartDefinition cube_r61 = bone65.addOrReplaceChild("cube_r61", CubeListBuilder.create().texOffs(93, 215).mirror().addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(5.6332F, -9.1237F, -13.2395F, -1.9635F, 0.1745F, -3.1416F));

        PartDefinition cube_r62 = bone65.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(75, 215).mirror().addBox(-1.0F, -6.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(6.067F, -9.6213F, -15.7F, -1.5708F, 0.1745F, -3.1416F));

        PartDefinition cube_r63 = bone65.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(156, 199).addBox(19.6F, -17.5F, -3.5F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-20.0F, 0.0F, -18.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r64 = bone65.addOrReplaceChild("cube_r64", CubeListBuilder.create().texOffs(75, 133).mirror().addBox(-10.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0217F, -5.6213F, -11.4919F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r65 = bone65.addOrReplaceChild("cube_r65", CubeListBuilder.create().texOffs(165, 205).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-5.8347F, -9.6213F, -3.4919F, -1.5708F, 1.0472F, -3.1416F));

        PartDefinition cube_r66 = bone65.addOrReplaceChild("cube_r66", CubeListBuilder.create().texOffs(93, 215).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.5667F, -9.6213F, -4.4919F, 1.5708F, 1.0472F, 0.0F));

        PartDefinition cube_r67 = bone65.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(75, 215).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.001F)).mirror(false), PartPose.offsetAndRotation(-5.9544F, -9.9142F, -4.1383F, -0.7854F, 1.0472F, 0.0F));

        PartDefinition cube_r68 = bone65.addOrReplaceChild("cube_r68", CubeListBuilder.create().texOffs(75, 133).mirror().addBox(21.0F, 0.5F, 6.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-24.6F, -9.0F, 10.5F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r69 = bone65.addOrReplaceChild("cube_r69", CubeListBuilder.create().texOffs(156, 199).addBox(1.5F, -17.5F, 19.6F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.0F, 0.0F, -32.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r70 = bone65.addOrReplaceChild("cube_r70", CubeListBuilder.create().texOffs(215, 119).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-14.0217F, -1.7808F, -16.5567F, 3.1416F, 0.1745F, 3.1416F));

        PartDefinition cube_r71 = bone65.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(93, 215).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-13.6332F, -9.1237F, -18.7605F, 1.9635F, 0.1745F, 3.1416F));

        PartDefinition cube_r72 = bone65.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(85, 115).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.7056F, -8.1054F, -18.3496F, 2.7925F, 0.1745F, 3.1416F));

        PartDefinition cube_r73 = bone65.addOrReplaceChild("cube_r73", CubeListBuilder.create().texOffs(75, 215).addBox(-1.0F, -6.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-14.067F, -9.6213F, -16.3F, 1.5708F, 0.1745F, 3.1416F));

        PartDefinition cube_r74 = bone65.addOrReplaceChild("cube_r74", CubeListBuilder.create().texOffs(165, 205).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(-2.1653F, -9.6213F, -28.5081F, 1.5708F, 1.0472F, 3.1416F));

        PartDefinition cube_r75 = bone65.addOrReplaceChild("cube_r75", CubeListBuilder.create().texOffs(75, 133).addBox(8.0F, -3.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0217F, -5.6213F, -20.5081F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r76 = bone65.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(156, 199).addBox(-21.6F, -17.5F, 1.5F, 2.0F, 17.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, 0.0F, -14.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition rim2 = pannel2.addOrReplaceChild("rim2", CubeListBuilder.create().texOffs(25, 128).addBox(18.5F, -14.3F, -5.5F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(173, 111).addBox(19.5F, -13.5F, -5.5F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, -2.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r77 = rim2.addOrReplaceChild("cube_r77", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, -5.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(171, 146).addBox(19.7F, -15.8F, -3.8F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r78 = rim2.addOrReplaceChild("cube_r78", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, 3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(91, 207).addBox(20.5F, -16.0F, -0.5F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r79 = rim2.addOrReplaceChild("cube_r79", CubeListBuilder.create().texOffs(161, 47).addBox(18.6F, -12.6F, -4.5F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(206, 103).addBox(18.5F, -16.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r80 = rim2.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(205, 157).addBox(18.5F, -16.0F, -0.5F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim3 = pannel2.addOrReplaceChild("rim3", CubeListBuilder.create(), PartPose.offset(-11.2F, -11.4F, 0.0F));

        PartDefinition panels2 = pannel2.addOrReplaceChild("panels2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r81 = panels2.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(36, 190).addBox(0.0F, -25.2F, 2.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(162, 111).addBox(0.5F, -25.2F, -2.8F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.001F))
                .texOffs(36, 190).addBox(0.0F, -25.2F, -3.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(201, 40).addBox(-5.2F, -22.6F, 0.25F, 10.0F, 0.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(199, 74).addBox(-5.2F, -22.6F, -2.25F, 10.0F, 0.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(75, 142).addBox(8.3F, -22.6F, 0.75F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(25, 125).addBox(8.3F, -22.6F, -2.25F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(106, 125).addBox(4.8F, -22.6F, 0.75F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(148, 144).addBox(-5.2F, -22.6F, -0.25F, 16.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(0, 38).addBox(-5.2F, -22.2F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r82 = panels2.addOrReplaceChild("cube_r82", CubeListBuilder.create().texOffs(174, 205).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.8918F, -22.1745F, -0.2F, 0.0F, 0.0F, 1.2217F));

        PartDefinition cube_r83 = panels2.addOrReplaceChild("cube_r83", CubeListBuilder.create().texOffs(53, 207).addBox(-1.6F, -0.2F, -2.3F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(130, 98).addBox(2.8F, -0.8F, -1.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(166, 183).addBox(3.9F, -0.4F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 98).addBox(3.7F, -0.8F, -2.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(166, 183).addBox(2.1F, -0.4F, -2.8F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(130, 98).addBox(1.9F, -0.8F, -2.2F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(157, 98).addBox(-4.2F, -0.8F, -3.8F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(157, 98).addBox(-4.2F, -0.8F, -0.7F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(215, 195).addBox(-0.2F, -0.5F, -1.3F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(14.85F, -18.122F, 1.75F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r84 = panels2.addOrReplaceChild("cube_r84", CubeListBuilder.create().texOffs(166, 183).addBox(0.4F, 0.0F, -0.6F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.1911F, -17.0054F, 0.45F, 0.0F, -1.5708F, 0.4363F));

        PartDefinition cube_r85 = panels2.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(205, 83).addBox(-2.5F, 0.0F, -1.9F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(17.1576F, -16.5975F, 3.5F, 0.0F, -0.3927F, 0.3927F));

        PartDefinition cube_r86 = panels2.addOrReplaceChild("cube_r86", CubeListBuilder.create().texOffs(205, 77).addBox(-2.5F, 0.0F, -3.1F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(17.1576F, -16.5975F, -3.5F, 0.0F, 0.3927F, 0.3927F));

        PartDefinition cube_r87 = panels2.addOrReplaceChild("cube_r87", CubeListBuilder.create().texOffs(69, 57).addBox(-1.0F, -21.5F, -9.0F, 13.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition under2 = pannel2.addOrReplaceChild("under2", CubeListBuilder.create().texOffs(0, 76).addBox(7.4572F, -14.3F, -4.4278F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r88 = under2.addOrReplaceChild("cube_r88", CubeListBuilder.create().texOffs(117, 76).addBox(9.5F, -16.0F, -5.4F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r89 = under2.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(0, 106).addBox(9.5F, -16.0F, 0.7F, 11.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition pannel3 = console.addOrReplaceChild("pannel3", CubeListBuilder.create().texOffs(0, 184).addBox(5.5F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(186, 16).addBox(7.3F, -24.5F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
                .texOffs(53, 211).addBox(6.3189F, -24.7F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(122, 185).addBox(5.1F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r90 = pannel3.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(215, 132).addBox(5.375F, -5.975F, 13.15F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.23F))
                .texOffs(183, 213).addBox(4.825F, -6.425F, 13.35F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(215, 132).addBox(5.625F, -5.225F, 12.05F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(20.4881F, -17.0097F, 5.6734F, 0.0F, -2.618F, 0.0F));

        PartDefinition cube_r91 = pannel3.addOrReplaceChild("cube_r91", CubeListBuilder.create().texOffs(189, 211).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, 4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r92 = pannel3.addOrReplaceChild("cube_r92", CubeListBuilder.create().texOffs(147, 178).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition pillars11 = pannel3.addOrReplaceChild("pillars11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition pillars12 = pillars11.addOrReplaceChild("pillars12", CubeListBuilder.create().texOffs(130, 102).addBox(-0.999F, -24.7252F, -11.2925F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(207, 207).addBox(-0.449F, -25.9252F, -19.9925F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(207, 207).mirror().addBox(-0.449F, -26.6752F, 12.7425F, 1.0F, 1.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 204).addBox(-1.0F, -1.8637F, -15.2598F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r93 = pillars12.addOrReplaceChild("cube_r93", CubeListBuilder.create().texOffs(66, 211).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4737F, -13.1113F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r94 = pillars12.addOrReplaceChild("cube_r94", CubeListBuilder.create().texOffs(128, 213).addBox(0.0F, -1.7049F, -0.124F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.001F, -24.7203F, 11.1165F, 0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r95 = pillars12.addOrReplaceChild("cube_r95", CubeListBuilder.create().texOffs(119, 213).addBox(0.0F, -1.7049F, -3.376F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.001F, -24.7203F, -11.6165F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r96 = pillars12.addOrReplaceChild("cube_r96", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r97 = pillars12.addOrReplaceChild("cube_r97", CubeListBuilder.create().texOffs(84, 215).addBox(21.0F, -3.5261F, -42.1375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.999F, 8.4485F, 12.8146F, -0.5672F, 0.0F, 0.0F));

        PartDefinition pillars13 = pillars12.addOrReplaceChild("pillars13", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars14 = pillars13.addOrReplaceChild("pillars14", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars15 = pillars14.addOrReplaceChild("pillars15", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars16 = pillars11.addOrReplaceChild("pillars16", CubeListBuilder.create().texOffs(35, 89).addBox(-0.999F, -24.7252F, -10.2925F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 204).addBox(-1.0F, -1.8637F, -15.2598F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r98 = pillars16.addOrReplaceChild("cube_r98", CubeListBuilder.create().texOffs(66, 211).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4737F, -13.1113F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r99 = pillars16.addOrReplaceChild("cube_r99", CubeListBuilder.create().texOffs(174, 213).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.1842F, -9.915F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r100 = pillars16.addOrReplaceChild("cube_r100", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r101 = pillars16.addOrReplaceChild("cube_r101", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(200, 201).addBox(-1.0F, -8.6049F, -6.676F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition pillars17 = pillars16.addOrReplaceChild("pillars17", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars18 = pillars17.addOrReplaceChild("pillars18", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars19 = pillars18.addOrReplaceChild("pillars19", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rim4 = pannel3.addOrReplaceChild("rim4", CubeListBuilder.create().texOffs(25, 128).addBox(18.5F, -14.3F, -5.5F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(173, 111).addBox(19.5F, -13.5F, -5.5F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, -2.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r102 = rim4.addOrReplaceChild("cube_r102", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, -5.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(171, 146).addBox(20.5F, -16.0F, -5.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r103 = rim4.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, 3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(91, 207).addBox(20.5F, -16.0F, -0.5F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r104 = rim4.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(206, 103).addBox(18.5F, -16.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r105 = rim4.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(205, 157).addBox(18.5F, -16.0F, -0.5F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim5 = pannel3.addOrReplaceChild("rim5", CubeListBuilder.create(), PartPose.offset(-11.2F, -11.4F, 0.0F));

        PartDefinition panels3 = pannel3.addOrReplaceChild("panels3", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r106 = panels3.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3F, 0.16F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(11.6341F, -19.5482F, -1.0F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r107 = panels3.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(216, 61).addBox(5.55F, -21.9F, -1.75F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -2.0F, 0.5F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r108 = panels3.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(216, 52).addBox(-0.5F, -0.4F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(216, 52).addBox(-0.5F, -0.4F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                .texOffs(216, 49).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(11.3806F, -19.0044F, -1.0F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r109 = panels3.addOrReplaceChild("cube_r109", CubeListBuilder.create().texOffs(216, 46).addBox(-1.8056F, 0.2086F, 0.0104F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(216, 46).addBox(-1.7011F, 0.1572F, -1.3795F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(9.4904F, -17.3898F, -14.8793F, 0.3829F, 0.2028F, 0.31F));

        PartDefinition cube_r110 = panels3.addOrReplaceChild("cube_r110", CubeListBuilder.create().texOffs(188, 216).addBox(-0.8688F, 0.2972F, -2.1126F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.05F))
                .texOffs(188, 216).addBox(-1.9255F, 0.3486F, -1.2037F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.4904F, -17.3898F, -14.8793F, 0.6319F, 0.9025F, 0.7505F));

        PartDefinition cube_r111 = panels3.addOrReplaceChild("cube_r111", CubeListBuilder.create().texOffs(216, 49).addBox(-1.9255F, -0.7486F, 0.2037F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F))
                .texOffs(216, 49).addBox(-0.8688F, -0.6972F, 1.1126F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(9.4904F, -17.3898F, -14.8793F, -2.5097F, 0.9025F, 0.7505F));

        PartDefinition cube_r112 = panels3.addOrReplaceChild("cube_r112", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3F, 0.16F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(14.0341F, -18.8482F, -0.1F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r113 = panels3.addOrReplaceChild("cube_r113", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(13.7806F, -18.3044F, -0.1F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r114 = panels3.addOrReplaceChild("cube_r114", CubeListBuilder.create().texOffs(188, 216).addBox(5.8F, -22.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -1.3F, 1.4F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r115 = panels3.addOrReplaceChild("cube_r115", CubeListBuilder.create().texOffs(188, 216).addBox(5.8F, -22.0F, -2.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.5F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r116 = panels3.addOrReplaceChild("cube_r116", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(14.8806F, -18.0044F, 1.0F, 3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r117 = panels3.addOrReplaceChild("cube_r117", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3F, 0.16F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(15.1341F, -18.5482F, 1.0F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r118 = panels3.addOrReplaceChild("cube_r118", CubeListBuilder.create().texOffs(36, 190).addBox(1.5F, -23.7F, -0.6F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(36, 190).addBox(5.2F, -24.4F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(96, 145).addBox(3.2F, -24.2F, -1.3F, 3.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(216, 36).addBox(5.2F, -24.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(44, 89).addBox(9.8F, -22.8F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(138, 55).addBox(5.8F, -22.3F, -0.25F, 5.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(0, 57).addBox(-5.2F, -22.2F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r119 = panels3.addOrReplaceChild("cube_r119", CubeListBuilder.create().texOffs(178, 77).addBox(-2.5F, 0.1F, -4.0F, 5.0F, 0.0F, 8.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(13.0914F, -19.2731F, -0.1188F, -0.0873F, 0.1396F, 0.4363F));

        PartDefinition cube_r120 = panels3.addOrReplaceChild("cube_r120", CubeListBuilder.create().texOffs(178, 92).addBox(-4.0F, 0.0F, -2.5F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.6708F, -17.1216F, -4.4F, 0.0F, 0.7854F, 0.3927F));

        PartDefinition cube_r121 = panels3.addOrReplaceChild("cube_r121", CubeListBuilder.create().texOffs(166, 185).addBox(-4.1508F, 0.0F, -2.5F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.8101F, -17.0639F, 4.4F, 0.0F, -0.7854F, 0.3927F));

        PartDefinition cube_r122 = panels3.addOrReplaceChild("cube_r122", CubeListBuilder.create().texOffs(141, 185).addBox(-0.8536F, -0.9F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(10.2617F, -21.9462F, 0.25F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r123 = panels3.addOrReplaceChild("cube_r123", CubeListBuilder.create().texOffs(13, 213).addBox(-1.75F, -1.1F, -1.75F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.2617F, -21.9462F, 0.25F, 0.0F, 0.7854F, 0.4363F));

        PartDefinition cube_r124 = panels3.addOrReplaceChild("cube_r124", CubeListBuilder.create().texOffs(209, 14).addBox(-1.6464F, -1.1F, -2.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(10.2617F, -21.9462F, 0.25F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r125 = panels3.addOrReplaceChild("cube_r125", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition button = panels3.addOrReplaceChild("button", CubeListBuilder.create(), PartPose.offset(9.4904F, -17.3898F, -14.8793F));

        PartDefinition cube_r126 = button.addOrReplaceChild("cube_r126", CubeListBuilder.create().texOffs(36, 190).addBox(-0.9883F, -1.4441F, -1.2403F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(216, 36).addBox(-0.9883F, -1.0441F, -1.2403F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.6319F, 0.9025F, 0.7505F));

        PartDefinition under3 = pannel3.addOrReplaceChild("under3", CubeListBuilder.create().texOffs(138, 154).addBox(18.8F, -14.6F, -5.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(138, 154).mirror().addBox(-18.8F, -14.6F, -5.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.001F)).mirror(false)
                .texOffs(0, 76).addBox(6.9F, -14.3F, -5.5F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r127 = under3.addOrReplaceChild("cube_r127", CubeListBuilder.create().texOffs(117, 76).addBox(9.5F, -16.0F, -5.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r128 = under3.addOrReplaceChild("cube_r128", CubeListBuilder.create().texOffs(138, 147).addBox(9.5F, -16.0F, 0.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(186, 153).addBox(20.4F, -16.3F, -3.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r129 = under3.addOrReplaceChild("cube_r129", CubeListBuilder.create().texOffs(138, 154).mirror().addBox(-20.5F, -16.0F, -5.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-0.7986F, 1.699F, -1.3853F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r130 = under3.addOrReplaceChild("cube_r130", CubeListBuilder.create().texOffs(186, 153).mirror().addBox(-20.4F, -16.3F, -5.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r131 = under3.addOrReplaceChild("cube_r131", CubeListBuilder.create().texOffs(186, 153).mirror().addBox(-20.4F, -16.3F, -3.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r132 = under3.addOrReplaceChild("cube_r132", CubeListBuilder.create().texOffs(138, 154).addBox(20.4F, -16.3F, -5.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.7986F, 1.699F, -1.3853F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r133 = under3.addOrReplaceChild("cube_r133", CubeListBuilder.create().texOffs(187, 98).addBox(20.4F, -16.3F, -5.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.7986F, 1.699F, 1.3853F, 0.0F, 1.5708F, 0.0F));

        PartDefinition cube_r134 = under3.addOrReplaceChild("cube_r134", CubeListBuilder.create().texOffs(187, 98).addBox(20.4F, -16.3F, -3.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.7986F, 1.699F, -1.3853F, 0.0F, -1.5708F, 0.0F));

        PartDefinition cube_r135 = under3.addOrReplaceChild("cube_r135", CubeListBuilder.create().texOffs(186, 153).addBox(20.4F, -16.3F, -5.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r136 = under3.addOrReplaceChild("cube_r136", CubeListBuilder.create().texOffs(138, 154).addBox(20.4F, -16.3F, -5.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.7986F, 1.699F, 1.3853F, 0.0F, 2.0944F, 0.0F));

        PartDefinition cube_r137 = under3.addOrReplaceChild("cube_r137", CubeListBuilder.create().texOffs(138, 154).addBox(20.4F, -16.3F, -5.5F, 0.0F, 3.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.7986F, 1.699F, 1.3853F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rolly = pannel3.addOrReplaceChild("rolly", CubeListBuilder.create(), PartPose.offset(15.7721F, -17.818F, -7.2115F));

        PartDefinition cube_r138 = rolly.addOrReplaceChild("cube_r138", CubeListBuilder.create().texOffs(183, 213).addBox(-3.475F, -2.025F, 13.35F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(215, 132).addBox(-2.925F, -1.575F, 13.15F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.23F))
                .texOffs(215, 132).addBox(-2.675F, -0.825F, 12.05F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.65F)), PartPose.offsetAndRotation(4.716F, 0.8083F, 12.8849F, 0.0F, -2.618F, 0.0F));

        PartDefinition pannel4 = console.addOrReplaceChild("pannel4", CubeListBuilder.create().texOffs(0, 184).addBox(5.5F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(186, 16).addBox(7.3F, -24.5F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
                .texOffs(53, 211).addBox(6.3189F, -24.7F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(122, 185).addBox(5.1F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r139 = pannel4.addOrReplaceChild("cube_r139", CubeListBuilder.create().texOffs(215, 110).addBox(-1.8F, -0.75F, -1.125F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F))
                .texOffs(21, 132).addBox(-0.8F, -1.45F, -0.375F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.9771F, -26.1061F, 0.125F, 0.0F, 0.0F, 0.6109F));

        PartDefinition cube_r140 = pannel4.addOrReplaceChild("cube_r140", CubeListBuilder.create().texOffs(189, 211).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, 4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r141 = pannel4.addOrReplaceChild("cube_r141", CubeListBuilder.create().texOffs(147, 178).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition pillars20 = pannel4.addOrReplaceChild("pillars20", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition pillars21 = pillars20.addOrReplaceChild("pillars21", CubeListBuilder.create().texOffs(130, 102).addBox(-0.999F, -24.7252F, -11.2925F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(216, 30).addBox(-1.999F, -24.8252F, -10.5925F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 204).addBox(-1.0F, -1.8637F, -15.2598F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r142 = pillars21.addOrReplaceChild("cube_r142", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r143 = pillars21.addOrReplaceChild("cube_r143", CubeListBuilder.create().texOffs(200, 201).addBox(-1.0F, -8.6049F, -6.676F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition tardis = pillars21.addOrReplaceChild("tardis", CubeListBuilder.create(), PartPose.offset(-1.499F, -26.2252F, -10.0925F));

        PartDefinition cube_r144 = tardis.addOrReplaceChild("cube_r144", CubeListBuilder.create().texOffs(216, 33).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.35F))
                .texOffs(53, 203).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition pillars22 = pillars21.addOrReplaceChild("pillars22", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars23 = pillars22.addOrReplaceChild("pillars23", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars24 = pillars23.addOrReplaceChild("pillars24", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars25 = pillars20.addOrReplaceChild("pillars25", CubeListBuilder.create().texOffs(35, 89).addBox(-0.999F, -24.7252F, -10.2925F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 76).addBox(-1.0F, -14.6347F, -22.4402F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r145 = pillars25.addOrReplaceChild("cube_r145", CubeListBuilder.create().texOffs(174, 213).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.1842F, -9.915F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r146 = pillars25.addOrReplaceChild("cube_r146", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r147 = pillars25.addOrReplaceChild("cube_r147", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(200, 201).addBox(-1.0F, -8.6049F, -6.676F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition pillars26 = pillars25.addOrReplaceChild("pillars26", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars27 = pillars26.addOrReplaceChild("pillars27", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars28 = pillars27.addOrReplaceChild("pillars28", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rim6 = pannel4.addOrReplaceChild("rim6", CubeListBuilder.create().texOffs(25, 128).addBox(18.5F, -14.3F, -5.5F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(173, 111).addBox(19.5F, -13.5F, -5.5F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, -2.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r148 = rim6.addOrReplaceChild("cube_r148", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, -5.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(171, 146).addBox(20.5F, -16.0F, -5.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r149 = rim6.addOrReplaceChild("cube_r149", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, 3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(91, 207).addBox(20.5F, -16.0F, -0.5F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r150 = rim6.addOrReplaceChild("cube_r150", CubeListBuilder.create().texOffs(206, 103).addBox(18.5F, -16.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r151 = rim6.addOrReplaceChild("cube_r151", CubeListBuilder.create().texOffs(205, 157).addBox(18.5F, -16.0F, -0.5F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim7 = pannel4.addOrReplaceChild("rim7", CubeListBuilder.create(), PartPose.offset(-11.2F, -11.4F, 0.0F));

        PartDefinition panels4 = pannel4.addOrReplaceChild("panels4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r152 = panels4.addOrReplaceChild("cube_r152", CubeListBuilder.create().texOffs(36, 190).addBox(-0.3085F, -1.0893F, -3.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(162, 111).addBox(0.1915F, -1.0893F, -2.8F, 0.0F, 3.0F, 5.0F, new CubeDeformation(0.001F))
                .texOffs(36, 190).addBox(-0.3085F, -1.0893F, 2.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.3692F, -21.5213F, 0.0F, -3.1416F, 0.0F, -2.7053F));

        PartDefinition cube_r153 = panels4.addOrReplaceChild("cube_r153", CubeListBuilder.create().texOffs(174, 205).addBox(-0.7813F, -1.0521F, -3.2F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.3692F, -21.5213F, 0.0F, -3.1416F, 0.0F, 2.7925F));

        PartDefinition cube_r154 = panels4.addOrReplaceChild("cube_r154", CubeListBuilder.create().texOffs(199, 180).addBox(-2.5F, 0.0F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(18.7594F, -15.8577F, 0.0F, 0.0524F, 0.0F, 0.4363F));

        PartDefinition cube_r155 = panels4.addOrReplaceChild("cube_r155", CubeListBuilder.create().texOffs(205, 89).addBox(-2.0F, 0.0F, -2.5F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.0043F, -19.118F, 1.0F, 0.1047F, 0.0F, 0.4363F));

        PartDefinition cube_r156 = panels4.addOrReplaceChild("cube_r156", CubeListBuilder.create().texOffs(39, 213).addBox(0.8F, -23.2F, -1.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(215, 136).addBox(3.8F, -22.7F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(69, 0).addBox(-5.2F, -22.2F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r157 = panels4.addOrReplaceChild("cube_r157", CubeListBuilder.create().texOffs(215, 136).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(13.9324F, -18.0915F, 1.0F, -3.1416F, 0.0F, -2.7053F));

        PartDefinition cube_r158 = panels4.addOrReplaceChild("cube_r158", CubeListBuilder.create().texOffs(215, 136).addBox(-2.3F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)), PartPose.offsetAndRotation(13.9324F, -18.0915F, -0.3F, 0.0F, 1.5708F, 0.4363F));

        PartDefinition cube_r159 = panels4.addOrReplaceChild("cube_r159", CubeListBuilder.create().texOffs(204, 145).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(16.1572F, -16.7954F, -4.5F, 0.0F, 0.3927F, 0.3927F));

        PartDefinition cube_r160 = panels4.addOrReplaceChild("cube_r160", CubeListBuilder.create().texOffs(204, 139).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(16.1572F, -16.7954F, 4.5F, 0.0F, -0.3927F, 0.3927F));

        PartDefinition cube_r161 = panels4.addOrReplaceChild("cube_r161", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition under4 = pannel4.addOrReplaceChild("under4", CubeListBuilder.create().texOffs(0, 76).addBox(6.9F, -14.3F, -5.5F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r162 = under4.addOrReplaceChild("cube_r162", CubeListBuilder.create().texOffs(117, 76).addBox(9.5F, -16.0F, -5.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r163 = under4.addOrReplaceChild("cube_r163", CubeListBuilder.create().texOffs(138, 147).addBox(9.5F, -16.0F, 0.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition pannel5 = console.addOrReplaceChild("pannel5", CubeListBuilder.create().texOffs(0, 184).addBox(5.5F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(186, 16).addBox(7.3F, -24.5F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
                .texOffs(53, 211).addBox(6.3189F, -24.7F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(122, 185).addBox(5.1F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r164 = pannel5.addOrReplaceChild("cube_r164", CubeListBuilder.create().texOffs(215, 123).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.5189F, -24.4F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r165 = pannel5.addOrReplaceChild("cube_r165", CubeListBuilder.create().texOffs(189, 211).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, 4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r166 = pannel5.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(147, 178).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition pillars29 = pannel5.addOrReplaceChild("pillars29", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition pillars30 = pillars29.addOrReplaceChild("pillars30", CubeListBuilder.create().texOffs(130, 102).addBox(-0.999F, -24.7252F, -11.2925F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(0, 89).addBox(-1.0F, -14.6347F, -22.4402F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r167 = pillars30.addOrReplaceChild("cube_r167", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition pillars31 = pillars30.addOrReplaceChild("pillars31", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars32 = pillars31.addOrReplaceChild("pillars32", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars33 = pillars32.addOrReplaceChild("pillars33", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars34 = pillars29.addOrReplaceChild("pillars34", CubeListBuilder.create().texOffs(44, 147).addBox(-0.6F, -23.4886F, -9.354F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F))
                .texOffs(35, 89).addBox(-0.999F, -24.7252F, -10.2925F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(82, 76).addBox(-1.0F, -14.6347F, -22.4402F, 2.0F, 1.0F, 15.0F, new CubeDeformation(0.01F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r168 = pillars34.addOrReplaceChild("cube_r168", CubeListBuilder.create().texOffs(66, 211).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4737F, -13.1113F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r169 = pillars34.addOrReplaceChild("cube_r169", CubeListBuilder.create().texOffs(174, 213).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.1842F, -9.915F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r170 = pillars34.addOrReplaceChild("cube_r170", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r171 = pillars34.addOrReplaceChild("cube_r171", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition cube_r172 = pillars34.addOrReplaceChild("cube_r172", CubeListBuilder.create().texOffs(84, 215).addBox(21.0F, -3.5261F, -42.1375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.999F, 8.4485F, 12.8146F, -0.5672F, 0.0F, 0.0F));

        PartDefinition toggle = pillars34.addOrReplaceChild("toggle", CubeListBuilder.create().texOffs(44, 147).addBox(-1.2F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.45F)), PartPose.offsetAndRotation(1.0F, -22.9886F, -8.854F, 0.0F, 0.0F, -0.3927F));

        PartDefinition pillars35 = pillars34.addOrReplaceChild("pillars35", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars36 = pillars35.addOrReplaceChild("pillars36", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars37 = pillars36.addOrReplaceChild("pillars37", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rim8 = pannel5.addOrReplaceChild("rim8", CubeListBuilder.create().texOffs(25, 128).addBox(18.5F, -14.3F, -5.5F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(173, 111).addBox(19.5F, -13.5F, -5.5F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, -2.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r173 = rim8.addOrReplaceChild("cube_r173", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, -5.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(171, 146).addBox(20.5F, -16.0F, -5.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r174 = rim8.addOrReplaceChild("cube_r174", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, 3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(91, 207).addBox(20.5F, -16.0F, -0.5F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r175 = rim8.addOrReplaceChild("cube_r175", CubeListBuilder.create().texOffs(206, 103).addBox(18.5F, -16.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r176 = rim8.addOrReplaceChild("cube_r176", CubeListBuilder.create().texOffs(205, 157).addBox(18.5F, -16.0F, -0.5F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim9 = pannel5.addOrReplaceChild("rim9", CubeListBuilder.create(), PartPose.offset(-11.2F, -11.4F, 0.0F));

        PartDefinition panels5 = pannel5.addOrReplaceChild("panels5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r177 = panels5.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(206, 95).addBox(-0.5F, -0.5F, -2.6F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.8653F, -20.4464F, 0.4F, 0.0F, 0.0873F, 0.4363F));

        PartDefinition cube_r178 = panels5.addOrReplaceChild("cube_r178", CubeListBuilder.create().texOffs(126, 166).addBox(-1.5F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.001F)), PartPose.offsetAndRotation(14.85F, -18.122F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r179 = panels5.addOrReplaceChild("cube_r179", CubeListBuilder.create().texOffs(189, 205).addBox(6.8F, -23.2F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(189, 205).addBox(5.3F, -23.2F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(216, 9).addBox(8.6F, -23.0F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.9F))
                .texOffs(216, 6).addBox(1.8F, -23.2F, -2.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.9F))
                .texOffs(216, 9).addBox(8.6F, -23.2F, 1.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.9F))
                .texOffs(216, 65).addBox(-2.0F, -22.5F, -1.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(216, 65).addBox(-0.8F, -22.5F, 3.55F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(205, 151).addBox(-3.8F, -22.5F, -1.45F, 4.0F, 0.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(216, 6).addBox(-1.8F, -22.7F, -0.45F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(216, 6).addBox(1.8F, -23.2F, 1.3F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.9F))
                .texOffs(189, 205).addBox(3.8F, -23.2F, -2.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(95, 93).addBox(-5.2F, -22.7F, -2.0F, 16.0F, 0.0F, 4.0F, new CubeDeformation(0.001F))
                .texOffs(69, 19).addBox(-5.2F, -22.2F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r180 = panels5.addOrReplaceChild("cube_r180", CubeListBuilder.create().texOffs(216, 169).addBox(-0.5F, 0.0F, -2.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F))
                .texOffs(216, 167).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.2064F, -23.2925F, -3.5109F, -0.8727F, 0.0F, 0.4363F));

        PartDefinition cube_r181 = panels5.addOrReplaceChild("cube_r181", CubeListBuilder.create().texOffs(216, 165).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.8194F, -22.4625F, -3.0824F, -1.0472F, 0.0F, 0.4363F));

        PartDefinition cube_r182 = panels5.addOrReplaceChild("cube_r182", CubeListBuilder.create().texOffs(216, 71).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.8039F, -22.4292F, -3.1615F, -1.2217F, 0.0F, 0.4363F));

        PartDefinition cube_r183 = panels5.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(216, 69).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.4321F, -21.632F, -2.7035F, -0.9599F, 0.0F, 0.4363F));

        PartDefinition cube_r184 = panels5.addOrReplaceChild("cube_r184", CubeListBuilder.create().texOffs(216, 67).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.2042F, -21.1432F, -1.9332F, -0.2618F, 0.0F, 0.4363F));

        PartDefinition cube_r185 = panels5.addOrReplaceChild("cube_r185", CubeListBuilder.create().texOffs(166, 185).addBox(-3.7F, 0.0F, -2.5F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.657F, -16.8943F, 4.0F, 0.0F, -0.7854F, 0.3927F));

        PartDefinition cube_r186 = panels5.addOrReplaceChild("cube_r186", CubeListBuilder.create().texOffs(178, 86).addBox(-3.7F, 0.0F, -2.5F, 8.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.657F, -16.8943F, -4.0F, 0.0F, 0.7854F, 0.3927F));

        PartDefinition cube_r187 = panels5.addOrReplaceChild("cube_r187", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition under5 = pannel5.addOrReplaceChild("under5", CubeListBuilder.create().texOffs(0, 76).addBox(6.9F, -14.3F, -5.5F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r188 = under5.addOrReplaceChild("cube_r188", CubeListBuilder.create().texOffs(117, 76).addBox(9.5F, -16.0F, -5.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r189 = under5.addOrReplaceChild("cube_r189", CubeListBuilder.create().texOffs(138, 147).addBox(9.5F, -16.0F, 0.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition pannel6 = console.addOrReplaceChild("pannel6", CubeListBuilder.create().texOffs(0, 184).addBox(5.5F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(186, 16).addBox(7.3F, -24.5F, -4.5F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.001F))
                .texOffs(53, 211).addBox(6.3189F, -24.7F, -2.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.01F))
                .texOffs(122, 185).addBox(5.1F, -25.3F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r190 = pannel6.addOrReplaceChild("cube_r190", CubeListBuilder.create().texOffs(204, 145).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(16.1572F, -16.7954F, -4.5F, 0.0F, 0.3927F, 0.3927F));

        PartDefinition cube_r191 = pannel6.addOrReplaceChild("cube_r191", CubeListBuilder.create().texOffs(204, 139).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(16.1572F, -16.7954F, 4.5F, 0.0F, -0.3927F, 0.3927F));

        PartDefinition cube_r192 = pannel6.addOrReplaceChild("cube_r192", CubeListBuilder.create().texOffs(36, 190).addBox(-0.3085F, -1.0893F, -3.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(36, 190).addBox(-0.3085F, -1.0893F, 2.2F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.2692F, -21.5213F, 0.0F, -3.1416F, 0.0F, -2.7053F));

        PartDefinition cube_r193 = pannel6.addOrReplaceChild("cube_r193", CubeListBuilder.create().texOffs(174, 205).addBox(-0.7813F, -1.0521F, -3.2F, 1.0F, 1.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(10.2692F, -21.5213F, 0.0F, -3.1416F, 0.0F, 2.7925F));

        PartDefinition cube_r194 = pannel6.addOrReplaceChild("cube_r194", CubeListBuilder.create().texOffs(189, 211).addBox(-1.0F, -0.5F, -2.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, 4.0F, 0.0F, 0.2618F, 0.0F));

        PartDefinition cube_r195 = pannel6.addOrReplaceChild("cube_r195", CubeListBuilder.create().texOffs(147, 178).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, -24.2F, -4.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition pillars38 = pannel6.addOrReplaceChild("pillars38", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition pillars39 = pillars38.addOrReplaceChild("pillars39", CubeListBuilder.create().texOffs(130, 102).addBox(-0.999F, -24.7252F, -11.2925F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.1F))
                .texOffs(216, 24).addBox(-0.499F, -25.2252F, -11.0925F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(216, 24).mirror().addBox(-0.501F, -25.2252F, 10.0925F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(147, 169).addBox(-2.749F, -32.7252F, -9.7925F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(147, 169).addBox(-2.749F, -32.7252F, 9.7925F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 204).addBox(-1.0F, -1.8637F, -15.2598F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r196 = pillars39.addOrReplaceChild("cube_r196", CubeListBuilder.create().texOffs(66, 211).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4737F, -13.1113F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r197 = pillars39.addOrReplaceChild("cube_r197", CubeListBuilder.create().texOffs(174, 213).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.1842F, -9.915F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r198 = pillars39.addOrReplaceChild("cube_r198", CubeListBuilder.create().texOffs(44, 141).mirror().addBox(0.5798F, -1.25F, -2.2431F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(8.7403F, -24.9752F, -5.0462F, -3.1416F, 1.3963F, 3.1416F));

        PartDefinition cube_r199 = pillars39.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(216, 24).mirror().addBox(-0.501F, -0.25F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.7403F, -24.9752F, -5.0462F, -3.1416F, 1.0472F, 3.1416F));

        PartDefinition cube_r200 = pillars39.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(44, 141).addBox(-0.5798F, -1.25F, -2.2431F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.7403F, -24.9752F, -5.0462F, -3.1416F, 0.6981F, 3.1416F));

        PartDefinition cube_r201 = pillars39.addOrReplaceChild("cube_r201", CubeListBuilder.create().texOffs(44, 141).addBox(-0.5719F, -1.25F, -1.9234F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.4241F, -24.9752F, 4.9985F, 0.0F, 1.3963F, 0.0F));

        PartDefinition cube_r202 = pillars39.addOrReplaceChild("cube_r202", CubeListBuilder.create().texOffs(44, 141).mirror().addBox(0.7914F, -1.25F, -2.0032F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(8.4241F, -24.9752F, 4.9985F, 0.0F, 0.6981F, 0.0F));

        PartDefinition cube_r203 = pillars39.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(216, 24).mirror().addBox(-0.3842F, -0.25F, 0.2978F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.4241F, -24.9752F, 4.9985F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r204 = pillars39.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(216, 24).mirror().addBox(-0.6175F, -0.25F, 0.2982F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-7.7403F, -24.9752F, -4.3962F, -3.1416F, -1.0472F, 3.1416F));

        PartDefinition cube_r205 = pillars39.addOrReplaceChild("cube_r205", CubeListBuilder.create().texOffs(44, 141).addBox(-0.7913F, -1.25F, -2.0027F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-7.7403F, -24.9752F, -4.3962F, -3.1416F, -1.3963F, 3.1416F));

        PartDefinition cube_r206 = pillars39.addOrReplaceChild("cube_r206", CubeListBuilder.create().texOffs(44, 141).mirror().addBox(0.5723F, -1.25F, -1.9231F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-7.7403F, -24.9752F, -4.3962F, -3.1416F, -0.6981F, 3.1416F));

        PartDefinition cube_r207 = pillars39.addOrReplaceChild("cube_r207", CubeListBuilder.create().texOffs(44, 141).addBox(-0.5719F, -1.25F, -1.9234F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-8.5408F, -24.9752F, 4.7962F, 0.0F, -0.6981F, 0.0F));

        PartDefinition cube_r208 = pillars39.addOrReplaceChild("cube_r208", CubeListBuilder.create().texOffs(216, 24).mirror().addBox(-0.3842F, -0.25F, 0.2978F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.5408F, -24.9752F, 4.7962F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r209 = pillars39.addOrReplaceChild("cube_r209", CubeListBuilder.create().texOffs(44, 141).mirror().addBox(0.7914F, -1.25F, -2.0032F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-8.5408F, -24.9752F, 4.7962F, 0.0F, -1.3963F, 0.0F));

        PartDefinition cube_r210 = pillars39.addOrReplaceChild("cube_r210", CubeListBuilder.create().texOffs(44, 141).mirror().addBox(0.7914F, -1.25F, -2.0032F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(-0.1168F, -24.9752F, 9.7947F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r211 = pillars39.addOrReplaceChild("cube_r211", CubeListBuilder.create().texOffs(44, 141).addBox(-0.5719F, -1.25F, -1.9234F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.1168F, -24.9752F, 9.7947F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r212 = pillars39.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(44, 141).mirror().addBox(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)).mirror(false), PartPose.offsetAndRotation(0.799F, -25.2252F, -9.5925F, 0.0F, 0.3491F, 0.0F));

        PartDefinition cube_r213 = pillars39.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(44, 141).addBox(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(-0.799F, -25.2252F, -9.5925F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r214 = pillars39.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r215 = pillars39.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(200, 201).addBox(-1.0F, -8.6049F, -6.676F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition cube_r216 = pillars39.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(84, 215).addBox(21.0F, -3.5261F, -42.1375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.999F, 8.4485F, 12.8146F, -0.5672F, 0.0F, 0.0F));

        PartDefinition glassofhour = pillars39.addOrReplaceChild("glassofhour", CubeListBuilder.create().texOffs(69, 161).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(195, 26).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offset(-0.049F, -29.1252F, -9.7925F));

        PartDefinition cube_r217 = glassofhour.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(195, 26).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition hourglass = pillars39.addOrReplaceChild("hourglass", CubeListBuilder.create().texOffs(69, 161).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(195, 26).addBox(-0.5F, -2.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offset(-0.049F, -29.1252F, 9.7925F));

        PartDefinition cube_r218 = hourglass.addOrReplaceChild("cube_r218", CubeListBuilder.create().texOffs(195, 26).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.2F)), PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition pillars40 = pillars39.addOrReplaceChild("pillars40", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars41 = pillars40.addOrReplaceChild("pillars41", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars42 = pillars41.addOrReplaceChild("pillars42", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars43 = pillars38.addOrReplaceChild("pillars43", CubeListBuilder.create().texOffs(35, 89).addBox(-0.999F, -24.7252F, -10.2925F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(60, 110).addBox(-1.0F, -22.0637F, -9.2598F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(95, 183).addBox(-1.0F, -20.8637F, -9.2598F, 2.0F, 21.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(17, 204).addBox(-1.0F, -1.8637F, -15.2598F, 2.0F, 2.0F, 6.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r219 = pillars43.addOrReplaceChild("cube_r219", CubeListBuilder.create().texOffs(66, 211).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.4737F, -13.1113F, -1.4399F, 0.0F, 0.0F));

        PartDefinition cube_r220 = pillars43.addOrReplaceChild("cube_r220", CubeListBuilder.create().texOffs(174, 213).addBox(-1.0F, -2.6472F, -2.6383F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -0.1842F, -9.915F, -0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r221 = pillars43.addOrReplaceChild("cube_r221", CubeListBuilder.create().texOffs(209, 201).addBox(-1.0F, -0.5351F, -0.0031F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, -24.495F, -8.7766F, -1.1345F, 0.0F, 0.0F));

        PartDefinition cube_r222 = pillars43.addOrReplaceChild("cube_r222", CubeListBuilder.create().texOffs(69, 150).addBox(-1.2F, -4.6049F, -6.876F, 2.0F, 10.0F, 0.0F, new CubeDeformation(0.001F))
                .texOffs(200, 201).addBox(-1.0F, -8.6049F, -6.676F, 2.0F, 15.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(0.0F, -12.7212F, -12.7214F, -1.2654F, 0.0F, 0.0F));

        PartDefinition cube_r223 = pillars43.addOrReplaceChild("cube_r223", CubeListBuilder.create().texOffs(84, 215).addBox(21.0F, -3.5261F, -42.1375F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-21.999F, 8.4485F, 12.8146F, -0.5672F, 0.0F, 0.0F));

        PartDefinition pillars44 = pillars43.addOrReplaceChild("pillars44", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars45 = pillars44.addOrReplaceChild("pillars45", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition pillars46 = pillars45.addOrReplaceChild("pillars46", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rim10 = pannel6.addOrReplaceChild("rim10", CubeListBuilder.create().texOffs(25, 128).addBox(18.5F, -14.3F, -5.5F, 2.0F, 1.0F, 11.0F, new CubeDeformation(0.001F))
                .texOffs(173, 111).addBox(19.5F, -13.5F, -5.5F, 0.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(19.0F, -13.3F, -2.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r224 = rim10.addOrReplaceChild("cube_r224", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, -5.7F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -4.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, -3.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(171, 146).addBox(20.5F, -16.0F, -5.5F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r225 = rim10.addOrReplaceChild("cube_r225", CubeListBuilder.create().texOffs(141, 196).addBox(20.0F, -15.8F, 3.2F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 1.9F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(141, 196).addBox(20.0F, -15.8F, 4.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(91, 207).addBox(20.5F, -16.0F, -0.5F, 0.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r226 = rim10.addOrReplaceChild("cube_r226", CubeListBuilder.create().texOffs(206, 103).addBox(18.5F, -16.0F, -5.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r227 = rim10.addOrReplaceChild("cube_r227", CubeListBuilder.create().texOffs(205, 157).addBox(18.5F, -16.0F, -0.5F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim11 = pannel6.addOrReplaceChild("rim11", CubeListBuilder.create(), PartPose.offset(-11.2F, -11.4F, 0.0F));

        PartDefinition panels6 = pannel6.addOrReplaceChild("panels6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r228 = panels6.addOrReplaceChild("cube_r228", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3902F, -0.2722F, -0.4969F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(10.3166F, -20.3286F, -0.4573F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r229 = panels6.addOrReplaceChild("cube_r229", CubeListBuilder.create().texOffs(188, 216).addBox(-0.5659F, -0.1322F, -0.5616F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.3166F, -20.3286F, -0.4573F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r230 = panels6.addOrReplaceChild("cube_r230", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5659F, -0.2678F, -0.4384F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(10.3166F, -20.3286F, -0.4573F, -3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r231 = panels6.addOrReplaceChild("cube_r231", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3902F, -0.2722F, -0.4969F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(15.7166F, -18.6286F, 1.3427F, 0.0F, -0.7854F, 1.6755F));

        PartDefinition cube_r232 = panels6.addOrReplaceChild("cube_r232", CubeListBuilder.create().texOffs(188, 216).addBox(-0.5659F, -0.1322F, -0.5616F, 1.0F, 0.0F, 1.0F, new CubeDeformation(-0.11F)), PartPose.offsetAndRotation(15.7166F, -18.6286F, 1.3427F, 0.0F, 0.0F, 1.6755F));

        PartDefinition cube_r233 = panels6.addOrReplaceChild("cube_r233", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5659F, -0.2678F, -0.4384F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(15.7166F, -18.6286F, 1.3427F, -3.1416F, 0.0F, 1.6755F));

        PartDefinition cube_r234 = panels6.addOrReplaceChild("cube_r234", CubeListBuilder.create().texOffs(216, 46).addBox(-0.3902F, -0.2722F, -0.4969F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(10.3166F, -20.3286F, 0.7427F, 0.0F, -0.7854F, 0.4363F));

        PartDefinition cube_r235 = panels6.addOrReplaceChild("cube_r235", CubeListBuilder.create().texOffs(188, 216).addBox(-0.5659F, -0.1322F, -0.5616F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(10.3166F, -20.3286F, 0.7427F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r236 = panels6.addOrReplaceChild("cube_r236", CubeListBuilder.create().texOffs(216, 49).addBox(-0.5659F, -0.2678F, -0.4384F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(10.3166F, -20.3286F, 0.7427F, -3.1416F, 0.0F, 0.4363F));

        PartDefinition cube_r237 = panels6.addOrReplaceChild("cube_r237", CubeListBuilder.create().texOffs(141, 191).addBox(-8.1F, 3.4F, 3.1F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(18.4576F, -21.621F, 0.1084F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r238 = panels6.addOrReplaceChild("cube_r238", CubeListBuilder.create().texOffs(216, 6).addBox(-0.5F, -0.1F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.7F)), PartPose.offsetAndRotation(17.1275F, -18.2737F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r239 = panels6.addOrReplaceChild("cube_r239", CubeListBuilder.create().texOffs(183, 216).addBox(0.1F, 0.0F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.1678F, -19.6752F, 2.359F, -0.8879F, 0.6199F, -0.6598F));

        PartDefinition cube_r240 = panels6.addOrReplaceChild("cube_r240", CubeListBuilder.create().texOffs(216, 177).addBox(0.1F, 0.0F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.8176F, -20.2194F, 1.8022F, -0.4629F, 0.9593F, -0.0779F));

        PartDefinition cube_r241 = panels6.addOrReplaceChild("cube_r241", CubeListBuilder.create().texOffs(216, 175).addBox(0.1F, 0.0F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.3958F, -20.2801F, 0.9868F, -0.2114F, 1.0177F, 0.2224F));

        PartDefinition cube_r242 = panels6.addOrReplaceChild("cube_r242", CubeListBuilder.create().texOffs(216, 173).addBox(-0.1F, 0.0F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(13.0691F, -20.1258F, 0.0042F, -0.1885F, 0.667F, 0.2627F));

        PartDefinition cube_r243 = panels6.addOrReplaceChild("cube_r243", CubeListBuilder.create().texOffs(216, 171).addBox(-0.3F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(13.9343F, -19.794F, -0.5101F, -0.0616F, 0.4284F, 0.5039F));

        PartDefinition cube_r244 = panels6.addOrReplaceChild("cube_r244", CubeListBuilder.create().texOffs(216, 63).addBox(-0.4F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(14.7904F, -19.2165F, -0.88F, -0.0097F, 0.218F, 0.7407F));

        PartDefinition cube_r245 = panels6.addOrReplaceChild("cube_r245", CubeListBuilder.create().texOffs(216, 55).addBox(-0.4F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.9878F, -21.1758F, 3.6349F, 3.123F, 1.0386F, -2.7136F));

        PartDefinition cube_r246 = panels6.addOrReplaceChild("cube_r246", CubeListBuilder.create().texOffs(171, 183).addBox(-1.3F, 0.0F, -0.7F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(8.5657F, -21.3572F, 1.7778F, 3.1063F, 1.3003F, -2.7316F));

        PartDefinition cube_r247 = panels6.addOrReplaceChild("cube_r247", CubeListBuilder.create().texOffs(216, 59).addBox(-1.3F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.4101F, -20.9519F, 1.4406F, 0.0116F, 0.6194F, 0.4508F));

        PartDefinition cube_r248 = panels6.addOrReplaceChild("cube_r248", CubeListBuilder.create().texOffs(216, 57).addBox(-0.4F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(9.5325F, -20.8948F, 1.5494F, 0.0098F, 0.2703F, 0.4466F));

        PartDefinition cube_r249 = panels6.addOrReplaceChild("cube_r249", CubeListBuilder.create().texOffs(216, 12).addBox(-1.4F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.3479F, -20.0295F, 1.3858F, 0.0094F, 0.0085F, 0.4441F));

        PartDefinition cube_r250 = panels6.addOrReplaceChild("cube_r250", CubeListBuilder.create().texOffs(215, 199).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(11.3966F, -20.0059F, 1.3436F, 0.0095F, 0.0958F, 0.4449F));

        PartDefinition cube_r251 = panels6.addOrReplaceChild("cube_r251", CubeListBuilder.create().texOffs(209, 24).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(12.2319F, -19.607F, 1.1972F, 0.0097F, 0.218F, 0.4461F));

        PartDefinition cube_r252 = panels6.addOrReplaceChild("cube_r252", CubeListBuilder.create().texOffs(204, 14).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(13.1127F, -19.1858F, 0.981F, 0.0097F, 0.218F, 0.4461F));

        PartDefinition cube_r253 = panels6.addOrReplaceChild("cube_r253", CubeListBuilder.create().texOffs(199, 14).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(13.9934F, -18.7645F, 0.7648F, 0.0097F, 0.218F, 0.4461F));

        PartDefinition cube_r254 = panels6.addOrReplaceChild("cube_r254", CubeListBuilder.create().texOffs(193, 185).addBox(-2.4F, 0.2F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(16.6689F, -17.8225F, 0.1372F, 0.0F, 0.2182F, 0.4014F));

        PartDefinition cube_r255 = panels6.addOrReplaceChild("cube_r255", CubeListBuilder.create().texOffs(78, 181).addBox(-0.5F, 0.2F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.8807F, -18.157F, 0.45F, 0.0F, 0.0F, 0.4014F));

        PartDefinition cube_r256 = panels6.addOrReplaceChild("cube_r256", CubeListBuilder.create().texOffs(176, 183).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.5807F, -18.457F, -1.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r257 = panels6.addOrReplaceChild("cube_r257", CubeListBuilder.create().texOffs(150, 81).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.3877F, -17.892F, -1.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r258 = panels6.addOrReplaceChild("cube_r258", CubeListBuilder.create().texOffs(201, 43).addBox(6.8F, -23.3F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.001F))
                .texOffs(215, 115).addBox(6.8F, -23.2F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(216, 6).addBox(1.5F, -23.0F, 2.1F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.19F))
                .texOffs(216, 6).addBox(3.5F, -23.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.19F))
                .texOffs(148, 135).addBox(-1.2F, -22.6F, -4.0F, 6.0F, 0.0F, 8.0F, new CubeDeformation(0.001F))
                .texOffs(69, 38).addBox(-5.2F, -22.2F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition cube_r259 = panels6.addOrReplaceChild("cube_r259", CubeListBuilder.create().texOffs(84, 164).addBox(-2.0F, 0.0F, -0.75F, 4.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(17.3999F, -16.4916F, 0.5F, 0.0F, 0.0F, 0.48F));

        PartDefinition cube_r260 = panels6.addOrReplaceChild("cube_r260", CubeListBuilder.create().texOffs(26, 213).addBox(-0.5F, -0.2F, -2.5F, 1.0F, 0.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.7576F, -18.3503F, 2.5F, 0.0873F, 0.0F, 0.1745F));

        PartDefinition cube_r261 = panels6.addOrReplaceChild("cube_r261", CubeListBuilder.create().texOffs(138, 53).addBox(-3.1F, 0.0F, 3.5F, 6.0F, 0.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(15.8561F, -18.333F, -3.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r262 = panels6.addOrReplaceChild("cube_r262", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition button2 = panels6.addOrReplaceChild("button2", CubeListBuilder.create(), PartPose.offset(18.4576F, -21.621F, 0.1084F));

        PartDefinition cube_r263 = button2.addOrReplaceChild("cube_r263", CubeListBuilder.create().texOffs(36, 190).addBox(-6.4F, 3.4F, -0.6F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(216, 36).addBox(-6.4F, 3.8F, -0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition under6 = pannel6.addOrReplaceChild("under6", CubeListBuilder.create().texOffs(0, 76).addBox(6.9F, -14.3F, -5.5F, 12.0F, 1.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r264 = under6.addOrReplaceChild("cube_r264", CubeListBuilder.create().texOffs(117, 76).addBox(9.5F, -16.0F, -5.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r265 = under6.addOrReplaceChild("cube_r265", CubeListBuilder.create().texOffs(138, 147).addBox(9.5F, -16.0F, 0.5F, 11.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.6F, 1.7F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone7 = console.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(95, 98).addBox(-1.1F, -20.5F, -4.0F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 22.25F, 0.0F));

        PartDefinition bone8 = bone7.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(95, 98).addBox(-1.1F, -20.5F, -4.0F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.0019F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(95, 98).addBox(-1.1F, -20.5F, -4.0F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.002F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(95, 98).addBox(-1.1F, -20.5F, -4.0F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.0015F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(95, 98).addBox(-1.1F, -20.5F, -4.0F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.0016F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(95, 98).addBox(-1.1F, -20.5F, -4.0F, 9.0F, 0.0F, 8.0F, new CubeDeformation(0.0017F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition rotor = console.addOrReplaceChild("rotor", CubeListBuilder.create(), PartPose.offset(0.0F, -20.0F, 0.0F));

        PartDefinition bone13 = rotor.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(120, 107).addBox(3.9F, -12.0F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.75F, 0.0F));

        PartDefinition bone14 = bone13.addOrReplaceChild("bone14", CubeListBuilder.create().texOffs(0, 151).addBox(3.4811F, -9.0F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone15 = bone14.addOrReplaceChild("bone15", CubeListBuilder.create().texOffs(180, 0).addBox(3.6433F, -8.0F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create().texOffs(157, 81).addBox(2.2244F, -9.0F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create().texOffs(0, 132).addBox(2.6433F, -11.0F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(105, 166).addBox(3.4811F, -8.0F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone19 = rotor.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(70, 93).addBox(0.9F, -11.0F, -3.0F, 6.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -8.75F, 0.0F));

        PartDefinition bone20 = bone19.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(95, 107).addBox(0.0481F, -10.0F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create().texOffs(21, 175).addBox(0.3442F, -8.0F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone22 = bone21.addOrReplaceChild("bone22", CubeListBuilder.create().texOffs(42, 181).addBox(1.4923F, -10.0F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create().texOffs(132, 57).addBox(0.3442F, -9.0F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone24 = bone23.addOrReplaceChild("bone24", CubeListBuilder.create().texOffs(52, 133).addBox(1.0481F, -10.0F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone25 = rotor.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(148, 121).addBox(-6.5311F, 18.0F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.02F))
                .texOffs(104, 200).addBox(-8.1913F, 18.0F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0234F, -40.25F, 5.3122F));

        PartDefinition cube_r266 = bone25.addOrReplaceChild("cube_r266", CubeListBuilder.create().texOffs(59, 197).addBox(-7.0F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r267 = bone25.addOrReplaceChild("cube_r267", CubeListBuilder.create().texOffs(183, 191).addBox(-2.0F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.3301F, 20.5F, 0.5718F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r268 = bone25.addOrReplaceChild("cube_r268", CubeListBuilder.create().texOffs(59, 197).addBox(-9.0F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5263F, 20.5F, -1.2679F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r269 = bone25.addOrReplaceChild("cube_r269", CubeListBuilder.create().texOffs(183, 191).addBox(-2.0F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1962F, 20.5F, -2.4282F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rotor2 = rotor.addOrReplaceChild("rotor2", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone26 = rotor2.addOrReplaceChild("bone26", CubeListBuilder.create().texOffs(85, 125).addBox(3.9F, -12.0F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(127, 127).addBox(4.1F, -12.0F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(106, 127).addBox(3.5F, -12.0F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offset(-1.0F, -1.75F, 0.0F));

        PartDefinition bone27 = bone26.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(21, 158).addBox(3.4811F, -9.0F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(161, 30).addBox(3.6811F, -9.0F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(84, 166).addBox(2.9811F, -9.0F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(59, 181).addBox(3.6433F, -8.0F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(147, 183).addBox(3.2433F, -8.0F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(182, 30).addBox(3.9433F, -8.0F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(159, 0).addBox(2.2244F, -9.0F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(63, 164).addBox(1.8244F, -9.0F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.6F))
                .texOffs(42, 164).addBox(2.6244F, -9.0F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(136, 83).addBox(2.6433F, -11.0F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(117, 147).addBox(2.9433F, -11.0F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(96, 147).addBox(2.2433F, -11.0F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone31 = bone30.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(161, 167).addBox(3.4811F, -8.0F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(126, 169).addBox(3.6811F, -8.0F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(0, 168).addBox(2.9811F, -8.0F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone32 = rotor2.addOrReplaceChild("bone32", CubeListBuilder.create().texOffs(161, 17).addBox(0.8F, -11.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(162, 98).addBox(1.0F, -10.9F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(161, 154).addBox(0.6F, -11.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.5F, -8.75F, 0.0F));

        PartDefinition bone33 = bone32.addOrReplaceChild("bone33", CubeListBuilder.create().texOffs(35, 110).addBox(-0.0519F, -10.0F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(60, 115).addBox(0.1481F, -10.0F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(0, 114).addBox(-0.3519F, -10.0F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone34 = bone33.addOrReplaceChild("bone34", CubeListBuilder.create().texOffs(177, 124).addBox(0.3442F, -8.0F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(178, 62).addBox(-0.0558F, -8.0F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(178, 47).addBox(0.7442F, -8.0F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone35 = bone34.addOrReplaceChild("bone35", CubeListBuilder.create().texOffs(105, 182).addBox(1.4923F, -10.0F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(78, 183).addBox(1.8923F, -10.0F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(182, 167).addBox(1.0923F, -10.0F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone36 = bone35.addOrReplaceChild("bone36", CubeListBuilder.create().texOffs(138, 0).addBox(0.3442F, -9.0F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(75, 145).addBox(0.7442F, -9.0F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(141, 102).addBox(-0.0558F, -9.0F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(138, 19).addBox(1.0481F, -10.0F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(21, 141).addBox(1.2481F, -10.0F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(138, 36).addBox(0.6481F, -10.0F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone38 = rotor2.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(44, 150).addBox(-6.5311F, 18.0F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(153, 67).addBox(-6.2311F, 18.0F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(153, 53).addBox(-6.7311F, 18.0F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(200, 187).addBox(-8.1913F, 18.0F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(201, 26).addBox(-7.7913F, 18.0F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(76, 201).addBox(-8.3913F, 18.0F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offset(4.0234F, -40.25F, 5.3122F));

        PartDefinition cube_r270 = bone38.addOrReplaceChild("cube_r270", CubeListBuilder.create().texOffs(36, 199).addBox(-6.6F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(199, 46).addBox(-7.3F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(199, 0).addBox(-7.0F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 20.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r271 = bone38.addOrReplaceChild("cube_r271", CubeListBuilder.create().texOffs(19, 190).addBox(-2.4F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(198, 125).addBox(-2.0F, -2.5F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-0.3301F, 20.5F, 0.5718F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r272 = bone38.addOrReplaceChild("cube_r272", CubeListBuilder.create().texOffs(139, 199).addBox(-1.4F, -4.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-5.3763F, 22.0F, -2.688F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r273 = bone38.addOrReplaceChild("cube_r273", CubeListBuilder.create().texOffs(199, 166).addBox(-9.2F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(166, 191).addBox(-8.6F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(198, 111).addBox(-9.0F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-1.5263F, 20.5F, -1.2679F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r274 = bone38.addOrReplaceChild("cube_r274", CubeListBuilder.create().texOffs(199, 60).addBox(-1.7F, -2.4F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(122, 199).addBox(-2.4F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(0, 198).addBox(-2.0F, -2.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-2.1962F, 20.5F, -2.4282F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rotor3 = rotor.addOrReplaceChild("rotor3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone39 = rotor3.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(120, 107).addBox(3.9F, -8.5F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.75F, 0.0F));

        PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(0, 151).addBox(3.4811F, -5.5F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(180, 0).addBox(3.6433F, -4.5F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(157, 81).addBox(2.2244F, -5.5F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(0, 132).addBox(2.6433F, -7.5F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(105, 166).addBox(3.4811F, -4.5F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone45 = rotor3.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(70, 93).addBox(0.9F, -7.5F, -3.0F, 6.0F, 15.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, -8.75F, 0.0F));

        PartDefinition bone46 = bone45.addOrReplaceChild("bone46", CubeListBuilder.create().texOffs(95, 107).addBox(0.0481F, -6.5F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone47 = bone46.addOrReplaceChild("bone47", CubeListBuilder.create().texOffs(21, 175).addBox(0.3442F, -4.5F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone48 = bone47.addOrReplaceChild("bone48", CubeListBuilder.create().texOffs(42, 181).addBox(1.4923F, -6.5F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone49 = bone48.addOrReplaceChild("bone49", CubeListBuilder.create().texOffs(132, 57).addBox(0.3442F, -5.5F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone50 = bone49.addOrReplaceChild("bone50", CubeListBuilder.create().texOffs(52, 133).addBox(1.0481F, -6.5F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone51 = rotor3.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(148, 121).addBox(-6.5311F, 21.5F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.02F))
                .texOffs(104, 200).addBox(-8.1913F, 21.5F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0234F, -40.25F, 5.3122F));

        PartDefinition cube_r275 = bone51.addOrReplaceChild("cube_r275", CubeListBuilder.create().texOffs(59, 197).addBox(-7.0F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r276 = bone51.addOrReplaceChild("cube_r276", CubeListBuilder.create().texOffs(183, 191).addBox(-2.0F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.3301F, 20.5F, 0.5718F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r277 = bone51.addOrReplaceChild("cube_r277", CubeListBuilder.create().texOffs(59, 197).addBox(-9.0F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-1.5263F, 20.5F, -1.2679F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r278 = bone51.addOrReplaceChild("cube_r278", CubeListBuilder.create().texOffs(183, 191).addBox(-2.0F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.1962F, 20.5F, -2.4282F, 0.0F, -1.0472F, 0.0F));

        PartDefinition rotor4 = rotor3.addOrReplaceChild("rotor4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone52 = rotor4.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(85, 125).addBox(3.9F, -8.5F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(127, 127).addBox(4.1F, -8.5F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(106, 127).addBox(3.5F, -8.5F, -3.5F, 3.0F, 12.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offset(-1.0F, -1.75F, 0.0F));

        PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(21, 158).addBox(3.4811F, -5.5F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(161, 30).addBox(3.6811F, -5.5F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(84, 166).addBox(2.9811F, -5.5F, -2.7744F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(59, 181).addBox(3.6433F, -4.5F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(147, 183).addBox(3.2433F, -4.5F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(182, 30).addBox(3.9433F, -4.5F, -2.7744F, 2.0F, 8.0F, 7.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone55 = bone54.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(159, 0).addBox(2.2244F, -5.5F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(63, 164).addBox(1.8244F, -5.5F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.6F))
                .texOffs(42, 164).addBox(2.6244F, -5.5F, -3.5F, 3.0F, 9.0F, 7.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone56 = bone55.addOrReplaceChild("bone56", CubeListBuilder.create().texOffs(136, 83).addBox(2.6433F, -7.5F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(117, 147).addBox(2.9433F, -7.5F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(96, 147).addBox(2.2433F, -7.5F, -4.2256F, 3.0F, 11.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone57 = bone56.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(161, 167).addBox(3.4811F, -4.5F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.5F))
                .texOffs(126, 169).addBox(3.6811F, -4.5F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.4F))
                .texOffs(0, 168).addBox(2.9811F, -4.5F, -4.2256F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone58 = rotor4.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(161, 17).addBox(0.8F, -7.5F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(162, 98).addBox(1.0F, -7.4F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(161, 154).addBox(0.6F, -7.5F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.5F)), PartPose.offset(-1.5F, -8.75F, 0.0F));

        PartDefinition bone59 = bone58.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(35, 110).addBox(-0.0519F, -6.5F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(60, 115).addBox(0.1481F, -6.5F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(0, 114).addBox(-0.3519F, -6.5F, -1.5244F, 6.0F, 11.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone60 = bone59.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(177, 124).addBox(0.3442F, -4.5F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(178, 62).addBox(-0.0558F, -4.5F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(178, 47).addBox(0.7442F, -4.5F, -1.5244F, 4.0F, 8.0F, 6.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone61 = bone60.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(105, 182).addBox(1.4923F, -6.5F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(78, 183).addBox(1.8923F, -6.5F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(182, 167).addBox(1.0923F, -6.5F, -3.0F, 2.0F, 11.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone62 = bone61.addOrReplaceChild("bone62", CubeListBuilder.create().texOffs(138, 0).addBox(0.3442F, -5.5F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(75, 145).addBox(0.7442F, -5.5F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(141, 102).addBox(-0.0558F, -5.5F, -4.4756F, 4.0F, 12.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone63 = bone62.addOrReplaceChild("bone63", CubeListBuilder.create().texOffs(138, 19).addBox(1.0481F, -6.5F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.5F))
                .texOffs(21, 141).addBox(1.2481F, -6.5F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.4F))
                .texOffs(138, 36).addBox(0.6481F, -6.5F, -4.4756F, 5.0F, 10.0F, 6.0F, new CubeDeformation(0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone64 = rotor4.addOrReplaceChild("bone64", CubeListBuilder.create().texOffs(44, 150).addBox(-6.5311F, 21.5F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(153, 67).addBox(-6.2311F, 21.5F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(153, 53).addBox(-6.7311F, 21.5F, -7.8122F, 7.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(200, 187).addBox(-8.1913F, 21.5F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.5F))
                .texOffs(201, 26).addBox(-7.7913F, 21.5F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(76, 201).addBox(-8.3913F, 21.5F, -7.8122F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offset(4.0234F, -40.25F, 5.3122F));

        PartDefinition cube_r279 = bone64.addOrReplaceChild("cube_r279", CubeListBuilder.create().texOffs(36, 199).addBox(-6.6F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(199, 46).addBox(-7.3F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(199, 0).addBox(-7.0F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 20.5F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r280 = bone64.addOrReplaceChild("cube_r280", CubeListBuilder.create().texOffs(19, 190).addBox(-2.4F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(198, 125).addBox(-2.0F, 1.0F, 3.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-0.3301F, 20.5F, 0.5718F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r281 = bone64.addOrReplaceChild("cube_r281", CubeListBuilder.create().texOffs(139, 199).addBox(-1.4F, -0.5F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(-5.3763F, 22.0F, -2.688F, 0.0F, -2.0944F, 0.0F));

        PartDefinition cube_r282 = bone64.addOrReplaceChild("cube_r282", CubeListBuilder.create().texOffs(199, 166).addBox(-9.2F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(166, 191).addBox(-8.6F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(198, 111).addBox(-9.0F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-1.5263F, 20.5F, -1.2679F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r283 = bone64.addOrReplaceChild("cube_r283", CubeListBuilder.create().texOffs(199, 60).addBox(-1.7F, 1.1F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.4F))
                .texOffs(122, 199).addBox(-2.4F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.6F))
                .texOffs(0, 198).addBox(-2.0F, 1.0F, -2.5F, 3.0F, 8.0F, 5.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(-2.1962F, 20.5F, -2.4282F, 0.0F, -1.0472F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        console.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = this.controlDelta();
        matrices.pushPose();
        this.applyRootTransform(matrices);

        // Throttle Control
        ModelPart throttle = this.console.getChild("pannel2").getChild("pillars2").getChild("pillars3").getChild("leveer2");
        float throttleTarget = -(tardis.travel().speed() / (float) tardis.travel().maxSpeed().get());
        throttle.xRot = getAngle(console, "throttle", throttleTarget, delta);

        // Handbrake Control
        ModelPart handbrake = this.console.getChild("pannel2").getChild("pillars2").getChild("pillars3").getChild("leveer");
        float handbrakeTarget = tardis.travel().handbrake() ? 1.0f : 0f;
        handbrake.xRot = getAngle(console, "handbrake", handbrakeTarget, delta);

        // Power Control
        ModelPart power = this.console.getChild("pannel3").getChild("rolly");
        float powerTarget = tardis.fuel().hasPower() ? 0f : -1.55f;
        power.zRot = getAngle(console, "power", powerTarget, delta);

        // Anti-Grav Control
        ModelPart antigravs = this.console.getChild("pannel7").getChild("panels7").getChild("cube1");
        float antigravTarget = tardis.travel().antigravs().get() ? -1.58f : 0f;
        antigravs.yRot = getAngle(console, "antigravs", antigravTarget, delta);

        // Increment Control
        ModelPart increment = this.console.getChild("pannel7").getChild("panels7").getChild("bone4");
        float incrementTarget = -(IncrementManager.increment(tardis) / 1000f);
        increment.zRot = getAngle(console, "increment", incrementTarget, delta);

        // Fuel Gauge
        ModelPart fuelGauge = this.console.getChild("pannel3").getChild("panels3").getChild("button");
        fuelGauge.x += 0.25f;
        fuelGauge.z += 0.25f;
        float fuelGaugeTarget = (float) ((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 2f) - 1f;
        fuelGauge.yRot = getAngle(console, "fuel_gauge", fuelGaugeTarget, delta);

        // Direction Control
        ModelPart direction = this.console.getChild("pannel7").getChild("pillars56").getChild("pillars57").getChild("spinnio");
        float directionTargetDegrees = tardis.travel().destination().getRotation() * 22.5f;
        direction.zRot = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        return switch (state) {
            default -> CrystallineAnimations.CRYSTALLINE_FLIGHT;
            case LANDED -> CrystallineAnimations.CRYSTALLINE_IDLE;
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
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.005f, 0.005f, 0.005f);
        matrices.mulPose(Axis.YN.rotationDegrees(-30f));
        matrices.translate(-246f, -225, -5f);
        String positionPosText = abppPos.getX() + ", " + abppPos.getY() + ", " + abppPos.getZ();
        Component positionDimensionText = WorldUtil.worldText(abpp.getDimension());
        String positionDirectionText = DirectionControl.rotationToDirection(abpp.getRotation()).toUpperCase();
        String destinationPosText = abpdPos.getX() + ", " + abpdPos.getY() + ", " + abpdPos.getZ();
        Component destinationDimensionText = WorldUtil.worldText(abpd.getDimension(), false);
        String destinationDirectionText = DirectionControl.rotationToDirection(abpd.getRotation()).toUpperCase();
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
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.005f, 0.005f, 0.005f);
        matrices.mulPose(Axis.YN.rotationDegrees(150f));
        matrices.translate(-246f, -235, -5f);
        renderer.drawInBatch8xOutline(Component.nullToEmpty("\uD83E\uDC97").getVisualOrderText(), 0, 40, 0xFF0000, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(destinationPosText).getVisualOrderText(), 8, 40, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(destinationDimensionText.getVisualOrderText(), 8, 48, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(destinationDirectionText).getVisualOrderText(), 8, 56, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.5, 0.75, 0.5);
        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.scale(0.015f, 0.015f, 0.015f);
        matrices.mulPose(Axis.YN.rotationDegrees(150f));
        matrices.mulPose(Axis.XP.rotationDegrees(-20.5f));
        String progressText = tardis.travel().getState() == TravelHandlerBase.State.LANDED
                ? "⏳: 0%"
                : "⏳: " + tardis.travel().getDurationAsPercentage() + "%";
        matrices.translate(0, -38, -52);
        matrices.translate(0 - entity.getLevel().random.nextFloat() * 0.4, 0 + entity.getLevel().random.nextFloat() * 0.4, 0 - entity.getLevel().random.nextFloat() * 0.4);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(progressText).getVisualOrderText(), 0 - renderer.width(progressText) / 2, 0, 0xffffff, 0x03cffc,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();
    }
}