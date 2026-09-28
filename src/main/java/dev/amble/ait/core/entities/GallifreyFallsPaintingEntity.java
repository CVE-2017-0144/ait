package dev.amble.ait.core.entities;

import dev.amble.ait.core.AITItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;


public class GallifreyFallsPaintingEntity extends BOTIPaintingEntity {

    public GallifreyFallsPaintingEntity(EntityType<? extends BOTIPaintingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(AITItems.GALLIFREY_FALLS_PAINTING);
    }

    @Override
    public void dropItem(@Nullable Entity entity) {
        if (!this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            return;
        }
        this.playSound(SoundEvents.PAINTING_BREAK, 1.0f, 1.0f);
        if (entity instanceof Player player && player.isCreative()) {
            return;
        }
        this.spawnAtLocation(AITItems.GALLIFREY_FALLS_PAINTING);
    }
}
