package dev.amble.ait.core.blocks;

import static dev.amble.ait.client.util.TooltipUtil.addShiftHiddenTooltip;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.blockentities.CoralBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.LoyaltyHandler;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.manager.TardisBuilder;
import dev.amble.ait.core.world.RiftChunkManager;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.exterior.variant.growth.CoralGrowthVariant;
import dev.amble.ait.registry.impl.DesktopRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;

@SuppressWarnings("deprecation")
public class CoralPlantBlock extends HorizontalDirectionalBlock implements EntityBlock {
    private final VoxelShape DEFAULT = Block.box(0.0, 0.0, 0.0, 16.0, 32.0, 16.0);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public CoralPlantBlock(Properties settings) {
        super(settings);

        this.registerDefaultState(
                this.defaultBlockState().setValue(AGE, 0)
        );
    }

    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return 7;
    }

    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public final boolean isMature(BlockState blockState) {
        return this.getAge(blockState) >= this.getMaxAge();
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        Vec3 centre = pos.above().getCenter();
        for (int i = 0; i < getAge(state); i++) {
            double offsetX = AITMod.RANDOM.nextGaussian() * getAge(state) * 0.01f;
            double offsetY = AITMod.RANDOM.nextGaussian() * getAge(state) * 0.01f;
            double offsetZ = AITMod.RANDOM.nextGaussian() * getAge(state) * 0.01f;
            world.addParticle(AITMod.CORAL_PARTICLE, centre.x(), centre.y() , centre.z(), offsetX, offsetY, offsetZ);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (world.getRawBrightness(pos, 0) >= 4) {
            int i = this.getAge(state);
            if (i < this.getMaxAge()) {
                if (!(world.getBlockState(pos.below()).getBlock() instanceof SoulSandBlock)) {
                    world.destroyBlock(pos, true);
                    return;
                }

                world.setBlock(pos, state.setValue(AGE, i + 1), 2);
            }
        }

        tryCreate(world, pos, state);
    }

    private boolean tryCreate(ServerLevel world, BlockPos pos, BlockState state) {
        if (!this.isMature(state))
            return false;

        if (TardisServerWorld.isTardisDimension(world)) {
            this.createConsole(world, pos);
            return true;
        }

        if (world.getBlockEntity(pos) instanceof CoralBlockEntity coral)
            this.createTardis(world, pos, coral.creator, state);

        return true;
    }

    private void createConsole(ServerLevel world, BlockPos pos) {
        world.playSound(null, pos, AITSounds.FABRICATOR_END, SoundSource.BLOCKS);

        world.setBlockAndUpdate(pos, AITBlocks.CONSOLE.defaultBlockState());
    }

    private void createTardis(ServerLevel world, BlockPos pos, UUID creatorId, BlockState state) {
        if (!(world.getPlayerByUUID(creatorId) instanceof ServerPlayer player))
            return;

        TardisBuilder builder = new TardisBuilder().at(CachedDirectedGlobalPos.create(world, pos,
                        CachedDirectedGlobalPos.getGeneralizedRotation(state.getValue(FACING))))
                .owner(player)
                .<FuelHandler>with(TardisComponent.Id.FUEL, fuel -> fuel.setCurrentFuel(5000))
                .<LoyaltyHandler>with(TardisComponent.Id.LOYALTY, loyaltyHandler -> loyaltyHandler.set(player, new Loyalty(Loyalty.Type.NEUTRAL)))
                .with(TardisComponent.Id.TRAVEL, travel -> travel.tardis().travel().autopilot(false))
                .exterior(ExteriorVariantRegistry.getInstance().get(CoralGrowthVariant.REFERENCE))
                .desktop(DesktopRegistry.DEFAULT_CAVE);

        ServerTardis created = ServerTardisManager.getInstance()
                .create(builder);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
            ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);

        if (!(placer instanceof ServerPlayer player))
            return;

        if (!RiftChunkManager.isRiftChunk((ServerLevel) world, pos) && !TardisServerWorld.isTardisDimension((ServerLevel) world)) {
            world.destroyBlock(pos, !placer.isAlwaysTicking() || !player.isCreative());
            player.displayClientMessage(Component.translatable("ait.tooltip.coral_riftchunk").withStyle(ChatFormatting.RED), true);
            return;
        }

        if (!(world.getBlockState(pos.below()).getBlock() instanceof SoulSandBlock)) {
            world.destroyBlock(pos, !placer.isAlwaysTicking() || !player.isCreative());
            player.displayClientMessage(Component.translatable("ait.tooltip.coral_soulsand").withStyle(ChatFormatting.RED), true);
            return;
        }

        if (world.getBlockEntity(pos) instanceof CoralBlockEntity coral) {
            if (player.getUUID() != null) {
                coral.creator = player.getUUID();
                coral.setChanged();
            }
            TardisCriterions.PLACE_CORAL.trigger(player);
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return (world.getRawBrightness(pos, 0) >= 4 || world.canSeeSky(pos)) && super.canSurvive(state, world, pos);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof Ravager && world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            world.destroyBlock(pos, true, entity);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return DEFAULT;
    }

    @Override
    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return DEFAULT;
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        return AITBlocks.CORAL_PLANT.asItem().getDefaultInstance();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE).add(FACING);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
        super.appendHoverText(stack, world, tooltip, options);

        addShiftHiddenTooltip(stack, tooltip, tooltips -> {
            tooltip.add(Component.translatable("tooltip.ait.tardis_coral").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
        });
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CoralBlockEntity(pos, state);
    }
}
