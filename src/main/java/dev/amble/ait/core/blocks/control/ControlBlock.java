package dev.amble.ait.core.blocks.control;

import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.blockentities.control.ControlBlockEntity;
import dev.amble.ait.core.blocks.types.HorizontalDirectionalBlock;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.item.control.ControlBlockItem;
import dev.amble.ait.core.item.sonic.SonicMode;

public abstract class ControlBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public ControlBlock(Properties settings) {
        super(settings);
    }

    @Override
    public Item asItem() {
        return null;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer,
            ItemStack itemStack) {
        Optional<ResourceLocation> id = ControlBlockItem.findControlId(itemStack);

        if (id.isEmpty())
            return;

        BlockEntity be = world.getBlockEntity(pos);
        if (!(be instanceof ControlBlockEntity))
            return;

        ((ControlBlockEntity) be).setControlId(id.get());
    }

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
            BlockHitResult hit) {
        if (world.isClientSide())
            return InteractionResult.SUCCESS;

        if (!(world.getBlockEntity(pos) instanceof ControlBlockEntity be))
            return InteractionResult.FAIL;

        if (isHoldingScanningSonic(player))
            sendSonicMessage((ServerPlayer) player, be);

        return be.run((ServerPlayer) player, false)
                ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        if (world.isClientSide())
            return;

        if (!(world.getBlockEntity(pos) instanceof ControlBlockEntity be))
            return;

        if (isHoldingScanningSonic(player))
            sendSonicMessage((ServerPlayer) player, be);

        be.run((ServerPlayer) player, true);
    }

    protected static boolean isHoldingScanningSonic(Player player) {
        return SonicItem.mode(player.getMainHandItem()) == SonicMode.Modes.SCANNING;
    }

    protected static void sendSonicMessage(ServerPlayer player, ControlBlockEntity entity) {
        player.sendSystemMessage(Component.translatable(entity.getControl().id().toLanguageKey("control")).withStyle(ChatFormatting.AQUA));
    }
}
