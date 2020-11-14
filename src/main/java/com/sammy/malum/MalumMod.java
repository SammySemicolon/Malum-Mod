package com.sammy.malum;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.sammy.malum.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.data.*;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.sammy.malum.init.ModItems.*;
import static net.minecraft.item.Items.*;

@SuppressWarnings("unused")
@Mod("malum")
public class MalumMod
{
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "malum";
    public static final Random random = new Random();
    
    public MalumMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::gatherData);
    }
    
    private void gatherData(GatherDataEvent event)
    {
        DataGen.gatherData(event);
    }
    
    
    public static class DataGen
    {
        public static void gatherData(GatherDataEvent event)
        {
            DataGenerator gen = event.getGenerator();
            
            if (event.includeServer())
            {
                gen.addProvider(new Recipes(gen));
            }
        }
        
        private static class Recipes extends RecipeProvider implements IDataProvider, IConditionBuilder
        {
            public Recipes(DataGenerator gen)
            {
                super(gen);
            }
            
            @Override
            protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
            {
                //ingots
                ShapelessRecipeBuilder.shapelessRecipe(spirited_steel_nugget, 9).addIngredient(spirited_steel_ingot).addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).build(consumer);
                ShapelessRecipeBuilder.shapelessRecipe(spirited_steel_ingot).addIngredient(spirited_steel_nugget, 9).addCriterion("has_spirited_steel_nugget", hasItem(spirited_steel_nugget)).build(consumer);
                
                ShapelessRecipeBuilder.shapelessRecipe(transmissive_nugget, 9).addIngredient(transmissive_ingot).addCriterion("has_transmissive_ingot", hasItem(transmissive_ingot)).build(consumer);
                ShapelessRecipeBuilder.shapelessRecipe(transmissive_ingot).addIngredient(transmissive_nugget, 9).addCriterion("has_transmissive_nugget", hasItem(transmissive_nugget)).build(consumer);
                
                ShapelessRecipeBuilder.shapelessRecipe(umbral_steel_nugget, 9).addIngredient(umbral_steel_ingot).addCriterion("has_umbral_steel_ingot", hasItem(umbral_steel_ingot)).build(consumer);
                ShapelessRecipeBuilder.shapelessRecipe(umbral_steel_ingot).addIngredient(umbral_steel_nugget, 9).addCriterion("has_umbral_steel_nugget", hasItem(umbral_steel_nugget)).build(consumer);
                
                //combined materials
                ShapedRecipeBuilder.shapedRecipe(spirit_fabric, 2).key('a', spirit_silk).key('b', ItemTags.WOOL).addCriterion("has_spirit_silk", hasItem(spirit_silk)).patternLine(" a ").patternLine("aba").patternLine(" a ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(resonant_lens, 4).key('a', ectoplasm).key('b', GLASS_PANE).addCriterion("has_ectoplasm", hasItem(ectoplasm)).patternLine(" a ").patternLine("aba").patternLine(" a ").build(consumer);
                ShapelessRecipeBuilder.shapelessRecipe(runic_mechanism, 2).addIngredient(IRON_INGOT).addIngredient(runic_ash).addIngredient(vacant_gemstone).addIngredient(BLACK_WOOL).addIngredient(spirit_stone).addIngredient(dark_spirit_stone).addCriterion("has_runic_ash", hasItem(runic_ash)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(arcane_apparatus).key('a', spirited_steel_ingot).key('b', stygian_pearl).key('c', BLACK_WOOL).addCriterion("has_stygian_pearl", hasItem(stygian_pearl)).patternLine("cac").patternLine("aba").patternLine("cac").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(stellar_apparatus).key('a', arcane_apparatus).key('b', cursed_nebulous).key('c', runic_mechanism).addCriterion("has_stygian_pearl", hasItem(cursed_nebulous)).patternLine("cac").patternLine("aba").patternLine("cac").build(consumer);
                
                //spirit storage
                ShapedRecipeBuilder.shapedRecipe(spirit_vault).key('a', transmissive_ingot).key('b', stygian_pearl).addCriterion("has_stygian_pearl", hasItem(stygian_pearl)).patternLine(" a ").patternLine("aba").patternLine(" a ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirit_capacitor).key('a', transmissive_nugget).key('b', runic_ash).addCriterion("has_runic_ash", hasItem(runic_ash)).patternLine("aaa").patternLine("aba").patternLine("aaa").build(consumer);
                
                //tools
                ShapedRecipeBuilder.shapedRecipe(spiritwood_stave).key('a', STICK).key('b', spirit_planks).key('c', BLACK_WOOL).addCriterion("has_spirit_planks", hasItem(spirit_planks)).patternLine(" cb").patternLine(" bb").patternLine("a  ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirited_steel_buster_sword).key('a', STICK).key('b', spirited_steel_ingot).addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).patternLine("  b").patternLine(" bb").patternLine("ab ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirited_steel_excavator).key('a', STICK).key('b', spirited_steel_ingot).addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).patternLine("bbb").patternLine("bab").patternLine(" a ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(umbral_steel_buster_sword).key('a', STICK).key('b', umbral_steel_ingot).addCriterion("has_umbral_ingot", hasItem(umbral_steel_ingot)).patternLine("  b").patternLine(" bb").patternLine("ab ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(umbral_steel_excavator).key('a', STICK).key('b', umbral_steel_ingot).addCriterion("has_umbral_ingot", hasItem(umbral_steel_ingot)).patternLine("bbb").patternLine("bab").patternLine(" a ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(vacant_rapier).key('a', STICK).key('b', vacant_gemstone).addCriterion("has_vacant_gemstone", hasItem(vacant_gemstone)).patternLine(" b ").patternLine(" b ").patternLine(" a ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(ultimate_weapon).key('a', STICK).key('b', spirited_steel_ingot).addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).patternLine(" bb").patternLine(" bb").patternLine("a  ").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(bow_of_lost_souls).key('a', BONE).key('b', spirit_silk).addCriterion("has_spirit_silk", hasItem(spirit_silk)).patternLine(" ab").patternLine("a b").patternLine(" ab").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(ender_artifact).key('a', ENDER_CHEST).key('b', ENDER_EYE).key('c', arcane_apparatus).key('d', spirited_steel_ingot).addCriterion("has_arcane_apparatus", hasItem(arcane_apparatus)).patternLine("dad").patternLine("dcd").patternLine("dbd").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(shulker_storage).key('#', resonant_lens).key('-', Items.SHULKER_SHELL).patternLine("-").patternLine("#").patternLine("-").addCriterion("has_shulker_shell", hasItem(Items.SHULKER_SHELL)).build(consumer);
                
                //curios
                ShapedRecipeBuilder.shapedRecipe(spiritwood_bark_necklace).key('a', spirit_silk).key('b', runic_ash).key('c', spirit_log).patternLine(" a ").patternLine("aba").patternLine(" c ").addCriterion("has_spirit_silk", hasItem(spirit_silk)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(enchanted_lectern).key('a', spirit_planks).key('b', enchanted_quartz).patternLine("bb ").patternLine("bba").patternLine(" aa").addCriterion("has_enchanted_quartz", hasItem(enchanted_quartz)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(vacant_aegis).key('a', vacant_gemstone).key('b', spirit_stone).patternLine("aba").patternLine("aaa").patternLine(" a ").addCriterion("has_vacant_gemstone", hasItem(vacant_gemstone)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(vampire_necklace).key('a', spirit_silk).key('b', spirited_steel_ingot).key('c', FERMENTED_SPIDER_EYE).key('d', ROTTEN_FLESH).patternLine(" a ").patternLine("aba").patternLine("dcd").addCriterion("has_spirit_silk", hasItem(spirit_silk)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(necrotic_catalyst).key('a', dark_spirit_stone).key('b', ROTTEN_FLESH).patternLine("aa ").patternLine("abb").patternLine(" bb").addCriterion("has_dark_spirit_stone", hasItem(dark_spirit_stone)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(ignition_reactor).key('a', IRON_INGOT).key('b', IRON_BLOCK).key('c', FIRE_CHARGE).key('d', BLAZE_POWDER).patternLine("baa").patternLine("bcd").patternLine(" aa").addCriterion("has_charge", hasItem(FIRE_CHARGE)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(netherborne_capacitor).key('a', NETHER_WART).key('b', NETHERRACK).key('c', NETHERITE_SCRAP).key('d', spirited_steel_ingot).patternLine("dcd").patternLine("bab").patternLine(" a ").addCriterion("has_scrap", hasItem(NETHERITE_SCRAP)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(good_luck_charm).key('a', spirit_silk).key('b', GOLD_INGOT).key('c', EMERALD).patternLine(" a ").patternLine("a a").patternLine("bcb").addCriterion("has_spirit_silk", hasItem(spirit_silk)).build(consumer);
                
                //blocks
                ShapedRecipeBuilder.shapedRecipe(spirit_furnace).key('a', dark_spirit_stone).key('b', spirit_stone).key('c', BLACK_WOOL).key('d', FURNACE).key('e', BLAST_FURNACE).patternLine("ada").patternLine("bcb").patternLine("aea").addCriterion("has_spirit_stone", hasItem(spirit_stone)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(crystalline_accelerator).key('a', dark_spirit_stone).key('b', spirit_stone).key('c', arcane_apparatus).key('d', spirit_glass).patternLine(" d ").patternLine("bcb").patternLine("aaa").addCriterion("has_spirit_glass", hasItem(spirit_glass)).build(consumer);
    
                ShapedRecipeBuilder.shapedRecipe(basic_mirror, 2).key('a', vacant_gemstone).key('b', IRON_INGOT).key('c', resonant_lens).patternLine("a a").patternLine("bcb").patternLine("a a").addCriterion("has_resonant_lens", hasItem(resonant_lens)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(input_mirror).key('a', basic_mirror).key('b', HOPPER).patternLine("b").patternLine("a").addCriterion("has_resonant_lens", hasItem(resonant_lens)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(output_mirror).key('a', basic_mirror).key('b', HOPPER).patternLine("a").patternLine("b").addCriterion("has_resonant_lens", hasItem(resonant_lens)).build(consumer);
                
                ShapedRecipeBuilder.shapedRecipe(redstone_clock).key('a', REDSTONE_BLOCK).key('b', dark_spirit_stone).key('c', spirit_stone).key('d', runic_mechanism).patternLine("bdb").patternLine("dad").patternLine("cdc").addCriterion("has_runic_mechanism", hasItem(runic_mechanism)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(funk_engine).key('a', JUKEBOX).key('b', dark_spirit_stone).key('c', spirit_stone).key('d', runic_mechanism).patternLine("bdb").patternLine("dad").patternLine("cdc").addCriterion("has_runic_mechanism", hasItem(runic_mechanism)).build(consumer);
                
                
                //armor
                
                ShapedRecipeBuilder.shapedRecipe(spirited_steel_battle_helm).key('X', spirited_steel_ingot).patternLine("XXX").patternLine("X X").addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirited_steel_battle_chestplate).key('X', spirited_steel_ingot).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirited_steel_battle_leggings).key('X', spirited_steel_ingot).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirited_steel_battle_shoes).key('X', spirited_steel_ingot).patternLine("X X").patternLine("X X").addCriterion("has_spirited_steel_ingot", hasItem(spirited_steel_ingot)).build(consumer);
                
                ShapedRecipeBuilder.shapedRecipe(spirit_hunter_helm).key('X', spirit_fabric).patternLine("XXX").patternLine("X X").addCriterion("has_spirit_fabric", hasItem(spirit_fabric)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirit_hunter_chestplate).key('X', spirit_fabric).patternLine("X X").patternLine("XXX").patternLine("XXX").addCriterion("has_spirit_fabric", hasItem(spirit_fabric)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirit_hunter_leggings).key('X', spirit_fabric).patternLine("XXX").patternLine("X X").patternLine("X X").addCriterion("has_spirit_fabric", hasItem(spirit_fabric)).build(consumer);
                ShapedRecipeBuilder.shapedRecipe(spirit_hunter_shoes).key('X', spirit_fabric).patternLine("X X").patternLine("X X").addCriterion("has_spirit_fabric", hasItem(spirit_fabric)).build(consumer);
                
                ShapedRecipeBuilder.shapedRecipe(umbral_steel_helm).key('a', umbral_steel_ingot).key('b', arcane_apparatus).key('c', NETHERITE_HELMET).key('d', spirited_steel_battle_helm).key('e', spirit_hunter_helm).addCriterion("has_umbral_ingot", hasItem(umbral_steel_ingot)).patternLine("aea").patternLine("bdb").patternLine("aca").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(umbral_steel_chestplate).key('a', umbral_steel_ingot).key('b', arcane_apparatus).key('c', NETHERITE_CHESTPLATE).key('d', spirited_steel_battle_chestplate).key('e', spirit_hunter_chestplate).addCriterion("has_umbral_ingot", hasItem(umbral_steel_ingot)).patternLine("aea").patternLine("bdb").patternLine("aca").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(umbral_steel_leggings).key('a', umbral_steel_ingot).key('b', arcane_apparatus).key('c', NETHERITE_LEGGINGS).key('d', spirited_steel_battle_leggings).key('e', spirit_hunter_leggings).addCriterion("has_umbral_ingot", hasItem(umbral_steel_ingot)).patternLine("aea").patternLine("bdb").patternLine("aca").build(consumer);
                ShapedRecipeBuilder.shapedRecipe(umbral_steel_shoes).key('a', umbral_steel_ingot).key('b', arcane_apparatus).key('c', NETHERITE_BOOTS).key('d', spirited_steel_battle_shoes).key('e', spirit_hunter_shoes).addCriterion("has_umbral_ingot", hasItem(umbral_steel_ingot)).patternLine("aea").patternLine("bdb").patternLine("aca").build(consumer);
                
                
                //ores and other smelting
                CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ModBlocks.spirit_log), spirit_charcoal, 0.1F, 200).addCriterion("has_spirit_log", hasItem(ModBlocks.spirit_log)).build(consumer);
                
            }
        }
        
        private static class LootTables extends LootTableProvider implements IDataProvider
        {
            private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = ImmutableList.of(Pair.of(BlockTables::new, LootParameterSets.BLOCK)
                    //Pair.of(FishingLootTables::new, LootParameterSets.FISHING),
                    //Pair.of(ChestLootTables::new, LootParameterSets.CHEST),
                    //Pair.of(EntityLootTables::new, LootParameterSets.ENTITY),
                    //Pair.of(GiftLootTables::new, LootParameterSets.GIFT)
            );
            
            public LootTables(DataGenerator gen)
            {
                super(gen);
            }
            
            @Override
            protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables()
            {
                return tables;
            }
            
            public static class BlockTables extends BlockLootTables
            {
                @Override
                protected void addTables()
                {
                    registerDropSelfLootTable(ModBlocks.block_of_flesh);
                }
                
                @Override
                protected Iterable<Block> getKnownBlocks()
                {
                    return ForgeRegistries.BLOCKS.getValues().stream().filter(b -> b.getRegistryName().getNamespace().equals(MODID)).collect(Collectors.toList());
                }
            }
        }
    }
}