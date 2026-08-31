package dev.amble.ait.data.schema.console.variant.toyota;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.ToyotaType;
import net.minecraft.resources.ResourceLocation;

public class ToyotaLegacyVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/toyota_legacy");

    public ToyotaLegacyVariant() {
        super(ToyotaType.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.OWNER));
    }
}
