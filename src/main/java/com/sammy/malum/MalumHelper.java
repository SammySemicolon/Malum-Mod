package com.sammy.malum;

import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.state.Property;
import net.minecraft.stats.Stats;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import org.apache.commons.lang3.tuple.ImmutableTriple;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sammy.malum.MalumMod.MODID;

public class MalumHelper {
    public static ItemStack copyWithNewCount(ItemStack stack, int newCount) {
        ItemStack newStack = stack.copy();
        newStack.setCount(newCount);
        return newStack;
    }

    public static Color darker(Color color, int times) {
        float FACTOR = (float) Math.pow(0.7f, times);
        return new Color(Math.max((int) (color.getRed() * FACTOR), 0),
                Math.max((int) (color.getGreen() * FACTOR), 0),
                Math.max((int) (color.getBlue() * FACTOR), 0),
                color.getAlpha());
    }

    public static Color brighter(Color color, int times) {
        float FACTOR = (float) Math.pow(0.7f, times);
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();
        int alpha = color.getAlpha();

        int i = (int) (1.0 / (1.0 - FACTOR));
        if (r == 0 && g == 0 && b == 0) {
            return new Color(i, i, i, alpha);
        }
        if (r > 0 && r < i) r = i;
        if (g > 0 && g < i) g = i;
        if (b > 0 && b < i) b = i;

        return new Color(Math.min((int) (r / FACTOR), 255),
                Math.min((int) (g / FACTOR), 255),
                Math.min((int) (b / FACTOR), 255),
                alpha);
    }

    public static <T extends LivingEntity> boolean damageItem(ItemStack stack, int amount, T entityIn, Consumer<T> onBroken) {
        if (!entityIn.world.isRemote && (!(entityIn instanceof PlayerEntity) || !((PlayerEntity) entityIn).abilities.isCreativeMode)) {
            if (stack.isDamageable()) {
                amount = stack.getItem().damageItem(stack, amount, entityIn, onBroken);
                if (stack.attemptDamageItem(amount, entityIn.getRNG(), entityIn instanceof ServerPlayerEntity ? (ServerPlayerEntity) entityIn : null)) {
                    onBroken.accept(entityIn);
                    Item item = stack.getItem();
                    stack.shrink(1);
                    if (entityIn instanceof PlayerEntity) {
                        ((PlayerEntity) entityIn).addStat(Stats.ITEM_BROKEN.get(item));
                    }

                    stack.setDamage(0);
                    return true;
                }

            }
        }
        return false;
    }

    public static <T extends Entity> Entity getClosestEntity(List<T> entities, Vector3d pos) {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;

        for (T entity : entities) {
            double newDistance = entity.getDistanceSq(pos.x, pos.y, pos.z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance) {
                cachedDistance = newDistance;
                resultEntity = entity;
            }

        }
        return resultEntity;
    }

