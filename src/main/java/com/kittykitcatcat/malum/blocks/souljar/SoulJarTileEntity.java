package com.kittykitcatcat.malum.blocks.souljar;

import com.kittykitcatcat.malum.SpiritData;
import com.kittykitcatcat.malum.init.ModTileEntities;
import com.kittykitcatcat.malum.network.packets.SpiritWhisperPacket;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;

import static com.kittykitcatcat.malum.network.NetworkManager.INSTANCE;

public class SoulJarTileEntity extends TileEntity implements ITickableTileEntity
{
    public SoulJarTileEntity()
    {
        super(ModTileEntities.soul_jar_tile_entity);
    }
    public float delayedPurity;
    public SpiritData data;
    @Override
    public CompoundNBT write(CompoundNBT compound)
    {
        super.write(compound);
        if (data != null)
        {
            data.writeSpiritDataIntoNBT(compound);
        }
        return compound;
    }
    @Override
    public void read(CompoundNBT compound)
    {
        super.read(compound);
        if (SpiritData.hasSpiritDataInNBT(compound))
        {
            data = SpiritData.readSpiritDataFromNBT(compound);
        }
    }
    @Override
    public void tick()
    {
        if (data != null)
        {
            if (data.purity != 0)
            {
                if (MathHelper.nextInt(world.rand, 0, 200) == 0)
                {
                    if (!world.isRemote)
                    {
                        INSTANCE.send(
                                PacketDistributor.TRACKING_CHUNK.with(() -> this.world.getChunkAt(pos)),
                                new SpiritWhisperPacket(pos.getX(), pos.getY(), pos.getZ()));
                    }
                }
                delayedPurity = MathHelper.lerp(0.95f, data.purity, delayedPurity);
            }
            else
            {
                delayedPurity = 0;
            }
        }
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