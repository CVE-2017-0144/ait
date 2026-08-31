package dev.amble.ait.core;

import dev.amble.ait.AITMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.Level;

public class AITDamageTypes {
    public static final ResourceKey<DamageType> TARDIS_SQUASH_DAMAGE_TYPE = ResourceKey.create(Registries.DAMAGE_TYPE,
            AITMod.id("tardis_squash_damage_type"));

    public static final ResourceKey<DamageType> INTERIOR_CHANGE = ResourceKey.create(Registries.DAMAGE_TYPE,
            AITMod.id("interior_change_damage_type"));

    public static DamageSource of(Level world, ResourceKey<DamageType> key) {
        return new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
