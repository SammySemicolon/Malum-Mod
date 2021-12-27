package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritFocusingRecipeBuilder;
import com.sammy.malum.core.registry.item.ItemRegistry;
import com.sammy.malum.core.registry.item.ItemTagRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Consumer;

import static com.sammy.malum.core.registry.content.SpiritTypeRegistry.*;

public class MalumSpiritFocusingRecipes extends RecipeProvider
{
    public MalumSpiritFocusingRecipes(DataGenerator generatorIn)
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
        new SpiritFocusingRecipeBuilder(longDuration, 2, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GUNPOWDER), 6)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GLOWSTONE_DUST), 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.REDSTONE), 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(longDuration, 3, Ingredient.of(ItemRegistry.IRON_IMPETUS.get()), Ingredient.of(Items.IRON_NUGGET), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "iron_from_impetus");

        new SpiritFocusingRecipeBuilder(longDuration, 3, Ingredient.of(ItemRegistry.GOLD_IMPETUS.get()), Ingredient.of(Items.GOLD_NUGGET), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "gold_from_impetus");

        new SpiritFocusingRecipeBuilder(longDuration, 3, Ingredient.of(ItemRegistry.COPPER_IMPETUS.get()), Ingredient.of(ItemTagRegistry.NUGGETS_COPPER), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "copper_from_impetus");

        new SpiritFocusingRecipeBuilder(longDuration, 3, Ingredient.of(ItemRegistry.LEAD_IMPETUS.get()), Ingredient.of(ItemTagRegistry.NUGGETS_LEAD), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "lead_from_impetus");

        new SpiritFocusingRecipeBuilder(longDuration, 3, Ingredient.of(ItemRegistry.SILVER_IMPETUS.get()), Ingredient.of(ItemTagRegistry.NUGGETS_SILVER), 6)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .build(consumer, "silver_from_impetus");

        new SpiritFocusingRecipeBuilder(shortDuration, 2, Ingredient.of(ItemRegistry.VITRIC_IMPETUS.get()), Ingredient.of(Items.QUARTZ), 4)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 2, Ingredient.of(ItemRegistry.VITRIC_IMPETUS.get()), Ingredient.of(ItemRegistry.BLAZING_QUARTZ.get()), 4)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 2, Ingredient.of(ItemRegistry.VITRIC_IMPETUS.get()), Ingredient.of(Items.PRISMARINE_SHARD), 4)
                .addSpirit(AQUEOUS_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 2, Ingredient.of(ItemRegistry.VITRIC_IMPETUS.get()), Ingredient.of(Items.PRISMARINE_CRYSTALS), 6)
                .addSpirit(AQUEOUS_SPIRIT, 1)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 10, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.ENDER_PEARL), 1)
                .addSpirit(ELDRITCH_SPIRIT, 3)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 10, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GHAST_TEAR), 1)
                .addSpirit(ELDRITCH_SPIRIT, 2)
                .addSpirit(AQUEOUS_SPIRIT, 8)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 10, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.RABBIT_FOOT), 1)
                .addSpirit(ARCANE_SPIRIT, 8)
                .addSpirit(SACRED_SPIRIT, 4)
                .build(consumer);

    }
}