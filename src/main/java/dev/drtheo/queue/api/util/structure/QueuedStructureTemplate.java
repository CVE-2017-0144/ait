package dev.drtheo.queue.api.util.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.mojang.datafixers.util.Pair;
import dev.drtheo.queue.api.ActionQueue;
import dev.drtheo.queue.mixin.StructureTemplateAccessor;
import dev.drtheo.scheduler.api.TimeUnit;
import dev.drtheo.scheduler.api.common.TaskStage;
import org.jetbrains.annotations.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.core.*;
import net.minecraft.world.phys.*;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;

public class QueuedStructureTemplate {

    private static final Direction[] directions = new Direction[]{ Direction.UP, Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST };

    private final List<StructureTemplate.Palette> blockInfoLists;
    private final List<StructureTemplate.StructureEntityInfo> entities;
    private final Vec3i size;

    public QueuedStructureTemplate(StructureTemplate template) {
        this((StructureTemplateAccessor) template);
    }

    private QueuedStructureTemplate(StructureTemplateAccessor accessor) {
        this.blockInfoLists = accessor.getBlockInfo();
        this.entities = accessor.getEntities();
        this.size = accessor.getSize();
    }

    public Optional<ActionQueue> place(ServerLevelAccessor world, BlockPos pos, BlockPos pivot, StructurePlaceSettings placementData, RandomSource random, int flags) {
        if (this.blockInfoLists.isEmpty())
            return Optional.empty();

        List<StructureTemplate.StructureBlockInfo> randomBlocks = placementData.getRandomPalette(this.blockInfoLists, pos).blocks();

        List<BlockPos> flowingFluid = new ArrayList<>(randomBlocks.size());
        List<BlockPos> stillFluid = new ArrayList<>(randomBlocks.size());

        if (randomBlocks.isEmpty() && (placementData.isIgnoreEntities() || this.entities.isEmpty()) || this.size.getX() < 1 || this.size.getY() < 1 || this.size.getZ() < 1)
            return Optional.empty();

        BoundingBox blockBox = placementData.getBoundingBox();
        List<Pair<BlockPos, CompoundTag>> nbtList = new ArrayList<>(randomBlocks.size());

        var ctx = new Object() {
            int x1 = Integer.MAX_VALUE;
            int y1 = Integer.MAX_VALUE;
            int z1 = Integer.MAX_VALUE;

            int x2 = Integer.MIN_VALUE;
            int y2 = Integer.MIN_VALUE;
            int z2 = Integer.MIN_VALUE;
        };

        List<StructureTemplate.StructureBlockInfo> processedBlocks = StructureTemplate.processBlockInfos(world, pos, pivot, placementData, randomBlocks);
        Iterator<StructureTemplate.StructureBlockInfo> blockInfoIter = processedBlocks.iterator();

        return Optional.of(new ActionQueue().thenRunSteps(() -> {
            if (!blockInfoIter.hasNext())
                return true;

            StructureTemplate.StructureBlockInfo blockInfo = blockInfoIter.next();
            BlockPos blockPos = blockInfo.pos();

            if (blockBox != null && !blockBox.isInside(blockPos))
                return false;

            FluidState fluidState = world.getFluidState(blockPos);
            BlockState blockState = blockInfo.state().mirror(placementData.getMirror()).rotate(placementData.getRotation());

            if (blockInfo.nbt() != null)
                Clearable.tryClear(world.getBlockEntity(blockPos));

            // !
            world.setBlock(blockPos, blockState, flags);

            ctx.x1 = Math.min(ctx.x1, blockPos.getX());
            ctx.y1 = Math.min(ctx.y1, blockPos.getY());
            ctx.z1 = Math.min(ctx.z1, blockPos.getZ());

            ctx.x2 = Math.max(ctx.x2, blockPos.getX());
            ctx.y2 = Math.max(ctx.y2, blockPos.getY());
            ctx.z2 = Math.max(ctx.z2, blockPos.getZ());

            nbtList.add(Pair.of(blockPos, blockInfo.nbt()));
            BlockEntity blockEntity = world.getBlockEntity(blockPos);

            // !
            if (blockInfo.nbt() != null && blockEntity != null)
                this.readNbt(world, blockEntity, blockInfo.nbt(), random);

            if (fluidState == null)
                return false;

            if (blockState.getFluidState().isSource()) {
                stillFluid.add(blockPos);
                return false;
            }

            if (!(blockState.getBlock() instanceof LiquidBlockContainer fillable))
                return false;

            // !
            fillable.placeLiquid(world, blockPos, blockState, fluidState);

            if (fluidState.isSource())
                return false;

            flowingFluid.add(blockPos);
            return false;
        }, TaskStage.startWorldTick(world.getLevel()), TimeUnit.TICKS, 1, 20)
                .thenRun(() -> {
                    // !
                    this.fillWithFluid(world, flowingFluid, stillFluid);

                    if (ctx.x1 <= ctx.x2)
                        this.update(world, placementData, nbtList, ctx.x1, ctx.y1, ctx.z1,
                                ctx.x2, ctx.y2, ctx.z2, flags);

                    if (!placementData.isIgnoreEntities())
                        this.spawnEntities(world, pos, placementData.getMirror(), placementData.getRotation(), placementData.getRotationPivot(), blockBox, placementData.shouldFinalizeEntities());
                }));
    }

