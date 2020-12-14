package com.sammy.malum.common.blocks.taint;

import net.minecraft.block.DoublePlantBlock;

public class TaintedDoublePlantBlock extends DoublePlantBlock implements ITaintSpreader
{
    public TaintedDoublePlantBlock(Properties properties)
    {
        super(properties);
    }
}
