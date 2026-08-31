
package dev.amble.ait.data.schema.console.variant.hudolin;


import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.HudolinType;
import net.minecraft.resources.ResourceLocation;

public class HudolinShalkaVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/hudolin_shalka");

    public HudolinShalkaVariant() {
        super(HudolinType.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.OWNER));
    }
}
