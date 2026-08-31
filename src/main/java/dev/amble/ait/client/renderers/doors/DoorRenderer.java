package dev.amble.ait.client.renderers.doors;

import dev.amble.ait.client.AITModClient;
import org.joml.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.client.models.doors.CapsuleDoorModel;
import dev.amble.ait.client.models.doors.exclusive.DoomDoorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blocks.DoorBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class DoorRenderer<T extends DoorBlockEntity> implements BlockEntityRenderer<T> {

    private ClientExteriorVariantSchema variant;
    private AnimatedModel<DoorBlockEntity> model;

    public DoorRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
                       int light, int overlay) {
        if (entity.getLevel() == null) return;
        if (!entity.isLinked()) {
            BlockState blockState = entity.getBlockState();
            float k = blockState.getValue(DoorBlock.FACING).toYRot();
            matrices.pushPose();
            matrices.translate(0.5, 1.5, 0.5);
            matrices.scale(1, 1, 1);
            matrices.mulPose(Axis.YN.rotationDegrees(k + 180));
            matrices.mulPose(Axis.XP.rotationDegrees(180f));
            CapsuleDoorModel doorModel = new CapsuleDoorModel(CapsuleDoorModel.getTexturedModelData().bakeRoot());
            doorModel.renderToBuffer(matrices, vertexConsumers.getBuffer(AITRenderLayers.entityCutout(ClientExteriorVariantRegistry.CAPSULE_DEFAULT.texture())),
                    light, overlay, 1, 1, 1, 1);
            matrices.popPose();
            return;
        }

        ProfilerFiller profiler = entity.getLevel().getProfiler();
        profiler.push("door");

        ClientTardis tardis = entity.tardis().get().asClient();
        if (!tardis.siege().isActive())
            this.renderDoor(profiler, tardis, entity, matrices, vertexConsumers, light, overlay, tickDelta);

        profiler.pop();
    }

    private void renderDoor(ProfilerFiller profiler, ClientTardis tardis, T entity, PoseStack matrices,
                            MultiBufferSource vertexConsumers, int light, int overlay, float tickDelta) {
        this.updateModel(tardis);

        BlockState blockState = entity.getBlockState();
        float k = blockState.getValue(DoorBlock.FACING).toYRot();

        ResourceLocation texture = this.variant.texture();

        if (this.variant.equals(ClientExteriorVariantRegistry.DOOM))
            texture = tardis.door().isOpen() ? DoomDoorModel.DOOM_DOOR_OPEN : DoomDoorModel.DOOM_DOOR;

        matrices.pushPose();
        matrices.translate(0.5, 0, 0.5);
        Vector3f scale = tardis.stats().getScale();
        matrices.scale(scale.x(), scale.y(), scale.z());
        matrices.mulPose(Axis.YN.rotationDegrees(k));
        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        if (!DependencyChecker.hasIris()) {
            model.renderWithAnimations(tardis, entity, model.root(), matrices,
                    vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(texture)), light, overlay, 1, 1,
                    1, 1, tickDelta);
        }

        /*if (tardis.overgrown().overgrown().get())
            model.renderWithAnimations(entity, model.root(), matrices,
                    vertexConsumers.getBuffer(AITRenderLayers.getEntityTranslucentCull(
                            tardis.overgrown().getOvergrownTexture())),
                    light, overlay, 1, 1, 1, 1);*/

        profiler.push("emission");

        ResourceLocation emissive = this.variant.emission();

        if (!variant.equals(ClientExteriorVariantRegistry.DOOM) && emissive != null && !emissive.equals(DatapackConsole.EMPTY)) {
            boolean power = tardis.fuel().hasPower();
            boolean alarms = tardis.alarm().isEnabled();

            float u;
            float t;
            float s;

            if ((tardis.stats().getName() != null && "partytardis".equals(tardis.stats().getName().toLowerCase()) ||(!tardis.extra().getInsertedDisc().isEmpty()))) {
                int m = 25;
                int n = Minecraft.getInstance().player.tickCount / m + Minecraft.getInstance().player.getId();
                int o = DyeColor.values().length;
                int p = n % o;
                int q = (n + 1) % o;
                float r = ((float)(Minecraft.getInstance().player.tickCount % m)) / m;
                float[] fs = Sheep.getColorArray(DyeColor.byId(p));
                float[] gs = Sheep.getColorArray(DyeColor.byId(q));
                s = fs[0] * (1f - r) + gs[0] * r;
                t = fs[1] * (1f - r) + gs[1] * r;
                u = fs[2] * (1f - r) + gs[2] * r;
            } else {
                float[] hs = new float[]{ 1.0f, 1.0f, 1.0f };
                s = hs[0];
                t = hs[1];
                u = hs[2];
            }

            float colorAlpha = 1;

            float red = alarms ? !power ? 0.25f : s : s;
            float green = alarms ? !power ? 0.01f : 0.3f : t;
            float blue = alarms ? !power ? 0.01f : 0.3f : u;

            model.renderWithAnimations(tardis, entity, this.model.root(), matrices, vertexConsumers.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission(), true)),
                    0xf000f0, OverlayTexture.NO_OVERLAY, red, green, blue, colorAlpha, tickDelta);
        }

        if (DependencyChecker.hasIris()) {
            model.renderWithAnimations(tardis, entity, model.root(), matrices,
                    vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(texture)), light, overlay, 1, 1,
                    1, 1, tickDelta);
        }

        profiler.popPush("biome");

        if (this.variant != ClientExteriorVariantRegistry.CORAL_GROWTH) {
            BiomeHandler biome = tardis.handler(TardisComponent.Id.BIOME);
            ResourceLocation biomeTexture = biome.getBiomeKey().get(this.variant.overrides());

            if (biomeTexture != null && !texture.equals(biomeTexture)) {
                model.renderWithAnimations(tardis, entity, model.root(),
                        matrices, vertexConsumers.getBuffer(AITRenderLayers.entityCutoutNoCullZOffset(biomeTexture)),
                        light, overlay, 1, 1, 1, 1, tickDelta);
            }
        }

        if ((tardis.door().getLeftRot() > 0 || this.variant.hasTransparentDoors()) && !tardis.isGrowth() && !AITModClient.skipBuiltInBOTI())
            BOTI.DOOR_RENDER_QUEUE.add(entity);

        matrices.popPose();
        profiler.pop();
    }

    private void updateModel(Tardis tardis) {
        if (tardis.getExterior().getVariant() == null) return;
        ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();

        if (this.variant != variant) {
            this.variant = variant;
            this.model = variant.getDoor().model();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(DoorBlockEntity doorBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(DoorBlockEntity doorBlockEntity, Vec3 vec3d) {
        return Vec3.atCenterOf(doorBlockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(vec3d.multiply(1.0, 0.0, 1.0), this.getViewDistance());
    }
}
