package com.sammy.malum.common.block.item_storage;

import com.sammy.malum.core.systems.multiblock.ComponentBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;

import java.util.function.Supplier;

public class PlinthComponentBlock extends ComponentBlock {
    private final Supplier<Item> cloneStack;
    public PlinthComponentBlock(Properties properties, Supplier<Item> cloneStack) {
        super(properties);
        this.cloneStack = cloneStack;
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
        return cloneStack.get().getDefaultInstance();
    }
}