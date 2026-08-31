package dev.amble.ait.client.models.exteriors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.datapack.TravelAnimationMap;
import dev.amble.ait.data.schema.door.AnimatedDoor;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import dev.amble.lib.client.bedrock.BedrockModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class BedrockExteriorModel implements ExteriorModel, Identifiable {
    private final BedrockModel model;
    private final ModelPart root;

    public BedrockExteriorModel(BedrockModel model) {
        this.model = model;

        if (this.model == null) throw new IllegalStateException("Bedrock Model is null. Ensure the resource pack is loaded correctly.");

        this.root = this.model.create().bakeRoot();
    }

    @Override
    public ResourceLocation id() {
        return this.model.id();
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, float tickDelta) {
        matrices.pushPose();

        ExteriorVariantSchema schema = tardis.getExterior().getVariant();

        if (schema instanceof AnimatedDoor animDoor) {
            this.root().getAllParts().forEach(ModelPart::resetPose);
            animDoor.runAnimations(root, matrices, tickDelta, tardis);
        }

        if (schema instanceof TravelAnimationMap.Holder holder) {
            runTravelAnimations(holder, tardis, tickDelta);
        }

        this.render(matrices, vertices, light, overlay, red, green, blue, alpha);

        matrices.popPose();
    }

    private void runTravelAnimations(TravelAnimationMap.Holder holder, ClientTardis tardis, float tickDelta) {
        TravelAnimationMap map = holder.getAnimations();
        if (map == null) return;

        TravelHandlerBase.State state = tardis.travel().getState();
        BedrockAnimation anim = map.getAnimation(state);

        if (anim == null) return;

        if (anim.loopMode == BedrockAnimation.LoopMode.NONE) {
            if (Minecraft.getInstance().player.tickCount % 40 == 0) {
                AITMod.LOGGER.error("Non-looping animations are not supported in BedrockExteriorModel. Animation: {}", anim.name);
            }
            return;
        }

        float ticks = Minecraft.getInstance().player.tickCount;
        anim.apply(root, (int) ticks, tickDelta);
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {

    }

    @Override
    public ModelPart root() {
        return this.root;
    }
}
