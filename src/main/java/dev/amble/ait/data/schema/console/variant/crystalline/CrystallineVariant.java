package dev.amble.ait.data.schema.console.variant.crystalline;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import net.minecraft.resources.ResourceLocation;

public class CrystallineVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/crystalline");

    public CrystallineVariant() {
        super(CrystallineVariant.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.PILOT));
    }
}
