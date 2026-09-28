package dev.amble.ait.registry.impl;

import java.util.ArrayList;
import java.util.List;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.control.impl.*;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementControl;
import dev.amble.ait.core.tardis.control.impl.pos.XControl;
import dev.amble.ait.core.tardis.control.impl.pos.YControl;
import dev.amble.ait.core.tardis.control.impl.pos.ZControl;
import dev.amble.ait.core.tardis.control.impl.waypoint.LoadWaypointControl;
import dev.amble.ait.core.tardis.control.sequences.Sequence;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.data.DirectedBlockPos;
import dev.amble.lib.platform.registry.PlatformRegistries;
import dev.amble.plushies.PlushieBlocks;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class SequenceRegistry {
    public static final MappedRegistry<Sequence> REGISTRY = PlatformRegistries.createRegistry(ResourceKey.<Sequence>createRegistryKey(AITMod.id("sequence")));
    private static final RandomSource random = RandomSource.create();

    public static Sequence register(Sequence schema) {
        return Registry.register(REGISTRY, schema.id(), schema);
    }

    public static Sequence AVOID_DEBRIS;
    public static Sequence DIMENSIONAL_BREACH;
    public static Sequence ENERGY_DRAIN;
    public static Sequence DIMENSIONAL_DRIFT_X;
    public static Sequence DIMENSIONAL_DRIFT_Y;
    public static Sequence DIMENSIONAL_DRIFT_Z;
    // public static Sequence FORCED_MAT;
    public static Sequence CLOAK_TO_AVOID_VORTEX_TRAPPED_MOBS;
    public static Sequence ANTI_GRAVITY_ERROR;
    public static Sequence POWER_DRAIN_IMMINENT;
    public static Sequence SHIP_COMPUTER_OFFLINE;
    // public static Sequence VORTEX_COLLISION;
    public static Sequence DIRECTIONAL_ERROR;
    // public static Sequence RANDOM_LOCATION_IDENTIFIED;
    public static Sequence SPEED_UP_TO_AVOID_DRIFTING_OUT_OF_VORTEX;
    public static Sequence SLOW_DOWN_TO_AVOID_FLYING_OUT_OF_VORTEX;
    public static Sequence COURSE_CORRECT;
    public static Sequence GROUND_UNSTABLE;
    public static Sequence INCREMENT_SCALE_RECALCULATION_NECESSARY;
    public static Sequence SMALL_DEBRIS_FIELD;

    public static void init() {
        AVOID_DEBRIS = register(Sequence.Builder.create(AITMod.id("avoid_debris"),
                finishedTardis -> finishedTardis.travel().decreaseFlightTime(100), missedTardis -> {
                    missedTardis.removeFuel(-random.nextIntBetweenInclusive(45, 125));
                    missedTardis.door().openDoors();

                    missedTardis.travel().increaseFlightTime(700);

                    List<Explosion> explosions = new ArrayList<>();
                    ServerLevel world = missedTardis.asServer().world();

                    missedTardis.getDesktop().getConsolePos().forEach(console -> {
                        Explosion explosion = world.explode(null, null, null,
                                console.getCenter(), 3f * 2, false, Level.ExplosionInteraction.BLOCK);

                        explosions.add(explosion);
                    });

                    for (ServerPlayer player : world.players()) {
                        float xVel = AITMod.RANDOM.nextFloat(-2f, 3f);
                        float yVel = AITMod.RANDOM.nextFloat(-1f, 2f);
                        float zVel = AITMod.RANDOM.nextFloat(-2f, 3f);

                        player.setDeltaMovement(xVel * 2, yVel * 2, zVel * 2);

                        if (!explosions.isEmpty()) {
                            player.hurt(world.damageSources().explosion(explosions.get(0)), 0);
                        } else {
                            player.hurt(WorldUtil.getOverworld().damageSources().generic(), 0);
                        }
                    }
                }, 100L, Component.translatable("sequence.ait.avoid_debris").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                new DirectionControl(), new RandomiserControl()));

        DIMENSIONAL_BREACH = register(
                Sequence.Builder.create(AITMod.id("dimensional_breach"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                }), (missedTardis -> {
                    missedTardis.door().openDoors();
                }), 80L, Component.translatable("sequence.ait.dimensional_breach").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new DimensionControl(), new DoorControl()));

        ENERGY_DRAIN = register(
                Sequence.Builder.create(AITMod.id("energy_drain"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(25);
                    finishedTardis.addFuel(random.nextIntBetweenInclusive(45, 125));
                }), (missedTardis -> missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125))), 80L,
                        Component.translatable("sequence.ait.energy_drain").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new RefuelerControl()));

        POWER_DRAIN_IMMINENT = register(
                Sequence.Builder.create(AITMod.id("power_drain_imminent"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(75);
                    finishedTardis.addFuel(random.nextIntBetweenInclusive(45, 125));
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.fuel().disablePower();
                }), 110L, Component.translatable("sequence.ait.power_drain_imminent").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new PowerControl(), new RefuelerControl(), new RandomiserControl()));

        SHIP_COMPUTER_OFFLINE = register(
                Sequence.Builder.create(AITMod.id("ship_computer_offline"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                    finishedTardis.addFuel(random.nextIntBetweenInclusive(45, 125));
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.fuel().disablePower();
                }), 110L, Component.translatable("sequence.ait.ship_computer_offline").withStyle(ChatFormatting.ITALIC,
                        ChatFormatting.YELLOW), new AutoPilotControl()));

        ANTI_GRAVITY_ERROR = register(
                Sequence.Builder.create(AITMod.id("anti_gravity_error"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(25);
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.travel().antigravs().set(false);
                }), 80L, Component.translatable("sequence.ait.anti_gravity_error").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new AntiGravsControl()));

        DIMENSIONAL_DRIFT_X = register(
                Sequence.Builder.create(AITMod.id("dimensional_drift_x"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                }), (missedTardis -> missedTardis.travel().forceDestination(cached -> {
                    BlockPos pos = cached.getPos();

                    missedTardis.travel().increaseFlightTime(400);
                    return cached.pos(random.nextIntBetweenInclusive(pos.getX() - 8, pos.getX() + 8), pos.getY(),
                            random.nextIntBetweenInclusive(pos.getZ() - 8, pos.getZ() + 8));
                })), 100L, Component.translatable("sequence.ait.dimensional_drift_x").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new DimensionControl(), new XControl()));

        DIMENSIONAL_DRIFT_Y = register(
                Sequence.Builder.create(AITMod.id("dimensional_drift_y"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                }), (missedTardis -> missedTardis.travel().forceDestination(cached -> {
                    BlockPos pos = cached.getPos();

                    missedTardis.travel().increaseFlightTime(400);
                    return cached.pos(random.nextIntBetweenInclusive(pos.getX() - 8, pos.getX() + 8), pos.getY(),
                            random.nextIntBetweenInclusive(pos.getZ() - 8, pos.getZ() + 8));
                })), 100L, Component.translatable("sequence.ait.dimensional_drift_y").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new DimensionControl(), new YControl()));

        DIMENSIONAL_DRIFT_Z = register(
                Sequence.Builder.create(AITMod.id("dimensional_drift_z"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                }), (missedTardis -> missedTardis.travel().forceDestination(cached -> {
                    BlockPos pos = cached.getPos();

                    missedTardis.travel().increaseFlightTime(400);
                    return cached.pos(random.nextIntBetweenInclusive(pos.getX() - 8, pos.getX() + 8), pos.getY(),
                            random.nextIntBetweenInclusive(pos.getZ() - 8, pos.getZ() + 8));
                })), 100L, Component.translatable("sequence.ait.dimensional_drift_z").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new DimensionControl(), new ZControl()));

        CLOAK_TO_AVOID_VORTEX_TRAPPED_MOBS = register(Sequence.Builder
                .create(AITMod.id("cloak_to_avoid_vortex_trapped_mobs"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(75);
                    DirectedBlockPos directedDoorPos = finishedTardis.getDesktop().getDoorPos();

                    if (directedDoorPos == null)
                        return;

                    BlockPos doorPos = directedDoorPos.getPos();

                    if (finishedTardis.door().isOpen() || !(finishedTardis instanceof ServerTardis))
                        return;

                    ServerLevel world = finishedTardis.asServer().world();

                    ItemEntity rewardForCloaking = new ItemEntity(EntityType.ITEM, world);
                    rewardForCloaking.setPos(doorPos.getCenter());

                    rewardForCloaking.setItem(switch (random.nextInt(3)) {
                        case 0 -> Items.COOKIE.getDefaultInstance();
                        case 1 -> Items.POPPY.getDefaultInstance();
                        default -> PlushieBlocks.GIFT_BOX.asItem().getDefaultInstance();
                    });
                    world.addFreshEntity(rewardForCloaking);
                }), (missedTardis -> {
                    DirectedBlockPos directedDoorPos = missedTardis.getDesktop().getDoorPos();

                    if (directedDoorPos == null)
                        return;

                    BlockPos doorPos = directedDoorPos.getPos();
                    missedTardis.travel().increaseFlightTime(200);

                    if (missedTardis.door().isOpen() || !(missedTardis instanceof ServerTardis))
                        return;

                    ServerLevel interior = missedTardis.asServer().world();
                    Vec3 centered = doorPos.getCenter();

                    Zombie zombieEntity = new Zombie(EntityType.ZOMBIE, interior);
                    zombieEntity.setPos(centered);

                    Drowned drownedEntity = new Drowned(EntityType.DROWNED,
                            interior);
                    drownedEntity.setPos(centered);

                    Phantom phantomEntity = new Phantom(EntityType.PHANTOM,
                            interior);
                    phantomEntity.setPos(centered);

                    interior.addFreshEntity(random.nextBoolean() ? random.nextBoolean() ? drownedEntity : zombieEntity : phantomEntity);
                }), 80L, Component.translatable("sequence.ait.cloak_to_avoid_vortex_trapped_mobs").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new CloakControl(), new RandomiserControl()));

        DIRECTIONAL_ERROR = register(
                Sequence.Builder.create(AITMod.id("directional_error"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                }), (missedTardis -> {
                    missedTardis.travel().increaseFlightTime(200);
                }), 80L, Component.translatable("sequence.ait.directional_error").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new DirectionControl()));

        SPEED_UP_TO_AVOID_DRIFTING_OUT_OF_VORTEX = register(Sequence.Builder
                .create(AITMod.id("speed_up_to_avoid_drifting_out_of_vortex"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(100);
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.travel().increaseFlightTime(200);
                }), 80L, Component.translatable("sequence.ait.speed_up_to_avoid_drifting_out_of_vortex").withStyle(ChatFormatting.ITALIC,
                        ChatFormatting.YELLOW), new IncrementControl(), new ThrottleControl()));

        SLOW_DOWN_TO_AVOID_FLYING_OUT_OF_VORTEX = register(Sequence.Builder
                .create(AITMod.id("slow_down_to_avoid_flying_out_of_vortex"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(100);
                }), (missedTardis -> {
                    missedTardis.travel().rematerialize();
                }), 80L, Component.translatable("sequence.ait.slow_down_to_avoid_flying_out_of_vortex").withStyle(ChatFormatting.ITALIC,
                        ChatFormatting.YELLOW), new IncrementControl(), new HandBrakeControl(), new ThrottleControl()));


        COURSE_CORRECT = register(
                Sequence.Builder.create(AITMod.id("course_correct"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(75);
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(65, 250));

                    missedTardis.travel().forceDestination(cached -> {
                        BlockPos pos = cached.getPos();

                        missedTardis.travel().increaseFlightTime(400);

                        return cached.pos(random.nextIntBetweenInclusive(pos.getX() - 24, pos.getX() + 24), pos.getY(),
                                random.nextIntBetweenInclusive(pos.getZ() - 24, pos.getZ() + 24));
                    });
                }), 110L, Component.translatable("sequence.ait.course_correct").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new HandBrakeControl(), new ThrottleControl(), new RandomiserControl()));

        GROUND_UNSTABLE = register(
                Sequence.Builder.create(AITMod.id("ground_unstable"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(25);
                    finishedTardis.addFuel(random.nextIntBetweenInclusive(45, 125));
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.travel().increaseFlightTime(100);
                }), 110L, Component.translatable("sequence.ait.ground_unstable").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new LandTypeControl(), new YControl(), new LoadWaypointControl()));

        INCREMENT_SCALE_RECALCULATION_NECESSARY = register(Sequence.Builder
                .create(AITMod.id("increment_scale_recalculation_necessary"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(50);
                    finishedTardis.addFuel(random.nextIntBetweenInclusive(45, 125));
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.travel().increaseFlightTime(100);
                }), 80L, Component.translatable("sequence.ait.increment_scale_recalculation_necessary").withStyle(ChatFormatting.ITALIC,
                        ChatFormatting.YELLOW), new IncrementControl()));

        SMALL_DEBRIS_FIELD = register(
                Sequence.Builder.create(AITMod.id("small_debris_field"), (finishedTardis -> {
                    finishedTardis.travel().decreaseFlightTime(75);
                    finishedTardis.addFuel(random.nextIntBetweenInclusive(45, 125));
                }), (missedTardis -> {
                    missedTardis.removeFuel(random.nextIntBetweenInclusive(45, 125));
                    missedTardis.travel().increaseFlightTime(150);
                }), 80L, Component.translatable("sequence.ait.small_debris_field").withStyle(ChatFormatting.ITALIC, ChatFormatting.YELLOW),
                        new IncrementControl(), new ShieldsControl()));
    }
}
