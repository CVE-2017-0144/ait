package dev.amble.ait.module.planet.core.blockentities;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import dev.amble.ait.core.AITStatusEffects;
import dev.amble.ait.module.planet.core.PlanetBlockEntities;
import dev.amble.ait.module.planet.core.space.planet.Planet;
import dev.amble.ait.module.planet.core.space.planet.PlanetRegistry;

public class OxygenatorBlockEntity extends BlockEntity {
    public OxygenatorBlockEntity(BlockPos pos, BlockState state) {
        super(PlanetBlockEntities.OXYGENATOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    public void tick(Level world, BlockPos blockPos, BlockState blockState, OxygenatorBlockEntity oxygenatorBlockEntity) {
        if (world.isClientSide()) return;
        Planet planet = PlanetRegistry.getInstance().get(world);
        if (planet == null) return;
        if (planet.hasOxygen()) return;
        Predicate<Entity> predicate = EntitySelector.NO_SPECTATORS
                .and(EntitySelector.LIVING_ENTITY_STILL_ALIVE);
        world.getEntities((Entity) null, new AABB(blockPos).inflate(20), predicate).forEach(entity -> {
            if  (entity instanceof LivingEntity livingEntity) {
                livingEntity.addEffect(new MobEffectInstance(AITStatusEffects.OXYGENATED, 20, 1, true, false));
            }
        });
    }
}
