package dev.amble.ait.data.schema.console.variant.steam;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.console.type.SteamType;
import net.minecraft.resources.ResourceLocation;

public class SteamSteelVariant extends ConsoleVariantSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("console/steam_steel");

    public SteamSteelVariant() {
        super(SteamType.REFERENCE, REFERENCE, new Loyalty(Loyalty.Type.COMPANION));
    }
}
