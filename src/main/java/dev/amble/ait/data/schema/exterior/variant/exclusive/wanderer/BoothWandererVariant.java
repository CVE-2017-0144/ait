package dev.amble.ait.data.schema.exterior.variant.exclusive.wanderer;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.BoothDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.ExclusiveCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.world.phys.Vec3;

public class BoothWandererVariant extends ExteriorVariantSchema {

    public BoothWandererVariant() {
        super(ExclusiveCategory.REFERENCE, AITMod.id("exterior/exclusive/wanderer"));
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(BoothDoorVariant.REFERENCE);
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.125, -0.48f);
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }

    @Override
    public boolean hasPortals() {
        return true;
    }

    @Override
    public double portalWidth() {
        return 0.875d;
    }

    @Override
    public double portalHeight() {
        return 2.125d;
    }
}
