package dev.amble.lib.datagen.lang;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;
import dev.amble.lib.container.RegistryContainer;
import dev.amble.lib.container.impl.BlockContainer;
import dev.amble.lib.container.impl.ItemContainer;
import dev.amble.lib.datagen.util.NoEnglish;
import dev.amble.lib.platform.datagen.PlatformDataOutput;
import dev.amble.lib.util.ReflectionUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AmbleLanguageProvider extends LanguageProvider {

    private static final Logger LOGGER = LogManager.getLogger("AmbleKit");

    private final PlatformDataOutput output;
    protected final String modid;
    protected HashMap<String, String> translations = new HashMap<>();
    public LanguageType language;

    public AmbleLanguageProvider(PlatformDataOutput output, LanguageType language,
            CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, output.getModId(), language.name().toLowerCase());

        this.output = output;
        this.language = language;
        this.modid = output.getModId();
    }

    @Override
    protected void addTranslations() {
        this.translations.forEach(this::add);

        this.output.findResource("assets/" + this.modid + "/lang/"
                + this.language.name().toLowerCase() + ".existing.json").ifPresent(path -> {
                    try (BufferedReader reader = Files.newBufferedReader(path)) {
                        JsonObject existing = JsonParser.parseReader(reader).getAsJsonObject();

                        for (Map.Entry<String, JsonElement> entry : existing.entrySet()) {
                            this.add(entry.getKey(), entry.getValue().getAsString());
                        }
                    } catch (Exception e) {
                        LOGGER.warn("Failed to add existing language file! ({}) | ",
                                this.language.name().toLowerCase(), e);
                    }
                });
    }

    /**
     * Adds a translation to the language file.
     *
     * @param item
     *            The item to add the translation for.
     * @param translation
     *            The translation.
     */
    public void addTranslation(Item item, String translation) {
        translations.put(item.getDescriptionId(), translation);
    }

    /**
     * Adds a translation to the language file.
     *
     * @param itemGroup
     *            The item group to add the translation for.
     * @param translation
     *            The translation.
     */
    public void addTranslation(CreativeModeTab itemGroup, String translation) {
        if (!(itemGroup.getDisplayName().getContents() instanceof TranslatableContents translatable))
            return;

        translations.put(translatable.getKey(), translation);
    }

    /**
     * Adds a translation to the language file.
     *
     * @param key
     *            The key to add the translation for.
     * @param translation
     *            The translation.
     */
    public void addTranslation(String key, String translation) {
        translations.put(key, translation);
    }

    /**
     * Adds a translation to the language file
     *
     * @param block
     *            The block to add the translation for
     * @param translation
     *            The translation
     */
    public void addTranslation(Block block, String translation) {
        translations.put(block.getDescriptionId(), translation);
    }

    public <T, R extends RegistryContainer<T>> void addTranslation(Class<R> containerClazz, Class<T> valueClazz,
            Translator<T> translator) {
        Set<T> values = ReflectionUtil.getAnnotatedValues(containerClazz, valueClazz, NoEnglish.class, true).keySet();

        for (T value : values) {
            translator.addTranslation(this, value);
        }
    }

    public void translateItems(Class<? extends ItemContainer> container) {
        addTranslation(container, Item.class, ((provider, value) -> {
            if (value instanceof BlockItem) return;

            provider.addTranslation(value, getNameFromKey(value.getDescriptionId()));
        }));
    }

    public void translateBlocks(Class<? extends BlockContainer> container) {
        addTranslation(container, Block.class,
                ((provider, value) -> provider.addTranslation(value, getNameFromKey(value.getDescriptionId()))));
    }

    @FunctionalInterface
    public interface Translator<T> {
        void addTranslation(AmbleLanguageProvider provider, T value);
    }

    public static String getNameFromKey(String key) {
        // seperate at last .
        int lastDot = key.lastIndexOf('.');
        if (lastDot == -1) {
            return key;
        }
        String suffix = key.substring(lastDot + 1);

        // split at _
        String[] parts = suffix.split("_");

        // capitalise beginning of each string and join with space
        StringBuilder builder = new StringBuilder();
        for (String part : parts) {
            builder.append(part.substring(0, 1).toUpperCase());
            builder.append(part.substring(1));
            builder.append(" ");
        }

        // remove last space
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
