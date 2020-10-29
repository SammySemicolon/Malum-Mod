package com.sammy.malum.items.utility;

import net.minecraft.item.Item;

import java.util.ArrayList;

public class ConfigurableItem extends Item implements IConfigurableItem
{
    public ArrayList<Option> options;
    
    public ConfigurableItem(Item.Properties properties)
    {
        super(properties);
        options = new ArrayList<>();
    }
    
    @Override
    public ArrayList<Option> getOptions()
    {
        return options;
    }
}