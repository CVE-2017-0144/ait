package dev.amble.ait.data.schema.exterior.variant.easter_head.client;

import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.EasterHeadModel;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import net.minecraft.resources.ResourceLocation;

// a useful class for creating easter_head variants as they all have the same filepath you know
public abstract class ClientEasterHeadVariant extends ClientExteriorVariantSchema {
    private final String name;
    protected static final String CATEGORY_PATH = "textures/blockentities/exteriors/easter_head";
    protected static final ResourceLocation CATEGORY_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, CATEGORY_PATH + "/easter_head.png");
    protected static final ResourceLocation BIOME_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, CATEGORY_PATH + "/biome" + "/easter_head.png");
    protected static final String TEXTURE_PATH = CATEGORY_PATH + "/easter_head_";

    protected static final BiomeOverrides OVERRIDES = BiomeOverrides.builder()
            .with(type -> type.getTexture(BIOME_IDENTIFIER), BiomeHandler.BiomeType.SNOWY,
                    BiomeHandler.BiomeType.SCULK, BiomeHandler.BiomeType.CHORUS, BiomeHandler.BiomeType.CHERRY,
                    BiomeHandler.BiomeType.SANDY, BiomeHandler.BiomeType.RED_SANDY, BiomeHandler.BiomeType.MUDDY)
            .build();

    protected ClientEasterHeadVariant(String name) {
        super(AITMod.id("exterior/easter_head/" + name));

        this.name = name;
    }

    @Override
    public SimpleExteriorModel model() {
        return new EasterHeadModel(EasterHeadModel.getTexturedModelData().bakeRoot());
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
        return new Vector3f(0.25f, 1.1f, 1.2f);
    }

    @Override
    public float[] sonicItemRotations() {
        return new float[]{0f, 112.5f};
    }

    @Override
    public BiomeOverrides overrides() {
        return OVERRIDES;
    }
}
