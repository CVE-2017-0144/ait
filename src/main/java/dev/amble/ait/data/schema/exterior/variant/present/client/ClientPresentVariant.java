package dev.amble.ait.data.schema.exterior.variant.present.client;

import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.PresentExteriorModel;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import net.minecraft.resources.ResourceLocation;

public abstract class ClientPresentVariant extends ClientExteriorVariantSchema {
    private final String name;
    protected static final String CATEGORY_PATH = "textures/blockentities/exteriors/present";
    protected static final ResourceLocation BIOME_IDENTIFIER = AITMod.id(CATEGORY_PATH + "/biome" + "/present.png");
    protected static final ResourceLocation CATEGORY_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            CATEGORY_PATH + "/present.png");
    protected static final String TEXTURE_PATH = CATEGORY_PATH + "/present_";

    protected static final BiomeOverrides OVERRIDES = BiomeOverrides.builder()
            .with(type -> type.getTexture(BIOME_IDENTIFIER), BiomeHandler.BiomeType.SNOWY,
                    BiomeHandler.BiomeType.SCULK, BiomeHandler.BiomeType.CHORUS, BiomeHandler.BiomeType.CHERRY,
                    BiomeHandler.BiomeType.SANDY, BiomeHandler.BiomeType.RED_SANDY, BiomeHandler.BiomeType.MUDDY)
            .build();


    protected ClientPresentVariant(String name) {
        super(AITMod.id("exterior/present/" + name));

        this.name = name;
    }

    @Override
    public SimpleExteriorModel model() {
        return new PresentExteriorModel(PresentExteriorModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public BiomeOverrides overrides() {
        return null;
    }

    @Override
    public ResourceLocation texture() {
        return AITMod.id(TEXTURE_PATH + name + ".png");
    }

    @Override
    public ResourceLocation emission() {
        return null;
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(0.50f, 2.04f, 1.9f);
    }

}
