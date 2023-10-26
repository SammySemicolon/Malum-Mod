package com.sammy.malum.visual_effects.networked;

import com.sammy.malum.common.packets.*;

import com.sammy.malum.registry.common.*;
import com.sammy.malum.visual_effects.networked.data.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
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
        createPositionedEffect(level, positionData, null);
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
        void act(Level level, Random random, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData);
    }
}