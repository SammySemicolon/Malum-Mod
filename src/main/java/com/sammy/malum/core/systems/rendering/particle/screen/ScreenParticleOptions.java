package com.sammy.malum.core.systems.rendering.particle.screen;

import com.sammy.malum.core.systems.rendering.particle.SimpleParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import com.sammy.malum.core.systems.rendering.particle.screen.emitter.ParticleEmitter;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.item.ItemStack;

public class ScreenParticleOptions extends SimpleParticleOptions {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ItemStack stack;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}