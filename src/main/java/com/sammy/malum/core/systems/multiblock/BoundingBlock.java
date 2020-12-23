package com.sammy.malum.core.systems.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BoundingBlock extends Block
{
    public BoundingBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new BoundingBlockTileEntity();
    }
    
    public BlockState stateForPlacement(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
    {
        return getDefaultState();
    }
}
