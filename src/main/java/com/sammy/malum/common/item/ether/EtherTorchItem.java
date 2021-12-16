package com.sammy.malum.common.item.ether;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;

import javax.annotation.Nullable;
import java.util.Map;

public class EtherTorchItem extends AbstractEtherItem
{
    protected final Block wallBlock;

    public EtherTorchItem(Block floorBlock, Block wallBlockIn, Properties builder, boolean iridescent)
    {
        super(floorBlock, builder, iridescent);
        this.wallBlock = wallBlockIn;
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context)
    {
        BlockState blockstate = this.wallBlock.getStateForPlacement(context);
        BlockState blockstate1 = null;
        Level level = context.getLevel();
        BlockPos blockpos = context.getClickedPos();

        for (Direction direction : context.getNearestLookingDirections())
        {
            if (direction != Direction.UP)
            {
                BlockState blockstate2 = direction == Direction.DOWN ? this.getBlock().getStateForPlacement(context) : blockstate;
                if (blockstate2 != null && blockstate2.canSurvive(level, blockpos))
                {
                    blockstate1 = blockstate2;
                    break;
                }
            }
        }

        return blockstate1 != null && level.isUnobstructed(blockstate1, blockpos, CollisionContext.empty()) ? blockstate1 : null;
    }

    public void registerBlocks(Map<Block, Item> blockToItemMap, Item itemIn)
    {
        super.registerBlocks(blockToItemMap, itemIn);
        blockToItemMap.put(this.wallBlock, itemIn);
    }

    public void removeFromBlockToItemMap(Map<Block, Item> blockToItemMap, Item itemIn)
    {
        super.removeFromBlockToItemMap(blockToItemMap, itemIn);
        blockToItemMap.remove(this.wallBlock);
    }
}