package dev.amble.ait.data.schema.exterior.variant.box;

import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.PoliceBoxRenaissanceDoorVariant;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class PoliceBoxRenaissanceVariant extends PoliceBoxVariant {
    public PoliceBoxRenaissanceVariant() {
        super("renaissance");
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(PoliceBoxRenaissanceDoorVariant.REFERENCE);
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.01, -0.591);
    }

    @Override
    public double portalHeight() {
        return 2.35d;
    }
}
