package dev.amble.ait.client.screens.widget;

import java.util.function.Consumer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class AnimationScrubberWidget extends AbstractWidget {

    private final Consumer<Float> onScrub;
    private float progress;
    private boolean dragging;

    public AnimationScrubberWidget(int x, int y, int width, int height, Consumer<Float> onScrub) {
        super(x, y, width, height, Component.translatable("screen.ait.widget.timeline"));
        this.onScrub = onScrub;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float progress) {
        this.progress = Mth.clamp(progress, 0f, 1f);
    }

    public boolean isDragging() {
        return this.dragging;
    }

    private void updateFromMouse(double mouseX) {
        float p = (float) ((mouseX - this.getX()) / Math.max(1, this.getWidth() - 1));
        this.progress = Mth.clamp(p, 0f, 1f);

        if (this.onScrub != null)
            this.onScrub.accept(this.progress);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.dragging = true;
        this.updateFromMouse(mouseX);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double deltaX, double deltaY) {
        this.updateFromMouse(mouseX);
    }

    @Override
    public void onRelease(double mouseX, double mouseY) {
        this.dragging = false;
    }

    @Override
    public void playDownSound(SoundManager soundManager) {
    }

    @Override
    public void renderWidget(GuiGraphics context, int mouseX, int mouseY, float delta) {
        if (!this.visible)
            return;

        int x = this.getX();
        int y = this.getY();
        int w = this.getWidth();
        int h = this.getHeight();

        int trackColor = 0xFF15171C;
        int fillColor = 0xFF54C0FF;
        int handleColor = (this.isHovered() || this.dragging) ? 0xFFFFFFFF : 0xFFCCE8FF;

        context.fill(x, y, x + w, y + h, trackColor);

        int fillW = Math.round(this.progress * (w - 2));
        context.fill(x + 1, y + 1, x + 1 + fillW, y + h - 1, fillColor);

        int handleX = x + Math.round(this.progress * (w - 3));
        context.fill(handleX, y - 1, handleX + 3, y + h + 1, handleColor);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput builder) {
        this.defaultButtonNarrationText(builder);
    }
}
