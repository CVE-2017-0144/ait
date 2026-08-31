package dev.amble.ait.core.drinks;

import java.util.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.item.TardisMatrixItem;
import dev.amble.ait.core.util.ItemNbt;

public class DrinkUtil {

    public static final String CUSTOM_DRINK_EFFECTS_KEY = "CustomDrinkEffects";
    public static final String CUSTOM_DRINK_COLOR_KEY = "CustomDrinkColor";
    public static final String DRINK_KEY = "Drink";
    private static final int DEFAULT_COLOR = TardisMatrixItem.colorToInt(210, 227, 252);
    private static final Component NONE_TEXT = Component.translatable("effect.none").withStyle(ChatFormatting.GRAY);
    public static final Drink EMPTY = DrinkRegistry.EMPTY_MUG;

    public static List<MobEffectInstance> getDrinkEffects(ItemStack stack) {
        return DrinkUtil.getDrinkEffects(ItemNbt.getNullable(stack));
    }

    public static List<MobEffectInstance> getDrinkEffects(Drink drink, Collection<MobEffectInstance> custom) {
        ArrayList<MobEffectInstance> list = Lists.newArrayList();
        list.addAll(drink.getEffects());
        list.addAll(custom);
        return list;
    }

    public static List<MobEffectInstance> getDrinkEffects(CompoundTag nbt) {
        ArrayList<MobEffectInstance> list = Lists.newArrayList();
        Drink drink = DrinkUtil.getDrink(nbt);

        if (drink == null) return list;

        list.addAll(DrinkUtil.getDrink(nbt).getEffects());
        DrinkUtil.getCustomDrinkEffects(nbt, list);
        return list;
    }

    public static List<MobEffectInstance> getCustomDrinkEffects(ItemStack stack) {
        return DrinkUtil.getCustomDrinkEffects(ItemNbt.getNullable(stack));
    }

    public static List<MobEffectInstance> getCustomDrinkEffects(@Nullable CompoundTag nbt) {
        ArrayList<MobEffectInstance> list = Lists.newArrayList();
        DrinkUtil.getCustomDrinkEffects(nbt, list);
        return list;
    }

    public static void getCustomDrinkEffects(@Nullable CompoundTag nbt, List<MobEffectInstance> list) {
        if (nbt != null && nbt.contains(CUSTOM_DRINK_EFFECTS_KEY, Tag.TAG_LIST)) {
            ListTag nbtList = nbt.getList(CUSTOM_DRINK_EFFECTS_KEY, Tag.TAG_COMPOUND);
            for (int i = 0; i < nbtList.size(); ++i) {
                CompoundTag nbtCompound = nbtList.getCompound(i);
                MobEffectInstance statusEffectInstance = MobEffectInstance.load(nbtCompound);
                if (statusEffectInstance == null) continue;
                list.add(statusEffectInstance);
            }
        }
    }

    public static int getColor(ItemStack stack) {
        CompoundTag nbtCompound = ItemNbt.getNullable(stack);
        if (nbtCompound != null && nbtCompound.contains(CUSTOM_DRINK_COLOR_KEY, Tag.TAG_ANY_NUMERIC)) {
            return nbtCompound.getInt(CUSTOM_DRINK_COLOR_KEY);
        }
        Drink drink = DrinkUtil.getDrink(stack);
        return drink == null || Objects.equals(drink, EMPTY) ? DEFAULT_COLOR : DrinkUtil.getColor(drink, DrinkUtil.getDrinkEffects(stack));
    }

    public static int getColor(Drink drink, Collection<MobEffectInstance> effects) {
        if (drink.getHasColor()) {
            // TODO: use hex or normal int rgb values instead of this shit
            Vector3f vector = drink.getColor();
            return TardisMatrixItem.colorToInt((int) (vector.x() * 255), (int) (vector.y() * 255), (int) (vector.z() * 255));
        }
        if (effects.isEmpty()) {
            return DEFAULT_COLOR;
        }
        float f = 0.0f;
        float g = 0.0f;
        float h = 0.0f;
        int j = 0;
        for (MobEffectInstance statusEffectInstance : effects) {
            if (!statusEffectInstance.isVisible()) continue;
            int k = statusEffectInstance.getEffect().value().getColor();
            int l = statusEffectInstance.getAmplifier() + 1;
            f += (float)(l * (k >> 16 & 0xFF)) / 255.0f;
            g += (float)(l * (k >> 8 & 0xFF)) / 255.0f;
            h += (float)(l * (k & 0xFF)) / 255.0f;
            j += l;
        }
        if (j == 0) {
            return 0;
        }
        f = f / (float)j * 255.0f;
        g = g / (float)j * 255.0f;
        h = h / (float)j * 255.0f;
        return (int)f << 16 | (int) g << 8 | (int)h;
    }

