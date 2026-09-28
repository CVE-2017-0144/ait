package dev.amble.ait.client.screens;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.screens.widget.CompassYawWidget;
import dev.amble.ait.client.screens.widget.PitchLadderWidget;
import dev.amble.ait.client.screens.widget.WorldListWidget;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.blocks.EnvironmentProjectorBlock;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.WorldUtil;

public class EnvironmentProjectorScreen extends TardisScreen {
    private static final ResourceLocation DEFAULT_TEXTURE = AITMod.id("textures/gui/block/environment_projector/environment_menu_sky.png");
    private static final ResourceLocation DIRECTION_TEXTURE = AITMod.id("textures/gui/block/environment_projector/environment_menu_direction_compass.png");

    private GuiSelection currentGuiSelection = GuiSelection.SKY;
    private final BlockPos projectorPos;
    private List<ResourceKey<Level>> availableWorlds = Collections.emptyList();
    private WorldListWidget worldList;
    private StringWidget enabledLabel;
    private Checkbox enabledCheckbox;
    private PitchLadderWidget pitchLadder;
    private CompassYawWidget yawCompass;

    int bgHeight = 150;
    int bgWidth = 216;
    int left, top;

    private ResourceKey<Level> current = Level.END;
    private float currentYaw = 0f;
    private float currentPitch = 0f;
    private enum GuiSelection { SKY, DIRECTION }

    public EnvironmentProjectorScreen(ClientTardis tardis, BlockPos projectorPos) {
        super(Component.translatable("screen." + AITMod.MOD_ID + ".environment_projector"), tardis);
        this.minecraft = Minecraft.getInstance();
        this.projectorPos = projectorPos;
    }

    public void setAvailableWorlds(List<ResourceKey<Level>> worlds) {
        this.availableWorlds = worlds;
    }

