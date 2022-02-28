package com.sammy.malum.core.data;

import com.sammy.malum.core.data.builder.SpiritFocusingRecipeBuilder;
import com.sammy.malum.core.helper.DataHelper;
import com.sammy.malum.core.setup.content.item.ItemRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

import static com.sammy.malum.core.setup.content.SpiritTypeRegistry.*;

public class MalumSpiritFocusingRecipes extends RecipeProvider implements IConditionBuilder {
    public MalumSpiritFocusingRecipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "Malum Spirit Crucible Recipe Provider";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        int longDuration = 600;
        int metalDuration = 1200;
        int shortDuration = 300;
        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GUNPOWDER), 8)
                .addSpirit(EARTHEN_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.GLOWSTONE_DUST), 8)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);

        new SpiritFocusingRecipeBuilder(shortDuration, 1, Ingredient.of(ItemRegistry.ALCHEMICAL_IMPETUS.get()), Ingredient.of(Items.REDSTONE), 8)
                .addSpirit(ARCANE_SPIRIT, 1)
                .build(consumer);

        metalNodeRecipe(consumer, metalDuration, ItemRegistry.IRON_IMPETUS, ItemRegistry.IRON_NODE);
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.GOLD_IMPETUS, ItemRegistry.GOLD_NODE);
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.COPPER_IMPETUS, ItemRegistry.COPPER_NODE);
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.LEAD_IMPETUS, ItemRegistry.LEAD_NODE, "forge:nuggets/lead");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.SILVER_IMPETUS, ItemRegistry.SILVER_NODE, "forge:nuggets/silver");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.ALUMINUM_IMPETUS, ItemRegistry.ALUMINUM_NODE, "forge:nuggets/aluminum");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.NICKEL_IMPETUS, ItemRegistry.NICKEL_NODE, "forge:nuggets/nickel");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.URANIUM_IMPETUS, ItemRegistry.URANIUM_NODE, "forge:nuggets/uranium");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.OSMIUM_IMPETUS, ItemRegistry.OSMIUM_NODE, "forge:nuggets/osmium");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.ZINC_IMPETUS, ItemRegistry.ZINC_NODE, "forge:nuggets/zinc");
        metalNodeRecipe(consumer, metalDuration, ItemRegistry.TIN_IMPETUS, ItemRegistry.TIN_NODE, "forge:nuggets/tin");
    }

    public void metalNodeRecipe(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<Item> impetus, RegistryObject<Item> node) {
        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), Ingredient.of(node.get()), 2)
                .addSpirit(EARTHEN_SPIRIT, 2)
                .addSpirit(INFERNAL_SPIRIT, 1)
                .build(consumer);
    }

    public void metalNodeRecipe(Consumer<FinishedRecipe> consumer, int duration, RegistryObject<Item> impetus, RegistryObject<Item> node, String tag) {

        ConditionalRecipe.builder().addCondition(not(new TagEmptyCondition(tag))).addRecipe(
                        new SpiritFocusingRecipeBuilder(duration, 2, Ingredient.of(impetus.get()), Ingredient.of(node.get()), 2)
                                .addSpirit(EARTHEN_SPIRIT, 2)
                                .addSpirit(INFERNAL_SPIRIT, 1)
                                ::build
                )
                .generateAdvancement()
                .build(consumer, DataHelper.prefix("conditional_node_" + tag.replace("forge:nuggets", "")));
    }
}