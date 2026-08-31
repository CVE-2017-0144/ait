package dev.amble.ait.module.planet.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.amble.ait.module.planet.core.item.SpacesuitItem;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

@Mixin(value = PlayerModel.class, priority = 1001)
public class PlayerEntityModelMixin<T extends LivingEntity> {

    @Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    private void ait$setAngles(T livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        PlayerModel model = (PlayerModel) (Object) this;

        boolean noHelmet = !(livingEntity.getItemBySlot(EquipmentSlot.HEAD).getItem() instanceof SpacesuitItem);
        boolean noChestplate = !(livingEntity.getItemBySlot(EquipmentSlot.CHEST).getItem() instanceof SpacesuitItem);
        boolean noPants = !(livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() instanceof SpacesuitItem);

        // i hate this just as much as everyone else seeing this block of code.
        // the &&'s were added because mods that use playeranimator may change visibility
        //  of certain player model parts
        model.head.visible = noHelmet && model.head.visible;
        model.hat.visible = noHelmet && model.hat.visible;
        model.body.visible = noChestplate && model.body.visible;
        model.jacket.visible = noChestplate && model.jacket.visible;
        model.leftArm.visible = noChestplate && model.leftArm.visible;
        model.leftSleeve.visible = noChestplate && model.leftSleeve.visible;
        model.rightArm.visible = noChestplate && model.rightArm.visible;
        model.rightSleeve.visible = noChestplate && model.rightSleeve.visible;
        model.leftLeg.visible = noPants && model.leftLeg.visible;
        model.rightLeg.visible = noPants && model.rightLeg.visible;
        model.leftPants.visible = noPants && model.leftPants.visible;
        model.rightPants.visible = noPants && model.rightPants.visible;
    }
}
