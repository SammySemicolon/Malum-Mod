package com.sammy.malum.common.blocks.itempedestal;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ItemPedestalTileEntity extends SimpleInventoryTileEntity
{
    public ItemPedestalTileEntity()
    {
        super(MalumTileEntities.ITEM_PEDESTAL_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemPedestalTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    public static Vector3d itemOffset()
    {
        return new Vector3d(0.5f, 1.1f, 0.5f);
    }
}
