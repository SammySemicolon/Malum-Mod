package com.sammy.malum.network.packets;

import com.sammy.malum.ClientHandler;
import com.sammy.malum.items.staves.BasicStave;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.function.Supplier;

import static com.sammy.malum.capabilities.MalumDataProvider.getHusk;
import static com.sammy.malum.network.NetworkManager.INSTANCE;

public class UpdateStaveNBT
{
    public int slot;
    public int value;
    public UpdateStaveNBT(int slot, int value)
    {
        this.slot = slot;
        this.value = value;
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
    
            if (stack.getItem() instanceof BasicStave)
            {
                int tag = stack.getOrCreateTag().getInt("malum:staveOption");
                stack.getTag().putInt("malum:staveOption", tag + value);
                ClientHandler.makeStaveMessage(context.get().getSender(), tag + value);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static UpdateStaveNBT decode(PacketBuffer buf)
    {
        int slot = buf.readInt();
        int value = buf.readInt();
        return new UpdateStaveNBT(slot, value);
    }
}