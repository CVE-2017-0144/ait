package dev.amble.ait.data.schema.exterior.category;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.exterior.ExteriorCategorySchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import net.minecraft.resources.ResourceLocation;

public class DalekModCategory extends ExteriorCategorySchema {
    public static final ResourceLocation REFERENCE = AITMod.id("exterior/dalek_mod");
    public DalekModCategory() {
        super(REFERENCE, "dalek_mod");
    }

    @Override
    public ExteriorVariantSchema getDefaultVariant() {
        return ExteriorVariantRegistry.DALEK_MOD_1963;
    }
}
