package dev.amble.ait.core.item.sonic;

import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LeverBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.api.tardis.link.v2.block.AbstractLinkableBlockEntity;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.impl.HADSControl;
import dev.amble.ait.core.tardis.control.impl.HandBrakeControl;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.sonic.SonicSchema;

public class OverloadSonicMode extends SonicMode {

    protected OverloadSonicMode(int index) {
        super(index);
    }

    @Override
    public Component text() {
        return Component.translatable("sonic.ait.mode.overload").withStyle(ChatFormatting.RED, ChatFormatting.BOLD);
    }

    @Override
    public void tick(ItemStack stack, Level world, LivingEntity user, int ticks, int ticksLeft) {
        if (!(world instanceof ServerLevel serverWorld) || ticks % 10 != 0) return;
        this.process(serverWorld, user, ticks);
    }

    private void process(ServerLevel world, LivingEntity user, int ticks) {
        HitResult hitResult = SonicMode.getHitResultForOutline(user);

        SonicMode.checkSonicWoodAdvancementConditions(world, user, hitResult);

        if (hitResult instanceof BlockHitResult blockHit) {
            this.overloadBlock(blockHit.getBlockPos(), world, user, ticks, blockHit);
        }

        // Entity raycast for creeper ignition and player-to-player transfer
        HitResult entityHitResult = SonicMode.getHitResult(user);

        // Ignite creepers when targeted
        if (entityHitResult instanceof EntityHitResult entityHit
                && entityHit.getEntity() instanceof Creeper creeper
                && canLight(ticks)) {
            creeper.ignite();
        }

        if (!(user instanceof Player player)) return;

        ItemStack main = player.getMainHandItem();
        ItemStack off = player.getOffhandItem();

        // Dual sonic overload in main and offhand
        /// I ain't never seen more overcomplicated code in my life bruh and I work for fucking DALEK MOD - Loqor
        if (main.getItem() instanceof SonicItem mainSonic &&
                off.getItem() instanceof SonicItem offSonic &&
                SonicItem.mode(off) == Modes.OVERLOAD) {

            double mainFuel = mainSonic.getCurrentFuel(main);
            double offFuel = offSonic.getCurrentFuel(off);

            if (mainFuel > 0 || offFuel > 0) {
                mainSonic.removeFuel(mainFuel, main);
                offSonic.removeFuel(offFuel, off);

                Vec3 dir = player.getViewVector(1.0F).scale(-0.5).add(0, 0.3, 0);
                player.push(dir.x, dir.y, dir.z);
                player.hurt(world.damageSources().magic(), 2.0F);

                playSparkEffect(world, player);

                player.getCooldowns().addCooldown(main.getItem(), 60);
                player.getCooldowns().addCooldown(off.getItem(), 60);
            }
        }

        // Overload transfer between two players via targeting
        if (entityHitResult instanceof EntityHitResult entityHit &&
                entityHit.getEntity() instanceof Player other) {

            ItemStack userStack = player.getMainHandItem();
            ItemStack otherStack = other.getMainHandItem();

            if (!(userStack.getItem() instanceof SonicItem userSonic)) return;
            if (!(otherStack.getItem() instanceof SonicItem otherSonic)) return;

            if (SonicItem.mode(otherStack) != Modes.OVERLOAD) return;

            double userFuel = userSonic.getCurrentFuel(userStack);
            double otherFuel = otherSonic.getCurrentFuel(otherStack);

            if (userFuel == otherFuel) return;

            Player lowerFuelPlayer = userFuel < otherFuel ? player : other;
            Player higherFuelPlayer = userFuel < otherFuel ? other : player;

            ItemStack lowerStack = lowerFuelPlayer.getMainHandItem();
            ItemStack higherStack = higherFuelPlayer.getMainHandItem();

            double transferAmount = 10.0;
            double actualRemoved = Math.min(transferAmount,
                    ((SonicItem) higherStack.getItem()).getCurrentFuel(higherStack));

            ((SonicItem) higherStack.getItem()).removeFuel(actualRemoved, higherStack);
            ((SonicItem) lowerStack.getItem()).addFuel(actualRemoved, lowerStack);

            playSparkEffect(world, lowerFuelPlayer);

            Vec3 pushDir = lowerFuelPlayer.position()
                    .subtract(higherFuelPlayer.position()).normalize().scale(0.5);
            lowerFuelPlayer.push(pushDir.x, 0.1, pushDir.z);
            higherFuelPlayer.push(-pushDir.x, 0.1, -pushDir.z);

            lowerFuelPlayer.hurt(world.damageSources().magic(), 1.0F);
            higherFuelPlayer.hurt(world.damageSources().magic(), 1.0F);

            player.getCooldowns().addCooldown(userStack.getItem(), 60);
            other.getCooldowns().addCooldown(otherStack.getItem(), 500);

        }
    }

