package com.sammy.malum.visual_effects.networked.totem;

import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.visual_effects.TotemParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Supplier;

public class TotemPoleActivatedParticleEffect extends ParticleEffectType {

    public TotemPoleActivatedParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof TotemPoleBlockEntity totemPole)) {
                return;
            }
            TotemParticleEffects.activateTotemPoleParticles(totemPole);
        };
    }
}