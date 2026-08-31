package dev.amble.ait.data.schema.console.variant.alnico;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.AlnicoType;
import net.minecraft.resources.ResourceLocation;

public class BlueAlnicoVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/alnico_blue");

    public BlueAlnicoVariant() {
        super(AlnicoType.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.COMPANION));
    }
}
