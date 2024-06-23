package com.sammy.malum.visual_effects.networked.crucible;

import com.sammy.malum.common.block.curiosities.spirit_crucible.SpiritCrucibleCoreBlockEntity;
import com.sammy.malum.visual_effects.SpiritCrucibleParticleEffects;
import com.sammy.malum.visual_effects.networked.ParticleEffectType;
import com.sammy.malum.visual_effects.networked.data.ColorEffectData;
import com.sammy.malum.visual_effects.networked.data.NBTEffectData;
import com.sammy.malum.visual_effects.networked.data.PositionEffectData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;

import java.util.function.Supplier;

public class SpiritCrucibleCraftParticleEffect extends ParticleEffectType {

    public SpiritCrucibleCraftParticleEffect(String id) {
        super(id);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SpiritCrucibleCoreBlockEntity crucible)) {
                return;
            }
            SpiritCrucibleParticleEffects.craftItemParticles(crucible, colorData);
        };
    }
}