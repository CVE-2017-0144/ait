package dev.amble.ait.client.renderers.machines;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.client.models.machines.GenericSubSystemModel;
import dev.amble.ait.core.engine.block.generic.GenericStructureSystemBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import org.joml.Vector3f;
public class GenericSubSystemRenderer<T extends GenericStructureSystemBlockEntity> implements BlockEntityRenderer<T> {
    private final GenericSubSystemModel model;
    private static final Minecraft client = Minecraft.getInstance();

    public GenericSubSystemRenderer(BlockEntityRendererProvider.Context ctx) {
        this.model = new GenericSubSystemModel();
    }

    @Override
    public void render(GenericStructureSystemBlockEntity entity, float tickDelta, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        matrices.pushPose();

        matrices.mulPose(Axis.XP.rotationDegrees(180));
        matrices.translate(0.5f, -1.5f, -0.5f);

        ItemStack stack = entity.getSourceStack().orElse(null);
        boolean hasStack = stack != null && !stack.isEmpty();

        ModelPart wires = this.model.root().getChild("wires");
        wires.visible = hasStack;

        if (hasStack) {
            matrices.pushPose();
            matrices.mulPose(Axis.XP.rotationDegrees(180));
            double offset = Math.sin((entity.getLevel().getGameTime() + tickDelta) / 8.0) / 18.0;

            matrices.translate(0, -0.95f + (offset / 2), 0);

            Vector3f scale = client.getItemRenderer().getModel(stack, entity.getLevel(), null, 0).getTransforms().firstPersonRightHand.scale;
            matrices.scale(0.9f, 0.9f, 0.9f);
            matrices.scale(scale.x, scale.y, scale.z);

            client.getItemRenderer().renderStatic(stack, ItemDisplayContext.GROUND, 0xf000f0,
                    overlay, matrices, vertexConsumers, entity.getLevel(), 0);
            matrices.popPose();
        }

        matrices.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(T blockEntity) {
        return AABB.INFINITE;
    }
}
