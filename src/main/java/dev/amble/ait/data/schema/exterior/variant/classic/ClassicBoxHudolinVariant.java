package dev.amble.ait.data.schema.exterior.variant.classic;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.ClassicHudolinDoorVariant;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.world.phys.Vec3;

public class ClassicBoxHudolinVariant extends ClassicBoxVariant {
    public ClassicBoxHudolinVariant() {
        super("hudolin");
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(ClassicHudolinDoorVariant.REFERENCE);
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0, -0.599);
    }
}
