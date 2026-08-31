package dev.amble.ait.api;

import net.minecraft.network.chat.Component;

// TODO: change the String to Text
// TODO: make it so if the object is Nameable AND Identifiable, use Identifier#toTranslationKey
public interface Nameable {
    String name();

    default Component text() {
        return Component.literal(this.name());
    }
}
