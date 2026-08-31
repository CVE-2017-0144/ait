package dev.amble.ait.registry.impl.responses;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.handles.HandlesResponse;
import dev.amble.ait.core.handles.HandlesSound;
import dev.amble.ait.core.tardis.ServerTardis;

public class TakeOffResponse implements HandlesResponse {
    @Override
    public boolean run(ServerPlayer player, HandlesSound source, ServerTardis tardis) {
        return false;
    }

    @Override
    public List<String> getCommandWords() {
        List<String> list = new ArrayList<>();
        // Will add them later im lazy as fuck
        return list;
    }

    @Override
    public ResourceLocation id() {
        return AITMod.id("take_off");
    }
}
