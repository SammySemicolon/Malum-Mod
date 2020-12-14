package com.sammy.malum.core.recipes;

import com.sammy.malum.MalumHelper;
import com.sammy.malum.core.init.MalumItems;
import com.sammy.malum.core.init.essences.MalumSpiritTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.ArrayList;

import static com.sammy.malum.MalumHelper.toArrayList;
import static com.sammy.malum.core.init.MalumItems.*;

public class ArcaneCraftingRecipe
{
    public static final ArrayList<ArcaneCraftingRecipe> recipes = new ArrayList<>();
    public final boolean takesSpirits;
    public final ArrayList<Item> inputItems;
    public final ArrayList<String> spirits;
    public final Item outputItem;
    public final int outputItemCount;
    
    public final boolean hasSecondOutput;
    public final Item secondOutputItem;
    public final int secondOutputItemCount;
    
    public ArcaneCraftingRecipe(ArrayList<Item> inputItems, ArrayList<String> spirits, Item outputItem, int outputItemCount, Item secondOutputItem, int secondOutputItemCount)
    {
        this.inputItems = inputItems;
        this.takesSpirits = true;
        this.spirits = spirits;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.hasSecondOutput = true;
        this.secondOutputItem = secondOutputItem;
        this.secondOutputItemCount = secondOutputItemCount;
        recipes.add(this);
    }
    public ArcaneCraftingRecipe(ArrayList<Item> inputItems, ArrayList<String> spirits, Item outputItem, int outputItemCount)
    {
        this.inputItems = inputItems;
        this.takesSpirits = true;
        this.spirits = spirits;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.secondOutputItem = null;
        this.secondOutputItemCount = 0;
        this.hasSecondOutput = false;
        recipes.add(this);
    }
    public ArcaneCraftingRecipe(ArrayList<Item> inputItems, Item outputItem, int outputItemCount, Item secondOutputItem, int secondOutputItemCount)
    {
        this.inputItems = inputItems;
        this.takesSpirits = false;
        this.spirits = null;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.hasSecondOutput = true;
        this.secondOutputItem = secondOutputItem;
        this.secondOutputItemCount = secondOutputItemCount;
        recipes.add(this);
    }
    public ArcaneCraftingRecipe(ArrayList<Item> inputItems, Item outputItem, int outputItemCount)
    {
        this.inputItems = inputItems;
        this.takesSpirits = false;
        this.spirits = null;
        this.outputItem = outputItem;
        this.outputItemCount = outputItemCount;
        this.hasSecondOutput = false;
        this.secondOutputItem = null;
        this.secondOutputItemCount = 0;
        recipes.add(this);
    }
    public static void init()
    {
        new ArcaneCraftingRecipe(toArrayList(Items.IRON_INGOT, Items.IRON_INGOT, DARK_FLARES.get()), toArrayList(MalumSpiritTypes.SINISTER_ESSENCE.identifier),RUIN_PLATING.get(), 1);
        new ArcaneCraftingRecipe(toArrayList(Items.GUNPOWDER, Items.GUNPOWDER, Items.GLOWSTONE_DUST, Items.GLOWSTONE_DUST), toArrayList(MalumSpiritTypes.ARCANE_ESSENCE.identifier),ECTOPLASM.get(), 4);
        new ArcaneCraftingRecipe(toArrayList(Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE, Items.GLASS_PANE, SEED_OF_CORRUPTION.get()),VOID_LENS.get(), 1);
        new ArcaneCraftingRecipe(toArrayList(TAINTED_ROCK.get(), Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK),CRIMSON_ROCK.get(), 9);
        new ArcaneCraftingRecipe(toArrayList(DARKENED_ROCK.get(), Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK),ARCHAIC_ROCK.get(), 9);
        
        new ArcaneCraftingRecipe(toArrayList(Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK, Items.NETHERRACK),SEED_OF_CORRUPTION.get(), 9, Items.DIAMOND_SWORD, 2);
        
        new ArcaneCraftingRecipe(toArrayList(Items.BLAZE_ROD), toArrayList(MalumSpiritTypes.NETHERBORNE_ESSENCE.identifier),Items.BLAZE_POWDER, 3);

    }
    public static ArcaneCraftingRecipe getRecipe(ArrayList<ItemStack> stacks)
    {
        for (ArcaneCraftingRecipe recipe : recipes)
        {
            ArcaneCraftingRecipe rightRecipeMaybe = test(MalumHelper.nonEmptyStackList(stacks), recipe);
            if (rightRecipeMaybe != null)
            {
                return rightRecipeMaybe;
            }
        }
        return null;
    }
    public static ArcaneCraftingRecipe test(ArrayList<ItemStack> stacks, ArcaneCraftingRecipe recipe)
    {
        if (stacks.isEmpty())
        {
            return null;
        }
        if (stacks.size() != recipe.inputItems.size())
        {
            return null;
        }
        for (ItemStack stack : stacks)
        {
            if (!recipe.inputItems.contains(stack.getItem()))
            {
                return null;
            }
        }
        return recipe;
    }
}