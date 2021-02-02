package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AffectBlocksRite extends MalumRite
{
    public AffectBlocksRite(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    public boolean effect(BlockPos pos, BlockState state, World world)
    {
        return false;
    }
    
    @Override
    public int cooldown()
    {
        return 20;
    }
    public int maxEffects()
    {
        return 0;
    }
    public boolean doVertical()
    {
        return false;
    }
    public int yOffset()
    {
        return 0;
    }
    @Override
    public void effect(BlockPos pos, World world)
    {
        if (doVertical())
        {
            for (int x = -range(); x <= range(); x++)
            {
                for (int y = -range(); y <= range(); y++)
                {
                    for (int z = -range(); z <= range(); z++)
                    {
                        BlockPos blockPos = pos.add(x, yOffset() + y, z);
                        BlockState state = world.getBlockState(blockPos);
                        if (!state.getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get()))
                        {
                            boolean success = effect(blockPos, state, world);
                            if (success)
                            {
                                return;
                            }
                        }
                    }
                }
            }
        }
        else
        {
            for (int x = -range(); x <= range(); x++)
            {
                for (int z = -range(); z <= range(); z++)
                {
                    BlockPos blockPos = pos.add(x, yOffset(), z);
                    BlockState state = world.getBlockState(blockPos);
                    if (!state.getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get()))
                    {
                        boolean success = effect(blockPos, state, world);
                        if (success)
                        {
                            return;
                        }
                    }
                }
            }
        }
    }
}
