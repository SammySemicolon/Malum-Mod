package com.sammy.malum.common.blocks.arcanecraftingtable;

import com.sammy.malum.core.init.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntityType;

public class ArcaneCraftingTableTileEntity extends SimpleInventoryTileEntity
{
    public ArcaneCraftingTableTileEntity()
    {
        super(MalumTileEntities.ARCANE_CRAFTING_TABLE_TILE_ENTITY.get());
        inventory = new SimpleInventory(9, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ArcaneCraftingTableTileEntity.this.markDirty();
                if (!world.isRemote)
                {
                    updateContainingBlockInfo();
                    BlockState state = world.getBlockState(pos);
                    world.notifyBlockUpdate(pos, state, state, 3);
                }
            }
        };
    }
}