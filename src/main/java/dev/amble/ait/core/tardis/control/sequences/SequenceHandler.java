package dev.amble.ait.core.tardis.control.sequences;

import java.util.UUID;

import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.ait.registry.impl.SequenceRegistry;

public class SequenceHandler extends KeyedTardisComponent implements TardisTickable {

    private static final RandomSource RANDOM = RandomSource.create();
    private static final BoolProperty HAS_ACTIVE_SEQUENCE = new BoolProperty("has_active_sequence", false);

    @Exclude
    private RecentControls recent;

    @Exclude(strategy = Exclude.Strategy.NETWORK)
    private UUID playerUUID;

    @Exclude(strategy = Exclude.Strategy.NETWORK)
    private int ticks = 0;

    @Exclude
    private Sequence activeSequence;

    /**
     * This is for the client to recognize whether or not a sequence is active
     */
    private final BoolValue hasActiveSequence = HAS_ACTIVE_SEQUENCE.create(this);

    public SequenceHandler() {
        super(Id.SEQUENCE);
    }

    @Override
    protected void onInit(InitContext ctx) {
        recent = new RecentControls(tardis.getUuid());
        activeSequence = null;
    }

    @Override
    public void postInit(InitContext ctx) {
        hasActiveSequence.set(false);
    }

    @Override
    public void onLoaded() {
        hasActiveSequence.of(this, HAS_ACTIVE_SEQUENCE);
    }

    public void setActivePlayer(ServerPlayer player) {
        this.playerUUID = player.getUUID();
    }

    public ServerPlayer getActivePlayer() {
        if (this.playerUUID == null)
            return null;

        ServerLevel world = this.tardis.asServer().world();

        if (world == null)
            return null;

        return (ServerPlayer) world.getPlayerByUUID(this.playerUUID);
    }

    public void add(Control control, ServerPlayer player, BlockPos console) {
        if (this.getActiveSequence() == null || recent == null)
            return;

        recent.add(control);
        ticks = 0;

        this.setActivePlayer(player);
        this.doesControlIndexMatch(control);
        this.compareToSequences(console);
    }

    public boolean doesControlIndexMatch(Control control) {
        if (recent == null || this.getActiveSequence() == null)
            return false;

        if (recent.indexOf(control) != this.getActiveSequence().getControls().indexOf(control)) {
            recent.remove(control);
            return false;
        }

        return true;
    }

    public boolean hasActiveSequence() {
        return this.activeSequence != null;
    }

    public boolean hasClientActiveSequence() {
        return this.hasActiveSequence.get();
    }

    public void setActiveSequence(@Nullable Sequence sequence, boolean setTicksTo0) {
        if (setTicksTo0)
            this.ticks = 0;

        this.activeSequence = sequence;
        this.hasActiveSequence.set(this.activeSequence != null);

        if (this.activeSequence == null)
            return;

        this.activeSequence.sendMessageToInteriorPlayers(tardis.asServer().world().players());
    }

    public void triggerRandomSequence(boolean setTicksTo0) {
        if (setTicksTo0)
            ticks = 0;

        int rand = RANDOM.nextIntBetweenInclusive(0, SequenceRegistry.REGISTRY.size());
        Sequence sequence = SequenceRegistry.REGISTRY.byId(rand);

        if (sequence == null)
            return;

        this.activeSequence = sequence;
        this.hasActiveSequence.set(true);
        this.activeSequence.sendMessageToInteriorPlayers(tardis.asServer().world().players());

        this.tardis().getDesktop().playSoundAtEveryConsole(SoundEvents.BEACON_POWER_SELECT);
    }

    @Nullable public Sequence getActiveSequence() {
        return activeSequence;
    }

