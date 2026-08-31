package dev.amble.ait.core.util;

import java.lang.ref.WeakReference;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import dev.amble.ait.data.Exclude;

/**
 * Beloved TardisRefs now available for entities!
 */
public class EntityRef<T extends Entity> {

    @Exclude
    private ServerLevel world;

    @Exclude
    private WeakReference<T> ref;

    private final UUID id;

    public EntityRef(ServerLevel world, T entity) {
        this.id = entity.getUUID();

        this.world = world;
        this.ref = new WeakReference<>(entity);
    }

    public EntityRef(ServerLevel world, UUID id) {
        this.id = id;
        this.world = world;
    }

    public ServerLevel getWorld() {
        return this.world;
    }

    public void setWorld(ServerLevel world) {
        this.world = world;
    }

    public boolean hasWorld() {
        return this.world != null;
    }

    public T get() {
        T portal = this.ref != null ? this.ref.get() : null;

        if (portal != null || this.id == null)
            return portal;

        portal = (T) this.world.getEntity(this.id);
        this.ref = new WeakReference<>(portal);

        return portal;
    }

    public UUID getId() {
        return id;
    }
}
