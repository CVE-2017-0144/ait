package dev.amble.ait.api.tardis.link;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.core.tardis.Tardis;

public abstract class LinkableLivingEntity extends LivingEntity implements Linkable {

    public static final EntityDataAccessor<Optional<UUID>> TARDIS_ID  = SynchedEntityData.defineId(
            LinkableLivingEntity.class, EntityDataSerializers.OPTIONAL_UUID);

    private TardisRef cache;

    protected LinkableLivingEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        UUID id = nbt.getUUID("Tardis");

        if (id != null)
            this.link(id);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);

        this.tardisId().ifPresent(id -> nbt.putUUID("Tardis", id));
    }

    @Override
    public void link(Tardis tardis) {
        this.link(tardis.getUuid());
    }

    @Override
    public void link(UUID id) {
        this.entityData.set(TARDIS_ID, Optional.of(id));
        this.createCache(id);
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TARDIS_ID, Optional.empty());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);

        if (TARDIS_ID.equals(data))
            this.reloadCache();
    }

    public TardisRef tardis() {
        if (this.cache != null)
            return cache;

        this.reloadCache();
        return cache;
    }

    private Optional<UUID> tardisId() {
        return this.entityData.get(TARDIS_ID);
    }
}
