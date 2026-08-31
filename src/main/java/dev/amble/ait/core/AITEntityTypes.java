package dev.amble.ait.core;

import dev.amble.ait.core.entities.*;
import dev.amble.lib.container.AssignedName;
import dev.amble.lib.container.impl.EntityContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class AITEntityTypes implements EntityContainer {

    @AssignedName("control_entity")
    public static final EntityType<ConsoleControlEntity> CONTROL_ENTITY_TYPE = EntityType.Builder
            .of(ConsoleControlEntity::new, MobCategory.MISC).sized(0.125f, 0.125f)
            .noSummon()
            .build("control_entity");

    @AssignedName("falling_tardis")
    public static final EntityType<FallingTardisEntity> FALLING_TARDIS_TYPE = EntityType.Builder
            .of(FallingTardisEntity::new, MobCategory.MISC).sized(0.98f, 0.98f)
            .noSummon()
            .build("falling_tardis");

    @AssignedName("flight_tardis")
    public static final EntityType<FlightTardisEntity> FLIGHT_TARDIS_TYPE = EntityType.Builder
            .of(FlightTardisEntity::new, MobCategory.MISC).sized(0.98f, 0.98f)
            .noSummon()
            .build("flight_tardis");

    public static final EntityType<GallifreyFallsPaintingEntity> GALLIFREY_FALLS_PAINTING_ENTITY_TYPE = EntityType.Builder
            .of(GallifreyFallsPaintingEntity::new, MobCategory.MISC)
            .sized(0.5f, 0.5f).build("gallifrey_falls_painting_entity_type");

    public static final EntityType<TrenzalorePaintingEntity> TRENZALORE_PAINTING_ENTITY_TYPE = EntityType.Builder
            .of(TrenzalorePaintingEntity::new, MobCategory.MISC)
            .sized(0.5f, 0.5f).build("trenzalore_painting_entity_type");

//    public static final EntityType<CobbledSnowballEntity> COBBLED_SNOWBALL_TYPE = EntityType.Builder
//            .<CobbledSnowballEntity>create(SpawnGroup.MISC, CobbledSnowballEntity::new)
//            .dimensions(EntityDimensions.fixed(0.25f, 0.25f)).trackRangeBlocks(4).trackedUpdateRate(10).build();

    public static final EntityType<RiftEntity> RIFT_ENTITY = EntityType.Builder
            .<RiftEntity>of(RiftEntity::new, MobCategory.MISC).sized(1.5f, 2f)
            .canSpawnFarFromPlayer().build("rift_entity");
}
