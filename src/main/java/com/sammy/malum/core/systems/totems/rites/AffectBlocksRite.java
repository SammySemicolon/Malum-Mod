package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.modcontent.MalumTransfusions;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class AffectBlocksRite extends MalumRite
{
    public AffectBlocksRite(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
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
    public int yOffset()
    {
        return 0;
    }
    public boolean doCheckpoints()
    {
        return false;
    }
    public int lastX = -range();
    public int lastZ = -range();
    @Override
    public void effect(BlockPos pos, World world)
    {
        int startingX = -range();
        if (doCheckpoints())
        {
            startingX = lastX;
        }
        int startingZ = -range();
        if (doCheckpoints())
        {
            startingZ = lastZ;
        }
        for (int x = startingX; x <= range(); x++)
        {
            for (int z = startingZ; z <= range(); z++)
            {
                BlockPos blockPos = pos.add(x, yOffset(), z);
                BlockState state = world.getBlockState(blockPos);
                if (!state.getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get()))
                {
                    boolean success = effect(blockPos, state, world);
                    if (success)
                    {
                        lastX = x;
                        lastZ = z;
                        return;
                    }
                }
            }
        }
    }
}
