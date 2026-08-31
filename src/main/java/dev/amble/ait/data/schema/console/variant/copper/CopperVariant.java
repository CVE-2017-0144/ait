package dev.amble.ait.data.schema.console.variant.copper;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.CopperType;
import net.minecraft.resources.ResourceLocation;

public class CopperVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/copper");

    public CopperVariant() {
        super(CopperType.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.OWNER));
    }
}
