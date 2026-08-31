package dev.amble.ait.data.schema;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.tardis.ClientTardis;

public interface AnimatedFeature {
    @Environment(EnvType.CLIENT)
    void runAnimations(ModelPart root, PoseStack matrices, float tickDelta, ClientTardis tardis);
}
