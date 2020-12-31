package com.sammy.malum;

import com.sammy.malum.client.particles.itemcircle.ItemCircleParticleData;
import com.sammy.malum.client.particles.spiritflame.SpiritFlameParticleData;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.malum.MalumMod.MODID;

public class MalumHelper
{
    public static Vector3f pos(BlockPos pos)
    {
        return new Vector3f(pos.getX(),pos.getY(),pos.getZ());
    }
    public static Vector3f randPos(BlockPos pos, Random rand, float min, float max)
    {
        float x = MathHelper.nextFloat(rand, min, max) + pos.getX();
        float y = MathHelper.nextFloat(rand, min, max) + pos.getY();
        float z = MathHelper.nextFloat(rand, min, max) + pos.getZ();
        return new Vector3f(x, y, z);
    }
    
    public static boolean areWeOnClient(World world)
    {
        return world.isRemote;
    }
    
    public static boolean areWeOnServer(World world)
    {
        return !areWeOnClient(world);
    }
    
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
    
    public static ArrayList<BlockPos> itemStands(World world, BlockPos pos, Direction... excludedDirections)
    {
        ArrayList<BlockPos> positions = new ArrayList<>();
        ArrayList<Direction> excludedDirectionsArray = MalumHelper.toArrayList(excludedDirections);
        ArrayList<Direction> directions = MalumHelper.toArrayList(Arrays.stream(Direction.values()).filter(b -> !excludedDirectionsArray.contains(b)));
        for (Direction direction : directions)
        {
            BlockPos offsetPosition = pos.add(direction.getDirectionVec());
            if (world.getTileEntity(offsetPosition) instanceof ItemStandTileEntity)
            {
                positions.add(offsetPosition);
            }
        }
        return positions;
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
    public static <T> boolean hasDuplicate(T[] things)
    {
        Set<T> thingSet = new HashSet<>();
        return !Arrays.stream(things).allMatch(thingSet::add);
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
        compoundNBT.putInt(extra + "X", pos.getX());
        compoundNBT.putInt(extra + "Y", pos.getY());
        compoundNBT.putInt(extra + "Z", pos.getZ());
        return compoundNBT;
    }
    
    public static BlockPos readBlockPos(CompoundNBT tag)
    {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }
    
    public static BlockPos readBlockPosExtra(CompoundNBT tag, String extra)
    {
        return new BlockPos(tag.getInt(extra + "X"), tag.getInt(extra + "Y"), tag.getInt(extra + "Z"));
    }
    
    public static void updateState(BlockState state, World worldIn, BlockPos pos)
    {
        worldIn.notifyBlockUpdate(pos, state, state, 3);
    }
    public static void makeCircle(ServerWorld world, Vector3f pos)
    {
        makeCircle(world, pos, new ItemCircleParticleData(0.75f+ world.rand.nextFloat() * 0.5f, false));
    }
    public static void makeCircle(ServerWorld world, Vector3f pos, ItemCircleParticleData data)
    {
        world.spawnParticle(data,pos.getX(),pos.getY(),pos.getZ(),1,0,0,0,0);
    }
    public static void makeFancyCircle(ServerWorld world, Vector3f pos)
    {
        MalumHelper.makeCircle(world, pos);
        world.spawnParticle(new SpiritFlameParticleData(0.25f + world.rand.nextFloat() * 0.25f, true),pos.getX(),pos.getY(),pos.getZ(),1+world.rand.nextInt(3),0.1f,0.1f,0.1f,0.05f);
    }
    public static void spawnParticles(World world, BlockPos pos, IParticleData data, float chance)
    {
        double d0 = 0.5625D;
        Random random = world.rand;
        
        for (Direction direction : Direction.values())
        {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos))
            {
                if (chance == 0f || (chance != 0f && random.nextFloat() < chance))
                {
                    Direction.Axis direction$axis = direction.getAxis();
                    double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
                    double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
                    double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();
                    world.addParticle(data, (double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        
    }
}