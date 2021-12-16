package com.sammy.malum.common.block.item_storage;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.Level.IBlockReader;

import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;

public class WoodItemPedestalBlock extends ItemPedestalBlock
{
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(4, 0, 4, 12, 3, 12),
            Block.box(5, 3, 5, 11, 11, 11),
            Block.box(4, 11, 4, 12, 13, 12)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public WoodItemPedestalBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader level, BlockPos pos, ISelectionContext context)
    {
        return SHAPE;
    }
}
