package dev.amble.ait.module;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import dev.amble.ait.module.decoration.DecorationModule;
import dev.amble.ait.module.gun.GunModule;
import dev.amble.ait.module.planet.PlanetModule;
import dev.amble.lib.register.datapack.DatapackRegistry;

// these arent datapack definable
public class ModuleRegistry extends DatapackRegistry<Module> {
    public static final ModuleRegistry INSTANCE = new ModuleRegistry();

    @Override
    public void onCommonInit() {
        super.onCommonInit();

        register(PlanetModule.instance());
        register(DecorationModule.instance());
        register(GunModule.instance());

        iterator().forEachRemaining(Module::init);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void onClientInit() {
        super.onClientInit();

        iterator().forEachRemaining(Module::initClient);
    }

    @Override
    public Module register(Module schema) {
        if (!schema.shouldRegister()) return schema;

        return super.register(schema);
    }

    @Override
    public Module fallback() {
        return null;
    }

    @Override
    public void syncToClient(ServerPlayer player) {

    }

    @Override
    public void readFromServer(FriendlyByteBuf buf) {

    }

    public static ModuleRegistry instance() {
        return INSTANCE;
    }
}
