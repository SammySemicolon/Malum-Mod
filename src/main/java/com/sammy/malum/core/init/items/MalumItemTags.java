package com.sammy.malum.core.init.items;

import com.sammy.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class MalumItemTags
{
    public static ITag.INamedTag<Item> RUNEWOOD_LOGS = makeWrapperTag("runewood_logs");
    public static ITag.INamedTag<Item> SOULWOOD_LOGS = makeWrapperTag("soulwood_logs");
    public static ITag.INamedTag<Item> SAPBALLS = makeWrapperTag("sapballs");

    public static ITag.INamedTag<Item> makeWrapperTag(String id)
    {
        return ItemTags.createOptional(MalumHelper.prefix(id));
    }
}