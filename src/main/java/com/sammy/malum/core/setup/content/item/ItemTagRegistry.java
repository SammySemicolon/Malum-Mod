package com.sammy.malum.core.setup.content.item;

import com.sammy.malum.MalumMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ItemTagRegistry {
    public static TagKey<Item> SOUL_HUNTER_WEAPON = malumTag("soul_hunter_weapon");
    public static TagKey<Item> SCYTHE = malumTag("scythe");
    public static TagKey<Item> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static TagKey<Item> SOULWOOD_LOGS = malumTag("soulwood_logs");
    public static TagKey<Item> SAPBALLS = malumTag("sapballs");
    public static TagKey<Item> GROSS_FOODS = malumTag("gross_foods");

    public static final TagKey<Item> KNIVES_FD = modTag("farmersdelight:tools/knives");
    public static final TagKey<Item> KNIVES = forgeTag("tools/knives");

    private static TagKey<Item> modTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(path));
    }

    private static TagKey<Item> malumTag(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, MalumMod.prefix(path));
    }

    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
}