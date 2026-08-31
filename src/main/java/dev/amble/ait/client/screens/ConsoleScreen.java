package dev.amble.ait.client.screens;


import org.jetbrains.annotations.Nullable;
import dev.amble.ait.client.sounds.PlayerFollowingLoopingSound;
import dev.amble.ait.client.tardis.ClientTardis;
import dev.amble.ait.core.AITSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

/**
 * A screen that is opened from a console.
 * It also plays idle sfx. see {@link #shouldPlayIdleSfx()} and {@link #getIdleSound()}
 */
public abstract class ConsoleScreen extends TardisScreen {

    protected final BlockPos console;
    protected static PlayerFollowingLoopingSound idleSound;

    protected ConsoleScreen(Component title, ClientTardis tardis, BlockPos console) {
        super(title, tardis);

        this.minecraft = Minecraft.getInstance();
        this.console = console;

        boolean hasChanged = idleSound == null || idleSound.getLocation() != this.getIdleSound().getLocation();

        if (hasChanged) {
            idleSound = (shouldPlayIdleSfx()) ? new PlayerFollowingLoopingSound(this.getIdleSound(), SoundSource.AMBIENT, 0.25F) : null;
        }

        if (!shouldPlayIdleSfx() || hasChanged)
            this.minecraft.getSoundManager().stop(idleSound);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers))
            return true;

        if (this.canCloseWithKey() && this.minecraft.options.keyInventory.matches(keyCode, scanCode)) {
            this.onClose();
            return true;
        }

        return false;
    }

    public BlockPos getConsole() {
        return console;
    }

    public boolean shouldPlayIdleSfx() {
        return this.getIdleSound() != null;
    }

    public @Nullable SoundEvent getIdleSound() {
        return AITSounds.MONITOR_IDLE;
    }

    @Override
    protected void init() {
        super.init();

        if (idleSound != null && !this.minecraft.getSoundManager().isActive(idleSound))
            this.minecraft.getSoundManager().play(idleSound);
    }

    @Override
    public void onClose() {
        this.minecraft.getSoundManager().stop(idleSound);

        super.onClose();
    }

    public boolean canCloseWithKey() {
        return true;
    }
}
