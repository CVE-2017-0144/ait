package dev.amble.ait.client.screens;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.PlainTextButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.data.ClientLandingManager;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.data.landing.LandingPadRegion;

public class LandingPadScreen extends Screen {
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(AITMod.MOD_ID,
            "textures/gui/landing_marker_gui.png");
    private final BlockPos pos;
    private final LandingPadRegion landingRegion;
    int bgHeight = 137;
    int bgWidth = 191;
    int left, top;
    private EditBox landingCodeInput;

    public LandingPadScreen(BlockPos pos) {
        super(Component.translatable("screen.ait.landing_pad"));

        this.minecraft = Minecraft.getInstance();
        this.pos = pos;
        this.landingRegion = ClientLandingManager.getInstance().getRegion(new ChunkPos(pos));
    }

    @Override
    protected void init() {
        this.top = (this.height - this.bgHeight) / 2; // this means everythings centered and scaling, same for below
        this.left = (this.width - this.bgWidth) / 2;
        this.landingCodeInput = new EditBox(this.font, (int) (left + (bgWidth * 0.06f)), (this.height / 2) - 20, 120, this.font.lineHeight + 4,
                Component.translatable("message.ait.landing_code"));
        this.addButton(new PlainTextButton((width / 2 + 40), (height / 2) - 20,
                this.font.width("✓"), 20, Component.literal("✓").withStyle(ChatFormatting.BOLD), button -> {
            updateLandingCode();
        }, this.font));

        this.landingCodeInput.setMaxLength(50);
        this.landingCodeInput.setBordered(true);
        this.landingCodeInput.setVisible(true);

        if (this.landingRegion == null) {
            this.onClose();
            return;
        }

        if(this.landingRegion.getLandingCode().isBlank())
            this.landingCodeInput.setHint(Component.translatable("message.ait.enter_landing_code"));
        else
            this.landingCodeInput.setValue(this.landingRegion.getLandingCode());

        this.addWidget(this.landingCodeInput);
        super.init();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Check if a text field is focused to prevent closing
        if (this.landingCodeInput.canConsumeInput())
            return super.keyPressed(keyCode, scanCode, modifiers);

        // Close the screen when the inventory key is pressed
        if (this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void updateLandingCode() {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        if (this.pos == null) return;

        buf.writeBlockPos(this.pos);
        buf.writeUtf(this.landingCodeInput.getValue());

        AitNetworking.send(TardisUtil.REGION_LANDING_CODE, buf);
    }

    private <T extends AbstractWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        button.active = true; // this whole method is unnecessary bc it defaults to true ( ?? )
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        context.blit(TEXTURE, left, top, 0, 0, bgWidth, bgHeight);

        this.landingCodeInput.render(context, mouseX, mouseY, delta);
        this.landingCodeInput.setTextColor(this.landingCodeInput.isHoveredOrFocused() || !this.landingCodeInput.getValue().isBlank() ? 0xffffff: 0x545454);

        super.render(context, mouseX, mouseY, delta);
    }
}
