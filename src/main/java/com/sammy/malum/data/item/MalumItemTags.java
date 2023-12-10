package com.sammy.malum.data.item;

import com.sammy.malum.MalumMod;
import com.sammy.malum.common.item.impetus.NodeItem;
import com.sammy.malum.registry.common.block.BlockRegistry;
import com.sammy.malum.registry.common.block.BlockTagRegistry;
import com.sammy.malum.registry.common.item.ItemTagRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.sammy.malum.registry.common.item.ItemRegistry.*;
import static com.sammy.malum.registry.common.item.ItemTagRegistry.*;
import static team.lodestar.lodestone.registry.common.tag.LodestoneItemTags.NUGGETS_COPPER;

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
        tag(Tags.Items.GEMS).add(PROCESSED_SOULSTONE.get(), BLAZING_QUARTZ.get());

        tag(ItemTags.LOGS).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);
        tag(ItemTags.LOGS_THAT_BURN).addTag(RUNEWOOD_LOGS).addTag(SOULWOOD_LOGS);

        //this.copy(BlockTags.WOOL, ItemTags.WOOL);
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        //this.copy(BlockTags.STONE_BRICKS, ItemTags.STONE_BRICKS);
        this.copy(BlockTags.WOODEN_BUTTONS, ItemTags.WOODEN_BUTTONS);
        this.copy(BlockTags.BUTTONS, ItemTags.BUTTONS);
        //this.copy(BlockTags.WOOL_CARPETS, ItemTags.WOOL_CARPETS);
        this.copy(BlockTags.WOODEN_DOORS, ItemTags.WOODEN_DOORS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);
        this.copy(BlockTags.WOODEN_PRESSURE_PLATES, ItemTags.WOODEN_PRESSURE_PLATES);
        this.copy(BlockTags.DOORS, ItemTags.DOORS);
        this.copy(BlockTags.SAPLINGS, ItemTags.SAPLINGS);
        this.copy(BlockTagRegistry.STRIPPED_LOGS, STRIPPED_LOGS);
        //this.copy(BlockTags.SAND, ItemTags.SAND);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WALLS, ItemTags.WALLS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        //this.copy(BlockTags.ANVIL, ItemTags.ANVIL);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
        this.copy(BlockTags.WOODEN_TRAPDOORS, ItemTags.WOODEN_TRAPDOORS);
        this.copy(BlockTags.TRAPDOORS, ItemTags.TRAPDOORS);
        //this.copy(BlockTags.SMALL_FLOWERS, ItemTags.SMALL_FLOWERS);
        //this.copy(BlockTags.BEDS, ItemTags.BEDS);
        this.copy(BlockTags.FENCES, ItemTags.FENCES);
        //this.copy(BlockTags.TALL_FLOWERS, ItemTags.TALL_FLOWERS);
        //this.copy(BlockTags.FLOWERS, ItemTags.FLOWERS);
        //this.copy(BlockTags.GOLD_ORES, ItemTags.GOLD_ORES);
        //this.copy(BlockTags.SOUL_FIRE_BASE_BLOCKS, ItemTags.SOUL_FIRE_BASE_BLOCKS);
        this.copy(Tags.Blocks.ORES, Tags.Items.ORES);
        this.copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);

 

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
        tag(STAFF).add(SOUL_STAINED_STEEL_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get());

        tag(SOUL_HUNTER_WEAPON).add(SOUL_STAINED_STEEL_STAFF.get(), STAFF_OF_THE_AURIC_FLAME.get());
        tag(SOUL_HUNTER_WEAPON).add(TYRVING.get(), CRUDE_SCYTHE.get(), SOUL_STAINED_STEEL_SCYTHE.get(), CREATIVE_SCYTHE.get());
        tag(SOUL_HUNTER_WEAPON).add(SOUL_STAINED_STEEL_AXE.get(), SOUL_STAINED_STEEL_PICKAXE.get(), SOUL_STAINED_STEEL_SHOVEL.get(), SOUL_STAINED_STEEL_SWORD.get(), SOUL_STAINED_STEEL_HOE.get(), SOUL_STAINED_STEEL_KNIFE.get());

        tag(Tags.Items.NUGGETS).add(COPPER_NUGGET.get(), HALLOWED_GOLD_NUGGET.get(), SOUL_STAINED_STEEL_NUGGET.get());
        tag(Tags.Items.GEMS).add(NATURAL_QUARTZ.get(), BLAZING_QUARTZ.get(), CLUSTER_OF_BRILLIANCE.get());
        tag(Tags.Items.INGOTS).add(SOUL_STAINED_STEEL_INGOT.get(), HALLOWED_GOLD_INGOT.get());


        tag(KNIVES).add(SOUL_STAINED_STEEL_KNIFE.get());
        tag(KNIVES_FD).add(SOUL_STAINED_STEEL_KNIFE.get());

        tag(NUGGETS_COPPER).add(COPPER_NUGGET.get());
    }
}