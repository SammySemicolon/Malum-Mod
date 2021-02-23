package com.sammy.malum;

import com.sammy.malum.common.blocks.extractionfocus.ExtractionFocusBlock;
import com.sammy.malum.common.blocks.itemstand.ItemStandTileEntity;
import com.sammy.malum.core.init.MalumEffects;
import com.sammy.malum.core.init.particles.MalumParticles;
import com.sammy.malum.core.systems.particles.data.MalumParticleData;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.IParticleData;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.Property;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.malum.MalumMod.MODID;

public class MalumHelper
{
    public static Vector3d[] offsets = new Vector3d[]{
            new Vector3d(0,0,1),
            new Vector3d(1,0,1),
            new Vector3d(1,0,0),
            new Vector3d(1,0,-1),
            new Vector3d(0,0,-1),
            new Vector3d(-1,0,-1),
            new Vector3d(-1,0,0),
            new Vector3d(-1,0,1)
    };
    public static Color darker(Color color, int times)
    {
        for (int i = 0; i < times; i++)
        {
            color = color.darker();
        }
        return color;
    }
    public static Color brighter(Color color, int times)
    {
        for (int i = 0; i < times; i++)
        {
            color = color.brighter();
        }
        return color;
    }
    public static <T extends LivingEntity> boolean damageItem(ItemStack stack, int amount, T entityIn, Consumer<T> onBroken)
    {
        if (!entityIn.world.isRemote && (!(entityIn instanceof PlayerEntity) || !((PlayerEntity) entityIn).abilities.isCreativeMode))
        {
            if (stack.isDamageable())
            {
                amount = stack.getItem().damageItem(stack, amount, entityIn, onBroken);
                if (stack.attemptDamageItem(amount, entityIn.getRNG(), entityIn instanceof ServerPlayerEntity ? (ServerPlayerEntity) entityIn : null))
                {
                    onBroken.accept(entityIn);
                    Item item = stack.getItem();
                    stack.shrink(1);
                    if (entityIn instanceof PlayerEntity)
                    {
                        ((PlayerEntity) entityIn).addStat(Stats.ITEM_BROKEN.get(item));
                    }
                    
                    stack.setDamage(0);
                    return true;
                }
    
            }
        }
        return false;
    }
    public static <T extends Entity> Entity getClosestEntity(List<T> entities, Vector3d pos)
    {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;
        
        for (T entity : entities)
        {
            double newDistance = entity.getDistanceSq(pos.x, pos.y, pos.z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance)
            {
                cachedDistance = newDistance;
                resultEntity = entity;
            }
        }
        return resultEntity;
    }
    public static Vector3d pos(BlockPos pos)
    {
        return new Vector3d(pos.getX(),pos.getY(),pos.getZ());
    }
    public static Vector3f fPos(BlockPos pos)
    {
        return new Vector3f(pos.getX(),pos.getY(),pos.getZ());
    }
    public static Vector3d randPos(BlockPos pos, Random rand, double min, double max)
    {
        double x = MathHelper.nextDouble(rand, min, max) + pos.getX();
        double y = MathHelper.nextDouble(rand, min, max) + pos.getY();
        double z = MathHelper.nextDouble(rand, min, max) + pos.getZ();
        return new Vector3d(x, y, z);
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
    
    public static BlockPos extractionFocus(World world, BlockPos pos, Direction... excludedDirections)
    {
        ArrayList<Direction> excludedDirectionsArray = MalumHelper.toArrayList(excludedDirections);
        ArrayList<Direction> directions = MalumHelper.toArrayList(Arrays.stream(Direction.values()).filter(b -> !excludedDirectionsArray.contains(b)));
        for (Direction direction : directions)
        {
            BlockPos offsetPosition = pos.add(direction.getDirectionVec());
            if (world.getBlockState(offsetPosition).getBlock() instanceof ExtractionFocusBlock)
            {
                return offsetPosition;
            }
        }
        return null;
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
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items)
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
    
    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred)
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
    
    public static void setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState, int flags)
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
            world.notifyBlockUpdate(pos, oldState, finalState, flags);
        
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
        worldIn.notifyBlockUpdate(pos, state, state, 2);
    }
    public static void updateState(World worldIn, BlockPos pos)
    {
        updateState(worldIn.getBlockState(pos),worldIn,pos);
    }
    
    public static Vector3d circlePosition(Vector3d pos, float distance, float current, float total)
    {
        double angle = current / total * (Math.PI * 2);
        double dx2 = (distance * Math.cos(angle));
        double dz2 = (distance * Math.sin(angle));
        
        Vector3d vector2f = new Vector3d(dx2,0,dz2);
        double x = vector2f.x * distance;
        double z = vector2f.z * distance;
        return pos.add(x,0,z);
    }
    public static Vector3d rotatedCirclePosition(Vector3d pos, float distance, float current, float total, long gameTime, float time)
    {
        double angle = current / total * (Math.PI * 2);
        angle += ((gameTime % time) / time) * (Math.PI * 2);
        double dx2 = (distance * Math.cos(angle));
        double dz2 = (distance * Math.sin(angle));
        
        Vector3d vector2f = new Vector3d(dx2,0,dz2);
        double x = vector2f.x * distance;
        double z = vector2f.z * distance;
        return pos.add(x,0,z);
    }
    public static ArrayList<Vector3d> blockOutlinePositions(World world, BlockPos pos)
    {
        ArrayList<Vector3d> arrayList = new ArrayList<>();
        double d0 = 0.5625D;
        Random random = world.rand;
        for (Direction direction : Direction.values())
        {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos))
            {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();
                arrayList.add(new Vector3d((double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3));
            }
        }
        return arrayList;
    }
    public static void giveAmplifyingEffect(Effect effect, LivingEntity target, int duration, int amplifier, int cap)
    {
        EffectInstance instance = target.getActivePotionEffect(effect);
        if (instance != null)
        {
            amplifier += instance.amplifier+1;
        }
        target.addPotionEffect(new EffectInstance(effect, duration, Math.min(amplifier, cap)));
    }
    public static void giveStackingEffect(Effect effect, LivingEntity target, int duration, int amplifier)
    {
        EffectInstance instance = target.getActivePotionEffect(effect);
        if (instance != null)
        {
            duration += instance.duration;
        }
        target.addPotionEffect(new EffectInstance(effect, duration, amplifier));
    }
    public static void giveItemToPlayerNoSound(PlayerEntity player, @Nonnull ItemStack stack)
    {
        if (stack.isEmpty()) return;
        IItemHandler inventory = new PlayerMainInvWrapper(player.inventory);
        World world = player.world;
        ItemStack remainder = stack;
        if (!remainder.isEmpty())
        {
            remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
        }
        if (!remainder.isEmpty() && !world.isRemote)
        {
            ItemEntity entityitem = new ItemEntity(world, player.getPosX(), player.getPosY() + 0.5, player.getPosZ(), remainder);
            entityitem.setPickupDelay(40);
            entityitem.setMotion(entityitem.getMotion().mul(0, 1, 0));
            world.addEntity(entityitem);
        }
    }
}