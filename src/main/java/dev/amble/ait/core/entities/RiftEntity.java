package dev.amble.ait.core.entities;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.*;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.entities.base.DummyAmbientEntity;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.util.StackUtil;
import dev.amble.ait.core.util.TagsUtil;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.module.planet.core.util.ISpaceImmune;
import dev.amble.lib.util.TeleportUtil;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.Heightmap;

public class RiftEntity extends DummyAmbientEntity implements ISpaceImmune {
    private int interactAmount = 0;
    private int ambientSoundCooldown = 0;
    private int currentSoundIndex = 0;

    private static final SoundEvent[] RIFT_SOUNDS = {
            AITSounds.DRUMS,
    };

    @Override
    public boolean checkSpawnRules(LevelAccessor world, MobSpawnType spawnReason) {
        return super.checkSpawnRules(world, spawnReason);
    }

    private static final int[] RIFT_DURATIONS = {
            20,
    };

    public RiftEntity(EntityType<RiftEntity> type, Level world) {
        super(type, world);
    }

    public RiftEntity(Level world) {
        this(AITEntityTypes.RIFT_ENTITY, world);
    }

    @Override
    public void playerTouch(Player player) {
        if (player.getBoundingBox().intersects(this.getBoundingBox().contract(0.5f, 0.5f, 0.5f))) {
            if (WorldUtil.getTimeVortex() == null) return;
            TeleportUtil.teleport(player, WorldUtil.getTimeVortex(), player.position(), player.yBodyRot);
        }
    }

    @Override
    public final InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (this.level().isClientSide()) return InteractionResult.SUCCESS;

        ItemStack stack = player.getItemInHand(hand);

        if (stack.getItem() instanceof SonicItem sonic) {
            if (!this.level().isClientSide()) {
                sonic.addFuel(1000, stack);
                this.level().playSound(null, this.blockPosition(), AITSounds.RIFT_SONIC, SoundSource.AMBIENT, 1f, 1f);
                StackUtil.spawn(this.level(), this.blockPosition(), new ItemStack(AITItems.CORAL_FRAGMENT));
                this.discard();
            }
            return InteractionResult.SUCCESS;

        }
        interactAmount += 1;

        if (interactAmount == 1) {
            TardisCriterions.FIRST_RIFT.trigger((ServerPlayer) player);
        }

        if (interactAmount >= 3) {
            boolean gotFragment = this.level().getRandom().nextBoolean();

            player.hurt(this.level().damageSources().hotFloor(), 7);
            if (gotFragment) {

                Item randomItem = TagsUtil.getRandomItemFromTag(
                        this.level(),
                        AITTags.Items.RIFT_SUCCESS_EXTRA_ITEM
                );

                // Since we don't really wanna have to use 3 billion charged zeiton crystals, just spawn more coral fragments. - Loqor
                ItemStack coralFragments = new ItemStack(AITItems.CORAL_FRAGMENT);

                coralFragments.setCount(this.level().random.nextIntBetweenInclusive(3, 8));
                StackUtil.spawn(this.level(), this.blockPosition(), coralFragments);

                StackUtil.spawn(this.level(), this.blockPosition(), new ItemStack(randomItem));
                this.level().playSound(null, player.blockPosition(), AITSounds.RIFT_SUCCESS, SoundSource.AMBIENT, 1f, 1f);
            } else {
                Item randomItem = TagsUtil.getRandomItemFromTag(
                        this.level(),
                        AITTags.Items.RIFT_FAIL_ITEM
                );

                StackUtil.spawn(this.level(), this.blockPosition(), new ItemStack(randomItem));
                this.level().playSound(null, this.blockPosition(), AITSounds.RIFT_FAIL, SoundSource.AMBIENT, 1f, 1f);
                spreadTardisCoral(this.level(), this.blockPosition());
            }

            this.discard();

            return gotFragment ? InteractionResult.SUCCESS : InteractionResult.FAIL;
        }

        return InteractionResult.CONSUME;
    }

    private void spreadTardisCoral(Level world, BlockPos pos) {
        int radius = 4;

        ChunkAccess chunk = world.getChunk(pos);
        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-radius, 0, -radius), pos.offset(radius, 0, radius))) {
            if (world.random.nextIntBetweenInclusive(0, 10) < 3) { // 30% chance per block
                targetPos = targetPos.atY(chunk.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                        targetPos.getX() & 15, targetPos.getZ() & 15));

                BlockState currentState = world.getBlockState(targetPos);
                BlockState newState = getReplacementBlock(currentState);
                if (newState != null) {
                    world.setBlock(targetPos, newState, Block.UPDATE_ALL);

                    world.addParticle(AITMod.CORAL_PARTICLE,
                            targetPos.getX() + 0.5, targetPos.getY() + 0.5, targetPos.getZ() + 0.5,
                            0, 0, 0);

                    if (newState.is(AITBlocks.TARDIS_CORAL_BLOCK)) {
                        placeCoralFans(world, targetPos);
                    }
                }
            }
        }
    }

    private BlockState getReplacementBlock(BlockState currentState) {
        Block block = currentState.getBlock();

        if (block instanceof SlabBlock) return AITBlocks.TARDIS_CORAL_SLAB.defaultBlockState()
                .setValue(BlockStateProperties.SLAB_TYPE, currentState.getValue(BlockStateProperties.SLAB_TYPE));

        if (block instanceof StairBlock) return AITBlocks.TARDIS_CORAL_STAIRS.defaultBlockState()
                .setValue(BlockStateProperties.HORIZONTAL_FACING, currentState.getValue(BlockStateProperties.HORIZONTAL_FACING))
                .setValue(BlockStateProperties.SLAB_TYPE, currentState.getValue(BlockStateProperties.SLAB_TYPE))
                .setValue(BlockStateProperties.STAIRS_SHAPE, currentState.getValue(BlockStateProperties.STAIRS_SHAPE));


        if (canTransform(block)) return AITBlocks.TARDIS_CORAL_BLOCK.defaultBlockState();

        return null;
    }

    private boolean canTransform(Block block) {
        return block == Blocks.STONE || block == Blocks.DIRT || block == Blocks.GRASS_BLOCK ||
                block == Blocks.SAND || block == Blocks.DEEPSLATE;
    }

    private void placeCoralFans(Level world, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            BlockPos adjacent = pos.relative(dir);
            if (world.getBlockState(adjacent).isAir() && isCoralBlock(world.getBlockState(pos))) {
                world.setBlock(adjacent, AITBlocks.TARDIS_CORAL_FAN.defaultBlockState()
                        .setValue(BlockStateProperties.WATERLOGGED,false)
                        .setValue(BlockStateProperties.FACING, dir), Block.UPDATE_ALL);
            }
        }
    }

    private boolean isCoralBlock(BlockState state) {
        return state.is(AITBlocks.TARDIS_CORAL_BLOCK);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (ambientSoundCooldown > 0) {
                ambientSoundCooldown--;
            } else {
                this.level().playSound(null, this.blockPosition(), RIFT_SOUNDS[currentSoundIndex], SoundSource.AMBIENT, 0.7f, 1.0f);
                ambientSoundCooldown = RIFT_DURATIONS[currentSoundIndex];
                currentSoundIndex = (currentSoundIndex + 1) % RIFT_SOUNDS.length;
            }
        }
    }
}
