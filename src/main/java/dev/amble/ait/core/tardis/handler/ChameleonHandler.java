package dev.amble.ait.core.tardis.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.AITBlocks;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.util.NetworkUtil;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.schema.exterior.variant.adaptive.AdaptiveVariant;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.drtheo.gaslighter.Gaslighter3000;
import dev.drtheo.gaslighter.api.FakeBlockEvents;
import dev.drtheo.gaslighter.impl.FakeStructureWorldAccess;
import org.jetbrains.annotations.NotNull;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.ChorusPlantFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.DesertWellFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.HugeFungusFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.Vec3;

public class ChameleonHandler extends KeyedTardisComponent {

    @Exclude
    private Gaslighter3000 gaslighter;

    static {
        TardisEvents.ENTER_FLIGHT.register(tardis -> {
            tardis.chameleon().clearDisguise();
        });

        TardisEvents.START_FALLING.register(tardis -> {
            tardis.chameleon().clearDisguise();
        });

        TardisEvents.TOGGLE_SIEGE.register((tardis, active) -> {
            if (shouldNotBeDisguised(tardis)) {
                tardis.chameleon().clearDisguise();
            } else {
                tardis.chameleon().applyDisguise();
            }
        });

        TardisEvents.LANDED.register(tardis -> {
            if (!shouldNotBeDisguised(tardis))
                tardis.chameleon().applyDisguise();
        });

        TardisEvents.SEND_TARDIS.register((tardis, player) -> {
            if (player.isChangingDimension())
                return;

            if (shouldNotBeDisguised(tardis))
                return;

            CachedDirectedGlobalPos pos = tardis.travel().position();

            if (pos == null || pos.getWorld() != player.serverLevel())
                return;

            tardis.chameleon().applyDisguise(player);
        });

        TardisEvents.EXTERIOR_CHANGE.register(tardis -> {
            if (shouldNotBeDisguised(tardis)) {
                tardis.chameleon().clearDisguise();
            } else {
                tardis.chameleon().applyDisguise();
            }
        });

        TardisEvents.DOOR_USED.register((tardis,  player) -> {
            if (player == null || !isDisguised(tardis))
                return DoorHandler.InteractionResult.CONTINUE;

            if (!shouldNotBeDisguised(tardis)) {
                tardis.chameleon().applyDisguise(player);
                return DoorHandler.InteractionResult.CONTINUE;
            }

            CachedDirectedGlobalPos cached = tardis.travel().position();
            Optional<ExteriorBlockEntity> blockEntity = tardis.getExterior().findExteriorBlock();

            if (blockEntity.isEmpty())
                return DoorHandler.InteractionResult.CONTINUE;

            player.connection.send(new ClientboundBlockUpdatePacket(cached.getWorld(), cached.getPos()));
            player.connection.send(new ClientboundBlockUpdatePacket(cached.getWorld(), cached.getPos().above()));
            player.connection.send(ClientboundBlockEntityDataPacket.create(blockEntity.get()));

            return DoorHandler.InteractionResult.CONTINUE;
        });

        FakeBlockEvents.INTERACT.register((player, hand, pos) -> {
            // allow only main hand clicks!
            if (hand != InteractionHand.MAIN_HAND)
                return FakeBlockEvents.Action.REMOVE;

            return FakeBlockEvents.Action.CONTINUE;
        });

        FakeBlockEvents.CHECK.register((player, hand, state, pos) -> {
            if (state.is(AITBlocks.EXTERIOR_BLOCK))
                return FakeBlockEvents.Action.CONTINUE;

            ServerLevel world = player.serverLevel();

            // should be cheap enough
            if (hand == InteractionHand.MAIN_HAND && world.getBlockEntity(pos.below()) instanceof ExteriorBlockEntity ebe) {
                ebe.useOn(world, player.isShiftKeyDown(), player);
                return FakeBlockEvents.Action.CONTINUE;
            }

            shitParticles(world, pos);
            return FakeBlockEvents.Action.REMOVE;
        });

        FakeBlockEvents.PLACED.register((world, state, pos) -> shitParticles(world, pos));
        FakeBlockEvents.REMOVED.register(ChameleonHandler::shitParticles);
    }

    private ResourceLocation lastFeature = null;

    public ChameleonHandler() {
        super(Id.CHAMELEON);
    }

    @Override
    public void postInit(InitContext ctx) {
        if (ctx.created() || !this.isServer()) return;

        if (lastFeature == null)
            return;

        CachedDirectedGlobalPos cached = tardis.travel().position();

        BlockPos pos = cached.getPos();
        ServerLevel world = cached.getWorld();

        Optional<Holder.Reference<ConfiguredFeature<?, ?>>> feature =
                getRegistry(world).getHolder(asFeature(lastFeature));

        if (feature.isEmpty())
            return;

        this.gaslighter = new Gaslighter3000(world);
        if (!this.generate(world, pos, feature.get()) && !this.applyFallback(world, pos))
            return;

        if (!this.tryFixDisguise(world, pos))
            return;

        this.applyDisguise();
    }

    private static boolean shouldNotBeDisguised(Tardis tardis) {
        return !isDisguised(tardis) || !tardis.travel().isLanded()
                || tardis.siege().isActive() || tardis.door().isOpen()
                || tardis.flight().falling().get()
                || (tardis.travel().antigravs().get() && tardis.flight().shouldFall().get());
    }

    public static boolean isDisguised(Tardis tardis) {
        return tardis.getExterior().getVariant() instanceof AdaptiveVariant;
    }

