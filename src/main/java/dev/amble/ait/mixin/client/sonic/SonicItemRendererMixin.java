package dev.amble.ait.mixin.client.sonic;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(ItemRenderer.class)
public class SonicItemRendererMixin {

    @Shadow @Final private ItemModelShaper itemModelShaper;

    @Inject(method = "getModel", at = @At("HEAD"), cancellable = true)
    public void getModel(ItemStack stack, Level world, LivingEntity entity, int seed, CallbackInfoReturnable<BakedModel> cir) {
        if (!stack.is(AITItems.SONIC_SCREWDRIVER))
            return;

        SonicSchema.Models models = SonicItem.schema(stack).models();
        BakedModel model;

        if (entity == null || !(entity.getUseItem() == stack && entity.isUsingItem())) {
            model = this.getOrMissing(models.inactive());
        } else {
            model = this.getOrMissing(SonicItem.mode(stack).model(models));
        }

        model.getOverrides().resolve(model, stack, (ClientLevel) world, entity, seed);
        cir.setReturnValue(model);
    }

    @Unique private BakedModel getOrMissing(ResourceLocation id) {
        BakedModel model = this.itemModelShaper.getModelManager().getModel(
                ModelResourceLocation.inventory(id)
        );

        if (model == null)
            return this.itemModelShaper.getModelManager().getMissingModel();

        return model;
    }
}
