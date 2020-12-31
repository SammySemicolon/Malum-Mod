package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.multiblock.BoundingBlock;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import static com.sammy.malum.common.blocks.spiritkiln.SpiritKilnCoreBlock.STATE;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritKilnBoundingBlock extends BoundingBlock implements IAlwaysActivatedBlock
{
    public SpiritKilnBoundingBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(STATE, 0));
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(STATE);
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    @Override
    public BlockState stateForPlacement(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
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
                if (state.get(STATE) == 1)
                {
                    ItemStack stack = player.getHeldItemMainhand();
                    if (stack.getItem().equals(MalumItems.TAINTED_ROCK.get()))
                    {
                        if (stack.getCount() >= 4)
                        {
                            stack.shrink(4);
                            tileEntity.repairKiln();
                            player.swingArm(handIn);
                            return ActionResultType.SUCCESS;
                        }
                    }
                }
                tileEntity.inventory.playerHandleItem(state, worldIn, pos.down(), player, handIn);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}
