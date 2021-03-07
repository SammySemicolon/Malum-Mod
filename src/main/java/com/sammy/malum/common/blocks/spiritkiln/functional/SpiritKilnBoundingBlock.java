package com.sammy.malum.common.blocks.spiritkiln.functional;

import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreBlock.POWERED;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritKilnBoundingBlock extends BoundingBlock
{
    public SpiritKilnBoundingBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(POWERED, false));
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(POWERED);
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    @Override
    public BlockState multiblockState(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
    {
        return getDefaultState().with(HORIZONTAL_FACING, state.get(HORIZONTAL_FACING));
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos.down()) instanceof SpiritKilnCoreTileEntity)
            {
                SpiritKilnCoreTileEntity tileEntity = (SpiritKilnCoreTileEntity) worldIn.getTileEntity(pos.down());
    
                player.swing(handIn, true);
                if (tileEntity.outputInventory.nonEmptyItems() != 0)
                {
                    tileEntity.outputInventory.playerExtractItem(worldIn, player);
                    return ActionResultType.SUCCESS;
                }
                tileEntity.inventory.playerHandleItem(worldIn, player, handIn);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
