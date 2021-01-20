package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.AirBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class RiteOfDestruction extends AffectBlocksRite
{
    HashMap<BlockPos, Integer> map = new HashMap<>();
    public RiteOfDestruction(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
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
    
                for (int i = 0; i < 5; i++)
                {
                    ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, pos);
                    particlePositions.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, state), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
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
