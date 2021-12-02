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
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.ENCYCLOPEDIA_ARCANA.get()).addIngredient(Items.BOOK).addIngredient(ItemRegistry.PROCESSED_SOULSTONE.get()).addCriterion("has_soulstone", hasItem(ItemRegistry.PROCESSED_SOULSTONE.get())).build(consumer);
        shapelessRecipe(ItemRegistry.COAL_FRAGMENT.get(),8).addIngredient(Items.COAL).addCriterion("has_coal", hasItem(Items.COAL)).build(consumer);
        shapelessRecipe(ItemRegistry.BLAZING_QUARTZ_FRAGMENT.get(),8).addIngredient(ItemRegistry.BLAZING_QUARTZ.get()).addCriterion("has_blazing_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).build(consumer);
        shapelessRecipe(ItemRegistry.ARCANE_CHARCOAL_FRAGMENT.get(),8).addIngredient(ItemRegistry.ARCANE_CHARCOAL.get()).addCriterion("has_arcane_charcoal", hasItem(ItemRegistry.ARCANE_CHARCOAL.get())).build(consumer);

        smeltingRecipe(Ingredient.fromTag(ITemTagRegistry.RUNEWOOD_LOGS), ItemRegistry.ARCANE_CHARCOAL.get(),0.1f,200).addCriterion("has_runewood_planks", hasItem(ITemTagRegistry.RUNEWOOD_LOGS)).build(consumer);
        smeltingRecipe(Ingredient.fromTag(ITemTagRegistry.SOULWOOD_LOGS), ItemRegistry.ARCANE_CHARCOAL.get(),0.1f,200).addCriterion("has_soulwood_planks", hasItem(ITemTagRegistry.SOULWOOD_LOGS)).build(consumer, "arcane_charcoal_from_soulwood");

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SPIRIT_ALTAR.get()).key('Z', Tags.Items.INGOTS_GOLD).key('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).key('X', ItemRegistry.RUNEWOOD_PLANKS.get()).patternLine(" Y ").patternLine("ZXZ").patternLine("XXX").addCriterion("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SPIRIT_JAR.get()).key('Z', ItemRegistry.HALLOWED_GOLD_INGOT.get()).key('Y', Tags.Items.GLASS_PANES).patternLine("YZY").patternLine("Y Y").patternLine("YYY").addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer);

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.BRILLIANT_STONE.get()), ItemRegistry.BRILLIANCE_CHUNK.get(),0.25f,200).addCriterion("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).build(consumer);
        blastingRecipe(Ingredient.fromItems(ItemRegistry.BRILLIANT_STONE.get()), ItemRegistry.BRILLIANCE_CHUNK.get(),0.25f,100).addCriterion("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).build(consumer, "brilliance_from_blasting");
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).key('#', ItemRegistry.BRILLIANCE_CHUNK.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.BRILLIANCE_CHUNK.get(), 9).addIngredient(ItemRegistry.BLOCK_OF_BRILLIANCE.get()).addCriterion("has_brilliance", hasItem(ItemRegistry.BRILLIANCE_CLUSTER.get())).build(consumer, "brilliance_from_block");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(),0.25f,200).addCriterion("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).build(consumer);
        blastingRecipe(Ingredient.fromItems(ItemRegistry.SOULSTONE_ORE.get()), ItemRegistry.PROCESSED_SOULSTONE.get(),0.25f,100).addCriterion("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).build(consumer, "soulstone_from_blasting");
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.BLOCK_OF_SOULSTONE.get()).key('#', ItemRegistry.PROCESSED_SOULSTONE.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.PROCESSED_SOULSTONE.get(), 9).addIngredient(ItemRegistry.BLOCK_OF_SOULSTONE.get()).addCriterion("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).build(consumer, "soulstone_from_block");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.BLAZING_QUARTZ_ORE.get()), ItemRegistry.BLAZING_QUARTZ.get(),0.25f,200).addCriterion("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).build(consumer);
        blastingRecipe(Ingredient.fromItems(ItemRegistry.BLAZING_QUARTZ_ORE.get()), ItemRegistry.BLAZING_QUARTZ.get(),0.25f,100).addCriterion("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).build(consumer, "blazing_quartz_from_blasting");
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).key('#', ItemRegistry.BLAZING_QUARTZ.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.BLAZING_QUARTZ.get(), 9).addIngredient(ItemRegistry.BLOCK_OF_BLAZING_QUARTZ.get()).addCriterion("has_blaze_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).build(consumer, "blaze_quartz_alt");
        ShapedRecipeBuilder.shapedRecipe(Items.NETHERRACK, 2).key('Z', ItemRegistry.BLAZING_QUARTZ.get()).key('Y', Tags.Items.COBBLESTONE).patternLine("ZY").patternLine("YZ").addCriterion("has_blazing_quartz", hasItem(ItemRegistry.BLAZING_QUARTZ.get())).build(consumer, "blazing_quartz_netherrack");

        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.HOLY_SAPBALL.get(), 3).addIngredient(ItemRegistry.HOLY_SAP.get()).addIngredient(Items.SLIME_BALL).addCriterion("has_holy_extract", hasItem(ItemRegistry.HOLY_SAP.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(ItemRegistry.HOLY_SAP.get()), ItemRegistry.HOLY_SYRUP.get(),0.1f,200).addCriterion("has_holy_extract", hasItem(ItemRegistry.HOLY_SAP.get())).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.UNHOLY_SAPBALL.get(), 3).addIngredient(ItemRegistry.UNHOLY_SAP.get()).addIngredient(Items.SLIME_BALL).addCriterion("has_unholy_extract", hasItem(ItemRegistry.UNHOLY_SAP.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(ItemRegistry.UNHOLY_SAP.get()), ItemRegistry.UNHOLY_SYRUP.get(),0.1f,200).addCriterion("has_unholy_extract", hasItem(ItemRegistry.UNHOLY_SAP.get())).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGMA_CREAM).addIngredient(Items.BLAZE_POWDER).addIngredient(ITemTagRegistry.SAPBALLS).addCriterion("has_sapball", hasItem(ITemTagRegistry.SAPBALLS)).build(consumer, "magma_cream_from_sapballs");
        ShapedRecipeBuilder.shapedRecipe(Blocks.STICKY_PISTON).key('P', Blocks.PISTON).key('S', ITemTagRegistry.SAPBALLS).patternLine("S").patternLine("P").addCriterion("has_sapball", hasItem(ITemTagRegistry.SAPBALLS)).build(consumer, "sticky_piston_from_sapballs");
        ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2).key('~', Tags.Items.STRING).key('O', ITemTagRegistry.SAPBALLS).patternLine("~~ ").patternLine("~O ").patternLine("  ~").addCriterion("has_sapball", hasItem(ITemTagRegistry.SAPBALLS)).build(consumer);

        shapedRecipe(ItemRegistry.GILDED_BELT.get()).key('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).key('X', Tags.Items.LEATHER).key('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).patternLine("XXX").patternLine("#Y#").patternLine(" # ").addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer);
        shapedRecipe(ItemRegistry.GILDED_RING.get()).key('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).key('X', Tags.Items.LEATHER).patternLine(" X#").patternLine("X X").patternLine(" X ").addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer);

        shapedRecipe(ItemRegistry.ORNATE_NECKLACE.get()).key('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).key('X', Tags.Items.STRING).patternLine(" X ").patternLine("X X").patternLine(" # ").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        shapedRecipe(ItemRegistry.ORNATE_RING.get()).key('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).key('X', Tags.Items.LEATHER).patternLine(" X#").patternLine("X X").patternLine(" X ").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SPIRIT_POUCH.get()).key('X', Tags.Items.STRING).key('Y', ItemRegistry.SPIRIT_FABRIC.get()).key('Z', ItemTags.SOUL_FIRE_BASE_BLOCKS).patternLine(" X ").patternLine("YZY").patternLine(" Y ").addCriterion("has_spirit_fabric", hasItem(ItemRegistry.SPIRIT_FABRIC.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRUDE_SCYTHE.get()).key('#', Items.STICK).key('Y', ItemRegistry.PROCESSED_SOULSTONE.get()).key('X', Tags.Items.INGOTS_IRON).patternLine("XXY").patternLine(" #X").patternLine("#  ").addCriterion("has_soulstone", hasItem(ItemRegistry.SOULSTONE_CLUSTER.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SOUL_STAINED_STEEL_HOE.get()).key('#', Items.STICK).key('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SOUL_STAINED_STEEL_LEGGINGS.get()).key('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SOUL_STAINED_STEEL_PICKAXE.get()).key('#', Items.STICK).key('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SOUL_STAINED_STEEL_SHOVEL.get()).key('#', Items.STICK).key('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SOUL_STAINED_STEEL_SWORD.get()).key('#', Items.STICK).key('X', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).key('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).key('#', ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer, "soul_stained_steel_from_shards");
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.SOUL_STAINED_STEEL_NUGGET.get(), 9).addIngredient(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get(), 9).addIngredient(ItemRegistry.BLOCK_OF_SOUL_STAINED_STEEL.get()).addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer, "soul_stained_steel_from_soul_stained_steel_block");

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).key('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.HALLOWED_GOLD_INGOT.get()).key('#', ItemRegistry.HALLOWED_GOLD_NUGGET.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer, "hallowed_gold_from_nuggets");
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.HALLOWED_GOLD_NUGGET.get(), 9).addIngredient(ItemRegistry.HALLOWED_GOLD_INGOT.get()).addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(ItemRegistry.HALLOWED_GOLD_INGOT.get(),9).addIngredient(ItemRegistry.BLOCK_OF_HALLOWED_GOLD.get()).addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer, "hallowed_gold_ingot_from_hallowed_gold_block");

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.HALLOWED_SPIRIT_RESONATOR.get()).key('#', ItemRegistry.HALLOWED_GOLD_INGOT.get()).key('X', ItemRegistry.RUNEWOOD_PLANKS.get()).key('Y', Tags.Items.GEMS_QUARTZ).patternLine(" X ").patternLine("#Y#").patternLine(" X ").addCriterion("has_hallowed_gold", hasItem(ItemRegistry.HALLOWED_GOLD_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.STAINED_SPIRIT_RESONATOR.get()).key('#', ItemRegistry.SOUL_STAINED_STEEL_INGOT.get()).key('X', ItemRegistry.RUNEWOOD_PLANKS.get()).key('Y', Tags.Items.GEMS_QUARTZ).patternLine(" X ").patternLine("#Y#").patternLine(" X ").addCriterion("has_soul_stained_steel", hasItem(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(Items.EXPERIENCE_BOTTLE).addIngredient(ItemRegistry.BRILLIANCE_CHUNK.get()).addIngredient(Items.GLASS_BOTTLE).addCriterion("has_confined_brilliance", hasItem(ItemRegistry.BRILLIANCE_CHUNK.get())).build(consumer);

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
        shapedRecipe(ItemRegistry.RUNEWOOD_BOAT.get()).key('#', ItemRegistry.RUNEWOOD_PLANKS.get()).patternLine("# #").patternLine("###").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);

        shapedRecipe(ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get(),2).key('#', ItemRegistry.RUNEWOOD_PLANKS.get()).patternLine("#").patternLine("#").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get());

        shapedRecipe(ItemRegistry.RUNEWOOD_PANEL.get(),4).key('#', ItemRegistry.RUNEWOOD_PLANKS.get()).patternLine(" # ").patternLine("# #").patternLine(" # ").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_PANEL_SLAB.get(), ItemRegistry.RUNEWOOD_PANEL.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_PANEL_STAIRS.get(), ItemRegistry.RUNEWOOD_PANEL.get());

        shapedRecipe(ItemRegistry.RUNEWOOD_TILES.get(),4).key('#', ItemRegistry.RUNEWOOD_PANEL.get()).patternLine(" # ").patternLine("# #").patternLine(" # ").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, ItemRegistry.RUNEWOOD_TILES_SLAB.get(), ItemRegistry.RUNEWOOD_TILES.get());
        shapedStairs(consumer, ItemRegistry.RUNEWOOD_TILES_STAIRS.get(), ItemRegistry.RUNEWOOD_TILES.get());

        shapedRecipe(ItemRegistry.CUT_RUNEWOOD_PLANKS.get(),2).key('#', ItemRegistry.RUNEWOOD_PANEL.get()).key('X', ItemRegistry.RUNEWOOD_PLANKS.get()).patternLine("#").patternLine("X").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedRecipe(ItemRegistry.RUNEWOOD_BEAM.get(),2).key('#', ItemRegistry.VERTICAL_RUNEWOOD_PLANKS.get()).patternLine("#").patternLine("#").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);

        shapedRecipe(ItemRegistry.RUNEWOOD_ITEM_STAND.get(), 2).key('X', ItemRegistry.RUNEWOOD_PLANKS.get()).key('Y', ItemRegistry.RUNEWOOD_PLANKS_SLAB.get()).patternLine("YYY").patternLine("XXX").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedRecipe(ItemRegistry.RUNEWOOD_ITEM_PEDESTAL.get()).key('X', ItemRegistry.RUNEWOOD_PLANKS.get()).key('Y', ItemRegistry.RUNEWOOD_PLANKS_SLAB.get()).patternLine("YYY").patternLine(" X ").patternLine("YYY").addCriterion("has_runewood_planks", hasItem(ItemRegistry.RUNEWOOD_PLANKS.get())).build(consumer);

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
        shapedRecipe(ItemRegistry.SOULWOOD_BOAT.get()).key('#', ItemRegistry.SOULWOOD_PLANKS.get()).patternLine("# #").patternLine("###").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);

        shapedRecipe(ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get(),2).key('#', ItemRegistry.SOULWOOD_PLANKS.get()).patternLine("#").patternLine("#").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, ItemRegistry.VERTICAL_SOULWOOD_PLANKS_SLAB.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get());
        shapedStairs(consumer, ItemRegistry.VERTICAL_SOULWOOD_PLANKS_STAIRS.get(), ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get());

        shapedRecipe(ItemRegistry.SOULWOOD_PANEL.get(),4).key('#', ItemRegistry.SOULWOOD_PLANKS.get()).patternLine(" # ").patternLine("# #").patternLine(" # ").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, ItemRegistry.SOULWOOD_PANEL_SLAB.get(), ItemRegistry.SOULWOOD_PANEL.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_PANEL_STAIRS.get(), ItemRegistry.SOULWOOD_PANEL.get());

        shapedRecipe(ItemRegistry.SOULWOOD_TILES.get(),4).key('#', ItemRegistry.SOULWOOD_PANEL.get()).patternLine(" # ").patternLine("# #").patternLine(" # ").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, ItemRegistry.SOULWOOD_TILES_SLAB.get(), ItemRegistry.SOULWOOD_TILES.get());
        shapedStairs(consumer, ItemRegistry.SOULWOOD_TILES_STAIRS.get(), ItemRegistry.SOULWOOD_TILES.get());

        shapedRecipe(ItemRegistry.CUT_SOULWOOD_PLANKS.get(),2).key('#', ItemRegistry.SOULWOOD_PANEL.get()).key('X', ItemRegistry.SOULWOOD_PLANKS.get()).patternLine("#").patternLine("X").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);
        shapedRecipe(ItemRegistry.SOULWOOD_BEAM.get(),2).key('#', ItemRegistry.VERTICAL_SOULWOOD_PLANKS.get()).patternLine("#").patternLine("#").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);

        shapedRecipe(ItemRegistry.SOULWOOD_ITEM_STAND.get(), 2).key('X', ItemRegistry.SOULWOOD_PLANKS.get()).key('Y', ItemRegistry.SOULWOOD_PLANKS_SLAB.get()).patternLine("YYY").patternLine("XXX").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);
        shapedRecipe(ItemRegistry.SOULWOOD_ITEM_PEDESTAL.get()).key('X', ItemRegistry.SOULWOOD_PLANKS.get()).key('Y', ItemRegistry.SOULWOOD_PLANKS_SLAB.get()).patternLine("YYY").patternLine(" X ").patternLine("YYY").addCriterion("has_soulwood_planks", hasItem(ItemRegistry.SOULWOOD_PLANKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.TAINTED_ROCK_WALL.get(), 6).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_SLAB.get(), 6).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_STAIRS.get(), 4).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_WALL.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_wall_stonecutting");

        shapedRecipe(ItemRegistry.POLISHED_TAINTED_ROCK.get(),9).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), 6).key('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get(), 4).key('#', ItemRegistry.POLISHED_TAINTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK_SLAB.get(), 2).addCriterion("has_polished_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).build(consumer, "polished_tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.POLISHED_TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK_STAIRS.get()).addCriterion("has_polished_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).build(consumer, "polished_tainted_rock_stairs_stonecutting");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), 6).key('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get(), 4).key('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMOOTH_TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK_SLAB.get(), 2).addCriterion("has_smooth_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).build(consumer, "smooth_tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMOOTH_TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK_STAIRS.get()).addCriterion("has_smooth_tainted_rock", hasItem(ItemRegistry.POLISHED_TAINTED_ROCK.get())).build(consumer, "smooth_tainted_rock_stairs_stonecutting");

        shapedRecipe(ItemRegistry.TAINTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_BRICKS_WALL.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_brick_wall_stonecutting");

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_cracked_tainted_rock_bricks", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get())).build(consumer, "cracked_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_cracked_tainted_rock_bricks", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get())).build(consumer, "cracked_tainted_rock_bricks_stairs_stonecutting");

        shapedRecipe(ItemRegistry.TAINTED_ROCK_TILES.get(),4).key('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', ItemRegistry.TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', ItemRegistry.TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.TAINTED_ROCK_TILES_WALL.get(), 6).key('#', ItemRegistry.TAINTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.TAINTED_ROCK_TILES_WALL.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_wall_stonecutting");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get(), 6).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_cracked_tainted_rock_tiles", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get())).build(consumer, "cracked_tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_cracked_tainted_rock_tiles", hasItem(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get())).build(consumer, "cracked_tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TAINTED_ROCK_TILES_WALL.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_tainted_rock_tiles_wall_stonecutting");

        shapedRecipe(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.TAINTED_ROCK_TILES.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS_WALL.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_wall_stonecutting");

        shapedRecipe(ItemRegistry.TAINTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_bricks_from_small_bricks");
        smeltingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_small_tainted_rock_bricks_smelting");
        shapedRecipe(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_small_tainted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_small_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_small_tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TAINTED_ROCK_BRICKS_WALL.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cracked_small_tainted_rock_bricks_wall_stonecutting");

        shapedRecipe(ItemRegistry.CHISELED_TAINTED_ROCK.get()).key('#', ItemRegistry.TAINTED_ROCK_SLAB.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_PILLAR.get(),2).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_COLUMN.get(),2).key('#', ItemRegistry.TAINTED_ROCK_BRICKS.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_PILLAR_CAP.get()).key('$', ItemRegistry.TAINTED_ROCK_PILLAR.get()).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).key('$', ItemRegistry.TAINTED_ROCK_COLUMN.get()).key('#', ItemRegistry.TAINTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);

        shapedRecipe(ItemRegistry.CUT_TAINTED_ROCK.get(),4).key('#', ItemRegistry.SMOOTH_TAINTED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.CUT_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "cut_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_PILLAR.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_pillar_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_PILLAR_CAP.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_pillar_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_COLUMN.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_column_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_COLUMN_CAP.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_column_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.SMOOTH_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "smooth_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.POLISHED_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "polished_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK.get()), ItemRegistry.CHISELED_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "chiseled_tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.CHISELED_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "chiseled_tainted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_stonecutting_from_bricks");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TAINTED_ROCK_TILES.get()), ItemRegistry.SMALL_TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer, "small_tainted_rock_bricks_stonecutting_from_tiles");

        shapedRecipe(ItemRegistry.TAINTED_ROCK_ITEM_STAND.get(), 2).key('X', ItemRegistry.TAINTED_ROCK.get()).key('Y', ItemRegistry.TAINTED_ROCK_SLAB.get()).patternLine("YYY").patternLine("XXX").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TAINTED_ROCK_ITEM_PEDESTAL.get()).key('X', ItemRegistry.TAINTED_ROCK.get()).key('Y', ItemRegistry.TAINTED_ROCK_SLAB.get()).patternLine("YYY").patternLine(" X ").patternLine("YYY").addCriterion("has_tainted_rock", hasItem(ItemRegistry.TAINTED_ROCK.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.TWISTED_ROCK_WALL.get(), 6).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_SLAB.get(), 6).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_STAIRS.get(), 4).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_SLAB.get(), 2).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_STAIRS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_WALL.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_wall_stonecutting");

        shapedRecipe(ItemRegistry.POLISHED_TWISTED_ROCK.get(),9).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), 6).key('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get(), 4).key('#', ItemRegistry.POLISHED_TWISTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK_SLAB.get(), 2).addCriterion("has_polished_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).build(consumer, "polished_twisted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.POLISHED_TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK_STAIRS.get()).addCriterion("has_polished_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).build(consumer, "polished_twisted_rock_stairs_stonecutting");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK.get(),0.1f,200).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), 6).key('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get(), 4).key('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMOOTH_TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK_SLAB.get(), 2).addCriterion("has_smooth_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).build(consumer, "smooth_twisted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMOOTH_TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK_STAIRS.get()).addCriterion("has_smooth_twisted_rock", hasItem(ItemRegistry.POLISHED_TWISTED_ROCK.get())).build(consumer, "smooth_twisted_rock_stairs_stonecutting");

        shapedRecipe(ItemRegistry.TWISTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_BRICKS_WALL.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_brick_wall_stonecutting");

        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get(),0.1f,200).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_cracked_twisted_rock_bricks", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get())).build(consumer, "cracked_twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_cracked_twisted_rock_bricks", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get())).build(consumer, "cracked_twisted_rock_bricks_stairs_stonecutting");

        shapedRecipe(ItemRegistry.TWISTED_ROCK_TILES.get(),4).key('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).patternLine("##").patternLine("##").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), 6).key('#', ItemRegistry.TWISTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get(), 4).key('#', ItemRegistry.TWISTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.TWISTED_ROCK_TILES_WALL.get(), 6).key('#', ItemRegistry.TWISTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.TWISTED_ROCK_TILES.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_STAIRS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.TWISTED_ROCK_TILES_WALL.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_tiles_wall_stonecutting");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get(),0.1f,200).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), 6).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get(), 4).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get(), 6).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_twisted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_cracked_twisted_rock_tiles", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get())).build(consumer, "cracked_twisted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_STAIRS.get()).addCriterion("has_cracked_twisted_rock_tiles", hasItem(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get())).build(consumer, "cracked_twisted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()), ItemRegistry.CRACKED_TWISTED_ROCK_TILES_WALL.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_twisted_rock_tiles_wall_stonecutting");

        shapedRecipe(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.TWISTED_ROCK_TILES.get()).patternLine("##").patternLine("##").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS_WALL.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_wall_stonecutting");

        shapedRecipe(ItemRegistry.TWISTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("##").patternLine("##").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_bricks_from_small_bricks");

        smeltingRecipe(Ingredient.fromItems(ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(),0.1f,200).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_small_twisted_rock_bricks_smelting");
        shapedRecipe(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get(),4).key('#', ItemRegistry.CRACKED_TWISTED_ROCK_TILES.get()).patternLine("##").patternLine("##").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 6).key('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get(), 6).key('#', ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_small_twisted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_small_twisted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_small_twisted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS.get()), ItemRegistry.CRACKED_SMALL_TWISTED_ROCK_BRICKS_WALL.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cracked_small_twisted_rock_bricks_wall_stonecutting");

        shapedRecipe(ItemRegistry.CHISELED_TWISTED_ROCK.get()).key('#', ItemRegistry.TWISTED_ROCK_SLAB.get()).patternLine("#").patternLine("#").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_PILLAR.get(),2).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("#").patternLine("#").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_COLUMN.get(),2).key('#', ItemRegistry.TWISTED_ROCK_BRICKS.get()).patternLine("#").patternLine("#").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_PILLAR_CAP.get()).key('$', ItemRegistry.TWISTED_ROCK_PILLAR.get()).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).key('$', ItemRegistry.TWISTED_ROCK_COLUMN.get()).key('#', ItemRegistry.TWISTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);

        shapedRecipe(ItemRegistry.CUT_TWISTED_ROCK.get(),4).key('#', ItemRegistry.SMOOTH_TWISTED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.CUT_TWISTED_ROCK.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "cut_twisted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_PILLAR.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_pillar_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_PILLAR_CAP.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_pillar_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_COLUMN.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_column_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_COLUMN_CAP.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_column_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.TWISTED_ROCK_BRICKS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "twisted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.SMOOTH_TWISTED_ROCK.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "smooth_twisted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.POLISHED_TWISTED_ROCK.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "polished_twisted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK.get()), ItemRegistry.CHISELED_TWISTED_ROCK.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "chiseled_twisted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.CHISELED_TWISTED_ROCK.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "chiseled_twisted_rock_bricks_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_BRICKS.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_stonecutting_from_bricks");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(ItemRegistry.TWISTED_ROCK_TILES.get()), ItemRegistry.SMALL_TWISTED_ROCK_BRICKS.get()).addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer, "small_twisted_rock_bricks_stonecutting_from_tiles");

        shapedRecipe(ItemRegistry.TWISTED_ROCK_ITEM_STAND.get(), 2).key('X', ItemRegistry.TWISTED_ROCK.get()).key('Y', ItemRegistry.TWISTED_ROCK_SLAB.get()).patternLine("YYY").patternLine("XXX").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);
        shapedRecipe(ItemRegistry.TWISTED_ROCK_ITEM_PEDESTAL.get()).key('X', ItemRegistry.TWISTED_ROCK.get()).key('Y', ItemRegistry.TWISTED_ROCK_SLAB.get()).patternLine("YYY").patternLine(" X ").patternLine("YYY").addCriterion("has_twisted_rock", hasItem(ItemRegistry.TWISTED_ROCK.get())).build(consumer);

        etherTorch(consumer, ItemRegistry.ETHER_TORCH.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.ETHER.get());
        etherTorch(consumer, ItemRegistry.IRIDESCENT_ETHER_TORCH.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TAINTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TAINTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());
        etherBrazier(consumer, ItemRegistry.TWISTED_IRIDESCENT_ETHER_BRAZIER.get(), ItemRegistry.TWISTED_ROCK.get(), ItemRegistry.IRIDESCENT_ETHER.get());
    }
    private static void etherBrazier(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider rock, IItemProvider ether)
    {
        NBTCarryRecipeBuilder.shapedRecipe(output,2,Ingredient.fromItems(ether)).key('#', rock).key('S', Items.STICK).key('X', ether).patternLine("#X#").patternLine("S#S").addCriterion("has_ether", hasItem(ItemRegistry.ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath());
    }
    private static void etherTorch(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider ether)
    {
        NBTCarryRecipeBuilder.shapedRecipe(output, 4,Ingredient.fromItems(ether)).key('#', Items.STICK).key('X', ether).patternLine("X").patternLine("#").addCriterion("has_ether", hasItem(ItemRegistry.ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath() + "_alternative");
    }
    private static void smithingReinforce(Consumer<IFinishedRecipe> recipeConsumer, Item itemToReinforce, Item output)
    {
        SmithingRecipeBuilder.smithingRecipe(Ingredient.fromItems(itemToReinforce), Ingredient.fromItems(Items.NETHERITE_INGOT), output).addCriterion("has_netherite_ingot", hasItem(Items.NETHERITE_INGOT)).build(recipeConsumer, Registry.ITEM.getKey(output.asItem()).getPath() + "_smithing");
    }
    private static void shapelessPlanks(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider planks, ITag<Item> input)
    {
        shapelessRecipe(planks, 4).addIngredient(input).setGroup("planks").addCriterion("has_logs", hasItem(input)).build(recipeConsumer);
    }
    private static void shapelessWood(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider stripped, IItemProvider input)
    {
        shapedRecipe(stripped, 3).key('#', input).patternLine("##").patternLine("##").setGroup("bark").addCriterion("has_log", hasItem(input)).build(recipeConsumer);
    }
    private static void shapelessButton(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider button, IItemProvider input)
    {
        shapelessRecipe(button).addIngredient(input).addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedDoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider door, IItemProvider input)
    {
        shapedRecipe(door, 3).key('#', input).patternLine("##").patternLine("##").patternLine("##").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fence, IItemProvider input)
    {
        shapedRecipe(fence, 3).key('#', Items.STICK).key('W', input).patternLine("W#W").patternLine("W#W").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedFenceGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fenceGate, IItemProvider input)
    {
        shapedRecipe(fenceGate).key('#', Items.STICK).key('W', input).patternLine("#W#").patternLine("#W#").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedPressurePlate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pressurePlate, IItemProvider input)
    {
        shapedRecipe(pressurePlate).key('#', input).patternLine("##").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedSlab(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider slab, IItemProvider input)
    {
        shapedRecipe(slab, 6).key('#', input).patternLine("###").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedStairs(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider stairs, IItemProvider input)
    {
        shapedRecipe(stairs, 4).key('#', input).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapelessSolidTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider solid, IItemProvider normal)
    {
        shapelessRecipe(solid).addIngredient(normal).addCriterion("has_input", hasItem(normal)).build(recipeConsumer);
    }
    private static void shapedTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider trapdoor, IItemProvider input)
    {
        shapedRecipe(trapdoor, 2).key('#', input).patternLine("###").patternLine("###").addCriterion("has_input", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedSign(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider sign, IItemProvider input)
    {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shapedRecipe(sign, 3).setGroup("sign").key('#', input).key('X', Items.STICK).patternLine("###").patternLine("###").patternLine(" X ").addCriterion("has_" + s, hasItem(input)).build(recipeConsumer);
    }
    private static void shapelessColoredWool(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredWool, IItemProvider dye)
    {
        shapelessRecipe(coloredWool).addIngredient(dye).addIngredient(Blocks.WHITE_WOOL).setGroup("wool").addCriterion("has_white_wool", hasItem(Blocks.WHITE_WOOL)).build(recipeConsumer);
    }
    private static void shapedCarpet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider carpet, IItemProvider input)
    {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shapedRecipe(carpet, 3).key('#', input).patternLine("##").setGroup("carpet").addCriterion("has_" + s, hasItem(input)).build(recipeConsumer);
    }
    private static void shapelessColoredCarpet(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredCarpet, IItemProvider dye)
    {
        String s = Registry.ITEM.getKey(coloredCarpet.asItem()).getPath();
        String s1 = Registry.ITEM.getKey(dye.asItem()).getPath();
        shapedRecipe(coloredCarpet, 8).key('#', Blocks.WHITE_CARPET).key('$', dye).patternLine("###").patternLine("#$#").patternLine("###").setGroup("carpet").addCriterion("has_white_carpet", hasItem(Blocks.WHITE_CARPET)).addCriterion("has_" + s1, hasItem(dye)).build(recipeConsumer, s + "_from_white_carpet");
    }
    private static void shapedBed(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider bed, IItemProvider wool)
    {
        String s = Registry.ITEM.getKey(wool.asItem()).getPath();
        shapedRecipe(bed).key('#', wool).key('X', ItemTags.PLANKS).patternLine("###").patternLine("XXX").setGroup("bed").addCriterion("has_" + s, hasItem(wool)).build(recipeConsumer);
    }
    private static void shapedColoredBed(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredBed, IItemProvider dye)
    {
        String s = Registry.ITEM.getKey(coloredBed.asItem()).getPath();
        shapelessRecipe(coloredBed).addIngredient(Items.WHITE_BED).addIngredient(dye).setGroup("dyed_bed").addCriterion("has_bed", hasItem(Items.WHITE_BED)).build(recipeConsumer, s + "_from_white_bed");
    }
    private static void shapedBanner(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider banner, IItemProvider input)
    {
        String s = Registry.ITEM.getKey(input.asItem()).getPath();
        shapedRecipe(banner).key('#', input).key('|', Items.STICK).patternLine("###").patternLine("###").patternLine(" | ").setGroup("banner").addCriterion("has_" + s, hasItem(input)).build(recipeConsumer);
    }
    private static void shapedColoredGlass(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredGlass, IItemProvider dye)
    {
        shapedRecipe(coloredGlass, 8).key('#', Blocks.GLASS).key('X', dye).patternLine("###").patternLine("#X#").patternLine("###").setGroup("stained_glass").addCriterion("has_glass", hasItem(Blocks.GLASS)).build(recipeConsumer);
    }
    private static void shapedGlassPane(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pane, IItemProvider glass)
    {
        shapedRecipe(pane, 16).key('#', glass).patternLine("###").patternLine("###").setGroup("stained_glass_pane").addCriterion("has_glass", hasItem(glass)).build(recipeConsumer);
    }
    private static void shapedColoredPane(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredPane, IItemProvider dye)
    {
        String s = Registry.ITEM.getKey(coloredPane.asItem()).getPath();
        String s1 = Registry.ITEM.getKey(dye.asItem()).getPath();
        shapedRecipe(coloredPane, 8).key('#', Blocks.GLASS_PANE).key('$', dye).patternLine("###").patternLine("#$#").patternLine("###").setGroup("stained_glass_pane").addCriterion("has_glass_pane", hasItem(Blocks.GLASS_PANE)).addCriterion("has_" + s1, hasItem(dye)).build(recipeConsumer, s + "_from_glass_pane");
    }
    private static void shapedColoredTerracotta(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredTerracotta, IItemProvider dye)
    {
        shapedRecipe(coloredTerracotta, 8).key('#', Blocks.TERRACOTTA).key('X', dye).patternLine("###").patternLine("#X#").patternLine("###").setGroup("stained_terracotta").addCriterion("has_terracotta", hasItem(Blocks.TERRACOTTA)).build(recipeConsumer);
    }
    private static void shapedColorConcretePowder(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider coloredConcretePowder, IItemProvider dye)
    {
        shapelessRecipe(coloredConcretePowder, 8).addIngredient(dye).addIngredient(Blocks.SAND, 4).addIngredient(Blocks.GRAVEL, 4).setGroup("concrete_powder").addCriterion("has_sand", hasItem(Blocks.SAND)).addCriterion("has_gravel", hasItem(Blocks.GRAVEL)).build(recipeConsumer);
    }
    protected static EnterBlockTrigger.Instance enteredBlock(Block block)
    {
        return new EnterBlockTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, block, StatePropertiesPredicate.EMPTY);
    }
    protected static InventoryChangeTrigger.Instance hasItem(IItemProvider item)
    {
        return hasItem(ItemPredicate.Builder.create().item(item).build());
    }
    protected static InventoryChangeTrigger.Instance hasItem(ITag<Item> tag)
    {
        return hasItem(ItemPredicate.Builder.create().tag(tag).build());
    }
    protected static InventoryChangeTrigger.Instance hasItem(ItemPredicate... predicate)
    {
        return new InventoryChangeTrigger.Instance(EntityPredicate.AndPredicate.ANY_AND, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, MinMaxBounds.IntBound.UNBOUNDED, predicate);
    }
}