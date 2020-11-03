package com.sammy.malum.blocks.machines.redstonebattery;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.utility.IConfigurableBlock;
import com.sammy.malum.blocks.utility.IConfigurableTileEntity;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import static com.sammy.malum.MalumHelper.machineOption;
import static com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity.redstoneClockFunctionTypeEnum;
import static net.minecraft.block.RedstoneTorchBlock.LIT;

public class RedstoneBatteryBlock extends Block implements IConfigurableBlock
{
    public RedstoneBatteryBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
    }
    
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        if (worldIn.isBlockPowered(pos))
        {
            RedstoneBatteryTileEntity tileEntity = (RedstoneBatteryTileEntity) worldIn.getTileEntity(pos);
            tileEntity.timer = tileEntity.cooldown[tileEntity.tickMultiplier];
        }
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
    }
    
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> blockStateBuilder)
    {
        blockStateBuilder.add(LIT);
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }
    
    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        return blockState.get(LIT) ? 15 : 0;
    }
    
    @Override
    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side)
    {
        return blockState.get(LIT) ? 15 : 0;
    }
    
    @Override
    public boolean canProvidePower(BlockState state)
    {
        return true;
    }
    
    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (!isMoving)
        {
            for (Direction direction : Direction.values())
            {
                worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
            }
        }
    }
    
    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        for (Direction direction : Direction.values())
        {
            worldIn.notifyNeighborsOfStateChange(pos.offset(direction), this);
        }
    }
    
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new RedstoneBatteryTileEntity();
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        return activateConfigurableBlock(state, worldIn, pos, player, handIn, hit);
    }
    
    @Override
    public int options()
    {
        return 2;
    }
    
    @Override
    public void configureTileEntity(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, IConfigurableTileEntity tileEntity, int option, boolean isSneaking)
    {
        if (tileEntity instanceof RedstoneBatteryTileEntity)
        {
            RedstoneBatteryTileEntity redstoneClockTileEntity = (RedstoneBatteryTileEntity) tileEntity;
            int change = isSneaking ? -1 : 1;
            float pitch = 0f;
            int finalOption = machineOption(option, options());
            switch (finalOption)
            {
                case 1:
                {
                    redstoneClockTileEntity.type = machineOption(change + redstoneClockTileEntity.type, redstoneClockFunctionTypeEnum.values().length);
                    pitch = (float) redstoneClockTileEntity.type / redstoneClockFunctionTypeEnum.values().length;
                    break;
                }
                case 0:
                {
                    redstoneClockTileEntity.tickMultiplier = machineOption(change + redstoneClockTileEntity.tickMultiplier, redstoneClockTileEntity.cooldown.length);
                    pitch = (float) redstoneClockTileEntity.tickMultiplier / redstoneClockTileEntity.cooldown.length;
                    break;
                }
            }
            MalumHelper.makeMachineToggleSound(worldIn, pos, 1f + pitch);
            MalumHelper.makeMachineToggleSound(worldIn, pos, 1f + pitch);
            player.world.notifyBlockUpdate(pos, state, state, 3);
            player.swingArm(handIn);
        }
    }
}