package com.sammy.malum.registry.common.block;

import com.sammy.malum.registry.common.SoundRegistry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.material.*;
import net.minecraftforge.common.Tags;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;

public class MalumBlockProperties {

    public static LodestoneBlockProperties TAINTED_ROCK() {
        return new LodestoneBlockProperties()
                .addTag(BlockTagRegistry.TAINTED_ROCK)
                .sound(SoundRegistry.TAINTED_ROCK)
                .mapColor(MapColor.STONE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(1.25F, 9.0F);
    }

    public static LodestoneBlockProperties TWISTED_ROCK() {
        return new LodestoneBlockProperties()
                .addTag(BlockTagRegistry.TWISTED_ROCK)
                .sound(SoundRegistry.TWISTED_ROCK)
                .mapColor(MapColor.STONE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(1.25F, 9.0F);
    }

    public static LodestoneBlockProperties RUNEWOOD() {
        return new LodestoneBlockProperties()
                .sound(SoundType.WOOD)
                .mapColor(MapColor.WOOD)
                .needsAxe()
                .ignitedByLava()
                .strength(1.75F, 4.0F);
    }

    public static LodestoneBlockProperties RUNEWOOD_SAPLING() {
        return new LodestoneBlockProperties()
                .sound(SoundType.GRASS)
                .mapColor(DyeColor.YELLOW)
                .addTag(BlockTags.SAPLINGS)
                .noCollission()
                .noOcclusion()
                .instabreak()
                .randomTicks();
    }

    public static LodestoneBlockProperties RUNEWOOD_LEAVES() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.RUNEWOOD_LEAVES)
                .mapColor(DyeColor.PURPLE)
                .addTag(BlockTags.LEAVES)
                .needsHoe()
                .noOcclusion()
                .randomTicks()
                .strength(0.2F)
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .isRedstoneConductor(Blocks::never);
    }

