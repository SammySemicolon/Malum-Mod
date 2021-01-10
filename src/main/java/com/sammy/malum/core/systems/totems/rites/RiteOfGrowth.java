package com.sammy.malum.core.systems.totems.rites;

import com.sammy.malum.MalumConstants;
import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totems.TotemCoreTileEntity;
import com.sammy.malum.common.blocks.totems.TotemPoleBlock;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumRites;
import com.sammy.malum.core.modcontent.MalumRites.MalumRite;
import com.sammy.malum.core.modcontent.MalumRunes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;

public class RiteOfGrowth extends AffectBlocksRite
{
    public RiteOfGrowth(String identifier, boolean isInstant, MalumRunes.MalumRune... runes)
    {
        super(identifier, isInstant, runes);
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
    public void effect(BlockPos pos, BlockState state, World world)
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
        super.effect(pos, state, world);
    }
}