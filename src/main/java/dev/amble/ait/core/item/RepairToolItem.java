package dev.amble.ait.core.item;

import static dev.amble.ait.client.util.TooltipUtil.addMultilineTooltip;

import java.util.List;

import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.block.SubSystemBlockEntity;
import dev.amble.ait.core.entities.ConsoleControlEntity;
import org.jetbrains.annotations.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.item.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class RepairToolItem extends Item {
    public RepairToolItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        addMultilineTooltip(tooltip, Component.translatable("tooltip.ait.repair_tool")
                .withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        user.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        float f;
        if (!(user instanceof Player playerEntity)) {
            return;
        }
        if ((double)(f = RepairToolItem.getPullProgress(this.getUseDuration(stack) - remainingUseTicks)) < 0.1) {
            return;
        }

        HitResult hitResult = playerEntity.pick(16, 0.0f, false);
        if (hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof SubSystemBlockEntity subSystem) {
                if (subSystem.system() instanceof DurableSubSystem durable) {
                    playerEntity.displayClientMessage(Component.literal(Math.round(durable.durability()) + "/" + DurableSubSystem.MAX_DURABILITY).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD).withBold(true)), true);
                    world.playSound(null, pos, SoundEvents.ANCIENT_DEBRIS_HIT, SoundSource.BLOCKS, 0.5f, 0.8f);
                    if (durable.durability() < DurableSubSystem.MAX_DURABILITY) {
                        float val = world.getRandom().nextIntBetweenInclusive(2, 10) * DurableSubSystem.MAX_DURABILITY / 100f;
                        durable.addDurability(val);
                        stack.hurtAndBreak(1, playerEntity, playerEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

                        world.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 0.5f, 1.5f);

                        for (int i = 0; i < (val / 2); i++) {
                            world.addAlwaysVisibleParticle(ParticleTypes.ENCHANT, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, 0, 0.1f, 0);
                        }
                        return;
                    }
                }
            }
        } else if (hitResult instanceof EntityHitResult result) {
            if (result.getEntity() instanceof ConsoleControlEntity consoleControl) {
                playerEntity.displayClientMessage(Component.literal(consoleControl.getDurability() + "/" + ConsoleControlEntity.MAX_DURABILITY).setStyle(Style.EMPTY.withColor(ChatFormatting.GOLD).withBold(true)), true);
                world.playSound(null, consoleControl.blockPosition(), SoundEvents.ANCIENT_DEBRIS_HIT, SoundSource.BLOCKS, 0.5f, 0.8f);
                if (consoleControl.getDurability() < DurableSubSystem.MAX_DURABILITY) {
                    consoleControl.addDurability(world.getRandom().nextFloat());
                    stack.hurtAndBreak(1, playerEntity, playerEntity.getUsedItemHand() == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);

                    world.playSound(null, consoleControl.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS, 0.5f, 1.5f);

                    for (int i = 0; i < (world.getRandom().nextIntBetweenInclusive(2, 5) / 2); i++) {
                        world.addAlwaysVisibleParticle(ParticleTypes.ENCHANT, consoleControl.getX() + 0.5, consoleControl.getY() + 1, consoleControl.getZ() + 0.5, 0, 0.1f, 0);
                    }
                    return;
                }
            }
        }

        world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.CHAIN_HIT, SoundSource.PLAYERS, 1.0f, 1.0f / (world.getRandom().nextFloat() * 0.4f + 1.2f) + f * 0.5f);
        playerEntity.awardStat(Stats.ITEM_USED.get(this));
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0f;
        if ((f = (f * f + f * 2.0f) / 3.0f) > 1.0f) {
            f = 1.0f;
        }
        return f;
    }
}
