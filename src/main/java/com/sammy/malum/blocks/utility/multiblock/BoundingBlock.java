package com.sammy.malum.blocks.utility.multiblock;


import com.sammy.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

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
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new BoundingBlockTileEntity();
    }
}