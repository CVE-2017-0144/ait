package dev.amble.ait.data.schema.exterior.variant.booth.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.BoothExteriorModel;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

// a useful class for creating tardim variants as they all have the same filepath you know
public abstract class ClientBoothVariant extends ClientExteriorVariantSchema {
    private final String name;
    protected static final String CATEGORY_PATH = "textures/blockentities/exteriors/booth";
    protected static final ResourceLocation CATEGORY_IDENTIFIER = AITMod.id(CATEGORY_PATH + "/booth.png");
    protected static final ResourceLocation BIOME_IDENTIFIER = AITMod.id(CATEGORY_PATH + "/biome" + "/booth.png");
    protected static final String TEXTURE_PATH = CATEGORY_PATH + "/booth_";

    protected static final BiomeOverrides OVERRIDES = BiomeOverrides.builder()
            .with(type -> type.getTexture(BIOME_IDENTIFIER), BiomeHandler.BiomeType.SNOWY,
                    BiomeHandler.BiomeType.SCULK, BiomeHandler.BiomeType.CHORUS, BiomeHandler.BiomeType.CHERRY,
                    BiomeHandler.BiomeType.SANDY, BiomeHandler.BiomeType.RED_SANDY, BiomeHandler.BiomeType.MUDDY)
            .build();

    protected ClientBoothVariant(String name) {
        super(AITMod.id("exterior/booth/" + name));

        this.name = name;
    }

    @Override
    public SimpleExteriorModel model() {
        return new BoothExteriorModel(BoothExteriorModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public ResourceLocation texture() {
        return AITMod.id(TEXTURE_PATH + name + ".png");
    }

    @Override
    public ResourceLocation emission() {
        return AITMod.id(TEXTURE_PATH + name + "_emission" + ".png");
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(0.845f, 1.125f, 1.05f);
    }

    @Override
    public BiomeOverrides overrides() {
        return OVERRIDES;
    }

    @Override
    public boolean hasTransparentDoors() {
        return false;
    }
}
