package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class ElectricalDischargeControl extends Control {

    private static final int ARTRON_COST = 1250;
    private static final int EFFECT_RADIUS = 2;
    private static final int INITIAL_DELAY = 40;
    private static final int TOTAL_DURATION = 80;

    public ElectricalDischargeControl() {
        super(AITMod.id("electrical_discharge"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (tardis.fuel().getCurrentFuel() < ARTRON_COST) {
            player.displayClientMessage(Component.translatable("tardis.message.control.electric.fail", ARTRON_COST).withStyle(ChatFormatting.RED), true);
            return Result.FAILURE;
        }

        tardis.fuel().removeFuel(ARTRON_COST);

        BlockPos exteriorPos = tardis.travel().position().getPos();
        ServerLevel exteriorWorld = tardis.travel().position().getWorld();

        Scheduler.get().runTaskLater(() -> {
            spreadElectricalEffects(exteriorWorld, exteriorPos);
        }, TaskStage.END_SERVER_TICK, TimeUnit.TICKS, INITIAL_DELAY);

        Scheduler.get().runTaskLater(() -> {
            world.playSound(null, console, AITSounds.DING, SoundSource.BLOCKS, 1.0F, 1.0F);
        }, TaskStage.END_SERVER_TICK, TimeUnit.TICKS, TOTAL_DURATION);

        return Result.SUCCESS;
    }

    private void spreadElectricalEffects(ServerLevel world, BlockPos pos) {
        AABB effectBox = new AABB(pos).inflate(EFFECT_RADIUS);

        world.getEntitiesOfClass(LivingEntity.class, effectBox, entity -> true).forEach(entity -> {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.POISON, 300, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 275, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 250, 1));
            entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 75, 1));
        });

        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-EFFECT_RADIUS, -1, -EFFECT_RADIUS), pos.offset(EFFECT_RADIUS, 2, EFFECT_RADIUS))) {
            world.sendParticles(ParticleTypes.ELECTRIC_SPARK, targetPos.getX() + 0.5, targetPos.getY() + 1.0, targetPos.getZ() + 0.5, 5, 0, 0.05, 0, 0.1);
        }
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.DESPERATION;
    }

    @Override
    public long getDelayLength(Tardis tardis) {
        return 800;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.BWEEP;
    }
}
