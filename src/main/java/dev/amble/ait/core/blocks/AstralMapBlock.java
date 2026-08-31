package dev.amble.ait.core.blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.phys.BlockHitResult;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.screens.AstralMapScreen;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.blockentities.AstralMapBlockEntity;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.control.impl.TelepathicControl;
import dev.amble.ait.core.tardis.util.AsyncLocatorUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class AstralMapBlock extends BaseEntityBlock implements EntityBlock {
    public static final int MAX_ROTATION_INDEX = RotationSegment.getMaxSegmentIndex();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public static final ResourceLocation REQUEST_SEARCH = AITMod.id("c2s/request_search");
    public static final ResourceLocation OPEN_ASTRAL_MAP = AITMod.id("s2c/open_astral_map");
    // Store structure IDs on the client since they aren't synced by default
    public static List<ResourceLocation> structureIds;

    static {
        AitNetworking.registerServerReceiver(REQUEST_SEARCH, (server, player, handler, buf, responseSender) -> {
            try {
                ServerLevel checkWorld = player.serverLevel();
                BlockPos playerPos = player.blockPosition();
                boolean hasAccess = false;
                for (BlockPos nearby : BlockPos.withinManhattan(playerPos, 4, 4, 4)) {
                    if (checkWorld.getBlockState(nearby).getBlock() instanceof AstralMapBlock) {
                        hasAccess = true;
                        break;
                    }
                }
                if (!hasAccess) return;

                ResourceLocation target = buf.readResourceLocation();
                AstralMapScreen.Category category = buf.readEnum(AstralMapScreen.Category.class);

                switch(category) {
                    case BIOMES -> handleBiomeRequest(player, target);
                    case STRUCTURES -> handleStructureRequest(player, target);
                }
            } catch (Exception e) {
                AITMod.LOGGER.error("Error handling search request", e);
            }
        });
    }

    public AstralMapBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(ROTATION, 0));
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return AITBlockEntityTypes.ASTRAL_MAP.create(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                              BlockHitResult hit) {
        BlockEntity blockEntity = world.getBlockEntity(pos);

        if (blockEntity instanceof AstralMapBlockEntity && !world.isClientSide()) {
            ServerLevel serverWorld = (ServerLevel) world;
            ServerPlayer serverPlayer = (ServerPlayer) player;

            sendStructuresAndOpenScreen(serverWorld, serverPlayer);

            player.playSound(SoundEvents.UI_BUTTON_CLICK.value(), 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }

    private static Optional<Holder.Reference<Structure>> getStructure(ServerLevel world, ResourceLocation id) {
        Registry<Structure> registry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
        ResourceKey<Structure> key = ResourceKey.create(Registries.STRUCTURE, id);
        return registry.getHolder(key);
    }

    private static void handleStructureRequest(ServerPlayer player, ResourceLocation target) {
        player.displayClientMessage(Component.translatable("block.ait.astral_map.finder.searching_for_structure"), false);

        ServerLevel world = player.serverLevel();
        BlockPos pos = player.blockPosition();

        if (TardisServerWorld.isTardisDimension(world)) {
            ServerTardis tardis = ((TardisServerWorld) world).getTardis();
            var tPos = tardis.travel().position();
            world = tPos.getWorld();

            Holder.Reference<Structure> targetStructure = getStructure(world, target).orElse(null);
            if (targetStructure == null) {
                AITMod.LOGGER.error("Structure not found: {}", target);
                return;
            }

            pos = tPos.getPos();

            AsyncLocatorUtil.locate(world, HolderSet.direct(targetStructure), pos, TelepathicControl.RADIUS, false).thenOnServerThread(pPos -> {
                BlockPos newPos = pPos != null ? pPos.getFirst() : null;
                if (newPos != null) {
                    player.displayClientMessage(Component.translatable(
                            "block.ait.astral_map.finder.found", newPos.getX(), newPos.getY(), newPos.getZ(),
                            Math.round(Math.sqrt(newPos.distSqr(tPos.getPos())))), false);
                    tardis.travel().destination(destination -> destination.pos(newPos));
                } else {
                    player.displayClientMessage(Component.translatable("block.ait.astral_map.finder.structure_not_found"), false);
                }
            });
        }
    }

    private static void handleBiomeRequest(ServerPlayer player, ResourceLocation target) {
        player.displayClientMessage(Component.translatable("block.ait.astral_map.finder.searching_for_biome"), false);
        player.getServer().execute(() -> {
            ServerLevel world = player.serverLevel();
            if (!TardisServerWorld.isTardisDimension(world))
                return;

            ServerTardis tardis = ((TardisServerWorld) world).getTardis();
            CachedDirectedGlobalPos currentPos = tardis.travel().position();
            ServerLevel targetWorld = currentPos.getWorld();
            BlockPos start = currentPos.getPos();
            ResourceKey<Biome> biomeKey = ResourceKey.create(Registries.BIOME, target);

            Pair<BlockPos, Holder<Biome>> r = targetWorld.findClosestBiome3d(
                    entry -> entry.is(biomeKey),
                    start, AITMod.CONFIG.astralMapBiomeLocatorRange, 32, 64);

            if (r != null) {
                BlockPos locatedBiome = r.getFirst();
                int distance = (int) Math.round(Math.sqrt(locatedBiome.distSqr(start)));
                player.displayClientMessage(Component.translatable("block.ait.astral_map.finder.found",
                        locatedBiome.getX(), locatedBiome.getY(), locatedBiome.getZ(), distance), false);
                tardis.travel().destination(destination -> destination.pos(locatedBiome));
            } else {
                player.displayClientMessage(Component.translatable("block.ait.astral_map.finder.biome_not_found"), false);
            }
        });
    }

    private static void sendStructuresAndOpenScreen(ServerLevel world, ServerPlayer target) {
        if (structureIds == null || structureIds.isEmpty()) {
            Registry<Structure> registry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
            List<ResourceLocation> ids = new ArrayList<>(registry.size());
            for (Structure entry : registry) {
                ids.add(registry.getKey(entry));
            }
            structureIds = ids;
        }

        FriendlyByteBuf buf = AitNetworking.buf();
        buf.writeCollection(structureIds, FriendlyByteBuf::writeResourceLocation);
        AitNetworking.send(target, OPEN_ASTRAL_MAP, buf);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(ctx.getRotation()));
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), MAX_ROTATIONS));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }
}
