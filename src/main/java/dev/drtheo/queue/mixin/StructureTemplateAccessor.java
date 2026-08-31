package dev.drtheo.queue.mixin;

import java.util.List;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(StructureTemplate.class)
public interface StructureTemplateAccessor {

    @Accessor("entityInfoList")
    List<StructureTemplate.StructureEntityInfo> getEntities();

    @Accessor("size")
    Vec3i getSize();

    @Accessor("palettes")
    List<StructureTemplate.Palette> getBlockInfo();
}