    public void apply(Tardis tardis, BlockState state) {
        tardis.stats().skybox().set(this.current);
        tardis.stats().skyboxYaw().set(this.currentYaw);
        tardis.stats().skyboxPitch().set(this.currentPitch);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void switchToDirectionTab() {
        this.currentGuiSelection = GuiSelection.DIRECTION;
        this.clearWidgets();
        renderTabButtons();
        directionTab();
    }

    private void switchToSkyTab() {
        this.currentGuiSelection = GuiSelection.SKY;
        this.clearWidgets();
        renderTabButtons();
        skyTab();
    }

    public void onWorldSelected(ResourceKey<Level> key) {
        BlockState state = this.minecraft.level.getBlockState(projectorPos);
        this.current = key;

        AITMod.sendProjectorSelection(projectorPos, key.location());

        ClientTardis tardis = tardis();
        if (tardis != null && state.getOptionalValue(EnvironmentProjectorBlock.ENABLED).orElse(false)) {
            this.apply(tardis, state);
        }
    }

    private void sendAngles() {
        AITMod.sendProjectorAngles(projectorPos, this.currentYaw, this.currentPitch);
    }

    private void snapToDirection(Direction direction) {
        if (direction == Direction.UP) {
            this.currentYaw = 0f;
            this.currentPitch = 90f;
        } else if (direction == Direction.DOWN) {
            this.currentYaw = 0f;
            this.currentPitch = -90f;
        } else {
            this.currentYaw = direction.toYRot();
            this.currentPitch = 0f;
        }
        if (this.pitchLadder != null) this.pitchLadder.setValue(this.currentPitch);
        if (this.yawCompass != null) this.yawCompass.setValue(this.currentYaw);
        sendAngles();
    }

    private Component projectorText(String key) {
        return Component.translatable("screen." + AITMod.MOD_ID + ".environment_projector." + key);
    }

    private void addTabButton(Component text, int xOffset, Runnable action) {
        this.addRenderableWidget(new PlainTextButton((width / 2 - this.font.width(text) / 2 + xOffset),
                (height / 2 - 71), this.font.width(text), 10, text, button -> action.run(),
                this.font));
    }

    private void addDirectionButton(Direction direction, String key, int xOffset, int yOffset) {
        Component text = this.projectorText("direction." + key);
        this.addRenderableWidget(new PlainTextButton((width / 2 - this.font.width(text) / 2 + xOffset),
                (height / 2 + yOffset), this.font.width(text), 10, text,
                button -> snapToDirection(direction), this.font));
    }

    private void renderTabButtons(){
        this.addTabButton(this.projectorText("tab.sky"), -85, this::switchToSkyTab);
        this.addTabButton(this.projectorText("tab.direction"), -35, this::switchToDirectionTab);
    }

    private void directionTab(){
        //north
        this.addDirectionButton(Direction.NORTH, "north", 0, -40);
        //south
        this.addDirectionButton(Direction.SOUTH, "south", 0, 45);
        //west
        this.addDirectionButton(Direction.WEST, "west", -45, 2);
        //east
        this.addDirectionButton(Direction.EAST, "east", 47, 2);
        //up
        this.addDirectionButton(Direction.UP, "up", -90, -50);
        //down
        this.addDirectionButton(Direction.DOWN, "down", 83, -50);
        int ladderW = 40;
        int ladderH = 80;
        int ladderX = width / 2 - 80 - ladderW / 2;
        int ladderY = height / 2 - ladderH / 2 + 15;

        this.pitchLadder = new PitchLadderWidget(ladderX, ladderY, ladderW, ladderH,
                this.currentPitch, this.font, v -> {
            this.currentPitch = v;
            sendAngles();
        });
        this.addRenderableWidget(this.pitchLadder);
        int compassSize = 70;
        this.yawCompass = new CompassYawWidget(width / 2 - compassSize / 2 - 0, height / 2 - compassSize / 2 + 6,
                compassSize, this.currentYaw, v -> {
            this.currentYaw = v;
            sendAngles();
        });
        this.addRenderableWidget(this.yawCompass);
    }

    private void skyTab(){
        currentGuiSelection = GuiSelection.SKY;
        this.top = (this.height - this.bgHeight) / 2;
        this.left = (this.width - this.bgWidth) / 2;

        super.init();

        int listLeft = this.left - 0;
        int listTop = this.top + 50;
        int listWidth = this.bgWidth - 15;
        int listHeight = this.bgHeight - 90;
        int itemHeight = 10;

        this.worldList = new WorldListWidget(this.minecraft, listWidth, listHeight, listTop, listTop + listHeight, itemHeight, listLeft, this::onWorldSelected);

        for (ResourceKey<Level> key : this.availableWorlds) {
            ResourceLocation id = key.location();
            Component label = Component.translatableWithFallback(id.toLanguageKey("dimension"), WorldUtil.fakeTranslate(id.getPath()));
            this.worldList.addWorld(key, label);
        }
        this.addRenderableWidget(this.worldList);

        BlockState state = this.minecraft.level.getBlockState(projectorPos);
        boolean enabled = state.getOptionalValue(EnvironmentProjectorBlock.ENABLED).orElse(false);

        Component onText = this.projectorText("enabled.on");
        Component offText = this.projectorText("enabled.off");
        int labelW = Math.max(this.font.width(onText), this.font.width(offText));
        int checkboxX = width / 2 + 76;
        this.enabledLabel = new StringWidget(
                checkboxX - labelW - 4,
                (height / 2 - 53) + 6,
                labelW,
                10,
                enabled ? onText : offText,
                this.font
        );
        this.enabledLabel.alignRight();
        this.addRenderableOnly(this.enabledLabel);
        this.enabledCheckbox = this.addRenderableWidget(Checkbox.builder(Component.empty(), this.font)
                .pos(checkboxX, height / 2 - 53)
                .selected(enabled)
                .onValueChange((checkbox, checked) -> {
                    this.enabledLabel.setMessage(this.projectorText(checked ? "enabled.on" : "enabled.off"));
                    AITMod.sendProjectorToggle(projectorPos, checked);
                })
                .build());
        Component currentLabel = this.projectorText("current");
        this.addRenderableOnly(new StringWidget(
                (width / 2 - this.font.width(currentLabel) / 2 - 72),
                (height / 2 - 52),
                this.font.width(currentLabel),
                10,
                currentLabel, this.font));
        this.addTabButton(this.projectorText("tab.sky"), -85, this::switchToSkyTab);
        this.addTabButton(this.projectorText("tab.direction"), -35, this::switchToDirectionTab);
    }

    @Override
    protected void init() {
        if (tardis() != null && tardis().stats() != null) {
            if (tardis().stats().skybox() != null) {
                ResourceKey<Level> saved = tardis().stats().skybox().get();
                if (saved != null) {
                    this.current = saved;
                }
            }
            if (tardis().stats().skyboxYaw() != null) {
                this.currentYaw = tardis().stats().skyboxYaw().get();
            }
            if (tardis().stats().skyboxPitch() != null) {
                this.currentPitch = tardis().stats().skyboxPitch().get();
            }
        }
        renderTabButtons();
        skyTab();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.drawBackground(context, currentGuiSelection);
        if (currentGuiSelection.equals(GuiSelection.SKY) && this.current != null){
            ResourceLocation currentId = this.current.location();
            Component currentText = Component.translatableWithFallback(currentId.toLanguageKey("dimension"), WorldUtil.fakeTranslate(currentId.getPath()));
            float scale = 0.9f;
            int x = this.left + 58;
            int y = this.top + 24;

            context.pose().pushPose();
            context.pose().translate(x, y, 0);
            context.pose().scale(scale, scale, 1);

            context.drawString(
                    this.font,
                    currentText,
                    0, 0,
                    0xFFFFFF,
                    false
            );

            context.pose().popPose();
        }
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawBackground(GuiGraphics context, GuiSelection current) {
        if (current == GuiSelection.SKY) {
            context.blit(DEFAULT_TEXTURE, left, top, 0, 0, bgWidth, bgHeight);
        } else {
            context.blit(DIRECTION_TEXTURE, left, top, 0, 0, bgWidth, bgHeight);
        }
    }
}
