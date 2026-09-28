package dev.amble.ait.mixin.client.rendering;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.amble.ait.api.AITUseActions;
import dev.amble.ait.core.AITTags;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public class HeldItemRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(method = "applyItemArmTransform", at = @At("HEAD"), cancellable = true)
    private void applyEquipProgress(PoseStack matrices, HumanoidArm arm, float equipProgress, CallbackInfo ci) {
        if (this.minecraft.player == null)
            return;

        Player player = this.minecraft.player;
        ItemStack stack = player.getUseItem();

        if (noBop(stack)) {
            int i = arm == HumanoidArm.RIGHT ? 1 : -1;
            matrices.translate((float) i * 0.56F, -0.52F, -0.72F);

            ci.cancel();
        }
    }

    @Redirect(method = "renderArmWithItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"))
    private UseAnim getUseAction(ItemStack instance) {
        UseAnim result = instance.getUseAnimation();

        return result == ((AITUseActions) (Object) UseAnim.NONE).ait$sonic()
                ? UseAnim.NONE : result;
    }

    @Unique private static boolean noBop(ItemStack stack) {
        return stack.getItemHolder().is(AITTags.Items.NO_BOP);
    }
}
