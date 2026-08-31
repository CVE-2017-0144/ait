package dev.amble.ait.client.screens.widget;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class WorldListWidget extends AbstractSelectionList<WorldListWidget.WorldEntry> {
    public interface SelectionHandler {
        void onSelect(ResourceKey<Level> key);
    }

    private final SelectionHandler select;
    private WorldEntry entry;

    public WorldListWidget(Minecraft client, int width, int height, int top, int bottom, int itemHeight, int left, SelectionHandler onSelect) {
        super(client, width, height, top, bottom, itemHeight);
        this.select = onSelect;
        this.setRenderBackground(false);
        this.setRenderTopAndBottom(false);
        this.setLeftPos(left);
    }

    public void addWorld(ResourceKey<Level> key, Component label) {
        super.addEntry(new WorldEntry(this, key, label));
    }

    public WorldEntry getSelected() {
        return this.entry;
    }

    @Override
    public int getRowWidth() {
        return this.width - 15;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.x0 + this.width - 6;
    }

    @Override
    public void updateNarration(NarrationElementOutput builder) {
        WorldEntry selected = this.getSelected();
        if (selected != null) {
            builder.add(NarratedElementType.TITLE, Component.translatable("message.ait.projector.world"));
        } else {
            builder.add(NarratedElementType.TITLE, Component.translatable("message.ait.projector.dimension_skys"));
        }
    }

    public static class WorldEntry extends AbstractSelectionList.Entry<WorldEntry> {
        private final WorldListWidget parent;
        private final ResourceKey<Level> key;
        final Component label;

        public WorldEntry(WorldListWidget parent, ResourceKey<Level> key, Component label) {
            this.parent = parent;
            this.key = key;
            this.label = label;
        }

        @Override
        public void render(GuiGraphics context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float delta) {
            int color = hovered ? 0xFFFFA0 : 0xFFFFFF;
            context.drawString(Minecraft.getInstance().font, this.label, x + 4, y + (entryHeight - 9) / 2, color, false);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            if (button == 0) {
                this.parent.entry = this;
                this.parent.select.onSelect(this.key);
                return true;
            }
            return false;
        }

        public void appendNarrations(NarrationElementOutput builder) {
            builder.add(NarratedElementType.TITLE, this.label);
        }
    }
}
