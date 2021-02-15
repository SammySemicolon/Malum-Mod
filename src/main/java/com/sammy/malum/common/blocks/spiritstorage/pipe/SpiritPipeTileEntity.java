package com.sammy.malum.common.blocks.spiritstorage.pipe;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.MalumSounds;
import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.modcontent.MalumSpiritTypes;
import com.sammy.malum.core.systems.particles.ParticleManager;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraftforge.items.ItemHandlerHelper;

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