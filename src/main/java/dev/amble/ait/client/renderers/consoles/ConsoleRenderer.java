package dev.amble.ait.client.renderers.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.consoles.ConsoleModel;
import dev.amble.ait.client.models.consoles.HartnellConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.client.models.items.HandlesModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.item.HandlesItem;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.crystalline.client.ClientCrystallineVariant;
import dev.amble.ait.registry.impl.console.variant.ClientConsoleVariantRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public class ConsoleRenderer<T extends ConsoleBlockEntity> implements BlockEntityRenderer<T> {

    private ClientConsoleVariantSchema variant;
    private ConsoleModel model;

    public ConsoleRenderer(BlockEntityRendererProvider.Context ctx) {
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
                       int light, int overlay) {

        if (entity.getLevel() == null) return;

        if (!entity.isLinked()) {
            matrices.pushPose();
            matrices.translate(0.5, 1.5, 0.5);
            matrices.mulPose(Axis.XP.rotationDegrees(180f));
            HartnellConsoleModel model = new HartnellConsoleModel(HartnellConsoleModel.getTexturedModelData().bakeRoot());
            model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityCutout(ClientConsoleVariantRegistry.HARTNELL.texture())),
                    light, overlay, 1, 1, 1, 1);
            RenderType layer = AITRenderLayers.tardisEmissiveCullZOffset(ClientConsoleVariantRegistry.HARTNELL.emission(), true);
            model.renderToBuffer(matrices, vertexConsumers.getBuffer(layer),
                    0xf000f0, overlay, 1, 1, 1, 1);
            matrices.popPose();
            return;
        }

        ClientTardis tardis = entity.tardis().get().asClient();
        ProfilerFiller profiler = entity.getLevel().getProfiler();

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
        matrices.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getFrameTime() % 180));
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
        matrices.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getFrameTime() % 180));
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

        profiler.popPush("render");
        model.renderWithAnimations(tardis, entity, model.root(),
                matrices, vertexConsumers.getBuffer(variant.equals(ClientConsoleVariantRegistry.COPPER) ? RenderType.entityTranslucent(variant.texture()) :
                        RenderType.entityCutout(variant.texture())), light, overlay,
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
            HandlesModel handlesModel = new HandlesModel(HandlesModel.getTexturedModelData().bakeRoot());
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
            model.renderWithAnimations(tardis, entity, model.root(),
                    matrices, vertexConsumers.getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(variant.emission(), true)), 0xf000f0, overlay,
                    1, 1, 1, 1, tickDelta);
        }
        matrices.popPose();
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
}
