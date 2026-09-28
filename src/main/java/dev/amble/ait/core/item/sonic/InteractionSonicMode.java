package dev.amble.ait.core.item.sonic;

import dev.amble.ait.core.AITTags;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ComparatorBlock;
import net.minecraft.world.level.block.DaylightDetectorBlock;
import net.minecraft.world.level.block.RepeaterBlock;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class InteractionSonicMode extends SonicMode {

    protected InteractionSonicMode(int index) {
        super(index);
    }

    @Override
    public Component text() {
        return Component.translatable("sonic.ait.mode.interaction").withStyle(ChatFormatting.GREEN, ChatFormatting.BOLD);
    }

    @Override
    public void tick(ItemStack stack, Level world, LivingEntity user, int ticks, int ticksLeft) {
        if (!(world instanceof ServerLevel serverWorld) || ticks % 10 != 0)
            return;

        this.process(serverWorld, user, ticks);
    }

    private void process(ServerLevel world, LivingEntity user, int ticks) {
        HitResult hitResult = SonicMode.getHitResult(user);

        SonicMode.checkSonicWoodAdvancementConditions(world, user, hitResult);

        if (hitResult instanceof EntityHitResult entity && entity.getEntity() instanceof Sheep sheep) {
            this.shearSheep(sheep, world, user);
        } else if (hitResult instanceof BlockHitResult blockHit) {
            this.interactBlock(blockHit.getBlockPos(), world, user, ticks, blockHit);
        }
    }

    private void interactBlock(BlockPos pos, ServerLevel world, LivingEntity user, int ticks, BlockHitResult blockHit) {
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();

        if (!state.is(AITTags.Blocks.SONIC_INTERACTABLE)) return;
        if (!(user instanceof Player player) || !world.mayInteract(player, pos)) return;
        if (!player.mayBuild() && !(block instanceof ButtonBlock)) return;

        if (block == Blocks.IRON_DOOR && state.hasProperty(BlockStateProperties.OPEN)) {
            boolean isOpen = state.getValue(BlockStateProperties.OPEN);
            world.setBlock(pos, state.setValue(BlockStateProperties.OPEN, !isOpen), 3);
            world.gameEvent(user, GameEvent.BLOCK_ACTIVATE, pos);
            return;
        }

        if (block == Blocks.IRON_TRAPDOOR && state.hasProperty(BlockStateProperties.OPEN)) {
            boolean isOpen = state.getValue(BlockStateProperties.OPEN);
            world.setBlock(pos, state.setValue(BlockStateProperties.OPEN, !isOpen), 3);
            world.gameEvent(user, GameEvent.BLOCK_ACTIVATE, pos);
            return;
        }

        if (block instanceof RepeaterBlock && state.hasProperty(BlockStateProperties.DELAY)) {
            world.setBlock(pos, state.cycle(BlockStateProperties.DELAY), 3);
            world.gameEvent(user, GameEvent.BLOCK_CHANGE, pos);
            return;
        }

        if (block instanceof ComparatorBlock && state.hasProperty(BlockStateProperties.MODE_COMPARATOR)) {
            world.setBlock(pos, state.cycle(BlockStateProperties.MODE_COMPARATOR), 3);
            world.gameEvent(user, GameEvent.BLOCK_CHANGE, pos);
            return;
        }

        if (block instanceof DaylightDetectorBlock && state.hasProperty(BlockStateProperties.INVERTED)) {
            world.setBlock(pos, state.cycle(BlockStateProperties.INVERTED), 3);
            world.gameEvent(user, GameEvent.BLOCK_CHANGE, pos);
            return;
        }

        if (block instanceof ButtonBlock button) {
            state.useWithoutItem(world, player, blockHit);
            return;
        }
    }

    private void shearSheep(Sheep sheep, ServerLevel world, LivingEntity user) {
        if (!sheep.readyForShearing()) return;

        sheep.shear(SoundSource.PLAYERS);
        world.gameEvent(user, GameEvent.SHEAR, sheep.blockPosition());
    }

    @Override
    public int maxTime() {
        return 5 * 60 * 20; // 5 minutes
    }

    @Override
    public ResourceLocation model(SonicSchema.Models models) {
        return models.interaction();
    }
}
