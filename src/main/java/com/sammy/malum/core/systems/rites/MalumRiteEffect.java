package com.sammy.malum.core.systems.rites;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
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

    public <T extends Entity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz) {
        return new ArrayList<>(totemBase.getLevel().getEntitiesOfClass(clazz, new AABB(getRiteEffectCenter(totemBase)).inflate(getRiteEffectRadius()))).stream();
    }

    public <T extends Entity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz, Predicate<T> predicate) {
        return totemBase.getLevel().getEntitiesOfClass(clazz, new AABB(getRiteEffectCenter(totemBase)).inflate(getRiteEffectRadius())).stream().filter(predicate);
    }

    public Stream<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        Set<Block> blockFilters = getBlockFilters(totemBase);
        return BlockHelper.getBlocksStream(getRiteEffectCenter(totemBase), getRiteEffectRadius(), p -> canAffectBlock(totemBase, blockFilters, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public Stream<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz, int height) {
        Set<Block> blockFilters = getBlockFilters(totemBase);
        int horizontal = getRiteEffectRadius();
        return BlockHelper.getBlocksStream(getRiteEffectCenter(totemBase), horizontal, height, horizontal, p -> canAffectBlock(totemBase, blockFilters, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public Stream<BlockPos> getBlocksUnderBase(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        Set<Block> blockFilters = getBlockFilters(totemBase);
        int horizontal = getRiteEffectRadius();
        return BlockHelper.getPlaneOfBlocksStream(getRiteEffectCenter(totemBase).below(), horizontal, p -> canAffectBlock(totemBase, blockFilters, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public Set<Block> getBlockFilters(TotemBaseBlockEntity totemBase) {
        return totemBase.cachedFilterInstances.stream().map(e -> e.inventory.getStackInSlot(0)).filter(e -> e.getItem() instanceof BlockItem).map(e -> ((BlockItem) e.getItem()).getBlock()).collect(Collectors.toCollection(HashSet::new));
    }

    public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Set<Block> filters, Class<?> clazz, BlockState state, BlockPos pos) {
        return clazz.isInstance(state.getBlock()) && canAffectBlock(totemBase, filters, state, pos);
    }

    public boolean canAffectBlock(TotemBaseBlockEntity totemBase, Set<Block> filters, BlockState state, BlockPos pos) {
        return (filters.isEmpty() || filters.contains(state.getBlock())) && !state.is(BlockTagRegistry.RITE_IMMUNE);
    }
}
