package com.sammy.malum.core.mod_systems.tile;

import com.sammy.malum.MalumHelper;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
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

public class SimpleTileEntityInventory extends ItemStackHandler
{
    public int slotCount;
    public int slotSize;
    public Predicate<ItemStack> inputPredicate;
    public Predicate<ItemStack> outputPredicate;
    
    public SimpleTileEntityInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate)
    {
        this(slotCount, slotSize, inputPredicate);
        this.outputPredicate = outputPredicate;
    }
    
    public SimpleTileEntityInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate)
    {
        this(slotCount, slotSize);
        this.inputPredicate = inputPredicate;
    }
    
    public SimpleTileEntityInventory(int slotCount, int slotSize)
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
    public void dumpItems(World world, Vector3d pos)
    {
        for (int i = 0; i < slotCount; i++)
        {
            if (!getStackInSlot(i).isEmpty())
            {
                world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), getStackInSlot(i)));
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
    
    public boolean playerHandleItem(World worldIn, PlayerEntity player, Hand handIn)
    {
        player.swing(handIn, true);
        if (player.isSneaking() || player.getHeldItem(handIn).isEmpty())
        {
            return playerExtractItem(worldIn, player);
        }
        else
        {
            return playerInsertItem(worldIn, player.getHeldItem(handIn));
        }
    }
    public boolean playerExtractItem(World worldIn, PlayerEntity player)
    {
        if (MalumHelper.areWeOnServer(worldIn))
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
    public boolean playerInsertItem(World worldIn, ItemStack stack)
    {
        if (MalumHelper.areWeOnServer(worldIn))
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
    
    public void extractItem(PlayerEntity playerEntity, ItemStack stack, int slot)
    {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, stack, playerEntity.inventory.currentItem);
        setStackInSlot(slot, ItemStack.EMPTY);
    }
    public void readData(CompoundNBT compound)
    {
        readData(compound,"inventory");
    }
    
    public CompoundNBT writeData(CompoundNBT compound)
    {
        writeData(compound, "inventory");
        return compound;
    }
    public void readData(CompoundNBT compound, String name)
    {
        deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get(name)));
    }
    
    public CompoundNBT writeData(CompoundNBT compound, String name)
    {
        compound.put(name, serializeNBT());
        return compound;
    }
    
}