package dev.amble.ait.data.schema.console.variant.renaissance.client;

import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.RenaissanceConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.renaissance.RenaissanceVariant;
import net.minecraft.resources.ResourceLocation;

public class ClientRenaissanceVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = new ResourceLocation(AITMod.MOD_ID,
            ("textures/blockentities/consoles/renaissance_default.png"));
    public static final ResourceLocation EMISSION = new ResourceLocation(AITMod.MOD_ID,
            ("textures/blockentities/consoles/renaissance_default_emission.png"));

    public ClientRenaissanceVariant() {
        super(RenaissanceVariant.REFERENCE, RenaissanceVariant.REFERENCE);
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
