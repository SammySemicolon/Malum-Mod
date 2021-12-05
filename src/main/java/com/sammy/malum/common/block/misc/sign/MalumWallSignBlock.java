package com.sammy.malum.common.block.misc.sign;

import com.sammy.malum.common.tile.MalumSignTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class MalumWallSignBlock extends WallSignBlock
{
    public MalumWallSignBlock(Properties properties, WoodType type)
    {
        super(properties, type);
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new MalumSignTileEntity();
    }
}
