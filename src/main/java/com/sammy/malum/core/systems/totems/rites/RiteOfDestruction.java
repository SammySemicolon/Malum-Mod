package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.modcontent.MalumTransfusions;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.awt.*;
import java.util.HashMap;

public class RiteOfDestruction extends AffectBlocksRite
{
    HashMap<BlockPos, Integer> map = new HashMap<>();
    public RiteOfDestruction(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
    }
    
    @Override
    public int cooldown()
    {
        return 50;
    }
    
    @Override
    public int range()
    {
        return 2;
    }
    
    @Override
    public int yOffset()
    {
        return -1;
    }
    
    @Override
    public boolean effect(BlockPos pos, BlockState state, World world)
    {
        int amount = 0;
        if (map.containsKey(pos))
        {
            amount = map.get(pos);
        }
        else
        {
            map.put(pos, amount);
        }
        if (!(state.getBlock() instanceof AirBlock))
        {
            if (amount > 5)
            {
                if (MalumHelper.areWeOnServer(world))
                {
                    world.destroyBlock(pos, true);
                    world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1, 1.1f);
                    map.remove(pos);
                }
            }
            else
            {
                if (MalumHelper.areWeOnServer(world))
                {
                    world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1, 1.75f + world.rand.nextFloat() * 0.25f);
                    map.replace(pos, amount + 1);
                }
            }
            if (MalumHelper.areWeOnClient(world))
            {
                Color color = MalumConstants.darkest();
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 20);
                for (int i = 0; i < 5;i++)
                {
                    MalumHelper.spawnParticles(world, pos, new BlockParticleData(ParticleTypes.BLOCK, state), 0);
                }
            }
        }
        else
        {
            map.remove(pos);
        }
        return false;
    }
}
