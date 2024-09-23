package com.sammy.malum.common.packets;

import com.sammy.malum.common.capability.MalumPlayerDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

import java.util.UUID;

public class SyncMalumPlayerCapabilityDataPacket extends OneSidedPayloadData {
    private final UUID uuid;
    private final CompoundTag tag;

    public SyncMalumPlayerCapabilityDataPacket(FriendlyByteBuf buf) {
        super(buf);
        this.uuid = buf.readUUID();
        this.tag = buf.readNbt();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void handle(IPayloadContext iPayloadContext) {
        Player player = Minecraft.getInstance().level.getPlayerByUUID(uuid);
        MalumPlayerDataCapability.getCapabilityOptional(player).ifPresent(c -> c.deserializeNBT(tag));
    }

    @Override
    public void serialize(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeUUID(uuid);
        friendlyByteBuf.writeNbt(tag);
    }
}