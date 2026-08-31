package dev.amble.ait.client.sounds.flight;

import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.sounds.LoopingSound;
import dev.amble.ait.client.sounds.PlayerFollowingLoopingSound;
import dev.amble.ait.client.sounds.SoundHandler;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.AITSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;

public class ClientFlightMusicHandler extends SoundHandler {

    public static LoopingSound FLIGHT;

    public LoopingSound getFlightMusic() {
        if (FLIGHT == null)
            FLIGHT = createFlightMusic();

        return FLIGHT;
    }

    private LoopingSound createFlightMusic() {
        return new PlayerFollowingLoopingSound(AITSounds.FLIGHT, SoundSource.MUSIC, AITModClient.CONFIG.flightMusicVolume);
    }

    public static ClientFlightMusicHandler create() {
        ClientFlightMusicHandler handler = new ClientFlightMusicHandler();

        handler.generate();
        return handler;
    }

    private void generate() {
        if (FLIGHT == null)
            FLIGHT = createFlightMusic();

        this.ofSounds(FLIGHT);
    }

    public void tick(Minecraft client) {
        ClientTardis tardis = ClientTardisUtil.getCurrentTardis();

        if (tardis == null) return;

        if (this.sounds == null)
            this.generate();

        if (tardis.travel().isLanded() || tardis.travel().autopilot()) {
            this.stopSounds();
            return;
        }

        this.startIfNotPlaying(this.getFlightMusic());

        float vol = switch (tardis.travel().getState()) {
            case FLIGHT -> AITModClient.CONFIG.flightMusicVolume;
            case LANDED -> 0.0F;
            case DEMAT -> Mth.lerp(0.05f, this.getFlightMusic().getVolume(), AITModClient.CONFIG.flightMusicVolume);
            case MAT -> Mth.lerp(0.05f, this.getFlightMusic().getVolume(), 0f);
        };

        this.getFlightMusic().setVolume(vol);
    }
}
