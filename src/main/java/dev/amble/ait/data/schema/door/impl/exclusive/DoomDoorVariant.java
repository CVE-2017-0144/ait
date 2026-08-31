package dev.amble.ait.data.schema.door.impl.exclusive;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class DoomDoorVariant extends DoorSchema {

    public static final ResourceLocation REFERENCE = AITMod.id("door/doom");

    public DoomDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public SoundEvent openSound() {
        return AITSounds.DOOM_DOOR_OPEN;
    }

    @Override
    public SoundEvent closeSound() {
        return AITSounds.DOOM_DOOR_CLOSE;
    }
}
