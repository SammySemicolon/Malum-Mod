package com.sammy.malum.registry.common.block.properties;

import com.sammy.malum.registry.common.sound.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.modules.toolkit.block.*;

import static com.sammy.malum.registry.common.MalumTags.Blocks.*;
import static net.minecraft.tags.BlockTags.*;
import static net.minecraft.world.level.block.Blocks.COPPER_BLOCK;

public class MalumBlockProperties {

    public static LodestoneBlockProperties POTTED_PLANT() {
        return new LodestoneBlockProperties()
                .setCutout()
                .addTag(FLOWER_POTS)
                .instabreak()
                .noOcclusion()
                .pushReaction(PushReaction.DESTROY);
    }

    public static LodestoneBlockProperties RUNEWOOD_IMPLEMENT() {
        return MalumWoodBlockProperties.RUNEWOOD()
                .strength(2.5f, 64f)
                .instrument(NoteBlockInstrument.XYLOPHONE)
                .mapColor(MapColor.TERRACOTTA_BROWN)
                .setCutout()
                .noOcclusion();
    }

    public static LodestoneBlockProperties POPPETRY_IMPLEMENT() {
        return MalumWoodBlockProperties.RUNEWOOD()
                .strength(1.5f, 64f)
                .mapColor(MapColor.TERRACOTTA_BROWN)
                .setCutout()
                .noOcclusion();
    }

    public static LodestoneBlockProperties COPPER_ARTIFICE() {
        return new LodestoneBlockProperties()
                .addTag(CREATE_WRENCH_PICKUP)
                .addTag(PREFERRED_SOULSTONE_BUD_SURFACE)
                .mapColor(COPPER_BLOCK.defaultMapColor())
                .strength(4.0F, 32.0F)
                .sound(MalumBlockSoundEvents.SPIRIT_DIODE)
                .requiresCorrectToolForDrops()
                .isRedstoneConductor(Blocks::never)
                .needsPickaxe()
                .needsAxe();
    }

    public static LodestoneBlockProperties SPIRIT_JAR() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.GOLD)
                .strength(1f, 64f)
                .sound(MalumBlockSoundEvents.BLOCK_OF_HALLOWED_GOLD)
                .setCutout()
                .noOcclusion();
    }

    public static LodestoneBlockProperties SOUL_BRAZIER() {
        return new LodestoneBlockProperties()
                .strength(2f, 64f)
                .instrument(NoteBlockInstrument.XYLOPHONE)
                .mapColor(MapColor.TERRACOTTA_BROWN)
                .sound(MalumBlockSoundEvents.RUNEWOOD)
                .setCutout()
                .noOcclusion()
                .needsAxe();
    }

    public static LodestoneBlockProperties SPIRITED_GLASS() {
        return new LodestoneBlockProperties()
                .instrument(NoteBlockInstrument.HAT)
                .isRedstoneConductor(Blocks::never)
                .isViewBlocking(Blocks::never)
                .isSuffocating(Blocks::never)
                .isValidSpawn(Blocks::never)
                .sound(SoundType.GLASS)
                .setTranslucent()
                .strength(0.3F)
                .needsPickaxe()
                .noOcclusion();
    }

    public static LodestoneBlockProperties DROSS_STONE() {
        return new LodestoneBlockProperties()
                .addTag(DROSS_STONE)
                .strength(2F, -1.0F)
                .sound(MalumBlockSoundEvents.DROSS_STONE)
                .mapColor(MapColor.COLOR_BROWN)
                .requiresCorrectToolForDrops()
                .needsPickaxe();
    }

    public static LodestoneBlockProperties CHISELED_DROSS_STONE() {
        return DROSS_STONE().sound(MalumBlockSoundEvents.CHISELED_DROSS_STONE);
    }

    public static LodestoneBlockProperties VARNISHED_TERRACOTTA(DyeColor color) {
        return new LodestoneBlockProperties()
                .sound(MalumBlockSoundEvents.VARNISHED_TERRACOTTA)
                .instrument(NoteBlockInstrument.BASEDRUM)
                .pushReaction(PushReaction.PUSH_ONLY)
                .requiresCorrectToolForDrops()
                .mapColor(color)
                .strength(1.4F)
                .needsPickaxe();
    }

    public static LodestoneBlockProperties SOULWOVEN_BANNER() {
        return MalumWoodBlockProperties.RUNEWOOD()
                .noOcclusion()
                .noCollission()
                .setCutout();
    }
}
