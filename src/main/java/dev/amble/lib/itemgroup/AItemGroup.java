package dev.amble.lib.itemgroup;

import java.util.function.Supplier;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AItemGroup extends CreativeModeTab {

    private final ResourceLocation id;

    protected AItemGroup(ResourceLocation id, Row row, int column, Type type, Component displayName, Supplier<ItemStack> iconSupplier, DisplayItemsGenerator entryCollector) {
        super(row, column, type, displayName, iconSupplier, entryCollector);

        this.id = id;
    }

    public ResourceLocation id() {
        return id;
    }

    public static dev.amble.lib.itemgroup.AItemGroup.Builder builder(ResourceLocation id) {
        return new dev.amble.lib.itemgroup.AItemGroup.Builder(id);
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

        public dev.amble.lib.itemgroup.AItemGroup.Builder displayName(Component displayName) {
            this.displayName = displayName;
            return this;
        }

        public dev.amble.lib.itemgroup.AItemGroup.Builder icon(Supplier<ItemStack> iconSupplier) {
            this.iconSupplier = iconSupplier;
            return this;
        }

        public dev.amble.lib.itemgroup.AItemGroup.Builder entries(DisplayItemsGenerator entryCollector) {
            this.entryCollector = entryCollector;
            return this;
        }

        public dev.amble.lib.itemgroup.AItemGroup.Builder special() {
            this.special = true;
            return this;
        }

        public dev.amble.lib.itemgroup.AItemGroup.Builder noRenderedName() {
            this.renderName = false;
            return this;
        }

        public dev.amble.lib.itemgroup.AItemGroup.Builder noScrollbar() {
            this.scrollbar = false;
            return this;
        }

        protected dev.amble.lib.itemgroup.AItemGroup.Builder type(Type type) {
            this.type = type;
            return this;
        }

        public dev.amble.lib.itemgroup.AItemGroup.Builder texture(String texture) {
            this.texture = texture;
            return this;
        }

        public AItemGroup build() {
            if ((this.type == Type.HOTBAR || this.type == Type.INVENTORY) && this.entryCollector != EMPTY_ENTRIES) {
                throw new IllegalStateException("Special tabs can't have display items");
            }

            if (this.displayName == null)
                this.displayName = Component.translatable("itemGroup." + id.getNamespace() + "." + id.getPath());

            AItemGroup itemGroup = new AItemGroup(this.id, null, -1, this.type, this.displayName, this.iconSupplier, this.entryCollector);

            itemGroup.alignedRight = this.special;
            itemGroup.showTitle = this.renderName;
            itemGroup.canScroll = this.scrollbar;
            itemGroup.backgroundSuffix = this.texture;
            return itemGroup;
        }
    }
}
