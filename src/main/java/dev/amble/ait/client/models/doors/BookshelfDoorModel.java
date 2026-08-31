package dev.amble.ait.client.models.doors;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class BookshelfDoorModel extends DoorModel {
    private final ModelPart bookshelf;

    public BookshelfDoorModel(ModelPart root) {
        this.bookshelf = root.getChild("bookshelf");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bookshelf = modelPartData.addOrReplaceChild("bookshelf",
                CubeListBuilder.create().texOffs(51, 14)
                        .addBox(-12.5938F, -21.5354F, 7.5385F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(14, 14)
                        .addBox(9.4062F, -21.5354F, 7.5385F, 2.0F, 42.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(14, 73)
                        .addBox(-10.5938F, 18.4646F, 7.5385F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(0, 78)
                        .addBox(-10.5938F, -19.5354F, 7.5385F, 20.0F, 38.0F, 0.0F, new CubeDeformation(0.05F)).texOffs(72, 57)
                        .addBox(-10.5938F, -21.5354F, 7.5385F, 20.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5938F, 3.5354F, 15.4615F, 0.0F, 3.1416F, 0.0F));

        bookshelf.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(66, 81).addBox(-9.0F, -38.0F, -1.0F, 10.0F, 38.0F,
                2.0F, new CubeDeformation(0.0F)), PartPose.offset(8.4062F, 18.4646F, 8.5385F));

        bookshelf.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(41, 81).addBox(-1.0F, -38.0F, -1.0F, 10.0F, 38.0F,
                2.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.5938F, 18.4646F, 8.5385F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red,
            float green, float blue, float alpha) {
        bookshelf.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, AbstractLinkableBlockEntity doorEntity, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, float tickDelta) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.bookshelf.getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? 4.75F : 0.0F;
            this.bookshelf.getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen()) ? -4.75F : 0.0F;
        } else {
            float maxRot = 90f;
            this.bookshelf.getChild("left_door").yRot = (float) Math.toRadians(maxRot*door.getRightRot());
            this.bookshelf.getChild("right_door").yRot = (float) -Math.toRadians(maxRot*door.getLeftRot());
        }

        matrices.pushPose();
        matrices.scale(1F, 1F, 1F);
        matrices.translate(0, -1.5, 0);

        super.renderWithAnimations(tardis, doorEntity, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, tickDelta);
        matrices.popPose();
    }

    @Override
    public ModelPart root() {
        return bookshelf;
    }
}