    private void compareToSequences(BlockPos console) {
        if (this.getActiveSequence() == null)
            return;

        if (this.recent == null)
            this.recent = new RecentControls(this.tardis().getUuid());

        if (this.getActiveSequence().isFinished(this.recent)) {
            recent.clear();
            this.getActiveSequence().execute(this.tardis(), this.getActivePlayer());

            this.doCompletedControlEffects(console);
            this.setActiveSequence(null, true);
        } else if (this.getActiveSequence().wasMissed(this.recent, ticks)) {
            recent.clear();
            this.getActiveSequence().executeMissed(this.tardis(), this.getActivePlayer());

            this.doMissedControlEffects(console);
            this.setActiveSequence(null, true);
        } else if (recent.size() >= this.getActiveSequence().getControls().size()) {
            recent.clear();
        }
    }

    private void doMissedControlEffects(@Nullable BlockPos console) {
        ServerLevel world = this.tardis.asServer().world();

        if (console == null) {
            this.tardis.getDesktop().getConsolePos().forEach(pos -> SequenceHandler.missedControlEffects(world, pos));
            return;
        }

        SequenceHandler.missedControlEffects(world, console);
    }

    public static void missedControlEffects(ServerLevel world, BlockPos pos) {
        TardisDesktop.playSoundAtConsole(world, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 3f, 1f);
        Vec3 vec3d = Vec3.atBottomCenterOf(pos).add(0.0, 1.2f, 0.0);

        world.sendParticles(ParticleTypes.SMALL_FLAME, vec3d.x(), vec3d.y(), vec3d.z(), 20, 0.4F, 1F, 0.4F,
                5.0F);
        world.sendParticles(ParticleTypes.ANGRY_VILLAGER, vec3d.x(), vec3d.y(), vec3d.z(), 1, 0.4F, 1F, 0.4F,
                0.5F);
        world.sendParticles(ParticleTypes.LAVA, vec3d.x(), vec3d.y(), vec3d.z(), 7, 0.4F, 1F, 0.4F,
                0.5F);
        world.sendParticles(ParticleTypes.FLASH, vec3d.x(), vec3d.y(), vec3d.z(), 4, 0.4F, 1F, 0.4F, 5.0F);
        world.sendParticles(new DustParticleOptions(new Vector3f(0.2f, 0.2f, 0.2f), 4f), vec3d.x(), vec3d.y(),
                vec3d.z(), 20, 0.0F, 1F, 0.0F, 2.0F);
    }

    private void doCompletedControlEffects(@Nullable BlockPos console) {
        ServerLevel world = this.tardis.asServer().world();

        if (console == null) {
            this.tardis.getDesktop().getConsolePos().forEach(pos -> SequenceHandler.completedControlEffects(world, pos));
            return;
        }

        SequenceHandler.completedControlEffects(world, console);
    }

    public static void completedControlEffects(ServerLevel world, BlockPos pos) {
        TardisDesktop.playSoundAtConsole(world, pos, SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 3f, 1f);
        Vec3 vec3d = Vec3.atBottomCenterOf(pos).add(0.0, 1.2f, 0.0);

        spawnControlParticles(world, vec3d);
        world.sendParticles(ParticleTypes.HEART, vec3d.x(), vec3d.y(), vec3d.z(), 1, 0.4F, 1F, 0.4F, 0.5F);
    }

    public static void spawnControlParticles(ServerLevel world, Vec3 vec3d) {
        world.sendParticles(ParticleTypes.GLOW, vec3d.x(), vec3d.y(), vec3d.z(), 12, 0.4F, 1F, 0.4F, 5.0F);
        world.sendParticles(ParticleTypes.ELECTRIC_SPARK, vec3d.x(), vec3d.y(), vec3d.z(), 12, 0.4F, 1F, 0.4F,
                5.0F);
    }

    @Override
    public void tick(MinecraftServer server) {
        if (this.getActiveSequence() == null)
            return;

        this.ticks++;
        if (this.ticks >= this.getActiveSequence().timeToFail()) {
            this.compareToSequences(null);

            this.recent.clear();
            this.ticks = 0;
        }
    }

    public boolean controlPartOfSequence(Control control) {
        if (this.getActiveSequence() == null)
            return false;

        return this.getActiveSequence().controlPartOfSequence(control);
    }
}
