package com.sammy.malum.common.blocks.lighting;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import java.awt.*;

public class FlaredLanternBlock extends LanternBlock implements IColor
{
    Color color;
    public FlaredLanternBlock(Properties properties, Color color)
    {
        super(properties);
        this.color = color;
    }
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new BasicLightingTileEntity();
    }
    
    @Override
    public Color getColor()
    {
        return color;
    }
}
