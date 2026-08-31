package dev.amble.plushies.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.lib.AmbleKit;
import dev.amble.lib.client.bedrock.BedrockEntityModel;
import dev.amble.lib.client.bedrock.BedrockModelReference;
import dev.amble.plushies.MarketablePlushieBlock;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class PlushieDynamicItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {

    @Override
    public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        if (stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof MarketablePlushieBlock plushieBlock) {
            BedrockEntityModel<?> model = plushieBlock.model == null ? plushieBlock.model = refreshModel(plushieBlock) : plushieBlock.model;

            matrices.pushPose();
            matrices.translate(0.5D, 0.0D, 0.5D);
            matrices.mulPose(Axis.XP.rotationDegrees(180F));

            model.renderToBuffer(
                    matrices,
                    vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(plushieBlock.getTexture())),
                    light,
                    overlay,
                    1.0f, 1.0f, 1.0f, 1.0f
            );

            ResourceLocation emission = plushieBlock.getEmissionTexture();
            if (emission != null) {
                model.renderToBuffer(
                        matrices,
                        vertexConsumers.getBuffer(RenderType.entityCutoutNoCullZOffset(emission)),
                        LightTexture.FULL_BRIGHT,
                        overlay,
                        1.0f, 1.0f, 1.0f, 1.0f
                );
            }

            matrices.popPose();
        }
    }

    protected BedrockEntityModel<?> refreshModel(MarketablePlushieBlock block) {
        BedrockModelReference ref = block.getModel();
        if (ref == null) {
            throw new IllegalStateException("Block " + block + " does not have a BedrockModelReference");
        }
        return new BedrockEntityModel<>(ref.get().orElseThrow(() ->
                new IllegalStateException("BedrockModel " + ref.id() + " not found for block " + block)));
    }
}
