package com.sammy.malum.data;

import com.sammy.malum.*;
import com.sammy.malum.common.item.impetus.*;
import com.sammy.malum.data.recipe.builder.vanilla.*;
import com.sammy.malum.registry.common.item.*;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.*;
import net.minecraft.data.*;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraftforge.common.*;
import net.minecraftforge.common.crafting.*;
import net.minecraftforge.common.crafting.conditions.*;
import net.minecraftforge.registries.*;
import team.lodestar.lodestone.data.builder.*;
import team.lodestar.lodestone.systems.recipe.*;

import java.util.function.*;

import static com.sammy.malum.MalumMod.*;
import static com.sammy.malum.data.recipe.builder.vanilla.MetalNodeCookingRecipeBuilder.*;
import static com.sammy.malum.data.recipe.builder.vanilla.StackedMalumCookingRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapedRecipeBuilder.*;
import static net.minecraft.data.recipes.ShapelessRecipeBuilder.*;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.smoking;
import static net.minecraft.data.recipes.SimpleCookingRecipeBuilder.*;
import static net.minecraft.data.recipes.SingleItemRecipeBuilder.*;
import static team.lodestar.lodestone.setup.LodestoneItemTags.*;

public class MalumRecipes extends RecipeProvider implements IConditionBuilder {
    public MalumRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        //KEY ITEMS
        shapeless(ItemRegistry.ENCYCLOPEDIA_ARCANA.get()).requires(Items.BOOK).requires(ItemRegistry.PROCESSED_SOULSTONE.get()).unlockedBy("has_soulstone", has(ItemRegistry.PROCESSED_SOULSTONE.get())).save(consumer);
        shaped(ItemRegistry.CRUDE_SCYTHE.get()).define('#', Tags.Items.RODS_WOODEN).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).define('X', Tags.Items.INGOTS_IRON).pattern("XXY").pattern(" #X").pattern("#  ").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer);
        shaped(ItemRegistry.SPIRIT_ALTAR.get()).define('Z', Tags.Items.INGOTS_GOLD).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern(" Y ").pattern("ZXZ").pattern("XXX").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer);
        shaped(ItemRegistry.SPIRIT_JAR.get()).define('Z', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('Y', Tags.Items.GLASS_PANES).pattern("YZY").pattern("Y Y").pattern("YYY").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(ItemRegistry.SPIRIT_POUCH.get()).define('X', Tags.Items.STRING).define('Y', ItemRegistry.SPIRIT_FABRIC.get()).define('Z', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern(" X ").pattern("YZY").pattern(" Y ").unlockedBy("has_spirit_fabric", has(ItemRegistry.SPIRIT_FABRIC.get())).save(consumer);
        shaped(ItemRegistry.WEAVERS_WORKBENCH.get()).define('Z', Tags.Items.INGOTS_GOLD).define('Y', ItemRegistry.HEX_ASH.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("XYX").pattern("XZX").unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer);

        //CRAFTING COMPONENTS
        shaped(ItemRegistry.SPECTRAL_LENS.get()).define('X', ItemRegistry.HEX_ASH.get()).define('Y', Tags.Items.GLASS_PANES).pattern(" X ").pattern("XYX").pattern(" X ").unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer);
        shaped(ItemRegistry.SPECTRAL_OPTIC.get(), 2).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', ItemRegistry.SPECTRAL_LENS.get()).pattern(" X ").pattern("#Y#").pattern(" X ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);

        //SPIRIT METALS
        shaped(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer, malumPath("soul_stained_steel_from_nuggets"));
        shapeless(ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get(), 9).requires(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shapeless(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 9).requires(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer, malumPath("soul_stained_steel_from_block"));

        shaped(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('#', ItemRegistry.HALLOWED_GOLD_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer, malumPath("hallowed_gold_from_nuggets"));
        shapeless(ItemRegistry.HALLOWED_GOLD_NUGGET.get(), 9).requires(ItemRegistry.HALLOWED_GOLD_INGOT.get()).unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shapeless(ItemRegistry.HALLOWED_GOLD_INGOT.get(), 9).requires(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer, malumPath("hallowed_gold_from_block"));

        //NODES
        smeltingWithCount(Ingredient.of(ItemRegistry.IRON_NODE.get()), Items.IRON_NUGGET, 6, 0.25f, 200).unlockedBy("has_impetus", has(ItemRegistry.IRON_IMPETUS.get())).save(consumer, malumPath("iron_from_node_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.IRON_NODE.get()), Items.IRON_NUGGET, 6, 0.25f, 100).unlockedBy("has_impetus", has(ItemRegistry.IRON_IMPETUS.get())).save(consumer, malumPath("iron_from_node_blasting"));

        smeltingWithCount(Ingredient.of(ItemRegistry.GOLD_NODE.get()), Items.GOLD_NUGGET, 6, 0.25f, 200).unlockedBy("has_impetus", has(ItemRegistry.GOLD_IMPETUS.get())).save(consumer, malumPath("gold_from_node_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.GOLD_NODE.get()), Items.GOLD_NUGGET, 6, 0.25f, 100).unlockedBy("has_impetus", has(ItemRegistry.GOLD_IMPETUS.get())).save(consumer, malumPath("gold_from_node_blasting"));

        smeltingWithCount(Ingredient.of(ItemRegistry.COPPER_NODE.get()), ItemRegistry.COPPER_NUGGET.get(), 6, 0.25f, 200).unlockedBy("has_impetus", has(ItemRegistry.COPPER_IMPETUS.get())).save(consumer, malumPath("copper_from_node_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.COPPER_NODE.get()), ItemRegistry.COPPER_NUGGET.get(), 6, 0.25f, 100).unlockedBy("has_impetus", has(ItemRegistry.COPPER_IMPETUS.get())).save(consumer, malumPath("copper_from_node_blasting"));

        nodeSmelting(consumer, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, NUGGETS_LEAD);
        nodeSmelting(consumer, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, NUGGETS_SILVER);
        nodeSmelting(consumer, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, NUGGETS_ALUMINUM);
        nodeSmelting(consumer, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, NUGGETS_NICKEL);
        nodeSmelting(consumer, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, NUGGETS_URANIUM);
        nodeSmelting(consumer, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, NUGGETS_OSMIUM);
        nodeSmelting(consumer, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, NUGGETS_ZINC);
        nodeSmelting(consumer, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, NUGGETS_TIN);
        //TOOLS
        shaped(ItemRegistry.SOUL_STAINED_STEEL_HOE.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.SOUL_STAINED_STEEL_AXE.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XX ").pattern("X# ").pattern(" # ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("X").pattern("#").pattern("#").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get()).define('#', Tags.Items.RODS_WOODEN).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("X").pattern("X").pattern("#").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        //TRINKETS
        shaped(ItemRegistry.GILDED_BELT.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', Tags.Items.LEATHER).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).pattern("XXX").pattern("#Y#").pattern(" # ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(ItemRegistry.GILDED_RING.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', Tags.Items.LEATHER).pattern(" X#").pattern("X X").pattern(" X ").unlockedBy("has_hallowed_gold", has(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(ItemRegistry.ORNATE_NECKLACE.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', Tags.Items.STRING).pattern(" X ").pattern("X X").pattern(" # ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.ORNATE_RING.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', Tags.Items.LEATHER).pattern(" X#").pattern("X X").pattern(" X ").unlockedBy("has_soul_stained_steel", has(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        //FRAGMENTS
        shapeless(ItemRegistry.COAL_FRAGMENT.get(), 8).requires(Items.COAL).unlockedBy("has_coal", has(Items.COAL)).save(consumer, malumPath("coal_fragment"));
        shapeless(Items.COAL, 1).requires(ItemRegistry.COAL_FRAGMENT.get(), 8).unlockedBy("has_coal", has(Items.COAL)).save(consumer, malumPath("coal_from_fragment"));
        shapeless(ItemRegistry.CHARCOAL_FRAGMENT.get(), 8).requires(Items.CHARCOAL).unlockedBy("has_charcoal", has(Items.CHARCOAL)).save(consumer, malumPath("charcoal_fragment"));
        shapeless(Items.CHARCOAL, 1).requires(ItemRegistry.CHARCOAL_FRAGMENT.get(), 8).unlockedBy("has_charcoal", has(Items.CHARCOAL)).save(consumer, malumPath("charcoal_from_fragment"));
        shapeless(ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(), 8).requires(ItemRegistry.BLAZING_QUARTZ.get()).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_fragment"));
        shapeless(ItemRegistry.BLAZING_QUARTZ.get(), 1).requires(ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(), 8).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_fragment"));
        shapeless(ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(), 8).requires(ItemRegistry.ARCANE_CHARCOAL.get()).unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("arcane_charcoal_fragment"));
        shapeless(ItemRegistry.ARCANE_CHARCOAL.get(), 1).requires(ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(), 8).unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("arcane_charcoal_from_fragment"));

        //COPPER
        shaped(Items.COPPER_INGOT).define('#', NUGGETS_COPPER).pattern("###").pattern("###").pattern("###").unlockedBy("has_copper", has(INGOTS_COPPER)).save(consumer, malumPath("copper_ingot_from_nugget"));
        shapeless(ItemRegistry.COPPER_NUGGET.get(), 9).requires(INGOTS_COPPER).unlockedBy("has_copper", has(INGOTS_COPPER)).save(consumer, malumPath("copper_nugget_from_ingot"));

        //ORE SMELTING
        smelting(Ingredient.of(ItemRegistry.BLAZING_QUARTZ_ORE.get()), ItemRegistry.BLAZING_QUARTZ.get(), 0.25f, 200).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_smelting"));
        blasting(Ingredient.of(ItemRegistry.BLAZING_QUARTZ_ORE.get()), ItemRegistry.BLAZING_QUARTZ.get(), 0.25f, 100).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_blasting"));
        smelting(Ingredient.of(ItemRegistry.NATURAL_QUARTZ_ORE.get()), ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 200).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_smelting"));
        blasting(Ingredient.of(ItemRegistry.NATURAL_QUARTZ_ORE.get()), ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 100).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_blasting"));
        smelting(Ingredient.of(ItemRegistry.DEEPSLATE_QUARTZ_ORE.get()), ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 200).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_deepslate_smelting"));
        blasting(Ingredient.of(ItemRegistry.DEEPSLATE_QUARTZ_ORE.get()), ItemRegistry.NATURAL_QUARTZ.get(), 0.25f, 100).unlockedBy("has_natural_quartz", has(ItemRegistry.NATURAL_QUARTZ.get())).save(consumer, malumPath("natural_quartz_from_deepslate_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_STONE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_STONE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_DEEPSLATE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_deepslate_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.BRILLIANT_DEEPSLATE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_deepslate_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.DEEPSLATE_SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_deepslate_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.DEEPSLATE_SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_deepslate_blasting"));
        smelting(Ingredient.of(ItemRegistry.BLOCK_OF_CTHONIC_GOLD.get()), ItemRegistry.CTHONIC_GOLD.get(), 2.0f, 200).unlockedBy("has_cthonic_gold", has(ItemRegistry.CTHONIC_GOLD.get())).save(consumer, malumPath("cthonic_gold_from_smelting"));
        blasting(Ingredient.of(ItemRegistry.BLOCK_OF_CTHONIC_GOLD.get()), ItemRegistry.CTHONIC_GOLD.get(), 2.0f, 100).unlockedBy("has_cthonic_gold", has(ItemRegistry.CTHONIC_GOLD.get())).save(consumer, malumPath("cthonic_gold_from_blasting"));

        smeltingWithCount(Ingredient.of(ItemRegistry.CLUSTER_OF_BRILLIANCE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_raw_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.CLUSTER_OF_BRILLIANCE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_raw_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.CRUSHED_BRILLIANCE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 200).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_crushed_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.CRUSHED_BRILLIANCE.get()), ItemRegistry.CHUNK_OF_BRILLIANCE.get(), 2, 1, 100).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_crushed_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.RAW_SOULSTONE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_raw_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.RAW_SOULSTONE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_raw_blasting"));
        smeltingWithCount(Ingredient.of(ItemRegistry.CRUSHED_SOULSTONE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 200).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_crushed_smelting"));
        blastingWithCount(Ingredient.of(ItemRegistry.CRUSHED_SOULSTONE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(), 2, 0.25f, 100).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_crushed_blasting"));

        //RAW ORE BLOCKS
        shaped(ItemRegistry.BLOCK_OF_RAW_SOULSTONE.get()).define('#', ItemRegistry.RAW_SOULSTONE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("raw_soulstone_block"));
        shapeless(ItemRegistry.RAW_SOULSTONE.get(), 9).requires(ItemRegistry.BLOCK_OF_RAW_SOULSTONE.get()).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("raw_soulstone_from_block"));

        //ORE BLOCKS
        shaped(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).define('#', ItemRegistry.BLAZING_QUARTZ.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("block_of_blazing_quartz"));
        shapeless(ItemRegistry.BLAZING_QUARTZ.get(), 9).requires(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_quartz_from_block"));
        shaped(ItemRegistry.BLOCK_OF_ARCANE_CHARCOAL.get()).define('#', ItemRegistry.ARCANE_CHARCOAL.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("block_of_arcane_charcoal"));
        shapeless(ItemRegistry.ARCANE_CHARCOAL.get(), 9).requires(ItemRegistry.BLOCK_OF_ARCANE_CHARCOAL.get()).unlockedBy("has_arcane_charcoal", has(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, malumPath("arcane_charcoal_from_block"));
        shaped(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).define('#', ItemRegistry.CLUSTER_OF_BRILLIANCE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("block_of_brilliance"));
        shapeless(ItemRegistry.CLUSTER_OF_BRILLIANCE.get(), 9).requires(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).unlockedBy("has_brilliance", has(ItemRegistry.CLUSTER_OF_BRILLIANCE.get())).save(consumer, malumPath("brilliance_from_block"));
        shaped(ItemRegistry.BLOCK_OF_SOULSTONE.get()).define('#', ItemRegistry.PROCESSED_SOULSTONE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("block_of_soulstone"));
        shapeless(ItemRegistry.PROCESSED_SOULSTONE.get(), 9).requires(ItemRegistry.BLOCK_OF_SOULSTONE.get()).unlockedBy("has_soulstone", has(ItemRegistry.RAW_SOULSTONE.get())).save(consumer, malumPath("soulstone_from_block"));

        //COMPACT BLOCKS
        shaped(ItemRegistry.BLOCK_OF_ROTTING_ESSENCE.get()).define('#', ItemRegistry.ROTTING_ESSENCE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_rotting_essence", has(ItemRegistry.ROTTING_ESSENCE.get())).save(consumer, malumPath("block_of_rotting_essence"));
        shapeless(ItemRegistry.ROTTING_ESSENCE.get(), 9).requires(ItemRegistry.BLOCK_OF_ROTTING_ESSENCE.get()).unlockedBy("has_rotting_essence", has(ItemRegistry.ROTTING_ESSENCE.get())).save(consumer, malumPath("rotting_essence_from_block"));
        shaped(ItemRegistry.BLOCK_OF_GRIM_TALC.get()).define('#', ItemRegistry.GRIM_TALC.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("block_of_grim_talc"));
        shapeless(ItemRegistry.GRIM_TALC.get(), 9).requires(ItemRegistry.BLOCK_OF_GRIM_TALC.get()).unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("grim_talc_from_block"));
        shaped(ItemRegistry.BLOCK_OF_ALCHEMICAL_CALX.get()).define('#', ItemRegistry.ALCHEMICAL_CALX.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_alchemical_calx", has(ItemRegistry.ALCHEMICAL_CALX.get())).save(consumer, malumPath("block_of_alchemical_calx"));
        shapeless(ItemRegistry.ALCHEMICAL_CALX.get(), 9).requires(ItemRegistry.BLOCK_OF_ALCHEMICAL_CALX.get()).unlockedBy("has_alchemical_calx", has(ItemRegistry.ALCHEMICAL_CALX.get())).save(consumer, malumPath("alchemical_calx_from_block"));
        shaped(ItemRegistry.BLOCK_OF_ASTRAL_WEAVE.get()).define('#', ItemRegistry.ASTRAL_WEAVE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_astral_weave", has(ItemRegistry.ASTRAL_WEAVE.get())).save(consumer, malumPath("block_of_astral_weave"));
        shapeless(ItemRegistry.ASTRAL_WEAVE.get(), 9).requires(ItemRegistry.BLOCK_OF_ASTRAL_WEAVE.get()).unlockedBy("has_astral_weave", has(ItemRegistry.ASTRAL_WEAVE.get())).save(consumer, malumPath("astral_weave_from_block"));
        shaped(ItemRegistry.BLOCK_OF_HEX_ASH.get()).define('#', ItemRegistry.HEX_ASH.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer, malumPath("block_of_hex_ash"));
        shapeless(ItemRegistry.HEX_ASH.get(), 9).requires(ItemRegistry.BLOCK_OF_HEX_ASH.get()).unlockedBy("has_hex_ash", has(ItemRegistry.HEX_ASH.get())).save(consumer, malumPath("hex_ash_from_block"));
        shaped(ItemRegistry.BLOCK_OF_CURSED_GRIT.get()).define('#', ItemRegistry.CURSED_GRIT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_cursed_grit", has(ItemRegistry.CURSED_GRIT.get())).save(consumer, malumPath("block_of_cursed_grit"));
        shapeless(ItemRegistry.CURSED_GRIT.get(), 9).requires(ItemRegistry.BLOCK_OF_CURSED_GRIT.get()).unlockedBy("has_cursed_grit", has(ItemRegistry.CURSED_GRIT.get())).save(consumer, malumPath("cursed_grit_from_block"));
        shaped(ItemRegistry.MASS_OF_BLIGHTED_GUNK.get()).define('#', ItemRegistry.BLIGHTED_GUNK.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_blighted_gunk", has(ItemRegistry.BLIGHTED_GUNK.get())).save(consumer, malumPath("bligh"));
        shapeless(ItemRegistry.BLIGHTED_GUNK.get(), 9).requires(ItemRegistry.MASS_OF_BLIGHTED_GUNK.get()).unlockedBy("has_blighted_gunk", has(ItemRegistry.BLIGHTED_GUNK.get())).save(consumer, malumPath("blighted_gunk_from_mass"));

        //MISC
        shaped(Items.NETHERRACK, 2).define('Z', ItemRegistry.BLAZING_QUARTZ.get()).define('Y', Tags.Items.COBBLESTONE).pattern("ZY").pattern("YZ").unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("netherrack_from_blazing_quartz"));
        shapeless(Items.EXPERIENCE_BOTTLE).requires(ItemRegistry.CHUNK_OF_BRILLIANCE.get()).requires(Items.GLASS_BOTTLE).unlockedBy("has_brilliance", has(ItemRegistry.CHUNK_OF_BRILLIANCE.get())).save(consumer, malumPath("experience_bottle_from_brilliance"));

        shapeless(Items.BONE_MEAL, 6).requires(ItemRegistry.GRIM_TALC.get()).unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("bonemeal_from_grim_talc"));
        shaped(Items.SKELETON_SKULL).define('#', ItemRegistry.GRIM_TALC.get()).define('&', Tags.Items.BONES).pattern("&&&").pattern("&#&").pattern("&&&").unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("skeleton_skull_from_grim_talc"));
        shaped(Items.ZOMBIE_HEAD).define('#', ItemRegistry.GRIM_TALC.get()).define('&', Items.ROTTEN_FLESH).pattern("&&&").pattern("&#&").pattern("&&&").unlockedBy("has_grim_talc", has(ItemRegistry.GRIM_TALC.get())).save(consumer, malumPath("zombie_head_from_grim_talc"));

        shaped(Items.TORCH, 6).define('#', ItemRegistry.BLAZING_QUARTZ.get()).define('&', Items.STICK).pattern("#").pattern("&").unlockedBy("has_blazing_quartz", has(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, malumPath("blazing_torch"));

        //SAP & ARCANE CHARCOAL
        smelting(Ingredient.of(ItemTagRegistry.RUNEWOOD_LOGS), ItemRegistry.ARCANE_CHARCOAL.get(), 0.25f, 200).unlockedBy("has_runewood_planks", has(ItemTagRegistry.RUNEWOOD_LOGS)).save(consumer, malumPath("arcane_charcoal_from_runewood"));
        shapeless(ItemRegistry.HOLY_SAPBALL.get(), 3).requires(ItemRegistry.HOLY_SAP.get()).requires(Items.SLIME_BALL).unlockedBy("has_holy_sap", has(ItemRegistry.HOLY_SAP.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.HOLY_SAP.get()), ItemRegistry.HOLY_SYRUP.get(), 0.1f, 200).unlockedBy("has_holy_sap", has(ItemRegistry.HOLY_SAP.get())).save(consumer, malumPath("holy_sap_from_smelting"));
        smoking(Ingredient.of(ItemRegistry.HOLY_SAP.get()), ItemRegistry.HOLY_SYRUP.get(), 0.1f, 100).unlockedBy("has_holy_sap", has(ItemRegistry.HOLY_SAP.get())).save(consumer, malumPath("holy_sap_from_smoking"));

        smelting(Ingredient.of(ItemTagRegistry.SOULWOOD_LOGS), ItemRegistry.ARCANE_CHARCOAL.get(), 0.25f, 200).unlockedBy("has_soulwood_planks", has(ItemTagRegistry.SOULWOOD_LOGS)).save(consumer, malumPath("arcane_charcoal_from_soulwood"));
        shapeless(ItemRegistry.UNHOLY_SAPBALL.get(), 3).requires(ItemRegistry.UNHOLY_SAP.get()).requires(Items.SLIME_BALL).unlockedBy("has_unholy_sap", has(ItemRegistry.UNHOLY_SAP.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.UNHOLY_SAP.get()), ItemRegistry.UNHOLY_SYRUP.get(), 0.1f, 200).unlockedBy("has_unholy_sap", has(ItemRegistry.UNHOLY_SAP.get())).save(consumer, malumPath("unholy_sap_from_smelting"));
        smoking(Ingredient.of(ItemRegistry.UNHOLY_SAP.get()), ItemRegistry.UNHOLY_SYRUP.get(), 0.1f, 100).unlockedBy("has_unholy_sap", has(ItemRegistry.UNHOLY_SAP.get())).save(consumer, malumPath("unholy_sap_from_smoking"));

        shapeless(Items.MAGMA_CREAM).requires(Items.BLAZE_POWDER).requires(ItemTagRegistry.SAPBALLS).unlockedBy("has_sapball", has(ItemTagRegistry.SAPBALLS)).save(consumer, malumPath("magma_cream_from_sapballs"));
        shaped(Blocks.STICKY_PISTON).define('P', Blocks.PISTON).define('S', ItemTagRegistry.SAPBALLS).pattern("S").pattern("P").unlockedBy("has_sapball", has(ItemTagRegistry.SAPBALLS)).save(consumer, malumPath("sticky_piston_from_sapballs"));
        shaped(Items.LEAD, 2).define('~', Tags.Items.STRING).define('O', ItemTagRegistry.SAPBALLS).pattern("~~ ").pattern("~O ").pattern("  ~").unlockedBy("has_sapball", has(ItemTagRegistry.SAPBALLS)).save(consumer, malumPath("lead_from_sapballs"));

        //RUNEWOOD BLOCKS
        shapelessPlanks(consumer, ItemRegistry.RUNEWOOD_PLANKS.get(), ItemTagRegistry.RUNEWOOD_LOGS);
        shapelessWood(consumer, ItemRegistry.RUNEWOOD.get(), ItemRegistry.RUNEWOOD_LOG.get());
        shapelessWood(consumer, ItemRegistry.STRIPPED_RUNEWOOD.get(), ItemRegistry.STRIPPED_RUNEWOOD_LOG.get());
        shapelessButton(consumer, ItemRegistry.RUNEWOOD_PLANKS_BUTTON.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedDoor(consumer, ItemRegistry.RUNEWOOD_DOOR.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedFence(consumer, ItemRegistry.RUNEWOOD_PLANKS_FENCE.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedFenceGate(consumer, ItemRegistry.RUNEWOOD_PLANKS_FENCE_GATE.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedPressurePlate(consumer, ItemRegistry.RUNEWOOD_PLANKS_PRESSURE_PLATE.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapedTrapdoor(consumer, ItemRegistry.RUNEWOOD_TRAPDOOR.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shapelessSolidTrapdoor(consumer, ItemRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), ItemRegistry.RUNEWOOD_TRAPDOOR.get());
        shapelessSolidTrapdoor(consumer, ItemRegistry.RUNEWOOD_TRAPDOOR.get(), ItemRegistry.SOLID_RUNEWOOD_TRAPDOOR.get(), "runewood_trapdoor_from_solid");
        shapedSign(consumer, ItemRegistry.RUNEWOOD_SIGN.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shaped(ItemRegistry.RUNEWOOD_BOAT.get()).define('#', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("# #").pattern("###").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get(), 3).define('#', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("#").pattern("#").pattern("#").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get());

        shaped(ItemRegistry.RUNEWOOD_PANEL.get(), 4).define('#', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_PANEL_SLAB.get(), ItemRegistry.RUNEWOOD_PANEL.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_PANEL_STAIRS.get(), ItemRegistry.RUNEWOOD_PANEL.get());

        shaped(ItemRegistry.RUNEWOOD_TILES.get(), 4).define('#', ItemRegistry.RUNEWOOD_PANEL.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_TILES_SLAB.get(), ItemRegistry.RUNEWOOD_TILES.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_TILES_STAIRS.get(), ItemRegistry.RUNEWOOD_TILES.get());

        shaped(ItemRegistry.CUT_RUNEWOOD_PLANKS.get(), 2).define('#', ItemRegistry.RUNEWOOD_PANEL.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("#").pattern("X").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.RUNEWOOD_BEAM.get(), 3).define('#', ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get()).pattern("#").pattern("#").pattern("#").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.RUNEWOOD_ITEM_STAND.get(), 2).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', ItemRegistry.RUNEWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', ItemRegistry.RUNEWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_runewood_planks", has(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        //SOULWOOD BLOCKS
        shapelessPlanks(consumer, ItemRegistry.SOULWOOD_PLANKS.get(), ItemTagRegistry.SOULWOOD_LOGS);
        shapelessWood(consumer, ItemRegistry.SOULWOOD.get(), ItemRegistry.SOULWOOD_LOG.get());
        shapelessWood(consumer, ItemRegistry.STRIPPED_SOULWOOD.get(), ItemRegistry.STRIPPED_SOULWOOD_LOG.get());
        shapelessButton(consumer, ItemRegistry.SOULWOOD_PLANKS_BUTTON.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedDoor(consumer, ItemRegistry.SOULWOOD_DOOR.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedFence(consumer, ItemRegistry.SOULWOOD_PLANKS_FENCE.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedFenceGate(consumer, ItemRegistry.SOULWOOD_PLANKS_FENCE_GATE.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedPressurePlate(consumer, ItemRegistry.SOULWOOD_PLANKS_PRESSURE_PLATE.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedSlab(consumer, ItemRegistry.SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapedTrapdoor(consumer, ItemRegistry.SOULWOOD_TRAPDOOR.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shapelessSolidTrapdoor(consumer, ItemRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), ItemRegistry.SOULWOOD_TRAPDOOR.get());
        shapelessSolidTrapdoor(consumer, ItemRegistry.SOULWOOD_TRAPDOOR.get(), ItemRegistry.SOLID_SOULWOOD_TRAPDOOR.get(), "soulwood_trapdoor_from_solid");
        shapedSign(consumer, ItemRegistry.SOULWOOD_SIGN.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shaped(ItemRegistry.SOULWOOD_BOAT.get()).define('#', ItemRegistry.SOULWOOD_PLANKS.get()).pattern("# #").pattern("###").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get(), 3).define('#', ItemRegistry.SOULWOOD_PLANKS.get()).pattern("#").pattern("#").pattern("#").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get());

        shaped(ItemRegistry.SOULWOOD_PANEL.get(), 4).define('#', ItemRegistry.SOULWOOD_PLANKS.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.SOULWOOD_PANEL_SLAB.get(), ItemRegistry.SOULWOOD_PANEL.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_PANEL_STAIRS.get(), ItemRegistry.SOULWOOD_PANEL.get());

        shaped(ItemRegistry.SOULWOOD_TILES.get(), 4).define('#', ItemRegistry.SOULWOOD_PANEL.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.SOULWOOD_TILES_SLAB.get(), ItemRegistry.SOULWOOD_TILES.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_TILES_STAIRS.get(), ItemRegistry.SOULWOOD_TILES.get());

        shaped(ItemRegistry.CUT_SOULWOOD_PLANKS.get(), 2).define('#', ItemRegistry.SOULWOOD_PANEL.get()).define('X', ItemRegistry.SOULWOOD_PLANKS.get()).pattern("#").pattern("X").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.SOULWOOD_BEAM.get(), 3).define('#', ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get()).pattern("#").pattern("#").pattern("#").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.SOULWOOD_ITEM_STAND.get(), 2).define('X', ItemRegistry.SOULWOOD_PLANKS.get()).define('Y', ItemRegistry.SOULWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.SOULWOOD_ITEM_PEDESTAL.get()).define('X', ItemRegistry.SOULWOOD_PLANKS.get()).define('Y', ItemRegistry.SOULWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_soulwood_planks", has(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);

        //TAINTED ROCK
        shapedPressurePlate(consumer, ItemRegistry.TAINTED_ROCK_PRESSURE_PLATE.get(), ItemRegistry.TAINTED_ROCK.get());
        shapelessButton(consumer, ItemRegistry.TAINTED_ROCK_BUTTON.get(), ItemRegistry.TAINTED_ROCK.get());

        shaped(ItemRegistry.TAINTED_ROCK_WALL.get(), 6).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_SLAB.get(), 2).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_STAIRS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_WALL.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_wall_stonecutting"));

        shaped(ItemRegistry.POLISHED_TAINTED_ROCK.get(), 4).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), 2).unlockedBy("has_polished_tainted_rock", has(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, malumPath("polished_tainted_rock_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get()).unlockedBy("has_polished_tainted_rock", has(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, malumPath("polished_tainted_rock_stairs_stonecutting"));

        smelting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK.get(), 0.1f, 200).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), 2).unlockedBy("has_smooth_tainted_rock", has(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, malumPath("smooth_tainted_rock_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get()).unlockedBy("has_smooth_tainted_rock", has(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, malumPath("smooth_tainted_rock_stairs_stonecutting"));

        shaped(ItemRegistry.TAINTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_bricks_from_small_bricks"));
        shaped(ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_bricks_stonecutting_from_polished"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_bricks_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_brick_wall_stonecutting"));

        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get(), 0.1f, 200).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_cracked_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_cracked_tainted_rock_bricks", has(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get())).save(consumer, malumPath("cracked_tainted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_cracked_tainted_rock_bricks", has(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get())).save(consumer, malumPath("cracked_tainted_rock_bricks_stairs_stonecutting"));

        shaped(ItemRegistry.TAINTED_ROCK_TILES.get(), 4).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_tiles_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_tiles_stonecutting_from_polished"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_tiles_stonecutting_from_bricks"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), 2).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_tiles_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_tiles_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_WALL.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_tiles_wall_stonecutting"));

        smelting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get(), 0.1f, 200).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_cracked_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_tainted_rock_tiles_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 2).unlockedBy("has_cracked_tainted_rock_tiles", has(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get())).save(consumer, malumPath("cracked_tainted_rock_tiles_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get()).unlockedBy("has_cracked_tainted_rock_tiles", has(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get())).save(consumer, malumPath("cracked_tainted_rock_tiles_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_tainted_rock_tiles_wall_stonecutting"));

        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_stonecutting_alt"));

        stonecutting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_wall_stonecutting"));

        smelting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(), 0.1f, 200).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_small_tainted_rock_bricks_smelting"));
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_small_tainted_rock_bricks_stonecutting_alt"));

        stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_small_tainted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_small_tainted_rock_bricks_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cracked_small_tainted_rock_bricks_wall_stonecutting"));

        shaped(ItemRegistry.CHISELED_TAINTED_ROCK.get()).define('#', ItemRegistry.TAINTED_ROCK_SLAB.get()).pattern("#").pattern("#").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_COLUMN.get(), 2).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("#").pattern("#").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shapeless(ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).requires(ItemRegistry.TAINTED_ROCK_COLUMN.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shapeless(ItemRegistry.TAINTED_ROCK_COLUMN.get()).requires(ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_column_from_cap"));

        shaped(ItemRegistry.CUT_TAINTED_ROCK.get(), 4).define('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.CUT_TAINTED_ROCK.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("cut_tainted_rock_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_COLUMN.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_column_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_column_cap_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("tainted_rock_bricks_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("smooth_tainted_rock_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("polished_tainted_rock_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.CHISELED_TAINTED_ROCK.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("chiseled_tainted_rock_bricks_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CHISELED_TAINTED_ROCK.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("chiseled_tainted_rock_bricks_stonecutting_alt"));

        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_stonecutting_from_bricks"));
        stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer, malumPath("small_tainted_rock_bricks_stonecutting_from_tiles"));

        shaped(ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(), 2).define('X', ItemRegistry.TAINTED_ROCK.get()).define('Y', ItemRegistry.TAINTED_ROCK_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get()).define('X', ItemRegistry.TAINTED_ROCK.get()).define('Y', ItemRegistry.TAINTED_ROCK_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_tainted_rock", has(ItemRegistry.TAINTED_ROCK.get())).save(consumer);

        //TWISTED ROCK
        shapedPressurePlate(consumer, ItemRegistry.TWISTED_ROCK_PRESSURE_PLATE.get(), ItemRegistry.TWISTED_ROCK.get());
        shapelessButton(consumer, ItemRegistry.TWISTED_ROCK_BUTTON.get(), ItemRegistry.TWISTED_ROCK.get());

        shaped(ItemRegistry.TWISTED_ROCK_WALL.get(), 6).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_SLAB.get(), 2).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_STAIRS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_WALL.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_wall_stonecutting"));

        shaped(ItemRegistry.POLISHED_TWISTED_ROCK.get(), 4).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), 2).unlockedBy("has_polished_twisted_rock", has(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, malumPath("polished_twisted_rock_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get()).unlockedBy("has_polished_twisted_rock", has(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, malumPath("polished_twisted_rock_stairs_stonecutting"));

        smelting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK.get(), 0.1f, 200).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), 2).unlockedBy("has_smooth_twisted_rock", has(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, malumPath("smooth_twisted_rock_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get()).unlockedBy("has_smooth_twisted_rock", has(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, malumPath("smooth_twisted_rock_stairs_stonecutting"));

        shaped(ItemRegistry.TWISTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_bricks_from_small_bricks"));
        shaped(ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_bricks_stonecutting_from_polished"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_bricks_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_brick_wall_stonecutting"));

        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get(), 0.1f, 200).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_cracked_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_cracked_twisted_rock_bricks", has(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get())).save(consumer, malumPath("cracked_twisted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_cracked_twisted_rock_bricks", has(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get())).save(consumer, malumPath("cracked_twisted_rock_bricks_stairs_stonecutting"));

        shaped(ItemRegistry.TWISTED_ROCK_TILES.get(), 4).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_tiles_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_tiles_stonecutting_from_polished"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_tiles_stonecutting_from_bricks"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), 2).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_tiles_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_tiles_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_WALL.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_tiles_wall_stonecutting"));

        smelting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get(), 0.1f, 200).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_cracked_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_twisted_rock_tiles_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), 2).unlockedBy("has_cracked_twisted_rock_tiles", has(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get())).save(consumer, malumPath("cracked_twisted_rock_tiles_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get()).unlockedBy("has_cracked_twisted_rock_tiles", has(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get())).save(consumer, malumPath("cracked_twisted_rock_tiles_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_twisted_rock_tiles_wall_stonecutting"));

        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_stonecutting_alt"));

        stonecutting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_wall_stonecutting"));

        smelting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(), 0.1f, 200).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_small_twisted_rock_bricks_smelting"));
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(), 4).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_small_twisted_rock_bricks_stonecutting_alt"));

        stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_small_twisted_rock_bricks_slab_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_small_twisted_rock_bricks_stairs_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cracked_small_twisted_rock_bricks_wall_stonecutting"));

        shaped(ItemRegistry.CHISELED_TWISTED_ROCK.get()).define('#', ItemRegistry.TWISTED_ROCK_SLAB.get()).pattern("#").pattern("#").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_COLUMN.get(), 2).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("#").pattern("#").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shapeless(ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).requires(ItemRegistry.TWISTED_ROCK_COLUMN.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shapeless(ItemRegistry.TWISTED_ROCK_COLUMN.get()).requires(ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_column_from_cap"));

        shaped(ItemRegistry.CUT_TWISTED_ROCK.get(), 4).define('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.CUT_TWISTED_ROCK.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("cut_twisted_rock_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_COLUMN.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_column_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_column_cap_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("twisted_rock_bricks_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("smooth_twisted_rock_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("polished_twisted_rock_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.CHISELED_TWISTED_ROCK.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("chiseled_twisted_rock_bricks_stonecutting"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CHISELED_TWISTED_ROCK.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("chiseled_twisted_rock_bricks_stonecutting_alt"));

        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_stonecutting_from_bricks"));
        stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer, malumPath("small_twisted_rock_bricks_stonecutting_from_tiles"));

        shaped(ItemRegistry.TWISTED_ROCK_ITEM_STAND.get(), 2).define('X', ItemRegistry.TWISTED_ROCK.get()).define('Y', ItemRegistry.TWISTED_ROCK_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get()).define('X', ItemRegistry.TWISTED_ROCK.get()).define('Y', ItemRegistry.TWISTED_ROCK_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_twisted_rock", has(ItemRegistry.TWISTED_ROCK.get())).save(consumer);

        //ETHER
        etherTorch(consumer, ItemRegistry.ETHER_TORCH.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherTorch(consumer, ItemRegistry.IRIDESCENT_ETHER_TORCH.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());

        //THE DEVICE
        TheDeviceRecipeBuilder.shaped(ItemRegistry.THE_DEVICE.get()).define('X', ItemRegistry.TWISTED_ROCK.get()).define('Y', ItemRegistry.TAINTED_ROCK.get()).pattern("XYX").pattern("YXY").pattern("XYX").unlockedBy("has_bedrock", has(Items.BEDROCK)).save(consumer);

        weaveRecipe(consumer, ItemRegistry.BLIGHTED_GUNK.get(), ItemRegistry.ANCIENT_WEAVE);
        weaveRecipe(consumer, Items.IRON_INGOT, ItemRegistry.CORNERED_WEAVE);
        weaveRecipe(consumer, Items.COPPER_INGOT, ItemRegistry.DREADED_WEAVE);
        weaveRecipe(consumer, Items.LAPIS_LAZULI, ItemRegistry.MECHANICAL_WEAVE_V1);
        weaveRecipe(consumer, Items.REDSTONE, ItemRegistry.MECHANICAL_WEAVE_V2);

        weaveRecipe(consumer, Items.BREAD, ItemRegistry.ACE_PRIDEWEAVE);
        weaveRecipe(consumer, Items.BOOK, ItemRegistry.AGENDER_PRIDEWEAVE);
        weaveRecipe(consumer, Items.ARROW, ItemRegistry.ARO_PRIDEWEAVE);
        weaveRecipe(consumer, Items.WHEAT_SEEDS, ItemRegistry.AROACE_PRIDEWEAVE);
        weaveRecipe(consumer, Items.WHEAT, ItemRegistry.BI_PRIDEWEAVE);
        weaveRecipe(consumer, Items.RAW_IRON, ItemRegistry.DEMIBOY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.RAW_COPPER, ItemRegistry.DEMIGIRL_PRIDEWEAVE);
        weaveRecipe(consumer, Items.MOSS_BLOCK, ItemRegistry.ENBY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.MELON_SLICE, ItemRegistry.GAY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.WATER_BUCKET, ItemRegistry.GENDERFLUID_PRIDEWEAVE);
        weaveRecipe(consumer, Items.GLASS_BOTTLE, ItemRegistry.GENDERQUEER_PRIDEWEAVE);
        weaveRecipe(consumer, Items.AZALEA, ItemRegistry.INTERSEX_PRIDEWEAVE);
        weaveRecipe(consumer, Items.HONEYCOMB, ItemRegistry.LESBIAN_PRIDEWEAVE);
        weaveRecipe(consumer, Items.CARROT, ItemRegistry.PAN_PRIDEWEAVE);
        weaveRecipe(consumer, Items.REPEATER, ItemRegistry.PLURAL_PRIDEWEAVE);
        weaveRecipe(consumer, Items.COMPARATOR, ItemRegistry.POLY_PRIDEWEAVE);
        weaveRecipe(consumer, Items.STONE_BRICK_WALL, ItemRegistry.PRIDE_PRIDEWEAVE);
        weaveRecipe(consumer, Items.EGG, ItemRegistry.TRANS_PRIDEWEAVE);
    }

    private void weaveRecipe(Consumer<FinishedRecipe> consumer, Item sideItem, Supplier<? extends Item> output) {
        shapeless(output.get()).requires(ItemRegistry.ESOTERIC_SPOOL.get()).requires(sideItem).unlockedBy("has_spool", has(ItemRegistry.ESOTERIC_SPOOL.get())).save(consumer);
    }

    private void nodeSmelting(Consumer<FinishedRecipe> recipeConsumer, RegistryObject<ImpetusItem> impetus, RegistryObject<Item> node, TagKey<Item> tag) {
        String name = node.get().getRegistryName().getPath().replaceFirst("_node", "");

        ConditionalRecipe.builder().addCondition(not(new TagEmptyCondition(tag.location().toString()))).addRecipe(
                        smeltingWithTag(new IngredientWithCount(Ingredient.of(tag), 6), Ingredient.of(node.get()), 0.25f, 200)
                                ::build)
                .generateAdvancement()
                .build(recipeConsumer, MalumMod.malumPath(name + "_from_node_smelting"));

        ConditionalRecipe.builder().addCondition(not(new TagEmptyCondition(tag.location().toString()))).addRecipe(
                        blastingWithTag(new IngredientWithCount(Ingredient.of(tag), 6), Ingredient.of(node.get()), 0.25f, 100)
                                ::build)
                .generateAdvancement()
                .build(recipeConsumer, MalumMod.malumPath(name + "_from_node_blasting"));
    }

    private static void etherBrazier(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike rock, ItemLike ether) {
        NBTCarryRecipeBuilder.shapedRecipe(output, 2, Ingredient.of(ether)).key('#', rock).key('S', Ingredient.of(Tags.Items.RODS_WOODEN)).key('X', ether).patternLine("#X#").patternLine("S#S").addCriterion("has_ether", has(ItemRegistry.ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath());
    }

    private static void etherTorch(Consumer<FinishedRecipe> recipeConsumer, ItemLike output, ItemLike ether) {
        NBTCarryRecipeBuilder.shapedRecipe(output, 4, Ingredient.of(ether)).key('#', Ingredient.of(Tags.Items.RODS_WOODEN)).key('X', ether).patternLine("X").patternLine("#").addCriterion("has_ether", has(ItemRegistry.ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath() + "_alternative");
    }

    private static void shapelessPlanks(Consumer<FinishedRecipe> recipeConsumer, ItemLike planks, TagKey<Item> input) {
        shapeless(planks, 4).requires(input).group("planks").unlockedBy("has_logs", has(input)).save(recipeConsumer);
    }

    private static void shapelessWood(Consumer<FinishedRecipe> recipeConsumer, ItemLike stripped, ItemLike input) {
        shaped(stripped, 3).define('#', input).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(input)).save(recipeConsumer);
    }

    private static void shapelessButton(Consumer<FinishedRecipe> recipeConsumer, ItemLike button, ItemLike input) {
        shapeless(button).requires(input).unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedDoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike door, ItemLike input) {
        shaped(door, 3).define('#', input).pattern("##").pattern("##").pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedFence(Consumer<FinishedRecipe> recipeConsumer, ItemLike fence, ItemLike input) {
        shaped(fence, 3).define('#', Tags.Items.RODS_WOODEN).define('W', input).pattern("W#W").pattern("W#W").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedFenceGate(Consumer<FinishedRecipe> recipeConsumer, ItemLike fenceGate, ItemLike input) {
        shaped(fenceGate).define('#', Tags.Items.RODS_WOODEN).define('W', input).pattern("#W#").pattern("#W#").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedPressurePlate(Consumer<FinishedRecipe> recipeConsumer, ItemLike pressurePlate, ItemLike input) {
        shaped(pressurePlate).define('#', input).pattern("##").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedSlab(Consumer<FinishedRecipe> recipeConsumer, ItemLike slab, ItemLike input) {
        shaped(slab, 6).define('#', input).pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedStairs(Consumer<FinishedRecipe> recipeConsumer, ItemLike stairs, ItemLike input) {
        shaped(stairs, 4).define('#', input).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapelessSolidTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike solid, ItemLike normal) {
        shapeless(solid).requires(normal).unlockedBy("has_input", has(normal)).save(recipeConsumer);
    }

    private static void shapelessSolidTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike solid, ItemLike normal, String path) {
        shapeless(solid).requires(normal).unlockedBy("has_input", has(normal)).save(recipeConsumer, malumPath(path));
    }

    private static void shapedTrapdoor(Consumer<FinishedRecipe> recipeConsumer, ItemLike trapdoor, ItemLike input) {
        shaped(trapdoor, 2).define('#', input).pattern("###").pattern("###").unlockedBy("has_input", has(input)).save(recipeConsumer);
    }

    private static void shapedSign(Consumer<FinishedRecipe> recipeConsumer, ItemLike sign, ItemLike input) {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shaped(sign, 3).group("sign").define('#', input).define('X', Tags.Items.RODS_WOODEN).pattern("###").pattern("###").pattern(" X ").unlockedBy("has_" + s, has(input)).save(recipeConsumer);
    }

    protected static EnterBlockTrigger.TriggerInstance insideOf(Block pBlock) {
        return new EnterBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY, pBlock, StatePropertiesPredicate.ANY);
    }

    protected static InventoryChangeTrigger.TriggerInstance has(MinMaxBounds.Ints pCount, ItemLike pItem) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItem).withCount(pCount).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(ItemLike pItemLike) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pItemLike).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance has(TagKey<Item> pTag) {
        return inventoryTrigger(ItemPredicate.Builder.item().of(pTag).build());
    }

    protected static InventoryChangeTrigger.TriggerInstance inventoryTrigger(ItemPredicate... pPredicates) {
        return new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, pPredicates);
    }
}
