package dev.amble.ait.core.tardis.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITStatusEffects;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class ShieldHandler extends KeyedTardisComponent implements TardisTickable {
    private static final BoolProperty IS_SHIELDED = new BoolProperty("is_shielded", false);
    private final BoolValue isShielded = IS_SHIELDED.create(this);
    public static BoolProperty IS_VISUALLY_SHIELDED = new BoolProperty("is_visually_shielded", false);
    private final BoolValue isVisuallyShielded = IS_VISUALLY_SHIELDED.create(this);

    private int shieldAmbienceTicks = 0;

    public ShieldHandler() {
        super(Id.SHIELDS);
    }

    @Override
    public void onLoaded() {
        isShielded.of(this, IS_SHIELDED);
        isVisuallyShielded.of(this, IS_VISUALLY_SHIELDED);
    }

    public BoolValue shielded() {
        return isShielded;
    }

    public BoolValue visuallyShielded() {
        return isVisuallyShielded;
    }

    public void enable() {
        this.shielded().set(true);
        TardisEvents.TOGGLE_SHIELDS.invoker().onShields(this.tardis, true, this.visuallyShielded().get());
    }

    public void disable() {
        this.shielded().set(false);
        TardisEvents.TOGGLE_SHIELDS.invoker().onShields(this.tardis, false, this.visuallyShielded().get());
    }

    public void toggle() {
        if (this.shielded().get())
            this.disable();
        else
            this.enable();
    }

    public void enableVisuals() {
        this.visuallyShielded().set(true);
        TardisEvents.TOGGLE_SHIELDS.invoker().onShields(this.tardis, this.shielded().get(), true);
    }

    public void disableVisuals() {
        this.visuallyShielded().set(false);
        TardisEvents.TOGGLE_SHIELDS.invoker().onShields(this.tardis, this.shielded().get(), false);
    }

    public void toggleVisuals() {
        if (this.visuallyShielded().get())
            this.disableVisuals();
        else
            this.enableVisuals();
    }

    public void disableAll() {
        this.disableVisuals();
        this.disable();
    }

    @Override
    public void tick(MinecraftServer server) {
        if (!this.shielded().get() || !this.tardis.subsystems().shields().isEnabled() || this.tardis().subsystems().shields().isBroken())
            return;

        TravelHandler travel = tardis.travel();

        if (!this.tardis.fuel().hasPower())
            this.disableAll();

        if (travel.getState() == TravelHandlerBase.State.FLIGHT)
            return;

        tardis.removeFuel(2 * travel.instability()); // idle drain of 2 fuel per tick
        CachedDirectedGlobalPos globalExteriorPos = travel.position();

        Level world = globalExteriorPos.getWorld();
        BlockPos exteriorPos = globalExteriorPos.getPos();

        if (this.visuallyShielded().get()) {
            shieldAmbienceTicks++;
            if (shieldAmbienceTicks >= 44) {
                shieldAmbienceTicks = 0;
                tardis.getExterior().playSound(AITSounds.SHIELD_AMBIANCE, SoundSource.BLOCKS, 2f, 0.7f);
            }
        }
        world.getEntities(null, new AABB(exteriorPos).inflate(8f)).stream()
                .filter(entity -> entity.isPushable() || entity instanceof Projectile)
                .forEach(entity -> {
                    if (entity instanceof ServerPlayer player) {
                        if (!canPush(player)) {
                            if (entity.isUnderWater()) {
                                player.addEffect(
                                        new MobEffectInstance(MobEffects.WATER_BREATHING, 15, 3, true, false, false));
                            }
                            if (entity.level().dimension().equals(AITDimensions.SPACE)) {
                                player.addEffect(
                                        new MobEffectInstance(AITStatusEffects.OXYGENATED, 20, 1, true, false));
                            }
                            return;
                        }
                    }
                    if (this.visuallyShielded().get()) {
                        Vec3 centerExteriorPos = exteriorPos.getCenter();

                        if (entity.distanceToSqr(centerExteriorPos) <= 8f) {
                            Vec3 motion = entity.blockPosition().getCenter().subtract(centerExteriorPos).normalize()
                                    .scale(0.1f);

                            if (entity instanceof Projectile projectile) {
                                BlockPos pos = projectile.blockPosition();

                                if (projectile instanceof ThrownTrident) {
                                    projectile.getDeltaMovement().add(motion.scale(2f));

                                    world.playSound(null, pos, SoundEvents.TRIDENT_HIT, SoundSource.BLOCKS, 1f,
                                            1f);
                                    return;
                                }

                                world.playSound(null, pos, SoundEvents.GENERIC_BURN, SoundSource.BLOCKS, 1f,
                                        1f);

                                projectile.discard();
                                return;
                            }

                            entity.setDeltaMovement(entity.getDeltaMovement().add(motion.scale(2f)));

                            entity.hasImpulse = true;
                            entity.hurtMarked = true;
                        }
                    }
                });
    }

    /**
     * Checks
     * - Loyalty > COMPANION
     * - Has linked key
     * @param entity the entity to check
     * @return true if the entity will be repulsed by the shield
     */
    private boolean canPush(ServerPlayer entity) {
        boolean companion = tardis.loyalty().get(entity).isOf(Loyalty.Type.COMPANION);

        return !(companion || SecurityControl.hasMatchingKey(entity, this.tardis()));
    }
}
