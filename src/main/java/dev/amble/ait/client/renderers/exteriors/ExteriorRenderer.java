package dev.amble.ait.client.renderers.exteriors;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.boti.BOTI;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.models.exteriors.SiegeModeModel;
import dev.amble.ait.client.models.machines.ShieldsModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientRenderPass;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.client.util.OffScreenCull;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.compat.iris.IrisCompat;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.core.tardis.handler.SiegeHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import org.joml.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.*;
import net.minecraft.world.phys.Vec3;

public class ExteriorRenderer<T extends ExteriorBlockEntity> implements BlockEntityRenderer<T> {

    private static final ResourceLocation SHIELDS = AITMod.id("textures/environment/shields.png");

    private static final SiegeModeModel SIEGE_MODEL = new SiegeModeModel(
            SiegeModeModel.getTexturedModelData().bakeRoot());
    private static final ShieldsModel SHIELDS_MODEL = new ShieldsModel(
            ShieldsModel.getTexturedModelData().bakeRoot());

    private ClientExteriorVariantSchema variant;
    private ExteriorModel model;

    public ExteriorRenderer(BlockEntityRendererProvider.Context ctx) {}

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
            int light, int overlay) {
        if (entity.getLevel() == null) return;

        // One counter per exit, so "the renderer never ran" and "it ran and drew nothing" stop looking
        // identical. Without these a zero in the BOTI queue counter has several possible causes.
        //
        // Fetched per call, never held: the client swaps its profiler object out every frame.
        ProfilerFiller profiler = entity.getLevel().getProfiler();
        profiler.incrementCounter("ait_exterior_dispatched");

        // Called twice a pass: once from the chunk's block entity list, once from the global no-cull
        // list. Both draws are identical, so only the first does the work. When the section is culled
        // the first call never arrives and the global one draws instead, which is the point of being
        // on that list at all.
        //
        // Ahead of the zone, not inside it. Opening a zone and returning without closing it leaks a
        // push per skipped duplicate and mis-nests everything measured after it.
        if (!ClientRenderPass.shouldDraw(entity)) {
            profiler.incrementCounter("ait_exterior_duplicate_skipped");
            return;
        }

        profiler.push("exterior");

        profiler.push("find_tardis");

        if (!entity.isLinked()) {
            profiler.incrementCounter("ait_exterior_unlinked");
            profiler.pop();
            profiler.pop();
            return;
        }

        ClientTardis tardis = entity.tardis().get().asClient();

        profiler.popPush("render");

        this.render0(entity, tardis, profiler, tickDelta, matrices, vertexConsumers, light, overlay);

        profiler.pop();
        profiler.pop();
    }

    private void render0(T entity, ClientTardis tardis, ProfilerFiller profiler, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
                         int light, int overlay) {
        this.updateModel(tardis);

        // Behind the duplicate guard rather than in front of it, unlike the console, because the
        // bound needs the tardis: rematerialisation keyframes translate an exterior by up to six
        // blocks and the stats scale multiplies it, so the offset and the scale are read per frame
        // instead of padded for. Padding for the worst case would make the bound so large that
        // nothing close behind the camera would ever be rejected.
        //
        // A sphere, not a box: the renderer applies an arbitrary yaw, and for a doom exterior that
        // yaw follows the player's head, so no axis aligned bound survives. The slop covers the
        // antigrav bob, which is a unit sine applied after the rotation.
        double scale = maxScale(tardis.travel().getScale());

        // The origin is where the renderer actually puts the model: the animation offset, then half a
        // block in X and Z and none in Y.
        //
        // Slop covers what is drawn after the rotation, and it scales because those displacements do.
        // The antigrav bob is a unit sine multiplied by the scale; the grumm and dinnerbone transform
        // displaces about one and a half blocks before the scale is applied; and the shields bubble is
        // a four block cube drawn outside the model entirely, so it has to be paid for whenever it is
        // up or a player stood beside a small variant would watch the swirl pop.
        double slop = 2.0 * scale + (tardis.areVisualShieldsActive() ? 4.5 : 0.0);

        Vector3f animation = tardis.travel().getAnimationPosition(tickDelta);

        if (this.model != null && OffScreenCull.sphereBehindCamera(entity, this.model.root(),
                animation.x() + 0.5, animation.y(), animation.z() + 0.5, scale, slop)) {
            profiler.incrementCounter("ait_exterior_offscreen_skipped");
            return;
        }

        if (tardis.travel().getAlpha() > 0) {
            profiler.incrementCounter("ait_exterior_drawn");
            this.renderExterior(profiler, tardis, entity, tickDelta, matrices, vertexConsumers, light, overlay);
        } else {
            profiler.incrementCounter("ait_exterior_alpha_zero");
        }

        if (tardis.door().getLeftRot() <= 0 && !variant.hasTransparentDoors()) {
            profiler.incrementCounter("ait_exterior_doors_shut");
            return;
        }

        if (!tardis.travel().isLanded() || tardis.siege().isActive()) {
            profiler.incrementCounter("ait_exterior_not_landed");
            return;
        }

        profiler.incrementCounter("ait_exterior_enqueued");
        if (!IrisCompat.isRenderingShadowPass() && (variant.parent().hasPortals() || !AITModClient.skipBuiltInBOTI())) BOTI.EXTERIOR_RENDER_QUEUE.add(entity);
    }

    /** The largest axis of a non uniform scale, since the bound is a sphere. */
    private static double maxScale(Vector3f scale) {
        return Math.max(1.0, Math.max(scale.x(), Math.max(scale.y(), scale.z())));
    }

    private boolean awesomeIPEmissionHack(Tardis tardis) {
        return DependencyChecker.hasPortals() && ClientTardisUtil.getCurrentTardis() == tardis;
    }

    private void renderExterior(ProfilerFiller profiler, ClientTardis tardis, T entity, float tickDelta, PoseStack matrices,
                                MultiBufferSource vertexConsumers, int light, int overlay) {
        final float alpha = tardis.travel().getAlpha(tickDelta);
        // tf does even all of this do?
        RenderSystem.enableCull();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();

        SiegeHandler siege = tardis.siege();

        if (siege.isActive()) {
            profiler.push("siege");

            matrices.pushPose();
            matrices.translate(0.5f, 0.5f, 0.5f);
            SIEGE_MODEL.renderWithAnimations(tardis, entity, SIEGE_MODEL.root(),
                    matrices,
                    vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(siege.texture().get())), light, overlay, 1, 1, 1, 1, tickDelta);

            matrices.popPose();
            profiler.pop();
            return;
        }

        TravelHandler travel = tardis.travel();

        CachedDirectedGlobalPos exteriorPos = travel.position();

        if (exteriorPos == null)
            return;

        BlockState blockState = entity.getBlockState();
        int k = blockState.getValue(ExteriorBlock.ROTATION);
        float h = RotationSegment.convertToDegrees(k);

        boolean isDoom = this.variant.equals(ClientExteriorVariantRegistry.DOOM);

        matrices.pushPose();

        // adjust based off animation position
        Vector3f animPositionOffset = travel.getAnimationPosition(tickDelta);
        matrices.translate(animPositionOffset.x(), animPositionOffset.y(), animPositionOffset.z());

        matrices.translate(0.5f, 0.0f, 0.5f);

        // adjust based off animation rotation
        Vector3f animRotationOffset = travel.getAnimationRotation(tickDelta);
        matrices.mulPose(Axis.XP.rotationDegrees(animRotationOffset.z()));
        matrices.mulPose(Axis.YP.rotationDegrees(animRotationOffset.y()));
        matrices.mulPose(Axis.ZP.rotationDegrees(animRotationOffset.x()));

        this.applyNameTransforms(tardis, matrices, tardis.stats().getName(), tickDelta);

        ResourceLocation texture = this.variant.texture();
        ResourceLocation emission = this.variant.emission();

        if (Minecraft.getInstance().player == null) {
            matrices.popPose();
            return;
        }

        int rotation = travel.position().getRotation();
        boolean isDiagonal = rotation > 0 && rotation < 4 || rotation > 4 && rotation < 8 || rotation > 8 && rotation < 12
                || rotation > 12 && rotation < 16;
        float wrappedDegrees = Mth.wrapDegrees(Minecraft.getInstance().player.getYHeadRot() + h + (isDiagonal ? 90f : 0));

        if (isDoom) {
            texture = DoomConstants.getTextureForRotation(wrappedDegrees, tardis);
            emission = DoomConstants.getEmissionForRotation(DoomConstants.getTextureForRotation(wrappedDegrees, tardis),
                    tardis);
        }

        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        matrices.mulPose(
                Axis.YP.rotationDegrees(!isDoom
                        ? h + 180f
                        : Minecraft.getInstance().player.getYHeadRot()
                        + ((wrappedDegrees > -135 && wrappedDegrees < 135) ? 180f : 0f)
                + (travel.position().getRotationDirection() == Direction.EAST ||
                        travel.position().getRotationDirection() == Direction.WEST ? 180f : 0f)));

        if (model == null) {
            matrices.popPose();
            return;
        }

        if (travel.antigravs().get() && tardis.flight().falling().get()) {
            float sinFunc = (float) Math.sin((Minecraft.getInstance().player.tickCount / 400f * 220f) * 0.2f + 0.2f);
            matrices.translate(0, sinFunc, 0);
        }

        if (!DependencyChecker.hasIris()) {
            model.renderWithAnimations(tardis, entity, this.model.root(),
                    matrices, vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(texture)), light, overlay, 1, 1,
                    1, alpha, tickDelta);
        }

        profiler.push("emission");

        boolean alarms = tardis.alarm().isEnabled();
        boolean power = tardis.fuel().hasPower();

        // the emission should only render IF
        //  1) the alpha of the exterior is higher than a certain threshold
        //  2) there is an emissive texture
        //  3) there's power OR alarms on
        if (alpha > 0.105f && emission != null && (power || alarms)
                && !emission.equals(DatapackConsole.EMPTY) && !awesomeIPEmissionHack(tardis)) {
            float u = 1;
            float t = 1;
            float s = 1;

            if ("partytardis".equals(tardis.stats().getName()) ||
                    !tardis.extra().getInsertedDisc().isEmpty()) {
                final float[] rgb = ClientTardisUtil.getPartyColors();

                u = rgb[0];
                t = rgb[1];
                s = rgb[2];
            } else if (tardis.sonic().getExteriorSonic() != null) {
                float time = Minecraft.getInstance().player.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
                float progress = (float)((Math.sin(time * 0.03) + 1) / 2.0f);

                final float FROM_R = 1.0f, FROM_G = 1.0f, FROM_B = 1.0f;
                final float TO_R = 0.3f, TO_G = 0.3f, TO_B = 1.0f;

                s = FROM_R * (1f - progress) + TO_R * progress;
                t = FROM_G * (1f - progress) + TO_G * progress;
                u = FROM_B * (1f - progress) + TO_B * progress;
            }

            float colorAlpha = 1 - alpha;

            float red = alarms
                    ? !power ? 0.25f : s - colorAlpha
                    : s - colorAlpha;

            float green = alarms
                    ? !power ? 0.01f : 0.3f
                    : t - colorAlpha;

            float blue = alarms
                    ? !power ? 0.01f : 0.3f
                    : u - colorAlpha;

            // TODO the guard above tests `emission`, which DOOM reassigns per rotation, but the layer
            // below binds `variant.emission()`, the un-adjusted base. For DOOM those disagree. Left
            // alone here because changing which texture DOOM binds is not part of this change.
           model.renderWithAnimations(tardis, entity, this.model.root(), matrices, vertexConsumers.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission())),
                   0xF000F0, OverlayTexture.NO_OVERLAY, red, green, blue, alpha, tickDelta);
        }
        if (DependencyChecker.hasIris()) {
            model.renderWithAnimations(tardis, entity, this.model.root(),
                    matrices, vertexConsumers.getBuffer(AITRenderLayers.entityTranslucentCull(texture)), light, overlay, 1, 1,
                    1, alpha, tickDelta);
        }

        profiler.popPush("biome");

        if (this.variant != ClientExteriorVariantRegistry.CORAL_GROWTH) {
            BiomeHandler handler = tardis.handler(TardisComponent.Id.BIOME);
            if (handler.getBiomeKey() != null) {
                ResourceLocation biomeTexture = handler.getBiomeKey().get(this.variant.overrides());

                if (alpha > 0.105f && (biomeTexture != null && !texture.equals(biomeTexture))) {
                    model.renderWithAnimations(tardis, entity, this.model.root(),
                            matrices,
                            vertexConsumers.getBuffer(AITRenderLayers.entityCutoutNoCullZOffset(biomeTexture)), light, overlay, 1, 1, 1, alpha, tickDelta);
                }

            }
        }

        profiler.pop();
        matrices.popPose();

        if (tardis.areVisualShieldsActive()) {
            profiler.push("shields");

            float delta = (tickDelta + Minecraft.getInstance().player.tickCount) * 0.03f;
            VertexConsumer vertexConsumer = vertexConsumers
                    .getBuffer(RenderType.energySwirl(SHIELDS, delta % 1.0F, (delta * 0.1F) % 1.0F));

            matrices.pushPose();
            matrices.translate(0.5F, 0.0F, 0.5F);

            SHIELDS_MODEL.renderToBuffer(matrices, vertexConsumer, LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(alpha, 0f, 0.25f, 0.5f));

            matrices.popPose();
            profiler.pop();
        }

        profiler.push("sonic");
        ItemStack stack = tardis.sonic().getExteriorSonic();

        if (stack == null || entity.getLevel() == null) {
            profiler.pop();
            return;
        }

        matrices.pushPose();
        matrices.rotateAround(Axis.YN.rotationDegrees(180f + h + this.variant.sonicItemRotations()[0]),
                (float) entity.getBlockPos().getCenter().x - entity.getBlockPos().getX(),
                (float) entity.getBlockPos().getCenter().y - entity.getBlockPos().getY(),
                (float) entity.getBlockPos().getCenter().z - entity.getBlockPos().getZ());
        matrices.translate(this.variant.sonicItemTranslations().x(), this.variant.sonicItemTranslations().y(),
                this.variant.sonicItemTranslations().z());
        matrices.mulPose(Axis.XP.rotationDegrees(this.variant.sonicItemRotations()[1]));
        matrices.scale(0.9f, 0.9f, 0.9f);

        int lightAbove = LevelRenderer.getLightColor(entity.getLevel(), entity.getBlockPos().above());
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, lightAbove,
                OverlayTexture.NO_OVERLAY, matrices, vertexConsumers, entity.getLevel(), 0);

        matrices.popPose();
        profiler.pop();
    }

    private void updateModel(Tardis tardis) {
        if (tardis.getExterior() == null)
            return;
        ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();

        if (this.variant != variant) {
            this.variant = variant;
            this.model = variant.getCachedModel();
        }
    }

    private void applyNameTransforms(Tardis tardis, PoseStack matrices, String name, float delta) {
        Vector3f scale = tardis.travel().getScale(delta);

        if (name.equalsIgnoreCase("grumm") || name.equalsIgnoreCase("dinnerbone")) {
            matrices.mulPose(Axis.XP.rotationDegrees(-90f));
            matrices.translate(0, scale.y + 0.25f, scale.z - 1.7f);
        }

        matrices.scale(scale.x, scale.y, scale.z);
    }

    @Override
    public boolean shouldRenderOffScreen(ExteriorBlockEntity exteriorBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(ExteriorBlockEntity exteriorBlockEntity, Vec3 vec3d) {
        return Vec3.atCenterOf(exteriorBlockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(vec3d.multiply(1.0, 0.0, 1.0), this.getViewDistance());
    }
}
