package com.sammy.malum.recipes;

import com.sammy.malum.init.ModItems;
import com.sammy.malum.init.ModRecipes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import static com.sammy.malum.recipes.CrystallineAcceleratorRecipe.crystallineAcceleratorRecipeTypeEnum.*;

public class CrystallineAcceleratorRecipe
{
    public enum crystallineAcceleratorRecipeTypeEnum
    {
        basic(0), inputSpirit(1), outputSpirit(2);
        
        public final int type;
    
        crystallineAcceleratorRecipeTypeEnum(int type) { this.type = type;}
    }
    private final Item inputItem;
    private final int inputCount;
    private final int recipeTime;
    private final Item outputItem;
    private final int outputCount;
    private final String inputSpirit;
    private final int inputSpiritCount;
    public final crystallineAcceleratorRecipeTypeEnum type;
    public CrystallineAcceleratorRecipe(Item inputItem, int inputCount, int recipeTime, Item outputItem, int outputCount, boolean produceSpirit)
    {
        this.inputItem = inputItem;
        this.inputCount = inputCount;
        this.recipeTime = recipeTime;
        this.outputItem = outputItem;
        this.outputCount = outputCount;
        this.type = produceSpirit ? outputSpirit : basic;
        this.inputSpirit = null;
        this.inputSpiritCount = 0;
    }
    
    public CrystallineAcceleratorRecipe(Item inputItem, int inputCount, int recipeTime, Item outputItem, int outputCount,String inputSpirit,int inputSpiritCount)
    {
        this.inputItem = inputItem;
        this.inputCount = inputCount;
        this.recipeTime = recipeTime;
        this.outputItem = outputItem;
        this.outputCount = outputCount;
        this.inputSpirit = inputSpirit;
        this.inputSpiritCount = inputSpiritCount;
        this.type = crystallineAcceleratorRecipeTypeEnum.inputSpirit;
    }
    
    public static void initData()
    {
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(ModItems.enchanted_quartz,1, 100, ModItems.enriched_crystal, 2, false));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(ModItems.crystalline_compound,1, 1000, ModItems.enriched_crystal, 1, true));
    
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(ModItems.archaic_quartz_ore,1, 800, ModItems.archaic_quartz, 1, "minecraft:ghast", 1));
    
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.COAL_ORE,1, 200, Items.COAL, 3, "minecraft:blaze", 1));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.LAPIS_ORE,1, 200, Items.LAPIS_LAZULI, 6, "minecraft:blaze", 1));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.REDSTONE_ORE,1, 200, Items.REDSTONE, 9, "minecraft:blaze", 1));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.DIAMOND_ORE,1, 400, Items.DIAMOND, 3, "minecraft:blaze", 2));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.EMERALD_ORE,1, 400, Items.EMERALD, 6, "minecraft:blaze", 2));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.NETHER_QUARTZ_ORE,1, 200, Items.QUARTZ, 6, "minecraft:blaze", 1));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.ANCIENT_DEBRIS,1, 800, Items.NETHERITE_SCRAP, 2, "minecraft:wither_skeleton", 4));
        ModRecipes.addCrystallineAcceleratorRecipe(new CrystallineAcceleratorRecipe(Items.PRISMARINE_CRYSTALS,4, 50, Items.SEA_LANTERN, 1, false));
    }
    
    public Item getInputItem()
    {
        return inputItem;
    }
    
    public int getInputCount()
    {
        return inputCount;
    }
    
    public int getRecipeTime()
    {
        return recipeTime;
    }
    
    public Item getOutputItem()
    {
        return outputItem;
    }
    
    public int getOutputCount()
    {
        return outputCount;
    }
    
    public int getInputSpiritCount()
    {
        return inputSpiritCount;
    }
    
    public String getInputSpirit()
    {
        return inputSpirit;
    }
}