package dev.drtheo.gaslighter.mixin;

import dev.drtheo.gaslighter.api.Twitter;
import it.unimi.dsi.fastutil.longs.LongArraySet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerLevel.class)
public class ServerWorldMixin implements Twitter {

    @Unique private LongSet ait$fake;

    @Override
    public void ait$setFake(BlockPos pos, boolean fake) {
        if (this.ait$fake == null)
            this.ait$fake = new LongArraySet();

        if (fake) {
            this.ait$fake.add(pos.asLong());
            return;
        }

        this.ait$fake.remove(pos.asLong());
    }

    @Override
    public boolean ait$isFake(BlockPos pos) {
        if (this.ait$fake == null)
            return false;

        return this.ait$fake.contains(pos.asLong());
    }
}
