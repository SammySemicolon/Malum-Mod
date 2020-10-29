package com.sammy.malum.items;

import com.sammy.malum.blocks.utility.multiblock.BoundingBlockTileEntity;
import com.sammy.malum.blocks.utility.multiblock.MultiblockStructure;
import com.sammy.malum.blocks.utility.multiblock.MultiblockTileEntity;
import com.sammy.malum.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;

import static net.minecraft.world.World.isValid;

public class MultiblockItem extends BlockItem
{
    public MultiblockStructure structure;
    public MultiblockItem(Block blockIn, Properties builder, MultiblockStructure structure)
    {
        super(blockIn, builder);
        this.structure = structure;
    }
    
    @Override
    protected boolean placeBlock(BlockItemUseContext context, BlockState state)
    {
        World world = context.getWorld();
        BlockPos placePos = context.getPos();
        for (BlockPos pos : structure.occupiedPositions)
        {
            BlockPos targetPos = placePos.add(pos);
            if (!isValid(targetPos) || !world.getBlockState(targetPos).getMaterial().isReplaceable())
            {
                return false;
            }
        }
        return super.placeBlock(context, state);
    }
    
    @Override
    protected boolean onBlockPlaced(BlockPos placePos, World world, PlayerEntity player, ItemStack stack, BlockState state)
    {
        for (BlockPos pos : structure.occupiedPositions)
        {
            BlockPos targetPos = placePos.add(pos);
            if (isValid(targetPos) && world.getBlockState(targetPos).getMaterial().isReplaceable())
            {
                world.setBlockState(targetPos, ModBlocks.bounding_block.getDefaultState());
                if (world.getTileEntity(targetPos) instanceof BoundingBlockTileEntity)
                {
                    BoundingBlockTileEntity tileEntity = (BoundingBlockTileEntity) world.getTileEntity(targetPos);
                    tileEntity.ownerPos = placePos;
                }
            }
        }
        if (world.getTileEntity(placePos)instanceof MultiblockTileEntity)
        {
            MultiblockTileEntity tileEntity = (MultiblockTileEntity) world.getTileEntity(placePos);
            tileEntity.positions = new ArrayList<>();
            for (BlockPos pos : structure.occupiedPositions)
            {
                BlockPos targetPos = placePos.add(pos);
                tileEntity.positions.add(targetPos);
            }
        }
        return super.onBlockPlaced(placePos, world, player, stack, state);
    }
}
