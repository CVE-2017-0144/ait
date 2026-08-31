package dev.amble.ait.module.planet.core.item;

import java.util.function.Supplier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import dev.amble.ait.module.planet.core.PlanetBlocks;

public enum PlanetToolMaterial implements Tier {
    MARTIAN_STONE(1, 201, 4.0f, 1.0f, 6,
            () -> Ingredient.of(PlanetBlocks.MARTIAN_STONE)),
    ANORTHOSITE(1, 194, 4.0f, 1.0f, 6,
                          () -> Ingredient.of(PlanetBlocks.ANORTHOSITE));

    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    PlanetToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attckDamage, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attckDamage;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getUses() {
        return this.itemDurability;
    }

    @Override
    public float getSpeed() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.attackDamage;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return this.miningLevel >= 3 ? BlockTags.INCORRECT_FOR_DIAMOND_TOOL
                : this.miningLevel == 2 ? BlockTags.INCORRECT_FOR_IRON_TOOL
                        : BlockTags.INCORRECT_FOR_STONE_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
