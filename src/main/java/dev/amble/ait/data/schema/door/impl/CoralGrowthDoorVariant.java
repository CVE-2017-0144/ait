package dev.amble.ait.data.schema.door.impl;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

public class CoralGrowthDoorVariant extends DoorSchema {

    public static final ResourceLocation REFERENCE = AITMod.id("door/coral_growth");

    public CoralGrowthDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public SoundEvent openSound() {
        return SoundEvents.BEEHIVE_SHEAR;
    }

    @Override
    public SoundEvent closeSound() {
        return SoundEvents.BEEHIVE_SHEAR;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0, 0.1);
    }
}
