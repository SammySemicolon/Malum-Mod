package com.sammy.malum.visual_effects.networked.altar;

import com.sammy.malum.common.block.curiosities.spirit_altar.SpiritAltarBlockEntity;
import com.sammy.malum.visual_effects.SpiritAltarParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class SpiritAltarCraftParticleEffect extends ParticleEffectType {

    public SpiritAltarCraftParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SpiritAltarBlockEntity spiritAltar)) {
                return;
            }
            SpiritAltarParticleEffects.craftItemParticles(spiritAltar, colorData);
        };
    }
}