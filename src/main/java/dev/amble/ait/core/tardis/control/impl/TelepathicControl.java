package dev.amble.ait.core.tardis.control.impl;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.NameTagItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import net.minecraft.world.level.levelgen.structure.Structure;
import dev.drtheo.queue.api.ActionQueue;
import org.jetbrains.annotations.Nullable;
import dev.amble.ait.AITMod;
import dev.amble.ait.api.tardis.link.LinkableItem;
import dev.amble.ait.core.AITItems;
import dev.amble.ait.core.AITSounds;
import dev.amble.ait.core.advancement.TardisCriterions;
import dev.amble.ait.core.drinks.DrinkUtil;
import dev.amble.ait.core.item.HandlesItem;
import dev.amble.ait.core.item.HypercubeItem;
import dev.amble.ait.core.item.KeyItem;
import dev.amble.ait.core.item.SonicItem;
import dev.amble.ait.core.likes.ItemOpinion;
import dev.amble.ait.core.likes.ItemOpinionRegistry;
import dev.amble.ait.core.lock.LockedDimensionRegistry;
import dev.amble.ait.core.tardis.Tardis;
import dev.amble.ait.core.tardis.control.Control;
import dev.amble.ait.core.tardis.control.impl.pos.IncrementManager;
import dev.amble.ait.core.tardis.handler.SiegeHandler;
import dev.amble.ait.core.tardis.handler.distress.DistressCall;
import dev.amble.ait.core.tardis.handler.travel.TravelHandler;
import dev.amble.ait.core.tardis.handler.travel.TravelHandlerBase;
import dev.amble.ait.core.tardis.handler.travel.TravelUtil;
import dev.amble.ait.core.tardis.util.AsyncLocatorUtil;
import dev.amble.ait.data.Loyalty;
import dev.amble.lib.data.CachedDirectedGlobalPos;

public class TelepathicControl extends Control {

    public static final int RADIUS = 256;

    public TelepathicControl() {
        super(AITMod.id("telepathic_circuit"));
    }

    @Override
    public Result runServer(Tardis tardis, ServerPlayer player, ServerLevel world, BlockPos console, boolean leftClick) {
        super.runServer(tardis, player, world, console, leftClick);

        if (tardis.stats().security().get() && !KeyItem.hasMatchingKeyInInventory(player, tardis))
            return Result.FAILURE;

        ItemStack held = player.getMainHandItem();
        Item type = held.getItem();

        if (type == Items.BRICK) {
            tardis.siege().texture().set(SiegeHandler.BRICK_TEXTURE);
            return Result.FAILURE;
        }

        if (type == Items.STONE) {
            tardis.siege().texture().set(SiegeHandler.DEFAULT_TEXTURRE);
            return Result.FAILURE;
        }

        if (type == Items.OBSERVER) {
            tardis.siege().texture().set(SiegeHandler.APERTURE_TEXTURE);
            return Result.FAILURE;
        }

        if (type == Items.QUARTZ_BLOCK) {
            tardis.siege().texture().set(SiegeHandler.COMPANION_TEXTURE);
            return Result.FAILURE;
        }

        if (type instanceof LinkableItem linker) {
            if (linker instanceof SonicItem || linker instanceof HandlesItem)
                return Result.FAILURE;

            linker.link(held, tardis);
            world.playSound(null, player.blockPosition(), SoundEvents.AMETHYST_BLOCK_RESONATE, SoundSource.BLOCKS,
                    1.0F, 1.0F);
            return Result.SUCCESS_ALT;
        }

        if (type instanceof NameTagItem) {
            if (!held.hasCustomHoverName())
                return Result.FAILURE;

            tardis.stats().setName(held.getHoverName().getString());
            world.playSound(null, player.blockPosition(), SoundEvents.ANVIL_PLACE, SoundSource.BLOCKS, 1F, 1.0F);

            if (!player.isCreative())
                held.shrink(1);

            return Result.SUCCESS;
        }

        if (type instanceof HypercubeItem) {
            DistressCall call = HypercubeItem.getCall(held, world.getServer().getTickCount());

            if (call == null) {
                // create new call
                call = DistressCall.create(tardis, held.hasCustomHoverName() ? held.getHoverName().getString() : "SOS", true);
                HypercubeItem.setCall(held, call);
            }

            if (call.canSend(tardis.getUuid())) {
                call.send(tardis.getUuid(), held);
            }

            // receive and process call
            call.summon(tardis, held);
            return Result.SUCCESS;
        }

        if (held.is(AITItems.CORAL_FRAGMENT)) {
            Loyalty loyalty = tardis.loyalty().get(player);
            CachedDirectedGlobalPos currentPos = tardis.travel().position();
            boolean inNether = currentPos != null && currentPos.getDimension().equals(Level.NETHER);
            Loyalty.Type required = inNether ? Loyalty.Type.OWNER : Loyalty.Type.PILOT;

            if (currentPos == null || !tardis.travel().isLanded())
                return Result.FAILURE;

            if (!loyalty.isOf(required)) {
                player.displayClientMessage(Component.translatable(
                                inNether ? "tardis.message.control.telepathic.home_denied_nether"
                                        : "tardis.message.control.telepathic.home_denied"),
                        true);
                return Result.FAILURE;
            }

            tardis.stats().setHome(currentPos);

            player.displayClientMessage(Component.translatable("tardis.message.control.telepathic.home_updated"), true);

            if (!player.isCreative())
                held.shrink(1);

            return Result.SUCCESS;
        }

        if (held.is(Items.NETHER_STAR) && tardis.loyalty().get(player).isOf(Loyalty.Type.PILOT)) {
            tardis.selfDestruct().boom();

            if (!(tardis.selfDestruct().isQueued()))
                return Result.FAILURE;

            if (!player.isCreative())
                held.shrink(1);

            return Result.SUCCESS;
        }

        if (isLiquid(held))
            return spillLiquid(tardis, world, console, player);

        if (LockedDimensionRegistry.tryUnlockDimension(player, held, tardis.asServer()))
            return Result.SUCCESS;

        ItemOpinion opinion = ItemOpinionRegistry.getInstance().get(held.getItem()).orElse(null);
        if (opinion != null && tardis.opinions().contains(opinion) && (player.experienceLevel >= opinion.cost() || player.isCreative())) {
            opinion.apply(tardis.asServer(), player);

            player.serverLevel().playSound(null, console, AITSounds.TARDIS_BLING, SoundSource.AMBIENT, 0.25f, 1f);
            player.serverLevel().sendParticles((opinion.likes()) ? ParticleTypes.HEART : ParticleTypes.ANGRY_VILLAGER, console.getCenter().x(),
                    console.getCenter().y() + 1, console.getCenter().z(), 1, 0f, 1F, 0f, 5.0F);

            return Result.SUCCESS;
        }

        Component text = Component.translatable("tardis.message.control.telepathic.choosing");
        player.displayClientMessage(text, true);

        CachedDirectedGlobalPos globalPos = tardis.travel().position();

        locateStructureOfInterest(player, tardis, globalPos.getWorld(), globalPos.getPos());
        return Result.SUCCESS;
    }

