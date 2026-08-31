package dev.amble.ait.data.schema.exterior.variant.exclusive.doom;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.exclusive.DoomDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.ExclusiveCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class DoomVariant extends ExteriorVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("exterior/exclusive/doom");

    public DoomVariant() {
        super(ExclusiveCategory.REFERENCE, REFERENCE);
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(DoomDoorVariant.REFERENCE);
    }

    @Override
    public boolean hasPortals() {
        return false;
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }
}
