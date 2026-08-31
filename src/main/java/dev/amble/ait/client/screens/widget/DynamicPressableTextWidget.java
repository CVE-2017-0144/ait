package dev.amble.ait.client.screens.widget;

import java.util.function.Function;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class DynamicPressableTextWidget extends Button {
    private final Font textRenderer;
    private final Function<DynamicPressableTextWidget, Component> text;

    private boolean leftClick = true;

    private Component cached;
    private Component hoverText;

    public DynamicPressableTextWidget(int x, int y, int width, int height,
            Function<DynamicPressableTextWidget, Component> text, Button.OnPress onPress,
            Font textRenderer) {
        super(x, y, width, height, Component.empty(), onPress, DEFAULT_NARRATION);

        this.textRenderer = textRenderer;
        this.text = text;

        this.refresh();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.active || !this.visible)
            return false;

        if (!this.clicked(mouseX, mouseY))
            return false;

        this.leftClick = button == 0;

        this.playDownSound(Minecraft.getInstance().getSoundManager());
        this.onClick(mouseX, mouseY);
        return true;
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        Component text = this.isHoveredOrFocused() ? this.hoverText : this.cached;
        context.drawString(this.textRenderer, text, this.getX(), this.getY(),
                0xFFFFFF | Mth.ceil(this.alpha * 255.0f) << 24);
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public void refresh() {
        this.cached = this.text.apply(this);
        this.hoverText = ComponentUtils.mergeStyles(this.cached.copy(), Style.EMPTY.withUnderlined(true));
    }
}
