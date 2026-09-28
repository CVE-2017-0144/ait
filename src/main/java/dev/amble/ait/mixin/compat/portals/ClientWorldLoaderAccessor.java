package dev.amble.ait.mixin.compat.portals;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import qouteall.imm_ptl.core.ClientWorldLoader;

@Mixin(value = ClientWorldLoader.class, remap = false)
public interface ClientWorldLoaderAccessor {
    @Accessor("isCreatingClientWorld")
    static void ait$setCreatingClientWorld(boolean creating) {
        throw new AssertionError();
    }
}
