package dev.amble.ait.data.schema.sonic;

import static dev.amble.ait.AITMod.MOD_ID;

import dev.amble.ait.AITMod;
import net.minecraft.resources.ResourceLocation;

public class BuiltinSonic extends SonicSchema {

    private static final String prefix = "item/sonic/";

    private static final String[] MODES = new String[]{"inactive", "interaction", "overload", "scanning", "tardis"};

    protected BuiltinSonic(ResourceLocation id, Models models) {
        super(id, models, new Rendering());
    }

    public static BuiltinSonic create(String id) {
        ResourceLocation[] identifiers = new ResourceLocation[MODES.length];

        for (int i = 0; i < MODES.length; i++) {
            identifiers[i] = ResourceLocation.fromNamespaceAndPath(MOD_ID, prefix + id + "/" + MODES[i]);
        }

        ResourceLocation sonicId = AITMod.id(id);

        return new BuiltinSonic(sonicId,
                new Models(identifiers[0], identifiers[1], identifiers[2], identifiers[3], identifiers[4]));
    }
}
