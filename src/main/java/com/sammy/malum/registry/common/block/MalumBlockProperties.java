package com.sammy.malum.registry.common.block;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.Tags;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class MalumBlockProperties {

    public static LodestoneBlockProperties TAINTED_ROCK() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE)
                .addTag(BlockTagRegistry.TAINTED_ROCK)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .sound(SoundRegistry.TAINTED_ROCK)
                .strength(1.25F, 9.0F);
    }

    public static LodestoneBlockProperties TWISTED_ROCK() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE)
                .addTag(BlockTagRegistry.TWISTED_ROCK)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .sound(SoundRegistry.TWISTED_ROCK)
                .strength(1.25F, 9.0F);
    }

    public static LodestoneBlockProperties RUNEWOOD() {
        return new LodestoneBlockProperties(Material.WOOD, MaterialColor.COLOR_YELLOW)
                .needsAxe()
                .sound(SoundType.WOOD)
                .strength(1.75F, 4.0F);
    }

    public static LodestoneBlockProperties RUNEWOOD_SAPLING() {
        return new LodestoneBlockProperties(Material.PLANT, MaterialColor.COLOR_YELLOW)
                .addTag(BlockTags.SAPLINGS)
                .noCollission()
                .noOcclusion()
                .sound(SoundType.GRASS)
                .instabreak();
    }

    public static LodestoneBlockProperties RUNEWOOD_LEAVES() {
        return new LodestoneBlockProperties(Material.LEAVES, MaterialColor.COLOR_YELLOW)
                .addTag(BlockTags.LEAVES)
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .sound(SoundRegistry.RUNEWOOD_LEAVES)
                .needsHoe().addTag(BlockTags.LEAVES);
    }

    public static LodestoneBlockProperties SOULWOOD() {
        return new LodestoneBlockProperties(Material.WOOD, MaterialColor.COLOR_PURPLE)
                .sound(SoundRegistry.SOULWOOD)
                .strength(1.75F, 4.0F)
                .needsAxe();
    }

    public static LodestoneBlockProperties BLIGHTED_PLANTS() {
        return new LodestoneBlockProperties(Material.REPLACEABLE_PLANT, MaterialColor.COLOR_PURPLE)
                .addTag(BlockTagRegistry.BLIGHTED_PLANTS)
                .noCollission()
                .noOcclusion()
                .sound(SoundRegistry.BLIGHTED_FOLIAGE)
                .instabreak();
    }

    public static LodestoneBlockProperties SOULWOOD_LEAVES() {
        return new LodestoneBlockProperties(Material.LEAVES, MaterialColor.COLOR_PURPLE)
                .addTag(BlockTags.LEAVES)
                .needsHoe()
                .strength(0.2F)
                .randomTicks()
                .noOcclusion()
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .sound(SoundRegistry.SOULWOOD_LEAVES);
    }

    public static LodestoneBlockProperties BLIGHT() {
        return new LodestoneBlockProperties(Material.MOSS, MaterialColor.COLOR_PURPLE)
                .addTag(BlockTagRegistry.BLIGHTED_BLOCKS)
                .needsShovel()
                .needsHoe()
                .sound(SoundRegistry.BLIGHTED_EARTH)
                .strength(0.7f);
    }

    public static LodestoneBlockProperties BRILLIANCE_ORE(boolean isDeepslate) {
        return new LodestoneBlockProperties(Material.STONE, isDeepslate ? MaterialColor.DEEPSLATE : MaterialColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(isDeepslate ? 5f : 3f, 3f)
                .sound(isDeepslate ? SoundType.DEEPSLATE : SoundType.STONE);
    }

    public static LodestoneBlockProperties NATURAL_QUARTZ_ORE(boolean isDeepslate) {
        return new LodestoneBlockProperties(Material.STONE, isDeepslate ? MaterialColor.DEEPSLATE : MaterialColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(isDeepslate ? 6f : 4f, 3f)
                .sound(isDeepslate ? SoundRegistry.DEEPSLATE_QUARTZ : SoundRegistry.NATURAL_QUARTZ);
    }

    public static LodestoneBlockProperties SOULSTONE_ORE(boolean isDeepslate) {
        return new LodestoneBlockProperties(Material.STONE, isDeepslate ? MaterialColor.DEEPSLATE : MaterialColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(isDeepslate ? 7.0f : 5.0F, 3.0F)
                .sound(isDeepslate ? SoundRegistry.DEEPSLATE_SOULSTONE : SoundRegistry.SOULSTONE);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_ORE() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.NETHER)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(3.0F, 3.0F)
                .sound(SoundRegistry.BLAZING_QUARTZ_ORE);
    }

    public static LodestoneBlockProperties CTHONIC_GOLD_ORE() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(25f, 9999f)
                .sound(SoundRegistry.CTHONIC_GOLD);
    }

    public static LodestoneBlockProperties NATURAL_QUARTZ_CLUSTER() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(1.5F)
                .sound(SoundRegistry.QUARTZ_CLUSTER);
    }

    public static LodestoneBlockProperties ETHER() {
        return new LodestoneBlockProperties(Material.WOOL, MaterialColor.COLOR_YELLOW)
                .addTag(BlockTagRegistry.TRAY_HEAT_SOURCES)
                .sound(SoundRegistry.ETHER)
                .noCollission()
                .instabreak()
                .lightLevel((b) -> 14);
    }

    public static LodestoneBlockProperties SOULSTONE_BLOCK() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.NETHER)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 3.0F)
                .sound(SoundRegistry.SOULSTONE);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_BLOCK() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.COLOR_RED)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .addTags(BlockTagRegistry.HEAT_SOURCES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F)
                .sound(SoundRegistry.BLAZING_QUARTZ_BLOCK);
    }

    public static LodestoneBlockProperties BRILLIANCE_BLOCK() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.COLOR_LIGHT_GREEN)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 3.0F)
                .sound(SoundRegistry.BRILLIANCE_BLOCK);
    }

    public static LodestoneBlockProperties ARCANE_CHARCOAL_BLOCK() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.COLOR_BLACK)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F)
                .sound(SoundRegistry.ARCANE_CHARCOAL_BLOCK);
    }

    public static LodestoneBlockProperties HALLOWED_GOLD() {
        return new LodestoneBlockProperties(Material.METAL, MaterialColor.COLOR_YELLOW)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .requiresCorrectToolForDrops()
                .needsPickaxe()
                .sound(SoundRegistry.HALLOWED_GOLD)
                .noOcclusion()
                .strength(2F, 16.0F);
    }

    public static LodestoneBlockProperties SOUL_STAINED_STEEL_BLOCK() {
        return new LodestoneBlockProperties(Material.METAL, MaterialColor.COLOR_PURPLE)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .addTag(BlockTags.BEACON_BASE_BLOCKS)
                .requiresCorrectToolForDrops()
                .needsPickaxe()
                .sound(SoundRegistry.SOUL_STAINED_STEEL)
                .strength(5f, 64.0f);
    }

    public static LodestoneBlockProperties SPIRIT_JAR() {
        return new LodestoneBlockProperties(Material.GLASS, MaterialColor.COLOR_BLUE)
                .strength(0.5f, 64f)
                .sound(SoundRegistry.HALLOWED_GOLD)
                .noOcclusion();
    }

    public static LodestoneBlockProperties SOUL_VIAL() {
        return new LodestoneBlockProperties(Material.GLASS, MaterialColor.COLOR_BLUE)
                .strength(0.75f, 64f)
                .sound(SoundRegistry.SOUL_STAINED_STEEL)
                .noOcclusion();
    }

    public static LodestoneBlockProperties WEEPING_WELL() {
        return new LodestoneBlockProperties(Material.STONE, MaterialColor.STONE)
                .needsPickaxe()
                .sound(SoundRegistry.TAINTED_ROCK)
                .requiresCorrectToolForDrops()
                .strength(-1.0F, 3600000.0F);
    }

    public static LodestoneBlockProperties PRIMORDIAL_SOUP() {
        return new LodestoneBlockProperties(Material.PORTAL, MaterialColor.COLOR_BLACK)
                .sound(SoundRegistry.BLIGHTED_EARTH)
                .strength(-1.0F, 3600000.0F);
    }
}
