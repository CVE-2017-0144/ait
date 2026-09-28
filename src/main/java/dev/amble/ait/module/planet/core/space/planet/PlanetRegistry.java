package dev.amble.ait.module.planet.core.space.planet;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import dev.amble.ait.AITMod;
import dev.amble.lib.register.datapack.SimpleDatapackRegistry;

public class PlanetRegistry extends SimpleDatapackRegistry<Planet> {

    private static final PlanetRegistry instance = new PlanetRegistry();

    public PlanetRegistry() {
        super(Planet::fromInputStream, Planet.CODEC, "planet", true, AITMod.MOD_ID);
    }

    public static Planet OVERWORLD;
    public static Planet THE_NETHER;
    public static Planet THE_END;

    @Override
    protected void defaults() {
              THE_NETHER = register(new Planet(BuiltinDimensionTypes.NETHER_EFFECTS, -1, true, true, 548, PlanetRenderInfo.EMPTY, PlanetTransition.EMPTY));
        THE_END = register(new Planet(BuiltinDimensionTypes.END_EFFECTS, -1, true, true, 548, PlanetRenderInfo.EMPTY, PlanetTransition.EMPTY));// -1f means dont change gravity btw
    }

    @Override
    public void onCommonInit() {
        super.onCommonInit();
        this.defaults();
    }

    @Override
    public Planet fallback() {
        return OVERWORLD;
    }

    /**
     * @implNote O(N) - worst scenario (cache miss), O(1) otherwise.
     */
    public Planet get(Level world) {
        // all worlds implement PlanetWorld
        if (!(world instanceof PlanetWorld planetWorld))
            return null;

        if (planetWorld.ait_planet$isAPlanet())
            return planetWorld.ait_planet$getPlanet();

        Planet planet = this.get(world.dimension().location());

        planetWorld.ait_planet$setPlanet(planet);
        planetWorld.ait_planet$setIsAPlanet(planet != null);
        return planet;
    }

    public static PlanetRegistry getInstance() {
        return instance;
    }
}
