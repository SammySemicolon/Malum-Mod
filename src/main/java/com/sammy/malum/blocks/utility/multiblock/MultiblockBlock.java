package com.sammy.malum.blocks.utility.multiblock;


import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class MultiblockBlock extends Block
{
    public MultiblockBlock(Properties properties)
    {
        super(properties.notSolid().noDrops());
    }
    
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (!worldIn.isRemote())
        {
            if (handIn != Hand.OFF_HAND)
            {
                return activateBlock(state, worldIn, pos, player, handIn, hit, pos);
            }
        }
        return ActionResultType.FAIL;
    }
    
    public ActionResultType activateBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit, BlockPos boundingBlockSource)
    {
        return ActionResultType.FAIL;
    }
}