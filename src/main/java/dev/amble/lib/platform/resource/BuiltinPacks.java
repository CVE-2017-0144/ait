package dev.amble.lib.platform.resource;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import dev.amble.lib.platform.Platform;

public final class BuiltinPacks {

    private BuiltinPacks() {}

    public static void register(String modId, ResourceLocation id, boolean enabledByDefault) {
        IEventBus modBus = Platform.modBus();

        if (modBus == null)
            return;

        modBus.addListener(AddPackFindersEvent.class, event -> {
            if (event.getPackType() != PackType.CLIENT_RESOURCES)
                return;

            event.addPackFinders(id, PackType.CLIENT_RESOURCES,
                    Component.translatable("resourcePack." + id.getPath() + ".name"),
                    PackSource.BUILT_IN, enabledByDefault, Pack.Position.TOP);
        });
    }
}
