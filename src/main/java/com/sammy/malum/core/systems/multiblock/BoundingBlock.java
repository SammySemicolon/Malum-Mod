package com.sammy.malum.core.systems.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BoundingBlock extends Block
{
    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player)
    {
        BlockPos pos1 = pos.down();
        BlockState state1 = worldIn.getBlockState(pos1);
        TileEntity entity = worldIn.getTileEntity(pos1);
        spawnDrops(state1, worldIn, pos1, entity, player, player.getHeldItemMainhand());
    
        super.onBlockHarvested(worldIn, pos, state, player);
    }
    
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
    
    public BlockState multiblockState(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state, BlockPos pos)
    {
        return getDefaultState();
    }
}
