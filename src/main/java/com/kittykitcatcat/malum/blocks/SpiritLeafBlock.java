package com.kittykitcatcat.malum.blocks;


import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.particles.bloodparticle.BloodParticleData;
import com.kittykitcatcat.malum.particles.spiritleaf.SpiritLeafData;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public class SpiritLeafBlock extends LeavesBlock
{
    public SpiritLeafBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void animateTick(BlockState stateIn, World world, BlockPos pos, Random rand)
    {
        super.animateTick(stateIn, world, pos, rand);
        if (world.isRemote)
        {
            if (world.getBlockState(pos.down()).isAir())
            {
                if (MathHelper.nextInt(world.rand, 0, 4) == 0)
                {
                    Vec3d velocity = MalumHelper.randVelocity(world, -0.2f, -0.1f);
                    Vec3d particlePos = MalumHelper.randPos(pos, world, -0.5f, 0.5f);
                    world.addParticle(new SpiritLeafData(),
                            particlePos.x,pos.getY()-0.1f,particlePos.z, 0f,velocity.y,0f);
                }
            }
        }
    }
}