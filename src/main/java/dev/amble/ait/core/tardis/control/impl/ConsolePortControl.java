package dev.amble.ait.core.tardis.control.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.item.ControlDiscItem;
import dev.amble.ait.core.item.WaypointItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.data.Waypoint;
import dev.amble.ait.module.gun.core.item.StaserBoltMagazine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundStopSoundPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.JukeboxSong;

public class ConsolePortControl extends Control {

    private SoundEvent currentMusic = null;

    public ConsolePortControl() {
        super(AITMod.id("console_port"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console,
                             boolean leftClick) {
        if (leftClick) {
            if (!tardis.extra().getInsertedDisc().isEmpty()) {
                ejectDisc(tardis, player, world, console);
                return Result.SUCCESS;
            }

            tardis.waypoint().spawnItem(console);
            return Result.SUCCESS;
        }

        ItemStack itemStack = player.getMainHandItem();

        if (itemStack.is(AITTags.Items.INSERTABLE_DISCS) || itemStack.has(DataComponents.JUKEBOX_PLAYABLE)) {
            if (!tardis.extra().getInsertedDisc().isEmpty()) return Result.FAILURE;


            tardis.extra().setInsertedDisc(itemStack.copy());
            JukeboxSong.fromStack(world.registryAccess(), itemStack).ifPresent(song -> {
                this.currentMusic = song.value().soundEvent().value();
                world.playSound(null, console, this.currentMusic, SoundSource.RECORDS, 6f, 1);
            });
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

            return Result.SUCCESS;
        }

        if (itemStack.getItem() instanceof StaserBoltMagazine) {
            CompoundTag nbt = ItemNbt.get(itemStack);
            double currentFuel = nbt.getDouble(StaserBoltMagazine.FUEL_KEY);
            double maxFuel = StaserBoltMagazine.MAX_FUEL;

            if (currentFuel < maxFuel) {
                double newFuel = Math.min(currentFuel + 500, maxFuel);
                nbt.putDouble(StaserBoltMagazine.FUEL_KEY, newFuel);
                ItemNbt.set(itemStack, nbt);
                tardis.removeFuel(500);

                TardisDesktop.playSoundAtConsole(world, console, AITSounds.SLOT_IN, SoundSource.PLAYERS, 6f, 1);
                return Result.SUCCESS_ALT;
            }
        }

        if (itemStack.getItem() instanceof WaypointItem) {
            /*if (WaypointItem.getPos(itemStack) == null)
                WaypointItem.setPos(itemStack, tardis.travel().position());*/

            tardis.waypoint().setHasCartridge();
            tardis.waypoint().set(Waypoint.fromStack(itemStack), console, true);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);

            TardisDesktop.playSoundAtConsole(world, console, AITSounds.SLOT_IN, SoundSource.PLAYERS, 6f, 1);
            return Result.SUCCESS_ALT;
        } else if(itemStack.getItem() instanceof ControlDiscItem) {
            CompoundTag stackNbt = ItemNbt.get(itemStack);
            if (stackNbt.get(ControlDiscItem.POS_KEY) == null) return Result.FAILURE;
            // We're going to set both cartridge and disc booleans just for parity
            tardis.waypoint().setIsDisc();
            tardis.waypoint().setHasCartridge();
            if (stackNbt.get(ControlDiscItem.CAN_CONTAIN_PLAYERS) != null) {
                tardis.waypoint().setCanContainPlayers(ControlDiscItem.canContainPlayers(itemStack));
            }
            tardis.waypoint().set(Waypoint.fromStack(itemStack), console, false);
            player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
        }

        return Result.FAILURE;
    }


    private void ejectDisc(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console) {
        if (tardis.extra().getInsertedDisc().isEmpty()) return;
        world.playSound(null, console, AITSounds.SLOT_IN, SoundSource.PLAYERS, 6f, 1);
        ClientboundStopSoundPacket stopPacket = new ClientboundStopSoundPacket(null, SoundSource.RECORDS);
        for (ServerPlayer otherPlayer : world.players()) {
            otherPlayer.connection.send(stopPacket);
        }
        player.getInventory().placeItemBackInInventory(tardis.extra().getInsertedDisc());
        tardis.extra().setInsertedDisc(ItemStack.EMPTY);
        currentMusic = null;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return SoundEvents.EMPTY;
    }
}
