package com.sammy.malum.visual_effects.networked.weeping_well;

import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.minecraft.util.Mth;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import com.sammy.malum.visual_effects.*;

import java.awt.*;
import java.util.function.Supplier;

public class WeepingWellReactionParticleEffect extends ParticleEffectType {

    public WeepingWellReactionParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            WeepingWellParticleEffects.spitOutItemParticles(level, positionData);
        };
    }
}