package dev.amble.ait.client.models.items;

import org.jetbrains.annotations.Nullable;

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
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;

public class HandlesModel extends Model {
    public static final ResourceLocation TEXTURE = AITMod.id("textures/blockentities/items/handles.png");
    public static final ResourceLocation EMISSION = AITMod.id("textures/blockentities/items/handles_emission.png");
    public static final ResourceLocation MOUTH = AITMod.id("textures/blockentities/items/handles_mouth.png");
    public final ModelPart handles;
    public HandlesModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.handles = root.getChild("handles");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition handles = modelPartData.addOrReplaceChild("handles", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition stalk = handles.addOrReplaceChild("stalk", CubeListBuilder.create().texOffs(32, 10).addBox(-1.0F, -8.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = stalk.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -0.5F, -1.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.5355F, -8.0F, -1.1213F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r2 = stalk.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(-1.8F, -4.7F, 1.2F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.7041F, -4.0F, 2.6749F, 0.0F, -1.7453F, 0.0F));

        PartDefinition cube_r3 = stalk.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 16).addBox(-1.8F, -4.7F, 1.2F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.281F, -3.8F, 2.7433F, 0.0F, -2.7925F, 0.0F));

        PartDefinition cube_r4 = stalk.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 16).addBox(-1.7F, -4.0F, 0.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.8478F, -4.0F, 0.2346F, 0.0F, 0.3927F, 0.0F));

        PartDefinition cube_r5 = stalk.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 16).addBox(6.4F, 0.0F, -0.1F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.3722F, -8.0F, 4.926F, 0.0F, 1.1781F, 0.0F));

        PartDefinition cube_r6 = stalk.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(6.0F, 0.0F, -1.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-2.5362F, -8.0F, 3.949F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r7 = stalk.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(6.0F, 0.0F, -1.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.9497F, -8.0F, -2.5355F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r8 = stalk.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -1.0F, 4.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4142F, -8.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition head = stalk.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(0, 16).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.01F))
                .texOffs(0, 32).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.11F))
                .texOffs(32, 0).addBox(-6.0F, -10.0F, 0.0F, 12.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(24, 16).addBox(-1.0F, -10.0F, -2.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(32, 23).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, -8.0F, 0.0F));

        PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -0.3F, 0.0F, 2.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, -7.5F, -4.6F, 0.0F, 0.0F, 0.829F));
        return LayerDefinition.create(modelData, 64, 64);
    }

    public void setAngles(PoseStack matrices, ItemDisplayContext renderMode, boolean left) {
        matrices.translate(0.5, -1.25f, -0.5);

        if (renderMode == ItemDisplayContext.FIXED) {
            matrices.translate(0, -0.2, 0);
            matrices.mulPose(Axis.YP.rotationDegrees(180F));
            return;
        }

        matrices.scale(0.6f, 0.6f, 0.6f);

        if (renderMode == ItemDisplayContext.GUI) {
            matrices.mulPose(Axis.XP.rotationDegrees(22.5f));
            matrices.mulPose(Axis.YP.rotationDegrees(45f));
            matrices.translate(0, 0.3f, 0);
            matrices.scale(1.2f, 1.2f, 1.2f);
        }

        if (renderMode == ItemDisplayContext.HEAD) {
            matrices.translate(0, -0.725f, 0);
            matrices.scale(2.725f, 2.725f, 2.725f);
            matrices.mulPose(Axis.YP.rotationDegrees(180));
        }
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        handles.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    public void render(@Nullable ClientLevel world, @Nullable LivingEntity entity, ItemStack stack, PoseStack matrices, MultiBufferSource provider, int light, int overlay, int seed) {
        this.renderToBuffer(matrices, provider.getBuffer(RenderType.entityCutout(TEXTURE)), light, overlay, 1, 1, 1, 1);
        this.renderToBuffer(matrices, provider.getBuffer(RenderType.entityCutoutNoCullZOffset(EMISSION)), 0xf000f0, overlay, 1, 1, 1, 1);
        if (entity instanceof Player player && player.isShiftKeyDown())
            this.renderToBuffer(matrices, provider.getBuffer(RenderType.entityCutoutNoCullZOffset(MOUTH)), 0xf000f0, overlay, 1, 1, 1, 1);
    }
}