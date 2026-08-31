package dev.amble.ait.client.screens;

import java.util.*;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.CommonColors;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.screens.widget.CallbackCheckboxWidget;
import dev.amble.ait.core.blocks.AstralMapBlock;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.util.WorldUtil;

@Environment(EnvType.CLIENT)
public class AstralMapScreen extends Screen {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/astral_map.png");
    private static final Component SEARCH_TEXT = Component.translatable("gui.socialInteractions.search_hint")
            .withStyle(ChatFormatting.ITALIC).withStyle(ChatFormatting.GRAY);
    int bgHeight = 190;
    int bgWidth = 324;
    int left, top;

    private EditBox searchBox;
    private AstralMapListWidget entryList;
    private CallbackCheckboxWidget showStructuresCheckbox;
    private CallbackCheckboxWidget showBiomesCheckbox;

    private static final Component SHOW_STRUCTURES_MESSAGE = Component.translatable("screen.ait.astral_map.show_structures");
    private static final Component SHOW_BIOMES_MESSAGE = Component.translatable("screen.ait.astral_map.show_biomes");

    public enum Category {
        STRUCTURES,
        BIOMES;

        // Used for bitwise masking in order to combine shown and hidden categories
        public final int bitValue = 1 << this.ordinal();

        public boolean isShown(int shownCategories) {
            return (shownCategories & this.bitValue) != 0;
        }
    }

    public AstralMapScreen() {
        super(Component.translatable("screen." + AITMod.MOD_ID + ".astral_map"));
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.top = (this.height - this.bgHeight) / 2; // this means everything's centered and scaling, same for below
        this.left = (this.width - this.bgWidth) / 2;
        super.init();

        this.searchBox = new EditBox(this.minecraft.font, this.left + 12,
                this.top + 13, this.bgWidth - 26, 15, SEARCH_TEXT);
        this.searchBox.setHint(SEARCH_TEXT);
        this.searchBox.setResponder(this::onSearchChange);

        this.showStructuresCheckbox = new CallbackCheckboxWidget(this.left + 11, this.top + 33,
                20, 20, SHOW_STRUCTURES_MESSAGE, true, this::onShowStructuresChecked);
        this.showBiomesCheckbox = new CallbackCheckboxWidget(
                this.left + this.minecraft.font.width(SHOW_STRUCTURES_MESSAGE) + 38, this.top + 33,
                20, 20, SHOW_BIOMES_MESSAGE, true, this::onShowBiomesChecked);
        this.entryList = new AstralMapListWidget(width, height, top + 63,
                (height + bgHeight) / 2 - 9, 13);

        this.addWidget(searchBox);
        this.addRenderableWidget(showStructuresCheckbox);
        this.addRenderableWidget(showBiomesCheckbox);
        this.addWidget(entryList);
        this.setInitialFocus(searchBox);
    }

    private void onSearchChange(String currentSearch) {
        this.entryList.setCurrentSearch(currentSearch);
    }

    private void onShowStructuresChecked(CallbackCheckboxWidget checkbox) {
        this.showBiomesCheckbox.active = checkbox.selected() || !this.showBiomesCheckbox.selected();
        this.entryList.toggleCategory(Category.STRUCTURES);
    }

