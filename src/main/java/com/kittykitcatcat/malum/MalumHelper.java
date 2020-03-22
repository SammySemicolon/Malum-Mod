package com.kittykitcatcat.malum;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings("unused")
public class MalumHelper
{
    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, IProperty<T> property)
    {
        return newState.with(property, oldState.get(property));
    }
    public static void updateState(World world, BlockState state, BlockPos pos)
    {
        world.notifyBlockUpdate(pos, state,state,3);
    }
    public static void updateState(World world, BlockState state, BlockState newState, BlockPos pos)
    {
        world.notifyBlockUpdate(pos, state,newState,3);
    }
    public static void setStackInTEInventory(ItemStackHandler inventory, ItemStack stack, int slot)
    {
        inventory.setStackInSlot(slot, stack);
    }
    public static void addStackToTEInventory(ItemStackHandler inventory, ItemStack stack, int slot)
    {
        inventory.getStackInSlot(slot).setCount(inventory.getStackInSlot(slot).getCount() + stack.getCount());
    }
    public static void increaseStackSizeInTEInventory(ItemStackHandler inventory, int amount, int slot)
    {
        inventory.getStackInSlot(slot).setCount(inventory.getStackInSlot(slot).getCount() + amount);
    }
    public static void giveItemStackToPlayer(PlayerEntity target, ItemStack stack)
    {
        target.world.addEntity(new ItemEntity(target.world, target.getPosX(), target.getPosY(),target.getPosZ(), stack));
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
