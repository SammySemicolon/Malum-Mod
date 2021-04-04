package com.sammy.malum.common.worldgen;

import com.ibm.icu.impl.Pair;
import com.sammy.malum.common.blocks.RunewoodSaplingBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.ArrayList;
import java.util.Random;

import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236558_a_;

public class RunewoodTreeFeature extends Feature<NoFeatureConfig>
{
    public RunewoodTreeFeature()
    {
        super(field_236558_a_);
    }

    public static final int minimumTrunkHeight = 8;
    public static final int extraTrunkHeight = 3;

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (reader.isAirBlock(pos.down()) || reader.getBlockState(pos.down()).getBlock().equals(MalumBlocks.SUN_KISSED_LEAVES.get()))
        {
            return false;
        }
        BlockState defaultLog = MalumBlocks.RUNEWOOD_LOG.get().getDefaultState();
        BlockState sapLog = MalumBlocks.SAP_FILLED_RUNEWOOD_LOG.get().getDefaultState();
        ArrayList<Pair<BlockPos, BlockState>> filler = new ArrayList<>();
        ArrayList<Pair<BlockPos, BlockState>> leavesFiller = new ArrayList<>();

        int trunkHeight = minimumTrunkHeight + rand.nextInt(extraTrunkHeight + 1);
        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = pos.up(i);
            if (canPlace(reader, trunkPos))
            {
                filler.add(Pair.of(trunkPos, defaultLog));
            }
            else
            {
                return false;
            }
        }
        fill(reader,filler);
        return true;
    }

    public static void fill(ISeedReader reader, ArrayList<Pair<BlockPos, BlockState>> filler)
    {
        for (Pair<BlockPos, BlockState> pair : filler)
        {
            reader.setBlockState(pair.first, pair.second, 3);
        }
    }

    public static boolean canPlace(ISeedReader reader, BlockPos pos)
    {
        if (World.isOutsideBuildHeight(pos))
        {
            return false;
        }
        BlockState state = reader.getBlockState(pos);
        return state.getBlock() instanceof RunewoodSaplingBlock || reader.isAirBlock(pos) || state.getMaterial().isReplaceable();
    }
}