package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;

public class TotemPoleTileEntity extends SimpleTileEntity
{
    public TotemPoleTileEntity()
    {
        super(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get());
    }
    
    public MalumSpiritType type;
    public boolean active;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        if (type != null)
        {
            compound.putString("type", type.identifier);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        active = compound.getBoolean("active");
        type = SpiritHelper.figureOutType(compound.getString("type"));
        super.readData(compound);
    }
}