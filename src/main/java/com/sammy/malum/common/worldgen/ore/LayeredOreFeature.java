package com.sammy.malum.common.worldgen.ore;

import com.mojang.serialization.*;
import net.minecraft.core.*;
import net.minecraft.util.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;

import java.util.*;

public class LayeredOreFeature extends OreFeature {

    public List<OreConfiguration> oreConfigurations = new ArrayList<>();

    public LayeredOreFeature(Codec<OreConfiguration> pCodec) {
        super(pCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<OreConfiguration> pContext) {
        RandomSource randomsource = pContext.random();
        BlockPos blockpos = pContext.origin();
        WorldGenLevel worldgenlevel = pContext.level();

        boolean result = false;
        if (oreConfigurations.isEmpty()) {
            OreConfiguration primaryOreConfig = pContext.config();
            final List<OreConfiguration.TargetBlockState> targetStates = primaryOreConfig.targetStates;
            int size = primaryOreConfig.size;
            for (OreConfiguration.TargetBlockState target : targetStates) {
                oreConfigurations.add(new OreConfiguration(List.of(target), size));
                size *= 2;
            }
        }

        float f = randomsource.nextFloat() * (float) Math.PI;
        final double sin = Math.sin(f);
        final double cos = Math.cos(f);
        double d4 = (blockpos.getY() + randomsource.nextInt(3) - 2);
        double d5 = (blockpos.getY() + randomsource.nextInt(3) - 2);
        for (int i = 0; i < oreConfigurations.size(); i++) {
            OreConfiguration oreConfiguration = oreConfigurations.get(i);
            boolean success = false;
            float f1 = (float) oreConfiguration.size / 8.0F;
            int j = Mth.ceil(((float) oreConfiguration.size / 16.0F * 2.0F + 1.0F) / 2.0F);
            double d0 = (double) blockpos.getX() + sin * (double) f1;
            double d1 = (double) blockpos.getX() - sin * (double) f1;
            double d2 = (double) blockpos.getZ() + cos * (double) f1;
            double d3 = (double) blockpos.getZ() - cos * (double) f1;
            int k = blockpos.getX() - Mth.ceil(f1) - j;
            int l = blockpos.getY() - 2 - j;
            int i1 = blockpos.getZ() - Mth.ceil(f1) - j;
            int width = 2 * (Mth.ceil(f1) + j);
            int height = 2 * (2 + j);

            for (int l1 = k; l1 <= k + width; ++l1) {
                if (success) {
                    break;
                }
                for (int i2 = i1; i2 <= i1 + width; ++i2) {
                    if (success) {
                        break;
                    }
                    if (l <= worldgenlevel.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, l1, i2)) {
                        result = doPlace(worldgenlevel, randomsource, oreConfiguration, d0, d1, d2, d3, d4, d5, k, l, i1, width, height);
                        if (!result && i == 0) {
                            return false;
                        }
                        success = true;
                    }
                }
            }
        }
        return result;
    }
}
