package com.sammy.malum.core.data;

import com.sammy.malum.MalumMod;
import com.sammy.malum.core.setup.content.item.ItemTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.sammy.malum.core.setup.content.item.ItemRegistry.*;
import static com.sammy.malum.core.setup.content.item.ItemTagRegistry.*;
import static com.sammy.ortus.setup.OrtusItemTags.*;

public class MalumItemTags extends ItemTagsProvider {
    public MalumItemTags(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, MalumMod.MALUM, existingFileHelper);
    }

    @Override
    public String getName() {
        return "Malum Item Tags";
    }

    @Override
    protected void addTags() {
        tag(Tags.Items.GEMS).add(PROCESSED_SOULSTONE.get(), BLAZING_QUARTZ.get());

        this.copy(BlockTags.WOOL, net.minecraft.tags.ItemTags.WOOL);
        this.copy(BlockTags.PLANKS, net.minecraft.tags.ItemTags.PLANKS);
        this.copy(BlockTags.STONE_BRICKS, net.minecraft.tags.ItemTags.STONE_BRICKS);
        this.copy(BlockTags.WOODEN_BUTTONS, net.minecraft.tags.ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.BUTTONS, net.minecraft.tags.ItemTags.BUTTONS);
        this.copy(BlockTags.CARPETS, net.minecraft.tags.ItemTags.CARPETS);
        this.copy(BlockTags.WOODEN_DOORS, net.minecraft.tags.ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, net.minecraft.tags.ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, net.minecraft.tags.ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, net.minecraft.tags.ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, net.minecraft.tags.ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.DOORS, net.minecraft.tags.ItemTags.DOORS);
        this.copy(BlockTags.SAPLINGS, net.minecraft.tags.ItemTags.SAPLINGS);
        this.copy(BlockTags.OAK_LOGS, net.minecraft.tags.ItemTags.OAK_LOGS);
        this.copy(BlockTags.DARK_OAK_LOGS, net.minecraft.tags.ItemTags.DARK_OAK_LOGS);
        this.copy(BlockTags.BIRCH_LOGS, net.minecraft.tags.ItemTags.BIRCH_LOGS);
        this.copy(BlockTags.ACACIA_LOGS, net.minecraft.tags.ItemTags.ACACIA_LOGS);
        this.copy(BlockTags.SPRUCE_LOGS, net.minecraft.tags.ItemTags.SPRUCE_LOGS);
        this.copy(BlockTags.JUNGLE_LOGS, net.minecraft.tags.ItemTags.JUNGLE_LOGS);
        this.copy(BlockTags.CRIMSON_STEMS, net.minecraft.tags.ItemTags.CRIMSON_STEMS);
        this.copy(BlockTags.WARPED_STEMS, net.minecraft.tags.ItemTags.WARPED_STEMS);
        this.copy(BlockTags.LOGS_THAT_BURN, net.minecraft.tags.ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.LOGS, net.minecraft.tags.ItemTags.LOGS);
        this.copy(BlockTags.SAND, net.minecraft.tags.ItemTags.SAND);
        this.copy(BlockTags.SLABS, net.minecraft.tags.ItemTags.SLABS);
        this.copy(BlockTags.WALLS, net.minecraft.tags.ItemTags.WALLS);
        this.copy(BlockTags.STAIRS, net.minecraft.tags.ItemTags.STAIRS);
        this.copy(BlockTags.ANVIL, net.minecraft.tags.ItemTags.ANVIL);
        this.copy(BlockTags.RAILS, net.minecraft.tags.ItemTags.RAILS);
        this.copy(BlockTags.LEAVES, net.minecraft.tags.ItemTags.LEAVES);
        this.copy(BlockTags.WOODEN_TRAPDOORS, net.minecraft.tags.ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.TRAPDOORS, net.minecraft.tags.ItemTags.TRAPDOORS);
        this.copy(BlockTags.SMALL_FLOWERS, net.minecraft.tags.ItemTags.SMALL_FLOWERS);
        this.copy(BlockTags.BEDS, net.minecraft.tags.ItemTags.BEDS);
        this.copy(BlockTags.FENCES, net.minecraft.tags.ItemTags.FENCES);
        this.copy(BlockTags.TALL_FLOWERS, net.minecraft.tags.ItemTags.TALL_FLOWERS);
        this.copy(BlockTags.FLOWERS, net.minecraft.tags.ItemTags.FLOWERS);
        this.copy(BlockTags.GOLD_ORES, net.minecraft.tags.ItemTags.GOLD_ORES);
        this.copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, net.minecraft.tags.ItemTags.SOUL_FIRE_BASE_BLOCKS);
        this.copy(Tags.Blocks.ORES, Tags.Items.ORES);

        tag(Tags.Items.SLIMEBALLS).add(HOLY_SAPBALL.get(), UNHOLY_SAPBALL.get());
        tag(ItemTagRegistry.SAPBALLS).add(HOLY_SAPBALL.get(), UNHOLY_SAPBALL.get());

        tag(GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get());

        tag(RUNEWOOD_LOGS).add(RUNEWOOD_LOG.get(), STRIPPED_RUNEWOOD_LOG.get(), RUNEWOOD.get(), STRIPPED_RUNEWOOD.get(), EXPOSED_RUNEWOOD_LOG.get(), REVEALED_RUNEWOOD_LOG.get());
        tag(SOULWOOD_LOGS).add(SOULWOOD_LOG.get(), STRIPPED_SOULWOOD_LOG.get(), SOULWOOD.get(), STRIPPED_SOULWOOD.get(), EXPOSED_SOULWOOD_LOG.get(), REVEALED_SOULWOOD_LOG.get());

        tag(SCYTHE).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());

        tag(SOUL_HUNTER_WEAPON).add(TYRVING.get(), CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());
        tag(SOUL_HUNTER_WEAPON).add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        tag(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());

        tag(KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        tag(NUGGETS_COPPER).add(COPPER_NUGGET.get());
        tag(INGOTS_COPPER).add(Items.COPPER_INGOT);
        tag(NUGGETS_LEAD);
        tag(INGOTS_LEAD);
        tag(NUGGETS_SILVER);
        tag(INGOTS_SILVER);
        tag(NUGGETS_ALUMINUM);
        tag(INGOTS_ALUMINUM);
        tag(NUGGETS_NICKEL);
        tag(INGOTS_NICKEL);
        tag(NUGGETS_URANIUM);
        tag(INGOTS_URANIUM);
        tag(NUGGETS_OSMIUM);
        tag(INGOTS_OSMIUM);
        tag(NUGGETS_ZINC);
        tag(INGOTS_ZINC);
        tag(NUGGETS_TIN);
        tag(INGOTS_TIN);
    }
}