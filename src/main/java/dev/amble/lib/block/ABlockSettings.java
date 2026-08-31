package dev.amble.lib.block;

import java.util.function.Function;
import java.util.function.ToIntFunction;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.ApiStatus;

@SuppressWarnings("deprecation")
public class ABlockSettings extends Properties {

    public static ABlockSettings of() {
        return new ABlockSettings();
    }

    private Item.Properties settings;
    private Property<?>[] properties;

    public ABlockSettings itemSettings(Item.Properties settings) {
        this.settings = settings;
        return this;
    }

    @ApiStatus.Internal
    public ABlockSettings properties(Property<?>[] properties) {
        this.properties = properties;
        return this;
    }

    @Override
    public ABlockSettings noCollission() {
        return (ABlockSettings) super.noCollission();
    }

    @Override
    public ABlockSettings noOcclusion() {
        return (ABlockSettings) super.noOcclusion();
    }

    @Override
    public ABlockSettings friction(float value) {
        return (ABlockSettings) super.friction(value);
    }

    @Override
    public ABlockSettings speedFactor(float velocityMultiplier) {
        return (ABlockSettings) super.speedFactor(velocityMultiplier);
    }

    @Override
    public ABlockSettings jumpFactor(float jumpVelocityMultiplier) {
        return (ABlockSettings) super.jumpFactor(jumpVelocityMultiplier);
    }

    @Override
    public ABlockSettings sound(SoundType group) {
        return (ABlockSettings) super.sound(group);
    }

    @Override
    public ABlockSettings lightLevel(ToIntFunction<BlockState> levelFunction) {
        return (ABlockSettings) super.lightLevel(levelFunction);
    }

    @Override
    public ABlockSettings strength(float hardness, float resistance) {
        return (ABlockSettings) super.strength(hardness, resistance);
    }

    @Override
    public ABlockSettings instabreak() {
        return (ABlockSettings) super.instabreak();
    }

    @Override
    public ABlockSettings strength(float strength) {
        return (ABlockSettings) super.strength(strength);
    }

    @Override
    public ABlockSettings randomTicks() {
        return (ABlockSettings) super.randomTicks();
    }

    @Override
    public ABlockSettings dynamicShape() {
        return (ABlockSettings) super.dynamicShape();
    }

    @Override
    public ABlockSettings noLootTable() {
        return (ABlockSettings) super.noLootTable();
    }

    @Override
    public ABlockSettings dropsLike(Block block) {
        return (ABlockSettings) super.dropsLike(block);
    }

    @Override
    public ABlockSettings air() {
        return (ABlockSettings) super.air();
    }

    @Override
    public ABlockSettings isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> predicate) {
        return (ABlockSettings) super.isValidSpawn(predicate);
    }

    @Override
    public ABlockSettings isRedstoneConductor(BlockBehaviour.StatePredicate predicate) {
        return (ABlockSettings) super.isRedstoneConductor(predicate);
    }

    @Override
    public ABlockSettings isSuffocating(BlockBehaviour.StatePredicate predicate) {
        return (ABlockSettings) super.isSuffocating(predicate);
    }

    @Override
    public ABlockSettings isViewBlocking(BlockBehaviour.StatePredicate predicate) {
        return (ABlockSettings) super.isViewBlocking(predicate);
    }

    @Override
    public ABlockSettings hasPostProcess(BlockBehaviour.StatePredicate predicate) {
        return (ABlockSettings) super.hasPostProcess(predicate);
    }

    @Override
    public ABlockSettings emissiveRendering(BlockBehaviour.StatePredicate predicate) {
        return (ABlockSettings) super.emissiveRendering(predicate);
    }

    @Override
    public ABlockSettings requiresCorrectToolForDrops() {
        return (ABlockSettings) super.requiresCorrectToolForDrops();
    }

    @Override
    public ABlockSettings mapColor(MapColor color) {
        return (ABlockSettings) super.mapColor(color);
    }

    @Override
    public ABlockSettings destroyTime(float hardness) {
        return (ABlockSettings) super.destroyTime(hardness);
    }

    @Override
    public ABlockSettings explosionResistance(float resistance) {
        return (ABlockSettings) super.explosionResistance(resistance);
    }

    @Override
    public ABlockSettings offsetType(BlockBehaviour.OffsetType offsetType) {
        return (ABlockSettings) super.offsetType(offsetType);
    }


    @Override
    public ABlockSettings requiredFeatures(FeatureFlag... features) {
        return (ABlockSettings) super.requiredFeatures(features);
    }

    @Override
    public ABlockSettings mapColor(Function<BlockState, MapColor> mapColorProvider) {
        return (ABlockSettings) super.mapColor(mapColorProvider);
    }

    @Override
    public ABlockSettings ignitedByLava() {
        return (ABlockSettings) super.ignitedByLava();
    }

    @Override
    public ABlockSettings liquid() {
        return (ABlockSettings) super.liquid();
    }

    @Override
    public ABlockSettings forceSolidOn() {
        return (ABlockSettings) super.forceSolidOn();
    }

    @Override
    public ABlockSettings forceSolidOff() {
        return (ABlockSettings) super.forceSolidOff();
    }

    @Override
    public ABlockSettings pushReaction(PushReaction pistonBehavior) {
        return (ABlockSettings) super.pushReaction(pistonBehavior);
    }

    @Override
    public ABlockSettings instrument(NoteBlockInstrument instrument) {
        return (ABlockSettings) super.instrument(instrument);
    }

    @Override
    public ABlockSettings replaceable() {
        return (ABlockSettings) super.replaceable();
    }

    @Override
    public ABlockSettings lightLevel(int lightLevel) {
        return (ABlockSettings) super.lightLevel(lightLevel);
    }





    @Override
    public ABlockSettings mapColor(DyeColor color) {
        return (ABlockSettings) super.mapColor(color);
    }


    public Item.Properties itemSettings() {
        return settings;
    }

    public Property<?>[] properties() {
        return properties;
    }
}
