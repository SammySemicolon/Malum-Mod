package com.sammy.malum.core.systems.particle_effects;

import com.sammy.malum.common.packets.*;
import com.sammy.malum.registry.common.*;
import net.minecraft.core.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.phys.*;
import net.minecraftforge.api.distmarker.*;
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

    @OnlyIn(Dist.CLIENT)
    public abstract Supplier<? extends ParticleEffectActor> get();

    public void createEntityEffect(Entity entity) {
        createEntityEffect(entity, null);
    }

    public void createEntityEffect(Entity entity, ColorEffectData colorData) {
        createEntityEffect(PacketDistributor.TRACKING_ENTITY_AND_SELF, entity, colorData);
    }

    public void createEntityEffect(PacketDistributor<Entity> packetDistributor, Entity entity) {
        createEntityEffect(packetDistributor, entity, null);
    }

    public void createEntityEffect(PacketDistributor<Entity> packetDistributor, Entity entity, ColorEffectData colorData) {
        createEffect(packetDistributor.with(() -> entity), new PositionEffectData(entity), colorData);
    }

    public void createBlockEffect(Level level, BlockPos pos) {
        createBlockEffect(level, pos, null);
    }

    public void createBlockEffect(Level level, BlockPos pos, ColorEffectData colorData) {
        createEffect(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new PositionEffectData(pos), colorData);
    }

    public void createPositionedEffect(Level level, double posX, double posY, double posZ) {
        createPositionedEffect(level, posX, posY, posZ, null);
    }

    public void createPositionedEffect(Level level, Vec3 pos) {
        createPositionedEffect(level, pos.x, pos.y, pos.z, null);
    }

    public void createPositionedEffect(Level level, Vec3 pos, ColorEffectData colorData) {
        createPositionedEffect(level, pos.x, pos.y, pos.z, colorData);
    }

    public void createPositionedEffect(Level level, double posX, double posY, double posZ, ColorEffectData colorData) {
        createEffect(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(new BlockPos(posX, posY, posZ))), new PositionEffectData(posX, posY, posZ), colorData);
    }

    public void createEffect(PacketDistributor.PacketTarget target, PositionEffectData positionData, ColorEffectData colorData) {
        MALUM_CHANNEL.send(target, createPacket(positionData, colorData));
    }

    private ParticleEffectPacket createPacket(PositionEffectData positionData, ColorEffectData colorData) {
        return new ParticleEffectPacket(id, positionData, colorData);
    }

    public interface ParticleEffectActor {
        void act(Level level, Random random, PositionEffectData positionData, ColorEffectData colorData);
    }
}