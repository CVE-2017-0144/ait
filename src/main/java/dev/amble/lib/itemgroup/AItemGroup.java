package dev.amble.lib.itemgroup;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AItemGroup extends CreativeModeTab {

    private final ResourceLocation id;

    protected AItemGroup(ResourceLocation id, CreativeModeTab.Builder builder) {
        super(builder);

        this.id = id;
    }

    public ResourceLocation id() {
        return id;
    }

    public static AItemGroup.Builder builder(ResourceLocation id) {
        return new AItemGroup.Builder(id);
    }

    public static class Builder {

        private static final DisplayItemsGenerator EMPTY_ENTRIES = (displayContext, entries) -> {};
        private Component displayName = null;
        private Supplier<ItemStack> iconSupplier = () -> ItemStack.EMPTY;

        private DisplayItemsGenerator entryCollector = EMPTY_ENTRIES;
        private boolean scrollbar = true;
        private boolean renderName = true;
        private boolean special = false;
        private Type type = Type.CATEGORY;
        private String texture = "items.png";

        private final ResourceLocation id;

        public Builder(ResourceLocation id) {
            this.id = id;
        }

        public AItemGroup.Builder displayName(Component displayName) {
            this.displayName = displayName;
            return this;
        }

        public AItemGroup.Builder icon(Supplier<ItemStack> iconSupplier) {
            this.iconSupplier = iconSupplier;
            return this;
        }

        public AItemGroup.Builder entries(DisplayItemsGenerator entryCollector) {
            this.entryCollector = entryCollector;
            return this;
        }

        public AItemGroup.Builder special() {
            this.special = true;
            return this;
        }

        public AItemGroup.Builder noRenderedName() {
            this.renderName = false;
            return this;
        }

        public AItemGroup.Builder noScrollbar() {
            this.scrollbar = false;
            return this;
        }

        protected AItemGroup.Builder type(Type type) {
            this.type = type;
            return this;
        }

        public AItemGroup.Builder texture(String texture) {
            this.texture = texture;
            return this;
        }

        public AItemGroup build() {
            if ((this.type == Type.HOTBAR || this.type == Type.INVENTORY) && this.entryCollector != EMPTY_ENTRIES) {
                throw new IllegalStateException("Special tabs can't have display items");
            }

            if (this.displayName == null)
                this.displayName = Component.translatable("itemGroup." + id.getNamespace() + "." + id.getPath());

            CreativeModeTab.Builder builder = new CreativeModeTab.Builder(Row.TOP, -1)
                    .title(this.displayName)
                    .icon(this.iconSupplier)
                    .displayItems(this.entryCollector)
                    .backgroundTexture(CreativeModeTab.createTextureLocation(this.texture));

            if (this.special)
                builder.alignedRight();

            if (!this.renderName)
                builder.hideTitle();

            if (!this.scrollbar)
                builder.noScrollBar();

            return new AItemGroup(this.id, builder);
        }
    }
}
