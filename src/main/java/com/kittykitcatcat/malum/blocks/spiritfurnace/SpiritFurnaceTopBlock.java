package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import static com.kittykitcatcat.malum.MalumHelper.updateState;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritFurnaceTopBlock extends Block
{
    public SpiritFurnaceTopBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos)
    {
        return false;
    }
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        worldIn.setBlockState(pos.down(), Blocks.AIR.getDefaultState());
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public BlockState updatePostPlacement(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos)
    {
        if (!worldIn.getBlockState(currentPos.down()).getBlock().equals(ModBlocks.spirit_furnace_bottom))
        {
            worldIn.setBlockState(currentPos, Blocks.AIR.getDefaultState(), 3);
        }
        return super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos.down()) instanceof SpiritFurnaceTileEntity)
                {
                    SpiritFurnaceTileEntity furnaceTileEntity = (SpiritFurnaceTileEntity) worldIn.getTileEntity(pos.down());
                    ItemStack heldItem = player.getHeldItem(handIn);
                    ItemStack inputItem = furnaceTileEntity.getInputStack(furnaceTileEntity.inventory);
                    //when input is empty
                    //right clicking adds held item to input
                    if (inputItem.isEmpty())
                    {
                        MalumHelper.setStackInTEInventory(furnaceTileEntity.inventory, heldItem, 1);
                        updateState(worldIn, state,pos);
                        updateState(worldIn, state,pos.down());
                        player.setHeldItem(handIn, ItemStack.EMPTY);
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                    //otherwise
                    //right clicking adds input to hand if its empty
                    else if (heldItem.isEmpty())
                    {
                        player.setHeldItem(handIn, inputItem);
                        MalumHelper.setStackInTEInventory(furnaceTileEntity.inventory, ItemStack.EMPTY, 1);
                        updateState(worldIn, state,pos);
                        updateState(worldIn, state,pos.down());
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }

                    //right clicking with an item matching input adds its count to input
                    if (heldItem.getItem().equals(inputItem.getItem()))
                    {
                        int cachedCount = heldItem.getCount();
                        for (int i = 0; i < cachedCount; i++)
                        {
                            if (inputItem.getCount() < 64)
                            {
                                MalumHelper.increaseStackSizeInTEInventory(furnaceTileEntity.inventory, 1, 1);
                                updateState(worldIn, state, pos);
                                updateState(worldIn, state, pos.down());
                                heldItem.setCount(heldItem.getCount() - 1);
                            }
                        }
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return ActionResultType.FAIL;
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
    }

}