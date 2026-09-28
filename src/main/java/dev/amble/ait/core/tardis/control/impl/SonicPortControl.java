package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.item.HandlesItem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.sequences.SequenceHandler;
import dev.amble.ait.core.tardis.handler.ButlerHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class SonicPortControl extends Control {

    public SonicPortControl() {
        super(AITMod.id("sonic_port"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        ButlerHandler butler = tardis.butler();
        if (!(world.getBlockEntity(console) instanceof ConsoleBlockEntity consoleBlockEntity)) return Result.FAILURE;

        boolean hasSonicStored = !consoleBlockEntity.getSonicScrewdriver().isEmpty();
        boolean hasHandlesStored = butler.getHandles() != null;

        if ((leftClick || player.isShiftKeyDown()) && (hasSonicStored || hasHandlesStored)) {
            ItemStack item;
            if (hasSonicStored) {
                item = consoleBlockEntity.getSonicScrewdriver();
                consoleBlockEntity.setSonicScrewdriver(ItemStack.EMPTY);
            } else {
                item = butler.takeHandles();
            }

            if (item == null)
                return Result.FAILURE;

            player.getInventory().placeItemBackInInventory(item);
            return Result.SUCCESS;
        }

        ItemStack stack = player.getMainHandItem();
        if (!((stack.getItem() instanceof SonicItem) || (stack.getItem() instanceof HandlesItem)))
            return Result.FAILURE;

        LinkableItem linker = (LinkableItem) stack.getItem();
        if (!linker.isLinked(stack) || player.isShiftKeyDown()) {
            linker.link(stack, tardis);
            world.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS,
                    1.0F, 1.0F);
            SequenceHandler.spawnControlParticles(world, Vec3.atBottomCenterOf(console).add(0.0, 1.2f, 0.0));
        }

        if (stack.getItem() instanceof HandlesItem) {
            if (hasHandlesStored || hasSonicStored)
                return Result.FAILURE;

            butler.insertHandles(stack, console);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        } else if (stack.getItem() instanceof SonicItem) {
            if (hasSonicStored || hasHandlesStored)
                return Result.FAILURE;

            consoleBlockEntity.setSonicScrewdriver(stack);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }

        boolean hasSonic = !consoleBlockEntity.getSonicScrewdriver().isEmpty() || butler.getHandles() != null;
        return hasSonic ? Result.SUCCESS : Result.SUCCESS_ALT;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.SONIC_PORT;
    }

    @Override
    public boolean requiresPower() {
        return false;
    }

    @Override
    protected SubSystem.IdLike requiredSubSystem() {
        return null;
    }
}