package com.kittykitcatcat.malum.blocks.machines.spiritjar;

import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

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