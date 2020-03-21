package com.kittykitcatcat.malum.blocks.spiritfurnace;

import com.kittykitcatcat.malum.MalumMod;
import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class SpiritFurnaceTileEntity extends TileEntity implements ITickableTileEntity
{
    public SpiritFurnaceTileEntity()
    {
        super(ModTileEntities.spirit_furnace_tile_entity);

    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.write(new CompoundNBT());
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
        MalumMod.LOGGER.info("gay");
    }
}