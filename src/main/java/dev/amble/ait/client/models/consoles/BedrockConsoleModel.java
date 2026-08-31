package dev.amble.ait.client.models.consoles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.datapack.TravelAnimationMap;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.lib.api.Identifiable;
import dev.amble.lib.client.bedrock.BedrockAnimation;
import dev.amble.lib.client.bedrock.BedrockModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class BedrockConsoleModel implements ConsoleModel, Identifiable {
    private final BedrockModel model;
    private final ModelPart root;

    public BedrockConsoleModel(BedrockModel model) {
        this.model = model;

        if (this.model == null) throw new IllegalStateException("Bedrock Model is null. Ensure the resource pack is loaded correctly.");

        this.root = this.model.create().bakeRoot();
    }

    @Override
    public ResourceLocation id() {
        return this.model.id();
    }

    @Override
    public ModelPart root() {
        return root;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ConsoleBlockEntity console, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        matrices.pushPose();

        ConsoleVariantSchema schema = console.getVariant();

        this.applyOffsets(matrices, schema);

        root().render(matrices, vertices, light, overlay);

        matrices.popPose();

    }

    public void applyOffsets(PoseStack matrices, ConsoleVariantSchema schema) {
        if (schema instanceof DatapackConsole datapackConsole) {
            Vec3 offset = datapackConsole.getOffset().multiply(1, -1, 1);
            matrices.translate(offset.x, offset.y, offset.z);

            Vec3 scale = datapackConsole.getScale();
            matrices.scale((float) scale.x, (float) scale.y, (float) scale.z);
        }
    }

    @Override
    public void animateBlockEntity(ConsoleBlockEntity console, TravelHandlerBase.State state, boolean hasPower) {
        if (!(console.getVariant() instanceof TravelAnimationMap.Holder schema)) return;

        TravelAnimationMap map = schema.getAnimations();
        if (map == null) {
            throw new IllegalStateException("DatapackConsole " + console.getVariant().id() + " has no animations defined.");
        }

        BedrockAnimation anim = map.getAnimation(state);

        if (anim == null) return;

        this.root().getAllParts().forEach(ModelPart::resetPose);

        anim.apply(this.root(), console.ANIM_STATE, console.getAge(), 1F, null);
    }
}
