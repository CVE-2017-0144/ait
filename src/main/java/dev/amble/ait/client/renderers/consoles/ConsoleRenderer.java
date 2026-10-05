package dev.amble.ait.client.renderers.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.consoles.BedrockConsoleModel;
import dev.amble.ait.client.models.consoles.ConsoleModel;
import dev.amble.ait.client.models.consoles.HartnellConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.client.models.items.HandlesModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.renderers.EmissiveGeometry;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientRenderPass;
import dev.amble.ait.client.util.OffScreenCull;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.item.HandlesItem;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.crystalline.client.ClientCrystallineVariant;
import dev.amble.ait.registry.impl.console.variant.ClientConsoleVariantRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
public class ConsoleRenderer<T extends ConsoleBlockEntity> implements BlockEntityRenderer<T> {

    private ClientConsoleVariantSchema variant;
    private ConsoleModel model;
    private HartnellConsoleModel unlinked;
    private HandlesModel handles;

    public ConsoleRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
                       int light, int overlay) {

        if (entity.getLevel() == null) return;

        ProfilerFiller profiler = entity.getLevel().getProfiler();
        profiler.incrementCounter("ait_console_dispatched");

        // Ahead of the duplicate guard so the two counters partition the dispatches.
        //
        // Origin is the block centre a block and a half up: the renderer flips 180 degrees about X and
        // the models then translate by (0.5, -1.5, -0.5), which the flip turns into this. Slop covers
        // what is drawn outside the model root, the largest being monitor text at an anchor up to 1.86
        // blocks out reaching a further 1.5.
        if (OffScreenCull.boxBehindCamera(entity, this.rootFor(entity), 0.5, 1.5, 0.5, 3.0)) {
            profiler.incrementCounter("ait_console_offscreen_skipped");
            return;
        }

        // Called twice a pass: once from the chunk's block entity list, once from the global no-cull
        // list. Both draws are identical, so only the first does the work. When the section is culled
        // the first call never arrives and the global one draws instead, which is the point of being
        // on that list at all.
        if (!ClientRenderPass.shouldDraw(entity)) {
            profiler.incrementCounter("ait_console_duplicate_skipped");
            return;
        }

        if (!entity.isLinked()) {
            profiler.incrementCounter("ait_console_unlinked");
            profiler.incrementCounter("ait_model_build");
            matrices.pushPose();
            matrices.translate(0.5, 1.5, 0.5);
            matrices.mulPose(Axis.XP.rotationDegrees(180f));
            if (this.unlinked == null)
                this.unlinked = new HartnellConsoleModel(HartnellConsoleModel.getTexturedModelData().bakeRoot());

            HartnellConsoleModel model = this.unlinked;
            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutout(ClientConsoleVariantRegistry.HARTNELL.texture())), light, overlay, 0xFFFFFFFF);
            RenderType layer = AITRenderLayers.tardisEmissiveCullZOffset(ClientConsoleVariantRegistry.HARTNELL.emission());
            model.renderToBuffer(matrices, vertexConsumers.getBuffer(layer), 0xf000f0, overlay, 0xFFFFFFFF);
            matrices.popPose();
            return;
        }

        ClientTardis tardis = entity.tardis().get().asClient();

        profiler.incrementCounter("ait_console_drawn");

        profiler.push("console");
        this.renderConsole(profiler, tardis, entity, matrices, vertexConsumers, light, overlay, tickDelta);

        if (variant instanceof ClientCrystallineVariant)
            this.renderPanes(tardis, entity, matrices, vertexConsumers, light, overlay);
        profiler.pop();
    }

    private void renderPanes(ClientTardis tardis, T entity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (!tardis.fuel().hasPower()) return;
        matrices.pushPose();
        matrices.translate(1, 2 + entity.getLevel().random.nextFloat() * 0.02, 0.5);
        matrices.mulPose(Axis.XN.rotationDegrees(180f));
        matrices.mulPose(Axis.YP.rotationDegrees(30f));
        matrices.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) % 180));
        matrices.translate(0.58, 0.1, -0.25);
        matrices.scale(0.9f, 0.9f, 0.9f);

        Minecraft.getInstance().getItemRenderer().
                renderStatic(new ItemStack(Items.ORANGE_STAINED_GLASS_PANE),
                        ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getLevel(), 0);
        matrices.translate(0 + entity.getLevel().random.nextFloat() * 0.02, 0 + entity.getLevel().random.nextFloat() * 0.02, 0 + entity.getLevel().random.nextFloat() * 0.02);
        Minecraft.getInstance().getItemRenderer().
                renderStatic(new ItemStack(Items.ORANGE_STAINED_GLASS_PANE),
                        ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getLevel(), 0);
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(-1, 2 + entity.getLevel().random.nextFloat() * 0.02, -0.5);
        matrices.mulPose(Axis.XN.rotationDegrees(180f));
        matrices.mulPose(Axis.YP.rotationDegrees(30f));
        matrices.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) % 180));
        matrices.translate(0.78, 0.15, -0.11);
        matrices.scale(0.9f, 0.9f, 0.9f);

        Minecraft.getInstance().getItemRenderer().
                renderStatic(new ItemStack(Items.ORANGE_STAINED_GLASS_PANE),
                        ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getLevel(), 0);
        matrices.translate(0 - entity.getLevel().random.nextFloat() * 0.02, 0 + entity.getLevel().random.nextFloat() * 0.02, 0 - entity.getLevel().random.nextFloat() * 0.02);
        Minecraft.getInstance().getItemRenderer().
                renderStatic(new ItemStack(Items.ORANGE_STAINED_GLASS_PANE),
                        ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getLevel(), 0);
        matrices.popPose();
    }

    private void renderConsole(ProfilerFiller profiler, ClientTardis tardis, T entity, PoseStack matrices,
                               MultiBufferSource vertexConsumers, int light, int overlay, float tickDelta) {
        profiler.push("model");

        this.updateModel(entity);

        boolean hasPower = tardis.fuel().hasPower();

        matrices.pushPose();
        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        profiler.popPush("animate");
        model.animateBlockEntity(entity, tardis.travel().getState(), hasPower);

        profiler.popPush("base_buffer");
        profiler.incrementCounter("ait_console_layer_switch");

        // Cutout for every variant, copper included. Copper used to ask for the translucent layer,
        // which pays a CPU quad sort on every flush, on the largest model in the mod: it cost 0.575 ms
        // a frame in the zone that flushes this layer. Its base textures carry no partial alpha at all,
        // so alpha testing and alpha blending produce the same pixels and the sort bought nothing.
        VertexConsumer baseBuffer = vertexConsumers.getBuffer(RenderType.entityCutout(variant.texture()));

        profiler.popPush("render");
        model.renderWithAnimations(tardis, entity, model.root(),
                matrices, baseBuffer, light, overlay,
                1, 1, 1, 1, tickDelta);

        this.renderEmissions(profiler, matrices, vertexConsumers, tardis, entity, hasPower, light, overlay, tickDelta);

        matrices.popPose();
        matrices.pushPose();

        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        matrices.popPose();

        profiler.popPush("monitor");

        if (hasPower && AITModClient.CONFIG.showConsoleMonitorText && model instanceof SimpleConsoleModel simplez) {
            simplez.renderMonitorText(tardis, entity, matrices, vertexConsumers, light, overlay);
        }

        profiler.popPush("sonic_port"); // } emission / sonic {

        ItemStack stack = entity.getSonicScrewdriver() == null || entity.getSonicScrewdriver().isEmpty() ? tardis.butler().getHandles() : entity.getSonicScrewdriver();

        if (stack == null) {
            profiler.pop(); // } sonic
            return;
        }

        if (stack.getItem() instanceof HandlesItem) {
            matrices.pushPose();
            matrices.translate(variant.handlesTranslations().x(), variant.handlesTranslations().y(),
                    variant.handlesTranslations().z());
            matrices.mulPose(Axis.YN.rotationDegrees(variant.handlesRotations()[0]));
            matrices.mulPose(Axis.XP.rotationDegrees(variant.handlesRotations()[1]));
            matrices.scale(0.6f, 0.6f, 0.6f);
            if (this.handles == null)
                this.handles = new HandlesModel(HandlesModel.getTexturedModelData().bakeRoot());

            HandlesModel handlesModel = this.handles;
            //handlesModel.setAngles(matrices, ModelTransformationMode.GROUND, false);
            handlesModel.handles.getChild("stalk").xRot = 45f;
            handlesModel.handles.getChild("stalk").getChild("head").xRot = -0.25f;
            handlesModel.render(null, Minecraft.getInstance().player, stack, matrices, vertexConsumers, light, overlay, 0);
            matrices.popPose();
        } else {
            matrices.pushPose();
            matrices.translate(variant.sonicItemTranslations().x(), variant.sonicItemTranslations().y(),
                    variant.sonicItemTranslations().z());
            matrices.mulPose(Axis.YN.rotationDegrees(variant.sonicItemRotations()[0]));
            matrices.mulPose(Axis.XP.rotationDegrees(variant.sonicItemRotations()[1]));
            matrices.scale(0.9f, 0.9f, 0.9f);
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, light,
                    overlay, matrices, vertexConsumers, entity.getLevel(), 0);
            matrices.popPose();
        }

        profiler.pop(); // } sonic
    }

    private void renderEmissions(ProfilerFiller profiler, PoseStack matrices, MultiBufferSource vertexConsumers, ClientTardis tardis, T entity, boolean hasPower, int light, int overlay, float tickDelta) {
        if (!hasPower) return;

        profiler.popPush("emission");

        matrices.pushPose();
        if (variant.emission() != null && !variant.emission().equals(DatapackConsole.EMPTY)) {
            profiler.popPush("emission_buffer");
            profiler.incrementCounter("ait_console_layer_switch");
            VertexConsumer emissive = vertexConsumers
                    .getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission()));

            profiler.popPush("emission_geometry");

            // Geometry only. The control state was applied before the base layer was drawn and does not
            // depend on the layer, so re-running it here would only pose the model differently under
            // the glow than under the geometry it sits on.
            //
            // The model instance is shared by every console of this variant, so the hidden parts have
            // to be put back before anything else draws it.
            EmissiveGeometry.Scope unlit = EmissiveGeometry.hideUnlit(model.root(), variant.emission());

            try {
                model.renderGeometryOnly(tardis, entity, model.root(),
                        matrices, emissive, 0xf000f0, overlay,
                        1, 1, 1, 1, tickDelta);
            } finally {
                unlit.restore();
            }
        }
        matrices.popPose();
    }

    /**
     * The model root to bound, or null when there is nothing to bound yet.
     *
     * <p>Read straight from the variant rather than from {@link #updateModel}, because the cull runs
     * before the draw does and must not be the thing that decides which model is cached.
     */
    private ModelPart rootFor(T entity) {
        if (!entity.isLinked() || entity.getVariant() == null)
            return null;

        ClientConsoleVariantSchema schema = entity.getVariant().getClient();

        if (schema == null)
            return null;

        ConsoleModel cached = schema.getCachedModel();

        // A datapack console applies its own offset and scale inside renderWithAnimations, from
        // fields with no bound on them, so there is nothing here that could bound it. Never culled.
        if (cached instanceof BedrockConsoleModel)
            return null;

        return cached == null ? null : cached.root();
    }

    private void updateModel(T entity) {
        ClientConsoleVariantSchema variant = entity.getVariant().getClient();

        if (this.variant != variant) {
            this.variant = variant;
            this.model = variant.getCachedModel();
        }
    }

    @Override
    public boolean shouldRenderOffScreen(ConsoleBlockEntity consoleBlockEntity) {
        return true;
    }

    @Override
    public int getViewDistance() {
        return 256;
    }

    @Override
    public boolean shouldRender(ConsoleBlockEntity consoleBlockEntity, Vec3 vec3d) {
        return Vec3.atCenterOf(consoleBlockEntity.getBlockPos()).multiply(1.0, 0.0, 1.0).closerThan(vec3d.multiply(1.0, 0.0, 1.0), this.getViewDistance());
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return AABB.INFINITE;
    }
}
