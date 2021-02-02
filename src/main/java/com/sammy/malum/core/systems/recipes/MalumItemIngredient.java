package com.sammy.malum.core.systems.recipes;

import com.sammy.malum.MalumMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;

import java.util.ArrayList;
import java.util.List;

public class MalumItemIngredient
{
    public ITag<Item> tag;
    public Item item;
    public int count;
    public boolean isSimple()
    {
        return tag == null;
    }
    public ItemStack random()
    {
        if (isSimple())
        {
            return new ItemStack(item, count);
        }
        else
        {
            ArrayList<ItemStack> stacks = (ArrayList<ItemStack>) stacks();
            return stacks().get(MalumMod.RANDOM.nextInt(stacks.size()));
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
    public MalumItemIngredient(ITag<Item> tag, int count)
    {
        this.tag = tag;
        this.count = count;
    }
    public MalumItemIngredient(Item item, int count) {
        this.item = item;
        this.count = count;
    }
}
