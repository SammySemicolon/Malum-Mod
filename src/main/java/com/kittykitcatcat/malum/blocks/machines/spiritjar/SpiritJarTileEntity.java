package com.kittykitcatcat.malum.blocks.machines.spiritjar;

import com.kittykitcatcat.malum.blocks.utility.soulstorage.SpiritStoringTileEntity;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;

import javax.annotation.Nullable;

public class SpiritJarTileEntity extends SpiritStoringTileEntity implements ITickableTileEntity
{
    public SpiritJarTileEntity()
    {
        super(ModTileEntities.spirit_jar_tile_entity);
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

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        return new SUpdateTileEntityPacket(pos, 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt)
    {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public void tick()
    {

    }
}