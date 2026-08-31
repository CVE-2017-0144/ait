package dev.amble.ait.core.item;

import static net.minecraft.world.level.block.entity.BeaconBlockEntity.playSound;

import java.util.Iterator;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.util.*;
import net.minecraft.resources.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.util.WorldUtil;
import dev.amble.ait.data.enummap.EnumSet;
import dev.amble.ait.data.enummap.Ordered;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class TardisGoatHorn extends LinkableItem {

    private final TagKey<Instrument> instrumentTag;
    private final EnumSet<TardisGoatHorn.Protocols> protocols;

    public TardisGoatHorn(Properties settings, TagKey<Instrument> instrumentTag, TardisGoatHorn.Protocols... abs) {
        super(settings.stacksTo(1), true);
        this.instrumentTag = instrumentTag;

        this.protocols = new EnumSet<>(TardisGoatHorn.Protocols::values);
        this.protocols.addAll(abs);
    }

    public enum Protocols implements Ordered {
        SNAP, HAIL, PERCEPTION, SKELETON;

        @Override
        public int index() {
            return ordinal();
        }
    }

    @Override
    public void onDestroyed(ItemEntity entity) {
        Entity owner = entity.getOwner();

        if (!(owner instanceof ServerPlayer player))
            return;

        Tardis tardis = KeyItem.getTardisStatic(entity.level(), entity.getItem());

        if (tardis == null)
            return;

        tardis.loyalty().subLevel(player, 35);
        tardis.getDesktop().playSoundAtEveryConsole(AITSounds.CLOISTER);
    }

    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(itemStack);
        if (optional.isPresent()) {
            Instrument instrument = (Instrument)((Holder<?>) optional.get()).value();
            user.startUsingItem(hand);
            playSound(world, user.blockPosition(), instrument.soundEvent().value());
            user.getCooldowns().addCooldown(this, instrument.useDuration());
            user.awardStat(Stats.ITEM_USED.get(this));

            Tardis tardis = TardisGoatHorn.getTardisStatic(world, user.getItemInHand(hand));
            if (tardis == null || tardis.travel() == null) return InteractionResultHolder.fail(itemStack);
            CachedDirectedGlobalPos abpd = tardis.travel().destination();
            BlockPos abpdPos = abpd.getPos();
            Component message = Component.translatable("message.ait.tardis_goat_horn.destination", abpdPos.getX(),
                    abpdPos.getY(), abpdPos.getZ(), WorldUtil.worldText(abpd.getDimension(), false))
                    .withStyle(ChatFormatting.GRAY);

            user.displayClientMessage(message, true);

            return InteractionResultHolder.consume(itemStack);
        } else {
            return InteractionResultHolder.fail(itemStack);
        }
    }

    public int getUseDuration(ItemStack stack) {
        Optional<? extends Holder<Instrument>> optional = this.getInstrument(stack);
        return optional.map((instrument) -> instrument.value().useDuration()).orElse(0);
    }

    private Optional<Holder<Instrument>> getInstrument(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTag();
        if (nbtCompound != null && nbtCompound.contains("instrument", 8)) {
            ResourceLocation identifier = ResourceLocation.tryParse(nbtCompound.getString("instrument"));
            if (identifier != null) {
                // Cast the reference to the expected type
                return BuiltInRegistries.INSTRUMENT.getHolder(
                        ResourceKey.create(Registries.INSTRUMENT, identifier)
                ).map(entryRef -> entryRef);
            }
        }

        Iterator<Holder<Instrument>> iterator = BuiltInRegistries.INSTRUMENT.getTagOrEmpty(this.instrumentTag).iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.empty();
    }


    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.TOOT_HORN;
    }
}
