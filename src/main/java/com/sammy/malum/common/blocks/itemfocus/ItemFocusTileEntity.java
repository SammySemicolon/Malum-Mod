package com.sammy.malum.common.blocks.itemfocus;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.IAltarProvider;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public class ItemFocusTileEntity extends SimpleInventoryTileEntity implements IAltarProvider, ITickableTileEntity
{
    public ItemFocusTileEntity()
    {
        super(MalumTileEntities.ITEM_FOCUS_TILE_ENTITY.get());
        inventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemFocusTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
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
        return MalumHelper.pos(tileEntity.getPos()).add(itemOffset());
    }
    public static Vector3d itemOffset()
    {
        return new Vector3d(0.5f, 1.2f, 0.5f);
    }

    @Override
    public void tick()
    {
        if (inventory.getStackInSlot(0).getItem() instanceof SpiritItem)
        {
            SpiritItem item = (SpiritItem) inventory.getStackInSlot(0).getItem();
            Color color = item.type.color;
            Vector3d pos = itemPos(this);
            double x = pos.x;
            double y = pos.y + Math.sin(world.getGameTime() / 20f) * 0.1f;
            double z = pos.z;
            ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                    .setAlpha(0.2f, 0f)
                    .setLifetime(10)
                    .setScale(0.3f, 0)
                    .setColor(color.brighter(), color.darker())
                    .enableNoClip()
                    .repeat(world, x,y,z, 2);

            ParticleManager.create(MalumParticles.WISP_PARTICLE)
                    .setAlpha(0.2f, 0f)
                    .setLifetime(80)
                    .setSpin(0.1f)
                    .setScale(0.2f, 0)
                    .setColor(color, color.darker())
                    .enableNoClip()
                    .repeat(world, x,y,z, 1);
        }
    }
}
