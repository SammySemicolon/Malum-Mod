package com.sammy.malum.blocks.machines.spiritsmeltery;

import com.sammy.malum.blocks.utility.multiblock.MultiblockTileEntity;
import com.sammy.malum.init.ModTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;

public class SpiritSmelteryTileEntity extends MultiblockTileEntity implements ITickableTileEntity
{
    
    public SpiritSmelteryTileEntity()
    {
        super(ModTileEntities.spirit_smeltery_tile_entity);
    }
    
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        return compound;
    }
    
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
    }
    @Override
    public void tick()
    {
    }
}