package dev.amble.ait.core.item;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.blocks.PeanutBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.tardis.util.TardisUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.joml.Vector3f;

public class HammerItem extends SwordItem {

    public HammerItem(int attackDamage, float attackSpeed, Properties settings) {
        super(Tiers.IRON, settings.attributes(SwordItem.createAttributes(Tiers.IRON, attackDamage, attackSpeed)));
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.IRON_BLOCK)) {
            return 15.0F;
        } else {
            return state.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
        }
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Player player = context.getPlayer();
        ItemStack stack = context.getItemInHand();

        if (!(context.getLevel() instanceof ServerLevel world))
            return InteractionResult.SUCCESS;

        if (world.getBlockState(pos).getBlock() instanceof PeanutBlock peanut)
            peanut.explode(context.getLevel(), pos);

        if (!(world.getBlockEntity(pos) instanceof ConsoleBlockEntity consoleBlockEntity))
            return InteractionResult.PASS;

        if (player == null || !consoleBlockEntity.isLinked())
            return InteractionResult.PASS;

        Tardis tardis = consoleBlockEntity.tardis().get();

        if (SecurityControl.cannotAccess(tardis.asServer(), (ServerPlayer) player))
            return InteractionResult.PASS;

        TravelHandler travel = tardis.travel();

        if (player.getCooldowns().isOnCooldown(stack.getItem()))
            return InteractionResult.PASS;

        if (!(tardis.travel().getState() == TravelHandlerBase.State.FLIGHT)) {

            if (!player.getCooldowns().isOnCooldown(stack.getItem())) {

                int hammerUses = travel.getHammerUses();
                world.playSound(null, consoleBlockEntity.getBlockPos(), AITSounds.HAMMER_HIT, SoundSource.BLOCKS,
                        1f, 1.0f);
                tardis.loyalty().subLevel((ServerPlayer) player, 10); // safe cast since its on server already

                if (hammerUses > 3) {
                    world.playSound(null, player, AITSounds.HAMMER_STRIKE, SoundSource.PLAYERS, 0.5f, 0.2f);

                    tardis.door().closeDoors();
                    tardis.door().setLocked(true);

                    travel.handbrake(false);
                    tardis.addFuel(10);
                    travel.dematerialize();
                    tardis.alarm().isEnabled();

                    world.sendParticles(ParticleTypes.SMALL_FLAME, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f,
                            5 * hammerUses, 0, 0, 0, 0.1f * hammerUses);

                    world.sendParticles(ParticleTypes.EXPLOSION, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f,
                            5 * hammerUses, 0, 0, 0, 0.1f * hammerUses);

                    world.sendParticles(
                            new DustColorTransitionOptions(new Vector3f(0.75f, 0.75f, 0.75f), new Vector3f(0.1f, 0.1f, 0.1f),
                                    1),
                            pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, 5 * hammerUses, 0, 0, 0, 0.1f * hammerUses);

                    world.explode(null, world.damageSources().fellOutOfWorld(), TardisUtil.EXPLOSION_BEHAVIOR, pos.getCenter(), 5, TardisUtil.doCreateFire(world),
                            Level.ExplosionInteraction.MOB);

                    tardis.loyalty().subLevel((ServerPlayer) player, 50); // safe cast since its on server already
                    player.getCooldowns().addCooldown(stack.getItem(), 10 * 20);
                    return InteractionResult.SUCCESS;
                }
            }
        }


        int targetTicks = travel.getTargetTicks();
        int currentFlightTicks = travel.getFlightTicks();
        int bonus = 500 * travel.speed();
        int hammerUses = travel.getHammerUses();

        double fuel = tardis.fuel().getCurrentFuel();

        double fuelCost = bonus / 5.0;

        if (hammerUses > 0) {
            bonus -= (int) Math.round(bonus * 0.1 * hammerUses);
            fuelCost += (150 * travel.speed() * hammerUses) / 7.0;
        }

        if (!world.isClientSide() && fuel < fuelCost) {
            travel.crash();

            tardis.fuel().setCurrentFuel(0.0);
            return InteractionResult.SUCCESS;
        }

        travel.setFlightTicks(Math.min(currentFlightTicks + bonus, targetTicks));
        tardis.fuel().setCurrentFuel(fuel - fuelCost);
        travel.useHammer();

        if (!world.isClientSide() && shouldCrashTardis(hammerUses)) {
            travel.crash();
        } else {
            world.playSound(null, consoleBlockEntity.getBlockPos(), SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS,
                    0.25f * hammerUses, 1.0f);
        }

        if (world.isClientSide())
            return InteractionResult.PASS;

        world.sendParticles(ParticleTypes.SMALL_FLAME, pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f,
                5 * hammerUses, 0, 0, 0, 0.1f * hammerUses);

        world.sendParticles(
                new DustColorTransitionOptions(new Vector3f(0.75f, 0.75f, 0.75f), new Vector3f(0.1f, 0.1f, 0.1f),
                        1),
                pos.getX() + 0.5f, pos.getY() + 1.25, pos.getZ() + 0.5f, 5 * hammerUses, 0, 0, 0, 0.1f * hammerUses);

        world.playSound(null, consoleBlockEntity.getBlockPos(), SoundEvents.GLOW_ITEM_FRAME_BREAK,
                SoundSource.BLOCKS, 0.25f * hammerUses, 1.0f);

        return InteractionResult.SUCCESS;
    }

    public boolean shouldCrashTardis(int annoyance) {
        if (annoyance <= 3)
            return false;

        for (int i = 0; i < annoyance; i++) {
            if (AITMod.RANDOM.nextInt(0, 10) == 1)
                return true;
        }

        return false;
    }

    @Override
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        return state.is(Blocks.IRON_BLOCK);
    }
}
