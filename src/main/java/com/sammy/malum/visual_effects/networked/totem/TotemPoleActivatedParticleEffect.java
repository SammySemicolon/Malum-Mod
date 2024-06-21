package com.sammy.malum.visual_effects.networked.totem;

import com.sammy.malum.common.block.curiosities.totem.TotemPoleBlockEntity;
import com.sammy.malum.visual_effects.TotemParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class TotemPoleActivatedParticleEffect extends ParticleEffectType {

    public TotemPoleActivatedParticleEffect(String id) {
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
                        if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof TotemPoleBlockEntity totemPole)) {
                            return;
                        }
                        if (level.isClientSide) {
                            TotemParticleEffects.activateTotemPoleParticles(totemPole);
                        }
                    }
                };
            }
        };
    }
}