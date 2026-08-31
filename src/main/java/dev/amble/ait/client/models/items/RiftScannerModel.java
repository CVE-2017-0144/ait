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
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.util.AngleInterpolator;
import dev.amble.ait.core.item.RiftScannerItem;
import dev.amble.ait.core.world.TardisServerWorld;

public class RiftScannerModel extends Model {

    public static final ResourceLocation TEXTURE = AITMod.id("textures/blockentities/items/rift_scanner.png");
    public static final ResourceLocation EMISSION = AITMod.id("textures/blockentities/items/rift_scanner_emission.png");

    private static final float MULTIPLIER = (float) (2 * Math.PI);

    private final AngleInterpolator aimedInterpolator = new AngleInterpolator();
    private final AngleInterpolator aimlessInterpolator = new AngleInterpolator();

    private final ModelPart root;

    public RiftScannerModel(ModelPart root) {
        super(RenderType::entityCutout);
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addOrReplaceChild("root", CubeListBuilder.create()
                .texOffs(29, 1).addBox(-10.5F, -11.0F, 7.0F, 5.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(18, 10).addBox(-7.0F, -6.0F, 6.9F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(19, 10).addBox(-6.7F, -5.7F, 6.8F, 0.4F, 0.4F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(11, 9).addBox(-10.0F, -2.5F, 6.9F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(20, 6).addBox(-11.5F, -8.0F, 7.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 6).addBox(-11.5F, -10.1F, 7.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 12).addBox(-11.25F, -8.0F, 8.0F, 4.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(20, 7).addBox(-11.5F, -11.4F, 7.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-11.3F, -12.4F, 7.7F, 0.6F, 5.0F, 0.6F, new CubeDeformation(0.0F))
                .texOffs(0, 3).addBox(-11.1F, -17.4F, 8.0F, 0.2F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-9.5F, -14.5F, 6.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(7, 3).addBox(-9.5F, -14.5F, 9.5F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, -3).addBox(-9.5F, -14.5F, 6.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(-2, 3).addBox(-9.5F, -11.5F, 6.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(3, 0).addBox(-9.5F, -14.5F, 6.5F, 3.0F, 0.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(7, 0).addBox(-6.5F, -14.5F, 6.5F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(19, 0).addBox(-9.0F, -11.5F, 7.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.001F))
                .texOffs(53, 0).addBox(-8.5F, -6.75F, 5.0F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 0).addBox(-5.25F, -10.2F, 7.25F, 1.5F, 9.0F, 1.5F, new CubeDeformation(0.0F))
                .texOffs(0, 20).addBox(-5.5F, -9.7F, 7.75F, 0.3F, 8.0F, 0.5F, new CubeDeformation(0.0F))
                .texOffs(18, 9).addBox(-5.25F, -9.7F, 7.25F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.1F))
                .texOffs(18, 9).addBox(-5.25F, -2.7F, 7.25F, 1.5F, 1.0F, 1.5F, new CubeDeformation(0.1F))
                .texOffs(8, 7).addBox(-10.0F, -10.5F, 6.5F, 0.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 7).addBox(-6.5F, -10.5F, 6.5F, 0.5F, 4.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(7, 9).addBox(-9.5F, -10.5F, 6.5F, 3.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(8, 9).addBox(-9.5F, -7.0F, 6.5F, 3.0F, 0.5F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-9.9F, -10.4F, 6.85F, 3.8F, 3.8F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 24.0F, -8.0F));

        root.addOrReplaceChild("arrow", CubeListBuilder.create().texOffs(1, 6).addBox(-0.2F, -1.4F, 1.75F, 0.4F, 1.9F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -8.5F, 5.0F, 0.0F, 0.0F, 0F));

        root.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(18, 11).addBox(-1.45F, 6.9F, 0.0F, 2.9F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(18, 8).addBox(0.0F, 6.9F, -1.45F, 0.0F, 1.0F, 2.9F, new CubeDeformation(0.0F))
                .texOffs(18, 11).addBox(-1.55F, -0.1F, 0.0F, 3.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(18, 8).addBox(0.0F, -0.1F, -1.45F, 0.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, -9.5F, 8.0F, 0.0F, 0.7854F, 0.0F));

        root.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(10, 9).addBox(-0.1F, -0.5F, -1.4F, 0.2F, 1.0F, 0.3F, new CubeDeformation(0.0F))
                .texOffs(10, 11).addBox(-0.5F, -0.5F, -1.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-9.5F, -5.5F, 8.0F, 0.0F, 0.0F, 0.7854F));

        root.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(19, 10).addBox(-0.5F, -0.5F, -1.1F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.5F, -2.0F, 8.0F, 0.0F, 0.0F, 0.3927F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        root.render(matrices, vertexConsumer, light, overlay, color);
    }

    public void setAngles(PoseStack matrices, ItemDisplayContext renderMode, boolean left) {
        matrices.translate(0.5, -1.75, -0.5);

        if (renderMode == ItemDisplayContext.GUI)
            matrices.translate(0, 0.2, 0);
    }

    public void render(@Nullable ClientLevel world, @Nullable LivingEntity entity, ItemStack stack, PoseStack matrices, MultiBufferSource provider, int light, int overlay, int seed) {
        this.root.getChild("arrow").zRot = this.unclampedCall(stack, world, entity, seed) * MULTIPLIER;
        this.renderToBuffer(matrices, provider.getBuffer(RenderType.entityCutout(TEXTURE)), light, overlay, 0xFFFFFFFF);
        this.renderToBuffer(matrices, provider.getBuffer(RenderType.entityCutout(EMISSION)), 0xf000f0, overlay, 0xFFFFFFFF);
    }

    public float unclampedCall(ItemStack stack, @Nullable ClientLevel clientWorld, @Nullable LivingEntity livingEntity, int i) {
        Entity entity = livingEntity != null ? livingEntity : stack.getEntityRepresentation();

        if (entity == null)
            return 0;

        clientWorld = this.getClientWorld(entity, clientWorld);

        return clientWorld == null ? 0.0F : this.getAngle(RiftScannerItem.getTarget(stack)
                .getMiddleBlockPosition(75), clientWorld, i, entity);
    }

    private float getAngle(BlockPos target, ClientLevel world, int seed, Entity entity) {
        long l = world.getGameTime();
        return !this.canPointTo(entity, target, world)
                ? this.getAimlessAngle(seed, l)
                : this.getAngleTo(entity, l, target);
    }

    private float getAimlessAngle(int seed, long time) {
        if (this.aimlessInterpolator.shouldUpdate(time))
            this.aimlessInterpolator.update(time, Math.random());

        double d = this.aimlessInterpolator.value() + (double) ((float) this.scatter(seed) / 2.14748365E9F);
        return Mth.positiveModulo((float) d, 1.0F);
    }

    private int scatter(int seed) {
        return seed * 1327217883;
    }

    private boolean canPointTo(Entity entity, @Nullable BlockPos pos, @Nullable ClientLevel world) {
        return world != null && !TardisServerWorld.isTardisDimension(world) &&
                pos != null && !(pos.distToCenterSqr(entity.position()) < 9.999999747378752E-6);
    }

    private @Nullable ClientLevel getClientWorld(Entity entity, @Nullable ClientLevel world) {
        return world == null && entity.level() instanceof ClientLevel ? (ClientLevel) entity.level() : world;
    }

    private double getAngleTo(Entity entity, BlockPos pos) {
        Vec3 vec3d = Vec3.atCenterOf(pos);
        return Math.atan2(vec3d.z() - entity.getZ(), vec3d.x() - entity.getX()) / 6.2831854820251465;
    }

    private float getAngleTo(Entity entity, long time, BlockPos pos) {
        double d = this.getAngleTo(entity, pos);
        double e = this.getBodyYaw(entity);
        double f;
        if (entity instanceof Player playerEntity) {
            if (playerEntity.isLocalPlayer()) {
                if (this.aimedInterpolator.shouldUpdate(time)) {
                    this.aimedInterpolator.update(time, 0.5 - (e - 0.25));
                }

                f = d + this.aimedInterpolator.value();
                return Mth.positiveModulo((float) f, 1.0F);
            }
        }

        f = 0.5 - (e - 0.25 - d);
        return Mth.positiveModulo((float) f, 1.0F);
    }

    private double getBodyYaw(Entity entity) {
        return Mth.positiveModulo(entity.getVisualRotationYInDegrees() / 360.0F, 1.0);
    }
}
