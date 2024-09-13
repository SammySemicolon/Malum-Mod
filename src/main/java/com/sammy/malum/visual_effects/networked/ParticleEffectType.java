package com.sammy.malum.visual_effects.networked;

import com.sammy.malum.common.packets.ParticleEffectPacket;
import com.sammy.malum.registry.common.ParticleEffectTypeRegistry;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.function.Supplier;

import static com.sammy.malum.registry.common.PacketRegistry.MALUM_CHANNEL;

public abstract class ParticleEffectType {

    public final String id;

    public ParticleEffectType(String id) {
        this.id = id;
        ParticleEffectTypeRegistry.EFFECT_TYPES.put(id, this);
    }

    @OnlyIn(Dist.CLIENT)
    public abstract Supplier<ParticleEffectActor> get();

    public void createEntityEffect(Entity entity) {
        createEntityEffect(entity, null);
    }

    public void createEntityEffect(Entity entity, ColorEffectData colorData) {
        createEntityEffect(entity, colorData, null);
    }

    public void createEntityEffect(Entity entity, ColorEffectData colorData, NBTEffectData nbtData) {
        createEffect(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), new PositionEffectData(entity), colorData, nbtData);
    }

    public void createPositionedEffect(Level level, PositionEffectData positionData) {
        createPositionedEffect(level, positionData, null, null);
    }

    public void createPositionedEffect(Level level, PositionEffectData positionData, NBTEffectData nbtData) {
        createPositionedEffect(level, positionData, null, nbtData);
    }

    public void createPositionedEffect(Level level, PositionEffectData positionData, ColorEffectData colorData) {
        createPositionedEffect(level, positionData, colorData, null);
    }

    public void createPositionedEffect(Level level, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData) {
        createEffect(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(positionData.getAsBlockPos())), positionData, colorData, nbtData);
    }

    public void createEffect(PacketDistributor.PacketTarget target, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData) {
        MALUM_CHANNEL.send(target, new ParticleEffectPacket(id, positionData, colorData, nbtData));
    }

    public interface ParticleEffectActor {
        void act(Level level, RandomSource random, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData);
    }
}