package dev.amble.ait.client.renderers.machines;

import org.joml.Vector3f;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.machines.EngineModel;
import dev.amble.ait.core.blockentities.EngineBlockEntity;
import dev.amble.ait.core.engine.impl.EngineSystem;
import dev.amble.ait.core.tardis.Tardis;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LightLayer;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class EngineRenderer<T extends EngineBlockEntity> implements BlockEntityRenderer<T> {

    public static final ResourceLocation ENGINE_TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            ("textures/blockentities/machines/engine.png"));
    public static final ResourceLocation EMISSIVE_ENGINE_TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            ("textures/blockentities/machines/engine_emission.png"));
    private final EngineModel engineModel;

    public EngineRenderer(BlockEntityRendererProvider.Context ctx) {
        this.engineModel = new EngineModel(EngineModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public void render(EngineBlockEntity entity, float tickDelta, PoseStack matrices,
            MultiBufferSource vertexConsumers, int light, int overlay) {

        if (!entity.isLinked())
            return;

        Tardis tardis = entity.tardis().get();
        matrices.pushPose();
        matrices.mulPose(Axis.XN.rotationDegrees(180));
        matrices.translate(0.5, -0.867f, -0.5);

        this.engineModel.render(tardis, matrices, vertexConsumers.getBuffer(RenderType.entityCutoutNoCull(ENGINE_TEXTURE)),
                LightTexture.pack(entity.getLevel().getBrightness(LightLayer.BLOCK, entity.getBlockPos().above()), entity.getLevel().getBrightness(LightLayer.SKY, entity.getBlockPos())), overlay, 1.0F, 1.0F, 1.0F, 1.0F);

        if (tardis.fuel().hasPower()) {
            EngineSystem.Status status = tardis.subsystems().engine().status();
            Vector3f colours = status.colour;
            this.engineModel.render(tardis, matrices, vertexConsumers.getBuffer
                            (RenderType.entityCutoutNoCullZOffset(EMISSIVE_ENGINE_TEXTURE, true)),
                    0xf000f0,
                    overlay, colours.x, colours.y, colours.z, (status !=
                            EngineSystem.Status.OFF) ? 1.0F : 0.0F);
        }

        matrices.popPose();
    }
}
