package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class RiteOfGrowth extends AffectBlocksRite
{
    public RiteOfGrowth(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public int cooldown()
    {
        return 40;
    }
    
    @Override
    public int range()
    {
        return 4;
    }
    
    @Override
    public boolean effect(BlockPos pos, BlockState state, World world)
    {
        if (MalumHelper.areWeOnServer(world))
        {
            if (state.getBlock() instanceof IGrowable)
            {
                if (world.rand.nextFloat() < 0.025f)
                {
                    state.randomTick((ServerWorld) world, pos, world.rand);
                }
            }
        }
        return false;
    }
}