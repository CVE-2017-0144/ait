package dev.amble.ait.mixin.server;

import net.minecraft.world.level.dimension.end.EndDragonFight;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EndDragonFight.class)
public interface EnderDragonFightAccessor {
    @Accessor
    boolean getDragonKilled();
}
