package dev.amble.ait.mixin.client;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import dev.amble.ait.AITMod;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.core.devteam.BetaVerification;

@Mixin(value = TitleScreen.class, priority = 999)
public abstract class TitleScreenMixin extends Screen {

    protected TitleScreenMixin(Component title) {
        super(title);
    }

    @Unique private static final PanoramaRenderer NEWPANO = new PanoramaRenderer(
            new CubeMap(AITMod.id("textures/gui/title/background/panorama"))
    );

    // This modifies the panorama in the background
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/PanoramaRenderer;render(FF)V", ordinal = 0))
    private void something(PanoramaRenderer instance, float delta, float alpha) {
        boolean isConfigEnabled = AITModClient.CONFIG.customMenu;

        if (isConfigEnabled)
            NEWPANO.render(delta, alpha);
        else
            instance.render(delta, alpha);
    }

    @Redirect(method = "createNormalMenuOptions", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/components/Button;builder(Lnet/minecraft/network/chat/Component;Lnet/minecraft/client/gui/components/Button$OnPress;)Lnet/minecraft/client/gui/components/Button$Builder;", ordinal = 0))
    private Button.Builder initWidgetsNormal1(Component message, Button.OnPress onPress) {
        boolean beta = AITMod.isBetaLocked();

        Button.Builder instance = new Button.Builder(beta ? Component.translatable("text.ait.beta.play") : message, button -> {
            if (BetaVerification.isServerRunning()) {
                // TODO: maybe use whatever minecraft is using? if it isn't using this ig?
                StringSelection stringSelection = new StringSelection(BetaVerification.getAuthUrl());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);
                return;
            }
            if (AITMod.isBetaLocked()) {
                button.setMessage(Component.translatable("text.ait.beta.play.browser"));
                BetaVerification.startAndWaitForToken(valid -> {
                    if (valid) {
                        button.setTooltip(null);
                        button.setMessage(message);
                    } else {
                        button.setMessage(Component.translatable("text.ait.beta.play"));
                    }
                });
                return;
            }

            onPress.onPress(button);
        });

        if (beta)
            instance = instance.tooltip(Tooltip.create(Component.translatable("text.ait.beta.play.tooltip")));

        return instance;
    }
}
