package dev.amble.ait.api.tardis.link.v2.block;

import dev.amble.ait.core.world.TardisServerWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class InteriorLinkableBlockEntity extends AbstractLinkableBlockEntity {

    public InteriorLinkableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void setLevel(Level world) {
        super.setLevel(world);

        if (this.ref != null)
            return;

        if (world instanceof TardisServerWorld tardisWorld)
            this.link(tardisWorld.getTardis());
    }
}
