package com.sammy.malum.data.block;

import com.sammy.malum.*;
import com.sammy.malum.registry.common.block.*;
import net.minecraft.data.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.systems.datagen.providers.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.sammy.malum.registry.common.block.BlockRegistry.*;

public class MalumBlockTags extends LodestoneBlockTagsProvider {
    public MalumBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, MalumMod.MALUM, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BLOCKS.getEntries());

        tag(BlockTagRegistry.RITE_IMMUNE).addTags(BlockTagRegistry.TAINTED_ROCK, BlockTagRegistry.TWISTED_ROCK);

        tag(BlockTagRegistry.ENDLESS_FLAME);
        tag(BlockTagRegistry.GREATER_AERIAL_WHITELIST);

        addTagsFromBlockProperties(blocks.stream().map(RegistryObject::get).collect(Collectors.toList()));
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
