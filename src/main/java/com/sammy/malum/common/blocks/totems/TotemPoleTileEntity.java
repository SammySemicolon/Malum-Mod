package com.sammy.malum.common.blocks.totems;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Direction;

public class TotemPoleTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public TotemPoleTileEntity()
    {
        super(MalumTileEntities.TOTEM_POLE_TILE_ENTITY.get());
    }
    
    public MalumSpiritType type;
    public boolean active;
    public int activeTime;
    public int expectedActiveTime;
    public Direction direction;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putBoolean("active", active);
        compound.putInt("activeTime", activeTime);
        compound.putInt("expectedActiveTime", expectedActiveTime);
        if (direction != null)
        {
            compound.putInt("direction", direction.getIndex());
        }
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
        activeTime = compound.getInt("activeTime");
        expectedActiveTime = compound.getInt("expectedActiveTime");
        direction = Direction.values()[compound.getInt("direction")];
        type = SpiritHelper.figureOutType(compound.getString("type"));
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        if (activeTime < expectedActiveTime)
        {
            activeTime++;
        }
        if (activeTime > expectedActiveTime)
        {
            activeTime--;
        }
    }
}