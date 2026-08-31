package dev.amble.lib.client.model;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.world.entity.Entity;

@SuppressWarnings("rawtypes")
public abstract class BlockEntityModel extends HierarchicalModel {

    @Override
    public void setupAnim(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) { }
}
