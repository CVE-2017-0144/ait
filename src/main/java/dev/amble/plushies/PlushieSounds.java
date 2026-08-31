package dev.amble.plushies;

import dev.amble.lib.AmbleKit;
import dev.amble.lib.container.impl.SoundContainer;
import net.minecraft.sounds.SoundEvent;

public class PlushieSounds implements SoundContainer {
    public static final SoundEvent BOOP = SoundEvent.createVariableRangeEvent(AmbleKit.id("secret/boop"));
}
