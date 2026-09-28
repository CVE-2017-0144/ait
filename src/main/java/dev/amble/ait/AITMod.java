package dev.amble.ait;

import static dev.amble.ait.module.planet.core.space.planet.Crater.CRATER_ID;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import dev.amble.ait.api.AITModInitializer;
import dev.amble.ait.config.AITServerConfig;
import dev.amble.ait.core.*;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.blockentities.EnvironmentProjectorBlockEntity;
import dev.amble.ait.core.blockentities.PottedSonicScrewdriverBlockEntity;
import dev.amble.ait.core.blocks.EnvironmentProjectorBlock;
import dev.amble.ait.core.commands.*;
import dev.amble.ait.core.devteam.BetaTokenPrefs;
import dev.amble.ait.core.devteam.DevTeam;
import dev.amble.ait.core.drinks.DrinkRegistry;
import dev.amble.ait.core.engine.registry.SubSystemRegistry;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.entities.RiftEntity;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.blueprint.BlueprintRegistry;
import dev.amble.ait.core.item.component.AbstractTardisPart;
import dev.amble.ait.core.item.part.MachineItem;
import dev.amble.ait.core.likes.ItemOpinionRegistry;
import dev.amble.ait.core.lock.LockedDimensionRegistry;
import dev.amble.ait.core.loot.SetBlueprintLootFunction;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.sounds.flight.FlightSoundRegistry;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.animation.v2.blockbench.BlockbenchParser;
import dev.amble.ait.core.tardis.animation.v2.datapack.TardisAnimationRegistry;
import dev.amble.ait.core.tardis.control.sound.ControlSoundRegistry;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.AsyncLocatorUtil;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.tardis.vortex.reference.VortexReferenceRegistry;
import dev.amble.ait.core.util.CustomTrades;
import dev.amble.ait.core.util.StackUtil;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.LandingPadManager;
import dev.amble.ait.core.world.RiftChunkManager;
import dev.amble.ait.data.landing.LandingPadRegion;
import dev.amble.ait.data.schema.MachineRecipeSchema;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.ait.module.planet.core.space.planet.Crater;
import dev.amble.ait.registry.impl.*;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.platform.Entrypoints;
import dev.amble.lib.platform.ModEntrypoint;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.command.Commands;
import dev.amble.lib.platform.interaction.PlayerInteractionEvents;
import dev.amble.lib.platform.loot.LootEvents;
import dev.amble.lib.platform.registry.PlatformGameRules;
import dev.amble.lib.platform.registry.PlatformRegistries;
import dev.amble.lib.platform.worldgen.BiomeModifications;
import dev.amble.lib.platform.worldgen.BiomeSelectors;
import dev.amble.lib.register.AmbleRegistries;
import dev.amble.lib.util.ServerLifecycleHooks;
import dev.drtheo.multidim.MultiDim;
import net.neoforged.fml.loading.FMLEnvironment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AITMod implements ModEntrypoint {

    public static final String MOD_ID = "ait";
    public static final Logger LOGGER = LoggerFactory.getLogger("ait");
    public static final Random RANDOM = new Random();

    public static AITServerConfig CONFIG;
    public static final GameRules.Key<GameRules.BooleanValue> STASER_GRIEFING = PlatformGameRules.registerBoolean("staserGriefing", GameRules.Category.MISC, true);

    public static final GameRules.Key<GameRules.BooleanValue> TARDIS_GRIEFING = PlatformGameRules.registerBoolean("tardisGriefing", GameRules.Category.MISC, true);

    public static final GameRules.Key<GameRules.BooleanValue> TARDIS_FIRE_GRIEFING = PlatformGameRules.registerBoolean("tardisFireGriefing", GameRules.Category.MISC, false);


    public static final ResourceKey<PlacedFeature> CUSTOM_GEODE_PLACED_KEY = ResourceKey.create(Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "zeiton_geode"));

    // This DefaultParticleType gets called when you want to use your particle in code.
    public static final SimpleParticleType CORAL_PARTICLE = PlatformRegistries.simpleParticle();

    // This is the Crater feature that generates in the world. It's made with AI so it sucks lol
    public static final Crater CRATER = new Crater(ProbabilityFeatureConfiguration.CODEC);

    public static final String BRANCH;

    static {
        // 1.x.xx-[BRANCH-]dev+mc.1.20.1
        String version = Platform.modVersion(MOD_ID).orElse("unknown");
        // get the part of the version string between the - and +
        BRANCH = version.substring(version.indexOf("-") + 1, version.indexOf("+"));
    }

    public static boolean isUnsafeBranch() {
        return !BRANCH.contains("release");
    }

    public static boolean isOfficialBeta() {
        return BRANCH.contains("dev");
    }

    public static boolean isBetaLocked() {
        return isOfficialBeta() && !DevTeam.isDev() && !BetaTokenPrefs.isTokenValid();
    }

    public void registerParticles() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, id("coral_particle"), CORAL_PARTICLE);
    }

    @Override
    public void onInitialize() {
        AITServerConfig.INSTANCE.load();
        CONFIG = AITServerConfig.INSTANCE.instance();

        ServerLifecycleHooks.init();
        AsyncLocatorUtil.init();
        MultiDim.init();

        CreakRegistry.init();
        SequenceRegistry.init();
        MoodEventPoolRegistry.init();
        LandingPadManager.init();
        ControlRegistry.init();
        RiftChunkManager.init();

        AmbleRegistries.getInstance().registerAll(
                ConsoleRegistry.getInstance(),
                SonicRegistry.getInstance(),
                DesktopRegistry.getInstance(),
                ConsoleVariantRegistry.getInstance(),
                MachineRecipeRegistry.getInstance(),
                FlightSoundRegistry.getInstance(),
                VortexReferenceRegistry.getInstance(),
                BlueprintRegistry.getInstance(),
                ExteriorVariantRegistry.getInstance(),
                CategoryRegistry.getInstance(),
                TardisComponentRegistry.getInstance(),
                LockedDimensionRegistry.getInstance(),
                HumRegistry.getInstance(),
                SubSystemRegistry.getInstance(),
                ItemOpinionRegistry.getInstance(),
                DrinkRegistry.getInstance(),
                TardisAnimationRegistry.getInstance(),
                DoorRegistry.getInstance()
        );
        ControlSoundRegistry.init();
        BlockbenchParser.init();

        registerParticles();

        // For all the addon devs
        Entrypoints.invoke(AITModInitializer.class, AITModInitializer::onInitializeAIT);

        HandlesResponseRegistry.init();

        AITStatusEffects.init();
        AITVillagers.init();
        AITArgumentTypes.register();
        AITSounds.init();
        AITDimensions.init();

        CustomTrades.register();

        RegistryContainer.register(AITItemGroups.class, MOD_ID);
        RegistryContainer.register(AITItems.class, MOD_ID);
        RegistryContainer.register(AITBlocks.class, MOD_ID);
        RegistryContainer.register(AITBlockEntityTypes.class, MOD_ID);
        RegistryContainer.register(AITEntityTypes.class, MOD_ID);
        ModuleRegistry.instance().onCommonInit();

        BlueprintRegistry.BLUEPRINT_TYPE = Registry.register(BuiltInRegistries.LOOT_FUNCTION_TYPE,
                AITMod.id("set_blueprint"),
                new LootItemFunctionType<>(SetBlueprintLootFunction.CODEC));

        WorldUtil.init();
        TardisUtil.init();

        ServerTardisManager.init();
        TardisCriterions.init();

        entityAttributeRegister();

        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES,
                CUSTOM_GEODE_PLACED_KEY);

        Registry.register(net.minecraft.core.registries.BuiltInRegistries.FEATURE, CRATER_ID, CRATER);

        Commands.register(((dispatcher, registryAccess, environment) -> {
            TeleportInteriorCommand.register(dispatcher);
            SummonTardisCommand.register(dispatcher);
            SetLockedCommand.register(dispatcher);
            ThisTardisCommand.register(dispatcher);
            FuelCommand.register(dispatcher);
            SetRepairTicksCommand.register(dispatcher);
            RiftChunkCommand.register(dispatcher);
            ScaleCommand.register(dispatcher);
            TriggerMoodRollCommand.register(dispatcher);
            SetNameCommand.register(dispatcher);
            GetNameCommand.register(dispatcher);
            GetCreatorCommand.register(dispatcher);
            HomeCommand.register(dispatcher);
            SetMaxSpeedCommand.register(dispatcher);
            SetSiegeCommand.register(dispatcher);
            LinkCommand.register(dispatcher);
            UnLinkCommand.register(dispatcher);
            RemoveCommand.register(dispatcher);
            PermissionCommand.register(dispatcher);
            LoyaltyCommand.register(dispatcher);
            UnlockCommand.register(dispatcher);
            DataCommand.register(dispatcher);
            TravelDebugCommand.register(dispatcher);
            VersionCommand.register(dispatcher);
            SafePosCommand.register(dispatcher);
            ListCommand.register(dispatcher);
            LoadCommand.register(dispatcher);
            DebugCommand.register(dispatcher);

            if (!FMLEnvironment.production) {
                ProfileClientCommand.register(dispatcher);
                PerfScenarioCommand.register(dispatcher);
            }

            EraseChunksCommand.register(dispatcher);
            FlightCommand.register(dispatcher);
            SetDoorParticleCommand.register(dispatcher, registryAccess);
        }));

        AitNetworking.registerServerReceiver(TardisUtil.REGION_LANDING_CODE,
                (server, player, handler, buf, responseSender) -> {
                    BlockPos pos = buf.readBlockPos();
                    String landingCode = buf.readUtf();

                    server.execute(() -> {
                        if (!player.canInteractWithBlock(pos, 1.0))
                            return;

                        LandingPadRegion region = LandingPadManager.getInstance((ServerLevel) player.level()).getRegionAt(pos);

                        if (region == null)
                            return;

                        region.setLandingCode(landingCode);
                        LandingPadManager.Network.syncTracked(LandingPadManager.Network.Action.ADD, player.serverLevel(),
                                new ChunkPos(pos));
                    });
                });

        AitNetworking.registerServerReceiver(MachineItem.MACHINE_DISASSEMBLE,
                (server, player, handler, buf, responseSender) -> {
                    ItemStack machine = ItemStack.STREAM_CODEC.decode(buf);

                    Optional<MachineRecipeSchema> schema = MachineRecipeRegistry.getInstance().findMatching(machine);

                    if (schema.isEmpty())
                        return;

                    // this should ALWAYS be executed on the main thread
                    server.execute(() -> {
                        MachineItem.disassemble(player, machine, schema.get());

                        StackUtil.playBreak(player);
                    });
                });

        AitNetworking.registerServerReceiver(AbstractTardisPart.DISASSEMBLE,
                (server, player, handler, buf, responseSender) -> {
                    ItemStack machine = ItemStack.STREAM_CODEC.decode(buf);

                    Optional<MachineRecipeSchema> schema = MachineRecipeRegistry.getInstance().findMatching(machine);

                    if (schema.isEmpty())
                        return;

                    // this should ALWAYS be executed on the main thread
                    server.execute(() -> {
                        AbstractTardisPart.disassemble(player, machine, schema.get());

                        StackUtil.playBreak(player);
                    });
                });

        AitNetworking.registerServerReceiver(TOGGLE_PROJECTOR, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            boolean enabled = buf.readBoolean();

            server.execute(() -> {
                Level world = player.level();

                if (!player.canInteractWithBlock(pos, 1.0))
                    return;

                BlockState state = world.getBlockState(pos);

                if (!(world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity projector))
                    return;

                Tardis tardis = projector.tardis().get();
                world.setBlock(pos, state.setValue(EnvironmentProjectorBlock.ENABLED, enabled), Block.UPDATE_ALL);
                EnvironmentProjectorBlock.toggle(tardis, null, world, pos, world.getBlockState(pos), enabled);
            });
        });

        AitNetworking.registerServerReceiver(PROJECTOR_SELECTION, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            ResourceLocation id = buf.readResourceLocation();
            server.execute(() -> {
                ServerLevel world = player.serverLevel();
                if (player.canInteractWithBlock(pos, 1.0)
                        && world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity projector) {
                    ResourceKey<Level> key = ResourceKey.create(Registries.DIMENSION, id);

                    if (!WorldUtil.getProjectorWorlds().contains(server.getLevel(key)))
                        return;

                    projector.setCurrentFromClient(key, player);
                }
            });
        });

        AitNetworking.registerServerReceiver(PROJECTOR_ANGLES, (server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            float yaw = buf.readFloat();
            float pitch = buf.readFloat();
            server.execute(() -> {
                ServerLevel world = player.serverLevel();
                if (player.canInteractWithBlock(pos, 1.0)
                        && world.getBlockEntity(pos) instanceof EnvironmentProjectorBlockEntity projector) {
                    projector.setAnglesFromClient(yaw, pitch, player);
                }
            });
        });

        LootEvents.MODIFY.register((id, tableBuilder, builtin, registries) -> {
            if (builtin
                    && (id == BuiltInLootTables.NETHER_BRIDGE || id == BuiltInLootTables.DESERT_PYRAMID
                    || id == BuiltInLootTables.VILLAGE_ARMORER || id == BuiltInLootTables.RUINED_PORTAL)
                    || id.equals(BuiltInLootTables.END_CITY_TREASURE) || id.equals(BuiltInLootTables.SHIPWRECK_MAP)
                    || id.equals(BuiltInLootTables.ABANDONED_MINESHAFT) || id.equals(BuiltInLootTables.VILLAGE_CARTOGRAPHER)
                    || id.equals(BuiltInLootTables.VILLAGE_TOOLSMITH) || id.equals(BuiltInLootTables.SHIPWRECK_TREASURE)
                    || id.equals(BuiltInLootTables.ANCIENT_CITY) || id.equals(BuiltInLootTables.ANCIENT_CITY_ICE_BOX)
                    || id.equals(BuiltInLootTables.BURIED_TREASURE) || id.equals(BuiltInLootTables.DESERT_PYRAMID_ARCHAEOLOGY)
                    || id.equals(BuiltInLootTables.DESERT_WELL_ARCHAEOLOGY) || id.equals(BuiltInLootTables.OCEAN_RUIN_COLD_ARCHAEOLOGY)
                    || id.equals(BuiltInLootTables.OCEAN_RUIN_WARM_ARCHAEOLOGY) || id.equals(BuiltInLootTables.TRAIL_RUINS_ARCHAEOLOGY_RARE)
                    || id.equals(BuiltInLootTables.FISHING_TREASURE) || id == BuiltInLootTables.DESERT_PYRAMID
                    || id.equals(BuiltInLootTables.SIMPLE_DUNGEON) || id.equals(BuiltInLootTables.STRONGHOLD_LIBRARY)) {


                LootPool.Builder poolBuilder = LootPool.lootPool().add(LootItem.lootTableItem(AITItems.BLUEPRINT).apply(SetBlueprintLootFunction.random()).setWeight(10));

                tableBuilder.withPool(poolBuilder);
            }
        });

        PlayerInteractionEvents.USE_BLOCK.register((player, world, hand, hit) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (!(stack.getItem() instanceof SonicItem)) return InteractionResult.PASS;

            BlockPos pos = hit.getBlockPos();
            BlockState state = world.getBlockState(pos);

            if (state.is(Blocks.FLOWER_POT)) {
                if (!world.isClientSide) {
                    world.setBlock(pos, AITBlocks.POTTED_SONIC_SCREWDRIVER.defaultBlockState(), Block.UPDATE_ALL);
                    if (world.getBlockEntity(pos) instanceof PottedSonicScrewdriverBlockEntity pot)
                        pot.addSonic(stack);
                    world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                }
                return InteractionResult.sidedSuccess(world.isClientSide);
            }

            if (state.is(AITBlocks.POTTED_SONIC_SCREWDRIVER)
                    && world.getBlockEntity(pos) instanceof PottedSonicScrewdriverBlockEntity pot && !pot.isFull()) {
                if (!world.isClientSide) {
                    pot.addSonic(stack);
                    world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                    if (!player.getAbilities().instabuild) stack.shrink(1);
                }
                return InteractionResult.sidedSuccess(world.isClientSide);
            }

            return InteractionResult.PASS;
        });
    }

    public void entityAttributeRegister() {
        PlatformRegistries.attributes(AITEntityTypes.RIFT_ENTITY,
                RiftEntity::createMobAttributes);

        PlatformRegistries.attributes(AITEntityTypes.FLIGHT_TARDIS_TYPE,
                FlightTardisEntity::createDummyAttributes);
    }

    public static final ResourceLocation OPEN_SCREEN = AITMod.id("open_screen");
    public static final ResourceLocation OPEN_SCREEN_TARDIS = AITMod.id("open_screen_tardis");
    public static final ResourceLocation OPEN_SCREEN_CONSOLE = AITMod.id("open_screen_console");
    public static final ResourceLocation OPEN_SCREEN_PROJECTOR = AITMod.id("open_screen_projector");
    public static final ResourceLocation TOGGLE_PROJECTOR = AITMod.id("toggle_projector");
    public static final ResourceLocation PROJECTOR_SELECTION = ResourceLocation.fromNamespaceAndPath(MOD_ID, "projector_selection");
    public static final ResourceLocation PROJECTOR_ANGLES = ResourceLocation.fromNamespaceAndPath(MOD_ID, "projector_angles");
    public static final ResourceLocation PROFILE_CLIENT = AITMod.id("profile_client");

    public static void openScreen(ServerPlayer player, int id) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeInt(id);
        AitNetworking.send(player, OPEN_SCREEN, buf);
    }

    public static void openScreen(ServerPlayer player, int id, UUID tardis) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeInt(id);
        buf.writeUUID(tardis);
        AitNetworking.send(player, OPEN_SCREEN_TARDIS, buf);
    }

    public static void openScreen(ServerPlayer player, int id, UUID tardis, BlockPos console) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeInt(id);
        buf.writeUUID(tardis);
        buf.writeBlockPos(console);

        AitNetworking.send(player, OPEN_SCREEN_CONSOLE, buf);
    }

    public static void openScreen(ServerPlayer player, int id, BlockPos console) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeInt(id);
        buf.writeBlockPos(console);

        List<ServerLevel> worlds = WorldUtil.getProjectorWorlds();
        buf.writeVarInt(worlds.size());
        for (ServerLevel world : worlds)
            buf.writeResourceLocation(world.dimension().location());

        AitNetworking.send(player, OPEN_SCREEN_PROJECTOR, buf);
    }


    public static void sendProjectorToggle(BlockPos pos, boolean enabled) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeBlockPos(pos);
        buf.writeBoolean(enabled);
        AitNetworking.send(TOGGLE_PROJECTOR, buf);
    }

    public static void sendProjectorSelection(BlockPos pos, ResourceLocation worldId) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeBlockPos(pos);
        buf.writeResourceLocation(worldId);
        AitNetworking.send(PROJECTOR_SELECTION, buf);
    }

    public static void sendProjectorAngles(BlockPos pos, float yaw, float pitch) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeBlockPos(pos);
        buf.writeFloat(yaw);
        buf.writeFloat(pitch);
        AitNetworking.send(PROJECTOR_ANGLES, buf);
    }

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }
}
