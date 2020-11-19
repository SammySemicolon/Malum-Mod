package com.sammy.malum.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;

public class SolarOreBlock extends Block
{
    @Override
    public SoundType getSoundType(BlockState state)
    {
        return super.getSoundType(state);
    }
    
    public SolarOreBlock(Properties properties)
    {
        super(properties);
    }
}
