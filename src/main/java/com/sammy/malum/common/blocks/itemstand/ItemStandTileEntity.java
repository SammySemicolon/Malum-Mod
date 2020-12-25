package com.sammy.malum.common.blocks.itemstand;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;

public class ItemStandTileEntity extends SimpleInventoryTileEntity
{
    public ItemStandTileEntity()
    {
        super(MalumTileEntities.ITEM_STAND_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemStandTileEntity.this.markDirty();
                updateContainingBlockInfo();
                updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
}
