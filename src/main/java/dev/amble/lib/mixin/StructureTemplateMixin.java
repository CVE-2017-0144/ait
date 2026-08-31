package dev.amble.lib.mixin;

import dev.amble.lib.blockentity.StructurePlaceableBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {

    @Redirect(method = "placeInWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;load(Lnet/minecraft/nbt/CompoundTag;)V"))
    public void place(BlockEntity blockEntity, CompoundTag nbt) {
        if (blockEntity instanceof StructurePlaceableBlockEntity placeable) placeable.amble$onStructurePlaced(nbt);

        blockEntity.loadWithComponents(nbt, world.registryAccess());
    }
}