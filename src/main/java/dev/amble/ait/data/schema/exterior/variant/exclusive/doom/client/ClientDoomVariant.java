package dev.amble.ait.data.schema.exterior.variant.exclusive.doom.client;

import dev.amble.ait.client.models.exteriors.SimpleExteriorModel;
import dev.amble.ait.client.models.exteriors.exclusive.DoomExteriorModel;
import dev.amble.ait.client.renderers.exteriors.DoomConstants;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.variant.exclusive.doom.DoomVariant;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class ClientDoomVariant extends ClientExteriorVariantSchema {

    public ClientDoomVariant() {
        super(DoomVariant.REFERENCE);
    }

    @Override
    public SimpleExteriorModel model() {
        return new DoomExteriorModel(DoomExteriorModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(0.5f, 1.5f, 0f);
    }

    @Override
    public ResourceLocation texture() {
        return DoomConstants.DOOM_FRONT_BACK;
    }

    @Override
    public ResourceLocation emission() {
        return DoomConstants.DOOM_TEXTURE_EMISSION;
    }

    @Override
    public BiomeOverrides overrides() {
        return null;
    }
}
