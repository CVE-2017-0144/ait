package dev.amble.ait.core.lock;

import java.util.ArrayList;
import java.util.List;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import dev.amble.ait.AITMod;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.lib.register.datapack.SimpleDatapackRegistry;

public class LockedDimensionRegistry extends SimpleDatapackRegistry<LockedDimension> {
    private static final LockedDimensionRegistry instance = new LockedDimensionRegistry();


    public LockedDimensionRegistry() {
        super(LockedDimension::fromInputStream, LockedDimension.CODEC, "locked_dimension", true, AITMod.MOD_ID);
    }

    public static LockedDimension NETHER;

    @Override
    public void onCommonInit() {
        super.onCommonInit();
        this.defaults();
        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(this);
    }

    @Override
    protected void defaults() {
        NETHER = register(new LockedDimension(BuiltinDimensionTypes.NETHER_EFFECTS, new ItemStack(Items.BLAZE_ROD)));
        // all others should be in datapack
    }

    @Override
    public LockedDimension fallback() {
        return NETHER;
    }

    public LockedDimension get(Level world) {
        return this.get(world.dimension().location());
    }
    public List<LockedDimension> forStack(ItemStack stack) {
        // ow :(
        List<LockedDimension> copy = new ArrayList<>(this.REGISTRY.values());

        copy.removeIf((dim) -> !(dim.stack().getItem().equals(stack.getItem())));

        return copy;
    }

    public static LockedDimensionRegistry getInstance() {
        return instance;
    }

    public static boolean tryUnlockDimension(ServerPlayer player, ItemStack held, ServerTardis tardis) {
        if (held.isEmpty()) return false;
        if (!AITMod.CONFIG.lockDimensions) return false;

        List<LockedDimension> dims = getInstance().forStack(held);

        if (dims.isEmpty()) return false;

        dims.forEach(dim -> {
            tardis.stats().unlock(dim);

            player.displayClientMessage(Component.translatable("message.ait.dimension.unlocked", dim.text()).withStyle(
                    ChatFormatting.BOLD, ChatFormatting.ITALIC, ChatFormatting.GOLD), false);
        });
        player.serverLevel().playSound(null, player.blockPosition(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE,
                SoundSource.PLAYERS, 0.2F, 1.0F);

        held.shrink(1);

        return true;
    }

    public boolean isUnlocked(Tardis tardis, Level world) {
        if (!AITMod.CONFIG.lockDimensions)
            return true;

        if (isEnd(world))
            return WorldUtil.isEndDragonDead();

        LockedDimension dim = this.get(world);
        return dim == null || tardis.isUnlocked(dim);
    }
    private boolean isEnd(Level world) {
        return world.dimension().location().equals(Level.END.location());
    }
}
