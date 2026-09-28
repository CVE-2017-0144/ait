package dev.amble.ait.compat.portal;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.link.v2.TardisRef;
import dev.amble.ait.client.AITModClient;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.EntityRef;
import qouteall.imm_ptl.core.portal.Portal;

public class TardisPortal extends Portal {

    public static EntityType<TardisPortal> ENTITY_TYPE = createPortalEntityType(TardisPortal::new);

    private TardisRef tardis;

    public TardisPortal(Tardis tardis, Level world) {
        this(ENTITY_TYPE, world);
        this.tardis = TardisRef.createAs(this, tardis);
    }

    public TardisPortal(EntityType<TardisPortal> type, Level world) {
        super(type, world);
    }

    // ip calls this server side too (isInteractableBy) and there's no AITModClient on a dedi
    @Override
    public boolean isVisible() {
        return super.isVisible() && (!this.level().isClientSide() || AITModClient.CONFIG.allowPortalsBoti);
    }

    @Override
    public boolean isInteractableBy(Player player) {
        if (!super.isInteractableBy(player))
            return false;

        // client picks the target: sneak + item only, so normal door/console clicks stay inside
        return !this.level().isClientSide() || (player.isSecondaryUseActive()
                && !(player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty()));
    }

    // closed door portal only goes to players inside, ip relays sound through hidden portals too
    @Override
    public boolean broadcastToPlayer(ServerPlayer spectator) {
        return super.broadcastToPlayer(spectator) && (super.isVisible() || spectator.level() == this.level());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        if (!(this.level() instanceof ServerLevel) || !nbt.contains("Tardis"))
            return;

        this.tardis = TardisRef.createAs(this, nbt.getUUID("Tardis"));
    }

    @Override
    public boolean isPortalValid() {
        return super.isPortalValid() && (this.level().isClientSide() || this.isCurrent());
    }

    private boolean isCurrent() {
        Tardis tardis = this.tardis != null ? this.tardis.get() : null;

        if (tardis == null || tardis.asServer().isRemoved() || !(tardis.handler(PortalsHandler.ID) instanceof PortalsHandler portalsHandler))
            return false;

        EntityRef<TardisPortal> extPortal = portalsHandler.getExteriorRef();
        EntityRef<TardisPortal> intPortal = portalsHandler.getInteriorRef();

        UUID id = this.getUUID();

        return (extPortal != null && id.equals(extPortal.getId())) || (intPortal != null && id.equals(intPortal.getId()));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (tardis != null) {
            nbt.putUUID("Tardis", tardis.getId());
        }
    }
}
