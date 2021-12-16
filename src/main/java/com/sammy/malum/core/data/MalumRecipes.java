package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.NBTCarryRecipeBuilder;
import com.sammy.malum.core.registry.items.ITemTagRegistry;
import com.sammy.malum.core.registry.items.ItemRegistry;
import net.minecraft.advancements.criterion.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import static net.minecraft.data.CookingRecipeBuilder.blastingRecipe;
import static net.minecraft.data.CookingRecipeBuilder.smeltingRecipe;
import static net.minecraft.data.ShapedRecipeBuilder.shapedRecipe;
import static net.minecraft.data.ShapelessRecipeBuilder.shapelessRecipe;

public class MalumRecipes extends RecipeProvider
{
    public MalumRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Recipe Provider";
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapelessRecipeBuilder.shapeless(ItemRegistry.ENCYCLOPEDIA_ARCANA.get()).requires(Items.BOOK).requires(ItemRegistry.PROCESSED_SOULSTONE.get()).unlockedBy("has_soulstone", hasItem(ItemRegistry.PROCESSED_SOULSTONE.get())).save(consumer);
        shapeless(ItemRegistry.COAL_FRAGMENT.get(),8).requires(Items.COAL).unlockedBy("has_coal", hasItem(Items.COAL)).save(consumer);


