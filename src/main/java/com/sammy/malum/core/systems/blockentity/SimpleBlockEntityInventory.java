package com.sammy.malum.core.systems.blockentity;

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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleBlockEntityInventory extends ItemStackHandler
{
    public int slotCount;
    public int slotSize;
    public Predicate<ItemStack> inputPredicate;
    public Predicate<ItemStack> outputPredicate;
    
    public SimpleBlockEntityInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate)
    {
        this(slotCount, slotSize, inputPredicate);
        this.outputPredicate = outputPredicate;
    }
    
    public SimpleBlockEntityInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate)
    {
        this(slotCount, slotSize);
        this.inputPredicate = inputPredicate;
    }
    
    public SimpleBlockEntityInventory(int slotCount, int slotSize)
    {
        super(slotCount);
        this.slotCount = slotCount;
        this.slotSize = slotSize;
    }
    public int firstEmptyItem()
    {
        for (int i = 0; i < slotCount; i++)
        {
            if (getStackInSlot(i).isEmpty())
            {
                return i;
            }
        }
        return -1;
    }
    public int nonEmptyItems()
    {
        int itemCount = 0;
        for (int i = 0; i < slotCount; i++)
        {
            ItemStack item = getStackInSlot(i);
            if (!item.isEmpty())
            {
                itemCount++;
            }
        }
        return itemCount;
    }
    public void clearItems()
    {
        for (int i = 0; i < slotCount; i++)
        {
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    public ArrayList<Item> items()
    {
        ArrayList<Item> items = new ArrayList<>();
        for (int i = 0; i < slotCount; i++)
        {
            items.add(getStackInSlot(i).getItem());
        }
        return items;
    }
    public ArrayList<ItemStack> stacks()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < slotCount; i++)
        {
            stacks.add(getStackInSlot(i));
        }
        return stacks;
    }
    public ArrayList<ItemStack> nonEmptyStacks()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < slotCount; i++)
        {
            if (!getStackInSlot(i).isEmpty())
            {
                stacks.add(getStackInSlot(i));
            }
        }
        return stacks;
    }
    public void dumpItems(Level level, Vec3 pos)
    {
        for (int i = 0; i < slotCount; i++)
        {
            if (!getStackInSlot(i).isEmpty())
            {
                level.addFreshEntity(new ItemEntity(level, pos.x(), pos.y(), pos.z(), getStackInSlot(i)));
            }
            setStackInSlot(i, ItemStack.EMPTY);
        }
    }
    public final LazyOptional<IItemHandler> inventoryOptional = LazyOptional.of(() -> this);
    @Override
    public int getSlots()
    {
        return slotCount;
    }
    
    @Override
    public int getSlotLimit(int slot)
    {
        return slotSize;
    }
    
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack)
    {
        if (inputPredicate != null)
        {
            if (!inputPredicate.test(stack))
            {
                return false;
            }
        }
        return super.isItemValid(slot, stack);
    }
    
    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (outputPredicate != null)
        {
            if (!outputPredicate.test(super.extractItem(slot, amount, true)))
            {
                return ItemStack.EMPTY;
            }
        }
        return super.extractItem(slot, amount, simulate);
    }
    
    public boolean playerHandleItem(Level level, Player player, InteractionHand handIn)
    {
        player.swing(handIn, true);
        if (player.isShiftKeyDown() || player.getItemInHand(handIn).isEmpty())
        {
            return playerExtractItem(level, player);
        }
        else
        {
            return playerInsertItem(level, player.getItemInHand(handIn));
        }
    }
    public boolean playerExtractItem(Level level, Player player)
    {
        if (!level.isClientSide)
        {
            List<ItemStack> nonEmptyStacks = stacks.stream().filter(i -> !i.isEmpty()).collect(Collectors.toList());
            if (nonEmptyStacks.isEmpty())
            {
                return false;
            }
            ItemStack takeOutStack = nonEmptyStacks.get(nonEmptyStacks.size() - 1);
            int slot = stacks.indexOf(takeOutStack);
            if (extractItem(slot, takeOutStack.getCount(), true).equals(ItemStack.EMPTY))
            {
                return false;
            }
            extractItem(player, takeOutStack, stacks.indexOf(takeOutStack));
            return true;
        }
        return false;
    }
    public boolean playerInsertItem(Level level, ItemStack stack)
    {
        if (!level.isClientSide)
        {
            if (!stack.isEmpty())
            {
                ItemStack simulate = ItemHandlerHelper.insertItem(this, stack, true);
                int count = stack.getCount() - simulate.getCount();
                if (count > slotSize)
                {
                    count = slotSize;
                }
                ItemHandlerHelper.insertItem(this, stack.split(count), false);
                return true;
            }
        }
        return false;
    }
    public void insertItem(ItemStack stack, int slot)
    {
        ItemStack simulate = this.insertItem(slot, stack, true);
        int count = stack.getCount() - simulate.getCount();
        if (count > slotSize)
        {
            count = slotSize;
        }
        insertItem(slot, stack.split(count), false);
    }
    
    public void extractItem(Player playerEntity, ItemStack stack, int slot)
    {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, stack, playerEntity.getInventory().selected);
        setStackInSlot(slot, ItemStack.EMPTY);
    }
    public void load(CompoundTag compound)
    {
        load(compound,"inventory");
    }

    public void load(CompoundTag compound, String name)
    {
        deserializeNBT((CompoundTag) Objects.requireNonNull(compound.get(name)));
    }

    public CompoundTag save(CompoundTag compound)
    {
        save(compound, "inventory");
        return compound;
    }
    public CompoundTag save(CompoundTag compound, String name)
    {
        compound.put(name, serializeNBT());
        return compound;
    }
    
}