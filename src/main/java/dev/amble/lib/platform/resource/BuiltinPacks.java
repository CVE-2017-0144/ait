package dev.amble.lib.platform.resource;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddPackFindersEvent;

import dev.amble.lib.platform.Platform;

public class BuiltinPacks {

    public static void register(ResourceLocation id, boolean enabledByDefault) {
        IEventBus modBus = Platform.modBus();

        if (modBus == null)
            return;

        modBus.addListener(AddPackFindersEvent.class, event -> {
            if (event.getPackType() != PackType.CLIENT_RESOURCES)
                return;

            // neoforge wants the jar-root path
            event.addPackFinders(ResourceLocation.fromNamespaceAndPath(id.getNamespace(),
                            "resourcepacks/" + id.getPath()), PackType.CLIENT_RESOURCES,
                    Component.translatable("resourcePack." + id.getPath() + ".name"),
                    PackSource.BUILT_IN, enabledByDefault, Pack.Position.TOP);
        });
    }
}
