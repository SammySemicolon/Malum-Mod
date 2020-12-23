package com.sammy.malum.core.systems.multiblock;

import com.sammy.malum.core.init.blocks.MalumTileEntities;
import com.sammy.malum.core.systems.tileentities.SimpleTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;

public class BoundingBlockTileEntity extends SimpleTileEntity
{
    public BlockPos ownerPos;
    
    public BoundingBlockTileEntity()
    {
        super(MalumTileEntities.BOUNDING_BLOCK_TILE_ENTITY.get());
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
    
    @Override
    public void remove()
    {
        if (world.getTileEntity(ownerPos) instanceof MultiblockTileEntity)
        {
            world.removeBlock(ownerPos, true);
        }
    }
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap)
    {
        if (world.getTileEntity(ownerPos) instanceof MultiblockTileEntity)
        {
            return world.getTileEntity(ownerPos).getCapability(cap);
        }
        return super.getCapability(cap);
    }
    
    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, Direction side)
    {
        if (world.getTileEntity(ownerPos) instanceof MultiblockTileEntity)
        {
            return world.getTileEntity(ownerPos).getCapability(cap, side);
        }
        return super.getCapability(cap,side);
    }
}