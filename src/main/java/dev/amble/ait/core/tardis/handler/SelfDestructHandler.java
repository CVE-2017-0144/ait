package dev.amble.ait.core.tardis.handler;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class SelfDestructHandler extends KeyedTardisComponent implements TardisTickable {

    private static final BoolProperty QUEUED = new BoolProperty("queued");
    private final BoolValue queued = QUEUED.create(this);

    @Exclude
    private boolean destructing;

    public SelfDestructHandler() {
        super(Id.SELF_DESTRUCT);
    }

    @Override
    public void onLoaded() {
        queued.of(this, QUEUED);
        this.destructing = false;
    }

    public void boom() {
        if (this.isQueued() || !this.canSelfDestruct())
            return;

        this.queued.set(true);
        this.tardis.alarm().enable();
    }

    private void complete() {
        CachedDirectedGlobalPos exterior = tardis.travel().position();
        ServerLevel world = exterior.getWorld();
        BlockPos pos = exterior.getPos();

        this.queued.set(false);

        AITMod.LOGGER.warn("Tardis {} has self destructed, expect lag.", tardis.getUuid());
        world.getServer().executeIfPossible(() -> ServerTardisManager.getInstance().remove(world.getServer(), tardis.asServer()));

        world.explode(null, null, TardisUtil.EXPLOSION_BEHAVIOR, pos.getX(), pos.getY(), pos.getZ(), 50, TardisUtil.doCreateFire(world),
                Level.ExplosionInteraction.MOB);
        world.sendParticles(ParticleTypes.EXPLOSION_EMITTER, pos.getX(), pos.getY(), pos.getZ(), 10, 1, 1, 1, 1);
        world.sendParticles(ParticleTypes.CLOUD, pos.getX(), pos.getY(), pos.getZ(), 100, 1, 1, 1, 1);
        world.sendParticles(ParticleTypes.LARGE_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 250, 1, 1, 1, 1);
        world.sendParticles(ParticleTypes.FLAME, pos.getX(), pos.getY(), pos.getZ(), 50, 1, 1, 1, 1);
        world.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, pos.getX(), pos.getY(), pos.getZ(), 25, 1, 1, 1, 1);
        world.sendParticles(ParticleTypes.SMALL_FLAME, pos.getX(), pos.getY(), pos.getZ(), 10, 1, 1, 1, 1);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX(), pos.getY(), pos.getZ(), 500, 1, 1, 1, 1);
        world.playSound(null, pos, AITSounds.GROAN, SoundSource.BLOCKS, 10f, 0.7f);

        ServerTardisManager.getInstance().remove(world.getServer(), tardis.asServer());
    }

    public boolean isQueued() {
        return queued.get();
    }

    private boolean canSelfDestruct() {
        return tardis.travel().isLanded();
    }

    private void warnPlayers() {
        for (Player player : this.tardis.asServer().world().players()) {
            player.displayClientMessage(Component.translatable("tardis.message.self_destruct.warning").withStyle(ChatFormatting.RED),
                    true);
        }
    }

    @Override
    public void tick(MinecraftServer server) {
        if (!this.isQueued())
            return;

        if (!this.canSelfDestruct()) {
            this.queued.set(false);

            tardis.alarm().disable();
            return;
        }

        if (!TardisUtil.isInteriorEmpty(tardis.asServer())) {
            warnPlayers();
            return;
        }

        if (!this.destructing) {
            tardis.getDesktop().startQueue(true);

            tardis.travel().setTemporaryAnimation(AITMod.id("self_destruct"));
            tardis.travel().onAnimationComplete(this::complete);

            this.destructing = true;
        }
    }
}
