package dev.amble.ait.core.bind;

import java.util.function.Consumer;
import com.mojang.blaze3d.platform.InputConstants;
import dev.amble.ait.AITMod;
import dev.amble.lib.platform.render.ClientRegistries;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public class KeyBind {

    protected final String name;
    protected final String category;

    protected final InputConstants.Type type;
    protected final int code;

    protected KeyMapping self;
    private final Consumer<Minecraft> consumer;

    public KeyBind(String name, String category, InputConstants.Type type, int code, Consumer<Minecraft> consumer) {
        this.name = name;
        this.category = category;

        this.type = type;
        this.code = code;

        this.consumer = consumer;
    }

    public void tick(Minecraft client) {
        if (this.shouldTrigger(client))
            this.trigger(client);
    }

    public void register() {
        this.self = ClientRegistries.keyBinding(new KeyMapping("key." + AITMod.MOD_ID + "." + name, this.type,
                this.code, "category." + AITMod.MOD_ID + "." + category));
    }

    protected boolean shouldTrigger(Minecraft client) {
        return this.self.isDown();
    }

    protected void trigger(Minecraft client) {
        this.consumer.accept(client);
    }

    public static class Held extends KeyBind {

        private boolean held;

        public Held(String name, String category, InputConstants.Type type, int code, Consumer<Minecraft> consumer) {
            super(name, category, type, code, consumer);
        }

        @Override
        protected boolean shouldTrigger(Minecraft client) {
            LocalPlayer player = client.player;

            if (player == null)
                return false;

            if (!this.self.isDown()) {
                this.held = false;
                return false;
            }

            if (this.held)
                return false;

            this.held = true;
            return true;
        }
    }
}
