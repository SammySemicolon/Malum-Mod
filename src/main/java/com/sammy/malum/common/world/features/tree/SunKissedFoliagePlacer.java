package com.sammy.malum.common.world.features.tree;

import com.sammy.malum.common.blocks.MalumLeavesBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.FeatureSpread;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.TreeFeature;
import net.minecraft.world.gen.foliageplacer.SpruceFoliagePlacer;

import java.util.Random;
import java.util.Set;

public class SunKissedFoliagePlacer extends SpruceFoliagePlacer
{
    public SunKissedFoliagePlacer(FeatureSpread p_i242003_1_, FeatureSpread p_i242003_2_, FeatureSpread p_i242003_3_)
    {
        super(p_i242003_1_, p_i242003_2_, p_i242003_3_);
    }
    
    @Override
    protected void func_236753_a_(IWorldGenerationReader worldGenerationReader, Random random, BaseTreeFeatureConfig baseTreeFeatureConfig, BlockPos pos, int size, Set<BlockPos> posSet, int yPos, boolean someBoolean, MutableBoundingBox boundingBox)
    {
        int i = someBoolean ? 1 : 0;
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
    
        for (int xPos = -size; xPos <= size + i; ++xPos)
        {
            for (int zPos = -size; zPos <= size + i; ++zPos)
            {
                if (!this.func_230375_b_(random, xPos, yPos, zPos, size, someBoolean))
                {
                    blockpos$mutable.setAndOffset(pos, xPos, yPos, zPos);
                    if (TreeFeature.isReplaceableAt(worldGenerationReader, blockpos$mutable))
                    {
                        int color = Math.max(Math.min(Math.abs(yPos),9),0);
                        worldGenerationReader.setBlockState(blockpos$mutable, baseTreeFeatureConfig.leavesProvider.getBlockState(random, blockpos$mutable).with(MalumLeavesBlock.COLOR, color), 19);
                        boundingBox.expandTo(new MutableBoundingBox(blockpos$mutable, blockpos$mutable));
                        posSet.add(blockpos$mutable.toImmutable());

                    }
                }
            }
        }
    }
}