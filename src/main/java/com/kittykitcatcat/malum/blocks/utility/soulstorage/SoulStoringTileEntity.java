package com.kittykitcatcat.malum.blocks.utility.soulstorage;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

import static com.kittykitcatcat.malum.SpiritDataHelper.countNBT;
import static com.kittykitcatcat.malum.SpiritDataHelper.typeNBT;

public class SoulStoringTileEntity extends TileEntity
{
    public String type;
    public int count;
    public SoulStoringTileEntity(TileEntityType<? extends SoulStoringTileEntity> tileEntityType)
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