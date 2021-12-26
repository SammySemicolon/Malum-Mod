package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritCrucibleRecipeBuilder;
import com.sammy.malum.core.registry.item.ItemTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.ARCANE_SPIRIT;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.EARTHEN_SPIRIT;
import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.INFERNAL_SPIRIT;
import static com.sammy.malum.core.registry.item.ItemRegistry.*;

public class MalumSpiritCrucibleRecipes extends RecipeProvider
{
    public MalumSpiritCrucibleRecipes(DataGenerator generatorIn)
    {
        super(generatorIn);
    }

    @Override
    public String getName()
    {
        return "Malum Spirit Crucible Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer)
    {
        int longDuration = 1200;
        int shortDuration = 400;
        new SpiritCrucibleRecipeBuilder(longDuration, 2, Ingredient.of(ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GUNPOWDER), 6)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritCrucibleRecipeBuilder(shortDuration, 1, Ingredient.of(ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GLOWSTONE_DUST), 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritCrucibleRecipeBuilder(shortDuration, 1, Ingredient.of(ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.REDSTONE), 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritCrucibleRecipeBuilder(longDuration, 3, Ingredient.of(IRON_IMPETUS.get()), Ingredient.of(Items.IRON_NUGGET), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "iron_from_impetus");

        new SpiritCrucibleRecipeBuilder(longDuration, 3, Ingredient.of(GOLD_IMPETUS.get()), Ingredient.of(Items.GOLD_NUGGET), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "gold_from_impetus");

        new SpiritCrucibleRecipeBuilder(longDuration, 3, Ingredient.of(COPPER_IMPETUS.get()), Ingredient.of(ItemTagRegistry.NUGGETS_COPPER), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "copper_from_impetus");

        new SpiritCrucibleRecipeBuilder(longDuration, 3, Ingredient.of(LEAD_IMPETUS.get()), Ingredient.of(ItemTagRegistry.NUGGETS_LEAD), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "lead_from_impetus");

        new SpiritCrucibleRecipeBuilder(longDuration, 3, Ingredient.of(SILVER_IMPETUS.get()), Ingredient.of(ItemTagRegistry.NUGGETS_SILVER), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "silver_from_impetus");

    }
}