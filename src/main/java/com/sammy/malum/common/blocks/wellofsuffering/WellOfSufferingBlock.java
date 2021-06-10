package com.sammy.malum.common.blocks.wellofsuffering;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;

import java.util.stream.Stream;

public class WellOfSufferingBlock extends Block implements IBucketPickupHandler
{
    public WellOfSufferingBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state)
    {
        return Fluids.WATER;
    }

    public final VoxelShape shape = Stream.of(
            Block.makeCuboidShape(14, 4, 0, 16, 16, 14),
            Block.makeCuboidShape(0, 4, 2, 2, 16, 16),
            Block.makeCuboidShape(0, 0, 0, 16, 2, 16),
            Block.makeCuboidShape(1, 2, 1, 15, 4, 15),
            Block.makeCuboidShape(0, 4, 0, 14, 16, 2),
            Block.makeCuboidShape(2, 4, 14, 16, 16, 16)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return shape;
    }

    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new WellOfSufferingTileEntity();
    }
}