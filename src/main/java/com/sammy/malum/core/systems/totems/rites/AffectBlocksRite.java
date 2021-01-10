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
    
    public void effect(BlockPos pos, BlockState state, World world)
    {
    
    }
    
    @Override
    public int cooldown()
    {
        return 20;
    }
    public int yOffset()
    {
        return 0;
    }
    @Override
    public void effect(BlockPos pos, World world)
    {
        for (int x = -range(); x <= range(); x++)
        {
            for (int z = -range(); z <= range(); z++)
            {
                BlockPos blockPos = pos.add(x, yOffset(), z);
                BlockState state = world.getBlockState(blockPos);
                if (!state.getBlock().equals(MalumBlocks.IMPERVIOUS_ROCK.get()))
                {
                    effect(blockPos, state, world);
                }
            }
        }
    }
}
