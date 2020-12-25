package com.sammy.malum.common.blocks.spiritpipe;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.client.ClientHelper;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.block.ISpiritTransferTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import static com.sammy.malum.common.blocks.spiritpipe.SpiritPipeBlock.notifyNeighbors;

public class OpenTransmissiveMetalBlock extends Block
{
    public OpenTransmissiveMetalBlock(Properties properties)
    {
        super(properties);
    }
//    @Override
//    public TileEntity createTileEntity(BlockState state, IBlockReader world)
//    {
//        return new SpiritPipeTileEntity();
//    }
//
//    @Override
//    public boolean hasTileEntity(BlockState state)
//    {
//        return true;
//    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (worldIn.getTileEntity(pos) instanceof ISpiritTransferTileEntity)
        {
            ISpiritTransferTileEntity tileEntity = (ISpiritTransferTileEntity) worldIn.getTileEntity(pos);
            player.sendStatusMessage(ClientHelper.simpleComponent("" + tileEntity.getNeedsUpdate()), true);
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
    
    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving)
    {
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos, isMoving);
        if (MalumHelper.areWeOnServer(worldIn))
        {
            if (SpiritHelper.isSpiritRelated(worldIn.getTileEntity(fromPos)))
            {
                if (worldIn.getTileEntity(pos) instanceof ISpiritTransferTileEntity)
                {
                    ISpiritTransferTileEntity tileEntity = (ISpiritTransferTileEntity) worldIn.getTileEntity(pos);
                    tileEntity.setNeedsUpdate(true);
                    notifyNeighbors(worldIn, pos, blockIn);
                }
            }
        }
    }
}