    public static Vector3d pos(BlockPos pos) {
        return new Vector3d(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector3f fPos(BlockPos pos) {
        return new Vector3f(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector3d randPos(BlockPos pos, Random rand, double min, double max) {
        double x = MathHelper.nextDouble(rand, min, max) + pos.getX();
        double y = MathHelper.nextDouble(rand, min, max) + pos.getY();
        double z = MathHelper.nextDouble(rand, min, max) + pos.getZ();
        return new Vector3d(x, y, z);
    }

    public static boolean areWeOnClient(World world) {
        return world.isRemote;
    }

    public static boolean areWeOnServer(World world) {
        return !world.isRemote;
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static ResourceLocation moddedLocation(String notResourceLocation) {
        String[] values = notResourceLocation.split(":");
        String modId = values[0];
        String path = values[1];
        return new ResourceLocation(modId, path);
    }

    public static <T> ArrayList<T> toArrayList(T... items) {
        return new ArrayList<>(Arrays.asList(items));
    }

    public static <T> ArrayList<T> toArrayList(Stream<T> items) {
        return items.collect(Collectors.toCollection(ArrayList::new));
    }

    public static ArrayList<ItemStack> nonEmptyStackList(ArrayList<ItemStack> stacks) {
        ArrayList<ItemStack> nonEmptyStacks = new ArrayList<>();
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty()) {
                nonEmptyStacks.add(stack);
            }
        }
        return nonEmptyStacks;
    }

    public static String toTitleCase(String givenString, String regex) {
        String[] stringArray = givenString.split(regex);
        StringBuilder stringBuilder = new StringBuilder();
        for (String string : stringArray) {
            stringBuilder.append(Character.toUpperCase(string.charAt(0))).append(string.substring(1)).append(regex);
        }
        return stringBuilder.toString().trim().replaceAll(regex, " ").substring(0, stringBuilder.length() - 1);
    }

    public static int[] nextInts(Random rand, int count, int range) {
        int[] ints = new int[count];
        for (int i = 0; i < count; i++) {
            while (true) {
                int nextInt = rand.nextInt(range);
                if (Arrays.stream(ints).noneMatch(j -> j == nextInt)) {
                    ints[i] = nextInt;
                    break;
                }
            }
        }
        return ints;
    }

    public static <T> boolean hasDuplicate(T[] things) {
        Set<T> thingSet = new HashSet<>();
        return !Arrays.stream(things).allMatch(thingSet::add);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                return Collections.emptyList();
            }
        }
        if (!src.removeAll(ret)) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred) {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext()) {
            T item = iter.next();
            if (pred.test(item)) {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static BlockState getBlockStateWithExistingProperties(BlockState oldState, BlockState newState) {
        BlockState finalState = newState;
        for (Property<?> property : oldState.getProperties()) {
            if (newState.hasProperty(property)) {
                finalState = newStateWithOldProperty(oldState, finalState, property);
            }
        }
        return finalState;
    }

    public static void setBlockStateWithExistingProperties(World world, BlockPos pos, BlockState newState, int flags) {
        BlockState oldState = world.getBlockState(pos);
        BlockState finalState = getBlockStateWithExistingProperties(oldState, newState);
        world.notifyBlockUpdate(pos, oldState, finalState, flags);
        world.setBlockState(pos, finalState, flags);
    }

    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, Property<T> property) {
        return newState.with(property, oldState.get(property));
    }

    public static CompoundNBT writeBlockPos(CompoundNBT compoundNBT, BlockPos pos) {
        compoundNBT.putInt("X", pos.getX());
        compoundNBT.putInt("Y", pos.getY());
        compoundNBT.putInt("Z", pos.getZ());
        return compoundNBT;
    }

    public static CompoundNBT writeBlockPos(CompoundNBT compoundNBT, BlockPos pos, String extra) {
        compoundNBT.putInt(extra + "X", pos.getX());
        compoundNBT.putInt(extra + "Y", pos.getY());
        compoundNBT.putInt(extra + "Z", pos.getZ());
        return compoundNBT;
    }

    public static BlockPos readBlockPos(CompoundNBT tag) {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }

    public static BlockPos readBlockPos(CompoundNBT tag, String extra) {
        return new BlockPos(tag.getInt(extra + "X"), tag.getInt(extra + "Y"), tag.getInt(extra + "Z"));
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int range, Predicate<BlockPos> predicate) {
        return getBlocks(pos, range, range, range, predicate);
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x, int y, int z, Predicate<BlockPos> predicate) {
        ArrayList<BlockPos> blocks = getBlocks(pos, x, y, z);
        blocks.removeIf(b -> !predicate.test(b));
        return blocks;
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x, int y, int z) {
        return getBlocks(pos, -x, -y, -z, x, y, z);
    }

    public static ArrayList<BlockPos> getBlocks(BlockPos pos, int x1, int y1, int z1, int x2, int y2, int z2) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int y = y1; y <= y2; y++) {
                for (int z = z1; z <= z2; z++) {
                    positions.add(pos.add(x, y, z));
                }
            }
        }
        return positions;
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int range, Predicate<BlockPos> predicate) {
        return getPlaneOfBlocks(pos, range, range, predicate);
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x, int z, Predicate<BlockPos> predicate) {
        ArrayList<BlockPos> blocks = getPlaneOfBlocks(pos, x, z);
        blocks.removeIf(b -> !predicate.test(b));
        return blocks;
    }

    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x, int z) {
        return getPlaneOfBlocks(pos, -x, -z, x, z);
    }
    public static ArrayList<BlockPos> getPlaneOfBlocks(BlockPos pos, int x1, int z1, int x2, int z2) {
        ArrayList<BlockPos> positions = new ArrayList<>();
        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                positions.add(new BlockPos(pos.getX()+x, pos.getY(), pos.getZ()+z));
            }
        }
        return positions;
    }

    public static void updateState(World worldIn, BlockPos pos) {
        updateState(worldIn.getBlockState(pos), worldIn, pos);
    }

    public static void updateState(BlockState state, World worldIn, BlockPos pos) {
        worldIn.notifyBlockUpdate(pos, state, state, 2);
    }

    public static void updateAndNotifyState(World worldIn, BlockPos pos) {
        updateAndNotifyState(worldIn.getBlockState(pos), worldIn, pos);
    }

    public static void updateAndNotifyState(BlockState state, World worldIn, BlockPos pos) {
        worldIn.notifyBlockUpdate(pos, state, state, 2);
        state.updateNeighbours(worldIn, pos, 2);
    }


    public static Vector3d circlePosition(Vector3d pos, float distance, float current, float total) {
        double angle = current / total * (Math.PI * 2);
        double dx2 = (distance * Math.cos(angle));
        double dz2 = (distance * Math.sin(angle));

        Vector3d vector2f = new Vector3d(dx2, 0, dz2);
        double x = vector2f.x * distance;
        double z = vector2f.z * distance;
        return pos.add(x, 0, z);
    }

    public static Vector3d rotatedCirclePosition(Vector3d pos, float distance, float current, float total, long gameTime, float time) {
        return rotatedCirclePosition(pos, distance, distance, current, total, gameTime, time);
    }

    public static Vector3d rotatedCirclePosition(Vector3d pos, float distanceX, float distanceZ, float current, float total, long gameTime, float time) {
        double angle = current / total * (Math.PI * 2);
        angle += ((gameTime % time) / time) * (Math.PI * 2);
        double dx2 = (distanceX * Math.cos(angle));
        double dz2 = (distanceZ * Math.sin(angle));

        Vector3d vector2f = new Vector3d(dx2, 0, dz2);
        double x = vector2f.x * distanceX;
        double z = vector2f.z * distanceZ;
        return pos.add(x, 0, z);
    }

    public static ArrayList<Vector3d> blockOutlinePositions(World world, BlockPos pos) {
        ArrayList<Vector3d> arrayList = new ArrayList<>();
        double d0 = 0.5625D;
        Random random = world.rand;
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.offset(direction);
            if (!world.getBlockState(blockpos).isOpaqueCube(world, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getXOffset() : (double) random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getYOffset() : (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getZOffset() : (double) random.nextFloat();
                arrayList.add(new Vector3d((double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3));
            }
        }
        return arrayList;
    }

    public static void giveAmplifyingEffect(Effect effect, LivingEntity target, int duration, int amplifier, int cap) {
        EffectInstance instance = target.getActivePotionEffect(effect);
        if (instance != null) {
            amplifier += instance.amplifier + 1;
        }
        target.addPotionEffect(new EffectInstance(effect, duration, Math.min(amplifier, cap)));
    }

    public static void giveStackingEffect(Effect effect, LivingEntity target, int duration, int amplifier) {
        EffectInstance instance = target.getActivePotionEffect(effect);
        if (instance != null) {
            duration += instance.duration;
        }
        target.addPotionEffect(new EffectInstance(effect, duration, amplifier));
    }

    public static void giveItemToPlayerNoSound(PlayerEntity player, @Nonnull ItemStack stack) {
        if (stack.isEmpty()) return;
        IItemHandler inventory = new PlayerMainInvWrapper(player.inventory);
        World world = player.world;
        ItemStack remainder = stack;
        if (!remainder.isEmpty()) {
            remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
        }
        if (!remainder.isEmpty() && !world.isRemote) {
            ItemEntity entityitem = new ItemEntity(world, player.getPosX(), player.getPosY() + 0.5, player.getPosZ(), remainder);
            entityitem.setPickupDelay(40);
            entityitem.setMotion(entityitem.getMotion().mul(0, 1, 0));
            world.addEntity(entityitem);
        }
    }

    @Nonnull
    public static Optional<ImmutableTriple<String, Integer, ItemStack>> findCosmeticCurio(Predicate<ItemStack> filter, @Nonnull final LivingEntity livingEntity) {
        ImmutableTriple<String, Integer, ItemStack> result = CuriosApi.getCuriosHelper().getCuriosHandler(livingEntity).map(handler ->
        {
            Map<String, ICurioStacksHandler> curios = handler.getCurios();

            for (String id : curios.keySet()) {
                ICurioStacksHandler stacksHandler = curios.get(id);
                IDynamicStackHandler stackHandler = stacksHandler.getStacks();
                IDynamicStackHandler cosmeticStackHelper = stacksHandler.getCosmeticStacks();

                for (int i = 0; i < stackHandler.getSlots(); i++) {
                    ItemStack stack = stackHandler.getStackInSlot(i);

                    if (!stack.isEmpty() && filter.test(stack)) {
                        return new ImmutableTriple<>(id, i, stack);
                    }
                }
                for (int i = 0; i < cosmeticStackHelper.getSlots(); i++) {
                    ItemStack stack = cosmeticStackHelper.getStackInSlot(i);

                    if (!stack.isEmpty() && filter.test(stack)) {
                        return new ImmutableTriple<>(id, i, stack);
                    }
                }
            }
            return new ImmutableTriple<>("", 0, ItemStack.EMPTY);
        }).orElse(new ImmutableTriple<>("", 0, ItemStack.EMPTY));

        return result.getLeft().isEmpty() ? Optional.empty() : Optional.of(result);
    }

    public static ItemStack heldItem(LivingEntity livingEntity, Predicate<ItemStack> stackPredicate) {
        if (livingEntity.swingingHand != null) {
            if (stackPredicate.test(livingEntity.getHeldItem(livingEntity.swingingHand))) {
                return livingEntity.getHeldItem(livingEntity.swingingHand);
            }
        }
        if (stackPredicate.test(livingEntity.getHeldItem(livingEntity.getActiveHand()))) {
            return livingEntity.getHeldItem(livingEntity.getActiveHand());
        }
        if (stackPredicate.test(livingEntity.getHeldItem(Hand.MAIN_HAND))) {
            return livingEntity.getHeldItem(Hand.MAIN_HAND);
        }
        if (stackPredicate.test(livingEntity.getHeldItem(Hand.OFF_HAND))) {
            return livingEntity.getHeldItem(Hand.OFF_HAND);
        }
        return ItemStack.EMPTY;
    }

    public static boolean hasCurioEquipped(LivingEntity entity, RegistryObject<Item> curio) {
        return CuriosApi.getCuriosHelper().findEquippedCurio(curio.get(), entity).isPresent();
    }

    public static ArrayList<ItemStack> equippedCurios(LivingEntity entity) {
        Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosHelper().getEquippedCurios(entity).resolve();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (optional.isPresent()) {
            IItemHandlerModifiable handler = optional.get();
            for (int i = 0; i < handler.getSlots(); i++) {
                stacks.add(handler.getStackInSlot(i));
            }
        }
        return stacks;
    }

    public static ArrayList<ItemStack> equippedCurios(LivingEntity entity, Predicate<ItemStack> predicate) {
        Optional<IItemHandlerModifiable> optional = CuriosApi.getCuriosHelper().getEquippedCurios(entity).resolve();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (optional.isPresent()) {
            IItemHandlerModifiable handler = optional.get();
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack stack = handler.getStackInSlot(i);
                if (predicate.test(stack)) {
                    stacks.add(stack);
                }
            }
        }
        return stacks;
    }

    public static void applyEnchantments(LivingEntity user, Entity target, ItemStack stack) {
        EnchantmentHelper.IEnchantmentVisitor visitor = (enchantment, level) ->
                enchantment.onEntityDamaged(user, target, level);
        if (user != null) {
            EnchantmentHelper.applyEnchantmentModifierArray(visitor, user.getEquipmentAndArmor());
        }

        if (user instanceof PlayerEntity) {
            EnchantmentHelper.applyEnchantmentModifier(visitor, stack);
        }
    }

    public static void giveItemToEntity(ItemStack item, LivingEntity entity) {
        if (entity instanceof PlayerEntity) {
            ItemHandlerHelper.giveItemToPlayer((PlayerEntity) entity, item);
        } else {
            ItemEntity itemEntity = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY() + 0.5, entity.getPosZ(), item);
            itemEntity.setPickupDelay(40);
            itemEntity.setMotion(itemEntity.getMotion().mul(0, 1, 0));
            entity.world.addEntity(itemEntity);
        }
    }

    public static Block[] getModBlocks(Class<?>... blockClasses) {
        Collection<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries();
        ArrayList<Block> matchingBlocks = new ArrayList<>();
        for (RegistryObject<Block> registryObject : blocks) {
            if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(registryObject.get()))) {
                matchingBlocks.add(registryObject.get());
            }
        }
        return matchingBlocks.toArray(new Block[0]);
    }

    public static Item[] getModItems(Class<?>... itemClasses) {
        Collection<RegistryObject<Item>> items = ItemRegistry.ITEMS.getEntries();
        ArrayList<Item> matchingItems = new ArrayList<>();
        for (RegistryObject<Item> registryObject : items) {
            if (Arrays.stream(itemClasses).anyMatch(i -> i.isInstance(registryObject.get()))) {
                matchingItems.add(registryObject.get());
            }
        }
        return matchingItems.toArray(new Item[0]);
    }
}