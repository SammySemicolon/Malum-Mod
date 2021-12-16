package com.sammy.malum;

import com.mojang.math.Vector3f;
import com.sammy.malum.core.registry.block.BlockRegistry;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fmllegacy.RegistryObject;
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
        if (!entityIn.level.isClientSide && (!(entityIn instanceof Player) || !((Player) entityIn).getAbilities().instabuild)) {
            if (stack.isDamageableItem()) {
                amount = stack.getItem().damageItem(stack, amount, entityIn, onBroken);
                if (stack.hurt(amount, entityIn.getRandom(), entityIn instanceof ServerPlayer ? (ServerPlayer) entityIn : null)) {
                    onBroken.accept(entityIn);
                    Item item = stack.getItem();
                    stack.shrink(1);
                    if (entityIn instanceof Player) {
                        ((Player) entityIn).awardStat(Stats.ITEM_BROKEN.get(item));
                    }

                    stack.setDamageValue(0);
                    return true;
                }

            }
        }
        return false;
    }

    public static <T extends Entity> Entity getClosestEntity(List<T> entities, Vec3 pos) {
        double cachedDistance = -1.0D;
        Entity resultEntity = null;

        for (T entity : entities) {
            double newDistance = entity.distanceToSqr(pos.x, pos.y, pos.z);
            if (cachedDistance == -1.0D || newDistance < cachedDistance) {
                cachedDistance = newDistance;
                resultEntity = entity;
            }

        }
        return resultEntity;
    }

    public static Vec3 pos(BlockPos pos) {
        return new Vec3(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vector3f fPos(BlockPos pos) {
        return new Vector3f(pos.getX(), pos.getY(), pos.getZ());
    }

    public static Vec3 randPos(BlockPos pos, Random rand, double min, double max) {
        double x = Mth.nextDouble(rand, min, max) + pos.getX();
        double y = Mth.nextDouble(rand, min, max) + pos.getY();
        double z = Mth.nextDouble(rand, min, max) + pos.getZ();
        return new Vec3(x, y, z);
    }

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(MODID, path);
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

    public static void setBlockStateWithExistingProperties(Level Level, BlockPos pos, BlockState newState, int flags) {
        BlockState oldState = Level.getBlockState(pos);
        BlockState finalState = getBlockStateWithExistingProperties(oldState, newState);
        Level.sendBlockUpdated(pos, oldState, finalState, flags);
        Level.setBlock(pos, finalState, flags);
    }

    public static <T extends Comparable<T>> BlockState newStateWithOldProperty(BlockState oldState, BlockState newState, Property<T> property) {
        return newState.setValue(property, oldState.getValue(property));
    }

    public static CompoundTag writeBlockPos(CompoundTag compoundNBT, BlockPos pos) {
        compoundNBT.putInt("X", pos.getX());
        compoundNBT.putInt("Y", pos.getY());
        compoundNBT.putInt("Z", pos.getZ());
        return compoundNBT;
    }

    public static CompoundTag writeBlockPos(CompoundTag compoundNBT, BlockPos pos, String extra) {
        compoundNBT.putInt(extra + "X", pos.getX());
        compoundNBT.putInt(extra + "Y", pos.getY());
        compoundNBT.putInt(extra + "Z", pos.getZ());
        return compoundNBT;
    }

    public static BlockPos readBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }

    public static BlockPos readBlockPos(CompoundTag tag, String extra) {
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
                    positions.add(pos.offset(x, y, z));
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

    public static void updateState(Level LevelIn, BlockPos pos) {
        updateState(LevelIn.getBlockState(pos), LevelIn, pos);
    }

    public static void updateState(BlockState state, Level LevelIn, BlockPos pos) {
        LevelIn.sendBlockUpdated(pos, state, state, 2);
    }

    public static void updateAndNotifyState(Level LevelIn, BlockPos pos) {
        updateAndNotifyState(LevelIn.getBlockState(pos), LevelIn, pos);
    }

    public static void updateAndNotifyState(BlockState state, Level LevelIn, BlockPos pos) {
        LevelIn.sendBlockUpdated(pos, state, state, 2);
        state.updateNeighbourShapes(LevelIn, pos, 2);
    }


    public static Vec3 circlePosition(Vec3 pos, float distance, float current, float total) {
        double angle = current / total * (Math.PI * 2);
        double dx2 = (distance * Math.cos(angle));
        double dz2 = (distance * Math.sin(angle));

        Vec3 vector = new Vec3(dx2, 0, dz2);
        double x = vector.x * distance;
        double z = vector.z * distance;
        return pos.add(new Vec3(x, 0, z));
    }

    public static Vec3 rotatedCirclePosition(Vec3 pos, float distance, float current, float total, long gameTime, float time) {
        return rotatedCirclePosition(pos, distance, distance, current, total, gameTime, time);
    }

    public static Vec3 rotatedCirclePosition(Vec3 pos, float distanceX, float distanceZ, float current, float total, long gameTime, float time) {
        double angle = current / total * (Math.PI * 2);
        angle += ((gameTime % time) / time) * (Math.PI * 2);
        double dx2 = (distanceX * Math.cos(angle));
        double dz2 = (distanceZ * Math.sin(angle));

        Vec3 vector2f = new Vec3(dx2, 0, dz2);
        double x = vector2f.x * distanceX;
        double z = vector2f.z * distanceZ;
        return pos.add(x, 0, z);
    }

    public static ArrayList<Vec3> blockOutlinePositions(Level Level, BlockPos pos) {
        ArrayList<Vec3> arrayList = new ArrayList<>();
        double d0 = 0.5625D;
        Random random = Level.random;
        for (Direction direction : Direction.values()) {
            BlockPos blockpos = pos.relative(direction);
            if (!Level.getBlockState(blockpos).isSolidRender(Level, blockpos)) {
                Direction.Axis direction$axis = direction.getAxis();
                double d1 = direction$axis == Direction.Axis.X ? 0.5D + d0 * (double) direction.getStepX() : (double) random.nextFloat();
                double d2 = direction$axis == Direction.Axis.Y ? 0.5D + d0 * (double) direction.getStepY() : (double) random.nextFloat();
                double d3 = direction$axis == Direction.Axis.Z ? 0.5D + d0 * (double) direction.getStepZ() : (double) random.nextFloat();
                arrayList.add(new Vec3((double) pos.getX() + d1, (double) pos.getY() + d2, (double) pos.getZ() + d3));
            }
        }
        return arrayList;
    }

    public static void giveAmplifyingEffect(MobEffect effect, LivingEntity target, int duration, int amplifier, int cap) {
        MobEffectInstance instance = target.getEffect(effect);
        if (instance != null) {
            amplifier += instance.getAmplifier() + 1;
        }
        target.addEffect(new MobEffectInstance(effect, duration, Math.min(amplifier, cap)));
    }

    public static void giveStackingEffect(MobEffect effect, LivingEntity target, int duration, int amplifier) {
        MobEffectInstance instance = target.getEffect(effect);
        if (instance != null) {
            duration += instance.getDuration();
        }
        target.addEffect(new MobEffectInstance(effect, duration, amplifier));
    }

    public static void quietlyGiveItemToPlayer(Player player, @Nonnull ItemStack stack) {
        if (stack.isEmpty()) return;
        IItemHandler inventory = new PlayerMainInvWrapper(player.getInventory());
        Level Level = player.level;
        ItemStack remainder = stack;
        if (!remainder.isEmpty()) {
            remainder = ItemHandlerHelper.insertItemStacked(inventory, remainder, false);
        }
        if (!remainder.isEmpty() && !Level.isClientSide) {
            ItemEntity entityitem = new ItemEntity(Level, player.getX(), player.getY() + 0.5, player.getZ(), remainder);
            entityitem.setPickUpDelay(40);
            entityitem.setDeltaMovement(entityitem.getDeltaMovement().multiply(0, 1, 0));
            Level.addFreshEntity(entityitem);
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

    public static ItemStack getHeldItem(LivingEntity livingEntity, Predicate<ItemStack> stackPredicate) {
        if (livingEntity.swingingArm != null) {
            if (stackPredicate.test(livingEntity.getItemInHand(livingEntity.swingingArm))) {
                return livingEntity.getItemInHand(livingEntity.swingingArm);
            }
        }
        if (stackPredicate.test(livingEntity.getItemInHand(livingEntity.getUsedItemHand()))) {
            return livingEntity.getItemInHand(livingEntity.getUsedItemHand());
        }
        if (stackPredicate.test(livingEntity.getItemInHand(InteractionHand.MAIN_HAND))) {
            return livingEntity.getItemInHand(InteractionHand.MAIN_HAND);
        }
        if (stackPredicate.test(livingEntity.getItemInHand(InteractionHand.OFF_HAND))) {
            return livingEntity.getItemInHand(InteractionHand.OFF_HAND);
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
        EnchantmentHelper.EnchantmentVisitor visitor = (enchantment, level) ->
                enchantment.doPostAttack(user, target, level);
        if (user != null) {
            EnchantmentHelper.runIterationOnInventory(visitor, user.getAllSlots());
        }

        if (user instanceof Player) {
            EnchantmentHelper.runIterationOnItem(visitor, stack);
        }
    }

    public static void giveItemToEntity(ItemStack item, LivingEntity entity) {
        if (entity instanceof Player) {
            ItemHandlerHelper.giveItemToPlayer((Player) entity, item);
        } else {
            ItemEntity itemEntity = new ItemEntity(entity.level, entity.getX(), entity.getY() + 0.5, entity.getZ(), item);
            itemEntity.setPickUpDelay(40);
            itemEntity.setDeltaMovement(itemEntity.getDeltaMovement().multiply(0, 1, 0));
            entity.level.addFreshEntity(itemEntity);
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