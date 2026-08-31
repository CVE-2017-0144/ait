package dev.amble.ait.client.screens.interior;

import static dev.amble.ait.core.tardis.handler.InteriorChangingHandler.CHANGE_DESKTOP;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.Nameable;
import dev.amble.ait.api.tardis.TardisClientEvents;
import dev.amble.ait.client.models.exteriors.BedrockExteriorModel;
import dev.amble.ait.client.models.exteriors.ExteriorModel;
import dev.amble.ait.client.renderers.AITRenderLayers;
import dev.amble.ait.client.screens.ConsoleScreen;
import dev.amble.ait.client.screens.SaveLoadInteriorScreen;
import dev.amble.ait.client.screens.SonicSettingsScreen;
import dev.amble.ait.client.screens.TardisSecurityScreen;
import dev.amble.ait.client.screens.widget.AnimationScrubberWidget;
import dev.amble.ait.client.screens.widget.IconButtonWidget;
import dev.amble.ait.client.screens.widget.SwitcherManager;
import dev.amble.ait.client.sounds.ClientSoundManager;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.compat.DependencyChecker;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.sounds.flight.FlightSound;
import dev.amble.ait.core.tardis.TardisDesktop;
import dev.amble.ait.core.tardis.animation.v2.TardisAnimation;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.tardis.vortex.reference.VortexReference;
import dev.amble.ait.data.hum.Hum;
import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import dev.amble.ait.data.schema.exterior.ClientExteriorVariantSchema;
import dev.amble.ait.data.schema.exterior.ExteriorCategorySchema;
import dev.amble.ait.data.schema.exterior.category.ClassicCategory;
import dev.amble.ait.data.schema.exterior.category.PoliceBoxCategory;
import dev.amble.ait.registry.impl.CategoryRegistry;
import dev.amble.ait.registry.impl.DesktopRegistry;

@Environment(EnvType.CLIENT)
public class InteriorSettingsScreen extends ConsoleScreen {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/interior_settings.png");
    private static final ResourceLocation ANIM_BACKGROUND = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/interior_settings_anim.png");
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/interior_settings.png");
    private static final ResourceLocation MISSING_PREVIEW = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/presets/missing_preview.png");
    private static final int PREVIEW_X_OFFSET = 151;
    private static final int PREVIEW_Y_OFFSET = 10;
    private static final int PREVIEW_SIZE = 95;
    private final List<Button> buttons = Lists.newArrayList();
    int bgHeight = 166;
    int bgWidth = 256;
    int left, top;
    private int tickForSpin = 0;
    public int choicesCount = 0;
    private final Screen parent;
    private TardisDesktopSchema selectedDesktop;
    private SwitcherManager.ModeManager modeManager;
    private final int APPLY_BUTTON_WIDTH = 53;
    private final int APPLY_BUTTON_HEIGHT = 20;
    private final int APPLY_BAR_BUTTON_WIDTH = 53;
    private final int APPLY_BAR_BUTTON_HEIGHT = 12;
    private final int SMALL_ARROW_BUTTON_WIDTH = 20;
    private final int SMALL_ARROW_BUTTON_HEIGHT = 12;
    private final int BIG_ARROW_BUTTON_WIDTH = 20;
    private final int BIG_ARROW_BUTTON_HEIGHT = 20;
    private final int MAIN_SETTINGS_BUTTON_WIDTH = 20;
    private final int MAIN_SETTINGS_BUTTON_HEIGHT = 20;
    private BlockPos console;

    private AnimationScrubberWidget timeline;
    private IconButtonWidget playButton;
    private IconButtonWidget stopButton;
    private IconButtonWidget muteButton;
    private boolean previewMuted;
    private TardisAnimation previewBase;
    private TardisAnimation previewAnim;
    private ResourceLocation previewAnimId;
    private int previewTicks;
    private int previewMax = 1;
    private SoundInstance previewSound;
    private boolean humSuppressed;

