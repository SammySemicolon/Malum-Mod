package com.sammy.malum.common.packets;

import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.systems.network.LodestoneClientPacket;


public class ParticleEffectPacket extends LodestoneClientPacket {

    private final String id;
    private final PositionEffectData positionData;
    @Nullable
    private final ColorEffectData colorData;
    @Nullable
    private final NBTEffectData nbtData;

    public ParticleEffectPacket(String id, PositionEffectData positionData, @Nullable ColorEffectData colorData, @Nullable NBTEffectData nbtData) {
        this.id = id;
        this.positionData = positionData;
        this.colorData = colorData;
        this.nbtData = nbtData;
    }


    public ParticleEffectPacket(FriendlyByteBuf buf) {
        this.id = buf.readUtf();
        this.positionData = new PositionEffectData(buf);
        this.colorData = buf.readBoolean() ? new ColorEffectData(buf) : null;
        this.nbtData = buf.readBoolean() ? new NBTEffectData(buf.readNbt()) : null;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeUtf(id);
        positionData.encode(buf);
        boolean NotNullColorData = colorData != null;
        buf.writeBoolean(NotNullColorData);
        if (NotNullColorData) {
            colorData.encode(buf);
        }
        boolean NotNullCompoundTag = nbtData != null;
        buf.writeBoolean(NotNullCompoundTag);
        if (NotNullCompoundTag) {
            buf.writeNbt(nbtData.compoundTag);
        }
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void executeClient(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        ClientExecuteHelper.generic(client, id, positionData, colorData, nbtData);
    }

}