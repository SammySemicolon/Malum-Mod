package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.blockentity.totem.TotemBaseBlockEntity;
import com.sammy.malum.core.setup.content.block.BlockTagRegistry;
import com.sammy.ortus.helpers.BlockHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.stream.Stream;

@SuppressWarnings("ConstantConditions")
public abstract class MalumRiteEffect {
    public static final int BASE_RADIUS = 2;
    public static final int BASE_TICK_RATE = 20;

    protected MalumRiteEffect() {
    }

    public abstract void riteEffect(TotemBaseBlockEntity totemBase);

    public boolean isOneAndDone() {
        return false;
    }

    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        return totemBase.getBlockPos();
    }

    public int getRiteEffectRadius() {
        return BASE_RADIUS;
    }

    public int getRiteEffectTickRate() {
        return BASE_TICK_RATE;
    }


    public <T extends LivingEntity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz) {
        return new ArrayList<>(totemBase.getLevel().getEntitiesOfClass(clazz, new AABB(getRiteEffectCenter(totemBase)).inflate(getRiteEffectRadius()))).stream();
    }
    public <T extends LivingEntity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz, Predicate<T> predicate) {
        return totemBase.getLevel().getEntitiesOfClass(clazz, new AABB(getRiteEffectCenter(totemBase)).inflate(getRiteEffectRadius())).stream().filter(predicate);
    }

    public Stream<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        return BlockHelper.getBlocksStream(getRiteEffectCenter(totemBase), getRiteEffectRadius(), p -> canAffectBlock(totemBase, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public Stream<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz, int height) {
        int horizontal = getRiteEffectRadius();
        return BlockHelper.getBlocksStream(getRiteEffectCenter(totemBase), horizontal, height, horizontal, p -> canAffectBlock(totemBase, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public Stream<BlockPos> getBlocksUnderBase(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        int horizontal = getRiteEffectRadius();
        return BlockHelper.getPlaneOfBlocksStream(getRiteEffectCenter(totemBase).below(), horizontal, p -> canAffectBlock(totemBase, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Class<?> clazz, BlockState state, BlockPos pos) {
        return clazz.isInstance(state.getBlock()) && canAffectBlock(totemBase, state, pos);
    }

    public boolean canAffectBlock(TotemBaseBlockEntity totemBase, BlockState state, BlockPos pos) {
        return !state.is(BlockTagRegistry.RITE_IMMUNE);
    }
}