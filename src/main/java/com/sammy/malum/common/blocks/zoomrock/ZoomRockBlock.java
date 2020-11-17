package com.sammy.malum.common.blocks.zoomrock;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ZoomRockBlock extends Block
{
    public ZoomRockBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (entityIn instanceof PlayerEntity)
        {
            ((PlayerEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SPEED, 10, 3));
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }
}
