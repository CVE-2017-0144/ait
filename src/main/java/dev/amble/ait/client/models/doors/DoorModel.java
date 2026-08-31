package dev.amble.ait.client.models.doors;

import java.util.function.Function;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.DoorBlockEntity;

@SuppressWarnings("rawtypes")
public abstract class DoorModel extends HierarchicalModel implements AnimatedModel<DoorBlockEntity> {

    public static String TEXTURE_PATH = "textures/blockentities/exteriors/";

    public DoorModel() {
        this(RenderType::entityCutoutNoCull);
    }

    public DoorModel(Function<ResourceLocation, RenderType> function) {
        super(function);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, DoorBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        renderWithAnimations(tardis, ((AbstractLinkableBlockEntity) linkableBlockEntity) , root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
    }

    // Overloaded method for compatibility with older code
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity linkableBlockEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        assert linkableBlockEntity instanceof DoorBlockEntity : "Expected DoorBlockEntity, got " + linkableBlockEntity.getClass().getSimpleName();

        root.render(matrices, vertices, light, overlay, red, green, blue, pAlpha);
    }

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw,
            float headPitch) {
    }
}
