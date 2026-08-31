package dev.amble.ait.core.tardis.util;

import java.util.*;
import java.util.function.Predicate;

import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import it.unimi.dsi.fastutil.longs.LongBidirectionalIterator;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.AbortableIterationConsumer;
import net.minecraft.core.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.ExtraPushableEntity;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.blockentities.DoorBlockEntity;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.mixin.lookup.EntityTrackingSectionAccessor;
import dev.amble.ait.mixin.lookup.SectionedEntityCacheAccessor;
import dev.amble.ait.mixin.lookup.SimpleEntityLookupAccessor;
import dev.amble.ait.mixin.lookup.WorldInvoker;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedBlockPos;
import dev.amble.lib.platform.util.TriState;
import dev.amble.lib.util.ServerLifecycleHooks;
import dev.amble.lib.util.TeleportUtil;

@SuppressWarnings("unused")
public class TardisUtil {

    public static final ResourceLocation REGION_LANDING_CODE = AITMod.id("region_landing_code");
    public static final ResourceLocation SNAP = AITMod.id("snap");
    public static final ResourceLocation FLYING_SPEED = AITMod.id("flying_speed");
    public static final ResourceLocation TOGGLE_ANTIGRAVS = AITMod.id("toggle_antigravs");
    public static final ExplosionDamageCalculator EXPLOSION_BEHAVIOR = new ExplosionDamageCalculator() {
        @Override
        public boolean shouldBlockExplode(Explosion explosion, BlockGetter world, BlockPos pos, BlockState state, float power) {
            MinecraftServer server = ServerLifecycleHooks.get();
            if (server == null) return false;
            if (!server.getGameRules().getBoolean(AITMod.TARDIS_GRIEFING)) return false;

            return super.shouldBlockExplode(explosion, world, pos, state, power);
        }
    };

    public static boolean doCreateFire(Level world) {
        return world.getGameRules().getBoolean(AITMod.TARDIS_FIRE_GRIEFING);
    }

    private static boolean notPilot(ServerTardis tardis, ServerPlayer player) {
        return !tardis.loyalty().get(player).isOf(Loyalty.Type.PILOT);
    }

    public static void init() {
        AitNetworking.registerServerReceiver(SNAP, (server, player, handler, buf, responseSender) -> {
            UUID uuid = buf.readUUID();
            ServerTardisManager.getInstance().getTardis(server, uuid, tardis -> {
                if (notPilot(tardis, player))
                    return;

                if (tardis.flight().isFlying()) {
                    server.execute(() -> {
                        if (!player.isShiftKeyDown()) {
                            tardis.door().interactAllDoors(player.serverLevel(), null, player, true);
                        } else {
                            tardis.door().interactToggleLock(player);
                        }
                    });

                    return;
                }

                player.level().playSound(null, player.blockPosition(), AITSounds.SNAP, SoundSource.PLAYERS, 4f, 1f);

                BlockPos exteriorPos = tardis.travel().position().getPos();

                BlockPos pos = TardisServerWorld.isTardisDimension(player.serverLevel())
                        ? tardis.getDesktop().getDoorPos().getPos()
                        : exteriorPos;

                if ((player.distanceToSqr(exteriorPos.getX(), exteriorPos.getY(), exteriorPos.getZ())) > 200
                        && (tardis.hasWorld() && player.level() != tardis.world()))
                    return;

                server.execute(() -> {
                    if (!player.isShiftKeyDown()) {
                        tardis.door().interact(player.serverLevel(), null, player);
                    } else {
                        boolean isLocked = tardis.door().locked();
                        tardis.door().interactToggleLock(player);
                        player.level().playSound(
                                null,
                                pos,
                                isLocked ? AITSounds.REMOTE_UNLOCK : AITSounds.REMOTE_LOCK,
                                SoundSource.BLOCKS,
                                1.0F,
                                1.0F
                        );
                    }
                });
            });
        });

        AitNetworking.registerServerReceiver(FLYING_SPEED, (server, player, handler, buf, responseSender) -> {
            UUID uuid = buf.readUUID();
            String direction = buf.readUtf();
            ServerTardisManager.getInstance().getTardis(server, uuid, tardis -> {
                if (notPilot(tardis, player)) return;
                if (!tardis.flight().isFlying()) return;
                switch (direction) {
                    case "up":
                        tardis.travel().increaseSpeed();
                        break;
                    case "down":
                        tardis.travel().decreaseSpeed();
                        break;
                }
            });
        });
        AitNetworking.registerServerReceiver(TOGGLE_ANTIGRAVS, (server, player, handler, buf, responseSender) -> {
            UUID uuid = buf.readUUID();
            ServerTardisManager.getInstance().getTardis(server, uuid, tardis -> {
                if (notPilot(tardis, player)) return;
                if (!tardis.flight().isFlying()) return;
                tardis.travel().antigravs().toggle();
            });
        });
    }

