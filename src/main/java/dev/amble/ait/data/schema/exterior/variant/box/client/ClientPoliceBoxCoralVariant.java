package dev.amble.ait.data.schema.exterior.variant.box.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.PoliceBoxCoralModel;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import net.minecraft.resources.ResourceLocation;

public class ClientPoliceBoxCoralVariant extends ClientPoliceBoxVariant {
    protected static final ResourceLocation BIOME_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, CATEGORY_PATH + "/biome" + "/police_box_coral.png");
    public ClientPoliceBoxCoralVariant() {
        super("coral");
    }

    private final BiomeOverrides OVERRIDES = BiomeOverrides.builder(ClientPoliceBoxVariant.OVERRIDES)
            .with(type -> type.getTexture(BIOME_IDENTIFIER), BiomeHandler.BiomeType.SANDY).build();

    @Override
    public SimpleExteriorModel model() {
        return new PoliceBoxCoralModel(PoliceBoxCoralModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public BiomeOverrides overrides() {
        return OVERRIDES;
    }
}
