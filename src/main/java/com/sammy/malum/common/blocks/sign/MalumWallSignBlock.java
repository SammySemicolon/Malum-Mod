package com.sammy.malum.common.blocks.sign;

import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class MalumWallSignBlock extends WallSignBlock
{
    public MalumWallSignBlock(Properties properties)
    {
        super(properties, null);
    }
    
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn)
    {
        return new MalumSignTileEntity();
    }
}
