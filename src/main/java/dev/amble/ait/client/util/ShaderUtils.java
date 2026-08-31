package dev.amble.ait.client.util;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import dev.amble.ait.AITMod;

public class ShaderUtils {
    public static Minecraft client = Minecraft.getInstance();
    public static PostChain shader;
    public static boolean enabled = false;

    private static PostChain getCurrent() {
        try {
            // "values": [ 0.3, 0.59, 0.11 ]
            return new PostChain(client.getTextureManager(), client.getResourceManager(),
                    client.getMainRenderTarget(), AITMod.id("shaders/post/red_tinted.json"));
        } catch (IOException e) {
            return null;
        }
    }

    public static void load() {
        if (shader != null)
            shader.close();
        shader = getCurrent();
        if (shader != null) {
            shader.resize(client.getWindow().getWidth(), client.getWindow().getHeight());
            enabled = true;
            return;
        }
        enabled = false;
    }
}
