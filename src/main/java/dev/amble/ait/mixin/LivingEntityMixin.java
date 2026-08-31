package dev.amble.ait.mixin;

import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import dev.amble.ait.api.ExtraPushableEntity;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.util.SafePosSearch;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.util.TeleportUtil;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ExtraPushableEntity {

    @Unique private TriState ait$pushable = TriState.DEFAULT;

    @Shadow public abstract ItemStack getItemBySlot(EquipmentSlot var1);

    public LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void ait$tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;

        if (entity instanceof Player player
                && (player.isCreative() || player.isSpectator()))
             return;

        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);

        if (stack.is(AITTags.Items.FULL_RESPIRATORS) || stack.is(AITTags.Items.HALF_RESPIRATORS))
            return;

        if (entity.level() instanceof TardisServerWorld tardisWorld && !tardisWorld.getTardis().isGrowth()
                && !tardisWorld.getTardis().subsystems().lifeSupport().isEnabled()) {
            entity.addEffect(new MobEffectInstance(MobEffects.WITHER, 1,
                    200, false, false));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS,
                    200, 1, false, false));
        }
    }

    @Override
    public void ait$setPushBehaviour(TriState pushable) {
        this.ait$pushable = pushable;
    }

    @Override
    public TriState ait$pushBehaviour() {
        return ait$pushable;
    }

    @Inject(method = "isPushable", at = @At("RETURN"), cancellable = true)
    public void isPushable(CallbackInfoReturnable<Boolean> cir) {
        boolean pushable = cir.getReturnValueZ();

        if (this.ait$pushable != TriState.DEFAULT)
            pushable = this.ait$pushable.get();

        cir.setReturnValue(pushable);
    }

    @Inject(method = "onBelowWorld", at = @At("HEAD"))
    public void tickVoid(CallbackInfo ci) {
        if (!this.level().isClientSide() && this.level().dimension() == AITDimensions.TIME_VORTEX_WORLD) {
            if (WorldUtil.getTravelWorlds().isEmpty())
                return;

            LivingEntity entity = (LivingEntity) (Object) this;
            int worldIndex = this.level().getRandom().nextInt(WorldUtil.getTravelWorlds().size());

            ServerLevel world = WorldUtil.getTravelWorlds().get(worldIndex);
            CachedDirectedGlobalPos safe = CachedDirectedGlobalPos.create(world, entity.blockPosition(), (byte) 0);

            SafePosSearch.wrapSafe(safe, SafePosSearch.Kind.MEDIAN, true,
                    result -> TeleportUtil.teleport(entity, world, result.getPos().getCenter(), entity.getYRot()));
        }
    }
}