    public InteriorSettingsScreen(ClientTardis tardis, BlockPos console, Screen parent) {
        super(Component.translatable("screen." + AITMod.MOD_ID + ".interiorsettings.title"), tardis, console);
        this.parent = parent;
        this.console = console;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.modeManager = new SwitcherManager.ModeManager(this.tardis());
        this.selectedDesktop = tardis().getDesktop().getSchema();

        if (this.selectedDesktop == null)
            this.nextDesktop();

        this.top = (this.height - this.bgHeight) / 2; // this means everythings centered and scaling, same for below
        this.left = (this.width - this.bgWidth) / 2;
        this.createButtons();
        this.createPreviewWidgets();

        super.init();
    }

    private void createPreviewWidgets() {
        this.timeline = this.addRenderableWidget(new AnimationScrubberWidget(
                this.left + 152, this.top + 78, 81, 5, this::scrubTo));
        this.timeline.visible = false;

        this.playButton = this.addRenderableWidget(new IconButtonWidget(
                this.left + 238, this.top + 127, 6, IconButtonWidget.Icon.PLAY, this::playPreviewSound));
        this.stopButton = this.addRenderableWidget(new IconButtonWidget(
                this.left + 238, this.top + 135, 6, IconButtonWidget.Icon.STOP, this::stopPreviewSound));

        this.muteButton = this.addRenderableWidget(new IconButtonWidget(
                this.left + 235, this.top + 77, 7, IconButtonWidget.Icon.SOUND_ON, this::toggleMute));
        this.muteButton.visible = false;
    }

    private boolean isAnimMode() {
        return this.modeManager != null && this.modeManager.get().get() instanceof TardisAnimation;
    }

    private boolean isVortexMode() {
        return this.modeManager != null && this.modeManager.get().get() instanceof VortexReference;
    }

    private void sendCachePacket() {
        if (this.console == null)
            return;

        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(this.tardis().getUuid());
        buf.writeBlockPos(this.console);

        AitNetworking.send(TardisDesktop.CACHE_CONSOLE, buf);
        this.onClose();
    }

    private void createCompatButtons() { }

    private void createButtons() {
        choicesCount = 0;
        this.buttons.clear();

        createTextButton(Component.translatable("screen.ait.interiorsettings.cacheconsole")
                .withStyle(this.console != null ? ChatFormatting.WHITE : ChatFormatting.GRAY), button -> sendCachePacket());
        createTextButton(Component.translatable("screen.ait.security.button"), (button -> toSecurityScreen()));

        boolean showSonicButton = console != null && Minecraft.getInstance().level.getBlockEntity(console) instanceof ConsoleBlockEntity consoleBlock
                && consoleBlock.getSonicScrewdriver() != null && !consoleBlock.getSonicScrewdriver().isEmpty();

        createTextButton(Component.translatable("screen.ait.sonic.button")
                .withStyle(showSonicButton ? ChatFormatting.WHITE : ChatFormatting.GRAY), button -> {
                    if (showSonicButton)
                        toSonicScreen();
                });

        /*createTextButton(Text.translatable("screen.ait.loadsaveinterior.button")
                .formatted(Formatting.WHITE), button -> {
                toLoadSaveInteriorScreen();
        });*/

        this.createCompatButtons();
        TardisClientEvents.SETTINGS_SETUP.invoker().onSetup(this);

        // arrow - hum/misc screen - left
        this.addButton(new PlainTextButton((width / 2 + 23), (height / 2 + 61),
                SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT, Component.empty(), button -> this.modeManager.get().previous(), this.font));

        // arrow - hum/misc screen - right
        this.addButton(new PlainTextButton((width / 2 + 98), (height / 2 + 61),
                SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT, Component.empty(), button -> this.modeManager.get().next(), this.font));

        // apply (HUM)
        this.addButton(new PlainTextButton((width / 2 + 44), (height / 2 + 61),
                APPLY_BAR_BUTTON_WIDTH, APPLY_BAR_BUTTON_HEIGHT, Component.empty(), button -> this.modeManager.get().sync(this.tardis()), this.font));

        // arrows (Interior)
        this.addButton(new PlainTextButton((width / 2 + 23), (height / 2 + 3), BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT,
                Component.empty(), button -> {
                    previousDesktop();
                }, this.font));
        this.addButton(new PlainTextButton((width / 2 + 98), (height / 2 + 3), BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT,
                Component.empty(), button -> {
                    nextDesktop();
                }, this.font));

        // apply (Interior)
        MutableComponent applyInteriorText = Component.translatable("screen.ait.monitor.apply");
        this.addRenderableOnly(new StringWidget((width / 2 + 44), (height / 2 + 3),
                APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT, applyInteriorText.withStyle(ChatFormatting.BOLD), this.font));
        this.addButton(new PlainTextButton((width / 2 + 44), (height / 2 + 3),
                APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT, Component.empty(), button -> applyDesktop(), this.font));

        // back to main monitor menu
        this.addButton(new PlainTextButton((width / 2 - 13), (height / 2 + 52),
                MAIN_SETTINGS_BUTTON_WIDTH, MAIN_SETTINGS_BUTTON_HEIGHT,
                Component.empty(),
                button -> backToExteriorChangeScreen(), this.font));


        // arrows (HUM) mode selector
        this.addButton(new PlainTextButton((width / 2 + 77), (height / 2 + 30),
                SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT, Component.empty(), button -> this.modeManager.previous(), this.font));
        this.addButton(new PlainTextButton((width / 2 + 98), (height / 2 + 30),
                SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT, Component.empty(), button -> this.modeManager.next(), this.font));
    }

