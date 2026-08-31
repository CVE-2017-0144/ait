package dev.amble.ait.client.sounds.flight;

import dev.amble.ait.client.sounds.PositionedLoopingSound;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.sounds.flight.FlightSound;
import net.minecraft.client.Minecraft;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;

/**
 * Not to be confused with {@link dev.amble.ait.core.tardis.animation.v2.TardisAnimation}'s ability of playing mat/demat sounds.
 * Yeah.
 */
public class ExteriorFlightSound extends PositionedLoopingSound implements FlightSoundPlayer {
    private FlightSound data;
    private int ticks = 0;
    private boolean dirty = true;

    public ExteriorFlightSound(FlightSound data, SoundSource soundCategory) {
        super(data.sound(), soundCategory, new BlockPos(0,0,0));
        this.data = data;
    }

    @Override
    public ResourceLocation getLocation() {
        return data.soundId();
    }

    @Override
    public WeighedSoundEvents resolve(SoundManager soundManager) {
        if (this.getLocation().equals(SoundManager.INTENTIONALLY_EMPTY_SOUND_LOCATION)) {
            this.sound = SoundManager.INTENTIONALLY_EMPTY_SOUND;
            return SoundManager.INTENTIONALLY_EMPTY_SOUND_EVENT;
        } else {
            WeighedSoundEvents weightedSoundSet = soundManager.getSoundEvent(this.getLocation());
            if (weightedSoundSet == null) {
                this.sound = SoundManager.EMPTY_SOUND;
            } else {
                this.sound = weightedSoundSet.getSound(this.random);
            }

            return weightedSoundSet;
        }
    }

    @Override
    public ClientTardis tardis() {
        return ClientTardisUtil.getNearestTardis(ClientFlightHandler.MAX_DISTANCE).orElse(null);
    }

    @Override
    public void tick() {
        super.tick();
        this.ticks++;

        if (this.ticks >= (this.getData().length() / this.pitch)) {
            this.refresh();
        }
    }

    @Override
    public float getProgress() {
        if (this.data == null) return 0f;
        return (float) this.ticks / (this.data.length() / this.pitch);
    }

    @Override
    public void refresh() {
        ClientTardis tardis = tardis();
        this.pitch = FlightSoundPlayer.getRandomPitch(tardis);

        BlockPos pos = tardis != null && tardis.travel().isLanded() ? tardis.travel().position().getPos() : BlockPos.ZERO;

        this.setPosition(pos);
        this.ticks = 0;

        if (this.dirty || tardis == null) {
            Minecraft.getInstance().getSoundManager().stop(this);
        }

        this.dirty = true;
    }

    @Override
    public FlightSound getData() {
        if (this.data == null && tardis() != null)
            this.data = tardis().stats().getFlightEffects();

        return this.data;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
