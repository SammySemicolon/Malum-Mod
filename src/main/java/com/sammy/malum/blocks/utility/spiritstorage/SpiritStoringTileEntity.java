package com.sammy.malum.blocks.utility.spiritstorage;

import com.sammy.malum.blocks.utility.BasicTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

import static com.sammy.malum.SpiritDataHelper.countNBT;
import static com.sammy.malum.SpiritDataHelper.typeNBT;

public class SpiritStoringTileEntity extends BasicTileEntity
{
    public String type;
    public int count;
    
    public SpiritStoringTileEntity(TileEntityType<? extends SpiritStoringTileEntity> tileEntityType)
    {
        super(tileEntityType);
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        if (type != null)
        {
            compound.putString(typeNBT, type);
        }
        if (count != 0)
        {
            compound.putInt(countNBT, count);
        }
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (compound.contains(typeNBT))
        {
            type = compound.getString(typeNBT);
        }
        if (compound.contains(countNBT))
        {
            count = compound.getInt(countNBT);
        }
    }
}