package com.sammy.malum.core.registry.item;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class ItemTagRegistry
{
    public static Tag.Named<Item> SOUL_HUNTER_WEAPON = tag("soul_hunter_weapon");
    public static Tag.Named<Item> RUNEWOOD_LOGS = tag("runewood_logs");
    public static Tag.Named<Item> SOULWOOD_LOGS = tag("soulwood_logs");
    public static Tag.Named<Item> SAPBALLS = tag("sapballs");

    public static final Tags.IOptionalNamedTag<Item> NUGGETS_LEAD = forgeTag("nuggets/lead");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_LEAD = forgeTag("ingots/lead");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_SILVER = forgeTag("nuggets/silver");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_SILVER = forgeTag("ingots/silver");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_COPPER = forgeTag("nuggets/copper");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_COPPER = forgeTag("ingots/copper");

    private static Tags.IOptionalNamedTag<Item> forgeTag(String name, @Nullable Set<Supplier<Item>> defaults)
    {
        return ItemTags.createOptional(new ResourceLocation("forge", name), defaults);
    }

    private static Tags.IOptionalNamedTag<Item> forgeTag(String name)
    {
        return forgeTag(name, null);
    }
    private static Tag.Named<Item> tag(String name, @Nullable Set<Supplier<Item>> defaults)
    {
        return ItemTags.createOptional(DataHelper.prefix(name), defaults);
    }

    private static Tag.Named<Item> tag(String name)
    {
        return tag(name, null);
    }
}