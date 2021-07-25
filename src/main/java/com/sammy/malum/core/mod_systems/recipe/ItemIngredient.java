package com.sammy.malum.core.mod_systems.recipe;

import com.sammy.malum.MalumMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class ItemIngredient
{
    public ITag<Item> tag;
    public Item item;
    public int count;
    public boolean isSimple()
    {
        return tag == null;
    }
    public ItemStack getItem()
    {
        if (isSimple())
        {
            return new ItemStack(item, count);
        }
        else
        {
            ArrayList<ItemStack> stacks = (ArrayList<ItemStack>) stacks();
            return stacks.get(MalumMod.RANDOM.nextInt(stacks.size()));
        }
    }
    public ItemStack getItemAlt()
    {
        if (isSimple())
        {
            return new ItemStack(item, count);
        }
        else
        {
            return stacks().get(0);
        }
    }
    public List<ItemStack> stacks()
    {
        ArrayList<ItemStack> stacks = new ArrayList<>();
        if (isSimple())
        {
            stacks.add(new ItemStack(item, count));
        }
        else
        {
            for(Item tagItem : tag.getAllElements())
            {
                stacks.add(new ItemStack(tagItem, count));
            }
        }
        return stacks;
    }
    public boolean matches(ItemStack stack)
    {
        if (isSimple())
        {
            return stack.getItem().equals(item) && stack.getCount() >= count;
        }
        else
        {
            return tag.contains(stack.getItem()) && stack.getCount() >= count;
        }
    }
    public ItemIngredient(ITag<Item> tag, int count)
    {
        this.tag = tag;
        this.count = count;
    }
    public ItemIngredient(Item item, int count) {
        this.item = item;
        this.count = count;
    }
}
