package dev.amble.ait.data.schema.console.variant.renaissance;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.RenaissanceType;
import net.minecraft.resources.ResourceLocation;

public class RenaissanceIdentityVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/renaissance_identity");

    public RenaissanceIdentityVariant() {super(RenaissanceType.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.OWNER));
    }
}
