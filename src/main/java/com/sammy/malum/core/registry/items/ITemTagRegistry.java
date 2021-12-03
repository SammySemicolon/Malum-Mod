package com.sammy.malum.core.registry.items;

import com.sammy.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class ITemTagRegistry
{
    public static ITag.INamedTag<Item> SOUL_HUNTER_WEAPON = create("soul_hunter_weapon");
    public static ITag.INamedTag<Item> RUNEWOOD_LOGS = create("runewood_logs");
    public static ITag.INamedTag<Item> SOULWOOD_LOGS = create("soulwood_logs");
    public static ITag.INamedTag<Item> SAPBALLS = create("sapballs");

    public static ITag.INamedTag<Item> create(String id)
    {
        return ItemTags.createOptional(MalumHelper.prefix(id));
    }
}