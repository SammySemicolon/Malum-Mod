package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.UUID;
import java.util.function.Supplier;

public class SyncMalumPlayerCapabilityDataPacket extends LodestoneClientPacket {
    private final UUID uuid;
    private final CompoundTag tag;

    public SyncMalumPlayerCapabilityDataPacket(UUID uuid, CompoundTag tag) {
        this.uuid = uuid;
        this.tag = tag;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeNbt(tag);
    }

    @OnlyIn(Dist.CLIENT)
    public void execute(Supplier<NetworkEvent.Context> context) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> c.deserializeNBT(tag));
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, SyncMalumPlayerCapabilityDataPacket.class, SyncMalumPlayerCapabilityDataPacket::encode, SyncMalumPlayerCapabilityDataPacket::decode, SyncMalumPlayerCapabilityDataPacket::handle);
    }

    public static SyncMalumPlayerCapabilityDataPacket decode(FriendlyByteBuf buf) {
        return new SyncMalumPlayerCapabilityDataPacket(buf.readUUID(), buf.readNbt());
    }
}