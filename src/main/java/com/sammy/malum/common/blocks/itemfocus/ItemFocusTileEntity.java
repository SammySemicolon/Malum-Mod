package com.sammy.malum.common.blocks.itemfocus;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.arcanecompressor.ArcaneCompressorTileEntity;
import com.sammy.malum.common.items.SpiritItem;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumCompressingRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleInventoryTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.math.vector.Vector3d;

import java.awt.*;

import static com.sammy.malum.common.blocks.arcanecompressor.ArcaneCompressorTileEntity.PRESS_DURATION;

public class ItemFocusTileEntity extends SimpleInventoryTileEntity implements ITickableTileEntity
{
    public int progress;
    public int spin;
    public MalumCompressingRecipes.ArcaneCompressorRecipe recipe;
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
                recipe = MalumCompressingRecipes.getRecipe(stacks());
                updateFocus();
                MalumHelper.updateAndNotifyState(world, pos);
            }
        };
    }

    public void updateFocus()
    {
        if (world.getTileEntity(pos.up(2)) instanceof ArcaneCompressorTileEntity)
        {
            ArcaneCompressorTileEntity arcaneCompressorTileEntity = (ArcaneCompressorTileEntity) world.getTileEntity(pos.up(2));
            arcaneCompressorTileEntity.updateFocus(pos);
        }
    }
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putInt("progress", progress);
        return super.writeData(compound);
    }

    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        super.readData(compound);
        recipe = MalumCompressingRecipes.getRecipe(inventory.stacks());
        updateFocus();
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
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            passiveParticles();
        }
    }
    public void passiveParticles()
    {
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