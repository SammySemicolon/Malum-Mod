package com.sammy.malum.core.mod_systems.multiblock;

import com.sammy.malum.core.mod_systems.tile.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;

public abstract class BoundingBlockTileEntity extends SimpleTileEntity
{
    public BlockPos ownerPos;
    
    public BoundingBlockTileEntity(TileEntityType type)
    {
        super(type);
        ownerPos = new BlockPos.Mutable();
    }
    
    @Override
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.putIntArray("pos", new int[]{ownerPos.getX(), ownerPos.getY(), ownerPos.getZ()});
        return super.writeData(compound);
    }
    
    @Override
    public void readData(CompoundNBT compound)
    {
        int[] positions = compound.getIntArray("pos");
        ownerPos = new BlockPos(positions[0], positions[1], positions[2]);
        super.readData(compound);
    }
}