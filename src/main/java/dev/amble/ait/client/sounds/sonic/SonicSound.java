package dev.amble.ait.client.sounds.sonic;

import dev.amble.ait.client.sounds.PositionedLoopingSound;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class SonicSound extends PositionedLoopingSound {
    private final AbstractClientPlayer player;
    private boolean hasPlayedOnSound = false;
    private boolean hasPlayedOffSound = false;
    private float lastYaw;
    private float lastPitch;

    public SonicSound(AbstractClientPlayer player) {
        super(AITSounds.SONIC_USE, SoundSource.PLAYERS, player.blockPosition(), 1f, 1f);
        this.player = player;
        this.lastYaw = player.getYRot();
        this.lastPitch = player.getXRot();
    }

    @Override
    public void tick() {
        super.tick();

        if (!shouldPlay(this.player)) {
            this.stopSonic();
            return;
        }

        if (checkAndPlayDuelSound())
            return;


        this.updatePosition();
        this.updatePitchBasedOnCameraMovement();
    }

    private boolean checkAndPlayDuelSound() {

        HitResult hitResult = this.player.pick(16, 0.0f, false);

        if (hitResult.getType() != HitResult.Type.ENTITY) return false;

        EntityHitResult entityHitResult = (EntityHitResult) hitResult;

        if (!(entityHitResult.getEntity() instanceof AbstractClientPlayer otherPlayer)) return false;

        if (!shouldPlay(otherPlayer)) return false;

        if (!hasPlayedOnSound) {
            playSoundAtPlayer(AITSounds.SONIC_DUEL);
            hasPlayedOnSound = true;
            hasPlayedOffSound = false;
            return true;
        }

        return false;
    }

    public static boolean shouldPlay(Player player) {
        return player.isUsingItem() && player.getUseItem().is(AITItems.SONIC_SCREWDRIVER);
    }

    public void play() {
        if (!hasPlayedOnSound) {
            playSoundAtPlayer(AITSounds.SONIC_ON);
            hasPlayedOnSound = true;
            hasPlayedOffSound = false;
        }

        Minecraft.getInstance().getSoundManager().play(this);
    }

    public boolean isPlaying() {
        return Minecraft.getInstance().getSoundManager().isActive(this);
    }

    public void stopSonic() {
        Minecraft.getInstance().getSoundManager().stop(this);

        if (!hasPlayedOffSound) {
            playSoundAtPlayer(AITSounds.SONIC_OFF);
            hasPlayedOffSound = true;
            hasPlayedOnSound = false;
        }
    }

    private void updatePosition() {
        this.setPosition(this.player.blockPosition());
    }

    private void updatePitchBasedOnCameraMovement() {
        float currentYaw = this.player.getYRot();
        float currentPitch = this.player.getXRot();

        float yawChange = Math.abs(currentYaw - lastYaw);
        float pitchChange = Math.abs(currentPitch - lastPitch);

        float totalCameraSpeed = yawChange + pitchChange;

        float newPitch = 1.2f + (totalCameraSpeed * 0.02f);
        newPitch = Math.max(1.2f, Math.min(newPitch, 1.5f));

        this.setPitch(newPitch);

        this.lastYaw = currentYaw;
        this.lastPitch = currentPitch;
    }

    public void onUse() {
        if (!this.isPlaying()) {
            this.play();
        }
    }

    public void onFinishUse() {
        this.stopSonic();
    }

    private void playSoundAtPlayer(SoundEvent sound) {
        Level world = Minecraft.getInstance().level;
        if (world != null) {
            world.playLocalSound(
                    this.player.getX(), this.player.getY(), this.player.getZ(),
                    sound, SoundSource.PLAYERS, 1.0f, 1.0f, false
            );
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof SonicSound other)) return false;
        return this.player != null && other.player != null && this.player.getUUID().equals(other.player.getUUID());
    }
}
