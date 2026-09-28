package dev.amble.ait.core.tardis.handler.travel;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mth;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import dev.amble.lib.util.ServerLifecycleHooks;

public class TravelUtil {

    private static final int BASE_FLIGHT_TICKS = 5 * 20;
    private static final int QUICK_FLIGHT_THRESHOLD = 128;

    public static void randomPos(Tardis tardis, int limit, int max, Consumer<CachedDirectedGlobalPos> consumer) {
        MinecraftServer server = ServerLifecycleHooks.get();
        CachedDirectedGlobalPos start = tardis.travel().destination();

        CompletableFuture.supplyAsync(() -> {
            CachedDirectedGlobalPos dest = start;

            int posX = dest.getPos().getX();
            int posZ = dest.getPos().getZ();

            for (int i = 0; i <= limit; i++) {
                dest = dest.pos(
                        AITMod.RANDOM.nextBoolean()
                                ? AITMod.RANDOM.nextInt(max) == 0 ? posX + 1 : posX + AITMod.RANDOM.nextInt(max)
                                : AITMod.RANDOM.nextInt(max) == -0 ? posX - 1 : posX - AITMod.RANDOM.nextInt(max),
                        dest.getPos().getY(),
                        AITMod.RANDOM.nextBoolean()
                                ? AITMod.RANDOM.nextInt(max) == 0 ? posZ + 1 : posZ + AITMod.RANDOM.nextInt(max)
                                : AITMod.RANDOM.nextInt(max) == -0 ? posZ - 1 : posZ - AITMod.RANDOM.nextInt(max));
            }

            return dest;
        }).thenAccept(dest -> server.execute(() -> consumer.accept(dest)));
    }

    public static void travelTo(Tardis tardis, CachedDirectedGlobalPos pos) {
        TravelHandler travel = tardis.travel();

        travel.autopilot(true);
        travel.destination(pos);

        if (travel.getState() == TravelHandlerBase.State.LANDED)
            travel.dematerialize();
    }

    public static CachedDirectedGlobalPos getPositionFromPercentage(CachedDirectedGlobalPos source,
                                                                    CachedDirectedGlobalPos destination, int percentage) {
        // https://stackoverflow.com/questions/33907276/calculate-point-between-two-coordinates-based-on-a-percentage
        if (percentage == 0)
            return source;

        if (percentage == 100)
            return destination;

        float per = percentage / 100f;
        BlockPos pos = source.getPos();
        BlockPos diff = destination.getPos().subtract(pos);

        return destination
                .pos(pos.offset((int) (diff.getX() * per), (int) (diff.getY() * per), (int) (diff.getZ() * per)));
    }

    public static int getFlightDuration(CachedDirectedGlobalPos source, CachedDirectedGlobalPos destination) {
        float distance = Mth.sqrt((float) source.getPos().distSqr(destination.getPos()));

        boolean hasDirChanged = source.getRotation() != destination.getRotation();
        boolean hasDimChanged = !source.getDimension().equals(destination.getDimension());
        
        if (distance < QUICK_FLIGHT_THRESHOLD && !hasDimChanged)
            return 1; // fast travel

        return (int) (BASE_FLIGHT_TICKS + (distance / 10f) + (hasDirChanged ? 100 : 0) + (hasDimChanged ? 600 : 0));
    }

    public static CachedDirectedGlobalPos jukePos(CachedDirectedGlobalPos pos, int min, int max, int multiplier) {
        Random random = AITMod.RANDOM;
        multiplier *= random.nextInt(0, 2) == 0 ? 1 : -1;

        return pos.offset(random.nextInt(min, max) * multiplier, 0,
                random.nextInt(min, max) * multiplier);
    }

    public static CachedDirectedGlobalPos jukePos(CachedDirectedGlobalPos pos, int min, int max) {
        return jukePos(pos, min, max, 1);
    }

    public static int getHardCap(int targetTicks) {
        if (targetTicks <= QUICK_FLIGHT_THRESHOLD) return 1;
        return 1 + Mth.floor(1d/6d * Mth.sqrt(targetTicks - QUICK_FLIGHT_THRESHOLD));
    }
}
