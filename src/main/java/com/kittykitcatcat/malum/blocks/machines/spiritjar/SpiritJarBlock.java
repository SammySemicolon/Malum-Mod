package com.kittykitcatcat.malum.blocks.machines.spiritjar;

import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SpiritJarBlock extends SpiritStoringBlock
{
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public int capacity()
    {
        return 20;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritJarTileEntity();
    }

}