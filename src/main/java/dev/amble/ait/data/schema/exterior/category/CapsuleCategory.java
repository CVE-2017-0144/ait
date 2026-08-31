package dev.amble.ait.data.schema.exterior.category;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.exterior.ExteriorCategorySchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import net.minecraft.resources.ResourceLocation;

public class CapsuleCategory extends ExteriorCategorySchema {
    public static final ResourceLocation REFERENCE = AITMod.id("exterior/capsule");

    public CapsuleCategory() {
        super(REFERENCE, "capsule");
    }

    @Override
    public boolean hasPortals() {
        return true;
    }

    @Override
    public ExteriorVariantSchema getDefaultVariant() {
        return ExteriorVariantRegistry.CAPSULE_DEFAULT;
    }
}
