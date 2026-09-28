package dev.amble.ait.data.schema.exterior.variant.booth;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.BoothDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.BoothCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

// a useful class for creating tardim variants as they all have the same filepath you know
public abstract class BoothVariant extends ExteriorVariantSchema {
    protected static final String TEXTURE_PATH = "textures/blockentities/exteriors/booth/booth_";

    protected BoothVariant(String name, String modId) { // idk why i added the modid bit i dont use it later lol
        super(BoothCategory.REFERENCE, ResourceLocation.fromNamespaceAndPath(modId, "exterior/booth/" + name),
                new Loyalty(Loyalty.Type.PILOT));
    }

    protected BoothVariant(String name) {
        this(name, AITMod.MOD_ID);
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
