package dev.amble.ait.core.tardis.control.impl;

import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class EngineOverloadControl extends Control {

    private static final Random RANDOM = AITMod.RANDOM;
    private static final String[] SPINNER = {"/", "-", "\\", "|"};

    public EngineOverloadControl() {
        super(AITMod.id("engine_overload"));
    }



    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);



        if (tardis.fuel().getCurrentFuel() < 25000) {
            player.displayClientMessage(Component.translatable("tardis.message.control.engine_overdrive.insufficient_fuel").withStyle(ChatFormatting.RED), true);
            world.playSound(null, player.blockPosition(), AITSounds.CLOISTER, SoundSource.BLOCKS, 1.0F, 1.0F);
            return Result.FAILURE;
        }


        if (!TravelHandler.isEngineOverloadArmed(tardis.getUuid())) {
            player.displayClientMessage(Component.translatable("tardis.message.control.engine_overdrive.primed").withStyle(ChatFormatting.RED), true);
            TravelHandler.armEngineOverload(tardis.getUuid(), world);
            return Result.SUCCESS_ALT;
        }
        TravelHandler.disarmEngineOverload(tardis.getUuid());

        boolean isInFlight = tardis.travel().getState() == TravelHandlerBase.State.FLIGHT;

        if (!isInFlight) {
            tardis.travel().finishDemat();
        }

        runDumpingArtronSequence(player, () -> {
            world.playSound(null, player.blockPosition(), AITSounds.ENGINE_OVERLOAD, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.getServer().execute(() -> {
                tardis.travel().handbrake(false);

                if (!isInFlight)
                    tardis.travel().finishDemat();

                tardis.setFuelCount(0);
                tardis.travel().decreaseFlightTime(999999999);
                tardis.setRefueling(false);

                Scheduler.get().runTaskLater(() -> triggerExplosion(world, console, tardis, 4), TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 0);
            });
        });

        return Result.SUCCESS;
    }

    private void triggerExplosion(ServerLevel world, BlockPos console, Tardis tardis, int stage) {
        if (stage <= 0) return;

        //DONT BUFF THE DAMAGE, THIS HAPPENS EACH TIME THE CONSOLE EXPLODES SO 4x IT
        tardis.alarm().enable();
        tardis.subsystems().demat().removeDurability(250);
        tardis.subsystems().chameleon().removeDurability(250);
        tardis.subsystems().shields().removeDurability(250);
        tardis.subsystems().lifeSupport().removeDurability(250);
        tardis.subsystems().engine().removeDurability(250);
        tardis.crash().addRepairTicks(999999999);

        spawnParticles(world, console);
        Scheduler.get().runTaskLater(() -> spawnExteriorParticles(tardis), TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 3);

        int nextDelay = (stage == 4) ? 2 : 3;
        Scheduler.get().runTaskLater(() -> triggerExplosion(world, console, tardis, stage - 1), TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, nextDelay);
    }

    private void runDumpingArtronSequence(ServerPlayer player, Runnable onFinish) {
        for (int i = 0; i < 6; i++) {
            int delay = i + 1;
            Scheduler.get().runTaskLater(() -> {
                String frame = SPINNER[delay % SPINNER.length];

                player.displayClientMessage(Component.translatable("tardis.message.control.engine_overdrive.dumping_artron").append(" " + frame).withStyle(ChatFormatting.GOLD), true);
            }, TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, delay);
        }

        Scheduler.get().runTaskLater(() -> runFlashingFinalMessage(player, onFinish), TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 3);
    }

    private void runFlashingFinalMessage(ServerPlayer player, Runnable onFinish) {
        for (int i = 0; i < 6; i++) {
            int delay = i + 1;
            Scheduler.get().runTaskLater(() -> {
                ChatFormatting flashColor = (delay % 2 == 0) ? ChatFormatting.RED : ChatFormatting.WHITE;
                player.displayClientMessage(Component.translatable("tardis.message.control.engine_overdrive.engines_overloaded").withStyle(flashColor), true);
            }, TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, delay);
        }

        Scheduler.get().runTaskLater(onFinish, TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 3);
    }

    private void spawnParticles(ServerLevel world, BlockPos position) {
        for (int i = 0; i < 50; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 2.0;
            double offsetY = RANDOM.nextDouble() * 1.5;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 2.0;

            world.sendParticles(ParticleTypes.SNEEZE, position.getX() + 0.5 + offsetX, position.getY() + 1.5 + offsetY, position.getZ() + 0.5 + offsetZ, 2, 0, 0.05, 0, 0.1);
            world.sendParticles(ParticleTypes.ASH, position.getX() + 0.5 + offsetX, position.getY() + 1.5 + offsetY, position.getZ() + 0.5 + offsetZ, 2, 0, 0.05, 0, 0.1);
            world.sendParticles(ParticleTypes.EXPLOSION, position.getX() + 0.5 + offsetX, position.getY() + 1.5 + offsetY, position.getZ() + 0.5 + offsetZ, 2, 0, 0.05, 0, 0.1);
            world.sendParticles(ParticleTypes.LAVA, position.getX() + 0.5 + offsetX, position.getY() + 1.5 + offsetY, position.getZ() + 0.5 + offsetZ, 2, 0, 0.05, 0, 0.1);
            world.sendParticles(ParticleTypes.SMALL_FLAME, position.getX() + 0.5 + offsetX, position.getY() + 1.5 + offsetY, position.getZ() + 0.5 + offsetZ, 2, 0, 0.05, 0, 0.1);
            world.sendParticles(ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, position.getX() + 0.5 + offsetX, position.getY() + 1.5 + offsetY, position.getZ() + 0.5 + offsetZ, 2, 0, 0.05, 0, 0.1);
        }
    }

    private void spawnExteriorParticles(Tardis tardis) {
        CachedDirectedGlobalPos exteriorPos = tardis.travel().position();

        if (exteriorPos == null) return;
        ServerLevel exteriorWorld = exteriorPos.getWorld();
        BlockPos exteriorBlockPos = exteriorPos.getPos();

        spawnParticles(exteriorWorld, exteriorBlockPos);
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return SubSystem.Id.ENGINE;
    }

    @Override
    public long getDelayLength(Tardis tardis) {
        if (TravelHandler.isEngineOverloadArmed(tardis.getUuid()))
            return 360000;

        return 5;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.BWEEP;
    }
}
