package com.sammy.malum.registry.common.item;

import com.sammy.malum.MalumMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemTagRegistry {
    public static final TagKey<Item> SOUL_HUNTER_WEAPON = malumTag("soul_hunter_weapon");
    public static final TagKey<Item> SCYTHE = malumTag("scythe");
    public static final TagKey<Item> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static final TagKey<Item> SOULWOOD_LOGS = malumTag("soulwood_logs");
    public static final TagKey<Item> SAPBALLS = malumTag("sapballs");
    public static final TagKey<Item> GROSS_FOODS = malumTag("gross_foods");
    public static final TagKey<Item> PROSPECTORS_TREASURE = malumTag("prospectors_treasure");
    public static final TagKey<Item> METAL_NODES = malumTag("metal_nodes");

    public static final TagKey<Item> KNIVES_FD = modTag("farmersdelight:tools/knives");
    public static final TagKey<Item> KNIVES = forgeTag("tools/knives");

    public static final TagKey<Item> STRIPPED_LOGS = forgeTag("stripped_logs");

    private static TagKey<Item> modTag(String path) {
        return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation(path));
    }

    private static TagKey<Item> malumTag(String path) {
        return TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), MalumMod.malumPath(path));
    }

    private static TagKey<Item> forgeTag(String name) {
        return ItemTags.create(new ResourceLocation("forge", name));
    }
}