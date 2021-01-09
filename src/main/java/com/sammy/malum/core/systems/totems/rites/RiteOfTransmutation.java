package com.sammy.malum.core.systems.totems.rites;

import com.ibm.icu.impl.Pair;
import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;

public class RiteOfTransmutation extends MalumRite
{
    int[] progress = new int[9];
    public RiteOfTransmutation(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public void effect(BlockPos pos, World world)
    {
        if (world.getGameTime() % 20 == 0)
        {
            Color color = MalumConstants.bright();
            int i = 0;
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    BlockPos blockPos = pos.add(x, -1, z);
                    BlockState state = world.getBlockState(blockPos);
                    if (!state.isAir(world,pos))
                    {
                        progress[i]++;
                        if (progress[i] > 4)
                        {
                            if (MalumHelper.areWeOnClient(world))
                            {
                                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.05f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0.01f, 0f).enableNoClip().repeatEdges(world, blockPos, 40);
                            }
                            
                            world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                            progress[i] = 0;
                        }
                    }
                    else
                    {
                        progress[i] = 0;
                    }
                    i++;
                }
            }
        }
    }
}
