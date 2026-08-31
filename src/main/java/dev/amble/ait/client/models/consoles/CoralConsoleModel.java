package dev.amble.ait.client.models.consoles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.gui.Font;
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
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.animation.console.coral.CoralAnimations;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.WaypointHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class CoralConsoleModel extends SimpleConsoleModel {
    public static final AnimationDefinition EMPTY_ANIM = AnimationDefinition.Builder.withLength(1).build(); // temporary animation bc rn we have
                                                                                    // none

    private final ModelPart console;

    public CoralConsoleModel(ModelPart root) {
        this.console = root.getChild("console");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition console = modelPartData.addOrReplaceChild("console", CubeListBuilder.create(),
                PartPose.offset(0.0F, 26.0F, 0.0F));

        PartDefinition tubes2 = console.addOrReplaceChild("tubes2",
                CubeListBuilder.create().texOffs(80, 43)
                        .addBox(16.65F, -12.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(14.65F, -10.5F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(12.65F, -9.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(105, 43)
                        .addBox(-0.35F, -7.5F, -5.0F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition cube_r1 = tubes2.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(113, 148).addBox(16.65F, -16.0F,
                4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 7.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r2 = tubes2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(120, 148).addBox(16.65F, -16.0F,
                -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r3 = tubes2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(113, 0).addBox(16.65F, -16.0F,
                -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r4 = tubes2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(105, 130).addBox(16.65F, -16.0F,
                3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 5.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r5 = tubes2.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(70, 128).addBox(16.65F, -16.0F,
                -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r6 = tubes2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(132, 34).addBox(16.65F, -16.0F,
                2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition tubes3 = tubes2.addOrReplaceChild("tubes3",
                CubeListBuilder.create().texOffs(80, 43)
                        .addBox(16.65F, -12.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(14.65F, -10.5F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(12.65F, -9.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(105, 43)
                        .addBox(-0.35F, -7.5F, -5.0F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r7 = tubes3.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(113, 148).addBox(16.65F, -16.5F,
                4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r8 = tubes3.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(120, 148).addBox(16.65F, -16.5F,
                -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r9 = tubes3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(113, 0).addBox(16.65F, -16.25F,
                -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r10 = tubes3.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(105, 130).addBox(16.65F,
                -16.25F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r11 = tubes3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(70, 128).addBox(16.65F,
                -16.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r12 = tubes3.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(132, 34).addBox(16.65F,
                -16.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition tubes4 = tubes3.addOrReplaceChild("tubes4",
                CubeListBuilder.create().texOffs(80, 43)
                        .addBox(16.65F, -12.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(14.65F, -10.5F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(12.65F, -9.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(105, 43)
                        .addBox(-0.35F, -7.5F, -5.0F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r13 = tubes4.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(113, 148).addBox(16.65F,
                -16.5F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r14 = tubes4.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(120, 148).addBox(16.65F,
                -16.5F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r15 = tubes4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(113, 0).addBox(16.65F,
                -16.25F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r16 = tubes4.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(105, 130).addBox(16.65F,
                -16.25F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r17 = tubes4.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(70, 128).addBox(16.65F,
                -16.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r18 = tubes4.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(132, 34).addBox(16.65F,
                -16.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition tubes5 = tubes4.addOrReplaceChild("tubes5",
                CubeListBuilder.create().texOffs(80, 43)
                        .addBox(16.65F, -12.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(14.65F, -10.5F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(12.65F, -9.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(105, 43)
                        .addBox(-0.35F, -7.5F, -5.0F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r19 = tubes5.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(113, 148).addBox(16.65F,
                -16.5F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r20 = tubes5.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(120, 148).addBox(16.65F,
                -16.5F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r21 = tubes5.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(113, 0).addBox(16.65F,
                -16.25F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r22 = tubes5.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(105, 130).addBox(16.65F,
                -16.25F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r23 = tubes5.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(70, 128).addBox(16.65F,
                -16.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r24 = tubes5.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(132, 34).addBox(16.65F,
                -16.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition tubes6 = tubes5.addOrReplaceChild("tubes6",
                CubeListBuilder.create().texOffs(80, 43)
                        .addBox(16.65F, -12.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(14.65F, -10.5F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(12.65F, -9.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(105, 43)
                        .addBox(-0.35F, -7.5F, -5.0F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r25 = tubes6.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(113, 148).addBox(16.65F,
                -16.5F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r26 = tubes6.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(120, 148).addBox(16.65F,
                -16.5F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r27 = tubes6.addOrReplaceChild("cube_r27", CubeListBuilder.create().texOffs(113, 0).addBox(16.65F,
                -16.25F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r28 = tubes6.addOrReplaceChild("cube_r28", CubeListBuilder.create().texOffs(105, 130).addBox(16.65F,
                -16.25F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r29 = tubes6.addOrReplaceChild("cube_r29", CubeListBuilder.create().texOffs(70, 128).addBox(16.65F,
                -16.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r30 = tubes6.addOrReplaceChild("cube_r30", CubeListBuilder.create().texOffs(132, 34).addBox(16.65F,
                -16.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition tubes7 = tubes6.addOrReplaceChild("tubes7",
                CubeListBuilder.create().texOffs(80, 43)
                        .addBox(16.65F, -12.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(14.65F, -10.5F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(80, 43)
                        .addBox(12.65F, -9.0F, -5.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.001F)).texOffs(105, 43)
                        .addBox(-0.35F, -7.5F, -5.0F, 13.0F, 2.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r31 = tubes7.addOrReplaceChild("cube_r31", CubeListBuilder.create().texOffs(113, 148).addBox(16.65F,
                -16.5F, 4.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r32 = tubes7.addOrReplaceChild("cube_r32", CubeListBuilder.create().texOffs(120, 148).addBox(16.65F,
                -16.5F, -5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 7.5F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r33 = tubes7.addOrReplaceChild("cube_r33", CubeListBuilder.create().texOffs(113, 0).addBox(16.65F,
                -16.25F, -5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r34 = tubes7.addOrReplaceChild("cube_r34", CubeListBuilder.create().texOffs(105, 130).addBox(16.65F,
                -16.25F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.0F, 5.75F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition cube_r35 = tubes7.addOrReplaceChild("cube_r35", CubeListBuilder.create().texOffs(70, 128).addBox(16.65F,
                -16.0F, -5.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r36 = tubes7.addOrReplaceChild("cube_r36", CubeListBuilder.create().texOffs(132, 34).addBox(16.65F,
                -16.0F, 2.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition pillars = console.addOrReplaceChild("pillars", CubeListBuilder.create().texOffs(133, 79).addBox(-2.0F,
                -15.5884F, -22.7409F, 4.0F, 2.0F, 5.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r37 = pillars
                .addOrReplaceChild("cube_r37",
                        CubeListBuilder.create().texOffs(70, 119).addBox(-2.0F, 1.75F, -23.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.404F, -0.4858F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r38 = pillars
                .addOrReplaceChild(
                        "cube_r38", CubeListBuilder.create().texOffs(44, 134).addBox(-2.0F, 4.75F, -23.75F, 4.0F, 6.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.4821F, -0.433F, -1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r39 = pillars
                .addOrReplaceChild("cube_r39",
                        CubeListBuilder.create().texOffs(19, 135).addBox(-2.0F, 2.0F, -26.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.4888F, -0.4121F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r40 = pillars.addOrReplaceChild("cube_r40", CubeListBuilder.create().texOffs(125, 9).addBox(-2.0F, -3.75F,
                -27.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.4488F, -0.3723F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r41 = pillars
                .addOrReplaceChild(
                        "cube_r41", CubeListBuilder.create().texOffs(132, 25).addBox(-2.0F, -24.0F, -11.0F, 4.0F, 3.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.7427F, -0.5684F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r42 = pillars
                .addOrReplaceChild(
                        "cube_r42", CubeListBuilder.create().texOffs(91, 130).addBox(-2.0F, -23.25F, -5.25F, 4.0F, 6.0F,
                                5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.7785F, -0.4404F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r43 = pillars
                .addOrReplaceChild("cube_r43",
                        CubeListBuilder.create().texOffs(133, 70).addBox(-2.0F, -11.0F, -7.5F, 4.0F, 3.0F, 5.0F,
                                new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.8486F, -0.0846F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r44 = pillars.addOrReplaceChild("cube_r44", CubeListBuilder.create().texOffs(128, 94).addBox(-2.0F,
                -18.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.8622F, -0.2399F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r45 = pillars.addOrReplaceChild("cube_r45", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0F, -9.0F,
                -12.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition pillars2 = pillars
                .addOrReplaceChild(
                        "pillars2", CubeListBuilder.create().texOffs(133, 79).addBox(-2.0F, -15.5884F, -22.7409F, 4.0F,
                                2.0F, 5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r46 = pillars2
                .addOrReplaceChild("cube_r46",
                        CubeListBuilder.create().texOffs(70, 119).addBox(-2.0F, 1.75F, -23.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.404F, -0.4858F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r47 = pillars2
                .addOrReplaceChild(
                        "cube_r47", CubeListBuilder.create().texOffs(44, 134).addBox(-2.0F, 4.75F, -23.75F, 4.0F, 6.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.4821F, -0.433F, -1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r48 = pillars2
                .addOrReplaceChild("cube_r48",
                        CubeListBuilder.create().texOffs(19, 135).addBox(-2.0F, 2.0F, -26.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.4888F, -0.4121F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r49 = pillars2.addOrReplaceChild("cube_r49", CubeListBuilder.create().texOffs(125, 9).addBox(-2.0F,
                -3.75F, -27.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.4488F, -0.3723F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r50 = pillars2
                .addOrReplaceChild(
                        "cube_r50", CubeListBuilder.create().texOffs(132, 25).addBox(-2.0F, -24.0F, -11.0F, 4.0F, 3.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.7427F, -0.5684F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r51 = pillars2
                .addOrReplaceChild(
                        "cube_r51", CubeListBuilder.create().texOffs(91, 130).addBox(-2.0F, -23.25F, -5.25F, 4.0F, 6.0F,
                                5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.7785F, -0.4404F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r52 = pillars2
                .addOrReplaceChild("cube_r52",
                        CubeListBuilder.create().texOffs(133, 70).addBox(-2.0F, -11.0F, -7.5F, 4.0F, 3.0F, 5.0F,
                                new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.8486F, -0.0846F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r53 = pillars2.addOrReplaceChild("cube_r53", CubeListBuilder.create().texOffs(128, 94).addBox(-2.0F,
                -18.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.8622F, -0.2399F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r54 = pillars2.addOrReplaceChild("cube_r54", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0F, -9.0F,
                -12.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition pillars3 = pillars2
                .addOrReplaceChild(
                        "pillars3", CubeListBuilder.create().texOffs(133, 79).addBox(-2.0F, -15.5884F, -22.7409F, 4.0F,
                                2.0F, 5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r55 = pillars3
                .addOrReplaceChild("cube_r55",
                        CubeListBuilder.create().texOffs(70, 119).addBox(-2.0F, 1.75F, -23.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.404F, -0.4858F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r56 = pillars3
                .addOrReplaceChild(
                        "cube_r56", CubeListBuilder.create().texOffs(44, 134).addBox(-2.0F, 4.75F, -23.75F, 4.0F, 6.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.4821F, -0.433F, -1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r57 = pillars3
                .addOrReplaceChild("cube_r57",
                        CubeListBuilder.create().texOffs(19, 135).addBox(-2.0F, 2.0F, -26.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.4888F, -0.4121F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r58 = pillars3.addOrReplaceChild("cube_r58", CubeListBuilder.create().texOffs(125, 9).addBox(-2.0F,
                -3.75F, -27.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.4488F, -0.3723F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r59 = pillars3
                .addOrReplaceChild(
                        "cube_r59", CubeListBuilder.create().texOffs(132, 25).addBox(-2.0F, -24.0F, -11.0F, 4.0F, 3.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.7427F, -0.5684F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r60 = pillars3
                .addOrReplaceChild(
                        "cube_r60", CubeListBuilder.create().texOffs(91, 130).addBox(-2.0F, -23.25F, -5.25F, 4.0F, 6.0F,
                                5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.7785F, -0.4404F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r61 = pillars3
                .addOrReplaceChild("cube_r61",
                        CubeListBuilder.create().texOffs(133, 70).addBox(-2.0F, -11.0F, -7.5F, 4.0F, 3.0F, 5.0F,
                                new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.8486F, -0.0846F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r62 = pillars3.addOrReplaceChild("cube_r62", CubeListBuilder.create().texOffs(128, 94).addBox(-2.0F,
                -18.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.8622F, -0.2399F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r63 = pillars3.addOrReplaceChild("cube_r63", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0F, -9.0F,
                -12.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition pillars4 = pillars3
                .addOrReplaceChild(
                        "pillars4", CubeListBuilder.create().texOffs(133, 79).addBox(-2.0F, -15.5884F, -22.7409F, 4.0F,
                                2.0F, 5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r64 = pillars4
                .addOrReplaceChild("cube_r64",
                        CubeListBuilder.create().texOffs(70, 119).addBox(-2.0F, 1.75F, -23.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.404F, -0.4858F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r65 = pillars4
                .addOrReplaceChild(
                        "cube_r65", CubeListBuilder.create().texOffs(44, 134).addBox(-2.0F, 4.75F, -23.75F, 4.0F, 6.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.4821F, -0.433F, -1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r66 = pillars4
                .addOrReplaceChild("cube_r66",
                        CubeListBuilder.create().texOffs(19, 135).addBox(-2.0F, 2.0F, -26.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.4888F, -0.4121F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r67 = pillars4.addOrReplaceChild("cube_r67", CubeListBuilder.create().texOffs(125, 9).addBox(-2.0F,
                -3.75F, -27.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.4488F, -0.3723F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r68 = pillars4
                .addOrReplaceChild(
                        "cube_r68", CubeListBuilder.create().texOffs(132, 25).addBox(-2.0F, -24.0F, -11.0F, 4.0F, 3.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.7427F, -0.5684F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r69 = pillars4
                .addOrReplaceChild(
                        "cube_r69", CubeListBuilder.create().texOffs(91, 130).addBox(-2.0F, -23.25F, -5.25F, 4.0F, 6.0F,
                                5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.7785F, -0.4404F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r70 = pillars4
                .addOrReplaceChild("cube_r70",
                        CubeListBuilder.create().texOffs(133, 70).addBox(-2.0F, -11.0F, -7.5F, 4.0F, 3.0F, 5.0F,
                                new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.8486F, -0.0846F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r71 = pillars4.addOrReplaceChild("cube_r71", CubeListBuilder.create().texOffs(128, 94).addBox(-2.0F,
                -18.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.8622F, -0.2399F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r72 = pillars4.addOrReplaceChild("cube_r72", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0F, -9.0F,
                -12.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition pillars5 = pillars4
                .addOrReplaceChild(
                        "pillars5", CubeListBuilder.create().texOffs(133, 79).addBox(-2.0F, -15.5884F, -22.7409F, 4.0F,
                                2.0F, 5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r73 = pillars5
                .addOrReplaceChild("cube_r73",
                        CubeListBuilder.create().texOffs(70, 119).addBox(-2.0F, 1.75F, -23.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.404F, -0.4858F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r74 = pillars5
                .addOrReplaceChild(
                        "cube_r74", CubeListBuilder.create().texOffs(44, 134).addBox(-2.0F, 4.75F, -23.75F, 4.0F, 6.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.4821F, -0.433F, -1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r75 = pillars5
                .addOrReplaceChild("cube_r75",
                        CubeListBuilder.create().texOffs(19, 135).addBox(-2.0F, 2.0F, -26.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.4888F, -0.4121F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r76 = pillars5.addOrReplaceChild("cube_r76", CubeListBuilder.create().texOffs(125, 9).addBox(-2.0F,
                -3.75F, -27.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.4488F, -0.3723F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r77 = pillars5
                .addOrReplaceChild(
                        "cube_r77", CubeListBuilder.create().texOffs(132, 25).addBox(-2.0F, -24.0F, -11.0F, 4.0F, 3.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.7427F, -0.5684F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r78 = pillars5
                .addOrReplaceChild(
                        "cube_r78", CubeListBuilder.create().texOffs(91, 130).addBox(-2.0F, -23.25F, -5.25F, 4.0F, 6.0F,
                                5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.7785F, -0.4404F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r79 = pillars5
                .addOrReplaceChild("cube_r79",
                        CubeListBuilder.create().texOffs(133, 70).addBox(-2.0F, -11.0F, -7.5F, 4.0F, 3.0F, 5.0F,
                                new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.8486F, -0.0846F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r80 = pillars5.addOrReplaceChild("cube_r80", CubeListBuilder.create().texOffs(128, 94).addBox(-2.0F,
                -18.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.8622F, -0.2399F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r81 = pillars5.addOrReplaceChild("cube_r81", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0F, -9.0F,
                -12.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition pillars6 = pillars5
                .addOrReplaceChild(
                        "pillars6", CubeListBuilder.create().texOffs(133, 79).addBox(-2.0F, -15.5884F, -22.7409F, 4.0F,
                                2.0F, 5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r82 = pillars6
                .addOrReplaceChild("cube_r82",
                        CubeListBuilder.create().texOffs(70, 119).addBox(-2.0F, 1.75F, -23.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.404F, -0.4858F, -1.4835F, 0.0F, 0.0F));

        PartDefinition cube_r83 = pillars6
                .addOrReplaceChild(
                        "cube_r83", CubeListBuilder.create().texOffs(44, 134).addBox(-2.0F, 4.75F, -23.75F, 4.0F, 6.0F,
                                2.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, -0.4821F, -0.433F, -1.3526F, 0.0F, 0.0F));

        PartDefinition cube_r84 = pillars6
                .addOrReplaceChild("cube_r84",
                        CubeListBuilder.create().texOffs(19, 135).addBox(-2.0F, 2.0F, -26.0F, 4.0F, 6.0F, 2.0F,
                                new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, -0.4888F, -0.4121F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r85 = pillars6.addOrReplaceChild("cube_r85", CubeListBuilder.create().texOffs(125, 9).addBox(-2.0F,
                -3.75F, -27.0F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.4488F, -0.3723F, -0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r86 = pillars6
                .addOrReplaceChild(
                        "cube_r86", CubeListBuilder.create().texOffs(132, 25).addBox(-2.0F, -24.0F, -11.0F, 4.0F, 3.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.7427F, -0.5684F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r87 = pillars6
                .addOrReplaceChild(
                        "cube_r87", CubeListBuilder.create().texOffs(91, 130).addBox(-2.0F, -23.25F, -5.25F, 4.0F, 6.0F,
                                5.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.7785F, -0.4404F, 0.829F, 0.0F, 0.0F));

        PartDefinition cube_r88 = pillars6
                .addOrReplaceChild("cube_r88",
                        CubeListBuilder.create().texOffs(133, 70).addBox(-2.0F, -11.0F, -7.5F, 4.0F, 3.0F, 5.0F,
                                new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.8486F, -0.0846F, 0.5672F, 0.0F, 0.0F));

        PartDefinition cube_r89 = pillars6.addOrReplaceChild("cube_r89", CubeListBuilder.create().texOffs(128, 94).addBox(-2.0F,
                -18.0F, -3.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.8622F, -0.2399F, 0.9599F, 0.0F, 0.0F));

        PartDefinition cube_r90 = pillars6.addOrReplaceChild("cube_r90", CubeListBuilder.create().texOffs(29, 19).addBox(-2.0F, -9.0F,
                -12.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition rim = console.addOrReplaceChild("rim", CubeListBuilder.create().texOffs(91, 113).addBox(18.5F, -16.0F, -5.5F,
                2.0F, 5.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition cube_r91 = rim.addOrReplaceChild("cube_r91",
                CubeListBuilder.create().texOffs(80, 5).addBox(18.5F, -16.0F, -5.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r92 = rim.addOrReplaceChild("cube_r92",
                CubeListBuilder.create().texOffs(29, 38).addBox(18.5F, -16.0F, -0.5F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim2 = rim.addOrReplaceChild("rim2", CubeListBuilder.create().texOffs(91, 113).addBox(18.5F, -16.0F, -5.5F,
                2.0F, 5.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r93 = rim2.addOrReplaceChild("cube_r93",
                CubeListBuilder.create().texOffs(80, 5).addBox(18.5F, -16.0F, -5.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r94 = rim2.addOrReplaceChild("cube_r94",
                CubeListBuilder.create().texOffs(29, 38).addBox(18.5F, -16.0F, -0.5F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim3 = rim2.addOrReplaceChild("rim3", CubeListBuilder.create().texOffs(91, 113).addBox(18.5F, -16.0F, -5.5F,
                2.0F, 5.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r95 = rim3.addOrReplaceChild("cube_r95",
                CubeListBuilder.create().texOffs(80, 5).addBox(18.5F, -16.0F, -5.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r96 = rim3.addOrReplaceChild("cube_r96",
                CubeListBuilder.create().texOffs(29, 38).addBox(18.5F, -16.0F, -0.5F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim4 = rim3.addOrReplaceChild("rim4", CubeListBuilder.create().texOffs(91, 113).addBox(18.5F, -16.0F, -5.5F,
                2.0F, 5.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r97 = rim4.addOrReplaceChild("cube_r97",
                CubeListBuilder.create().texOffs(80, 5).addBox(18.5F, -16.0F, -5.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r98 = rim4.addOrReplaceChild("cube_r98",
                CubeListBuilder.create().texOffs(29, 38).addBox(18.5F, -16.0F, -0.5F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim5 = rim4.addOrReplaceChild("rim5", CubeListBuilder.create().texOffs(91, 113).addBox(18.5F, -16.0F, -5.5F,
                2.0F, 5.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r99 = rim5.addOrReplaceChild("cube_r99",
                CubeListBuilder.create().texOffs(80, 5).addBox(18.5F, -16.0F, -5.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r100 = rim5.addOrReplaceChild("cube_r100",
                CubeListBuilder.create().texOffs(29, 38).addBox(18.5F, -16.0F, -0.5F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rim6 = rim5.addOrReplaceChild("rim6", CubeListBuilder.create().texOffs(91, 113).addBox(18.5F, -16.0F, -5.5F,
                2.0F, 5.0F, 11.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r101 = rim6.addOrReplaceChild("cube_r101",
                CubeListBuilder.create().texOffs(80, 5).addBox(18.5F, -16.0F, -5.5F, 2.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r102 = rim6.addOrReplaceChild("cube_r102",
                CubeListBuilder.create().texOffs(29, 38).addBox(18.5F, -16.0F, -0.5F, 2.0F, 5.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition panels = console.addOrReplaceChild("panels", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition net_r1 = panels.addOrReplaceChild("net_r1", CubeListBuilder.create().texOffs(62, 58).addBox(-4.0F, -22.0F,
                -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r103 = panels.addOrReplaceChild("cube_r103", CubeListBuilder.create().texOffs(29, 38).addBox(-4.0F,
                -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition panels2 = panels.addOrReplaceChild("panels2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition growth_r1 = panels2.addOrReplaceChild("growth_r1", CubeListBuilder.create().texOffs(29, 19).addBox(-4.0F,
                -22.0F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r104 = panels2.addOrReplaceChild("cube_r104", CubeListBuilder.create().texOffs(29, 38).addBox(-4.0F,
                -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition panels3 = panels2.addOrReplaceChild("panels3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition growth_r2 = panels3.addOrReplaceChild("growth_r2", CubeListBuilder.create().texOffs(29, 19).addBox(-4.0F,
                -22.0F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r105 = panels3.addOrReplaceChild("cube_r105", CubeListBuilder.create().texOffs(29, 38).addBox(-4.0F,
                -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition panels4 = panels3.addOrReplaceChild("panels4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition wires_r1 = panels4.addOrReplaceChild("wires_r1",
                CubeListBuilder.create().texOffs(29, 0)
                        .addBox(-3.75F, -23.0F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)).texOffs(62, 58)
                        .addBox(-4.0F, -22.0F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r106 = panels4.addOrReplaceChild("cube_r106", CubeListBuilder.create().texOffs(29, 38).addBox(-4.0F,
                -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition panels5 = panels4.addOrReplaceChild("panels5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cover_r1 = panels5.addOrReplaceChild("cover_r1", CubeListBuilder.create().texOffs(11, 57).addBox(-4.0F, -22.0F,
                -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r107 = panels5.addOrReplaceChild("cube_r107", CubeListBuilder.create().texOffs(29, 38).addBox(-4.0F,
                -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition panels6 = panels5.addOrReplaceChild("panels6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition growth_r3 = panels6.addOrReplaceChild("growth_r3", CubeListBuilder.create().texOffs(29, 19).addBox(-4.0F,
                -22.0F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition cube_r108 = panels6.addOrReplaceChild("cube_r108", CubeListBuilder.create().texOffs(29, 38).addBox(-4.0F,
                -21.5F, -9.0F, 16.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition bone = console.addOrReplaceChild("bone",
                CubeListBuilder.create().texOffs(15, 0).addBox(-0.1F, -23.5F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.25F, 0.0F));

        PartDefinition bone2 = bone.addOrReplaceChild("bone2",
                CubeListBuilder.create().texOffs(15, 0).addBox(-0.1F, -23.5F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone3 = bone2.addOrReplaceChild("bone3",
                CubeListBuilder.create().texOffs(15, 0).addBox(-0.1F, -23.5F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4",
                CubeListBuilder.create().texOffs(15, 0).addBox(-0.1F, -23.5F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5",
                CubeListBuilder.create().texOffs(15, 0).addBox(-0.1F, -23.5F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6",
                CubeListBuilder.create().texOffs(15, 0).addBox(-0.1F, -23.5F, -4.0F, 7.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition controls = console.addOrReplaceChild("controls", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ctrl_1 = controls.addOrReplaceChild("ctrl_1", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone13 = ctrl_1.addOrReplaceChild("bone13", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -17.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        PartDefinition panel = bone13.addOrReplaceChild("panel", CubeListBuilder.create().texOffs(44, 119).addBox(-1.0F, -4.0F, -5.0F,
                1.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, -1.25F, -2.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition switch0 = panel.addOrReplaceChild("switch0", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, -3.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r109 = switch0.addOrReplaceChild("cube_r109",
                CubeListBuilder.create().texOffs(65, 145).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch2 = panel.addOrReplaceChild("switch2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, -2.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r110 = switch2.addOrReplaceChild("cube_r110",
                CubeListBuilder.create().texOffs(91, 142).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch3 = panel.addOrReplaceChild("switch3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, -0.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r111 = switch3.addOrReplaceChild("cube_r111",
                CubeListBuilder.create().texOffs(6, 141).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch4 = panel.addOrReplaceChild("switch4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r112 = switch4.addOrReplaceChild("cube_r112",
                CubeListBuilder.create().texOffs(92, 99).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch29 = panel.addOrReplaceChild("switch29", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, 1.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r113 = switch29.addOrReplaceChild("cube_r113",
                CubeListBuilder.create().texOffs(92, 99).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch5 = panel.addOrReplaceChild("switch5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, 2.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r114 = switch5.addOrReplaceChild("cube_r114",
                CubeListBuilder.create().texOffs(0, 141).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch6 = panel.addOrReplaceChild("switch6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -1.0F, 3.5F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r115 = switch6.addOrReplaceChild("cube_r115",
                CubeListBuilder.create().texOffs(140, 87).addBox(0.0F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition remote = bone13.addOrReplaceChild("remote", CubeListBuilder.create().texOffs(110, 133).addBox(-1.0F, -2.0F,
                -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.5F, -5.5F, -0.5F));

        PartDefinition cube_r116 = remote.addOrReplaceChild("cube_r116",
                CubeListBuilder.create().texOffs(107, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.3F, -1.0F, -2.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition button = remote.addOrReplaceChild("button",
                CubeListBuilder.create().texOffs(153, 89).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.9F, -1.6F));

        PartDefinition button2 = remote.addOrReplaceChild("button2",
                CubeListBuilder.create().texOffs(153, 83).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.9F, -0.85F));

        PartDefinition button3 = remote.addOrReplaceChild("button3",
                CubeListBuilder.create().texOffs(68, 153).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.9F, -0.1F));

        PartDefinition button4 = remote.addOrReplaceChild("button4",
                CubeListBuilder.create().texOffs(153, 58).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.9F, 0.65F));

        PartDefinition button5 = remote.addOrReplaceChild("button5",
                CubeListBuilder.create().texOffs(46, 153).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.15F, -1.6F));

        PartDefinition button6 = remote.addOrReplaceChild("button6",
                CubeListBuilder.create().texOffs(41, 153).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.15F, -0.85F));

        PartDefinition button7 = remote.addOrReplaceChild("button7",
                CubeListBuilder.create().texOffs(153, 27).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.15F, -0.1F));

        PartDefinition button8 = remote.addOrReplaceChild("button8",
                CubeListBuilder.create().texOffs(153, 18).addBox(0.0F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-0.5F, -0.15F, 0.65F));

        PartDefinition port = bone13.addOrReplaceChild("port",
                CubeListBuilder.create().texOffs(150, 107).addBox(-1.8F, -2.5F, -1.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(142, 21).addBox(-0.8F, -1.0F, -1.5F, 1.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(63, 150)
                        .addBox(-1.8F, -2.5F, 0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(153, 15)
                        .addBox(-1.8F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 153)
                        .addBox(-1.8F, -2.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, -9.0F, 0.0F));

        PartDefinition cube_r117 = port.addOrReplaceChild("cube_r117",
                CubeListBuilder.create().texOffs(133, 79).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.25F, 0.0F, -1.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r118 = port.addOrReplaceChild("cube_r118",
                CubeListBuilder.create().texOffs(127, 148).addBox(-1.0F, -5.0F, 0.0F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.25F, 0.0F, 1.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r119 = port.addOrReplaceChild("cube_r119",
                CubeListBuilder.create().texOffs(115, 9).addBox(0.0F, -2.0F, -0.5F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.3F, -1.0F, -1.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition compass = bone13.addOrReplaceChild("compass",
                CubeListBuilder.create().texOffs(144, 87).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.4F))
                        .texOffs(70, 148).addBox(-0.55F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.offset(1.0F, -3.0F, 5.0F));

        PartDefinition cube_r120 = compass.addOrReplaceChild("cube_r120",
                CubeListBuilder.create().texOffs(73, 62).addBox(0.0F, -1.25F, -1.75F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.8F, -2.25F, -0.75F, 0.0F, 0.0F, -3.1416F));

        PartDefinition needle = compass.addOrReplaceChild("needle", CubeListBuilder.create().texOffs(64, 119).addBox(-0.25F, -1.5F,
                -0.25F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition insert = bone13.addOrReplaceChild("insert",
                CubeListBuilder.create().texOffs(58, 150).addBox(-1.0F, -1.5F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.25F, -3.75F, -0.3054F, 0.0F, 0.0F));

        PartDefinition bone96 = insert.addOrReplaceChild("bone96",
                CubeListBuilder.create().texOffs(43, 148).addBox(-1.0F, -3.0F, 0.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.25F, 1.5F, -0.5F));

        PartDefinition box = bone13.addOrReplaceChild("box",
                CubeListBuilder.create().texOffs(148, 19).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -8.0F, 3.25F, -0.8727F, 0.0F, 0.0F));

        PartDefinition bone109 = box.addOrReplaceChild("bone109", CubeListBuilder.create().texOffs(10, 153).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)), PartPose.offset(-0.5F, 1.2F, 0.0F));

        PartDefinition ctrl_2 = controls.addOrReplaceChild("ctrl_2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone7 = ctrl_2.addOrReplaceChild("bone7",
                CubeListBuilder.create().texOffs(148, 7).addBox(-1.0F, -3.5F, -4.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(19.0F, -17.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        PartDefinition cube_r121 = bone7.addOrReplaceChild("cube_r121",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -9.0F, 2.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r122 = bone7.addOrReplaceChild("cube_r122",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -9.5F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r123 = bone7.addOrReplaceChild("cube_r123",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -6.0F, -0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r124 = bone7.addOrReplaceChild("cube_r124",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -5.5F, 3.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r125 = bone7.addOrReplaceChild("cube_r125",
                CubeListBuilder.create().texOffs(140, 34).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -4.0F, 2.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r126 = bone7.addOrReplaceChild("cube_r126",
                CubeListBuilder.create().texOffs(73, 62).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -9.0F, -2.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r127 = bone7.addOrReplaceChild("cube_r127",
                CubeListBuilder.create().texOffs(73, 62).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.8F, -3.5F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition ball = bone7.addOrReplaceChild("ball",
                CubeListBuilder.create().texOffs(16, 144).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(144, 14).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-0.8F, -2.5F, 3.5F));

        PartDefinition ball2 = bone7.addOrReplaceChild("ball2",
                CubeListBuilder.create().texOffs(16, 144).addBox(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(144, 14).addBox(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-0.8F, -11.25F, 0.0F));

        PartDefinition knob = bone7.addOrReplaceChild("knob",
                CubeListBuilder.create().texOffs(25, 144).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                        .texOffs(143, 147).addBox(0.25F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)),
                PartPose.offsetAndRotation(-0.75F, -6.5F, 1.25F, 0.9163F, 0.0F, 0.0F));

        PartDefinition wires = bone7.addOrReplaceChild("wires", CubeListBuilder.create().texOffs(127, 137).addBox(-1.2F, -2.0F, -0.5F,
                2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -5.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cube_r128 = wires.addOrReplaceChild("cube_r128",
                CubeListBuilder.create().texOffs(96, 147).addBox(0.0F, -2.0F, 1.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.7F, 0.0F, 0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition spring = bone7.addOrReplaceChild("spring",
                CubeListBuilder.create().texOffs(80, 29).addBox(0.0F, -2.0F, -2.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(135, 133).addBox(0.0F, 1.0F, -1.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(143, 136)
                        .addBox(0.0F, -2.0F, -1.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, -4.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition cube_r129 = spring.addOrReplaceChild("cube_r129",
                CubeListBuilder.create().texOffs(80, 29).addBox(-0.1846F, -0.5858F, -2.4021F, 0.0F, 1.0F, 6.0F,
                        new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.4975F, -3.0811F, 3.2527F, -0.7897F, 0.0924F, 3.0488F));

        PartDefinition ctrl_3 = controls.addOrReplaceChild("ctrl_3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone14 = ctrl_3.addOrReplaceChild("bone14", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -17.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        PartDefinition panel2 = bone14.addOrReplaceChild("panel2",
                CubeListBuilder.create().texOffs(59, 126).addBox(-1.0F, -2.0F, -4.0F, 1.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, -2.5F, -2.0F, -0.0436F, 0.0F, 0.0F));

        PartDefinition switch7 = panel2.addOrReplaceChild("switch7", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.5F, -3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r130 = switch7.addOrReplaceChild("cube_r130",
                CubeListBuilder.create().texOffs(139, 130).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch8 = panel2.addOrReplaceChild("switch8", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.5F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r131 = switch8.addOrReplaceChild("cube_r131",
                CubeListBuilder.create().texOffs(124, 137).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch9 = panel2.addOrReplaceChild("switch9", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r132 = switch9.addOrReplaceChild("cube_r132",
                CubeListBuilder.create().texOffs(135, 70).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch10 = panel2.addOrReplaceChild("switch10", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.5F, 1.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r133 = switch10.addOrReplaceChild("cube_r133",
                CubeListBuilder.create().texOffs(133, 87).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch11 = panel2.addOrReplaceChild("switch11", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.5F, 3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r134 = switch11.addOrReplaceChild("cube_r134",
                CubeListBuilder.create().texOffs(132, 34).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch12 = panel2.addOrReplaceChild("switch12", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.5F, -3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r135 = switch12.addOrReplaceChild("cube_r135",
                CubeListBuilder.create().texOffs(131, 110).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch13 = panel2.addOrReplaceChild("switch13", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.5F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r136 = switch13.addOrReplaceChild("cube_r136",
                CubeListBuilder.create().texOffs(114, 130).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch14 = panel2.addOrReplaceChild("switch14", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r137 = switch14.addOrReplaceChild("cube_r137",
                CubeListBuilder.create().texOffs(128, 119).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch15 = panel2.addOrReplaceChild("switch15", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.5F, 1.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r138 = switch15.addOrReplaceChild("cube_r138",
                CubeListBuilder.create().texOffs(78, 128).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch16 = panel2.addOrReplaceChild("switch16", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.5F, 3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r139 = switch16.addOrReplaceChild("cube_r139",
                CubeListBuilder.create().texOffs(70, 128).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch17 = panel2.addOrReplaceChild("switch17", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -0.5F, -3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r140 = switch17.addOrReplaceChild("cube_r140",
                CubeListBuilder.create().texOffs(20, 126).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch18 = panel2.addOrReplaceChild("switch18", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -0.5F, -2.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r141 = switch18.addOrReplaceChild("cube_r141",
                CubeListBuilder.create().texOffs(12, 126).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch19 = panel2.addOrReplaceChild("switch19", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r142 = switch19.addOrReplaceChild("cube_r142",
                CubeListBuilder.create().texOffs(4, 126).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch20 = panel2.addOrReplaceChild("switch20", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -0.5F, 1.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r143 = switch20.addOrReplaceChild("cube_r143",
                CubeListBuilder.create().texOffs(0, 126).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch21 = panel2.addOrReplaceChild("switch21", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, -0.5F, 3.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r144 = switch21.addOrReplaceChild("cube_r144",
                CubeListBuilder.create().texOffs(99, 121).addBox(0.0F, 0.0F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition wiggles = bone14.addOrReplaceChild("wiggles",
                CubeListBuilder.create().texOffs(147, 97).addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.0F, -7.0F, 2.75F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r145 = wiggles.addOrReplaceChild("cube_r145",
                CubeListBuilder.create().texOffs(73, 69).addBox(0.0F, -3.0F, -1.0F, 1.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.1F, 0.0F, 2.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r146 = wiggles.addOrReplaceChild("cube_r146",
                CubeListBuilder.create().texOffs(25, 152).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.099F, 0.2588F, 1.0341F, 2.8798F, 0.0F, 3.1416F));

        PartDefinition cube_r147 = wiggles.addOrReplaceChild("cube_r147",
                CubeListBuilder.create().texOffs(133, 87).addBox(1.0F, -1.0F, -3.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.6F, 0.0F, -1.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r148 = wiggles.addOrReplaceChild("cube_r148",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.0F, 2.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, -2.35F, -4.0F, -2.8798F, 0.0F, 3.1416F));

        PartDefinition cube_r149 = wiggles.addOrReplaceChild("cube_r149",
                CubeListBuilder.create().texOffs(138, 144).addBox(0.0F, -2.0F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, -0.5F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition ball3 = bone14.addOrReplaceChild("ball3",
                CubeListBuilder.create().texOffs(143, 142).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(100, 143).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(-0.8F, -10.5F, -1.0F));

        PartDefinition cork = bone14.addOrReplaceChild("cork",
                CubeListBuilder.create().texOffs(57, 119).addBox(-1.5F, -2.0F, -2.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(153, 6).addBox(-1.0F, -1.5F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(153, 6)
                        .addBox(-1.0F, -1.5F, 0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(150, 42)
                        .addBox(-1.0F, -1.5F, -0.75F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(147, 114)
                        .addBox(-0.4F, -2.0F, -1.5F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.5F, 5.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r150 = cork.addOrReplaceChild("cube_r150",
                CubeListBuilder.create().texOffs(139, 119).addBox(0.0F, -0.5F, -4.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.4F, -1.0F, 1.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition dial = bone14.addOrReplaceChild("dial", CubeListBuilder.create(),
                PartPose.offsetAndRotation(-0.6F, -6.75F, -3.75F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r151 = dial.addOrReplaceChild("cube_r151",
                CubeListBuilder.create().texOffs(80, 19).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition ctrl_4 = controls.addOrReplaceChild("ctrl_4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone15 = ctrl_4.addOrReplaceChild("bone15", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -17.0F, 0.0F, 0.0F, 0.0F, -1.1781F));

        PartDefinition phone = bone15.addOrReplaceChild("phone",
                CubeListBuilder.create().texOffs(40, 38).addBox(-0.5F, -2.0F, -0.5F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -8.5F, -2.0F));

        PartDefinition cube_r152 = phone.addOrReplaceChild("cube_r152",
                CubeListBuilder.create().texOffs(66, 139).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.25F, 0.5F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition keypad = bone15.addOrReplaceChild("keypad",
                CubeListBuilder.create().texOffs(118, 137)
                        .addBox(-0.05F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(75, 136)
                        .addBox(0.05F, -1.25F, -1.75F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-1.0F, -3.5F, 3.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r153 = keypad.addOrReplaceChild("cube_r153",
                CubeListBuilder.create().texOffs(0, 126).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r154 = keypad.addOrReplaceChild("cube_r154",
                CubeListBuilder.create().texOffs(88, 19).addBox(0.0F, -0.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.9F, 0.0F, -0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition valve = bone15.addOrReplaceChild("valve",
                CubeListBuilder.create().texOffs(128, 119).addBox(0.2F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 137).addBox(-0.05F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(73, 143)
                        .addBox(0.2F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(147, 102)
                        .addBox(1.2F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(-1.0F, -8.5F, 1.0F));

        PartDefinition cube_r155 = valve.addOrReplaceChild("cube_r155",
                CubeListBuilder.create().texOffs(120, 9).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.95F, -0.25F, 0.25F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r156 = valve.addOrReplaceChild("cube_r156",
                CubeListBuilder.create().texOffs(0, 126).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition keypad2 = bone15.addOrReplaceChild("keypad2",
                CubeListBuilder.create().texOffs(118, 137)
                        .addBox(-0.05F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(125, 9)
                        .addBox(0.0F, -1.75F, -1.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(-1.0F, -4.0F, -0.5F));

        PartDefinition cube_r157 = keypad2.addOrReplaceChild("cube_r157",
                CubeListBuilder.create().texOffs(29, 19).addBox(0.0F, -0.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.45F, -1.75F, 1.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r158 = keypad2.addOrReplaceChild("cube_r158",
                CubeListBuilder.create().texOffs(80, 38).addBox(0.0F, -0.5F, -0.5F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 38).addBox(0.0F, -0.5F, -1.25F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(32, 141)
                        .addBox(0.0F, -1.5F, -2.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.45F, 2.25F, 1.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r159 = keypad2.addOrReplaceChild("cube_r159",
                CubeListBuilder.create().texOffs(0, 126).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition button47 = keypad2.addOrReplaceChild("button47", CubeListBuilder.create().texOffs(0, 153).addBox(-0.75F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.95F, 0.75F, -0.75F));

        PartDefinition switch22 = keypad2.addOrReplaceChild("switch22", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r160 = switch22.addOrReplaceChild("cube_r160",
                CubeListBuilder.create().texOffs(57, 119).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, -0.25F, 0.0F, 0.0F, -3.1416F));

        PartDefinition switch23 = keypad2.addOrReplaceChild("switch23", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.75F, 0.0F, 0.75F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r161 = switch23.addOrReplaceChild("cube_r161",
                CubeListBuilder.create().texOffs(50, 119).addBox(0.0F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, -0.25F, 0.0F, 0.0F, -3.1416F));

        PartDefinition knob4 = keypad2.addOrReplaceChild("knob4",
                CubeListBuilder.create().texOffs(153, 3).addBox(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                        .texOffs(80, 16).addBox(0.75F, 0.0F, -0.5F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.75F, 0.5F, 0.4363F, 0.0F, 0.0F));

        PartDefinition knob2 = bone15.addOrReplaceChild("knob2", CubeListBuilder.create(),
                PartPose.offset(-1.0F, -2.0F, -3.5F));

        PartDefinition cube_r162 = knob2.addOrReplaceChild("cube_r162",
                CubeListBuilder.create().texOffs(119, 56).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone16 = knob2.addOrReplaceChild("bone16",
                CubeListBuilder.create().texOffs(44, 143).addBox(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(-0.8F, 0.0F, 0.0F));

        PartDefinition button9 = bone15.addOrReplaceChild("button9", CubeListBuilder.create(),
                PartPose.offset(-1.0F, -4.5F, -3.5F));

        PartDefinition cube_r163 = button9.addOrReplaceChild("cube_r163",
                CubeListBuilder.create().texOffs(44, 119).addBox(0.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.3F, 0.75F, -0.5F, 0.0F, 0.0F, -3.1416F));

        PartDefinition cube_r164 = button9.addOrReplaceChild("cube_r164",
                CubeListBuilder.create().texOffs(119, 56).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone18 = button9.addOrReplaceChild("bone18", CubeListBuilder.create().texOffs(146, 152).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.2F, 0.0F, 0.0F));

        PartDefinition knob3 = bone15.addOrReplaceChild("knob3", CubeListBuilder.create(),
                PartPose.offset(-1.0F, -2.5F, 5.75F));

        PartDefinition cube_r165 = knob3.addOrReplaceChild("cube_r165",
                CubeListBuilder.create().texOffs(119, 56).addBox(0.0F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.2F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition bone17 = knob3.addOrReplaceChild("bone17",
                CubeListBuilder.create().texOffs(142, 92).addBox(0.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(-0.8F, 0.0F, 0.0F));

        PartDefinition tube = bone15.addOrReplaceChild("tube",
                CubeListBuilder.create().texOffs(112, 113).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(91, 130).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(5, 153)
                        .addBox(-1.0F, -1.0F, -0.6F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(85, 16)
                        .addBox(-1.0F, 0.75F, -1.0F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -7.0F, -4.5F));

        PartDefinition switch24 = bone15.addOrReplaceChild("switch24", CubeListBuilder.create().texOffs(57, 139).addBox(-0.8F, -1.0F,
                -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(-1.25F, -8.0F, 3.25F));

        PartDefinition bone19 = switch24.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(142, 130).addBox(0.4F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offset(-0.8F, 0.0F, 0.0F));

        PartDefinition switch25 = bone15.addOrReplaceChild("switch25", CubeListBuilder.create().texOffs(57, 139).addBox(-0.8F, -1.0F,
                -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(-1.25F, -7.25F, 4.5F));

        PartDefinition bone20 = switch25.addOrReplaceChild("bone20", CubeListBuilder.create().texOffs(142, 130).addBox(0.4F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)), PartPose.offset(-0.8F, 0.0F, 0.0F));

        PartDefinition ctrl_5 = controls.addOrReplaceChild("ctrl_5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone21 = ctrl_5.addOrReplaceChild("bone21",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.8F, 2.0F, 1.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 38).addBox(0.8F, 7.5F, 3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 38)
                        .addBox(0.8F, 2.0F, 3.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(19.0F, -17.0F, 0.0F, 0.0F, 0.0F, 1.9635F));

        PartDefinition keypad3 = bone21.addOrReplaceChild("keypad3",
                CubeListBuilder.create().texOffs(107, 113).addBox(0.8F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 119).addBox(0.8F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.3F)),
                PartPose.offset(0.0F, 5.25F, -2.5F));

        PartDefinition button10 = keypad3.addOrReplaceChild("button10", CubeListBuilder.create().texOffs(152, 144).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, -1.2F));

        PartDefinition button11 = keypad3.addOrReplaceChild("button11", CubeListBuilder.create().texOffs(141, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, -0.4F));

        PartDefinition button12 = keypad3.addOrReplaceChild("button12", CubeListBuilder.create().texOffs(152, 138).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, 0.4F));

        PartDefinition button13 = keypad3.addOrReplaceChild("button13", CubeListBuilder.create().texOffs(106, 153).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, 1.2F));

        PartDefinition button14 = keypad3.addOrReplaceChild("button14", CubeListBuilder.create().texOffs(96, 154).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, -1.2F));

        PartDefinition button15 = keypad3.addOrReplaceChild("button15", CubeListBuilder.create().texOffs(136, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, -0.4F));

        PartDefinition button16 = keypad3.addOrReplaceChild("button16", CubeListBuilder.create().texOffs(131, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, 0.4F));

        PartDefinition button17 = keypad3.addOrReplaceChild("button17", CubeListBuilder.create().texOffs(122, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, 1.2F));

        PartDefinition button18 = keypad3.addOrReplaceChild("button18", CubeListBuilder.create().texOffs(117, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, -1.2F));

        PartDefinition button19 = keypad3.addOrReplaceChild("button19", CubeListBuilder.create().texOffs(152, 113).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, -0.4F));

        PartDefinition button20 = keypad3.addOrReplaceChild("button20", CubeListBuilder.create().texOffs(112, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, 0.4F));

        PartDefinition button21 = keypad3.addOrReplaceChild("button21", CubeListBuilder.create().texOffs(153, 101).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, 1.2F));

        PartDefinition button22 = keypad3.addOrReplaceChild("button22", CubeListBuilder.create().texOffs(91, 154).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, -1.2F));

        PartDefinition button23 = keypad3.addOrReplaceChild("button23", CubeListBuilder.create().texOffs(154, 70).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, -0.4F));

        PartDefinition button24 = keypad3.addOrReplaceChild("button24", CubeListBuilder.create().texOffs(154, 45).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, 0.4F));

        PartDefinition button25 = keypad3.addOrReplaceChild("button25", CubeListBuilder.create().texOffs(154, 98).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, 1.2F));

        PartDefinition keypad4 = bone21.addOrReplaceChild("keypad4",
                CubeListBuilder.create().texOffs(107, 113).addBox(0.8F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 119).addBox(0.8F, -1.5F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.3F)),
                PartPose.offset(0.0F, 5.25F, 2.5F));

        PartDefinition button26 = keypad4.addOrReplaceChild("button26", CubeListBuilder.create().texOffs(101, 152).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, -1.2F));

        PartDefinition button27 = keypad4.addOrReplaceChild("button27", CubeListBuilder.create().texOffs(152, 95).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, -0.4F));

        PartDefinition button28 = keypad4.addOrReplaceChild("button28", CubeListBuilder.create().texOffs(152, 76).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, 0.4F));

        PartDefinition button29 = keypad4.addOrReplaceChild("button29", CubeListBuilder.create().texOffs(30, 154).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 1.2F, 1.2F));

        PartDefinition button30 = keypad4.addOrReplaceChild("button30",
                CubeListBuilder.create().texOffs(154, 0).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(0.625F, 0.4F, -1.2F));

        PartDefinition button31 = keypad4.addOrReplaceChild("button31", CubeListBuilder.create().texOffs(76, 152).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, -0.4F));

        PartDefinition button32 = keypad4.addOrReplaceChild("button32", CubeListBuilder.create().texOffs(152, 73).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, 0.4F));

        PartDefinition button33 = keypad4.addOrReplaceChild("button33", CubeListBuilder.create().texOffs(152, 67).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, 0.4F, 1.2F));

        PartDefinition button34 = keypad4.addOrReplaceChild("button34", CubeListBuilder.create().texOffs(152, 62).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, -1.2F));

        PartDefinition button35 = keypad4.addOrReplaceChild("button35", CubeListBuilder.create().texOffs(152, 53).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, -0.4F));

        PartDefinition button36 = keypad4.addOrReplaceChild("button36", CubeListBuilder.create().texOffs(53, 152).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, 0.4F));

        PartDefinition button37 = keypad4.addOrReplaceChild("button37", CubeListBuilder.create().texOffs(36, 152).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -0.4F, 1.2F));

        PartDefinition button38 = keypad4.addOrReplaceChild("button38", CubeListBuilder.create().texOffs(151, 153).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, -1.2F));

        PartDefinition button39 = keypad4.addOrReplaceChild("button39", CubeListBuilder.create().texOffs(153, 121).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, -0.4F));

        PartDefinition button40 = keypad4.addOrReplaceChild("button40", CubeListBuilder.create().texOffs(153, 118).addBox(-0.5F,
                -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, 0.4F));

        PartDefinition button41 = keypad4.addOrReplaceChild("button41", CubeListBuilder.create().texOffs(154, 79).addBox(-0.5F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(0.625F, -1.2F, 1.2F));

        PartDefinition ring = bone21.addOrReplaceChild("ring",
                CubeListBuilder.create().texOffs(80, 19).addBox(0.8F, -2.0F, -1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(14, 149).addBox(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(90, 8)
                        .addBox(0.0F, -0.5F, -0.2F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(87, 47).addBox(0.0F, -0.5F,
                                1.2F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 9.5F, 0.5F));

        PartDefinition knob5 = bone21.addOrReplaceChild("knob5",
                CubeListBuilder.create().texOffs(119, 56).addBox(0.2F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.6F, 8.5F, -2.25F));

        PartDefinition bone22 = knob5.addOrReplaceChild("bone22", CubeListBuilder.create(),
                PartPose.offset(-0.8F, 0.0F, 0.0F));

        PartDefinition cube_r166 = bone22.addOrReplaceChild("cube_r166", CubeListBuilder.create().texOffs(142, 92).addBox(-2.0F,
                -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition keyboard = bone21.addOrReplaceChild("keyboard", CubeListBuilder.create().texOffs(119, 101).addBox(-1.0F, -1.0F,
                -3.0F, 1.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 12.0F, 0.0F));

        PartDefinition switch26 = keyboard.addOrReplaceChild("switch26",
                CubeListBuilder.create().texOffs(115, 9).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offset(-1.0F, 0.5F, 0.0F));

        PartDefinition switch27 = keyboard.addOrReplaceChild("switch27", CubeListBuilder.create().texOffs(107, 113).addBox(0.0F,
                -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.0F, 0.5F, -1.25F));

        PartDefinition switch28 = keyboard.addOrReplaceChild("switch28", CubeListBuilder.create().texOffs(91, 113).addBox(0.0F, -0.5F,
                -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offset(-1.0F, 0.5F, 1.25F));

        PartDefinition tubes = bone21.addOrReplaceChild("tubes",
                CubeListBuilder.create().texOffs(29, 38).addBox(0.0F, -2.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(93, 19).addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(91, 142)
                        .addBox(0.3F, 0.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(9, 0)
                        .addBox(0.0F, -4.0F, 0.0F, 1.0F, 9.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(113, 56)
                        .addBox(0.0F, -4.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(113, 70)
                        .addBox(-0.25F, 0.0F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(92, 107)
                        .addBox(-0.35F, 0.25F, 0.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 4.0F, -6.0F));

        PartDefinition wiggles2 = bone21.addOrReplaceChild("wiggles2",
                CubeListBuilder.create().texOffs(138, 144).addBox(0.8F, -2.5F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 4.0F, 5.75F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r167 = wiggles2.addOrReplaceChild("cube_r167",
                CubeListBuilder.create().texOffs(107, 0).addBox(0.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.102F, -0.4824F, 1.75F, 2.8798F, 0.0F, 0.0F));

        PartDefinition cube_r168 = wiggles2.addOrReplaceChild("cube_r168",
                CubeListBuilder.create().texOffs(81, 134).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.101F, -0.7412F, 0.7841F, 2.8798F, 0.0F, 0.0F));

        PartDefinition cube_r169 = wiggles2.addOrReplaceChild("cube_r169",
                CubeListBuilder.create().texOffs(0, 149).addBox(1.0F, -1.0F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.1F, 0.25F, -0.75F, -0.2618F, 0.0F, 0.0F));

        PartDefinition cube_r170 = wiggles2.addOrReplaceChild("cube_r170",
                CubeListBuilder.create().texOffs(147, 97).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -3.1416F));

        PartDefinition copper = bone21.addOrReplaceChild("copper",
                CubeListBuilder.create().texOffs(147, 79)
                        .addBox(0.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(-0.001F)).texOffs(133, 144)
                        .addBox(0.8F, -2.5F, -1.0F, 0.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(28, 149)
                        .addBox(-0.2F, -2.5F, -1.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(50, 148)
                        .addBox(-0.2F, -2.5F, 1.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.75F, -1.5F, -1.4399F, 0.0F, 0.0F));

        PartDefinition ctrl_6 = controls.addOrReplaceChild("ctrl_6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone23 = ctrl_6.addOrReplaceChild("bone23",
                CubeListBuilder.create().texOffs(95, 38).addBox(0.8F, 4.0F, -2.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 38).addBox(0.8F, 6.75F, 0.75F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(19.0F, -17.0F, 0.0F, 0.0F, 0.0F, 1.9635F));

        PartDefinition crystal = bone23.addOrReplaceChild("crystal",
                CubeListBuilder.create().texOffs(80, 19).addBox(0.8F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 2.0F, -1.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition bone24 = crystal.addOrReplaceChild("bone24", CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.45F, 0.0F, 0.25F, 0.0F, -0.6109F, 0.0F));

        PartDefinition cube_r171 = bone24.addOrReplaceChild("cube_r171",
                CubeListBuilder.create().texOffs(142, 48).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition box2 = bone23.addOrReplaceChild("box2",
                CubeListBuilder.create().texOffs(115, 30).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(136, 61)
                        .addBox(-1.5F, 2.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(136, 56)
                        .addBox(-1.5F, -3.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(108, 140)
                        .addBox(-2.0F, 2.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(140, 107)
                        .addBox(-2.0F, -3.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(0, 0)
                        .addBox(-1.0F, -4.5F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(-0.7F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        PartDefinition ball4 = bone23.addOrReplaceChild("ball4",
                CubeListBuilder.create().texOffs(142, 43).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(142, 38).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(1.2F, 11.0F, 1.0F));

        PartDefinition lights = bone23.addOrReplaceChild("lights", CubeListBuilder.create().texOffs(91, 113).addBox(-1.0F, -2.5653F,
                -1.9957F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.5F, 6.5F, -3.25F, 0.1309F, 0.0F, 0.0F));

        PartDefinition button42 = lights.addOrReplaceChild("button42", CubeListBuilder.create().texOffs(20, 152).addBox(-0.25F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.0F, 1.4347F, -0.4957F));

        PartDefinition bone25 = button42.addOrReplaceChild("bone25", CubeListBuilder.create().texOffs(151, 130).addBox(-1.0F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.4F, 0.0F, 0.0F));

        PartDefinition bone10 = bone25.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(31, 151).addBox(-1.0F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition button43 = lights.addOrReplaceChild("button43", CubeListBuilder.create().texOffs(151, 92).addBox(-0.25F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.0F, -0.0653F, -0.4957F));

        PartDefinition bone26 = button43.addOrReplaceChild("bone26",
                CubeListBuilder.create().texOffs(151, 86).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.4F, 0.0F, 0.0F));

        PartDefinition bone11 = bone26.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(151, 30).addBox(-1.0F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition button44 = lights.addOrReplaceChild("button44", CubeListBuilder.create().texOffs(151, 50).addBox(-0.25F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(-1.0F, -1.5653F, -0.4957F));

        PartDefinition bone27 = button44.addOrReplaceChild("bone27",
                CubeListBuilder.create().texOffs(151, 37).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.4F, 0.0F, 0.0F));

        PartDefinition bone12 = bone27.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(151, 12).addBox(-1.0F, -0.5F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition tube2 = bone23.addOrReplaceChild("tube2",
                CubeListBuilder.create().texOffs(53, 144).addBox(0.0F, -3.0F, 0.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(91, 147).addBox(0.0F, -2.5F, 0.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.1F)).texOffs(146, 109)
                        .addBox(0.8F, 1.4F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(31, 146)
                        .addBox(0.8F, -2.4F, -1.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 6.0F, 4.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition cylinder = bone23.addOrReplaceChild("cylinder",
                CubeListBuilder.create().texOffs(9, 141).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(36, 149).addBox(-3.0F, 1.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(7, 11)
                        .addBox(-3.0F, -1.0F, -1.0F, 1.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(84, 107)
                        .addBox(-3.0F, -1.0F, -1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(102, 0)
                        .addBox(-3.0F, -1.0F, 1.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(1.0F, 2.0F, -4.0F));

        PartDefinition cube_r172 = cylinder.addOrReplaceChild("cube_r172",
                CubeListBuilder.create().texOffs(73, 62).addBox(0.0F, -1.25F, -2.75F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.2F, 0.5F, -0.75F, 0.2618F, 0.0F, 0.0F));

        PartDefinition pedal = bone23.addOrReplaceChild("pedal",
                CubeListBuilder.create().texOffs(32, 135).addBox(0.0F, -2.0F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.6F, 3.0F, 3.0F));

        PartDefinition bone28 = pedal.addOrReplaceChild("bone28",
                CubeListBuilder.create().texOffs(134, 24).addBox(0.0F, 0.0F, -0.5F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(81, 128).addBox(-0.1F, 0.25F, -0.25F, 0.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(29, 16)
                        .addBox(0.0F, 3.0F, -0.5F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition wires2 = bone23.addOrReplaceChild("wires2",
                CubeListBuilder.create().texOffs(115, 9).addBox(0.0F, -4.0F, -5.0F, 0.0F, 11.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.5F, 3.0F, 4.0F));

        PartDefinition p_ctrl_1 = controls.addOrReplaceChild("p_ctrl_1", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone29 = p_ctrl_1.addOrReplaceChild("bone29", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -20.0F, 0.0F, 0.0F, 0.0F, 2.0071F));

        PartDefinition lever = bone29.addOrReplaceChild("lever",
                CubeListBuilder.create().texOffs(134, 18)
                        .addBox(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(-0.201F)).texOffs(147, 0)
                        .addBox(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.201F)).texOffs(138, 0)
                        .addBox(-1.0F, -1.5F, -0.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.201F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r173 = lever
                .addOrReplaceChild(
                        "cube_r173", CubeListBuilder.create().texOffs(139, 125).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F,
                                2.0F, new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition bone30 = lever.addOrReplaceChild("bone30",
                CubeListBuilder.create().texOffs(41, 12).addBox(-1.85F, -0.6F, -1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 26).addBox(-1.85F, -0.6F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(150, 150)
                        .addBox(-3.35F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 16)
                        .addBox(-3.35F, -0.6F, -0.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1F, 0.1F, 0.0F, 0.0F, 0.0F, -0.9599F));

        PartDefinition bone8 = lever.addOrReplaceChild("bone8",
                CubeListBuilder.create().texOffs(26, 73).addBox(-1.25F, -0.6F, -0.9F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 26).addBox(-1.25F, -0.6F, -0.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(150, 150)
                        .addBox(-2.75F, -0.6F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 16)
                        .addBox(-2.75F, -0.6F, -0.4F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1F, 0.1F, 0.0F, 0.0F, 0.0F, 0.829F));

        PartDefinition crank = bone29.addOrReplaceChild("crank",
                CubeListBuilder.create().texOffs(147, 68).addBox(-1.0F, -1.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(2.75F, -5.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition bone32 = crank.addOrReplaceChild("bone32",
                CubeListBuilder.create().texOffs(146, 25).addBox(-1.0F, -0.5F, -0.5F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(77, 57).addBox(-2.0F, -0.5F, 2.5F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.1F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

        PartDefinition box3 = bone29.addOrReplaceChild("box3",
                CubeListBuilder.create().texOffs(115, 30).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(136, 61)
                        .addBox(-1.5F, 2.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(136, 56)
                        .addBox(-1.5F, -3.75F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(108, 140)
                        .addBox(-2.0F, 2.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(140, 107)
                        .addBox(-2.0F, -3.75F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(0, 0)
                        .addBox(-1.0F, -4.5F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(-0.7F)),
                PartPose.offsetAndRotation(0.0F, 7.25F, -0.5F, 0.0F, 0.0F, -0.2618F));

        PartDefinition bone39 = bone29.addOrReplaceChild("bone39",
                CubeListBuilder.create().texOffs(147, 56).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(110, 145).addBox(0.25F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(0.0F, 6.25F, 1.25F, 0.0F, 0.0F, -0.2618F));

        PartDefinition wires3 = bone29.addOrReplaceChild("wires3", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r174 = wires3
                .addOrReplaceChild(
                        "cube_r174", CubeListBuilder.create().texOffs(84, 77).addBox(-1.0F, -8.0F, -2.45F, 24.0F, 21.0F,
                                0.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0873F, 0.0F, -0.1309F));

        PartDefinition cube_r175 = wires3
                .addOrReplaceChild(
                        "cube_r175", CubeListBuilder.create().texOffs(35, 99).addBox(-1.0F, -6.0F, -2.25F, 24.0F, 19.0F,
                                0.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0436F, 0.0F, -0.1309F));

        PartDefinition bone61 = bone29.addOrReplaceChild("bone61",
                CubeListBuilder.create().texOffs(148, 126)
                        .addBox(-1.65F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(101, 148)
                        .addBox(-1.65F, -1.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(2.0F, 9.0F, 1.75F, 1.5708F, 0.0F, -0.2182F));

        PartDefinition p_ctrl_2 = controls.addOrReplaceChild("p_ctrl_2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition bone33 = p_ctrl_2.addOrReplaceChild("bone33", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -20.0F, 0.0F, 0.0F, 0.0F, 2.0071F));

        PartDefinition valve2 = bone33.addOrReplaceChild("valve2",
                CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -1.5F, -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(80, 5).addBox(-0.1F, 0.0F, 0.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.05F, 8.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition meter = bone33.addOrReplaceChild("meter", CubeListBuilder.create(),
                PartPose.offsetAndRotation(1.0F, -3.0F, 0.0F, 0.0F, 0.0F, 0.5672F));

        PartDefinition bone35 = meter.addOrReplaceChild("bone35",
                CubeListBuilder.create().texOffs(19, 126)
                        .addBox(-1.6F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(-0.95F)).texOffs(62, 57)
                        .addBox(-0.5F, -1.0F, -1.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, -1.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone9 = bone35.addOrReplaceChild("bone9",
                CubeListBuilder.create().texOffs(42, 14).addBox(0.0F, -0.75F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.6F, 0.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

        PartDefinition bone31 = bone33.addOrReplaceChild("bone31",
                CubeListBuilder.create().texOffs(119, 99).addBox(0.0F, -2.0F, 0.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F))
                        .texOffs(119, 99).addBox(0.0F, -2.0F, 1.1F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(119, 99)
                        .addBox(0.0F, -2.0F, -0.9F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(119, 99)
                        .addBox(0.0F, -2.0F, -1.9F, 1.0F, 6.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(95, 42)
                        .addBox(0.0F, -2.0F, -1.15F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 42)
                        .addBox(0.0F, -2.0F, -0.15F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 8)
                        .addBox(-0.75F, -3.0F, -0.15F, 0.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 73)
                        .addBox(-0.75F, 4.0F, -0.15F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(21, 73)
                        .addBox(-0.75F, -2.0F, -0.15F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 42)
                        .addBox(0.0F, -2.0F, 0.85F, 0.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.25F, 1.0F, -0.1F));

        PartDefinition bone34 = bone31.addOrReplaceChild("bone34",
                CubeListBuilder.create().texOffs(0, 141).addBox(-0.65F, -0.5F, -1.4F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(84, 99).addBox(-0.8F, -0.25F, -0.15F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -0.5F, 0.0F));

        PartDefinition crank2 = bone31.addOrReplaceChild("crank2",
                CubeListBuilder.create().texOffs(124, 144).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(22, 12).addBox(-1.0F, 0.0F, -0.25F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(95, 50)
                        .addBox(-1.0F, -1.0F, -0.75F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.75F, -2.6F, 0.1F, 0.0F, -0.6981F, 0.0F));

        PartDefinition wires4 = bone33.addOrReplaceChild("wires4", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r176 = wires4
                .addOrReplaceChild(
                        "cube_r176", CubeListBuilder.create().texOffs(62, 57).addBox(2.25F, -4.05F, -3.4F, 0.0F, 11.0F,
                                5.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.2618F, -0.1309F));

        PartDefinition p_ctrl_3 = controls.addOrReplaceChild("p_ctrl_3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition bone36 = p_ctrl_3.addOrReplaceChild("bone36", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -20.0F, 0.0F, 0.0F, 0.0F, 2.0071F));

        PartDefinition cube_r177 = bone36.addOrReplaceChild("cube_r177", CubeListBuilder.create().texOffs(136, 139).addBox(-2.75F,
                9.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

        PartDefinition box4 = bone36.addOrReplaceChild("box4",
                CubeListBuilder.create().texOffs(115, 30).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(38, 0).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 5.0F, 2.0F, new CubeDeformation(-0.2F)).texOffs(136, 61)
                        .addBox(-1.5F, 2.75F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(136, 56)
                        .addBox(-1.5F, -3.75F, -0.5F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.5F)).texOffs(108, 140)
                        .addBox(-2.0F, 2.75F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(140, 107)
                        .addBox(-2.0F, -3.75F, -0.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.7F)).texOffs(0, 0)
                        .addBox(-1.0F, -4.5F, -0.5F, 2.0F, 10.0F, 2.0F, new CubeDeformation(-0.7F)),
                PartPose.offsetAndRotation(-0.5F, 2.25F, 0.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition bone37 = bone36.addOrReplaceChild("bone37", CubeListBuilder.create().texOffs(139, 9).addBox(-0.75F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offsetAndRotation(1.0F, -4.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition cube_r178 = bone37.addOrReplaceChild("cube_r178",
                CubeListBuilder.create().texOffs(84, 107).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r179 = bone37.addOrReplaceChild("cube_r179",
                CubeListBuilder.create().texOffs(84, 107).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone38 = bone36.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(139, 9).addBox(-0.75F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, -0.9F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r180 = bone38.addOrReplaceChild("cube_r180",
                CubeListBuilder.create().texOffs(84, 107).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r181 = bone38.addOrReplaceChild("cube_r181",
                CubeListBuilder.create().texOffs(84, 107).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition wires5 = bone36.addOrReplaceChild("wires5", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r182 = wires5
                .addOrReplaceChild(
                        "cube_r182", CubeListBuilder.create().texOffs(35, 77).addBox(-1.5F, -5.0F, 3.55F, 24.0F, 21.0F,
                                0.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0396F, 0.0496F, -0.3493F));

        PartDefinition circuit = wires5.addOrReplaceChild("circuit",
                CubeListBuilder.create().texOffs(80, 38).addBox(1.25F, -3.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                        .texOffs(150, 24).addBox(0.5F, -1.0F, -2.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -1.0F, -1.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -1.7F, -2.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -1.7F, -1.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -2.4F, -2.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -2.4F, -1.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -3.1F, -2.1F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)).texOffs(150, 24)
                        .addBox(0.5F, -3.1F, -1.4F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offsetAndRotation(-0.25F, 7.95F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition button45 = circuit.addOrReplaceChild("button45", CubeListBuilder.create().texOffs(150, 33).addBox(-0.75F,
                -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)), PartPose.offset(1.25F, 0.25F, -1.25F));

        PartDefinition pully2 = bone36.addOrReplaceChild("pully2",
                CubeListBuilder.create().texOffs(90, 5).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 124).addBox(2.0F, -0.75F, 0.5F, 10.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(101, 5)
                        .addBox(1.75F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(7.0F, -4.75F, 2.0F));

        PartDefinition bone44 = pully2.addOrReplaceChild("bone44",
                CubeListBuilder.create().texOffs(136, 119).addBox(-2.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 126).addBox(-3.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(29, 53)
                        .addBox(-1.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(-1.75F, -0.5F, 0.5F));

        PartDefinition handbrake = bone36.addOrReplaceChild("handbrake", CubeListBuilder.create().texOffs(29, 160).addBox(-3.25F,
                -0.75F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, -5.0F, -2.1F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r183 = handbrake.addOrReplaceChild("cube_r183", CubeListBuilder.create().texOffs(55, 155).addBox(0.5F,
                -4.25F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-4.5855F, 4.6435F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r184 = handbrake.addOrReplaceChild("cube_r184",
                CubeListBuilder.create().texOffs(45, 159).addBox(-6.0F, -3.0F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.8481F, 0.3481F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition p_ctrl_4 = controls.addOrReplaceChild("p_ctrl_4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.618F, 0.0F));

        PartDefinition bone41 = p_ctrl_4.addOrReplaceChild("bone41", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -20.0F, 0.0F, 0.0F, 0.0F, 2.0071F));

        PartDefinition lever2 = bone41.addOrReplaceChild("lever2",
                CubeListBuilder.create().texOffs(134, 18)
                        .addBox(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(-0.201F)).texOffs(147, 0)
                        .addBox(-1.0F, -1.0F, -1.8F, 2.0F, 2.0F, 1.0F, new CubeDeformation(-0.201F)).texOffs(138, 0)
                        .addBox(-1.0F, -1.5F, -0.2F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.201F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r185 = lever2
                .addOrReplaceChild(
                        "cube_r185", CubeListBuilder.create().texOffs(139, 125).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F,
                                2.0F, new CubeDeformation(-0.001F)),
                        PartPose.offsetAndRotation(0.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition bone42 = lever2.addOrReplaceChild("bone42",
                CubeListBuilder.create().texOffs(41, 12).addBox(-1.85F, -0.6F, -1.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 26).addBox(-1.85F, -0.6F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(150, 150)
                        .addBox(-3.35F, -0.6F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 16)
                        .addBox(-3.35F, -0.6F, -0.5F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1F, 0.1F, 0.0F, 0.0F, 0.0F, -0.9599F));

        PartDefinition bone43 = lever2.addOrReplaceChild("bone43",
                CubeListBuilder.create().texOffs(26, 73).addBox(-1.25F, -0.6F, -0.9F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(95, 26).addBox(-1.25F, -0.6F, -0.9F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(150, 150)
                        .addBox(-2.75F, -0.6F, -0.4F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(36, 16)
                        .addBox(-2.75F, -0.6F, -0.4F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.1F, 0.1F, 0.0F, 0.0F, 0.0F, 0.829F));

        PartDefinition pully = bone41.addOrReplaceChild("pully",
                CubeListBuilder.create().texOffs(90, 5).addBox(-2.0F, -1.0F, 0.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 124).addBox(2.0F, -0.75F, 0.5F, 10.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(101, 5)
                        .addBox(1.75F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(7.0F, -4.75F, 2.0F));

        PartDefinition bone47 = pully.addOrReplaceChild("bone47",
                CubeListBuilder.create().texOffs(136, 119).addBox(-2.5F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(44, 126).addBox(-3.0F, -0.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(29, 53)
                        .addBox(-1.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(-1.75F, -0.5F, 0.5F));

        PartDefinition light = bone41.addOrReplaceChild("light",
                CubeListBuilder.create().texOffs(149, 135).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(110, 145).addBox(0.75F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.4F)),
                PartPose.offsetAndRotation(-0.25F, 6.25F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition bone45 = light.addOrReplaceChild("bone45", CubeListBuilder.create().texOffs(21, 149).addBox(-1.0F, -2.0F,
                -1.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.5F, 0.5F));

        PartDefinition bone46 = bone41.addOrReplaceChild("bone46", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r186 = bone46.addOrReplaceChild("cube_r186",
                CubeListBuilder.create().texOffs(101, 148).addBox(-1.65F, 8.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
                        .texOffs(148, 126).addBox(-1.65F, 8.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition wires6 = bone41.addOrReplaceChild("wires6", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r187 = wires6.addOrReplaceChild("cube_r187",
                CubeListBuilder.create().texOffs(29, 12).addBox(-3.0F, 8.0F, -3.0F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition p_ctrl_5 = controls.addOrReplaceChild("p_ctrl_5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone49 = p_ctrl_5.addOrReplaceChild("bone49", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -20.0F, 0.0F, 0.0F, 0.0F, 2.0071F));

        PartDefinition ring2 = bone49.addOrReplaceChild("ring2", CubeListBuilder.create().texOffs(21, 66).addBox(0.3347F, -1.4957F,
                -1.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition switch30 = ring2.addOrReplaceChild("switch30", CubeListBuilder.create().texOffs(144, 119).addBox(-0.85F, 0.0F,
                -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.2847F, -1.2457F, 0.0F));

        PartDefinition bone50 = bone49.addOrReplaceChild("bone50", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition cube_r188 = bone50.addOrReplaceChild("cube_r188",
                CubeListBuilder.create().texOffs(101, 148).addBox(-0.15F, 2.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.1F))
                        .texOffs(148, 126).addBox(-0.15F, 2.0F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition lever3 = bone49.addOrReplaceChild("lever3",
                CubeListBuilder.create().texOffs(84, 99).addBox(-0.5F, -2.0F, -1.0F, 2.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(42, 19).addBox(-0.2F, -2.0F, 1.4F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(15, 0)
                        .addBox(-1.5F, -4.0F, -1.0F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(85, 38)
                        .addBox(-1.5F, 2.0F, -1.0F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(15, 0)
                        .addBox(-1.5F, -4.0F, 1.51F, 3.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.75F, 9.25F, -0.5F, 0.0F, 0.0F, -0.3054F));

        PartDefinition cube_r189 = lever3
                .addOrReplaceChild(
                        "cube_r189", CubeListBuilder.create().texOffs(12, 126).addBox(-0.2695F, 0.1749F, -1.0F, 2.0F, 0.0F,
                                3.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-0.4053F, -2.3064F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition meter2 = lever3.addOrReplaceChild("meter2", CubeListBuilder.create().texOffs(115, 144).addBox(-1.0F, -0.75F,
                -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.55F, -2.15F, 0.25F, 0.0F, 0.0F, -0.6981F));

        PartDefinition bone51 = meter2.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(143, 34).addBox(-1.25F, -0.25F,
                -0.55F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 0.0F, 0.0F, -0.6109F, 0.0F));

        PartDefinition bone52 = lever3.addOrReplaceChild("bone52",
                CubeListBuilder.create().texOffs(15, 12).addBox(-2.7F, -0.5F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .texOffs(85, 38).addBox(-2.7F, -0.5F, 0.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -1.0F, 1.9F, 0.0F, 0.0F, -0.2182F));

        PartDefinition lights2 = lever3.addOrReplaceChild("lights2", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone53 = lights2.addOrReplaceChild("bone53",
                CubeListBuilder.create().texOffs(150, 147).addBox(0.0F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(150, 141).addBox(0.25F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-1.0F, 1.75F, -0.25F));

        PartDefinition bone57 = bone53.addOrReplaceChild("bone57", CubeListBuilder.create().texOffs(150, 47).addBox(0.0F, -1.0F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.18F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone56 = lights2.addOrReplaceChild("bone56",
                CubeListBuilder.create().texOffs(150, 147).addBox(0.0F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(150, 141).addBox(0.25F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-1.0F, 0.75F, -0.25F));

        PartDefinition bone60 = bone56.addOrReplaceChild("bone60", CubeListBuilder.create().texOffs(150, 47).addBox(0.0F, -1.0F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.18F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone54 = lights2.addOrReplaceChild("bone54",
                CubeListBuilder.create().texOffs(150, 147).addBox(0.0F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(150, 141).addBox(0.25F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-1.0F, 1.75F, 0.75F));

        PartDefinition bone58 = bone54.addOrReplaceChild("bone58", CubeListBuilder.create().texOffs(108, 150).addBox(0.0F, -1.0F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.18F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone55 = lights2.addOrReplaceChild("bone55",
                CubeListBuilder.create().texOffs(150, 147).addBox(0.0F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(150, 141).addBox(0.25F, -1.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(-1.0F, 0.75F, 0.75F));

        PartDefinition bone59 = bone55.addOrReplaceChild("bone59", CubeListBuilder.create().texOffs(108, 150).addBox(0.0F, -1.0F,
                -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.18F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pully3 = bone49.addOrReplaceChild("pully3",
                CubeListBuilder.create().texOffs(5, 156).addBox(-3.0F, -1.0F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(118, 124).addBox(2.0F, -0.75F, 0.5F, 10.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(101, 5)
                        .addBox(1.75F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offset(7.0F, -4.75F, -3.0F));

        PartDefinition bone48 = pully3.addOrReplaceChild("bone48",
                CubeListBuilder.create().texOffs(0, 156).addBox(-1.25F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(29, 53).addBox(-1.0F, -0.5F, -0.5F, 5.0F, 1.0F, 1.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(-2.75F, -0.5F, 0.5F));

        PartDefinition p_ctrl_6 = controls.addOrReplaceChild("p_ctrl_6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone62 = p_ctrl_6.addOrReplaceChild("bone62", CubeListBuilder.create(),
                PartPose.offsetAndRotation(19.0F, -20.0F, 0.0F, 0.0F, 0.0F, 2.0071F));

        PartDefinition bone63 = bone62.addOrReplaceChild("bone63", CubeListBuilder.create().texOffs(139, 9).addBox(-0.75F, -1.0F,
                -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)),
                PartPose.offsetAndRotation(-0.5F, 1.5F, -0.9F, 0.0F, 0.0F, 0.1745F));

        PartDefinition cube_r190 = bone63.addOrReplaceChild("cube_r190",
                CubeListBuilder.create().texOffs(84, 107).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r191 = bone63.addOrReplaceChild("cube_r191",
                CubeListBuilder.create().texOffs(84, 107).addBox(-0.5F, 0.0F, -1.5F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

        PartDefinition bone64 = bone62.addOrReplaceChild("bone64",
                CubeListBuilder.create().texOffs(147, 56).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                        .texOffs(110, 145).addBox(0.25F, -1.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(1.0F, 10.75F, 0.0F, 0.0F, 0.0F, -0.2618F));

        PartDefinition bone65 = bone62.addOrReplaceChild("bone65",
                CubeListBuilder.create().texOffs(113, 63).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(68, 57).addBox(-2.0F, -2.0F, -1.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(0.5F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3054F));

        PartDefinition panel3 = bone62.addOrReplaceChild("panel3",
                CubeListBuilder.create().texOffs(9, 146).addBox(0.0F, -1.5F, 0.0F, 0.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(84, 19).addBox(0.0F, -0.75F, -1.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(80, 19)
                        .addBox(-0.1F, -0.5F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.35F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition button46 = panel3.addOrReplaceChild("button46",
                CubeListBuilder.create().texOffs(97, 142).addBox(-0.2F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-0.25F, 1.75F, 0.75F));

        PartDefinition bow = bone62.addOrReplaceChild("bow",
                CubeListBuilder.create().texOffs(80, 0).addBox(0.0F, 0.5F, -1.75F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.05F, 5.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r192 = bow.addOrReplaceChild("cube_r192",
                CubeListBuilder.create().texOffs(21, 66).addBox(0.0F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r193 = bow.addOrReplaceChild("cube_r193",
                CubeListBuilder.create().texOffs(25, 66).addBox(0.0F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, 0.6109F, 0.0F, 0.0F));

        PartDefinition cube_r194 = bow.addOrReplaceChild("cube_r194",
                CubeListBuilder.create().texOffs(73, 62).addBox(0.0F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, 0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r195 = bow.addOrReplaceChild("cube_r195",
                CubeListBuilder.create().texOffs(77, 62).addBox(0.0F, -0.5F, -3.25F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, -0.3054F, 0.0F, 0.0F));

        PartDefinition bone68 = bow.addOrReplaceChild("bone68",
                CubeListBuilder.create().texOffs(30, 126)
                        .addBox(-0.25F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)).texOffs(106, 145)
                        .addBox(0.0F, -0.25F, -3.0F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(30, 126)
                        .addBox(-0.25F, -0.5F, -3.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.01F)),
                PartPose.offsetAndRotation(0.0F, 1.0F, 1.5F, 0.2182F, 0.0F, 0.0F));

        PartDefinition bone69 = bow.addOrReplaceChild("bone69",
                CubeListBuilder.create().texOffs(42, 51).addBox(0.0F, -1.25F, -1.25F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 2.0F, -0.5F, -0.6109F, 0.0F, 0.0F));

        PartDefinition bone70 = bow.addOrReplaceChild("bone70",
                CubeListBuilder.create().texOffs(42, 51).addBox(0.0F, -1.25F, -1.25F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.25F, 0.75F, 0.9F, 0.2182F, 0.0F, 0.0F));

        PartDefinition bone73 = bow.addOrReplaceChild("bone73", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.25F, 0.0F, -0.85F, 1.0472F, 0.0F, 0.0F));

        PartDefinition cube_r196 = bone73.addOrReplaceChild("cube_r196",
                CubeListBuilder.create().texOffs(42, 51).addBox(0.0F, -1.25F, -1.25F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.829F, 0.0F, 0.0F));

        PartDefinition bone71 = bow.addOrReplaceChild("bone71",
                CubeListBuilder.create().texOffs(29, 12).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.15F, 0.25F, -0.1F, 0.4363F, 0.0F, 0.0F));

        PartDefinition bone72 = bow.addOrReplaceChild("bone72",
                CubeListBuilder.create().texOffs(29, 12).addBox(0.0F, -0.5F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.15F, 1.75F, 0.25F, -0.2618F, 0.0F, 0.0F));

        PartDefinition wires7 = bone62.addOrReplaceChild("wires7", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r197 = wires7.addOrReplaceChild("cube_r197",
                CubeListBuilder.create().texOffs(80, 19).addBox(-3.5F, 0.25F, 5.0F, 0.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.019F, 0.5F, -0.1021F, -0.0496F, 0.7779F, 0.0956F));

        PartDefinition cube_r198 = wires7.addOrReplaceChild("cube_r198",
                CubeListBuilder.create().texOffs(88, 19).addBox(0.0F, 0.25F, 2.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.5F, 0.0F, -0.0359F, 0.1676F, 0.1245F));

        PartDefinition cube_r199 = wires7.addOrReplaceChild("cube_r199", CubeListBuilder.create().texOffs(107, 113).addBox(-1.0F,
                -0.1F, 0.8F, 5.0F, 0.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.48F, 0.0F, 0.0F));

        PartDefinition hammer = bone62.addOrReplaceChild("hammer", CubeListBuilder.create().texOffs(77, 148).addBox(4.0F, -3.0F, -3.25F, 1.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition bone40 = hammer.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(29, 50).addBox(-0.5F, -0.5F, -0.5F, 6.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F))
                .texOffs(80, 47).addBox(5.25F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(12, 130).addBox(-2.7F, -1.25F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(6.5F, -2.5F, -2.5F));

        PartDefinition handbrake2 = bone62.addOrReplaceChild("handbrake2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(4.25F, -3.65F, 1.9F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r200 = handbrake2.addOrReplaceChild("cube_r200", CubeListBuilder.create().texOffs(18, 155).addBox(-0.9F,
                -1.5F, -1.2F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone102 = handbrake2.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(38, 156).addBox(-2.7071F,
                -0.5F, -0.7071F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(1.0F, -1.0F, 0.0F, 0.0F, 0.829F, 0.0F));

        PartDefinition cube_r201 = bone102.addOrReplaceChild("cube_r201",
                CubeListBuilder.create().texOffs(27, 157).addBox(-5.0F, -0.5F, 0.4F, 4.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)),
                PartPose.offsetAndRotation(-0.7071F, 0.0F, -0.7071F, 0.0F, -0.2182F, 0.0F));

        PartDefinition roof = console.addOrReplaceChild("roof",
                CubeListBuilder.create().texOffs(113, 56)
                        .addBox(-10.75F, -29.75F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.5F)).texOffs(84, 99)
                        .addBox(-11.0F, -33.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.81F)).texOffs(113, 56)
                        .addBox(-10.4F, -27.0F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(128, 107)
                        .addBox(-6.65F, -31.25F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.8F)),
                PartPose.offset(0.0F, -48.25F, 0.0F));

        PartDefinition top2 = roof.addOrReplaceChild("top2",
                CubeListBuilder.create().texOffs(113, 56)
                        .addBox(-10.75F, -29.75F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.5F)).texOffs(84, 99)
                        .addBox(-11.0F, -33.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.81F)).texOffs(113, 56)
                        .addBox(-10.4F, -27.0F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(128, 107)
                        .addBox(-6.65F, -31.25F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.8F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top3 = top2.addOrReplaceChild("top3",
                CubeListBuilder.create().texOffs(113, 56)
                        .addBox(-10.75F, -29.75F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.5F)).texOffs(84, 99)
                        .addBox(-11.0F, -33.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.81F)).texOffs(113, 56)
                        .addBox(-10.4F, -27.0F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(128, 107)
                        .addBox(-6.65F, -31.25F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.8F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top4 = top3.addOrReplaceChild("top4",
                CubeListBuilder.create().texOffs(113, 56)
                        .addBox(-10.75F, -29.75F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.5F)).texOffs(84, 99)
                        .addBox(-11.0F, -33.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.81F)).texOffs(113, 56)
                        .addBox(-10.4F, -27.0F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(128, 107)
                        .addBox(-6.65F, -31.25F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.8F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top5 = top4.addOrReplaceChild("top5",
                CubeListBuilder.create().texOffs(113, 56)
                        .addBox(-10.75F, -29.75F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.5F)).texOffs(84, 99)
                        .addBox(-11.0F, -33.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.81F)).texOffs(113, 56)
                        .addBox(-10.4F, -27.0F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(128, 107)
                        .addBox(-6.65F, -31.25F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.8F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top6 = top5.addOrReplaceChild("top6",
                CubeListBuilder.create().texOffs(113, 56)
                        .addBox(-10.75F, -29.75F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.5F)).texOffs(84, 99)
                        .addBox(-11.0F, -33.0F, -6.0F, 11.0F, 1.0F, 12.0F, new CubeDeformation(0.8F)).texOffs(113, 56)
                        .addBox(-10.4F, -27.0F, -6.0F, 5.0F, 1.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(128, 107)
                        .addBox(-6.65F, -31.25F, -3.5F, 2.0F, 4.0F, 7.0F, new CubeDeformation(0.8F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition wires8 = roof.addOrReplaceChild("wires8", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r202 = wires8.addOrReplaceChild("cube_r202",
                CubeListBuilder.create().texOffs(77, 22).addBox(21.25F, -2.5F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0556F, -0.1186F, -0.8323F));

        PartDefinition cube_r203 = wires8.addOrReplaceChild("cube_r203", CubeListBuilder.create().texOffs(0, 76).addBox(-8.0F, -28.0F,
                -8.0F, 17.0F, 49.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.618F, 0.0F));

        PartDefinition cube_r204 = wires8.addOrReplaceChild("cube_r204", CubeListBuilder.create().texOffs(0, 76).addBox(-8.0F, -28.0F,
                -8.0F, 17.0F, 49.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3491F, 0.0F));

        PartDefinition cube_r205 = wires8.addOrReplaceChild("cube_r205",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -32.4F, 6.0F, 0.0F, 51.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.7053F, 0.0F));

        PartDefinition cube_r206 = wires8.addOrReplaceChild("cube_r206",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -32.5F, 6.0F, 0.0F, 51.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.3963F, 0.0F));

        PartDefinition cube_r207 = wires8.addOrReplaceChild("cube_r207",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -32.5F, 6.0F, 0.0F, 51.0F, 14.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.2618F, 0.0F));

        PartDefinition top = console.addOrReplaceChild("top",
                CubeListBuilder.create().texOffs(0, 127).addBox(-6.05F, -29.0F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 30).addBox(-8.8F, -29.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.2F)).texOffs(115, 30)
                        .addBox(-8.65F, -31.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition top8 = top.addOrReplaceChild("top8",
                CubeListBuilder.create().texOffs(0, 127).addBox(-6.05F, -29.0F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 30).addBox(-8.8F, -29.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.2F)).texOffs(115, 30)
                        .addBox(-8.65F, -31.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top9 = top8.addOrReplaceChild("top9",
                CubeListBuilder.create().texOffs(0, 127).addBox(-6.05F, -29.0F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 30).addBox(-8.8F, -29.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.2F)).texOffs(115, 30)
                        .addBox(-8.65F, -31.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top10 = top9.addOrReplaceChild("top10",
                CubeListBuilder.create().texOffs(0, 127).addBox(-6.05F, -29.0F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 30).addBox(-8.8F, -29.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.2F)).texOffs(115, 30)
                        .addBox(-8.65F, -31.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top11 = top10.addOrReplaceChild("top11",
                CubeListBuilder.create().texOffs(0, 127).addBox(-6.05F, -29.0F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 30).addBox(-8.8F, -29.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.2F)).texOffs(115, 30)
                        .addBox(-8.65F, -31.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition top12 = top11.addOrReplaceChild("top12",
                CubeListBuilder.create().texOffs(0, 127).addBox(-6.05F, -29.0F, -3.5F, 2.0F, 6.0F, 7.0F, new CubeDeformation(0.0F))
                        .texOffs(115, 30).addBox(-8.8F, -29.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.2F)).texOffs(115, 30)
                        .addBox(-8.65F, -31.0F, -5.0F, 3.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition monitor = top.addOrReplaceChild("monitor", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.15F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition bone75 = monitor.addOrReplaceChild("bone75",
                CubeListBuilder.create().texOffs(113, 70).addBox(-4.5F, 4.0F, 2.0F, 9.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 146).addBox(1.5F, 3.25F, 2.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F)).texOffs(57, 126)
                        .addBox(1.5F, 3.25F, 2.75F, 3.0F, 1.0F, 1.0F, new CubeDeformation(-0.1F)).texOffs(80, 0)
                        .addBox(-4.5F, -4.0F, 4.0F, 9.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(0, 66)
                        .addBox(-4.5F, -3.0F, 4.0F, 9.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -27.0F, -17.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition cube_r208 = bone75.addOrReplaceChild("cube_r208",
                CubeListBuilder.create().texOffs(146, 123).addBox(2.0F, 3.5F, 2.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.1529F, -1.4052F, 1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r209 = bone75.addOrReplaceChild("cube_r209",
                CubeListBuilder.create().texOffs(145, 4).addBox(2.0F, 3.5F, 2.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.5821F, 0.5771F, 1.6144F, 0.0F, 0.0F));

        PartDefinition cube_r210 = bone75.addOrReplaceChild("cube_r210",
                CubeListBuilder.create().texOffs(145, 59).addBox(-4.0F, 4.25F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.5821F, 0.2771F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r211 = bone75.addOrReplaceChild("cube_r211",
                CubeListBuilder.create().texOffs(145, 64).addBox(-4.0F, 4.25F, 0.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.25F, 0.2618F, 0.0F, 0.0F));

        PartDefinition screen = bone75.addOrReplaceChild("screen", CubeListBuilder.create(),
                PartPose.offset(0.0F, 27.0F, 17.0F));

        PartDefinition bone66 = screen.addOrReplaceChild("bone66",
                CubeListBuilder.create().texOffs(87, 29).addBox(-2.25F, -2.25F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.75F, -26.75F, -13.2F, 0.0F, 0.0F, -0.2618F));

        PartDefinition bone67 = screen.addOrReplaceChild("bone67",
                CubeListBuilder.create().texOffs(118, 5).addBox(-0.75F, -0.75F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.25F, -28.25F, -13.1F, 0.0F, 0.0F, 1.8326F));

        PartDefinition bone74 = screen.addOrReplaceChild("bone74", CubeListBuilder.create().texOffs(97, 113).addBox(-0.75F, -0.75F,
                0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-2.25F, -25.75F, -13.1F, 0.0F, 0.0F, 1.6581F));

        PartDefinition bone116 = screen.addOrReplaceChild("bone116",
                CubeListBuilder.create().texOffs(0, 160).addBox(-3.5F, 2.0F, 3.95F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -27.0F, -17.0F));

        PartDefinition bone76 = monitor.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(65, 145).addBox(0.0F, -0.75F,
                -2.75F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.75F, -10.0F, -0.8727F, 0.0F, 0.0F));

        PartDefinition bone77 = monitor.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(113, 56).addBox(-0.1F, -0.75F,
                -0.25F, 0.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -26.75F, -10.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition rotor = console.addOrReplaceChild("rotor", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition top7 = rotor.addOrReplaceChild("top7", CubeListBuilder.create(),
                PartPose.offset(0.0F, -17.0F, 0.0F));

        PartDefinition bone103 = top7.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, -25.0F, 0.0F));

        PartDefinition bone104 = bone103.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone105 = bone104.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone106 = bone105.addOrReplaceChild("bone106", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone107 = bone106.addOrReplaceChild("bone107", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone108 = bone107.addOrReplaceChild("bone108", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone110 = top7.addOrReplaceChild("bone110", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)), PartPose.offset(0.0F, -20.0F, 0.0F));

        PartDefinition bone111 = bone110.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone112 = bone111.addOrReplaceChild("bone112", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone113 = bone112.addOrReplaceChild("bone113", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone114 = bone113.addOrReplaceChild("bone114", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone115 = bone114.addOrReplaceChild("bone115", CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, -31.0F,
                -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone84 = top7.addOrReplaceChild("bone84", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -59.0F, -1.0F,
                2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone85 = bone84.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -59.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone86 = bone85.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -59.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone87 = bone86.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -59.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone88 = bone87.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -59.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone89 = bone88.addOrReplaceChild("bone89", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -59.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bottom = rotor.addOrReplaceChild("bottom", CubeListBuilder.create(),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition bone117 = bottom.addOrReplaceChild("bone117",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offset(0.0F, -38.0F, 0.0F));

        PartDefinition bone118 = bone117.addOrReplaceChild("bone118",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone119 = bone118.addOrReplaceChild("bone119",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone120 = bone119.addOrReplaceChild("bone120",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone121 = bone120.addOrReplaceChild("bone121",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone122 = bone121.addOrReplaceChild("bone122",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone90 = bottom.addOrReplaceChild("bone90",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offset(0.0F, -33.0F, 0.0F));

        PartDefinition bone91 = bone90.addOrReplaceChild("bone91",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone92 = bone91.addOrReplaceChild("bone92",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone93 = bone92.addOrReplaceChild("bone93",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone94 = bone93.addOrReplaceChild("bone94",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.005F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone95 = bone94.addOrReplaceChild("bone95",
                CubeListBuilder.create().texOffs(115, 0).addBox(-6.9F, 3.0F, -4.0F, 7.0F, 0.0F, 8.0F, new CubeDeformation(0.006F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone78 = bottom.addOrReplaceChild("bone78", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -55.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone79 = bone78.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -55.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone80 = bone79.addOrReplaceChild("bone80", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -55.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone81 = bone80.addOrReplaceChild("bone81", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -55.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone82 = bone81.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -55.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone83 = bone82.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(35, 119).addBox(3.0F, -55.0F,
                -1.0F, 2.0F, 28.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition glass = console.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(98, 0).addBox(-6.95F, -75.0F,
                -4.0F, 0.0F, 45.0F, 8.0F, new CubeDeformation(0.05F)), PartPose.offset(0.0F, 0.75F, 0.0F));

        PartDefinition cube_r212 = glass.addOrReplaceChild("cube_r212", CubeListBuilder.create().texOffs(84, 113).addBox(-10.9F,
                -75.0F, 0.0F, 3.0F, 45.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, -0.5F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition glass2 = glass.addOrReplaceChild("glass2", CubeListBuilder.create().texOffs(98, 0).addBox(-6.95F, -75.0F,
                -4.0F, 0.0F, 45.0F, 8.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r213 = glass2.addOrReplaceChild("cube_r213", CubeListBuilder.create().texOffs(84, 113).addBox(-10.9F,
                -75.5F, 0.0F, 3.0F, 45.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition glass3 = glass2.addOrReplaceChild("glass3", CubeListBuilder.create().texOffs(98, 0).addBox(-6.95F, -75.0F,
                -4.0F, 0.0F, 45.0F, 8.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r214 = glass3.addOrReplaceChild("cube_r214", CubeListBuilder.create().texOffs(84, 113).addBox(-10.9F,
                -75.5F, 0.0F, 3.0F, 45.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition glass4 = glass3.addOrReplaceChild("glass4", CubeListBuilder.create().texOffs(98, 0).addBox(-6.95F, -75.0F,
                -4.0F, 0.0F, 45.0F, 8.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r215 = glass4.addOrReplaceChild("cube_r215", CubeListBuilder.create().texOffs(84, 113).addBox(-10.9F,
                -75.5F, 0.0F, 3.0F, 45.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition glass5 = glass4.addOrReplaceChild("glass5", CubeListBuilder.create().texOffs(98, 0).addBox(-6.95F, -75.0F,
                -4.0F, 0.0F, 45.0F, 8.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r216 = glass5.addOrReplaceChild("cube_r216", CubeListBuilder.create().texOffs(84, 113).addBox(-10.9F,
                -75.5F, 0.0F, 3.0F, 45.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition glass6 = glass5.addOrReplaceChild("glass6", CubeListBuilder.create().texOffs(98, 0).addBox(-6.95F, -75.0F,
                -4.0F, 0.0F, 45.0F, 8.0F, new CubeDeformation(0.05F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition cube_r217 = glass6.addOrReplaceChild("cube_r217", CubeListBuilder.create().texOffs(84, 113).addBox(-10.9F,
                -75.5F, 0.0F, 3.0F, 45.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition rings = console.addOrReplaceChild("rings", CubeListBuilder.create().texOffs(58, 145).addBox(-7.25F, -26.0F,
                -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition rings2 = rings.addOrReplaceChild("rings2", CubeListBuilder.create().texOffs(58, 145).addBox(-7.25F, -26.0F,
                -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition rings3 = rings2.addOrReplaceChild("rings3", CubeListBuilder.create().texOffs(58, 145).addBox(-7.25F, -26.0F,
                -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition rings4 = rings3.addOrReplaceChild("rings4", CubeListBuilder.create().texOffs(58, 145).addBox(-7.25F, -26.0F,
                -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));

        PartDefinition rings5 = rings4.addOrReplaceChild("rings5", CubeListBuilder.create().texOffs(58, 145).addBox(-7.25F, -26.0F,
                -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.4363F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        return switch (state) {
            default -> CoralAnimations.CORAL_CONSOLE_INFLIGHT_ANIMATION;
            case MAT -> CoralAnimations.CORAL_CONSOLE_REMAT_ANIMATION;
            case DEMAT -> CoralAnimations.CORAL_CONSOLE_DEMAT_ANIMATION;
            case LANDED -> CoralAnimations.CONSOLE_CORAL_IDLE_ANIMATION;
        };
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        console.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = !AITModClient.CONFIG.animateControls ? 1.0f : 0.1f * client.getTimer().getGameTimeDeltaPartialTick(true);
        matrices.pushPose();
        matrices.translate(0.5f, -1.5f, -0.5f);

        ModelPart controls = this.console.getChild("controls");

        this.console.getChild("rotor").getChild("top7").visible = !console.getVariant()
                .equals(ConsoleVariantRegistry.CORAL_WHITE);

        // Fuel Gauge
        controls.getChild("ctrl_1").getChild("bone13").getChild("compass")
                .getChild("needle").xRot = -(float) (((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 2) - 1);

        ModelPart fuelLowWarningLight = controls.getChild("p_ctrl_4").getChild("bone41").getChild("light")
                .getChild("bone45");
        // Low Fuel Light
        fuelLowWarningLight.visible = (tardis.getFuel() <= (FuelHandler.TARDIS_MAX_FUEL / 10));

        // Anti-gravs Lever
        ModelPart antigrav = controls.getChild("p_ctrl_1").getChild("bone29").getChild("lever").getChild("bone8");
        float antigravsTarget = tardis.travel().antigravs().get() ? 0.829F : 0.829F - 1.5f;
        antigrav.zRot = getAngle(console, "antigravs", antigravsTarget, delta) - 0.5f;

        // Door Control
        ModelPart doorControl = controls.getChild("p_ctrl_1").getChild("bone29").getChild("crank").getChild("bone32");
        float doorControlTarget = tardis.door().isLeftOpen() ? -0.6981F - 0.8f: tardis.door().isRightOpen() ? -0.6981F - 1.5f : 0;
        doorControl.xRot = getAngle(console, "door_control", doorControlTarget, delta);

        // Power Lever
        ModelPart power = controls.getChild("p_ctrl_4").getChild("bone41").getChild("lever2").getChild("bone43");
        float powerTarget = tardis.fuel().hasPower() ? 0 : 1.5f;
        float power2Target = tardis.fuel().hasPower() ? 0 : 0.5f;
        power.zRot = getAngle(console, "power", powerTarget, delta) - 0.5f;
        ModelPart power2 = controls.getChild("p_ctrl_4").getChild("bone41").getChild("lever2").getChild("bone42");
        power2.zRot = getAngle(console, "power2", power2Target, delta) - 0.5f;

        // Throttle
        ModelPart throttle = controls.getChild("p_ctrl_5").getChild("bone49").getChild("lever3").getChild("bone52");
        float throttleTarget = tardis.travel().maxSpeed().get() > 0 ? (float) tardis.travel().speed() / (float) tardis.travel().maxSpeed().get() : 0f;
        throttle.zRot = getAngle(console, "throttle", throttleTarget, delta);

        // Increment
        ModelPart increment = controls.getChild("p_ctrl_2").getChild("bone33").getChild("bone31").getChild("crank2");
        ModelPart incrementTwo = controls.getChild("p_ctrl_2").getChild("bone33").getChild("bone31").getChild("bone34");

        int incrementVal = IncrementManager.increment(tardis);
        float targetOne = 0f;
        float targetTwo = 0f;

        if (incrementVal >= 10000) {
            targetOne = 1.5f;
            targetTwo = 3.0f;
        } else if (incrementVal >= 1000) {
            targetOne = 1.25f;
            targetTwo = 2.0f;
        } else if (incrementVal >= 100) {
            targetOne = 1.0f;
            targetTwo = 1.0f;
        } else if (incrementVal >= 10) {
            targetOne = 0.5f;
            targetTwo = 0.5f;
        }

        increment.yRot = getAngle(console, "increment", targetOne, delta);
        incrementTwo.y = getAngle(console, "increment2", targetTwo, delta);

        // Refueler
        ModelPart refueler = controls.getChild("p_ctrl_5").getChild("bone49").getChild("ring2").getChild("switch30");
        float refuelerTarget = tardis.isRefueling() ? -1 : 0;
        refueler.y = getAngle(console, "refueler", refuelerTarget, delta);

        // Waypoint
        controls.getChild("ctrl_1").getChild("bone13").getChild("insert").getChild("bone96").visible = tardis
                .<WaypointHandler>handler(TardisComponent.Id.WAYPOINTS).hasCartridge();

        // Handbrake
        ModelPart handbrake = controls.getChild("p_ctrl_6").getChild("bone62").getChild("handbrake2")
                .getChild("bone102");
        float handbrakeTarget = !tardis.travel().handbrake() ? 0.829F : 0.829F + 0.75f;
        handbrake.yRot = getAngle(console, "handbrake", handbrakeTarget, delta);

        // Siege Mode
        ModelPart siege = controls.getChild("p_ctrl_3").getChild("bone36").getChild("handbrake");
        float siegeTarget = tardis.siege().isActive() ? 0.45f : 0;
        siege.zRot = getAngle(console, "siege", siegeTarget, delta);

        // Shields
        ModelPart shield = controls.getChild("p_ctrl_4").getChild("bone41").getChild("pully").getChild("bone47");
        float shieldTarget = tardis.shields().shielded().get() ? tardis.shields().visuallyShielded().get() ? -4f : -1.55f : -2.5f;
        shield.x = getAngle(console, "shields", shieldTarget, delta);

        // Autopilot
        ModelPart autopilot = controls.getChild("ctrl_4").getChild("bone15").getChild("switch24").getChild("bone19");
        float autopilotTarget = tardis.travel().autopilot() ? 1 : 0;
        autopilot.y = getAngle(console, "autopilot", autopilotTarget, delta);

        ModelPart security = controls.getChild("ctrl_4").getChild("bone15").getChild("switch25").getChild("bone20");
        float securityTarget = tardis.stats().security().get() ? 1 : 0;
        security.y = getAngle(console, "security", securityTarget, delta);

        // Ground Searching
        ModelPart groundSearch = controls.getChild("p_ctrl_6").getChild("bone62").getChild("bow").getChild("bone68");
        float groundSearchTarget = tardis.travel().horizontalSearch().get() ? 0.2182F - 0.5f : 0.2182F;
        groundSearch.xRot = getAngle(console, "ground_search", groundSearchTarget, delta);

        // Hammer
        ModelPart hammer = controls.getChild("p_ctrl_6").getChild("bone62").getChild("hammer").getChild("bone40");
        hammer.skipDraw = tardis.extra().getConsoleHammer() == null || tardis.extra().getConsoleHammer().isEmpty();

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
        matrices.translate(1.85, 0.60, 0.85);
        matrices.mulPose(Axis.XP.rotationDegrees(160f +11f));
        matrices.scale(0.005f, 0.005f, 0.005f);
        matrices.mulPose(Axis.ZN.rotationDegrees(5));
        matrices.mulPose(Axis.YN.rotationDegrees(30f));
        matrices.translate(-242f, -228, -2.1f);
        int y = 28;
        String positionPosText = abppPos.getX() + ", " + abppPos.getY() + ", " + abppPos.getZ();
        Component positionDimensionText = WorldUtil.worldText(abpp.getDimension());
        String positionDirectionText = DirectionControl.rotationToDirection(abpp.getRotation()).toUpperCase();
        renderer.drawInBatch8xOutline(Component.nullToEmpty("\uD83D\uDCCD").getVisualOrderText(), 0, y, 0x00EEFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(positionPosText).getVisualOrderText(), 8, y, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(positionDimensionText.getVisualOrderText(), 8, y + 8, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(positionDirectionText).getVisualOrderText(), 8, y + 16, 0xFFFFFF, 0x000000,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.81, 1.25, 0.93);
        matrices.mulPose(Axis.XP.rotationDegrees(160f +11f));
        matrices.scale(0.007f, 0.007f, 0.007f);
        matrices.mulPose(Axis.ZN.rotationDegrees(4f));
        matrices.mulPose(Axis.YN.rotationDegrees(30f));
        String progressText = tardis.travel().getState() == TravelHandlerBase.State.LANDED
                ? "⏳: 0%"
                : "⏳: " + tardis.travel().getDurationAsPercentage() + "%";
        matrices.translate(7, -13, -50f);
        renderer.drawInBatch8xOutline(Component.nullToEmpty(progressText).getVisualOrderText(),
                0 - renderer.width(progressText) / 2, 0, 0xffffff, 04,
                matrices.last().pose(), vertexConsumers, 0xF000F0);
        matrices.popPose();

    }

    @Override
    public ModelPart root() {
        return console;
    }
}
