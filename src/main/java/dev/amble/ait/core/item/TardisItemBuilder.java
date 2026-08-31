package dev.amble.ait.core.item;

import dev.amble.ait.api.tardis.TardisComponent;
import dev.amble.ait.core.blockentities.ConsoleBlockEntity;
import dev.amble.ait.core.tardis.ServerTardis;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.impl.DirectionControl;
import dev.amble.ait.core.tardis.handler.FuelHandler;
import dev.amble.ait.core.tardis.handler.LoyaltyHandler;
import dev.amble.ait.core.tardis.handler.SubSystemHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.tardis.manager.ServerTardisManager;
import dev.amble.ait.core.tardis.manager.TardisBuilder;
import dev.amble.ait.core.tardis.util.DefaultThemes;
import dev.amble.ait.data.Loyalty;
import dev.amble.ait.registry.impl.DesktopRegistry;
import dev.amble.ait.registry.impl.exterior.ExteriorVariantRegistry;
import dev.amble.lib.data.CachedDirectedGlobalPos;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.properties.RotationSegment;

public class TardisItemBuilder extends Item {
    private final ResourceLocation exterior;
    private final ResourceLocation desktop;

    public TardisItemBuilder(Properties settings, ResourceLocation exterior, ResourceLocation desktopId) {
        super(settings);

        this.exterior = exterior;
        this.desktop = desktopId;
    }

    public TardisItemBuilder(Properties settings, ResourceLocation exterior) {
        this(settings, exterior, null);
    }

    public TardisItemBuilder(Properties settings) {
        this(settings, null);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        Player player = context.getPlayer();

        if (!(player instanceof ServerPlayer serverPlayer))
            return InteractionResult.PASS;

        if (!(world instanceof ServerLevel serverWorld))
            return InteractionResult.PASS;

        if (player.getCooldowns().isOnCooldown(this))
            return InteractionResult.FAIL;

        if (context.getHand() != InteractionHand.MAIN_HAND)
            return InteractionResult.SUCCESS;

        CachedDirectedGlobalPos pos = CachedDirectedGlobalPos.create(serverWorld,
                serverWorld.getBlockState(context.getClickedPos()).canBeReplaced()
                        ? context.getClickedPos()
                        : context.getClickedPos().above(),
                DirectionControl.getGeneralizedRotation(RotationSegment.convertToSegment(player.getVisualRotationYInDegrees())));

        BlockEntity entity = world.getBlockEntity(context.getClickedPos());

        if (entity instanceof ConsoleBlockEntity consoleBlock) {
            Tardis tardis = consoleBlock.tardis().get();

            if (tardis == null)
                return InteractionResult.FAIL;

            TravelHandlerBase.State state = tardis.travel().getState();

            if (!(state == TravelHandlerBase.State.LANDED || state == TravelHandlerBase.State.FLIGHT))
                return InteractionResult.PASS;

            consoleBlock.killControls();
            world.removeBlock(context.getClickedPos(), false);
            world.removeBlockEntity(context.getClickedPos());
            return InteractionResult.SUCCESS;
        }

        // ExteriorCategorySchema category = CategoryRegistry.getInstance().get(this.exterior);

        TardisBuilder builder = new TardisBuilder().at(pos)
                .owner(serverPlayer)
                .<FuelHandler>with(TardisComponent.Id.FUEL, fuel -> {
                    fuel.setCurrentFuel(fuel.getMaxFuel());
                    fuel.enablePower();
                })
                .with(TardisComponent.Id.SUBSYSTEM, SubSystemHandler::repairAll)
                .<LoyaltyHandler> with(TardisComponent.Id.LOYALTY,
                loyalty -> {
                    loyalty.setMessageEnabled(false);
                    loyalty.set(serverPlayer, new Loyalty(Loyalty.Type.OWNER));
                    loyalty.setMessageEnabled(true);
                });

        if (this.exterior == null || this.desktop == null) {
            DefaultThemes.getRandom().apply(builder);
        } else {
            builder.exterior(ExteriorVariantRegistry.getInstance().get(this.exterior));
            builder.desktop(DesktopRegistry.getInstance().get(this.desktop));
        }

        ServerTardis created = ServerTardisManager.getInstance()
                .create(builder);

        player.displayClientMessage(Component.translatable("message.ait.unlocked_all", Component.translatable("message.ait.all_types").withStyle(ChatFormatting.GREEN)).withStyle(ChatFormatting.WHITE), false);


        if ( created == null ) {
            player.displayClientMessage(Component.translatable("message.ait.max_tardises"), true);
            return InteractionResult.FAIL;
        }

        context.getItemInHand().shrink(1);
        player.getCooldowns().addCooldown(this, 20);
        return InteractionResult.SUCCESS;
    }
}
