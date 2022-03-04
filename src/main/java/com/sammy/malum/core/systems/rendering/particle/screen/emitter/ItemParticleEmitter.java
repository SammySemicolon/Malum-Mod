package com.sammy.malum.core.systems.rendering.particle.screen.emitter;

import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.world.item.ItemStack;

public interface ItemParticleEmitter {
    public void tick(ItemStack stack, float pXPosition, float pYPosition, ScreenParticle.RenderOrder renderOrder);
}
