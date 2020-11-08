package com.sammy.malum.blocks.machines.spiritjar;

import com.sammy.malum.blocks.utility.spiritstorage.SpiritStoringBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import java.util.stream.Stream;

public class SpiritJarBlock extends SpiritStoringBlock
{
    public static final IntegerProperty TYPE = IntegerProperty.create("type", 0, 1);
    
    public SpiritJarBlock(Properties properties)
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
            return Stream.of(Block.makeCuboidShape(4, 0, 4, 12, 12, 12), Block.makeCuboidShape(5.5, 12, 5.5, 10.5, 14, 10.5), Block.makeCuboidShape(0, 0, 0, 3, 14, 3), Block.makeCuboidShape(13, 0, 0, 16, 14, 3), Block.makeCuboidShape(13, 0, 13, 16, 14, 16), Block.makeCuboidShape(0, 0, 13, 3, 14, 16), Block.makeCuboidShape(0, 14, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
        }
        else
        {
            return VoxelShapes.combineAndSimplify(Block.makeCuboidShape(4, 0, 4, 12, 12, 12), Block.makeCuboidShape(5.5, 12, 5.5, 10.5, 14, 10.5), IBooleanFunction.OR);
        }
    }
    
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        worldIn.setBlockState(pos,state.with(TYPE, worldIn.getBlockState(pos.up()).isAir() ? 0 : 1));
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
    
    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder)
    {
        builder.add(TYPE);
        super.fillStateContainer(builder);
    }
    
    @Override
    public int capacity()
    {
        return 20;
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritJarTileEntity();
    }
    
}