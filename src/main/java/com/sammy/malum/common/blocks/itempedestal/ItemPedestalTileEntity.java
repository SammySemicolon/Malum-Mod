package com.sammy.malum.common.blocks.itempedestal;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.IAltarProvider;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

public class ItemPedestalTileEntity extends SimpleInventoryTileEntity implements IAltarProvider, ITickableTileEntity
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

    @Override
    public SimpleInventory providedInventory()
    {
        return inventory;
    }
    @Override
    public Vector3d providedItemPos()
    {
        return itemPos(this);
    }
    public static Vector3d itemPos(SimpleTileEntity tileEntity)
    {
        return MalumHelper.pos(tileEntity.getPos()).add(0.5f,1.15f,0.5f);
    }
    public static Vector3d itemOffset()
    {
        return new Vector3d(0.5f, 1.1f, 0.5f);
    }

    @Override
    public void tick()
    {

    }
}
