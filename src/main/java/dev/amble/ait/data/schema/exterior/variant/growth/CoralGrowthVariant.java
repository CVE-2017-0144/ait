package dev.amble.ait.data.schema.exterior.variant.growth;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.CoralGrowthDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.GrowthCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CoralGrowthVariant extends ExteriorVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("exterior/coral_growth");

    public CoralGrowthVariant() {
        super(GrowthCategory.REFERENCE, REFERENCE);
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(CoralGrowthDoorVariant.REFERENCE);
    }

    @Override
    public boolean hasPortals() {
        return true;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0, -0.5);
    }

    @Override
    public double portalHeight() {
        return 2.1d;
    }

    @Override
    public double portalWidth() {
        return 0.6d;
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }
}
