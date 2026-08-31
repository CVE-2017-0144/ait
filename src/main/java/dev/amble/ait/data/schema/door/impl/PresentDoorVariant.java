package dev.amble.ait.data.schema.door.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;

public class PresentDoorVariant extends DoorSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("door/present");

    public PresentDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public SoundEvent openSound() {
        return SoundEvents.BARREL_OPEN;
    }

    @Override
    public SoundEvent closeSound() {
        return SoundEvents.BARREL_CLOSE;
    }

}
