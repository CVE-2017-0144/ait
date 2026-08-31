package dev.amble.ait.client.renderers.consoles;

import org.joml.Matrix4f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.BedrockConsoleModel;
import dev.amble.ait.client.models.consoles.ConsoleGeneratorModel;
import dev.amble.ait.client.models.consoles.ConsoleModel;
import dev.amble.ait.core.blockentities.ConsoleGeneratorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.registry.impl.console.variant.ClientConsoleVariantRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;

public class ConsoleGeneratorRenderer<T extends ConsoleGeneratorBlockEntity> implements BlockEntityRenderer<T> {

    private final ConsoleGeneratorModel generator;
    private final EntityRenderDispatcher dispatcher;

    public static final ResourceLocation TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            "textures/blockentities/consoles/console_generator/console_generator.png");

    private static final int VARIANT_TEXT_COLOR_TURQUOISE = FastColor.ARGB32.color(1, 0, 175, 235);
    private static final int VARIANT_TEXT_COLOR_YELLOW = FastColor.ARGB32.color(1, 255, 205, 0);

    public ConsoleGeneratorRenderer(BlockEntityRendererProvider.Context ctx) {
        this.dispatcher = ctx.getEntityRenderer();
        this.generator = new ConsoleGeneratorModel(ConsoleGeneratorModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(T entity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
            int light, int overlay) {
        if (entity.getLevel() == null || !entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();

        ConsoleVariantSchema variant = entity.getConsoleVariant();
        ClientConsoleVariantSchema clientVariant = variant.getClient();

        ConsoleModel console = clientVariant.getCachedModel();
        ResourceLocation consoleTexture = clientVariant.texture();
        ResourceLocation consoleEmission = clientVariant.emission();

        matrices.pushPose();

        matrices.mulPose(Axis.XP.rotationDegrees(180f));
        matrices.translate(0.5f, -1.5f, -0.5f);

        this.generator.renderToBuffer(matrices, vertexConsumers.getBuffer(RenderType.entityTranslucent(TEXTURE)), light,
                overlay, 1, 1, 1, 1);

        matrices.popPose();

        matrices.pushPose();
        matrices.mulPose(Axis.XP.rotationDegrees(180f));

        matrices.translate(0.5f, -1.5f + entity.getLevel().random.nextFloat() * 0.02, -0.5f);
        matrices.mulPose(Axis.YP.rotationDegrees(Minecraft.getInstance().getFrameTime() % 180));

        if (console instanceof BedrockConsoleModel bedrockConsoleModel) {
            bedrockConsoleModel.applyOffsets(matrices, entity.getConsoleVariant());
            matrices.translate(-0.5, 1.5, 0.5);
        }

        //if (powered) {
            if (tardis.isUnlocked(entity.getConsoleVariant())) {
                console.render(matrices,
                        vertexConsumers.getBuffer(entity.getConsoleVariant().getClient().equals(ClientConsoleVariantRegistry.COPPER) ? RenderType.entityTranslucent(consoleTexture) :
                                RenderType.entityTranslucentCull(consoleTexture)), 0xf000f0, overlay, 0.3607843137f,
                        0.9450980392f, 1, entity.getLevel().random.nextInt(32) != 6 ? 0.4f : 0.05f);
                if (consoleEmission != null && !consoleEmission.equals(DatapackConsole.EMPTY)) {
                    console.render(matrices,
                            vertexConsumers.getBuffer(entity.getConsoleVariant().getClient().equals(ClientConsoleVariantRegistry.COPPER) ? RenderType.entityTranslucent(consoleTexture) :
                                    RenderType.entityTranslucentCull(consoleEmission)), 0xf000f0, overlay, 0.3607843137f,
                            0.9450980392f, 1, entity.getLevel().random.nextInt(32) != 6 ? 0.4f : 0.05f);
                }
            } else {
                console.render(matrices,
                        vertexConsumers.getBuffer(entity.getConsoleVariant().getClient().equals(ClientConsoleVariantRegistry.COPPER) ? RenderType.entityTranslucent(consoleTexture) :
                                RenderType.entityTranslucentCull(consoleTexture)), light,
                        OverlayTexture.NO_OVERLAY, 0.2f, 0.2f, 0.2f,
                        entity.getLevel().random.nextInt(32) != 6 ? 0.4f : 0.05f);
            }
        //}
        matrices.popPose();

        matrices.pushPose();
        matrices.translate(0.5F, 2.75F, 0.5F);
        matrices.mulPose(this.dispatcher.cameraOrientation());
        matrices.scale(-0.1F, -0.1F, 0.1F);

        Component type = Component.translatable("console.ait.variant_label").append(entity.getConsoleVariant().text());
        Font textRenderer = Minecraft.getInstance().font;
        float l = (float) (-textRenderer.width(type) / 2);

        if (/*powered && */!tardis.isUnlocked(entity.getConsoleVariant())) {
            Component text = Component.literal("\uD83D\uDD12");
            Component requirementLevel = entity.getConsoleVariant().requirement().isPresent()
                    ? entity.getConsoleVariant().requirement().get().type().text()
                    : Component.translatable("console.ait.generator.requirement.none");
            Component requirement = Component.translatable("console.ait.generator.requires_loyalty", requirementLevel);
            float h = (float) (-textRenderer.width(text) / 2);
            float p = (float) (-textRenderer.width(requirement) / 2);

            Matrix4f matrix4f = matrices.last().pose();

            textRenderer.drawInBatch(text, h + 0.35f, 0.0F, 0xFFFFFFFF, false, matrix4f, vertexConsumers,
                    Font.DisplayMode.SEE_THROUGH, 0x000000, 0xf000f0);
            matrices.pushPose();
            matrices.scale(0.2f, 0.2f, 0.2f);
            Matrix4f matrixcf = matrices.last().pose();
            textRenderer.drawInBatch(type, l - 0.35f, 42.5F, VARIANT_TEXT_COLOR_TURQUOISE, false, matrixcf, vertexConsumers,
                    Font.DisplayMode.SEE_THROUGH, 0x000000, 0xf000f0);
            matrices.popPose();
            matrices.pushPose();
            matrices.scale(0.2f, 0.2f, 0.2f);
            Matrix4f matrixdf = matrices.last().pose();
            textRenderer.drawInBatch(requirement, p - 0.35f, 55F, VARIANT_TEXT_COLOR_YELLOW, false, matrixdf, vertexConsumers,
                    Font.DisplayMode.SEE_THROUGH, 0x000000, 0xf000f0);
            matrices.popPose();
            matrices.popPose();
        } else {
            matrices.scale(0.2f, 0.2f, 0.2f);
            Matrix4f matrixcf = matrices.last().pose();
            textRenderer.drawInBatch(type, l - 0.35f, 42.5F, VARIANT_TEXT_COLOR_YELLOW, false, matrixcf, vertexConsumers,
                    Font.DisplayMode.SEE_THROUGH, 0x000000, 0xf000f0);
            matrices.popPose();
        }
    }
}
