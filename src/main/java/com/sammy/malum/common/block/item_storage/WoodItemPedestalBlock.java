package com.sammy.malum.common.block.item_storage;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class WoodItemPedestalBlock extends ItemPedestalBlock
{
    public static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(4, 0, 4, 12, 3, 12),
            Block.makeCuboidShape(5, 3, 5, 11, 11, 11),
            Block.makeCuboidShape(4, 11, 4, 12, 13, 12)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public WoodItemPedestalBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
}
