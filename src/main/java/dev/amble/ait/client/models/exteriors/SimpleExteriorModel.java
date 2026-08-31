package dev.amble.ait.client.models.exteriors;

import java.util.function.Function;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;

@SuppressWarnings("rawtypes")
public abstract class SimpleExteriorModel extends HierarchicalModel implements ExteriorModel {

    public SimpleExteriorModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public SimpleExteriorModel(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta) {
        renderWithAnimations(tardis, linkableBlockEntity, root, matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    // Overloaded method for compatibility with older code
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(alpha, red, green, blue));
    }
    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, FastColor.ARGB32.colorFromFloat(alpha, red, green, blue));
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }

    @Override
    public abstract void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI);
}
