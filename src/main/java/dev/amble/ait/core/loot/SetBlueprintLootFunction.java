package dev.amble.ait.core.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import dev.amble.ait.core.item.blueprint.BlueprintItem;
import dev.amble.ait.core.item.blueprint.BlueprintRegistry;
import dev.amble.ait.core.item.blueprint.BlueprintSchema;

public class SetBlueprintLootFunction extends LootItemConditionalFunction {

    public static final MapCodec<SetBlueprintLootFunction> CODEC = RecordCodecBuilder.mapCodec(
            instance -> commonFields(instance)
                    .and(ResourceLocation.CODEC.fieldOf("id").forGetter(function -> function.blueprint.get().id()))
                    .apply(instance, SetBlueprintLootFunction::new));

    private final Supplier<BlueprintSchema> blueprint;

    private SetBlueprintLootFunction(List<LootItemCondition> conditions, ResourceLocation id) {
        this(conditions, () -> BlueprintRegistry.getInstance().getOptional(id)
                .orElseThrow(() -> new IllegalStateException("Unknown blueprint '" + id + "'")));
    }

    SetBlueprintLootFunction(List<LootItemCondition> conditions, BlueprintSchema blueprint) {
        this(conditions, () -> blueprint);
    }

    SetBlueprintLootFunction(List<LootItemCondition> conditions, Supplier<BlueprintSchema> blueprint) {
        super(conditions);
        this.blueprint = blueprint;
    }

    @Override
    public LootItemFunctionType<SetBlueprintLootFunction> getType() {
        return BlueprintRegistry.BLUEPRINT_TYPE;
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        BlueprintItem.setSchema(stack, this.blueprint.get());
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder(BlueprintSchema blueprint) {
        return SetBlueprintLootFunction.simpleBuilder(conditions -> new SetBlueprintLootFunction(conditions, blueprint));
    }

    public static LootItemConditionalFunction.Builder<?> random() {
        return SetBlueprintLootFunction.simpleBuilder(
                conditions -> new SetBlueprintLootFunction(conditions, () -> BlueprintRegistry.getInstance().getRandom()));
    }
}
