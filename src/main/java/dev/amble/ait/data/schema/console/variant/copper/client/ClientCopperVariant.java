package dev.amble.ait.data.schema.console.variant.copper.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.CopperConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.copper.CopperVariant;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.joml.Vector3f;

public class ClientCopperVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/copper_console.png"));
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/copper_console_emission.png"));

    public ClientCopperVariant() {
        super(CopperVariant.REFERENCE, CopperVariant.REFERENCE);
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
    public RenderType hologramLayer(ResourceLocation texture) {
        // No back face culling: the copper model has single sided geometry that disappears without it.
        return RenderType.entityTranslucent(texture);
    }

    @Override
    public SimpleConsoleModel model() {
        return new CopperConsoleModel(CopperConsoleModel.getTexturedModelData().bakeRoot());
    }

    @Override
    public Vector3f sonicItemTranslations() {
        return new Vector3f(1.55f, 1.05f, 1.105f);
    }

    @Override
    public float[] sonicItemRotations() {
        return new float[]{-60f, -12.5f};
    }
    @Override
    public Vector3f handlesTranslations() {
        return new Vector3f(0.8f, 1.25f, 0.68f);
    }

    @Override
    public float[] handlesRotations() {
        return new float[]{-60f, 120f};
    }
}
