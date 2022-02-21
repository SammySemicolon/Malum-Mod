package com.sammy.malum.core.systems.block;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("ALL")
public class SimpleBlockProperties extends BlockBehaviour.Properties {
    public boolean needsPickaxe;
    public boolean needsAxe;
    public boolean needsShovel;
    public boolean needsHoe;

    public boolean needsStone;
    public boolean needsIron;
    public boolean needsDiamond;

    public boolean cutout;

    public boolean ignoreLootDatagen;

    public enum StateType {
        predefined, automatic, customBlock, glowingBlock
    }
    public StateType type = StateType.automatic;

    public SimpleBlockProperties(Material p_60905_, MaterialColor p_60906_) {
        super(p_60905_, (p_60952_) -> p_60906_);
    }
    public SimpleBlockProperties customLoot()
    {
        ignoreLootDatagen = true;
        return this;
    }
    public SimpleBlockProperties blockStateDefinition(StateType type)
    {
        this.type = type;
        return this;
    }
    public SimpleBlockProperties needsPickaxe()
    {
        needsPickaxe = true;
        return this;
    }
    public SimpleBlockProperties needsAxe()
    {
        needsAxe = true;
        return this;
    }
    public SimpleBlockProperties needsShovel()
    {
        needsShovel = true;
        return this;
    }
    public SimpleBlockProperties needsHoe()
    {
        needsHoe = true;
        return this;
    }

    public SimpleBlockProperties needsStone()
    {
        needsStone = true;
        return this;
    }
    public SimpleBlockProperties needsIron()
    {
        needsIron = true;
        return this;
    }
    public SimpleBlockProperties needsDiamond()
    {
        needsDiamond = true;
        return this;
    }

    public SimpleBlockProperties isCutout()
    {
        cutout = true;
        return this;
    }

    @Override
    public SimpleBlockProperties noCollission() {
        return (SimpleBlockProperties)super.noCollission();
    }

    @Override
    public SimpleBlockProperties noOcclusion() {
        return (SimpleBlockProperties)super.noOcclusion();
    }

    @Override
    public SimpleBlockProperties friction(float p_60912_) {
        return (SimpleBlockProperties)super.friction(p_60912_);
    }

    @Override
    public SimpleBlockProperties speedFactor(float p_60957_) {
        return (SimpleBlockProperties)super.speedFactor(p_60957_);
    }

    @Override
    public SimpleBlockProperties jumpFactor(float p_60968_) {
        return (SimpleBlockProperties)super.jumpFactor(p_60968_);
    }

    @Override
    public SimpleBlockProperties sound(SoundType p_60919_) {
        return (SimpleBlockProperties)super.sound(p_60919_);
    }

    @Override
    public SimpleBlockProperties lightLevel(ToIntFunction<BlockState> p_60954_) {
        return (SimpleBlockProperties)super.lightLevel(p_60954_);
    }

    @Override
    public SimpleBlockProperties strength(float p_60914_, float p_60915_) {
        return (SimpleBlockProperties)super.strength(p_60914_, p_60915_);
    }

    @Override
    public SimpleBlockProperties instabreak() {
        return (SimpleBlockProperties)super.instabreak();
    }

    @Override
    public SimpleBlockProperties strength(float p_60979_) {
        return (SimpleBlockProperties)super.strength(p_60979_);
    }

    @Override
    public SimpleBlockProperties randomTicks() {
        return (SimpleBlockProperties)super.randomTicks();
    }

    @Override
    public SimpleBlockProperties dynamicShape() {
        return (SimpleBlockProperties)super.dynamicShape();
    }

    @Override
    public SimpleBlockProperties noDrops() {
        return (SimpleBlockProperties)super.noDrops();
    }

    @Override
    public SimpleBlockProperties dropsLike(Block p_60917_) {
        return (SimpleBlockProperties)super.dropsLike(p_60917_);
    }

    @Override
    public SimpleBlockProperties lootFrom(Supplier<? extends Block> blockIn) {
        customLoot();
        return (SimpleBlockProperties) super.lootFrom(blockIn);
    }

    @Override
    public SimpleBlockProperties air() {
        return (SimpleBlockProperties)super.air();
    }

    @Override
    public SimpleBlockProperties isValidSpawn(BlockBehaviour.StateArgumentPredicate<EntityType<?>> p_60923_) {
        return (SimpleBlockProperties)super.isValidSpawn(p_60923_);
    }

    @Override
    public SimpleBlockProperties isRedstoneConductor(BlockBehaviour.StatePredicate p_60925_) {
        return (SimpleBlockProperties)super.isRedstoneConductor(p_60925_);
    }

    @Override
    public SimpleBlockProperties isSuffocating(BlockBehaviour.StatePredicate p_60961_) {
        return (SimpleBlockProperties)super.isSuffocating(p_60961_);
    }

    @Override
    public SimpleBlockProperties isViewBlocking(BlockBehaviour.StatePredicate p_60972_) {
        return (SimpleBlockProperties)super.isViewBlocking(p_60972_);
    }

    @Override
    public SimpleBlockProperties hasPostProcess(BlockBehaviour.StatePredicate p_60983_) {
        return (SimpleBlockProperties)super.hasPostProcess(p_60983_);
    }

    @Override
    public SimpleBlockProperties emissiveRendering(BlockBehaviour.StatePredicate p_60992_) {
        return (SimpleBlockProperties)super.emissiveRendering(p_60992_);
    }

    @Override
    public SimpleBlockProperties requiresCorrectToolForDrops() {
        return (SimpleBlockProperties)super.requiresCorrectToolForDrops();
    }

    @Override
    public SimpleBlockProperties color(MaterialColor p_155950_) {
        return (SimpleBlockProperties)super.color(p_155950_);
    }

    @Override
    public SimpleBlockProperties destroyTime(float p_155955_) {
        return (SimpleBlockProperties)super.destroyTime(p_155955_);
    }

    @Override
    public SimpleBlockProperties explosionResistance(float p_155957_) {
        return (SimpleBlockProperties)super.explosionResistance(p_155957_);
    }
}
