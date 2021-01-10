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
        if (state.getBlock() instanceof CropsBlock)
        {
            return true;
        }
        return super.canSustainPlant(state, world, pos, facing, plantable);
    }
}
