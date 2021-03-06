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
        shapelessRecipe(Items.PURPLE_DYE,2).addIngredient(MalumItems.LAVENDER.get()).addCriterion("has_lavender", hasItem(MalumItems.LAVENDER.get())).build(consumer);
        shapelessRecipe(Items.YELLOW_DYE,2).addIngredient(MalumItems.MARIGOLD.get()).addCriterion("has_marigold", hasItem(MalumItems.MARIGOLD.get())).build(consumer);
        
        smeltingRecipe(Ingredient.fromTag(MalumItemTags.RUNEWOOD_LOGS), MalumItems.ARCANE_CHARCOAL.get(),0.1f,200).addCriterion("has_sun_kissed_log", hasItem(MalumItemTags.RUNEWOOD_LOGS)).build(consumer);
        shapedRecipe(MalumItems.ITEM_STAND.get()).key('#', MalumItems.TAINTED_ROCK.get()).key('X', MalumItems.DARKENED_ROCK.get()).key('Y', MalumItems.TAINTED_ROCK_SLAB.get()).patternLine(" Y ").patternLine("#X#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SPIRIT_ALTAR.get()).key('Z', Items.GOLD_INGOT).key('Y', MalumItems.SOUL_GEM.get()).key('X', MalumItems.RUNEWOOD_PLANKS.get()).patternLine(" Y ").patternLine("ZXZ").patternLine("XXX").addCriterion("has_soul_gem", hasItem(MalumItems.SOUL_GEM.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SPIRIT_JAR.get()).key('Z', MalumItems.HALLOWED_GOLD_INGOT.get()).key('Y', Tags.Items.GLASS_PANES).patternLine("YZY").patternLine("Y Y").patternLine("YYY").addCriterion("has_hallowed_gold", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer);
        
        ShapelessRecipeBuilder.shapelessRecipe(Items.BLAZE_POWDER).addIngredient(MalumItems.BLAZE_QUARTZ.get()).addIngredient(MalumItems.BLAZE_QUARTZ.get()).addIngredient(Items.GUNPOWDER).addCriterion("has_blaze_quartz", hasItem(MalumItems.BLAZE_QUARTZ.get())).build(consumer, "blaze_powder_from_blaze_quartz");
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.MALUM_BOOK.get()).addIngredient(MalumItems.LAVENDER.get()).addIngredient(Items.BOOK).addCriterion("has_unholy_blend", hasItem(MalumItems.UNHOLY_BLEND.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.UNHOLY_BLEND.get()).addIngredient(Items.ROTTEN_FLESH).addIngredient(Items.SOUL_SAND).addIngredient(Items.REDSTONE).addCriterion("has_sun_kissed_log", hasItem(MalumItemTags.RUNEWOOD_LOGS)).build(consumer);
        smeltingRecipe(Ingredient.fromItems(MalumItems.UNHOLY_BLEND.get()), MalumItems.ARCANE_GRIT.get(),0.1f,200).addCriterion("has_unholy_blend", hasItem(MalumItems.UNHOLY_BLEND.get())).build(consumer);
        shapedRecipe(MalumItems.SOUL_GEM.get()).key('#', MalumItems.ARCANE_GRIT.get()).key('X', Items.DIAMOND).patternLine(" # ").patternLine("#X#").patternLine(" # ").addCriterion("has_arcane_grit", hasItem(MalumItems.ARCANE_GRIT.get())).build(consumer);
        
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.SOLAR_SAPBALL.get(), 3).addIngredient(MalumItems.SOLAR_SAP_BOTTLE.get()).addIngredient(Items.SLIME_BALL).addCriterion("has_solar_sap", hasItem(MalumItems.SOLAR_SAP_BOTTLE.get())).build(consumer);
        
        smeltingRecipe(Ingredient.fromItems(MalumItems.SOLAR_SAP_BOTTLE.get()), MalumItems.SOLAR_SYRUP_BOTTLE.get(),0.1f,200).addCriterion("has_solar_sap", hasItem(MalumItems.SOLAR_SAP_BOTTLE.get())).build(consumer);
    
        ShapelessRecipeBuilder.shapelessRecipe(Items.MAGMA_CREAM).addIngredient(Items.BLAZE_POWDER).addIngredient(MalumItems.SOLAR_SAPBALL.get()).addCriterion("has_sapball", hasItem(MalumItems.SOLAR_SAPBALL.get())).build(consumer, "magma_cream_from_sapballs");
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.POPPET_OF_UNDYING.get()).addIngredient(Items.TOTEM_OF_UNDYING).addIngredient(MalumItems.POPPET.get()).addCriterion("has_totem", hasItem(Items.TOTEM_OF_UNDYING)).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(Blocks.STICKY_PISTON).key('P', Blocks.PISTON).key('S', MalumItems.SOLAR_SAPBALL.get()).patternLine("S").patternLine("P").addCriterion("has_sapball", hasItem(MalumItems.SOLAR_SAPBALL.get())).build(consumer, "sticky_piston_from_sapballs");
        ShapedRecipeBuilder.shapedRecipe(Items.LEAD, 2).key('~', Items.STRING).key('O', MalumItems.SOLAR_SAPBALL.get()).patternLine("~~ ").patternLine("~O ").patternLine("  ~").addCriterion("has_sapball", hasItem(MalumItems.SOLAR_SAPBALL.get())).build(consumer);
    
        shapedRecipe(MalumItems.GILDED_BELT.get()).key('#', MalumItems.HALLOWED_GOLD_INGOT.get()).key('X', Items.LEATHER).key('Y', MalumItems.SOUL_GEM.get()).patternLine("XXX").patternLine("#Y#").patternLine(" # ").addCriterion("has_hallowed_gold", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer);
        shapedRecipe(MalumItems.GILDED_RING.get()).key('#', MalumItems.HALLOWED_GOLD_INGOT.get()).key('X', Items.LEATHER).patternLine(" X#").patternLine("X X").patternLine(" X ").addCriterion("has_hallowed_gold", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer);
        shapedRecipe(MalumItems.UNRELENTING_BELT.get()).key('#', MalumItems.IMPERVIOUS_ROCK.get()).key('Y', Items.LEATHER).key('X', MalumItems.SOUL_GEM.get()).patternLine(" # ").patternLine("YXY").patternLine(" # ").addCriterion("has_impervious_rock", hasItem(MalumItems.IMPERVIOUS_ROCK.get())).build(consumer);
    
        ShapedRecipeBuilder.shapedRecipe(MalumItems.CRUDE_SCYTHE.get()).key('#', Items.STICK).key('Y', MalumItems.SOUL_GEM.get()).key('X', Items.IRON_INGOT).patternLine("XXY").patternLine(" #X").patternLine("#  ").addCriterion("has_soul_gem", hasItem(MalumItems.SOUL_GEM.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_AXE.get()).key('#', Items.STICK).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XX").patternLine("X#").patternLine(" #").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_BOOTS.get()).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("X X").patternLine("X X").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_CHESTPLATE.get()).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_HELMET.get()).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XXX").patternLine("X X").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_HOE.get()).key('#', Items.STICK).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XX").patternLine(" #").patternLine(" #").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_LEGGINGS.get()).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_PICKAXE.get()).key('#', Items.STICK).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("XXX").patternLine(" # ").patternLine(" # ").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_SHOVEL.get()).key('#', Items.STICK).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("X").patternLine("#").patternLine("#").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_SWORD.get()).key('#', Items.STICK).key('X', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_BLOCK.get()).key('#', MalumItems.SOUL_STAINED_STEEL_INGOT.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.SOUL_STAINED_STEEL_INGOT.get()).key('#', MalumItems.SOUL_STAINED_STEEL_NUGGET.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer, "soul_stained_steel_from_shards");
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.SOUL_STAINED_STEEL_NUGGET.get(), 9).addIngredient(MalumItems.SOUL_STAINED_STEEL_INGOT.get()).addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.SOUL_STAINED_STEEL_INGOT.get(), 9).addIngredient(MalumItems.SOUL_STAINED_STEEL_BLOCK.get()).addCriterion("has_soul_stained_steel", hasItem(MalumItems.SOUL_STAINED_STEEL_INGOT.get())).build(consumer, "soul_stained_steel_from_soul_stained_steel_block");
        
        ShapedRecipeBuilder.shapedRecipe(MalumItems.HALLOWED_GOLD_BLOCK.get()).key('#', MalumItems.HALLOWED_GOLD_INGOT.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_transmissive_metal", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.HALLOWED_GOLD_INGOT.get()).key('#', MalumItems.HALLOWED_GOLD_NUGGET.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_transmissive_metal", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer, "transmissive_metal_from_nuggets");
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.HALLOWED_GOLD_NUGGET.get(), 9).addIngredient(MalumItems.HALLOWED_GOLD_INGOT.get()).addCriterion("has_transmissive_metal", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer);
        ShapelessRecipeBuilder.shapelessRecipe(MalumItems.HALLOWED_GOLD_INGOT.get(),9).addIngredient(MalumItems.HALLOWED_GOLD_BLOCK.get()).addCriterion("has_transmissive_metal", hasItem(MalumItems.HALLOWED_GOLD_INGOT.get())).build(consumer, "transmissive_metal_ingot_from_transmissive_metal_block");
        
        shapelessPlanks(consumer, MalumItems.RUNEWOOD_PLANKS.get(), MalumItemTags.RUNEWOOD_LOGS);
        shapelessWood(consumer, MalumItems.RUNEWOOD.get(), MalumItems.RUNEWOOD_LOG.get());
        shapelessWood(consumer, MalumItems.STRIPPED_RUNEWOOD.get(), MalumItems.STRIPPED_RUNEWOOD_LOG.get());
        shapelessButton(consumer, MalumItems.RUNEWOOD_PLANKS_BUTTON.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedDoor(consumer, MalumItems.RUNEWOOD_DOOR.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedFence(consumer, MalumItems.RUNEWOOD_PLANKS_FENCE.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedFenceGate(consumer, MalumItems.RUNEWOOD_PLANKS_FENCE_GATE.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedPressurePlate(consumer, MalumItems.RUNEWOOD_PLANKS_PRESSURE_PLATE.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedSlab(consumer, MalumItems.RUNEWOOD_PLANKS_SLAB.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, MalumItems.RUNEWOOD_PLANKS_STAIRS.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapedTrapdoor(consumer, MalumItems.RUNEWOOD_TRAPDOOR.get(), MalumItems.RUNEWOOD_PLANKS.get());
        shapelessSolidTrapdoor(consumer, MalumItems.SOLID_RUNEWOOD_TRAPDOOR.get(), MalumItems.RUNEWOOD_PLANKS.get());
        
        shapedRecipe(MalumItems.BOLTED_RUNEWOOD_PLANKS.get(),8).key('#', MalumItems.RUNEWOOD_PLANKS.get()).key('X', Items.IRON_NUGGET).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, MalumItems.BOLTED_RUNEWOOD_PLANKS_SLAB.get(), MalumItems.BOLTED_RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, MalumItems.BOLTED_RUNEWOOD_PLANKS_STAIRS.get(), MalumItems.BOLTED_RUNEWOOD_PLANKS.get());
        
        shapedRecipe(MalumItems.VERTICAL_RUNEWOOD_PLANKS.get(),3).key('#', MalumItems.RUNEWOOD_PLANKS.get()).patternLine("#").patternLine("#").patternLine("#").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, MalumItems.VERTICAL_RUNEWOOD_PLANKS_SLAB.get(), MalumItems.VERTICAL_RUNEWOOD_PLANKS.get());
        shapedStairs(consumer, MalumItems.VERTICAL_RUNEWOOD_PLANKS_STAIRS.get(), MalumItems.VERTICAL_RUNEWOOD_PLANKS.get());
        
        shapedRecipe(MalumItems.RUNEWOOD_PANEL.get(),9).key('#', MalumItems.RUNEWOOD_PLANKS.get()).patternLine("###").patternLine("###").patternLine("###").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, MalumItems.RUNEWOOD_PANEL_SLAB.get(), MalumItems.RUNEWOOD_PANEL.get());
        shapedStairs(consumer, MalumItems.RUNEWOOD_PANEL_STAIRS.get(), MalumItems.RUNEWOOD_PANEL.get());
        
        shapedRecipe(MalumItems.RUNEWOOD_TILES.get(),4).key('#', MalumItems.RUNEWOOD_PANEL.get()).patternLine("##").patternLine("##").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedSlab(consumer, MalumItems.RUNEWOOD_TILES_SLAB.get(), MalumItems.RUNEWOOD_TILES.get());
        shapedStairs(consumer, MalumItems.RUNEWOOD_TILES_STAIRS.get(), MalumItems.RUNEWOOD_TILES.get());
        
        shapedRecipe(MalumItems.CUT_RUNEWOOD_PLANKS.get(),4).key('#', MalumItems.RUNEWOOD_PANEL.get()).key('X', MalumItems.RUNEWOOD_PLANKS.get()).patternLine("##").patternLine("XX").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedRecipe(MalumItems.RUNEWOOD_BEAM.get(),3).key('#', MalumItems.VERTICAL_RUNEWOOD_PLANKS.get()).patternLine("#").patternLine("#").patternLine("#").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        shapedRecipe(MalumItems.BOLTED_RUNEWOOD_BEAM.get(),8).key('#', MalumItems.RUNEWOOD_BEAM.get()).key('X', Items.IRON_NUGGET).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_sun_kissed_planks", hasItem(MalumItems.RUNEWOOD_PLANKS.get())).build(consumer);
        
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
        ShapedRecipeBuilder.shapedRecipe(MalumItems.TAINTED_ROCK_TILES_WALL.get(), 6).key('#', MalumItems.TAINTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK.get()), MalumItems.TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_BRICKS.get()), MalumItems.TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_tainted_rock_tiles", hasItem(MalumItems.TAINTED_ROCK_TILES.get())).build(consumer, "tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_tainted_rock_tiles", hasItem(MalumItems.TAINTED_ROCK_TILES.get())).build(consumer, "tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.TAINTED_ROCK_TILES_WALL.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "tainted_rock_tiles_wall_stonecutting");
        
        smeltingRecipe(Ingredient.fromItems(MalumItems.TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES.get(),0.1f,200).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.CRACKED_TAINTED_ROCK_TILES_WALL.get(), 6).key('#', MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_BRICKS.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "cracked_tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_cracked_tainted_rock_tiles", hasItem(MalumItems.CRACKED_TAINTED_ROCK_TILES.get())).build(consumer, "cracked_tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_cracked_tainted_rock_tiles", hasItem(MalumItems.CRACKED_TAINTED_ROCK_TILES.get())).build(consumer, "cracked_tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_TAINTED_ROCK_TILES.get()), MalumItems.CRACKED_TAINTED_ROCK_TILES_WALL.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "cracked_tainted_rock_tiles_wall_stonecutting");
        
        shapelessRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).addIngredient(MalumItems.TAINTED_ROCK_TILES.get()).addIngredient(Blocks.VINE).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.MOSSY_TAINTED_ROCK_TILES_WALL.get(), 6).key('#', MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_BRICKS.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "mossy_tainted_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "mossy_tainted_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES_STAIRS.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "mossy_tainted_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_TAINTED_ROCK_TILES.get()), MalumItems.MOSSY_TAINTED_ROCK_TILES_WALL.get()).addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer, "mossy_tainted_rock_tiles_wall_stonecutting");
        
        shapedRecipe(MalumItems.CHISELED_TAINTED_ROCK.get()).key('#', MalumItems.TAINTED_ROCK_SLAB.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_PILLAR.get(),2).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_COLUMN.get(),2).key('#', MalumItems.TAINTED_ROCK_BRICKS.get()).patternLine("#").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_PILLAR_CAP.get()).key('$', MalumItems.TAINTED_ROCK_PILLAR.get()).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.TAINTED_ROCK_COLUMN_CAP.get()).key('$', MalumItems.TAINTED_ROCK_COLUMN.get()).key('#', MalumItems.TAINTED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        
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
        
        shapedRecipe(MalumItems.DARKENED_ROCK.get(), 9).key('#', MalumItems.TAINTED_ROCK.get()).key('X', Items.BLACKSTONE).patternLine("###").patternLine("#X#").patternLine("###").addCriterion("has_tainted_rock", hasItem(MalumItems.TAINTED_ROCK.get())).build(consumer);
        
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
        ShapedRecipeBuilder.shapedRecipe(MalumItems.DARKENED_ROCK_TILES_WALL.get(), 6).key('#', MalumItems.DARKENED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK.get()), MalumItems.DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_tiles_stonecutting_alt");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.DARKENED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_darkened_rock_tiles", hasItem(MalumItems.DARKENED_ROCK_TILES.get())).build(consumer, "darkened_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.DARKENED_ROCK_TILES_STAIRS.get()).addCriterion("has_darkened_rock_tiles", hasItem(MalumItems.DARKENED_ROCK_TILES.get())).build(consumer, "darkened_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.DARKENED_ROCK_TILES_WALL.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "darkened_rock_tiles_wall_stonecutting");
        
        smeltingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES.get(),0.1f,200).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).patternLine("###").addCriterion("has_cracked_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_cracked_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.CRACKED_DARKENED_ROCK_TILES_WALL.get(), 6).key('#', MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_BRICKS.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "cracked_darkened_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_cracked_darkened_rock_tiles", hasItem(MalumItems.CRACKED_DARKENED_ROCK_TILES.get())).build(consumer, "cracked_darkened_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES_STAIRS.get()).addCriterion("has_cracked_darkened_rock_tiles", hasItem(MalumItems.CRACKED_DARKENED_ROCK_TILES.get())).build(consumer, "cracked_darkened_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.CRACKED_DARKENED_ROCK_TILES.get()), MalumItems.CRACKED_DARKENED_ROCK_TILES_WALL.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "cracked_darkened_rock_tiles_wall_stonecutting");
        
        shapelessRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).addIngredient(MalumItems.DARKENED_ROCK_TILES.get()).addIngredient(Blocks.VINE).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES_SLAB.get(), 6).key('#', MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES_STAIRS.get(), 4).key('#', MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).patternLine("#  ").patternLine("## ").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(MalumItems.MOSSY_DARKENED_ROCK_TILES_WALL.get(), 6).key('#', MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).patternLine("###").patternLine("###").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_BRICKS.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "mossy_darkened_rock_tiles_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES_SLAB.get(), 2).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "mossy_darkened_rock_tiles_slab_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES_STAIRS.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "mossy_darkened_rock_tiles_stairs_stonecutting");
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.MOSSY_DARKENED_ROCK_TILES.get()), MalumItems.MOSSY_DARKENED_ROCK_TILES_WALL.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "mossy_darkened_rock_tiles_wall_stonecutting");
        
        shapedRecipe(MalumItems.CHISELED_DARKENED_ROCK.get()).key('#', MalumItems.DARKENED_ROCK_SLAB.get()).patternLine("#").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_PILLAR.get(),2).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("#").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_COLUMN.get(),2).key('#', MalumItems.DARKENED_ROCK_BRICKS.get()).patternLine("#").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_PILLAR_CAP.get()).key('$', MalumItems.DARKENED_ROCK_PILLAR.get()).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        shapedRecipe(MalumItems.DARKENED_ROCK_COLUMN_CAP.get()).key('$', MalumItems.DARKENED_ROCK_COLUMN.get()).key('#', MalumItems.DARKENED_ROCK.get()).patternLine("$").patternLine("#").addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer);
        
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
        SingleItemRecipeBuilder.stonecuttingRecipe(Ingredient.fromItems(MalumItems.DARKENED_ROCK_BRICKS.get()), MalumItems.CHISELED_DARKENED_ROCK.get()).addCriterion("has_darkened_rock", hasItem(MalumItems.DARKENED_ROCK.get())).build(consumer, "chiseled_darkened_rock_bricks_stonecutting_alt");
        
        shapelessColoredEther(consumer, MalumItems.ORANGE_ETHER.get(), Items.ORANGE_DYE);
        shapelessColoredEther(consumer, MalumItems.MAGENTA_ETHER.get(), Items.MAGENTA_DYE);
        shapelessColoredEther(consumer, MalumItems.LIGHT_BLUE_ETHER.get(), Items.LIGHT_BLUE_DYE);
        shapelessColoredEther(consumer, MalumItems.YELLOW_ETHER.get(), Items.YELLOW_DYE);
        shapelessColoredEther(consumer, MalumItems.LIME_ETHER.get(), Items.LIME_DYE);
        shapelessColoredEther(consumer, MalumItems.PINK_ETHER.get(), Items.PINK_DYE);
        shapelessColoredEther(consumer, MalumItems.CYAN_ETHER.get(), Items.CYAN_DYE);
        shapelessColoredEther(consumer, MalumItems.PURPLE_ETHER.get(), Items.PURPLE_DYE);
        shapelessColoredEther(consumer, MalumItems.BLUE_ETHER.get(), Items.BLUE_DYE);
        shapelessColoredEther(consumer, MalumItems.BROWN_ETHER.get(), Items.BROWN_DYE);
        shapelessColoredEther(consumer, MalumItems.GREEN_ETHER.get(), Items.GREEN_DYE);
        shapelessColoredEther(consumer, MalumItems.RED_ETHER.get(), Items.RED_DYE);
        
        shapelessColoredEtherTorch(consumer, MalumItems.ORANGE_ETHER_TORCH.get(), Items.ORANGE_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.MAGENTA_ETHER_TORCH.get(), Items.MAGENTA_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.LIGHT_BLUE_ETHER_TORCH.get(), Items.LIGHT_BLUE_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.YELLOW_ETHER_TORCH.get(), Items.YELLOW_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.LIME_ETHER_TORCH.get(), Items.LIME_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.PINK_ETHER_TORCH.get(), Items.PINK_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.CYAN_ETHER_TORCH.get(), Items.CYAN_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.PURPLE_ETHER_TORCH.get(), Items.PURPLE_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.BLUE_ETHER_TORCH.get(), Items.BLUE_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.BROWN_ETHER_TORCH.get(), Items.BROWN_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.GREEN_ETHER_TORCH.get(), Items.GREEN_DYE);
        shapelessColoredEtherTorch(consumer, MalumItems.RED_ETHER_TORCH.get(), Items.RED_DYE);
        
        coloredEtherTorch(consumer, MalumItems.ORANGE_ETHER_TORCH.get(), MalumItems.ORANGE_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.MAGENTA_ETHER_TORCH.get(), MalumItems.MAGENTA_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.LIGHT_BLUE_ETHER_TORCH.get(), MalumItems.LIGHT_BLUE_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.YELLOW_ETHER_TORCH.get(), MalumItems.YELLOW_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.LIME_ETHER_TORCH.get(), MalumItems.LIME_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.PINK_ETHER_TORCH.get(), MalumItems.PINK_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.CYAN_ETHER_TORCH.get(), MalumItems.CYAN_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.PURPLE_ETHER_TORCH.get(), MalumItems.PURPLE_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.BLUE_ETHER_TORCH.get(), MalumItems.BLUE_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.BROWN_ETHER_TORCH.get(), MalumItems.BROWN_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.GREEN_ETHER_TORCH.get(), MalumItems.GREEN_ETHER.get());
        coloredEtherTorch(consumer, MalumItems.RED_ETHER_TORCH.get(), MalumItems.RED_ETHER.get());
        
        shapedColoredEtherBrazier(consumer, MalumItems.ORANGE_ETHER_BRAZIER.get(), MalumItems.ORANGE_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.MAGENTA_ETHER_BRAZIER.get(), MalumItems.MAGENTA_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.LIGHT_BLUE_ETHER_BRAZIER.get(), MalumItems.LIGHT_BLUE_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.YELLOW_ETHER_BRAZIER.get(), MalumItems.YELLOW_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.LIME_ETHER_BRAZIER.get(), MalumItems.LIME_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.PINK_ETHER_BRAZIER.get(), MalumItems.PINK_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.CYAN_ETHER_BRAZIER.get(), MalumItems.CYAN_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.PURPLE_ETHER_BRAZIER.get(), MalumItems.PURPLE_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.BLUE_ETHER_BRAZIER.get(), MalumItems.BLUE_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.BROWN_ETHER_BRAZIER.get(), MalumItems.BROWN_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.GREEN_ETHER_BRAZIER.get(), MalumItems.GREEN_ETHER.get());
        shapedColoredEtherBrazier(consumer, MalumItems.RED_ETHER_BRAZIER.get(), MalumItems.RED_ETHER.get());
        
        shapelessColoredEtherBrazier(consumer, MalumItems.ORANGE_ETHER_BRAZIER.get(), Items.ORANGE_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.MAGENTA_ETHER_BRAZIER.get(), Items.MAGENTA_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.LIGHT_BLUE_ETHER_BRAZIER.get(), Items.LIGHT_BLUE_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.YELLOW_ETHER_BRAZIER.get(), Items.YELLOW_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.LIME_ETHER_BRAZIER.get(), Items.LIME_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.PINK_ETHER_BRAZIER.get(), Items.PINK_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.CYAN_ETHER_BRAZIER.get(), Items.CYAN_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.PURPLE_ETHER_BRAZIER.get(), Items.PURPLE_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.BLUE_ETHER_BRAZIER.get(), Items.BLUE_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.BROWN_ETHER_BRAZIER.get(), Items.BROWN_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.GREEN_ETHER_BRAZIER.get(), Items.GREEN_DYE);
        shapelessColoredEtherBrazier(consumer, MalumItems.RED_ETHER_BRAZIER.get(), Items.RED_DYE);
    }
    private static void shapelessColoredEther(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider dye) {
        ShapelessRecipeBuilder.shapelessRecipe(output).addIngredient(dye).addIngredient(MalumItems.PINK_ETHER.get()).addCriterion("has_ether", hasItem(MalumItems.PINK_ETHER.get())).build(recipeConsumer);
    }
    private static void shapelessColoredEtherTorch(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider dye) {
        ShapelessRecipeBuilder.shapelessRecipe(output).addIngredient(dye).addIngredient(MalumItems.PINK_ETHER_TORCH.get()).addCriterion("has_ether", hasItem(MalumItems.PINK_ETHER.get())).build(recipeConsumer);
    }
    private static void shapelessColoredEtherBrazier(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider dye) {
        ShapelessRecipeBuilder.shapelessRecipe(output).addIngredient(dye).addIngredient(MalumItems.PINK_ETHER_BRAZIER.get()).addCriterion("has_ether", hasItem(MalumItems.PINK_ETHER.get())).build(recipeConsumer);
    }
    private static void shapedColoredEtherBrazier(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider ether)
    {
        ShapedRecipeBuilder.shapedRecipe(output,2).key('#', MalumItems.TAINTED_ROCK.get()).key('S', Items.STICK).key('X', ether).patternLine("#X#").patternLine("S#S").addCriterion("has_ether", hasItem(MalumItems.PINK_ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath() + "_brazier");
    }
    private static void coloredEtherTorch(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider output, IItemProvider ether)
    {
        ShapedRecipeBuilder.shapedRecipe(output, 4).key('#', Items.STICK).key('X', ether).patternLine("X").patternLine("#").addCriterion("has_ether", hasItem(MalumItems.PINK_ETHER.get())).build(recipeConsumer, output.asItem().getRegistryName().getPath() + "_alternative");
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
    private static void shapelessSolidTrapdoor(Consumer<IFinishedRecipe> recipeConsumer, IItemProvider button, IItemProvider input)
    {
        shapelessRecipe(button).addIngredient(input).addCriterion("has_input", hasItem(input)).build(recipeConsumer);
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