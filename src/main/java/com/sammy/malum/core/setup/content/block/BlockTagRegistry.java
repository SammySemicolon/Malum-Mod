package com.sammy.malum.core.setup.content.block;

import com.sammy.malum.MalumMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class BlockTagRegistry {

    public static final TagKey<Block> BLIGHTED_BLOCKS = malumTag("blighted_blocks");

    public static final TagKey<Block> RUNEWOOD_LOGS = malumTag("runewood_logs");
    public static final TagKey<Block> SOULWOOD_LOGS = malumTag("soulwood_logs");

    public static final TagKey<Block> TAINTED_ROCK = malumTag("tainted_rock");
    public static final TagKey<Block> TWISTED_ROCK = malumTag("twisted_rock");

    public static final TagKey<Block> RITE_IMMUNE = malumTag("rite_immune");

    public static final TagKey<Block> ENDLESS_FLAME = malumTag("endless_flame");

    public static final TagKey<Block> GREATER_AERIAL_WHITELIST = malumTag("greater_aerial_whitelist");

    public static final TagKey<Block> TRAY_HEAT_SOURCES = modTag("farmersdelight:tray_heat_sources");
    public static final TagKey<Block> HEAT_SOURCES = modTag("farmersdelight:heat_sources");

    public static final TagKey<Block> STRIPPED_LOGS = forgeTag("stripped_logs");

    private static TagKey<Block> modTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(path));
    }

    private static TagKey<Block> malumTag(String path) {
        return TagKey.create(Registry.BLOCK_REGISTRY, MalumMod.malumPath(path));
    }

    private static TagKey<Block> forgeTag(String name) {
        return BlockTags.create(new ResourceLocation("forge", name));
    }
}
