package dev.amble.ait.module.gun.core.item;

import java.util.List;
import java.util.function.Predicate;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.AITStatusEffects;
import dev.amble.ait.core.net.AitNetworking;
import dev.amble.ait.core.util.ItemNbt;

public class BaseGunItem extends ProjectileWeaponItem {
    public static final ResourceLocation SHOOT = AITMod.id("shoot_gun");
    public static final Predicate<ItemStack> GUN_PROJECTILES = itemStack -> itemStack.is(GunItems.STASER_BOLT_MAGAZINE);
    public static final double MAX_AMMO = 64;
    public static final String AMMO_KEY = "ammo";

    @Override
    protected void shootProjectile(LivingEntity shooter, Projectile projectile, int index, float velocity,
            float inaccuracy, float angle, @Nullable LivingEntity target) {
        projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + angle, 0.0f, velocity, inaccuracy);
    }

    public BaseGunItem(Properties settings) {
        super(settings);
    }

    static {
        AitNetworking.registerServerReceiver(SHOOT, (server, player, handler, buf, responseSender) -> {
        boolean shoot = buf.readBoolean();
        boolean isAds = buf.readBoolean();

        if (shoot) {
            if (player.getMainHandItem().getItem() instanceof BaseGunItem gun) {
                if (gun.getCurrentAmmo(player.getMainHandItem()) <= 0) {
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.STONE_BUTTON_CLICK_OFF, SoundSource.PLAYERS, 1.0f, 1.0f);
                    return;
                }
                BaseGunItem.shoot(player.level(), player, InteractionHand.MAIN_HAND, player.getMainHandItem(), GunItems.STASER_BOLT_MAGAZINE.getDefaultInstance(),
                        1.0f, false, 4.0f, player.hasEffect(AITStatusEffects.ZEITON_HIGH) ? 20f : gun.getAimDeviation(isAds), 0.0f);
                CompoundTag compound = ItemNbt.get(player.getMainHandItem());
                double current = compound.getDouble(AMMO_KEY);
                double removableAmmo = (isAds ? 2 : 1);
                player.getCooldowns().addCooldown(gun, gun.getCooldown());
                if (current - removableAmmo <= 0) {
                    compound.putDouble(AMMO_KEY, 0);
                    ItemNbt.set(player.getMainHandItem(), compound);
                } else {
                    compound.putDouble(AMMO_KEY, current - removableAmmo <= 0 ? 0 : current - removableAmmo);
                    ItemNbt.set(player.getMainHandItem(), compound);
                }
            }
        }
        });
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        CompoundTag nbt = ItemNbt.get(stack);
        nbt.putDouble(AMMO_KEY, 0);
        ItemNbt.set(stack, nbt);
        return stack;
    }

    @OnlyIn(Dist.CLIENT)
    public static void shootGun(boolean shoot, boolean isAds) {
        RegistryFriendlyByteBuf buf = AitNetworking.buf();
        buf.writeBoolean(shoot);
        buf.writeBoolean(isAds);
        AitNetworking.send(BaseGunItem.SHOOT, buf);
    }

    @OnlyIn(Dist.CLIENT)
    public void tryShoot(Level world, Entity entity, boolean selected) {
        if (world.isClientSide() && entity instanceof Player player) {
            if (selected) {
                BaseGunItem.shootGun(Minecraft.getInstance().options.keyAttack.isDown(), Minecraft.getInstance().options.keyUse.isDown());
                Minecraft.getInstance().options.keyAttack.setDown(false);
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if (world.isClientSide()) {
            if (entity instanceof Player player) {
                if (!player.getCooldowns().isOnCooldown(this))
                    this.tryShoot(world, entity, selected);
            }
        }
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack otherStack, Slot slot, ClickAction clickType, Player player, SlotAccess cursorStackReference) {
        if (otherStack.getItem() instanceof StaserBoltMagazine magazine) {
            double magazineAmmo = magazine.getCurrentFuel(otherStack);
            if (stack.getItem() instanceof BaseGunItem gun) {
                double ammo = gun.getCurrentAmmo(stack);
                if (clickType == ClickAction.SECONDARY && gun.getCurrentAmmo(stack) < gun.getMaxAmmo()) {
                    double residual = (ammo + magazineAmmo) - gun.getMaxAmmo();
                    gun.setCurrentAmmo(ammo + magazineAmmo, stack);
                    magazine.setCurrentFuel(residual, otherStack);
                    return true;
                }
            }
        }
        return super.overrideOtherStackedOnMe(stack, otherStack, slot, clickType, player, cursorStackReference);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return GUN_PROJECTILES;
    }

    public double getCurrentAmmo(ItemStack stack) {
        if (stack.getItem() == this)
            return ItemNbt.get(stack).getDouble(AMMO_KEY);
        return 0.0d;
    }

    public void setCurrentAmmo(double var, ItemStack stack) {
        if (stack.getItem() == this)
            ItemNbt.edit(stack, tag -> tag.putDouble(AMMO_KEY, Math.min(var, this.getMaxAmmo())));
    }

    public double getMaxAmmo() {
        return MAX_AMMO;
    }

    public float getAimDeviation(boolean isAds) {
        return isAds ? 0.2f : 1.42323f;
    }

    public int getCooldown() {
        return 20;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 24;
    }

    private static void shoot(Level world, LivingEntity shooter, InteractionHand hand, ItemStack gun, ItemStack projectile, float soundPitch, boolean creative, float speed, float divergence, float simulated) {
        AbstractArrow projectileEntity;
        if (world.isClientSide) {
            return;
        }
        projectileEntity = BaseGunItem.createBolt(world, shooter, gun, projectile);
        if (creative || simulated != 0.0f) {
            projectileEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
        }
        Vec3 up = shooter.getUpVector(1.0f);
        Quaternionf spread = new Quaternionf().setAngleAxis(simulated * ((float) Math.PI / 180), up.x, up.y, up.z);
        Vector3f aim = shooter.getViewVector(1.0f).toVector3f().rotate(spread);
        projectileEntity.shoot(aim.x(), aim.y(), aim.z(), speed, divergence);
        gun.hurtAndBreak(3, shooter, hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND);
        projectileEntity.setPosRaw(shooter.getX(), shooter.getY() + 1.2f, shooter.getZ());
        world.addFreshEntity(projectileEntity);
        world.playSound(null, shooter.getX(), shooter.getY(), shooter.getZ(), AITSounds.STASER, SoundSource.PLAYERS, 0.25f, soundPitch);
    }

    private static AbstractArrow createBolt(Level world, LivingEntity entity, ItemStack gun, ItemStack bolt) {
        StaserBoltMagazine boltItem = (StaserBoltMagazine)(bolt.getItem() instanceof StaserBoltMagazine ? bolt.getItem() : GunItems.STASER_BOLT_MAGAZINE);
        AbstractArrow persistentProjectileEntity = boltItem.createStaserbolt(world, bolt, entity);
        if (entity instanceof Player) {
            persistentProjectileEntity.setCritArrow(true);
        }
        persistentProjectileEntity.setSoundEvent(AITSounds.STASER);
        int i = world.registryAccess().registryOrThrow(Registries.ENCHANTMENT)
                .getHolder(Enchantments.PIERCING)
                .map(piercing -> EnchantmentHelper.getItemEnchantmentLevel(piercing, gun)).orElse(0);
        if (i > 0) {
            persistentProjectileEntity.setPierceLevel((byte)i);
        }
        return persistentProjectileEntity;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, tooltipContext, tooltip, context);

        double currentAmmo = this.getCurrentAmmo(stack);
        ChatFormatting ammoColor = currentAmmo > (this.getMaxAmmo() / 4) ? ChatFormatting.GREEN : ChatFormatting.RED;

        tooltip.add(
                Component.translatable("message.ait.ammo", currentAmmo)
                        .withStyle(ammoColor)
                        .append(Component.literal(" / ").withStyle(ChatFormatting.GRAY))
                        .append(Component.literal(String.valueOf(this.getMaxAmmo())).withStyle(ChatFormatting.GRAY))
        );
    }
}
