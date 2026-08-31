package dev.amble.ait.client.screens;

import dev.amble.ait.client.screens.widget.SwitcherManager;
import dev.amble.ait.core.tardis.handler.LandingPadHandler;
import dev.amble.ait.core.tardis.handler.ServerAlarmHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.properties.Value;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.tardis.handler.StatsHandler;
import dev.amble.ait.core.tardis.handler.permissions.PermissionHandler;
import dev.amble.ait.data.Loyalty;

public class TardisSecurityScreen extends ConsoleScreen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/tardis/monitor/security_menu.png");

    int bgHeight = 138;
    int bgWidth = 216;
    int left, top;
    int choicesCount = 0;
    private final Screen parent;
    private EditBox landingCodeInput;

    public TardisSecurityScreen(ClientTardis tardis, BlockPos console, Screen parent) {
        super(Component.translatable("screen.ait.security.title"), tardis, console);
        this.parent = parent;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    protected void init() {
        this.top = (this.height - this.bgHeight) / 2; // this means everythings centered and scaling, same for below
        this.left = (this.width - this.bgWidth) / 2;
        this.createButtons();

        super.init();
    }

    @Override
    public boolean canCloseWithKey() {
        return !this.landingCodeInput.canConsumeInput();
    }

    private void createButtons() {
        choicesCount = 0;

        createTextButton(Component.translatable("screen.ait.interiorsettings.back"),
                (button -> backToExteriorChangeScreen()));
        createTextButton(Component.translatable("screen.ait.security.leave_behind"), (button -> toggleLeaveBehind()));
        createTextButton(Component.translatable("screen.ait.security.hostile_alarms"), (button -> toggleHostileAlarms()));
        createTextButton(Component.translatable("screen.ait.security.minimum_loyalty"), (button -> changeMinimumLoyalty()));
        createTextButton(Component.translatable("screen.ait.security.receive_distress_calls"), (button -> receiveDistressCalls()));

        this.landingCodeInput = new EditBox(this.font, (int) (left + (bgWidth * 0.06f)), this.top + 85, 120, this.font.lineHeight + 4,
                Component.translatable("message.ait.landing_code"));
        this.addButton(new PlainTextButton((width / 2 + 40), (height / 2 + 18),
                this.font.width("✓"), 20, Component.literal("✓").withStyle(ChatFormatting.BOLD), button -> {
            updateLandingCode();
        }, this.font));

        this.landingCodeInput.setMaxLength(50);
        this.landingCodeInput.setBordered(true);
        this.landingCodeInput.setVisible(true);

        if(this.tardis().landingPad().code().get().isBlank())
            this.landingCodeInput.setHint(Component.translatable("message.ait.enter_landing_code"));
        else
            this.landingCodeInput.setValue(this.tardis().landingPad().code().get());

        this.addWidget(this.landingCodeInput);
    }

    private void receiveDistressCalls() {
        boolean bool = !this.tardis().stats().receiveCalls().get();
        this.tardis().stats().receiveCalls().set(bool);
        SwitcherManager.sync(this.tardis(), buf -> buf.writeBoolean(bool), StatsHandler.SHOULD_RECEIVE_CALLS);
    }

    private void toggleLeaveBehind() {
        boolean bool = !this.tardis().travel().leaveBehind().get();
        this.tardis().travel().leaveBehind().set(bool);
        SwitcherManager.sync(this.tardis(), buf -> buf.writeBoolean(bool), TravelHandlerBase.TOGGLE_LEAVE_BEHIND);
    }

    private void changeMinimumLoyalty() {
        ClientTardis tardis = this.tardis();
        PermissionHandler.p19Loyalty(tardis, getMinimumLoyalty(tardis).next());
    }

    private static Loyalty.Type getMinimumLoyalty(ClientTardis tardis) {
        return tardis.<PermissionHandler>handler(TardisComponent.Id.PERMISSIONS).p19Loyalty().get();
    }

    private void toggleHostileAlarms() {
        boolean bool = !this.tardis().alarm().hostilePresence().get();
        this.tardis().alarm().hostilePresence().set(bool);
        SwitcherManager.sync(this.tardis(), buf -> buf.writeBoolean(bool), ServerAlarmHandler.TOGGLE_HOSTILE_ALARMS);
    }

    private void updateLandingCode() {
        String input = this.landingCodeInput.getValue();

        this.tardis().landingPad().code().set(input);
        SwitcherManager.sync(this.tardis(), buf -> buf.writeUtf(input), LandingPadHandler.LANDING_CODE);
    }

    private <T extends AbstractWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        button.active = true; // this whole method is unnecessary bc it defaults to true ( ?? )
    }

    // this might be useful, so remember this exists and use it later on
    private void createTextButton(Component text, Button.OnPress onPress) {
        this.addButton(new PlainTextButton((int) (left + (bgWidth * 0.06f)),
                (int) (top + (bgHeight * (0.1f * (choicesCount + 1)))), this.font.width(text), 10, text,
                onPress, this.font));

        choicesCount++;
    }

    public void backToExteriorChangeScreen() {
        Minecraft.getInstance().setScreen(this.parent);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.drawBackground(context);

        ClientTardis tardis = this.tardis();

        Component onText = Component.translatable("screen.ait.monitor.on");
        Component offText = Component.translatable("screen.ait.monitor.off");


        context.drawString(this.font,
                Component.empty().append(": ").append(tardis.travel().leaveBehind().get() ? onText : offText),
                (int) (left + (bgWidth * 0.46f)), (int) (top + (bgHeight * (0.1f * 2))), 0xffA500, false);


        context.drawString(this.font,
                Component.empty().append(": ").append(tardis.alarm().hostilePresence().get() ? onText : offText),
                (int) (left + (bgWidth * 0.48f)), (int) (top + (bgHeight * (0.1f * 3))), 0xffA500, false);


        context.drawString(this.font,
                Component.literal(": ").append(getMinimumLoyalty(tardis).text()),
                (int) (left + (bgWidth * 0.51f)), (int) (top + (bgHeight * (0.1f * 4))), 0xffA500, false);


        context.drawString(this.font,
                Component.translatable("message.ait.date_created"),
                (int) (left + (bgWidth * 0.06f)),
                (int) (top + (bgHeight * (0.1f * 7.5))), 0xadcaf7, false);


        context.drawString(this.font,
                Component.literal(tardis.stats().getCreationString()),
                (int) (left + (bgWidth * 0.06f)),
                (int) (top + (bgHeight * (0.1f * 8.5))), 0xadcaf7, false);


        context.drawString(this.font,
                Component.empty().append(": ").append(this.tardis().<StatsHandler>handler(TardisComponent.Id.STATS).receiveCalls().get() ? onText : offText),
                (int) (left + (bgWidth * 0.7f)), (int) (top + (bgHeight * (0.1f * 5))), 0xffA500, false);

        this.landingCodeInput.render(context, mouseX, mouseY, delta);
        this.landingCodeInput.setTextColor(this.landingCodeInput.isHoveredOrFocused() || !this.landingCodeInput.getValue().isBlank() ? 0xffffff: 0x545454);
        super.render(context, mouseX, mouseY, delta);
    }

    private void drawBackground(GuiGraphics context) {
        context.blit(TEXTURE, left, top, 0, 0, bgWidth, bgHeight);
    }
}
