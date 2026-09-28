package dev.amble.ait.data.schema.console.variant.coral.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.CoralConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.coral.CoralSithVariant;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class ClientCoralSithVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/coral_sith.png"));
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/coral_sith_emission.png"));

    public ClientCoralSithVariant() {
        super(CoralSithVariant.REFERENCE, CoralSithVariant.REFERENCE);
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
