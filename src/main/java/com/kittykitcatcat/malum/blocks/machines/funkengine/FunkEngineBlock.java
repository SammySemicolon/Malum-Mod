package com.kittykitcatcat.malum.blocks.machines.funkengine;

import com.kittykitcatcat.malum.MalumHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.*;

public class FunkEngineBlock extends Block
{
    public FunkEngineBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(HAS_RECORD, false));
    }
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new FunkEngineTileEntity();
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
        blockStateBuilder.add(HAS_RECORD);
    }

    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (state.getBlock() != newState.getBlock())
        {
            if (worldIn.getTileEntity(pos) instanceof FunkEngineTileEntity)
            {
                FunkEngineTileEntity funkEngineTileEntity = (FunkEngineTileEntity) worldIn.getTileEntity(pos);
                if (funkEngineTileEntity.inventory.getStackInSlot(0) != ItemStack.EMPTY)
                {
                    Entity entity = new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.9f, pos.getZ() + 0.5f, funkEngineTileEntity.inventory.getStackInSlot(0).copy());
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
                if (worldIn.getTileEntity(pos) instanceof FunkEngineTileEntity)
                {
                    FunkEngineTileEntity funkEngineTileEntity = (FunkEngineTileEntity) worldIn.getTileEntity(pos);
                    ItemStack stack = player.getHeldItemMainhand();
                    boolean value = MalumHelper.basicTEHandling(player, handIn, stack, funkEngineTileEntity, 0);
                    BlockState newState = state.with(HAS_RECORD, value);
                    worldIn.setBlockState(pos, newState);
                    player.world.notifyBlockUpdate(pos, state, newState, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(HAS_RECORD, false);
    }

}