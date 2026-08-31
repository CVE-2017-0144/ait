package dev.amble.ait.compat.jei.client;

import java.util.List;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Renders inputs for Fabricator recipes in JEI with a range, rather than a specific number of items
 */
public class BlueprintInputItemStackRenderer implements IIngredientRenderer<ItemStack> {

    private final int minCount;
    private final int maxCount;

    public BlueprintInputItemStackRenderer(int minCount, int maxCount) {
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public void render(GuiGraphics context, @Nullable ItemStack maxCountStack) {
        this.render(context, maxCountStack, 0, 0);
    }

    public void render(GuiGraphics context, @Nullable ItemStack ingredient, int posX, int posY) {
        if (ingredient == null) {
            return;
        }
        RenderSystem.enableDepthTest();

        Minecraft minecraft = Minecraft.getInstance();
        context.renderFakeItem(ingredient, posX, posY);

        Font textRenderer = this.getFontRenderer(minecraft, ingredient);
        context.renderItemDecorations(textRenderer, ingredient, posX, posY, "");
        this.drawText(context, textRenderer, posX, posY);

        RenderSystem.disableBlend();
    }

    private static final int TEXT_COLOR = 16777215;

    /**
     * Custom text drawing method to fit the range of items within the item slot
     */
    private void drawText(GuiGraphics context, Font textRenderer, int posX, int posY) {
        String range;
        if (minCount == maxCount) {
            if (minCount == 1)
                return;
            range = String.valueOf(minCount);
        } else {
            range = minCount + "-" + maxCount;
        }

        PoseStack matrixStack = context.pose();
        matrixStack.pushPose();
        matrixStack.translate(0.0F, 0.0F, 200.0F);

        if (textRenderer.width(range) > 26) {
            context.drawString(
                    textRenderer,
                    String.valueOf(minCount),
                    posX + 20 - textRenderer.width(String.valueOf(minCount)),
                    posY + 4,
                    TEXT_COLOR,
                    true
            );
            context.drawString(
                    textRenderer,
                    "-" + maxCount,
                    posX + 20 - textRenderer.width("-" + maxCount),
                    posY + 12,
                    TEXT_COLOR,
                    true
            );
        } else {
            context.drawString(
                    textRenderer,
                    range,
                    posX + 20 - textRenderer.width(range),
                    posY + 12,
                    TEXT_COLOR,
                    true
            );
        }
        matrixStack.popPose();
    }

    /**
     * This is both required and deprecated for some reason so it has to be here
     */
    @SuppressWarnings("removal")
    @Override
    public @NotNull List<Component> getTooltip(ItemStack ingredient, TooltipFlag tooltipFlag) {
        return List.of();
    }


    public void getTooltip(ITooltipBuilder tooltip, ItemStack ingredient, TooltipFlag tooltipFlag) {
        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        List<Component> components = ingredient.getTooltipLines(player, tooltipFlag);
        tooltip.addAll(components);
    }

}
