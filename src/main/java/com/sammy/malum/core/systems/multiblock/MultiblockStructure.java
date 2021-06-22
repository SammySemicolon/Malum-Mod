package com.sammy.malum.core.systems.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiblockStructure
{
    public MultiblockStructure()
    {
    }

    public BlockState getBoundingBlockState(BlockPos sourcePos, BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state)
    {
        return Blocks.AIR.getDefaultState();
    }

    public List<BlockPos> getOccupiedPositions(BlockPos sourcePos, World world, PlayerEntity player, ItemStack stack, BlockState state)
    {
        return null;
    }
}