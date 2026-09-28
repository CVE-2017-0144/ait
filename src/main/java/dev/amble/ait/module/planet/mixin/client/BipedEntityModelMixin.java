package dev.amble.ait.module.planet.mixin.client;

import dev.amble.ait.module.planet.core.item.SpacesuitItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = HumanoidModel.class, priority = 1001)
public class BipedEntityModelMixin<T extends LivingEntity> {
    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void ait$setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity instanceof AbstractClientPlayer) {
            return;
        }

        HumanoidModel model = (HumanoidModel) (Object) this;

        model.head.visible = !(livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpacesuitItem);
        model.body.visible = !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpacesuitItem);
        model.leftArm.visible = !(livingEntity.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof SpacesuitItem);
        model.rightArm.visible = !(livingEntity.getItemBySlot(EquipmentSlot.OFFHAND).getItem() instanceof SpacesuitItem);
        model.leftLeg.visible = !(livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpacesuitItem);
        model.rightLeg.visible = !(livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpacesuitItem);
    }
}
