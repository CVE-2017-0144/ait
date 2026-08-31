package dev.amble.lib.platform.interaction;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

public class PlayerInteractionEvents {

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
                    InteractionResult r = callback.interact(player, world, hand, hit);

                    if (r != InteractionResult.PASS)
                        return r;
                }

                return InteractionResult.PASS;
            });

    public static final Event<AttackBlock> ATTACK_BLOCK = EventFactory.createArrayBacked(AttackBlock.class,
            callbacks -> (player, world, hand, pos, direction) -> {
                for (AttackBlock callback : callbacks) {
                    InteractionResult r = callback.attack(player, world, hand, pos, direction);

                    if (r != InteractionResult.PASS)
                        return r;
                }

                return InteractionResult.PASS;
            });

    static {
        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.RightClickBlock.class, e -> {
            InteractionResult r = USE_BLOCK.invoker().interact(e.getEntity(), e.getLevel(),
                    e.getHand(), e.getHitVec());

            if (r == InteractionResult.PASS)
                return;

            e.setCancellationResult(r);
            e.setCanceled(true);
        });

        NeoForge.EVENT_BUS.addListener(PlayerInteractEvent.LeftClickBlock.class, e -> {
            InteractionResult r = ATTACK_BLOCK.invoker().attack(e.getEntity(), e.getLevel(),
                    e.getHand(), e.getPos(), e.getFace());

            // LeftClickBlock has no cancellation result
            if (r != InteractionResult.PASS)
                e.setCanceled(true);
        });
    }
}
