package dev.amble.ait.data.schema.door.impl;

import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.door.DoorSchema;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BookshelfDoorVariant extends DoorSchema {
    public static final ResourceLocation REFERENCE = AITMod.id("door/bookshelf");

    public BookshelfDoorVariant() {
        super(REFERENCE);
    }

    @Override
    public boolean isDouble() {
        return true;
    }

    @Override
    public @Nullable Vec3 getPortalPosition() {
        return new Vec3(0, 0.125, -0.45);
    }
}