    private void overloadBlock(BlockPos pos, ServerLevel world, LivingEntity user, int ticks, BlockHitResult blockHit) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!state.is(AITTags.Blocks.SONIC_INTERACTABLE) || !canMakeRedstoneTweak(ticks)) return;

        if (world.getBlockEntity(pos) instanceof AbstractLinkableBlockEntity ext && ext.isLinked()) {
            ServerTardis tardis = ext.tardis().get().asServer();
            if (tardis.loyalty().get((Player) user).smallerThan(Loyalty.fromLevel(Loyalty.Type.COMPANION.level))) return;

            getExpectedControl(tardis).handleRun(tardis, (ServerPlayer) user, world, pos, false);
            playFx(world, pos);
            return;
        }

        if (block instanceof DaylightDetectorBlock) {
            activateBlock(world, pos, user, state, blockHit);
        }
        else if (block instanceof RedstoneLampBlock) {
            world.setBlockAndUpdate(pos, state.cycle(BlockStateProperties.LIT));
        }
        else if (block instanceof RedStoneWireBlock || block instanceof DiodeBlock) {
            forceRedstonePower(world, pos, state, 5 * 20);
        }
        else if (block instanceof LeverBlock lever) {
            lever.pull(state, world, pos);
        }
        else if (block instanceof TransparentBlock || block instanceof IronBarsBlock) {
            breakBlock(world, pos, user, state, blockHit);
        }
        else if (canLight(ticks) && block instanceof TntBlock) {
            TntBlock.explode(world, pos);
            world.removeBlock(pos, false);
            world.gameEvent(user, GameEvent.BLOCK_DESTROY, pos);
        }
        else if (state.getBlock() instanceof AbstractCandleBlock) {
            world.setBlock(pos, state.setValue(AbstractCandleBlock.LIT, true), Block.UPDATE_ALL);
            world.gameEvent(user, GameEvent.BLOCK_CHANGE, pos);
        }
        else if (state.is(Blocks.OBSIDIAN)) {
            BlockPos blockPos2 = pos.relative(blockHit.getDirection());
            if (BaseFireBlock.canBePlacedAt(world, blockPos2, user.getDirection())) {
                playFx(world, blockPos2);
                world.setBlock(blockPos2, BaseFireBlock.getState(world, blockPos2), Block.UPDATE_ALL);
                world.gameEvent(user, GameEvent.BLOCK_PLACE, pos);
            }
        } else if(state.is(Blocks.BRICKS)){
            breakBlock(world, pos, user, state, blockHit);
        }
        playFx(world, pos);
    }

    private void playSparkEffect(ServerLevel world, Player player) {
        Vec3 pos = player.position().add(0, player.getBbHeight() / 2.0, 0);
        world.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.x, pos.y, pos.z, 10, 0.2, 0.5, 0.2, 0.05);
        world.playSound(null, player.blockPosition(), AITSounds.SONIC_TWEAK, SoundSource.PLAYERS, 1.0f, 1.5f);
    }

    private static Control getExpectedControl(ServerTardis tardis) {
        TravelHandler travel = tardis.travel();
        return (travel.getState() == TravelHandlerBase.State.DEMAT || tardis.subsystems().engine().phaser().isPhasing())
                ? new HandBrakeControl()
                : new HADSControl();
    }

    private void activateBlock(ServerLevel world, BlockPos pos, LivingEntity user, BlockState state, BlockHitResult hit) {
        state.use(world, (Player) user, user.getUsedItemHand(), hit);
        playFx(world, pos);
    }

    private void breakBlock(ServerLevel world, BlockPos pos, LivingEntity user, BlockState state, BlockHitResult hit) {
        world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundSource.BLOCKS, 1.0F, 1.0F);
        world.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, state),
                pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                10, 0.5, 0.5, 0.5, 0.1);
        world.destroyBlock(pos, false);
    }

    private void forceRedstonePower(ServerLevel world, BlockPos pos, BlockState state, int durationTicks) {
        playFx(world, pos);
        world.blockUpdated(pos, state.getBlock());
    }

    private void playFx(ServerLevel world, BlockPos pos) {
        world.playSound(null, pos, AITSounds.SONIC_TWEAK, SoundSource.BLOCKS, 1.0f, 1.0f);
        spawnParticles(world, pos);
    }

    private void spawnParticles(ServerLevel world, BlockPos pos) {
        world.sendParticles(ParticleTypes.ELECTRIC_SPARK, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 5, 0.2, 0.2, 0.2, 0.01);
        world.sendParticles(ParticleTypes.LAVA, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 3, 0.1, 0.1, 0.1, 0.01);
    }

    private static boolean canLight(int ticks) {
        return ticks >= 10;
    }

    private static boolean canMakeRedstoneTweak(int ticks) {
        return ticks >= 10;
    }

    @Override
    public int maxTime() {
        return 5 * 60 * 20; // 5 minutes
    }

    @Override
    public ResourceLocation model(SonicSchema.Models models) {
        return models.overload();
    }

    @Override
    public int fuelCost() {
        return 2;
    }
}
