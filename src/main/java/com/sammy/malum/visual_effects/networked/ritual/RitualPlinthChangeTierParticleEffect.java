package com.sammy.malum.visual_effects.networked.ritual;

import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.*;

public class RitualPlinthChangeTierParticleEffect extends ParticleEffectType {

    public RitualPlinthChangeTierParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RitualPlinthBlockEntity ritualPlinth)) {
                return;
            }
            RitualPlinthParticleEffects.incrementRitualTierParticles(ritualPlinth, colorData);
        };
    }
}