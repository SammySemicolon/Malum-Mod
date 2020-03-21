package com.kittykitcatcat.malum;

import net.minecraft.block.BlockState;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class MalumHelper
{
    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, IProperty<T> property)
    {
        return newState.with(property, oldState.get(property));
    }

    public static void setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState)
    {
        BlockState oldState = world.getBlockState(pos);

        BlockState finalState = newState;
        for (IProperty<?> property : oldState.getProperties())
        {
            if (newState.has(property))
            {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }

        world.notifyBlockUpdate(pos, oldState, finalState, 3);
        world.setBlockState(pos, finalState);
    }
    public static Vec3d randVelocity(World world, float min, float max)
    {
        double x = MathHelper.nextFloat(world.rand, min, max);
        double y = MathHelper.nextFloat(world.rand, min, max);
        double z = MathHelper.nextFloat(world.rand, min, max);
        return new Vec3d(x,y,z);
    }
    public static Vec3d randPos(BlockPos pos, World world, float min, float max)
    {
        double x = MathHelper.nextFloat(world.rand, min, max) + pos.getX();
        double y = MathHelper.nextFloat(world.rand, min, max) + pos.getY();
        double z = MathHelper.nextFloat(world.rand, min, max) + pos.getZ();
        return new Vec3d(x,y,z);
    }
    public static Vec3d randPos(Vec3d pos, World world, float min, float max)
    {
        double x = MathHelper.nextFloat(world.rand, min, max) + pos.getX();
        double y = MathHelper.nextFloat(world.rand, min, max) + pos.getY();
        double z = MathHelper.nextFloat(world.rand, min, max) + pos.getZ();
        return new Vec3d(x,y,z);
    }
}
