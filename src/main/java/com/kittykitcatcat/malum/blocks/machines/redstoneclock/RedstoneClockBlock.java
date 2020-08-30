package com.kittykitcatcat.malum.blocks.machines.redstoneclock;

import com.kittykitcatcat.malum.MalumHelper;
import com.kittykitcatcat.malum.particles.lensmagic.LensMagicParticleData;
import com.kittykitcatcat.malum.particles.skull.SkullParticleData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RedstoneDiodeBlock;
import net.minecraft.client.renderer.entity.EnderDragonRenderer;
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

import static com.kittykitcatcat.malum.blocks.machines.redstoneclock.RedstoneClockTileEntity.redstoneClockFunctionTypeEnum.*;
import static net.minecraft.state.properties.BlockStateProperties.*;

public class RedstoneClockBlock extends Block
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
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof RedstoneClockTileEntity)
                {
                    RedstoneClockTileEntity redstoneClockTileEntity = (RedstoneClockTileEntity) worldIn.getTileEntity(pos);
                    float pitch;
                    if (player.isSneaking())
                    {
                        redstoneClockTileEntity.type++;
                        if (redstoneClockTileEntity.type >= values().length)
                        {
                            redstoneClockTileEntity.type = 0;
                        }
                        pitch = (float)redstoneClockTileEntity.type / values().length;
                    }
                    else
                    {
                        redstoneClockTileEntity.tickMultiplier++;
                        if (redstoneClockTileEntity.tickMultiplier >= redstoneClockTileEntity.cooldown.length)
                        {
                            redstoneClockTileEntity.tickMultiplier = 0;
                        }
                        pitch = (float)redstoneClockTileEntity.tickMultiplier / redstoneClockTileEntity.cooldown.length;
                    }
                    MalumHelper.makeMachineToggleSound(worldIn, pos, 1f + pitch);
                    player.world.notifyBlockUpdate(pos, state, state, 3);
                    player.swingArm(handIn);
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return ActionResultType.SUCCESS;
    }
}