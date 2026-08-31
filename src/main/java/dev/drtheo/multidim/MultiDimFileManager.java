package dev.drtheo.multidim;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.drtheo.multidim.api.MultiDimServerWorld;
import dev.drtheo.multidim.api.WorldBlueprint;
import dev.drtheo.multidim.event.ServerCrashEvent;
import dev.drtheo.multidim.event.WorldSaveEvent;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.LevelResource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class MultiDimFileManager {

    private static final Gson gson = new Gson();

    public static Path getRootSavePath(Path root) {
        return root.resolve(".multidim2");
    }

    public static Path getRootSavePath(MinecraftServer server) {
        return getRootSavePath(server.getWorldPath(LevelResource.ROOT));
    }

    public static Path getSavePath(MinecraftServer server, ResourceLocation id) {
        return getRootSavePath(server).resolve(id.getNamespace()).resolve(id.getPath() + ".json");
    }

    public static void init() {
        ServerCrashEvent.EVENT.register((server, report) -> {
            for (ServerLevel world : server.getAllLevels()) {
                writeIfNeeded(server, world);
            }
        });

        WorldSaveEvent.EVENT.register(world -> writeIfNeeded(world.getServer(), world));
        ServerLifecycleEvents.SERVER_STARTED.register(MultiDimFileManager::readAll);
    }

    public static void writeIfNeeded(MinecraftServer server, ServerLevel world) {
        if (world instanceof MultiDimServerWorld msw && msw.getBlueprint().persistent())
            write(server, msw);
    }

    public static void write(MinecraftServer server, MultiDimServerWorld world) {
        ResourceKey<Level> key = world.dimension();
        Path file = getSavePath(server, key.location());

        try {
            if (!Files.exists(file)) {
                Files.createDirectories(file.getParent());
                Files.createFile(file);
            }

            JsonObject root = new JsonObject();
            root.addProperty("blueprint", world.getBlueprint().id().toString());

            Files.writeString(file, gson.toJson(root));
        } catch (IOException e) {
            MultiDimMod.LOGGER.warn("Couldn't create world file! {}", key.location(), e);
        }
    }

    public static void readAll(MinecraftServer server) {
        if (server == null || !server.isRunning())
            return;

        Path root = getRootSavePath(server);

        if (!Files.exists(root))
            return;

        MultiDim multidim = MultiDim.get(server);

        try (Stream<Path> stream = Files.list(root)) {
            stream.forEach(namespace -> {
                if (!Files.isDirectory(namespace))
                    return;

                readNamespace(multidim, namespace);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readNamespace(MultiDim multidim, Path namespace) {
        try (Stream<Path> stream = Files.list(namespace)) {
            stream.forEach(file -> {
                Saved saved = readFromFile(multidim, namespace.getFileName().toString(), file);

                if (saved == null) {
                    MultiDimMod.LOGGER.warn("Failed to load world from file {}", file);
                    return;
                }

                WorldBlueprint blueprint = multidim.getBlueprint(saved.blueprint);

                if (blueprint.persistent() && blueprint.autoLoad())
                    multidim.load(blueprint, saved.world);
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Saved readFromFile(MultiDim multidim, String namespace, Path file) {
        String fileName = file.getFileName().toString();

        ResourceLocation id = new ResourceLocation(
                namespace, fileName.substring(0, fileName.length() - 5) // remove .json suffix
        );

        return read(multidim.server, id);
    }

    private static Saved read(MinecraftServer server, ResourceLocation id) {
        Path file = getSavePath(server, id);

        try {
            JsonObject element = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
            ResourceLocation blueprint = ResourceLocation.tryParse(element.get("blueprint").getAsString());

            return new Saved(blueprint, ResourceKey.create(Registries.DIMENSION, id));
        } catch (Throwable e) {
            MultiDimMod.LOGGER.warn("Couldn't read world file! {}", id, e);
            return null;
        }
    }

    public record Saved(ResourceLocation blueprint, ResourceKey<Level> world) { }
}
