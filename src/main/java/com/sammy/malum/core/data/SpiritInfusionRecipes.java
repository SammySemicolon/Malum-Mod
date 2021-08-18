package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritInfusionRecipeBuilder;
import com.sammy.malum.core.init.items.MalumItems;
import com.sammy.malum.core.mod_content.MalumSpiritTypes;
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

public class SpiritInfusionRecipes extends RecipeProvider
{
    public SpiritInfusionRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Spirit Infusion Recipe Provider";
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer)
    {
        new SpiritInfusionRecipeBuilder(Items.COBBLESTONE, 16, MalumItems.TAINTED_ROCK.get(), 16).build(consumer, "tainted_rock");

        new SpiritInfusionRecipeBuilder(Items.GOLD_INGOT, 1, MalumItems.HALLOWED_GOLD_INGOT.get(), 1)
                .addExtraItem(Items.QUARTZ, 2)
                .addExtraItem(MalumItems.SOULSTONE.get(), 1)
                .addSpirit(MalumSpiritTypes.SACRED_SPIRIT, 1)
                .addSpirit(MalumSpiritTypes.ARCANE_SPIRIT, 2)
                .build(consumer, "hallowed_gold");

    }
}