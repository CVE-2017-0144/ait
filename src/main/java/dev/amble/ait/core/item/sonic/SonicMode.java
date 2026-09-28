package dev.amble.ait.core.item.sonic;

import java.util.function.Function;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.core.AITTags;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.data.enummap.Ordered;
import dev.amble.ait.data.schema.sonic.SonicSchema;

public abstract class SonicMode implements Ordered {

    private static final int MAX_DISTANCE = 16;

    public static class Modes {
        public static final SonicMode[] VALUES = new SonicMode[4];
        private static int lastIndex = 0;

        public static final SonicMode INTERACTION = register(InteractionSonicMode::new);
        public static final SonicMode OVERLOAD = register(OverloadSonicMode::new);
        public static final SonicMode SCANNING = register(ScanningSonicMode::new);
        public static final SonicMode TARDIS = register(TardisSonicMode::new);

        public static final SonicMode INACTIVE = new InactiveSonicMode();

        public static SonicMode register(Function<Integer, SonicMode> consumer) {
            SonicMode mode = consumer.apply(lastIndex);
            VALUES[lastIndex] = mode;

            lastIndex++;
            return mode;
        }

        public static SonicMode next(SonicMode mode) {
            int nextIndex = mode.index() + 1;

            if (nextIndex == VALUES.length)
                return VALUES[0];

            return VALUES[nextIndex];
        }

        public static SonicMode previous(SonicMode mode) {
            int previousIndex = mode.index() - 1;

            if (previousIndex < 0)
                return VALUES[VALUES.length - 1];

            return VALUES[previousIndex];
        }

        public static SonicMode get(int index) {
            if (index == -1)
                return INACTIVE;

            return VALUES[index];
        }

        public static SonicMode getAndWrap(Integer index) {
            if (index == null || index < 0)
                return INACTIVE;

            return get(index % VALUES.length);
        }

        public static int size() {
            return VALUES.length;
        }
    }

    private final int index;

    protected SonicMode(int index) {
        this.index = index;
    }

    public SonicMode next() {
        return Modes.next(this);
    }

    public SonicMode previous() {
        return Modes.previous(this);
    }

    public abstract Component text();

    public abstract int maxTime();

    public boolean startUsing(ItemStack stack, Level world, Player user, InteractionHand hand) {
        return true;
    }

    public void tick(ItemStack stack, Level world, LivingEntity user, int ticks, int ticksLeft) { }

    public void stopUsing(ItemStack stack, Level world, LivingEntity user, int ticks, int ticksLeft) { }

    public void finishUsing(ItemStack stack, Level world, LivingEntity user) {
        this.stopUsing(stack, world, user, this.maxTime(), 0);
    }

    public abstract ResourceLocation model(SonicSchema.Models models);

    public int fuelCost() {
        return 1;
    }

    public static HitResult getHitResultForOutline(LivingEntity user) {
        return getHitResultForOutline(user, MAX_DISTANCE);
    }
    public static HitResult getHitResultForOutline(LivingEntity user, double distance) {
        BlockHitResult hitResult = null;

        if (user instanceof Player player) {
            Vec3 eyePos = player.getEyePosition(1.0F);
            Vec3 rotation = player.getViewVector(1.0F);
            Vec3 end = eyePos.add(rotation.scale(distance));
            hitResult = player.level().clip(new ClipContext(eyePos, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        }

        return hitResult;
    }
    public static HitResult getHitResult(LivingEntity user) {
        return getHitResult(user, MAX_DISTANCE);
    }
    public static HitResult getHitResult(LivingEntity user, double distance) {
        return ProjectileUtil.getHitResultOnViewVector(user, entity -> !entity.isSpectator() && entity.isPickable(), distance);
    }
    public static void checkSonicWoodAdvancementConditions(Level world, LivingEntity user, HitResult hitResult) {
        if (!(user instanceof ServerPlayer player))
            return;

        if (hitResult instanceof BlockHitResult blockHit) {
            BlockState state = world.getBlockState(blockHit.getBlockPos());

            if (state.is(AITTags.Blocks.WOODEN_BLOCKS)) {
                TardisCriterions.SONIC_WOOD.trigger(player);
            }
        }
    }

    @Override
    public int index() {
        return index;
    }
}
