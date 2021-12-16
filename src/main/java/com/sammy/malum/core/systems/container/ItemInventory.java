package com.sammy.malum.core.systems.container;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ItemInventory extends SimpleContainer
{
    private final ItemStack stack;

    public ItemInventory(ItemStack stack, int expectedSize)
    {
        super(expectedSize);
        this.stack = stack;

        ListTag list = stack.getOrCreateTag().getList("items", 10);
        int i = 0;
        for (; i < expectedSize && i < list.size(); i++)
        {
            setItem(i, ItemStack.of(list.getCompound(i)));
        }
    }

    @Override
    public boolean stillValid(Player player)
    {
        return !stack.isEmpty();
    }

    @Override
    public void setChanged()
    {
        super.setChanged();
        ListTag list = new ListTag();
        for (int i = 0; i < getContainerSize(); i++)
        {
            list.add(getItem(i).save(new CompoundTag()));
        }
        stack.getOrCreateTag().put("items", list);
    }
}
