package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.core.setup.block.BlockRegistry;
import com.sammy.malum.core.setup.block.BlockTagRegistry;
import com.sammy.malum.core.systems.block.SimpleBlockProperties;
import net.minecraft.world.level.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.core.setup.block.BlockRegistry.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

public class MalumBlockTags extends BlockTagsProvider
{
    public MalumBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper)
    {
        super(generatorIn, MalumMod.MODID, existingFileHelper);
    }
    
    @Override
    protected void addTags() {
        tag(Tags.Blocks.ORES).add(BlockRegistry.SOULSTONE_ORE.get(), BlockRegistry.BLAZING_QUARTZ_ORE.get(), BlockRegistry.BRILLIANT_STONE.get());
        tag(BEACON_BASE_BLOCKS).add(BlockRegistry.BLOCK_OF_SOULSTONE.get(), BlockRegistry.BLOCK_OF_BRILLIANCE.get(), BlockRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get(), BlockRegistry.BLOCK_OF_HALLOWED_GOLD.get());

        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        tag(BlockTags.WALLS).add(getModBlocks(b -> b instanceof WallBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(BlockTags.LEAVES).add(getModBlocks(b -> b instanceof LeavesBlock));
        tag(DOORS).add(getModBlocks(b -> b instanceof DoorBlock));
        tag(TRAPDOORS).add(getModBlocks(b -> b instanceof TrapDoorBlock));
        tag(BUTTONS).add(getModBlocks(b -> b instanceof ButtonBlock));
        tag(PRESSURE_PLATES).add(getModBlocks(b -> b instanceof PressurePlateBlock));
        tag(DIRT).add(getModBlocks(b -> b instanceof GrassBlock || b instanceof FarmBlock));
        tag(SAPLINGS).add(getModBlocks(b -> b instanceof SaplingBlock));

        tag(LOGS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_log") || b.getRegistryName().getPath().endsWith("wood")));
        tag(PLANKS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks")));
        tag(WOODEN_BUTTONS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_button") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_FENCES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_fence") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_DOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_door") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_STAIRS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_stairs") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_SLABS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_slab") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_TRAPDOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_trapdoor") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_pressure_plate") && b.getRegistryName().getPath().contains("wood")));


        tag(BlockTagRegistry.HEAT_SOURCES).add(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get());
        for (Block block : getModBlocks(b -> b instanceof EtherBlock)) {
            tag(BlockTagRegistry.TRAY_HEAT_SOURCES).add(block);
        }
        for (Block block : getModBlocks(b -> b.properties instanceof SimpleBlockProperties)) {
            SimpleBlockProperties properties = (SimpleBlockProperties) block.properties;
            if (properties.needsPickaxe) {
                tag(MINEABLE_WITH_PICKAXE).add(block);
            }
            if (properties.needsShovel) {
                tag(MINEABLE_WITH_SHOVEL).add(block);
            }
            if (properties.needsAxe) {
                tag(MINEABLE_WITH_AXE).add(block);
            }
            if (properties.needsHoe) {
                tag(MINEABLE_WITH_HOE).add(block);
            }
            if (properties.needsStone) {
                tag(NEEDS_STONE_TOOL).add(block);
            }
            if (properties.needsIron) {
                tag(NEEDS_IRON_TOOL).add(block);
            }
            if (properties.needsDiamond) {
                tag(NEEDS_DIAMOND_TOOL).add(block);
            }
        }
    }
    
    @Override
    public String getName()
    {
        return "Malum Block Tags";
    }

    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate)
    {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }
}