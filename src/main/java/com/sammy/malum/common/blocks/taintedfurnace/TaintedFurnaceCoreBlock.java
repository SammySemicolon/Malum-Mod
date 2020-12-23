package com.sammy.malum.common.blocks.taintedfurnace;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.systems.multiblock.IMultiblock;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class TaintedFurnaceCoreBlock extends Block implements IMultiblock, IAlwaysActivatedBlock
{
    public TaintedFurnaceCoreBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH));
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
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new TaintedFurnaceCoreTileEntity();
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos) instanceof TaintedFurnaceCoreTileEntity)
            {
                TaintedFurnaceCoreTileEntity tileEntity = (TaintedFurnaceCoreTileEntity) worldIn.getTileEntity(pos);
                ItemStack stack = player.getHeldItemMainhand();
                if (stack.getItem().equals(MalumItems.ARCANE_CHARCOAL.get()))
                {
                    if (player.isSneaking())
                    {
                        int count = stack.getCount();
                        tileEntity.fuel += count *16;
                        stack.shrink(count);
                    }
                    else
                    {
                        tileEntity.fuel += 16;
                        stack.shrink(1);
                    }
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}