    public static boolean inBox(AABB a, AABB b) {
        return a.minX < b.maxX && a.maxX > b.minX && a.minZ < b.maxZ && a.maxZ > b.minZ;
    }

    public static Vec3 offsetInteriorDoorPosition(Tardis tardis) {
        return TardisUtil.offsetInteriorDoorPosition(tardis.getDesktop());
    }

    public static Vec3 offsetInteriorDoorPosition(TardisDesktop desktop) {
        return TardisUtil.offsetInteriorDoorPos(desktop.getDoorPos());
    }

    public static Vec3 offsetDoorPosition(Vec3 pos, byte rotation) {
        return switch (rotation) {
            case 1, 2, 3 -> new Vec3(pos.x() + 1.1f, pos.y(), pos.z() - 0.5f);
            case 4 -> new Vec3(pos.x() + 1.5f, pos.y(), pos.z() + 0.5f);
            case 5, 6, 7 -> new Vec3(pos.x() + 1.5f, pos.y(), pos.z() + 1.1f);
            case 8 -> new Vec3(pos.x() + 0.5f, pos.y(), pos.z() + 1.5f);
            case 9, 10, 11 -> new Vec3(pos.x(), pos.y(), pos.z() + 1.5f);
            case 12 -> new Vec3(pos.x() - 0.5f, pos.y(), pos.z() + 0.5f);
            case 13, 14, 15 -> new Vec3(pos.x() - 0.3f, pos.y(), pos.z() - 0.5f);
            default -> new Vec3(pos.x() + 0.5f, pos.y(), pos.z() - 0.5f);
        };
    }

    public static Vec3 offsetDoorPosition(DirectedBlockPos directed) {
        return offsetDoorPosition(new Vec3(directed.getPos().getX(), directed.getPos().getY(), directed.getPos().getZ()), directed.getRotation());
    }

    public static Vec3 offsetInteriorDoorPos(DirectedBlockPos directed) {
        BlockPos pos = directed.getPos();

        return switch (directed.getRotation()) {
            case 4 -> new Vec3(pos.getX() + 0.4f, pos.getY(), pos.getZ() + 0.5f);
            case 8 -> new Vec3(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.4f);
            case 12 -> new Vec3(pos.getX() + 0.6f, pos.getY(), pos.getZ() + 0.5f);
            default -> new Vec3(pos.getX() + 0.5f, pos.getY(), pos.getZ() + 0.6f);
        };
    }

    // TODO - move to amblekit
    public static Vec3 offsetPos(DirectedBlockPos directed, float value) {
        BlockPos pos = directed.getPos();

        return new Vec3(pos.getX() + value * (double) directed.getVector().getX(),
                pos.getY() + value * (double) directed.getVector().getY(),
                pos.getZ() + value * (double) directed.getVector().getZ());
    }

    public static void teleportOutside(Tardis tardis, Entity entity) {
        TardisEvents.LEAVE_TARDIS.invoker().onLeave(tardis, entity);
        TardisUtil.teleportWithDoorOffset(tardis.travel().position().getWorld(), entity,
                tardis.travel().position().toPos());
    }

    public static void dropOutside(Tardis tardis, Entity entity) {
        TardisEvents.LEAVE_TARDIS.invoker().onLeave(tardis, entity);

        if (!(entity instanceof LivingEntity living))
            return;

        CachedDirectedGlobalPos percentageOfDestination = tardis.travel().getProgress();
        Scheduler scheduler = Scheduler.get();

        ServerLevel vortexWorld = WorldUtil.getTimeVortex();

        if (vortexWorld == null)
            return;

        TeleportUtil.teleport(living, vortexWorld, new Vec3(vortexWorld.getRandom().nextIntBetweenInclusive(0, 256), 0, vortexWorld.getRandom().nextIntBetweenInclusive(0, 256)), living.getVisualRotationYInDegrees());

        scheduler.runTaskLater(() -> {
            if (living.level() == vortexWorld) {
                TeleportUtil.teleport(living, tardis.travel().destination().getWorld(),
                        percentageOfDestination.getPos().getCenter(), living.getVisualRotationYInDegrees());
            }
        }, TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 4);
    }

    public static void teleportInside(ServerTardis tardis, Entity entity) {
        if (TardisEvents.ENTER_TARDIS.invoker().onEnter(tardis, entity) == TardisEvents.Interaction.FAIL) return;
        TardisUtil.teleportWithDoorOffset(tardis.world(), entity, tardis.getDesktop().getDoorPos());
    }

