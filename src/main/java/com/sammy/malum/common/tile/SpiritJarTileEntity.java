package com.sammy.malum.common.tile;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.registry.block.TileEntityRegistry;
import com.sammy.malum.core.systems.spirit.MalumSpiritType;
import com.sammy.malum.core.systems.spirit.SpiritHelper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

import java.awt.*;

public class SpiritJarTileEntity extends SimpleTileEntity implements ITickableTileEntity
{
    public SpiritJarTileEntity()
    {
        super(TileEntityRegistry.SPIRIT_JAR_TILE_ENTITY.get());
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
            type = SpiritHelper.getSpiritType(compound.getString("spirit"));
        }
        count = compound.getInt("count");
        super.readData(compound);
    }
    
    @Override
    public void tick()
    {
        if (MalumHelper.areWeOnClient(level))
        {
            if (type != null)
            {
                double x = getBlockPos().getX() + 0.5f;
                double y = getBlockPos().getY() + 0.5f + Math.sin(level.getGameTime() / 20f) * 0.2f;
                double z = getBlockPos().getZ() + 0.5f;
                Color color = type.color;
                SpiritHelper.spawnSpiritParticles(level, x,y,z, color);
            }
        }
    }
}