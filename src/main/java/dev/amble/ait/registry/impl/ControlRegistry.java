package dev.amble.ait.registry.impl;

import java.util.Optional;

import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.impl.*;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementControl;
import dev.amble.ait.core.tardis.control.impl.pos.XControl;
import dev.amble.ait.core.tardis.control.impl.pos.YControl;
import dev.amble.ait.core.tardis.control.impl.pos.ZControl;
import dev.amble.ait.core.tardis.control.impl.waypoint.LoadWaypointControl;
import dev.amble.ait.core.tardis.control.impl.waypoint.SaveWaypointControl;
import dev.amble.lib.platform.registry.PlatformRegistries;

public class ControlRegistry {

    public static final MappedRegistry<Control> REGISTRY = PlatformRegistries.createRegistry(ResourceKey.<Control>createRegistryKey(AITMod.id("control")));

    public static Control register(Control control) {
        return Registry.register(REGISTRY, control.id(), control);
    }

    /**
     * Finds a control by its ID ( name ) NOT its Identifier
     *
     * @param id
     *            the id to look for
     * @return the control found
     */
    public static Optional<Control> fromId(ResourceLocation id) {
        return Optional.ofNullable(REGISTRY.get(id));
    }

    public static void init() {
        // Pain.

        register(new AntiGravsControl());
        register(new AutoPilotControl());
        register(new CloakControl());
        register(new DimensionControl());
        register(new DirectionControl());
        register(new DoorControl());
        register(new DoorLockControl());
        register(new FastReturnControl());
        register(new HADSControl());
        register(new HailMaryControl());
        register(new HandBrakeControl());
        register(new LandTypeControl());
        register(new MonitorControl());
        register(new PowerControl());
        register(new RandomiserControl());
        register(new RefuelerControl());
        register(new SecurityControl());
        register(new SiegeModeControl());
        register(new SonicPortControl());
        register(new TelepathicControl());
        register(new ThrottleControl());
        register(new VisualiserControl());
        register(new EngineOverloadControl());
        register(new ElectricalDischargeControl());
        register(new ShieldsControl());

        // Waypoints
        register(new ConsolePortControl());
        register(new SaveWaypointControl());
        register(new LoadWaypointControl());

        // Pos
        register(new IncrementControl());
        register(new XControl());
        register(new YControl());
        register(new ZControl());
    }
}
