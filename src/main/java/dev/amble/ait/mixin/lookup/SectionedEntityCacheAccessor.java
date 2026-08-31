package dev.amble.ait.mixin.lookup;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.LongSortedSet;
import net.minecraft.world.level.entity.EntityAccess;
import net.minecraft.world.level.entity.EntitySection;
import net.minecraft.world.level.entity.EntitySectionStorage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntitySectionStorage.class)
public interface SectionedEntityCacheAccessor<T extends EntityAccess> {

    @Accessor("sections")
    Long2ObjectMap<EntitySection<T>> getTrackingSections();

    @Accessor("sectionIds")
    LongSortedSet getTrackedPositions();
}
