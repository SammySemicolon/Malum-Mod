package com.sammy.malum.core.systems.recipe;

import net.minecraft.item.Item;
import net.minecraft.tags.ITag;

public class SimpleItemIngredient extends ItemIngredient
{
    public SimpleItemIngredient(ITag<Item> tag)
    {
        super(tag, 1);
    }

    public SimpleItemIngredient(Item item)
    {
        super(item, 1);
    }
}
