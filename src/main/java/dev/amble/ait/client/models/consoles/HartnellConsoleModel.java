package dev.amble.ait.client.models.consoles;

import net.minecraft.client.animation.AnimationDefinition;
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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.animation.console.hartnell.HartnellAnimations;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.CloakHandler;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;

// Made with Blockbench 4.9.2
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class HartnellConsoleModel extends SimpleConsoleModel {

    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/consoles/hartnell_console.png");
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/blockentities/consoles/hartnell_console_emission.png");

    private final ModelPart bone;

    public HartnellConsoleModel(ModelPart root) {
        this.bone = root.getChild("bone");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bone = modelPartData.addOrReplaceChild("bone", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        PartDefinition base = bone.addOrReplaceChild("base", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone19 = base.addOrReplaceChild("bone19", CubeListBuilder.create().texOffs(56, 34).addBox(-0.475F, -1.0F,
                -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone3 = bone19.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(56, 34).addBox(-0.475F, -1.0F,
                -5.5F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone4 = bone3.addOrReplaceChild("bone4", CubeListBuilder.create().texOffs(56, 34).addBox(-0.475F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone5 = bone4.addOrReplaceChild("bone5", CubeListBuilder.create().texOffs(56, 34).addBox(-0.475F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone6 = bone5.addOrReplaceChild("bone6", CubeListBuilder.create().texOffs(56, 34).addBox(-0.475F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone7 = bone6.addOrReplaceChild("bone7", CubeListBuilder.create().texOffs(56, 34).addBox(-0.475F, -1.0F, -5.5F,
                10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone8 = base.addOrReplaceChild("bone8", CubeListBuilder.create().texOffs(67, 47).addBox(6.25F, -16.0F, -0.5F,
                4.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 1.75F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r1 = bone8.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -22.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.9319F, -14.4824F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone9 = bone8.addOrReplaceChild("bone9", CubeListBuilder.create().texOffs(67, 47).addBox(6.25F, -16.0F, -0.5F,
                4.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r2 = bone9.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -22.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.9319F, -14.4824F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone10 = bone9.addOrReplaceChild("bone10", CubeListBuilder.create().texOffs(67, 47).addBox(6.25F, -16.0F,
                -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r3 = bone10.addOrReplaceChild("cube_r3",
                CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -22.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.9319F, -14.4824F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone11 = bone10.addOrReplaceChild("bone11", CubeListBuilder.create().texOffs(67, 47).addBox(6.25F, -16.0F,
                -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r4 = bone11.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -22.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.9319F, -14.4824F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone12 = bone11.addOrReplaceChild("bone12", CubeListBuilder.create().texOffs(67, 47).addBox(6.25F, -16.0F,
                -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r5 = bone12.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -22.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.9319F, -14.4824F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone13 = bone12.addOrReplaceChild("bone13", CubeListBuilder.create().texOffs(67, 47).addBox(6.25F, -16.0F,
                -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.001F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r6 = bone13.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(0, 21).addBox(1.0F, -22.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.9319F, -14.4824F, 0.0F, 0.0F, 0.0F, 1.309F));

        PartDefinition bone14 = base.addOrReplaceChild("bone14", CubeListBuilder.create(),
                PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition cube_r7 = bone14.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 42).addBox(-0.0282F,
                -4.6997F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone2 = bone14.addOrReplaceChild("bone2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r8 = bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 42).addBox(-0.0282F, -4.6997F,
                -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone15 = bone2.addOrReplaceChild("bone15", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r9 = bone15.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 42).addBox(-0.0282F,
                -4.6997F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone16 = bone15.addOrReplaceChild("bone16", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r10 = bone16.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 42).addBox(-0.0282F,
                -4.6997F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone17 = bone16.addOrReplaceChild("bone17", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r11 = bone17.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 42).addBox(-0.0282F,
                -4.6997F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone18 = bone17.addOrReplaceChild("bone18", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r12 = bone18.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 42).addBox(-0.0282F,
                -4.6997F, -5.0F, 0.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(9.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition rim = bone.addOrReplaceChild("rim", CubeListBuilder.create().texOffs(0, 42).addBox(17.185F, -17.0F, -10.5F,
                1.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.4F, 0.0F));

        PartDefinition bone27 = rim.addOrReplaceChild("bone27", CubeListBuilder.create().texOffs(0, 42).addBox(17.185F, -17.0F,
                -10.5F, 1.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone28 = bone27.addOrReplaceChild("bone28", CubeListBuilder.create().texOffs(0, 42).addBox(17.185F, -17.0F,
                -10.5F, 1.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone29 = bone28.addOrReplaceChild("bone29", CubeListBuilder.create().texOffs(0, 42).addBox(17.185F, -17.0F,
                -10.5F, 1.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone30 = bone29.addOrReplaceChild("bone30", CubeListBuilder.create().texOffs(0, 42).addBox(17.185F, -17.0F,
                -10.5F, 1.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone31 = bone30.addOrReplaceChild("bone31", CubeListBuilder.create().texOffs(0, 42).addBox(17.185F, -17.0F,
                -10.5F, 1.0F, 2.0F, 21.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bottom = bone.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 21).addBox(5.25F, -16.0F, -10.0F,
                12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone51 = bottom.addOrReplaceChild("bone51", CubeListBuilder.create().texOffs(0, 21).addBox(5.25F, -16.0F,
                -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone52 = bone51.addOrReplaceChild("bone52", CubeListBuilder.create().texOffs(0, 21).addBox(5.25F, -16.0F,
                -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone53 = bone52.addOrReplaceChild("bone53", CubeListBuilder.create().texOffs(0, 21).addBox(5.25F, -16.0F,
                -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone54 = bone53.addOrReplaceChild("bone54", CubeListBuilder.create().texOffs(0, 21).addBox(5.25F, -16.0F,
                -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone55 = bone54.addOrReplaceChild("bone55", CubeListBuilder.create().texOffs(0, 21).addBox(5.25F, -16.0F,
                -10.0F, 12.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone32 = bone.addOrReplaceChild("bone32", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -0.5236F, 0.0F));

        PartDefinition cube_r13 = bone32.addOrReplaceChild("cube_r13",
                CubeListBuilder.create().texOffs(47, 13).addBox(-9.6F, -0.75F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(16.0F, -17.0F, 0.0F, 0.0F, 0.0F, 0.3142F));

        PartDefinition bone20 = bone32.addOrReplaceChild("bone20", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r14 = bone20.addOrReplaceChild("cube_r14",
                CubeListBuilder.create().texOffs(47, 13).addBox(-9.6F, -0.75F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(16.0F, -17.0F, 0.0F, 0.0F, 0.0F, 0.3142F));

        PartDefinition bone21 = bone20.addOrReplaceChild("bone21", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r15 = bone21.addOrReplaceChild("cube_r15",
                CubeListBuilder.create().texOffs(47, 13).addBox(-9.6F, -0.75F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(16.0F, -17.0F, 0.0F, 0.0F, 0.0F, 0.3142F));

        PartDefinition bone22 = bone21.addOrReplaceChild("bone22", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r16 = bone22.addOrReplaceChild("cube_r16",
                CubeListBuilder.create().texOffs(47, 13).addBox(-9.6F, -0.75F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(16.0F, -17.0F, 0.0F, 0.0F, 0.0F, 0.3142F));

        PartDefinition bone23 = bone22.addOrReplaceChild("bone23", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r17 = bone23.addOrReplaceChild("cube_r17",
                CubeListBuilder.create().texOffs(47, 13).addBox(-9.6F, -0.75F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(16.0F, -17.0F, 0.0F, 0.0F, 0.0F, 0.3142F));

        PartDefinition bone24 = bone23.addOrReplaceChild("bone24", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r18 = bone24.addOrReplaceChild("cube_r18",
                CubeListBuilder.create().texOffs(47, 13).addBox(-9.6F, -0.75F, 0.0F, 14.0F, 1.0F, 0.0F, new CubeDeformation(0.3F)),
                PartPose.offsetAndRotation(16.0F, -17.0F, 0.0F, 0.0F, 0.0F, 0.3142F));

        PartDefinition panels = bone.addOrReplaceChild("panels", CubeListBuilder.create(),
                PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition p_1 = panels.addOrReplaceChild("p_1", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone38 = p_1.addOrReplaceChild("bone38", CubeListBuilder.create().texOffs(0, 0).addBox(-11.25F, -0.95F, -10.0F,
                13.0F, 0.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition bone36 = bone38.addOrReplaceChild("bone36", CubeListBuilder.create(),
                PartPose.offset(-16.0F, 13.0F, 0.0F));

        PartDefinition bone37 = bone36.addOrReplaceChild("bone37",
                CubeListBuilder.create().texOffs(45, 21).addBox(6.7F, -14.15F, -2.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 21).addBox(11.2F, -14.15F, -2.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_1 = bone37.addOrReplaceChild("sl_switch_1", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r19 = sl_switch_1.addOrReplaceChild("cube_r19",
                CubeListBuilder.create().texOffs(53, 68).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone33 = sl_switch_1.addOrReplaceChild("bone33", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, 0.0F));

        PartDefinition cube_r20 = bone33.addOrReplaceChild("cube_r20",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_2 = bone37.addOrReplaceChild("sl_switch_2", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r21 = sl_switch_2.addOrReplaceChild("cube_r21",
                CubeListBuilder.create().texOffs(32, 68).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, 1.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone34 = sl_switch_2.addOrReplaceChild("bone34", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, 1.5F));

        PartDefinition cube_r22 = bone34.addOrReplaceChild("cube_r22",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_3 = bone37.addOrReplaceChild("sl_switch_3", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r23 = sl_switch_3.addOrReplaceChild("cube_r23",
                CubeListBuilder.create().texOffs(39, 68).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, 3.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone35 = sl_switch_3.addOrReplaceChild("bone35", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, 3.0F));

        PartDefinition cube_r24 = bone35.addOrReplaceChild("cube_r24",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_4 = bone37.addOrReplaceChild("sl_switch_4", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r25 = sl_switch_4.addOrReplaceChild("cube_r25",
                CubeListBuilder.create().texOffs(32, 68).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, 4.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone47 = sl_switch_4.addOrReplaceChild("bone47", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, 4.5F));

        PartDefinition cube_r26 = bone47.addOrReplaceChild("cube_r26",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition ind_lamp_1 = bone37.addOrReplaceChild("ind_lamp_1",
                CubeListBuilder.create().texOffs(56, 42).addBox(11.2F, -16.15F, -4.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(11.2F, -17.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone91 = ind_lamp_1.addOrReplaceChild("bone91", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F,
                -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(16.0F, -16.0F, 0.0F));

        PartDefinition ind_lamp_2 = bone37.addOrReplaceChild("ind_lamp_2",
                CubeListBuilder.create().texOffs(45, 25).addBox(9.0F, -17.0F, -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F))
                        .texOffs(56, 42).addBox(9.0F, -16.15F, -3.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone93 = ind_lamp_2.addOrReplaceChild("bone93", CubeListBuilder.create().texOffs(11, 42).addBox(-7.0F, -1.0F,
                -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(16.0F, -16.0F, 0.0F));

        PartDefinition ind_lamp_3 = bone37.addOrReplaceChild("ind_lamp_3",
                CubeListBuilder.create().texOffs(56, 42).addBox(9.0F, -16.15F, -1.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(9.0F, -17.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone92 = ind_lamp_3.addOrReplaceChild("bone92", CubeListBuilder.create().texOffs(11, 42).addBox(-7.0F, -1.0F,
                -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(16.0F, -16.0F, 0.0F));

        PartDefinition ind_lamp_4 = bone37.addOrReplaceChild("ind_lamp_4",
                CubeListBuilder.create().texOffs(56, 42).addBox(9.0F, -16.15F, 0.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(9.0F, -17.0F, 0.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone94 = ind_lamp_4.addOrReplaceChild("bone94",
                CubeListBuilder.create().texOffs(11, 42).addBox(-7.0F, -1.0F, 0.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)),
                PartPose.offset(16.0F, -16.0F, 0.0F));

        PartDefinition ind_lamp_5 = bone37.addOrReplaceChild("ind_lamp_5",
                CubeListBuilder.create().texOffs(56, 42).addBox(11.2F, -16.15F, 1.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(11.2F, -17.0F, 1.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone95 = ind_lamp_5.addOrReplaceChild("bone95",
                CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F, 1.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)),
                PartPose.offset(16.0F, -16.0F, 0.0F));

        PartDefinition m_lever_1 = bone37.addOrReplaceChild("m_lever_1",
                CubeListBuilder.create().texOffs(0, 38).addBox(13.35F, -16.15F, -7.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 34).addBox(13.35F, -17.4F, -7.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone45 = m_lever_1.addOrReplaceChild("bone45",
                CubeListBuilder.create().texOffs(17, 0).addBox(-0.6F, -1.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.6F, -16.35F, -6.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r27 = bone45.addOrReplaceChild("cube_r27",
                CubeListBuilder.create().texOffs(10, 61).addBox(-0.6F, -1.5F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition st_switch = bone37.addOrReplaceChild("st_switch",
                CubeListBuilder.create().texOffs(47, 16)
                        .addBox(13.95F, -16.15F, -4.8F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(24, 55)
                        .addBox(13.95F, -16.85F, -4.75F, 3.0F, 1.0F, 3.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone26 = st_switch.addOrReplaceChild("bone26",
                CubeListBuilder.create().texOffs(0, 13).addBox(0.0F, -1.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.5F, -16.55F, -3.25F, 0.0F, -0.5236F, 0.0F));

        PartDefinition m_lever_2 = bone37.addOrReplaceChild("m_lever_2",
                CubeListBuilder.create().texOffs(0, 38).addBox(13.35F, -16.15F, -1.6F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 34).addBox(13.35F, -17.4F, -1.6F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone46 = m_lever_2.addOrReplaceChild("bone46",
                CubeListBuilder.create().texOffs(17, 0).addBox(-0.6F, -1.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.6F, -16.35F, -0.6F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r28 = bone46.addOrReplaceChild("cube_r28",
                CubeListBuilder.create().texOffs(10, 61).addBox(-0.6F, -1.5F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition fastreturn = bone37.addOrReplaceChild("fastreturn",
                CubeListBuilder.create().texOffs(11, 26).addBox(8.2F, -17.3F, 3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.401F))
                        .texOffs(24, 42).addBox(8.2F, -17.0F, 3.0F, 3.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone25 = fastreturn.addOrReplaceChild("bone25",
                CubeListBuilder.create().texOffs(0, 47).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.6F)),
                PartPose.offset(9.75F, -15.9F, 4.0F));

        PartDefinition p_2 = panels.addOrReplaceChild("p_2", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.0472F, 0.0F));

        PartDefinition bone48 = p_2
                .addOrReplaceChild(
                        "bone48", CubeListBuilder.create().texOffs(58, 47).addBox(-11.25F, -0.95F, -10.0F, 13.0F, 0.0F,
                                20.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(16.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition bone49 = bone48.addOrReplaceChild("bone49", CubeListBuilder.create(),
                PartPose.offset(-16.0F, 13.0F, 0.0F));

        PartDefinition bone50 = bone49.addOrReplaceChild("bone50",
                CubeListBuilder.create().texOffs(54, 35).addBox(6.7F, -14.15F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition s_lever = bone50.addOrReplaceChild("s_lever", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r29 = s_lever.addOrReplaceChild("cube_r29",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(9.4F, -15.5F, 2.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone61 = s_lever.addOrReplaceChild("bone61", CubeListBuilder.create().texOffs(50, 39).addBox(-1.0501F, 0.0F,
                -0.2F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.65F, -16.25F, -4.5F, 0.0F, 0.0F, 0.7418F));

        PartDefinition sl_switch_5 = bone50.addOrReplaceChild("sl_switch_5", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r30 = sl_switch_5.addOrReplaceChild("cube_r30",
                CubeListBuilder.create().texOffs(9, 58).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(9.4F, -15.5F, 2.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone56 = sl_switch_5.addOrReplaceChild("bone56", CubeListBuilder.create(),
                PartPose.offset(9.4F, -15.3F, 2.0F));

        PartDefinition cube_r31 = bone56.addOrReplaceChild("cube_r31",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_6 = bone50.addOrReplaceChild("sl_switch_6", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r32 = sl_switch_6.addOrReplaceChild("cube_r32",
                CubeListBuilder.create().texOffs(39, 68).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(12.9F, -15.5F, 3.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone57 = sl_switch_6.addOrReplaceChild("bone57", CubeListBuilder.create(),
                PartPose.offset(12.9F, -15.3F, 3.0F));

        PartDefinition cube_r33 = bone57.addOrReplaceChild("cube_r33",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_7 = bone50.addOrReplaceChild("sl_switch_7", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r34 = sl_switch_7.addOrReplaceChild("cube_r34",
                CubeListBuilder.create().texOffs(9, 58).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(12.9F, -15.5F, 4.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone58 = sl_switch_7.addOrReplaceChild("bone58", CubeListBuilder.create(),
                PartPose.offset(12.9F, -15.3F, 4.5F));

        PartDefinition cube_r35 = bone58.addOrReplaceChild("cube_r35",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition s_crank_1 = bone50.addOrReplaceChild("s_crank_1", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone59 = s_crank_1.addOrReplaceChild("bone59",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.85F, -16.2F, -6.7F, 0.0F, 1.0908F, 0.0F));

        PartDefinition s_crank_2 = bone50.addOrReplaceChild("s_crank_2", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone60 = s_crank_2.addOrReplaceChild("bone60",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(15.85F, -16.2F, -3.7F));

        PartDefinition ind_lamp_6 = bone50.addOrReplaceChild("ind_lamp_6",
                CubeListBuilder.create().texOffs(56, 42).addBox(12.2F, -16.15F, -4.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(12.2F, -17.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone86 = ind_lamp_6.addOrReplaceChild("bone86", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F,
                -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.0F, -16.0F, 0.0F));

        PartDefinition ind_lamp_7 = bone50.addOrReplaceChild("ind_lamp_7",
                CubeListBuilder.create().texOffs(45, 25)
                        .addBox(10.0F, -17.0F, -2.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)).texOffs(56, 42)
                        .addBox(10.0F, -16.15F, -2.95F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone87 = ind_lamp_7.addOrReplaceChild("bone87", CubeListBuilder.create().texOffs(11, 42).addBox(-7.0F, -1.0F,
                -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.0F, -16.0F, 0.75F));

        PartDefinition ind_lamp_8 = bone50.addOrReplaceChild("ind_lamp_8",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(12.15F, -16.15F, -1.5F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(12.15F, -17.0F, -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone88 = ind_lamp_8.addOrReplaceChild("bone88", CubeListBuilder.create().texOffs(11, 42).addBox(-7.0F, -1.0F,
                -1.5F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(19.15F, -16.0F, 0.0F));

        PartDefinition ind_lamp_9 = bone50.addOrReplaceChild("ind_lamp_9",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(10.0F, -16.15F, -0.05F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(10.0F, -17.0F, -0.05F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone89 = ind_lamp_9.addOrReplaceChild("bone89",
                CubeListBuilder.create().texOffs(11, 42).addBox(-7.0F, -1.0F, 0.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)),
                PartPose.offset(17.0F, -16.0F, -0.75F));

        PartDefinition ind_lamp_10 = bone50.addOrReplaceChild("ind_lamp_10",
                CubeListBuilder.create().texOffs(56, 42).addBox(12.2F, -16.15F, 1.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(12.2F, -17.0F, 1.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone90 = ind_lamp_10.addOrReplaceChild("bone90",
                CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F, 1.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)),
                PartPose.offset(17.0F, -16.0F, 0.0F));

        PartDefinition misc_ctrl = bone50.addOrReplaceChild("misc_ctrl",
                CubeListBuilder.create().texOffs(0, 13).addBox(14.7F, -16.15F, -2.3F, 2.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone62 = misc_ctrl.addOrReplaceChild("bone62",
                CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(15.7F, -17.0F, -1.3F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone63 = misc_ctrl.addOrReplaceChild("bone63",
                CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, -0.85F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.25F, -16.15F, 0.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone65 = misc_ctrl.addOrReplaceChild("bone65",
                CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, -0.85F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.25F, -16.15F, 0.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone64 = misc_ctrl.addOrReplaceChild("bone64",
                CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, -0.85F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.25F, -16.15F, 1.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone80 = misc_ctrl.addOrReplaceChild("bone80",
                CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, -0.85F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.25F, -16.15F, 2.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone66 = misc_ctrl.addOrReplaceChild("bone66",
                CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, -0.85F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.25F, -16.15F, 1.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition bone81 = misc_ctrl.addOrReplaceChild("bone81",
                CubeListBuilder.create().texOffs(16, 31).addBox(0.0F, -0.85F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.25F, -16.15F, 2.5F, 0.0F, 0.0F, -0.7418F));

        PartDefinition sym_lamp = bone50.addOrReplaceChild("sym_lamp",
                CubeListBuilder.create().texOffs(10, 5)
                        .addBox(14.4121F, -16.85F, 3.2879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(0, 5)
                        .addBox(14.4121F, -16.85F, 3.2879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone97 = sym_lamp.addOrReplaceChild("bone97", CubeListBuilder.create().texOffs(0, 79).addBox(-1.2879F, -0.85F,
                -1.7121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F)), PartPose.offset(15.7F, -16.0F, 5.0F));

        PartDefinition p_3 = panels.addOrReplaceChild("p_3", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone67 = p_3
                .addOrReplaceChild(
                        "bone67", CubeListBuilder.create().texOffs(58, 68).addBox(-11.25F, -0.95F, -10.0F, 13.0F, 0.0F,
                                20.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(16.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition bone68 = bone67.addOrReplaceChild("bone68", CubeListBuilder.create(),
                PartPose.offset(-16.0F, 13.0F, 0.0F));

        PartDefinition bone69 = bone68.addOrReplaceChild("bone69",
                CubeListBuilder.create().texOffs(73, 21).addBox(6.7F, -14.15F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition s_lever_2 = bone69.addOrReplaceChild("s_lever_2", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r36 = s_lever_2.addOrReplaceChild("cube_r36",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(8.9F, -15.5F, 5.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone70 = s_lever_2.addOrReplaceChild("bone70", CubeListBuilder.create().texOffs(50, 39).addBox(-1.0501F, 0.0F,
                -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.15F, -16.25F, -0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition s_lever_3 = bone69.addOrReplaceChild("s_lever_3", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r37 = s_lever_3.addOrReplaceChild("cube_r37",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(11.25F, -15.5F, 5.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone76 = s_lever_3.addOrReplaceChild("bone76", CubeListBuilder.create().texOffs(11, 50).addBox(-1.0501F, 0.0F,
                -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.5F, -16.25F, -0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition s_lever_4 = bone69.addOrReplaceChild("s_lever_4", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r38 = s_lever_4.addOrReplaceChild("cube_r38",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(13.55F, -15.5F, 5.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone77 = s_lever_4.addOrReplaceChild("bone77", CubeListBuilder.create().texOffs(50, 39).addBox(-1.0501F, 0.0F,
                -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.8F, -16.25F, -0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition s_lever_5 = bone69.addOrReplaceChild("s_lever_5", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r39 = s_lever_5.addOrReplaceChild("cube_r39",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(13.55F, -15.5F, 7.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone71 = s_lever_5.addOrReplaceChild("bone71", CubeListBuilder.create().texOffs(11, 50).addBox(-1.0501F, 0.0F,
                -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.8F, -16.25F, 0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition sl_switch_9 = bone69.addOrReplaceChild("sl_switch_9", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r40 = sl_switch_9.addOrReplaceChild("cube_r40",
                CubeListBuilder.create().texOffs(9, 58).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, 3.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone72 = sl_switch_9.addOrReplaceChild("bone72", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, 3.0F));

        PartDefinition cube_r41 = bone72.addOrReplaceChild("cube_r41",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_10 = bone69.addOrReplaceChild("sl_switch_10", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r42 = sl_switch_10.addOrReplaceChild("cube_r42",
                CubeListBuilder.create().texOffs(39, 68).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, 4.5F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone73 = sl_switch_10.addOrReplaceChild("bone73", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, 4.5F));

        PartDefinition cube_r43 = bone73.addOrReplaceChild("cube_r43",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition s_crank_3 = bone69.addOrReplaceChild("s_crank_3", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone74 = s_crank_3.addOrReplaceChild("bone74",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(15.6F, -16.2F, -6.2F, 0.0F, -1.4399F, 0.0F));

        PartDefinition s_crank_4 = bone69.addOrReplaceChild("s_crank_4", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone75 = s_crank_4.addOrReplaceChild("bone75",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(12.85F, -16.2F, -5.2F, 0.0F, -0.6981F, 0.0F));

        PartDefinition s_crank_5 = bone69.addOrReplaceChild("s_crank_5", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone78 = s_crank_5.addOrReplaceChild("bone78",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.35F, -16.2F, 1.3F, 0.0F, -0.4363F, 0.0F));

        PartDefinition ind_lamp_11 = bone69.addOrReplaceChild("ind_lamp_11",
                CubeListBuilder.create().texOffs(56, 42).addBox(9.2F, -16.15F, -3.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(9.2F, -17.0F, -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone82 = ind_lamp_11.addOrReplaceChild("bone82", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F,
                -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(14.0F, -16.0F, 1.0F));

        PartDefinition ind_lamp_12 = bone69.addOrReplaceChild("ind_lamp_12",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(11.45F, -16.15F, -3.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(11.45F, -17.0F, -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone83 = ind_lamp_12.addOrReplaceChild("bone83", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F,
                -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(16.25F, -16.0F, 1.0F));

        PartDefinition ind_lamp_13 = bone69.addOrReplaceChild("ind_lamp_13",
                CubeListBuilder.create().texOffs(56, 42).addBox(13.7F, -16.15F, -3.7F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(13.7F, -17.0F, -3.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone84 = ind_lamp_13.addOrReplaceChild("bone84", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F,
                -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(18.5F, -16.0F, 1.0F));

        PartDefinition ind_lamp_14 = bone69.addOrReplaceChild("ind_lamp_14",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(12.45F, -16.15F, 1.55F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(12.45F, -17.0F, 1.55F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone85 = ind_lamp_14.addOrReplaceChild("bone85", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F, -1.0F,
                -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.25F, -16.0F, 6.25F));

        PartDefinition sym_lamp2 = bone69.addOrReplaceChild("sym_lamp2",
                CubeListBuilder.create().texOffs(10, 5)
                        .addBox(9.4121F, -16.85F, 2.8879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(0, 5)
                        .addBox(9.4121F, -16.85F, 2.8879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone96 = sym_lamp2.addOrReplaceChild("bone96", CubeListBuilder.create().texOffs(0, 79).addBox(-1.2879F, -0.85F,
                -1.7121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F)), PartPose.offset(10.7F, -16.0F, 4.6F));

        PartDefinition ctrl_switch = bone69.addOrReplaceChild("ctrl_switch", CubeListBuilder.create().texOffs(47, 8).addBox(14.95F,
                -16.15F, 1.55F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone79 = ctrl_switch.addOrReplaceChild("bone79", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.05F, -16.15F, 2.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition p_4 = panels.addOrReplaceChild("p_4", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition bone98 = p_4
                .addOrReplaceChild(
                        "bone98", CubeListBuilder.create().texOffs(58, 89).addBox(-11.25F, -0.95F, -10.0F, 13.0F, 0.0F,
                                20.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(16.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition bone99 = bone98.addOrReplaceChild("bone99", CubeListBuilder.create(),
                PartPose.offset(-16.0F, 13.0F, 0.0F));

        PartDefinition bone100 = bone99.addOrReplaceChild("bone100",
                CubeListBuilder.create().texOffs(73, 26).addBox(6.7F, -14.15F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ind_lamp_16 = bone100.addOrReplaceChild("ind_lamp_16",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(12.95F, -16.15F, -4.2F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(12.95F, -17.0F, -4.2F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone111 = ind_lamp_16.addOrReplaceChild("bone111", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.75F, -16.0F, 0.5F));

        PartDefinition ind_lamp_15 = bone100.addOrReplaceChild("ind_lamp_15",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(12.95F, -16.15F, -1.95F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(12.95F, -17.0F, -1.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone101 = ind_lamp_15.addOrReplaceChild("bone101", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.75F, -16.0F, 2.75F));

        PartDefinition ind_lamp_17 = bone100.addOrReplaceChild("ind_lamp_17",
                CubeListBuilder.create().texOffs(56, 42).addBox(12.95F, -16.15F, 0.3F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(12.95F, -17.0F, 0.3F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone102 = ind_lamp_17.addOrReplaceChild("bone102", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.75F, -16.0F, 5.0F));

        PartDefinition ind_lamp_18 = bone100.addOrReplaceChild("ind_lamp_18",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(12.95F, -16.15F, 2.55F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(12.95F, -17.0F, 2.55F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone103 = ind_lamp_18.addOrReplaceChild("bone103", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(17.75F, -16.0F, 7.25F));

        PartDefinition ctrl_panel_2 = bone100.addOrReplaceChild("ctrl_panel_2", CubeListBuilder.create().texOffs(0, 79).addBox(15.2F,
                -16.15F, -5.95F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone115 = ctrl_panel_2.addOrReplaceChild("bone115", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.3F, -16.15F, -3.25F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone104 = ctrl_panel_2.addOrReplaceChild("bone104", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.3F, -16.15F, -2.25F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone105 = ctrl_panel_2.addOrReplaceChild("bone105", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(16.3F, -16.15F, -0.75F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone106 = ctrl_panel_2.addOrReplaceChild("bone106",
                CubeListBuilder.create().texOffs(0, 83).addBox(-0.5F, -0.9F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(0, 86).addBox(-1.0F, -0.9F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(16.2F, -16.0F, -5.0F));

        PartDefinition bone107 = ctrl_panel_2.addOrReplaceChild("bone107",
                CubeListBuilder.create().texOffs(0, 83).addBox(-0.5F, -0.9F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(0, 86).addBox(-1.0F, -0.9F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(16.2F, -16.0F, 2.0F));

        PartDefinition bone108 = ctrl_panel_2.addOrReplaceChild("bone108",
                CubeListBuilder.create().texOffs(0, 83).addBox(-0.5F, -0.9F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(0, 86).addBox(-1.0F, -0.9F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(16.2F, -16.0F, 0.5F));

        PartDefinition m_sensor_1 = bone100.addOrReplaceChild("m_sensor_1",
                CubeListBuilder.create().texOffs(0, 0).addBox(10.5F, -16.5F, -2.15F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 0).addBox(10.5F, -16.5F, -2.15F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition m_sensor_2 = bone100.addOrReplaceChild("m_sensor_2",
                CubeListBuilder.create().texOffs(0, 0).addBox(10.5F, -16.5F, 0.15F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(10, 0).addBox(10.5F, -16.5F, 0.15F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition m_meter_1 = bone100.addOrReplaceChild("m_meter_1",
                CubeListBuilder.create().texOffs(78, 13).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F))
                        .texOffs(88, 31).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(88, 37)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(88, 37)
                        .addBox(-1.5F, -0.25F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(11.0F, -13.75F, 4.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone109 = m_meter_1.addOrReplaceChild("bone109", CubeListBuilder.create(),
                PartPose.offset(0.5F, -0.6F, -0.5F));

        PartDefinition cube_r44 = bone109.addOrReplaceChild("cube_r44",
                CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -2.05F, -0.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 2.0F, 0.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition m_meter_2 = bone100.addOrReplaceChild("m_meter_2",
                CubeListBuilder.create().texOffs(78, 13).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F))
                        .texOffs(88, 31).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(88, 37)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(88, 37)
                        .addBox(-1.5F, -0.25F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(11.0F, -13.75F, -4.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone110 = m_meter_2.addOrReplaceChild("bone110", CubeListBuilder.create(),
                PartPose.offset(0.5F, -0.6F, -0.5F));

        PartDefinition cube_r45 = bone110.addOrReplaceChild("cube_r45",
                CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -2.05F, -0.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 2.0F, 0.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition s_knob = bone100.addOrReplaceChild("s_knob", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone129 = s_knob.addOrReplaceChild("bone129",
                CubeListBuilder.create().texOffs(0, 42).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offsetAndRotation(15.5F, -16.75F, 6.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition p_5 = panels.addOrReplaceChild("p_5", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone112 = p_5
                .addOrReplaceChild(
                        "bone112", CubeListBuilder.create().texOffs(58, 110).addBox(-11.25F, -0.95F, -10.0F, 13.0F, 0.0F,
                                20.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(16.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition bone113 = bone112.addOrReplaceChild("bone113", CubeListBuilder.create(),
                PartPose.offset(-16.0F, 13.0F, 0.0F));

        PartDefinition bone114 = bone113.addOrReplaceChild("bone114",
                CubeListBuilder.create().texOffs(73, 21).addBox(6.7F, -14.15F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ind_lamp_19 = bone114.addOrReplaceChild("ind_lamp_19",
                CubeListBuilder.create().texOffs(56, 42).addBox(6.7F, -16.15F, 2.1F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(6.7F, -17.0F, 2.1F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone116 = ind_lamp_19.addOrReplaceChild("bone116", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(11.5F, -16.0F, 6.8F));

        PartDefinition ind_lamp_20 = bone114.addOrReplaceChild("ind_lamp_20", CubeListBuilder.create().texOffs(45, 25).addBox(13.4F,
                -17.0F, -1.95F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone117 = ind_lamp_20.addOrReplaceChild("bone117", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(18.2F, -16.0F, 2.75F));

        PartDefinition ind_lamp_21 = bone114.addOrReplaceChild("ind_lamp_21", CubeListBuilder.create().texOffs(45, 25).addBox(13.45F,
                -17.0F, 5.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone118 = ind_lamp_21.addOrReplaceChild("bone118", CubeListBuilder.create().texOffs(11, 42).addBox(-5.3F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(18.75F, -16.0F, 9.7F));

        PartDefinition ctrl_panel_3 = bone114.addOrReplaceChild("ctrl_panel_3", CubeListBuilder.create().texOffs(0, 79).addBox(13.45F,
                -16.15F, -1.95F, 2.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone120 = ctrl_panel_3.addOrReplaceChild("bone120", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.55F, -16.15F, 1.65F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone121 = ctrl_panel_3.addOrReplaceChild("bone121", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.55F, -16.15F, 2.65F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone119 = ctrl_panel_3.addOrReplaceChild("bone119", CubeListBuilder.create().texOffs(11, 31).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.55F, -16.15F, 3.65F, 0.0F, 0.0F, 0.7418F));

        PartDefinition bone123 = ctrl_panel_3.addOrReplaceChild("bone123",
                CubeListBuilder.create().texOffs(0, 83).addBox(-0.5F, -0.9F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(0, 86).addBox(-1.0F, -0.9F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(14.45F, -16.0F, 0.4F));

        PartDefinition bone125 = ctrl_panel_3.addOrReplaceChild("bone125",
                CubeListBuilder.create().texOffs(0, 83).addBox(-0.5F, -0.9F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(-0.2F))
                        .texOffs(0, 86).addBox(-1.0F, -0.9F, 0.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(14.45F, -16.0F, 4.25F));

        PartDefinition m_meter_3 = bone114.addOrReplaceChild("m_meter_3",
                CubeListBuilder.create().texOffs(78, 13).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F))
                        .texOffs(88, 31).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(88, 37)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(88, 37)
                        .addBox(-1.5F, -0.25F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(11.0F, -13.75F, 4.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone126 = m_meter_3.addOrReplaceChild("bone126", CubeListBuilder.create(),
                PartPose.offset(0.5F, -0.6F, -0.5F));

        PartDefinition cube_r46 = bone126.addOrReplaceChild("cube_r46",
                CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -2.05F, -0.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 2.0F, 0.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition m_meter_4 = bone114.addOrReplaceChild("m_meter_4",
                CubeListBuilder.create().texOffs(78, 13).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.4F))
                        .texOffs(88, 31).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.3F)).texOffs(88, 37)
                        .addBox(-1.5F, -1.0F, -1.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(-0.2F)).texOffs(88, 37)
                        .addBox(-1.5F, -0.25F, -1.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(11.0F, -13.75F, 0.25F, 0.0F, -0.7854F, 0.0F));

        PartDefinition bone127 = m_meter_4.addOrReplaceChild("bone127", CubeListBuilder.create(),
                PartPose.offset(0.5F, -0.6F, -0.5F));

        PartDefinition cube_r47 = bone127.addOrReplaceChild("cube_r47",
                CubeListBuilder.create().texOffs(78, 0).addBox(-1.0F, -2.05F, -0.75F, 2.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5F, 2.0F, 0.5F, 0.0F, 0.7854F, 0.0F));

        PartDefinition sym_lamp3 = bone114.addOrReplaceChild("sym_lamp3",
                CubeListBuilder.create().texOffs(10, 5)
                        .addBox(13.4121F, -16.85F, -7.1121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(0, 5)
                        .addBox(13.4121F, -16.85F, -7.1121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone122 = sym_lamp3.addOrReplaceChild("bone122", CubeListBuilder.create().texOffs(0, 79).addBox(-1.2879F,
                -0.85F, -1.7121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F)), PartPose.offset(14.7F, -16.0F, -5.4F));

        PartDefinition sl_switch_8 = bone114.addOrReplaceChild("sl_switch_8", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r48 = sl_switch_8.addOrReplaceChild("cube_r48",
                CubeListBuilder.create().texOffs(9, 58).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, -5.75F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone124 = sl_switch_8.addOrReplaceChild("bone124", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.3F, -5.75F));

        PartDefinition cube_r49 = bone124.addOrReplaceChild("cube_r49",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -1.0F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition sl_switch_11 = bone114.addOrReplaceChild("sl_switch_11", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r50 = sl_switch_11.addOrReplaceChild("cube_r50",
                CubeListBuilder.create().texOffs(9, 58).addBox(-0.8F, -0.65F, 1.05F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(14.9F, -15.5F, -4.25F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone128 = sl_switch_11.addOrReplaceChild("bone128", CubeListBuilder.create(),
                PartPose.offset(14.9F, -15.5F, -4.25F));

        PartDefinition cube_r51 = bone128.addOrReplaceChild("cube_r51",
                CubeListBuilder.create().texOffs(14, 18).addBox(-0.8F, -0.8F, 1.05F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone130 = bone114.addOrReplaceChild("bone130",
                CubeListBuilder.create().texOffs(0, 89).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.2F))
                        .texOffs(0, 93).addBox(-2.0F, -1.0F, -1.0F, 3.0F, 1.0F, 2.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(11.25F, -13.5F, -3.5F, 0.0F, 0.0F, -0.2618F));

        PartDefinition bone131 = bone130.addOrReplaceChild("bone131",
                CubeListBuilder.create().texOffs(0, 96).addBox(0.95F, -2.0F, -0.75F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.2F, 0.5F, 0.0F));

        PartDefinition p_6 = panels.addOrReplaceChild("p_6", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone132 = p_6
                .addOrReplaceChild(
                        "bone132", CubeListBuilder.create().texOffs(58, 131).addBox(-11.25F, -0.95F, -10.0F, 13.0F, 0.0F,
                                20.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(16.0F, -13.0F, 0.0F, 0.0F, 0.0F, 0.2618F));

        PartDefinition bone133 = bone132.addOrReplaceChild("bone133", CubeListBuilder.create(),
                PartPose.offset(-16.0F, 13.0F, 0.0F));

        PartDefinition bone134 = bone133.addOrReplaceChild("bone134",
                CubeListBuilder.create().texOffs(54, 35).addBox(6.7F, -14.15F, -2.0F, 2.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition ind_lamp_22 = bone134.addOrReplaceChild("ind_lamp_22",
                CubeListBuilder.create().texOffs(56, 42).addBox(8.7F, -16.15F, 2.25F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(8.7F, -17.0F, 2.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone135 = ind_lamp_22.addOrReplaceChild("bone135", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(13.5F, -16.0F, 6.95F));

        PartDefinition ind_lamp_25 = bone134.addOrReplaceChild("ind_lamp_25",
                CubeListBuilder.create().texOffs(56, 42).addBox(8.7F, -16.15F, -4.25F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(8.7F, -17.0F, -4.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone138 = ind_lamp_25.addOrReplaceChild("bone138", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(13.5F, -16.0F, 0.45F));

        PartDefinition ind_lamp_23 = bone134.addOrReplaceChild("ind_lamp_23",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(11.45F, -16.15F, 3.75F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(11.45F, -17.0F, 3.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone136 = ind_lamp_23.addOrReplaceChild("bone136", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(16.25F, -16.0F, 8.45F));

        PartDefinition ind_lamp_26 = bone134.addOrReplaceChild("ind_lamp_26",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(11.45F, -16.15F, -5.75F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(11.45F, -17.0F, -5.75F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone139 = ind_lamp_26.addOrReplaceChild("bone139", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(16.25F, -16.0F, -1.05F));

        PartDefinition ind_lamp_24 = bone134.addOrReplaceChild("ind_lamp_24",
                CubeListBuilder.create().texOffs(56, 42).addBox(14.2F, -16.15F, 5.25F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 25).addBox(14.2F, -17.0F, 5.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone137 = ind_lamp_24.addOrReplaceChild("bone137", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(19.0F, -16.0F, 9.95F));

        PartDefinition ind_lamp_27 = bone134.addOrReplaceChild("ind_lamp_27",
                CubeListBuilder.create().texOffs(56, 42)
                        .addBox(14.2F, -16.15F, -7.25F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(45, 25)
                        .addBox(14.2F, -17.0F, -7.25F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone140 = ind_lamp_27.addOrReplaceChild("bone140", CubeListBuilder.create().texOffs(11, 42).addBox(-4.8F,
                -1.0F, -4.7F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.38F)), PartPose.offset(19.0F, -16.0F, -2.55F));

        PartDefinition sym_lamp4 = bone134.addOrReplaceChild("sym_lamp4",
                CubeListBuilder.create().texOffs(10, 5)
                        .addBox(14.4121F, -16.85F, -4.8621F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(0, 5)
                        .addBox(14.4121F, -16.85F, -4.8621F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone145 = sym_lamp4.addOrReplaceChild("bone145", CubeListBuilder.create().texOffs(0, 79).addBox(-1.2879F,
                -0.85F, -1.7121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F)), PartPose.offset(15.7F, -16.0F, -3.15F));

        PartDefinition sym_lamp5 = bone134.addOrReplaceChild("sym_lamp5",
                CubeListBuilder.create().texOffs(10, 5)
                        .addBox(14.4121F, -16.85F, 2.8879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(0, 5)
                        .addBox(14.4121F, -16.85F, 2.8879F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.1F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone141 = sym_lamp5.addOrReplaceChild("bone141", CubeListBuilder.create().texOffs(0, 79).addBox(-1.2879F,
                -0.85F, -1.7121F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.28F)), PartPose.offset(15.7F, -16.0F, 4.6F));

        PartDefinition m_lever_3 = bone134.addOrReplaceChild("m_lever_3",
                CubeListBuilder.create().texOffs(0, 38).addBox(12.1F, -16.15F, -1.0F, 4.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(45, 34).addBox(12.1F, -17.4F, -1.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone142 = m_lever_3.addOrReplaceChild("bone142",
                CubeListBuilder.create().texOffs(17, 0).addBox(-0.6F, -1.5F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(14.35F, -16.35F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition cube_r52 = bone142.addOrReplaceChild("cube_r52",
                CubeListBuilder.create().texOffs(10, 61).addBox(-0.6F, -1.5F, -0.4F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.2F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition s_lever_6 = bone134.addOrReplaceChild("s_lever_6", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r53 = s_lever_6.addOrReplaceChild("cube_r53",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(8.9F, -15.5F, 5.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone143 = s_lever_6.addOrReplaceChild("bone143", CubeListBuilder.create().texOffs(50, 39).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.15F, -16.25F, -1.25F, 0.0F, 0.0F, 0.7418F));

        PartDefinition s_lever_7 = bone134.addOrReplaceChild("s_lever_7", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r54 = s_lever_7.addOrReplaceChild("cube_r54",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(8.9F, -15.5F, 6.45F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone144 = s_lever_7.addOrReplaceChild("bone144", CubeListBuilder.create().texOffs(11, 50).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.15F, -16.25F, 0.2F, 0.0F, 0.0F, 0.7418F));

        PartDefinition s_lever_8 = bone134.addOrReplaceChild("s_lever_8", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition cube_r55 = s_lever_8.addOrReplaceChild("cube_r55",
                CubeListBuilder.create().texOffs(66, 63).addBox(0.2F, -0.65F, -6.95F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.1F)),
                PartPose.offsetAndRotation(8.9F, -15.5F, 7.95F, 0.0F, 0.0F, 0.0F));

        PartDefinition bone146 = s_lever_8.addOrReplaceChild("bone146", CubeListBuilder.create().texOffs(50, 39).addBox(-1.0501F,
                0.0F, -0.45F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(10.15F, -16.25F, 1.7F, 0.0F, 0.0F, 0.7418F));

        PartDefinition s_crank_6 = bone134.addOrReplaceChild("s_crank_6", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone147 = s_crank_6.addOrReplaceChild("bone147",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(13.1F, -16.2F, -2.45F));

        PartDefinition s_crank_7 = bone134.addOrReplaceChild("s_crank_7", CubeListBuilder.create(),
                PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition bone148 = s_crank_7.addOrReplaceChild("bone148",
                CubeListBuilder.create().texOffs(0, 58).addBox(-1.05F, 0.05F, -1.0F, 2.0F, 0.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(64, 67).addBox(-0.3F, -0.45F, -0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offset(13.1F, -16.2F, 2.45F));

        PartDefinition bone39 = bone.addOrReplaceChild("bone39", CubeListBuilder.create().texOffs(43, 68).addBox(5.0F, -18.75F, -3.5F,
                1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.5F, 0.0F));

        PartDefinition cube_r56 = bone39.addOrReplaceChild("cube_r56",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -18.75F, -4.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.632F, 0.8688F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone40 = bone39.addOrReplaceChild("bone40", CubeListBuilder.create().texOffs(43, 68).addBox(5.0F, -18.75F,
                -3.5F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r57 = bone40.addOrReplaceChild("cube_r57",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -18.75F, -4.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.632F, 0.8688F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone41 = bone40.addOrReplaceChild("bone41", CubeListBuilder.create().texOffs(43, 68).addBox(5.0F, -18.75F,
                -3.5F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r58 = bone41.addOrReplaceChild("cube_r58",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -18.75F, -4.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.632F, 0.8688F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone42 = bone41.addOrReplaceChild("bone42", CubeListBuilder.create().texOffs(43, 68).addBox(5.0F, -18.75F,
                -3.5F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r59 = bone42.addOrReplaceChild("cube_r59",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -18.75F, -4.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.632F, 0.8688F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone43 = bone42.addOrReplaceChild("bone43", CubeListBuilder.create().texOffs(43, 68).addBox(5.0F, -18.75F,
                -3.5F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r60 = bone43.addOrReplaceChild("cube_r60",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -18.75F, -4.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.632F, 0.8688F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition bone44 = bone43.addOrReplaceChild("bone44", CubeListBuilder.create().texOffs(43, 68).addBox(5.0F, -18.75F,
                -3.5F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition cube_r61 = bone44.addOrReplaceChild("cube_r61",
                CubeListBuilder.create().texOffs(0, 0).addBox(6.0F, -18.75F, -4.5F, 0.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(7.632F, 0.8688F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition rotor = bone.addOrReplaceChild("rotor", CubeListBuilder.create(),
                PartPose.offset(0.0F, -19.5F, 0.0F));

        PartDefinition glass = rotor.addOrReplaceChild("glass", CubeListBuilder.create().texOffs(18, 79).addBox(-0.8F, -10.75F, -3.0F,
                6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition bone150 = glass.addOrReplaceChild("bone150", CubeListBuilder.create().texOffs(18, 79).addBox(-0.8F, -10.75F,
                -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone151 = bone150.addOrReplaceChild("bone151", CubeListBuilder.create().texOffs(18, 79).addBox(-0.8F, -10.75F,
                -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone152 = bone151.addOrReplaceChild("bone152", CubeListBuilder.create().texOffs(18, 79).addBox(-0.8F, -10.75F,
                -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone153 = bone152.addOrReplaceChild("bone153", CubeListBuilder.create().texOffs(18, 79).addBox(-0.8F, -10.75F,
                -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone154 = bone153.addOrReplaceChild("bone154", CubeListBuilder.create().texOffs(18, 79).addBox(-0.8F, -10.75F,
                -3.0F, 6.0F, 13.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition compass = rotor.addOrReplaceChild("compass", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition net = compass.addOrReplaceChild("net",
                CubeListBuilder.create().texOffs(0, 105).addBox(2.6F, -8.75F, -1.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 157).addBox(2.35F, -8.75F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(-3, 116)
                        .addBox(-1.4F, -1.75F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition bone149 = net.addOrReplaceChild("bone149",
                CubeListBuilder.create().texOffs(0, 105).addBox(2.6F, -8.75F, -1.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 116).addBox(-1.4F, -1.75F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone155 = bone149.addOrReplaceChild("bone155",
                CubeListBuilder.create().texOffs(0, 105).addBox(2.6F, -8.75F, -1.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 116).addBox(-1.4F, -1.75F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone156 = bone155.addOrReplaceChild("bone156",
                CubeListBuilder.create().texOffs(0, 105).addBox(2.6F, -8.75F, -1.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(0, 157).addBox(2.35F, -8.75F, -1.5F, 0.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)).texOffs(-3, 116)
                        .addBox(-1.4F, -1.75F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone157 = bone156.addOrReplaceChild("bone157",
                CubeListBuilder.create().texOffs(0, 105).addBox(2.6F, -8.75F, -1.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 116).addBox(-1.4F, -1.75F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone158 = bone157.addOrReplaceChild("bone158",
                CubeListBuilder.create().texOffs(0, 105).addBox(2.6F, -8.75F, -1.5F, 0.0F, 7.0F, 3.0F, new CubeDeformation(0.0F))
                        .texOffs(-3, 116).addBox(-1.4F, -1.75F, -1.5F, 4.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition nav = compass.addOrReplaceChild("nav",
                CubeListBuilder.create().texOffs(0, 120).addBox(1.0F, -4.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F))
                        .texOffs(0, 120).addBox(-3.0F, -4.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.3F)).texOffs(0, 125)
                        .addBox(-2.5F, -10.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(0, 145)
                        .addBox(-0.5F, -9.5F, -0.5F, 1.0F, 11.0F, 1.0F, new CubeDeformation(-0.3F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r62 = nav.addOrReplaceChild("cube_r62",
                CubeListBuilder.create().texOffs(0, 135).addBox(-2.5F, -10.0F, 0.0F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        PartDefinition bone159 = nav.addOrReplaceChild("bone159", CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r63 = bone159.addOrReplaceChild("cube_r63",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -5.1213F, -1.3713F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r64 = bone159.addOrReplaceChild("cube_r64",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -4.4142F, 0.75F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r65 = bone159.addOrReplaceChild("cube_r65",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -3.7071F, -1.3713F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r66 = bone159.addOrReplaceChild("cube_r66",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -3.0F, 0.75F, 0.7854F, 0.0F, 0.0F));

        PartDefinition bone160 = nav.addOrReplaceChild("bone160", CubeListBuilder.create(),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r67 = bone160.addOrReplaceChild("cube_r67",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -5.1213F, -1.3713F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r68 = bone160.addOrReplaceChild("cube_r68",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -4.4142F, 0.75F, 0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r69 = bone160.addOrReplaceChild("cube_r69",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -3.7071F, -1.3713F, -0.7854F, 0.0F, 0.0F));

        PartDefinition cube_r70 = bone160.addOrReplaceChild("cube_r70",
                CubeListBuilder.create().texOffs(0, 158).addBox(-1.0F, -2.0F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(1.25F, -3.0F, 0.75F, 0.7854F, 0.0F, 0.0F));

        PartDefinition frame = rotor.addOrReplaceChild("frame",
                CubeListBuilder.create().texOffs(14, 103).addBox(4.35F, -9.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 116).addBox(-0.65F, -0.75F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)).texOffs(20, 108)
                        .addBox(-4.5F, -1.5F, -2.5F, 9.0F, 0.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone161 = frame.addOrReplaceChild("bone161",
                CubeListBuilder.create().texOffs(14, 103).addBox(4.35F, -9.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 116).addBox(-0.65F, -0.75F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone162 = bone161.addOrReplaceChild("bone162",
                CubeListBuilder.create().texOffs(14, 103).addBox(4.35F, -9.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 116).addBox(-0.65F, -0.75F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone163 = bone162.addOrReplaceChild("bone163",
                CubeListBuilder.create().texOffs(14, 103).addBox(4.35F, -9.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 116).addBox(-0.65F, -0.75F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone164 = bone163.addOrReplaceChild("bone164",
                CubeListBuilder.create().texOffs(14, 103).addBox(4.35F, -9.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 116).addBox(-0.65F, -0.75F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition bone165 = bone164.addOrReplaceChild("bone165",
                CubeListBuilder.create().texOffs(14, 103).addBox(4.35F, -9.0F, -2.5F, 0.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
                        .texOffs(11, 116).addBox(-0.65F, -0.75F, -2.5F, 5.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

        PartDefinition lights = frame.addOrReplaceChild("lights", CubeListBuilder.create(),
                PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition light_1 = lights.addOrReplaceChild("light_1",
                CubeListBuilder.create().texOffs(3, 67).addBox(2.9F, -7.75F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone166 = light_1.addOrReplaceChild("bone166",
                CubeListBuilder.create().texOffs(0, 67).addBox(2.9F, -7.75F, 0.05F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone169 = light_1.addOrReplaceChild("bone169",
                CubeListBuilder.create().texOffs(0, 67).addBox(2.9F, -7.75F, -0.05F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition light_2 = lights.addOrReplaceChild("light_2",
                CubeListBuilder.create().texOffs(3, 67).addBox(2.9F, -7.75F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -2.0944F, 0.0F));

        PartDefinition bone167 = light_2.addOrReplaceChild("bone167",
                CubeListBuilder.create().texOffs(0, 67).addBox(2.9F, -7.75F, 0.05F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone170 = light_2.addOrReplaceChild("bone170",
                CubeListBuilder.create().texOffs(0, 67).addBox(2.9F, -7.75F, -0.05F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition light_3 = lights.addOrReplaceChild("light_3",
                CubeListBuilder.create().texOffs(3, 67).addBox(2.9F, -7.75F, 0.0F, 2.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.0944F, 0.0F));

        PartDefinition bone168 = light_3.addOrReplaceChild("bone168",
                CubeListBuilder.create().texOffs(0, 67).addBox(2.9F, -7.75F, 0.05F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition bone171 = light_3.addOrReplaceChild("bone171",
                CubeListBuilder.create().texOffs(0, 67).addBox(2.9F, -7.75F, -0.05F, 1.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public AnimationDefinition getAnimationForState(TravelHandlerBase.State state) {
        if (state == TravelHandlerBase.State.LANDED)
            return HartnellAnimations.HARTNELL_IDLE_ANIMATION;

        return HartnellAnimations.HARTNELL_INFLIGHT_ANIMATION;
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        bone.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public void renderWithAnimations(ConsoleBlockEntity console, ClientTardis tardis, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        float delta = !AITModClient.CONFIG.animateControls ? 1.0f : 0.1f * client.getTimer().getGameTimeDeltaPartialTick(true);
        matrices.pushPose();
        matrices.translate(0.5f, -1.5f, -0.5f);

        this.bone.getChild("panels").getChild("p_4").getChild("bone98").getChild("bone99").getChild("bone100")
                .getChild("m_meter_2")
                .getChild("bone110").yRot = (float) (((tardis.getFuel() / FuelHandler.TARDIS_MAX_FUEL) * 2) - 1);
        ModelPart fuelLowWarningLight = this.bone.getChild("panels").getChild("p_3").getChild("bone67")
                .getChild("bone68").getChild("bone69").getChild("sym_lamp2").getChild("bone96");
        // Low Fuel Light
        if (!(tardis.getFuel() <= (FuelHandler.TARDIS_MAX_FUEL / 10))) {
            fuelLowWarningLight.y = fuelLowWarningLight.y + 1;
        }

        // X Control Movement
        ModelPart xControl = this.bone.getChild("panels").getChild("p_3").getChild("bone67").getChild("bone68")
                .getChild("bone69").getChild("s_lever_2").getChild("bone70");
        ModelPart xControlLight = this.bone.getChild("panels").getChild("p_3").getChild("bone67").getChild("bone68")
                .getChild("bone69").getChild("ind_lamp_11").getChild("bone82");
        BlockPos destination = tardis.travel().destination().getPos();

        if (destination.getX() < 0) {
            xControl.zRot = xControl.zRot + 1.575f;
            xControlLight.y = xControlLight.y + 1;
        }


        // Y Control Movement
        ModelPart yControl = this.bone.getChild("panels").getChild("p_3").getChild("bone67").getChild("bone68")
                .getChild("bone69").getChild("s_lever_3").getChild("bone76");
        ModelPart yControlLight = this.bone.getChild("panels").getChild("p_3").getChild("bone67").getChild("bone68")
                .getChild("bone69").getChild("ind_lamp_12").getChild("bone83");

        if (destination.getY() < 0) {
            yControl.zRot = yControl.zRot + 1.575f;
            yControlLight.y = yControlLight.y + 1;
        }

        // Z Control Movement
        ModelPart zControl = this.bone.getChild("panels").getChild("p_3").getChild("bone67").getChild("bone68")
                .getChild("bone69").getChild("s_lever_4").getChild("bone77");
        ModelPart zControlLight = this.bone.getChild("panels").getChild("p_3").getChild("bone67").getChild("bone68")
                .getChild("bone69").getChild("ind_lamp_13").getChild("bone84");

        if (destination.getZ() < 0) {
            zControl.zRot = zControl.zRot + 1.575f;
            zControlLight.y = zControlLight.y + 1;
        }

        // Throttle Control Movements
        ModelPart throttle = this.bone.getChild("panels").getChild("p_1").getChild("bone38").getChild("bone36")
                .getChild("bone37").getChild("m_lever_1").getChild("bone45");
        float throttleTarget = tardis.travel().maxSpeed().get() > 0 ? (float) tardis.travel().speed() / (float) tardis.travel().maxSpeed().get() : 0f;
        float throttleAngle = getAngle(console, "throttle", throttleTarget, delta);
        throttle.zRot = throttleAngle - 0.5f;

        // Handbrake Control Movements
        ModelPart handbrake = this.bone.getChild("panels").getChild("p_1").getChild("bone38").getChild("bone36")
                .getChild("bone37").getChild("m_lever_2").getChild("bone46");
        float handbrakeTarget = tardis.travel().handbrake() ? 1f : 0f;
        float handbrakeAngle =  getAngle(console, "handbrake", handbrakeTarget, delta);
        handbrake.zRot = handbrakeAngle - 0.5f;

        // Power Control Movements
        ModelPart powerControl = this.bone.getChild("panels").getChild("p_6").getChild("bone132").getChild("bone133")
                .getChild("bone134").getChild("m_lever_3").getChild("bone142");
        ModelPart rotor = this.bone.getChild("rotor");
        float powerTarget = tardis.fuel().hasPower() ? 1f : 0f;
        float powerAngle = getAngle(console, "power", powerTarget, delta);
        powerControl.zRot = powerAngle - 0.5f;
        float rotorTarget = !tardis.fuel().hasPower() ? rotor.y + 5 : -19.5f;
        float rotorAngle = getAngle(console, "rotor", rotorTarget, delta);
        if (tardis.travel().isLanded()) rotor.y = rotorAngle;

        // Door Control Movements
        ModelPart doorControl = this.bone.getChild("panels").getChild("p_5").getChild("bone112").getChild("bone113")
                .getChild("bone114").getChild("ctrl_panel_3").getChild("bone123");
        ModelPart doorControlLight = this.bone.getChild("panels").getChild("p_5").getChild("bone112")
                .getChild("bone113").getChild("bone114").getChild("ind_lamp_20").getChild("bone117");
        float doorControlTarget = tardis.door().isLeftOpen() ? 2.075f : tardis.door().areBothOpen() ? 3.15f : 0.5f;
        float doorControlAngle = getAngle(console, "door_control", doorControlTarget, delta);
        doorControl.yRot = doorControlAngle - 0.5f;
        if (tardis.door().isLeftOpen()) {
            doorControlLight.y = doorControlLight.y + 1;
        } else if (tardis.door().isRightOpen()) {
            doorControlLight.y = doorControlLight.y + 1;
        }

        // Door Lock Control Movement
        ModelPart doorLock = this.bone.getChild("panels").getChild("p_5").getChild("bone112").getChild("bone113")
                .getChild("bone114").getChild("ctrl_panel_3").getChild("bone125");
        ModelPart doorLockLight = this.bone.getChild("panels").getChild("p_5").getChild("bone112").getChild("bone113")
                .getChild("bone114").getChild("ind_lamp_21").getChild("bone118");
        float doorLockTarget = tardis.door().locked() ? 2.075f : 0.5f;
        float doorLockAngle = getAngle(console, "door_lock", doorLockTarget, delta);
        doorLock.yRot = doorLockAngle - 0.5f;
        doorLockLight.y = tardis.door().locked() ? doorLockLight.y + 1 : doorLockLight.y;

        // Refueler Control Movements
        ModelPart refueler = this.bone.getChild("panels").getChild("p_4").getChild("bone98").getChild("bone99")
                .getChild("bone100").getChild("ctrl_panel_2").getChild("bone106");
        ModelPart refuelerLight = this.bone.getChild("panels").getChild("p_4").getChild("bone98").getChild("bone99")
                .getChild("bone100").getChild("ind_lamp_16").getChild("bone111");
        float refuelerTarget = tardis.isRefueling() ? 2.075f : 0.5f;
        float refuelerAngle = getAngle(console, "refueler", refuelerTarget, delta);
        refueler.yRot = refuelerAngle - 0.5f;
        refuelerLight.y = tardis.isRefueling() ? refuelerLight.y : refuelerLight.y + 1;

        ModelPart cloak = this.bone.getChild("panels").getChild("p_4").getChild("bone98").getChild("bone99")
                .getChild("bone100").getChild("ctrl_panel_2").getChild("bone108");
        ModelPart cloakLight = this.bone.getChild("panels").getChild("p_4").getChild("bone98").getChild("bone99")
                .getChild("bone100").getChild("ind_lamp_15").getChild("bone101");
        float cloakTarget = tardis.<CloakHandler>handler(TardisComponent.Id.CLOAK).cloaked().get()
                ? 2.075f
                : 0.5f;
        float cloakAngle = getAngle(console, "cloak", cloakTarget, delta);
        cloak.yRot = cloakAngle - 0.5f;
        cloakLight.y = tardis.<CloakHandler>handler(TardisComponent.Id.CLOAK).cloaked().get()
                ? cloakLight.y
                : cloakLight.y + 1;

        // Ground Search Control Movements
        ModelPart groundSearch = this.bone.getChild("panels").getChild("p_4").getChild("bone98").getChild("bone99")
                .getChild("bone100").getChild("s_knob");
        float groundSearchTarget = tardis.travel().horizontalSearch().get() ? 1.0f : -1.5f;
        float groundSearchAngle = getAngle(console, "ground_search", groundSearchTarget, delta);
        groundSearch.z = groundSearchAngle - 0.5f;

        // Hail Mary Control Movements
        ModelPart hailMary = this.bone.getChild("panels").getChild("p_2").getChild("bone48").getChild("bone49")
                .getChild("bone50").getChild("s_lever").getChild("bone61");
        float hailMaryTarget = tardis.stats().hailMary().get() ? 3.0f : 1.5f;
        float hailMaryAngle = getAngle(console, "hail_mary", hailMaryTarget, delta);
        hailMary.zRot = hailMaryAngle - 0.5f;
        ModelPart hailMaryWarningLight = this.bone.getChild("panels").getChild("p_2").getChild("bone48")
                .getChild("bone49").getChild("bone50").getChild("sym_lamp").getChild("bone97");
        hailMaryWarningLight.y = tardis.stats().hailMary().get()
                ? hailMaryWarningLight.y
                : hailMaryWarningLight.y + 1;

        // Hads Alarm Control Movements
        ModelPart hadsAlarms = this.bone.getChild("panels").getChild("p_6").getChild("bone132").getChild("bone133")
                .getChild("bone134").getChild("s_lever_6").getChild("bone143");
        ModelPart hadsAlarmsLightsOne = this.bone.getChild("panels").getChild("p_6").getChild("bone132")
                .getChild("bone133").getChild("bone134").getChild("sym_lamp4").getChild("bone145");
        ModelPart hadsAlarmsLightsTwo = this.bone.getChild("panels").getChild("p_6").getChild("bone132")
                .getChild("bone133").getChild("bone134").getChild("sym_lamp5").getChild("bone141");
        float alarmTarget = tardis.alarm().isEnabled() ? 3.0f : 1.5f;
        float alarmAngle = getAngle(console, "alarm", alarmTarget, delta);
        hadsAlarms.zRot = alarmAngle - 0.5f;
        if (!tardis.alarm().isEnabled()) {
            hadsAlarmsLightsOne.y = hadsAlarmsLightsOne.y + 1;
            hadsAlarmsLightsTwo.y = hadsAlarmsLightsTwo.y + 1;
        }

        ModelPart security = this.bone.getChild("panels").getChild("p_6").getChild("bone132").getChild("bone133")
                .getChild("bone134").getChild("s_lever_7").getChild("bone144");
        float securityTarget = (tardis.stats().security().get()) ? 3.0f : 1.5f;
        float securityAngle = getAngle(console, "security", securityTarget, delta);
        security.zRot = securityAngle - 0.5f;

        ModelPart increment = this.bone.getChild("panels").getChild("p_3").getChild("bone67")
                .getChild("bone68").getChild("bone69").getChild("s_crank_3").getChild("bone74");

        int incrementVal = IncrementManager.increment(tardis);
        float incrementTarget = 0f;

        if (incrementVal >= 10000) {
            incrementTarget = 2.0f;
        } else if (incrementVal >= 1000) {
            incrementTarget = 1.5f;
        } else if (incrementVal >= 100) {
            incrementTarget = 1.0f;
        } else if (incrementVal >= 10) {
            incrementTarget = 0.5f;
        }

        float incrementAngle = getAngle(console, "increment", incrementTarget, delta);
        increment.yRot = incrementAngle - 0.5f;

        // Direction Control Movements
        ModelPart direction = this.bone.getChild("panels").getChild("p_2").getChild("bone48").getChild("bone49")
                .getChild("bone50").getChild("s_crank_1").getChild("bone59");
        float directionTargetDegrees = (0.3927f * tardis.travel().destination().getRotation()) * (180f / (float) Math.PI);
        direction.yRot = getLerpedDegrees(console, "direction", directionTargetDegrees, delta);

        // Anti Grav Control Movements
        ModelPart antiGrav = this.bone.getChild("panels").getChild("p_1").getChild("bone38").getChild("bone36")
                .getChild("bone37").getChild("sl_switch_1").getChild("bone33");
        float antiGravTarget = !tardis.travel().antigravs().get() ? 15.4f : 16.4f;
        float antiGravAngle = getAngle(console, "antigravs", antiGravTarget, delta);
        antiGrav.x = antiGravAngle - 0.5f;

        ModelPart shield = this.bone.getChild("panels").getChild("p_2").getChild("bone48").getChild("bone49")
                .getChild("bone50").getChild("sl_switch_6").getChild("bone57");
        float shieldTarget = tardis.shields().shielded().get()
                ? tardis.shields().visuallyShielded().get()
                        ? 13.9f
                        : 14.4f
                : 13.4f;
        float shieldAngle = getAngle(console, "shields", shieldTarget, delta);
        shield.x = shieldAngle - 0.5f;

        ModelPart siegeProtocol = this.bone.getChild("panels").getChild("p_2").getChild("bone48").getChild("bone49")
                .getChild("bone50").getChild("sl_switch_5").getChild("bone56");
        float siegeTarget = !tardis.siege().isActive() ? 9.9f : 10.9f;
        float siegeAngle = getAngle(console, "siege", siegeTarget, delta);
        siegeProtocol.x = siegeAngle - 0.5f;

        // Auto Pilot Control Movements
        ModelPart autoPilot = this.bone.getChild("panels").getChild("p_1").getChild("bone38").getChild("bone36")
                .getChild("bone37").getChild("st_switch").getChild("bone26");
        float autopilotTarget = !tardis.travel().autopilot() ? 1 : 0f;
        float autopilotAngle = getAngle(console, "autopilot", autopilotTarget, delta);
        autoPilot.yRot = autopilotAngle - 0.5f;

        super.renderWithAnimations(console, tardis, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return bone;
    }
}
