package dev.amble.ait.core.tardis.control.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.item.KeyItem;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.manager.old.DeprecatedServerTardisManager;
import dev.amble.ait.core.tardis.util.TardisUtil;

public class SecurityControl extends Control {

    public SecurityControl() {
        // ⨷ ?
        super(AITMod.id("protocol_19"));
    }

    public static boolean cannotAccess(ServerTardis tardis, ServerPlayer player) {
        if (!tardis.hasWorld() || tardis.world() != player.serverLevel())
            return true; // To verify the packet is coming from a player in the TARDIS' dimension

        return tardis.stats().security().get() && !SecurityControl.hasMatchingKey(player, tardis);
    }

    public static DeprecatedServerTardisManager.Receiver withLoyaltyCheck(DeprecatedServerTardisManager.Receiver receiver) {
        return (tardis, server, player, handler, buf, sender) -> {
              if (cannotAccess(tardis, player)) return;
              receiver.receive(tardis, server, player, handler, buf, sender);
        };
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (!hasMatchingKey(player, tardis))
            return Result.FAILURE;

        boolean security = tardis.stats().security().get();
        tardis.stats().security().set(!security);
        return security ? Result.SUCCESS : Result.SUCCESS_ALT;
    }

    public static void runSecurityProtocols(Tardis tardis) {
        boolean security = tardis.stats().security().get();
        boolean isDiscShouldLeave = tardis.waypoint().isDisc() && !tardis.waypoint().canContainPlayers();
        boolean leaveBehind = tardis.travel().leaveBehind().get() || isDiscShouldLeave;

        if (!security && !isDiscShouldLeave)
            return;

        List<ServerPlayer> forRemoval = new ArrayList<>();

        if (leaveBehind) {
            for (ServerPlayer player : tardis.asServer().world().players()) {
                if (isDiscShouldLeave || !hasMatchingKey(player, tardis)) {
                    forRemoval.add(player);
                }
            }

            for (ServerPlayer player : forRemoval) {
                TardisUtil.teleportOutside(tardis, player);
            }
        }
    }

    public static boolean hasMatchingKey(ServerPlayer player, Tardis tardis) {
        if (player.hasPermissions(2))
            return true;

        if (!tardis.loyalty().get(player).isOf(tardis.permissions().p19Loyalty().get()))
            return false;

        if (!KeyItem.isKeyInInventory(player))
            return false;

        Collection<ItemStack> keys = KeyItem.getKeysInInventory(player);

        for (ItemStack stack : keys) {
            Tardis found = KeyItem.getTardisStatic(player.level(), stack);

            if (stack.getItem() == AITItems.SKELETON_KEY)
                return true;

            if (found == tardis)
                return true;
        }

        return false;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.PROTOCOL_19;
    }

    @Override
    public long getDelayLength(Tardis tardis) {
        return 50;
    }
}
