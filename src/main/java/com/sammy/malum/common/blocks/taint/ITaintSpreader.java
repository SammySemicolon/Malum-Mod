package com.sammy.malum.common.blocks.taint;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.recipes.TaintTransfusion;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.server.ServerWorld;

import static net.minecraft.util.SoundCategory.AMBIENT;

public interface ITaintSpreader
{
    default void spread(ServerWorld worldIn, BlockPos pos)
    {
        Vector3i[] offsets = new Vector3i[]{
                new Vector3i(-1, 0, -1),
                new Vector3i(-1, 0, 0),
                new Vector3i(-1, 0, 1),
                new Vector3i(0, 0, 1),
                new Vector3i(1, 0, 1),
                new Vector3i(1, 0, 0),
                new Vector3i(1, 0, -1),
                new Vector3i(0, 0, -1),
        
                new Vector3i(-1, -1, -1),
                new Vector3i(-1, -1, 0),
                new Vector3i(-1, -1, 1),
                new Vector3i(0, -1, 1),
                new Vector3i(1, -1, 1),
                new Vector3i(1, -1, 0),
                new Vector3i(1, -1, -1),
                new Vector3i(0, -1, -1),
                new Vector3i(0, -1, 0),
        
                new Vector3i(-1, 1, -1),
                new Vector3i(-1, 1, 0),
                new Vector3i(-1, 1, 1),
                new Vector3i(0, 1, 1),
                new Vector3i(1, 1, 1),
                new Vector3i(1, 1, 0),
                new Vector3i(1, 1, -1),
                new Vector3i(0, 1, -1),
                new Vector3i(0, 1, 0)
        };
        boolean playSound = false;
        for (Vector3i offset : offsets)
        {
            BlockPos targetPos = pos.add(offset);
            Block targetBlock = worldIn.getBlockState(targetPos).getBlock();
            TaintTransfusion conversion = TaintTransfusion.getTransfusion(targetBlock);
            if (conversion != null)
            {
                playSound = true;
                TaintTransfusion.spread(worldIn,targetPos,conversion);
                if (MalumMod.RANDOM.nextFloat() <= 0.5f)
                {
                    break;
                }
            }
        }
        if (playSound)
        {
            worldIn.playSound(null, pos, MalumSounds.TAINT_SPREAD,AMBIENT, 0.5f,1f + worldIn.rand.nextFloat() * 0.5f);
        }
    }
}
