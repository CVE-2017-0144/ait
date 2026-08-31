package dev.amble.ait.api.tardis.link.v2.block;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import dev.amble.ait.api.tardis.link.v2.Linkable;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;

public abstract class AbstractLinkableBlockEntity extends BlockEntity implements Linkable {

    protected TardisRef ref;

    public AbstractLinkableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public TardisRef tardis() {
        return ref;
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);

        if (this.ref != null && this.ref.getId() != null)
            nbt.putUUID("tardis", this.ref.getId());
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);

        Tag id = nbt.get("tardis");

        if (id == null)
            return;

        this.ref = TardisRef.createAs(this, NbtUtils.loadUUID(id));

        if (this.level == null)
            return;

        this.onLinked();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (this.ref == null || this.ref.isEmpty())
            return;

        if (!(this.level instanceof ServerLevel serverWorld))
            return;

        ServerTardisManager.getInstance().unmark(serverWorld, (ServerTardis) this.ref.get(), new ChunkPos(this.worldPosition));
    }

    @Override
    public void link(Tardis tardis) {
        this.ref = TardisRef.createAs(this, tardis);
        this.handleLink();
    }

    @Override
    public void link(UUID id) {
        this.ref = TardisRef.createAs(this, id);
        this.handleLink();
    }

    private void mark() {
        if (this.level instanceof ServerLevel serverWorld)
            ServerTardisManager.getInstance().mark(serverWorld, (ServerTardis) this.tardis().get(),
                    new ChunkPos(this.worldPosition));
    }

    private void handleLink() {
        this.mark();
        this.onLinked();

        this.sync();
        this.setChanged();
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        if (this.isLinked())
            this.mark();

        return saveWithoutMetadata();
    }

    protected void sync() {
        if (this.level != null && this.level.getChunkSource() instanceof ServerChunkCache chunkManager)
            chunkManager.blockChanged(this.worldPosition);
    }
}
