package com.sammy.malum.blocks.utility.multiblock;


import com.sammy.malum.blocks.machines.funkengine.FunkEngineTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

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
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world)
    {
        return new BoundingBlockTileEntity();
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                if (worldIn.getTileEntity(pos) instanceof BoundingBlockTileEntity)
                {
                    BoundingBlockTileEntity tileEntity = (BoundingBlockTileEntity) worldIn.getTileEntity(pos);
                    if (worldIn.getBlockState(tileEntity.ownerPos).getBlock() instanceof MultiblockBlock)
                    {
                        MultiblockBlock block = (MultiblockBlock) worldIn.getBlockState(tileEntity.ownerPos).getBlock();
                        return block.activateBlock(state, worldIn, tileEntity.ownerPos, player, handIn, hit, pos);
                    }
                }
            }
        }
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }
}