package com.sammy.malum.common.blocks.lighting;

import net.minecraft.block.BlockState;
import net.minecraft.block.WallTorchBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public class WallEtherTorchBlock extends WallTorchBlock implements IColor
{
    public Color color;
    public WallEtherTorchBlock(Properties properties, Color color)
    {
        super(properties,null);
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
    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
    
    }
}