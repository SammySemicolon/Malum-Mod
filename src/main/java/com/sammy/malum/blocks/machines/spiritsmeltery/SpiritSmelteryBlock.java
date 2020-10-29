package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.blocks.utility.multiblock.MultiblockBlock;
import com.sammy.malum.blocks.utility.multiblock.MultiblockStructure;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class SpiritSmelteryBlock extends MultiblockBlock
{
    //region structure
    public static final MultiblockStructure structure = new MultiblockStructure(
            new BlockPos(0,0,1),
            new BlockPos(1,0,1),
            new BlockPos(1,0,0),
            new BlockPos(1,0,-1),
            new BlockPos(0,0,-1),
            new BlockPos(-1,0,-1),
            new BlockPos(-1,0,0),
            new BlockPos(-1,0,1),
        
            new BlockPos(0,1,0),
        
            new BlockPos(0,1,1),
            new BlockPos(1,1,1),
            new BlockPos(1,1,0),
            new BlockPos(1,1,-1),
            new BlockPos(0,1,-1),
            new BlockPos(-1,1,-1),
            new BlockPos(-1,1,0),
            new BlockPos(-1,1,1),
            
            new BlockPos(0,2,0),
        
            new BlockPos(0,2,1),
            new BlockPos(1,2,1),
            new BlockPos(1,2,0),
            new BlockPos(1,2,-1),
            new BlockPos(0,2,-1),
            new BlockPos(-1,2,-1),
            new BlockPos(-1,2,0),
            new BlockPos(-1,2,1)
    );
    //endregion
    
    public SpiritSmelteryBlock(Properties properties)
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
        return new SpiritSmelteryTileEntity();
    }
}