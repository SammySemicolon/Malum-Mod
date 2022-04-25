package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.PlayerDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class SyncMalumPlayerCapabilityDataPacket {
    private final UUID uuid;
    private final CompoundTag tag;

    public SyncMalumPlayerCapabilityDataPacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncMalumPlayerCapabilityDataPacket.class, SyncMalumPlayerCapabilityDataPacket::encode, SyncMalumPlayerCapabilityDataPacket::decode, SyncMalumPlayerCapabilityDataPacket::execute);
    }

    public static SyncMalumPlayerCapabilityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncMalumPlayerCapabilityDataPacket(buf.readUUID(), buf.readNbt());
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeNbt(tag);
    }

    public void execute(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            if (FMLEnvironment.dist == Dist.CLIENT) {
                ClientOnly.syncData(uuid, tag);
            }
        });
        context.get().setPacketHandled(true);
    }

    public static class ClientOnly {
        public static void syncData(UUID uuid, CompoundTag tag) {
            Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
            PlayerDataCapability.getCapability(player).ifPresent(c -> c.deserializeNBT(tag));
        }
    }
}