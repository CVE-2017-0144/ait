package dev.amble.ait.data.schema.door.impl;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;

public class RenegadeDoorVariant extends DoorSchema {

    public static final ResourceLocation REFERENCE = AITMod.id("door/renegade");

    public RenegadeDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public SoundEvent openSound() {
        return SoundEvents.GRINDSTONE_USE;
    }

    @Override
    public SoundEvent closeSound() {
        return SoundEvents.GRINDSTONE_USE;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0, -0.4);
    }
}
