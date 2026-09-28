package dev.amble.ait.module.planet.mixin;

import dev.amble.ait.core.AITDimensions;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public abstract class WorldMixin implements PlanetWorld {

    @Shadow public abstract ResourceKey<Level> dimension();

    @Unique private Planet planet;

    @Unique private boolean isAPlanet;

    @Override
    public boolean ait_planet$isAPlanet() {
        return isAPlanet;
    }

    @Override
    public @Nullable Planet ait_planet$getPlanet() {
        return planet;
    }

    @Override
    public void ait_planet$setPlanet(Planet planet) {
        this.planet = planet;
    }

    @Override
    public void ait_planet$setIsAPlanet(boolean isAPlanet) {
        this.isAPlanet = isAPlanet;
    }

    @Inject(method = "isInWorldBounds", at = @At("HEAD"), cancellable = true)
    private void ait$isInBuildLimit(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Level world = (Level) (Object) this;
        if (world.dimension().equals(AITDimensions.SPACE)) {
            cir.setReturnValue(true);
        }
    }
}
