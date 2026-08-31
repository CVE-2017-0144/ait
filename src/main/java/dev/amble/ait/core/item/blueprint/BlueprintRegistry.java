package dev.amble.ait.core.item.blueprint;

import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.loot.SetBlueprintLootFunction;
import dev.amble.lib.platform.resource.ReloadListeners;
import dev.amble.lib.register.datapack.SimpleDatapackRegistry;


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
        ReloadListeners.register(PackType.CLIENT_RESOURCES, this);
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
