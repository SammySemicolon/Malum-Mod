package com.sammy.malum.core.systems.spirits.block;

import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;

public class SimpleSpiritHolderTileEntity extends SimpleTileEntity implements ISpiritHolderTileEntity
{
    protected String spiritType;
    protected int count;
    
    public SimpleSpiritHolderTileEntity(TileEntityType<?> tileEntityTypeIn)
    {
        super(tileEntityTypeIn);
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        super.writeData(compound);
        if (spiritType != null)
        {
            compound.putString("type", spiritType);
        }
        else
        {
            compound.putByte("typeIsNull", (byte) 0);
        }
        compound.putInt("count", count);
        return compound;
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        super.readData(compound);
        if (compound.contains("typeIsNull"))
        {
            spiritType = null;
            count = 0;
            return;
        }
        if (compound.contains("type"))
        {
            spiritType = compound.getString("type");
        }
        count = compound.getInt("count");
        
    }
    
    @Override
    public int maxSpirits()
    {
        return 0;
    }
    
    @Override
    public String getSpiritType()
    {
        return spiritType;
    }
    
    @Override
    public int currentSpirits()
    {
        return count;
    }
    
    @Override
    public void setSpiritType(String spiritType)
    {
        this.spiritType = spiritType;
    }
    
    @Override
    public void setSpirits(int count)
    {
        this.count = count;
    }
}