    protected void readNbt(ServerLevelAccessor world, BlockEntity blockEntity, CompoundTag nbt, RandomSource random) {
        if (blockEntity instanceof RandomizableContainerBlockEntity)
            nbt.putLong("LootTableSeed", random.nextLong());

        blockEntity.loadWithComponents(nbt, world.registryAccess());
    }

    private void update(ServerLevelAccessor world, StructurePlaceSettings placementData, List<Pair<BlockPos, CompoundTag>> nbtList, int x1, int y1, int z1, int x2, int y2, int z2, int flags) {
        if (!placementData.getKnownShape()) {
            BitSetDiscreteVoxelShape voxelSet = new BitSetDiscreteVoxelShape(x2 - x1 + 1, y2 - y1 + 1, z2 - z1 + 1);

            for (Pair<BlockPos, CompoundTag> pair : nbtList) {
                BlockPos pos = pair.getFirst();
                voxelSet.fill(pos.getX() - x1, pos.getY() - y1, pos.getZ() - z1);
            }

            StructureTemplate.updateShapeAtEdge(world, flags, voxelSet, x1, y1, z1);
        }

        for (Pair<BlockPos, CompoundTag> pair : nbtList) {
            BlockPos pos = pair.getFirst();
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (!placementData.getKnownShape()) {
                BlockState originalState = world.getBlockState(pos);
                BlockState processedState = Block.updateFromNeighbourShapes(originalState, world, pos);

                // !
                if (originalState != processedState)
                    world.setBlock(pos, processedState, flags & ~Block.UPDATE_NEIGHBORS | Block.UPDATE_KNOWN_SHAPE);

                world.blockUpdated(pos, processedState.getBlock());
            }

            if (pair.getSecond() == null || blockEntity == null)
                continue;

            blockEntity.setChanged();
        }
    }

    private void fillWithFluid(ServerLevelAccessor world, List<BlockPos> flowing, List<BlockPos> still) {
        boolean shouldContinue = true;

        while (shouldContinue && !flowing.isEmpty()) {
            shouldContinue = false;

            for (BlockPos blockPos : flowing) {
                BlockState blockState = world.getBlockState(blockPos);
                FluidState fluidState = world.getFluidState(blockPos);
                Block block = blockState.getBlock();

                if (!(block instanceof LiquidBlockContainer fillable))
                    continue;

                for (int o = 0; o < directions.length && !fluidState.isSource(); ++o) {
                    BlockPos offsetPos = blockPos.relative(directions[o]);
                    FluidState offsetFluidState = world.getFluidState(offsetPos);
                    if (!offsetFluidState.isSource() || still.contains(offsetPos))
                        continue;

                    fluidState = offsetFluidState;
                }

                if (!fluidState.isSource())
                    continue;

                // !
                fillable.placeLiquid(world, blockPos, blockState, fluidState);
                shouldContinue = true;
            }
        }
    }

    private void spawnEntities(ServerLevelAccessor world, BlockPos pos, Mirror mirror, Rotation rotation, BlockPos pivot, @Nullable BoundingBox area, boolean initializeMobs) {
        for (StructureTemplate.StructureEntityInfo structureEntityInfo : this.entities) {
            BlockPos blockPos = StructureTemplate.transform(structureEntityInfo.blockPos, mirror, rotation, pivot).offset(pos);

            if (area != null && !area.isInside(blockPos))
                continue;

            CompoundTag nbtCompound = structureEntityInfo.nbt.copy();

            Vec3 vec3d = StructureTemplate.transform(structureEntityInfo.pos, mirror, rotation, pivot);
            Vec3 vec3d2 = vec3d.add(pos.getX(), pos.getY(), pos.getZ());

            ListTag nbtList = new ListTag();

            nbtList.add(DoubleTag.valueOf(vec3d2.x));
            nbtList.add(DoubleTag.valueOf(vec3d2.y));
            nbtList.add(DoubleTag.valueOf(vec3d2.z));

            nbtCompound.put("Pos", nbtList);
            nbtCompound.remove("UUID");

            getEntity(world, nbtCompound).ifPresent(entity -> {
                float yaw = entity.rotate(rotation) + entity.mirror(mirror) - entity.getYRot();
                entity.moveTo(vec3d.x, vec3d.y, vec3d.z, yaw, entity.getXRot());

                if (initializeMobs && entity instanceof Mob mob)
                    mob.finalizeSpawn(world, world.getCurrentDifficultyAt(
                            BlockPos.containing(vec3d2)
                    ), MobSpawnType.STRUCTURE, null);

                world.addFreshEntityWithPassengers(entity);
            });
        }
    }

    private static Optional<Entity> getEntity(ServerLevelAccessor world, CompoundTag nbt) {
        try {
            return EntityType.create(nbt, world.getLevel());
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}