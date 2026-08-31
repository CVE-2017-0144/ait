package dev.amble.ait.data.schema.exterior.variant.adaptive;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.AdaptiveDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.AdaptiveCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.world.phys.Vec3;

public class AdaptiveVariant extends ExteriorVariantSchema {

    public AdaptiveVariant() {
        super(AdaptiveCategory.REFERENCE, AITMod.id("exterior/adaptive"));
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(AdaptiveDoorVariant.REFERENCE);
    }

    @Override
    public boolean hasPortals() {
        return true;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.125, -0.5);
    }

    @Override
    public double portalWidth() {
        return 0.75d;
    }
}