        smelting(Ingredient.of(ItemRegistry.BLAZING_QUARTZ_ORE.get()), ItemRegistry.BLAZING_QUARTZ.get(),0.25f,200).unlockedBy("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer);
        blasting(Ingredient.of(ItemRegistry.BLAZING_QUARTZ_ORE.get()), ItemRegistry.BLAZING_QUARTZ.get(),0.25f,100).unlockedBy("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, "blazing_quartz_from_blasting");
        ShapedRecipeBuilder.shaped(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).define('#', ItemRegistry.BLAZING_QUARTZ.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.BLAZING_QUARTZ.get(), 9).requires(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).unlockedBy("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, "blaze_quartz_from_block");
        ShapedRecipeBuilder.shaped(Items.NETHERRACK, 2).define('Z', ItemRegistry.BLAZING_QUARTZ.get()).define('Y', Tags.Items.COBBLESTONE).pattern("ZY").pattern("YZ").unlockedBy("has_blazing_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer, "blazing_quartz_netherrack");

        shapeless(ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(),8).requires(ItemRegistry.BLAZING_QUARTZ.get()).unlockedBy("has_blazing_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).save(consumer);
        shapeless(ItemRegistry.CHARCOAL_FRAGMENT.get(),8).requires(Items.CHARCOAL).unlockedBy("has_charcoal", hasItem(Items.CHARCOAL)).save(consumer);


        ShapedRecipeBuilder.shaped(ItemRegistry.BLOCK_OF_ARCANE_CHARCOAL.get()).define('#', ItemRegistry.ARCANE_CHARCOAL.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_arcane_charcoal", hasItem(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.ARCANE_CHARCOAL.get(), 9).requires(ItemRegistry.BLOCK_OF_ARCANE_CHARCOAL.get()).unlockedBy("has_arcane_charcoal", hasItem(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer, "blaze_quartz_alt");
        shapeless(ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(),8).requires(ItemRegistry.ARCANE_CHARCOAL.get()).unlockedBy("has_arcane_charcoal", hasItem(ItemRegistry.ARCANE_CHARCOAL.get())).save(consumer);

        smelting(Ingredient.of(ITemTagRegistry.RUNEWOOD_LOGS), ItemRegistry.ARCANE_CHARCOAL.get(),0.1f,200).unlockedBy("has_runewood_planks", hasItem(ITemTagRegistry.RUNEWOOD_LOGS)).save(consumer);
        smelting(Ingredient.of(ITemTagRegistry.SOULWOOD_LOGS), ItemRegistry.ARCANE_CHARCOAL.get(),0.1f,200).unlockedBy("has_soulwood_planks", hasItem(ITemTagRegistry.SOULWOOD_LOGS)).save(consumer, "arcane_charcoal_from_soulwood");

        ShapedRecipeBuilder.shaped(ItemRegistry.SPIRIT_ALTAR.get()).define('Z', Tags.Items.INGOTS_GOLD).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern(" Y ").pattern("ZXZ").pattern("XXX").unlockedBy("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SPIRIT_JAR.get()).define('Z', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('Y', Tags.Items.GLASS_PANES).pattern("YZY").pattern("Y Y").pattern("YYY").unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);

        smelting(Ingredient.of(ItemRegistry.BRILLIANT_STONE.get()), ItemRegistry.BRILLIANCE_CHUNK.get(),0.25f,200).unlockedBy("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).save(consumer);
        blasting(Ingredient.of(ItemRegistry.BRILLIANT_STONE.get()), ItemRegistry.BRILLIANCE_CHUNK.get(),0.25f,100).unlockedBy("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).save(consumer, "brilliance_from_blasting");
        ShapedRecipeBuilder.shaped(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).define('#', ItemRegistry.BRILLIANCE_CHUNK.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.BRILLIANCE_CHUNK.get(), 9).requires(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).unlockedBy("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).save(consumer, "brilliance_from_block");

        smelting(Ingredient.of(ItemRegistry.SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(),0.25f,200).unlockedBy("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).save(consumer);
        blasting(Ingredient.of(ItemRegistry.SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(),0.25f,100).unlockedBy("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).save(consumer, "soulstone_from_blasting");
        ShapedRecipeBuilder.shaped(ItemRegistry.BLOCK_OF_SOULSTONE.get()).define('#', ItemRegistry.PROCESSED_SOULSTONE.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.PROCESSED_SOULSTONE.get(), 9).requires(ItemRegistry.BLOCK_OF_SOULSTONE.get()).unlockedBy("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).save(consumer, "soulstone_from_block");

        ShapelessRecipeBuilder.shapeless(ItemRegistry.HOLY_SAPBALL.get(), 3).requires(ItemRegistry.HOLY_SAP.get()).requires(Items.SLIME_BALL).unlockedBy("has_holy_extract", hasItem(ItemRegistry.HOLY_SAP.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.HOLY_SAP.get()), ItemRegistry.HOLY_SYRUP.get(),0.1f,200).unlockedBy("has_holy_extract", hasItem(ItemRegistry.HOLY_SAP.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(ItemRegistry.UNHOLY_SAPBALL.get(), 3).requires(ItemRegistry.UNHOLY_SAP.get()).requires(Items.SLIME_BALL).unlockedBy("has_unholy_extract", hasItem(ItemRegistry.UNHOLY_SAP.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.UNHOLY_SAP.get()), ItemRegistry.UNHOLY_SYRUP.get(),0.1f,200).unlockedBy("has_unholy_extract", hasItem(ItemRegistry.UNHOLY_SAP.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.MAGMA_CREAM).requires(Items.BLAZE_POWDER).requires(ITemTagRegistry.SAPBALLS).unlockedBy("has_sapball", hasItem(ITemTagRegistry.SAPBALLS)).save(consumer, "magma_cream_from_sapballs");
        ShapedRecipeBuilder.shaped(Blocks.STICKY_PISTON).define('P', Blocks.PISTON).define('S', ITemTagRegistry.SAPBALLS).pattern("S").pattern("P").unlockedBy("has_sapball", hasItem(ITemTagRegistry.SAPBALLS)).save(consumer, "sticky_piston_from_sapballs");
        ShapedRecipeBuilder.shaped(Items.LEAD, 2).define('~', Tags.Items.STRING).define('O', ITemTagRegistry.SAPBALLS).pattern("~~ ").pattern("~O ").pattern("  ~").unlockedBy("has_sapball", hasItem(ITemTagRegistry.SAPBALLS)).save(consumer);

        shaped(ItemRegistry.GILDED_BELT.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', Tags.Items.LEATHER).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).pattern("XXX").pattern("#Y#").pattern(" # ").unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        shaped(ItemRegistry.GILDED_RING.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', Tags.Items.LEATHER).pattern(" X#").pattern("X X").pattern(" X ").unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);

        shaped(ItemRegistry.ORNATE_NECKLACE.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', Tags.Items.STRING).pattern(" X ").pattern("X X").pattern(" # ").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        shaped(ItemRegistry.ORNATE_RING.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', Tags.Items.LEATHER).pattern(" X#").pattern("X X").pattern(" X ").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.SPIRIT_POUCH.get()).define('X', Tags.Items.STRING).define('Y', ItemRegistry.SPIRIT_FABRIC.get()).define('Z', ItemTags.SOUL_FIRE_BASE_BLOCKS).pattern(" X ").pattern("YZY").pattern(" Y ").unlockedBy("has_spirit_fabric", hasItem(ItemRegistry.SPIRIT_FABRIC.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.CRUDE_SCYTHE.get()).define('#', Items.STICK).define('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).define('X', Tags.Items.INGOTS_IRON).pattern("XXY").pattern(" #X").pattern("#  ").unlockedBy("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SOUL_STAINED_STEEL_HOE.get()).define('#', Items.STICK).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XX").pattern(" #").pattern(" #").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get()).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XXX").pattern("X X").pattern("X X").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get()).define('#', Items.STICK).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("XXX").pattern(" # ").pattern(" # ").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get()).define('#', Items.STICK).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("X").pattern("#").pattern("#").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get()).define('#', Items.STICK).define('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("X").pattern("X").pattern("#").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer, "soul_stained_steel_from_shards");
        ShapelessRecipeBuilder.shapeless(ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get(), 9).requires(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 9).requires(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer, "soul_stained_steel_from_soul_stained_steel_block");

        ShapedRecipeBuilder.shaped(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('#', ItemRegistry.HALLOWED_GOLD_NUGGET.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer, "hallowed_gold_from_nuggets");
        ShapelessRecipeBuilder.shapeless(ItemRegistry.HALLOWED_GOLD_NUGGET.get(), 9).requires(ItemRegistry.HALLOWED_GOLD_INGOT.get()).unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(ItemRegistry.HALLOWED_GOLD_INGOT.get(),9).requires(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer, "hallowed_gold_ingot_from_hallowed_gold_block");

        ShapedRecipeBuilder.shaped(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get()).define('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', Tags.Items.GEMS_QUARTZ).pattern(" X ").pattern("#Y#").pattern(" X ").unlockedBy("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.STAINED_SPIRIT_RESONATOR.get()).define('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', Tags.Items.GEMS_QUARTZ).pattern(" X ").pattern("#Y#").pattern(" X ").unlockedBy("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).save(consumer);

        ShapelessRecipeBuilder.shapeless(Items.EXPERIENCE_BOTTLE).requires(ItemRegistry.BRILLIANCE_CHUNK.get()).requires(Items.GLASS_BOTTLE).unlockedBy("has_confined_brilliance", hasItem(ItemRegistry.BRILLIANCE_CHUNK.get())).save(consumer);

        shapelessPlanks(consumer, ItemRegistry.RUNEWOOD_PLANKS.get(), ITemTagRegistry.RUNEWOOD_LOGS);
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
        shapedSign(consumer, ItemRegistry.RUNEWOOD_SIGN.get(), ItemRegistry.RUNEWOOD_PLANKS.get());
        shaped(ItemRegistry.RUNEWOOD_BOAT.get()).define('#', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("# #").pattern("###").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get(),2).define('#', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("#").pattern("#").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get());

        shaped(ItemRegistry.RUNEWOOD_PANEL.get(),4).define('#', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_PANEL_SLAB.get(), ItemRegistry.RUNEWOOD_PANEL.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_PANEL_STAIRS.get(), ItemRegistry.RUNEWOOD_PANEL.get());

        shaped(ItemRegistry.RUNEWOOD_TILES.get(),4).define('#', ItemRegistry.RUNEWOOD_PANEL.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_TILES_SLAB.get(), ItemRegistry.RUNEWOOD_TILES.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_TILES_STAIRS.get(), ItemRegistry.RUNEWOOD_TILES.get());

        shaped(ItemRegistry.CUT_RUNEWOOD_PLANKS.get(),2).define('#', ItemRegistry.RUNEWOOD_PANEL.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).pattern("#").pattern("X").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.RUNEWOOD_BEAM.get(),2).define('#', ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get()).pattern("#").pattern("#").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.RUNEWOOD_ITEM_STAND.get(), 2).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', ItemRegistry.RUNEWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get()).define('X', ItemRegistry.RUNEWOOD_PLANKS.get()).define('Y', ItemRegistry.RUNEWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).save(consumer);

        shapelessPlanks(consumer, ItemRegistry.SOULWOOD_PLANKS.get(), ITemTagRegistry.SOULWOOD_LOGS);
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
        shapedSign(consumer, ItemRegistry.SOULWOOD_SIGN.get(), ItemRegistry.SOULWOOD_PLANKS.get());
        shaped(ItemRegistry.SOULWOOD_BOAT.get()).define('#', ItemRegistry.SOULWOOD_PLANKS.get()).pattern("# #").pattern("###").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get(),2).define('#', ItemRegistry.SOULWOOD_PLANKS.get()).pattern("#").pattern("#").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get());

        shaped(ItemRegistry.SOULWOOD_PANEL.get(),4).define('#', ItemRegistry.SOULWOOD_PLANKS.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.SOULWOOD_PANEL_SLAB.get(), ItemRegistry.SOULWOOD_PANEL.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_PANEL_STAIRS.get(), ItemRegistry.SOULWOOD_PANEL.get());

        shaped(ItemRegistry.SOULWOOD_TILES.get(),4).define('#', ItemRegistry.SOULWOOD_PANEL.get()).pattern(" # ").pattern("# #").pattern(" # ").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shapedSlab(consumer, ItemRegistry.SOULWOOD_TILES_SLAB.get(), ItemRegistry.SOULWOOD_TILES.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_TILES_STAIRS.get(), ItemRegistry.SOULWOOD_TILES.get());

        shaped(ItemRegistry.CUT_SOULWOOD_PLANKS.get(),2).define('#', ItemRegistry.SOULWOOD_PANEL.get()).define('X', ItemRegistry.SOULWOOD_PLANKS.get()).pattern("#").pattern("X").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.SOULWOOD_BEAM.get(),2).define('#', ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get()).pattern("#").pattern("#").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);

        shaped(ItemRegistry.SOULWOOD_ITEM_STAND.get(), 2).define('X', ItemRegistry.SOULWOOD_PLANKS.get()).define('Y', ItemRegistry.SOULWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);
        shaped(ItemRegistry.SOULWOOD_ITEM_PEDESTAL.get()).define('X', ItemRegistry.SOULWOOD_PLANKS.get()).define('Y', ItemRegistry.SOULWOOD_PLANKS_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.TAINTED_ROCK_WALL.get(), 6).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_SLAB.get(), 2).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_STAIRS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_WALL.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_wall_stonecutting");

        shaped(ItemRegistry.POLISHED_TAINTED_ROCK.get(),9).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), 2).unlocks("has_polished_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, "polished_tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get()).unlocks("has_polished_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, "polished_tainted_rock_stairs_stonecutting");

        smelting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK.get(),0.1f,200).unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), 2).unlocks("has_smooth_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, "smooth_tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get()).unlocks("has_smooth_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).save(consumer, "smooth_tainted_rock_stairs_stonecutting");

        shaped(ItemRegistry.TAINTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_brick_wall_stonecutting");

        ShapedRecipeBuilder.shaped(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get(),0.1f,200).unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_cracked_tainted_rock_bricks", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get())).save(consumer, "cracked_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_cracked_tainted_rock_bricks", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get())).save(consumer, "cracked_tainted_rock_bricks_stairs_stonecutting");

        shaped(ItemRegistry.TAINTED_ROCK_TILES.get(),4).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.TAINTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), 2).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_WALL.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_tiles_wall_stonecutting");

        smelting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get(),0.1f,200).unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 2).unlocks("has_cracked_tainted_rock_tiles", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get())).save(consumer, "cracked_tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get()).unlocks("has_cracked_tainted_rock_tiles", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get())).save(consumer, "cracked_tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_tainted_rock_tiles_wall_stonecutting");

        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.TAINTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_wall_stonecutting");

        shaped(ItemRegistry.TAINTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_bricks_from_small_bricks");
        smelting(Ingredient.of(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(),0.1f,200).unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_small_tainted_rock_bricks_smelting");
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_small_tainted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_small_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_small_tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cracked_small_tainted_rock_bricks_wall_stonecutting");

        shaped(ItemRegistry.CHISELED_TAINTED_ROCK.get()).define('#', ItemRegistry.TAINTED_ROCK_SLAB.get()).pattern("#").pattern("#").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_PILLAR.get(),2).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("#").pattern("#").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_COLUMN.get(),2).define('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).pattern("#").pattern("#").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_PILLAR_CAP.get()).define('$', ItemRegistry.TAINTED_ROCK_PILLAR.get()).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("$").pattern("#").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).define('$', ItemRegistry.TAINTED_ROCK_COLUMN.get()).define('#', ItemRegistry.TAINTED_ROCK.get()).pattern("$").pattern("#").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);

        shaped(ItemRegistry.CUT_TAINTED_ROCK.get(),4).define('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.CUT_TAINTED_ROCK.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "cut_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_PILLAR.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_pillar_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_PILLAR_CAP.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_pillar_cap_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_COLUMN.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_column_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_column_cap_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_BRICKS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "smooth_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "polished_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.CHISELED_TAINTED_ROCK.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "chiseled_tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CHISELED_TAINTED_ROCK.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "chiseled_tainted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_stonecutting_from_bricks");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).unlocks("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer, "small_tainted_rock_bricks_stonecutting_from_tiles");

        shaped(ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(), 2).define('X', ItemRegistry.TAINTED_ROCK.get()).define('Y', ItemRegistry.TAINTED_ROCK_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get()).define('X', ItemRegistry.TAINTED_ROCK.get()).define('Y', ItemRegistry.TAINTED_ROCK_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).save(consumer);

        ShapedRecipeBuilder.shaped(ItemRegistry.TWISTED_ROCK_WALL.get(), 6).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_SLAB.get(), 2).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_STAIRS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_WALL.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_wall_stonecutting");

        shaped(ItemRegistry.POLISHED_TWISTED_ROCK.get(),9).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("###").pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), 2).unlocks("has_polished_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, "polished_twisted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get()).unlocks("has_polished_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, "polished_twisted_rock_stairs_stonecutting");

        smelting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK.get(),0.1f,200).unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), 6).define('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), 4).define('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), 2).unlocks("has_smooth_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, "smooth_twisted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMOOTH_TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get()).unlocks("has_smooth_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).save(consumer, "smooth_twisted_rock_stairs_stonecutting");

        shaped(ItemRegistry.TWISTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_brick_wall_stonecutting");

        ShapedRecipeBuilder.shaped(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        smelting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get(),0.1f,200).unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_cracked_twisted_rock_bricks", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get())).save(consumer, "cracked_twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_cracked_twisted_rock_bricks", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get())).save(consumer, "cracked_twisted_rock_bricks_stairs_stonecutting");

        shaped(ItemRegistry.TWISTED_ROCK_TILES.get(),4).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.TWISTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), 2).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_WALL.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_tiles_wall_stonecutting");

        smelting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get(),0.1f,200).unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("###").unlockedBy("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get(), 6).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_twisted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), 2).unlocks("has_cracked_twisted_rock_tiles", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get())).save(consumer, "cracked_twisted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get()).unlocks("has_cracked_twisted_rock_tiles", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get())).save(consumer, "cracked_twisted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_twisted_rock_tiles_wall_stonecutting");

        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.TWISTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_wall_stonecutting");

        shaped(ItemRegistry.TWISTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_bricks_from_small_bricks");

        smelting(Ingredient.of(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(),0.1f,200).unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_small_twisted_rock_bricks_smelting");
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(),4).define('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).define('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        ShapedRecipeBuilder.shaped(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), 6).define('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).pattern("###").pattern("###").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_small_twisted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 2).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_small_twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_small_twisted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cracked_small_twisted_rock_bricks_wall_stonecutting");

        shaped(ItemRegistry.CHISELED_TWISTED_ROCK.get()).define('#', ItemRegistry.TWISTED_ROCK_SLAB.get()).pattern("#").pattern("#").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_PILLAR.get(),2).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("#").pattern("#").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_COLUMN.get(),2).define('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).pattern("#").pattern("#").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_PILLAR_CAP.get()).define('$', ItemRegistry.TWISTED_ROCK_PILLAR.get()).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("$").pattern("#").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).define('$', ItemRegistry.TWISTED_ROCK_COLUMN.get()).define('#', ItemRegistry.TWISTED_ROCK.get()).pattern("$").pattern("#").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);

        shaped(ItemRegistry.CUT_TWISTED_ROCK.get(),4).define('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).pattern("##").pattern("##").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.CUT_TWISTED_ROCK.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "cut_twisted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_PILLAR.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_pillar_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_PILLAR_CAP.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_pillar_cap_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_COLUMN.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_column_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_column_cap_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_BRICKS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "twisted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "smooth_twisted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "polished_twisted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.CHISELED_TWISTED_ROCK.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "chiseled_twisted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CHISELED_TWISTED_ROCK.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "chiseled_twisted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_stonecutting_from_bricks");
        SingleItemRecipeBuilder.stonecutting(Ingredient.of(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).unlocks("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer, "small_twisted_rock_bricks_stonecutting_from_tiles");

        shaped(ItemRegistry.TWISTED_ROCK_ITEM_STAND.get(), 2).define('X', ItemRegistry.TWISTED_ROCK.get()).define('Y', ItemRegistry.TWISTED_ROCK_SLAB.get()).pattern("YYY").pattern("XXX").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);
        shaped(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get()).define('X', ItemRegistry.TWISTED_ROCK.get()).define('Y', ItemRegistry.TWISTED_ROCK_SLAB.get()).pattern("YYY").pattern(" X ").pattern("YYY").unlockedBy("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).save(consumer);

        etherTorch(consumer, ItemRegistry.ETHER_TORCH.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherTorch(consumer, ItemRegistry.IRIDESCENT_ETHER_TORCH.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());
    }
    private static void etherBrazier(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider rock, IItemProvider ether)
    {
        NBTCarryRecipeBuilder.shapedRecipe(output,2,Ingredient.of(ether)).key('#', rock).key('S', Items.STICK).key('X', ether).patternLine("#X#").patternLine("S#S").addCriterion("has_ether", hasItem(ItemRegistry.ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath());
    }
    private static void etherTorch(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider ether)
    {
        NBTCarryRecipeBuilder.shapedRecipe(output, 4,Ingredient.of(ether)).key('#', Items.STICK).key('X', ether).patternLine("X").patternLine("#").addCriterion("has_ether", hasItem(ItemRegistry.ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath() + "_alternative");
    }
    private static void smithingReinforce(Consumer<IFinishedRecipe> recipeConsumer, Item itemToReinforce, Item output)
    {
        SmithingRecipeBuilder.smithing(Ingredient.of(itemToReinforce), Ingredient.of(Items.NETHERITE_INGOT), output).unlocks("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT)).save(recipeConsumer, Registry.ITEM.getKey(output.asItem()).getPath() + "_smithing");
    }
    private static void shapelessPlanks(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider planks, ITag<Item> input)
    {
        shapeless(planks, 4).requires(input).group("planks").unlockedBy("has_logs", hasItem(input)).save(recipeConsumer);
    }
    private static void shapelessWood(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider stripped, IItemProvider input)
    {
        shaped(stripped, 3).define('#', input).pattern("##").pattern("##").group("bark").unlockedBy("has_log", hasItem(input)).save(recipeConsumer);
    }
    private static void shapelessButton(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider button, IItemProvider input)
    {
        shapeless(button).requires(input).unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedDoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider door, IItemProvider input)
    {
        shaped(door, 3).define('#', input).pattern("##").pattern("##").pattern("##").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fence, IItemProvider input)
    {
        shaped(fence, 3).define('#', Items.STICK).define('W', input).pattern("W#W").pattern("W#W").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedFenceGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fenceGate, IItemProvider input)
    {
        shaped(fenceGate).define('#', Items.STICK).define('W', input).pattern("#W#").pattern("#W#").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedPressurePlate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pressurePlate, IItemProvider input)
    {
        shaped(pressurePlate).define('#', input).pattern("##").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedSlab(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider slab, IItemProvider input)
    {
        shaped(slab, 6).define('#', input).pattern("###").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedStairs(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider stairs, IItemProvider input)
    {
        shaped(stairs, 4).define('#', input).pattern("#  ").pattern("## ").pattern("###").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapelessSolidTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider solid, IItemProvider normal)
    {
        shapeless(solid).requires(normal).unlockedBy("has_input", hasItem(normal)).save(recipeConsumer);
    }
    private static void shapedTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider trapdoor, IItemProvider input)
    {
        shaped(trapdoor, 2).define('#', input).pattern("###").pattern("###").unlockedBy("has_input", hasItem(input)).save(recipeConsumer);
    }
    private static void shapedSign(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider sign, IItemProvider input)
    {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shaped(sign, 3).group("sign").define('#', input).define('X', Items.STICK).pattern("###").pattern("###").pattern(" X ").unlockedBy("has_" + s, hasItem(input)).save(recipeConsumer);
    }
    private static void shapelessColoredWool(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredWool, IItemProvider dye)
    {
        shapeless(coloredWool).requires(dye).requires(Blocks.WHITE_WOOL).group("wool").unlockedBy("has_white_wool", hasItem(Blocks.WHITE_WOOL)).save(recipeConsumer);
    }
    private static void shapedCarpet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider carpet, IItemProvider input)
    {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shaped(carpet, 3).define('#', input).pattern("##").group("carpet").unlockedBy("has_" + s, hasItem(input)).save(recipeConsumer);
    }
    private static void shapelessColoredCarpet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredCarpet, IItemProvider dye)
    {
        String s = Registry.ITEM.getKey(coloredCarpet.asItem()).getPath();
        String s1 = Registry.ITEM.getKey(dye.asItem()).getPath();
        shaped(coloredCarpet, 8).define('#', Blocks.WHITE_CARPET).define('$', dye).pattern("###").pattern("#$#").pattern("###").group("carpet").unlockedBy("has_white_carpet", hasItem(Blocks.WHITE_CARPET)).unlockedBy("has_" + s1, hasItem(dye)).save(recipeConsumer, s + "_from_white_carpet");
    }
    private static void shapedBed(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider bed, IItemProvider wool)
    {
        String s = Registry.ITEM.getKey(wool.asItem()).getPath();
        shaped(bed).define('#', wool).define('X', ItemTags.PLANKS).pattern("###").pattern("XXX").group("bed").unlockedBy("has_" + s, hasItem(wool)).save(recipeConsumer);
    }
    private static void shapedColoredBed(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredBed, IItemProvider dye)
    {
        String s = Registry.ITEM.getKey(coloredBed.asItem()).getPath();
        shapeless(coloredBed).requires(Items.WHITE_BED).requires(dye).group("dyed_bed").unlockedBy("has_bed", hasItem(Items.WHITE_BED)).save(recipeConsumer, s + "_from_white_bed");
    }
    private static void shapedBanner(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider banner, IItemProvider input)
    {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shaped(banner).define('#', input).define('|', Items.STICK).pattern("###").pattern("###").pattern(" | ").group("banner").unlockedBy("has_" + s, hasItem(input)).save(recipeConsumer);
    }
    private static void shapedColoredGlass(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredGlass, IItemProvider dye)
    {
        shaped(coloredGlass, 8).define('#', Blocks.GLASS).define('X', dye).pattern("###").pattern("#X#").pattern("###").group("stained_glass").unlockedBy("has_glass", hasItem(Blocks.GLASS)).save(recipeConsumer);
    }
    private static void shapedGlassPane(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pane, IItemProvider glass)
    {
        shaped(pane, 16).define('#', glass).pattern("###").pattern("###").group("stained_glass_pane").unlockedBy("has_glass", hasItem(glass)).save(recipeConsumer);
    }
    private static void shapedColoredPane(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredPane, IItemProvider dye)
    {
        String s = Registry.ITEM.getKey(coloredPane.asItem()).getPath();
        String s1 = Registry.ITEM.getKey(dye.asItem()).getPath();
        shaped(coloredPane, 8).define('#', Blocks.GLASS_PANE).define('$', dye).pattern("###").pattern("#$#").pattern("###").group("stained_glass_pane").unlockedBy("has_glass_pane", hasItem(Blocks.GLASS_PANE)).unlockedBy("has_" + s1, hasItem(dye)).save(recipeConsumer, s + "_from_glass_pane");
    }
    private static void shapedColoredTerracotta(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredTerracotta, IItemProvider dye)
    {
        shaped(coloredTerracotta, 8).define('#', Blocks.TERRACOTTA).define('X', dye).pattern("###").pattern("#X#").pattern("###").group("stained_terracotta").unlockedBy("has_terracotta", hasItem(Blocks.TERRACOTTA)).save(recipeConsumer);
    }
    private static void shapedColorConcretePowder(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredConcretePowder, IItemProvider dye)
    {
        shapeless(coloredConcretePowder, 8).requires(dye).requires(Blocks.SAND, 4).requires(Blocks.GRAVEL, 4).group("concrete_powder").unlockedBy("has_sand", hasItem(Blocks.SAND)).unlockedBy("has_gravel", hasItem(Blocks.GRAVEL)).save(recipeConsumer);
    }
    protected static EnterBlockTrigger.Instance enteredBlock(Block block)
    {
        return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.ANY, block, StatePropertiesPredicate.ANY);
    }
    protected static InventoryChangeTrigger.Instance hasItem(IItemProvider item)
    {
        return hasItem(ItemPredicate.Builder.item().of(item).build());
    }
    protected static InventoryChangeTrigger.Instance hasItem(ITag<Item> tag)
    {
        return hasItem(ItemPredicate.Builder.item().of(tag).build());
    }
    protected static InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicate)
    {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, MinMaxBounds.IntBound.ANY, predicate);
    }
}