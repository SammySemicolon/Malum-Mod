package com.sammy.malum.visual_effects.networked.gluttony;

import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.*;

public class ThrownGluttonyParticleEffect extends ParticleEffectType {

    public ThrownGluttonyParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            GluttonyParticleEffects.thrownGluttonySplash(positionData);
        };
    }
}