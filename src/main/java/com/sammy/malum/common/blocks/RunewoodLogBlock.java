package com.sammy.malum.common.blocks;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class RunewoodLogBlock extends RotatedPillarBlock
{
    public RunewoodLogBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public BlockState getToolModifiedState(BlockState state, World world, BlockPos pos, PlayerEntity player, ItemStack stack, ToolType toolType)
    {
        return world.rand.nextFloat() < 0.1f ? MalumBlocks.SAP_FILLED_RUNEWOOD_LOG.get().getDefaultState().with(AXIS, state.get(AXIS)) : MalumBlocks.STRIPPED_RUNEWOOD_LOG.get().getDefaultState().with(AXIS, state.get(AXIS));
    }
}
