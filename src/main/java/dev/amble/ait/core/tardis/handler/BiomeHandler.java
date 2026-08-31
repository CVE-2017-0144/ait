package dev.amble.ait.core.tardis.handler;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.apache.commons.lang3.StringUtils;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.data.datapack.exterior.BiomeOverrides;
import dev.amble.ait.data.enummap.Ordered;
import dev.amble.ait.data.properties.Property;
import dev.amble.ait.data.properties.Value;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.platform.worldgen.BiomeTags;

/**
 * @author Loqor
 * TODO reminder to work on this more, making it so you have to
 *         brush off different biomes if you don't just demat/remat + having to
 *         land on the respective blocks / has to snow for it to take effect.
 */
public class BiomeHandler extends KeyedTardisComponent {

    private static final Property<BiomeType> TYPE = Property.forEnum("type", BiomeType.class, BiomeType.DEFAULT);
    private final Value<BiomeType> type = TYPE.create(this);

    static {
        TardisEvents.DEMAT.register(tardis -> {
            tardis.<BiomeHandler>handler(Id.BIOME).forceTypeDefault();
            return TardisEvents.Interaction.PASS;
        });
        TardisEvents.LANDED.register(tardis -> tardis.<BiomeHandler>handler(Id.BIOME).update());
        TardisEvents.ENTER_FLIGHT.register(tardis -> tardis.<BiomeHandler>handler(Id.BIOME).forceTypeDefault());
    }

    public BiomeHandler() {
        super(Id.BIOME);
    }

    @Override
    public void onLoaded() {
        type.of(this, TYPE);
    }

    public void update() {
        this.update(this.tardis.travel().position());
    }

    public void update(CachedDirectedGlobalPos globalPos) {
        if (globalPos == null)
            return;

        Holder<Biome> entry = globalPos.getWorld().getBiome(globalPos.getPos());
        BiomeType biome = getTagForBiome(entry);

        this.type.set(biome);
        this.sync();
    }

    public void forceTypeDefault() {
        this.type.set(BiomeType.DEFAULT);
        this.sync();
    }

    public BiomeType getBiomeKey() {
        return this.type.get();
    }

    // FIXME(PERFORMANCE)
    private static BiomeType getTagForBiome(Holder<Biome> biome) {
        if (biome.is(BiomeTags.SNOWY) || biome.is(BiomeTags.SNOWY_PLAINS)
                || biome.is(BiomeTags.ICY))
            return BiomeType.SNOWY;

        if (biome.is(BiomeTags.DESERT) || biome.is(BiomeTags.BEACH)
                || biome.is(BiomeTags.DEAD))
            return BiomeType.SANDY;

        if (biome.is(BiomeTags.BADLANDS))
            return BiomeType.RED_SANDY;

        if (biome.is(BiomeTags.SWAMP))
            return BiomeType.MUDDY;

        if (biome.is(BiomeTags.IN_THE_END))
            return BiomeType.CHORUS;

        if (biome.is(BiomeTags.FLORAL))
            return BiomeType.CHERRY;

        ResourceKey<Biome> biomeKey = biome.unwrapKey().orElse(null);

        if (biomeKey == Biomes.DEEP_DARK)
            return BiomeType.SCULK;

        if (biomeKey == Biomes.CHERRY_GROVE)
            return BiomeType.CHERRY;

        return BiomeType.DEFAULT;
    }

    public enum BiomeType implements StringRepresentable, Ordered {
        DEFAULT, SNOWY("_snowy"),
        SCULK("_sculk"),
        SANDY("_sand"),
        RED_SANDY("_red_sand"),
        MUDDY("_mud"),
        CHORUS("_chorus"),
        CHERRY("_cherry");

        public static final BiomeType[] VALUES = BiomeType.values();
        public static final EnumCodec<BiomeType> CODEC = StringRepresentable.fromEnum(() -> VALUES);

        private final String suffix;

        BiomeType(String suffix) {
            this.suffix = suffix;
        }

        BiomeType() {
            this(null);
        }

        @Override
        public String getSerializedName() {
            return StringUtils.capitalize(this.toString().replace("_", " "));
        }

        public ResourceLocation getTexture(ResourceLocation texture) {
            if (this.suffix == null)
                return texture;

            String path = texture.getPath();
            return AITMod.id(path.substring(0, path.length() - 4) + this.suffix + ".png");
        };

        public ResourceLocation get(BiomeOverrides overrides) {
            if (overrides == null)
                return null;

            return overrides.get(this);
        }

        @Override
        public int index() {
            return ordinal();
        }
    }
}
