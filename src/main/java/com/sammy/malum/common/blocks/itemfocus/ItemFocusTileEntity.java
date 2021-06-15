package com.sammy.malum.common.blocks.itemfocus;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritaltar.IAltarProvider;
import com.sammy.malum.common.blocks.spiritaltar.SpiritAltarTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.common.rites.RiteOfAssembly;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

public class ItemFocusTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity, RiteOfAssembly.IAssembled
{
    public static final int PRESS_DURATION = 2;
    public boolean active;
    public int pressDistance;
    public float pressSpin;
    public float pressSpinUp;
    public int progress;
    public int spin;
    public SimpleInventory apparatusInventory;
    public ItemFocusTileEntity()
    {
        super(MalumTileEntities.ITEM_FOCUS_TILE_ENTITY.get());
        inventory = new SimpleInventory(3, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                ItemFocusTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
        apparatusInventory = new SimpleInventory(2, 1)
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
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("progress", progress);
        compound.putInt("pressDistance", pressDistance);
        compound.putFloat("pressSpinUp", pressSpinUp);
        compound.putBoolean("active", active);
        apparatusInventory.writeData(compound, "apparatus_inventory");
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        pressDistance = compound.getInt("pressDistance");
        pressSpinUp = compound.getFloat("pressSpinUp");
        active = compound.getBoolean("active");
        apparatusInventory.readData(compound, "apparatus_inventory");
        super.readData(compound);
    }

    public static Vector3d itemOffset(ItemFocusTileEntity tileEntity, int slot)
    {
        int nonEmpty = tileEntity.inventory.nonEmptyItems();
        if (nonEmpty == 1)
        {
            return new Vector3d(0.5, 1.5, 0.5);
        }
        float press = Math.min(PRESS_DURATION,tileEntity.progress);
        float pressPercentage = 0.25f * (press / PRESS_DURATION);
        return MalumHelper.rotatedCirclePosition(new Vector3d(0.5f,1.5f,0.5f), 0.5f-pressPercentage,slot, nonEmpty, (long)tileEntity.spin,360);
    }

    @Override
    public void assemble()
    {
        MalumHelper.updateAndNotifyState(world, pos);

        active = true;
    }
    @Override
    public void tick()
    {
        pressDistance += inventory.nonEmptyItems() == 0 ? (pressDistance > 0 ? -1 : 0) : (pressDistance < 20 ? 1 : 0);

        if (active)
        {
            if (pressSpinUp > 0)
            {
                pressSpinUp -= 0.2f;
            }
            progress++;
            if (progress > 60)
            {
                active = false;
                progress = 10;
            }
        }
        else
        {
            if (pressSpinUp < 1)
            {
                pressSpinUp +=0.2f;
            }
            if (progress > 0)
            {
                progress--;
            }
        }
        if (MalumHelper.areWeOnClient(world))
        {
            passiveParticles();
        }
    }
    public void passiveParticles()
    {
        pressSpin += pressSpinUp;
        spin += 1;
        for (int i = 0; i < inventory.slotCount; i++)
        {
            ItemStack item = inventory.getStackInSlot(i);
            if (item.getItem() instanceof SpiritItem)
            {
                Vector3d offset = itemOffset(this, i);
                double x = getPos().getX() + offset.getX();
                double y = getPos().getY() + offset.getY();
                double z = getPos().getZ() + offset.getZ();
                SpiritItem spiritSplinterItem = (SpiritItem) item.getItem();
                Color color = spiritSplinterItem.type.color;


                ParticleManager.create(MalumParticles.SPARKLE_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(10)
                        .setScale(0.25f, 0)
                        .setColor(color.brighter(), color.darker())
                        .enableNoClip()
                        .repeat(world, x, y, z, 2);

                ParticleManager.create(MalumParticles.WISP_PARTICLE)
                        .setAlpha(0.2f, 0f)
                        .setLifetime(80)
                        .setSpin(0.1f)
                        .setScale(0.16f, 0)
                        .setColor(color, color.darker())
                        .enableNoClip()
                        .repeat(world, x, y, z, 1);

            }
        }
    }
}