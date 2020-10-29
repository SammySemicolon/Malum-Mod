package com.sammy.malum.network.packets;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.items.utility.IConfigurableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateStaveNBT
{
    public int slot;
    public int value;
    
    public UpdateStaveNBT(int slot, int value)
    {
        this.slot = slot;
        this.value = value;
    }
    
    public static UpdateStaveNBT decode(PacketBuffer buf)
    {
        int slot = buf.readInt();
        int value = buf.readInt();
        return new UpdateStaveNBT(slot, value);
    }
    
    public void encode(PacketBuffer buf)
    {
        buf.writeInt(slot);
        buf.writeInt(value);
    }
    
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            ItemStack stack = slot == -621 ? context.get().getSender().getHeldItemOffhand() : context.get().getSender().inventory.getStackInSlot(slot);
            
            if (stack.getItem() instanceof IConfigurableItem)
            {
                int tag = stack.getOrCreateTag().getInt("malum:itemOption");
                stack.getTag().putInt("malum:itemOption", tag + value);
                ClientHandler.makeItemConfigMessage((IConfigurableItem) stack.getItem(), context.get().getSender(), tag + value);
            }
        });
        context.get().setPacketHandled(true);
    }
}