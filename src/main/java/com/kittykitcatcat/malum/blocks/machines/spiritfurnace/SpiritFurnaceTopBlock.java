package com.kittykitcatcat.malum.blocks.machines.spiritfurnace;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import static com.kittykitcatcat.malum.MalumHelper.updateState;
import static com.kittykitcatcat.malum.blocks.machines.spiritfurnace.SpiritFurnaceBottomTileEntity.spiritFuranceSlotEnum.input;
import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;
import static net.minecraft.state.properties.BlockStateProperties.LIT;

public class SpiritFurnaceTopBlock extends Block
{
    public SpiritFurnaceTopBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(LIT, false));
    }
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritFurnaceTopTileEntity();
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
    public int getLightValue(BlockState state, IBlockReader world, BlockPos pos)
    {
        return state.get(LIT) ? 12 : 2;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!isMoving && !newState.getBlock().equals(ModBlocks.spirit_furnace_top))
        {
            if (worldIn.getTileEntity(pos) instanceof SpiritFurnaceTopTileEntity)
            {
                SpiritFurnaceTopTileEntity tileEntity = (SpiritFurnaceTopTileEntity) worldIn.getTileEntity(pos);
                if (!tileEntity.inventory.getStackInSlot(0).isEmpty())
                {
                    Entity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.9f, pos.getZ() + 0.5f, tileEntity.inventory.getStackInSlot(0));
                    worldIn.addEntity(entity);
                }
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos.down()) instanceof SpiritFurnaceBottomTileEntity)
                {
                    SpiritFurnaceBottomTileEntity furnaceTileEntity = (SpiritFurnaceBottomTileEntity) worldIn.getTileEntity(pos.down());
                    ItemStack heldItem = player.getHeldItem(handIn);
                    ItemStack inputItem = furnaceTileEntity.getItemStack(input);
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
                        MalumHelper.giveItemStackToPlayer(player, inputItem);
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
        return ActionResultType.SUCCESS;
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
        blockStateBuilder.add(LIT);
    }
}