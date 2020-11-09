package com.sammy.malum.blocks.machines.crystallineaccelerator;

import com.sammy.malum.blocks.utility.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
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

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class CrystallineAcceleratorBoundingBlock extends BoundingBlock
{
    public CrystallineAcceleratorBoundingBlock(Properties properties)
    {
        super(properties);
    }
    
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        return Stream.of(Block.makeCuboidShape(2, 0, 2, 14, 3, 14), Block.makeCuboidShape(0, 0, 0, 5, 5, 5), Block.makeCuboidShape(11, 0, 11, 16, 5, 16), Block.makeCuboidShape(0, 0, 11, 5, 5, 16), Block.makeCuboidShape(11, 0, 0, 16, 5, 5), Block.makeCuboidShape(3, 3, 3, 13, 13, 13)).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    }
}