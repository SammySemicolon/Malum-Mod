package com.sammy.malum.data.item;

import com.sammy.malum.*;
import com.sammy.malum.common.item.curiosities.curios.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.registry.common.block.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.tags.*;
import net.minecraft.resources.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.data.*;
import net.minecraftforge.registries.*;
import org.jetbrains.annotations.*;

import java.util.concurrent.*;

import static com.sammy.malum.registry.common.item.ItemRegistry.RUNEWOOD_PLANKS;
import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.*;

public class MalumItemTags extends ItemTagsProvider {


    public MalumItemTags(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Malum Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        copy(BlockTags.PLANKS, ItemTags.PLANKS);
        copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        copy(BlockTags.DOORS, ItemTags.DOORS);
        copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        copy(BlockTagRegistry.STRIPPED_LOGS, STRIPPED_LOGS);
        copy(BlockTags.SLABS, ItemTags.SLABS);
        copy(BlockTags.WALLS, ItemTags.WALLS);
        copy(BlockTags.STAIRS, ItemTags.STAIRS);
        copy(BlockTags.LEAVES, ItemTags.LEAVES);
        copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        copy(BlockTags.FENCES, ItemTags.FENCES);
        copy(Tags.Blocks.ORES, Tags.Items.ORES);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

        tag(Tags.Items.GEMS).add(PROCESSED_SOULSTONE.get(), BLAZING_QUARTZ.get());
        tag(ItemTags.LOGS).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);
        tag(ItemTags.LOGS_THAT_BURN).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);
        tag(Tags.Items.SLIMEBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        tag(Tags.Items.GEMS_QUARTZ).add(NATURAL_QUARTZ.get());
        tag(Tags.Items.ORES_QUARTZ).add(NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get());

        tag(ItemTagRegistry.SAPBALLS).add(RUNIC_SAPBALL.get(), CURSED_SAPBALL.get());
        tag(GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get());

        ITEMS.getEntries().stream().filter(i -> i.get() instanceof NodeItem).map(RegistryObject::get).forEach(i -> {
            tag(METAL_NODES).add(i);
        });
        tag(PROSPECTORS_TREASURE).addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, METAL_NODES);
        tag(PROSPECTORS_TREASURE).addOptional(new ResourceLocation("tetra", "geode"));

        tag(RUNEWOOD_LOGS).add(RUNEWOOD_LOG.get(), STRIPPED_RUNEWOOD_LOG.get(), RUNEWOOD.get(), STRIPPED_RUNEWOOD.get(), EXPOSED_RUNEWOOD_LOG.get(), REVEALED_RUNEWOOD_LOG.get());
        tag(RUNEWOOD_WITH_BARK).add(RUNEWOOD_LOG.get(), RUNEWOOD.get());

        tag(ItemTagRegistry.RUNEWOOD_PLANKS).add(
                RUNEWOOD_PLANKS.get(), RUSTIC_RUNEWOOD_PLANKS.get(), VERTICAL_RUNEWOOD_PLANKS.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS.get(), RUNEWOOD_TILES.get(), RUSTIC_RUNEWOOD_TILES.get());
        tag(RUNEWOOD_SLABS).add(
                RUNEWOOD_PLANKS_SLAB.get(), RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), VERTICAL_RUNEWOOD_PLANKS_SLAB.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS_SLAB.get(), RUNEWOOD_TILES_SLAB.get(), RUSTIC_RUNEWOOD_TILES_SLAB.get());
        tag(RUNEWOOD_STAIRS).add(
                RUNEWOOD_PLANKS_STAIRS.get(), RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(),
                VERTICAL_RUSTIC_RUNEWOOD_PLANKS_STAIRS.get(), RUNEWOOD_TILES_STAIRS.get(), RUSTIC_RUNEWOOD_TILES_STAIRS.get());


        tag(SOULWOOD_LOGS).add(SOULWOOD_LOG.get(), STRIPPED_SOULWOOD_LOG.get(), SOULWOOD.get(), STRIPPED_SOULWOOD.get(), EXPOSED_SOULWOOD_LOG.get(), REVEALED_SOULWOOD_LOG.get(), BLIGHTED_SOULWOOD.get());
        tag(SOULWOOD_WITH_BARK).add(SOULWOOD_LOG.get(), SOULWOOD.get());
        tag(ItemTagRegistry.SOULWOOD_PLANKS);
        tag(SOULWOOD_SLABS);
        tag(SOULWOOD_STAIRS);
//        tag(ItemTagRegistry.SOULWOOD_PLANKS).add(SOULWOOD_PLANKS.get(), RUSTIC_SOULWOOD_PLANKS.get(), VERTICAL_SOULWOOD_PLANKS.get(), VERTICAL_RUSTIC_SOULWOOD_PLANKS.get(), SOULWOOD_TILES.get(), RUSTIC_SOULWOOD_TILES.get());

        tag(SCYTHE).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());
        tag(STAFF).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), MALIGNANT_SCEPTER.get());

        tag(SOUL_HUNTER_WEAPON).add(MNEMONIC_HEX_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get(), MALIGNANT_SCEPTER.get());
        tag(SOUL_HUNTER_WEAPON).add(TYRVING.get(), CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get(), WEIGHT_OF_WORLDS.get());
        tag(SOUL_HUNTER_WEAPON).add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        tag(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());
        tag(Tags.Items.GEMS).add(NATURAL_QUARTZ.get(), BLAZING_QUARTZ.get(), CLUSTER_OF_BRILLIANCE.get());
        tag(Tags.Items.INGOTS).add(SOUL_STAINED_STEEL_INGOT.get(), HALLOWED_GOLD_INGOT.get());
        
        tag(KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        tag(NUGGETS_COPPER).add(COPPER_NUGGET.get());

        for (RegistryObject<Item> i : ITEMS.getEntries()) {
            if (i.get() instanceof MalumCurioItem) {
                final Item item = i.get();
                final ResourceLocation id = i.getId();
                if (id.getPath().contains("_ring") || id.getPath().contains("ring_")) {
                    tag(RING).add(item);
                    continue;
                }
                if (id.getPath().contains("_necklace") || id.getPath().contains("necklace_")) {
                    tag(NECKLACE).add(item);
                    continue;
                }
                if (id.getPath().contains("_belt") || id.getPath().contains("belt_")) {
                    tag(BELT).add(item);
                    continue;
                }
                if (id.getPath().contains("_rune") || id.getPath().contains("rune_")) {
                    tag(RUNE).add(item);
                    continue;
                }
                if (id.getPath().contains("_brooch") || id.getPath().contains("brooch_")) {
                    tag(BROOCH).add(item);
                }
            }
        }
        tag(CHARM).add(TOPHAT.get(), TOKEN_OF_GRATITUDE.get());
    }
}