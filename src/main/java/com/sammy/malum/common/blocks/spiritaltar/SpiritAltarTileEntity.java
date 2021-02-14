package com.sammy.malum.common.blocks.spiritaltar;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.spiritkiln.functional.SpiritKilnCoreTileEntity;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.modcontent.MalumSpiritKilnRecipes;
import com.sammy.malum.core.systems.inventory.SimpleInventory;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

public class SpiritAltarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritAltarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_ALTAR_TILE_ENTITY.get());
    
        spiritInventory = new SimpleInventory(1, 64)
        {
            @Override
            protected void onContentsChanged(int slot)
            {
                SpiritAltarTileEntity.this.markDirty();
                updateContainingBlockInfo();
                MalumHelper.updateState(world.getBlockState(pos), world, pos);
            }
        };
    }
    
    public SimpleInventory spiritInventory;
    
    int progress;
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (progress != 0)
        {
            compound.putInt("progress", progress);
        }
        spiritInventory.writeData(compound, "spiritInventory");
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        progress = compound.getInt("progress");
        spiritInventory.readData(compound, "spiritInventory");
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
    
    }
}