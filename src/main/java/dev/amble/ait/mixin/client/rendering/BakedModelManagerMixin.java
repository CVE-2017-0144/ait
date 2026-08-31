package dev.amble.ait.mixin.client.rendering;

import java.util.Map;

import net.fabricmc.fabric.api.client.model.loading.v1.FabricBakedModelManager;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import dev.amble.ait.client.renderers.BakedModelEditor;

@Mixin(ModelManager.class)
public abstract class BakedModelManagerMixin implements BakedModelEditor {

    @Shadow
    private Map<ResourceLocation, BakedModel> bakedRegistry;

    @Override
    @Shadow
    public abstract BakedModel getModel(ModelResourceLocation id);

    @Override
    public BakedModel ait$getModel(ResourceLocation identifier) {
        return ((FabricBakedModelManager) this).getModel(identifier);
    }

    @Override
    public void ait$setModel(ResourceLocation identifier, BakedModel model) {
        this.bakedRegistry.put(identifier, model);
    }

    @Override
    public void ait$setModel(ModelResourceLocation identifier, BakedModel model) {
        this.bakedRegistry.put(identifier, model);
    }
}
