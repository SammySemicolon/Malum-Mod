package com.sammy.malum.blocks.utility;


import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public class ModSlabBlock extends SlabBlock
{
    public ModSlabBlock(Properties properties)
    {
        super(properties);
    }
    @Override
    public VoxelShape getCollisionShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return state.getShape(worldIn, pos);
    }
}