package dev.amble.lib.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Rarity;

public class AItemSettings extends Properties {

    private CreativeModeTab group;

    public AItemSettings group(CreativeModeTab group) {
        this.group = group;
        return this;
    }
    public AItemSettings group(ResourceKey<CreativeModeTab> group) {
        this.group = BuiltInRegistries.CREATIVE_MODE_TAB.get(group);
        return this;
    }



    @Override
    public AItemSettings food(FoodProperties foodComponent) {
        return (AItemSettings) super.food(foodComponent);
    }

    @Override
    public AItemSettings stacksTo(int maxCount) {
        return (AItemSettings) super.stacksTo(maxCount);
    }


    @Override
    public AItemSettings durability(int maxDamage) {
        return (AItemSettings) super.durability(maxDamage);
    }

    @Override
    public AItemSettings craftRemainder(Item recipeRemainder) {
        return (AItemSettings) super.craftRemainder(recipeRemainder);
    }

    @Override
    public AItemSettings rarity(Rarity rarity) {
        return (AItemSettings) super.rarity(rarity);
    }

    @Override
    public AItemSettings fireResistant() {
        return (AItemSettings) super.fireResistant();
    }

    @Override
    public AItemSettings requiredFeatures(FeatureFlag... features) {
        return (AItemSettings) super.requiredFeatures(features);
    }

    public CreativeModeTab group() {
        return group;
    }
}
