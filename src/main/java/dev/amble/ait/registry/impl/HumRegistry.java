package dev.amble.ait.registry.impl;


import dev.amble.ait.AITMod;
import dev.amble.ait.client.sounds.ClientSoundManager;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.data.hum.DatapackHum;
import dev.amble.ait.data.hum.Hum;
import dev.amble.lib.register.datapack.SimpleDatapackRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;

public class HumRegistry extends SimpleDatapackRegistry<Hum> {
    private static final HumRegistry instance = new HumRegistry();

    protected HumRegistry() {
        super(DatapackHum::fromInputStream, DatapackHum.CODEC, "hum", true, AITMod.MOD_ID);
    }

    public static HumRegistry getInstance() {
        return instance;
    }

    public static Hum CORAL;
    public static Hum CHRISTMAS;
    public static Hum OFF;

    @Override
    public void onCommonInit() {
        super.onCommonInit();
        this.defaults();
    }

    @Override
    protected void defaults() {
        CORAL = register(Hum.create(AITMod.MOD_ID, "coral", AITSounds.CORAL_HUM));
        CHRISTMAS = register(Hum.create(AITMod.MOD_ID, "christmas", AITSounds.CHRISTMAS_HUM));
        OFF = register(Hum.create(AITMod.MOD_ID, "off", SoundEvents.EMPTY));
    }

    @Override
    public Hum fallback() {
        return CORAL;
    }

    @Override
    public void readFromServer(RegistryFriendlyByteBuf buf) {
        super.readFromServer(buf);

        ClientSoundManager.getHum().onSynced();
    }
}
