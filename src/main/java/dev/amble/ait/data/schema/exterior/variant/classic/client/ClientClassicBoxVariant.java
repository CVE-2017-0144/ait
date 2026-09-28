package dev.amble.ait.data.schema.exterior.variant.classic.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.ClassicExteriorModel;
import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

// a useful class for creating tardim variants as they all have the same filepath you know
public abstract class ClientClassicBoxVariant extends ClientExteriorVariantSchema {
    private final String name;
    protected static final String CATEGORY_PATH = "textures/blockentities/exteriors/classic";
    protected static final ResourceLocation CATEGORY_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, CATEGORY_PATH + "/classic.png");
    protected static final ResourceLocation BIOME_IDENTIFIER = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID, CATEGORY_PATH + "/biome" + "/classic.png");
    protected static final String TEXTURE_PATH = CATEGORY_PATH + "/classic_";

    protected static final BiomeOverrides OVERRIDES = BiomeOverrides.builder()
            .with(type -> type.getTexture(BIOME_IDENTIFIER), BiomeHandler.BiomeType.SNOWY,
                    BiomeHandler.BiomeType.SCULK, BiomeHandler.BiomeType.CHORUS, BiomeHandler.BiomeType.CHERRY,
                    BiomeHandler.BiomeType.SANDY, BiomeHandler.BiomeType.RED_SANDY, BiomeHandler.BiomeType.MUDDY)
            .build();

    protected ClientClassicBoxVariant(String name) {
        super(AITMod.id("exterior/classic/" + name));

        this.name = name;
    }

    @Override
    public SimpleExteriorModel model() {
        return new ClassicExteriorModel(ClassicExteriorModel.getTexturedModelData().bakeRoot());
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
        return new Vector3f(0.42f, 1.125f, 1.165f);
    }

    @Override
    public BiomeOverrides overrides() {
        return OVERRIDES;
    }
}
