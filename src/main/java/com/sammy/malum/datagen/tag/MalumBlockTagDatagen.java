package com.sammy.malum.datagen.tag;

import com.sammy.malum.MalumMod;
import com.sammy.malum.datagen.set.MalumMetallicsDatagen;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.*;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestone.modules.datagen.providers.tag.LodestoneBlockTagsSystem;

import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.sammy.malum.registry.common.MalumContent.Blight.*;
import static com.sammy.malum.registry.common.MalumTags.Blocks.*;
import static com.sammy.malum.registry.common.MalumContent.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

public class MalumBlockTagDatagen extends LodestoneBlockTagsSystem {

    public MalumBlockTagDatagen(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Malum Block Tags";
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        var blocks = new HashSet<>(BLOCKS.getEntries());

        MalumMetallicsDatagen.MALUM.addTags(this::tag);

        tag(PREFERRED_SOULSTONE_BUD_SURFACE).addTags(NATURAL_SOULSTONE_BUD_SURFACE, BASE_STONE_OVERWORLD);
        tag(PREFERRED_WITCHHAZEL_SURFACE).addTags(BASE_STONE_OVERWORLD).add(Blocks.MYCELIUM);
        tag(PREFERRED_EBONY_SURFACE).addTags(BASE_STONE_OVERWORLD);

        tag(BLIGHT_REPLACEABLE).addTags(MOSS_REPLACEABLE, SAND).add(Blocks.CLAY, Blocks.GRAVEL);
        tag(BLIGHT_REMOVABLE).addTags(FLOWERS, REPLACEABLE).remove(BLIGHTED_PLANTS).remove(CLINGING_BLIGHT.get());

        tag(EBONY_REPLACEABLE).addTags(MOSS_REPLACEABLE, SAND).add(Blocks.DRIPSTONE_BLOCK, Blocks.GRAVEL);

        tag(UNCHAINED_RITE_CATALYST).add(BLIGHTED_EARTH.get());
        tag(IS_RITE_IMMUNE).addTags(TAINTED_ROCK, TWISTED_ROCK, WEEPING_WELL);

        tag(GREATER_AERIAL_WHITELIST);

        tag(SUNDERING_ANCHOR_PLEASE_BE_A_KNIFE).addOptional(ResourceLocation.parse("farmersdelight:cutting_board"));

        tag(REAPING_ORES).addTags(IRON_ORES, GOLD_ORES, COPPER_ORES);

        addTagsFromBlockProperties(blocks);
    }
}
