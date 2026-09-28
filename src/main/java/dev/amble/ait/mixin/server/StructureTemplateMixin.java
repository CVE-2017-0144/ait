package dev.amble.ait.mixin.server;

import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import dev.amble.ait.core.entities.ConsoleControlEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin {

    @Shadow
    @Final
    private List<StructureTemplate.StructureEntityInfo> entityInfoList;

    @Redirect(method = "fillFromWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;fillEntityList(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;)V", ordinal = 0))
    private void ait$saveFromWorld(StructureTemplate instance, Level world, BlockPos firstCorner,
            BlockPos secondCorner) {
        List<Entity> list = world.getEntitiesOfClass(Entity.class, AABB.encapsulatingFullBlocks(firstCorner, secondCorner),
                (entity) -> !(entity instanceof Player) && !(entity instanceof ConsoleControlEntity));
        this.entityInfoList.clear();

        Vec3 vec3d;
        CompoundTag nbtCompound;
        BlockPos blockPos;
        for (Iterator<Entity> var5 = list.iterator(); var5.hasNext(); this.entityInfoList
                .add(new StructureTemplate.StructureEntityInfo(vec3d, blockPos, nbtCompound.copy()))) {
            Entity entity = var5.next();
            vec3d = new Vec3(entity.getX() - (double) firstCorner.getX(), entity.getY() - (double) firstCorner.getY(),
                    entity.getZ() - (double) firstCorner.getZ());
            nbtCompound = new CompoundTag();
            entity.save(nbtCompound);
            if (entity instanceof Painting) {
                blockPos = ((Painting) entity).getPos().subtract(firstCorner);
            } else {
                blockPos = BlockPos.containing(vec3d);
            }
        }
    }
}
