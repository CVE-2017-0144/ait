package dev.amble.lib.platform.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public final class PlayerInteractionEvents {

    private PlayerInteractionEvents() {}

    public interface UseBlock {
        InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hit);
    }

    public interface AttackBlock {
        InteractionResult attack(Player player, Level world, InteractionHand hand, BlockPos pos,
                Direction direction);
    }

    public static final Event<UseBlock> USE_BLOCK = EventFactory.createArrayBacked(UseBlock.class,
            callbacks -> (player, world, hand, hit) -> {
                for (UseBlock callback : callbacks) {
                    InteractionResult result = callback.interact(player, world, hand, hit);

                    if (result != InteractionResult.PASS)
                        return result;
                }

                return InteractionResult.PASS;
            });

    public static final Event<AttackBlock> ATTACK_BLOCK = EventFactory.createArrayBacked(AttackBlock.class,
            callbacks -> (player, world, hand, pos, direction) -> {
                for (AttackBlock callback : callbacks) {
                    InteractionResult result = callback.attack(player, world, hand, pos, direction);

                    if (result != InteractionResult.PASS)
                        return result;
                }

                return InteractionResult.PASS;
            });

    static {
        net.fabricmc.fabric.api.event.player.UseBlockCallback.EVENT
                .register((player, world, hand, hit) -> USE_BLOCK.invoker().interact(player, world, hand, hit));
        net.fabricmc.fabric.api.event.player.AttackBlockCallback.EVENT
                .register((player, world, hand, pos, direction) -> ATTACK_BLOCK.invoker()
                        .attack(player, world, hand, pos, direction));
    }
}
