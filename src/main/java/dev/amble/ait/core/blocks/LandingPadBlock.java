package dev.amble.ait.core.blocks;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.client.screens.LandingPadScreen;
import dev.amble.ait.core.world.LandingPadManager;
import dev.amble.ait.data.landing.LandingPadRegion;
import dev.amble.ait.data.landing.LandingPadSpot;

public class LandingPadBlock extends Block {
    private static final BooleanProperty ACTIVE = BooleanProperty.create("active"); // whether this block created a region

    public LandingPadBlock(BlockBehaviour.Properties settings) {
        super(settings);

        this.registerDefaultState(
                this.getStateDefinition().any().setValue(ACTIVE, false)
        );
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);

        builder.add(ACTIVE);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        Vec3 centre = pos.above().getCenter();
        world.addParticle(ParticleTypes.GLOW, centre.x(), centre.y() - 0.5, centre.z(), 0.0, 0.0, 0.0);

        // I hate this its so annoying </3
        //if (random.nextDouble() < 0.2f)
        //    world.playSound(centre.getX(), centre.getY(), centre.getZ(), SoundEvents.BLOCK_BELL_RESONATE, SoundCategory.BLOCKS, 0.1f, 1f, true);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            openScreen(pos);
            return super.useWithoutItem(state, world, pos, player, hit);
        }

        return InteractionResult.CONSUME;
    }

    @OnlyIn(Dist.CLIENT)
    private static void openScreen(BlockPos pos) {
        Minecraft.getInstance().setScreen(new LandingPadScreen(pos));
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        if (!(world instanceof ServerLevel serverWorld))
            return;

        LandingPadManager manager = LandingPadManager.getInstance(serverWorld);

        if (manager.getRegionAt(pos) != null) {
            world.destroyBlock(pos, true);
            return;
        }

        world.setBlockAndUpdate(pos, state.setValue(ACTIVE, true));
        manager.claim(pos);

        LandingPadRegion region = LandingPadManager.getInstance((ServerLevel) world).getRegionAt(pos);
        if (region != null) {
            for(LandingPadSpot spot : region.getSpots()) {
                spot.setPos(new BlockPos(spot.getPos().getX(), world.getChunk(SectionPos.blockToSectionCoord(spot.getPos().getX()), SectionPos.blockToSectionCoord(spot.getPos().getZ()))
                        .getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, spot.getPos().getX() & 15, spot.getPos().getZ() & 15) + 1, spot.getPos().getZ()));
                LandingPadManager.Network.syncTracked(LandingPadManager.Network.Action.ADD, (ServerLevel) world, new ChunkPos(pos));
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        super.onRemove(state, world, pos, newState, moved);

        if (!(world instanceof ServerLevel serverWorld))
            return;

        if (!state.getValue(ACTIVE)) return;

        LandingPadManager.getInstance(serverWorld).releaseAt(pos);
    }
}
