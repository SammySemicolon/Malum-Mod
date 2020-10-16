package com.sammy.malum.network.packets;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateStaveNBT
{
    public ItemStack stave;
    public int value;
    public UpdateStaveNBT(ItemStack stave, int value)
    {
        this.stave = stave;
        this.value = value;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeItemStack(stave);
        buf.writeInt(value);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> stave.getOrCreateTag().putInt("malum:staveOption", value));
        context.get().setPacketHandled(true);
    }

    public static UpdateStaveNBT decode(PacketBuffer buf)
    {
        ItemStack stave = buf.readItemStack();
        int value = buf.readInt();
        return new UpdateStaveNBT(stave, value);
    }
}