package com.sammy.malum.common.packets;

import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    public void execute(Supplier<NetworkEvent.Context> context) {
        Minecraft instance = Minecraft.getInstance();
        ClientLevel level = instance.level;
        ParticleEffectType particleEffectType = ParticleEffectTypeRegistry.EFFECT_TYPES.get(id);
        if (particleEffectType == null) {
            throw new RuntimeException("This shouldn't be happening.");
        }
        ParticleEffectType.ParticleEffectActor particleEffectActor = particleEffectType.get().get();
        particleEffectActor.act(level, level.random, positionData, colorData, nbtData);
    }

    public static void register(SimpleChannel instance, int index) {
        instance.registerMessage(index, ParticleEffectPacket.class, ParticleEffectPacket::encode, ParticleEffectPacket::decode, ParticleEffectPacket::handle);
    }

    public static ParticleEffectPacket decode(FriendlyByteBuf buf) {
        return new ParticleEffectPacket(buf.readUtf(), new PositionEffectData(buf), buf.readBoolean() ? new ColorEffectData(buf) : null, buf.readBoolean() ? new NBTEffectData(buf.readNbt()) : null);
    }
}