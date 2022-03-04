package com.sammy.malum.core.systems.rendering.particle.screen;

import com.sammy.malum.core.systems.rendering.particle.SimpleParticleOptions;
import com.sammy.malum.core.systems.rendering.particle.screen.base.ScreenParticle;
import net.minecraft.data.worldgen.placement.PlacementUtils;

public class ScreenParticleOptions extends SimpleParticleOptions {

    public final ScreenParticleType<?> type;
    public ScreenParticle.RenderOrder renderOrder;
    public ScreenParticleOptions(ScreenParticleType<?> type) {
        this.type = type;
    }
}