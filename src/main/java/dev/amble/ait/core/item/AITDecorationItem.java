package dev.amble.ait.core.item;

import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.entities.BOTIPaintingEntity;

public class AITDecorationItem extends Item {
    private final EntityType<? extends HangingEntity> entityType;

    public AITDecorationItem(EntityType<? extends HangingEntity> type, Item.Properties settings) {
        super(settings);
        this.entityType = type;
    }



    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos blockPos = context.getClickedPos();
        Direction clickedSide = context.getClickedFace();
        Player player = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();

        Direction facing = clickedSide.getAxis().isVertical() ? player.getDirection().getOpposite() : clickedSide;

        BlockPos placementPos = blockPos.relative(facing);

        if (player != null && !this.canPlaceOn(player, facing, itemStack, placementPos)) {
            return InteractionResult.FAIL;
        }

        Level world = context.getLevel();

        if (this.entityType == AITEntityTypes.GALLIFREY_FALLS_PAINTING_ENTITY_TYPE || this.entityType == AITEntityTypes.TRENZALORE_PAINTING_ENTITY_TYPE) {
            Optional<BOTIPaintingEntity> optional = BOTIPaintingEntity.placePainting((EntityType<? extends BOTIPaintingEntity>)
                    this.entityType, world, placementPos, facing);

            if (optional.isEmpty()) {
                return InteractionResult.CONSUME;
            }

            BOTIPaintingEntity paintingEntity = optional.get();

            CompoundTag nbtData = itemStack.getTag();
            if (nbtData != null) {
                EntityType.updateCustomEntityTag(world, player, paintingEntity, nbtData);
            }

            if (!world.isClientSide) {
                paintingEntity.playPlacementSound();
                world.gameEvent(player, GameEvent.ENTITY_PLACE, paintingEntity.position());
                world.addFreshEntity(paintingEntity);
            }

            itemStack.shrink(1);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }

        return InteractionResult.PASS;
    }


    protected boolean canPlaceOn(Player player, Direction side, ItemStack stack, BlockPos pos) {
        return !side.getAxis().isVertical() && player.mayUseItemAt(pos, side, stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, world, tooltip, context);


        if (stack.getItem() == AITItems.GALLIFREY_FALLS_PAINTING) {
            tooltip.add(Component.translatable("painting.ait.gallifrey_falls.title").withStyle(ChatFormatting.YELLOW));
            tooltip.add(Component.translatable("painting.ait.gallifrey_falls.author").withStyle(ChatFormatting.GRAY));
        }
        if (stack.getItem() == AITItems.TRENZALORE_PAINTING) {
            tooltip.add(Component.translatable("painting.ait.trenzalore.title").withStyle(ChatFormatting.YELLOW));
            tooltip.add(Component.translatable("painting.ait.trenzalore.author").withStyle(ChatFormatting.GRAY));
        }
    }
}
