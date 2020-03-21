package com.kittykitcatcat.malum.blocks;


import com.kittykitcatcat.malum.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class SpiritSaplingBlock extends BushBlock implements IGrowable
{
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public SpiritSaplingBlock(Properties properties)
    {
        super(properties);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
    @Override
    public boolean isTransparent(BlockState state)
    {
        return true;
    }

    @Override
    public boolean canGrow(IBlockReader worldIn, BlockPos pos, BlockState state, boolean isClient)
    {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, BlockState state)
    {
        return true;
    }


    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state)
    {
        int baseHeight = MathHelper.nextInt(world.rand, 4, 6)-2;
        int secondLogStartHeight = baseHeight - MathHelper.nextInt(world.rand, 1, 2)+2;
        int leafDomeHeight = baseHeight + MathHelper.nextInt(world.rand, 2, 4)+2;
        makeLogs(world, pos, baseHeight+1, 0);
        makeStump(world,pos);
        BlockPos offsetPos = new BlockPos(pos);
        switch(MathHelper.nextInt(new Random(), 0, 3))
        {
            case(0):
            {
                offsetPos = pos.east();
                break;
            }
            case(1):
            {
                offsetPos = pos.south();
                break;
            }
            case(2):
            {
                offsetPos = pos.west();
                break;
            }
            case(3):
            {
                offsetPos = pos.north();
                break;
            }
        }
        makeLeaves(world, offsetPos.up(leafDomeHeight).down(1), 3);
        makeLeaves(world, offsetPos.up(leafDomeHeight).down(2), 2);
        makeLogs(world, offsetPos, leafDomeHeight, secondLogStartHeight);
        makeLeafDome(world,offsetPos.up(leafDomeHeight), 3);
        makeSideLeaves(world,offsetPos.up(leafDomeHeight).down(2), 3);
    }
    public void makeLeafDome(ServerWorld world, BlockPos pos, int size)
    {
        for (int x = 0; x < size; x++)
        {
            makeLeaves(world, pos.up(x), size - x);
        }
    }
    public void makeLeaves(ServerWorld world, BlockPos pos, int leafRadius)
    {
        for (int x = -leafRadius; x <= leafRadius; x++)
        {
            for (int z = -leafRadius; z <= leafRadius; z++)
            {
                if (!((x == -leafRadius || x == leafRadius) && (z == -leafRadius || z == leafRadius)))
                {
                    makeLeaf(world, pos.east(x).north(z));
                }
            }
        }
    }
    public void makeStump(ServerWorld world, BlockPos pos)
    {
        world.setBlockState(pos, ModBlocks.spirit_log.getDefaultState());
        makeLogs(world, pos.east(), MathHelper.nextInt(world.rand,0, 2),0);
        makeLogs(world, pos.north(), MathHelper.nextInt(world.rand,0, 2),0);
        makeLogs(world, pos.west(), MathHelper.nextInt(world.rand,0, 2),0);
        makeLogs(world, pos.south(), MathHelper.nextInt(world.rand,0, 2) ,0);
    }
    public void makeSideLeaves(ServerWorld world, BlockPos pos, int offset)
    {
        makeLeaves(world, pos.east(offset), MathHelper.nextInt(world.rand,1, 4),0);
        makeLeaves(world, pos.north(offset), MathHelper.nextInt(world.rand,1, 4),0);
        makeLeaves(world, pos.west(offset), MathHelper.nextInt(world.rand,1, 4),0);
        makeLeaves(world, pos.south(offset), MathHelper.nextInt(world.rand,1, 4) ,0);
    }
    public void makeLogs(ServerWorld world, BlockPos pos, int maxHeight, int startingHeight)
    {
        for (int i = startingHeight; i <= maxHeight; i++)
        {
            makeLog(world, pos.up(i));
        }
    }
    public void makeLeaves(ServerWorld world, BlockPos pos, int maxHeight, int startingHeight)
    {
        for (int i = startingHeight; i <= maxHeight; i++)
        {
            makeLeaf(world, pos.down(i));
        }
    }
    public void makeLeaf(ServerWorld world, BlockPos pos)
    {
        if (world.getBlockState(pos).isAir())
        {
            world.setBlockState(pos, ModBlocks.spirit_leaves.getDefaultState());
        }
    }
    public void makeLog(ServerWorld world, BlockPos pos)
    {
        if (world.getBlockState(pos).isAir() || world.getBlockState(pos).equals(ModBlocks.spirit_leaves.getDefaultState()))
        {
            world.setBlockState(pos, ModBlocks.spirit_log.getDefaultState());
        }
    }
}