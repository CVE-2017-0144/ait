package dev.amble.ait.data.schema.exterior.category;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.exterior.ExteriorCategorySchema;
import net.minecraft.resources.ResourceLocation;

public class GrowthCategory extends ExteriorCategorySchema {

    public static final ResourceLocation REFERENCE = AITMod.id("exterior/coral_growth");

    public GrowthCategory() {
        super(REFERENCE, "coral_growth");
    }

    @Override
    public boolean hasPortals() {
        return true;
    }
}
