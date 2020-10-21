package com.sammy.malum.blocks.machines.redstoneclock;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.blocks.utility.ConfigurableBlock;
import com.sammy.malum.blocks.utility.ConfigurableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
import static com.sammy.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity.*;
import static net.minecraft.state.properties.BlockStateProperties.*;

public class RedstoneClockBlock extends ConfigurableBlock
{
    public RedstoneClockBlock(Properties properties)
    {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LIT, false));
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

    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
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
        return new RedstoneClockTileEntity();
    }
    
    @Override
    public void configureTileEntity(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, ConfigurableTileEntity tileEntity, int option, boolean isSneaking)
    {
        if (tileEntity instanceof RedstoneClockTileEntity)
        {
            RedstoneClockTileEntity redstoneClockTileEntity = (RedstoneClockTileEntity) tileEntity;
            int change = isSneaking ? -1 : 1;
            float pitch = 0f;
            int finalOption = machineOption(option, 2);
            switch (finalOption)
            {
                case 1:
                {
                    redstoneClockTileEntity.type = machineOption(change + redstoneClockTileEntity.type, redstoneClockFunctionTypeEnum.values().length);
                    pitch = (float)redstoneClockTileEntity.type / redstoneClockFunctionTypeEnum.values().length;
                    break;
                }
                case 0:
                {
                    redstoneClockTileEntity.tickMultiplier = machineOption(change + redstoneClockTileEntity.tickMultiplier, redstoneClockTileEntity.cooldown.length);
                    pitch = (float)redstoneClockTileEntity.tickMultiplier / redstoneClockTileEntity.cooldown.length;
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