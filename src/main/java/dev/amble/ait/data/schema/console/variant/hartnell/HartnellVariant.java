package dev.amble.ait.data.schema.console.variant.hartnell;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.HartnellType;
import net.minecraft.resources.ResourceLocation;

public class HartnellVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/hartnell");

    public HartnellVariant() {
        super(HartnellType.REFERENCE, REFERENCE);
    }
}
