package com.sammy.malum.common.blocks.itemstand;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

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
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().get(FACING);
        Vector3d directionVector = new Vector3d(direction.getXOffset(), 0.5f, direction.getZOffset());
        return new Vector3d(0.5f - directionVector.getX() * 0.25f, directionVector.getY(), 0.5f - directionVector.getZ() * 0.25f);
    }
}
