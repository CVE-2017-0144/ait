package dev.amble.ait.core.tardis.handler;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;

public class HadsHandler extends KeyedTardisComponent implements TardisTickable {

    private static final BoolProperty HADS_ENABLED = new BoolProperty("enabled", false);
    private static final BoolProperty IS_IN_ACTIVE_DANGER = new BoolProperty("is_in_active_danger", false);

    private final BoolValue enabled = HADS_ENABLED.create(this);
    private final BoolValue inDanger = IS_IN_ACTIVE_DANGER.create(this);

    public HadsHandler() {
        super(Id.HADS);
    }

    public boolean isActive() {
        return enabled.get();
    }

    public void setIsInDanger(boolean bool) {
        inDanger.set(bool);
    }

    public boolean isInDanger() {
        return inDanger.get();
    }

    @Override
    public void onLoaded() {
        enabled.of(this, HADS_ENABLED);
        inDanger.of(this, IS_IN_ACTIVE_DANGER);
    }

    @Override
    public void tick(MinecraftServer server) {
        if (isActive())
            tickingForDanger(tardis.travel().position().getWorld());
    }

    // @TODO Fix hads idk why its broken. duzo did something to the demat idk what
    // happened lol
    public void tickingForDanger(Level world) {
        List<Entity> listOfEntities = world.getEntities((Entity) null,
                new AABB(tardis.travel().position().getPos()).inflate(3f), EntitySelector.NO_CREATIVE_OR_SPECTATOR);

        for (Entity entity : listOfEntities) {
            if (entity instanceof Creeper creeperEntity) {
                if (creeperEntity.getSwellDir() > 0) {
                    setIsInDanger(true);
                    break;
                }
            } else if (entity instanceof PrimedTnt) {
                setIsInDanger(true);
                break;
            }
            setIsInDanger(false);
        }

        dematerialiseWhenInDanger();
    }

    public void dematerialiseWhenInDanger() {
        TravelHandler travel = tardis.travel();
        TravelHandlerBase.State state = travel.getState();

        ServerAlarmHandler alarm = tardis.alarm();

        if (this.isInDanger()) {
            if (state == TravelHandlerBase.State.LANDED)
                travel.dematerialize();

            alarm.enable();
            return;
        }

        // FIXME this is dumb.
        if (state == TravelHandlerBase.State.FLIGHT)
            travel.rematerialize();

        if (state == TravelHandlerBase.State.MAT)
            alarm.disable();
    }

    public BoolValue enabled() {
        return enabled;
    }
}
