package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.helpers.RandomHelper;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.options.*;

import java.util.function.Consumer;

public class SpiritMoteParticleEffects {

    private static final Consumer<LodestoneWorldParticle> SLOWDOWN = p -> {
        Vec3 speed = p.getParticleSpeed();
        double d0 = (Math.random() + Math.random() + 1.0D) * (double) 0.15F;
        double d1 = Math.sqrt(speed.x * speed.x + speed.y * speed.y + speed.z * speed.z);
        p.setParticleSpeed(
                speed.x / d1 * d0 * 0.4F,
                speed.y / d1 * d0 * 0.4F + 0.1F,
                speed.z / d1 * d0 * 0.4F);
    };

    public static void destroy(Level level, BlockPos pPos, BlockState pState, MalumSpiritType spiritType) {
        if (!pState.isAir()) {
            VoxelShape voxelshape = pState.getShape(level, pPos);

            var builder = SpiritBasedParticleBuilder.createSpirit(new LodestoneTerrainParticleOptions(LodestoneParticleTypes.TERRAIN_PARTICLE, pState, pPos))
                    .setRenderType(LodestoneWorldParticleRenderType.TERRAIN_SHEET)
                    .setSpirit(spiritType)
                    .setGravityStrength(1f)
                    .setFrictionStrength(0.98f)
                    .setTransparencyData(GenericParticleData.create(0.5f, 0).build())
                    .setScaleData(GenericParticleData.create(0.0625f).build())
                    .addSpawnActor(SLOWDOWN);

            voxelshape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                double xOffset = Math.min(1.0D, maxX - minX);
                double yOffset = Math.min(1.0D, maxY - minY);
                double zOffset = Math.min(1.0D, maxZ - minZ);
                float xDistance = Math.max(2, Mth.ceil(xOffset / 0.25D));
                float yDistance = Math.max(2, Mth.ceil(yOffset / 0.25D));
                float zDistance = Math.max(2, Mth.ceil(zOffset / 0.25D));

                for(int x = 0; x < xDistance; ++x) {
                    for(int y = 0; y < yDistance; ++y) {
                        for(int z = 0; z < zDistance; ++z) {
                            float motionX = (x + 0.5f) / xDistance;
                            float motionY = (y + 0.5f) / yDistance;
                            float motionZ = (z + 0.5f) / zDistance;
                            double posX = pPos.getX() + motionX * xOffset + minX;
                            double posY = pPos.getY() + motionY * yOffset + minY;
                            double posZ = pPos.getZ() + motionZ * zOffset + minZ;
                            RandomSource random = level.random;
                            builder
                                    .setMotion(
                                            motionX - 0.5f + (random.nextFloat() * 2f - 1f) * 0.4f,
                                            motionY - 0.5f + (random.nextFloat() * 2f - 1f) * 0.4f,
                                            motionZ - 0.5f + (random.nextFloat() * 2f - 1f) * 0.4f
                                    )
                                    .setLifetime(RandomHelper.randomBetween(level.random, 20, 40))
                                    .spawn(level, posX, posY, posZ);
                        }
                    }
                }

            });
        }
    }
    
}
