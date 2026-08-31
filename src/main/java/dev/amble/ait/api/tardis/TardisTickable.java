package dev.amble.ait.api.tardis;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;

import dev.amble.ait.core.tardis.Tardis;

/**
 * An interface for something that can be ticked by a tardis Make sure to add
 * whatever it is that needs ticking to {@link Tardis}
 */
public interface TardisTickable {
    default void tick(MinecraftServer server) { }

    @OnlyIn(Dist.CLIENT)
    default void tick(Minecraft client) { }
}
