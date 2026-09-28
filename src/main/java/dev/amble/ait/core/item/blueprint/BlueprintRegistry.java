package dev.amble.ait.core.item.blueprint;

import dev.amble.ait.AITMod;
import dev.amble.ait.core.loot.SetBlueprintLootFunction;
import dev.amble.lib.register.datapack.SimpleDatapackRegistry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;


public class BlueprintRegistry extends SimpleDatapackRegistry<BlueprintSchema> {
    public static LootItemFunctionType<SetBlueprintLootFunction> BLUEPRINT_TYPE;

    private static final BlueprintRegistry instance = new BlueprintRegistry();

    public BlueprintRegistry() {
        super(BlueprintSchema::fromInputStream, BlueprintSchema.CODEC, "blueprint", true, AITMod.MOD_ID);
    }

    @Override
    public void onCommonInit() {
        super.onCommonInit();
        this.defaults();
    }

    @Override
    protected void defaults() {
    }

    @Override
    public BlueprintSchema fallback() {
        return null;
    }

    public static BlueprintRegistry getInstance() {
        return instance;
    }
}
