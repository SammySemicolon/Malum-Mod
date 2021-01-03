package com.sammy.malum.server.network.packets;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.MalumMod;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.function.Supplier;

public class SplinterHandlePacket
{
    ItemStack returnStack;
    ItemStack slottedStack;
    int slot;
    public SplinterHandlePacket(ItemStack returnStack,ItemStack slottedStack, int slot)
    {
        this.returnStack = returnStack;
        this.slottedStack = slottedStack;
        this.slot = slot;
    }
    
    public static SplinterHandlePacket decode(PacketBuffer buf)
    {
        ItemStack returnStack = buf.readItemStack();
        ItemStack slottedStack = buf.readItemStack();
        int slot = buf.readInt();
        return new SplinterHandlePacket(returnStack, slottedStack,slot);
    }
    
    public void encode(PacketBuffer buf)
    {
        buf.writeItemStack(returnStack);
        buf.writeItemStack(slottedStack);
        buf.writeInt(slot);
    }
    
    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() -> {
            ServerPlayerEntity playerEntity = context.get().getSender();
            if (returnStack != null && !returnStack.isEmpty())
            {
                playerEntity.inventory.setItemStack(returnStack);
            }
            if (slottedStack != null && !slottedStack.isEmpty())
            {
                playerEntity.inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
                ItemHandlerHelper.giveItemToPlayer(playerEntity, slottedStack, slot);
            }
        });
        context.get().setPacketHandled(true);
    }
}
