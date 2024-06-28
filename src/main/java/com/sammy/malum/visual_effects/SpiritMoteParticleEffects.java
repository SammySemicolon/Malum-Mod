package com.sammy.malum.visual_effects;

import com.sammy.malum.client.*;
import com.sammy.malum.core.systems.spirit.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.phys.shapes.*;
import team.lodestar.lodestone.registry.common.particle.*;
import team.lodestar.lodestone.systems.particle.data.*;
import team.lodestar.lodestone.systems.particle.data.color.*;
import team.lodestar.lodestone.systems.particle.render_types.*;
import team.lodestar.lodestone.systems.particle.world.options.*;

public class SpiritMoteParticleEffects {

    public static void destroy(Level level, BlockPos pPos, BlockState pState, MalumSpiritType spiritType) {
        if (!pState.isAir()) {
            VoxelShape voxelshape = pState.getShape(level, pPos);

            var builder = SpiritBasedParticleBuilder.createSpirit(new LodestoneTerrainParticleOptions(LodestoneParticleRegistry.TERRAIN_PARTICLE, pState, pPos))
                    .setRenderType(LodestoneWorldParticleRenderType.TERRAIN_SHEET)
                    .setSpirit(spiritType)
                    .setTransparencyData(GenericParticleData.create(0.2f, 0).build())
                    .setScaleData(GenericParticleData.create(0.125f).build())
                    .setColorData(ColorParticleData.create(0.6f, 0.6f, 0.6f).build());

            voxelshape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
                double xOffset = Math.min(1.0D, maxX - minX);
                double yOffset = Math.min(1.0D, maxY - minY);
                double zOffset = Math.min(1.0D, maxZ - minZ);
                int xDistance = Math.max(2, Mth.ceil(xOffset / 0.25D));
                int yDistance = Math.max(2, Mth.ceil(yOffset / 0.25D));
                int zDistance = Math.max(2, Mth.ceil(zOffset / 0.25D));

                for(int x = 0; x < xDistance; ++x) {
                    for(int y = 0; y < yDistance; ++y) {
                        for(int z = 0; z < zDistance; ++z) {
                            float motionX = (x + 0.5f) / xDistance;
                            float motionY = (y + 0.5f) / yDistance;
                            float motionZ = (z + 0.5f) / zDistance;
                            double posX = pPos.getX() + motionX * xOffset + minX;
                            double posY = pPos.getY() + motionY * yOffset + minY;
                            double posZ = pPos.getZ() + motionZ * zOffset + minZ;
                            builder
                                    .setMotion(motionX - 0.5f, motionY - 0.5f, motionZ - 0.5f)
                                    .spawn(level, posX, posY, posZ);
                        }
                    }
                }

            });
        }
    }
    
}
