package com.sammy.malum.items.utility;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;

public interface IConfigurableItem
{
    ArrayList<Option> getOptions();
    
    default void addOption(int option, String tooltip)
    {
        getOptions().add(new Option(option, tooltip));
    }
    
    default Option getOption(ItemStack stack)
    {
        return getOption(stack.getOrCreateTag().getInt("malum:itemOption"));
    }
    
    default Option getOption(int i)
    {
        int length = getOptions().size();
        int index = (length + i % length) % length;
        return getOptions().get(index);
    }
}

