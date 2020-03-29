package com.kittykitcatcat.malum.blocks.souljar;

import com.kittykitcatcat.malum.init.ModTileEntities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

public class SoulJarTileEntity extends TileEntity implements ITickableTileEntity
{
    public SoulJarTileEntity()
    {
        super(ModTileEntities.soul_jar_tile_entity);
    }
    public float purity;
    public String entityRegistryName;
    public String entityDisplayName;
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        compound.putFloat("purity", purity);
        if (entityRegistryName != null)
        {
            compound.putString("entityRegistryName", entityRegistryName);
        }
        if (entityDisplayName != null)
        {
            compound.putString("entityDisplayName", entityDisplayName);
        }
        return compound;
    }
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        entityRegistryName = compound.getString("entityRegistryName");
        entityDisplayName = compound.getString("entityDisplayName");
        purity = compound.getFloat("purity");
    }
    @Override
    public void tick()
    {
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
}