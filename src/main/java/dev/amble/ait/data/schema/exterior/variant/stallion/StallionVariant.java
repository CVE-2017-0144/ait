package dev.amble.ait.data.schema.exterior.variant.stallion;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.StallionDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.StallionCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;


public abstract class StallionVariant extends ExteriorVariantSchema {
    protected static final String TEXTURE_PATH = "textures/blockentities/exteriors/stallion/stallion_";

    protected StallionVariant(String name, String modId) {
        super(StallionCategory.REFERENCE, new ResourceLocation(modId, "exterior/stallion/" + name),
                new Loyalty(Loyalty.Type.COMPANION));
    }

    protected StallionVariant(String name) {
        this(name, AITMod.MOD_ID);
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(StallionDoorVariant.REFERENCE);
    }

    @Override
    public boolean hasPortals() {
        return true;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.0625, -0.5);
    }

    @Override
    public double portalHeight() {
        return 2.3d;
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }
}
