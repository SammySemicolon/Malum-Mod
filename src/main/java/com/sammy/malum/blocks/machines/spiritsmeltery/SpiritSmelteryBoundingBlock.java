package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.blocks.utility.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import java.util.stream.Stream;

public class SpiritSmelteryBoundingBlock extends BoundingBlock
{
    public static final IntegerProperty xProperty = IntegerProperty.create("x", 0, 2);
    public static final IntegerProperty yProperty = IntegerProperty.create("y", 0, 2);
    public static final IntegerProperty zProperty = IntegerProperty.create("z", 0, 2);
    
    public SpiritSmelteryBoundingBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(xProperty, 0).with(yProperty, 0).with(zProperty, 0));
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(xProperty);
        blockStateBuilder.add(yProperty);
        blockStateBuilder.add(zProperty);
    }
    
    //region shapes
    VoxelShape lower_corner = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 6, 16), Block.makeCuboidShape(2, 6, 2, 16, 12, 16), Block.makeCuboidShape(0, 12, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape lower_corner_90 = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 6, 16), Block.makeCuboidShape(2, 6, 0, 16, 12, 14), Block.makeCuboidShape(0, 12, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape lower_corner_180 = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 6, 16), Block.makeCuboidShape(0, 6, 0, 14, 12, 14), Block.makeCuboidShape(0, 12, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape lower_corner_270 = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 6, 16), Block.makeCuboidShape(0, 6, 2, 14, 12, 16), Block.makeCuboidShape(0, 12, 0, 16, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    
    VoxelShape middle_edge = Stream.of(Block.makeCuboidShape(0, 0, 10, 16, 2, 16), Block.makeCuboidShape(0, 10, 2, 16, 13, 5), Block.makeCuboidShape(0, 0, 2, 16, 10, 5), Block.makeCuboidShape(0, 0, 5, 16, 16, 10)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape middle_edge_90 = Stream.of(Block.makeCuboidShape(10, 0, 0, 16, 2, 16), Block.makeCuboidShape(2, 10, 0, 5, 13, 16), Block.makeCuboidShape(2, 0, 0, 5, 10, 16), Block.makeCuboidShape(5, 0, 0, 10, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape middle_edge_180 = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 2, 6), Block.makeCuboidShape(0, 10, 11, 16, 13, 14), Block.makeCuboidShape(0, 0, 11, 16, 10, 14), Block.makeCuboidShape(0, 0, 6, 16, 16, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape middle_edge_270 = Stream.of(Block.makeCuboidShape(0, 0, 0, 6, 2, 16), Block.makeCuboidShape(11, 10, 0, 14, 13, 16), Block.makeCuboidShape(11, 0, 0, 14, 10, 16), Block.makeCuboidShape(6, 0, 0, 11, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    
    VoxelShape middle_corner = Stream.of(Block.makeCuboidShape(2, 0, 2, 12, 10, 5), Block.makeCuboidShape(2, 0, 5, 5, 10, 12), Block.makeCuboidShape(2, 13, 2, 12, 16, 5), Block.makeCuboidShape(2, 13, 5, 5, 16, 12), Block.makeCuboidShape(10, 0, 10, 16, 2, 16), Block.makeCuboidShape(5, 0, 10, 10, 16, 16), Block.makeCuboidShape(5, 0, 5, 16, 16, 10)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape middle_corner_90 = Stream.of(Block.makeCuboidShape(2, 0, 4, 5, 10, 14), Block.makeCuboidShape(5, 0, 11, 12, 10, 14), Block.makeCuboidShape(2, 13, 4, 5, 16, 14), Block.makeCuboidShape(5, 13, 11, 12, 16, 14), Block.makeCuboidShape(10, 0, 0, 16, 2, 6), Block.makeCuboidShape(10, 0, 6, 16, 16, 11), Block.makeCuboidShape(5, 0, 0, 10, 16, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape middle_corner_180 = Stream.of(Block.makeCuboidShape(4, 0, 11, 14, 10, 14), Block.makeCuboidShape(11, 0, 4, 14, 10, 11), Block.makeCuboidShape(4, 13, 11, 14, 16, 14), Block.makeCuboidShape(11, 13, 4, 14, 16, 11), Block.makeCuboidShape(0, 0, 0, 6, 2, 6), Block.makeCuboidShape(6, 0, 0, 11, 16, 6), Block.makeCuboidShape(0, 0, 6, 11, 16, 11)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape middle_corner_270 = Stream.of(Block.makeCuboidShape(11, 0, 2, 14, 10, 12), Block.makeCuboidShape(4, 0, 2, 11, 10, 5), Block.makeCuboidShape(11, 13, 2, 14, 16, 12), Block.makeCuboidShape(4, 13, 2, 11, 16, 5), Block.makeCuboidShape(0, 0, 10, 6, 2, 16), Block.makeCuboidShape(0, 0, 5, 6, 16, 10), Block.makeCuboidShape(6, 0, 5, 11, 16, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    
    VoxelShape upper_corner = Stream.of(Block.makeCuboidShape(8, 6, 0, 16, 16, 4), Block.makeCuboidShape(0, 0, 0, 16, 6, 10), Block.makeCuboidShape(4, 6, 4, 16, 14, 10), Block.makeCuboidShape(0, 6, 8, 4, 16, 16), Block.makeCuboidShape(4, 6, 10, 10, 14, 16), Block.makeCuboidShape(0, 0, 10, 10, 6, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape upper_corner_90 = Stream.of(Block.makeCuboidShape(0, 6, 0, 4, 16, 8), Block.makeCuboidShape(0, 0, 0, 10, 6, 16), Block.makeCuboidShape(4, 6, 0, 10, 14, 12), Block.makeCuboidShape(8, 6, 12, 16, 16, 16), Block.makeCuboidShape(10, 6, 6, 16, 14, 12), Block.makeCuboidShape(10, 0, 6, 16, 6, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape upper_corner_180 = Stream.of(Block.makeCuboidShape(0, 6, 12, 8, 16, 16), Block.makeCuboidShape(0, 0, 6, 16, 6, 16), Block.makeCuboidShape(0, 6, 6, 12, 14, 12), Block.makeCuboidShape(12, 6, 0, 16, 16, 8), Block.makeCuboidShape(6, 6, 0, 12, 14, 6), Block.makeCuboidShape(6, 0, 0, 16, 6, 6)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape upper_corner_270 = Stream.of(Block.makeCuboidShape(12, 6, 8, 16, 16, 16), Block.makeCuboidShape(6, 0, 0, 16, 6, 16), Block.makeCuboidShape(6, 6, 4, 12, 14, 16), Block.makeCuboidShape(0, 6, 0, 8, 16, 4), Block.makeCuboidShape(0, 6, 4, 6, 14, 10), Block.makeCuboidShape(0, 0, 0, 6, 6, 10)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    
    VoxelShape upper_edge = Stream.of(Block.makeCuboidShape(0, 0, 0, 16, 6, 10), Block.makeCuboidShape(4, 6, 0, 12, 16, 4), Block.makeCuboidShape(0, 6, 4, 16, 14, 10)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape upper_edge_90 = Stream.of(Block.makeCuboidShape(0, 0, 0, 10, 6, 16), Block.makeCuboidShape(0, 6, 4, 4, 16, 12), Block.makeCuboidShape(4, 6, 0, 10, 14, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape upper_edge_180 = Stream.of(Block.makeCuboidShape(0, 0, 6, 16, 6, 16), Block.makeCuboidShape(4, 6, 12, 12, 16, 16), Block.makeCuboidShape(0, 6, 6, 16, 14, 12)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    VoxelShape upper_edge_270 = Stream.of(Block.makeCuboidShape(6, 0, 0, 16, 6, 16), Block.makeCuboidShape(12, 6, 4, 16, 16, 12), Block.makeCuboidShape(6, 6, 0, 12, 14, 16)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        int x = state.get(xProperty);
        int y = state.get(yProperty);
        int z = state.get(zProperty);
        if (y == 0)
        {
            if (x == 0 && z == 0)
            {
                return lower_corner;
            }
            if (x == 2 && z == 0)
            {
                return lower_corner_270;
            }
            if (x == 2 && z == 2)
            {
                return lower_corner_180;
            }
            if (x == 0 && z == 2)
            {
                return lower_corner_90;
            }
        }
        if (y == 1)
        {
            if (x == 1 && z == 1)
            {
                return Block.makeCuboidShape(0, 0, 0, 16, 2, 16);
            }
            if (x == 0 && z == 0)
            {
                return middle_corner;
            }
            if (x == 2 && z == 0)
            {
                return middle_corner_270;
            }
            if (x == 2 && z == 2)
            {
                return middle_corner_180;
            }
            if (x == 0 && z == 2)
            {
                return middle_corner_90;
            }
            
            if (x == 1 && z == 2)
            {
                return middle_edge_180;
            }
            if (x == 1 && z == 0)
            {
                return middle_edge;
            }
            if (x == 2 && z == 1)
            {
                return middle_edge_270;
            }
            if (x == 0 && z == 1)
            {
                return middle_edge_90;
            }
        }
    
        if (y == 2)
        {
            if (x == 1 && z == 1)
            {
                return VoxelShapes.empty();
            }
            if (x == 0 && z == 0)
            {
                return upper_corner;
            }
            if (x == 2 && z == 0)
            {
                return upper_corner_270;
            }
            if (x == 2 && z == 2)
            {
                return upper_corner_180;
            }
            if (x == 0 && z == 2)
            {
                return upper_corner_90;
            }
    
            if (x == 1 && z == 2)
            {
                return upper_edge_180;
            }
            if (x == 1 && z == 0)
            {
                return upper_edge;
            }
            if (x == 2 && z == 1)
            {
                return upper_edge_270;
            }
            if (x == 0 && z == 1)
            {
                return upper_edge_90;
            }
        }
        return super.getShape(state, worldIn, pos, context);
    }
    //endregion
    
    @Override
    public BlockState state(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
    {
        return super.state(placePos, world, player, stack, state, pos).with(xProperty, pos.getX() + 1).with(yProperty, pos.getY()).with(zProperty, pos.getZ() + 1);
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(xProperty, 0).with(yProperty, 0).with(zProperty, 0);
    }
}