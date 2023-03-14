package com.sammy.malum.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import team.lodestar.lodestone.systems.block.data.LodestoneDatagenBlockData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumBlockTags extends BlockTagsProvider {
    public MalumBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, MalumMod.MALUM, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        tag(BlockTagRegistry.RITE_IMMUNE).addTags(BlockTagRegistry.TAINTED_ROCK, BlockTagRegistry.TWISTED_ROCK);

        tag(BlockTagRegistry.ENDLESS_FLAME);
        tag(BlockTagRegistry.GREATER_AERIAL_WHITELIST);

        for (Block block : getModBlocks(b -> b.properties instanceof LodestoneBlockProperties)) {
            LodestoneBlockProperties properties = (LodestoneBlockProperties) block.properties;
            LodestoneDatagenBlockData data = properties.getDatagenData();
            for (TagKey<Block> tag : data.getTags()) {
                tag(tag).add(block);
            }
        }
    }

    @Override
    public String getName() {
        return "Malum Block Tags";
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}