    public static boolean isLiquid(ItemStack held) {
        return (held.is(AITItems.MUG) && DrinkUtil.getDrink(held) != DrinkUtil.EMPTY)
                || held.is(Items.LAVA_BUCKET) || held.is(Items.WATER_BUCKET) || held.is(Items.MILK_BUCKET);
    }

    public static Result spillLiquid(Tardis tardis, ServerLevel world, BlockPos console, @Nullable ServerPlayer player) {
        /*
            This is an example of how to use the travel queue.
            This code enqueues a crash to be performed after dematerialization.
             */

        TravelHandler travel = tardis.travel();
        TravelHandlerBase.State state = travel.getState();

        ActionQueue drinkAction = new ActionQueue();

        drinkAction.thenRun(() -> {
            // This is called after the dematerialization is complete
            travel.speed(travel.maxSpeed().get());
            travel.crash();
            tardis.crash().addRepairTicks(1500);

            world.sendParticles(ParticleTypes.SMALL_FLAME, console.getCenter().x() + 0.5f, console.getCenter().y() + 1.25, console.getCenter().z() + 0.5f,
                    5 * 10, 0, 0, 0, 0.1f * 10);

            world.sendParticles(ParticleTypes.EXPLOSION, console.getCenter().x() + 0.5f, console.getCenter().y() + 1.25, console.getCenter().z() + 0.5f,
                    5 * 10, 0, 0, 0, 0.1f * 10);


            world.playSound(null, console, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
            world.playSound(null, console, AITSounds.SIEGE_ENABLE, SoundSource.BLOCKS, 1.0f, 1.0f);

            if (player != null) {
                TardisCriterions.BRAND_NEW.trigger(player);
            }
        });

        if (state == TravelHandlerBase.State.LANDED) {
            boolean hadAutopilot = travel.autopilot();

            travel.autopilot(true);

            TravelUtil.randomPos(tardis, 100000, 100000, cached -> {
                tardis.travel().destination(cached);
                tardis.removeFuel(0.1d * IncrementManager.increment(tardis) * tardis.travel().instability());
            });

            travel.dematerialize().ifPresent(tr -> {
                // These only run if the dematerialization is successful

                tr.thenRun(drinkAction);

                // This is called just before the dematerialization starts
                tardis.alarm().enable();

                world.sendParticles(ParticleTypes.SMALL_FLAME, console.getCenter().x() + 0.5f, console.getCenter().y() + 1.25, console.getCenter().z() + 0.5f,
                        5 * 10, 0, 0, 0, 0.1f * 10);

                world.sendParticles(ParticleTypes.EXPLOSION, console.getCenter().x() + 0.5f, console.getCenter().y() + 1.25, console.getCenter().z() + 0.5f,
                        5 * 10, 0, 0, 0, 0.1f * 10);

                world.playSound(null, console, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 1.0f, 1.0f);
                world.playSound(null, console, AITSounds.SIEGE_ENABLE, SoundSource.BLOCKS, 1.0f, 1.0f);
            });

            travel.autopilot(hadAutopilot);

            return Result.SUCCESS;
        }

        if (state == TravelHandlerBase.State.FLIGHT) {
            drinkAction.execute();

            return Result.SUCCESS;
        }

        return Result.FAILURE;
    }

    public static void locateStructureOfInterest(ServerPlayer player, Tardis tardis, ServerLevel world,
                                                 BlockPos source) {
        if (world.dimension() == Level.NETHER) {
            getStructureViaChunkGen(player, tardis, world, source, RADIUS, BuiltinStructures.FORTRESS);
        } else if (world.dimension() == Level.END) {
            getStructureViaChunkGen(player, tardis, world, source, RADIUS, BuiltinStructures.END_CITY);
        } else if (world.dimension() == Level.OVERWORLD) {
            getStructureViaWorld(player, tardis, world, source, RADIUS, StructureTags.VILLAGE);
        } else {
            Registry<Structure> registry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);
            // get a list of all the registry entries
            List<Holder<Structure>> structures = new ArrayList<>();

            for (int i = 0; i < registry.size() - 1; i++) {
                structures.add(registry.getHolder(i).orElseThrow());
            }

            locateWithChunkGenAsync(player, tardis, HolderSet.direct(structures), world, source, RADIUS);
        }
    }

