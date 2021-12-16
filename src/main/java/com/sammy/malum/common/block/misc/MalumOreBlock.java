package com.sammy.malum.common.block.misc;

import net.minecraft.block.OreBlock;
import net.minecraft.util.math.MathHelper;

import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;

public class MalumOreBlock extends OreBlock
{
    public final int minExperience;
    public final int maxExperience;

    public MalumOreBlock(Properties properties, int minExperience, int maxExperience)
    {
        super(properties);
        this.minExperience = minExperience;
        this.maxExperience = maxExperience;
    }

    @Override
    protected int xpOnDrop(Random rand)
    {
        return MathHelper.nextInt(rand, minExperience, maxExperience);
    }
}