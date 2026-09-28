package dev.amble.ait.core.tardis.handler;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.data.hum.Hum;
import dev.amble.ait.registry.impl.HumRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerHumHandler extends TardisComponent {
    public static final ResourceLocation SEND = AITMod.id("send_hum");
    public static final ResourceLocation RECEIVE = AITMod.id("receive_hum");
    private Hum current;

    static {
        AitNetworking.registerServerReceiver(ServerHumHandler.RECEIVE,
                ServerTardisManager.receiveTardis(SecurityControl.withLoyaltyCheck((tardis, server, player, handler, buf, responseSender) -> {
                    if (tardis == null) return;

                    Hum hum = HumRegistry.getInstance().get(buf.readResourceLocation());

                    if (hum == null)
                        return;

                    server.execute(() -> tardis.hum().set(hum));
                })));
    }

    public ServerHumHandler() {
        super(Id.HUM);
    }

    public Hum get() {
        if (current == null) {
            this.current = HumRegistry.getInstance().getRandom();
        }

        return this.current;
    }

    public void set(Hum hum) {
        this.current = hum;

        this.updateClientHum();
    }

    private void updateClientHum() {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeResourceLocation(this.current.sound().getLocation());

        for (ServerPlayer player : this.tardis.asServer().world().players()) {
            AitNetworking.send(player, SEND, buf);
        }
    }
}