    public static void teleportToInteriorPosition(ServerTardis tardis, Entity entity, BlockPos pos) {
        if (entity instanceof ServerPlayer player) {
            if (TardisEvents.ENTER_TARDIS.invoker().onEnter(tardis, entity) == TardisEvents.Interaction.FAIL) return;

            WorldUtil.teleportToWorld(player, tardis.world(),
                    new Vec3(pos.getX(), pos.getY(), pos.getZ()), entity.getYRot(), player.getXRot());

            player.connection.send(new ClientboundSetEntityMotionPacket(player));
        }
    }

    private static void teleportWithDoorOffset(ServerLevel world, Entity entity, DirectedBlockPos directed) {
        if (!AITMod.CONFIG.tntCanTeleportThroughDoors && entity instanceof PrimedTnt) {
            return;
        }

        if (entity instanceof ExtraPushableEntity pushable && pushable.ait$pushBehaviour() == TriState.FALSE)
            return;

        BlockPos pos = directed.getPos();
        boolean isDoor = world.getBlockEntity(pos) instanceof DoorBlockEntity;

        Vec3 vec = isDoor
                ? TardisUtil.offsetInteriorDoorPos(directed)
                : TardisUtil.offsetDoorPosition(directed).add(0, 0.125, 0);

        world.getServer().execute(() -> {
            if (entity.getVehicle() instanceof FlightTardisEntity)
                return;

            if (entity instanceof ExtraPushableEntity pushable)
                pushable.ait$setPushBehaviour(TriState.FALSE);

            if (entity instanceof ServerPlayer player) {
                WorldUtil.teleportToWorld(player, world, vec,
                        RotationSegment.convertToDegrees(directed.getRotation()) + (isDoor ? 0 : 180f),
                        player.getXRot());

                player.connection.send(new ClientboundSetEntityMotionPacket(player));
            } else {
                if (entity.getType().is(AITTags.EntityTypes.BOSS))
                    return;

                if (entity.level().dimension() == world.dimension()) {
                    entity.moveTo(offset(vec, directed, -0.5f).x, vec.y,
                            offset(vec, directed, -0.5f).z,
                            RotationSegment.convertToDegrees(directed.getRotation()) + (isDoor ? 0 : 180f),
                            entity.getXRot());
                } else {
                    entity.teleportTo(world, offset(vec, directed, -0.5f).x, vec.y, offset(vec, directed, -0.5f).z,
                            Set.of(),
                            RotationSegment.convertToDegrees(directed.getRotation()) + (isDoor ? 0 : 180f),
                            entity.getXRot());
                }
            }
            if (entity instanceof ExtraPushableEntity pushable)
                Scheduler.get().runTaskLater(() -> pushable.ait$setPushBehaviour(TriState.DEFAULT),
                        TaskStage.END_SERVER_TICK, TimeUnit.SECONDS, 3);
        });
    }

    public static Vec3 offset(Vec3 vec, DirectedBlockPos direction, double value) {
        Vec3i vec3i = direction.getVector();

        return new Vec3(vec.x + value * (double) vec3i.getX(), vec.y + value * (double) vec3i.getY(),
                vec.z + value * (double) vec3i.getZ());
    }

    public static void giveEffectToInteriorPlayers(ServerTardis tardis, MobEffectInstance effect) {
        for (Player player : tardis.world().players()) {
            player.addEffect(effect);
        }
    }

    public static @Nullable Player getAnyPlayerInsideInterior(ServerLevel world) {
        for (Player player : world.players()) {
            return player;
        }
        return null;
    }

    public static <T extends Entity> List<T> getEntitiesInBox(Class<T> clazz, Level world, AABB box,
            Predicate<T> predicate) {
        return fastFlatLookup(clazz, world, box, predicate);
    }

