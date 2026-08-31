package dev.amble.ait.client.renderers.entities;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.models.consoles.ControlModel;
import dev.amble.ait.client.renderers.SonicRendering;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.entities.ConsoleControlEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;

@Environment(value = EnvType.CLIENT)
public class ControlEntityRenderer extends EntityRenderer<ConsoleControlEntity> {

    private static final ResourceLocation TEXTURE = AITMod.id("textures/entity/control/sequenced.png");

    ControlModel model = new ControlModel(ControlModel.getTexturedModelData().bakeRoot());

    public ControlEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ConsoleControlEntity entity, float yaw, float tickDelta, PoseStack matrixStack,
            MultiBufferSource vertexConsumerProvider, int light) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);

        if (SonicRendering.isPlayerHoldingScanningSonic() && AITModClient.CONFIG.showControlHitboxes) {
            renderOutline(entity, matrixStack, vertexConsumerProvider);
        }
    }

    @Override
    protected void renderNameTag(ConsoleControlEntity entity, Component text, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light) {
        double d = this.entityRenderDispatcher.distanceToSqr(entity);

        if (d > 4096.0)
            return;

        Component name = entity.getCustomName();

        if (name == null)
            return;

        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();

        if (tardis == null)
            return;

        Control control = entity.getControl();
        Component label = control != null ? control.getName(tardis) : name;

        Font textRenderer = this.getFont();
        float h = (float) -textRenderer.width(label) / 2;
        float f = entity.getNameTagOffsetY() - 0.3f;

        matrices.pushPose();
        matrices.translate(0.0f, f, 0.0f);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.scale(-0.0075f, -0.0075f, 0.0075f);

        Matrix4f matrix4f = matrices.last().pose();
        HitResult hitresult = Minecraft.getInstance().hitResult;

        if (hitresult != null) {
            boolean isPlayerLookingWithSonic = isPlayerLookingAtControlWithSonic(hitresult, entity);
            FormattedCharSequence nameOrdered = label.getVisualOrderText();

            if (isPlayerLookingWithSonic) {
                textRenderer.drawInBatch8xOutline(nameOrdered, h, 0f, 0xF0F0F0, 0x000000,
                        matrix4f, vertexConsumers, 0xFF);
            }
        }

        matrices.popPose();

        if (hitresult == null)
            return;

        boolean sonicInConsole = isScanningSonicInConsole(entity);
        boolean handlesInConsole = isHandlesInConsole(entity);

        if (!entity.isPartOfSequence() || (!sonicInConsole && !handlesInConsole)) return;

        matrices.pushPose();
        matrices.scale(0.4f, 0.4f, 0.4f);
        matrices.mulPose(Axis.XN.rotationDegrees(180f));
        matrices.translate(0, (-2 - entity.getControlHeight() / 2) + entity.level().random.nextFloat() * 0.02, 0);
        matrices.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) % 180));

        float alpha = entity.level().random.nextInt(32) != 6 ? 0.4f : 0.05f;
        float red = entity.wasSequenced() ? 0.0f : 1.0f;
        float green = (entity.wasSequenced()) ? 1.0f : 1 - (entity.getSequencePercentage());

        this.model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(TEXTURE)), light, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(alpha, red, green, 0));

        this.model.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.eyes(TEXTURE)), 0xFF00F0, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(alpha, red, green, 0));

        matrices.popPose();
    }

    private static void renderOutline(Entity entity, PoseStack matrices,
            MultiBufferSource vertexConsumers) {
        VertexConsumer vertices = vertexConsumers.getBuffer(RenderType.LINES);

        AABB box = entity.getBoundingBox().move(-entity.getX(), -entity.getY(), -entity.getZ());
        LevelRenderer.renderLineBox(matrices, vertices, box, 0.0f, 0.8f, 1.0f, 1.0f);
    }

    private static boolean isPlayerLookingAtControlWithSonic(HitResult hitResult, ConsoleControlEntity entity) {
        Player player = Minecraft.getInstance().player;

        if (player == null || !(hitResult instanceof EntityHitResult entityHit))
            return false;

        Entity hitEntity = entityHit.getEntity();

        if (hitEntity == null)
            return false;

        ItemStack sonic = SonicRendering.getSonicStack(player);

        if (sonic == null)
            return false;

        return hitEntity.equals(entity) && SonicRendering.isScanningSonic(sonic);
    }

    private static boolean isScanningSonicInConsole(ConsoleControlEntity entity) {
        if (entity.getConsole() == null) return false;

        ConsoleBlockEntity console = entity.getConsole();

        if (console.getSonicScrewdriver() == null || console.getSonicScrewdriver().isEmpty()) return false;

        ItemStack sonic = console.getSonicScrewdriver();

        if (sonic == null) {
            return false;
        }

        return SonicRendering.isScanningSonic(sonic);
    }

    private static boolean isHandlesInConsole(ConsoleControlEntity entity) {
        ConsoleBlockEntity console = entity.getConsole();
        if (console  == null) return false;

        if (!console.isLinked()) return false;

        Tardis tardis = console.tardis().get();
        return (tardis != null && tardis.butler().getHandles() != null);
    }

    @Override
    public ResourceLocation getTextureLocation(ConsoleControlEntity controlEntity) {
        return TEXTURE;
    }
}
