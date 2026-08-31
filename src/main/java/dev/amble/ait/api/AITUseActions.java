package dev.amble.ait.api;

import net.minecraft.world.item.UseAnim;

public interface AITUseActions {
    UseAnim SONIC = ((AITUseActions) (Object) UseAnim.NONE).ait$sonic();
    UseAnim ait$sonic();
}
