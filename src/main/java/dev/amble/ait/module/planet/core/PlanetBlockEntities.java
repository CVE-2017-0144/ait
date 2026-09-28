package dev.amble.ait.module.planet.core;

import dev.amble.ait.module.planet.core.blockentities.OxygenatorBlockEntity;
import dev.amble.lib.container.impl.BlockEntityContainer;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class PlanetBlockEntities implements BlockEntityContainer {

    public static BlockEntityType<OxygenatorBlockEntity> OXYGENATOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(OxygenatorBlockEntity::new, PlanetBlocks.OXYGENATOR_BLOCK).build(null);

}
