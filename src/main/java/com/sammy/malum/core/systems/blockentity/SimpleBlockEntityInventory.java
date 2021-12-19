package com.sammy.malum.core.systems.blockentity;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleBlockEntityInventory extends ItemStackHandler {
    public final int slotCount;
    public final int slotSize;
    public Predicate<ItemStack> inputPredicate;
    public Predicate<ItemStack> outputPredicate;
    public final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this);

    public ArrayList<Item> items = new ArrayList<>();
    public ArrayList<ItemStack> nonEmptyStacks = new ArrayList<>();
    public int emptyItemAmount;
    public int nonEmptyItemAmount;
    public int firstEmptyItemIndex;

    public SimpleBlockEntityInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate) {
        this(slotCount, slotSize, inputPredicate);
        this.outputPredicate = outputPredicate;
    }

    public SimpleBlockEntityInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate) {
        this(slotCount, slotSize);
        this.inputPredicate = inputPredicate;
    }

    public SimpleBlockEntityInventory(int slotCount, int slotSize) {
        super(slotCount);
        this.slotCount = slotCount;
        this.slotSize = slotSize;
    }

    @Override
    protected void onContentsChanged(int slot) {
        updateData();
    }

    public void updateData() {
        items = getItems();
        nonEmptyStacks = getNonEmptyItemStacks();
        emptyItemAmount = getEmptyItemCount();
        nonEmptyItemAmount = getNonEmptyItemCount();
        firstEmptyItemIndex = getFirstEmptyItemIndex();
    }

    public void load(CompoundTag compound) {
        load(compound, "inventory");
    }

    public void load(CompoundTag compound, String name) {
        deserializeNBT(compound.getCompound(name));
        updateData();
    }

    public void save(CompoundTag compound) {
        save(compound, "inventory");
    }

    public void save(CompoundTag compound, String name) {
        compound.put(name, serializeNBT());
    }

    @Override
    public int getSlots() {
        return slotCount;
    }

    @Override
    public int getSlotLimit(int slot) {
        return slotSize;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        if (inputPredicate != null) {
            if (!inputPredicate.test(stack)) {
                return false;
            }
        }
        return super.isItemValid(slot, stack);
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (outputPredicate != null) {
            if (!outputPredicate.test(super.extractItem(slot, amount, true))) {
                return ItemStack.EMPTY;
            }
        }
        return super.extractItem(slot, amount, simulate);
    }

    public int getFirstEmptyItemIndex() {
        for (int i = 0; i < slotCount; i++) {
            if (getStackInSlot(i).isEmpty()) {
                return i;
            }
        }
        return -1;
    }

    public NonNullList<ItemStack> getStacks() {
        return stacks;
    }

    public int getNonEmptyItemCount() {
        return (int) getStacks().stream().filter(s -> !s.isEmpty()).count();
    }

    public int getEmptyItemCount() {
        return (int) getStacks().stream().filter(ItemStack::isEmpty).count();
    }

    public ArrayList<ItemStack> getNonEmptyItemStacks() {
        return getStacks().stream().filter(s -> !s.isEmpty()).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Item> getItems() {
        return getStacks().stream().map(ItemStack::getItem).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Item> getNonEmptyItemAmount() {
        return getNonEmptyItemStacks().stream().map(ItemStack::getItem).collect(Collectors.toCollection(ArrayList::new));
    }

    public void clear() {
        for (int i = 0; i < slotCount; i++)
        {
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public void dumpItems(Level level, BlockPos pos) {
        dumpItems(level, DataHelper.fromBlockPos(pos).add(0.5, 0.5, 0.5));
    }

    public void dumpItems(Level level, Vec3 pos) {
        for (int i = 0; i < slotCount; i++) {
            if (!getStackInSlot(i).isEmpty()) {
                level.addFreshEntity(new ItemEntity(level, pos.x(), pos.y(), pos.z(), getStackInSlot(i)));
            }
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }

    public boolean interact(Level level, Player player, InteractionHand handIn) {
        ItemStack held = player.getItemInHand(handIn);
        player.swing(handIn, true);
        if (held.isEmpty() || firstEmptyItemIndex == -1) {
            ItemStack takeOutStack = nonEmptyStacks.get(nonEmptyStacks.size() - 1);
            if (takeOutStack.getItem().equals(held.getItem()))
            {
                return !insertItem(level, held).isEmpty();
            }
            ItemStack extractedStack = extractItem(level, held, player);
            boolean success = !extractedStack.isEmpty();
            if (success) {
                success = !insertItem(level, held).isEmpty();
            }
            return success;
        } else {
            return !insertItem(level, held).isEmpty();
        }
    }

    public ItemStack extractItem(Level level, ItemStack heldStack, Player player) {
        if (!level.isClientSide) {
            List<ItemStack> nonEmptyStacks = this.nonEmptyStacks;
            if (nonEmptyStacks.isEmpty()) {
                return ItemStack.EMPTY;
            }
            ItemStack takeOutStack = nonEmptyStacks.get(nonEmptyStacks.size() - 1);
            int slot = stacks.indexOf(takeOutStack);
            if (extractItem(slot, takeOutStack.getCount(), true).equals(ItemStack.EMPTY)) {
                return ItemStack.EMPTY;
            }
            extractItem(player, takeOutStack, slot);
            return takeOutStack;
        }
        return ItemStack.EMPTY;
    }

    public void extractItem(Player playerEntity, ItemStack stack, int slot) {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, stack, playerEntity.getInventory().selected);
        setStackInSlot(slot, ItemStack.EMPTY);
    }

    public ItemStack insertItem(Level level, ItemStack stack) {
        if (!level.isClientSide) {
            if (!stack.isEmpty()) {
                ItemStack simulate = insertItem(stack, true);
                if (simulate.equals(stack)) {
                    return ItemStack.EMPTY;
                }
                int count = stack.getCount() - simulate.getCount();
                if (count > slotSize) {
                    count = slotSize;
                }
                return insertItem(stack.split(count), false);
            }
        }
        return ItemStack.EMPTY;
    }

    public ItemStack insertItem(ItemStack stack, boolean simulate) {
        return ItemHandlerHelper.insertItem(this, stack, simulate);
    }
}