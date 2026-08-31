package dev.amble.ait.client.models.exteriors; // Made with Blockbench 4.10.1
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

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
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.handler.DoorHandler;

public class BookshelfExteriorModel extends SimpleExteriorModel {
    private final ModelPart bookshelf;

    public BookshelfExteriorModel(ModelPart root) {
        this.bookshelf = root.getChild("bookshelf");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition bookshelf = modelPartData.addOrReplaceChild("bookshelf",
                CubeListBuilder.create().texOffs(37, 0)
                        .addBox(-12.5938F, -21.5354F, -6.4615F, 2.0F, 42.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 0)
                        .addBox(9.4062F, -21.5354F, -6.4615F, 2.0F, 42.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 59)
                        .addBox(-10.5938F, 18.4646F, -6.4615F, 20.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 78)
                        .addBox(-10.5938F, -19.5354F, 7.5385F, 20.0F, 38.0F, 0.0F, new CubeDeformation(0.05F)).texOffs(74, 0)
                        .addBox(-10.5938F, 4.4646F, -6.4615F, 20.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(21, 0)
                        .addBox(-9.8438F, -3.5354F, -5.4615F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).texOffs(59, 64)
                        .addBox(-10.5938F, -6.5354F, -6.4615F, 20.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)).texOffs(58, 43)
                        .addBox(-10.5938F, -21.5354F, -6.4615F, 20.0F, 2.0F, 16.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.5938F, 3.5354F, 1.4615F, 0.0F, 3.1416F, 0.0F));

        PartDefinition cube_r1 = bookshelf.addOrReplaceChild("cube_r1",
                CubeListBuilder.create().texOffs(91, 98).addBox(14.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-17.5938F, 2.4646F, -3.4615F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r2 = bookshelf.addOrReplaceChild("cube_r2",
                CubeListBuilder.create().texOffs(101, 28).addBox(14.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-15.5938F, 2.4646F, -3.4615F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r3 = bookshelf
                .addOrReplaceChild("cube_r3",
                        CubeListBuilder.create().texOffs(102, 107).addBox(14.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F,
                                new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-13.5938F, 2.4646F, -3.4615F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r4 = bookshelf.addOrReplaceChild("cube_r4",
                CubeListBuilder.create().texOffs(112, 17).addBox(14.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-11.5938F, 2.4646F, -3.4615F, 0.0F, 0.0F, 0.1309F));

        PartDefinition cube_r5 = bookshelf.addOrReplaceChild("cube_r5",
                CubeListBuilder.create().texOffs(114, 62).addBox(14.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-7.5938F, 2.4646F, -3.4615F, 0.0F, 0.0F, 0.1309F));

        PartDefinition left_door = bookshelf.addOrReplaceChild("left_door", CubeListBuilder.create().texOffs(66, 81).addBox(-9.0F,
                -38.0F, -1.0F, 10.0F, 38.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(8.4062F, 18.4646F, 8.5385F));

        PartDefinition right_door = bookshelf.addOrReplaceChild("right_door", CubeListBuilder.create().texOffs(41, 81).addBox(-1.0F,
                -38.0F, -1.0F, 10.0F, 38.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(-9.5938F, 18.4646F, 8.5385F));

        PartDefinition plant = bookshelf.addOrReplaceChild("plant",
                CubeListBuilder.create().texOffs(58, 0).addBox(-2.5F, 3.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.9062F, 10.4646F, 1.0385F, 0.0F, 0.2182F, 0.0F));

        PartDefinition cube_r6 = plant.addOrReplaceChild("cube_r6",
                CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -3.0F, -2.5F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition cube_r7 = plant.addOrReplaceChild("cube_r7",
                CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, -3.0F, -2.5F, 0.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition doom_helmet = bookshelf.addOrReplaceChild("doom_helmet", CubeListBuilder.create(),
                PartPose.offset(-8.5938F, 18.3646F, -1.4615F));

        PartDefinition cube_r8 = doom_helmet.addOrReplaceChild("cube_r8",
                CubeListBuilder.create().texOffs(74, 17).addBox(0.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.25F))
                        .texOffs(91, 81).addBox(0.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.3054F, 0.0F));

        PartDefinition door_open_book = bookshelf.addOrReplaceChild("door_open_book", CubeListBuilder.create(),
                PartPose.offset(5.8F, 4.4567F, 0.5385F));

        PartDefinition cube_r9 = door_open_book.addOrReplaceChild("cube_r9",
                CubeListBuilder.create().texOffs(113, 98).addBox(14.0F, -8.0F, -2.0F, 2.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-15.3938F, -1.9921F, -4.0F, 0.0F, 0.0F, 0.1309F));

        PartDefinition film = bookshelf.addOrReplaceChild("film",
                CubeListBuilder.create().texOffs(46, 9)
                        .addBox(-2.1667F, 0.1667F, -1.1667F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.05F)).texOffs(0, 12)
                        .addBox(-2.1667F, 0.1667F, -1.1667F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(11, 5)
                        .addBox(-0.1667F, -0.8333F, 0.8333F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).texOffs(45, 14)
                        .addBox(-0.1667F, -0.8333F, 0.8333F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F)).texOffs(21, 3)
                        .addBox(0.8333F, -0.8333F, -1.1667F, 1.0F, 0.0F, 1.0F, new CubeDeformation(0.05F)).texOffs(0, 0)
                        .addBox(0.8333F, -0.8333F, -1.1667F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-0.4271F, -7.7021F, -4.2949F, 0.0F, 0.2182F, 0.0F));

        PartDefinition camera = bookshelf.addOrReplaceChild("camera", CubeListBuilder.create(),
                PartPose.offset(4.9062F, -7.5354F, -2.4615F));

        PartDefinition cube_r10 = camera.addOrReplaceChild("cube_r10",
                CubeListBuilder.create().texOffs(6, 0).addBox(-1.5F, -1.75F, -1.75F, 3.0F, 3.0F, 1.0F, new CubeDeformation(-0.25F))
                        .texOffs(40, 0).addBox(-1.0F, -1.0F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(11, 9)
                        .addBox(-1.0F, -1.0F, -3.1F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(57, 62)
                        .addBox(-2.5F, -2.0F, -1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).texOffs(7, 12)
                        .addBox(-1.0F, -2.5F, 0.25F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.48F, 0.0F));

        PartDefinition candlestick = bookshelf.addOrReplaceChild("candlestick",
                CubeListBuilder.create().texOffs(79, 0).addBox(5.0F, -11.0F, -2.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                        .texOffs(57, 68).addBox(5.0F, -5.0F, -2.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.25F)).texOffs(21, 0)
                        .addBox(7.25F, -4.0F, -1.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).texOffs(99, 17)
                        .addBox(4.0F, -1.0F, -3.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-12.1372F, -6.5354F, -0.6306F, 0.0F, -0.2182F, 0.0F));

        PartDefinition cube_r11 = candlestick.addOrReplaceChild("cube_r11",
                CubeListBuilder.create().texOffs(47, 5).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, -11.0F, -1.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition cube_r12 = candlestick.addOrReplaceChild("cube_r12",
                CubeListBuilder.create().texOffs(46, 7).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(6.0F, -11.0F, -1.0F, 0.0F, 0.7854F, 0.0F));

        PartDefinition potion = bookshelf.addOrReplaceChild("potion", CubeListBuilder.create(),
                PartPose.offset(5.4062F, -6.5354F, 3.5385F));

        PartDefinition cube_r13 = potion
                .addOrReplaceChild(
                        "cube_r13", CubeListBuilder.create().texOffs(131, 42).addBox(-6.0F, -12.75F, -3.0F, 10.0F, 15.0F,
                                0.0F, new CubeDeformation(0.001F)),
                        PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.3969F, 0.5372F, 0.0702F));
        return LayerDefinition.create(modelData, 256, 256);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        bookshelf.render(matrices, vertexConsumer, light, overlay, color);
    }

    @Override
    public ModelPart root() {
        return bookshelf;
    }

    @Override
    public void renderWithAnimations(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices,
                                     VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha) {
        matrices.pushPose();
        matrices.scale(1F, 1F, 1F);
        matrices.translate(0, -1.5f, 0);

        this.renderDoors(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha, false);

        super.renderWithAnimations(tardis, exterior, root, matrices, vertices, light, overlay, red, green, blue, pAlpha);

        matrices.popPose();
    }

    @Override
    public <T extends Entity & Linkable> void renderEntity(T falling, ModelPart root, PoseStack matrices,
                                                           VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        if (!falling.isLinked())
            return;

        matrices.pushPose();
        matrices.scale(1F, 1F, 1F);
        matrices.translate(0, -1.5f, 0);

        if (!AITModClient.CONFIG.animateDoors) {
            DoorHandler door = falling.tardis().get().door();

            this.bookshelf.getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -4.75F : 0.0F;
            this.bookshelf.getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen()) ? 4.75F : 0.0F;
        } else {
            float maxRot = 90f;
            this.bookshelf.getChild("left_door").yRot = -(float) Math.toRadians(maxRot * falling.tardis().get().door().getLeftRot());
            this.bookshelf.getChild("right_door").yRot = (float) Math.toRadians(maxRot * falling.tardis().get().door().getRightRot());
        }

        super.renderEntity(falling, root, matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        matrices.popPose();
    }

    @Override
    public void renderDoors(ClientTardis tardis, ExteriorBlockEntity exterior, ModelPart root, PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float pAlpha, boolean isBOTI) {
        DoorHandler door = tardis.door();

        if (!AITModClient.CONFIG.animateDoors) {
            this.bookshelf.getChild("left_door").yRot = (door.isLeftOpen() || door.isOpen()) ? -4.75F : 0.0F;
            this.bookshelf.getChild("right_door").yRot = (door.isRightOpen() || door.areBothOpen()) ? 4.75F : 0.0F;
        } else {
            float maxRot = 90f;
            this.bookshelf.getChild("left_door").yRot = -(float) Math.toRadians(maxRot * door.getLeftRot());
            this.bookshelf.getChild("right_door").yRot = (float) Math.toRadians(maxRot * door.getRightRot());
        }

      if (isBOTI) {
          matrices.pushPose();
            matrices.scale(1F, 1F, 1F);
           matrices.mulPose(Axis.YP.rotationDegrees(180f));
           matrices.translate(0.04f, -1.28f, -0.10f);
           this.bookshelf.getChild("left_door").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
           this.bookshelf.getChild("right_door").render(matrices, vertices, light, overlay, FastColor.ARGB32.colorFromFloat(pAlpha, red, green, blue));
            matrices.popPose();
        }
    }
}
