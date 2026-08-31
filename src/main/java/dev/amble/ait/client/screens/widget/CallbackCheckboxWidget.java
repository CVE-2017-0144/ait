package dev.amble.ait.client.screens.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.network.chat.Component;

/**
 * Variant of the Checkbox that allows for a callback when pressed
 */
public class CallbackCheckboxWidget extends Checkbox {

    private final PressAction onPress;

    public CallbackCheckboxWidget(int x, int y, int width, int height, Component message, boolean checked, PressAction onPress) {
        super(x, y, width, message, Minecraft.getInstance().font, checked, (checkbox, value) -> {});
        this.onPress = onPress;
    }

    @Override
    public void onPress() {
        super.onPress();
        this.onPress.onPress(this);
    }

    @FunctionalInterface
    public interface PressAction {
        void onPress(CallbackCheckboxWidget checkbox);
    }

}