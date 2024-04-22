package com.sammy.malum.common.spiritrite;

import com.sammy.malum.common.block.curiosities.totem.TotemBaseBlockEntity;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import team.lodestar.lodestone.helpers.BlockHelper;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("ConstantConditions")
public abstract class TotemicRiteEffect {

    public enum MalumRiteEffectCategory {
        AURA(40, 8),
        LIVING_ENTITY_EFFECT(40, 4),
        DIRECTIONAL_BLOCK_EFFECT(160, 2),
        RADIAL_BLOCK_EFFECT(80, 5),
        ONE_TIME_EFFECT(0, 0);

        private final int tickRate;
        private final int range;
        MalumRiteEffectCategory(int tickRate, int range) {
            this.tickRate = tickRate;
            this.range = range;
        }

        public String getTranslationKey() {
            return "malum.gui.rite.category." + name().toLowerCase(Locale.ROOT);
        }
    }

    public final MalumRiteEffectCategory category;

    protected TotemicRiteEffect(MalumRiteEffectCategory category) {
        this.category = category;
    }

    public abstract void doRiteEffect(TotemBaseBlockEntity totemBase);

    public BlockPos getRiteEffectCenter(TotemBaseBlockEntity totemBase) {
        return totemBase.getBlockPos();
    }

    public String getRiteCoverageDescriptor() {
        int coverage = getRiteEffectHorizontalRadius();
        if (coverage > 1) {
            coverage = coverage * 2 + 1;
        }
        int yCoverage = getRiteEffectVerticalRadius();
        if (yCoverage > 1) {
            yCoverage = yCoverage * 2 + 1;
        }
        return coverage + "x" + yCoverage + "x" + coverage;
    }

    public int getRiteEffectHorizontalRadius() {
        return category.range;
    }

    public int getRiteEffectVerticalRadius() {
        return category.range;
    }

    public int getRiteEffectTickRate() {
        return category.tickRate;
    }

    public <T extends Entity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz) {
        return getNearbyEntities(totemBase, clazz, e -> true);
    }

    public <T extends Entity> Stream<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz, Predicate<T> predicate) {
        final int horizontal = getRiteEffectHorizontalRadius();
        final int vertical = getRiteEffectVerticalRadius();
        return totemBase.getLevel().getEntitiesOfClass(clazz, new AABB(getRiteEffectCenter(totemBase)).inflate(horizontal, vertical, horizontal)).stream().filter(predicate);
    }

    public Stream<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        Set<Block> blockFilters = getBlockFilters(totemBase);
        final int horizontal = getRiteEffectHorizontalRadius();
        final int vertical = getRiteEffectVerticalRadius();
        return BlockHelper.getBlocksStream(getRiteEffectCenter(totemBase), horizontal, vertical, horizontal, p -> canAffectBlock(totemBase, blockFilters, clazz, totemBase.getLevel().getBlockState(p), p));
    }

    public Stream<BlockPos> getBlocksAhead(TotemBaseBlockEntity totemBase) {
        Set<Block> blockFilters = getBlockFilters(totemBase);
        int horizontal = getRiteEffectHorizontalRadius();
        return BlockHelper.getPlaneOfBlocksStream(getRiteEffectCenter(totemBase).below(), horizontal, p -> canAffectBlock(totemBase, blockFilters, totemBase.getLevel().getBlockState(p), p));
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
