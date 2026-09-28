package dev.amble.ait.data.datapack.exterior;

import java.util.Map;
import java.util.function.Function;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.amble.ait.core.tardis.handler.BiomeHandler;
import dev.amble.ait.data.enummap.EnumMap;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.StringRepresentable;

public record BiomeOverrides(EnumMap.Compliant<BiomeHandler.BiomeType, ResourceLocation> lookup) {

    public static BiomeOverrides EMPTY = new BiomeOverrides(createMap());

    private static EnumMap.Compliant<BiomeHandler.BiomeType, ResourceLocation> createMap() {
        return new EnumMap.Compliant<>(() -> BiomeHandler.BiomeType.VALUES, ResourceLocation[]::new);
    }

    private BiomeOverrides(Map<BiomeHandler.BiomeType, ResourceLocation> map) {
        this(createMap());
        this.lookup.putAll(map);
    }

    public ResourceLocation get(BiomeHandler.BiomeType type) {
        return this.lookup.get(type);
    }

    @OnlyIn(Dist.CLIENT)
    public void validate() {
        ResourceManager manager = Minecraft.getInstance().getResourceManager();

        this.lookup.map(id -> {
            if (id == null)
                return null;

            return manager.getResource(id).isPresent() ? id : null;
        });
    }

    public static BiomeOverrides of(Function<BiomeHandler.BiomeType, ResourceLocation> func) {
        EnumMap.Compliant<BiomeHandler.BiomeType, ResourceLocation> map = createMap();

        for (BiomeHandler.BiomeType type : BiomeHandler.BiomeType.VALUES) {
            map.put(type, func.apply(type));
        }

        return new BiomeOverrides(map);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(BiomeOverrides overrides) {
        return new Builder(overrides);
    }

    public static final MapCodec<BiomeOverrides> CODEC = RecordCodecBuilder.mapCodec(instance ->
            instance.group(
                    Codec.simpleMap(
                            BiomeHandler.BiomeType.CODEC, ResourceLocation.CODEC,
                            StringRepresentable.keys(BiomeHandler.BiomeType.VALUES)
                    ).forGetter(overrides -> overrides.lookup)
            ).apply(instance, BiomeOverrides::new));

    public static class Builder {

        private final EnumMap.Compliant<BiomeHandler.BiomeType, ResourceLocation> map = createMap();

        private Builder() { }

        private Builder(BiomeOverrides overrides) {
            for (BiomeHandler.BiomeType type : BiomeHandler.BiomeType.VALUES) {
                this.with(type, overrides.lookup().get(type));
            }
        }

        public Builder with(BiomeHandler.BiomeType type, ResourceLocation id) {
            map.put(type, id);
            return this;
        }

        public Builder with(Function<BiomeHandler.BiomeType, ResourceLocation> func, BiomeHandler.BiomeType... types) {
            for (BiomeHandler.BiomeType type : types) {
                this.with(type, func.apply(type));
            }

            return this;
        }

        public BiomeOverrides build() {
            return new BiomeOverrides(map);
        }
    }
}