    private static <T extends EntityAccess> void forEachInFlatBox(SectionedEntityCacheAccessor<T> accessor, AABB box,
            AbortableIterationConsumer<EntitySection<T>> consumer) {
        int j = SectionPos.posToSectionCoord(box.minX - 2.0);
        int l = SectionPos.posToSectionCoord(box.minZ - 2.0);

        int m = SectionPos.posToSectionCoord(box.maxX + 2.0);
        int o = SectionPos.posToSectionCoord(box.maxZ + 2.0);

        for (int p = j; p <= m; p++) {
            long q = SectionPos.asLong(p, 0, 0);
            long r = SectionPos.asLong(p, -1, -1);

            LongBidirectionalIterator longIterator = accessor.getTrackedPositions().subSet(q, r + 1L).iterator();

            while (longIterator.hasNext()) {
                long s = longIterator.nextLong();
                int u = SectionPos.z(s);

                if (u < l || u > o)
                    continue;

                EntitySection<T> section = accessor.getTrackingSections().get(s);

                if (section == null || section.isEmpty() || !section.getStatus().isAccessible()
                        || !consumer.accept(section).shouldAbort())
                    continue;

                return;
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static <U extends T, T extends EntityAccess> void forEachIntersects(SectionedEntityCacheAccessor<T> cache,
            EntityTypeTest<T, U> filter, AABB box, AbortableIterationConsumer<U> consumer) {
        TardisUtil.forEachInFlatBox(cache, box, section -> {
            EntityTrackingSectionAccessor<T> accessor = (EntityTrackingSectionAccessor<T>) section;
            Collection<T> collection = accessor.getCollection().find((Class<T>) filter.getBaseClass());

            if (collection.isEmpty())
                return AbortableIterationConsumer.Continuation.CONTINUE;

            for (T entityLike : collection) {
                U downcast = filter.tryCast(entityLike);

                if (downcast == null || !TardisUtil.inBox(entityLike.getBoundingBox(), box)
                        || !consumer.accept(downcast).shouldAbort())
                    continue;

                return AbortableIterationConsumer.Continuation.ABORT;
            }

            return AbortableIterationConsumer.Continuation.CONTINUE;
        });
    }

    @SuppressWarnings("unchecked")
    private static <T extends Entity> List<T> fastFlatLookup(Class<T> clazz, Level world, AABB box,
            Predicate<T> predicate) {
        List<T> result = new ArrayList<>();
        world.getProfiler().incrementCounter("getEntities");

        SectionedEntityCacheAccessor<T> cache = (SectionedEntityCacheAccessor<T>) ((SimpleEntityLookupAccessor<T>) ((WorldInvoker) world)
                .getEntityLookup()).getCache();

        TardisUtil.forEachIntersects(cache, EntityTypeTest.forClass(clazz), box, entity -> {
            if (predicate.test(entity))
                result.add(entity);

            return AbortableIterationConsumer.Continuation.CONTINUE;
        });

        return result;
    }

    public static List<LivingEntity> getLivingEntitiesInInterior(Tardis tardis, int area) {
        DirectedBlockPos directedPos = tardis.getDesktop().getDoorPos();

        if (directedPos == null)
            return List.of();

        BlockPos pos = tardis.getDesktop().getDoorPos().getPos();

        return tardis.asServer().world().getEntitiesOfClass(LivingEntity.class,
                AABB.encapsulatingFullBlocks(pos.north(area).east(area).above(area), pos.south(area).west(area).below(area)), (e) -> true);
    }

    public static List<Entity> getEntitiesInInterior(Tardis tardis, int area) {
        DirectedBlockPos directedPos = tardis.getDesktop().getDoorPos();

        if (directedPos == null)
            return List.of();

        BlockPos pos = directedPos.getPos();

        return tardis.asServer().world().getEntitiesOfClass(Entity.class,
                AABB.encapsulatingFullBlocks(pos.north(area).east(area).above(area), pos.south(area).west(area).below(area)), e -> true);
    }

    public static List<LivingEntity> getLivingEntitiesInInterior(ServerTardis tardis) {
        return getLivingEntitiesInInterior(tardis, 20);
    }

    public static boolean isInteriorEmpty(ServerTardis tardis) {
        return tardis.world().players().isEmpty();
    }

    public static void sendMessageToInterior(ServerTardis tardis, Component text) {
        for (ServerPlayer player : tardis.world().players()) {
            player.displayClientMessage(text, true);
        }
    }

    public static void sendMessageToLinked(ServerTardis tardis, Component message) {
        NetworkUtil.getLinkedPlayers(tardis).forEach(player -> player.displayClientMessage(message, true));
    }

    public static Optional<ServerPlayer> findNearestPlayer(CachedDirectedGlobalPos position) {
        ServerLevel world = position.getWorld();
        BlockPos pos = position.getPos();
        ServerPlayer nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;

        for (ServerPlayer player : world.players()) {
            double distance = player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ());
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestPlayer = player;
            }
        }

        return Optional.ofNullable(nearestPlayer);
    }

    public static boolean isNearTardis(Player player, Tardis tardis, double radius) {
        return radius >= distanceFromTardis(player, tardis);
    }

    public static double distanceFromTardis(Player player, Tardis tardis) {
        BlockPos pPos = player.blockPosition();
        BlockPos tPos = tardis.travel().position().getPos();
        return Math.sqrt(tPos.distSqr(pPos));
    }

    public static double estimatedFuelCost(Player player, Tardis tardis, double distance) {
        int speed = Math.max(tardis.travel().speed(), 1);
        double ticksRequired = distance / speed;
        double perTick = FuelHandler.getPerTickFuelCost(speed, tardis.travel().instability(), tardis.travel().autopilot());
        return perTick * ticksRequired;
    }
}
