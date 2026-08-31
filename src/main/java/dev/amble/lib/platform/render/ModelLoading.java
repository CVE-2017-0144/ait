package dev.amble.lib.platform.render;

import java.util.Collection;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.ModelEvent;

import dev.amble.lib.platform.Platform;

@OnlyIn(Dist.CLIENT)
public final class ModelLoading {

    private ModelLoading() {}

    private static Collection<ResourceLocation> pending;

    static {
        IEventBus modBus = Platform.modBus();

        if (modBus != null)
            modBus.addListener(ModelEvent.RegisterAdditional.class, event -> {
                if (pending == null)
                    return;

                pending.forEach(id -> event.register(ModelResourceLocation.inventory(id)));
            });
    }

    public static void addModels(Collection<ResourceLocation> models) {
        pending = models;
    }
}
