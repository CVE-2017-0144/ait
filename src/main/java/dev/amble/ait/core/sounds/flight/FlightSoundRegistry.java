package dev.amble.ait.core.sounds.flight;

import net.minecraft.server.packs.PackType;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.lib.platform.resource.ReloadListeners;
import dev.amble.lib.register.datapack.SimpleDatapackRegistry;

public class FlightSoundRegistry extends SimpleDatapackRegistry<FlightSound> {
    private static final FlightSoundRegistry instance = new FlightSoundRegistry();

    // just for spotless apply to apply
    public FlightSoundRegistry() {
        super(FlightSound::fromInputStream, FlightSound.CODEC, "fx/flight", true, AITMod.MOD_ID);
    }

    public static FlightSoundRegistry getInstance() {
        return instance;
    }

    public static FlightSound DEFAULT;

    @Override
    public void onCommonInit() {
        super.onCommonInit();
        this.defaults();
        ReloadListeners.register(PackType.CLIENT_RESOURCES, this);
    }

    @Override
    protected void defaults() {
        DEFAULT = register(new FlightSound(AITMod.id("default"), AITSounds.FLIGHT_LOOP.getLocation(), 80, "default"));
    }

    @Override
    public FlightSound fallback() {
        return DEFAULT;
    }
}
