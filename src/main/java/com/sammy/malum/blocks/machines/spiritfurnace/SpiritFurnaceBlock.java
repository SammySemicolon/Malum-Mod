package com.sammy.malum.blocks.machines.spiritfurnace;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.utility.multiblock.MultiblockBlock;
import com.sammy.malum.blocks.utility.multiblock.MultiblockStructure;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritFurnaceBlock extends MultiblockBlock
{
    //region structure
    public static final MultiblockStructure structure = new MultiblockStructure(
            new BlockPos(0,1,0)
    );
    //endregion
    
    public SpiritFurnaceBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
    }
    
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context)
    {
        worldIn.getTileEntity(pos);
        return super.getShape(state, worldIn, pos, context);
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new SpiritFurnaceTileEntity();
    }
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
    @Override
    public ActionResultType activateBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, BlockPos boundingBlockSource)
    {
        if (worldIn.getTileEntity(pos) instanceof SpiritFurnaceTileEntity)
        {
            SpiritFurnaceTileEntity furnaceTileEntity = (SpiritFurnaceTileEntity) worldIn.getTileEntity(pos);
    
            if (boundingBlockSource.equals(pos.up())) //smeltable inventory access
            {
                boolean success = MalumHelper.singleItemTEHandling(player, handIn, player.getHeldItemMainhand(), furnaceTileEntity.smeltableInventory, 0);
                if (success)
                {
                    player.world.notifyBlockUpdate(pos, state, state, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
            if (boundingBlockSource.equals(pos)) //fuel inv access
            {
                boolean success = MalumHelper.singleItemTEHandling(player, handIn, player.getHeldItemMainhand(), furnaceTileEntity.fuelInventory, 0);
                if (success)
                {
                    player.world.notifyBlockUpdate(pos, state, state, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.activateBlock(state, worldIn, pos, player, handIn, hit, boundingBlockSource);
    }
}