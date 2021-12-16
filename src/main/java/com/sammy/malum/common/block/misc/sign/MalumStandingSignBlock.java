package com.sammy.malum.common.block.misc.sign;

import com.sammy.malum.common.tile.MalumSignTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.Level.IBlockReader;

import javax.annotation.Nullable;

import net.minecraft.block.AbstractBlock.Properties;

public class MalumStandingSignBlock extends StandingSignBlock
{
    public MalumStandingSignBlock(Properties properties, WoodType type)
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
    public BlockEntity createTileEntity(BlockState state, IBlockReader Level)
    {
        return new MalumSignTileEntity();
    }
}
