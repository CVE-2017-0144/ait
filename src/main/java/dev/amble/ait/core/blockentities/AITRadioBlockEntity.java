package dev.amble.ait.core.blockentities;

import static dev.amble.ait.core.blocks.RadioBlock.*;
import static java.lang.Double.NaN;

import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import dev.amble.ait.core.AITBlockEntityTypes;

public class AITRadioBlockEntity extends BlockEntity {

    public Player player;
    public float tickRotT, tickRotV;

    private int timeInSeconds;

    public AITRadioBlockEntity(BlockPos pos, BlockState state) {
        super(AITBlockEntityTypes.AIT_RADIO_BLOCK_ENTITY_TYPE, pos, state);
        setTickRot("tuner", 0);
        setTickRot("volume", 0);
        toggleRadio(true);
    }

    public static void tick(Level world1, BlockPos pos, BlockState state, AITRadioBlockEntity be) {
        be.timeInSeconds++;
        if (be.getTickRot("volume") > 0 && be.hasSecondPassed()) {
            be.timeInSeconds = 0;
            be.spawnNoteParticle(world1, pos);
        }
    }

    public boolean hasSecondPassed() {
        return this.timeInSeconds >= 20;
    }

    private void spawnNoteParticle(Level world, BlockPos pos) {
        if (world instanceof ServerLevel serverWorld) {
            Vec3 vec3d = Vec3.atBottomCenterOf(pos).add(0.0, 1.2f, 0.0);
            serverWorld.sendParticles(ParticleTypes.NOTE, vec3d.x(), vec3d.y(), vec3d.z(), 0, NaN, 0.0, 0.0,
                    1.0F);
        }
    }

    public void useOn(BlockHitResult hit, BlockState state, Player player, Level world, boolean isSneaking) {
        this.player = player;
        double mouseX = (hit.getLocation().x * 16) - (hit.getBlockPos().getX() * 16);
        double mouseY = (hit.getLocation().y * 16) - (hit.getBlockPos().getY() * 16);
        double mouseZ = (hit.getLocation().z * 16) - (hit.getBlockPos().getZ() * 16);

        double[] xTuner = {0, 0, PZ_AXIS_TUNER.min(Direction.Axis.X), NZ_AXIS_TUNER.min(Direction.Axis.X),
                PX_AXIS_TUNER.min(Direction.Axis.X), NX_AXIS_TUNER.min(Direction.Axis.X)};
        double[] yTuner = {0, 0, PZ_AXIS_TUNER.min(Direction.Axis.Y), NZ_AXIS_TUNER.min(Direction.Axis.Y),
                PX_AXIS_TUNER.min(Direction.Axis.Y), NX_AXIS_TUNER.min(Direction.Axis.Y)};
        double[] zTuner = {0, 0, PZ_AXIS_TUNER.min(Direction.Axis.Z), NZ_AXIS_TUNER.min(Direction.Axis.Z),
                PX_AXIS_TUNER.min(Direction.Axis.Z), NX_AXIS_TUNER.min(Direction.Axis.Z)};

        double[] xVolume = {0, 0, PZ_AXIS_VOLUME.min(Direction.Axis.X), NZ_AXIS_VOLUME.min(Direction.Axis.X),
                PX_AXIS_VOLUME.min(Direction.Axis.X), NX_AXIS_VOLUME.min(Direction.Axis.X)};
        double[] yVolume = {0, 0, PZ_AXIS_VOLUME.min(Direction.Axis.Y), NZ_AXIS_VOLUME.min(Direction.Axis.Y),
                PX_AXIS_VOLUME.min(Direction.Axis.Y), NX_AXIS_VOLUME.min(Direction.Axis.Y)};
        double[] zVolume = {0, 0, PZ_AXIS_VOLUME.min(Direction.Axis.Z), NZ_AXIS_VOLUME.min(Direction.Axis.Z),
                PX_AXIS_VOLUME.min(Direction.Axis.Z), NX_AXIS_VOLUME.min(Direction.Axis.Z)};

        double width = 2;
        double height = 2;
        double length = 2;
        float multiVal = 36; // 22.5;

        boolean tmx = mouseX >= (xTuner[state.getValue(FACING).ordinal()] * 16)
                && mouseY >= (yTuner[state.getValue(FACING).ordinal()] * 16)
                && mouseZ >= (zTuner[state.getValue(FACING).ordinal()] * 16)
                && mouseX <= ((xTuner[state.getValue(FACING).ordinal()] * 16) + width)
                && mouseY <= ((yTuner[state.getValue(FACING).ordinal()] * 16) + height)
                && mouseZ <= ((zTuner[state.getValue(FACING).ordinal()] * 16) + length);

        boolean vmx = mouseX >= (xVolume[state.getValue(FACING).ordinal()] * 16)
                && mouseY >= (yVolume[state.getValue(FACING).ordinal()] * 16)
                && mouseZ >= (zVolume[state.getValue(FACING).ordinal()] * 16)
                && mouseX <= ((xVolume[state.getValue(FACING).ordinal()] * 16) + width)
                && mouseY <= ((yVolume[state.getValue(FACING).ordinal()] * 16) + height)
                && mouseZ <= ((zVolume[state.getValue(FACING).ordinal()] * 16) + length);

        if (tmx && this.isRadioOn() && !isSneaking) {
            if (this.tickRotT < (360F - 22.5F) * ((float) Math.PI / 180f))
                this.tickRotT = this.tickRotT + 22.5F * ((float) Math.PI / 180f);
            else if (this.tickRotT >= (360F - 22.5F) * ((float) Math.PI / 180f))
                this.tickRotT = 0;
            if (player != null)
                player.displayClientMessage(Component.translatable("message.ait.radio.changing_frequency"), true);
            world.playSound(null, worldPosition, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.MASTER, 0.1F,
                    this.tickRotT * this.tickRotT);
        }
        if (vmx && this.isRadioOn() && !isSneaking) {
            if (this.tickRotV <= (360F - multiVal) * ((float) Math.PI / 180f))
                this.tickRotV = this.tickRotV + multiVal * ((float) Math.PI / 180f);
            else if (this.tickRotV <= 360F * ((float) Math.PI / 180f))
                this.tickRotV = 0;

            world.playSound(null, worldPosition, SoundEvents.UI_BUTTON_CLICK.value(), SoundSource.MASTER, 0.1F,
                    this.tickRotV * this.tickRotV);
        }

        if (!isSneaking && tmx && this.isRadioOn())
            this.setTickRot("tuner", this.tickRotT);

        if (!isSneaking && vmx && this.isRadioOn())
            this.setTickRot("volume", this.tickRotV);

        // @TODO Use SoundInstance and Minecraft.getInstance().getSoundManager() to play
        // music. It
        // allows for multiple
        // values like looping and stopping the audio. It's perfect for our use case. -
        // Loqor
        // @Creativious
        if (((int) (Math.nextUp(this.getTickRot("volume") * (180 / Math.PI) * 11) / 360) + 1) == 10) {
            // world.playSound(null, pos, AITSounds.SECRET_MUSIC, SoundCategory.MASTER, 1F, 1F);
        }

        if (this.isRadioOn()) {
            if (isSneaking && !vmx && !tmx) {
                this.toggleRadio(!this.isRadioOn());
                if (player != null)
                    player.displayClientMessage(Component.translatable("message.ait.radio.off"), true);
                world.playSound(null, worldPosition, SoundEvents.CHAIN_HIT, SoundSource.MASTER, 0.2F, 1F);
            }
        } else {
            this.toggleRadio(!this.isRadioOn());
            if (player != null)
                player.displayClientMessage(Component.translatable("message.ait.radio.on"), true);
            world.playSound(null, worldPosition, SoundEvents.CHAIN_HIT, SoundSource.MASTER, 0.2F, 2F);
        }
    }