    public static void applyEffects(ItemStack drinkStack, LivingEntity user) {
        if (isMilk(getDrink(drinkStack))) {
            user.removeAllEffects();
            return;
        }

        List<MobEffectInstance> list = DrinkUtil.getDrinkEffects(drinkStack);
        for (MobEffectInstance statusEffectInstance : list) {
            if (statusEffectInstance.getEffect().value().isInstantenous()) {
                statusEffectInstance.getEffect().value().applyInstantenousEffect(null, null, user, statusEffectInstance.getAmplifier(), 1.0);
                continue;
            }
            user.addEffect(new MobEffectInstance(statusEffectInstance));
        }
    }

    private static boolean isMilk(Drink drink) {
        return DrinkRegistry.getInstance().getOrFallback(AITMod.id("milk")).equals(drink);
    }

    public static Drink getDrink(ItemStack stack) {
        return DrinkUtil.getDrink(ItemNbt.getNullable(stack));
    }

    public static Drink getDrink(@Nullable CompoundTag compound) {
        if (compound == null) {
            return EMPTY;
        }
        return DrinkRegistry.getInstance().get(ResourceLocation.tryParse(compound.getString(DRINK_KEY)));
    }

    public static ItemStack setDrink(ItemStack stack, Drink drink) {
        if (drink == null || drink == EMPTY) {
            ItemNbt.edit(stack, tag -> tag.remove(DRINK_KEY));
        } else {
            ItemNbt.edit(stack, tag -> tag.putString(DRINK_KEY, drink.id().toString()));
        }
        return stack;
    }

    public static ItemStack setCustomDrinkEffects(ItemStack stack, Collection<MobEffectInstance> effects) {
        if (effects.isEmpty()) {
            return stack;
        }
        CompoundTag nbtCompound = ItemNbt.get(stack);
        ListTag nbtList = nbtCompound.getList(CUSTOM_DRINK_EFFECTS_KEY, Tag.TAG_LIST);
        for (MobEffectInstance statusEffectInstance : effects) {
            nbtList.add(statusEffectInstance.save());
        }
        nbtCompound.put(CUSTOM_DRINK_EFFECTS_KEY, nbtList);
        return stack;
    }

    public static void buildTooltip(ItemStack stack, List<Component> list, float durationMultiplier) {
        DrinkUtil.buildTooltip(DrinkUtil.getDrinkEffects(stack), list, durationMultiplier);
    }

    public static void buildTooltip(List<MobEffectInstance> statusEffects, List<Component> list, float durationMultiplier) {
        List<Pair<Holder<Attribute>, AttributeModifier>> modifiers = Lists.newArrayList();

        if (statusEffects.isEmpty()) {
            list.add(NONE_TEXT);
        } else {
            for (MobEffectInstance instance : statusEffects) {
                MutableComponent text = Component.translatable(instance.getDescriptionId());
                MobEffect effect = instance.getEffect().value();

                effect.createModifiers(instance.getAmplifier(),
                        (attribute, modifier) -> modifiers.add(new Pair<>(attribute, modifier)));

                if (instance.getAmplifier() > 0)
                    text = Component.translatable("potion.withAmplifier", text,
                            Component.translatable("potion.potency." + instance.getAmplifier()));

                if (!instance.endsWithin(20))
                    text = Component.translatable("potion.withDuration", text,
                            MobEffectUtil.formatDuration(instance, durationMultiplier, 20.0f));

                list.add(text.withStyle(effect.getCategory().getTooltipFormatting()));
            }
        }

        if (modifiers.isEmpty())
            return;

        list.add(CommonComponents.EMPTY);
        list.add(Component.translatable("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));

        for (Pair<Holder<Attribute>, AttributeModifier> pair : modifiers) {
            AttributeModifier modifier = pair.getSecond();
            double amount = modifier.amount();
            double shown = modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_BASE
                    || modifier.operation() == AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
                            ? amount * 100.0
                            : amount;

            if (amount > 0.0) {
                list.add(Component.translatable("attribute.modifier.plus." + modifier.operation().id(),
                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(shown),
                        Component.translatable(pair.getFirst().value().getDescriptionId()))
                        .withStyle(ChatFormatting.BLUE));
            } else if (amount < 0.0) {
                list.add(Component.translatable("attribute.modifier.take." + modifier.operation().id(),
                        ItemAttributeModifiers.ATTRIBUTE_MODIFIER_FORMAT.format(-shown),
                        Component.translatable(pair.getFirst().value().getDescriptionId()))
                        .withStyle(ChatFormatting.RED));
            }
        }
    }
}
