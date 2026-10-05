package dev.amble.ait.client;

import static dev.amble.ait.AITMod.*;
import static dev.amble.ait.core.AITItems.isUnlockedOnThisDay;
import static dev.amble.ait.core.item.TardisMatrixItem.colorToInt;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.boti.*;
import dev.amble.ait.client.commands.ConfigCommand;
import dev.amble.ait.client.commands.DebugCommand;
import dev.amble.ait.client.config.AITClientConfig;
import dev.amble.ait.client.data.ClientLandingManager;
import dev.amble.ait.client.models.AnimatedModel;
import dev.amble.ait.client.models.decoration.GallifreyFallsModel;
import dev.amble.ait.client.models.decoration.PaintingFrameModel;
import dev.amble.ait.client.models.decoration.RiftModel;
import dev.amble.ait.client.models.decoration.TrenzalorePaintingModel;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.overlays.*;
import dev.amble.ait.client.renderers.EmissiveGeometry;
import dev.amble.ait.client.renderers.SonicRendering;
import dev.amble.ait.client.renderers.TardisStar;
import dev.amble.ait.client.renderers.consoles.ConsoleGeneratorRenderer;
import dev.amble.ait.client.renderers.consoles.ConsoleRenderer;
import dev.amble.ait.client.renderers.coral.CoralRenderer;
import dev.amble.ait.client.renderers.decoration.FlagBlockEntityRenderer;
import dev.amble.ait.client.renderers.decoration.PlaqueRenderer;
import dev.amble.ait.client.renderers.decoration.PottedSonicScrewdriverRenderer;
import dev.amble.ait.client.renderers.decoration.SnowGlobeRenderer;
import dev.amble.ait.client.renderers.doors.DoorRenderer;
import dev.amble.ait.client.renderers.entities.*;
import dev.amble.ait.client.renderers.exteriors.ExteriorRenderer;
import dev.amble.ait.client.renderers.machines.*;
import dev.amble.ait.client.renderers.monitors.MonitorRenderer;
import dev.amble.ait.client.renderers.monitors.WallMonitorRenderer;
import dev.amble.ait.client.renderers.sky.MarsSkyProperties;
import dev.amble.ait.client.screens.*;
import dev.amble.ait.client.sonic.SonicModelLoader;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.tardis.manager.ClientTardisManager;
import dev.amble.ait.client.util.ClientRenderPass;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.compat.portal.PortalsAPI;
import dev.amble.ait.core.*;
import dev.amble.ait.core.blockentities.ConsoleGeneratorBlockEntity;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.blockentities.ExteriorBlockEntity;
import dev.amble.ait.core.blocks.AstralMapBlock;
import dev.amble.ait.core.blocks.ExteriorBlock;
import dev.amble.ait.core.devteam.BetaVerification;
import dev.amble.ait.core.drinks.DrinkRegistry;
import dev.amble.ait.core.drinks.DrinkUtil;
import dev.amble.ait.core.entities.BOTIPaintingEntity;
import dev.amble.ait.core.entities.RiftEntity;
import dev.amble.ait.core.gravity.AitGravity;
import dev.amble.ait.core.item.*;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.ItemNbt;
import dev.amble.ait.data.schema.console.ConsoleTypeSchema;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.module.ModuleRegistry;
import dev.amble.ait.module.gun.core.item.BaseGunItem;
import dev.amble.ait.registry.impl.SonicRegistry;
import dev.amble.ait.registry.impl.console.ConsoleRegistry;
import dev.amble.ait.registry.impl.console.variant.ClientConsoleVariantRegistry;
import dev.amble.ait.registry.impl.door.ClientDoorRegistry;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.lib.platform.ClientModEntrypoint;
import dev.amble.lib.platform.Platform;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;
import dev.amble.lib.platform.clientlifecycle.ClientInputEvents;
import dev.amble.lib.platform.command.Commands;
import dev.amble.lib.platform.render.ClientRegistries;
import dev.amble.lib.platform.render.HudRenderEvents;
import dev.amble.lib.platform.render.WorldRenderContext;
import dev.amble.lib.platform.render.WorldRenderEvents;
import dev.amble.lib.platform.resource.BuiltinPacks;
import dev.amble.lib.register.AmbleRegistries;
import dev.loqor.portal.client.PortalDataManager;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.EndRodParticle;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class AITModClient implements ClientModEntrypoint {

    /** Its own logger rather than a prefix on every line, so the profiler output filters cleanly. */
    private static final Logger PROFILE_LOGGER = LoggerFactory.getLogger("ait-profile");

    public static AITClientConfig CONFIG;
    private final Minecraft client = Minecraft.getInstance();
    private TardisExteriorBOTI exteriorBoti;

    @Override
    public void onInitializeClient() {
        resourcepackRegister();
        AITClientConfig.INSTANCE.load();
        CONFIG = AITClientConfig.INSTANCE.instance();

        // TODO move to Registries
        AmbleRegistries.getInstance().registerAll(
                SonicRegistry.getInstance(),
                DrinkRegistry.getInstance(),
                ClientExteriorVariantRegistry.getInstance(),
                ClientConsoleVariantRegistry.getInstance(),
                ClientDoorRegistry.getInstance()
        );

        ClientTardisManager.init();

        if (Minecraft.ON_OSX) {
            Platform.modBus().addListener(RegisterShadersEvent.class, event -> {
                try {
                    event.registerShader(new ShaderInstance(event.getResourceProvider(),
                                    ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, "copy_depth"),
                                    DefaultVertexFormat.POSITION_TEX),
                            program -> BOTI.COPY_DEPTH_PROGRAM = program);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            });
        }

        ModuleRegistry.instance().onClientInit();

        setupBlockRendering();
        blockEntityRendererRegister();
        entityRenderRegister();
        chargedZeitonCrystalPredicate();
        waypointPredicate();
        hammerPredicate();
        siegeItemPredicate();
        adventItemPredicates();
        registerItemColors();
        registerParticles();

        Commands.Client.register((dispatcher, registryAccess) -> {
            ConfigCommand.register(dispatcher);
            DebugCommand.register(dispatcher);
        });

        // Must be registered, or the pass counter never advances and the duplicate-draw guard in
        // the renderers would let the first draw through and reject every one after it.
        ClientRenderPass.init();
        EmissiveGeometry.init();

        AITKeyBinds.init();

        ClientLandingManager.init();

        HudRenderEvents.HUD.register(new SonicOverlay());
        HudRenderEvents.HUD.register(new RWFOverlay());
        HudRenderEvents.HUD.register(new FabricatorOverlay());
        HudRenderEvents.HUD.register(new ExteriorAxeOverlay());
        HudRenderEvents.HUD.register(new UntemperedSchismOverlay());

        ClientInputEvents.PRE_ATTACK.register((client, player, clickCount) -> (player.getMainHandItem().getItem() instanceof BaseGunItem));

        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            if (!DependencyChecker.isIrisShaderPackInUse())
                this.renderBOTI(context);
        });

        if (DependencyChecker.hasIris()) {
            WorldRenderEvents.END.register(context -> {
                if (DependencyChecker.isIrisShaderPackInUse())
                    this.renderBOTI(context);
            });

            WorldRenderEvents.AFTER_ENTITIES.register(dev.amble.ait.client.boti.iris.ExteriorGbufferInjection::run);
        }

        // portal blits need a matching depth-stencil
        ClientEvents.CLIENT_STARTED.register(client -> AITRenderHelper.setIsStencilEnabled(client.getMainRenderTarget(), true));

        // @TODO idk why but this gets rid of other important stuff, not sure
        ClientRegistries.dimensionEffects(AITDimensions.MARS.location(), new MarsSkyProperties());

        WorldRenderEvents.BEFORE_ENTITIES.register(context -> {
            Tardis tardis = ClientTardisUtil.getCurrentTardis();

            if (tardis == null)
                return;

            TardisStar.render(context, tardis);
        });

        if (!FMLEnvironment.production) {
            AitNetworking.registerClientReceiver(AITMod.PROFILE_CLIENT, (client, handler, buf, responseSender) ->
                    client.execute(() -> {
                        // debugClientMetricsStart is what F3+L calls. The recorder stops itself after 10s and hands
                        // the dump path to this consumer, which is the only way to learn it without a keyboard.
                        boolean started = client.debugClientMetricsStart(
                                text -> PROFILE_LOGGER.info(text.getString()));

                        PROFILE_LOGGER.info(started ? "started" : "stopped an active recording");
                    }));
        }

        AitNetworking.registerClientReceiver(OPEN_SCREEN, (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            Screen screen = screenFromId(id);

            if (screen == null)
                return;

            client.execute(() -> client.forceSetScreen(screen));
        });

        AitNetworking.registerClientReceiver(OPEN_SCREEN_TARDIS, (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            UUID uuid = buf.readUUID();

            ClientTardisManager.getInstance().getTardis(uuid, tardis -> {
                Screen screen = screenFromId(id, tardis);

                if (screen == null)
                    return;

                client.execute(() -> client.forceSetScreen(screen));
            });
        });

        AitNetworking.registerClientReceiver(OPEN_SCREEN_CONSOLE, (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            UUID uuid = buf.readUUID();
            BlockPos console = buf.readBlockPos();

            ClientTardisManager.getInstance().getTardis(uuid, tardis -> {
                Screen screen = screenFromId(id, tardis, console);

                if (screen == null)
                    return;

                client.execute(() -> client.forceSetScreen(screen));
            });
        });

        AitNetworking.registerClientReceiver(OPEN_SCREEN_PROJECTOR, (client, handler, buf, responseSender) -> {
            int id = buf.readInt();
            BlockPos projector = buf.readBlockPos();

            List<ResourceKey<Level>> worldKeys = buf.readList(b -> b.readResourceKey(Registries.DIMENSION));

            client.execute(() -> {
                ClientTardis tardis = ClientTardisUtil.getCurrentTardis();

                if (tardis == null)
                    return; // not in a TARDIS

                Screen screen = screenFromId(id, tardis, projector);
                if (screen instanceof EnvironmentProjectorScreen projectorScreen) {
                    projectorScreen.setAvailableWorlds(worldKeys);
                    client.forceSetScreen(screen);
                }
            });
        });

        AitNetworking.registerClientReceiver(ConsoleGeneratorBlockEntity.SYNC_TYPE,
                (client, handler, buf, responseSender) -> {
                    if (client.level == null)
                        return;

                    String id = buf.readUtf();
                    ConsoleTypeSchema type = ConsoleRegistry.getInstance().get(ResourceLocation.tryParse(id));
                    BlockPos consolePos = buf.readBlockPos();

                    if (client.level.getBlockEntity(consolePos) instanceof ConsoleGeneratorBlockEntity console)
                        console.setConsoleSchema(type.id());
                });

        AitNetworking.registerClientReceiver(ConsoleGeneratorBlockEntity.SYNC_VARIANT,
                (client, handler, buf, responseSender) -> {
                    if (client.level == null)
                        return;

                    ResourceLocation id = ResourceLocation.tryParse(buf.readUtf());
                    BlockPos consolePos = buf.readBlockPos();

                    if (client.level.getBlockEntity(consolePos) instanceof ConsoleGeneratorBlockEntity console)
                        console.setVariant(id);
                });

        ClientTardisUtil.init();

        PortalDataManager.init();

        WorldRenderEvents.END.register((context) -> SonicRendering.getInstance().renderWorld(context));
        HudRenderEvents.HUD.register((context, delta) -> SonicRendering.getInstance()
                .renderGui(context, delta.getGameTimeDeltaPartialTick(true)));

        SonicModelLoader.init();

        AitNetworking.registerClientReceiver(AstralMapBlock.OPEN_ASTRAL_MAP, (client, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            List<ResourceLocation> ids = buf.readList(FriendlyByteBuf::readResourceLocation);
            client.execute(() -> {
                AstralMapBlock.structureIds = ids;
                client.setScreen(new AstralMapScreen(pos));
            });
        });

        ClientEvents.JOIN.register((client) -> BOTI.tryWarn(client));

        AitGravity.clientInit();

        BetaVerification.init();
    }

    public static Screen screenFromId(int id) {
        return screenFromId(id, null, null);
    }

    public static Screen screenFromId(int id, @Nullable ClientTardis tardis) {
        return screenFromId(id, tardis, null);
    }

    public static Screen screenFromId(int id, @Nullable ClientTardis tardis, @Nullable BlockPos console) {
        return switch (id) {
            case 0 -> new MonitorScreen(tardis, console);
            case 1 -> new BlueprintFabricatorScreen();
            case 3 -> new EnvironmentProjectorScreen(tardis, console);
            default -> null;
        };
    }

    public void chargedZeitonCrystalPredicate() {
        ItemProperties.register(AITItems.CHARGED_ZEITON_CRYSTAL, ResourceLocation.parse("fuel"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (livingEntity == null)
                        return 0.0F;
                    if (itemStack.getItem() instanceof ChargedZeitonCrystalItem item) {
                        float value = (float) (item.getCurrentFuel(itemStack) / item.getMaxFuel(itemStack));
                        if (value > 0.0f && value < 0.5f) {
                            return 0.5f;
                        } else if (value > 0.5f && value < 1.0f) {
                            return 1.0f;
                        } else {
                            return 0.0f;
                        }
                    }

                    return 0.0F;
                });
    }

    public static void waypointPredicate() {
        ItemProperties.register(AITItems.WAYPOINT_CARTRIDGE, ResourceLocation.parse("type"),
                (stack, clientWorld, livingEntity, integer) ->
                        ItemNbt.get(stack).contains(WaypointItem.POS_KEY) ? 1 : 0);
    }

    public static void hammerPredicate() {
        ItemProperties.register(AITItems.HAMMER, ResourceLocation.parse("toymakered"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof HammerItem) {
                        if (itemStack.getHoverName().getString().equalsIgnoreCase("Toymaker Hammer"))
                            return 1.0f;
                        else
                            return 0.0f;
                    }
                    return 0.0F;
                });
    }

    public static void siegeItemPredicate() {
        ItemProperties.register(AITItems.HAMMER, ResourceLocation.parse("bricked"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.has(AITDataComponents.SIEGE_CURRENT_TEXTURE)) {
                        return itemStack.getOrDefault(AITDataComponents.SIEGE_CURRENT_TEXTURE, 0);
                    }
                    return 0.0f;
                });
    }

    public static void adventItemPredicates() {
        ItemProperties.register(AITItems.HYPERCUBE, ResourceLocation.parse("advent"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof HypercubeItem) {
                        return isUnlockedOnThisDay(Calendar.JANUARY, 1) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });

        ItemProperties.register(AITItems.HAZANDRA, ResourceLocation.parse("advent"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof InteriorTeleporterItem) {
                        return isUnlockedOnThisDay(Calendar.DECEMBER, 28) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });

        ItemProperties.register(AITItems.IRON_KEY, ResourceLocation.parse("advent"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof KeyItem) {
                        return isUnlockedOnThisDay(Calendar.DECEMBER, 26) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });

        ItemProperties.register(AITItems.GOLD_KEY, ResourceLocation.parse("advent"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof KeyItem) {
                        return isUnlockedOnThisDay(Calendar.DECEMBER, 26) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });

        ItemProperties.register(AITItems.NETHERITE_KEY, ResourceLocation.parse("advent"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof KeyItem) {
                        return isUnlockedOnThisDay(Calendar.DECEMBER, 26) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });

        ItemProperties.register(AITItems.CLASSIC_KEY, ResourceLocation.parse("advent"),
                (itemStack, clientWorld, livingEntity, integer) -> {
                    if (itemStack.getItem() instanceof KeyItem) {
                        return isUnlockedOnThisDay(Calendar.DECEMBER, 26) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });
    }

    public static void blockEntityRendererRegister() {
        BlockEntityRenderers.register(AITBlockEntityTypes.CONSOLE_BLOCK_ENTITY_TYPE, ConsoleRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.CONSOLE_GENERATOR_ENTITY_TYPE,
                ConsoleGeneratorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.EXTERIOR_BLOCK_ENTITY_TYPE, ExteriorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.DOOR_BLOCK_ENTITY_TYPE, DoorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.CORAL_BLOCK_ENTITY_TYPE, CoralRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.MONITOR_BLOCK_ENTITY_TYPE, MonitorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.ARTRON_COLLECTOR_BLOCK_ENTITY_TYPE,
                ArtronCollectorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.PLAQUE_BLOCK_ENTITY_TYPE, PlaqueRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.WALL_MONITOR_BLOCK_ENTITY_TYPE,
                WallMonitorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.ENGINE_BLOCK_ENTITY_TYPE, EngineRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.FABRICATOR_BLOCK_ENTITY_TYPE,
                FabricatorRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.WAYPOINT_BANK_BLOCK_ENTITY_TYPE,
                WaypointBankBlockEntityRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.FLAG_BLOCK_ENTITY_TYPE, FlagBlockEntityRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.GENERIC_SUBSYSTEM_BLOCK_TYPE,
                GenericSubSystemRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.POWER_CONVERTER_BLOCK_TYPE,
                PowerConverterRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.FOOD_MACHINE_BLOCK_ENTITY_TYPE,
                FoodMachineRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.ASTRAL_MAP, AstralMapRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.POTTED_SONIC_SCREWDRIVER_BLOCK_ENTITY_TYPE, PottedSonicScrewdriverRenderer::new);
        BlockEntityRenderers.register(AITBlockEntityTypes.RIFT_RIPPER_BLOCK_ENTITY_TYPE, UntemperedSchismRenderer::new);
        if (isUnlockedOnThisDay(Calendar.DECEMBER, 30)) {
            BlockEntityRenderers.register(AITBlockEntityTypes.SNOW_GLOBE_BLOCK_ENTITY_TYPE,
                    SnowGlobeRenderer::new);
        }
    }

    public static void entityRenderRegister() {
        ClientRegistries.entityRenderer(AITEntityTypes.CONTROL_ENTITY_TYPE, ControlEntityRenderer::new);
        ClientRegistries.entityRenderer(AITEntityTypes.FALLING_TARDIS_TYPE, FallingTardisRenderer::new);
        ClientRegistries.entityRenderer(AITEntityTypes.FLIGHT_TARDIS_TYPE, FlightTardisRenderer::new);
        ClientRegistries.entityRenderer(AITEntityTypes.GALLIFREY_FALLS_PAINTING_ENTITY_TYPE, GallifreyanPaintingEntityRenderer::new);
        ClientRegistries.entityRenderer(AITEntityTypes.TRENZALORE_PAINTING_ENTITY_TYPE, TrenzalorePaintingEntityRenderer::new);
//        if (isUnlockedOnThisDay(Calendar.DECEMBER, 26)) {
//            EntityRendererRegistry.register(AITEntityTypes.COBBLED_SNOWBALL_TYPE, FlyingItemEntityRenderer::new);
//        }
        ClientRegistries.entityRenderer(AITEntityTypes.RIFT_ENTITY, RiftEntityRenderer::new);
    }

    public static void setupBlockRendering() {
        ClientRegistries.blockRenderLayer(AITBlocks.ZEITON_BLOCK, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.BUDDING_ZEITON, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.ENGINE_BLOCK, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.ZEITON_CLUSTER, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.LARGE_ZEITON_BUD, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.MEDIUM_ZEITON_BUD, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.SMALL_ZEITON_BUD, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.MACHINE_CASING, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.FABRICATOR, RenderType.translucent());
        ClientRegistries.blockRenderLayer(AITBlocks.ENVIRONMENT_PROJECTOR, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.WAYPOINT_BANK, RenderType.cutout());
        if (isUnlockedOnThisDay(Calendar.DECEMBER, 30)) {
            ClientRegistries.blockRenderLayer(AITBlocks.SNOW_GLOBE, RenderType.cutout());
        }
        ClientRegistries.blockRenderLayer(AITBlocks.TARDIS_CORAL_BLOCK, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.TARDIS_CORAL_FAN, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.TARDIS_CORAL_WALL, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.TARDIS_CORAL_FENCE, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.TARDIS_CORAL_LEAVES, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.MATRIX_ENERGIZER, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.GENERIC_SUBSYSTEM, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.POTTED_SONIC_SCREWDRIVER, RenderType.cutout());
        ClientRegistries.blockRenderLayer(AITBlocks.ARTRON_COLLECTOR_BLOCK, RenderType.cutout());
    }

    public void registerItemColors() {
        ClientRegistries.itemColor((stack, tintIndex) -> {
                    if (tintIndex != 0)
                        return -1;

                    TardisMatrixItem tardisMatrixItem = (TardisMatrixItem) stack.getItem();
                    int[] integers = tardisMatrixItem.getColor(stack);
                    return colorToInt(integers[0], integers[1], integers[2]);
                }, AITItems.TARDIS_MATRIX);

        ClientRegistries.itemColor((stack, tintIndex) -> tintIndex > 0 ? -1 :
                DrinkUtil.getColor(stack), AITItems.MUG);

        ClientRegistries.itemColor((stack, tintIndex) -> {
            if (tintIndex != 0)
                return -1;

            return WaypointItem.getColor(stack);
        }, AITItems.WAYPOINT_CARTRIDGE);
    }

    public void registerParticles() {
        ClientRegistries.particle(CORAL_PARTICLE, EndRodParticle.Provider::new);
    }

    public static boolean skipBuiltInBOTI() {
        return (DependencyChecker.hasPortals() && CONFIG.allowPortalsBoti) || !CONFIG.enableTardisBOTI;
    }

    public static boolean skipPaintingBOTI() {
        return !CONFIG.enableTardisBOTI;
    }

    private void renderBOTI(WorldRenderContext context) {
        this.exteriorBOTI(context);
        this.doorBOTI(context);
        this.gallifreyanBOTI(context);
        this.trenzaloreBOTI(context);
        this.riftBOTI(context);
    }

    public void exteriorBOTI(WorldRenderContext context) {
        // Counted before the guard on purpose. BOTI disables itself on Macs and on non-Nvidia cards
        // without Indium, and a counter inside the loop cannot tell that apart from an empty queue.
        ProfilerFiller profiler = context.world().getProfiler();
        profiler.incrementCounter("ait_boti_exterior_queued", BOTI.EXTERIOR_RENDER_QUEUE.size());

        if (skipBuiltInBOTI()) {
            profiler.incrementCounter("ait_boti_exterior_disabled");
            BOTI.EXTERIOR_RENDER_QUEUE.clear();
            return;
        }

        if (client.player == null || client.level == null) {
            BOTI.EXTERIOR_RENDER_QUEUE.clear();
            return;
        }

        if (exteriorBoti == null)
            exteriorBoti = new TardisExteriorBOTI();

        ClientLevel world = client.level;
        PoseStack stack = context.matrixStack();

        profiler.push("ait:boti_exterior");

        for (ExteriorBlockEntity exterior : BOTI.EXTERIOR_RENDER_QUEUE) {
            if (exterior == null || !exterior.isLinked()) continue;
            Tardis tardis = exterior.tardis().get();

            ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();
            ExteriorModel model = variant.getCachedModel();
            BlockPos pos = exterior.getBlockPos();
            stack.pushPose();
            stack.translate(0.5, 0, 0.5);
            stack.translate(pos.getX() - context.camera().getPosition().x(), pos.getY() - context.camera().getPosition().y(), pos.getZ() - context.camera().getPosition().z());
            stack.scale(1, -1, -1);
            stack.mulPose(Axis.YP.rotationDegrees(RotationSegment.convertToDegrees(exterior.getBlockState().getValue(ExteriorBlock.ROTATION))));

            if (tardis.door().getLeftRot() > 0 || variant.hasTransparentDoors()) {
                int light = LightTexture.pack(world.getBrightness(LightLayer.BLOCK, pos), world.getBrightness(LightLayer.SKY, pos));
                profiler.incrementCounter("ait_boti_exterior_drawn");
                profiler.incrementCounter("ait_model_build");
                exteriorBoti.renderExteriorBoti(exterior, variant, stack, AITMod.id("textures/environment/tardis_sky.png"), model,
                        BOTI.portalMask(), light);
            } else {
                profiler.incrementCounter("ait_boti_exterior_culled");
            }

            stack.popPose();
        }

        profiler.pop();

        BOTI.EXTERIOR_RENDER_QUEUE.clear();
    }

    public void doorBOTI(WorldRenderContext context) {
        ProfilerFiller profiler = context.world().getProfiler();
        profiler.incrementCounter("ait_boti_door_queued", BOTI.DOOR_RENDER_QUEUE.size());

        if (skipBuiltInBOTI()) {
            profiler.incrementCounter("ait_boti_door_disabled");
            BOTI.DOOR_RENDER_QUEUE.clear();
            return;
        }

        if (client.player == null || client.level == null) {
            BOTI.DOOR_RENDER_QUEUE.clear();
            return;
        }

        ClientLevel world = client.level;
        PoseStack stack = context.matrixStack();

        ClientTardis tardis = ClientTardisUtil.getCurrentTardis();

        if (tardis == null) {
            BOTI.DOOR_RENDER_QUEUE.clear();
            return;
        }

        ClientExteriorVariantSchema variant = tardis.getExterior().getVariant().getClient();
        AnimatedModel model = variant.getDoor().getCachedModel();
        Frustum frustum = context.frustum();

        profiler.push("ait:boti_door");

        for (DoorBlockEntity door : BOTI.DOOR_RENDER_QUEUE) {
            if (door == null) continue;
            BlockPos pos = door.getBlockPos();

            if (frustum != null && !frustum.isVisible(new AABB(pos).inflate(2.0)))
                continue;

            stack.pushPose();
            stack.translate(0.5, 0, 0.5);
            stack.translate(pos.getX() - context.camera().getPosition().x(), pos.getY() - context.camera().getPosition().y(), pos.getZ() - context.camera().getPosition().z());
            stack.scale(1, -1, -1);
            stack.mulPose(Axis.YP.rotationDegrees(door.getBlockState().getValue(DoorBlock.FACING).toYRot()));

            if (tardis.door().getLeftRot() > 0 || variant.hasTransparentDoors()) {
                int light = LightTexture.pack(world.getBrightness(LightLayer.BLOCK, pos), world.getBrightness(LightLayer.SKY, pos));
                profiler.incrementCounter("ait_boti_door_drawn");
                profiler.incrementCounter("ait_model_build");
                TardisDoorBOTI.renderInteriorDoorBoti(tardis, door, variant, stack,
                        AITMod.id("textures/environment/tardis_sky.png"), model,
                        BOTI.portalMask(), light, context.tickCounter().getGameTimeDeltaPartialTick(true));
            } else {
                profiler.incrementCounter("ait_boti_door_culled");
            }

            stack.popPose();
        }

        profiler.pop();

        BOTI.DOOR_RENDER_QUEUE.clear();
    }

    public void gallifreyanBOTI(WorldRenderContext context) {
        if (PortalsAPI.RENDERING_PORTAL.getAsBoolean())
            return;

        ProfilerFiller profiler = context.world().getProfiler();
        profiler.incrementCounter("ait_boti_gallifreyan_queued", BOTI.GALLIFREYAN_RENDER_QUEUE.size());

        if (skipPaintingBOTI()) {
            profiler.incrementCounter("ait_boti_gallifreyan_disabled");
            BOTI.GALLIFREYAN_RENDER_QUEUE.clear();
            return;
        }

        if (BOTI.GALLIFREYAN_RENDER_QUEUE.isEmpty())
            return;

        profiler.push("ait:boti_gallifreyan");

        profiler.incrementCounter("ait_model_build");
        HierarchicalModel contents = new GallifreyFallsModel(GallifreyFallsModel.getTexturedModelData().bakeRoot());
        ResourceLocation frameTex = GallifreyanPaintingEntityRenderer.GALLIFREY_FRAME_TEXTURE;
        ResourceLocation contentsTex = GallifreyanPaintingEntityRenderer.GALLIFREY_PAINTING_TEXTURE;
        if (client.player == null || client.level == null) {
            profiler.pop();
            return;
        }
        ClientLevel world = client.level;
        PoseStack stack = context.matrixStack();
        for (BOTIPaintingEntity painting : BOTI.GALLIFREYAN_RENDER_QUEUE) {
            if (painting == null) continue;
            Vec3 pos = painting.position();
            stack.pushPose();
            stack.translate(pos.x() - context.camera().getPosition().x(),
                    pos.y() - context.camera().getPosition().y(), pos.z() - context.camera().getPosition().z());
            stack.mulPose(Axis.XP.rotationDegrees(180f));
            stack.mulPose(Axis.YP.rotationDegrees(painting.getVisualRotationYInDegrees()));
            stack.translate(0, -0.5f, 0.5);
            profiler.incrementCounter("ait_model_build");
            PaintingFrameModel frame = BOTI.paintingFrame();
            BlockPos blockPos = BlockPos.containing(painting.getLightProbePosition(client.getTimer().getGameTimeDeltaPartialTick(true)));
            PaintingBOTI.renderBOTIPainting(stack, frame,
                    LightTexture.pack(world.getBrightness(LightLayer.BLOCK, blockPos),
                            world.getBrightness(LightLayer.SKY, blockPos)), contents, frameTex, contentsTex);
            stack.popPose();
        }

        profiler.pop();

        BOTI.GALLIFREYAN_RENDER_QUEUE.clear();
    }

    public void trenzaloreBOTI(WorldRenderContext context) {
        if (PortalsAPI.RENDERING_PORTAL.getAsBoolean())
            return;

        ProfilerFiller profiler = context.world().getProfiler();
        profiler.incrementCounter("ait_boti_trenzalore_queued", BOTI.TRENZALORE_PAINTING_QUEUE.size());

        if (skipPaintingBOTI()) {
            profiler.incrementCounter("ait_boti_trenzalore_disabled");
            BOTI.TRENZALORE_PAINTING_QUEUE.clear();
            return;
        }

        if (BOTI.TRENZALORE_PAINTING_QUEUE.isEmpty())
            return;

        profiler.push("ait:boti_trenzalore");

        profiler.incrementCounter("ait_model_build");
        HierarchicalModel contents = new TrenzalorePaintingModel(TrenzalorePaintingModel.getTexturedModelData().bakeRoot());
        ResourceLocation frameTex = TrenzalorePaintingEntityRenderer.TRENZALORE_FRAME_TEXTURE;
        ResourceLocation contentsTex = TrenzalorePaintingEntityRenderer.TRENZALORE_PAINTING_TEXTURE;
        if (client.player == null || client.level == null) {
            profiler.pop();
            return;
        }
        ClientLevel world = client.level;
        PoseStack stack = context.matrixStack();
        for (BOTIPaintingEntity painting : BOTI.TRENZALORE_PAINTING_QUEUE) {
            if (painting == null) continue;
            Vec3 pos = painting.position();
            stack.pushPose();
            stack.translate(pos.x() - context.camera().getPosition().x(),
                    pos.y() - context.camera().getPosition().y(), pos.z() - context.camera().getPosition().z());
            stack.mulPose(Axis.XP.rotationDegrees(180f));
            stack.mulPose(Axis.YP.rotationDegrees(painting.getVisualRotationYInDegrees()));
            stack.translate(0, -0.5f, 0.5);
            profiler.incrementCounter("ait_model_build");
            PaintingFrameModel frame = BOTI.paintingFrame();
            BlockPos blockPos = BlockPos.containing(painting.getLightProbePosition(client.getTimer().getGameTimeDeltaPartialTick(true)));
            PaintingBOTI.renderBOTIPainting(stack, frame,
                    LightTexture.pack(world.getBrightness(LightLayer.BLOCK, blockPos),
                            world.getBrightness(LightLayer.SKY, blockPos)), contents, frameTex, contentsTex);
            stack.popPose();
        }

        profiler.pop();

        BOTI.TRENZALORE_PAINTING_QUEUE.clear();
    }

    public void riftBOTI(WorldRenderContext context) {
        ProfilerFiller profiler = context.world().getProfiler();
        profiler.incrementCounter("ait_boti_rift_queued", BOTI.RIFT_RENDERING_QUEUE.size());

        if (skipPaintingBOTI()) {
            profiler.incrementCounter("ait_boti_rift_disabled");
            BOTI.RIFT_RENDERING_QUEUE.clear();
            return;
        }

        if (client.player == null || client.level == null) {
            BOTI.RIFT_RENDERING_QUEUE.clear();
            return;
        }

        ClientLevel world = client.level;
        PoseStack stack = context.matrixStack();

        profiler.push("ait:boti_rift");

        for (RiftEntity rift : BOTI.RIFT_RENDERING_QUEUE) {
            if (rift == null) continue;
            Vec3 pos = rift.position();
            stack.pushPose();
            stack.translate(pos.x() - context.camera().getPosition().x(),
                    pos.y() - context.camera().getPosition().y(), pos.z() - context.camera().getPosition().z());
            stack.translate(0, 1.5f, 0);
            stack.mulPose(Axis.YP.rotationDegrees(rift.getYRot()));
            stack.mulPose(Axis.XP.rotationDegrees(rift.getXRot()));
            profiler.incrementCounter("ait_model_build");
            RiftModel riftModel = BOTI.rift();
            BlockPos blockPos = BlockPos.containing(rift.getLightProbePosition(client.getTimer().getGameTimeDeltaPartialTick(true)));
            RiftBOTI.renderRiftBoti(stack, riftModel, LightTexture.pack(world.getBrightness(LightLayer.BLOCK, blockPos), world.getBrightness(LightLayer.SKY, blockPos)));
            stack.popPose();
        }

        profiler.pop();

        BOTI.RIFT_RENDERING_QUEUE.clear();
    }
    public static void resourcepackRegister() {

        // Register builtin resourcepacks (thank you addie for your help)
        BuiltinPacks.register(id("aitmenu"), true);
        BuiltinPacks.register(id("bushy_leaves"), false);
    }
}
