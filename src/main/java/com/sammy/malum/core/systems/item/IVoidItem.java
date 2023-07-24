package com.sammy.malum.core.systems.item;

import com.sammy.malum.client.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import team.lodestar.lodestone.handlers.screenparticle.*;
import team.lodestar.lodestone.systems.particle.screen.*;

public interface IVoidItem extends ParticleEmitterHandler.ItemParticleSupplier {

    @Override
    default void spawnEarlyParticles(ScreenParticleHolder target, Level level, float partialTick, ItemStack stack, float x, float y) {
        ScreenParticleEffects.spawnVoidItemScreenParticles(target, level, partialTick);
    }
}
