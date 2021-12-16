package com.sammy.malum.client.particles.wisp;

import com.sammy.malum.core.systems.particle.GenericMalumParticle;
import com.sammy.malum.core.systems.particle.data.MalumParticleData;
import net.minecraft.client.Level.ClientLevel;

public class WispParticle extends GenericMalumParticle {
    public WispParticle(ClientLevel Level, MalumParticleData data, double x, double y, double z, double vx, double vy, double vz) {
        super(Level, data, x, y, z, vx, vy, vz);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }

}