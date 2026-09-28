package dev.amble.ait.data.schema.exterior.variant.dalek_mod;


import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.door.DoorSchema;
import dev.amble.ait.data.schema.door.impl.DalekModDoorVariant;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.DalekModCategory;
import dev.amble.ait.registry.impl.door.DoorRegistry;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;


public abstract class DalekModVariant extends ExteriorVariantSchema {
    protected static final String TEXTURE_PATH = "textures/blockentities/exteriors/dalek_mod/dalek_mod_";

    protected DalekModVariant(int number) {
        super(DalekModCategory.REFERENCE, AITMod.id("exterior/dalek_mod/" + number),
                new Loyalty(Loyalty.Type.OWNER));
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.0625, -0.59);
    }

    @Override
    public double portalHeight() {
        return 1.90625d;
    }

    @Override
    public DoorSchema door() {
        return DoorRegistry.getInstance().get(DalekModDoorVariant.REFERENCE);
    }

    @Override
    public Vec3 seatTranslations() {
        return new Vec3(0.5, 1, 0.5);
    }

    @Override
    public boolean hasPortals() {
        return true;
    }
}
