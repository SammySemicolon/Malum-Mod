package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.spirit_altar.IAltarProvider;
import com.sammy.malum.common.item.misc.MalumSpiritItem;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.blockentity.SimpleBlockEntityInventory;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import com.sammy.malum.core.systems.blockentity.SimpleInventoryBlockEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

import static net.minecraft.state.properties.BlockStateProperties.FACING;

public class ItemStandTileEntity extends SimpleInventoryBlockEntity implements IAltarProvider, ITickableTileEntity
{
    public ItemStandTileEntity()
    {
        super(TileEntityRegistry.ITEM_STAND_TILE_ENTITY.get());
        inventory = new SimpleBlockEntityInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemStandTileEntity.this.setChanged();
                clearCache();
                MalumHelper.updateAndNotifyState(level, LevelPosition);
            }
        };
    }

    @Override
    public SimpleBlockEntityInventory providedInventory()
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
        return MalumHelper.pos(tileEntity.getBlockPos()).add(itemOffset(tileEntity));
    }
    public static Vector3d itemOffset(SimpleTileEntity tileEntity)
    {
        Direction direction = tileEntity.getBlockState().getValue(FACING);
        Vector3d directionVector = new Vector3d(direction.getStepX(), 0.5f, direction.getStepZ());
        return new Vector3d(0.5f - directionVector.x() * 0.25f, directionVector.y(), 0.5f - directionVector.z() * 0.25f);
    }

    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnServer(level))
        {
            return;
        }
        if (inventory.getStackInSlot(0).getItem() instanceof MalumSpiritItem)
        {
            MalumSpiritItem item = (MalumSpiritItem) inventory.getStackInSlot(0).getItem();
            Color color = item.type.color;
            Vector3d pos = itemPos(this);
            double x = pos.x;
            double y = pos.y + Math.sin((level.getGameTime() % 360) / 20f) * 0.05f;
            double z = pos.z;
            SpiritHelper.spawnSpiritParticles(level, x,y,z, color);
        }
    }
}
