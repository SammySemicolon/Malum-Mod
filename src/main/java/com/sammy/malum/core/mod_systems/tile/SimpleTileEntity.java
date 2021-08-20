package com.sammy.malum.core.mod_systems.tile;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public abstract class SimpleTileEntity extends TileEntity
{
    public SimpleTileEntity(TileEntityType type)
    {
        super(type);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        return writeData(compound);
    }
    
    @Override
    public void read(BlockState state, CompoundNBT compound)
    {
        super.read(state, compound);
        readData(compound);
    }
    
    @Override
    public CompoundNBT getUpdateTag()
    {
        return write(new CompoundNBT());
    }
    
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        CompoundNBT nbt = new CompoundNBT();
        writeData(nbt);
        return new SUpdateTileEntityPacket(pos, 0, nbt);
    }
    
    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket packet)
    {
        super.onDataPacket(net, packet);
        readData(packet.getNbtCompound());
    }
    
    public void readData(CompoundNBT compound)
    {
    
    }
    public CompoundNBT writeData(CompoundNBT compound)
    {
        return compound;
    }
}