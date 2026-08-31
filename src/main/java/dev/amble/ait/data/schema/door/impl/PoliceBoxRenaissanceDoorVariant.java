package dev.amble.ait.data.schema.door.impl;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class PoliceBoxRenaissanceDoorVariant extends DoorSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("door/police_box/renaissance");

    public PoliceBoxRenaissanceDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, -0.15, -0.4);
    }
}
