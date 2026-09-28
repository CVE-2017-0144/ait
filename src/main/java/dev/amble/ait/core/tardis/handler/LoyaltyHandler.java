package dev.amble.ait.core.tardis.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

import dev.amble.ait.AITMod;
import dev.amble.ait.api.Nameable;
import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.api.tardis.TardisTickable;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.likes.ItemOpinion;
import dev.amble.ait.core.likes.ItemOpinionRegistry;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.data.schema.console.ConsoleVariantSchema;
import dev.amble.ait.data.schema.desktop.TardisDesktopSchema;
import dev.amble.ait.data.schema.exterior.ExteriorVariantSchema;
import dev.amble.ait.data.schema.sonic.SonicSchema;
import dev.amble.ait.registry.impl.DesktopRegistry;
import dev.amble.ait.registry.impl.SonicRegistry;
import dev.amble.ait.registry.impl.console.variant.ConsoleVariantRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class LoyaltyHandler extends TardisComponent implements TardisTickable {
    private final Map<UUID, Loyalty> data;
    private boolean messageEnabled = true;

    public boolean isMessageEnabled() {
        return messageEnabled;
    }

    public void setMessageEnabled(boolean messageEnabled) {
        this.messageEnabled = messageEnabled;
    }

    public LoyaltyHandler(HashMap<UUID, Loyalty> data) {
        super(Id.LOYALTY);
        this.data = data;
    }

    public LoyaltyHandler() {
        this(new HashMap<>());
    }

    public Map<UUID, Loyalty> data() {
        return this.data;
    }

    public Loyalty get(Player player) {
        return this.data.getOrDefault(player.getUUID(), new Loyalty(Loyalty.Type.NEUTRAL));
    }

    public Loyalty set(ServerPlayer player, Loyalty loyalty) {
        this.data.put(player.getUUID(), loyalty);
        this.unlock(player, loyalty);

        this.sync();
        return loyalty;
    }

    @Override
    public void tick(MinecraftServer server) {
        if (server.getTickCount() % 40 != 0)
            return;

        for (ServerPlayer player : tardis.asServer().world().players()) {
            Loyalty loyalty = this.get(player);

            if (!loyalty.isOf(Loyalty.Type.NEUTRAL))
                continue;

            if (ItemOpinionRegistry.getInstance().get(player.getMainHandItem()).isPresent()) {
                ItemOpinion opinion = ItemOpinionRegistry.getInstance().get(player.getMainHandItem()).get();
                tardis.opinions().contains(opinion);
                player.sendSystemMessage(Component.translatable("ait.tardis.likes_item", true));
            }

            if (AITMod.RANDOM.nextInt(0, 20) != 14)
                continue;

            this.addLevel(player, 1);
        }
    }

    public void update(ServerPlayer player, Function<Loyalty, Loyalty> consumer) {
        Loyalty current = this.get(player);
        current = consumer.apply(current);

        this.set(player, current);
    }

    public void unlock(ServerPlayer player, Loyalty loyalty) {
        ServerTardis tardis = (ServerTardis) this.tardis;

        boolean playSound = messageEnabled;

        if (playSound) {
            playSound = ConsoleVariantRegistry.getInstance().tryUnlock(tardis, loyalty,
                    schema -> this.playUnlockEffects(player, schema));
            playSound = DesktopRegistry.getInstance().tryUnlock(tardis, loyalty,
                    schema -> this.playUnlockEffects(player, schema)) || playSound;
            playSound = ExteriorVariantRegistry.getInstance().tryUnlock(tardis, loyalty,
                    schema -> this.playUnlockEffects(player, schema)) || playSound;
            playSound = SonicRegistry.getInstance().tryUnlock(tardis, loyalty,
                    schema -> this.playUnlockEffects(player, schema)) || playSound;
        }

        if (playSound)
            player.serverLevel().playSound(null, player.blockPosition(), AITSounds.LOYALTY_UP,
                    SoundSource.PLAYERS, 0.2F, 1.0F);

        if (loyalty.isOf(Loyalty.Type.OWNER))
            TardisCriterions.REACH_OWNER.trigger(player);
        else if (loyalty.isOf(Loyalty.Type.PILOT))
            TardisCriterions.REACH_PILOT.trigger(player);
    }

    private void playUnlockEffects(ServerPlayer player, Nameable nameable) {
        Component nameText = nameable.text().copy().withStyle(ChatFormatting.GREEN);

        Component unlockedMessage;
        if (nameable instanceof SonicSchema) {
            unlockedMessage = Component.translatable("message.ait.unlocked_sonic", nameText).withStyle(ChatFormatting.WHITE);
        } else if (nameable instanceof ConsoleVariantSchema) {
            unlockedMessage = Component.translatable("message.ait.unlocked_console", nameText).withStyle(ChatFormatting.WHITE);
        } else if (nameable instanceof TardisDesktopSchema) {
            unlockedMessage = Component.translatable("message.ait.unlocked_interior", nameText).withStyle(ChatFormatting.WHITE);
        } else if (nameable instanceof ExteriorVariantSchema) {
            unlockedMessage = Component.translatable("message.ait.unlocked_exterior", nameText).withStyle(ChatFormatting.WHITE);
        } else {
            unlockedMessage = Component.translatable("message.ait.unlocked", nameText).withStyle(ChatFormatting.WHITE);
        }

        player.displayClientMessage(unlockedMessage, false);
    }


    public void addLevel(ServerPlayer player, int level) {
        this.update(player, loyalty -> loyalty.add(level));
    }

    public void subLevel(ServerPlayer player, int level) {
        this.addLevel(player, -level);
    }

    public ServerPlayer getLoyalPlayerInside() {
        if (!(this.tardis instanceof ServerTardis serverTardis))
            return null;

        ServerPlayer highest = null;
        int highestLoyalty = 0;

        for (ServerPlayer player : serverTardis.world().players()) {
            if (highest == null) {
                highest = player;
                highestLoyalty = this.get(highest).level();
                continue;
            }

            int found = this.get(player).level();

            if (found > highestLoyalty) {
                highest = player;
                highestLoyalty = found;
            }
        }

        return highest;
    }

    public void sendMessageToPilot(Component text) {
        ServerPlayer player = this.getLoyalPlayerInside();

        if (player == null)
            return;

        player.displayClientMessage(text, true);
    }
}
