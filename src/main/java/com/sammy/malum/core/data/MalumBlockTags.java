package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.block.ether.EtherBlock;
import com.sammy.malum.core.setup.content.block.BlockRegistry;
import com.sammy.malum.core.setup.content.block.BlockTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import team.lodestar.lodestone.systems.block.LodestoneBlockProperties;
import team.lodestar.lodestone.systems.block.LodestoneThrowawayBlockData;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static com.sammy.malum.core.setup.content.block.BlockRegistry.*;
import static com.sammy.malum.core.setup.content.block.BlockTagRegistry.STRIPPED_LOGS;
import static net.minecraft.tags.BlockTags.*;

public class MalumBlockTags extends BlockTagsProvider {
    public MalumBlockTags(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, MalumMod.MALUM, existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void addTags() {
        tag(Tags.Blocks.ORES).add(BlockRegistry.SOULSTONE_ORE.get(), DEEPSLATE_SOULSTONE_ORE.get(), BlockRegistry.BLAZING_QUARTZ_ORE.get(), BlockRegistry.BRILLIANT_STONE.get(), BRILLIANT_DEEPSLATE.get(), NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get(), BLOCK_OF_AURUM.get());

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
        tag(STRIPPED_LOGS).add(getModBlocks(b -> b.getRegistryName().getPath().startsWith("stripped_") && (b.getRegistryName().getPath().endsWith("_log") || b.getRegistryName().getPath().endsWith("wood"))));
        tag(PLANKS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_planks")));
        tag(WOODEN_BUTTONS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_button") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_FENCES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_fence") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_DOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_door") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_STAIRS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_stairs") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_SLABS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_slab") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_TRAPDOORS).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_trapdoor") && b.getRegistryName().getPath().contains("wood")));
        tag(WOODEN_PRESSURE_PLATES).add(getModBlocks(b -> b.getRegistryName().getPath().endsWith("_pressure_plate") && b.getRegistryName().getPath().contains("wood")));

        tag(BlockTagRegistry.HEAT_SOURCES).add(BlockRegistry.BLOCK_OF_BLAZING_QUARTZ.get());
        tag(BlockTagRegistry.RUNEWOOD_LOGS).add(BlockRegistry.RUNEWOOD_LOG.get(),BlockRegistry.RUNEWOOD.get(),BlockRegistry.EXPOSED_RUNEWOOD_LOG.get(),BlockRegistry.REVEALED_RUNEWOOD_LOG.get());
        tag(BlockTagRegistry.SOULWOOD_LOGS).add(BlockRegistry.SOULWOOD_LOG.get(),BlockRegistry.SOULWOOD.get(),BlockRegistry.EXPOSED_SOULWOOD_LOG.get(),BlockRegistry.REVEALED_SOULWOOD_LOG.get());

        tag(BlockTagRegistry.BLIGHTED_BLOCKS).add(BlockRegistry.BLIGHTED_SOIL.get());
        tag(BlockTagRegistry.BLIGHTED_PLANTS).add(BLIGHTED_WEED.get(), BLIGHTED_TUMOR.get(), SOULWOOD_GROWTH.get());

        for (Block block : getModBlocks(b -> b.getRegistryName().getPath().contains("tainted_"))) {
            tag(BlockTagRegistry.TAINTED_ROCK).add(block);
        }
        for (Block block : getModBlocks(b -> b.getRegistryName().getPath().contains("twisted_"))) {
            tag(BlockTagRegistry.TWISTED_ROCK).add(block);
        }
        tag(BlockTagRegistry.RITE_IMMUNE).add(RUNEWOOD_TOTEM_BASE.get(), RUNEWOOD_TOTEM_POLE.get(), SOULWOOD_TOTEM_BASE.get(), SOULWOOD_TOTEM_POLE.get());
        tag(BlockTagRegistry.RITE_IMMUNE).addTags(BlockTagRegistry.TAINTED_ROCK, BlockTagRegistry.TWISTED_ROCK);

        tag(BlockTagRegistry.ENDLESS_FLAME);
        tag(BlockTagRegistry.GREATER_AERIAL_WHITELIST);

        for (Block block : getModBlocks(b -> b instanceof EtherBlock)) {
            tag(BlockTagRegistry.TRAY_HEAT_SOURCES).add(block);
        }
        for (Block block : getModBlocks(b -> b.properties instanceof LodestoneBlockProperties)) {
            LodestoneBlockProperties properties = (LodestoneBlockProperties) block.properties;
            LodestoneThrowawayBlockData data = properties.getThrowawayData();
            if (data.needsPickaxe) {
                tag(MINEABLE_WITH_PICKAXE).add(block);
            }
            if (data.needsShovel) {
                tag(MINEABLE_WITH_SHOVEL).add(block);
            }
            if (data.needsAxe) {
                tag(MINEABLE_WITH_AXE).add(block);
            }
            if (data.needsHoe) {
                tag(MINEABLE_WITH_HOE).add(block);
            }
            if (data.needsStone) {
                tag(NEEDS_STONE_TOOL).add(block);
            }
            if (data.needsIron) {
                tag(NEEDS_IRON_TOOL).add(block);
            }
            if (data.needsDiamond) {
                tag(NEEDS_DIAMOND_TOOL).add(block);
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
