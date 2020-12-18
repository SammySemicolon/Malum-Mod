package com.sammy.malum.core.init;

import com.sammy.malum.MalumHelper;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class MalumItemTags
{
    public static ITag.INamedTag<Item> SUN_KISSED_LOGS;
    public static ITag.INamedTag<Item> TAINTED_LOGS;
    
    public static ITag.INamedTag<Item> makeWrapperTag(String id)
    {
        return ItemTags.createOptional(MalumHelper.prefix(id));
    }
    public static void init()
    {
        SUN_KISSED_LOGS = makeWrapperTag("sun_logs");
        TAINTED_LOGS = makeWrapperTag("tainted_logs");
    }
}