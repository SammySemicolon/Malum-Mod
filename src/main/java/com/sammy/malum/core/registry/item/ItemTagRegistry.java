package com.sammy.malum.core.registry.item;

import com.sammy.malum.MalumHelper;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;

public class ItemTagRegistry
{
    public static Tag.Named<Item> SOUL_HUNTER_WEAPON = create("soul_hunter_weapon");
    public static Tag.Named<Item> RUNEWOOD_LOGS = create("runewood_logs");
    public static Tag.Named<Item> SOULWOOD_LOGS = create("soulwood_logs");
    public static Tag.Named<Item> SAPBALLS = create("sapballs");

    public static Tag.Named<Item> create(String id)
    {
        return ItemTags.createOptional(MalumHelper.prefix(id));
    }
}