package dev.amble.ait.data.schema.console.variant.hudolin.client;


import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.HudolinConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.hudolin.HudolinVariant;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class ClientHudolinVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/hudolin_console.png"));
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/hudolin_console_emission.png"));

    public ClientHudolinVariant() {
        super(HudolinVariant.REFERENCE, HudolinVariant.REFERENCE);
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
        return new HudolinConsoleModel(HudolinConsoleModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(-0.055f, 1.03f, -0.09f);
    }

    @Override
    public float[] sonicItemRotations() {
        return new float[]{120f, 170f};
    }

    @Override
    public Vector3f handlesTranslations() {
        return new Vector3f(0.5f, 1.3f, 0.2f);
    }

    @Override
    public float[] handlesRotations() {
        return new float[]{120f, 135f};
    }
}
