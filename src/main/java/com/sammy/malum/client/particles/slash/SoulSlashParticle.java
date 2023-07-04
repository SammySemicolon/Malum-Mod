package com.sammy.malum.client.particles.slash;

import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.particle.*;
import team.lodestar.lodestone.systems.particle.world.*;

public class SoulSlashParticle extends GenericParticle {

    public SoulSlashParticle(ClientLevel world, WorldParticleOptions options, ParticleEngine.MutableSpriteSet spriteSet, double x, double y, double z, double xd, double yd, double zd) {
        super(world, options, spriteSet, x, y, z, xd, yd, zd);
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(VertexConsumer consumer, Camera camera, float partialTicks) {
        super.render(consumer, camera, partialTicks);
    }

    @Override
    protected int getLightColor(float partialTicks) {
        return 0xF000F0;
    }
}