package com.sammy.malum.common.blocks.taint;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.systems.recipes.TaintConversion;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

public interface ITaintSpreader
{
    default void spread(ServerWorld worldIn, BlockPos pos)
    {
        for (Direction direction : Direction.values())
        {
            BlockPos targetPos = pos.add(direction.getDirectionVec());
            Block targetBlock = worldIn.getBlockState(targetPos).getBlock();
            TaintConversion conversion = TaintConversion.getConversion(targetBlock);
            if (conversion != null)
            {
                TaintConversion.spread(worldIn,targetPos,conversion);
            }
        }
    }
}
