package dev.amble.ait.core.item.sonic;

import dev.amble.ait.core.engine.SubSystem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.TardisServerWorld;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedGlobalPos;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

public class TardisSonicMode extends SonicMode {

    protected TardisSonicMode(int index) {
        super(index);
    }

    @Override
    public void tick(ItemStack stack, Level world, LivingEntity user, int ticks, int ticksLeft) {
        if (!(world instanceof ServerLevel) || !(user instanceof Player player) || ticks % 10 != 0)
            return;

        this.process(stack, world, player);
    }

    public boolean process(ItemStack stack, Level world, Player user) {
        if (!(user instanceof ServerPlayer player))
            return false;

        Tardis tardis = SonicItem.getTardisStatic(world, stack);

        if (tardis == null)
            return false;

        boolean isMainHand = user.getMainHandItem().getItem() == stack.getItem();
        if (isMainHand) {
            HitResult hitResult = SonicMode.getHitResult(user, 2);

            // summon to selected block
            return this.interactBlock(stack, world, player, BlockPos.containing(hitResult.getLocation()));
        }
        boolean isLookingUp = user.getXRot() < 0;

        if (isLookingUp) {
            // send tardis to flight and disengage handbrake
            tardis.travel().handbrake(false);
            tardis.travel().dematerialize();

            player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.flight"), true);

            return true;
        }

        // turn on handbrake and engage refueling
        tardis.travel().handbrake(true);
        tardis.fuel().refueling().set(true);

        player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.refuel"), true);

        return true;
    }

    private boolean interactBlock(ItemStack stack, Level world, ServerPlayer player, BlockPos pos) {
        // summon tardis to block
        Tardis tardis = SonicItem.getTardisStatic(world, stack);

        if (tardis == null)
            return false;

        // fail silently if in tardis dim
        if (TardisServerWorld.isTardisDimension(world)) return false;

        // get position of player
        CachedDirectedGlobalPos targetPos = CachedDirectedGlobalPos.create(player.serverLevel().dimension(), pos, DirectedGlobalPos.getGeneralizedRotation(player.getMotionDirection()));

        if (!tardis.subsystems().get(SubSystem.Id.STABILISERS).isUsable()) {
            player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.does_not_have_stabilisers"), true);
            return false;
        }

        // check if player is within range of and in same world as TARDIS
        Level tardisWorld = tardis.travel().position().getWorld();
        boolean inSameWorld = player.level().equals(tardisWorld);
        boolean isNearTardis = TardisUtil.isNearTardis(player, tardis, 256);
        double distance = TardisUtil.distanceFromTardis(player, tardis);

        if (!tardis.fuel().hasPower()){
            player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.does_not_have_power"), true);
            return false;
        }

        if (tardis.fuel().getCurrentFuel() <= TardisUtil.estimatedFuelCost(player, tardis, distance)) {
            player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.insufficient_fuel"), true);
            return false;
        }

        if (!inSameWorld || !isNearTardis) {
            player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.is_not_in_range"), true);
            return false;
        }

        tardis.travel().destination(targetPos);
        tardis.travel().autopilot(true);
        tardis.travel().dematerialize();

        // inform player
        player.displayClientMessage(Component.translatable("sonic.ait.mode.tardis.location_summon"), true);

        return true;
    }

    @Override
    public Component text() {
        return Component.translatable("sonic.ait.mode.tardis").withStyle(ChatFormatting.BLUE, ChatFormatting.BOLD);
    }

    @Override
    public int maxTime() {
        return 2 * 20;
    }

    @Override
    public ResourceLocation model(SonicSchema.Models models) {
        return models.tardis();
    }
}
