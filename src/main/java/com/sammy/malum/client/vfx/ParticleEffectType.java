package com.sammy.malum.client.vfx;

import com.sammy.malum.common.packets.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraftforge.network.*;

import java.util.*;
import java.util.function.*;

import static com.sammy.malum.registry.common.PacketRegistry.*;

public abstract class ParticleEffectType {

    public final String id;

    public ParticleEffectType(String id) {
        this.id = id;
        ParticleEffectTypeRegistry.EFFECT_TYPES.put(id, this);
    }

    public abstract Supplier<ParticleEffectActor> get();

    public ParticleEffectPacket createPacket(PositionEffectData positionData, ColorEffectData colorData) {
        return new ParticleEffectPacket(id, positionData, colorData);
    }

    public void createEffect(PacketDistributor.PacketTarget target, PositionEffectData positionData, ColorEffectData colorData) {
        MALUM_CHANNEL.send(target, createPacket(positionData, colorData));
    }

    public void createEntityEffect(Entity entity, ColorEffectData colorData) {
        createEntityEffect(PacketDistributor.TRACKING_ENTITY_AND_SELF, entity, colorData);
    }

    public void createEntityEffect(PacketDistributor<Entity> packetDistributor, Entity entity, ColorEffectData colorData) {
        createEffect(packetDistributor.with(() -> entity), new PositionEffectData(entity), colorData);
    }

    public void createBlockEffect(Level level, BlockPos pos, ColorEffectData colorData) {
        createEffect(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new PositionEffectData(pos), colorData);
    }

    public interface ParticleEffectActor {
        void act(Level level, Random random, PositionEffectData positionData, ColorEffectData colorData);
    }
}