package com.sammy.malum.common.blocks.essencejar;

import com.sammy.malum.core.systems.essences.IEssenceRequest;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class EssenceJarBlock extends Block implements IEssenceRequest
{
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 1);
    
    public EssenceJarBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(TYPE, 0));
    }
    
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return super.getStateForPlacement(context).with(TYPE, context.getWorld().getBlockState(context.getPos().up()).isAir() ? 0 : 1);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        if (state.get(TYPE) == 1)
        {
            return Stream.of(Block.makeCuboidShape(12, 0, 12, 15, 4, 15), Block.makeCuboidShape(4, 0, 4, 12, 12, 12), Block.makeCuboidShape(5.5, 12, 5.5, 10.5, 14, 10.5), Block.makeCuboidShape(1, 10, 12, 4, 14, 15), Block.makeCuboidShape(0, 4, 12, 4, 10, 16), Block.makeCuboidShape(1, 0, 12, 4, 4, 15), Block.makeCuboidShape(1, 10, 1, 4, 14, 4), Block.makeCuboidShape(0, 4, 0, 4, 10, 4), Block.makeCuboidShape(1, 0, 1, 4, 4, 4), Block.makeCuboidShape(12, 10, 1, 15, 14, 4), Block.makeCuboidShape(12, 4, 0, 16, 10, 4), Block.makeCuboidShape(12, 0, 1, 15, 4, 4), Block.makeCuboidShape(0, 14, 0, 16, 16, 16), Block.makeCuboidShape(12, 10, 12, 15, 14, 15), Block.makeCuboidShape(12, 4, 12, 16, 10, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
        }
        else
        {
            return VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 0, 4, 12, 12, 12), Block.makeCuboidShape(5.5, 12, 5.5, 10.5, 14, 10.5), IBooleanFunction.OR);
        }
    }
    
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        worldIn.setBlockState(pos, state.with(TYPE, worldIn.getBlockState(pos.up()).isAir() ? 0 : 1));
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE);
        super.fillStateContainer(builder);
    }
}