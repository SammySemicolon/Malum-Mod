package com.sammy.malum.visual_effects.networked.plinth;

import com.sammy.malum.common.block.curiosities.ritual_plinth.*;
import com.sammy.malum.visual_effects.*;
import com.sammy.malum.visual_effects.networked.*;
import net.minecraftforge.api.distmarker.*;

import java.util.function.*;

public class RitualPlinthBeginChargingParticleEffect extends ParticleEffectType {

    public RitualPlinthBeginChargingParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof RitualPlinthBlockEntity ritualPlinth)) {
                return;
            }
            RitualPlinthParticleEffects.beginChargingParticles(ritualPlinth, colorData);
        };
    }
}