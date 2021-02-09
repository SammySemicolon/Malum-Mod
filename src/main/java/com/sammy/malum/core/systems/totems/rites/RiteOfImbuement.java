package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import net.minecraft.block.Block;
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

public class RiteOfImbuement extends AffectBlocksRite
{
    HashMap<BlockPos, Integer> map = new HashMap<>();
    public RiteOfImbuement(String identifier, boolean isInstant, MalumSpiritType... spirits)
    {
        super(identifier, isInstant, spirits);
    }
    
    @Override
    public int cooldown()
    {
        return 10;
    }
    
    @Override
    public boolean effect(BlockPos pos, BlockState state, World world)
    {
        pos = pos.down(1);
        state = world.getBlockState(pos);
        Block outputBlock = MalumTransfusions.getTransfusedBlock(state.getBlock());
        int amount = 0;
        if (map.containsKey(pos))
        {
            amount = map.get(pos);
        }
        else
        {
            map.put(pos, amount);
        }
        if (outputBlock != null)
        {
            if (amount > 10)
            {
                if (MalumHelper.areWeOnServer(world))
                {
                    world.setBlockState(pos, outputBlock.getDefaultState(), 64);
                    MalumHelper.updateState(outputBlock.getDefaultState(), world, pos);
                    world.playSound(null, pos, outputBlock.getDefaultState().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1, 1.1f);
                    map.remove(pos);
                }
            }
            else
            {
                if (MalumHelper.areWeOnServer(world))
                {
                    world.playSound(null, pos, state.getSoundType().getBreakSound(), SoundCategory.BLOCKS, 1, 1.1f);
                
                    map.replace(pos, amount + 1);
                }
            }
            if (MalumHelper.areWeOnClient(world))
            {
                Color color = MalumConstants.darkest();
                ParticleManager.create(MalumParticles.WISP_PARTICLE).setAlpha(1.0f, 0f).setLifetime(40).setScale(0.075f, 0).setColor(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getRed() / 255f, (color.getGreen() * 0.5f) / 255f, (color.getBlue() * 0.5f) / 255f).randomVelocity(0f, 0.01f).enableNoClip().repeatEdges(world, pos, 20 + 4 * amount);
                for (int i = 0; i < 5; i++)
                {
                    ArrayList<Vector3d> particlePositions = MalumHelper.blockOutlinePositions(world, pos);
                    particlePositions.forEach(p -> world.addParticle(new BlockParticleData(ParticleTypes.BLOCK, outputBlock.getDefaultState()), p.x, p.y, p.z, 0, world.rand.nextFloat() * 0.1f, 0));
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
