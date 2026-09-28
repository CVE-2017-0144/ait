package dev.amble.ait.client.tardis;

import java.lang.reflect.Type;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import com.google.gson.InstanceCreator;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.Disposable;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.client.util.ClientShakeUtil;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.TardisExterior;
import dev.amble.ait.core.tardis.handler.DoorHandler;
import dev.amble.ait.data.Exclude;

public class ClientTardis extends Tardis implements Disposable {

    @Exclude
    private final UUID check;

    @Exclude
    private boolean aged = false;

    @Exclude
    private float leftRot = -1, leftRotO, rightRot, rightRotO;

    private ClientTardis(UUID check) {
        super();
        this.check = check;
    }

    public void setDesktop(TardisDesktop desktop) {
        desktop.setTardis(this);
        this.desktop = desktop;
    }

    public void setExterior(TardisExterior exterior) {
        exterior.setTardis(this);
        this.exterior = exterior;
    }

    public void tick(Minecraft client) {
        this.getHandlers().tick(client);

        if (!client.isPaused())
            this.tickDoors();

        if (ClientTardisUtil.getCurrentTardis() != this)
            return;

        ClientTardisUtil.tickPowerDelta();
        ClientTardisUtil.tickAlarmDelta();

        float amount = ClientShakeUtil.getShakeAmount(this) * AITModClient.CONFIG.screenShake;
        ClientShakeUtil.shake(amount);
    }

    // synced rot lands uneven per tick, lerp per frame
    private void tickDoors() {
        DoorHandler door = this.door();
        float left = door.getSyncedRot(true);
        float right = door.getSyncedRot(false);

        if (this.leftRot < 0) {
            this.leftRot = this.leftRotO = left;
            this.rightRot = this.rightRotO = right;
            return;
        }

        this.leftRotO = this.leftRot;
        this.rightRotO = this.rightRot;
        this.leftRot = follow(door, this.leftRot, left, door.getDoorState() != DoorHandler.DoorState.CLOSED);
        this.rightRot = follow(door, this.rightRot, right, door.getDoorState() == DoorHandler.DoorState.BOTH);
    }

    private static float follow(DoorHandler door, float rot, float synced, boolean opening) {
        float next = door.calculateRotation(rot, opening);
        return opening ? Math.max(next, synced) : Math.min(next, synced);
    }

    // resyncs replace this mid swing
    public void keepDoorRot(ClientTardis old) {
        this.leftRot = old.leftRot;
        this.leftRotO = old.leftRotO;
        this.rightRot = old.rightRot;
        this.rightRotO = old.rightRotO;
    }

    public float getDoorRot(boolean left) {
        if (this.leftRot < 0)
            return this.door().getSyncedRot(left);

        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(false);
        return left ? Mth.lerp(delta, this.leftRotO, this.leftRot) : Mth.lerp(delta, this.rightRotO, this.rightRot);
    }

    @Override
    public <T extends TardisComponent> T handler(TardisComponent.IdLike type) {
        if (this.handlers == null) {
            AITMod.LOGGER.error("Asked for a handler too early on {}, aged? {}", this, this.aged);
            return null;
        }

        return super.handler(type);
    }

    public void age() {
        this.aged = true;
    }

    @Override
    public boolean isAged() {
        return aged;
    }

    @Override
    public void dispose() { }

    @Override
    public String toString() {
        return super.toString() + " (" + Integer.toHexString(check.hashCode()) + ")";
    }

    public static Object creator() {
        return new ClientTardisCreator();
    }

    static class ClientTardisCreator implements InstanceCreator<ClientTardis> {

        @Override
        public ClientTardis createInstance(Type type) {
            return new ClientTardis(UUID.randomUUID());
        }
    }
}
