package dev.amble.ait.api.tardis.link.v2.entity;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.core.tardis.Tardis;

public interface AbstractLinkableEntity extends Linkable {

    Level level();

    SynchedEntityData getEntityData();

    EntityDataAccessor<Optional<UUID>> getTracked();

    TardisRef asRef();

    void setRef(TardisRef ref);

    @Override
    default void link(UUID id) {
        this.setRef(TardisRef.createAs(this.level(), id));
        this.getEntityData().set(this.getTracked(), Optional.ofNullable(id));
    }

    @Override
    default void link(Tardis tardis) {
        this.setRef(TardisRef.createAs(this.level(), tardis));
        this.getEntityData().set(this.getTracked(), Optional.of(tardis.getUuid()));
    }

    @Override
    default TardisRef tardis() {
        TardisRef result = this.asRef();

        if (result == null) {
            this.link(this.getEntityData().get(this.getTracked()).orElse(null));
            return this.tardis();
        }

        return result;
    }

    default void initDataTracker() {
        this.getEntityData().define(this.getTracked(), Optional.empty());
    }

    default void onTrackedDataSet(EntityDataAccessor<?> data) {
        if (!this.getTracked().equals(data))
            return;

        this.link(this.getEntityData().get(this.getTracked()).orElse(null));
    }

    default void readCustomDataFromNbt(CompoundTag nbt) {
        Tag id = nbt.get("tardis");

        if (id == null)
            return;

        this.link(NbtUtils.loadUUID(id));

        if (this.level() == null)
            return;

        this.onLinked();
    }

    default void writeCustomDataToNbt(CompoundTag nbt) {
        TardisRef ref = this.asRef();

        if (ref != null && ref.getId() != null)
            nbt.putUUID("tardis", ref.getId());
    }

    static <T extends Entity & AbstractLinkableEntity> EntityDataAccessor<Optional<UUID>> register(Class<T> self) {
        return SynchedEntityData.defineId(self, EntityDataSerializers.OPTIONAL_UUID);
    }
}
