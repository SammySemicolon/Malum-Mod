package com.sammy.malum.core.systems.essences;

import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class EssenceHolderTileEntity extends SimpleTileEntity implements IEssenceHolderTileEntity
{
    public String type;
    public int count;
    public EssenceHolderTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        super.writeData(compound);
        if (type != null)
        {
            compound.putString("type", type);
        }
        if (count != 0)
        {
            compound.putInt("count", count);
        }
        return compound;
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        super.readData(compound);
        if (compound.contains("type"))
        {
            type = compound.getString("type");
        }
        if (compound.contains("count"))
        {
            count = compound.getInt("count");
        }
    }
    
    @Override
    public int getMaxEssence()
    {
        return 0;
    }
    
    @Override
    public String getEssenceType()
    {
        return type;
    }
    
    @Override
    public int getEssenceCount()
    {
        return count;
    }
    
    @Override
    public void setType(String type)
    {
        this.type = type;
    }
    
    @Override
    public void setCount(int count)
    {
        this.count = count;
    }
}
