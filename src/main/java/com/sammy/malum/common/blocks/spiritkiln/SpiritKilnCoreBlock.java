package com.sammy.malum.common.blocks.spiritkiln;

import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.recipes.SpiritKilnFuelData;
import com.sammy.malum.core.systems.multiblock.IMultiblock;
import com.sammy.malum.core.systems.otherutilities.IAlwaysActivatedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import static net.minecraft.state.properties.BlockStateProperties.HORIZONTAL_FACING;

public class SpiritKilnCoreBlock extends Block implements IMultiblock, IAlwaysActivatedBlock
{
    public static final IntegerProperty STATE = IntegerProperty.create("state", 0, 2);
    
    public SpiritKilnCoreBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(HORIZONTAL_FACING, Direction.NORTH).with(STATE, 0));
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(STATE);
        blockStateBuilder.add(HORIZONTAL_FACING);
    }
    
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context)
    {
        return getDefaultState().with(HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite()).with(STATE, 0);
    }
    
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new SpiritKilnCoreTileEntity();
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (handIn.equals(Hand.MAIN_HAND))
        {
            if (worldIn.getTileEntity(pos) instanceof SpiritKilnCoreTileEntity)
            {
                SpiritKilnCoreTileEntity tileEntity = (SpiritKilnCoreTileEntity) worldIn.getTileEntity(pos);
                ItemStack stack = player.getHeldItemMainhand();
                if (state.get(STATE) == 1)
                {
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
                if (SpiritKilnFuelData.getData(stack) != null)
                {
                    SpiritKilnFuelData data = SpiritKilnFuelData.getData(stack);
                    boolean success = tileEntity.powerStorage.increase(data);
                    if (success)
                    {
                        stack.shrink(1);
                        player.swingArm(handIn);
                        return ActionResultType.SUCCESS;
                    }
                }
            
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}