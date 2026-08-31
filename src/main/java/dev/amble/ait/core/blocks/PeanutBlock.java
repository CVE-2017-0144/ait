package dev.amble.ait.core.blocks;

import dev.amble.ait.core.tardis.util.TardisUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class PeanutBlock extends Block {
    private static final float EXPLOSION_POWER = 100;

    public PeanutBlock(Properties settings) {
        super(settings.strength(-1.0f, 3600000.0f).emissiveRendering((state, world, pos) -> true).lightLevel(value -> 128)
                .friction(100));
    }

    public void explode(Level world, BlockPos pos) {
        world.explode(null, world.damageSources().fellOutOfWorld(), TardisUtil.EXPLOSION_BEHAVIOR, pos.getCenter(), EXPLOSION_POWER, TardisUtil.doCreateFire(world),
                Level.ExplosionInteraction.MOB);
        world.explode(null, null, TardisUtil.EXPLOSION_BEHAVIOR, pos.getCenter(), EXPLOSION_POWER, TardisUtil.doCreateFire(world), Level.ExplosionInteraction.BLOCK);
        world.explode(null, world.damageSources().fellOutOfWorld(), TardisUtil.EXPLOSION_BEHAVIOR, pos.getCenter(), EXPLOSION_POWER, TardisUtil.doCreateFire(world),
                Level.ExplosionInteraction.TNT);
    }
}
