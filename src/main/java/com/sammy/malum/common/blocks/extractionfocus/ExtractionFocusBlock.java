package com.sammy.malum.common.blocks.extractionfocus;

import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public class ExtractionFocusBlock extends Block
{
    public ExtractionFocusBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH));
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(FACING);
    }
    public final VoxelShape up =Block.makeCuboidShape(4, 0, 4, 12, 2, 12);
    public final VoxelShape down =Block.makeCuboidShape(4, 14, 4, 12, 16, 12);
    public final VoxelShape south =Block.makeCuboidShape(4, 4, 0, 12, 12, 2);
    public final VoxelShape north =Block.makeCuboidShape(4, 4, 14, 12, 12, 16);
    public final VoxelShape west =Block.makeCuboidShape(14, 4, 4, 16, 12, 12);
    public final VoxelShape east =Block.makeCuboidShape(0, 4, 4, 2, 12, 12);
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        switch (state.get(FACING))
        {
            case UP:
            {
                return up;
            }
            case DOWN:
            {
                return down;
            }
            case SOUTH:
            {
                return south;
            }
            case NORTH:
            {
                return north;
            }
            case WEST:
            {
                return west;
            }
            case EAST:
            {
                return east;
            }
        }
        return super.getShape(state, worldIn, pos, context);
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(FACING, context.getNearestLookingDirection().getOpposite());
    }
}