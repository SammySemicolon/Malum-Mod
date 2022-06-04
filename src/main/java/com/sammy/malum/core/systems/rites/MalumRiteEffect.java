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
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public abstract class MalumRiteEffect {
    public static int BASE_RADIUS = 2;
    public static int BASE_TICK_RATE = 20;

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


    public <T extends LivingEntity> ArrayList<T> getNearbyEntities(TotemBaseBlockEntity totemBase, Class<T> clazz) {
        return totemBase.getLevel().getEntitiesOfClass(clazz, new AABB(getRiteEffectCenter(totemBase)).inflate(getRiteEffectRadius())).stream().filter(getEntityPredicate()).collect(Collectors.toCollection(ArrayList::new));
    }

    public <T extends LivingEntity> Predicate<T> getEntityPredicate() {
        return e -> true;
    }

    public ArrayList<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        return BlockHelper.getBlocks(getRiteEffectCenter(totemBase), getRiteEffectRadius(), p -> canAffectBlock(clazz, totemBase.getLevel().getBlockState(p)));
    }

    public ArrayList<BlockPos> getNearbyBlocks(TotemBaseBlockEntity totemBase, Class<?> clazz, int height) {
        int horizontal = getRiteEffectRadius();
        return BlockHelper.getBlocks(getRiteEffectCenter(totemBase), horizontal, height, horizontal, p -> canAffectBlock(clazz, totemBase.getLevel().getBlockState(p)));
    }

    public ArrayList<BlockPos> getBlocksUnderBase(TotemBaseBlockEntity totemBase, Class<?> clazz) {
        int horizontal = getRiteEffectRadius();
        return BlockHelper.getPlaneOfBlocks(getRiteEffectCenter(totemBase).below(), horizontal, p -> canAffectBlock(clazz, totemBase.getLevel().getBlockState(p)));
    }

    public boolean canAffectBlock(Class<?> clazz, BlockState state) {
        return clazz.isInstance(state.getBlock()) && canAffectBlock(state);
    }

    public boolean canAffectBlock(BlockState state) {
        return !state.is(BlockTagRegistry.RITE_IMMUNE);
    }
}