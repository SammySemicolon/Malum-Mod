package com.sammy.malum.core.data;

import com.sammy.malum.core.init.MalumItemTags;
import com.sammy.malum.core.init.MalumItems;
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

import static com.sammy.malum.core.init.MalumItemTags.SAPBALLS;
import static net.minecraft.data.CookingRecipeBuilder.blastingRecipe;
import static net.minecraft.data.CookingRecipeBuilder.smeltingRecipe;
import static net.minecraft.data.ShapedRecipeBuilder.shapedRecipe;
import static net.minecraft.data.ShapelessRecipeBuilder.shapelessRecipe;

public class MalumRecipeProvider extends RecipeProvider
{
    public MalumRecipeProvider(DataGenerator generatorIn)
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
        shapelessRecipe(Items.PURPLE_DYE,2).addIngredient(MalumItemTags.LAVENDER).addCriterion("has_lavender", hasItem(MalumItemTags.LAVENDER)).build(consumer);
        shapelessRecipe(Items.YELLOW_DYE,2).addIngredient(MalumItems.MARIGOLD.get()).addCriterion("has_marigold", hasItem(MalumItems.MARIGOLD.get())).build(consumer);
    
        shapelessRecipe(MalumItems.TAINT_RUDIMENT.get()).addIngredient(Items.WHEAT_SEEDS).addIngredient(Items.CRIMSON_FUNGUS).addIngredient(Items.WARPED_FUNGUS).addCriterion("has_wheat_seeds", hasItem(Items.WHEAT_SEEDS)).build(consumer);
        shapelessRecipe(MalumItems.DARK_FLARES.get(),4).addIngredient(MalumItems.FLARED_GLOWSTONE_BLOCK.get()).addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.FLARED_GLOWSTONE_BLOCK.get()).key('#', MalumItems.DARK_FLARES.get()).patternLine("##").patternLine("##").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapelessRecipe(MalumItems.ABSTRUSE_BLOCK.get()).addIngredient(Items.PRISMARINE).addIngredient(MalumItems.DARK_FLARES.get()).addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapelessRecipe(MalumItems.WITHER_SAND.get()).addIngredient(Items.SOUL_SAND).addIngredient(MalumItems.DARK_FLARES.get()).addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        blastingRecipe(Ingredient.fromItems(Items.BLAZE_POWDER), MalumItems.SULPHUR.get(),0.25f,200).addCriterion("has_blaze_rod", hasItem(Items.BLAZE_ROD)).build(consumer);
        smeltingRecipe(Ingredient.fromItems(Items.LEATHER), MalumItems.ADHESIVE.get(),0.25f,200).addCriterion("has_leather", hasItem(Items.LEATHER)).build(consumer);
        smeltingRecipe(Ingredient.fromTag(MalumItemTags.SUN_KISSED_LOGS), MalumItems.ARCANE_CHARCOAL.get(),0.1f,200).addCriterion("has_seed_of_corruption", hasItem(MalumItems.TAINT_RUDIMENT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.CONTAINED_TAINT.get()).key('#', Tags.Items.GLASS_PANES).key('X', MalumItems.TAINT_RUDIMENT.get()).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_seed_of_corruption", hasItem(MalumItems.TAINT_RUDIMENT.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_FURNACE.get()).key('#', MalumItems.CONTAINED_TAINT.get()).key('X', MalumItems.TAINTED_ROCK.get()).key('Y', MalumItems.DARKENED_ROCK.get()).key('Z', Items.FURNACE).patternLine("XYX").patternLine("#Z#").patternLine("XYX").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.ITEM_STAND.get()).key('#', MalumItems.TAINTED_ROCK.get()).key('X', MalumItems.DARKENED_ROCK.get()).key('Y', MalumItems.TAINTED_ROCK_SLAB.get()).patternLine(" Y ").patternLine("#X#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
    
        shapelessRecipe(MalumItems.SOLAR_SAPBALL.get()).addIngredient(MalumItems.ADHESIVE.get()).addIngredient(MalumItems.SOLAR_SAP_BOTTLE.get()).addCriterion("has_solar_sap", hasItem(MalumItems.SOLAR_SAP_BOTTLE.get())).build(consumer);
        shapelessRecipe(MalumItems.LUNAR_SAPBALL.get()).addIngredient(MalumItems.ADHESIVE.get()).addIngredient(MalumItems.LUNAR_SAP_BOTTLE.get()).addCriterion("has_lunar_sap", hasItem(MalumItems.LUNAR_SAP_BOTTLE.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(MalumItems.SOLAR_SAP_BOTTLE.get()), MalumItems.SOLAR_SYRUP_BOTTLE.get(),0.1f,200).addCriterion("has_solar_sap", hasItem(MalumItems.SOLAR_SAP_BOTTLE.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(MalumItems.LUNAR_SAP_BOTTLE.get()), MalumItems.LUNAR_SYRUP_BOTTLE.get(),0.1f,200).addCriterion("has_lunar_sap", hasItem(MalumItems.LUNAR_SAP_BOTTLE.get())).build(consumer);
        
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGMA_CREAM).addIngredient(Items.BLAZE_POWDER).addIngredient(SAPBALLS).addCriterion("has_blaze_powder", hasItem(Items.BLAZE_POWDER)).build(consumer, "magma_cream_from_sapballs");
        ShapedRecipeBuilder.shapedRecipe(Blocks.STICKY_PISTON).key('P', Blocks.PISTON).key('S', SAPBALLS).patternLine("S").patternLine("P").addCriterion("has_sapballs", hasItem(SAPBALLS)).build(consumer, "sticky_piston_from_sapballs");
        ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2).key('~', Items.STRING).key('O', SAPBALLS).patternLine("~~ ").patternLine("~O ").patternLine("  ~").addCriterion("has_sapballs", hasItem(SAPBALLS)).build(consumer);
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_AXE.get()).key('#', Items.STICK).key('X', MalumItems.RUIN_PLATING.get()).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_BOOTS.get()).key('X', MalumItems.RUIN_PLATING.get()).patternLine("X X").patternLine("X X").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_CHESTPLATE.get()).key('X', MalumItems.RUIN_PLATING.get()).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_HELMET.get()).key('X', MalumItems.RUIN_PLATING.get()).patternLine("XXX").patternLine("X X").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_HOE.get()).key('#', Items.STICK).key('X', MalumItems.RUIN_PLATING.get()).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_LEGGINGS.get()).key('X', MalumItems.RUIN_PLATING.get()).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_PICKAXE.get()).key('#', Items.STICK).key('X', MalumItems.RUIN_PLATING.get()).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_SHOVEL.get()).key('#', Items.STICK).key('X', MalumItems.RUIN_PLATING.get()).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_SWORD.get()).key('#', Items.STICK).key('X', MalumItems.RUIN_PLATING.get()).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_PLATING_BLOCK.get(), 4).key('#', MalumItems.RUIN_PLATING.get()).patternLine("##").patternLine("##").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.RUIN_PLATING.get()).key('#', MalumItems.RUIN_SCRAPS.get()).patternLine("###").patternLine("###").patternLine("###").setGroup("iron_ingot").addCriterion("has_ruin_scraps", hasItem(MalumItems.RUIN_SCRAPS.get())).build(consumer, "ruin_plating_from_scraps");
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.RUIN_SCRAPS.get(), 9).addIngredient(MalumItems.RUIN_PLATING.get()).addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.RUIN_PLATING.get(), 4).addIngredient(MalumItemTags.RUIN_PLATING_BLOCKS).addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer, "ruin_plating_from_ruin_plating_block");
        shapedRecipe(MalumItems.RUIN_PLATING_TILES.get(),4).key('#', MalumItems.RUIN_PLATING_BLOCK.get()).patternLine("##").patternLine("##").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.RUIN_PLATING_TILES_SLAB.get(), MalumItems.RUIN_PLATING_TILES.get());
        shapedWoodenStairs(consumer, MalumItems.RUIN_PLATING_TILES_STAIRS.get(), MalumItems.RUIN_PLATING_TILES.get());
        shapedRecipe(MalumItems.STACKED_RUIN_PLATING.get(),4).key('#', MalumItems.RUIN_PLATING_TILES.get()).patternLine("##").patternLine("##").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.STACKED_RUIN_PLATING_SLAB.get(), MalumItems.STACKED_RUIN_PLATING.get());
        shapedWoodenStairs(consumer, MalumItems.STACKED_RUIN_PLATING_STAIRS.get(), MalumItems.STACKED_RUIN_PLATING.get());
        shapedRecipe(MalumItems.RUIN_PLATING_BLOCK.get(),4).key('#', MalumItems.STACKED_RUIN_PLATING.get()).patternLine("##").patternLine("##").addCriterion("has_ruin_plating", hasItem(MalumItems.RUIN_PLATING.get())).build(consumer, "ruin_plating_block_from_stacked_ruin_plating");
        shapedWoodenSlab(consumer, MalumItems.RUIN_PLATING_BLOCK_SLAB.get(), MalumItems.RUIN_PLATING_BLOCK.get());
        shapedWoodenStairs(consumer, MalumItems.RUIN_PLATING_BLOCK_STAIRS.get(), MalumItems.RUIN_PLATING_BLOCK.get());
        ShapedRecipeBuilder.shapedRecipe(MalumItems.TRANSMISSIVE_METAL_BLOCK.get(), 4).key('#', MalumItems.TRANSMISSIVE_METAL_INGOT.get()).patternLine("##").patternLine("##").addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.TRANSMISSIVE_METAL_INGOT.get()).key('#', MalumItems.TRANSMISSIVE_METAL_NUGGET.get()).patternLine("###").patternLine("###").patternLine("###").setGroup("iron_ingot").addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer, "transmissive_metal_from_nuggets");
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.TRANSMISSIVE_METAL_NUGGET.get(), 9).addIngredient(MalumItems.TRANSMISSIVE_METAL_INGOT.get()).addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.TRANSMISSIVE_METAL_INGOT.get()).addIngredient(MalumItemTags.TRANSMISSIVE_METAL_BLOCKS).addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer, "transmissive_metal_ingot_from_transmissive_metal_block");
        shapedRecipe(MalumItems.TRANSMISSIVE_METAL_TILES.get(),4).key('#', MalumItems.TRANSMISSIVE_METAL_BLOCK.get()).patternLine("##").patternLine("##").addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.TRANSMISSIVE_METAL_TILES_SLAB.get(), MalumItems.TRANSMISSIVE_METAL_TILES.get());
        shapedWoodenStairs(consumer, MalumItems.TRANSMISSIVE_METAL_TILES_STAIRS.get(), MalumItems.TRANSMISSIVE_METAL_TILES.get());
        shapedRecipe(MalumItems.STACKED_TRANSMISSIVE_METAL.get(),4).key('#', MalumItems.TRANSMISSIVE_METAL_TILES.get()).patternLine("##").patternLine("##").addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.STACKED_TRANSMISSIVE_METAL_SLAB.get(), MalumItems.STACKED_TRANSMISSIVE_METAL.get());
        shapedWoodenStairs(consumer, MalumItems.STACKED_TRANSMISSIVE_METAL_STAIRS.get(), MalumItems.STACKED_TRANSMISSIVE_METAL.get());
        shapedRecipe(MalumItems.TRANSMISSIVE_METAL_BLOCK.get(),4).key('#', MalumItems.STACKED_TRANSMISSIVE_METAL.get()).patternLine("##").patternLine("##").addCriterion("has_transmissive_metal", hasItem(MalumItems.TRANSMISSIVE_METAL_INGOT.get())).build(consumer, "transmissive_metal_block_from_stacked_transmissive_metal");
        shapedWoodenSlab(consumer, MalumItems.TRANSMISSIVE_METAL_BLOCK_SLAB.get(), MalumItems.TRANSMISSIVE_METAL_BLOCK.get());
        shapedWoodenStairs(consumer, MalumItems.TRANSMISSIVE_METAL_BLOCK_STAIRS.get(), MalumItems.TRANSMISSIVE_METAL_BLOCK.get());
        
        shapelessPlanks(consumer, MalumItems.TAINTED_PLANKS.get(), MalumItemTags.TAINTED_LOGS);
        shapelessWood(consumer, MalumItems.TAINTED_WOOD.get(), MalumItems.TAINTED_LOG.get());
        shapelessWood(consumer, MalumItems.STRIPPED_TAINTED_WOOD.get(), MalumItems.STRIPPED_TAINTED_LOG.get());
        shapelessWoodenButton(consumer, MalumItems.TAINTED_PLANKS_BUTTON.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenDoor(consumer, MalumItems.TAINTED_DOOR.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenFence(consumer, MalumItems.TAINTED_PLANKS_FENCE.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenFenceGate(consumer, MalumItems.TAINTED_PLANKS_FENCE_GATE.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenPressurePlate(consumer, MalumItems.TAINTED_PLANKS_PRESSURE_PLATE.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenSlab(consumer, MalumItems.TAINTED_PLANKS_SLAB.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenStairs(consumer, MalumItems.TAINTED_PLANKS_STAIRS.get(), MalumItems.TAINTED_PLANKS.get());
        shapedWoodenTrapdoor(consumer, MalumItems.TAINTED_TRAPDOOR.get(), MalumItems.TAINTED_PLANKS.get());
        shapelessSolidWoodenTrapDoor(consumer, MalumItems.SOLID_TAINTED_TRAPDOOR.get(), MalumItems.TAINTED_TRAPDOOR.get());
    
        shapelessPlanks(consumer, MalumItems.SUN_KISSED_PLANKS.get(), MalumItemTags.SUN_KISSED_LOGS);
        shapelessWood(consumer, MalumItems.SUN_KISSED_WOOD.get(), MalumItems.SUN_KISSED_LOG.get());
        shapelessWood(consumer, MalumItems.STRIPPED_SUN_KISSED_WOOD.get(), MalumItems.STRIPPED_SUN_KISSED_LOG.get());
        shapelessWoodenButton(consumer, MalumItems.SUN_KISSED_PLANKS_BUTTON.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenDoor(consumer, MalumItems.SUN_KISSED_DOOR.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenFence(consumer, MalumItems.SUN_KISSED_PLANKS_FENCE.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenFenceGate(consumer, MalumItems.SUN_KISSED_PLANKS_FENCE_GATE.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenPressurePlate(consumer, MalumItems.SUN_KISSED_PLANKS_PRESSURE_PLATE.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenSlab(consumer, MalumItems.SUN_KISSED_PLANKS_SLAB.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenStairs(consumer, MalumItems.SUN_KISSED_PLANKS_STAIRS.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenTrapdoor(consumer, MalumItems.SUN_KISSED_TRAPDOOR.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapelessSolidWoodenTrapDoor(consumer, MalumItems.SOLID_SUN_KISSED_TRAPDOOR.get(), MalumItems.SUN_KISSED_PLANKS.get());
    
        shapedRecipe(MalumItems.BOLTED_SUN_KISSED_PLANKS.get(),8).key('#', MalumItems.SUN_KISSED_PLANKS.get()).key('X', Items.IRON_NUGGET).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.SUN_KISSED_PLANKS.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.BOLTED_SUN_KISSED_PLANKS_SLAB.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenStairs(consumer, MalumItems.BOLTED_SUN_KISSED_PLANKS_STAIRS.get(), MalumItems.SUN_KISSED_PLANKS.get());
        
        shapedRecipe(MalumItems.VERTICAL_SUN_KISSED_PLANKS.get(),3).key('#', MalumItems.SUN_KISSED_PLANKS.get()).patternLine("#").patternLine("#").patternLine("#").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.SUN_KISSED_PLANKS.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.VERTICAL_SUN_KISSED_PLANKS_SLAB.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenStairs(consumer, MalumItems.VERTICAL_SUN_KISSED_PLANKS_STAIRS.get(), MalumItems.SUN_KISSED_PLANKS.get());
        
        shapedRecipe(MalumItems.SUN_KISSED_PANEL.get(),9).key('#', MalumItems.SUN_KISSED_PLANKS.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.SUN_KISSED_PLANKS.get())).build(consumer);
        shapedWoodenSlab(consumer, MalumItems.SUN_KISSED_PANEL_SLAB.get(), MalumItems.SUN_KISSED_PLANKS.get());
        shapedWoodenStairs(consumer, MalumItems.SUN_KISSED_PANEL_STAIRS.get(), MalumItems.SUN_KISSED_PLANKS.get());
        
        shapedRecipe(MalumItems.CUT_SUN_KISSED_PLANKS.get(),4).key('#', MalumItems.SUN_KISSED_PANEL.get()).key('X', MalumItems.SUN_KISSED_PLANKS.get()).patternLine("##").patternLine("XX").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.SUN_KISSED_PLANKS.get())).build(consumer);
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.TAINTED_ROCK_WALL.get(), 6).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_SLAB.get(), 6).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_STAIRS.get(), 4).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_WALL.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_wall_stonecutting");
    
        shapedRecipe(MalumItems.POLISHED_TAINTED_ROCK.get(),9).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.POLISHED_TAINTED_ROCK_SLAB.get(), 6).key('#', MalumItems.POLISHED_TAINTED_ROCK.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.POLISHED_TAINTED_ROCK_STAIRS.get(), 4).key('#', MalumItems.POLISHED_TAINTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.POLISHED_TAINTED_ROCK.get()), MalumItems.POLISHED_TAINTED_ROCK_SLAB.get(), 2).addCriterion("has_polished_tainted_rock", hasItem(MalumItems.POLISHED_TAINTED_ROCK.get())).build(consumer, "polished_tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.POLISHED_TAINTED_ROCK.get()), MalumItems.POLISHED_TAINTED_ROCK_STAIRS.get()).addCriterion("has_polished_tainted_rock", hasItem(MalumItems.POLISHED_TAINTED_ROCK.get())).build(consumer, "polished_tainted_rock_stairs_stonecutting");
    
        smeltingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.SMOOTH_TAINTED_ROCK.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.SMOOTH_TAINTED_ROCK_SLAB.get(), 6).key('#', MalumItems.SMOOTH_TAINTED_ROCK.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.SMOOTH_TAINTED_ROCK_STAIRS.get(), 4).key('#', MalumItems.SMOOTH_TAINTED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.SMOOTH_TAINTED_ROCK.get()), MalumItems.SMOOTH_TAINTED_ROCK_SLAB.get(), 2).addCriterion("has_smooth_tainted_rock", hasItem(MalumItems.POLISHED_TAINTED_ROCK.get())).build(consumer, "smooth_tainted_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.SMOOTH_TAINTED_ROCK.get()), MalumItems.SMOOTH_TAINTED_ROCK_STAIRS.get()).addCriterion("has_smooth_tainted_rock", hasItem(MalumItems.POLISHED_TAINTED_ROCK.get())).build(consumer, "smooth_tainted_rock_stairs_stonecutting");
    
        shapedRecipe(MalumItems.TAINTED_ROCK_BRICKS.get(),4).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', MalumItems.TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', MalumItems.TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', MalumItems.TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_tainted_rock_bricks", hasItem(MalumItems.TAINTED_ROCK_BRICKS.get())).build(consumer, "tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_tainted_rock_bricks", hasItem(MalumItems.TAINTED_ROCK_BRICKS.get())).build(consumer, "tainted_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.TAINTED_ROCK_BRICKS_WALL.get()).addCriterion("has_tainted_rock_bricks", hasItem(MalumItems.TAINTED_ROCK_BRICKS.get())).build(consumer, "tainted_rock_brick_wall_stonecutting");
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()), MalumItems.CRACKED_TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_cracked_tainted_rock_bricks", hasItem(MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get())).build(consumer, "cracked_tainted_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()), MalumItems.CRACKED_TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_cracked_tainted_rock_bricks", hasItem(MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get())).build(consumer, "cracked_tainted_rock_bricks_stairs_stonecutting");
    
        shapelessRecipe(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()).addIngredient(MalumItems.TAINTED_ROCK_BRICKS.get()).addIngredient(Blocks.VINE).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_BRICKS_SLAB.get(), 6).key('#', MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_BRICKS_STAIRS.get(), 4).key('#', MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_BRICKS_WALL.get(), 6).key('#', MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()), MalumItems.MOSSY_TAINTED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_mossy_tainted_rock_bricks", hasItem(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get())).build(consumer, "mossy_tainted_rock_brick_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()), MalumItems.MOSSY_TAINTED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_mossy_tainted_rock_bricks", hasItem(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get())).build(consumer, "mossy_tainted_rock_brick_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()), MalumItems.MOSSY_TAINTED_ROCK_BRICKS_WALL.get()).addCriterion("has_mossy_tainted_rock_bricks", hasItem(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get())).build(consumer, "mossy_tainted_rock_brick_wall_stonecutting");
    
        shapedRecipe(MalumItems.TAINTED_ROCK_TILES.get(),4).key('#', MalumItems.TAINTED_ROCK_BRICKS.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_tainted_rock_tiles", hasItem(MalumItems.TAINTED_ROCK_TILES.get())).build(consumer, "tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_tainted_rock_tiles", hasItem(MalumItems.TAINTED_ROCK_TILES.get())).build(consumer, "tainted_rock_tiles_stairs_stonecutting");
    
        smeltingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "cracked_tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_cracked_tainted_rock_tiles", hasItem(MalumItems.CRACKED_TAINTED_ROCK_TILES.get())).build(consumer, "cracked_tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_cracked_tainted_rock_tiles", hasItem(MalumItems.CRACKED_TAINTED_ROCK_TILES.get())).build(consumer, "cracked_tainted_rock_tiles_stairs_stonecutting");
        
        shapelessRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).addIngredient(MalumItems.TAINTED_ROCK_TILES.get()).addIngredient(Blocks.VINE).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "mossy_tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_mossy_tainted_rock_tiles", hasItem(MalumItems.MOSSY_TAINTED_ROCK_TILES.get())).build(consumer, "mossy_tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_mossy_tainted_rock_tiles", hasItem(MalumItems.MOSSY_TAINTED_ROCK_TILES.get())).build(consumer, "mossy_tainted_rock_tiles_stairs_stonecutting");
    
        shapedRecipe(MalumItems.CHISELED_TAINTED_ROCK.get()).key('#', MalumItems.TAINTED_ROCK_SLAB.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_PILLAR.get(),2).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_COLUMN.get(),2).key('#', MalumItems.TAINTED_ROCK_BRICKS.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_PILLAR_CAP.get()).key('$', MalumItems.TAINTED_ROCK_PILLAR.get()).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_COLUMN_CAP.get()).key('$', MalumItems.TAINTED_ROCK_COLUMN.get()).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
    
        shapedRecipe(MalumItems.TAINTED_FLARED_LANTERN.get(),8).key('#', MalumItems.SMOOTH_TAINTED_ROCK.get()).key('X', MalumItems.DARK_FLARES.get()).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.FLARED_TAINTED_ROCK.get(),4).key('#', MalumItems.SMOOTH_TAINTED_ROCK.get()).key('X', MalumItems.DARK_FLARES.get()).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.HORIZONTAL_FLARED_TAINTED_ROCK.get(),2).key('#', MalumItems.SMOOTH_TAINTED_ROCK.get()).key('X', MalumItems.DARK_FLARES.get()).patternLine("#").patternLine("X").patternLine("#").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.CUT_TAINTED_ROCK.get(),4).key('#', MalumItems.SMOOTH_TAINTED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.CUT_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "cut_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_PILLAR.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_pillar_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_PILLAR_CAP.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_pillar_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_COLUMN.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_column_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_COLUMN_CAP.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_column_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_BRICKS.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.SMOOTH_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "smooth_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.POLISHED_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "polished_tainted_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.CHISELED_TAINTED_ROCK.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "chiseled_tainted_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.CHISELED_TAINTED_ROCK.get()).addCriterion("has_tainted_rock_bricks", hasItem(MalumItems.TAINTED_ROCK_BRICKS.get())).build(consumer, "chiseled_tainted_rock_bricks_stonecutting_alt");
    
        shapedRecipe(MalumItems.DARKENED_ROCK.get(), 9).key('#', MalumItems.TAINTED_ROCK.get()).key('X', Items.COAL).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.DARKENED_ROCK_WALL.get(), 6).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_SLAB.get(), 6).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_STAIRS.get(), 4).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_SLAB.get(), 2).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_STAIRS.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_WALL.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_wall_stonecutting");
    
        shapedRecipe(MalumItems.POLISHED_DARKENED_ROCK.get(),9).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.POLISHED_DARKENED_ROCK_SLAB.get(), 6).key('#', MalumItems.POLISHED_DARKENED_ROCK.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.POLISHED_DARKENED_ROCK_STAIRS.get(), 4).key('#', MalumItems.POLISHED_DARKENED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.POLISHED_DARKENED_ROCK.get()), MalumItems.POLISHED_DARKENED_ROCK_SLAB.get(), 2).addCriterion("has_polished_darkened_rock", hasItem(MalumItems.POLISHED_DARKENED_ROCK.get())).build(consumer, "polished_darkened_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.POLISHED_DARKENED_ROCK.get()), MalumItems.POLISHED_DARKENED_ROCK_STAIRS.get()).addCriterion("has_polished_darkened_rock", hasItem(MalumItems.POLISHED_DARKENED_ROCK.get())).build(consumer, "polished_darkened_rock_stairs_stonecutting");
    
        smeltingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.SMOOTH_DARKENED_ROCK.get(),0.1f,200).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.SMOOTH_DARKENED_ROCK_SLAB.get(), 6).key('#', MalumItems.SMOOTH_DARKENED_ROCK.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.SMOOTH_DARKENED_ROCK_STAIRS.get(), 4).key('#', MalumItems.SMOOTH_DARKENED_ROCK.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.SMOOTH_DARKENED_ROCK.get()), MalumItems.SMOOTH_DARKENED_ROCK_SLAB.get(), 2).addCriterion("has_smooth_darkened_rock", hasItem(MalumItems.POLISHED_DARKENED_ROCK.get())).build(consumer, "smooth_darkened_rock_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.SMOOTH_DARKENED_ROCK.get()), MalumItems.SMOOTH_DARKENED_ROCK_STAIRS.get()).addCriterion("has_smooth_darkened_rock", hasItem(MalumItems.POLISHED_DARKENED_ROCK.get())).build(consumer, "smooth_darkened_rock_stairs_stonecutting");
    
        shapedRecipe(MalumItems.DARKENED_ROCK_BRICKS.get(),4).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_BRICKS_SLAB.get(), 6).key('#', MalumItems.DARKENED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_BRICKS_STAIRS.get(), 4).key('#', MalumItems.DARKENED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.DARKENED_ROCK_BRICKS_WALL.get(), 6).key('#', MalumItems.DARKENED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.DARKENED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_darkened_rock_bricks", hasItem(MalumItems.DARKENED_ROCK_BRICKS.get())).build(consumer, "darkened_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.DARKENED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_darkened_rock_bricks", hasItem(MalumItems.DARKENED_ROCK_BRICKS.get())).build(consumer, "darkened_rock_bricks_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.DARKENED_ROCK_BRICKS_WALL.get()).addCriterion("has_darkened_rock_bricks", hasItem(MalumItems.DARKENED_ROCK_BRICKS.get())).build(consumer, "darkened_rock_brick_wall_stonecutting");
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_BRICKS_WALL.get(), 6).key('#', MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        smeltingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get(),0.1f,200).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_BRICKS_SLAB.get(), 6).key('#', MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_cracked_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_BRICKS_STAIRS.get(), 4).key('#', MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()), MalumItems.CRACKED_DARKENED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_cracked_darkened_rock_bricks", hasItem(MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get())).build(consumer, "cracked_darkened_rock_bricks_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()), MalumItems.CRACKED_DARKENED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_cracked_darkened_rock_bricks", hasItem(MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get())).build(consumer, "cracked_darkened_rock_bricks_stairs_stonecutting");
    
        shapelessRecipe(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()).addIngredient(MalumItems.DARKENED_ROCK_BRICKS.get()).addIngredient(Blocks.VINE).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_BRICKS_SLAB.get(), 6).key('#', MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_BRICKS_STAIRS.get(), 4).key('#', MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_BRICKS_WALL.get(), 6).key('#', MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()), MalumItems.MOSSY_DARKENED_ROCK_BRICKS_SLAB.get(), 2).addCriterion("has_mossy_darkened_rock_bricks", hasItem(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get())).build(consumer, "mossy_darkened_rock_brick_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()), MalumItems.MOSSY_DARKENED_ROCK_BRICKS_STAIRS.get()).addCriterion("has_mossy_darkened_rock_bricks", hasItem(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get())).build(consumer, "mossy_darkened_rock_brick_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()), MalumItems.MOSSY_DARKENED_ROCK_BRICKS_WALL.get()).addCriterion("has_mossy_darkened_rock_bricks", hasItem(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get())).build(consumer, "mossy_darkened_rock_brick_wall_stonecutting");
    
        shapedRecipe(MalumItems.DARKENED_ROCK_TILES.get(),4).key('#', MalumItems.DARKENED_ROCK_BRICKS.get()).patternLine("##").patternLine("##").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.DARKENED_ROCK_TILES.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.DARKENED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.DARKENED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_darkened_rock_tiles", hasItem(MalumItems.DARKENED_ROCK_TILES.get())).build(consumer, "darkened_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.DARKENED_ROCK_TILES_STAIRS.get()).addCriterion("has_darkened_rock_tiles", hasItem(MalumItems.DARKENED_ROCK_TILES.get())).build(consumer, "darkened_rock_tiles_stairs_stonecutting");
    
        smeltingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES.get(),0.1f,200).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).patternLine("###").addCriterion("has_cracked_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "cracked_darkened_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_cracked_darkened_rock_tiles", hasItem(MalumItems.CRACKED_DARKENED_ROCK_TILES.get())).build(consumer, "cracked_darkened_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES_STAIRS.get()).addCriterion("has_cracked_darkened_rock_tiles", hasItem(MalumItems.CRACKED_DARKENED_ROCK_TILES.get())).build(consumer, "cracked_darkened_rock_tiles_stairs_stonecutting");
    
        shapelessRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).addIngredient(MalumItems.DARKENED_ROCK_TILES.get()).addIngredient(Blocks.VINE).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "mossy_darkened_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_mossy_darkened_rock_tiles", hasItem(MalumItems.MOSSY_DARKENED_ROCK_TILES.get())).build(consumer, "mossy_darkened_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES_STAIRS.get()).addCriterion("has_mossy_darkened_rock_tiles", hasItem(MalumItems.MOSSY_DARKENED_ROCK_TILES.get())).build(consumer, "mossy_darkened_rock_tiles_stairs_stonecutting");
    
        shapedRecipe(MalumItems.CHISELED_DARKENED_ROCK.get()).key('#', MalumItems.DARKENED_ROCK_SLAB.get()).patternLine("#").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_PILLAR.get(),2).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("#").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_COLUMN.get(),2).key('#', MalumItems.DARKENED_ROCK_BRICKS.get()).patternLine("#").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_PILLAR_CAP.get()).key('$', MalumItems.DARKENED_ROCK_PILLAR.get()).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_COLUMN_CAP.get()).key('$', MalumItems.DARKENED_ROCK_COLUMN.get()).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
    
        shapedRecipe(MalumItems.DARKENED_FLARED_LANTERN.get(),8).key('#', MalumItems.SMOOTH_DARKENED_ROCK.get()).key('X', MalumItems.DARK_FLARES.get()).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.FLARED_DARKENED_ROCK.get(),4).key('#', MalumItems.SMOOTH_DARKENED_ROCK.get()).key('X', MalumItems.DARK_FLARES.get()).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.HORIZONTAL_FLARED_DARKENED_ROCK.get(),2).key('#', MalumItems.SMOOTH_DARKENED_ROCK.get()).key('X', MalumItems.DARK_FLARES.get()).patternLine("#").patternLine("X").patternLine("#").addCriterion("has_flared_glowstone", hasItem(MalumItems.FLARED_GLOWSTONE_BLOCK.get())).build(consumer);
        shapedRecipe(MalumItems.CUT_DARKENED_ROCK.get(),4).key('#', MalumItems.SMOOTH_DARKENED_ROCK.get()).patternLine("##").patternLine("##").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.CUT_DARKENED_ROCK.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "cut_darkened_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_PILLAR.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_pillar_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_PILLAR_CAP.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_pillar_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_COLUMN.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_column_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_COLUMN_CAP.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_column_cap_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_BRICKS.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.SMOOTH_DARKENED_ROCK.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "smooth_darkened_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.POLISHED_DARKENED_ROCK.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "polished_darkened_rock_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.CHISELED_DARKENED_ROCK.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "chiseled_darkened_rock_bricks_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.CHISELED_DARKENED_ROCK.get()).addCriterion("has_darkened_rock_bricks", hasItem(MalumItems.DARKENED_ROCK_BRICKS.get())).build(consumer, "chiseled_darkened_rock_bricks_stonecutting_alt");
    
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
    private static void shapedBoat(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider boat, IItemProvider input)
    {
        shapedRecipe(boat).key('#', input).patternLine("# #").patternLine("###").setGroup("boat").addCriterion("in_water", enteredBlock(Blocks.WATER)).build(recipeConsumer);
    }
    private static void shapelessWoodenButton(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider button, IItemProvider input)
    {
        shapelessRecipe(button).addIngredient(input).setGroup("wooden_button").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenDoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider door, IItemProvider input)
    {
        shapedRecipe(door, 3).key('#', input).patternLine("##").patternLine("##").patternLine("##").setGroup("wooden_door").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenFence(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fence, IItemProvider input)
    {
        shapedRecipe(fence, 3).key('#', Items.STICK).key('W', input).patternLine("W#W").patternLine("W#W").setGroup("wooden_fence").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenFenceGate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider fenceGate, IItemProvider input)
    {
        shapedRecipe(fenceGate).key('#', Items.STICK).key('W', input).patternLine("#W#").patternLine("#W#").setGroup("wooden_fence_gate").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenPressurePlate(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider pressurePlate, IItemProvider input)
    {
        shapedRecipe(pressurePlate).key('#', input).patternLine("##").setGroup("wooden_pressure_plate").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenSlab(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider slab, IItemProvider input)
    {
        shapedRecipe(slab, 6).key('#', input).patternLine("###").setGroup("wooden_slab").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenStairs(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider stairs, IItemProvider input)
    {
        shapedRecipe(stairs, 4).key('#', input).patternLine("#  ").patternLine("## ").patternLine("###").setGroup("wooden_stairs").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapelessSolidWoodenTrapDoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider button, IItemProvider input)
    {
        shapelessRecipe(button).addIngredient(input).setGroup("wooden_trapdoor").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
    }
    private static void shapedWoodenTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider trapdoor, IItemProvider input)
    {
        shapedRecipe(trapdoor, 2).key('#', input).patternLine("###").patternLine("###").setGroup("wooden_trapdoor").addCriterion("has_planks", hasItem(input)).build(recipeConsumer);
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