package com.sammy.malum.common.blocks.taint;

import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.core.recipes.TaintConversion;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.awt.*;
import java.util.Random;

public class TaintedLeavesBlock extends MalumLeavesBlock implements ITaintSpreader
{
    public TaintedLeavesBlock(Properties properties, Color maxColor, Color minColor)
    {
        super(properties, maxColor, minColor);
    }
    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos, Random rand)
    {
        super.tick(state, worldIn, pos, rand);
        spread(worldIn,pos);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        TaintConversion.issueSpread(worldIn, pos);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
    
    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        TaintConversion.issueSpread((World) worldIn, currentPos);
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }
}
