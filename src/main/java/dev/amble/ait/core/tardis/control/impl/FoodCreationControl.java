package dev.amble.ait.core.tardis.control.impl;

import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;

public class FoodCreationControl extends Control {

    public FoodCreationControl() {
        super(AITMod.id("food_creation"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console,
                             boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);
        if (tardis.fuel().getCurrentFuel() < 500)
            return Result.FAILURE;

        Scheduler.get().runTaskLater(() -> {
            if (world.getBlockState(console).isAir())
                return;

            ItemStack coffeeItem = tardis.extra().getRefreshmentItem();

            Vec3 spawnPosition = Vec3.atCenterOf(console).add(0, 1.5, 1);
            ItemEntity coffeeEntity = new ItemEntity(world, spawnPosition.x, spawnPosition.y, spawnPosition.z, coffeeItem);

            tardis.removeFuel(500);
            world.addFreshEntity(coffeeEntity);
        }, TaskStage.END_SERVER_TICK, TimeUnit.TICKS, 45);

        return Result.SUCCESS;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.COFFEE_MACHINE;
    }
}
