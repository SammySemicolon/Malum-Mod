package com.sammy.malum.common.blocks.spiritpipe;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.spirits.MalumSpiritType;
import com.sammy.malum.core.systems.spirits.SpiritHelper;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

import java.awt.*;

public class SpiritPipeTileEntity extends SimpleTileEntity
{
    public SpiritPipeTileEntity()
    {
        super(MalumTileEntities.SPIRIT_PIPE_TILE_ENTITY.get());
    }

    public boolean needsUpdate;

    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        if (needsUpdate)
        {
            compound.putBoolean("needsUpdate", true);
        }
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        needsUpdate = compound.getBoolean("needsUpdate");
        super.readData(compound);
    }
}