package dev.amble.ait.core.entities.base;

import java.util.Collections;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Dynamic;
import org.jetbrains.annotations.Nullable;
import net.minecraft.nbt.NbtOps;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class DummyLivingEntity extends LivingEntity {

    protected static final Iterable<ItemStack> ARMOR = Collections.singleton(ItemStack.EMPTY);
    private Brain<?> brain;

    protected DummyLivingEntity(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
        NbtOps nbtOps = NbtOps.INSTANCE;
        this.brain = this.makeBrain(new Dynamic<>(nbtOps, nbtOps.createMap(ImmutableMap.of(nbtOps.createString("memories"), nbtOps.emptyMap()))));
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return ARMOR;
    }

    @Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.LEFT;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public boolean isInvulnerable() {
        return true;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true;
    }

    @Override
    public boolean displayFireAnimation() {
        return false;
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Nullable @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.EMPTY;
    }

    @Override
    public Fallsounds getFallSounds() {
        return new Fallsounds(SoundEvents.EMPTY, SoundEvents.EMPTY);
    }

    @Nullable @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.EMPTY;
    }

    @Override
    protected void playBlockFallSound() {
    }

    @Override
    public Brain<?> getBrain() {
        return this.brain;
    }

    @Override
    public boolean addEffect(MobEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    public static AttributeSupplier.Builder createDummyAttributes() {
        return Mob.createMobAttributes().add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.MAX_HEALTH, 20.0).add(Attributes.ATTACK_DAMAGE, 0);
    }
}
