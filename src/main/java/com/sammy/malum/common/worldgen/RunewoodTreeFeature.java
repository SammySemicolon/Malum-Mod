package com.sammy.malum.common.worldgen;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.common.blocks.MalumLeavesBlock;
import com.sammy.malum.common.blocks.RunewoodSaplingBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import com.sammy.malum.core.systems.worldgen.MalumFiller;
import com.sammy.malum.core.systems.worldgen.MalumFiller.BlockStateEntry;
import net.minecraft.block.*;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
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

    public static int minimumTrunkHeight = 9;
    public static int extraTrunkHeight = 3;
    public static int minimumSideTrunkHeight = 2;
    public static int extraSideTrunkHeight = 2;
    public static int minimumSideSideTrunkHeight = 1;
    public static int extraSideSideTrunkHeight = 1;

    public static int maximumDownwardsBranchOffset = 4;
    public static int minimumBranchCoreOffset = 3;
    public static int branchCoreOffsetExtra = 1;
    public static int minimumBranchHeight = 4;
    public static int branchHeightExtra = 3;

    public static int leavesHeight = 8;
    public static int leavesHeightExtra = 4;
    public static int leavesStartDownwardsOffset = 1;
    public static int leavesStartDownwardsOffsetExtra = 2;
    public static int leavesSize = 1;
    public static int leavesSizeCap = 3;
    public static int leavesSizeExtra = 1;
    public static int leavesShrinkStart = 5;


    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
    {
        if (reader.isAirBlock(pos.down()) || !(reader.getBlockState(pos.down()).getBlock() instanceof GrassBlock))
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
            int sideSideTrunkHeight = minimumSideSideTrunkHeight + rand.nextInt(extraSideSideTrunkHeight + 1);
            for (Direction direction2 : directions) //side SIDE trunk placement
            {
                if (rand.nextFloat() < 0.4f)
                {
                    for (int i = 0; i < sideSideTrunkHeight; i++)
                    {
                        if (!direction2.equals(direction.getOpposite()) && !direction2.equals(direction))
                        {
                            BlockPos sideSideTrunkPos = pos.offset(direction).offset(direction2).up(i);
                            if (canPlace(reader, sideSideTrunkPos))
                            {
                                treeFiller.entries.add(new BlockStateEntry(defaultLog, sideSideTrunkPos));
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }
                    downwardsTrunk(reader, treeFiller, pos.offset(direction).offset(direction2));
                }
            }
            downwardsTrunk(reader, treeFiller, pos.offset(direction));
        }
        for (Direction direction : directions) //tree top placement
        {
            int branchCoreOffset = rand.nextInt(maximumDownwardsBranchOffset + 1);
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
            for (int i = 0; i <= branchHeight; i++) //branch placement
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
            makeLeafBlob(leavesFiller, rand, branchStartPos.up(branchHeight - 1));
        }

        MalumFiller groundFiller = new MalumFiller();
        MalumFiller grassFiller = new MalumFiller();
        makeHallowedGround(reader, groundFiller, grassFiller, rand, pos.down(), 4, 0);

        for (Direction direction : directions) //hallowed ground placement
        {
            BlockPos groundPos = pos.down().offset(direction, 2);
            makeHallowedGround(reader, groundFiller, grassFiller, rand, groundPos, 4, 0.2f);
        }
        for (int i = 0; i < 2 + rand.nextInt(3); i++)
        {
            int x = MathHelper.nextInt(rand, -5, 5);
            int z = MathHelper.nextInt(rand, -5, 5);
            BlockPos groundPos = pos.down().add(x, 0, z);
            makeHallowedGround(reader, groundFiller, grassFiller, rand, groundPos, 3, 0.5f);
        }
        groundFiller.fill(reader, false);
        treeFiller.fill(reader, false);
        leavesFiller.fill(reader, true);
        grassFiller.fill(reader, true);
        return true;
    }

    public static void makeHallowedGround(ISeedReader reader, MalumFiller groundFiller, MalumFiller grassFiller, Random rand, BlockPos pos, int size, float tallGrassProbability)
    {
        for (int x = -size; x <= size; x++)
        {
            for (int z = -size; z <= size; z++)
            {
                if (Math.abs(x) == size && Math.abs(z) == size)
                {
                    continue;
                }
                BlockPos grassPos = reader.getHeight(Heightmap.Type.WORLD_SURFACE, pos.add(x, 0, z)).down();
                do
                {
                    if (reader.getBlockState(grassPos).getBlock() instanceof BushBlock)
                    {
                        groundFiller.entries.add(new BlockStateEntry(Blocks.AIR.getDefaultState(), grassPos));
                        grassPos = grassPos.down();
                    }
                    else
                    {
                        break;
                    }
                }
                while (true);
                if (reader.getBlockState(grassPos).getBlock() instanceof GrassBlock)
                {
                    groundFiller.entries.add(new BlockStateEntry(MalumBlocks.SUN_KISSED_GRASS_BLOCK.get().getDefaultState(), grassPos));
                    if (rand.nextFloat() < 0.25f)
                    {
                        if (rand.nextFloat() < tallGrassProbability)
                        {
                            DoublePlantBlock tallGrassBlock = null;
                            if (rand.nextBoolean())
                            {
                                tallGrassBlock = (DoublePlantBlock) MalumBlocks.TALL_SUN_KISSED_GRASS.get();
                            }
                            else
                            {
                                tallGrassBlock = (DoublePlantBlock) MalumBlocks.LAVENDER.get();
                            }
                            BlockStateEntry tallGrassEntry = new BlockStateEntry(tallGrassBlock.getDefaultState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER), grassPos.up())
                            {
                                @Override
                                public boolean canPlace(ISeedReader reader)
                                {
                                    return super.canPlace(reader) && super.canPlace(reader, pos.up());
                                }

                                @Override
                                public void additionalPlacement(ISeedReader reader)
                                {
                                    reader.setBlockState(pos.up(), state.with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER), 3);
                                    if (reader instanceof World)
                                    {
                                        MalumHelper.updateState((World) reader, pos.up());
                                    }
                                }
                            };
                            grassFiller.entries.add(tallGrassEntry);
                        }
                        else
                        {
                            groundFiller.entries.add(new BlockStateEntry(MalumBlocks.SUN_KISSED_GRASS.get().getDefaultState(), grassPos.up()));
                        }
                    }
                }
            }
        }
    }

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
        int randomOffset = leavesStartDownwardsOffset + rand.nextInt(leavesStartDownwardsOffsetExtra + 1);
        int finalLeavesHeight = leavesHeight + rand.nextInt(leavesHeightExtra + 1);

        int extraSize = rand.nextInt(leavesSizeExtra + 1);
        int sizeCap = leavesSizeCap + extraSize;
        int size = leavesSize - 1 + extraSize;
        for (int i = 0; i < finalLeavesHeight; i++)
        {
            BlockPos blobSliceCenter = pos.down(randomOffset).up(i);
            if (i < leavesShrinkStart && size < sizeCap)
            {
                size++;
            }
            else
            {
                size--;
            }
            int color = Math.min(i, 9);
            if (color == 9 && rand.nextBoolean())
            {
                color -= rand.nextInt(3);
            }
            makeLeafSlice(filler, blobSliceCenter, size, color);
        }
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
                filler.entries.add(new BlockStateEntry(MalumBlocks.SUN_KISSED_LEAVES.get().getDefaultState().with(LeavesBlock.DISTANCE, 1).with(MalumLeavesBlock.COLOR, leavesColor), leavesPos));
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