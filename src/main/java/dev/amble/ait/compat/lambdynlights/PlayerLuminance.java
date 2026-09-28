package dev.amble.ait.compat.lambdynlights;

import dev.amble.ait.core.item.SonicItem;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

public class PlayerLuminance implements EntityLuminance {

    @Override
    public @NotNull Type type() {
        return Type.VALUE;
    }

    @Override
    public @Range(from = 0L, to = 15L) int getLuminance(@NotNull ItemLightSourceManager itemLightSourceManager, @NotNull Entity entity) {
        if (entity instanceof Player player) {
            ItemStack stack = player.getUseItem();
            if (stack == null || stack.isEmpty()) return 0;
            if (stack.getItem() instanceof SonicItem sonic) {
                double current = sonic.getCurrentFuel(stack);
                if (current > 0) {
                    double max = sonic.getMaxFuel(stack);
                    int lum = (int) Math.ceil((current / max) * 15.0);
                    return Mth.clamp(lum, 0, 15);
                }
            }
        }
        return 0;
    }
}
