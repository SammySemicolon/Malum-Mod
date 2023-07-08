package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.NUGGETS_COPPER;

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
        this.copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        this.copy(BlockTags.WOODEN_DOORS, net.minecraft.tags.ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, net.minecraft.tags.ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, net.minecraft.tags.ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, net.minecraft.tags.ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, net.minecraft.tags.ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.DOORS, net.minecraft.tags.ItemTags.DOORS);
        this.copy(BlockTags.SAPLINGS, net.minecraft.tags.ItemTags.SAPLINGS);
        this.copy(BlockTags.LOGS_THAT_BURN, net.minecraft.tags.ItemTags.LOGS_THAT_BURN);
        this.copy(BlockTags.LOGS, net.minecraft.tags.ItemTags.LOGS);
        this.copy(BlockTagRegistry.STRIPPED_LOGS, STRIPPED_LOGS);
        this.copy(BlockTags.SAND, net.minecraft.tags.ItemTags.SAND);
        this.copy(BlockTags.SLABS, net.minecraft.tags.ItemTags.SLABS);
        this.copy(BlockTags.WALLS, net.minecraft.tags.ItemTags.WALLS);
        this.copy(BlockTags.STAIRS, net.minecraft.tags.ItemTags.STAIRS);
        this.copy(BlockTags.ANVIL, net.minecraft.tags.ItemTags.ANVIL);
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
        tag(Tags.Items.GEMS_QUARTZ).add(NATURAL_QUARTZ.get());
        tag(Tags.Items.ORES_QUARTZ).add(NATURAL_QUARTZ_ORE.get(), DEEPSLATE_QUARTZ_ORE.get());

        tag(GROSS_FOODS).add(Items.ROTTEN_FLESH, ROTTING_ESSENCE.get());
        ITEMS.getEntries().stream().filter(i -> i.get() instanceof NodeItem).map(RegistryObject::get).forEach(i -> {
            tag(METAL_NODES).add(i);
        });

        tag(PROSPECTORS_TREASURE).addTags(Tags.Items.ORES, Tags.Items.STORAGE_BLOCKS, Tags.Items.INGOTS, Tags.Items.NUGGETS, Tags.Items.GEMS, Tags.Items.RAW_MATERIALS, ItemTags.COALS, METAL_NODES);
        tag(PROSPECTORS_TREASURE).addOptional(new ResourceLocation("tetra", "geode"));


        tag(RUNEWOOD_LOGS).add(RUNEWOOD_LOG.get(), STRIPPED_RUNEWOOD_LOG.get(), RUNEWOOD.get(), STRIPPED_RUNEWOOD.get(), EXPOSED_RUNEWOOD_LOG.get(), REVEALED_RUNEWOOD_LOG.get());
        tag(SOULWOOD_LOGS).add(SOULWOOD_LOG.get(), STRIPPED_SOULWOOD_LOG.get(), SOULWOOD.get(), STRIPPED_SOULWOOD.get(), EXPOSED_SOULWOOD_LOG.get(), REVEALED_SOULWOOD_LOG.get(), BLIGHTED_SOULWOOD.get());

        tag(SCYTHE).add(CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());

        tag(SOUL_HUNTER_WEAPON).add(TYRVING.get(), CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());
        tag(SOUL_HUNTER_WEAPON).add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        tag(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());

        tag(KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        tag(NUGGETS_COPPER).add(COPPER_NUGGET.get());
    }
}