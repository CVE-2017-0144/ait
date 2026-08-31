package dev.amble.ait.data.schema.console.variant.coral.client;

import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.CoralConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.coral.BlueCoralVariant;
import net.minecraft.resources.ResourceLocation;

public class ClientBlueCoralVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/coral_blue.png"));
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/coral_blue_emission.png"));

    public ClientBlueCoralVariant() {
        super(BlueCoralVariant.REFERENCE, BlueCoralVariant.REFERENCE);
    }

    @Override
    public ResourceLocation texture() {
        return TEXTURE;
    }

    @Override
    public ResourceLocation emission() {
        return EMISSION;
    }

    @Override
    public SimpleConsoleModel model() {
        return new CoralConsoleModel(CoralConsoleModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(1.15f, 1.2f, 0.5f);
    }

    @Override
    public float[] sonicItemRotations() {
        return new float[]{90f, 135f};
    }

    @Override
    public Vector3f handlesTranslations() {
        return new Vector3f(0.5f, 1.6f, 0.5f);
    }

    @Override
    public float[] handlesRotations() {
        return new float[]{-90f, 135f};
    }
}
