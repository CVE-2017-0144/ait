package dev.amble.ait.core.entities.base;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.api.tardis.link.v2.entity.AbstractLinkableEntity;

public class LinkableDummyEntity extends DummyEntity implements AbstractLinkableEntity {

    private static final EntityDataAccessor<Optional<UUID>> TARDIS = AbstractLinkableEntity
            .register(LinkableDummyEntity.class);

    private TardisRef tardis;

    public LinkableDummyEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    /**
     * Used by {@link AbstractLinkableEntity}, do not remove.
     */
    @Override
    public Level level() {
        return super.level();
    }

    /**
     * Used by {@link AbstractLinkableEntity}, do not remove.
     */
    @Override
    public SynchedEntityData getEntityData() {
        return super.getEntityData();
    }

    @Override
    public EntityDataAccessor<Optional<UUID>> getTracked() {
        return TARDIS;
    }

    @Override
    public TardisRef asRef() {
        return this.tardis;
    }

    @Override
    public void setRef(TardisRef ref) {
        this.tardis = ref;
    }

    @Override
    public void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        AbstractLinkableEntity.super.initDataTracker(builder);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);
        AbstractLinkableEntity.super.onTrackedDataSet(data);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        AbstractLinkableEntity.super.readCustomDataFromNbt(nbt);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        AbstractLinkableEntity.super.writeCustomDataToNbt(nbt);
    }
}
