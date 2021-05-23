package com.sammy.malum.common.blocks;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totem.TotemBaseBlock;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleBlock;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
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

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        Block block = context.getWorld().getBlockState(context.getPos().down()).getBlock();
        if (block instanceof TotemBaseBlock || block instanceof TotemPoleBlock)
        {
            return MalumBlocks.TOTEM_POLE.get().getStateForPlacement(context);
        }
        return super.getStateForPlacement(context);
    }
}
