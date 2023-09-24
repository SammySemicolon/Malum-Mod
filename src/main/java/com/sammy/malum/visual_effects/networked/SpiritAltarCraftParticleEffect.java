package com.sammy.malum.visual_effects.networked;

import com.sammy.malum.common.block.curiosities.spirit_altar.*;
import com.sammy.malum.core.systems.particle_effects.*;
import com.sammy.malum.visual_effects.*;
import net.minecraft.util.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;

import java.awt.*;
import java.util.function.*;

public class SpiritAltarCraftParticleEffect extends ParticleEffectType {

    public SpiritAltarCraftParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData) -> {
            if (!(level.getBlockEntity(positionData.getAsBlockPos()) instanceof SpiritAltarBlockEntity spiritAltar)) {
                return;
            }
            SpiritAltarParticleEffects.craftItemParticles(spiritAltar);
        };
    }
}