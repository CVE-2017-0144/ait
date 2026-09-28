package dev.amble.ait.data.schema.console.variant.renaissance.client;


import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.RenaissanceConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.renaissance.RenaissanceIdentityVariant;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class ClientRenaissanceIdentityVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/renaissance_identity.png"));
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/renaissance_identity_emission.png"));

    public ClientRenaissanceIdentityVariant() {
        super(RenaissanceIdentityVariant.REFERENCE, RenaissanceIdentityVariant.REFERENCE);
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
        return new RenaissanceConsoleModel(RenaissanceConsoleModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(-0.013f, 1.2f, -0.895f);
    }

    @Override
    public float[] sonicItemRotations() {
        return new float[]{-180f, -30f};
    }

    @Override
    public Vector3f handlesTranslations() {
        return new Vector3f(-0.01f, 1.45f, -0.04f);
    }

    @Override
    public float[] handlesRotations() {
        return new float[]{-180f, 120f};
    }
}
