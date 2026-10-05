package com.sammy.malum.registry.common.block.properties;

import com.sammy.malum.registry.common.sound.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.modules.toolkit.block.*;

import static com.sammy.malum.registry.common.MalumTags.Blocks.*;

public class MalumStoneBlockProperties {

    public static LodestoneBlockProperties STONE_BOOKSHELF() {
        return new LodestoneBlockProperties()
                .strength(1.5F, 6.0F)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .mapColor(MapColor.STONE)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties SEED_QUARTZ(SoundType soundType) {
        return new LodestoneBlockProperties()
                .strength(0.75F, 9.0F)
                .sound(soundType)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties TRODDEN_STONE(SoundType soundType) {
        return new LodestoneBlockProperties()
                .strength(1F, 9.0F)
                .sound(soundType)
                .mapColor(MapColor.COLOR_GRAY)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties IGNEOUS_ROCK(SoundType soundType) {
        return new LodestoneBlockProperties()
                .strength(1.25F, 9.0F)
                .sound(soundType)
                .mapColor(MapColor.TERRACOTTA_GRAY)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties COMPOSITE_STONE(SoundType soundType) {
        return new LodestoneBlockProperties()
                .strength(1.5F, 9.0F)
                .sound(soundType)
                .mapColor(MapColor.COLOR_ORANGE)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties EBONSTONE(SoundType soundType) {
        return new LodestoneBlockProperties()
                .strength(1.75F, 9.0F)
                .sound(soundType)
                .mapColor(MapColor.TERRACOTTA_WHITE)
                .requiresCorrectToolForDrops()
                .addTag(PREFERRED_EBONY_SURFACE)
                .needsPickaxe();
    }

    public static LodestoneBlockProperties TAINTED_ROCK() {
        return new LodestoneBlockProperties()
                .addTag(TAINTED_ROCK)
                .strength(1.25F, 9.0F)
                .sound(MalumBlockSoundEvents.TAINTED_ROCK)
                .mapColor(MapColor.COLOR_GRAY)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties TAINTED_ROCK_BRICKS() {
        return TAINTED_ROCK().sound(MalumBlockSoundEvents.TAINTED_ROCK_BRICKS);
    }

    public static LodestoneBlockProperties CHISELED_TAINTED_ROCK() {
        return TAINTED_ROCK().sound(MalumBlockSoundEvents.CHISELED_TAINTED_ROCK);
    }

    public static LodestoneBlockProperties TWISTED_ROCK() {
        return new LodestoneBlockProperties()
                .addTag(TWISTED_ROCK)
                .strength(1.25F, 9.0F)
                .sound(MalumBlockSoundEvents.TWISTED_ROCK)
                .mapColor(MapColor.COLOR_BLACK)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties TWISTED_ROCK_BRICKS() {
        return TWISTED_ROCK().sound(MalumBlockSoundEvents.TWISTED_ROCK_BRICKS);
    }

    public static LodestoneBlockProperties CHISELED_TWISTED_ROCK() {
        return TWISTED_ROCK().sound(MalumBlockSoundEvents.CHISELED_TWISTED_ROCK);
    }

    public static LodestoneBlockProperties ARCANE_ROCK_ARTIFICE() {
        return TAINTED_ROCK_BRICKS()
                .strength(2.5f, 30.0F)
                .sound(MalumBlockSoundEvents.ARCANE_ROCK_ARTIFICE)
                .setCutout()
                .noOcclusion();
    }

    public static LodestoneBlockProperties TAINTED_ROCK_TOTEMANCY() {
        return TAINTED_ROCK_BRICKS()
                .strength(2F, 30.0F)
                .isRedstoneConductor(Blocks::never)
                .addTag(IS_RITE_IMMUNE);
    }

    public static LodestoneBlockProperties TWISTED_ROCK_TOTEMANCY() {
        return TWISTED_ROCK_BRICKS()
                .strength(2F, 30.0F)
                .isRedstoneConductor(Blocks::never)
                .addTag(IS_RITE_IMMUNE);
    }
}
