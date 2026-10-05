package com.sammy.malum.registry.common.block.properties;

import com.sammy.malum.registry.common.sound.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.*;
import team.lodestar.lodestone.modules.toolkit.block.*;

import static com.sammy.malum.registry.common.MalumTags.Blocks.WEEPING_WELL;
import static net.minecraft.tags.BlockTags.*;

public class MalumEnscriptionBlockProperties {

    public static LodestoneBlockProperties MYRIAD_GATEWAY() {
        return new LodestoneBlockProperties()
                .strength(-1.0F, 3600000.0F)
                .addTags(FEATURES_CANNOT_REPLACE)
                .mapColor(MapColor.TERRACOTTA_GRAY)
                .pushReaction(PushReaction.BLOCK);
    }

    public static LodestoneBlockProperties MEDITATING_EFFIGY() {
        return MalumBlockProperties.CHISELED_DROSS_STONE()
                .addTag(WALL_POST_OVERRIDE)
                .setCutout()
                .noOcclusion()
                .strength(3.5F, -1.0F);
    }

    public static LodestoneBlockProperties VOID_FLUID() {
        return new LodestoneBlockProperties()
                .strength(-1.0F, 3600000.0F)
                .addTags(FEATURES_CANNOT_REPLACE, WEEPING_WELL)
                .sound(MalumBlockSoundEvents.BLIGHTED_EARTH)
                .mapColor(MapColor.TERRACOTTA_BLACK)
                .pushReaction(PushReaction.BLOCK)
                .isSuffocating(Blocks::never)
                .setCutout();
    }

    public static LodestoneBlockProperties FLESH() {
        return new LodestoneBlockProperties()
                .mapColor(MapColor.COLOR_BLACK);
    }

    public static LodestoneBlockProperties FLESH_BLOCK() {
        return FLESH()
                .sound(MalumBlockSoundEvents.FLESH)
                .strength(1.5F, -1.0F);
    }

    public static LodestoneBlockProperties FLESHBULB() {
        return FLESH_BLOCK()
                .lightLevel(s -> 6);
    }

    public static LodestoneBlockProperties WRITHING_FLESH() {
        return FLESH()
                .sound(MalumBlockSoundEvents.WRITHING_FLESH)
                .addTag(REPLACEABLE_BY_TREES)
                .setCutout()
                .strength(0.5F, -1.0F)
                .noCollission()
                .noOcclusion();
    }
}