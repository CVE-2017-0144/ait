package dev.amble.ait.data.schema.console.variant.hartnell.client;

import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.consoles.HartnellConsoleModel;
import dev.amble.ait.client.models.consoles.SimpleConsoleModel;
import dev.amble.ait.data.schema.console.ClientConsoleVariantSchema;
import dev.amble.ait.data.schema.console.variant.hartnell.MintHartnellVariant;
import net.minecraft.resources.ResourceLocation;

public class ClientMintHartnellVariant extends ClientConsoleVariantSchema {
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/hartnell_mint_console.png"));
    public static final ResourceLocation EMISSION = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            ("textures/blockentities/consoles/hartnell_console_emission.png"));

    public ClientMintHartnellVariant() {
        super(MintHartnellVariant.REFERENCE, MintHartnellVariant.REFERENCE);
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
        return new HartnellConsoleModel(HartnellConsoleModel.getTexturedModelData().bakeRoot());
    }
}
