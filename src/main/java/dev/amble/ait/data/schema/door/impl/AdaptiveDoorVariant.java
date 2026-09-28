package dev.amble.ait.data.schema.door.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class AdaptiveDoorVariant extends DoorSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("door/adaptive");

    public AdaptiveDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public SoundEvent openSound() {
        return SoundEvents.IRON_DOOR_OPEN;
    }

    @Override
    public SoundEvent closeSound() {
        return SoundEvents.IRON_DOOR_CLOSE;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.125, -0.45);
    }
}
