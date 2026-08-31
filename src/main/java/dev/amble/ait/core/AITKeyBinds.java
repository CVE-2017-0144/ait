package dev.amble.ait.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;
import com.mojang.blaze3d.platform.InputConstants;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.bind.KeyBind;
import dev.amble.ait.core.entities.FlightTardisEntity;
import dev.amble.ait.core.item.KeyItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.lib.platform.clientlifecycle.ClientEvents;

public class AITKeyBinds {

    private static final List<KeyBind> BINDS = new ArrayList<>();

    public static void init() {
        ClientEvents.END_CLIENT_TICK.register(client -> {
            for (KeyBind bind : BINDS)
                bind.tick(client);
        });

        register(new KeyBind.Held("snap", "main", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_V, client -> {
            LocalPlayer player = client.player;

            if (player == null)
                return;

            if (player.isPassenger()) {
                Entity entity = player.getVehicle();
                if (entity instanceof FlightTardisEntity flightTardis) {
                    if (!flightTardis.isLinked()) return;
                    Tardis tardis = flightTardis.tardis().get();

                    ClientTardisUtil.snapToOpenDoors(tardis);
                    return;
                }
            }

            Collection<ItemStack> keys = KeyItem.getKeysInInventory(player);

            for (ItemStack stack : keys) {
                if (stack.getItem() instanceof KeyItem key && key.hasProtocol(KeyItem.Protocols.SNAP)) {
                    Tardis tardis = key.getTardis(player.level(), stack);

                    if (tardis == null)
                        return;

                    ClientTardisUtil.snapToOpenDoors(tardis);
                }
            }
        }));
        register(new KeyBind.Held("increase_speed", "main", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, client -> {
            LocalPlayer player = client.player;

            if (player == null || !player.isPassenger())
                return;

            Entity entity = player.getVehicle();
            if (entity instanceof FlightTardisEntity flightTardis) {
                if (!flightTardis.isLinked()) return;
                Tardis tardis = flightTardis.tardis().get();

                ClientTardisUtil.flyingSpeedPacket(tardis, "up");
            }
        }));
        register(new KeyBind.Held("decrease_speed", "main", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_B, client -> {
            LocalPlayer player = client.player;

            if (player == null || !player.isPassenger())
                return;

            Entity entity = player.getVehicle();
            if (entity instanceof FlightTardisEntity flightTardis) {
                if (!flightTardis.isLinked()) return;
                Tardis tardis = flightTardis.tardis().get();

                ClientTardisUtil.flyingSpeedPacket(tardis, "down");
            }
        }));
        register(new KeyBind.Held("toggle_antigravs", "main", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_H, client -> {
            LocalPlayer player = client.player;

            if (player == null || !player.isPassenger())
                return;

            Entity entity = player.getVehicle();
            if (entity instanceof FlightTardisEntity flightTardis) {
                if (!flightTardis.isLinked()) return;
                Tardis tardis = flightTardis.tardis().get();

                ClientTardisUtil.toggleAntigravs(tardis);
            }
        }));
    }

    private static void register(KeyBind bind) {
        bind.register();
        BINDS.add(bind);
    }
}
