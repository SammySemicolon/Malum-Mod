package com.sammy.malum.common.worldgen.ore;

import com.sammy.malum.registry.common.block.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;

public class CthonicGoldOreFeature extends LayeredOreFeature {
    public CthonicGoldOreFeature() {
        super(OreConfiguration.CODEC);
    }

    @Override
    protected boolean doPlace(WorldGenLevel pLevel, RandomSource pRandom, OreConfiguration pConfig, double pMinX, double pMaxX, double pMinZ, double pMaxZ, double pMinY, double pMaxY, int pX, int pY, int pZ, int pWidth, int pHeight) {
        int i = 0;
        boolean isCthonicGold = pConfig.targetStates.get(0).state.getBlock().equals(BlockRegistry.CTHONIC_GOLD_ORE.get());
        List<Runnable> clusterPlacements = new ArrayList<>();
        List<Runnable> cthonicGoldPlacements = isCthonicGold ? new ArrayList<>() : null;
        BitSet bitset = new BitSet(pWidth * pHeight * pWidth);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int j = pConfig.size;
        double[] adouble = new double[j * 4];

        for (int k = 0; k < j; ++k) {
            float f = (float) k / (float) j;
            double d0 = Mth.lerp(f, pMinX, pMaxX);
            double d1 = Mth.lerp(f, pMinY, pMaxY);
            double d2 = Mth.lerp(f, pMinZ, pMaxZ);
            double d3 = pRandom.nextDouble() * (double) j / 16.0D;
            double d4 = ((double) (Mth.sin((float) Math.PI * f) + 1.0F) * d3 + 1.0D) / 2.0D;
            adouble[k * 4] = d0;
            adouble[k * 4 + 1] = d1;
            adouble[k * 4 + 2] = d2;
            adouble[k * 4 + 3] = d4;
        }

        for (int l3 = 0; l3 < j - 1; ++l3) {
            if (!(adouble[l3 * 4 + 3] <= 0.0D)) {
                for (int i4 = l3 + 1; i4 < j; ++i4) {
                    if (!(adouble[i4 * 4 + 3] <= 0.0D)) {
                        double d8 = adouble[l3 * 4] - adouble[i4 * 4];
                        double d10 = adouble[l3 * 4 + 1] - adouble[i4 * 4 + 1];
                        double d12 = adouble[l3 * 4 + 2] - adouble[i4 * 4 + 2];
                        double d14 = adouble[l3 * 4 + 3] - adouble[i4 * 4 + 3];
                        if (d14 * d14 > d8 * d8 + d10 * d10 + d12 * d12) {
                            if (d14 > 0.0D) {
                                adouble[i4 * 4 + 3] = -1.0D;
                            } else {
                                adouble[l3 * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        try (BulkSectionAccess bulksectionaccess = new BulkSectionAccess(pLevel)) {
            for (int j4 = 0; j4 < j; ++j4) {
                double d9 = adouble[j4 * 4 + 3];
                if (!(d9 < 0.0D)) {
                    double d11 = adouble[j4 * 4];
                    double d13 = adouble[j4 * 4 + 1];
                    double d15 = adouble[j4 * 4 + 2];
                    int k4 = Math.max(Mth.floor(d11 - d9), pX);
                    int l = Math.max(Mth.floor(d13 - d9), pY);
                    int i1 = Math.max(Mth.floor(d15 - d9), pZ);
                    int j1 = Math.max(Mth.floor(d11 + d9), k4);
                    int k1 = Math.max(Mth.floor(d13 + d9), l);
                    int l1 = Math.max(Mth.floor(d15 + d9), i1);

                    for (int i2 = k4; i2 <= j1; ++i2) {
                        double d5 = ((double) i2 + 0.5D - d11) / d9;
                        if (d5 * d5 < 1.0D) {
                            for (int j2 = l; j2 <= k1; ++j2) {
                                double d6 = ((double) j2 + 0.5D - d13) / d9;
                                if (d5 * d5 + d6 * d6 < 1.0D) {
                                    for (int k2 = i1; k2 <= l1; ++k2) {
                                        double d7 = ((double) k2 + 0.5D - d15) / d9;
                                        if (d5 * d5 + d6 * d6 + d7 * d7 < 1.0D && !pLevel.isOutsideBuildHeight(j2)) {
                                            int l2 = i2 - pX + (j2 - pY) * pWidth + (k2 - pZ) * pWidth * pHeight;
                                            if (!bitset.get(l2)) {
                                                bitset.set(l2);
                                                mutable.set(i2, j2, k2);
                                                if (pLevel.ensureCanWrite(mutable)) {
                                                    final LevelChunkSection section = bulksectionaccess.getSection(mutable);
                                                    if (section != null) {
                                                        int x = SectionPos.sectionRelative(i2);
                                                        int y = SectionPos.sectionRelative(j2);
                                                        int z = SectionPos.sectionRelative(k2);
                                                        BlockState blockstate = section.getBlockState(x, y, z);

                                                        for (OreConfiguration.TargetBlockState oreconfiguration$targetblockstate : pConfig.targetStates) {
                                                            if (canPlaceOre(blockstate, bulksectionaccess::getBlockState, pRandom, pConfig, oreconfiguration$targetblockstate, mutable)) {
                                                                if (isCthonicGold) {
                                                                    cthonicGoldPlacements.add(
                                                                            () -> section.setBlockState(x, y, z, oreconfiguration$targetblockstate.state, false));
                                                                } else {
                                                                    section.setBlockState(x, y, z, oreconfiguration$targetblockstate.state, false);
                                                                }
                                                                for (Direction direction : Direction.values()) {
                                                                    mutable.set(i2, j2, k2).move(direction);
                                                                    if (pLevel.ensureCanWrite(mutable)) {
                                                                        final LevelChunkSection offsetSection = bulksectionaccess.getSection(mutable);
                                                                        if (offsetSection != null) {
                                                                            final int clusterX = SectionPos.sectionRelative(mutable.getX());
                                                                            final int clusterY = SectionPos.sectionRelative(mutable.getY());
                                                                            final int clusterZ = SectionPos.sectionRelative(mutable.getZ());
                                                                            if (offsetSection.getBlockState(clusterX, clusterY, clusterZ).isAir()) {
                                                                                clusterPlacements.add(
                                                                                        () -> offsetSection.setBlockState(clusterX, clusterY, clusterZ, BlockRegistry.CTHONIC_GOLD_CLUSTER.get().defaultBlockState().setValue(BlockStateProperties.FACING, direction), false)
                                                                                );
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                                i++;
                                                                if (isCthonicGold && i >= 3) {
                                                                    cthonicGoldPlacements.forEach(Runnable::run);
                                                                    placeClusters(clusterPlacements, pRandom);
                                                                    return true;
                                                                }
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (isCthonicGold ? i >= 3 : i > 0) {
                placeClusters(clusterPlacements, pRandom);
            }
        }
        return isCthonicGold ? i >= 3 : i > 0;
    }

    public void placeClusters(List<Runnable> clusterPlacements, RandomSource random) {
        if (!clusterPlacements.isEmpty()) {
            Collections.shuffle(clusterPlacements);
            int clusterAmount = (clusterPlacements.size() + random.nextInt(4)) / 3;
            if (clusterAmount != 0) {
                for (int k = 0; k < Math.min(clusterAmount, clusterPlacements.size()); k++) {
                    clusterPlacements.get(k).run();
                }
            }
        }
    }
}
