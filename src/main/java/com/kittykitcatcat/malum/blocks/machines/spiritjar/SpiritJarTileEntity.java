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
    public void tick()
    {

    }
}