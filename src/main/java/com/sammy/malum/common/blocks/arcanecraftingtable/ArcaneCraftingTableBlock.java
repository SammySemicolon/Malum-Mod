package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.core.systems.tileentities.SimpleInventoryBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class ArcaneCraftingTableBlock extends SimpleInventoryBlock
{
    public ArcaneCraftingTableBlock(Properties properties)
    {
        super(properties);
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world)
    {
        return new ArcaneCraftingTableTileEntity();
    }
}
