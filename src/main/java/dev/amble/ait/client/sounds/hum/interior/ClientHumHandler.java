package dev.amble.ait.client.sounds.hum.interior;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import dev.amble.ait.api.tardis.TardisClientEvents;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.sounds.ClientSoundManager;
import dev.amble.ait.client.sounds.LoopingSound;
import dev.amble.ait.client.sounds.PlayerFollowingLoopingSound;
import dev.amble.ait.client.sounds.SoundHandler;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.handler.ServerHumHandler;
import dev.amble.ait.data.hum.Hum;
import dev.amble.ait.registry.impl.HumRegistry;

public class ClientHumHandler extends SoundHandler {

    private LoopingSound current;
    private boolean needsReinit = false;
    private boolean suppressed = false;

    static {
        TardisClientEvents.ENTER_CLIENT_TARDIS.register(tardis -> {
            refresh();
        });
    }

    private static void refresh() {
        if (Minecraft.getInstance().level == null)
            return;

        ClientHumHandler handler = ClientSoundManager.getHum();
        handler.stopSounds();
        handler.current = null;
        handler.needsReinit = true;
    }

    protected ClientHumHandler() {
        AitNetworking.registerClientReceiver(ServerHumHandler.SEND, (client, handler, buf, responseSender) -> {
            ResourceLocation id = buf.readResourceLocation();
            SoundInstance sound = findSoundById(id);

            if (sound.getLocation() == SoundEvents.EMPTY.getLocation())
                return;

            if (!(sound instanceof LoopingSound hum))
                return; // it aint a hum.

            ClientTardis tardis = ClientTardisUtil.getCurrentTardis();

            if (tardis == null) return;

            this.setHum(tardis, hum);
        });
    }

    public void onSynced() {
        this.sounds = registryToList();
    }

    public LoopingSound getHum(ClientTardis tardis) {
        if (this.current == null)
            this.current = (LoopingSound) findSoundByEvent(
                    tardis.<ServerHumHandler>handler(TardisComponent.Id.HUM).get().sound());

        return this.current;
    }

    public void setHum(ClientTardis tardis, LoopingSound hum) {
        LoopingSound previous = this.getHum(tardis);
        this.current = hum;

        this.stopSound(previous);
    }

    public void setServersHum(ClientTardis tardis, Hum hum) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(tardis.getUuid());
        buf.writeResourceLocation(hum.id());

        AitNetworking.send(ServerHumHandler.RECEIVE, buf);
    }

    public static ClientHumHandler create() {
        ClientHumHandler handler = new ClientHumHandler();

        handler.generateHums();
        return handler;
    }

    private void generateHums() {
        this.sounds = registryToList();
    }

    private List<SoundInstance> registryToList() {
        List<SoundInstance> list = new ArrayList<>();

        for (Hum sound : HumRegistry.getInstance().toList()) {
            list.add(new PlayerFollowingLoopingSound(sound.sound(), SoundSource.AMBIENT,
                    AITModClient.CONFIG.interiorHumVolume));
        }

        return list;
    }

    private boolean shouldPlaySounds(ClientTardis tardis) {
        return tardis != null && tardis.fuel().hasPower();
    }

    public void setSuppressed(boolean suppressed) {
        this.suppressed = suppressed;

        if (suppressed)
            this.stopSounds();
    }

    public boolean isSuppressed() {
        return this.suppressed;
    }

    public void tick(Minecraft client) {
        ClientTardis tardis = ClientTardisUtil.getCurrentTardis();

        if (this.sounds == null)
            this.generateHums();

        if (this.suppressed) {
            this.stopSounds();
            return;
        }

        if (this.needsReinit && tardis != null) {
            this.needsReinit = false;
            this.current = null;
            this.getHum(tardis);
        }

        if (this.shouldPlaySounds(tardis)) {
            this.startIfNotPlaying(this.getHum(tardis));
        } else {
            this.stopSounds();
        }
    }

    @Override
    public SoundInstance findSoundById(ResourceLocation id) {
        if (this.sounds == null) sounds = registryToList();

        return super.findSoundById(id);
    }
}
