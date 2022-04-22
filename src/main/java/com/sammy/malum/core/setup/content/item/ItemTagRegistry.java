package com.sammy.malum.core.setup.content.item;

import com.sammy.malum.core.helper.DataHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Supplier;

public class ItemTagRegistry {
    public static TagKey<Item> SOUL_HUNTER_WEAPON = malumTag("soul_hunter_weapon");
    public static TagKey<Item> SCYTHE = malumTag("scythe");
    public static TagKey<Item> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static TagKey<Item> SOULWOOD_LOGS = malumTag("soulwood_logs");
    public static TagKey<Item> SAPBALLS = malumTag("sapballs");

    public static final TagKey<Item> KNIVES_FD = modTag("farmersdelight:tools/knives");
    public static final TagKey<Item> KNIVES = forgeTag("tools/knives");

    public static final TagKey<Item> NUGGETS_COPPER = forgeTag("nuggets/copper");
    public static final TagKey<Item> INGOTS_COPPER = forgeTag("ingots/copper");
    public static final TagKey<Item> NUGGETS_LEAD = forgeTag("nuggets/lead");
    public static final TagKey<Item> INGOTS_LEAD = forgeTag("ingots/lead");
    public static final TagKey<Item> NUGGETS_SILVER = forgeTag("nuggets/silver");
    public static final TagKey<Item> INGOTS_SILVER = forgeTag("ingots/silver");
    public static final TagKey<Item> NUGGETS_ALUMINUM = forgeTag("nuggets/aluminum");
    public static final TagKey<Item> INGOTS_ALUMINUM = forgeTag("ingots/aluminum");
    public static final TagKey<Item> NUGGETS_NICKEL = forgeTag("nuggets/nickel");
    public static final TagKey<Item> INGOTS_NICKEL = forgeTag("ingots/nickel");
    public static final TagKey<Item> NUGGETS_URANIUM = forgeTag("nuggets/uranium");
    public static final TagKey<Item> INGOTS_URANIUM = forgeTag("ingots/uranium");
    public static final TagKey<Item> NUGGETS_OSMIUM = forgeTag("nuggets/osmium");
    public static final TagKey<Item> INGOTS_OSMIUM = forgeTag("ingots/osmium");
    public static final TagKey<Item> NUGGETS_ZINC = forgeTag("nuggets/zinc");
    public static final TagKey<Item> INGOTS_ZINC = forgeTag("ingots/zinc");
    public static final TagKey<Item> NUGGETS_TIN = forgeTag("nuggets/tin");
    public static final TagKey<Item> INGOTS_TIN = forgeTag("ingots/tin");

    private static TagKey<Item> modTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(path));
    }

    private static TagKey<Item> malumTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, DataHelper.prefix(path));
    }

    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
}