package dev.amble.ait.api.tardis.link;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;

public abstract class LinkableEntity extends Entity {

    public static final EntityDataAccessor<Optional<UUID>> TARDIS_ID  = SynchedEntityData.defineId(
            LinkableEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private TardisRef cache;

    protected LinkableEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) { }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) { }

    public void link(ServerTardis tardis) {
        this.entityData.set(TARDIS_ID, Optional.of(tardis.getUuid()));
        this.createCache(tardis.getUuid());
    }

    private void reloadCache() {
        UUID id = this.tardisId().orElse(null);

        if (id == null)
            return;

        this.createCache(id);
    }

    private void createCache(UUID id) {
        this.cache = TardisRef.createAs(this, id);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(TARDIS_ID, Optional.empty());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);

        if (TARDIS_ID.equals(data))
            this.reloadCache();
    }

    public Tardis tardis() {
        if (this.cache != null)
            return cache.get();

        this.reloadCache();
        return cache != null ? cache.get() : null;
    }

    private Optional<UUID> tardisId() {
        return this.entityData.get(TARDIS_ID);
    }
}
