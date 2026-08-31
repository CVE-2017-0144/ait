package dev.drtheo.gaslighter.mixin;

import dev.drtheo.gaslighter.api.FakeBlockEvents;
import dev.drtheo.gaslighter.api.Twitter;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerGamePacketListenerImpl.class, priority = 1001)
public abstract class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayer player;

    @Redirect(method = "handleUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayerGameMode;useItemOn(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"))
    public InteractionResult onPlayerInteractBlock(ServerPlayerGameMode instance, ServerPlayer player, Level world, ItemStack stack, InteractionHand hand, BlockHitResult hitResult) {
        ServerLevel serverWorld = this.player.serverLevel();
        BlockPos blockPos = hitResult.getBlockPos();

        if (serverWorld instanceof Twitter twitter && twitter.ait$isFake(blockPos)) {
            FakeBlockEvents.Action action = FakeBlockEvents.INTERACT.invoker().check(player, hand, hitResult.getBlockPos());

            if (action.shouldRemove())
                return InteractionResult.PASS;
        }

        return instance.useItemOn(player, world, stack, hand, hitResult);
    }

    @Inject(method = "handleUseItemOn", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"), cancellable = true)
    public void onPlayerInteractBlock(ServerboundUseItemOnPacket packet, CallbackInfo ci) {
        ServerLevel serverWorld = this.player.serverLevel();

        BlockHitResult blockHitResult = packet.getHitResult();
        BlockPos blockPos = blockHitResult.getBlockPos();

        BlockState state = serverWorld.getBlockState(blockPos);

        if (serverWorld instanceof Twitter twitter && twitter.ait$isFake(blockPos)) {
            FakeBlockEvents.Action action = FakeBlockEvents.CHECK.invoker().check(player, packet.getHand(), state, blockPos);

            if (!action.shouldRemove())
                ci.cancel();
        }
    }
}
