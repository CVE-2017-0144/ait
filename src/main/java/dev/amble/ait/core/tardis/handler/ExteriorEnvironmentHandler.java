package dev.amble.ait.core.tardis.handler;

import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import dev.amble.ait.api.tardis.KeyedTardisComponent;
import dev.amble.ait.api.tardis.TardisEvents;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.data.properties.bool.BoolProperty;
import dev.amble.ait.data.properties.bool.BoolValue;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class ExteriorEnvironmentHandler extends KeyedTardisComponent implements TardisTickable {

    private static final BoolProperty RAINING = new BoolProperty("raining", false);
    private static final BoolProperty THUNDERING = new BoolProperty("thundering", false);
    private static final BoolProperty LAVA = new BoolProperty("lava", false);

    private final BoolValue raining = RAINING.create(this);
    private final BoolValue thundering = THUNDERING.create(this);
    private final BoolValue lava = LAVA.create(this);

    static {
        TardisEvents.LANDED.register((tdis) -> {
            tdis.<ExteriorEnvironmentHandler>handler(Id.ENVIRONMENT).updateLava();
        });
    }

    public ExteriorEnvironmentHandler() {
        super(Id.ENVIRONMENT);
    }

    @Override
    public void onLoaded() {
        this.raining.of(this, RAINING);
        this.thundering.of(this, THUNDERING);
        this.lava.of(this, LAVA);
    }

    @Override
    public void tick(MinecraftServer server) {
        if (server.getTickCount() % 20 != 0)
            return;

        TravelHandler travel = this.tardis.travel();
        Level exterior = travel.position().getWorld();

        if (exterior == null) return;

        boolean isRaining = false;
        boolean isThundering = false;

        if (travel.getState() == TravelHandlerBase.State.LANDED) {
            boolean snowy = tardis.<BiomeHandler>handler(Id.BIOME).getBiomeKey() == BiomeHandler.BiomeType.SNOWY;

            isRaining = !snowy && exterior.isRaining();
            isThundering = !snowy && exterior.isThundering();

            if (isRaining || isThundering) {
                boolean hasRain = exterior.isRainingAt(travel.position().getPos());

                isRaining = isRaining && hasRain;
                isThundering = isThundering && hasRain;
            }
        }

        if (this.isRaining() != isRaining)
            this.raining.set(isRaining);

        if (this.isThundering() != isThundering)
            this.thundering.set(isThundering);
    }

    private void updateLava() {
        boolean hasLava = this.isInLava();

        if (this.tardis.travel().getState() != TravelHandlerBase.State.LANDED)
            hasLava = false;

        if (this.hasLava() != hasLava)
            this.lava.set(hasLava);
    }

    public boolean isRaining() {
        return this.raining.get();
    }

    public boolean isThundering() {
        return this.thundering.get();
    }

    public boolean hasLava() {
        return this.lava.get();
    }

    private boolean isInLava() {
        if (this.isClient())
            return false;

        CachedDirectedGlobalPos cached = tardis.travel().position();

        Level world = cached.getWorld();
        BlockPos tardisPos = cached.getPos();

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                BlockPos blockPos = tardisPos.offset(xOffset, 0, zOffset);

                if (world.getBlockState(blockPos).getBlock() == Blocks.LAVA)
                    return true;
            }
        }

        return false;
    }
}