    private void onShowBiomesChecked(CallbackCheckboxWidget checkbox) {
        this.showStructuresCheckbox.active = checkbox.selected() || !this.showStructuresCheckbox.selected();
        this.entryList.toggleCategory(Category.BIOMES);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.searchBox.isFocused() && this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_ENTER) {
            var entry = this.entryList.getFocused();
            if (entry != null) {
                this.exit(entry);
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void exit(AstralMapListWidget.Entry entry) {
        var packetByteBuf = AitNetworking.buf().writeResourceLocation(entry.identifier);
        packetByteBuf.writeEnum(entry.category);
        AitNetworking.send(AstralMapBlock.REQUEST_SEARCH, packetByteBuf);
        this.minecraft.setScreen(null);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.fill(RenderType.endGateway(), this.left + 4, this.top + 4, this.left + this.bgWidth - 4,
                this.top + this.bgHeight - 4, 0xFFFFFF);
        context.fill(left + 2, top + 62, left + bgWidth - 4, top + bgHeight - 4, 0xAA000000);
        context.blit(TEXTURE, left, top, 0, 0, bgWidth, bgHeight, bgWidth, bgHeight);

        this.searchBox.render(context, mouseX, mouseY, delta);
        this.entryList.render(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
    }

    /**
     * Widget to display a searchable list of biomes and structures to locate
     */
     class AstralMapListWidget extends ObjectSelectionList<AstralMapListWidget.Entry> {

        // Bit flags are used to filter which categories to show. All categories are initially shown, hence the
        // bit inversion of 0 for initialization
        private int shownCategories = ~0;
        private final List<dev.amble.ait.client.screens.AstralMapScreen.AstralMapListWidget.Entry> entries = new ArrayList<>();
        private final Map<String, String> mods = new HashMap<>();
        private String currentSearch = "";

        // Used to check if the mouse has moved so that the top element is always focused when searching,
        // but moving the mouse will switch focus to the element under the mouse.
        private int lastMouseX, lastMouseY;
        private boolean shouldHover;

        public AstralMapListWidget(int width, int height, int top, int bottom, int elementHeight) {
            super(AstralMapScreen.this.minecraft, width, height, top, elementHeight);

            this.refreshEntries();
            this.replaceEntries(this.entries);
            this.setFocused(this.getFirstElement());
        }

        public String getModName(String modId) {
            return mods.computeIfAbsent(modId, id -> FabricLoader.getInstance().getModContainer(id)
                    .map(mod -> mod.getMetadata().getName()).orElse(id));
        }

        public void refreshEntries() {
            this.entries.clear();
            if (Category.STRUCTURES.isShown(this.shownCategories)) {
                for (ResourceLocation id : AstralMapBlock.structureIds) {
                    this.entries.add(new dev.amble.ait.client.screens.AstralMapScreen.AstralMapListWidget.Entry(id, Category.STRUCTURES, null));
                }
            }
            if (Category.BIOMES.isShown(this.shownCategories)) {
                for (ResourceLocation id : minecraft.level.registryAccess().registryOrThrow(Registries.BIOME).keySet()) {
                    this.entries.add(new dev.amble.ait.client.screens.AstralMapScreen.AstralMapListWidget.Entry(id, Category.BIOMES, id.toLanguageKey("biome")));
                }
            }
            this.entries.sort((e1, e2) -> e1.text.getString().compareToIgnoreCase(e2.text.getString()));
        }

        public void setCurrentSearch(String search) {
            this.refreshEntries();
            this.currentSearch = search.strip();
            String[] splitSearch = this.currentSearch.split(" ");
            for (String substring : splitSearch) {
                if (substring.isEmpty()) continue;
                String lowercase = substring.toLowerCase(Locale.ROOT);
                // Allow users to search by mod by prefixing their search with "@"
                if (lowercase.charAt(0) == '@') {
                    if (lowercase.length() < 2) continue;
                    // a "@" and at least one more character
                    String modId = lowercase.substring(1);
                    this.entries.removeIf(entry -> !entry.identifier.getNamespace()
                            .contains(modId));
                } else {
                    this.entries.removeIf(entry -> !entry.text.toString().toLowerCase(Locale.ROOT)
                            .contains(lowercase));
                }
            }
            this.setScrollAmount(0);
            this.replaceEntries(this.entries);
            if (!this.children().isEmpty()) {
                this.setFocused(this.getFirstElement());
            }
        }

        public void toggleCategory(Category category) {
            // Bitwise XOR mask toggles the category
            this.shownCategories ^= category.bitValue;
            this.setCurrentSearch(this.currentSearch);
            this.setScrollAmount(0);
        }

        @Override
        public int getRowWidth() {
            return 290 + (this.getMaxScroll() > 0 ? 0 : 10); // Adjust for scrollbar width
        }

        @Override
        protected int getScrollbarPosition() {
            return (this.width + bgWidth) / 2 - 18;
        }

        @Override
        public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
            // Adjust for scrollbar width
            this.setX(this.getMaxScroll() > 0 ? -5 : 0);

            if (this.lastMouseX == 0 && this.lastMouseY == 0) {
                this.lastMouseX = mouseX;
                this.lastMouseY = mouseY;
            }
            super.render(context, mouseX, mouseY, delta);
            // Avoid the mouse switching focus when searching if it isn't moving
            this.shouldHover = (mouseX != this.lastMouseX || mouseY != this.lastMouseY);
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
        }

        /**
         * Entry representing a biome or structure
         */
        class Entry extends ObjectSelectionList.Entry<dev.amble.ait.client.screens.AstralMapScreen.AstralMapListWidget.Entry> {

            private final ResourceLocation identifier;
            private final Component text;
            private final String modName;
            public final Category category;

            public Entry(ResourceLocation identifier, Category category, @Nullable String translationKey) {
                this.identifier = identifier;
                this.category = category;
                // Only biomes have actual translation keys and some modded ones might not
                if (translationKey != null) {
                    this.text = Component.translatableWithFallback(translationKey, identifierToName(identifier));
                } else {
                    this.text = Component.literal(identifierToName(identifier));
                }
                this.modName = getModName(identifier.getNamespace());
            }

            public static String identifierToName(ResourceLocation id) {
                try {
                    return WorldUtil.fakeTranslate(id.getPath());
                } catch (Exception e) {
                    return id.toString();
                }
            }

            @Override
            public void render(GuiGraphics context, int index, int y, int x, int entryWidth,
                               int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
                int modNameWidth = minecraft.font.width(this.modName);
                context.drawString(minecraft.font, this.modName,
                        x + getRowWidth() - modNameWidth - 4, y, CommonColors.GRAY, false);

                context.drawString(minecraft.font, this.text, x + 2, y, CommonColors.WHITE, false);

                if (hovered && AstralMapListWidget.this.shouldHover) {
                    AstralMapListWidget.this.setFocused(this);
                }
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                if (button == 0) {
                    AstralMapScreen.this.exit(this);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public Component getNarration() {
                return this.text;
            }
        }

    }
}
