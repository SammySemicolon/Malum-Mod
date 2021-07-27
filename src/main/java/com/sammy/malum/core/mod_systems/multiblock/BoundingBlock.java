package com.sammy.malum.core.mod_systems.multiblock;

import com.sammy.malum.ClientHelper;
import com.sammy.malum.MalumHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BoundingBlock extends Block
{
    public BoundingBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hasTileEntity(final BlockState state)
    {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return null;//new BoundingBlockTileEntity();
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        if (worldIn.getTileEntity(pos) instanceof BoundingBlockTileEntity)
        {
            BoundingBlockTileEntity tileEntity = (BoundingBlockTileEntity) worldIn.getTileEntity(pos);
            if (worldIn.getTileEntity(tileEntity.ownerPos) instanceof MultiblockTileEntity)
            {
                if (MalumHelper.areWeOnClient(worldIn))
                {
                    ClientHelper.spawnBlockParticles(tileEntity.ownerPos, worldIn.getBlockState(tileEntity.ownerPos));
                }
                spawnDrops(worldIn.getBlockState(tileEntity.ownerPos), worldIn, pos, worldIn.getTileEntity(tileEntity.ownerPos), player, player.getHeldItemMainhand());
                worldIn.removeBlock(tileEntity.ownerPos, true);
            }
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }
}
