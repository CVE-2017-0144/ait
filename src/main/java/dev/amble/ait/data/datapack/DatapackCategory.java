package dev.amble.ait.data.datapack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.resources.ResourceLocation;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.AITMod;
import dev.amble.ait.data.schema.exterior.ExteriorCategorySchema;

public class DatapackCategory extends ExteriorCategorySchema {
    public static final Codec<ExteriorCategorySchema> CODEC = RecordCodecBuilder.create(instance -> instance
            .group(ResourceLocation.CODEC.fieldOf("id").forGetter(ExteriorCategorySchema::id),
                    Codec.STRING.fieldOf("name").forGetter(ExteriorCategorySchema::name))
            .apply(instance, DatapackCategory::new));

    public DatapackCategory(ResourceLocation id, String name) {
        super(id, name);
    }

    public static DatapackCategory fromInputStream(InputStream stream) {
        return fromJson(JsonParser.parseReader(new InputStreamReader(stream)).getAsJsonObject());
    }

    public static DatapackCategory fromJson(JsonObject json) {
        AtomicReference<DatapackCategory> created = new AtomicReference<>();

        CODEC.decode(JsonOps.INSTANCE, json).ifSuccess(var -> {
            created.set((DatapackCategory) var.getFirst());
        }).ifError(err -> {
            created.set(null);
            AITMod.LOGGER.error("Error decoding datapack category: " + err);
        });

        return created.get();
    }
}
