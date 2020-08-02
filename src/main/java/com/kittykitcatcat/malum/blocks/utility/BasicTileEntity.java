package com.kittykitcatcat.malum.blocks.utility;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public abstract class BasicTileEntity extends TileEntity
{
    public BasicTileEntity(TileEntityType type)
    {
        super(type);
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
    public CompoundNBT getUpdateTag()
    {
        return write(new CompoundNBT());
    }
    @Override
    public void handleUpdateTag(CompoundNBT tag)
    {
        read(tag);
    }
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        this.write(nbt);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }
}