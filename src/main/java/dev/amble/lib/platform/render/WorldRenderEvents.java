package dev.amble.lib.platform.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import dev.amble.lib.platform.event.Event;
import dev.amble.lib.platform.event.EventFactory;

@Environment(EnvType.CLIENT)
public final class WorldRenderEvents {

    private WorldRenderEvents() {}

    public interface Render {
        void render(WorldRenderContext context);
    }

    private static Event<Render> event() {
        return EventFactory.createArrayBacked(Render.class, callbacks -> context -> {
            for (Render callback : callbacks) {
                callback.render(context);
            }
        });
    }

    public static final Event<Render> AFTER_SETUP = event();
    public static final Event<Render> BEFORE_ENTITIES = event();
    public static final Event<Render> AFTER_ENTITIES = event();
    public static final Event<Render> END = event();

    static {
        bootstrap();
    }

    private static void bootstrap() {
        net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.AFTER_SETUP
                .register(context -> AFTER_SETUP.invoker().render(wrap(context)));
        net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.BEFORE_ENTITIES
                .register(context -> BEFORE_ENTITIES.invoker().render(wrap(context)));
        net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.AFTER_ENTITIES
                .register(context -> AFTER_ENTITIES.invoker().render(wrap(context)));
        net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents.END
                .register(context -> END.invoker().render(wrap(context)));
    }

    private static WorldRenderContext wrap(
            net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext context) {
        return new FabricWorldRenderContext(context);
    }
}
