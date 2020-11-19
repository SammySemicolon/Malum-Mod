package com.sammy.malum.common.blocks.abstruceblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class AbstruseBlock extends Block
{
    public AbstruseBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public boolean hasTileEntity(BlockState state)
    {
        return false;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new AbstruseBlockTileEntity();
    }
    
    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        if (entityIn instanceof PlayerEntity)
        {
            if (worldIn.getTileEntity(pos) instanceof AbstruseBlockTileEntity)
            {
                AbstruseBlockTileEntity tileEntity = (AbstruseBlockTileEntity) worldIn.getTileEntity(pos);
                if (tileEntity.owner.equals(entityIn))
                {
                    tileEntity.progress = 0;
                }
            }
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack)
    {
        if (worldIn.isRemote())
        {
             if (placer instanceof PlayerEntity)
            {
                if (worldIn.getTileEntity(pos) instanceof AbstruseBlockTileEntity)
                {
                    ((AbstruseBlockTileEntity) worldIn.getTileEntity(pos)).owner = (PlayerEntity) placer;
                }
            }
        }
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }
}
