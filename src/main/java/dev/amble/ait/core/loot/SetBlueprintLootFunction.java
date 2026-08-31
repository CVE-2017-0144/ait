
package dev.amble.ait.core.loot;

import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;
import dev.amble.ait.core.item.blueprint.BlueprintItem;
import dev.amble.ait.core.item.blueprint.BlueprintRegistry;
import dev.amble.ait.core.item.blueprint.BlueprintSchema;

public class SetBlueprintLootFunction extends LootItemConditionalFunction {
    private final Supplier<BlueprintSchema> blueprint;

    SetBlueprintLootFunction(LootItemCondition[] conditions, BlueprintSchema blueprint) {
        this(conditions, () -> blueprint);
    }

    SetBlueprintLootFunction(LootItemCondition[] conditions, Supplier<BlueprintSchema> blueprint) {
        super(conditions);
        this.blueprint = blueprint;
    }

    @Override
    public LootItemFunctionType getType() {
        return BlueprintRegistry.BLUEPRINT_TYPE;
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        BlueprintItem.setSchema(stack, this.blueprint.get());
        return stack;
    }

    public static LootItemConditionalFunction.Builder<?> builder(BlueprintSchema blueprint) {
        return SetBlueprintLootFunction
                .simpleBuilder((LootItemCondition[] conditions) -> new SetBlueprintLootFunction(conditions, blueprint));
    }

    public static LootItemConditionalFunction.Builder<?> random() {
        return SetBlueprintLootFunction
                .simpleBuilder((LootItemCondition[] conditions) -> new SetBlueprintLootFunction(conditions, () -> BlueprintRegistry.getInstance().getRandom()));
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SetBlueprintLootFunction> {
        @Override
        public void serialize(JsonObject jsonObject, SetBlueprintLootFunction setBlueprintLootFunction,
                JsonSerializationContext jsonSerializationContext) {
            super.serialize(jsonObject, setBlueprintLootFunction, jsonSerializationContext);
            jsonObject.addProperty("id",
                    setBlueprintLootFunction.blueprint.get().id().toString());
        }

        @Override
        public SetBlueprintLootFunction deserialize(JsonObject jsonObject,
                                                 JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] lootConditions) {
            String string = GsonHelper.getAsString(jsonObject, "id");
            BlueprintSchema blueprint = BlueprintRegistry.getInstance().getOptional(ResourceLocation.tryParse(string))
                    .orElseThrow(() -> new JsonSyntaxException("Unknown blueprint '" + string + "'"));
            return new SetBlueprintLootFunction(lootConditions, blueprint);
        }
    }
}
