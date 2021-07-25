package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.block.MalumTileEntities;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import com.sammy.malum.core.systems.tile.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

import java.awt.*;

public class SpiritJarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritJarTileEntity()
    {
        super(MalumTileEntities.SPIRIT_JAR_TILE_ENTITY.get());
    }
    
    public MalumSpiritType type;
    public int count;
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (type != null)
        {
            compound.putString("spirit", type.identifier);
        }
        if (count != 0)
        {
            compound.putInt("count", count);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        if (compound.contains("spirit"))
        {
            type = SpiritHelper.figureOutType(compound.getString("spirit"));
        }
        count = compound.getInt("count");
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(world))
        {
            if (type != null)
            {
                double x = getPos().getX() + 0.5f;
                double y = getPos().getY() + 0.5f + Math.sin(world.getGameTime() / 20f) * 0.2f;
                double z = getPos().getZ() + 0.5f;
                Color color = type.color;
                SpiritHelper.spiritParticles(world, x,y,z, color);
            }
        }
    }
}