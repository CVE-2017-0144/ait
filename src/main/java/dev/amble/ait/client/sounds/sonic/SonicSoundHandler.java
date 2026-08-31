package dev.amble.ait.client.sounds.sonic;

import java.util.HashMap;
import java.util.UUID;
import net.minecraft.client.player.AbstractClientPlayer;
import dev.amble.ait.api.ClientWorldEvents;
import dev.amble.ait.client.sounds.ClientSoundManager;

public class SonicSoundHandler {
    private final HashMap<UUID, SonicSound> sounds;

    static {
        ClientWorldEvents.CHANGE_WORLD.register((client, world) -> {
            SonicSoundHandler handler = ClientSoundManager.getSonicSound();
            handler.sounds.values().forEach(SonicSound::stopSonic);
            handler.sounds.clear();
        });
    }

    public SonicSoundHandler() {
        this.sounds = new HashMap<>();
    }

    public SonicSound get(AbstractClientPlayer player) {
        return this.sounds.computeIfAbsent(
                player.getUUID(),
                uuid -> new SonicSound(player)
        );
    }

    public void onUse(AbstractClientPlayer user) {
        this.get(user).onUse();
    }

    public void onFinishUse(AbstractClientPlayer user) {
        this.get(user).onFinishUse();
    }
}
