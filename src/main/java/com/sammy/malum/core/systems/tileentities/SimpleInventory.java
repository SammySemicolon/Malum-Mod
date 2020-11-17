package com.sammy.malum.core.systems.tileentities;

import com.ibm.icu.impl.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SimpleInventory extends ItemStackHandler
{
    public int slotCount;
    public int slotSize;
    public Predicate<ItemStack> inputPredicate;
    public Predicate<ItemStack> outputPredicate;
    
    public SimpleInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate, Predicate<ItemStack> outputPredicate)
    {
        this(slotCount, slotSize, inputPredicate);
        this.outputPredicate = outputPredicate;
    }
    
    public SimpleInventory(int slotCount, int slotSize, Predicate<ItemStack> inputPredicate)
    {
        this(slotCount, slotSize);
        this.inputPredicate = inputPredicate;
    }
    
    public SimpleInventory(int slotCount, int slotSize)
    {
        super(slotCount);
        this.slotCount = slotCount;
        this.slotSize = slotSize;
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
            else
            {
                break;
            }
        }
        return itemCount;
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
    public void handleItem(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit)
    {
        if (player.getHeldItem(handIn).isEmpty() || player.isSneaking())
        {
            List<ItemStack> nonEmptyStacks = stacks.stream().filter(i -> !i.isEmpty()).collect(Collectors.toList());
            if (nonEmptyStacks.isEmpty())
            {
                return;
            }
            ItemStack takeOutStack = nonEmptyStacks.get(nonEmptyStacks.size()-1);
            int slot = stacks.indexOf(takeOutStack);
            if (extractItem(slot, takeOutStack.getCount(), true).equals(ItemStack.EMPTY))
            {
                return;
            }
            outputItem(player, takeOutStack, stacks.indexOf(takeOutStack));
            updateState(state, worldIn, pos);
            return;
        }
        if (!player.getHeldItem(handIn).isEmpty())
        {
            ItemStack desiredStack = player.getHeldItem(handIn);
            ItemStack simulate = ItemHandlerHelper.insertItem(this, desiredStack, true);
            int count = desiredStack.getCount() - simulate.getCount();
            ItemHandlerHelper.insertItem(this, desiredStack.split(count), false);
            updateState(state, worldIn, pos);
        }
    }
    public void updateState(BlockState state, World worldIn, BlockPos pos)
    {
        worldIn.notifyBlockUpdate(pos, state,state, 3);
    }
    public void outputItem(PlayerEntity playerEntity, ItemStack stack, int slot)
    {
        ItemHandlerHelper.giveItemToPlayer(playerEntity, stack, playerEntity.inventory.currentItem);
        setStackInSlot(slot, ItemStack.EMPTY);
    }
    public void readData(CompoundNBT compound)
    {
        deserializeNBT((CompoundNBT) Objects.requireNonNull(compound.get("inventory")));
    }
    
    public CompoundNBT writeData(CompoundNBT compound)
    {
        compound.put("inventory", serializeNBT());
        return compound;
    }
}