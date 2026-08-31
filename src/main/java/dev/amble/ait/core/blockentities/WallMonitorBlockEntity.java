package dev.amble.ait.core.blockentities;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class WallMonitorBlockEntity extends InteriorLinkableBlockEntity {

    public WallMonitorBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.WALL_MONITOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    public void useOn(Level world, boolean sneaking, Player player) {
        if (!(player instanceof ServerPlayer serverPlayer))
            return;

        if (this.tardis().isEmpty())
            return;

        Tardis tardis = this.tardis().get();

        if (!tardis.fuel().hasPower())
            return;

        boolean security = tardis.stats().security().get();

        if (security && !SecurityControl.hasMatchingKey(serverPlayer, tardis))
            return;

        player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.0F);
        AITMod.openScreen((ServerPlayer) player, 0, tardis.getUuid()); // we can cast because we know its on
                                                                                // server :p
    }
}