    public static LodestoneBlockProperties SOULWOOD() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.SOULWOOD)
                .mapColor(MapColor.WOOD)
                .needsAxe()
                .ignitedByLava()
                .strength(1.75F, 4.0F);
    }

    public static LodestoneBlockProperties BLIGHTED_PLANTS() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BLIGHTED_FOLIAGE)
                .mapColor(DyeColor.GRAY)
                .addTag(BlockTagRegistry.BLIGHTED_PLANTS)
                .offsetType(BlockBehaviour.OffsetType.XYZ)
                .replaceable()
                .noCollission()
                .noOcclusion()
                .instabreak();
    }

    public static LodestoneBlockProperties SOULWOOD_LEAVES() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.SOULWOOD_LEAVES)
                .mapColor(MapColor.COLOR_PURPLE)
                .addTag(BlockTags.LEAVES)
                .needsHoe()
                .noOcclusion()
                .randomTicks()
                .strength(0.2F)
                .isValidSpawn(Blocks::ocelotOrParrot)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never)
                .isRedstoneConductor(Blocks::never);
    }

    public static LodestoneBlockProperties BLIGHT() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BLIGHTED_EARTH)
                .mapColor(DyeColor.BLACK)
                .addTag(BlockTagRegistry.BLIGHTED_BLOCKS)
                .needsShovel()
                .needsHoe()
                .speedFactor(0.7F)
                .strength(0.7f);
    }

    public static LodestoneBlockProperties BRILLIANCE_ORE(boolean isDeepslate) {
        return new LodestoneBlockProperties()
                .sound(isDeepslate ? SoundType.DEEPSLATE : SoundType.STONE)
                .mapColor(isDeepslate ? MapColor.DEEPSLATE : MapColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(isDeepslate ? 5f : 3f, 3f);
    }

    public static LodestoneBlockProperties NATURAL_QUARTZ_ORE(boolean isDeepslate) {
        return new LodestoneBlockProperties()
                .sound(isDeepslate ? SoundRegistry.DEEPSLATE_QUARTZ : SoundRegistry.NATURAL_QUARTZ)
                .mapColor(isDeepslate ? MapColor.DEEPSLATE : MapColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(isDeepslate ? 6f : 4f, 3f);
    }

    public static LodestoneBlockProperties SOULSTONE_ORE(boolean isDeepslate) {
        return new LodestoneBlockProperties()
                .sound(isDeepslate ? SoundRegistry.DEEPSLATE_SOULSTONE : SoundRegistry.SOULSTONE)
                .mapColor(isDeepslate ? MapColor.DEEPSLATE : MapColor.STONE)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(isDeepslate ? 7.0f : 5.0F, 3.0F);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_ORE() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BLAZING_QUARTZ_ORE)
                .mapColor(MapColor.NETHER)
                .addTag(Tags.Blocks.ORES)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(3.0F, 3.0F);
    }

    public static LodestoneBlockProperties CTHONIC_GOLD_ORE() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.CTHONIC_GOLD)
                .addTag(Tags.Blocks.ORES)
                .mapColor(MapColor.GOLD)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(25f, 9999f);
    }

    public static LodestoneBlockProperties NATURAL_QUARTZ_CLUSTER() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.QUARTZ_CLUSTER)
                .mapColor(DyeColor.WHITE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .forceSolidOn()
                .noOcclusion()
                .strength(1.5F);
    }

    public static LodestoneBlockProperties ETHER() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.ETHER)
                .addTag(BlockTagRegistry.TRAY_HEAT_SOURCES)
                .mapColor(DyeColor.YELLOW)
                .noCollission()
                .instabreak()
                .lightLevel((b) -> 14);
    }

    public static LodestoneBlockProperties MANA_MOTE_BLOCK() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BLAZING_QUARTZ_BLOCK)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .mapColor(MapColor.METAL)
                .setCutoutRenderType()
                .noOcclusion()
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(8.0F, 8.0f);
    }

    public static LodestoneBlockProperties SOULSTONE_BLOCK() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.SOULSTONE)
                .addTags(Tags.Blocks.STORAGE_BLOCKS, BlockTags.BEACON_BASE_BLOCKS)
                .mapColor(MapColor.COLOR_PURPLE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 3.0F);
    }

    public static LodestoneBlockProperties BLAZING_QUARTZ_BLOCK() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BLAZING_QUARTZ_BLOCK)
                .addTags(Tags.Blocks.STORAGE_BLOCKS, BlockTags.BEACON_BASE_BLOCKS, BlockTagRegistry.HEAT_SOURCES)
                .mapColor(DyeColor.ORANGE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F);
    }

    public static LodestoneBlockProperties BRILLIANCE_BLOCK() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BRILLIANCE_BLOCK)
                .addTags(Tags.Blocks.STORAGE_BLOCKS, BlockTags.BEACON_BASE_BLOCKS)
                .mapColor(MapColor.COLOR_LIGHT_GREEN)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 3.0F);
    }

    public static LodestoneBlockProperties ARCANE_CHARCOAL_BLOCK() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.ARCANE_CHARCOAL_BLOCK)
                .addTag(Tags.Blocks.STORAGE_BLOCKS)
                .mapColor(DyeColor.LIGHT_GRAY)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5.0F, 6.0F);
    }

    public static LodestoneBlockProperties HALLOWED_GOLD() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.HALLOWED_GOLD)
                .addTags(Tags.Blocks.STORAGE_BLOCKS, BlockTags.BEACON_BASE_BLOCKS)
                .mapColor(DyeColor.YELLOW)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(2F, 16.0F);
    }

    public static LodestoneBlockProperties SOUL_STAINED_STEEL_BLOCK() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.SOUL_STAINED_STEEL)
                .addTags(Tags.Blocks.STORAGE_BLOCKS, BlockTags.BEACON_BASE_BLOCKS)
                .mapColor(DyeColor.PURPLE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(5f, 64.0f);
    }

    public static LodestoneBlockProperties SPIRIT_JAR() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.HALLOWED_GOLD)
                .noOcclusion()
                .strength(0.5f, 64f)
                .isValidSpawn(Blocks::never)
                .isRedstoneConductor(Blocks::never)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never);
    }

    public static LodestoneBlockProperties SOUL_VIAL() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.SOUL_STAINED_STEEL)
                .noOcclusion()
                .strength(0.75f, 64f)
                .isValidSpawn(Blocks::never)
                .isRedstoneConductor(Blocks::never)
                .isSuffocating(Blocks::never)
                .isViewBlocking(Blocks::never);
    }

    public static LodestoneBlockProperties WEEPING_WELL() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.TAINTED_ROCK)
                .mapColor(MapColor.STONE)
                .needsPickaxe()
                .requiresCorrectToolForDrops()
                .strength(-1.0F, 3600000.0F);
    }

    public static LodestoneBlockProperties PRIMORDIAL_SOUP() {
        return new LodestoneBlockProperties()
                .sound(SoundRegistry.BLIGHTED_EARTH)
                .mapColor(DyeColor.BLACK)
                .strength(-1.0F, 3600000.0F);
    }
}
