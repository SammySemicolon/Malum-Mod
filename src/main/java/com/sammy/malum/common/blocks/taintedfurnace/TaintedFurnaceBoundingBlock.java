package com.sammy.malum.common.blocks.taintedfurnace;

import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TaintedFurnaceBoundingBlock extends BoundingBlock
{
    public TaintedFurnaceBoundingBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    
    @Override
    public BlockState stateForPlacement(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
    {
        return getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING));
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos.down()) instanceof TaintedFurnaceCoreTileEntity)
            {
                ((TaintedFurnaceCoreTileEntity) worldIn.getTileEntity(pos.down())).inventory.handleItem(state, worldIn, pos.down(), player, handIn);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
