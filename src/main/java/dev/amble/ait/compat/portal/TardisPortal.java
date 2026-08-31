package dev.amble.ait.compat.portal;

import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.util.EntityRef;
import qouteall.imm_ptl.core.portal.Portal;
import dev.amble.ait.client.AITModClient;
import qouteall.imm_ptl.core.portal.PortalManipulation;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TardisPortal extends Portal {

    public static EntityType<TardisPortal> ENTITY_TYPE = createPortalEntityType(TardisPortal::new);

    private Tardis tardis;

    public TardisPortal(Tardis tardis, Level world) {
        this(ENTITY_TYPE, world);
        this.tardis = tardis;
    }

    public TardisPortal(EntityType<TardisPortal> type, Level world) {
        super(type, world);
    }

    @Override
    public boolean isVisible() {
        return super.isVisible() && AITModClient.CONFIG.allowPortalsBoti;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);

        if (!(this.level() instanceof ServerLevel serverWorld) || !nbt.contains("Tardis"))
            return;

        this.tardis = ServerTardisManager.getInstance().demandTardis(serverWorld.getServer(), nbt.getUUID("Tardis"));

        if (this.tardis == null) {
            PortalManipulation.removeConnectedPortals(this, (p) -> {});
            this.discard();
            return;
        }

        PortalsHandler portalsHandler = this.tardis.handler(PortalsHandler.ID);

        EntityRef<TardisPortal> extPortal = portalsHandler.getExteriorRef();
        EntityRef<TardisPortal> intPortal = portalsHandler.getInteriorRef();

        UUID id = this.getUUID();

        if ((extPortal == null || !id.equals(extPortal.getId())) &&
                (intPortal == null || !id.equals(intPortal.getId()))) {
            PortalManipulation.removeConnectedPortals(this, (p) -> {});
            this.discard();
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (tardis != null) {
            nbt.putUUID("Tardis", tardis.getUuid());
        }
    }
}
