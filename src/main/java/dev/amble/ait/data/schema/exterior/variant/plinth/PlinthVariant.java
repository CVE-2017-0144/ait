package dev.amble.ait.data.schema.exterior.variant.plinth;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.PlinthDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.PlinthCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.world.phys.Vec3;

// a useful class for creating tardim variants as they all have the same filepath you know
public abstract class PlinthVariant extends ExteriorVariantSchema {
    protected static final String TEXTURE_PATH = "textures/blockentities/exteriors/plinth/plinth_";

    protected PlinthVariant(String name) {
        super(PlinthCategory.REFERENCE, AITMod.id("exterior/plinth/" + name),
                new Loyalty(Loyalty.Type.COMPANION));
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(PlinthDoorVariant.REFERENCE);
    }

    @Override
    public boolean hasPortals() {
        return true;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.1875, -0.4);
    }

    @Override
    public double portalHeight() {
        return 2.65d;
    }

    @Override
    public double portalWidth() {
        return 0.75d;
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }
}
