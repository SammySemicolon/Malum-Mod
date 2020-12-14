package com.sammy.malum;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.Property;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.malum.MalumMod.MODID;

public class MalumHelper
{
    public static ResourceLocation prefix(String path)
    {
        return new ResourceLocation(MODID, path);
    }
    
    public static <T> ArrayList<T> toArrayList(T... items)
    {
        return new ArrayList<>(Arrays.asList(items));
    }
    
    public static <T> ArrayList<T> toArrayList(Stream<T> items)
    {
        return items.collect(Collectors.toCollection(ArrayList::new));
    }
    
    public static ArrayList<ItemStack> nonEmptyStackList(ArrayList<ItemStack> stacks)
    {
        ArrayList<ItemStack> nonEmptyStacks = new ArrayList<>();
        for (ItemStack stack : stacks)
        {
            if (!stack.isEmpty())
            {
                nonEmptyStacks.add(stack);
            }
        }
        return nonEmptyStacks;
    }
    
    public static String toTitleCase(String givenString, String regex)
    {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray)
        {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }
    
    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Set<? extends T> src, T... items)
    {
        List<T> ret = Arrays.asList(items);
        for (T item : items)
        {
            if (!src.contains(item))
            {
                return Collections.emptyList();
            }
        }
        if (!src.removeAll(ret))
        {
            return Collections.emptyList();
        }
        return ret;
    }
    
    public static <T> Collection<T> takeAll(Set<T> src, Predicate<T> pred)
    {
        List<T> ret = new ArrayList<>();
        
        Iterator<T> iter = src.iterator();
        while (iter.hasNext())
        {
            T item = iter.next();
            if (pred.test(item))
            {
                iter.remove();
                ret.add(item);
            }
        }
        
        if (ret.isEmpty())
        {
            return Collections.emptyList();
        }
        return ret;
    }
    
    public static void setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState, int flags, boolean notify)
    {
        BlockState oldState = world.getBlockState(pos);
        
        BlockState finalState = newState;
        for (Property<?> property : oldState.getProperties())
        {
            if (newState.hasProperty(property))
            {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }
        if (notify)
        {
            world.notifyBlockUpdate(pos, oldState, finalState, flags);
        }
        world.setBlockState(pos, finalState, flags);
    }
    
    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, Property<T> property)
    {
        return newState.with(property, oldState.get(property));
    }
    
    public static CompoundNBT writeBlockPos(CompoundNBT compoundNBT, BlockPos pos)
    {
        compoundNBT.putInt("X", pos.getX());
        compoundNBT.putInt("Y", pos.getY());
        compoundNBT.putInt("Z", pos.getZ());
        return compoundNBT;
    }
    
    public static CompoundNBT writeBlockPosExtra(CompoundNBT compoundNBT, BlockPos pos, String extra)
    {
        compoundNBT.putInt(extra +"X", pos.getX());
        compoundNBT.putInt(extra +"Y", pos.getY());
        compoundNBT.putInt(extra +"Z", pos.getZ());
        return compoundNBT;
    }
    public static BlockPos readBlockPosExtra(CompoundNBT tag)
    {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }
    public static BlockPos readBlockPosExtra(CompoundNBT tag, String extra)
    {
        return new BlockPos(tag.getInt(extra +"X"), tag.getInt(extra +"Y"), tag.getInt(extra +"Z"));
    }
}