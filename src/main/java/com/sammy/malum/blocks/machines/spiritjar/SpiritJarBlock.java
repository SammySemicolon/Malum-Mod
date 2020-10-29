package com.sammy.malum.blocks.machines.spiritjar;

import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class SpiritJarBlock extends SpiritStoringBlock
{
    //0 is normal, just block in da world
    //1 is for the spirit
    //2 is the connected state
    public SpiritJarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE, 0));
    }
    
    
    @Override
    public int capacity()
    {
        return 20;
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