    public void clearDisguise() {
        if (this.gaslighter == null)
            return;

        gaslighter.touchGrass();
        gaslighter.tweet();

        this.gaslighter = null;
        this.lastFeature = null;
    }

    /**
     * @return Whether the recalculation was successful
     */
    public boolean recalcDisguise() {
        if (this.gaslighter != null)
            return true;

        long start = System.currentTimeMillis();
        CachedDirectedGlobalPos cached = tardis.travel().position();
        ServerLevel world = cached.getWorld();
        BlockPos pos = cached.getPos();

        this.gaslighter = new Gaslighter3000(world);
        boolean success = this.testBiome(world, pos);

        if (!success && !applyFallback(world, pos))
            return false;

        boolean result = this.tryFixDisguise(world, pos);
        AITMod.LOGGER.debug("Recalculated exterior in {}ms", System.currentTimeMillis() - start);

        return result;
    }

    private boolean tryFixDisguise(ServerLevel world, BlockPos pos) {
        // check if the exterior's position is still an exterior
        if (!this.gaslighter.getAgenda(pos).is(AITBlocks.EXTERIOR_BLOCK))
            return true;

        // if it is, then try applying fallback
        if (!this.applyFallback(world, pos)) {
            this.gaslighter = null;
            return false;
        }

        return true;
    }

    private boolean applyFallback(ServerLevel world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.below());

        if (!isSafe(below)) {
            below = world.getBlockState(pos.below(2));

            if (!isSafe(below)) {
                this.notifyFailure();
                return false;
            }
        }

        this.gaslighter.spreadLies(pos, below);
        return true;
    }

    private void notifyFailure() {
        Component text = Component.translatable("tardis.message.chameleon.failed")
                .withStyle(ChatFormatting.RED);

        NetworkUtil.getSubscribedPlayers(tardis.asServer()).forEach(player ->
                player.displayClientMessage(text, true));
    }

    private void applyDisguise(ServerPlayer player) {
        if (!this.recalcDisguise())
            return;

        this.gaslighter.tweet(player);
    }

    public void applyDisguise() {
        if (!this.recalcDisguise())
            return;

        this.gaslighter.tweet();
    }

    private boolean testBiome(ServerLevel world, BlockPos pos) {
        Holder<Biome> biome = world.getBiome(pos);
        List<Holder<ConfiguredFeature<?, ?>>> trees = this.findTrees(world, biome);

        if (trees.isEmpty())
            return false;

        Holder<ConfiguredFeature<?, ?>> tree = trees.get(world.random.nextInt(trees.size()));

        if (tree == null)
            return false;

        return this.generate(world, pos, tree);
    }

    private boolean generate(ServerLevel world, BlockPos pos, Holder<ConfiguredFeature<?, ?>> feature) {
        feature.unwrapKey().ifPresent(k -> this.lastFeature = k.location());

        FakeStructureWorldAccess access = new FakeStructureWorldAccess(world, gaslighter);
        return feature.value().place(access, world.getChunkSource().getGenerator(), world.random, pos);
    }

    private static boolean isSafe(BlockState state) {
        return state.isSolid() && !state.canBeReplaced();
    }

    private static final Set<Class<? extends Feature<?>>> TREES = Set.of(
            TreeFeature.class, AbstractHugeMushroomFeature.class, HugeFungusFeature.class,
            DesertWellFeature.class, ChorusPlantFeature.class
    );

    private static final ResourceKey<ConfiguredFeature<?, ?>> CACTUS = asFeature(AITMod.id("cactus"));

    private List<Holder<ConfiguredFeature<?, ?>>> findTrees(ServerLevel world, Holder<Biome> biome) {
        BiomeHandler biomeHandler = this.tardis.handler(Id.BIOME);
        List<Holder<ConfiguredFeature<?, ?>>> trees = new ArrayList<>();

        if (biomeHandler.getBiomeKey() == BiomeHandler.BiomeType.SANDY && world.random.nextInt(5) != 0) {
            trees.add(getRegistry(world).getHolder(CACTUS).orElse(null));
            return trees;
        }

        for (HolderSet<PlacedFeature> feature : biome.value().getGenerationSettings().features()) {
            for (Holder<PlacedFeature> entry : feature) {
                Holder<ConfiguredFeature<?, ?>> configured = entry.value().feature();

                if (isTree(configured.value(), biome)) {
                    trees.add(configured);
                    break;
                } else {
                    boolean shouldBreak = false;

                    for (ConfiguredFeature<?, ?> configuredFeature : configured.value()
                            .config().getFeatures().toList()) {
                        if (!isTree(configuredFeature, biome))
                            continue;

                        trees.add(configured);
                        shouldBreak = true;
                        break;
                    }

                    if (shouldBreak)
                        break;
                }
            }
        }

        return trees;
    }

    public boolean isApplied() {
        return isDisguised(tardis) && gaslighter != null;
    }

    private static void shitParticles(ServerLevel world, BlockPos pos) {
        Vec3 center = pos.getCenter();
        world.sendParticles(ParticleTypes.END_ROD, center.x(), center.y(), center.z(),
                12, 0.3, 0.3, 0.3, 0);
    }

    private static boolean isTree(ConfiguredFeature<?, ?> configured, Holder<Biome> biome) {
        Feature<?> feature = configured.feature();

        for (Class<?> clazz : TREES) {
            if (clazz.isInstance(feature))
                return true;
        }

        return false;
    }

    @NotNull private static Registry<ConfiguredFeature<?, ?>> getRegistry(Level world) {
        return world.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE);
    }

    @NotNull private static ResourceKey<ConfiguredFeature<?, ?>> asFeature(ResourceLocation id) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, id);
    }
}
