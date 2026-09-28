package dev.amble.ait.core.tardis.handler;

import static dev.amble.ait.core.engine.SubSystem.Id.GRAVITATIONAL;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.engine.impl.GravitationalCircuit;
import dev.amble.ait.core.entities.FallingTardisEntity;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.Scheduler;
import dev.drtheo.scheduler.api.common.TaskStage;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class RealFlightHandler extends KeyedTardisComponent implements TardisTickable {

    private static final ResourceLocation ENTER_FLIGHT = AITMod.id("enter_flight");
    private static final ResourceLocation EXIT_FLIGHT = AITMod.id("exit_flight");

    private static final BoolProperty IS_FALLING = new BoolProperty("falling", false);
    private static final BoolProperty FLYING = new BoolProperty("flying", false);
    private static final BoolProperty SHOULD_FALL = new BoolProperty("should_fall", false);

    private final BoolValue falling = IS_FALLING.create(this);
    private final BoolValue flying = FLYING.create(this);
    private final BoolValue shouldFall = SHOULD_FALL.create(this);

    static {
        TardisEvents.DEMAT.register(tardis -> {
            tardis.flight().flying.set(false);
            return tardis.flight().falling().get() ? TardisEvents.Interaction.FAIL : TardisEvents.Interaction.PASS;
        });
    }

    public RealFlightHandler() {
        super(Id.FLIGHT);
    }

    @Override
    public void onLoaded() {
        falling.of(this, IS_FALLING);
        flying.of(this, FLYING);
        shouldFall.of(this, SHOULD_FALL);
    }

    public boolean isFlying() {
        return flying.get();
    }

    @Override
    public void tick(MinecraftServer server) {
        if (this.falling.get())
            this.tardis.door().setLocked(true);
    }

    public void tickFlight(ServerPlayer player) {
        tardis.travel().forcePosition(cached -> cached.pos(player.blockPosition())
                .rotation((byte) RotationSegment.convertToSegment(player.getYRot())));
        if (player.tickCount % 20 != 0) {
            GravitationalCircuit circuit = tardis.subsystems().get(GRAVITATIONAL);
            if (circuit.isEnabled()) {
                circuit.removeDurability(0.5f);
            }
        }
    }

    public void onLanding(ServerLevel world, BlockPos pos) {
        this.tardis.travel().forcePosition(cached -> cached.world(world.dimension()).pos(pos));

        this.falling.set(false);
        this.tardis.door().setLocked(this.tardis.door().previouslyLocked().get());
        this.tardis.door().setDeadlocked(false);

        world.playSound(null, pos, AITSounds.LAND_THUD, SoundSource.BLOCKS);

        tardis.getDesktop().playSoundAtEveryConsole(AITSounds.LAND_THUD, SoundSource.BLOCKS);
        TardisEvents.LANDED.invoker().onLanded(tardis);
    }

    public void onStartFalling(ServerLevel world, BlockState state, BlockPos pos) {
        this.falling.set(true);
        TardisEvents.START_FALLING.invoker().onStartFall(tardis);

        FallingTardisEntity.spawnFromBlock(world, pos, state);
    }

    public void enterFlight(ServerPlayer player) {
        if (!AITMod.CONFIG.rwfEnabled) return;
        this.tardis.door().closeDoors();
        this.tardis().travel().autopilot(false);
        this.tardis.travel().handbrake(true);
        this.flying.set(true);

        FlightTardisEntity entity = FlightTardisEntity.createAndSpawn(
                player, this.tardis.asServer());

        TardisUtil.teleportOutside(tardis, player);

        Scheduler.get().runTaskLater(() -> {
            player.startRiding(entity);
            this.sendEnterFlightPacket(player);
        }, TaskStage.END_SERVER_TICK, TimeUnit.TICKS, 2);

        tardis.travel().finishDemat();
    }

    private void sendEnterFlightPacket(ServerPlayer player) {
        if (!AITMod.CONFIG.rwfEnabled) return;
        AitNetworking.send(player, ENTER_FLIGHT, AitNetworking.buf());
  }

    public void exitFlight(ServerPlayer player) {
        this.flying.set(false);

        player.setInvisible(false);
        player.setInvulnerable(false);
        this.sendExitFlightPacket(player);

        tardis.travel().forcePosition(cached -> cached.rotation((byte) RotationSegment.convertToSegment(player.getYRot())));
        tardis.travel().placeExterior(false);

        tardis.travel().finishRemat();
    }

    private void sendExitFlightPacket(ServerPlayer player) {
        AitNetworking.send(player, EXIT_FLIGHT, AitNetworking.buf());
    }

    public BoolValue falling() {
        return falling;
    }

    public BoolValue flying() {
        return flying;
    }

    public BoolValue shouldFall() {
        return shouldFall;
    }
}
