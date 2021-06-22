package com.sammy.malum.common.blocks.runetable;

import com.sammy.malum.common.worldgen.RunewoodTreeFeature;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.multiblock.MultiblockStructure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.DirectionalBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class RuneTableStructure extends MultiblockStructure
{
    public RuneTableStructure()
    {
        super();
    }

    @Override
    public List<BlockPos> getOccupiedPositions(BlockPos sourcePos, World world, PlayerEntity player, ItemStack stack, BlockState state)
    {
        Direction facing = player.getHorizontalFacing();
        BlockPos[] positions = facing == Direction.NORTH || facing == Direction.SOUTH ? new BlockPos[]{sourcePos.offset(Direction.WEST), sourcePos.offset(Direction.EAST)} : new BlockPos[]{sourcePos.offset(Direction.NORTH), sourcePos.offset(Direction.SOUTH)};
        return Arrays.asList(positions);
    }

    @Override
    public BlockState getBoundingBlockState(BlockPos sourcePos, BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state)
    {
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
        Direction toSourcePos = null;
        for (Direction direction : directions)
        {
            BlockPos offsetPlacePos = placePos.offset(direction);
            if (offsetPlacePos.getX() == sourcePos.getX() && offsetPlacePos.getZ() == sourcePos.getZ())
            {
                toSourcePos = direction;
                break;
            }
        }
        if (toSourcePos != null)
        {
            return MalumBlocks.RUNE_TABLE_BOUNDING_BLOCK.get().getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, toSourcePos.getOpposite());
        }
        else
        {
            return Blocks.STONE.getDefaultState();
        }
    }
}
