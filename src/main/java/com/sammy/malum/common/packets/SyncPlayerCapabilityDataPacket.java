package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.PlayerDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class SyncPlayerCapabilityDataPacket {
    private final CompoundTag tag;

    public SyncPlayerCapabilityDataPacket(CompoundTag tag) {
        this.tag = tag;
    }

    public static SyncPlayerCapabilityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncPlayerCapabilityDataPacket(buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeNbt(tag);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.syncData(tag);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncPlayerCapabilityDataPacket.class, SyncPlayerCapabilityDataPacket::encode, SyncPlayerCapabilityDataPacket::decode, SyncPlayerCapabilityDataPacket::execute);
    }

    public static class ClientOnly {
        public static void syncData(CompoundTag tag) {
            PlayerDataCapability.getCapability(Minecraft.getInstance().player).ifPresent(c -> c.deserializeNBT(tag));
        }
    }
}