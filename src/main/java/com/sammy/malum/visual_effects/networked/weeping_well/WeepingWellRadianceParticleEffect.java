package com.sammy.malum.visual_effects.networked.weeping_well;

import com.sammy.malum.visual_effects.networked.*;
import net.minecraft.util.*;
import net.minecraftforge.api.distmarker.*;
import team.lodestar.lodestone.helpers.*;
import team.lodestar.lodestone.setup.*;
import team.lodestar.lodestone.systems.easing.*;
import team.lodestar.lodestone.systems.particle.*;
import team.lodestar.lodestone.systems.particle.builder.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.data.spin.*;
import team.lodestar.lodestone.systems.particle.render_types.*;

import java.awt.*;
import java.util.function.*;

public class WeepingWellRadianceParticleEffect extends ParticleEffectType {

    public WeepingWellRadianceParticleEffect(String id) {
        super(id);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public Supplier<ParticleEffectActor> get() {
        return () -> (level, random, positionData, colorData, nbtData) -> {
            double posX = positionData.posX;
            double posY = positionData.posY;
            double posZ = positionData.posZ;
        };
    }
}