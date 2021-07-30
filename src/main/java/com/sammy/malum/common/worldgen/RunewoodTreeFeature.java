package com.sammy.malum.common.worldgen;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.block.MalumLeavesBlock;
import com.sammy.malum.common.block.RunewoodSaplingBlock;
import com.sammy.malum.core.init.block.MalumBlocks;
import com.sammy.malum.core.mod_systems.worldgen.MalumFiller;
import com.sammy.malum.core.mod_systems.worldgen.MalumFiller.BlockStateEntry;
import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

import static net.minecraft.world.gen.feature.NoFeatureConfig.field_236558_a_;

public class RunewoodTreeFeature extends Feature<NoFeatureConfig>
{
    public RunewoodTreeFeature()
    {
        super(field_236558_a_);
    }
    private static final int minimumSapBlockCount = 2;
    private static final int extraSapBlockCount = 1;

    private static final int minimumTrunkHeight = 7;
    private static final int extraTrunkHeight = 3;
    private static final int minimumSideTrunkHeight = 0;
    private static final int extraSideTrunkHeight = 2;

    private static final int minimumDownwardsBranchOffset = 2;
    private static final int extraDownwardsBranchOffset = 2;
    private static final int minimumBranchCoreOffset = 2;
    private static final int branchCoreOffsetExtra = 1;
    private static final int minimumBranchHeight = 3;
    private static final int branchHeightExtra = 2;

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (reader.isAirBlock(pos.down()) || !MalumBlocks.RUNEWOOD_SAPLING.get().getDefaultState().isValidPosition(reader, pos))
        {
            return false;
        }
        BlockState defaultLog = MalumBlocks.RUNEWOOD_LOG.get().getDefaultState();

        MalumFiller treeFiller = new MalumFiller();
        MalumFiller leavesFiller = new MalumFiller();

        int trunkHeight = minimumTrunkHeight + rand.nextInt(extraTrunkHeight + 1);
        BlockPos trunkTop = pos.up(trunkHeight);
        Direction[] directions = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};

        for (int i = 0; i <= trunkHeight; i++) //trunk placement
        {
            BlockPos trunkPos = pos.up(i);
            if (canPlace(reader, trunkPos))
            {
                treeFiller.entries.add(new BlockStateEntry(defaultLog, trunkPos));
            }
            else
            {
                return false;
            }
        }

        makeLeafBlob(leavesFiller, rand, trunkTop);
        for (Direction direction : directions) //side trunk placement
        {
            int sideTrunkHeight = minimumSideTrunkHeight + rand.nextInt(extraSideTrunkHeight + 1);
            for (int i = 0; i < sideTrunkHeight; i++)
            {
                BlockPos sideTrunkPos = pos.offset(direction).up(i);
                if (canPlace(reader, sideTrunkPos))
                {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog, sideTrunkPos));
                }
                else
                {
                    return false;
                }
            }
            downwardsTrunk(reader, treeFiller, pos.offset(direction));
        }
        for (Direction direction : directions) //tree top placement
        {
            int branchCoreOffset = minimumDownwardsBranchOffset + rand.nextInt(extraDownwardsBranchOffset + 1);
            int branchOffset = minimumBranchCoreOffset + rand.nextInt(branchCoreOffsetExtra + 1);
            BlockPos branchStartPos = trunkTop.down(branchCoreOffset).offset(direction, branchOffset);
            for (int i = 0; i < branchOffset; i++) //branch connection placement
            {
                BlockPos branchConnectionPos = branchStartPos.offset(direction.getOpposite(), i);
                if (canPlace(reader, branchConnectionPos))
                {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog.with(RotatedPillarBlock.AXIS, direction.getAxis()), branchConnectionPos));
                }
                else
                {
                    return false;
                }
            }
            int branchHeight = minimumBranchHeight + rand.nextInt(branchHeightExtra + 1);
            for (int i = 0; i < branchHeight; i++) //branch placement
            {
                BlockPos branchPos = branchStartPos.up(i);
                if (canPlace(reader, branchPos))
                {
                    treeFiller.entries.add(new BlockStateEntry(defaultLog, branchPos));
                }
                else
                {
                    return false;
                }
            }
            makeLeafBlob(leavesFiller, rand, branchStartPos.up(1));
        }
        int sapBlockCount = minimumSapBlockCount + rand.nextInt(extraSapBlockCount+1);
        int[] sapBlockIndexes = MalumHelper.nextInts(rand, sapBlockCount, treeFiller.entries.size());
        for (Integer index : sapBlockIndexes)
        {
            BlockStateEntry oldEntry = treeFiller.entries.get(index);
            BlockState newState = MalumHelper.getBlockStateWithExistingProperties(oldEntry.state, MalumBlocks.SAP_FILLED_RUNEWOOD_LOG.get().getDefaultState());
            treeFiller.replaceAt(index, new BlockStateEntry(newState, oldEntry.pos));
        }
        treeFiller.fill(reader, false);
        leavesFiller.fill(reader, true);
        return true;
    }

