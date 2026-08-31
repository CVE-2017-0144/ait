package dev.amble.ait.module.planet.core.space.planet;

import static dev.amble.ait.AITMod.MOD_ID;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;

/**
 * @author Codiak540
 * Why: Custom crater for Mars/Moon
 */
public class Crater extends Feature<ProbabilityFeatureConfiguration> {

    public static final ResourceLocation CRATER_ID = ResourceLocation.tryBuild(MOD_ID, "crater");

    public Crater(Codec<ProbabilityFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context) {
        if (context.level().isClientSide()) return false;
        if (!((float) context.level().getRandom().nextInt(11) / 10 < (context.config().probability))) return false;
        int radius = 5 + context.level().getRandom().nextInt(10);
        BlockPos pos = context.level().getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, context.origin()).above(radius / 3);
        pos.offset(0, context.level().getRandom().nextInt(4), 0);
        for (BlockPos p : BlockPos.betweenClosed(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius))) {
            if (p.closerThan(pos, radius))
                if (!context.level().getBlockState(p).isAir())
                    context.level().setBlock(p, Blocks.AIR.defaultBlockState(), 2);
        }
        return true;
    }
}