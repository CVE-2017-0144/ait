package dev.amble.ait.core.blockentities;

import org.jetbrains.annotations.Nullable;
import dev.amble.ait.api.tardis.link.v2.block.InteriorLinkableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AbstractConsoleBlockEntity extends InteriorLinkableBlockEntity implements Container,
        MenuProvider,
        Nameable {

    private LockCode lock = LockCode.NO_LOCK;
    @Nullable private Component customName;

    public AbstractConsoleBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        this.lock = LockCode.fromTag(nbt);
        if (nbt.contains("CustomName", Tag.TAG_STRING)) {
            this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"), registries);
        }
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        this.lock.addToTag(nbt);
        if (this.customName != null) {
            nbt.putString("CustomName", Component.Serializer.toJson(this.customName, registries));
        }
    }

    public void setCustomName(Component customName) {
        this.customName = customName;
    }

    @Override
    public Component getName() {
        if (this.customName != null) {
            return this.customName;
        }
        return this.getContainerName();
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Override
    @Nullable public Component getCustomName() {
        return this.customName;
    }

    protected abstract Component getContainerName();

    public boolean checkUnlocked(Player player) {
        return BaseContainerBlockEntity.canUnlock(player, this.lock, this.getDisplayName());
    }

    public static boolean checkUnlocked(Player player, LockCode lock, Component containerName) {
        if (player.isSpectator() || lock.unlocksWith(player.getMainHandItem())) {
            return true;
        }
        player.displayClientMessage(Component.translatable("container.isLocked", containerName), true);
        player.playNotifySound(SoundEvents.CHEST_LOCKED, SoundSource.BLOCKS, 1.0f, 1.0f);
        return false;
    }

    @Override
    @Nullable public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        if (this.checkUnlocked(playerEntity)) {
            return this.createScreenHandler(i, playerInventory);
        }
        return null;
    }

    protected abstract AbstractContainerMenu createScreenHandler(int var1, Inventory var2);
}