    public static void getStructureViaChunkGen(ServerPlayer player, Tardis tardis, ServerLevel world,
                                               BlockPos pos, int radius, ResourceKey<Structure> key) {
        Registry<Structure> registry = world.registryAccess().registryOrThrow(Registries.STRUCTURE);

        if (registry.getHolder(key).isPresent())
            locateWithChunkGenAsync(player, tardis, HolderSet.direct(registry.getHolder(key).get()), world, pos,
                    radius);
    }

    public static void getStructureViaWorld(ServerPlayer player, Tardis tardis, ServerLevel world, BlockPos pos,
                                            int radius, TagKey<Structure> key) {
        locateWithWorldAsync(player, tardis, key, world, pos, radius);
    }

    @Override
    public boolean requiresPower() {
        return false;
    }

    @Override
    public long getDelayLength(Tardis tardis) {
        return 120;
    }

    @Override
    public SoundEvent getFallbackSound() {
        return AITSounds.TELEPATHIC_CIRCUITS;
    }

    public static void locateWithChunkGenAsync(ServerPlayer player, Tardis tardis,
                                               HolderSet<Structure> structureList, ServerLevel world, BlockPos center, int radius) {
        AsyncLocatorUtil.locate(world, structureList, center, radius, false).thenOnServerThread(pos -> {
            BlockPos newPos = pos != null ? pos.getFirst() : null;
            if (newPos != null) {
                tardis.travel().forceDestination(cached -> cached.pos(newPos.atY(75)));
                tardis.removeFuel(500 * tardis.travel().instability());
                player.displayClientMessage(Component.translatable("tardis.message.control.telepathic.success"), true);
            } else {
                player.displayClientMessage(Component.translatable("tardis.message.control.telepathic.failed"), true);
            }
        });
    }

    public static void locateWithWorldAsync(ServerPlayer player, Tardis tardis, TagKey<Structure> structureTagKey,
                                            ServerLevel world, BlockPos center, int radius) {
        AsyncLocatorUtil.locate(world, structureTagKey, center, radius, false).thenOnServerThread(pos -> {
            if (pos != null) {
                tardis.travel().forceDestination(cached -> cached.pos(pos.atY(75)));
                tardis.removeFuel(500 * tardis.travel().instability());
                player.displayClientMessage(Component.translatable("tardis.message.control.telepathic.success"), true);
            } else {
                player.displayClientMessage(Component.translatable("tardis.message.control.telepathic.failed"), true);
            }
        });
    }

}
