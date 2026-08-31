package dev.amble.ait.client.renderers;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;

public interface BakedModelEditor {
    BakedModel getModel(ModelResourceLocation identifier);

    BakedModel ait$getModel(ResourceLocation identifier);

    void ait$setModel(ResourceLocation identifier, BakedModel model);

    void ait$setModel(ModelResourceLocation identifier, BakedModel model);
}
