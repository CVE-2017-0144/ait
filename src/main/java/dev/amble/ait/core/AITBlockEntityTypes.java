package dev.amble.ait.core;


import static dev.amble.ait.core.AITItems.isUnlockedOnThisDay;

import java.util.Calendar;

import dev.amble.lib.animation.HasBedrockModel;
import net.minecraft.world.level.block.entity.BlockEntityType;
import dev.amble.ait.core.blockentities.*;
import dev.amble.ait.core.blockentities.control.RedstoneControlBlockEntity;
import dev.amble.ait.core.blocks.PowerConverterBlock;
import dev.amble.ait.core.engine.block.generic.GenericStructureSystemBlockEntity;
import dev.amble.ait.core.engine.link.block.CableBlockEntity;
import dev.amble.ait.core.engine.link.block.FullCableBlockEntity;
import dev.amble.ait.module.planet.core.PlanetBlocks;
import dev.amble.lib.container.impl.BlockEntityContainer;

public class AITBlockEntityTypes implements BlockEntityContainer {
    public static BlockEntityType<SnowGlobeBlockEntity> SNOW_GLOBE_BLOCK_ENTITY_TYPE;
    public static BlockEntityType<ExteriorBlockEntity> EXTERIOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(ExteriorBlockEntity::new, AITBlocks.EXTERIOR_BLOCK).build(null);
    public static BlockEntityType<DoorBlockEntity> DOOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DoorBlockEntity::new, AITBlocks.DOOR_BLOCK).build(null);
    public static BlockEntityType<ConsoleBlockEntity> CONSOLE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(ConsoleBlockEntity::new, AITBlocks.CONSOLE).build(null);
    public static BlockEntityType<ConsoleGeneratorBlockEntity> CONSOLE_GENERATOR_ENTITY_TYPE = BlockEntityType.Builder.of(ConsoleGeneratorBlockEntity::new, AITBlocks.CONSOLE_GENERATOR).build(null);
    public static BlockEntityType<CoralBlockEntity> CORAL_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(CoralBlockEntity::new, AITBlocks.CORAL_PLANT).build(null);
    public static BlockEntityType<MatrixEnergizerBlockEntity> MATRIX_ENERGIZER_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(MatrixEnergizerBlockEntity::new, AITBlocks.MATRIX_ENERGIZER).build(null);
    public static BlockEntityType<MonitorBlockEntity> MONITOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(MonitorBlockEntity::new, AITBlocks.MONITOR_BLOCK).build(null);
    public static BlockEntityType<DetectorBlockEntity> DETECTOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(DetectorBlockEntity::new, AITBlocks.DETECTOR_BLOCK).build(null);
    @HasBedrockModel
    public static BlockEntityType<ArtronCollectorBlockEntity> ARTRON_COLLECTOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(ArtronCollectorBlockEntity::new, AITBlocks.ARTRON_COLLECTOR_BLOCK).build(null);
    public static BlockEntityType<PlaqueBlockEntity> PLAQUE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(PlaqueBlockEntity::new, AITBlocks.PLAQUE_BLOCK).build(null);
    public static BlockEntityType<EngineBlockEntity> ENGINE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(EngineBlockEntity::new, AITBlocks.ENGINE_BLOCK).build(null);
    public static BlockEntityType<WallMonitorBlockEntity> WALL_MONITOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(WallMonitorBlockEntity::new, AITBlocks.WALL_MONITOR_BLOCK).build(null);
    public static BlockEntityType<MachineCasingBlockEntity> MACHINE_CASING_ENTITY_TYPE = BlockEntityType.Builder.of(MachineCasingBlockEntity::new, AITBlocks.MACHINE_CASING).build(null);
    public static BlockEntityType<FabricatorBlockEntity> FABRICATOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(FabricatorBlockEntity::new, AITBlocks.FABRICATOR).build(null);
    public static BlockEntityType<EnvironmentProjectorBlockEntity> ENVIRONMENT_PROJECTOR_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(EnvironmentProjectorBlockEntity::new, AITBlocks.ENVIRONMENT_PROJECTOR).build(null);
    public static BlockEntityType<WaypointBankBlockEntity> WAYPOINT_BANK_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(WaypointBankBlockEntity::new, AITBlocks.WAYPOINT_BANK).build(null);
    public static final BlockEntityType<AITRadioBlockEntity> AIT_RADIO_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(AITRadioBlockEntity::new, AITBlocks.RADIO).build(null);
    public static BlockEntityType<RedstoneControlBlockEntity> REDSTONE_CONTROL_BLOCK_ENTITY = BlockEntityType.Builder.of(RedstoneControlBlockEntity::new, AITBlocks.REDSTONE_CONTROL_BLOCK).build(null);
    public static final BlockEntityType<FlagBlockEntity> FLAG_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(FlagBlockEntity::new, PlanetBlocks.FLAG).build(null);
    public static BlockEntityType<CableBlockEntity> CABLE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(CableBlockEntity::new, AITBlocks.CABLE_BLOCK).build(null);
    public static BlockEntityType<FullCableBlockEntity> FULL_CABLE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(FullCableBlockEntity::new, AITBlocks.CABLE_CONNECTOR_BLOCK ).build(null);
    public static BlockEntityType<PowerConverterBlock.BlockEntity> POWER_CONVERTER_BLOCK_TYPE = BlockEntityType.Builder.of(PowerConverterBlock.BlockEntity::new, AITBlocks.POWER_CONVERTER).build(null);
    public static BlockEntityType<FoodMachineBlockEntity> FOOD_MACHINE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(FoodMachineBlockEntity::new, AITBlocks.FOOD_MACHINE).build(null);
    public static BlockEntityType<GenericStructureSystemBlockEntity> GENERIC_SUBSYSTEM_BLOCK_TYPE = BlockEntityType.Builder.of(GenericStructureSystemBlockEntity::new, AITBlocks.GENERIC_SUBSYSTEM).build(null);
    public static BlockEntityType<AstralMapBlockEntity> ASTRAL_MAP = BlockEntityType.Builder.of(AstralMapBlockEntity::new, AITBlocks.ASTRAL_MAP).build(null);
    public static BlockEntityType<PottedSonicScrewdriverBlockEntity> POTTED_SONIC_SCREWDRIVER_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(PottedSonicScrewdriverBlockEntity::new, AITBlocks.POTTED_SONIC_SCREWDRIVER).build(null);
    public static BlockEntityType<UntemperedSchismBlockEntity> RIFT_RIPPER_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(UntemperedSchismBlockEntity::new, AITBlocks.UNTEMPERED_SCHISM).build(null);
    // TODO ADVENT might have to make this work like the block as well
    static {
        if (isUnlockedOnThisDay(Calendar.DECEMBER, 30)) {
            SNOW_GLOBE_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.of(SnowGlobeBlockEntity::new, AITBlocks.SNOW_GLOBE).build(null);
        }
    }
}
