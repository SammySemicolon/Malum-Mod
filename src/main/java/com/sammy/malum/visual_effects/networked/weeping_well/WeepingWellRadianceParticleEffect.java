package com.sammy.malum.visual_effects.networked.weeping_well;

import com.sammy.malum.visual_effects.RadiantParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class WeepingWellRadianceParticleEffect extends ParticleEffectType {

    public WeepingWellRadianceParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return new Supplier<>() {
            @Environment(EnvType.CLIENT)
            @Override
            public ParticleEffectActor get() {
                return new ParticleEffectActor() {
                    @Environment(EnvType.CLIENT)
                    @Override
                    public void act(Level level, RandomSource random, PositionEffectData positionData, ColorEffectData colorData, NBTEffectData nbtData) {
                        RadiantParticleEffects.spitOutWeepingWellRadiance(level, positionData);
                    }
                };
            }
        };
    }
}