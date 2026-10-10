package com.sammy.malum.datagen.recipe;

import com.sammy.malum.common.data.component.soulstone.StoredInSoulstoneMetal;
import com.sammy.malum.common.recipe.CrystalPropertyModifier;
import com.sammy.malum.common.recipe.furnace.crystallarium.MalumSizedChanceResult;
import com.sammy.malum.datagen.recipe.builder.ConjunctureCrystallariumRecipeBuilder;
import com.sammy.malum.datagen.recipe.builder.OreDerealizationRecipeBuilder;
import com.sammy.malum.registry.common.MalumContent;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

public class MalumConjunctureCrystallariumRecipes implements IConditionBuilder {

    protected static void buildRecipes(RecipeOutput recipeOutput) { //TODO a lot here can later be done better in MalumMetallicsDatagen
        new OreDerealizationRecipeBuilder(
                new TagMatchTest(Tags.Blocks.ORES_IRON),
                MalumContent.AlchemyAndMetallics.IRON_METALLICS.getOre().getDefaultState(),
                CrystalPropertyModifier.DEFAULT,
                new StoredInSoulstoneMetal(MalumContent.AlchemyAndMetallics.IRON_METALLICS.getId(), MalumContent.AlchemyAndMetallics.IRON_METALLICS.getNuggetTag())
        ).save(recipeOutput);

        new OreDerealizationRecipeBuilder(
                new TagMatchTest(Tags.Blocks.ORES_COPPER),
                MalumContent.AlchemyAndMetallics.COPPER_METALLICS.getOre().getDefaultState(),
                CrystalPropertyModifier.DEFAULT,
                new StoredInSoulstoneMetal(MalumContent.AlchemyAndMetallics.COPPER_METALLICS.getId(), MalumContent.AlchemyAndMetallics.COPPER_METALLICS.getNuggetTag())
        ).save(recipeOutput);

        new ConjunctureCrystallariumRecipeBuilder(
                Ingredient.of(Items.RAW_IRON),
                new MalumSizedChanceResult(MalumContent.AlchemyAndMetallics.IRON_METALLICS.getDerealizedMetal().toStack(), 1.0F),
                200
        ).growDefaultCrystal().addMetalData(new StoredInSoulstoneMetal(MalumContent.AlchemyAndMetallics.IRON_METALLICS.getId(), MalumContent.AlchemyAndMetallics.IRON_METALLICS.getNuggetTag())).save(recipeOutput);

        new ConjunctureCrystallariumRecipeBuilder(
                Ingredient.of(Items.RAW_COPPER),
                new MalumSizedChanceResult(MalumContent.AlchemyAndMetallics.COPPER_METALLICS.getDerealizedMetal().toStack(), 1.0F),
                200
        ).growDefaultCrystal().addMetalData(new StoredInSoulstoneMetal(MalumContent.AlchemyAndMetallics.COPPER_METALLICS.getId(), MalumContent.AlchemyAndMetallics.COPPER_METALLICS.getNuggetTag())).save(recipeOutput);

        new ConjunctureCrystallariumRecipeBuilder(
                Ingredient.of(Items.RAW_GOLD),
                new MalumSizedChanceResult(MalumContent.AlchemyAndMetallics.GOLD_METALLICS.getDerealizedMetal().toStack(), 1.0F),
                200
        ).growDefaultCrystal().addMetalData(new StoredInSoulstoneMetal(MalumContent.AlchemyAndMetallics.GOLD_METALLICS.getId(), MalumContent.AlchemyAndMetallics.GOLD_METALLICS.getNuggetTag())).save(recipeOutput);
    }
}
