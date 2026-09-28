package dev.amble.ait.core.item;

import java.util.List;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.drinks.Drink;
import dev.amble.ait.core.drinks.DrinkRegistry;
import dev.amble.ait.core.drinks.DrinkUtil;
import org.jetbrains.annotations.Nullable;

public class DrinkItem extends Item {
    private static final int MAX_USE_TIME = 32;

    public DrinkItem(Item.Properties settings) {
        super(settings);
    }

    @Override
    public String getDescriptionId(ItemStack stack) {
        Drink drink = DrinkUtil.getDrink(stack);
        if (drink != null) {
            return "ait.item.drink." + drink.id().getPath();
        }
        return this.getOrCreateDescriptionId();
    }

    @Override
    public ItemStack getDefaultInstance() {
        return DrinkUtil.setDrink(super.getDefaultInstance(), DrinkRegistry.EMPTY_MUG);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {

        Player playerEntity = user instanceof Player ? (Player)user : null;
        if (playerEntity instanceof ServerPlayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger((ServerPlayer)playerEntity, stack);
        }
        if (!world.isClientSide) {
            DrinkUtil.applyEffects(stack, user);
        }
        if (playerEntity != null) {
            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }
        if (playerEntity == null || !playerEntity.getAbilities().instabuild) {
            if (stack.isEmpty()) {
                return this.getDefaultInstance();
            }
            if (playerEntity != null) {
                playerEntity.getInventory().add(this.getDefaultInstance());
            }
        }
        user.gameEvent(GameEvent.DRINK);
        return stack;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        BlockPos blockPos = context.getClickedPos();
        Player playerEntity = context.getPlayer();
        ItemStack itemStack = context.getItemInHand();
        BlockState blockState = world.getBlockState(blockPos);

        if (context.getClickedFace() != Direction.DOWN && blockState.is(BlockTags.CONVERTABLE_TO_MUD) && DrinkUtil.getDrink(itemStack) == DrinkRegistry.EMPTY_MUG) {
            world.playSound(null, blockPos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0f, 1.0f);
            playerEntity.setItemInHand(context.getHand(), ItemUtils.createFilledResult(itemStack, playerEntity, new ItemStack(AITItems.MUG)));
            playerEntity.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
            if (!world.isClientSide) {
                ServerLevel serverWorld = (ServerLevel)world;
                for (int i = 0; i < 5; ++i) {
                    serverWorld.sendParticles(ParticleTypes.SPLASH, (double)blockPos.getX() + world.random.nextDouble(), blockPos.getY() + 1, (double)blockPos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                }
            }
            world.playSound(null, blockPos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0f, 1.0f);
            world.gameEvent(null, GameEvent.FLUID_PLACE, blockPos);
            world.setBlockAndUpdate(blockPos, Blocks.MUD.defaultBlockState());
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);

        if (DrinkUtil.getDrink(stack) != DrinkRegistry.getInstance().get(AITMod.id("mug_empty"))) {
            return ItemUtils.startUsingInstantly(world, user, hand);
        }

        return InteractionResultHolder.fail(stack);
    }



    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        DrinkUtil.buildTooltip(stack, tooltip, 1.0f);
    }
}