    public float getTickRot(String name) {
        return switch (name) {
            case "tuner" -> tickRotT;
            case "volume" -> tickRotV;
            default -> throw new IllegalArgumentException("Unexpected value: " + name);
        };
    }

    public boolean isRadioOn() {
        return true;
    }

    public void setTickRot(String name, float value) {
        if (name.equals("tuner"))
            this.tickRotT = value;
        else if (name.equals("volume"))
            this.tickRotV = value;
    }

    public void toggleRadio(boolean bool) {
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
    }

    record Handle(Function<Direction, VoxelShape> shape) {

        public boolean check(BlockHitResult hit, BlockState state) {
            double mouseX = (hit.getLocation().x * 16) - (hit.getBlockPos().getX() * 16);
            double mouseY = (hit.getLocation().y * 16) - (hit.getBlockPos().getY() * 16);
            double mouseZ = (hit.getLocation().z * 16) - (hit.getBlockPos().getZ() * 16);

            VoxelShape s = shape.apply(state.getValue(FACING));

            double minX = s.min(Direction.Axis.X);
            double maxX = s.max(Direction.Axis.X);
            double minY = s.min(Direction.Axis.Y);
            double maxY = s.max(Direction.Axis.Y);
            double minZ = s.min(Direction.Axis.Z);
            double maxZ = s.max(Direction.Axis.Z);

            // DOWN: >= 0 <= 0 + 2
            // UP: >= 16 <= 18
            //
            boolean checkX = mouseX >= (minX * 16) && mouseX <= (maxX * 16);

            boolean checkY = mouseY >= (minY * 16) && mouseY <= (maxY * 16);

            boolean checkZ = mouseZ >= (minZ * 16) && mouseZ <= (maxZ * 16);

            return checkX && checkY && checkZ;
        }
    }
}
