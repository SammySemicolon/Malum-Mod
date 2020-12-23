package com.sammy.malum.common.blocks;

import net.minecraft.block.SoulSandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WitherSandBlock extends SoulSandBlock
{
    public WitherSandBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (entityIn instanceof LivingEntity)
        {
            if (((LivingEntity) entityIn).getHealth() > 1)
            {
                entityIn.attackEntityFrom(DamageSource.WITHER, 1);
            }
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
