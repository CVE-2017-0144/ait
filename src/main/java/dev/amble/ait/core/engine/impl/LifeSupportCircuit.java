package dev.amble.ait.core.engine.impl;

import java.util.List;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.engine.DurableSubSystem;
import dev.amble.ait.core.engine.StructureHolder;
import dev.amble.ait.core.engine.block.multi.MultiBlockStructure;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.util.TardisUtil;
import dev.amble.lib.util.ServerLifecycleHooks;

public class LifeSupportCircuit extends DurableSubSystem implements StructureHolder {

    private static final int TICK_RATE = 20;

    private static final MultiBlockStructure STRUCTURE = createStructure();
    private static MultiBlockStructure createStructure() {
        MultiBlockStructure made = new MultiBlockStructure();

        return made;
    }

    public LifeSupportCircuit() {
        super(Id.LIFE_SUPPORT);
    }

    @Override
    protected float cost() {
        return 0.25f;
    }

    @Override
    protected boolean shouldDurabilityChange() {
        return !this.tardis.crash().isNormal();
    }

    @Override
    public MultiBlockStructure getStructure() {
        return MultiBlockStructure.EMPTY;
    }

    @Override
    public Item asItem() {
        return AITItems.LIFE_SUPPORT;
    }

    @Override
    public void tick() {
        super.tick();

        ServerTardis tardis = this.tardis().asServer();

        if (!this.isEnabled()) return;
        if (ServerLifecycleHooks.get().getTickCount() % TICK_RATE != 0)
            return;

        List<LivingEntity> entities = TardisUtil.getLivingEntitiesInInterior(tardis);

        for (LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, TICK_RATE * 2, 1, true, false));
        }
    }
}
