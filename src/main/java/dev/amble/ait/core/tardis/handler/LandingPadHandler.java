package dev.amble.ait.core.tardis.handler;


import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.tardis.control.impl.SecurityControl;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.ait.core.world.LandingPadManager;
import dev.amble.ait.data.Exclude;
import dev.amble.ait.data.landing.LandingPadRegion;
import dev.amble.ait.data.landing.LandingPadSpot;
import dev.amble.ait.data.properties.Property;
import dev.amble.ait.data.properties.Value;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class LandingPadHandler extends KeyedTardisComponent {

    public static final ResourceLocation LANDING_CODE = AITMod.id("landing_code");

    public static final Property<String> CODE = new Property<>(Property.STR, "code", "");
    private final Value<String> code = LandingPadHandler.CODE.create(this);
    @Exclude
    private LandingPadSpot current;

    static {
        TardisEvents.BEFORE_LAND.register((tardis, destination) ->
                new TardisEvents.Result<>(tardis.landingPad().update(destination)));

        TardisEvents.DEMAT.register(tardis -> {
            tardis.landingPad().release();
            return TardisEvents.Interaction.PASS;
        });

        TardisEvents.MAT.register(tardis -> {
            boolean success = tardis.landingPad().checkCode();

            if (!success) return TardisEvents.Interaction.FAIL;

            return TardisEvents.Interaction.PASS;
        });

        AitNetworking.registerServerReceiver(LANDING_CODE, ServerTardisManager.receiveTardis(SecurityControl.withLoyaltyCheck((tardis, server, player, handler, buf, responseSender) -> {
            if (tardis == null)
                return;

            String input = buf.readUtf();

            tardis.landingPad().code().set(input);
        })));
    }

    public LandingPadHandler() {
        super(Id.LANDING_PAD);
    }

    @Override
    public void postInit(InitContext ctx) {
        super.postInit(ctx);

        if (!(this.tardis instanceof ServerTardis))
            return;

        // find old spot and claim
        CachedDirectedGlobalPos pos = this.tardis.travel().position();

        if (pos.getWorld() == null)
            return; // nice

        LandingPadRegion region = LandingPadManager.getInstance(pos.getWorld()).getRegionAt(pos.getPos());

        if (region == null)
            return;

        LandingPadSpot found = region.getSpotAt(pos.getPos()).orElse(null);

        if (found == null)
            return;

        this.claim(found);
    }

    @Override
    public void onLoaded() {
        super.onLoaded();

        code.of(this, LandingPadHandler.CODE);
    }

    public Value<String> code() {
        return code;
    }

    private CachedDirectedGlobalPos update(CachedDirectedGlobalPos pos) {
        TravelHandler travel = this.tardis.travel();
        CachedDirectedGlobalPos destination = travel.destination();
        ServerLevel world = destination.getWorld();

        LandingPadSpot spot = findFreeSpot(world, destination.getPos());

        if (spot == null)
            return null;

        BoolValue hSearch = this.tardis.travel().horizontalSearch();
        boolean old = hSearch.get();
        hSearch.set(false);

        travel.destination(destination.pos(spot.getPos()));
        destination = travel.destination();

        hSearch.set(old);

        this.claim(spot);

        TardisEvents.LANDING_PAD_ADJUST.invoker().onLandingPadAdjust(this.tardis, this.current);
        TardisUtil.sendMessageToInterior(this.tardis.asServer(), Component.translatable("message.ait.landingpad.adjust"));

        return destination;
    }
    private boolean checkCode() {
        ServerLevel world = tardis.travel().destination().getWorld();
        BlockPos pos = tardis.travel().destination().getPos();

        LandingPadRegion region = LandingPadManager.getInstance(world)
                .getRegionAt(pos);

        if (region == null)
            return true;

        return hasMatchingCode(region);
    }
    private boolean hasMatchingCode(LandingPadRegion region) {
        String tardisCode = tardis.landingPad().code().get();
        String regionCode = region.getLandingCode();

        return tardisCode.equalsIgnoreCase(regionCode) || regionCode.isBlank();
    }

    private static @Nullable LandingPadSpot findFreeSpot(ServerLevel world, BlockPos pos) {
        LandingPadRegion region = LandingPadManager.getInstance(world).getRegionAt(pos);

        if (region == null)
            return null;

        return region.getFreeSpot();
    }

    public LandingPadSpot release() {
        LandingPadSpot spot = this.current;
        this.current = null;

        if (spot != null) {
            spot.release();

            this.syncSpot();
        }

        return spot;
    }

    public void claim(LandingPadSpot spot) {
        try {
            this.current = spot;
            this.current.claim(this.tardis);

            this.syncSpot();
        } catch (IllegalStateException e) {
            this.current = null;
            AITMod.LOGGER.error(String.valueOf(e));
        }
    }

    private void syncSpot() {
        CachedDirectedGlobalPos cached = this.tardis.travel().position();
        LandingPadManager.Network.syncTracked(LandingPadManager.Network.Action.ADD, cached.getWorld(), new ChunkPos(cached.getPos()));
    }
}
