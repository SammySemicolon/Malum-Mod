package com.sammy.malum.common.blocks.wildfarmland;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

import java.util.Random;

public class WildFarmlandBlock extends FarmlandBlock
{
    public WildFarmlandBlock(Properties properties)
    {
        super(properties);
    }
    
    
    @Override
    public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance)
    {
    
    }
    
    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random)
    {
        BlockState upState = worldIn.getBlockState(pos.up());
        if (upState.getBlock() instanceof IGrowable)
        {
            IGrowable growable = (IGrowable) upState.getBlock();
            if (growable.canGrow(worldIn,pos.up(), upState, false))
            {
                if (worldIn.rand.nextFloat() < 0.05f)
                {
                    growable.grow(worldIn, worldIn.rand, pos.up(),upState);
                    worldIn.playEvent(2005, pos.up(), 0);
                }
            }
        }
    }
    
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
    
    }
    
    @Override
    public boolean isFertile(BlockState state, IBlockReader world, BlockPos pos)
    {
        return true;
    }
    
    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable)
    {
        if (plantable instanceof CropsBlock)
        {
            return true;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }
}