//    public static void makeHallowedGround(ISeedReader reader, MalumFiller groundFiller, MalumFiller grassFiller, Random rand, BlockPos pos, int size, float tallGrassProbability)
//    {
//        for (int x = -size; x <= size; x++)
//        {
//            for (int z = -size; z <= size; z++)
//            {
//                if (Math.abs(x) == size && Math.abs(z) == size)
//                {
//                    continue;
//                }
//                BlockPos grassPos = reader.getHeight(Heightmap.Type.WORLD_SURFACE, pos.add(x, 0, z)).down();
//                do
//                {
//                    if (reader.getBlockState(grassPos).getBlock() instanceof BushBlock)
//                    {
//                        groundFiller.entries.add(new BlockStateEntry(Blocks.AIR.getDefaultState(), grassPos));
//                        grassPos = grassPos.down();
//                    }
//                    else
//                    {
//                        break;
//                    }
//                }
//                while (true);
//                if (reader.getBlockState(grassPos).getBlock() instanceof GrassBlock)
//                {
//                    groundFiller.entries.add(new BlockStateEntry(MalumBlocks.SUN_KISSED_GRASS_BLOCK.get().getDefaultState(), grassPos));
//                    if (rand.nextFloat() < 0.25f)
//                    {
//                        if (rand.nextFloat() < tallGrassProbability)
//                        {
//                            DoublePlantBlock tallGrassBlock;
//                            if (rand.nextBoolean())
//                            {
//                                tallGrassBlock = (DoublePlantBlock) MalumBlocks.TALL_SUN_KISSED_GRASS.get();
//                            }
//                            else
//                            {
//                                tallGrassBlock = (DoublePlantBlock) MalumBlocks.LAVENDER.get();
//                            }
//                            BlockStateEntry tallGrassEntry = new BlockStateEntry(tallGrassBlock.getDefaultState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), grassPos.up())
//                            {
//                                @Override
//                                public boolean canPlace(ISeedReader reader)
//                                {
//                                    return super.canPlace(reader) && super.canPlace(reader, pos.up());
//                                }
//
//                                @Override
//                                public void additionalPlacement(ISeedReader reader)
//                                {
//                                    reader.setBlockState(pos.up(), state.with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
//                                    if (reader instanceof World)
//                                    {
//                                        MalumHelper.updateState((World) reader, pos.up());
//                                    }
//                                }
//                            };
//                            grassFiller.entries.add(tallGrassEntry);
//                        }
//                        else
//                        {
//                            groundFiller.entries.add(new BlockStateEntry(MalumBlocks.SUN_KISSED_GRASS.get().getDefaultState(), grassPos.up()));
//                        }
//                    }
//                }
//            }
//        }
//    }

    public static void downwardsTrunk(ISeedReader reader, MalumFiller filler, BlockPos pos)
    {
        int i = 0;
        do
        {
            i++;
            BlockPos trunkPos = pos.down(i);
            if (canPlace(reader, trunkPos))
            {
                filler.entries.add(new BlockStateEntry(MalumBlocks.RUNEWOOD_LOG.get().getDefaultState(), trunkPos));
            }
            else
            {
                break;
            }
            if (i > reader.getHeight())
            {
                break;
            }
        }
        while (true);
    }

    public static void makeLeafBlob(MalumFiller filler, Random rand, BlockPos pos)
    {
        makeLeafSlice(filler, pos, 1, 0);
        makeLeafSlice(filler, pos.up(1), 2, 1);
        makeLeafSlice(filler, pos.up(2), 2, 2);
        makeLeafSlice(filler, pos.up(3), 2, 3);
        makeLeafSlice(filler, pos.up(4), 1, 4);
    }

    public static void makeLeafSlice(MalumFiller filler, BlockPos pos, int leavesSize, int leavesColor)
    {
        for (int x = -leavesSize; x <= leavesSize; x++)
        {
            for (int z = -leavesSize; z <= leavesSize; z++)
            {
                if (Math.abs(x) == leavesSize && Math.abs(z) == leavesSize)
                {
                    continue;
                }
                BlockPos leavesPos = new BlockPos(pos).add(x, 0, z);
                filler.entries.add(new BlockStateEntry(MalumBlocks.RUNEWOOD_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE, 1).with(MalumLeavesBlock.COLOR, leavesColor), leavesPos));
            }
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