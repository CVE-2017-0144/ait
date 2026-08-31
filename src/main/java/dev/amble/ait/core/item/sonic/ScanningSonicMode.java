package dev.amble.ait.core.item.sonic;

import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.entities.RiftEntity;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.MonitorUtil;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.LandingPadManager;
import dev.amble.ait.core.world.RiftChunkManager;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.landing.LandingPadRegion;
import dev.amble.ait.data.landing.LandingPadSpot;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import dev.amble.lib.api.ICantBreak;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ScanningSonicMode extends SonicMode {
    private static final Component RIFT_FOUND = Component.translatable("message.ait.sonic.riftfound").withStyle(ChatFormatting.AQUA)
            .withStyle(ChatFormatting.BOLD);
    private static final Component RIFT_NOT_FOUND = Component.translatable("message.ait.sonic.riftnotfound").withStyle(ChatFormatting.AQUA)
            .withStyle(ChatFormatting.BOLD);

    protected ScanningSonicMode(int index) {
        super(index);
    }

    @Override
    public Component text() {
        return Component.translatable("sonic.ait.mode.scanning").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
    }

    @Override
    public int maxTime() {
        return 5 * 60 * 20;
    }

    @Override
    public void tick(ItemStack stack, Level world, LivingEntity user, int ticks, int ticksLeft) {
        if (!(world instanceof ServerLevel serverWorld) || !(user instanceof Player player) || ticks % 10 != 0)
            return;

        this.process(stack, world, player);
    }



    public boolean process(ItemStack stack, Level world, Player user) {
        HitResult hitResult = SonicMode.getHitResult(user);

        boolean isMainHand = user.getMainHandItem().getItem() == stack.getItem();

        if (isMainHand) {
            SonicMode.checkSonicWoodAdvancementConditions(world, user, hitResult);

            if (hitResult instanceof BlockHitResult blockHit && !world.getBlockState(blockHit.getBlockPos()).isAir()) {
                return this.scanBlocks(stack, world, user, blockHit.getBlockPos());
            }

            if (hitResult instanceof EntityHitResult entityHit && !(entityHit.getEntity() instanceof RiftEntity)) {
                return this.scanEntities(stack, world, user, entityHit.getEntity());
            }
        }

        return this.scanRegion(stack, world, user, BlockPos.containing(hitResult.getLocation()));
    }



    public boolean scanBlocks(ItemStack stack, Level world, Player user, BlockPos pos) {
        if (world.isClientSide() || user == null)
            return true;

        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        Tardis tardis = SonicItem.getTardisStatic(world, stack);

        String blastRes = String.format("%.2f", block.getExplosionResistance());

        if (tardis != null && state.is(AITTags.Blocks.SONIC_CAN_LOCATE)) {
            BlockPos tPos = tardis.travel().position().getPos();
            Level tardisWorld = tardis.travel().position().getWorld();
            String dimensionText = MonitorUtil.truncateDimensionName(WorldUtil.worldText(tardisWorld.dimension()).getString(), 20);

            Component coordinatesMessage = Component.translatable("item.sonic.scanning.locator_message.coordinates", tPos.getX(), tPos.getY(), tPos.getZ());
            Component fullMessage = Component.translatable("item.sonic.scanning.locator_message.title", dimensionText).append("\n").append(coordinatesMessage);

            // Output looks like:
            // TARDIS Location: {DIMENSION}
            // Coordinates: {X} {Y} {Z}
            user.sendSystemMessage(fullMessage);
        }

        LandingPadRegion region = LandingPadManager.getInstance((ServerLevel) world).getRegionAt(pos);
        if (region != null) {
            if (world.getBlockState(pos).isAir()) return true;

            boolean wasSpotCreated = modifyRegion(null, (ServerLevel) world, pos.above(), user, stack, region);

            float pitch = wasSpotCreated ? 1.1f : 0.75f;
            world.playSound(null, pos, AITSounds.SONIC_SWITCH, SoundSource.PLAYERS, 1f, pitch);

            return true;
        }

        Component toolRequirement;
        if (block instanceof ICantBreak) {
            toolRequirement = Component.translatable("item.sonic.scanning.cant_break");
        } else if (!state.requiresCorrectToolForDrops()) {
            toolRequirement = Component.translatable("item.sonic.scanning.no_tool");
        } else {
            MutableComponent toolType = toolTypeText(state);
            MutableComponent tier = tierText(state);
            toolRequirement = tier != null ? tier.append(" ").append(toolType) : toolType;
        }

        Component message = Component.literal("\uD83D\uDD25: " + blastRes + " ⛏: ").append(toolRequirement).withStyle(ChatFormatting.YELLOW)
                .withStyle(ChatFormatting.GOLD);
        user.displayClientMessage(message, true);

        return true;
    }



    /** The tool class needed to mine the block (pickaxe/axe/shovel/hoe), or "any tool" if untagged. */
    private static MutableComponent toolTypeText(BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE))
            return Component.translatable("item.sonic.scanning.tool.pickaxe");
        if (state.is(BlockTags.MINEABLE_WITH_AXE))
            return Component.translatable("item.sonic.scanning.tool.axe");
        if (state.is(BlockTags.MINEABLE_WITH_SHOVEL))
            return Component.translatable("item.sonic.scanning.tool.shovel");
        if (state.is(BlockTags.MINEABLE_WITH_HOE))
            return Component.translatable("item.sonic.scanning.tool.hoe");

        return Component.translatable("item.sonic.scanning.any_tool");
    }

    /** The minimum material tier the block requires (diamond/iron/stone), or null if none. */
    private static MutableComponent tierText(BlockState state) {
        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL))
            return Component.translatable("item.sonic.scanning.tier.diamond");
        if (state.is(BlockTags.NEEDS_IRON_TOOL))
            return Component.translatable("item.sonic.scanning.tier.iron");
        if (state.is(BlockTags.NEEDS_STONE_TOOL))
            return Component.translatable("item.sonic.scanning.tier.stone");

        return null;
    }

    public boolean scanRegion(ItemStack stack, Level world, Player user, BlockPos pos) {
        if (world.isClientSide())
            return true;

        if (user == null)
            return false;

        if (!TardisServerWorld.isTardisDimension(world)) {
            sendRiftInfo(null, (ServerLevel) world, pos, user, stack);
            return true;
        }

        Tardis tardis = SonicItem.getTardisStatic(world, stack);

        if (tardis == null)
            return false;

        if (TardisServerWorld.isTardisDimension(world)) {
            sendTardisInfo(tardis, (ServerLevel) world, pos, user, stack);
            return true;
        }

        return false;
    }

    public boolean scanEntities(ItemStack stack, Level world, Player user, Entity entity) {
        if (world.isClientSide())
            return true;

        if (user == null)
            return false;

        if (entity instanceof LivingEntity) {
            String health = String.valueOf(((LivingEntity) entity).getHealth());
            String maxhealth = String.valueOf(((LivingEntity) entity).getMaxHealth());
            user.displayClientMessage(Component.literal("♥:").append(health).append("/").append(maxhealth).withStyle(ChatFormatting.YELLOW), true);
        }

        return false;
    }

    private static boolean modifyRegion(Tardis tardis, ServerLevel world, BlockPos pos, Player player, ItemStack stack, LandingPadRegion region) {
        LandingPadSpot spot = region.getSpotAt(pos).orElse(null);

        if (spot == null) {
            addSpot(region, pos);

            syncRegion(world, pos);
            return true;
        }

        removeSpot(region, pos);
        syncRegion(world, pos);

        return false;
    }
    private static void addSpot(LandingPadRegion region, BlockPos pos) {
        region.createSpotAt(pos);
    }
    private static void removeSpot(LandingPadRegion region, BlockPos pos) {
        region.removeSpotAt(pos);
    }
    private static void syncRegion(ServerLevel world, BlockPos pos) {
        LandingPadManager.Network.syncTracked(LandingPadManager.Network.Action.ADD, world, new ChunkPos(pos));
    }

    private static void sendRiftInfo(Tardis tardis, ServerLevel world, BlockPos pos, Player player, ItemStack stack) {
        boolean isRift = RiftChunkManager.isRiftChunk(world, pos);

        player.displayClientMessage(isRift ? RIFT_FOUND : RIFT_NOT_FOUND, true);

        if (!isRift) return;

        int artronValue = (int) RiftChunkManager.getInstance(world).getArtron(new ChunkPos(pos));
        player.sendSystemMessage(
                Component.translatable("message.ait.artron_units", artronValue)
                        .withStyle(ChatFormatting.GOLD)
        );
    }
    private static void sendTardisInfo(Tardis tardis, ServerLevel world, BlockPos pos, Player player, ItemStack stack) {
        if (tardis == null)
            return;

        if (tardis.crash().isUnstable() || tardis.crash().isToxic()) {
            player.displayClientMessage(Component.translatable("message.ait.sonic.repairtime", tardis.crash().getRepairTicks())
                    .withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC), true);
            return;
        }

        player.displayClientMessage(
                Component.translatable("message.ait.artron_units", tardis.fuel().getCurrentFuel()).withStyle(ChatFormatting.GOLD), true);
    }

    @Override
    public ResourceLocation model(SonicSchema.Models models) {
        return models.scanning();
    }
}
