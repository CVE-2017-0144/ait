package dev.amble.ait.client.screens;

import dev.amble.ait.AITMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class BlueprintFabricatorScreen extends Screen {
    @Override
    protected void renderBlurredBackground(float delta) {
    }


    private static final ResourceLocation TEXTURE = AITMod.id("textures/gui/blueprinting_deck.png");
    int backgroundHeight = 166;
    int backgroundWidth = 176;

    public BlueprintFabricatorScreen() {
        super(Component.translatable("screen.ait.blueprint_fabricator"));
    }

    protected void drawBackground(GuiGraphics context) {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = ((this.height) - this.backgroundHeight) / 2;
        context.blit(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        drawBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }
}
