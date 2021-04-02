package com.sammy.malum.common.world.features.tree;

import com.sammy.malum.core.init.blocks.MalumBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.AlterGroundTreeDecorator;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class SunKissedGroundDecorator extends AlterGroundTreeDecorator
{
    public final BlockStateProvider stateProvider;
    
    public SunKissedGroundDecorator(BlockStateProvider stateProvider)
    {
        super(stateProvider);
        this.stateProvider = stateProvider;
    }
    @Override
    public void func_225576_a_(ISeedReader seedReader, Random random, List<BlockPos> positions, List<BlockPos> noClue, Set<BlockPos> noClue2, MutableBoundingBox area)
    {
        int i = positions.get(0).getY();
        positions.stream().filter((pos) -> pos.getY() == i).forEach((pos) ->
        {
            transformBlocks(seedReader, random, pos.west().north());
            transformBlocks(seedReader, random, pos.east(2).north());
            transformBlocks(seedReader, random, pos.west().south(2));
            transformBlocks(seedReader, random, pos.east(2).south(2));

            for (int j = 0; j < 5; ++j)
            {
                int k = random.nextInt(64);
                int l = k % 8;
                int i1 = k / 8;
                if (l == 0 || l == 7 || i1 == 0 || i1 == 7)
                {
                    transformBlocks(seedReader, random, pos.add(-3 + l, 0, -3 + i1));
                }
            }
        });
    }
    
    protected void transformBlocks(IWorldGenerationReader reader, Random random, BlockPos pos)
    {
        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                if (Math.abs(i) != 2 || Math.abs(j) != 2)
                {
                    transformBlock(reader, random, pos.add(i, 0, j));
                }
            }
        }
        
    }
    
    protected void transformBlock(IWorldGenerationReader reader, Random random, BlockPos pos)
    {
        for (int i = 2; i >= -3; --i)
        {
            BlockPos blockpos = pos.up(i);
            if (Feature.isDirtAt(reader, blockpos))
            {
                reader.setBlockState(blockpos, stateProvider.getBlockState(random, pos), 19);
                createFlower(reader, random, blockpos.up(), new Block[]{MalumBlocks.SHORT_SUN_KISSED_GRASS.get(), MalumBlocks.SUN_KISSED_GRASS.get(), MalumBlocks.TALL_SUN_KISSED_GRASS.get()}, 0.5f);
    
                createFlower(reader, random, blockpos.up(), new Block[]{MalumBlocks.LAVENDER.get()}, 0.1f);
                break;
            }
            if (!Feature.isAirAt(reader, blockpos) && i < 0)
            {
                break;
            }
        }
    }
    protected void createFlower(IWorldGenerationReader reader, Random random, BlockPos pos, Block[] blocks, float chance)
    {
        if (reader.hasBlockState(pos, s -> s.getMaterial().isReplaceable()))
        {
            reader.destroyBlock(pos, false);
        }
        if (random.nextFloat() <= chance)
        {
            Block block = blocks.length == 1 ? blocks[0] : blocks[random.nextInt(blocks.length)];
            BlockState state = block.getDefaultState();
            if (state.isValidPosition((IWorldReader) reader, pos))
            {
                if (Feature.isAirAt(reader, pos))
                {
                    if (block instanceof DoublePlantBlock)
                    {
                        ((DoublePlantBlock) state.getBlock()).placeAt((IWorld) reader, pos, 2);
                    }
                    else
                    {
                        reader.setBlockState(pos, state, 19);
                    }
                }
            }
        }
    }
}
