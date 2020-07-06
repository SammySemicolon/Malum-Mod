package com.kittykitcatcat.malum.network.packets;

import com.kittykitcatcat.malum.capabilities.CapabilityValueGetter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class HuskChangePacket
{
    int id;
    boolean value;

    public HuskChangePacket(int id, boolean value)
    {
        this.id = id;
        this.value = value;
    }

    public void encode(PacketBuffer buf)
    {
        buf.writeInt(id);
        buf.writeBoolean(value);
    }

    public void whenThisPacketIsReceived(Supplier<NetworkEvent.Context> context)
    {
        context.get().enqueueWork(() ->
                DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
                {
                    World world = Minecraft.getInstance().world;
                    if (world != null)
                    {
                        if (world.getEntityByID(id) instanceof LivingEntity)
                        {
                            CapabilityValueGetter.setHusk((LivingEntity) world.getEntityByID(id), value);
                        }
                    }
                }));
        context.get().setPacketHandled(true);
    }

    public static HuskChangePacket decode(PacketBuffer buf)
    {
        int uuid = buf.readInt();
        boolean value = buf.readBoolean();
        return new HuskChangePacket(uuid, value);
    }
}