    private void toSonicScreen() {
        Minecraft.getInstance().setScreen(new SonicSettingsScreen(this.tardis(), this.console, this));
    }

    private void toLoadSaveInteriorScreen() {
        Minecraft.getInstance().setScreen(new SaveLoadInteriorScreen(this.tardis(), this.console, this));
    }

    public <T extends AbstractWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        button.active = true; // this whole method is unnecessary bc it defaults to true ( ?? )
        this.buttons.add((Button) button);
    }

    public PlainTextButton createTextButton(Component text, Button.OnPress onPress) {
        return this.createAnyButton(text, PlainTextButton::new, onPress);
    }

    public <T extends Button> T initAnyButton(Component text, ButtonCreator<T> creator,
            Button.OnPress onPress) {
        return creator.create((int) (left + (bgWidth * 0.06f)), (int) (top + (bgHeight * (0.1f * (choicesCount + 1)))),
                this.font.width(text), 10, text, onPress, this.font);
    }

    public <T extends Button> T initAnyDynamicButton(Function<T, Component> text, DynamicButtonCreator<T> creator,
            Button.OnPress onPress) {
        return creator.create((int) (left + (bgWidth * 0.06f)), (int) (top + (bgHeight * (0.1f * (choicesCount + 1)))),
                this.font.width(Component.empty()), 10, text, onPress, this.font);
    }

    public <T extends Button> T createAnyButton(Component text, ButtonCreator<T> creator,
            Button.OnPress onPress) {
        T result = this.initAnyButton(text, creator, onPress);

        this.addButton(result);
        choicesCount++;

        return result;
    }

    public <T extends Button> T createAnyDynamicButton(Function<T, Component> text, DynamicButtonCreator<T> creator,
            Button.OnPress onPress) {
        T result = this.initAnyDynamicButton(text, creator, onPress);

        this.addButton(result);
        choicesCount++;

        return result;
    }

    public void backToExteriorChangeScreen() {
        Minecraft.getInstance().setScreen(this.parent);
    }

    public void toSecurityScreen() {
        Minecraft.getInstance().setScreen(new TardisSecurityScreen(tardis(), this.console, this));
    }

    final int UV_BASE = 160;
    final int UV_INCREMENT = 19;

    int calculateUvOffsetForRange(int progress) {
        int rangeProgress = progress % 19;
        return (rangeProgress / 5) * UV_INCREMENT;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        int i = (this.width - this.bgWidth) / 2;
        int j = ((this.height) - this.bgHeight) / 2;
        this.renderDesktop(context);
        this.drawBackground(context); // the grey backdrop
        context.pose().pushPose();
        int x = (left + 79);
        int y = (top + 59);
        context.pose().translate(0, 0, 0f);
        context.pose().popPose();

        // TODO: this is a fucking nightmare
        int buttonIndex = DependencyChecker.hasGravity() ? 4 : 3;

        // arrow buttons (hum/misc screen)
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 93, 166,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 93, 178,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);

        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 113, 166,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 113, 178,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);

        // apply bar button (hum/misc screen)
        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 133, 166,
                    APPLY_BAR_BUTTON_WIDTH, APPLY_BAR_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 133, 178,
                    APPLY_BAR_BUTTON_WIDTH, APPLY_BAR_BUTTON_HEIGHT);

        // arrow buttons (interior)
        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 0, 166,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 0, 186,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);

        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 20, 166,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 20, 186,
                    BIG_ARROW_BUTTON_WIDTH, BIG_ARROW_BUTTON_HEIGHT);

        // apply button (interior)
        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 40, 166,
                    APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 40, 186,
                    APPLY_BUTTON_WIDTH, APPLY_BUTTON_HEIGHT);

        // back to main monitor menu button
        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 186, 166,
                    MAIN_SETTINGS_BUTTON_WIDTH, MAIN_SETTINGS_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 186, 186,
                    MAIN_SETTINGS_BUTTON_WIDTH, MAIN_SETTINGS_BUTTON_HEIGHT);

        // arrow buttons (hum/misc screen) - mode selector
        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 93, 166,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 93, 178,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);

        buttonIndex++;
        if (!this.buttons.get(buttonIndex).isHovered())
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 113, 166,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);
        else
            context.blit(TEXTURE, this.buttons.get(buttonIndex).getX(), this.buttons.get(buttonIndex).getY(), 113, 178,
                    SMALL_ARROW_BUTTON_WIDTH, SMALL_ARROW_BUTTON_HEIGHT);


        if (tardis() == null)
            return;

        // Fuel
        context.blit(TEXTURE, i + 16, j + 144, 0,
                this.tardis().getFuel() > (FuelHandler.TARDIS_MAX_FUEL / 4) ? 225 : 234,
                (int) (85 * this.tardis().getFuel() / FuelHandler.TARDIS_MAX_FUEL), 9);


        // fuel markers @TODO come back and actually do the rest of it with the halves
        // and the red
        // parts
        // too

        // Flight Progress
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


        this.renderCurrentMode(context);

        boolean anim = this.isAnimMode();
        boolean hasSound = this.currentPreviewSound() != null;

        if (this.timeline != null)
            this.timeline.visible = anim;
        if (this.playButton != null)
            this.playButton.visible = hasSound && !anim;
        if (this.stopButton != null)
            this.stopButton.visible = hasSound && !anim;
        if (this.muteButton != null) {
            this.muteButton.visible = anim;
            this.muteButton.setIcon(this.previewMuted
                    ? IconButtonWidget.Icon.SOUND_OFF : IconButtonWidget.Icon.SOUND_ON);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    private void drawBackground(GuiGraphics context) {
        context.blit(this.isAnimMode() ? ANIM_BACKGROUND : BACKGROUND, left, top, 0, 0, bgWidth, bgHeight);
    }

    private void renderDesktop(GuiGraphics context) {
        if (this.isAnimMode()) {
            this.renderAnimationPreview(context);
            return;
        }

        if (this.isVortexMode()) {
            this.renderVortexPreview(context);
            return;
        }

        if (this.selectedDesktop == null)
            return;

        context.pose().pushPose();
        context.pose().translate(0, 0, 15f);
        context.drawCenteredString(this.font, this.selectedDesktop.name(),
                (int) (left + (bgWidth * 0.77f)), (int) (top + (bgHeight * 0.080f)), 0xffffff);
        context.pose().popPose();

        context.pose().pushPose();
        context.blit(
                doesTextureExist(this.selectedDesktop.previewTexture().texture())
                        ? this.selectedDesktop.previewTexture().texture()
                        : MISSING_PREVIEW,
                left + PREVIEW_X_OFFSET, top + PREVIEW_Y_OFFSET, PREVIEW_SIZE, PREVIEW_SIZE, 0, 0,
                this.selectedDesktop.previewTexture().width * 2,
                this.selectedDesktop.previewTexture().height * 2, this.selectedDesktop.previewTexture().width * 2,
                this.selectedDesktop.previewTexture().height * 2);

        context.pose().popPose();
    }

    private void renderVortexPreview(GuiGraphics context) {
        Minecraft client = Minecraft.getInstance();
        Object current = this.modeManager.get().get();

        if (client.player == null || !(current instanceof VortexReference ref))
            return;

        int boxX = this.left + PREVIEW_X_OFFSET;
        int boxY = this.top + PREVIEW_Y_OFFSET;

        context.flush();

        Window window = client.getWindow();
        double scale = window.getGuiScale();
        int fbX = (int) (boxX * scale);
        int fbY = (int) (window.getHeight() - (boxY + PREVIEW_SIZE) * scale);
        int fbSize = (int) (PREVIEW_SIZE * scale);

        Matrix4f prevProjection = RenderSystem.getProjectionMatrix();
        VertexSorting prevSorter = RenderSystem.getVertexSorting();

        context.enableScissor(boxX, boxY, boxX + PREVIEW_SIZE, boxY + PREVIEW_SIZE);
        RenderSystem.viewport(fbX, fbY, fbSize, fbSize);
        RenderSystem.setProjectionMatrix(
                new Matrix4f().perspective((float) Math.toRadians(70.0), 1f, 0.05f, 4000f), VertexSorting.DISTANCE_TO_ORIGIN);

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();

        float spin = (client.player.tickCount + client.getTimer().getGameTimeDeltaPartialTick(true)) / 100f * 360f;

        PoseStack vortexStack = new PoseStack();
        vortexStack.mulPose(Axis.ZP.rotationDegrees(spin));
        vortexStack.translate(0, 0, 500);
        ref.toRender().render(vortexStack);

        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();

        RenderSystem.setProjectionMatrix(prevProjection, prevSorter);
        RenderSystem.viewport(0, 0, window.getWidth(), window.getHeight());
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        context.disableScissor();
    }

    private void renderAnimationPreview(GuiGraphics context) {
        if (this.tardis() == null || this.tardis().getExterior() == null)
            return;

        ClientExteriorVariantSchema variant = this.tardis().getExterior().getVariant().getClient();

        if (variant == null)
            return;

        ExteriorModel model = variant.model();

        if (model == null)
            return;

        Minecraft client = Minecraft.getInstance();
        float delta = client.getTimer().getGameTimeDeltaPartialTick(true);

        float alpha = 1f;
        Vector3f animPosition = new Vector3f();
        Vector3f animRotation = new Vector3f();
        Vector3f animScale = new Vector3f(1f, 1f, 1f);

        if (this.previewAnim != null) {
            alpha = Mth.clamp(this.previewAnim.getAlpha(delta), 0f, 1f);
            animPosition = this.previewAnim.getPosition(delta);
            animRotation = this.previewAnim.getRotation(delta);
            animScale = this.previewAnim.getScale(delta);
        }

        ExteriorCategorySchema category = this.tardis().getExterior().getCategory();
        boolean isPoliceBox = category.equals(CategoryRegistry.getInstance().get(PoliceBoxCategory.REFERENCE))
                || category.equals(CategoryRegistry.getInstance().get(ClassicCategory.REFERENCE));

        float baseScale = isPoliceBox ? 10f : 17f;
        int centerX = this.left + 198;
        int centerY = this.top + (isPoliceBox ? 59 : 48);
        float spin = (client.player == null ? 0 : client.player.tickCount + delta) * 3f;

        PoseStack stack = context.pose();
        stack.pushPose();
        stack.translate(centerX, centerY, 100f);
        stack.scale(-baseScale, baseScale, baseScale);

        if (model instanceof BedrockExteriorModel)
            stack.translate(0, 1.25f, 0);

        stack.translate(animPosition.x(), animPosition.y(), animPosition.z());
        stack.scale(animScale.x(), animScale.y(), animScale.z());
        stack.mulPose(Axis.YN.rotationDegrees(spin));
        stack.mulPose(Axis.XP.rotationDegrees(animRotation.z()));
        stack.mulPose(Axis.YP.rotationDegrees(animRotation.y()));
        stack.mulPose(Axis.ZP.rotationDegrees(animRotation.x()));

        model.render(stack, context.bufferSource().getBuffer(AITRenderLayers.entityTranslucentCull(variant.texture())), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY, FastColor.ARGB32.colorFromFloat(alpha, 1f, 1f, 1f));

        stack.popPose();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.humSuppressed && (this.previewSound == null
                || !Minecraft.getInstance().getSoundManager().isActive(this.previewSound)))
            this.setHumSuppressed(false);

        if (this.tardis() == null)
            return;

        Object current = this.modeManager == null ? null : this.modeManager.get().get();

        if (!(current instanceof TardisAnimation selected)) {
            if (this.previewBase != null)
                this.stopPreviewSound();

            this.previewBase = null;
            this.previewAnim = null;
            this.previewAnimId = null;
            return;
        }

        if (this.previewAnim == null || !selected.id().equals(this.previewAnimId)) {
            this.buildPreview(selected);
            this.playAnimationSound(selected);
        }

        if (this.timeline == null || !this.timeline.isDragging()) {
            if (this.previewAnim.isAged()) {
                this.buildPreview(selected);
                this.playAnimationSound(selected);
            } else {
                this.previewAnim.tick(Minecraft.getInstance());
                this.previewTicks = Math.min(this.previewTicks + 1, this.previewMax);
            }

            if (this.timeline != null)
                this.timeline.setProgress(this.previewMax <= 0 ? 0f : (float) this.previewTicks / this.previewMax);
        }
    }

    private void toggleMute() {
        this.previewMuted = !this.previewMuted;

        if (this.previewMuted) {
            this.stopPreviewSound();
        } else if (this.previewBase != null) {
            this.buildPreview(this.previewBase);
            this.playAnimationSound(this.previewBase);
        }
    }

    private void playAnimationSound(TardisAnimation anim) {
        this.stopPreviewSound();

        if (this.previewMuted)
            return;

        SoundEvent sfx = anim.getSound();

        if (sfx == null)
            return;

        this.previewSound = SimpleSoundInstance.forUI(sfx, 1f, 1f);
        Minecraft.getInstance().getSoundManager().play(this.previewSound);
    }

    private void buildPreview(TardisAnimation selected) {
        this.previewBase = selected;
        this.previewAnimId = selected.id();
        this.previewAnim = selected.instantiate();
        this.previewMax = Math.max(1, selected.getMaxDuration());
        this.previewTicks = 0;
    }

    private void scrubTo(float progress) {
        if (this.previewBase == null)
            return;

        int target = Mth.clamp(Math.round(progress * this.previewMax), 0, this.previewMax);
        TardisAnimation fresh = this.previewBase.instantiate();
        Minecraft client = Minecraft.getInstance();

        for (int i = 0; i < target; i++)
            fresh.tick(client);

        this.previewAnim = fresh;
        this.previewTicks = target;
    }

    private static SoundEvent soundOf(Object current) {
        if (current instanceof Hum hum)
            return hum.sound();
        if (current instanceof FlightSound flight)
            return flight.sound();
        if (current instanceof TardisAnimation anim)
            return anim.getSound();

        return null;
    }

    private SoundEvent currentPreviewSound() {
        return this.modeManager == null ? null : soundOf(this.modeManager.get().get());
    }

    private void playPreviewSound() {
        if (this.modeManager == null)
            return;

        Object current = this.modeManager.get().get();
        SoundEvent sfx = soundOf(current);

        if (sfx == null)
            return;

        this.stopPreviewSound();

        if (current instanceof Hum)
            this.setHumSuppressed(true);

        this.previewSound = SimpleSoundInstance.forUI(sfx, 1f, 1f);
        Minecraft.getInstance().getSoundManager().play(this.previewSound);
    }

    private void stopPreviewSound() {
        this.setHumSuppressed(false);

        if (this.previewSound == null)
            return;

        Minecraft.getInstance().getSoundManager().stop(this.previewSound);
        this.previewSound = null;
    }

    private void setHumSuppressed(boolean suppressed) {
        if (this.humSuppressed == suppressed)
            return;

        this.humSuppressed = suppressed;
        ClientSoundManager.getHum().setSuppressed(suppressed);
    }

    @Override
    public void removed() {
        this.stopPreviewSound();
        super.removed();
    }

    private void renderCurrentMode(GuiGraphics context) {
        Nameable current = this.modeManager.get().get();

        Component modeText = Component.translatable("screen.ait.interior_settings.mode."
                + this.modeManager.get().name().toLowerCase(Locale.ROOT));
        context.drawString(this.font, modeText,
                (width / 2 + 50) - this.font.width(modeText) / 2,
                height / 2 + 32, 0xffffff, true);
        String currentString = current.text().getString().toUpperCase(Locale.ROOT);
        context.drawString(this.font, currentString, (int) (left + (bgWidth * 0.78f)) - this.font.width(currentString) / 2,
                (int) (top + (bgHeight * 0.792f)), 0xffffff, true);
    }

    private void applyDesktop() {
        if (this.selectedDesktop == null)
            return;

        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeUUID(tardis().getUuid());
        buf.writeResourceLocation(this.selectedDesktop.id());

        AitNetworking.send(CHANGE_DESKTOP, buf);

        Minecraft.getInstance().setScreen(null);
    }

    private static TardisDesktopSchema nextDesktop(TardisDesktopSchema current) {
        List<TardisDesktopSchema> list = DesktopRegistry.getInstance().toList();

        int idx = current == null ? -1 : list.indexOf(current);
        idx = (idx + 1) % list.size();
        return list.get(idx);
    }

    private void nextDesktop() {
        this.selectedDesktop = nextDesktop(this.selectedDesktop);

        if (!isCurrentUnlocked() || this.selectedDesktop == DesktopRegistry.DEFAULT_CAVE)
            nextDesktop(); // ooo incursion crash
    }

    private static TardisDesktopSchema previousDesktop(TardisDesktopSchema current) {
        List<TardisDesktopSchema> list = DesktopRegistry.getInstance().toList();

        int idx = current == null ? -1 : list.indexOf(current);
        idx = (idx - 1 + list.size()) % list.size();
        return list.get(idx);
    }

    private void previousDesktop() {
        this.selectedDesktop = previousDesktop(this.selectedDesktop);

        if (!isCurrentUnlocked() || this.selectedDesktop == DesktopRegistry.DEFAULT_CAVE)
            previousDesktop(); // ooo incursion crash
    }

    public static boolean doesTextureExist(ResourceLocation id) {
        return Minecraft.getInstance().getResourceManager().getResource(id).isPresent();
    }

    private boolean isCurrentUnlocked() {
        return this.tardis().isUnlocked(this.selectedDesktop);
    }

    @FunctionalInterface
    public interface ButtonCreator<T extends Button> {
        T create(int x, int y, int width, int height, Component text, Button.OnPress onPress,
                Font textRenderer);
    }

    @FunctionalInterface
    public interface DynamicButtonCreator<T extends Button> {
        T create(int x, int y, int width, int height, Function<T, Component> text, Button.OnPress onPress,
                Font textRenderer);
    }
}
