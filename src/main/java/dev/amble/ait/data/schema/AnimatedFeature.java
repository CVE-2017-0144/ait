package dev.amble.ait.data.schema;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.model.geom.ModelPart;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.client.tardis.ClientTardis;

public interface AnimatedFeature {
    @OnlyIn(Dist.CLIENT)
    void runAnimations(ModelPart root, PoseStack matrices, float tickDelta, ClientTardis tardis);
}
