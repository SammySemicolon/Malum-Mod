package com.sammy.malum.common.blocks;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleBlock;
import com.sammy.malum.common.blocks.totem.pole.TotemPoleTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.entity.player.PlayerEntity;
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        ItemStack stack = player.getHeldItem(handIn);
        if (MalumHelper.areWeOnClient(worldIn))
        {
            if (stack.getItem() instanceof SpiritItem)
            {
                return ActionResultType.SUCCESS;
            }
        }
        if (stack.getItem() instanceof SpiritItem)
        {
            SpiritItem item = (SpiritItem) stack.getItem();
            worldIn.setBlockState(pos, MalumBlocks.TOTEM_POLE.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, player.getHorizontalFacing().getOpposite()));
            if (worldIn.getTileEntity(pos) instanceof TotemPoleTileEntity)
            {
                TotemPoleTileEntity tileEntity = (TotemPoleTileEntity) worldIn.getTileEntity(pos);
                tileEntity.create(item.type);
            }
            MalumHelper.updateAndNotifyState(worldIn,pos);
            if (!player.isCreative())
            {
                stack.shrink(1);
            }
            player.swing(handIn, true);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
