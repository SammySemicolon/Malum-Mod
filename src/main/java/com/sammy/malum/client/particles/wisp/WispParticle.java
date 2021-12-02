package com.sammy.malum.client.particles.wisp;

import com.sammy.malum.core.systems.particle.GenericMalumParticle;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import net.minecraft.client.world.ClientWorld;

public class WispParticle extends GenericMalumParticle {
    public WispParticle(ClientWorld world, MalumParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(world, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getBrightnessForRender(float partialTicks) {
        return 0xF000F0;
    }

}