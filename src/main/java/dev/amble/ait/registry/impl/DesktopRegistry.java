package dev.amble.ait.registry.impl;

import java.util.Random;

import net.minecraft.server.packs.PackType;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.data.datapack.DatapackDesktop;
import dev.amble.ait.data.schema.desktop.DefaultCaveDesktop;
import dev.amble.ait.data.schema.desktop.DevDesktop;
import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import dev.amble.lib.platform.resource.ReloadListeners;
import dev.amble.lib.register.datapack.DatapackRegistry;
import dev.amble.lib.register.unlockable.UnlockableRegistry;

public class DesktopRegistry extends UnlockableRegistry<TardisDesktopSchema> {

    private static final DesktopRegistry INSTANCE = new DesktopRegistry();

    protected DesktopRegistry() {
        super(DatapackDesktop::fromInputStream, DatapackDesktop.CODEC, "desktop", true);
    }

    @Override
    public void onCommonInit() {
        super.onCommonInit();
        this.defaults();
        ReloadListeners.register(PackType.CLIENT_RESOURCES, this);
    }


    @Override
    public TardisDesktopSchema getRandom(Tardis tardis, Random random) {
        return DatapackRegistry.getRandom(this.toList().stream().filter(t -> {
                            if (t instanceof DatapackDesktop desktop) {
                                return desktop != DesktopRegistry.DEFAULT_CAVE;
                            }
                            return true;
                        })
                        .filter(tardis::isUnlocked).toList(), random,
                this::fallback);
    }

    @Override
    public TardisDesktopSchema fallback() {
        throw new UnsupportedOperationException("No desktops registered!");
    }

    public static DesktopRegistry getInstance() {
        return INSTANCE;
    }

    public static TardisDesktopSchema DEFAULT_CAVE;
    public static TardisDesktopSchema DEV;

    @Override
    protected void defaults() {
        DEFAULT_CAVE = register(new DefaultCaveDesktop());
        DEV = register(new DevDesktop());
    }
}
