package com.sammy.malum.common.blocks.lighting;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.TorchBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.awt.*;
import java.util.Random;

public class EtherBrazierBlock extends LanternBlock implements IColor
{
    public Color color;
    public EtherBrazierBlock(Properties properties, Color color)
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