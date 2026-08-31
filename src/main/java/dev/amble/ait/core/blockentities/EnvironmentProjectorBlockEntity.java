package dev.amble.ait.core.blockentities;

import static dev.amble.ait.core.blocks.EnvironmentProjectorBlock.*;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import dev.amble.ait.core.AITBlockEntityTypes;
import dev.amble.ait.core.blocks.EnvironmentProjectorBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.properties.Value;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class EnvironmentProjectorBlockEntity extends InteriorLinkableBlockEntity {

    private static final ResourceKey<Level> DEFAULT = Level.END;
    private ResourceKey<Level> current = DEFAULT;
    private float currentYaw = 0f;
    private float currentPitch = 0f;

    public EnvironmentProjectorBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.ENVIRONMENT_PROJECTOR_BLOCK_ENTITY_TYPE, pos, state);
    }

    public void neighborUpdate(BlockState state, Level world, BlockPos pos) {
        boolean powered = world.hasNeighborSignal(pos);

        if (powered != state.getValue(POWERED)) {
            if (state.getValue(ENABLED) != powered && this.isLinked()) {
                state = state.setValue(ENABLED, powered);

                EnvironmentProjectorBlock.toggle(this.tardis().get(), null, world, pos, state, powered);
            }

            state = state.setValue(POWERED, powered);
        }

        world.setBlock(pos, state.setValue(SILENT, world.getBlockState(pos.below()).is(BlockTags.WOOL)),
                Block.UPDATE_CLIENTS);
    }

    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player) {
        if (!this.isLinked())
            return InteractionResult.FAIL;

        Tardis tardis = this.tardis().get();

        if (player.isShiftKeyDown()) {
            state = state.cycle(ENABLED);
            AITMod.sendProjectorToggle(pos, state.getValue(ENABLED));
        }

        return InteractionResult.SUCCESS;
    }


    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);

        this.current = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(nbt.getString("dimension")));

        if (nbt.contains("yaw")) {
            this.currentYaw = nbt.getFloat("yaw");
        } else if (nbt.contains("direction")) {
            try {
                Direction dir = Direction.valueOf(nbt.getString("direction"));
                this.applyLegacyDirection(dir);
            } catch (IllegalArgumentException ignored) {
            }
        }

        if (nbt.contains("pitch")) {
            this.currentPitch = nbt.getFloat("pitch");
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);

        nbt.putString("dimension", this.current.location().toString());
        nbt.putFloat("yaw", this.currentYaw);
        nbt.putFloat("pitch", this.currentPitch);
    }

    private void applyLegacyDirection(Direction dir) {
        switch (dir) {
            case UP -> { this.currentYaw = 0f; this.currentPitch = 90f; }
            case DOWN -> { this.currentYaw = 0f; this.currentPitch = -90f; }
            default -> { this.currentYaw = dir.toYRot(); this.currentPitch = 0f; }
        }
    }

    public void switchSkybox(Tardis tardis, BlockState state, Player player) {
        ServerLevel next = findNext(this.current);

        if (next == null) {
            player.sendSystemMessage(Component.translatableWithFallback("message.ait.projector.no_worlds",
                    "No worlds are currently available for the Environment Projector."));
            return;
        }

        player.sendSystemMessage(Component.translatable("message.ait.projector.skybox", next.dimension().location().toString()));
        AITMod.LOGGER.debug("Last: {}, next: {}", this.current, next);

        this.current = next.dimension();

        if (state.getValue(EnvironmentProjectorBlock.ENABLED))
            this.apply(tardis, state);
    }

    public void toggle(Tardis tardis, BlockState state, boolean active) {
        if (active) {
            this.apply(tardis, state);
        } else {
            this.disable(tardis);
        }
    }

    public void apply(Tardis tardis, BlockState state) {
        tardis.stats().skybox().set(this.current);
        tardis.stats().skyboxYaw().set(this.currentYaw);
        tardis.stats().skyboxPitch().set(this.currentPitch);
    }

    public void disable(Tardis tardis) {
        Value<ResourceKey<Level>> value = tardis.stats().skybox();

        if (same(this.current, value.get()))
            value.set(DEFAULT);
    }

    private static @Nullable ServerLevel findNext(ResourceKey<Level> last) {
        ServerLevel first = null;
        boolean returnNext = false;

        for (ServerLevel world : WorldUtil.getProjectorWorlds()) {
            if (TardisServerWorld.isTardisDimension(world))
                continue;

            if (first == null)
                first = world;

            if (returnNext)
                return world;

            if (same(world.dimension(), last))
                returnNext = true;
        }

        return first;
    }

    private static boolean same(ResourceKey<Level> a, ResourceKey<Level> b) {
        return a == b || a.location().equals(b.location());
    }

    public void setAnglesFromClient(float yaw, float pitch, ServerPlayer player) {
        if (!(this.level instanceof ServerLevel serverWorld))
            return;

        this.currentYaw = Mth.wrapDegrees(yaw);
        this.currentPitch = Mth.clamp(pitch, -90f, 90f);
        this.setChanged();

        BlockState state = serverWorld.getBlockState(this.worldPosition);

        Direction nearest = Direction.fromYRot(this.currentYaw);
        if (state.getValue(EnvironmentProjectorBlock.FACING) != nearest) {
            state = state.setValue(EnvironmentProjectorBlock.FACING, nearest);
            serverWorld.setBlock(this.worldPosition, state, Block.UPDATE_ALL);
        }

        if (state.getValue(EnvironmentProjectorBlock.ENABLED)) {
            Tardis tardis = this.tardis().get();
            if (tardis != null) {
                tardis.stats().skyboxYaw().set(this.currentYaw);
                tardis.stats().skyboxPitch().set(this.currentPitch);
            }
        }
    }

    public void setCurrentFromClient(ResourceKey<Level> key, ServerPlayer player) {
        this.current = key;
        this.setChanged();

        if (this.level instanceof ServerLevel serverWorld) {
            BlockState state = serverWorld.getBlockState(this.worldPosition);
            if (state.getValue(EnvironmentProjectorBlock.ENABLED)) {
                Tardis tardis = this.tardis().get();
                if (tardis != null) {
                    this.apply(tardis, state);
                }
            }

            serverWorld.sendBlockUpdated(this.worldPosition, state, state, Block.UPDATE_ALL);
        }
    }

}
