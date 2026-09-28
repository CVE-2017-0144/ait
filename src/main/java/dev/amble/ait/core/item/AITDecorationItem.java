package dev.amble.ait.core.item;

import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import dev.amble.ait.core.AITEntityTypes;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.entities.BOTIPaintingEntity;
import org.jetbrains.annotations.Nullable;

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

            CustomData entityTag = itemStack.get(DataComponents.ENTITY_DATA);
            if (entityTag != null) {
                EntityType.updateCustomEntityTag(world, player, paintingEntity, entityTag);
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
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);


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
