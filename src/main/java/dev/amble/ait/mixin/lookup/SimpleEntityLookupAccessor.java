package dev.amble.ait.mixin.lookup;

import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySectionStorage;
import net.minecraft.world.level.entity.LevelEntityGetterAdapter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LevelEntityGetterAdapter.class)
public interface SimpleEntityLookupAccessor<T extends EntityAccess> {

    @Accessor("sectionStorage")
    EntitySectionStorage<T> getCache();
}
