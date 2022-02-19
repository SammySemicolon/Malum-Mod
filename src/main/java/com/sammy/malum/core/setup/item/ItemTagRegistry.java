package com.sammy.malum.core.setup.item;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class ItemTagRegistry {
    public static Tag.Named<Item> SOUL_HUNTER_WEAPON = tag("soul_hunter_weapon");
    public static Tag.Named<Item> SCYTHE = tag("scythe");
    public static Tag.Named<Item> RUNEWOOD_LOGS = tag("runewood_logs");
    public static Tag.Named<Item> SOULWOOD_LOGS = tag("soulwood_logs");
    public static Tag.Named<Item> SAPBALLS = tag("sapballs");

    public static final Tag.Named<Item> KNIVES_FD = modTag("farmersdelight:tools/knives");
    public static final Tags.IOptionalNamedTag<Item> KNIVES = forgeTag("tools/knives");

    public static final Tags.IOptionalNamedTag<Item> NUGGETS_COPPER = forgeTag("nuggets/copper");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_COPPER = forgeTag("ingots/copper");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_LEAD = forgeTag("nuggets/lead");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_LEAD = forgeTag("ingots/lead");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_SILVER = forgeTag("nuggets/silver");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_SILVER = forgeTag("ingots/silver");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_ALUMINUM = forgeTag("nuggets/aluminum");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_ALUMINUM = forgeTag("ingots/aluminum");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_NICKEL = forgeTag("nuggets/nickel");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_NICKEL = forgeTag("ingots/nickel");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_URANIUM = forgeTag("nuggets/uranium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_URANIUM = forgeTag("ingots/uranium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_OSMIUM = forgeTag("nuggets/osmium");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_OSMIUM = forgeTag("ingots/osmium");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_ZINC = forgeTag("nuggets/zinc");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_ZINC = forgeTag("ingots/zinc");
    public static final Tags.IOptionalNamedTag<Item> NUGGETS_TIN = forgeTag("nuggets/tin");
    public static final Tags.IOptionalNamedTag<Item> INGOTS_TIN = forgeTag("ingots/tin");

    private static Tags.IOptionalNamedTag<Item> forgeTag(String name, @Nullable Set<Supplier<Item>> defaults) {
        return ItemTags.createOptional(new ResourceLocation("forge", name), defaults);
    }

    private static Tags.IOptionalNamedTag<Item> forgeTag(String name) {
        return forgeTag(name, null);
    }

    private static Tag.Named<Item> tag(String name, @Nullable Set<Supplier<Item>> defaults) {
        return ItemTags.createOptional(DataHelper.prefix(name), defaults);
    }

    private static Tag.Named<Item> tag(String name) {
        return tag(name, null);
    }

    private static Tag.Named<Item> modTag(String name, @Nullable Set<Supplier<Item>> defaults) {
        return ItemTags.createOptional(new ResourceLocation(name), defaults);
    }

    private static Tag.Named<Item> modTag(String name) {
        return modTag(name, null);
    }
}