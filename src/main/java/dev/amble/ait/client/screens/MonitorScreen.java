package dev.amble.ait.client.screens;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.player.Player;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.models.exteriors.BedrockExteriorModel;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.screens.interior.InteriorSettingsScreen;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.client.util.ClientTardisUtil;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.data.datapack.DatapackConsole;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.ExteriorCategorySchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.category.ClassicCategory;
import dev.amble.ait.data.schema.exterior.category.ExclusiveCategory;
import dev.amble.ait.data.schema.exterior.category.PoliceBoxCategory;
import dev.amble.ait.registry.impl.CategoryRegistry;
import dev.amble.ait.registry.impl.exterior.ClientExteriorVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.data.DirectedGlobalPos;

public class MonitorScreen extends ConsoleScreen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/monitor_gui.png");
    private final List<Button> buttons = Lists.newArrayList();
    private ExteriorCategorySchema category;
    private ClientExteriorVariantSchema currentVariant;
    int backgroundHeight = 166;
    int backgroundWidth = 256;
    private final int APPLY_BUTTON_WIDTH = 53;
    private final int APPLY_BUTTON_HEIGHT = 20;
    private final int SMALL_ARROW_BUTTON_WIDTH = 20;
    private final int SMALL_ARROW_BUTTON_HEIGHT = 12;
    private final int BIG_ARROW_BUTTON_WIDTH = 20;
    private final int BIG_ARROW_BUTTON_HEIGHT = 20;
    private final int INTERIOR_SETTINGS_BUTTON_WIDTH = 20;
    private final int INTERIOR_SETTINGS_BUTTON_HEIGHT = 20;

    public MonitorScreen(ClientTardis tardis, BlockPos console) {
        super(Component.translatable("screen." + AITMod.MOD_ID + ".monitor"), tardis, console);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private <T extends AbstractWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        this.buttons.add((Button) button);
    }

    @Override
    protected void init() {
        super.init();
        this.createButtons();
    }

    public ExteriorCategorySchema getCategory() {
        return category == null ? this.tardis().getExterior().getCategory() : category;
    }

    public void setCategory(ExteriorCategorySchema category) {
        this.category = category;

        if (currentVariant == null)
            return;

        if (this.currentVariant.parent().category() != category)
            currentVariant = null;
    }

    public ClientExteriorVariantSchema getCurrentVariant() {
        if (currentVariant == null)
            if (!this.tardis().getExterior().getCategory().equals(getCategory())) {
                setCurrentVariant(this.getCategory().getDefaultVariant());
            } else {
                setCurrentVariant(this.tardis().getExterior().getVariant());
            }

        return currentVariant;
    }

    public void setCurrentVariant(ExteriorVariantSchema var) {
        setCurrentVariant(var.getClient());
    }

    public void setCurrentVariant(ClientExteriorVariantSchema currentVariant) {
        this.currentVariant = currentVariant;
    }

    private void createButtons() {
        this.buttons.clear();
        // exterior change text button
        MutableComponent applyText = Component.translatable("screen.ait.monitor.apply");

        // apply text (exterior change screen)
        this.addRenderableOnly(new StringWidget((width / 2 + 44), (height / 2 + 3),
                APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT, applyText.withStyle(ChatFormatting.BOLD), this.font));

        // apply button (exterior change screen)
        this.addButton(new PlainTextButton((width / 2 + 44), (height / 2 + 3),
                APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT, Component.empty(), button -> {
                    sendExteriorPacket(this.tardis(), this.getCategory(), this.getCurrentVariant());
                }, this.font));

        // arrow buttons (exterior change screen)
        this.addButton(new PlainTextButton((width / 2 + 23), (height / 2 + 3),
                BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT, Component.empty(), button -> {
                    changeCategory(false);
                }, this.font));
        this.addButton(new PlainTextButton((width / 2 + 98), (height / 2 + 3),
                BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT, Component.empty(), button -> {
                    changeCategory(true);
                }, this.font));

        // arrow buttons (exterior variant screen)
        this.addButton(new PlainTextButton((width / 2 + 23), (height / 2 + 61),
                SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT, Component.empty(), button -> {
                    whichDirectionVariant(false);
                }, this.font));
        this.addButton(new PlainTextButton((width / 2 + 98), (height / 2 + 61),
                SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT, Component.empty(), button -> {
                    whichDirectionVariant(true);
                }, this.font));

        // interior settings button
        this.addButton(new PlainTextButton((width / 2 - 13), (height / 2 + 52),
                INTERIOR_SETTINGS_BUTTON_WIDTH, INTERIOR_SETTINGS_BUTTON_HEIGHT,
                Component.empty(),
                button -> toInteriorSettingsScreen(), this.font));

        this.buttons.forEach(buttons -> {
            // buttons.visible = false;
            buttons.active = true;
        });
    }

    public static void sendExteriorPacket(ClientTardis tardis, ExteriorCategorySchema category,
            ClientExteriorVariantSchema variant) {
        if (category != tardis.getExterior().getCategory() || variant.parent() != tardis.getExterior().getVariant()) {
            ClientTardisUtil.changeExteriorWithScreen(tardis, variant.id(),
                    variant.parent() != tardis.getExterior().getVariant());
        }
    }

    public void toInteriorSettingsScreen() {
        if (tardis() == null || tardis().isGrowth())
            return;

        Minecraft.getInstance().forceSetScreen(new InteriorSettingsScreen(this.tardis(), this.console, this));
    }

    public void changeCategory(boolean direction) {
        Player player = Minecraft.getInstance().player;

        if (player == null)
            return;

        if (direction)
            setCategory(nextCategory());
        else
            setCategory(previousCategory());

        if ((CategoryRegistry.EXCLUSIVE.equals(this.category) && !ExclusiveCategory.isUnlocked(player.getUUID()))
                || CategoryRegistry.CORAL_GROWTH.equals(this.category))
            changeCategory(direction);
    }

    public ExteriorCategorySchema nextCategory() {
        List<ExteriorCategorySchema> list = CategoryRegistry.getInstance().toList();

        int idx = list.indexOf(getCategory());
        idx = (idx + 1) % list.size();
        return list.get(idx);
    }

    public ExteriorCategorySchema previousCategory() {
        List<ExteriorCategorySchema> list = CategoryRegistry.getInstance().toList();

        int idx = list.indexOf(getCategory());
        idx = (idx - 1 + list.size()) % list.size();
        return list.get(idx);
    }

    public void whichDirectionVariant(boolean direction) {
        Player player = Minecraft.getInstance().player;

        if (player == null)
            return;

        if (direction)
            setCurrentVariant(nextVariant());
        else
            setCurrentVariant(previousVariant());
    }

    public ExteriorVariantSchema nextVariant() {
        List<ExteriorVariantSchema> list = ExteriorVariantRegistry.withParent(getCurrentVariant().parent().category())
                .stream().toList();

        int idx = list.indexOf(getCurrentVariant().parent());
        idx = (idx + 1) % list.size();
        return list.get(idx);
    }

    public ExteriorVariantSchema previousVariant() {
        List<ExteriorVariantSchema> list = ExteriorVariantRegistry.withParent(getCurrentVariant().parent().category())
                .stream().toList();

        int idx = list.indexOf(getCurrentVariant().parent());
        idx = (idx - 1 + list.size()) % list.size();
        return list.get(idx);
    }

    final int UV_BASE = 160;
    final int UV_INCREMENT = 19;

    int calculateUvOffsetForRange(int progress) {
        int rangeProgress = progress % 19;
        return (rangeProgress / 5) * UV_INCREMENT;
    }

    protected void drawBackground(GuiGraphics context) {
        // just this whole thing is for the flight
        if (this.tardis() == null)
            return;

        int i = (this.width - this.backgroundWidth) / 2;
        int j = ((this.height) - this.backgroundHeight) / 2;
        context.blit(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // apply button (exterior change screen)
        if (!this.buttons.get(0).isHovered())
            context.blit(TEXTURE, this.buttons.get(0).getX(), this.buttons.get(0).getY(), 40, 166,
                    APPLY_BUTTON_WIDTH,APPLY_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(0).getX(), this.buttons.get(0).getY(), 40, 186,
                    APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT);

        // arrow buttons (exterior change screen)
        if (!this.buttons.get(1).isHovered())
            context.blit(TEXTURE, this.buttons.get(1).getX(), this.buttons.get(1).getY(), 0, 166,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(1).getX(), this.buttons.get(1).getY(), 0, 186,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);
        if (!this.buttons.get(2).isHovered())
            context.blit(TEXTURE, this.buttons.get(2).getX(), this.buttons.get(2).getY(), 20, 166,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(2).getX(), this.buttons.get(2).getY(), 20, 186,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);

        // arrow buttons (exterior variant screen)
        if (!this.buttons.get(3).isHovered())
            context.blit(TEXTURE, this.buttons.get(3).getX(), this.buttons.get(3).getY(), 93, 166,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(3).getX(), this.buttons.get(3).getY(), 93, 178,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        if (!this.buttons.get(4).isHovered())
            context.blit(TEXTURE, this.buttons.get(4).getX(), this.buttons.get(4).getY(), 113, 166,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(4).getX(), this.buttons.get(4).getY(), 113, 178,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);

        // interior settings button
        if (!this.buttons.get(5).isHovered())
            context.blit(TEXTURE, this.buttons.get(5).getX(), this.buttons.get(5).getY(), 186, 166,
                    INTERIOR_SETTINGS_BUTTON_WIDTH, INTERIOR_SETTINGS_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(5).getX(), this.buttons.get(5).getY(), 186, 186,
                    INTERIOR_SETTINGS_BUTTON_WIDTH, INTERIOR_SETTINGS_BUTTON_HEIGHT);

        context.blit(TEXTURE, i + 16, j + 144, 0,
                this.tardis().getFuel() > (FuelHandler.TARDIS_MAX_FUEL / 4) ? 225 : 234,
                (int) (85 * this.tardis().getFuel() / FuelHandler.TARDIS_MAX_FUEL), 9);

        int progress = this.tardis().travel().getDurationAsPercentage();

        for (int index = 0; index < 5; index++) {
            int rangeStart = index * 19;
            int rangeEnd = (index + 1) * 19;

            int uvOffset;
            if (progress >= rangeStart && progress <= rangeEnd) {
                uvOffset = calculateUvOffsetForRange(progress);
            } else if (progress >= rangeEnd) {
                uvOffset = 76;
            } else {
                uvOffset = UV_BASE;
            }

            context.blit(TEXTURE, i + 11 + (index * 19), j + 113,
                    this.tardis().travel().getState() == TravelHandlerBase.State.FLIGHT
                            ? progress >= 100 ? 76 : uvOffset
                            : UV_BASE,
                    206, 19, 19);
        }
    }

    protected void drawTardisExterior(GuiGraphics context, int x, int y, float scale) {
        float delta = Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true) + Minecraft.getInstance().player.tickCount;
        Tardis tardis = this.tardis();

        if (tardis == null)
            return;

        PoseStack stack = context.pose();

        int centerWidth = width / 2;
        int centerHeight = height / 2;

        ExteriorCategorySchema category = this.getCategory();
        ClientExteriorVariantSchema variant = this.getCurrentVariant();

        if (category == null || variant == null)
            return;

        boolean isPoliceBox = category.equals(CategoryRegistry.getInstance().get(PoliceBoxCategory.REFERENCE))
                || category.equals(CategoryRegistry.getInstance().get(ClassicCategory.REFERENCE));

        boolean isHorriblyUnscaled = variant.equals(ClientExteriorVariantRegistry.DOOM);

        boolean isExtUnlocked = tardis.isUnlocked(variant.parent());
        boolean hasPower = tardis.fuel().hasPower();
        boolean alarms = tardis.alarm().isEnabled();

        stack.pushPose();
        stack.translate(0, 0, 500f);

        context.drawCenteredString(this.font, category.text(), (centerWidth + 70), (centerHeight - 68),
                5636095);

        List<ExteriorVariantSchema> list = ExteriorVariantRegistry.withParent(category);

        context.drawCenteredString(this.font, Component.literal((list.indexOf(variant.parent()) + 1) + "/" + list.size()).withStyle(ChatFormatting.BOLD),
                (centerWidth + 70), (centerHeight + 64), 0xffffff);

        context.drawCenteredString(this.font, variant.parent().text(), (centerWidth + 70),
                (centerHeight + 44), 5636095);

        context.drawCenteredString(this.font, variant.parent().id().getNamespace().toUpperCase(),
                (centerWidth + 70), (centerHeight + 34), 5636095);

        stack.popPose();
        ExteriorModel model = variant.model();

        stack.pushPose();
        stack.translate(x, isPoliceBox || isHorriblyUnscaled ? y + 11 : y, 100f);

        if (isPoliceBox) {
            stack.scale(-12, 12, 12);
        } else if (isHorriblyUnscaled) {
            stack.scale(-12, 12, 12);
        } else {
            stack.scale(-scale, scale, scale);
        }

        // datapack models float for some reason
        if (model instanceof BedrockExteriorModel) {
            stack.translate(0, 1.25f, 0);
        }

        stack.mulPose(Axis.YN.rotationDegrees(delta * 3));

        ResourceLocation texture = variant.texture();
        ResourceLocation emissive = variant.emission();

        float base = isExtUnlocked ? 1f : 0.1f;

        model.render(stack, context.bufferSource().getBuffer(AITRenderLayers.entityTranslucentCull(texture)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1f, base, base, base));

        if (hasPower && emissive != null && !(emissive.equals(DatapackConsole.EMPTY))) {
            model.render(stack, context.bufferSource().getBuffer(AITRenderLayers.tardisEmissiveCullZOffset(emissive, true)), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(1f, base, base, base));
        }
        stack.popPose();

        stack.pushPose();
        stack.translate(0, 0, 550f);
        context.drawCenteredString(this.font, isExtUnlocked ? "" : "\uD83D\uDD12", x, y,
                0xFFFFFF);

        stack.pushPose();
        stack.translate(0, 0, 50f);
        context.drawCenteredString(this.font, isExtUnlocked ? "" : "\uD83D\uDD12", x, y, 0xFFFFFF);
        stack.popPose();

        stack.popPose();
    }

    @Override
    public void renderBackground(GuiGraphics context) {
        super.renderBackground(context);
    }

    protected void drawInformationText(GuiGraphics context) {
        if (this.tardis() == null)
            return;

        TravelHandler travel = this.tardis().travel();
        DirectedGlobalPos abpd = travel.getState() == TravelHandlerBase.State.FLIGHT
                ? travel.getProgress()
                : travel.position();
        CachedDirectedGlobalPos dabpd = travel.destination();

        if (abpd.getDimension() == null)
            return;

        BlockPos abpdPos = abpd.getPos();

        String positionText = abpdPos.getX() + ", " + abpdPos.getY() + ", " + abpdPos.getZ();
        Component dimensionText = WorldUtil.worldText(abpd.getDimension());

        BlockPos dabpdPos = dabpd.getPos();

        String destinationText = dabpdPos.getX() + ", " + dabpdPos.getY() + ", " + dabpdPos.getZ();
        Component dDimensionText = WorldUtil.worldText(dabpd.getDimension(), false);

        // position
        context.drawString(this.font, Component.literal(positionText), (width / 2 - 119), (height / 2 - 48), 0xFFFFFF,
                true);
        context.drawString(this.font, dimensionText, (width / 2 - 119), (height / 2 - 38), 0xFFFFFF, true);
        context.drawString(this.font, WorldUtil.rot2Text(abpd.getRotation()).getVisualOrderText(), (width / 2 - 119), (height / 2 - 28), 0xFFFFFF,
                true);

        // destination
        context.drawString(this.font, Component.literal(destinationText), (width / 2 - 119), (height / 2 - 10),
                0xFFFFFF, true);
        context.drawString(this.font, dDimensionText, (width / 2 - 119), (height / 2), 0xFFFFFF, true);
        context.drawString(this.font, WorldUtil.rot2Text(dabpd.getRotation()).getVisualOrderText(), (width / 2 - 119), (height / 2 + 10),
                0xFFFFFF, true);

        // cloak silent
        if (this.tardis().cloak().silent().get()) {
            float scale = 0.4f;
            int x = width / 2 - 49;
            int y = height / 2 + 19;

            context.pose().pushPose();
            context.pose().translate(x, y, 0);
            context.pose().scale(scale, scale, 1);
            context.drawString(this.font, Component.translatable("screen.ait.monitor.shell_cloaking_activated_message"), 0, 0, 0xFFFFFF, true);
            context.pose().popPose();
        }
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        int i = ((this.height - this.backgroundHeight) / 2); // loqor make sure to use these so it stays consistent on
                                                                // different sized
        // screens
        // (kind of ??)
        int j = ((this.width - this.backgroundWidth) / 2);
        // background behind the tardis and gallifreyan text
        PoseStack stack = context.pose();
        this.drawTardisExterior(context, (width / 2 + 70), (height / 2 - 30), 19f);
        this.drawBackground(context);
        // todo manually adjusting all these values are annoying me
        this.drawInformationText(context);
        super.render(context, mouseX, mouseY, delta);
    }
}
