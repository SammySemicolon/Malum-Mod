package com.sammy.malum.common.packets.malignant_conversion;

import com.sammy.malum.core.handlers.*;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.*;
import net.minecraft.core.registries.*;
import net.minecraft.network.*;
import net.minecraft.world.entity.ai.attributes.*;
import team.lodestar.lodestone.systems.network.*;

import java.util.*;
import java.util.function.*;

public class SyncMalignantConversionPacket extends LodestoneClientPacket {

    private final Attribute attribute;
    private final UUID uuid;

    public SyncMalignantConversionPacket(Attribute attribute, UUID uuid) {
        this.attribute = attribute;
        this.uuid = uuid;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeId(BuiltInRegistries.ATTRIBUTE, attribute);
        buf.writeUUID(uuid);
    }
    @Environment(EnvType.CLIENT)
    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        final LocalPlayer player = Minecraft.getInstance().player;
        MalignantConversionHandler.syncPositiveUUIDS(player, attribute, uuid);
    }



    public static SyncMalignantConversionPacket decode(FriendlyByteBuf buf) {
        return new SyncMalignantConversionPacket(buf.readById(BuiltInRegistries.ATTRIBUTE), buf.readUUID());
    }
}